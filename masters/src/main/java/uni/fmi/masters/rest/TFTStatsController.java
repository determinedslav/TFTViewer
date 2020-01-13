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

import uni.fmi.masters.beans.TFTStatsBean;
import uni.fmi.masters.beans.UserBean;
import uni.fmi.masters.repos.TFTStatsRepo;

@RestController
public class TFTStatsController {
	TFTStatsRepo tftstatsRepo;
	
	public TFTStatsController (TFTStatsRepo tftstatsRepo) {
		this.tftstatsRepo = tftstatsRepo;
	}
	
	@PostMapping (path = "tftstats/add")
	public TFTStatsBean addStats(@RequestParam(value = "summonerRegion") String summonerRegion, @RequestParam(value = "summonerName") String summonerName, @RequestParam(value = "rank") String rank, @RequestParam(value = "division") String division, @RequestParam(value = "wins") int wins, @RequestParam(value = "lp") int lp, HttpSession session) {
		
		UserBean user = (UserBean) session.getAttribute("user");
		
		if(user != null) {
			TFTStatsBean tftstats = new TFTStatsBean(summonerRegion, summonerName, rank, division, wins, lp, user);
			
			TFTStatsBean tftstatsToCompare = tftstatsRepo.findTFTStatsBySummonerRegionAndSummonerNameAndUser(summonerRegion, summonerName, user);
			
			if (tftstatsToCompare == null) {				
				tftstatsRepo.saveAndFlush(tftstats);
				return tftstats;
			} else {
				return new TFTStatsBean();
			}
			
		} else {
			return null;
		}
	}
	
	@PutMapping (path = "tftstats/update")
	public TFTStatsBean updateStats (@RequestParam(value = "id") int id, @RequestParam(value = "summonerRegion") String summonerRegion, @RequestParam(value = "summonerName") String summonerName, @RequestParam(value = "rank") String rank, @RequestParam(value = "division") String division, @RequestParam(value = "wins") int wins, @RequestParam(value = "lp") int lp, HttpSession session) {
		
		UserBean user = (UserBean)session.getAttribute("user");
		
		Optional<TFTStatsBean> optionalTFTStats = tftstatsRepo.findById(id);
		
		if(optionalTFTStats.isPresent()) {
			TFTStatsBean tftstats = optionalTFTStats.get();
			if(tftstats.getUser().getId() == user.getId()) {
				
				tftstats.setSummonerRegion(summonerRegion);
				tftstats.setSummonerName(summonerName);
				tftstats.setRank(rank);
				tftstats.setDivision(division);
				tftstats.setWins(wins);
				tftstats.setLp(lp);
				
				tftstatsRepo.save(tftstats);
				
				return tftstats;
			} else {
				return new TFTStatsBean();
			}
		} else {
			return null;
		}
	}
	
	@DeleteMapping (path = "tftstats/delete")
	public int deleteStats (@RequestParam(value = "id") int id, HttpSession session) {
		
		UserBean user = (UserBean)session.getAttribute("user");
		
		Optional<TFTStatsBean> optionalTFTStats = tftstatsRepo.findById(id);
		
		if(optionalTFTStats.isPresent()) {
			TFTStatsBean tftstats = optionalTFTStats.get();
			if(tftstats.getUser().getId() == user.getId()) {
				tftstatsRepo.delete(tftstats);
				return 0;
			} else {
				return 1;
			}
		} else {
			return 2;
		}
	}
	
	@GetMapping(path = "tftstats/get")
	public List<TFTStatsBean> getAllComments(HttpSession session){
		UserBean user = (UserBean) session.getAttribute("user");
		return tftstatsRepo.findAllByUser(user);
	}
}
