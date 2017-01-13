package cz.muni.fi.pa165.sportsClub.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import cz.muni.fi.pa165.sportsClub.pojo.Team;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.pa165.sportsClub.pojo.Player;

/**
 * This is base implementation of {@link PlayerDao}
 *
 * @author Simon Sudora 461460
 */

@Repository
@Transactional
public class PlayerDaoImpl implements PlayerDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createPlayer(Player player) {
        if(player == null){
            throw new IllegalArgumentException("Argument can not be null");
        }
        em.persist(player);
    }

    @Override
    public void updatePlayer(Player player) {
        if(player == null){
            throw new IllegalArgumentException("Argument can not be null");
        }
        em.merge(player);
    }

    @Override
    public void deletePlayer(Player player) {
        if(player == null){
            throw new IllegalArgumentException("Argument can not be null");
        }
		if (em.contains(player))
			em.remove(player);
		else
			em.remove(em.getReference(Player.class, player.getId()));
    }

    @Override
    public Player getPlayerById(Long id)  {
        return em.find(Player.class, id);
    }

    @Override
    public List<Player> getAllPlayers() {
        try {
            return em.createQuery("SELECT p FROM Player p", Player.class).getResultList();
        } catch (NoResultException nrf) {
            return null;
        }
    }

    @Override
    public Player getPlayerByEmail(String email) {
        try {
            return em.createQuery("select p from Player p where email = :email",
                                Player.class).setParameter("email", email).getSingleResult();
	    } catch (NoResultException nrf) {
            return null;
	    }
    }

    @Override
    public List<Team> getTeamsOfPlayer(Player player) {
        TypedQuery<Team> query = em.createQuery("SELECT p FROM Team p " +
                "JOIN p.playerInfos pi " +
                "WHERE pi.player= :player", Team.class);
        query.setParameter("player", player);
        return query.getResultList();
    }
}
