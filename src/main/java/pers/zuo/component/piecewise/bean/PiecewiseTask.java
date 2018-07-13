package pers.zuo.component.piecewise.bean;

import java.util.ArrayList;
import java.util.List;
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

	private final List<PiecewiseResult<T>> taskResult;

	public PiecewiseTask(Callable<Boolean> callable, PiecewiseKey<Integer> taskKey) {
		super(callable);
		this.taskKey = taskKey;
		this.taskResult = new ArrayList<>();
	}

	public PiecewiseKey<Integer> getTaskKey() {
		return taskKey;
	}

	public List<PiecewiseResult<T>> getTaskResult() {
		return taskResult;
	}
}