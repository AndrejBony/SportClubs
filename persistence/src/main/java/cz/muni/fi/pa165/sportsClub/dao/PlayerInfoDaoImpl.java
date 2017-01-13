package cz.muni.fi.pa165.sportsClub.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cz.muni.fi.pa165.sportsClub.pojo.Player;
import cz.muni.fi.pa165.sportsClub.pojo.Team;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.pa165.sportsClub.pojo.PlayerInfo;

/**
 * @author David Koncak (410155)
 */
@Repository
@Transactional
public class PlayerInfoDaoImpl implements PlayerInfoDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createPlayerInfo(PlayerInfo pi) {
        if(pi == null){
            throw new IllegalArgumentException("Argument can not be null");
        }
        em.persist(pi);
    }

    @Override
    public void deletePlayerInfo(PlayerInfo pi) {
        if(pi == null){
            throw new IllegalArgumentException("Argument can not be null");
        }
		if (em.contains(pi))
			em.remove(pi);
		else
			em.remove(em.getReference(PlayerInfo.class, pi.getId()));
    }

    @Override
    public void deletePlayerInfoByTeamAndPlayer(Team team, Player player) {
        Query query = em.createQuery("DELETE  FROM PlayerInfo pi " +
                                    "WHERE pi.player = :player " +
                                    "AND pi.team = :team");
        query.setParameter("player", player);
        query.setParameter("team", team);
        if(query.executeUpdate() == 0){
            throw new IllegalArgumentException(player + " is not player of team " + team);
        };
    }

    @Override
    public void updatePlayerInfo(PlayerInfo pi) {
        if(pi == null){
            throw new IllegalArgumentException("Argument can not be null");
        }
        em.merge(pi);
    }

    @Override
	public PlayerInfo getPlayerInfoById(Long piId) {
        return em.find(PlayerInfo.class, piId);
    }

    @Override
    public void changeJerseyNumber(Team team, Player player, int newJerseyNumber) {
        Query query = em.createQuery("UPDATE PlayerInfo pi " +
                "SET pi.jerseyNumber = :newJerseyNumber " +
                "WHERE pi.player = :player " +
                "AND pi.team = :team");
        query.setParameter("player", player);
        query.setParameter("team", team);
        query.setParameter("newJerseyNumber", newJerseyNumber);
        if(query.executeUpdate() == 0){
            throw new IllegalArgumentException(player + " is not player of team " + team);
        };
    }
}
