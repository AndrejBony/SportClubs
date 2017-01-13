package cz.muni.fi.pa165.sportsClub.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.sportsClub.dao.ManagerDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.muni.fi.pa165.sportsClub.ServiceApplicationContext;
import cz.muni.fi.pa165.sportsClub.dao.PlayerInfoDao;
import cz.muni.fi.pa165.sportsClub.dao.TeamDao;
import cz.muni.fi.pa165.sportsClub.enums.Category;
import cz.muni.fi.pa165.sportsClub.pojo.Manager;
import cz.muni.fi.pa165.sportsClub.pojo.Player;
import cz.muni.fi.pa165.sportsClub.pojo.PlayerInfo;
import cz.muni.fi.pa165.sportsClub.pojo.Team;
import cz.muni.fi.pa165.sportsClub.service.TeamService;

/**
 * @author Simon Sudora 461460
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class TeamServiceTest {
    @Mock
    TeamDao teamDao;

    @Mock
    ManagerDao managerDao;

    @Mock
    Team mockTeam;

    @Mock
    Manager mockManager;

    @Mock
    Player mockPlayer;

	@Mock
	PlayerInfo mockPlayerInfo;

    @Mock
	PlayerInfoDao playerInfoDao;

    @Inject
    @InjectMocks
    private TeamService teamService;

    private Team team1;
    private Team team2;
    private PlayerInfo playerInfo1;
    private PlayerInfo playerInfo2;
    private Player player1;
    private Player player2;



    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        team1 = new Team();
        team1.setCategory(Category.U13);
        team2 = new Team();
        team2.setCategory(Category.MEN);

        playerInfo1 = new PlayerInfo();
        playerInfo2 = new PlayerInfo();

        player1 = new Player();
        player2 = new Player();
    }

    @Test(expected = RuntimeException.class)
    public void createTeamTest(){
        doThrow(new RuntimeException()).when(teamDao).createTeam(mockTeam);
        teamService.createTeam(mockTeam);
    }

    @Test(expected = RuntimeException.class)
    public void deleteTeamTest(){
        doThrow(new RuntimeException()).when(teamDao).deleteTeam(mockTeam);
        teamService.deleteTeam(mockTeam);
    }

    @Test(expected = RuntimeException.class)
    public void updateTeamTest(){
        doThrow(new RuntimeException()).when(teamDao).updateTeam(mockTeam);
        teamService.updateTeam(mockTeam);
    }

    @Test
    public void getTeamOfClubByCategoryTest(){

        List<Team> teams = new ArrayList<Team>();
        teams.add(team1);
        teams.add(team2);

		when(mockManager.getTeams()).thenReturn(teams);

		Team returnedTeam = teamService.getTeamOfClubByCategory(Category.U13, mockManager);
        assertNotNull(returnedTeam.getCategory());
        assertEquals(returnedTeam.getCategory(),Category.U13);
    }

    @Test
    public void getAllTeamsOfClubTest(){
        List<Team> teams = new ArrayList<Team>();
        teams.add(team1);
        teams.add(team2);

		when(mockManager.getTeams()).thenReturn(teams);

		List<Team> returnedTeams = teamService.getAllTeamsOfClub(mockManager);
        assertNotNull(returnedTeams);
        assertEquals(returnedTeams, teams);
    }

    @Test(expected = RuntimeException.class)
    public void changeJerseyNumberTest(){
        player1.setId(1L);
        playerInfo1.setJerseyNumber(11);
        playerInfo1.setTeam(mockTeam);
        playerInfo1.setPlayer(player1);

        player2.setId(2L);
        playerInfo2.setJerseyNumber(15);
        playerInfo2.setTeam(mockTeam);
        playerInfo2.setPlayer(player2);

        List<PlayerInfo> playersInfos = new ArrayList<PlayerInfo>();
        playersInfos.add(playerInfo1);
        playersInfos.add(playerInfo2);

        doThrow(new RuntimeException()).when(playerInfoDao).changeJerseyNumber(mockTeam, player1, 20);
        teamService.changeJerseyNumber(player1, mockTeam, 20);

        assertEquals(playerInfo1.getJerseyNumber(),20);
    }

	@Test(expected = RuntimeException.class)
	public void removePlayerFromTeamTest() {
	        doThrow(new RuntimeException()).when(playerInfoDao).deletePlayerInfo(mockPlayerInfo);
		teamService.removePlayerFromTeam(mockPlayerInfo);
	    }

    @Test(expected = DataIntegrityViolationException.class)
    public void assignPlayerToTeamTest(){
        player1.setDateOfBirth(LocalDate.parse("2000-06-15"));
        team1.setCategory(Category.U13);
        teamService.assignPlayerToTeam(player1,team1,10);
    }

    @Test
    public void isCategoryOfTeamUnique(){
        List<Category> categories = new ArrayList<>();
        categories.add(Category.U13);
        categories.add(Category.U17);
        team1.setManager(mockManager);
        when(managerDao.getCategoriesOfTeams(mockManager)).thenReturn(categories);
        boolean result = teamService.isCategoryOfTeamUnique(team1);
        assertEquals(false, result);
        team1.setCategory(Category.MEN);
        result = teamService.isCategoryOfTeamUnique(team1);
        assertEquals(true, result);
    }
}
