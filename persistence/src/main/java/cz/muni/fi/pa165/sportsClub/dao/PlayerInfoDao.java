package cz.muni.fi.pa165.sportsClub.dao;

import cz.muni.fi.pa165.sportsClub.pojo.Player;
import cz.muni.fi.pa165.sportsClub.pojo.PlayerInfo;
import cz.muni.fi.pa165.sportsClub.pojo.Team;

/**
 * CRUD operations on PlayerInfo
 *
 * @author David Koncak (410155)
 */
public interface PlayerInfoDao {

    /**
     * Creates a new player info and stores into database
     *
     * @param pi
     */
    void createPlayerInfo(PlayerInfo pi);

    /**
     * Updates exist player info in database
     *
     * @param pi
     */
    void updatePlayerInfo(PlayerInfo pi);

    /**
     * Deletes player info in database
     *
     * @param pi
     */
    void deletePlayerInfo(PlayerInfo pi);

    /**
     * Deletes player info for concrete Player of concrete team
     *
     * @param player whose jersey number is going to be changed
     * @param team which player is part of
     */
    void deletePlayerInfoByTeamAndPlayer(Team team, Player player);

    /**
     * Finds player info by its id
     *
     * @param pid player info id
     * @return found player info
     */
    PlayerInfo getPlayerInfoById(Long pid);

    /**
     * Change jersey number of concrete player in concrete team
     *
     * @param player whose jersey number is going to be changed
     * @param team which player is part of
     */
    void changeJerseyNumber(Team team, Player player, int newJerseyNumber);

}
