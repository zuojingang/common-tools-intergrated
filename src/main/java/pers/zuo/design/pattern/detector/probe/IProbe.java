package pers.zuo.design.pattern.detector.probe;

import pers.zuo.design.pattern.detector.IDetector;

/**
 * @author zuojingang
 * @Title: Probe
 * @Description: 探头统一接口
 * @date 2019-08-08 15:31
 */
public interface IProbe {

    /**
     * 安装探测器
     *
     * @param detector
     */
    void setDetector(IDetector detector);

    /**
     * 执行探测
     *
     * @return
     */
    Boolean detect();

    /**
     * 执行亮灯探测
     */
    void detectWithLight();
}
