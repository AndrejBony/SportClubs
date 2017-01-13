package cz.muni.fi.pa165.sportsClub.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.muni.fi.pa165.sportsClub.ServiceApplicationContext;
import cz.muni.fi.pa165.sportsClub.dto.ManagerCreateDto;
import cz.muni.fi.pa165.sportsClub.dto.ManagerDto;
import cz.muni.fi.pa165.sportsClub.facade.ManagerFacade;
import cz.muni.fi.pa165.sportsClub.testUtils.ScriptRunner;

/**
 * @author Jan Tomasek
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class ManagerFacadeIntegrationTest {

	@Inject
	private ManagerFacade managerFacade;

	@Inject
	private DataSource ds;

	@Before
	public void setup() throws FileNotFoundException, IOException, SQLException {
		try (Connection conn = ds.getConnection()) {
			ScriptRunner runner = new ScriptRunner(conn, false, false);
			runner.runScript(new BufferedReader(new FileReader("src/test/resources/testInit.sql")));
		}
	}

	@After
	public void tearDown() throws IOException, URISyntaxException, SQLException {
		try (Connection conn = ds.getConnection()) {
			ScriptRunner runner = new ScriptRunner(conn, false, false);
			runner.runScript(new BufferedReader(new FileReader("src/test/resources/delete.sql")));
		}
	}

	@Test
	public void createManager() {
		ManagerCreateDto c = new ManagerCreateDto();
		c.setClubName("newClubName");
		c.setEmail("some@mail.com");
		c.setFirstName("first");
		c.setLastName("last");
		c.setMobile("005686");
		c.setPassword("password");
		c.setRole("manager");
		managerFacade.createManager(c);
		assertNotNull(c.getId());
	}

	@Test
	public void updateManager() {
		ManagerDto c = managerFacade.getManagerById(10000L);
		String cNameBefore = c.getClubName();
		c.setClubName("newName");
		managerFacade.updateManager(c);
		String cNameAfter = managerFacade.getManagerById(managerFacade.getAllManagers().get(0).getId()).getClubName();
		assertNotEquals(cNameBefore, cNameAfter);
	}

	@Test
	public void getManagerById() {
		ManagerDto c = managerFacade.getManagerById(10000L);
		assertNotNull(c);
	}

	@Test
	public void getManagerByClubName() {
		assertNotNull(managerFacade.getManagerByClubName("1clubName"));
	}

	@Test
	public void getAllManagers() {
		assertEquals(2, managerFacade.getAllManagers().size());
	}
}