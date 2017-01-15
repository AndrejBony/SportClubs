package cz.muni.fi.pa165.sportsClub.service.facade;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.pa165.sportsClub.dto.AuthenticationDto;
import cz.muni.fi.pa165.sportsClub.dto.ManagerDto;
import cz.muni.fi.pa165.sportsClub.dto.ManagerWithTokenDto;
import cz.muni.fi.pa165.sportsClub.dto.PlayerDto;
import cz.muni.fi.pa165.sportsClub.dto.TeamDto;
import cz.muni.fi.pa165.sportsClub.facade.ManagerFacade;
import cz.muni.fi.pa165.sportsClub.pojo.Manager;
import cz.muni.fi.pa165.sportsClub.pojo.Team;
import cz.muni.fi.pa165.sportsClub.service.AuthUtils;
import cz.muni.fi.pa165.sportsClub.service.BeanMappingService;
import cz.muni.fi.pa165.sportsClub.service.ManagerService;

@Transactional
@Service
public class ManagerFacadeImpl implements ManagerFacade {

    @Inject
	ManagerService managerService;

	@Inject
	private BeanMappingService beanMappingService;

	@Override
	public Long createManager(ManagerDto m) {
		Manager managerEntity = beanMappingService.mapTo(m, Manager.class);
		managerService.createManager(managerEntity);
		m.setId(managerEntity.getId());
		return m.getId();
	}

	@Override
	public void updateManager(ManagerDto m) {
		managerService.updateManager(beanMappingService.mapTo(m, Manager.class));
	}

	@Override
	public void deleteManager(Long id) {
		managerService.deleteManager(new Manager(id));
	}

	@Override
	public ManagerDto getManagerById(Long managerId) {
		Manager m = managerService.getManagerById(managerId);
		if (m == null)
			return null;
		return beanMappingService.mapTo(m, ManagerDto.class);
	}

	@Override
	public List<TeamDto> getTeamsOfManager(Long managerId) {
		Manager m = managerService.getManagerById(managerId);
		if (m == null)
			return null;

		List<Team> teams = m.getTeams();
		return beanMappingService.mapTo(teams, TeamDto.class);
	}

	@Override
	public List<TeamDto> notCreatedTeams(Long managerId) {
		Manager m = managerService.getManagerById(managerId);
		if (m == null)
			return null;

		List<Team>notAlreadyCreatedTeams = managerService.getNotCreatedTeamsOfManager(m);
		return beanMappingService.mapTo(notAlreadyCreatedTeams, TeamDto.class);
	}

	@Override
	public ManagerDto getManagerByEmail(String email) {
		Manager m = managerService.getManagerByEmail(email);
		if (m == null)
			return null;
		return beanMappingService.mapTo(m, ManagerDto.class);
	}

	@Override
	public ManagerDto getManagerByClubName(String clubName) {
		Manager c = managerService.getManagerByClubName(clubName);
		if (c == null)
			return null;
		return beanMappingService.mapTo(c, ManagerDto.class);
	}

	@Override
	public List<ManagerDto> getAllManagers() {
		return beanMappingService.mapTo(managerService.getAllManagers(), ManagerDto.class);
	}

	@Override
	public List<PlayerDto> getFreePlayers(Long managerId) {
		return beanMappingService.mapTo(managerService.getFreePlayersOfClub(new Manager(managerId)),
				PlayerDto.class);
	}

	@Override
	public ManagerWithTokenDto login(AuthenticationDto credentials) {
		Manager m = managerService.authenticate(credentials.getEmail(), credentials.getPassword());
		if (m == null)
			return null;
		return new ManagerWithTokenDto(beanMappingService.mapTo(m, ManagerDto.class),
				AuthUtils.issueNewToken(m.getId(), m.getRole()));
	}
}
