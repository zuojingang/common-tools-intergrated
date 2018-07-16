package pers.zuo.util;

import java.util.concurrent.atomic.AtomicLong;

public class WeightUtil {

	private static AtomicLong mutexCurrent = new AtomicLong(System.currentTimeMillis());

	public static final long HIGHER_NUMBER = 0xFFFL << 42;

	public static final long HIGHER_MASK = 0xFFFL;

	public static final long POINT = 1L << 42;

	public static long increaseWeight(long weight) {
		return weight + POINT;
	}

	public static long reduceWeight(long weight) {
		return weight - POINT;
	}

	public static long initWeight() {
		return HIGHER_NUMBER | mutexCurrent.incrementAndGet();
	}

	public static long initWeight(long weight) {
		return HIGHER_NUMBER | weight;
	}
}
