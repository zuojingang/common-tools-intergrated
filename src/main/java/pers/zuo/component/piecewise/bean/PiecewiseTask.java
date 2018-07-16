package pers.zuo.component.piecewise.bean;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author zuojingang
 *
 * @param <K
 *            extends Number> the type of part process return
 */
public class PiecewiseTask extends FutureTask<Boolean> {

	private final PiecewiseKey taskKey;

	public PiecewiseTask(Callable<Boolean> callable, PiecewiseKey taskKey) {
		super(callable);
		this.taskKey = taskKey;
	}

	public PiecewiseKey getTaskKey() {
		return taskKey;
	}

}