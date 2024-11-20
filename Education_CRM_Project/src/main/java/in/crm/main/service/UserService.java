package in.crm.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.crm.main.entities.User;
import in.crm.main.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository repository;
	
	public void registerUser(User user)
	{
		repository.save(user);
		
		
	}
	
	public boolean LoginUser(String email , String password)
	{
		
			
			User user =  repository.findByEmail(email);
			if(user != null)
				return  password.equals(user.getPassword());
			
			return false;
	}
	

}
