package cz.muni.fi.pa165.sportsClub.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
 * 
 * @author Jan Tomasek
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceApplicationContext.class)
public class PlayerInfoTest {

	@Inject
	private ManagerDao managerDao;

	@Inject
	private TeamDao teamDao;

	@Inject
	private PlayerDao playerDao;

	@Inject
	private PlayerInfoDao playerInfoDao;

	private PlayerInfo testPlayerInfo1;
	private PlayerInfo testPlayerInfo2 = null;
	private Player testPlayer1;
	private Team testTeam1;
	private Manager testManager1;

	@Before
	public void beforeTest() {
		testManager1 = new Manager();
		testManager1.setEmail("mana@gmail.com");
		testManager1.setFirstName("mana");
		testManager1.setLastName("man");
		testManager1.setPassword("12345678");
		testManager1.setClubName("sebranka");
		testManager1.setRole("manager");

		testPlayer1 = new Player();
		testPlayer1.setDateOfBirth(LocalDate.of(1994, 5, 30));
		testPlayer1.setFirstName("first");
		testPlayer1.setLastName("last");
		testPlayer1.setEmail("test@gmail.com");
		testPlayer1.setHeight(175);
		testPlayer1.setWeight(75);
		testPlayer1.setMobile("00421605487986");

		testTeam1 = new Team();
		testTeam1.setCategory(Category.MEN);

		testTeam1.setManager(testManager1);
		testPlayer1.setManager(testManager1);

		testPlayerInfo1 = new PlayerInfo();
		testPlayerInfo1.setJerseyNumber(10);
		testPlayerInfo1.setPlayer(testPlayer1);
		testPlayerInfo1.setTeam(testTeam1);

	}

	@After
	public void afterTest() {
		if (testPlayerInfo1 != null)
			playerInfoDao.deletePlayerInfo(testPlayerInfo1);
		playerDao.deletePlayer(testPlayer1);
		teamDao.deleteTeam(testTeam1);
		managerDao.deleteManager(testManager1);
	}

	@Test
	public void testCreatePlayerInfoTest() {
		managerDao.createManager(testManager1);
		playerDao.createPlayer(testPlayer1);
		teamDao.createTeam(testTeam1);
		playerInfoDao.createPlayerInfo(testPlayerInfo1);

		Team testTeam2 = new Team();
		testTeam2.setCategory(Category.U19);
		testTeam2.setManager(testManager1);
		teamDao.createTeam(testTeam2);

		testTeam2.setManager(testManager1);
		testPlayerInfo2 = new PlayerInfo();
		testPlayerInfo2.setPlayer(testPlayer1);
		testPlayerInfo2.setTeam(testTeam2);
		testPlayerInfo2.setJerseyNumber(66);
		testTeam2.addPlayerInfo(testPlayerInfo2);
		testPlayer1.addPlayerInfo(testPlayerInfo2);
		playerInfoDao.createPlayerInfo(testPlayerInfo2);

		PlayerInfo pi = playerInfoDao.getPlayerInfoById(testPlayerInfo2.getId());

		assertEquals(testTeam2, pi.getTeam());
		assertEquals(testPlayer1, pi.getPlayer());
	}

	@Test
	public void updatePlayerInfoTest() {
		managerDao.createManager(testManager1);
		playerDao.createPlayer(testPlayer1);
		teamDao.createTeam(testTeam1);
		playerInfoDao.createPlayerInfo(testPlayerInfo1);

		testPlayerInfo1.setJerseyNumber(99);
		playerInfoDao.updatePlayerInfo(testPlayerInfo1);
		PlayerInfo pi = playerInfoDao.getPlayerInfoById(testPlayerInfo1.getId());
		assertEquals(99, pi.getJerseyNumber());
	}

	@Test
	public void deletePlayerInfoTest() {
		managerDao.createManager(testManager1);
		teamDao.createTeam(testTeam1);
		playerDao.createPlayer(testPlayer1);
		playerInfoDao.createPlayerInfo(testPlayerInfo1);

		Long id = testPlayerInfo1.getId();
		playerInfoDao.deletePlayerInfo(testPlayerInfo1);

		PlayerInfo deletedPlayerInfo = playerInfoDao.getPlayerInfoById(id);

		assertNull(deletedPlayerInfo);

		testPlayerInfo1 = null;
	}

	@Test
	public void getPlayerInfoByIdTest() {
		managerDao.createManager(testManager1);
		playerDao.createPlayer(testPlayer1);
		teamDao.createTeam(testTeam1);
		playerInfoDao.createPlayerInfo(testPlayerInfo1);

		PlayerInfo retrieved = playerInfoDao.getPlayerInfoById(testPlayerInfo1.getId());
		assertEquals(testPlayer1, retrieved.getPlayer());
		assertEquals(testTeam1, retrieved.getTeam());
		assertEquals(10, retrieved.getJerseyNumber());
	}

	@Test
	public void deletePlayerInfoByTeamAndPlayerTest() {
		managerDao.createManager(testManager1);
		playerDao.createPlayer(testPlayer1);
		teamDao.createTeam(testTeam1);
		playerInfoDao.createPlayerInfo(testPlayerInfo1);

		playerInfoDao.deletePlayerInfoByTeamAndPlayer(testTeam1, testPlayer1);
		PlayerInfo pi = playerInfoDao.getPlayerInfoById(testPlayerInfo1.getId());
		assertNull(pi);
		testPlayerInfo1 = null;
	}

	@Test
	public void changeJerseyNumberTest() {
		managerDao.createManager(testManager1);
		playerDao.createPlayer(testPlayer1);
		teamDao.createTeam(testTeam1);
		playerInfoDao.createPlayerInfo(testPlayerInfo1);

		playerInfoDao.changeJerseyNumber(testTeam1, testPlayer1, 30);
		PlayerInfo pi = playerInfoDao.getPlayerInfoById(testPlayerInfo1.getId());
		assertEquals(30, pi.getJerseyNumber());

	}

}
