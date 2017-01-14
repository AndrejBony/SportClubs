package cz.muni.fi.pa165.sportsClub.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.muni.fi.pa165.sportsClub.PersistenceApplicationContext;
import cz.muni.fi.pa165.sportsClub.dao.ManagerDao;
import cz.muni.fi.pa165.sportsClub.dao.PlayerDao;
import cz.muni.fi.pa165.sportsClub.dao.PlayerInfoDao;
import cz.muni.fi.pa165.sportsClub.dao.TeamDao;
import cz.muni.fi.pa165.sportsClub.enums.Category;
import cz.muni.fi.pa165.sportsClub.pojo.Manager;
import cz.muni.fi.pa165.sportsClub.pojo.Player;
import cz.muni.fi.pa165.sportsClub.pojo.PlayerInfo;
import cz.muni.fi.pa165.sportsClub.pojo.Team;

/**
 * @author Simon Sudora 461460
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceApplicationContext.class)
public class ManagerTest {

	private Manager manager;
	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;

	@Inject
	private ManagerDao managerDao;

	@Inject
	private PlayerDao playerDao;

	@Inject
	private PlayerInfoDao playerInfoDao;

	@Inject
	private TeamDao teamDao;

	@Before
	public void makeManager() {
		manager = new Manager();
		manager.setEmail("manager@manager.com");
		manager.setFirstName("Simon");
		manager.setLastName("Sudora");
		manager.setPassword("12345");
		manager.setMobile("0907123456");
		manager.setClubName("Sparta Praha");
		manager.setRole("manager");

		player1 = new Player();
		player1.setEmail("onassis@gmail.com");
		player1.setFirstName("Aristoteles");
		player1.setLastName("Onassis");
		player1.setHeight(120);
		player1.setWeight(120);
		player1.setDateOfBirth(LocalDate.parse("2000-06-15"));
		player1.setManager(manager);

		player2 = new Player();
		player2.setEmail("pepapppp@noooooovak.com");
		player2.setFirstName("Pepa");
		player2.setLastName("Novak");
		player2.setHeight(150);
		player2.setWeight(67);
		player2.setMobile("+42190000000");
		player2.setDateOfBirth(LocalDate.parse("1991-06-15"));
		player2.setManager(manager);

		player3 = new Player();
		player3.setEmail("fero@hora.com");
		player3.setFirstName("fero");
		player3.setLastName("hora");
		player3.setHeight(179);
		player3.setWeight(70);
		player3.setMobile("+42190000000");
		player3.setDateOfBirth(LocalDate.parse("1996-06-15"));
		player3.setManager(manager);

		player4 = new Player();
		player4.setEmail("lolo@hora.com");
		player4.setFirstName("lolo");
		player4.setLastName("hora");
		player4.setHeight(179);
		player4.setWeight(70);
		player4.setMobile("+42190000000");
		player4.setDateOfBirth(LocalDate.parse("1996-06-15"));
		player4.setManager(manager);
	}

	@After
	public void afterTest() {
		if (manager != null)
			managerDao.deleteManager(manager);
	}

	@Test()
	public void createManagerTest() {
		assertNull(manager.getId());
		managerDao.createManager(manager);
		assertNotNull(manager.getId());

		Manager savedManager = managerDao.getManagerById(manager.getId());
		assertEquals(manager.getId(), savedManager.getId());
	}

	@Test(expected = DataAccessException.class)
	public void createExistingManagerTest() {
		assertNull(manager.getId());

		managerDao.createManager(manager);
		assertNotNull(manager.getId());

		Manager savedManager = managerDao.getManagerById(manager.getId());
		assertEquals(manager.getId(), savedManager.getId());
		managerDao.createManager(manager);
	}

	@Test(expected = DataAccessException.class)
	public void createManagerWithExistingClubNameTest() {
		assertNull(manager.getId());

		managerDao.createManager(manager);
		assertNotNull(manager.getId());

		Manager savedManager = managerDao.getManagerById(manager.getId());
		assertEquals(manager.getId(), savedManager.getId());
		Manager manager2 = new Manager();
		manager2 = new Manager();
		manager2.setEmail("2manager@manager.com");
		manager2.setFirstName("2Simon");
		manager2.setLastName("2Sudora");
		manager2.setPassword("212345");
		manager2.setMobile("20907123456");
		manager2.setClubName(manager.getClubName());
		manager2.setRole("manager");
		managerDao.createManager(manager2);
	}

	@Test
	public void updateManagerTest() {
		Manager originalManager = manager;
		managerDao.createManager(originalManager);

		Manager updatedManager = originalManager;
		updatedManager.setClubName("Slavia Praha");
		managerDao.updateManager(updatedManager);
		Manager savedManager = managerDao.getManagerById(updatedManager.getId());
		assertEquals(originalManager.getId(), savedManager.getId());
		assertEquals(originalManager.getClubName(), savedManager.getClubName());
	}

	@Test
	public void deleteManagerTest() {
		managerDao.createManager(manager);
		Long id = manager.getId();

		managerDao.deleteManager(manager);
		Manager deletedManager = managerDao.getManagerById(id);
		assertNull(deletedManager);
		manager = null;
	}

	@Test
	public void getFreePlayers() {
		managerDao.createManager(manager);
		assertNotNull(manager.getId());

		playerDao.createPlayer(player1);
		playerDao.createPlayer(player2);
		assertNotNull(player1.getId());
		assertNotNull(player2.getId());

		Team team = new Team();
		team.setCategory(Category.MEN);
		team.setManager(manager);
		teamDao.createTeam(team);

		PlayerInfo playerInfo = new PlayerInfo();
		playerInfo.setJerseyNumber(10);
		playerInfo.setPlayer(player1);
		playerInfo.setTeam(team);
		playerInfoDao.createPlayerInfo(playerInfo);

		List<Player> freePlayers = managerDao.getFreePlayers(manager);

		assertEquals(1, freePlayers.size());
		assertEquals(player2, freePlayers.get(0));
	}

	@Test
	public void getPlayersWithHigherDobThan() {
		managerDao.createManager(manager);
		assertNotNull(manager.getId());
		playerDao.createPlayer(player1);
		playerDao.createPlayer(player2);
		playerDao.createPlayer(player3);
		playerDao.createPlayer(player4);

		Team team1 = new Team();
		team1.setCategory(Category.MEN);
		team1.setManager(manager);
		teamDao.createTeam(team1);

		Team team2 = new Team();
		team2.setCategory(Category.U17);
		team2.setManager(manager);
		teamDao.createTeam(team2);

		PlayerInfo playerInfo1 = new PlayerInfo();
		playerInfo1.setPlayer(player4);
		playerInfo1.setTeam(team1);
		playerInfo1.setJerseyNumber(14);
		playerInfoDao.createPlayerInfo(playerInfo1);

		PlayerInfo playerInfo2 = new PlayerInfo();
		playerInfo2.setPlayer(player3);
		playerInfo2.setTeam(team2);
		playerInfo2.setJerseyNumber(14);
		playerInfoDao.createPlayerInfo(playerInfo2);

		LocalDate bottomLimit = LocalDate.parse("1995-06-15");
		LocalDate upperLimit = LocalDate.parse("1999-06-15");
		List<Player> freePlayers = managerDao.getPlayersWithDobBetween(team1, bottomLimit, upperLimit);

		assertEquals(1, freePlayers.size());
		assertEquals(player3, freePlayers.get(0));
	}

	@Test
	public void testGetManagerByEmail() {
		managerDao.createManager(manager);
		Manager foundManager = managerDao.getManagerByEmail(manager.getEmail());
		assertEquals(manager, foundManager);
	}

	@Test
	public void testGetManagerByClubName() {
		managerDao.createManager(manager);
		Manager foundManager = managerDao.getManagerByClubName(manager.getClubName());
		assertEquals(manager, foundManager);
	}

	@Test
	public void getCategoriesOfTeamsTest(){
		managerDao.createManager(manager);
		Team team = new Team();
		team.setCategory(Category.MEN);
		team.setManager(manager);
		teamDao.createTeam(team);
		List<Category> categories = managerDao.getCategoriesOfTeams(manager);
		assertEquals(1, categories.size());
		assertEquals(Category.MEN, categories.get(0));
	}

}
