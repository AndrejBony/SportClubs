package cz.muni.fi.pa165.sportsClub.dao;

import java.time.LocalDate;
import java.util.List;

import cz.muni.fi.pa165.sportsClub.enums.Category;
import cz.muni.fi.pa165.sportsClub.pojo.Manager;
import cz.muni.fi.pa165.sportsClub.pojo.Player;
import cz.muni.fi.pa165.sportsClub.pojo.Team;

/**
 * @author Jan Tomasek
 *         <p>
 *         Interface for database operations over Manager entity.
 */
public interface ManagerDao {

    /**
     * Stores manager entity into database.
     *
     * @param manager
     */
    Manager createManager(Manager manager);

    /**
     * Updates manager entity in database.
     *
     * @param manager
     */
    void updateManager(Manager manager);

    /**
     * Removes manager entity from database.
     *
     * @param manager
     */
    void deleteManager(Manager manager);

    /**
     * Retrieves manager object by its unique id. Uses lazy loading so "team"
     * set is not populated.
     *
     * @param id
     * @return manager object with specified id if exists, null otherwise
     */
    Manager getManagerById(Long id);

    /**
     * Retrieves manager object by its unique email. Uses lazy loading so "team"
     * set is not populated.
     *
     * @param email
     * @return manager object with specified email if exists, null otherwise
     */
    Manager getManagerByEmail(String email);

    Manager getManagerByClubName(String clubName);

    List<Manager> getAllManagers();

    /**
     * @param managerId
     * @return completely initialized manager with all data
     * (players,teams,associations)
     */
    Manager getManagerWithAllData(Long managerId);

    /**
     * @param manager
     * @return all the free players of the manager
     */
    List<Player> getFreePlayers(Manager manager);

    /**
     * @param team,ageLimitDate
     * @return list of players with date of birth between two dates
     */
    List<Player> getPlayersWithDobBetween(Team team, LocalDate bottomLimit, LocalDate upperLimit);

    /**
     * @param manager
     * @return list of categories of teams fo concrete Manager
     */
    List<Category> getCategoriesOfTeams(Manager manager);
}
