package uni.fmi.masters.rest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uni.fmi.masters.beans.UserBean;
import uni.fmi.masters.repos.UserRepo;

@RestController
public class UserController {
	
	private UserRepo userRepo;
	
	public UserController (UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	@PostMapping(path = "user/login")
	public int login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password, HttpSession session) {
		
		UserBean user = userRepo.findUserByUsernameAndPassword(username, hash(password));
		
		if (user != null) {
			session.setAttribute("user", user);
			return 0;
		} else {
			return 1;
		}
	}
	
	@PostMapping(path = "user/register")
	public int register(@RequestParam(value = "user") String username, @RequestParam(value = "pass") String password, @RequestParam(value = "repeatPass") String repeatPassword, @RequestParam(value = "email") String email) {
		
		if(username.length() < 4 || username.length() > 20) {
			return 5;
		} else if (password.length() < 6 || password.length() > 20) {
			return 4;
		} else {		
			if(password.equals(repeatPassword)) {
			
				UserBean user = new UserBean(username, hash(password), email);
				UserBean usernameToCompare = userRepo.findUserByUsername(username);
				UserBean emailToCompare = userRepo.findUserByEmail(email);
			
				if (emailToCompare != null) {					
					return 2;
				} else if (usernameToCompare != null){
					return 1;
				} else {
					userRepo.saveAndFlush(user);
					return 0;
				}		
			} else {
			return 3;
			}
		}
	}
	
	@PostMapping (path = "user/logout")
	public ResponseEntity<Boolean> logout(HttpSession session){
		
		UserBean user = (UserBean)session.getAttribute("user");
		
		if(user != null) {
			session.invalidate();
			return  new ResponseEntity<>(true,HttpStatus.OK);	
		}
		return  new ResponseEntity<>(HttpStatus.UNAUTHORIZED);	
	}
	
	@GetMapping(path = "user/getId")
	public int getId(HttpSession session){
		
		UserBean user = (UserBean) session.getAttribute("user");
		
		if(user != null) {
			return user.getId();
		} else {
			return 0;	
		}
	}
	
	private String hash(String text) {

		StringBuilder sb = new StringBuilder();

		try {
			
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes());
			byte[] bytes = md.digest();

			for (int i = 0; i < bytes.length; i++) {
				sb.append((char) bytes[i]);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
