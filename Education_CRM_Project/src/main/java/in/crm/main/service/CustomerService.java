package in.crm.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.crm.main.entities.User;
import in.crm.main.repository.CustomerRepository;



@Service
public class CustomerService {
	
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public Page<User> getAllUserDetailsByPagination(Pageable pageable)
	{
		return customerRepository.findAll(pageable);
	}
	
	public User getCustomerDetails(String userEmail)
	{
		return customerRepository.findByEmail(userEmail);
	}
	
	public void updateUserBanStatus(User user)
	{
		customerRepository.save(user);
	}

}
