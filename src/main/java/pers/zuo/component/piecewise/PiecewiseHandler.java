package pers.zuo.component.piecewise;

import pers.zuo.component.piecewise.bean.PiecewiseKey;
import pers.zuo.component.piecewise.bean.PiecewiseResult;
import pers.zuo.component.piecewise.bean.PiecewiseTask;
import pers.zuo.component.piecewise.manager.PiecewiseBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zuojingang
 *
 * @param <V>
 *            the type of part process return
 */
public abstract class PiecewiseHandler<V> {

	public void nThreads(final Map<PiecewiseKey, PiecewiseResult<Map<PiecewiseKey, PiecewiseResult<V>>>> nThreadResult,
			final int totalNum) throws Exception {
		nThreads(nThreadResult, totalNum, D_THREAD_SIZE, D_PART_SIZE);
	}

	/**
	 * @param totalNum
	 * @param threadSize
	 * @return nThreads process result.
	 */
	public void nThreads(final Map<PiecewiseKey, PiecewiseResult<Map<PiecewiseKey, PiecewiseResult<V>>>> nThreadResult,
			final int totalNum, final int threadSize, final int partSize) throws Exception {

		if (null == nThreadResult || 0 >= totalNum || 0 >= threadSize) {
			return;
		}

		ExecutorService fixThreadPool = Executors.newFixedThreadPool(D_N_THREAD);
		List<PiecewiseTask> fTaskList = new ArrayList<>();

		int fromIndex = 0;
		try {
			while (totalNum > fromIndex) {

				final int thisFromIndex = fromIndex;
				final int threadProcessNum = Math.min(totalNum - fromIndex, threadSize);
				final int thisToIndex = thisFromIndex + threadProcessNum;

				if (0 < threadProcessNum) {
					PiecewiseTask futureTask = PiecewiseBuilder.buildTask(new Callable<Boolean>() {

						@Override
						public Boolean call() throws Exception {
							final Map<PiecewiseKey, PiecewiseResult<V>> threadResult = PiecewiseBuilder
									.initializeThreadResult();
							nThreadResult.put(PiecewiseBuilder.buildKey(thisFromIndex, thisToIndex),
									PiecewiseBuilder.buildResult(threadResult));
							singleThread(threadResult, thisFromIndex, threadProcessNum, partSize);
							return true;
						}
					}, PiecewiseBuilder.buildKey(thisFromIndex, thisToIndex));

					fixThreadPool.submit(futureTask);
					fTaskList.add(futureTask);
				}
				fromIndex += threadProcessNum;
			}

			boolean finished = true;
			for (PiecewiseTask futureTask : fTaskList) {
				try {
					finished = finished && futureTask.get();
				} catch (InterruptedException | ExecutionException e) {
					nThreadResult.get(futureTask.getTaskKey()).setException(e);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// the threadPool must manual-lock after use
			fixThreadPool.shutdown();
		}
	}

	public void singleThread(final Map<PiecewiseKey, PiecewiseResult<V>> threadResult, final int totalNum) {
		singleThread(threadResult, 0, totalNum);
	}

	public void singleThread(final Map<PiecewiseKey, PiecewiseResult<V>> threadResult, final int offset,
			final int totalNum) {
		singleThread(threadResult, offset, totalNum, D_PART_SIZE);
	}

	/**
	 * @param offset
	 * @param toIndex
	 * @param partSize
	 * @return process subList values and include first index(offset) and exclude
	 *         latest index(offset + totalNum)
	 */
	public void singleThread(final Map<PiecewiseKey, PiecewiseResult<V>> threadResult, final int offset,
			final int totalNum, final int partSize) {
		if (0 >= totalNum || 0 >= partSize) {
			return;
		}
		final int toIndex = offset + totalNum;

		int fromIndex = offset;
		while (toIndex > fromIndex) {

			int thisToIndex = Math.min(fromIndex + partSize, toIndex);

			V partResult = null;
			Exception pe = null;
			try {
				partResult = partProcess(fromIndex, thisToIndex);
			} catch (Exception e) {
				pe = e;
			}
			threadResult.put(PiecewiseBuilder.buildKey(fromIndex, thisToIndex),
					PiecewiseBuilder.buildResult(partResult, pe));

			fromIndex = thisToIndex;
		}
	}

	/**
	 * @param offset
	 * @param partSize
	 * @return part process result
	 */
	protected abstract V partProcess(final int fromIndex, final int toIndex) throws Exception;

	public static final int D_N_THREAD = 10;
	public static final int D_THREAD_SIZE = 10000;
	public static final int D_PART_SIZE = 1000;
}
