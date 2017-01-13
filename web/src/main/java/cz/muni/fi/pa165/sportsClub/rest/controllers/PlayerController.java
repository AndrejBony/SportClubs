package cz.muni.fi.pa165.sportsClub.rest.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import cz.muni.fi.pa165.sportsClub.dto.TeamOfPlayerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cz.muni.fi.pa165.sportsClub.dto.PlayerDto;
import cz.muni.fi.pa165.sportsClub.exception.TokenValidationException;
import cz.muni.fi.pa165.sportsClub.facade.PlayerFacade;
import cz.muni.fi.pa165.sportsClub.service.AuthUtils;


@RestController
@RequestMapping("/player")
public class PlayerController {

    @Inject
    private PlayerFacade playerFacade;

	private static final String[] AUTHORIZED_ROLES = new String[] { "admin", "manager" };

	@RequestMapping(value = "/{playerId}", method = RequestMethod.DELETE, produces = MediaType.TEXT_PLAIN_VALUE)
	public final void delete(@PathVariable("playerId") long playerId, HttpServletRequest hsr)
			throws TokenValidationException {
		String token = (hsr.getHeader("Authorization")).split(" ")[1];
		AuthUtils.authorizeRestCall(token, AUTHORIZED_ROLES);
        playerFacade.deletePlayer(playerId);
    }

    @RequestMapping(method = RequestMethod.POST)
	public PlayerDto create(@Valid @RequestBody PlayerDto player, HttpServletRequest hsr)
			throws TokenValidationException {
		String token = (hsr.getHeader("Authorization")).split(" ")[1];
		AuthUtils.authorizeRestCall(token, AUTHORIZED_ROLES);
        playerFacade.createPlayer(player);
        return player;
    }

    @RequestMapping(method = RequestMethod.PUT)
	public void update(@Valid @RequestBody PlayerDto player, HttpServletRequest hsr) throws TokenValidationException {
		String token = (hsr.getHeader("Authorization")).split(" ")[1];
		AuthUtils.authorizeRestCall(token, AUTHORIZED_ROLES);
        playerFacade.updatePlayer(player);
    }

    @RequestMapping(path = "/{playerId}", method = RequestMethod.GET)
	public PlayerDto findById(@PathVariable("playerId") long id, HttpServletRequest hsr) throws TokenValidationException {
		String token = (hsr.getHeader("Authorization")).split(" ")[1];
		AuthUtils.authorizeRestCall(token, AUTHORIZED_ROLES);
        return playerFacade.getPlayerById(id);
    }

    @RequestMapping(path = "/findall/{managerId}", method = RequestMethod.GET)
	public List<PlayerDto> findAllByManagerId(@PathVariable("managerId") long id, HttpServletRequest hsr)
			throws TokenValidationException {
		String token = (hsr.getHeader("Authorization")).split(" ")[1];
		AuthUtils.authorizeRestCall(token, AUTHORIZED_ROLES);
        return playerFacade.getAllPlayersOfClub(id);
    }

	@RequestMapping(path = "/{playerId}/teams", method = RequestMethod.GET)
	public List<TeamOfPlayerDto> getTeamsOfPlayer(@PathVariable("playerId") long id, HttpServletRequest hsr)
			throws TokenValidationException {
		String token = (hsr.getHeader("Authorization")).split(" ")[1];
		AuthUtils.authorizeRestCall(token, AUTHORIZED_ROLES);
		return playerFacade.getTeamsOfPlayer(id);
	}
}
