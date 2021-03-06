package com.nilfactor.activity4.model;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.nilfactor.activity4.util.LogInterceptor;

@Dependent
@Named
@Entity(name = "com.nilfactor.activity4.model.User")
@XmlRootElement
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "user_id"))
@Interceptors(LogInterceptor.class)
public class User implements Serializable {
	/**
     * Default value included to remove warning. Remove or modify at will.
     **/
    private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "user_id", unique=true, nullable = false)
	private Long userId;
	
	@NotNull
	@NotEmpty
    @Column(name="username")
    private String username;

    @NotNull
    @NotEmpty
    @Column(name="password")
	private String password;

    @NotNull
    @NotEmpty
    @Column(name="first_name")
	private String firstName;

    @NotNull
    @NotEmpty
    @Column(name="last_name")
	private String lastName;

	@NotNull
    @NotEmpty
    @Email
    @Column(name="email")
	private String email;
	
	public User() {
		
	}
	
	public User(Long userId, String firstName, String lastName, String email, String username, String password, Boolean active) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	@Interceptors(LogInterceptor.class)
	public Long getUserId() {
		return userId;
	}

	@Interceptors(LogInterceptor.class)
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Interceptors(LogInterceptor.class)
	public String getFirstName() {
		return firstName;
	}
	
	@XmlElement 
	@Interceptors(LogInterceptor.class)
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Interceptors(LogInterceptor.class)
	public String getLastName() {
		return lastName;
	}
	
	@XmlElement
	@Interceptors(LogInterceptor.class)
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Interceptors(LogInterceptor.class)
	public String getEmail() {
		return email;
	}
	
	@XmlElement 
	@Interceptors(LogInterceptor.class)
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Interceptors(LogInterceptor.class)
	public String getUsername() {
		return username;
	}
	
	@XmlElement 
	@Interceptors(LogInterceptor.class)
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Interceptors(LogInterceptor.class)
	public String getPassword() {
		return password;
	}
	
	@XmlElement 
	@Interceptors(LogInterceptor.class)
	public void setPassword(String password) {
		this.password = password;
	}
}
