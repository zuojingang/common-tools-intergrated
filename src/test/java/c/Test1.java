package c;

public class Test1 {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("main method start!");
		//
		// Integer a = 1;
		// Integer b = 2;
		// Integer c = 3;
		// Integer d = 3;
		// Integer e = 321;
		// Integer f = 321;
		// Long g = 3L;
		// int s = 3;
		// Integer y = 300;
		// Integer k = 21;
		// Long t = 321L;
		// long h = 321L;
		//
		// System.out.println(c == d);
		// System.out.println(e == f);
		// System.out.println(c == (a + b));
		// System.out.println(c.equals((a + b)));
		// System.out.println(g == (a + b));
		// System.out.println(g.equals(a + b));
		// System.out.println("^^^^^^^^^^^");
		// System.out.println(s == c);
		// System.out.println(s == (a + b));
		// System.out.println(h == e);
		// System.out.println(t == (y + k));
		// System.out.println(h == (y + k));

		Thread[] threads = new Thread[10];
		for (int i = 0; i < 10; i++) {
			final int i1 = i;
			Thread thread = new Thread(() -> {

				for (int j = 0; j < 10000; j++) {

					testM(i1, (i1 + 5) % 4);
				}

				// while (true) {
				// testM(i1, (i1 + 5) % 4);
				// }
			}, new StringBuilder("T->").append(i).append(i).append(i).toString());

			thread.start();
			threads[i] = thread;
		}
		// for (int i = 0; i < threads.length; i++) {
		//
		// Thread thread = threads[i];
		// thread.join();
		// }
		StringBuilder sBuilder = new StringBuilder();
		while (1 < Thread.activeCount()) {

			sBuilder.append(Thread.activeCount());
			if (Thread.activeCount() - 1 == threads.length) {
				Thread.sleep(650);
			} else {
				Thread.sleep(5);
			}
		}
		System.out.println(sBuilder.append("main method is ended!!!"));
	}

	private static boolean testM(int a, int b) {
		// System.out.println("enter the method testM!");
		int ret = a + b;
		// System.out.println("end the method testM");
		System.out.println(new StringBuilder(Thread.currentThread().getName()).append("execute:"));
		return ret % 3 == 0;
	}

}
