package uni.fmi.masters.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uni.fmi.masters.beans.TFTStatsBean;
import uni.fmi.masters.beans.UserBean;

public interface TFTStatsRepo extends JpaRepository<TFTStatsBean ,Integer> {

	TFTStatsBean findTFTStatsBySummonerRegionAndSummonerNameAndUser (String summonerRegion, String summonerName, UserBean user);
	
	List<TFTStatsBean> findAllByUser (UserBean user);
}
