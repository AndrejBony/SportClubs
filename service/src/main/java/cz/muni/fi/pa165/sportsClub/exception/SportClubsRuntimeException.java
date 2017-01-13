package cz.muni.fi.pa165.sportsClub.exception;

public class SportClubsRuntimeException extends RuntimeException {

	public SportClubsRuntimeException(String msg) {
		super(msg);
	}

	public SportClubsRuntimeException(Throwable cause) {
		super(cause);
	}

	public SportClubsRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
