package pers.zuo.util;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import pers.zuo.knn.KnnDataGrid;
import pers.zuo.knn.KnnDataPoint;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author : zuojingang
 * @description : KNN工具类，辅助进行落点判断及概率统计，此工具所定义的近邻统计，最细粒度为1
 * @since : 2022/7/30 11:22, Saturday
 **/
public class KnnUtils {

    /**
     * 初始化网格（第一步）
     *
     * @param min                 维度最小值
     * @param max                 维度最大值
     * @param dimension           维度数量
     * @param tolerance           近邻统计允许的误差范围
     * @param minValidSampleCount 最小有效样本数
     * @param <T>                 业务数据类型
     * @return 初始网格
     */
    public static <T> KnnDataGrid<T> initGrid(BigDecimal min, BigDecimal max, Integer dimension, BigDecimal tolerance, Integer minValidSampleCount) {
        if (Stream.of(min, max, dimension, tolerance).anyMatch(Objects::isNull) || min.compareTo(max) >= 0) {
            throw new RuntimeException("param error");
        }
        // 最小值向下取整
        BigDecimal minFloor = min.setScale(0, RoundingMode.FLOOR);
        // 最大值向上取整
        BigDecimal maxCeiling = max.setScale(0, RoundingMode.CEILING);
        KnnDataGrid<T> grid = new KnnDataGrid<>();
        grid.setTolerance(tolerance);
        grid.setMinValidSampleCount(minValidSampleCount);
        grid.setLevel(0);
        // 维度长度
        BigDecimal dimensionalLength = maxCeiling.subtract(minFloor);
        // 维度半径
        BigDecimal dimensionalRadius = dimensionalLength.divide(new BigDecimal("2"), 0, RoundingMode.CEILING);
        // 近邻计算，需要在规则下尽可能覆盖更多的数据，以减小误差，半径=Max(radius, tolerance)
        dimensionalRadius = dimensionalRadius.compareTo(tolerance) > 0 ? dimensionalRadius.add(tolerance) : tolerance;
        // 设置半径维度
        grid.setDimensionalRadius(dimensionalRadius);
        // 维度中心值
        BigDecimal center = minFloor.add(maxCeiling).divide(new BigDecimal("2"), 0, RoundingMode.HALF_UP);
        KnnDataPoint<T> centerPoint = new KnnDataPoint<>();
        centerPoint.setCoordinates(Lists.newArrayList());
        IntStream.range(0, dimension)
                .forEach(i ->
                        centerPoint.getCoordinates().add(center)
                );
        centerPoint.setGrids(Lists.newArrayList());
        centerPoint.getGrids().add(grid);
        grid.setCenterPoint(centerPoint);
        grid.setIncludedPoints(Lists.newArrayList());
        grid.setChildren(Lists.newArrayList());
        grid.setCoverageRatio(BigDecimal.ONE);
        return grid;
    }

    /**
     * 落点，将数据落到对应的网格中（第二步）
     *
     * @param grid  网格
     * @param point 数据点
     * @param <T>   业务数据类型
     */
    public static <T> void placement(KnnDataGrid<T> grid, KnnDataPoint<T> point) {
        if (Objects.isNull(grid) || Objects.isNull(grid.getDimensionalRadius()) || Objects.isNull(grid.getCenterPoint())
                || CollectionUtils.isEmpty(grid.getCenterPoint().getCoordinates()) || grid.getCenterPoint().getCoordinates().stream().anyMatch(Objects::isNull)) {
            throw new RuntimeException("param error");
        }
        if (Objects.isNull(point) || CollectionUtils.isEmpty(point.getCoordinates()) || point.getCoordinates().stream().anyMatch(Objects::isNull)
                // long 和 int 使用 equals 判断，结果为false，使用 == 判断，结果为true
                || grid.getCenterPoint().getCoordinates().stream().filter(Objects::nonNull).count() != point.getCoordinates().size()) {
            // 数据点异常，直接抛弃
            return;
        }
        // 网格中心点
        KnnDataPoint<T> centerPoint = grid.getCenterPoint();
        // 网格中心点坐标
        List<BigDecimal> centerPointCoordinates = centerPoint.getCoordinates();
        // 数据点坐标
        List<BigDecimal> pointCoordinates = point.getCoordinates();
        for (int i = 0; i < centerPointCoordinates.size(); i++) {
            // 中心点i维度坐标值
            BigDecimal centerCoordinate = centerPointCoordinates.get(i);
            // 数据点i维度坐标值
            BigDecimal coordinate = pointCoordinates.get(i);
            // i维度距离
            BigDecimal dimensionalDistance = centerCoordinate.subtract(coordinate).abs();
            if (dimensionalDistance.compareTo(grid.getDimensionalRadius()) > 0) {
                // 只要有一个维度的维度距离超过误差允许范围，就无法落点成功
                return;
            }
        }
        // 初始化网格落点集合
        if (Objects.isNull(grid.getIncludedPoints())) {
            grid.setIncludedPoints(new ArrayList<>());
        }
        grid.getIncludedPoints().add(point);
        if (Objects.isNull(point.getGrids())) {
            point.setGrids(new ArrayList<>());
        }
        point.getGrids().add(grid);
    }

