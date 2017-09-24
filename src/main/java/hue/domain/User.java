package hue.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false, length=20)
	private String userId;
	
	@Column(nullable=false, length=20)
	private String password;
	
	@Column(nullable=false, length=20)
	private String name;
	
	@Column(nullable=true, length=20)
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean matchPassword(String password) {
		return this.password.equals(password);
	}
	
	public boolean update(User user) {
		if(matchPassword(user.getPassword())) {
			name = user.getName();
			email = user.getEmail();
			return true;
		}
		return false;
	}

	public boolean matchId(long id) {
		return this.id.equals(id);
	}

}
