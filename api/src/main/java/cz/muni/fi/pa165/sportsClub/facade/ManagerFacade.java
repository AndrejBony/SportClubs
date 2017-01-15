package cz.muni.fi.pa165.sportsClub.facade;

import java.util.List;

import cz.muni.fi.pa165.sportsClub.dto.AuthenticationDto;
import cz.muni.fi.pa165.sportsClub.dto.ManagerDto;
import cz.muni.fi.pa165.sportsClub.dto.ManagerWithTokenDto;
import cz.muni.fi.pa165.sportsClub.dto.PlayerDto;
import cz.muni.fi.pa165.sportsClub.dto.TeamDto;

/**
 * Interface of manager facade layer
 *
 * @author
 */
public interface ManagerFacade {

    /**
     * Creates new manager
     *
     * @param m created manager
     */
    Long createManager(ManagerDto m);

    /**
     * Updates manager
     *
     * @param m updated manager
     */
    void updateManager(ManagerDto m);

    /**
     * Deletes manager
     *
     * @param id id of deleted manager
     */
    void deleteManager(Long id);

    /**
     * Gets manager by his id
     *
     * @param managerId manager id
     * @return found manager
     */
    ManagerDto getManagerById(Long managerId);

    /**
     * Gets manager by his email
     *
     * @param email email of manager
     * @return found manager
     */
    ManagerDto getManagerByEmail(String email);

    /**
     * Gets manager by his club name
     *
     * @param clubName name of club
     * @return found manager
     */
    ManagerDto getManagerByClubName(String clubName);

    /**
     * Gets all free players of manager
     *
     * @param managerId manager id
     * @return all free players
     */
    List<PlayerDto> getFreePlayers(Long managerId);

    /**
     * Gets all managers in database
     *
     * @return all managers
     */
    List<ManagerDto> getAllManagers();

    /**
     * Login for manager
     *
     * @param m email and password of manager
     * @return found manager
     */
    ManagerWithTokenDto login(AuthenticationDto m);

    /**
     * Gets all teams of manager
     *
     * @param managerId manager id
     * @return all teams
     */
    List<TeamDto> getTeamsOfManager(Long managerId);

    /**
     * Gets all not created teams of amnager
     *
     * @param managerId manager id
     * @return all not crated teams
     */
    List<TeamDto> notCreatedTeams(Long managerId);
}
