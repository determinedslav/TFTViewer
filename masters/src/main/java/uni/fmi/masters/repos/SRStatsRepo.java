package uni.fmi.masters.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uni.fmi.masters.beans.SRStatsBean;
import uni.fmi.masters.beans.UserBean;

public interface SRStatsRepo extends JpaRepository<SRStatsBean ,Integer> {

	SRStatsBean findSRStatsBySummonerRegionAndSummonerNameAndUser (String summonerRegion, String summonerName, UserBean user);
	
	List<SRStatsBean> findAllByUser (UserBean user);
}
