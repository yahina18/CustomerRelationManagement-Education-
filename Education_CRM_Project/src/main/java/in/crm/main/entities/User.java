package in.crm.main.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	@Pattern(regexp = "^[a-z,A-Z ]{5,25}$",message = "Invalid name pattern")
	private String name;
	
	@Column
	@Pattern(regexp = "^[a-zA-Z0-9._%±]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$",message = "Invalid email pattern")
	private String email;
	
	@Column
	@Pattern(regexp = "^[a-z,A-Z,0-9]{5,25}$",message = "Invalid password pattern")
	private String password;
	
	@Column
	@Pattern(regexp = "^[0-9]{10}$",message = "Invalid phoneNo pattern")
	private String phoneNo;
	
	@Column
	@Pattern(regexp = "^[a-z,A-Z]{3,25}$",message = "Invalid city pattern")
	private String city;

	@Column
	private boolean banStatus;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public boolean isBanStatus() {
		return banStatus;
	}

	public void setBanStatus(boolean banStatus) {
		this.banStatus = banStatus;
	}
	

}