    /**
     * 切分网格并落点（第三步）
     *
     * @param grid 网格
     * @param <T>  业务数据类型
     */
    public static <T> void segmentationThenPlacement(KnnDataGrid<T> grid) {
        if (Objects.isNull(grid) || Objects.isNull(grid.getDimensionalRadius()) || Objects.isNull(grid.getCenterPoint()) || Objects.isNull(grid.getTolerance())
                || CollectionUtils.isEmpty(grid.getCenterPoint().getCoordinates()) || grid.getCenterPoint().getCoordinates().stream().anyMatch(Objects::isNull)) {
            throw new RuntimeException("param error");
        }
        // 切分是否成功
        boolean segmentation = segmentation(grid);
        if (!segmentation) {
            // 如果切分失败，则说明已经不可切分，不需要继续往下走了
            return;
        }
        // 当前网格的子网格列表
        List<KnnDataGrid<T>> children = grid.getChildren();
        for (Iterator<KnnDataGrid<T>> iterator = children.iterator(); iterator.hasNext(); ) {
            KnnDataGrid<T> child = iterator.next();
            // 对每个子网格进行落点计算
            for (KnnDataPoint<T> point : grid.getIncludedPoints()) {
                // 对grid的每个数据点对子网格进行落点计算
                placement(child, point);
            }
            // 当前子网格落点 对比 最小有效落点数
            if (CollectionUtils.isEmpty(child.getIncludedPoints()) || child.getIncludedPoints().size() < child.getMinValidSampleCount()) {
                // 移除无效的子网格，减少不必要的空间消耗
                iterator.remove();
                continue;
            }
            // 对每个子网格进行切分及落点计算（实测切1014次，会栈溢出）
            //
            // java -XX:+PrintFlagsFinal -version | grep ThreadStackSize
            //      intx CompilerThreadStackSize                  = 1024                                   {pd product} {default}
            //      intx ThreadStackSize                          = 1024                                   {pd product} {default}
            //      intx VMThreadStackSize                        = 1024                                   {pd product} {default}
            segmentationThenPlacement(child);
        }
    }

    /**
     * 网格切分，每个维度分为有交叉（交叉部分为误差）的两部分
     *
     * @param <T>  业务数据类型
     * @param grid 网格
     * @return
     */
    private static <T> boolean segmentation(KnnDataGrid<T> grid) {
        if (grid.isLastLevel() || CollectionUtils.isEmpty(grid.getIncludedPoints()) || grid.getIncludedPoints().size() < grid.getMinValidSampleCount() || grid.getDimensionalRadius().compareTo(grid.getTolerance()) <= 0) {
            // 网格没有落点，或者网格落点少于最小有效样本数，或者网格半径小于等于近邻统计允许的误差范围，则不需要继续切分
            return false;
        }
        // 当前网格的级别
        Integer level = grid.getLevel();
        // 近邻归纳可容忍的误差
        BigDecimal tolerance = grid.getTolerance();
        // 维度半径
        BigDecimal dimensionalRadius = grid.getDimensionalRadius();
        // 子网格维度直径
        BigDecimal childDimensionalDiameter = dimensionalRadius.add(tolerance);
        // 子网格维度半径
        BigDecimal childDimensionalRadius = childDimensionalDiameter.divide(new BigDecimal("2"), 0, RoundingMode.CEILING);
        // 如果子网格维度半径计算结果 等于 当前网格维度半径，则标记当前网格为最后一级网格
        boolean lastLevel = childDimensionalRadius.compareTo(dimensionalRadius) == 0;
        // 如果当前网格为最后一级网格，则切分半径为允许误差
        childDimensionalRadius = lastLevel ? tolerance : childDimensionalRadius;
        // 执行切分
        doSegmentation(grid, level, tolerance, dimensionalRadius, childDimensionalRadius, lastLevel);
        return true;
    }

