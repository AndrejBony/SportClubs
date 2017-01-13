package cz.muni.fi.pa165.sportsClub.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import cz.muni.fi.pa165.sportsClub.exception.SportClubsRuntimeException;
import cz.muni.fi.pa165.sportsClub.exception.TokenValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Authorization utils
 */
public class AuthUtils {

	private static byte[] hashKey = new byte[64];

	public static String computeHash(String input) throws SportClubsRuntimeException {
		char[] inputAsCharArray = input.toCharArray();
		PBEKeySpec spec = new PBEKeySpec(inputAsCharArray, "constant".getBytes(), 1000, 512);
		SecretKeyFactory skf;
		try {
			skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		} catch (NoSuchAlgorithmException e) {
			throw new SportClubsRuntimeException(e);
		}
		byte[] hash;
		try {
			hash = skf.generateSecret(spec).getEncoded();
		} catch (InvalidKeySpecException e) {
			throw new SportClubsRuntimeException(e);
		}
		return Base64.getEncoder().encodeToString(hash);
	}

	public static byte[] getHashKey() {
		return hashKey;
	}

	public static void setHashKey() {
		try (BufferedInputStream bis = new BufferedInputStream(
				ManagerServiceImpl.class.getClassLoader().getResourceAsStream("hashkey"))) {
			bis.read(hashKey);
		} catch (IOException e) {
			throw new SportClubsRuntimeException("unable to create hashkey", e);
		}
	}

	public static String issueNewToken(Long userId, String userRole) throws SportClubsRuntimeException {
		Date exp = new Date(new Date().getTime() + 1000 * 60 * 60);
		return Jwts.builder().claim(userRole, true).setSubject(userId.toString()).setExpiration(exp)
				.signWith(SignatureAlgorithm.HS256, AuthUtils.getHashKey()).compact();
	}

	/**
	 * Assert that the token is valid and that the user has a role which is
	 * needed for particular REST call.
	 * 
	 * @param token
	 *            authorization token sent by user
	 * @param roles
	 *            array of roles which can call the particular REST call
	 * 
	 *            eg. When the REST call can be called only by admin, roles =
	 *            ['admin'], if it can be called also by manager then roles =
	 *            ['admin','manager']
	 * @return id of the user calling the REST (parsed from token)
	 * @throws TokenValidationException
	 */
	public static Long authorizeRestCall(String token, String... roles) throws TokenValidationException {
		try {
			Claims body = Jwts.parser().setSigningKey(AuthUtils.getHashKey()).parseClaimsJws(token).getBody();
			boolean hasPermission = false;
			for (String role : roles) {
				if (!body.containsKey(role))
					continue;
				if((boolean)body.get(role) == true){
					hasPermission=true;
					break;		
				}
			}
			if (!hasPermission)
				throw new TokenValidationException("user does not have required role");
			return Long.parseLong(body.getSubject());
		} catch (Exception e) {
			throw new TokenValidationException("token validation failed", e);
		}
	}
}