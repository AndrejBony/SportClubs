package cz.muni.fi.pa165.sportsClub.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import cz.muni.fi.pa165.sportsClub.pojo.Player;
import cz.muni.fi.pa165.sportsClub.pojo.PlayerInfo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.pa165.sportsClub.pojo.Team;

import java.util.List;

/**
 * @author Andrej 410433
 */
@Repository
@Transactional
public class TeamDaoImpl implements TeamDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createTeam(Team team) {
        if (team == null) {
            throw new IllegalArgumentException("Argument can not be null");
        }
        em.persist(team);
    }

    @Override
    public void deleteTeam(Team team) {
        if (team == null) {
            throw new IllegalArgumentException("Argument can not be null");
        }
        if (em.contains(team)) {
            em.remove(team);
        } else {
            em.remove(em.getReference(Team.class, team.getId()));
        }
    }

    @Override
    public void updateTeam(Team team) {
        if (team == null) {
            throw new IllegalArgumentException("Argument can not be null");
        }
        em.merge(team);
    }

    @Override
    public Team getTeamById(Long id) {
        return em.find(Team.class, id);
    }

    @Override
    public boolean isJerseyNumberUnique(Team team, int jerseyNumber) {
        TypedQuery<PlayerInfo> query = em.createQuery("SELECT pi FROM Team t "
                + "JOIN t.playerInfos pi "
                + "WHERE pi.jerseyNumber = :jerseyNumber", PlayerInfo.class);
        query.setParameter("jerseyNumber", jerseyNumber);
        if (query.getResultList().isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public List<Player> getPlayersOfTeam(Team team) {
        TypedQuery<Player> query = em.createQuery("SELECT p FROM Player p "
                + "JOIN p.playerInfos pi "
                + "WHERE pi.team= :team", Player.class);
        query.setParameter("team", team);
        return query.getResultList();
    }
}
