package cz.muni.fi.pa165.sportsClub.dto;

import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TeamOfPlayerDto {
	@NotNull
	@Max(99)
	@Min(0)
	private int jerseyNumber;

	private Long playerInfoId;
	
	private boolean playerOlderThanTeamLimit;

	private TeamDto team;

	/**
	 * Gets team
	 * 
	 * @return team
	 */
	public TeamDto getTeam() {
		return team;
	}

	/**
	 * Sets team
	 * 
	 * @param team
	 */
	public void setTeam(TeamDto team) {
		this.team = team;
	}

	/**
	 * Gets jersey number of player
	 *
	 * @return jersey number of player
	 */
	public int getJerseyNumber() {
		return this.jerseyNumber;
	}

	/**
	 * Sets jersey number of player
	 *
	 * @param jerseyNumber
	 *            jersey number of player
	 */
	public void setJerseyNumber(int jerseyNumber) {
		this.jerseyNumber = jerseyNumber;
	}

	public boolean isPlayerOlderThanTeamLimit() {
		return playerOlderThanTeamLimit;
	}

	public void setPlayerOlderThanTeamLimit(boolean value) {
		playerOlderThanTeamLimit = value;
	}

	public Long getPlayerInfoId() {
		return playerInfoId;
	}

	public void setPlayerInfoId(Long playerInfoId) {
		this.playerInfoId = playerInfoId;
	}

	@Override
	public String toString() {
		return "#" + jerseyNumber + ", " + team;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 79 * hash + this.getJerseyNumber();
		hash = 79 * hash + Objects.hashCode(this.getTeam());
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TeamOfPlayerDto other = (TeamOfPlayerDto) obj;
		if (this.getJerseyNumber() != other.getJerseyNumber()) {
			return false;
		}
		if (!Objects.equals(this.getTeam(), other.getTeam())) {
			return false;
		}
		return true;
	}
}
