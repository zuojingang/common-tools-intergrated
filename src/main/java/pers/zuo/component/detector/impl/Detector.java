package pers.zuo.component.detector.impl;

import com.sun.istack.internal.NotNull;
import pers.zuo.component.detector.IDetector;
import pers.zuo.component.detector.probe.IProbe;
import pers.zuo.component.detector.snapshot.DetectorSnapshot;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zuojingang
 * @Title: Detector
 * @Description: 探测器的实现类
 * @date 2019-08-08 15:23
 */
public class Detector implements IDetector {

    private ThreadLocal<DetectorSnapshot> snapshot = ThreadLocal.withInitial(() -> {
        DetectorSnapshot snapshot = new DetectorSnapshot();
        snapshot.setInstalledProbes(new HashSet<>());
        snapshot.setSignalProbes(new HashSet<>());
        return snapshot;
    });

    @Override
    public void install(@NotNull IProbe... probes) {
        if (0 == probes.length) {
            return;
        }
        for (IProbe probe : probes) {
            snapshot.get().getInstalledProbes().add(probe);
            probe.setDetector(this);
        }
    }

    @Override
    public void uninstall(@NotNull IProbe probe) {
        snapshot.get().getInstalledProbes().remove(probe);
        probe.setDetector(null);
    }

    @Override
    public Boolean detect() {
        if (snapshot.get().getInstalledProbes().isEmpty()) {
            return false;
        }
        snapshot.get().getInstalledProbes().forEach(IProbe::detectWithLight);
        return !snapshot.get().getSignalProbes().isEmpty();
    }

    @Override
    public void lightUp(@NotNull IProbe probe) {
        if (!snapshot.get().getInstalledProbes().contains(probe)) {
            return;
        }
        snapshot.get().getSignalProbes().add(probe);
    }

    @Override
    public Set<IProbe> signalProbes() {
        return snapshot.get().getSignalProbes();
    }
}
