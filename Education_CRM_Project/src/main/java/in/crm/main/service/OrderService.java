package in.crm.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.crm.main.entities.Orders;
import in.crm.main.repository.OrdersRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrdersRepository repository;
	
	public void storeUserOrder(Orders orders)
	{
		repository.save(orders);
	}

}
