package cz.muni.fi.pa165.sportsClub.service;

import java.util.List;

import cz.muni.fi.pa165.sportsClub.pojo.Manager;
import cz.muni.fi.pa165.sportsClub.pojo.Player;
import cz.muni.fi.pa165.sportsClub.pojo.PlayerInfo;

/**
 * Interface of player service layer
 *
 * @author Andrej (410433)
 */
public interface PlayerService {

    /**
     * Creates new player
     *
     * @param p created player
     */
    void createPlayer(Player p);

    /**
     * Updates player
     *
     * @param p updated player
     */
    void updatePlayer(Player p);

    /**
     * Deletes player
     *
     * @param id id of deleted player
     */
    void deletePlayer(Player p);

    /**
     * Gets player by id
     *
     * @param playerId of player
     * @return found player
     */
    Player getPlayerById(Long playerId);

    /**
     * Gets player by email
     *
     * @param email of player
     * @return found player
     */
    Player getPlayerByEmail(String email);

    /**
     * Gets all players of club
     *
     * @param m manager of club
     * @return all players of club
     */
    List<Player> getAllPlayersOfClub(Manager m);

    /**
     * Gets all player infos of player
     *
     * @param playerId player id
     * @return all player infos of player
     */
    List<PlayerInfo> getPlayerInfos(Long playerId);

}
