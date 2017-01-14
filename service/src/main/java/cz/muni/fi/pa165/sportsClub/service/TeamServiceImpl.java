package cz.muni.fi.pa165.sportsClub.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import javax.inject.Inject;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import cz.muni.fi.pa165.sportsClub.dao.ManagerDao;
import cz.muni.fi.pa165.sportsClub.dao.PlayerInfoDao;
import cz.muni.fi.pa165.sportsClub.dao.TeamDao;
import cz.muni.fi.pa165.sportsClub.enums.Category;
import cz.muni.fi.pa165.sportsClub.pojo.Manager;
import cz.muni.fi.pa165.sportsClub.pojo.Player;
import cz.muni.fi.pa165.sportsClub.pojo.PlayerInfo;
import cz.muni.fi.pa165.sportsClub.pojo.Team;

@Service
public class TeamServiceImpl implements TeamService {
    @Inject
    private TeamDao teamDao;

    @Inject
	private ManagerDao managerDao;

    @Inject
    private PlayerInfoDao playerInfoDao;

	@Override
	public void createTeam(Team t) {
        teamDao.createTeam(t);
    }

    @Override
    public boolean isCategoryOfTeamUnique(Team team) {
        List<Category> categories = managerDao.getCategoriesOfTeams(team.getManager());
        return !categories.contains(team.getCategory());
    }

	@Override
	public void updateTeam(Team t) {
        teamDao.updateTeam(t);
    }

	@Override
	public void deleteTeam(Team t) {
        teamDao.deleteTeam(t);
    }

	@Override
	public Team getTeamById(Long teamId) {
        return teamDao.getTeamById(teamId);
    }

	@Override
	public Team getTeamOfClubByCategory(Category category, Manager m) {
		List<Team> teams = m.getTeams();
        for (Team team : teams) {
            if (team.getCategory().equals(category)) {
                return team;
            }
        }
        return null;
    }

	@Override
	public List<Team> getAllTeamsOfClub(Manager m) {
		return m.getTeams();
    }

	@Override
	public List<PlayerInfo> getPlayerInfos(Long teamId) {
        Team team = teamDao.getTeamById(teamId);
        team.getPlayerInfos().size();
        return team.getPlayerInfos();
    }

    private boolean playerMeetsAgeLimit(Player player, Team team){
        LocalDate today = LocalDate.now();
        LocalDate birthday = player.getDateOfBirth();

        int age = Period.between(birthday, today).getYears();
        return age <= team.getCategory().getUpperAgeLimit() && age >= team.getCategory().getBottomAgeLimit();
    }

	@Override
	public Long assignPlayerToTeam(Player p, Team t, int jerseyNumber) {
        if(!playerMeetsAgeLimit(p,t)){
            throw new DataIntegrityViolationException("Player does not match age criteria of team");
        }
        if(!teamDao.isJerseyNumberUnique(t,jerseyNumber)){
            throw new DataIntegrityViolationException("Jersey number is not unique in the team");
        }
        if(p.getManager().getId() != t.getManager().getId()){
            throw new DataIntegrityViolationException("You can't assign player to the team of different club");
        }
        PlayerInfo playerInfo = new PlayerInfo();
        playerInfo.setPlayer(p);
        playerInfo.setTeam(t);
        playerInfo.setJerseyNumber(jerseyNumber);
        playerInfoDao.createPlayerInfo(playerInfo);
        return playerInfo.getId();
    }

	@Override
	public void changeJerseyNumber(Player p, Team t, int newjerseyNumber) {
        if(!teamDao.isJerseyNumberUnique(t,newjerseyNumber)){
        	throw new DataIntegrityViolationException("Jersey number is not unique in the team");
        }
        playerInfoDao.changeJerseyNumber(t, p, newjerseyNumber);
    }

	@Override
	public void removePlayerFromTeam(PlayerInfo pi) {
		playerInfoDao.deletePlayerInfo(pi);
    }

    @Override
	public List<Player> findSuitablePlayersForTeam(Team team) {
        LocalDate today = LocalDate.now();
        LocalDate bottomDobLimit = today.minusYears(team.getCategory().getUpperAgeLimit());
        LocalDate upperDobLimit = today.minusYears(team.getCategory().getBottomAgeLimit());
        List<Player> suitablePlayers = managerDao.getPlayersWithDobBetween(team, bottomDobLimit, upperDobLimit);
        suitablePlayers.addAll(managerDao.getFreePlayersWithDobBetween(team.getManager(), bottomDobLimit, upperDobLimit));
        return suitablePlayers;
    }

    @Override
    public boolean isJerseyNumberUnique(Team team, int jerseyNumber) {
        return teamDao.isJerseyNumberUnique(team, jerseyNumber);
    }
}