    /**
     * 执行切分
     *
     * @param grid
     * @param level
     * @param tolerance
     * @param dimensionalRadius
     * @param childDimensionalRadius
     * @param <T>
     */
    private static <T> void doSegmentation(KnnDataGrid<T> grid, Integer level, BigDecimal tolerance, BigDecimal dimensionalRadius, BigDecimal childDimensionalRadius, boolean lastLevel) {
        // 当前网格中心点
        KnnDataPoint<T> centerPoint = grid.getCenterPoint();
        // 当前网格中心点的坐标
        List<BigDecimal> centerPointCoordinates = centerPoint.getCoordinates();
        // 子网格的中心点坐标集合
        List<List<BigDecimal>> childrenCenterCoordinatesResult = getChildrenCenterCoordinates(dimensionalRadius, childDimensionalRadius, centerPointCoordinates);
        // 构造子网格，并将其挂载到当前网格
        for (List<BigDecimal> childrenCenterCoordinate : childrenCenterCoordinatesResult) {
            // 网格的最小有效样本数
            Integer minValidSampleCount = grid.getMinValidSampleCount();
            // 子网格
            KnnDataGrid<T> childGrid = new KnnDataGrid<>();
            // 子网格中心点
            KnnDataPoint<T> childCenterPoint = new KnnDataPoint<>();
            childCenterPoint.setCoordinates(Lists.newArrayList(childrenCenterCoordinate));
            childCenterPoint.setGrids(Lists.newArrayList());
            childCenterPoint.getGrids().add(childGrid);
            childGrid.setCenterPoint(childCenterPoint);

            childGrid.setLevel(level + 1);
            childGrid.setLastLevel(lastLevel);
            childGrid.setTolerance(tolerance);
            childGrid.setMinValidSampleCount(minValidSampleCount);
            childGrid.setDimensionalRadius(childDimensionalRadius);
            childGrid.setIncludedPoints(Lists.newArrayList());
            childGrid.setChildren(Lists.newArrayList());
            childGrid.setParent(grid);
            // 初始化子网格列表
            if (Objects.isNull(grid.getChildren())) {
                grid.setChildren(new ArrayList<>());
            }
            // 将子网格挂载在父网格上
            grid.getChildren().add(childGrid);
        }
    }

    /**
     * 子网格的中心点坐标集合
     *
     * @param dimensionalRadius
     * @param childDimensionalRadius
     * @param centerPointCoordinates
     * @return
     */
    private static List<List<BigDecimal>> getChildrenCenterCoordinates(BigDecimal dimensionalRadius, BigDecimal childDimensionalRadius, List<BigDecimal> centerPointCoordinates) {
        // 子网格的中心点坐标集合
        List<List<BigDecimal>> childrenCenterCoordinatesResult = new ArrayList<>();
        for (BigDecimal centerCoordinate : centerPointCoordinates) {
            // 当前网格在当前维度的最小值（向下取整）
            BigDecimal minCoordinate = centerCoordinate.subtract(dimensionalRadius);
            // 当前网格在当前维度的最大值（向上取整）
            BigDecimal maxCoordinate = centerCoordinate.add(dimensionalRadius);
            // 维度较小中心坐标
            BigDecimal minOfDimensionalCenter = minCoordinate.add(childDimensionalRadius);
            // 维度较大中心坐标
            BigDecimal maxOfDimensionalCenter = maxCoordinate.subtract(childDimensionalRadius);
            // 初始化子网格中心坐标
            if (CollectionUtils.isEmpty(childrenCenterCoordinatesResult)) {
                // 第一维度较小中心坐标
                List<BigDecimal> minDimensionalCenterCoordinates = new ArrayList<>();
                childrenCenterCoordinatesResult.add(minDimensionalCenterCoordinates);
                minDimensionalCenterCoordinates.add(minOfDimensionalCenter);
                // 第一维度较大中心坐标
                List<BigDecimal> maxDimensionalCenterCoordinates = new ArrayList<>();
                childrenCenterCoordinatesResult.add(maxDimensionalCenterCoordinates);
                maxDimensionalCenterCoordinates.add(maxOfDimensionalCenter);
                continue;
            }
            // 定义临时存储子网格中心点不完整坐标的集合
            List<List<BigDecimal>> childrenCenterCoordinatesTemporary = new ArrayList<>();
            // 组合当前维度较小中心坐标
            for (List<BigDecimal> childrenCenterCoordinate : childrenCenterCoordinatesResult) {
                List<BigDecimal> childCenterCoordinates = new ArrayList<>(childrenCenterCoordinate);
                childCenterCoordinates.add(minOfDimensionalCenter);
                childrenCenterCoordinatesTemporary.add(childCenterCoordinates);
            }
            // 组合当前维度较大中心坐标
            for (List<BigDecimal> childrenCenterCoordinate : childrenCenterCoordinatesResult) {
                List<BigDecimal> childCenterCoordinates = new ArrayList<>(childrenCenterCoordinate);
                childCenterCoordinates.add(maxOfDimensionalCenter);
                childrenCenterCoordinatesTemporary.add(childCenterCoordinates);
            }
            // 处理完一个轴，就将临时存储子网格中心点不完整坐标的集合赋值给最终结果
            childrenCenterCoordinatesResult = childrenCenterCoordinatesTemporary;
        }
        return childrenCenterCoordinatesResult;
    }

