package cz.muni.fi.pa165.sportsClub.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import cz.muni.fi.pa165.sportsClub.dao.ManagerDao;
import cz.muni.fi.pa165.sportsClub.enums.Category;
import cz.muni.fi.pa165.sportsClub.pojo.Manager;
import cz.muni.fi.pa165.sportsClub.pojo.Player;
import cz.muni.fi.pa165.sportsClub.pojo.Team;

@Service
public class ManagerServiceImpl implements ManagerService {
	
	@Inject
	private ManagerDao managerDao;

	@Override
	public Manager createManager(Manager m) {
		m.setPassword(AuthUtils.computeHash(m.getPassword()));
		managerDao.createManager(m);
		return m;
	}

	@Override
	public void updateManager(Manager m) {
		managerDao.updateManager(m);
	}

	@Override
	public void deleteManager(Manager m) {
		managerDao.deleteManager(m);
	}

	@Override
	public Manager getManagerById(Long managerId) {
		return managerDao.getManagerById(managerId);
	}

	@Override
	public Manager getManagerByEmail(String email) {
		return managerDao.getManagerByEmail(email);
	}

	@Override
	public Manager getManagerByClubName(String clubName) {
		return managerDao.getManagerByClubName(clubName);
	}

	@Override
	public List<Player> getFreePlayersOfClub(Manager m) {
		return managerDao.getFreePlayers(m);
	}

	@Override
	public List<Manager> getAllManagers() {
		return managerDao.getAllManagers();
	}

	@Override
	public List<Team> getNotCreatedTeamsOfManager(Manager m) {
		List<Category> categoriesOfCreatedTeams =  managerDao.getCategoriesOfTeams(m);
		List<Category> allCategories = new ArrayList<Category>(Arrays.asList(Category.values()));
		allCategories.removeAll(categoriesOfCreatedTeams);
		List<Team>notAlreadyCreatedTeams = new ArrayList<>();
		for(Category category : allCategories){
			Team team = new Team();
			team.setCategory(category);
			team.setManager(m);
			notAlreadyCreatedTeams.add(team);
		}
		return notAlreadyCreatedTeams;
	}

	@Override
	public Manager authenticate(String email, String password) {
		Manager manager = null;
		manager = managerDao.getManagerByEmail(email);
		if (manager == null) {
			// authentication failed - wrong email
			return null;
		}
		String accessingUserHash = AuthUtils.computeHash(password);
		if (!accessingUserHash.equals(manager.getPassword())) {
			// authentication failed - wrong password
			return null;
		}
		return manager;
	}
}
