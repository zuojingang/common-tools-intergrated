package pers.zuo.util;

import java.util.Random;

/**
 * 
 * @author zuojingang
 *
 */
public class RandomUtil {
	
	public static int randomInt(int maxmum) {
		Random random = new Random();
		return random.nextInt(maxmum);
	}

	/**
	 * @param base
	 * @param displacement
	 * @return randomValue
	 */
	private static int _randomInt(int base, int displacement) {
		if(displacement <=0) {
			return base;
		}
		int randomInt = randomInt(displacement);
		return base + randomInt;
	}
	
	/**
	 * @param from
	 * @param to
	 * @return
	 */
	public static int randomInt(int from, int to) {
		if(to <from) {
			return 0;
		}
		if(from == to) {
			return from;
		}
		return _randomInt(from, to - from);
	}
}
