package cz.muni.fi.pa165.sportsClub.facade;

import java.util.List;

import cz.muni.fi.pa165.sportsClub.dto.PlayerBasicInfoDto;
import cz.muni.fi.pa165.sportsClub.dto.PlayerDto;
import cz.muni.fi.pa165.sportsClub.dto.PlayerOfTeamDto;
import cz.muni.fi.pa165.sportsClub.dto.TeamDto;
import cz.muni.fi.pa165.sportsClub.enums.Category;

/**
 * Interface of team facade layer
 *
 * @author
 */
public interface TeamFacade {

    /**
     * Creates new team
     *
     * @param t created team
     */
    Long createTeam(TeamDto t);

    /**
     * Updates team
     *
     * @param t updated team
     */
    void updateTeam(TeamDto t);

    /**
     * Deletes team
     *
     * @param id id of deleted team
     */
    void deleteTeam(Long id);

    /**
     * Gets team by id
     *
     * @param teamId team id
     * @return found team
     */
    TeamDto getTeamById(Long teamId);

    /**
     * Gets team of club by specific category
     *
     * @param category age category of team
     * @param clubId id of club
     * @return found team
     */
    TeamDto getTeamOfClubByCategory(Category category, Long clubId);

    /**
     * Gets all team of club
     *
     * @param clubId id of club
     * @return found team
     */
    List<TeamDto> getAllTeamsOfClub(Long clubId);

    /**
     * Gets all players of team
     *
     * @param teamId id of team
     * @return all players of team
     */
    List<PlayerOfTeamDto> getPlayersOfTeam(Long teamId);

    /**
     * Assigns existing player to team
     *
     * @param playerId id of player
     * @param teamId id of team
     * @param jerseyNumber jersey number of player in team
     * @return player of team
     */
    PlayerOfTeamDto assignExistingPlayerToTeam(Long playerId, Long teamId, int jerseyNumber);

    /**
     * Assigns new player to team
     *
     * @param newPlayer new player
     * @param teamId id of team
     * @param jerseyNumber jersey number of player in team
     * @return player of team
     */
    PlayerOfTeamDto assignNewPlayerToTeam(PlayerDto newPlayer, Long teamId, int jerseyNumber);

    /**
     * Changes jersey number of player in team
     *
     * @param playerId id of player
     * @param teamId id of team
     * @param jerseyNumber jersey number of player in team
     */
    void changeJerseyNumber(Long playerId, Long teamId, int jerseyNumber);

    /**
     * Removes player from team
     *
     * @param playerInfoId id of removed player
     */
    void removePlayerFromTeam(Long playerInfoId);

    /**
     * Gets suitable player for team
     *
     * @param teamId id of team
     * @return basic info of suitable players
     */
    List<PlayerBasicInfoDto> findSuitablePlayersForTeam(Long teamId);

    /**
     * Returns true if jersey number is unique, false otherwise
     *
     * @param teamId id of team
     * @param jerseyNumber tested jersey number
     * @return true if jersey number is unique, false otherwise
     */
    boolean isJerseyNumberUnique(Long teamId, int jerseyNumber);
}
