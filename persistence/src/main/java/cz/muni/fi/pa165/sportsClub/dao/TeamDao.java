package cz.muni.fi.pa165.sportsClub.dao;
import cz.muni.fi.pa165.sportsClub.pojo.Player;
import cz.muni.fi.pa165.sportsClub.pojo.Team;

import java.util.List;

/**
 * CRUD operations on Team
 * 
 * @author Andrej 410433
 */
public interface TeamDao {
    
    /**
     * Creates a new team and stores into database
     * 
     * @param team
     */
    void createTeam(Team team);
    
    /**
     * Updates exist team in database
     * 
     * @param team  
     */
    void updateTeam(Team team);
    
    /**
     * Deletes team in database
     * 
     * @param team 
     */
    void deleteTeam(Team team);
    
    /**
     * Finds team by its id
     * 
     * @param id 
     * @return found team
     */
    Team getTeamById(Long id);

    /**
     * Check if given jersey number already exist in the team
     *
     * @param player, team, jerseyNumber
     * @return true if jersey number is unique, false otherwis
     */
    boolean isJerseyNumberUnique(Player player, Team team, int jerseyNumber);

    /**
     * Finds all players of team
     * 
     * @param team of players
     * @return players in team
     */
    List<Player> getPlayersOfTeam(Team team);
}
