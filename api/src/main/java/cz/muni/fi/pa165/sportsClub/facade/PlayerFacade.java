package cz.muni.fi.pa165.sportsClub.facade;

import java.util.List;

import cz.muni.fi.pa165.sportsClub.dto.PlayerDto;
import cz.muni.fi.pa165.sportsClub.dto.TeamOfPlayerDto;

/**
 * Interface of player facade layer
 *
 * @author
 */
public interface PlayerFacade {

    /**
     * Creates new player
     *
     * @param p created player
     */
    PlayerDto createPlayer(PlayerDto p, Long managerId);

    /**
     * Updates player
     *
     * @param p updated player
     */
    void updatePlayer(PlayerDto p);

    /**
     * Deletes player
     *
     * @param id id of deleted player
     */
    void deletePlayer(Long id);

    /**
     * Retrieves player by id
     *
     * @param playerId of player
     * @return found player
     */
    PlayerDto getPlayerById(Long playerId);

    /**
     * Retrieves player by email
     *
     * @param email of player
     * @return found player
     */
    PlayerDto getPlayerByEmail(String email);

    /**
     * Retrieves all players of club
     *
     * @param clubId id of club
     * @return all players of club
     */
    List<PlayerDto> getAllPlayersOfClub(Long clubId);

    /**
     * Retrieves all teams of player
     *
     * @param playerId player id
     * @return all teams of player
     */
    List<TeamOfPlayerDto> getTeamsOfPlayer(Long playerId);
}
