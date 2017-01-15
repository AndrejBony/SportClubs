package cz.muni.fi.pa165.sportsClub.service.facade;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.sportsClub.dto.ManagerDto;
import cz.muni.fi.pa165.sportsClub.service.ManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.pa165.sportsClub.dto.PlayerDto;
import cz.muni.fi.pa165.sportsClub.dto.TeamDto;
import cz.muni.fi.pa165.sportsClub.dto.TeamOfPlayerDto;
import cz.muni.fi.pa165.sportsClub.facade.PlayerFacade;
import cz.muni.fi.pa165.sportsClub.pojo.Manager;
import cz.muni.fi.pa165.sportsClub.pojo.Player;
import cz.muni.fi.pa165.sportsClub.pojo.PlayerInfo;
import cz.muni.fi.pa165.sportsClub.service.BeanMappingService;
import cz.muni.fi.pa165.sportsClub.service.PlayerService;

@Transactional
@Service
public class PlayerFacadeImpl implements PlayerFacade {

	@Inject
	PlayerService playerService;

	@Inject
	ManagerService managerService;

	@Inject
	private BeanMappingService beanMappingService;

	@Override
	public PlayerDto createPlayer(PlayerDto p, Long managerId) {
		Manager manager = managerService.getManagerById(managerId);
		ManagerDto managerDto = beanMappingService.mapTo(manager, ManagerDto.class);
		Player playerEntity = beanMappingService.mapTo(p, Player.class);
		playerEntity.setManager(manager);
		playerService.createPlayer(playerEntity);
		p.setId(playerEntity.getId());
		p.setManager(managerDto);
		return p;
	}

	@Override
	public void updatePlayer(PlayerDto p) {
		playerService.updatePlayer(beanMappingService.mapTo(p, Player.class));
	}

	@Override
	public void deletePlayer(Long id) {
		playerService.deletePlayer(new Player(id));
	}

	@Override
	public PlayerDto getPlayerById(Long playerId) {
		Player p = playerService.getPlayerById(playerId);
		return beanMappingService.mapTo(p, PlayerDto.class);
	}

	@Override
	public PlayerDto getPlayerByEmail(String email) {
		Player p = playerService.getPlayerByEmail(email);
		if (p == null)
			return null;
		return beanMappingService.mapTo(p, PlayerDto.class);
	}

	@Override
	public List<TeamOfPlayerDto> getTeamsOfPlayer(Long playerId) {
		List<TeamOfPlayerDto> teams = new ArrayList<TeamOfPlayerDto>();
		List<PlayerInfo> infos = playerService.getPlayerInfos(playerId);
		TeamOfPlayerDto teamOfPlayer;
		for (PlayerInfo playerInfo : infos) {
			teamOfPlayer = new TeamOfPlayerDto();
			teamOfPlayer.setJerseyNumber(playerInfo.getJerseyNumber());
			teamOfPlayer.setPlayerOlderThanTeamLimit(playerInfo.isPlayerOlderThanTeamLimit());
			teamOfPlayer.setPlayerInfoId(playerInfo.getId());
			teamOfPlayer.setTeam(beanMappingService.mapTo(playerInfo.getTeam(), TeamDto.class));
			teams.add(teamOfPlayer);
		}
		return teams;
	}

	@Override
	public List<PlayerDto> getAllPlayersOfClub(Long clubId) {
		return beanMappingService.mapTo(playerService.getAllPlayersOfClub(new Manager(clubId)),
				PlayerDto.class);
	}
}