    /**
     * 计算最优网格（第四步）
     *
     * @param grid 初始网格
     * @param <T>  业务数据类型
     * @return 最优网格
     */
    public static <T> KnnDataGrid<T> optimalGrid(KnnDataGrid<T> grid) {
        if (Objects.isNull(grid)) {
            throw new RuntimeException("param error");
        }
        // 最优网格容器
        AtomicReference<KnnDataGrid<T>> optimalGridAto = new AtomicReference<>();
        // 递归过滤出最优网格
        filtrateOptimalGrid(grid, optimalGridAto);
        return optimalGridAto.get();
    }

    /**
     * 递归过滤出最优网格
     *
     * @param grid           要处理的网格
     * @param optimalGridAto 最优网格包装对象
     * @param <T>            业务数据类型
     */
    private static <T> void filtrateOptimalGrid(KnnDataGrid<T> grid, AtomicReference<KnnDataGrid<T>> optimalGridAto) {
        // 最优网格首先是粒度上符合误差范围的最低级别网格，其次是覆盖率最大的网格
        if (grid.isLastLevel() && (Objects.isNull(optimalGridAto.get()) || grid.getCoverageRatio().compareTo(optimalGridAto.get().getCoverageRatio()) > 0)) {
            // 替换最优网格
            optimalGridAto.set(grid);
        }
        // 子空间列表
        List<KnnDataGrid<T>> children = grid.getChildren();
        if (CollectionUtils.isEmpty(children)) {
            return;
        }
        for (KnnDataGrid<T> child : children) {
            // 计算并填充子网格覆盖率
            calculateThenFillChildCoverageRatio(grid, child);
            // 处理子网格
            filtrateOptimalGrid(child, optimalGridAto);
        }
    }

    /**
     * 计算并填充子网格覆盖率
     *
     * @param <T>
     * @param grid
     * @param child
     */
    private static <T> void calculateThenFillChildCoverageRatio(KnnDataGrid<T> grid, KnnDataGrid<T> child) {
        // 父级网格样本总数
        int gridPointCount = grid.getIncludedPoints().size();
        // 当前子网格落点列表
        List<KnnDataPoint<T>> includedPoints = child.getIncludedPoints();
        // 子网格相对父网格覆盖率
        BigDecimal childRelativeCoverageRatio = new BigDecimal(includedPoints.size())
                .divide(new BigDecimal(gridPointCount), 6, RoundingMode.HALF_UP);
        // 父网格覆盖率
        BigDecimal gridCoverageRatio = Optional.ofNullable(grid.getCoverageRatio()).orElse(BigDecimal.ONE);
        // 子网格相对初始网格覆盖率
        BigDecimal childCoverageRatio = childRelativeCoverageRatio
                .multiply(gridCoverageRatio).setScale(6, RoundingMode.HALF_UP);
        // 设置相对覆盖率
        child.setRelativeCoverageRatio(childRelativeCoverageRatio);
        // 设置总覆盖率
        child.setCoverageRatio(childCoverageRatio);
    }
}
