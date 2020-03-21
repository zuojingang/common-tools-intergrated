package pers.zuo.design.pattern.detector.probe.snapshot;

import pers.zuo.design.pattern.detector.IDetector;

/**
 * @author zuojingang
 * @Title: ProbeSnapshot
 * @Description: 探头快照，包含每次使用探头的状态
 * @date 2019-08-09 10:20
 */
public class ProbeSnapshot {

    private IDetector detector;

    public IDetector getDetector() {
        return detector;
    }

    public void setDetector(IDetector detector) {
        this.detector = detector;
    }
}
