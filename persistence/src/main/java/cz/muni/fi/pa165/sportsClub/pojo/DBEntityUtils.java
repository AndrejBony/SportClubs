package cz.muni.fi.pa165.sportsClub.pojo;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Database entity utils
 */
public class DBEntityUtils {
	public static String quote(String input) {
		return "'" + input + "'";
	}

	public static String dateToVarBinary(LocalDate date) {
		return "(X'"
				+ String.format("%020x", new BigInteger(1, date.format(DateTimeFormatter.ISO_LOCAL_DATE).getBytes()))
				+ "')";
	}
}
