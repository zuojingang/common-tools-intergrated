package pers.zuo.component.piecewise.bean;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author zuojingang
 *
 * @param <T>
 *            the type of part process return
 */
public class PiecewiseTask<T> extends FutureTask<Boolean> {

	private final PiecewiseKey<Integer> taskKey;

	public PiecewiseTask(Callable<Boolean> callable, PiecewiseKey<Integer> taskKey) {
		super(callable);
		this.taskKey = taskKey;
	}

	public PiecewiseKey<Integer> getTaskKey() {
		return taskKey;
	}

}