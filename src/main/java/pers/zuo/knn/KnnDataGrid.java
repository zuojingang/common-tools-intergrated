package pers.zuo.knn;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @author : zuojingang
 * @description : 临近算法数据网格类
 * @since : 2022/7/27 17:01, Wednesday
 **/
@Getter
@Setter
public class KnnDataGrid<T> implements Serializable {
    /**
     * 可容忍的误差（点距离）
     */
    private BigDecimal tolerance;
    /**
     * 网格的最小有效样本数
     */
    private Integer minValidSampleCount;
    /**
     * 网格级别，默认从0开始，0代表初始网格；每次切割，网格级别+1
     */
    private Integer level;
    /**
     * 是否是最后一级网格
     */
    private boolean lastLevel;
    /**
     * 网格中心点，在做归纳时，用中心点 & 半径 判断一个数据点是否可以落在当前网格中
     */
    private KnnDataPoint<T> centerPoint;
    /**
     * 网格维度半径（多维立体的在单个维度的半径），理论上网格是一个多维立体空间，但是判断数据是否可落在当前网格时，实际是以中心点 & 半径 划定一个球体空间
     */
    private BigDecimal dimensionalRadius;
    /**
     * 当前网格中所包含的数据点
     */
    private List<KnnDataPoint<T>> includedPoints;
    /**
     * 子网格列表
     */
    private List<KnnDataGrid<T>> children;
    /**
     * 父网格
     */
    private KnnDataGrid<T> parent;
    /**
     * 相对父级覆盖率
     */
    private BigDecimal relativeCoverageRatio;
    /**
     * 总覆盖率，相对初始网格（总样本）
     */
    private BigDecimal coverageRatio;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KnnDataGrid<?> that = (KnnDataGrid<?>) o;
        return Objects.equals(level, that.level) && Objects.equals(centerPoint, that.centerPoint) && Objects.equals(dimensionalRadius, that.dimensionalRadius);
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, centerPoint, dimensionalRadius);
    }
}
