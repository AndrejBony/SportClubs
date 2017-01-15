package cz.muni.fi.pa165.sportsClub.service;

import java.util.List;

import cz.muni.fi.pa165.sportsClub.enums.Category;
import cz.muni.fi.pa165.sportsClub.pojo.Manager;
import cz.muni.fi.pa165.sportsClub.pojo.Player;
import cz.muni.fi.pa165.sportsClub.pojo.PlayerInfo;
import cz.muni.fi.pa165.sportsClub.pojo.Team;

/**
 * Interface of team service layer
 *
 * @author
 */
public interface TeamService {

    /**
     * Creates new team
     *
     * @param t created team
     */
    void createTeam(Team t);

    /**
     * Updates team
     *
     * @param t updated team
     */
    void updateTeam(Team t);

    /**
     * Deletes team
     *
     * @param id id of deleted team
     */
    void deleteTeam(Team t);

    /**
     * Gets team by id
     *
     * @param teamId team id
     * @return found team
     */
    Team getTeamById(Long teamId);

    /**
     * Gets team of club by specific category
     *
     * @param category age category of team
     * @param c manager of club
     * @return found team
     */
    Team getTeamOfClubByCategory(Category category, Manager c);

    /**
     * Gets all team of club
     *
     * @param m manager of club
     * @return found team
     */
    List<Team> getAllTeamsOfClub(Manager m);

    /**
     * Gets player infos of team
     *
     * @param teamId id of team
     * @return player infos of team
     */
    List<PlayerInfo> getPlayerInfos(Long teamId);

    /**
     * Assigns player to team
     *
     * @param p player
     * @param t team
     * @param jerseyNumber jersey number of player in team
     * @return id of assigned player
     */
    Long assignPlayerToTeam(Player p, Team t, int jerseyNumber);

    /**
     * Changes jersey number of player in team
     *
     * @param p player
     * @param t team
     * @param jerseyNumber jersey number of player in team
     */
    void changeJerseyNumber(Player p, Team t, int jerseyNumber);

    /**
     * Removes player from team
     *
     * @param p player info of player
     */
    void removePlayerFromTeam(PlayerInfo p);

    /**
     * Gets suitable player for team
     *
     * @param team team
     * @return suitable players
     */
    List<Player> findSuitablePlayersForTeam(Team team);

    /**
     * Checks if jersey number in team is unique
     *
     * @param team team
     * @param player player
     * @param jerseyNumber tested jersey number
     * @return true if jersey number is unique, false otherwise
     */
    boolean isJerseyNumberUnique(Player player, Team team, int jerseyNumber);

    /**
     * Checks if age category is unique
     *
     * @param team team
     * @return true if age category is unique, false otherwise
     */
    boolean isCategoryOfTeamUnique(Team team);
}
