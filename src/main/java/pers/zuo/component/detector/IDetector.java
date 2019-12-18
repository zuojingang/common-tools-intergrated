package pers.zuo.component.detector;

import pers.zuo.component.detector.probe.IProbe;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author zuojingang
 * @Title: IDetector
 * @Description: 探测器统一接口
 * @date 2019-08-08 15:20
 */
public interface IDetector {

    /**
     * 安装探头
     *
     * @param probes
     */
    void install(@NotNull IProbe... probes);

    /**
     * 卸载探头
     *
     * @param probe
     */
    void uninstall(@NotNull IProbe probe);

    /**
     * 执行检测
     *
     * @return
     */
    Boolean detect();

    /**
     * 亮灯，将检测成功的探头放入命中列表
     *
     * @param probe
     */
    void lightUp(@NotNull IProbe probe);

    /**
     * 命中的探头列表
     *
     * @return
     */
    Set<IProbe> signalProbes();
}
