package pers.zuo.design.pattern.piecewise;

import pers.zuo.design.pattern.piecewise.bean.PiecewiseKey;
import pers.zuo.design.pattern.piecewise.bean.PiecewiseResult;
import pers.zuo.design.pattern.piecewise.bean.PiecewiseTask;
import pers.zuo.design.pattern.piecewise.manager.PiecewiseBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @param <V> the type of part process return
 * @author zuojingang
 */
public abstract class PiecewiseHandler<V> {

	public void nThreads(final Map<PiecewiseKey, PiecewiseResult<Map<PiecewiseKey, PiecewiseResult<V>>>> nThreadResult,
						 final long totalNum) {
		nThreads(nThreadResult, totalNum, D_THREAD_SIZE, D_PART_SIZE);
	}

	public void nThreads(final Map<PiecewiseKey, PiecewiseResult<Map<PiecewiseKey, PiecewiseResult<V>>>> nThreadResult,
						 final long totalNum, final long threadSize, final long partSize) {
		nThreads(nThreadResult, totalNum, threadSize, partSize, D_N_THREAD, D_N_THREAD);
	}

	/**
	 * @param totalNum
	 * @param threadSize
	 * @param partSize
	 * @return nThreads process result.
	 */
	public void nThreads(final Map<PiecewiseKey, PiecewiseResult<Map<PiecewiseKey, PiecewiseResult<V>>>> nThreadResult,
						 final long totalNum, final long threadSize, final long partSize, int corePoolSize, int maximumPoolSize) {

		if (null == nThreadResult || 0 >= totalNum || 0 >= threadSize) {
			return;
		}

		ExecutorService fixThreadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
		List<PiecewiseTask> fTaskList = new ArrayList<>();

		long fromIndex = 0;
		try {
			while (totalNum > fromIndex) {

				final long thisFromIndex = fromIndex;
				final long threadProcessNum = Math.min(totalNum - fromIndex, threadSize);
				final long thisToIndex = thisFromIndex + threadProcessNum;

				if (0 < threadProcessNum) {
					PiecewiseTask futureTask = PiecewiseBuilder.buildTask(() -> {
						final Map<PiecewiseKey, PiecewiseResult<V>> threadResult = PiecewiseBuilder
								.initializeThreadResult();
						nThreadResult.put(PiecewiseBuilder.buildKey(thisFromIndex, thisToIndex),
								PiecewiseBuilder.buildResult(threadResult));
						singleThread(threadResult, thisFromIndex, threadProcessNum, partSize);
						return true;
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
			throw new RuntimeException(e);
		} finally {
			// the threadPool must manual-lock after use
			fixThreadPool.shutdown();
		}
	}

	public void singleThread(final Map<PiecewiseKey, PiecewiseResult<V>> threadResult, final long totalNum) {
		singleThread(threadResult, 0, totalNum);
	}

	public void singleThread(final Map<PiecewiseKey, PiecewiseResult<V>> threadResult, final long offset,
							 final long totalNum) {
		singleThread(threadResult, offset, totalNum, D_PART_SIZE);
	}

	/**
	 * @param offset
	 * @param partSize
	 * @return process subList values and include first index(offset) and exclude
	 * latest index(offset + totalNum)
	 */
	public void singleThread(final Map<PiecewiseKey, PiecewiseResult<V>> threadResult, final long offset,
							 final long totalNum, final long partSize) {
		if (0 >= totalNum || 0 >= partSize) {
			return;
		}
		final long toIndex = offset + totalNum;

		long fromIndex = offset;
		while (toIndex > fromIndex) {

			long thisToIndex = Math.min(fromIndex + partSize, toIndex);

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
	 * @return part process result
	 */
	protected abstract V partProcess(final long fromIndex, final long toIndex) throws Exception;

	public static final int D_N_THREAD = 10;
	public static final long D_THREAD_SIZE = 10000;
	public static final long D_PART_SIZE = 1000;

}
