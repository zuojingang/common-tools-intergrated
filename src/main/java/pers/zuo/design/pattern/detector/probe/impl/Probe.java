package pers.zuo.design.pattern.detector.probe.impl;

import pers.zuo.design.pattern.detector.IDetector;
import pers.zuo.design.pattern.detector.probe.IProbe;
import pers.zuo.design.pattern.detector.probe.judge.IJudge;
import pers.zuo.design.pattern.detector.probe.snapshot.ProbeSnapshot;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author zuojingang
 * @Title: Probe
 * @Description: 探头实现
 * @date 2019-08-08 15:53
 */
public class Probe implements IProbe {

    private ThreadLocal<ProbeSnapshot> snapshot = ThreadLocal.withInitial(ProbeSnapshot::new);
    private IJudge judge;

    /**
     * 一个探头有且仅有一个判定逻辑
     *
     * @param judge
     */
    public Probe(@NotNull IJudge judge) {
        this.judge = judge;
    }

    @Override
    public void setDetector(IDetector detector) {
        snapshot.get().setDetector(detector);
    }

    @Override
    public Boolean detect() {
        return judge.doJudgment();
    }

    @Override
    public void detectWithLight() {
        IDetector detector = snapshot.get().getDetector();
        if (Objects.isNull(detector)) {
            return;
        }
        Boolean detect = this.detect();
        if (Objects.isNull(detect) || !detect) {
            return;
        }
        detector.lightUp(this);
    }
}
