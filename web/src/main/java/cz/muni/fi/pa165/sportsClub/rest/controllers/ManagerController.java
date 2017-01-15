package cz.muni.fi.pa165.sportsClub.rest.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cz.muni.fi.pa165.sportsClub.dto.AuthenticationDto;
import cz.muni.fi.pa165.sportsClub.dto.ManagerCreateDto;
import cz.muni.fi.pa165.sportsClub.dto.ManagerDto;
import cz.muni.fi.pa165.sportsClub.dto.ManagerWithTokenDto;
import cz.muni.fi.pa165.sportsClub.dto.PlayerDto;
import cz.muni.fi.pa165.sportsClub.dto.TeamDto;
import cz.muni.fi.pa165.sportsClub.exception.TokenValidationException;
import cz.muni.fi.pa165.sportsClub.facade.ManagerFacade;
import cz.muni.fi.pa165.sportsClub.service.AuthUtils;


@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Inject
    private ManagerFacade managerFacade;

    @RequestMapping(path = "/{managerId}/teams", method = RequestMethod.GET)
	public final List<TeamDto> getTeamsOfManager(@PathVariable("managerId") long managerId, HttpServletRequest hsr)
			throws TokenValidationException {
		String token = (hsr.getHeader("Authorization")).split(" ")[1];
		AuthUtils.authorizeRestCall(token, "admin", "manager");
        return managerFacade.getTeamsOfManager(managerId);
    }

    @RequestMapping(path = "/{managerId}/freeTeams", method = RequestMethod.GET)
	public final List<TeamDto> getNotCreatedTeamsOfManager(@PathVariable("managerId") long managerId,
			HttpServletRequest hsr) throws TokenValidationException {
		String token = (hsr.getHeader("Authorization")).split(" ")[1];
		AuthUtils.authorizeRestCall(token, "admin", "manager");
		return managerFacade.notCreatedTeams(managerId);
    }

    @RequestMapping(path = "/{managerId}/freePlayers", method = RequestMethod.GET)
	public final List<PlayerDto> getFreePlayers(@PathVariable("managerId") long managerId, HttpServletRequest hsr)
			throws TokenValidationException {
		String token = (hsr.getHeader("Authorization")).split(" ")[1];
		AuthUtils.authorizeRestCall(token, "admin", "manager");
		ManagerDto managerDto = managerFacade.getManagerById(managerId);
		return managerFacade.getFreePlayers(managerDto.getId());
    }

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ManagerDto create(@Valid @RequestBody ManagerCreateDto manager, HttpServletRequest hsr)
			throws TokenValidationException {
		String token = (hsr.getHeader("Authorization")).split(" ")[1];
		AuthUtils.authorizeRestCall(token, "admin");
		manager.setRole("manager");
		Long id = managerFacade.createManager(manager);
		manager.setId(id);
		return manager;
	}

	/**
	 * returns null if no manager with corresponding credentials is found
	 * 
	 * @param credentials
	 * @return
	 */
	@RequestMapping(path = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ManagerWithTokenDto login(@RequestBody AuthenticationDto credentials) {
		return managerFacade.login(credentials);
	}

	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ManagerDto update(@Valid @RequestBody ManagerDto manager, HttpServletRequest hsr)
			throws TokenValidationException {
		String token = (hsr.getHeader("Authorization")).split(" ")[1];
		AuthUtils.authorizeRestCall(token, "admin", "manager");
		managerFacade.updateManager(manager);
		return manager;
    }

    @RequestMapping(path = "/{managerId}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("managerId") long id, HttpServletRequest hsr) throws TokenValidationException {
		String token = (hsr.getHeader("Authorization")).split(" ")[1];
		AuthUtils.authorizeRestCall(token, "admin");
		managerFacade.deleteManager(id);
    }

    @RequestMapping(path = "/{managerId}", method = RequestMethod.GET)
	public ManagerDto findById(@PathVariable("managerId") long id, HttpServletRequest hsr) throws TokenValidationException {
		String token = (hsr.getHeader("Authorization")).split(" ")[1];
		AuthUtils.authorizeRestCall(token, "admin");
		return managerFacade.getManagerById(id);
    }

	@RequestMapping(method = RequestMethod.GET)
	public List<ManagerDto> findAll(HttpServletRequest hsr) throws TokenValidationException {
		String token = (hsr.getHeader("Authorization")).split(" ")[1];
		AuthUtils.authorizeRestCall(token, "admin");
        return managerFacade.getAllManagers();
    }
}
