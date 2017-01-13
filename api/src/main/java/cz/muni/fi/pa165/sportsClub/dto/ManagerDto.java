package cz.muni.fi.pa165.sportsClub.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ManagerDto {

	private Long id;

	@NotNull
	@Size(max = 32, min = 2)
	private String clubName;

	@NotNull
	@Size(max = 32, min = 2)
	private String firstName;

	@NotNull
	@Size(max = 32, min = 2)
	private String lastName;

	@NotNull
	@Pattern(regexp = "[^@]+@[^@]+\\.[^@]+")
	private String email;

	@Pattern(regexp = "(\\+|00)?\\d+")
	private String mobile;

	@NotNull
	private String role;

	private List<TeamDto> teams = new ArrayList<>();

	private List<PlayerDto> players = new ArrayList<>();

	public String getClubName() {
		return clubName;
	}

	public void setClubName(String clubName) {
		this.clubName = clubName;
	}

	public List<TeamDto> getTeams() {
		return teams;
	}

	public void addTeam(TeamDto team) {
		teams.add(team);
	}

	public void removeTeam(TeamDto team) {
		teams.remove(team);
	}

	public void updateTeam(TeamDto team) {
		teams.remove(team);
		teams.add(team);
	}

	public List<PlayerDto> getPlayers() {
		return players;
	}

	public void addPlayer(PlayerDto player) {
		players.add(player);
	}

	public void removePlayer(PlayerDto player) {
		players.remove(player);
	}

	public void updatePlayer(PlayerDto player) {
		players.remove(player);
		players.add(player);
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ManagerDto))
			return false;
		ManagerDto other = (ManagerDto) obj;
		if (getEmail() == null) {
			if (other.getEmail() != null)
				return false;
		} else if (!getEmail().equals(other.getEmail()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Manager [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", mobile=" + mobile + ", clubName=" + clubName + "]";
	}

}
