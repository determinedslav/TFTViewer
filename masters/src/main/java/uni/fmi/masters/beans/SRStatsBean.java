package uni.fmi.masters.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "srstats")
public class SRStatsBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column (name = "summonerRegion", nullable = false, length = 30)
	private String summonerRegion;
	
	@Column (name = "summonerName", nullable = false, length = 30)
	private String summonerName;
	
	@Column (name = "rank", nullable = false, length = 20)
	private String rank;
	
	@Column (name = "division", nullable = false, length = 10)
	private String division;
	
	@Column (name = "wins", nullable = false, length = 10)
	private int wins;
	
	@Column (name = "lp", nullable = false, length = 10)
	private int lp;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private UserBean user;
	
	public SRStatsBean() {
		
	}
	
	public SRStatsBean(String summonerRegion, String summonerName, String rank, String division, int wins, int lp, UserBean user) {
		this.summonerRegion = summonerRegion;
		this.summonerName = summonerName;
		this.rank = rank;
		this.division = division;
		this.wins = wins;
		this.lp = lp;
		this.user = user;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSummonerRegion() {
		return summonerRegion;
	}

	public void setSummonerRegion(String summonerRegion) {
		this.summonerRegion = summonerRegion;
	}

	public String getSummonerName() {
		return summonerName;
	}

	public void setSummonerName(String summonerName) {
		this.summonerName = summonerName;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLp() {
		return lp;
	}

	public void setLp(int lp) {
		this.lp = lp;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}
	
}
