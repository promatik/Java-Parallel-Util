package pt.promatik.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Parallel {
	private long startTime, endTime = 0;
	private ExecutorService executor = null;
	private boolean terminated = false;
	private boolean cancelled = false;

	@FunctionalInterface
	public interface Callback<Result> {
		public void apply(Result result);
	}

	public <T> Parallel(Callable<T> runnable, Callback<T> callback) {
		startTime = System.nanoTime();
		executor = Executors.newSingleThreadExecutor();

		executor.execute(() -> {
			try {
				T result = runnable.call();
				if (!cancelled)
					callback.apply(result);
				endTime = System.nanoTime();
				terminated = true;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public void cancel() {
		cancelled = true;
		executor.shutdown();
		endTime = System.nanoTime();
	}

	public int getElapsedTime() {
		return Math.round(((endTime == 0 ? System.nanoTime() : endTime) - startTime) / 1000000f);
	}

	public boolean isTerminated() {
		return terminated;
	}

	public static <T> Parallel run(Callable<T> runnable, Callback<T> callback) {
		return new Parallel(runnable, callback);
	}
}
