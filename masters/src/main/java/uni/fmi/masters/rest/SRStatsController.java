package uni.fmi.masters.rest;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uni.fmi.masters.beans.SRStatsBean;
import uni.fmi.masters.beans.UserBean;
import uni.fmi.masters.repos.SRStatsRepo;

@RestController
public class SRStatsController {
	SRStatsRepo srstatsRepo;
	
	public SRStatsController (SRStatsRepo srstatsRepo) {
		this.srstatsRepo = srstatsRepo;
	}
	
	@PostMapping (path = "srstats/add")
	public SRStatsBean addStats(@RequestParam(value = "summonerRegion") String summonerRegion, @RequestParam(value = "summonerName") String summonerName, @RequestParam(value = "rank") String rank, @RequestParam(value = "division") String division, @RequestParam(value = "wins") int wins, @RequestParam(value = "lp") int lp, HttpSession session) {
		
		UserBean user = (UserBean) session.getAttribute("user");
		
		if(user != null) {
			SRStatsBean srstats = new SRStatsBean(summonerRegion, summonerName, rank, division, wins, lp, user);
			
			SRStatsBean srstatsToCompare = srstatsRepo.findSRStatsBySummonerRegionAndSummonerNameAndUser(summonerRegion, summonerName, user);
			
			if (srstatsToCompare == null) {				
				srstatsRepo.saveAndFlush(srstats);
				return srstats;
			} else {
				return new SRStatsBean();
			}
			
		} else {
			return null;
		}
	}
	
	@PutMapping (path = "srstats/update")
	public SRStatsBean updateStats (@RequestParam(value = "id") int id, @RequestParam(value = "summonerRegion") String summonerRegion, @RequestParam(value = "summonerName") String summonerName, @RequestParam(value = "rank") String rank, @RequestParam(value = "division") String division, @RequestParam(value = "wins") int wins, @RequestParam(value = "lp") int lp, HttpSession session) {
		
		UserBean user = (UserBean)session.getAttribute("user");
		
		Optional<SRStatsBean> optionalSRStats = srstatsRepo.findById(id);
		
		if(optionalSRStats.isPresent()) {
			SRStatsBean srstats = optionalSRStats.get();
			if(srstats.getUser().getId() == user.getId()) {
				
				srstats.setSummonerRegion(summonerRegion);
				srstats.setSummonerName(summonerName);
				srstats.setRank(rank);
				srstats.setDivision(division);
				srstats.setWins(wins);
				srstats.setLp(lp);
				
				srstatsRepo.save(srstats);
				
				return srstats;
			} else {
				return new SRStatsBean();
			}
		} else {
			return null;
		}
	}
	
	@DeleteMapping (path = "srstats/delete")
	public int deleteStats (@RequestParam(value = "id") int id, HttpSession session) {
		
		UserBean user = (UserBean)session.getAttribute("user");
		
		Optional<SRStatsBean> optionalSRStats = srstatsRepo.findById(id);
		
		if(optionalSRStats.isPresent()) {
			SRStatsBean srstats = optionalSRStats.get();
			if(srstats.getUser().getId() == user.getId()) {
				srstatsRepo.delete(srstats);
				return 0;
			} else {
				return 1;
			}
		} else {
			return 2;
		}
	}
	
	@GetMapping(path = "srstats/get")
	public List<SRStatsBean> getAllComments(HttpSession session){
		UserBean user = (UserBean) session.getAttribute("user");
		return srstatsRepo.findAllByUser(user);
	}
}
