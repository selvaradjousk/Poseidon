package com.nnk.springboot.config;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nnk.springboot.domain.User;



/**
 * The Class MyUserDetails.
 * @author Senthil
 */
public class MyUserDetails  implements UserDetails {

	private static final long serialVersionUID = 1L;

    private Integer id;

	/** The user name. */
	private String username;

    /** The password. */
    private String password;

    /** The authorities. */
    private GrantedAuthority authorities;


    // ##############################################################


    /**
     * Instantiates a new my user details.
     *
     * @param user the user
     */
    public MyUserDetails(Integer id, String username, String password,
            GrantedAuthority authorities) {
				this.id = id;
				this.username = username;
				this.password = password;
				this.authorities = authorities;
				}


    // ##############################################################



	public static MyUserDetails build(User user) {
        GrantedAuthority authorities = new SimpleGrantedAuthority(user.getRole());

        return new MyUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities);
    }

    // ##############################################################

    /**
     * Gets the authorities.
     *
     * @return the authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(authorities);
    }


    // ##############################################################

    public Integer getId() {
        return id;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Checks if is account non expired.
     *
     * @return true, if is account non expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Checks if is account non locked.
     *
     * @return true, if is account non locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Checks if is credentials non expired.
     *
     * @return true, if is credentials non expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Checks if is enabled.
     *
     * @return true, if is enabled
     */
    @Override
    public boolean isEnabled() {
        return true;
    }


    // ##############################################################

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MyUserDetails user = (MyUserDetails) o;
        return Objects.equals(id, user.id);
    }

    

    // ##############################################################

}