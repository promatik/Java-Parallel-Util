package pt.promatik.utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class Utils {
	public final static int LOG_NONE = 0;
	public final static int LOG_DEFAULT = 1;
	public final static int LOG_ERRORS = 2;
	public final static int LOG_FULL = 3;
	public final static int LOG_VERBOSE = 4;

	private static long nanoTime = 0;

	public static int log_level = LOG_DEFAULT;
	public static Random random = new Random(System.nanoTime());
	public final static SimpleDateFormat LOG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	// --------------------
	// Record nano time

	public static void nanoTime() {
		nanoTime("");
	}

	public static String nanoTime(String suffix) {
		String result = "";
		result = ((System.nanoTime() - nanoTime) / 1000000f) + " ms " + suffix;
		nanoTime = System.nanoTime();
		return result;
	}

	// --------------------
	// Random

	public static int random(int min, int max) {
		return min + (int) (Math.random() * (max - min + 1));
	}

	public static <T extends Enum<T>> T random(Class<T> enumerator) {
		return random(Arrays.asList(enumerator.getEnumConstants()));
	}

	public static <T> T random(Collection<T> coll) {
		if (coll.size() == 0)
			return null;
		int num = (int) (Math.random() * coll.size());
		for (T t : coll)
			if (--num < 0)
				return t;
		throw new AssertionError();
	}

	// --------------------
	// Log

	public static void log(String message) {
		log(message, "");
	}

	public static void log(String message, String ref) {
		log(message, ref, false);
	}

	public static void log(String message, boolean forceLog) {
		log(message, "", forceLog);
	}

	public static void log(String message, Exception e) {
		log(message, "", e);
	}

	public static void log(String message, String ref, Exception e) {
		error(message, ref);
		log(e);
	}

	public static void log(Exception e) {
		if (log_level >= LOG_ERRORS) {
			e.printStackTrace();
		}
	}

	public static void log(String message, String ref, boolean forceLog) {
		if (log_level >= LOG_DEFAULT || forceLog) {
			System.out.println(ref + LOG_DATE_FORMAT.format(System.currentTimeMillis()) + "> " + message);
		}
	}

	public static void error(String message) {
		error(message, "");
	}

	public static void error(String message, String ref) {
		System.err.println(ref + LOG_DATE_FORMAT.format(System.currentTimeMillis()) + "> " + message);
	}
}
