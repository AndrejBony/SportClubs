package cz.muni.fi.pa165.sportsClub.dto;

public class ManagerWithTokenDto {

	private ManagerDto manager;
	private String token;

	public ManagerWithTokenDto(ManagerDto manager, String token) {
		this.manager = manager;
		this.token = token;
	}

	public ManagerDto getManager() {
		return manager;
	}

	public void setManager(ManagerDto manager) {
		this.manager = manager;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
