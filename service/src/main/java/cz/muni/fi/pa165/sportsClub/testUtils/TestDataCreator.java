package cz.muni.fi.pa165.sportsClub.testUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cz.muni.fi.pa165.sportsClub.enums.Category;
import cz.muni.fi.pa165.sportsClub.pojo.Manager;
import cz.muni.fi.pa165.sportsClub.pojo.Player;
import cz.muni.fi.pa165.sportsClub.pojo.PlayerInfo;
import cz.muni.fi.pa165.sportsClub.pojo.Team;

/**
 * This class contains some method for generating test data and script to fill
 * db with it.
 * 
 * @author Jan Tomasek
 *
 */
public class TestDataCreator {

	private static Long nextId = 10000L;

	/**
	 * Creates .sql script with test data in src/test/resources folder.
	 * 
	 * @param countOfManagers
	 * @param countOfPlayers
	 * @throws IOException
	 */
	public static void createTestDataScript(int countOfManagers, int countOfPlayers) throws IOException {
		StringBuilder script = new StringBuilder();
		script.append(
				"--This script has been created by createTestDataScript() method in TestDataUtils class."
						+ System.lineSeparator());
		createTestAdmin(script);
		List<Manager> managers = createTestManagers(script, countOfManagers);
		List<Team> teams = createTestTeams(script, managers);
		List<Player> players = createTestPlayers(script, countOfPlayers, managers);
		createPlayerInfos(script, managers, teams, players);
		File file = new File("src/test/resources/testInit.sql");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(script.toString());
		}
	}

	public static Manager createTestAdmin(StringBuilder script) {
		Manager admin = new Manager();
		admin.setClubName("all");
		admin.setId(666666L);
		admin.setEmail("admin@mail.com");
		admin.setFirstName("adminfname");
		admin.setLastName("adminlname");
		admin.setMobile("+666666");
		admin.setPassword("adminpassword");
		admin.setRole("admin");
		script.append(admin.toInsertStatement());
		return admin;
	}

	public static List<Manager> createTestManagers(StringBuilder script, int countOfManagers) {
		Manager m;
		List<Manager> managers = new ArrayList<Manager>();
		for (int i = 0; i < countOfManagers; i++) {
			m = new Manager();
			m.setClubName(i + "clubName");
			m.setId(nextId);
			nextId++;
			m.setEmail(i + "man@mail.com");
			m.setFirstName(i + "fname");
			m.setLastName(i + "lname");
			m.setMobile("+" + i + "");
			m.setPassword(i + "password");
			m.setRole("manager");
			managers.add(m);
			script.append(m.toInsertStatement());
		}
		return managers;
	}

	public static List<Team> createTestTeams(StringBuilder script, List<Manager> managers) {
		Team t;
		List<Team> teams = new ArrayList<Team>();
		for (Manager manager : managers) {
			for (int i = 0; i < Category.values().length; i++) {
				t = new Team();
				t.setCategory(Category.values()[i]);
				t.setManager(manager);
				t.setId(nextId);
				nextId++;
				script.append(t.toInsertStatement());
				teams.add(t);
			}
		}
		return teams;
	}

	public static List<Player> createTestPlayers(StringBuilder script, int countOfPlayers, List<Manager> managers) {
		int countOfManagers = managers.size();
		Player p;
		Manager c;
		List<Player> players = new ArrayList<Player>();
		for (int i = 0; i < countOfPlayers; i++) {
			p = new Player();
			p.setEmail(i + "player@mail.com");
			p.setFirstName(i + "fname");
			p.setLastName(i + "lname");
			p.setMobile("+" + i + "");
			p.setHeight(120);
			p.setWeight(120);
			p.setId(nextId);
			nextId++;
			c = managers.get(i % countOfManagers);
			p.setManager(c);
			p.setDateOfBirth(categoryToLocalDate(Category.values()[i % Category.values().length]));
			p.setManager(c);
			script.append(p.toInsertStatement());
			players.add(p);
		}
		return players;
	}

	public static List<PlayerInfo> createPlayerInfos(StringBuilder script, List<Manager> managers, List<Team> allTeams,
			List<Player> allPlayers) {
		List<PlayerInfo> pis = new ArrayList<>();
		int countOfManagers = managers.size();
		Player p;
		PlayerInfo pi;
		Team t;
		List<Player> playersOfManager;
		List<Team> teamsOfManager;
		for (int i = 0; i < countOfManagers; i++) {
			Long managerId = managers.get(i).getId();
			playersOfManager = allPlayers.stream().filter(player -> player.getManager().getId() == managerId)
					.collect(Collectors.toList());
			teamsOfManager = allTeams.stream().filter(team -> team.getManager().getId() == managerId)
					.collect(Collectors.toList());
			for (int j = 0; j < playersOfManager.size(); j++) {
				p = playersOfManager.get(j);
				t = findSuitableTeamBasedOnDOB(p, teamsOfManager);
				pi = new PlayerInfo();
				pi.setJerseyNumber(j);
				pi.setPlayer(p);
				pi.setTeam(t);
				pi.setId(nextId);
				nextId++;
				script.append(pi.toInsertStatement());
				pis.add(pi);
			}
		}
		return pis;
	}

	private static LocalDate categoryToLocalDate(Category c) {
		int ageLimit = c.getUpperAgeLimit();
		int yearsBeforeNow;
		if (ageLimit == Integer.MAX_VALUE)
			yearsBeforeNow = 50;
		else
			yearsBeforeNow = ageLimit;
		return LocalDate.of(Year.now().getValue() - yearsBeforeNow + 1, 1, 1);
	}

	private static Team findSuitableTeamBasedOnDOB(Player p, List<Team> teams) {
		int suitableLimit = Integer.MAX_VALUE;
		Team suitableTeam = null;
		LocalDate now = LocalDate.now();
		int ageLimit;

		for (Team t : teams) {
			ageLimit = t.getCategory().getUpperAgeLimit();
			if (ageLimit == Integer.MAX_VALUE || now.minusYears(ageLimit).isBefore(p.getDateOfBirth())) {
				if (ageLimit <= suitableLimit) {
					suitableLimit = ageLimit;
					suitableTeam = t;
				}
			}
		}
		return suitableTeam;
	}
}
