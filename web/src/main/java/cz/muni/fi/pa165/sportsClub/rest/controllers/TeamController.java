package cz.muni.fi.pa165.sportsClub.rest.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cz.muni.fi.pa165.sportsClub.dto.PlayerBasicInfoDto;
import cz.muni.fi.pa165.sportsClub.dto.PlayerOfTeamDto;
import cz.muni.fi.pa165.sportsClub.dto.TeamDto;
import cz.muni.fi.pa165.sportsClub.exception.TokenValidationException;
import cz.muni.fi.pa165.sportsClub.facade.TeamFacade;
import cz.muni.fi.pa165.sportsClub.service.AuthUtils;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Inject
    private TeamFacade teamFacade;

    private static final String[] AUTHORIZED_ROLES = new String[]{"admin", "manager"};

    @RequestMapping(path = "/{teamId}/players", method = RequestMethod.GET)
    public final List<PlayerOfTeamDto> getPlayers(@PathVariable("teamId") long teamId, HttpServletRequest hsr)
            throws TokenValidationException {
        String token = (hsr.getHeader("Authorization")).split(" ")[1];
        AuthUtils.authorizeRestCall(token, AUTHORIZED_ROLES);
        return teamFacade.getPlayersOfTeam(teamId);
    }

    @RequestMapping(value = "/{teamId}", method = RequestMethod.DELETE, produces = MediaType.TEXT_PLAIN_VALUE)
    public final void delete(@PathVariable("teamId") long teamId, HttpServletRequest hsr) throws TokenValidationException {
        String token = (hsr.getHeader("Authorization")).split(" ")[1];
        AuthUtils.authorizeRestCall(token, AUTHORIZED_ROLES);
        teamFacade.deleteTeam(teamId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public final TeamDto create(@RequestBody TeamDto team, HttpServletRequest hsr) throws TokenValidationException {
        String token = (hsr.getHeader("Authorization")).split(" ")[1];
        AuthUtils.authorizeRestCall(token, AUTHORIZED_ROLES);
        Long id = teamFacade.createTeam(team);
        team.setId(id);
        return team;
    }

    @RequestMapping(path = "/{teamId}/suitablePlayers", method = RequestMethod.GET)
    public final List<PlayerBasicInfoDto> findSuitablePlayersForTeam(@PathVariable("teamId") long teamId,
            HttpServletRequest hsr) throws TokenValidationException {
        String token = (hsr.getHeader("Authorization")).split(" ")[1];
        AuthUtils.authorizeRestCall(token, AUTHORIZED_ROLES);
        return teamFacade.findSuitablePlayersForTeam(teamId);
    }

    @RequestMapping(path = "/{playerId}/{teamId}/{jerseyNumber}", method = RequestMethod.GET)
    public final boolean isJerseyNumberUnique(@PathVariable("playerId") long playerId,
            @PathVariable("teamId") long teamId,
            @PathVariable("jerseyNumber") int jerseyNumber,
            HttpServletRequest hsr) throws TokenValidationException {
        String token = (hsr.getHeader("Authorization")).split(" ")[1];
        AuthUtils.authorizeRestCall(token, AUTHORIZED_ROLES);
        return teamFacade.isJerseyNumberUnique(playerId, teamId, jerseyNumber);
    }
}
