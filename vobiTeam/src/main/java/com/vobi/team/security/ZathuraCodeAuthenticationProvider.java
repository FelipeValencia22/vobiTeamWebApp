package com.vobi.team.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Component;

import com.vobi.team.modelo.VtUsuario;
import com.vobi.team.presentation.businessDelegate.IBusinessDelegatorView;
import com.vobi.team.utilities.FacesUtils;

import java.util.ArrayList;
import java.util.List;


/**
* @author Zathura Code Generator http://code.google.com/p/zathura/
* www.zathuracode.org
*
*/
@Scope("singleton")
@Component("zathuraCodeAuthenticationProvider")
public class ZathuraCodeAuthenticationProvider implements AuthenticationProvider {
    /**
     * Security Implementation
     */
	
	@Autowired
	private IBusinessDelegatorView businessDelegatorView;
	
    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        
    	
    	   String name = authentication.getName();
           String password = authentication.getCredentials().toString();
           
           try {
   			VtUsuario vtUsuario = businessDelegatorView.autenticarUsuario(name, password);
   		    final List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
               grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
               final UserDetails principal = new User(name, password, grantedAuths);
               final Authentication auth = new UsernamePasswordAuthenticationToken(principal,
                       password, grantedAuths);
               FacesUtils.putinSession("vtUsuario", vtUsuario);
               return auth;
   		} catch (Exception e) {
   			return null;
   		}
    }
    

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
