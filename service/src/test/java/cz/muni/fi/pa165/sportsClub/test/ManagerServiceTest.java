package cz.muni.fi.pa165.sportsClub.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import cz.muni.fi.pa165.sportsClub.dao.ManagerDao;
import cz.muni.fi.pa165.sportsClub.enums.Category;
import cz.muni.fi.pa165.sportsClub.pojo.Manager;
import cz.muni.fi.pa165.sportsClub.pojo.Player;
import cz.muni.fi.pa165.sportsClub.pojo.PlayerInfo;
import cz.muni.fi.pa165.sportsClub.pojo.Team;
import cz.muni.fi.pa165.sportsClub.service.ManagerService;

/**
 * @author Andrej 410433
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class ManagerServiceTest {

    @Inject
    @InjectMocks
    private ManagerService managerService;

    @Mock
    private ManagerDao managerDao;

    @Mock
    private Manager mockManager;

    @Mock
    private Player mockPlayer1;

    @Mock
    private Player mockPlayer2;

    @Mock
    private Player mockPlayer3;

    private PlayerInfo playerInfo;
    private Team team;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockManager.setEmail("david@koncak.com");
        mockManager.setPassword("TheBest99");
        mockManager.setFirstName("David");
        mockManager.setLastName("Koncak");
        mockManager.setClubName("FC Slavia Praha");

        team = new Team();
        team.setManager(mockManager);
        team.setCategory(Category.MEN);

        mockPlayer1.setFirstName("Simon");
        mockPlayer1.setLastName("Sudora");
        mockPlayer1.setEmail("simon@sudora.com");
        mockPlayer1.setHeight(180);
        mockPlayer1.setWeight(120);
        mockPlayer1.setDateOfBirth(LocalDate.parse("2000-06-15"));
        mockPlayer1.setManager(mockManager);

        mockPlayer2.setFirstName("Jan");
        mockPlayer2.setLastName("Tomasek");
        mockPlayer2.setEmail("jan@tomasek.com");
        mockPlayer2.setHeight(180);
        mockPlayer2.setWeight(120);
        mockPlayer2.setDateOfBirth(LocalDate.parse("2000-06-15"));
        mockPlayer2.setManager(mockManager);

        mockPlayer3.setFirstName("Andrej");
        mockPlayer3.setLastName("Bonis");
        mockPlayer3.setEmail("andrej@bonis.com");
        mockPlayer3.setHeight(180);
        mockPlayer3.setWeight(120);
        mockPlayer3.setDateOfBirth(LocalDate.parse("2000-06-15"));
        mockPlayer3.setManager(mockManager);

        playerInfo = new PlayerInfo();
        playerInfo.setJerseyNumber(15);
        playerInfo.setPlayer(mockPlayer3);
        playerInfo.setTeam(team);

    }

    @Test(expected = RuntimeException.class)
    public void createManagerTest() {
        doThrow(new RuntimeException()).when(managerDao).createManager(mockManager);
        managerService.createManager(mockManager);
    }

    @Test(expected = RuntimeException.class)
    public void deleteManagerTest() {
        doThrow(new RuntimeException()).when(managerDao).deleteManager(mockManager);
        managerService.deleteManager(mockManager);
    }

    @Test(expected = RuntimeException.class)
    public void updateManagerTest() {
        doThrow(new RuntimeException()).when(managerDao).updateManager(mockManager);
        managerService.updateManager(mockManager);
    }

    @Test
    public void getManagerByIdTest() {
        when(managerDao.getManagerById(1L)).thenReturn(mockManager);
        Manager returnedManager = managerService.getManagerById(1L);
        assertEquals(mockManager, returnedManager);
    }

    @Test
    public void getManagerByClubNameTest() {
        when(managerDao.getManagerByClubName("FC Slavia Praha")).thenReturn(mockManager);
        Manager returnedManager = managerService.getManagerByClubName("FC Slavia Praha");
        assertEquals(mockManager, returnedManager);
    }

    @Test
    public void getAllManagersTest() {
        List<Manager> managers = new ArrayList<Manager>();

        Manager manager2 = new Manager();
        manager2.setClubName("FC Sparta Praha");

        managers.add(mockManager);
        managers.add(manager2);

        when(managerDao.getAllManagers()).thenReturn(managers);
        List<Manager> returnedManagers = managerService.getAllManagers();

        assertEquals(managers, returnedManagers);
    }

    @Test
    public void getFreePlayersOfManagerTest() {
        List<Player> freePlayers = new ArrayList<Player>();
        freePlayers.add(mockPlayer1);
        freePlayers.add(mockPlayer2);

        List<Player> playersOfManager = new ArrayList<Player>();
        playersOfManager.add(mockPlayer1);
        playersOfManager.add(mockPlayer2);
        playersOfManager.add(mockPlayer3);

        List<PlayerInfo> playerInfos = new ArrayList<PlayerInfo>();
        playerInfos.add(playerInfo);

        when(managerDao.getFreePlayers(mockManager)).thenReturn(freePlayers);
		when(mockManager.getPassword()).thenReturn("somepass");
        managerService.createManager(mockManager);

        List<Player> returnedFreePlayers = managerService.getFreePlayersOfClub(mockManager);
        assertEquals(freePlayers, returnedFreePlayers);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateNullManagerTest() {
        doThrow(new IllegalArgumentException("null Manager")).when(managerDao).updateManager(null);
        managerService.updateManager(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteNullManagerTest() {
        doThrow(new IllegalArgumentException("null Manager")).when(managerDao).deleteManager(null);
        managerService.deleteManager(null);
    }

    @Test
    public void getManagerByNonExistingIdTest() {
        assertNull(managerService.getManagerById(10L));
    }

    @Test
    public void getManagerByEmailTest() {
        when(managerDao.getManagerByEmail("andrej@gmail.com")).thenReturn(mockManager);
        Manager found = managerService.getManagerByEmail("andrej@gmail.com");
        assertEquals(mockManager, found);
    }

    @Test
    public void getManagerByNonExistingEmailTest() {
        assertNull(managerService.getManagerByEmail("bonis@gmail.com"));
    }

    @Test
    public void getNotAlreadyCreatedTeamsOfManagerTest() {
        List<Category> categoriesOfCreatedTeams = new ArrayList<Category>();
        categoriesOfCreatedTeams.add(Category.MEN);
        when(managerDao.getCategoriesOfTeams(mockManager)).thenReturn(categoriesOfCreatedTeams);
        List<Team> returnedTeams = managerService.getNotCreatedTeamsOfManager(mockManager);
        assertEquals(4,returnedTeams.size());
    }
}
