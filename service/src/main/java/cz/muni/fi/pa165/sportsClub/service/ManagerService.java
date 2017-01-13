package cz.muni.fi.pa165.sportsClub.service;

import java.util.List;

import cz.muni.fi.pa165.sportsClub.pojo.Manager;
import cz.muni.fi.pa165.sportsClub.pojo.Player;
import cz.muni.fi.pa165.sportsClub.pojo.Team;

/**
 * Interface of manager service layer
 *
 * @author
 */
public interface ManagerService {

    /**
     * Creates new manager
     *
     * @param m created manager
     * @return created amnager
     */
    Manager createManager(Manager m);

    /**
     * Updates manager
     *
     * @param m updated manager
     */
    void updateManager(Manager m);

    /**
     * Deletes manager
     *
     * @param m deleted manager
     */
    void deleteManager(Manager m);

    /**
     * Gets manager by id
     *
     * @param managerId manager id
     * @return found manager
     */
    Manager getManagerById(Long managerId);

    /**
     * Gets manager by his email
     *
     * @param email email of manager
     * @return found manager
     */
    Manager getManagerByEmail(String email);

    /**
     * Gets manager by his club name
     *
     * @param clubName name of club
     * @return found manager
     */
    Manager getManagerByClubName(String clubName);

    /**
     * Authenticates manager
     *
     * @param email email of amnager
     * @param password password of manager
     * @return authenticated amnager
     */
    Manager authenticate(String email, String password);

    /**
     * Gets all free players of club
     *
     * @param m manager of club
     * @return all free players
     */
    List<Player> getFreePlayersOfClub(Manager m);

    /**
     * Gets all managers in database
     *
     * @return all managers
     */
    List<Manager> getAllManagers();

    /**
     * Gets all not created teams of amnager
     *
     * @param managerId manager id
     * @return all not crated teams
     */
    List<Team> getNotCreatedTeamsOfManager(Manager m);
}
