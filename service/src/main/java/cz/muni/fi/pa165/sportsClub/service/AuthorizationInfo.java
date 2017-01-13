package cz.muni.fi.pa165.sportsClub.service;

/**
 * Authorization info 
 */
public class AuthorizationInfo {
	private Long userId;
	private String userRole;

	public AuthorizationInfo(Long userId, String userRole) {
		this.userId = userId;
		this.userRole = userRole;
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

}
