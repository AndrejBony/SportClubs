package cz.muni.fi.pa165.sportsClub.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import cz.muni.fi.pa165.sportsClub.dao.ManagerDao;
import cz.muni.fi.pa165.sportsClub.dao.PlayerDao;
import cz.muni.fi.pa165.sportsClub.pojo.Manager;
import cz.muni.fi.pa165.sportsClub.pojo.Player;
import cz.muni.fi.pa165.sportsClub.pojo.PlayerInfo;

/**
 * Represents service layer of player
 * 
 * @author Andrej Bonis 410433
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    @Inject
    private PlayerDao playerDao;
    
    @Inject
	private ManagerDao managerDao;

	@Override
	public void createPlayer(Player p) {
		playerDao.createPlayer(p);
    }

	@Override
	public void updatePlayer(Player p) {
		playerDao.updatePlayer(p);
    }

	@Override
	public void deletePlayer(Player p) {
        playerDao.deletePlayer(p);
    }

	@Override
	public Player getPlayerById(Long playerId) {
        return playerDao.getPlayerById(playerId);
    }

	@Override
	public Player getPlayerByEmail(String email) {
        return playerDao.getPlayerByEmail(email);
    }

	@Override
	public List<Player> getAllPlayersOfClub(Manager m) {
		List<Player> players = managerDao.getManagerById(m.getId()).getPlayers();
        players.size();
        return players;
    }

	@Override
	public List<PlayerInfo> getPlayerInfos(Long playerId) {
        Player player = playerDao.getPlayerById(playerId);
        List<PlayerInfo> playerInfos = player.getPlayerInfos();
        return playerInfos;
    }
}
