package in.crm.main.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		

	     var authorities =	authentication.getAuthorities();
	     System.out.println("Inside this if.... admin role successfully got ... "+authorities);
	     
	     if(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
	    	 System.out.println("Inside this if.... admin role successfully got ... ");
	    	 response.sendRedirect("/adminProfile");
	     }
	     else if(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"))) {
	    	 response.sendRedirect("/employeeManagement");
	     }
	     else if(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"))) {
	    	 response.sendRedirect("/userProfile");
	     }
	     
		
		
	}

}
