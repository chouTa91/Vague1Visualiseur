package fr.polemploi.suivi.migration.api.utils;

/**
 * Class utilitaire concernant les temps.
 *
 * @author jbourrea
 *
 */
public class TimeUtils {

	public TimeUtils() {
		throw new ExceptionInInitializerError("Static utils cannot be instantiate.");
	}

	public static String formatFromStringSeconds(String secondsStr) {
		long seconds = Long.parseLong(secondsStr);
		return String.format("%02d:%02d:%02d", seconds / 3600, seconds % 3600 / 60, seconds % 60);
	}

}
