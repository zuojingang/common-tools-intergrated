package pers.zuo.component.detector.snapshot;

import pers.zuo.component.detector.probe.IProbe;

import java.util.Set;

/**
 * @author zuojingang
 * @Title: DetectorSnapshot
 * @Description: 探测器快照，包含每次使用探测器的状态
 * @date 2019-08-09 09:48
 */
public class DetectorSnapshot {

    private Set<IProbe> installedProbes;
    private Set<IProbe> signalProbes;

    public Set<IProbe> getInstalledProbes() {
        return installedProbes;
    }

    public void setInstalledProbes(Set<IProbe> installedProbes) {
        this.installedProbes = installedProbes;
    }

    public Set<IProbe> getSignalProbes() {
        return signalProbes;
    }

    public void setSignalProbes(Set<IProbe> signalProbes) {
        this.signalProbes = signalProbes;
    }
}
