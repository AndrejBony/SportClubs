package cz.muni.fi.pa165.sportsClub.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.muni.fi.pa165.sportsClub.ServiceApplicationContext;
import cz.muni.fi.pa165.sportsClub.dao.PlayerDao;
import cz.muni.fi.pa165.sportsClub.pojo.Manager;
import cz.muni.fi.pa165.sportsClub.pojo.Player;
import cz.muni.fi.pa165.sportsClub.service.PlayerService;

/**
 *
 * @author David Koncak (410155)
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class PlayerServiceTest {

    @Mock
    private PlayerDao playerDao;

    private Player player;

	@Mock
	private Manager manager;


    @Inject
    @InjectMocks
    private PlayerService playerService;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        //playerService = new PlayerServiceImpl();
        LocalDate dt1 = LocalDate.parse("2000-06-15");
        
        player = new Player();
        player.setId(1L);
        player.setEmail("onassis@gmail.com");
        player.setFirstName("Aristoteles");
        player.setLastName("Onassis");
        player.setHeight(120);
        player.setWeight(120);
        player.setDateOfBirth(dt1);
		player.setManager(manager);
        
    }

    @Test(expected = RuntimeException.class)
    public void createPlayerTest(){
       doThrow(new RuntimeException()).when(playerDao).createPlayer(player);
       playerService.createPlayer(player);
    }
    
    @Test(expected = RuntimeException.class)
    public void createPlayerWithoutDateTest(){
        player.setDateOfBirth(null);
        doThrow(new RuntimeException()).when(playerDao).createPlayer(player);
        playerDao.createPlayer(player);
    }
    
    @Test
    public void findPlayerByIdTest(){
        when(playerDao.getPlayerById(1L)).thenReturn(player);
        Player returnedPlayer = playerService.getPlayerById(1L);
        assertEquals(player,returnedPlayer);
    }
    
    @Test(expected = RuntimeException.class)
    public void updatePlayerTest() {
        doThrow(new RuntimeException()).when(playerDao).updatePlayer(player);
        playerService.updatePlayer(player);
    }
    
    
    @Test(expected = RuntimeException.class)
    public void deletePlayerTest() {
        doThrow(new RuntimeException()).when(playerDao).deletePlayer(player);
        playerService.deletePlayer(player);
    }
    
    @Test
    public void getPlayerByEmailTest(){
        when(playerDao.getPlayerByEmail("onassis@gmail.com")).thenReturn(player);
        Player returnedPlayer = playerService.getPlayerByEmail("onassis@gmail.com");
        assertEquals(player,returnedPlayer);
    }
    
}
