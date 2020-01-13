package uni.fmi.masters.beans;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"tftStats"})
public class UserBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column (name = "username", nullable = false, unique = true, length = 20)
	private String username;
	
	@Column (name = "password", nullable = false, length = 20)
	private String password;
	
	@Column (name = "email", length = 50)
	private String email;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<TFTStatsBean> tftStats;
	
	public UserBean() {
	}

	public UserBean(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<TFTStatsBean> getTftStats() {
		return tftStats;
	}

	public void setTftStats(List<TFTStatsBean> tftStats) {
		this.tftStats = tftStats;
	}
	
	
	
}
