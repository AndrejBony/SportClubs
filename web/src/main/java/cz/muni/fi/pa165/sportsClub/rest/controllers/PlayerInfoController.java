package cz.muni.fi.pa165.sportsClub.rest.controllers;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cz.muni.fi.pa165.sportsClub.dto.PlayerDto;
import cz.muni.fi.pa165.sportsClub.dto.PlayerOfTeamDto;
import cz.muni.fi.pa165.sportsClub.exception.TokenValidationException;
import cz.muni.fi.pa165.sportsClub.facade.TeamFacade;
import cz.muni.fi.pa165.sportsClub.service.AuthUtils;

@RestController
@RequestMapping("/playerInfo")
public class PlayerInfoController {
	@Inject
	private TeamFacade teamFacade;

	private static final String[] AUTHORIZED_ROLES = new String[] { "admin", "manager" };

	@RequestMapping(value = "/{playerInfoId}", method = RequestMethod.DELETE, produces = MediaType.TEXT_PLAIN_VALUE)
	public final void removePlayerFromRoster(@PathVariable("playerInfoId") long playerInfoId,
			HttpServletRequest hsr) throws TokenValidationException {
		String token = (hsr.getHeader("Authorization")).split(" ")[1];
		AuthUtils.authorizeRestCall(token, AUTHORIZED_ROLES);
		teamFacade.removePlayerFromTeam(playerInfoId);
	}

	@RequestMapping(value = "/{teamId}/{playerId}/{jerseyNumber}", method = RequestMethod.POST)
	public final PlayerOfTeamDto addExistingPlayerOnRoster(@PathVariable("teamId") long teamId,
			@PathVariable("playerId") long playerId, @PathVariable("jerseyNumber") int jerseyNumber,
			HttpServletRequest hsr) throws TokenValidationException {
		String token = (hsr.getHeader("Authorization")).split(" ")[1];
		AuthUtils.authorizeRestCall(token, AUTHORIZED_ROLES);
		return teamFacade.assignExistingPlayerToTeam(playerId, teamId, jerseyNumber);
	}

	@RequestMapping(value = "/{teamId}/{jerseyNumber}", method = RequestMethod.POST)
	public final PlayerOfTeamDto addNewPlayerOnRoster(@PathVariable("teamId") long teamId,
			@PathVariable("jerseyNumber") int jerseyNumber, @Valid @RequestBody PlayerDto player,
			HttpServletRequest hsr) throws TokenValidationException {
		String token = (hsr.getHeader("Authorization")).split(" ")[1];
		AuthUtils.authorizeRestCall(token, AUTHORIZED_ROLES);
		return teamFacade.assignNewPlayerToTeam(player, teamId, jerseyNumber);
	}

	@RequestMapping(value = "/{playerId}/{teamId}/{jerseyNumber}", method = RequestMethod.PUT)
	public final void changeJerseyNumber(@PathVariable("playerId") long playerId,
										 @PathVariable("teamId") long teamId,
										 @PathVariable("jerseyNumber") int jerseyNumber,
										 HttpServletRequest hsr) throws TokenValidationException {
		String token = (hsr.getHeader("Authorization")).split(" ")[1];
		AuthUtils.authorizeRestCall(token, AUTHORIZED_ROLES);
		teamFacade.changeJerseyNumber(playerId, teamId, jerseyNumber);
	}
}
