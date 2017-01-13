package cz.muni.fi.pa165.sportsClub.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import cz.muni.fi.pa165.sportsClub.enums.Category;

public class TeamDto {
	private Long id;

	private List<PlayerOfTeamDto> playerInfos = new ArrayList<>();

	@NotNull
	private Category category;

	private ManagerDto manager;

	public ManagerDto getManager() {
		return manager;
	}

	public void setManager(ManagerDto manager) {
		this.manager = manager;
	}

	/**
	 * Sets list of player infos
	 * 
	 * @param playerInfo
	 *            player infos of team
	 */
	public void setPlayerInfos(List<PlayerOfTeamDto> playerInfo) {
		this.playerInfos = playerInfo;
	}

	/**
	 * Gets list of player infos
	 * 
	 * @return all player infos of team
	 */
	public List<PlayerOfTeamDto> getPlayerInfos() {
		return playerInfos;
	}

	public void addPlayerInfo(PlayerOfTeamDto p) {
		playerInfos.add(p);
	}

	public void removePlayerInfo(PlayerOfTeamDto p) {
		playerInfos.remove(p);
	}

	public void updatePlayerInfo(PlayerOfTeamDto p) {
		playerInfos.remove(p);
		playerInfos.add(p);
	}

	/**
	 * Gets age category of team
	 *
	 * @return age category of team
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * Sets age category of tema
	 *
	 * @param category
	 *            age category of team
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * Gets id of team
	 *
	 * @return id of team
	 *
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Sets id of team
	 *
	 * @param id
	 *            of team
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return getManager().getClubName() + " - " + getCategory().toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof TeamDto)) {
			return false;
		}

		TeamDto team = (TeamDto) o;
		if (!getCategory().equals(team.getCategory())) {
			return false;
		}
		if (getManager() == null || team.getManager() == null) {
			return true;
		}
		return getManager().getEmail().equals(team.getManager().getEmail());

	}

	@Override
	public int hashCode() {
		return 31 * getCategory().hashCode() + ((getManager() == null) ? 0 : getManager().getEmail().hashCode());
	}
}
