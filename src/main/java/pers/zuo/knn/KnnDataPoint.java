package pers.zuo.knn;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author : zuojingang
 * @description : 临近算法数据落点类
 * @since : 2022/7/27 17:01, Wednesday
 **/
@Getter
@Setter
@EqualsAndHashCode
public class KnnDataPoint<T> implements Serializable {
    /**
     * 多维数据的坐标信息
     */
    private List<BigDecimal> coordinates;
    /**
     * 落在该位置的完整数据
     */
    private T data;
    /**
     * 当前点归属的网格信息列表
     */
    private List<KnnDataGrid<T>> grids;

}
