package cz.muni.fi.pa165.sportsClub.test;

import java.time.LocalDate;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.muni.fi.pa165.sportsClub.PersistenceApplicationContext;
import cz.muni.fi.pa165.sportsClub.dao.ManagerDao;
import cz.muni.fi.pa165.sportsClub.dao.PlayerDao;
import cz.muni.fi.pa165.sportsClub.pojo.Manager;
import cz.muni.fi.pa165.sportsClub.pojo.Player;

/**
 *
 * @author David Koncak (410155)
 */

@ContextConfiguration(classes = PersistenceApplicationContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class PlayerTest {

    @Inject
    private PlayerDao playerDao;
	@Inject
	private ManagerDao managerDao;
    
	private Player player1;
	private Manager manager;
    
    @Before
    public void createPlayers(){
		manager = new Manager();
		manager.setClubName("manager");
		manager.setEmail("m@gmail.com");
		manager.setFirstName("first");
		manager.setLastName("last");
		manager.setMobile("002");
		manager.setPassword("sdfsdfsdf");
		manager.setRole("manager");
		managerDao.createManager(manager);

		player1 = new Player();
        player1.setEmail("onassis@gmail.com");
        player1.setFirstName("Aristoteles");
        player1.setLastName("Onassis");
        player1.setHeight(120);
        player1.setWeight(120);
		player1.setDateOfBirth(LocalDate.parse("2000-06-15"));
		player1.setManager(manager);
		manager.addPlayer(player1);
    }
    
	@After
	public void tearDown() {
		managerDao.deleteManager(manager);
	}

    @Test
    public void deletePlayerTest(){
		playerDao.createPlayer(player1);
		Assert.assertNotNull(player1.getId());
		playerDao.deletePlayer(player1);
		Assert.assertNull(playerDao.getPlayerById(player1.getId()));
    }
    
    @Test
    public void getPlayerByIdTest(){
        playerDao.createPlayer(player1);
        Player found = playerDao.getPlayerById(player1.getId());
        Assert.assertEquals(found, player1);
    }
    
    @Test
    public void findByNonExistentId() {
	Assert.assertNull(playerDao.getPlayerById(205648L));
    }
    
    @Test
    public void updatePlayerTest(){
        playerDao.createPlayer(player1);
        player1.setHeight(230);
        playerDao.updatePlayer(player1);
        Assert.assertEquals(230, playerDao.getPlayerById(player1.getId()).getHeight(), 0.01);
    }
    
    @Test
    public void createPlayerTest(){
        playerDao.createPlayer(player1);
        Assert.assertEquals("Onassis", playerDao.getPlayerById(player1.getId()).getLastName());
    }
    
    @Test
    public void nullIdTest(){
        Assert.assertNull(player1.getId());
        playerDao.createPlayer(player1);
        Assert.assertNotNull(player1.getId());
    }
    
}
