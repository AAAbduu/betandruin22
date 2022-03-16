package domain;

import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


@Entity
public class User {
	
	@Id
	private String userName;
	
	
	private String password;
	private String name;
	private String lastName;
	private String email;
	private Date birthDate;
	
	
	 
	public User(String userName, String password, String name, String lastName, String email, Date birthDate) {
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.birthDate = birthDate;
	}
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

}
