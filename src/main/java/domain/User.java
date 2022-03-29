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

    /**
     * Gets username of the User
     * @return username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets username of the User
     * @return username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets password of the User
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password of the User
     * @return password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets name of the User
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * Sets name of the User
     * @return name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Gets last name of the User
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Sets last name of the User
     * @return last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets email of the User
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email of the User
     * @return email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets birth date of the User
     * @return birth date
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Sets birth date of the User
     * @return birth date
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

}