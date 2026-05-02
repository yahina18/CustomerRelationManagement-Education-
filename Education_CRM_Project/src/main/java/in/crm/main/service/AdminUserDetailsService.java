package in.crm.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.crm.main.repository.AdminAuthRepository;

@Service
public class AdminUserDetailsService  implements UserDetailsService{
	
	@Autowired
	private AdminAuthRepository adminAuthRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return adminAuthRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found !!"));
		
	}

}
