package com.tool.springBoot.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class OpenIdAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	  

	
      
      
    @SuppressWarnings("unused")
	public Authentication attemptAuthentication(HttpServletRequest request,  
            HttpServletResponse response) throws AuthenticationException {  
          
        String genCode = this.obtainGeneratedCaptcha(request);  
        String inputCode = this.obtainCaptcha(request);  
        
          
        return super.attemptAuthentication(request, response);  
    }  
      
    protected String obtainCaptcha(HttpServletRequest request){  
        return request.getParameter("username");  
    }  
      
    protected String obtainGeneratedCaptcha (HttpServletRequest request){  
        return request.getParameter("password");  
    }  

}