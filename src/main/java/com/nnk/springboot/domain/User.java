package com.nnk.springboot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.nnk.springboot.constant.GeneralConstraints;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The Class User.
 */
@Builder
@Entity
@Table(name = "users", uniqueConstraints 
	= {@UniqueConstraint(columnNames = "username")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

	/** The id. */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

	/** The username. */
	@Column(name = "username",
			length = GeneralConstraints.VARIABLE_LENGTH_125,
			nullable = false)
    private String username;

	/** The password. */
	@Column(name = "password",
			length = GeneralConstraints.VARIABLE_LENGTH_125,
			nullable = false)
    private String password;

	/** The fullname. */
	@Column(name = "fullname",
			length = GeneralConstraints.VARIABLE_LENGTH_125,
			nullable = false)
    private String fullname;

	/** The role. */
	@Column(name = "role",
			length = GeneralConstraints.VARIABLE_LENGTH_125,
			nullable = false)
    private String role;

	// ############################################################

	/**
	 * Instantiates a new user.
	 *
	 * @param username the username
	 * @param password the password
	 * @param fullname the fullname
	 * @param role the role
	 */
	public User(
    		final String username,
    		final String password,
    		final String fullname,
    		final String role) {

    	this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }

	// ############################################################

}
