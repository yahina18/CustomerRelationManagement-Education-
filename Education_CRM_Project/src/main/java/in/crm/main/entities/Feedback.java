package in.crm.main.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Feedback
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String userName;
	
	@Column
	private String userEmail;
	
	@Column(length = 3000)
	private String userFeedback;
	
	@Column
	private String dateOfFeedback;
	
	@Column
	private String timeOfFeedback;
	
	@Column
	private String readStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserFeedback() {
		return userFeedback;
	}

	public void setUserFeedback(String userFeedback) {
		this.userFeedback = userFeedback;
	}

	public String getDateOfFeedback() {
		return dateOfFeedback;
	}

	public void setDateOfFeedback(String dateOfFeedback) {
		this.dateOfFeedback = dateOfFeedback;
	}

	public String getTimeOfFeedback() {
		return timeOfFeedback;
	}

	public void setTimeOfFeedback(String timeOfFeedback) {
		this.timeOfFeedback = timeOfFeedback;
	}

	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}
}
