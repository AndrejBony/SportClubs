package cz.muni.fi.pa165.sportsClub.dao;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.pa165.sportsClub.enums.Category;
import cz.muni.fi.pa165.sportsClub.pojo.Manager;
import cz.muni.fi.pa165.sportsClub.pojo.Player;
import cz.muni.fi.pa165.sportsClub.pojo.Team;

/**
 * @author Jan Tomasek
 */
@Repository
@Transactional
public class ManagerDaoImpl implements ManagerDao{

	@PersistenceContext
	private EntityManager em;

	@Override
	public Manager createManager(Manager m) {
		if(m == null){
			throw new IllegalArgumentException("Argument can not be null");
		}
		em.persist(m);
		return m;
	}

	@Override
	public Manager getManagerById(Long id) {
		return em.find(Manager.class, id);
	}

	@Override
	public Manager getManagerByEmail(String email) {
		try{
			return em.createQuery("select m from Manager m where email like :email", Manager.class).
			setParameter("email", email).getSingleResult();
		} catch (NoResultException nrf) {
			return null;
		}
	}

	@Override
	public void updateManager(Manager m) {
		if(m == null){
			throw new IllegalArgumentException("Argument can not be null");
		}
		Manager full = em.find(Manager.class, m.getId());
		if(full == null)
			throw new EmptyResultDataAccessException("manager with id: " + m.getId() + " not found",1);
		m.setPassword(full.getPassword());
		m.setRole(full.getRole());
		em.merge(m);
	}

	@Override
	public void deleteManager(Manager m){
		if(m == null){
			throw new IllegalArgumentException("Argument can not be null");
		}
		if (em.contains(m))
			em.remove(m);
		else
			em.remove(em.getReference(Manager.class, m.getId()));
	}

	@Override
	public Manager getManagerByClubName(String name) {
		try {
			return em.createQuery("select m from Manager m where clubName = :name", Manager.class)
					.setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException nrf) {
			return null;
		}
	}

	@Override
	public List<Manager> getAllManagers() {
		return Collections.unmodifiableList(
				em.createQuery("SELECT m FROM Manager m where role='manager'", Manager.class).getResultList());
	}

	@Override
	public Manager getManagerWithAllData(Long managerId) {
		Manager m = getManagerById(managerId);
		List<Player> players = m.getPlayers();
		List<Team> teams = m.getTeams();
		for (Team team : teams) {
			team.getPlayerInfos().size();
		}
		for (Player player : players) {
			player.getPlayerInfos().size();
		}
		return m;
	}

	@Override
	public List<Player> getFreePlayers(Manager manager) {
		TypedQuery<Player> query = em.createQuery("SELECT p FROM Player p " + "LEFT JOIN p.playerInfos pi "
				+ "WHERE p.manager = :manager " + "AND pi.player = null", Player.class);
		query.setParameter("manager", manager);
		return query.getResultList();
	}

	@Override
	public List<Player> getPlayersWithDobBetween(Team team,LocalDate bottomLimit, LocalDate upperLimit) {
		TypedQuery<Player> query = em.createQuery(
				"SELECT p FROM Player p " +
						"WHERE p.dateOfBirth > :bottomLimit " +
						"AND p.dateOfBirth <= :upperLimit " +
						"AND p.manager = :manager " +
						"AND p.id NOT IN (" +
						"SELECT distinct p.id FROM Player p " +
						"JOIN p.playerInfos pi " +
						"WHERE p.manager = :manager " +
						"AND pi.team = :team)",
				Player.class);
		query.setParameter("manager", team.getManager());
		query.setParameter("team", team);
		query.setParameter("bottomLimit", bottomLimit);
		query.setParameter("upperLimit", upperLimit);
		return query.getResultList();
	}

	@Override
	public List<Player> getFreePlayersWithDobBetween(Manager manager, LocalDate bottomLimit, LocalDate upperLimit) {
		TypedQuery<Player> query = em.createQuery(
				"SELECT distinct p FROM Player p " +
						"LEFT JOIN p.playerInfos pi " +
						"WHERE p.manager = :manager " +
						"AND (pi is null)" +
						"AND p.dateOfBirth > :bottomLimit " +
						"AND p.dateOfBirth <= :upperLimit",
				Player.class);
		query.setParameter("manager", manager);
		query.setParameter("bottomLimit", bottomLimit);
		query.setParameter("upperLimit", upperLimit);
		return query.getResultList();
	}

	@Override
	public List<Category> getCategoriesOfTeams(Manager manager) {
		TypedQuery<Category> query = em.createQuery(
				"SELECT t.category FROM Team t " + "WHERE t.manager = :manager",
				Category.class);
		query.setParameter("manager", manager);
		return query.getResultList();
	}
}
