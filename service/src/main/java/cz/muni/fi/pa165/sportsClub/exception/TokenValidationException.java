package cz.muni.fi.pa165.sportsClub.exception;

/**
 * thrown when user tries to access REST resource and: 1) authorization token is
 * invalid 2) user doesn't have permission to access particular rest 3) token is
 * expired (60min)
 * 
 * should be mapped to 409 response code
 * 
 * @author Jan Tomasek
 *
 */
public class TokenValidationException extends Exception {

	public TokenValidationException(String msg) {
		super(msg);
	}

	public TokenValidationException(Throwable cause) {
		super(cause);
	}

	public TokenValidationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
