package in.crm.main.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.crm.main.entities.Orders;
import in.crm.main.service.OrderService;

@RestController
@RequestMapping("/api")
public class OrderApi {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/storeOrderDetails")
	public ResponseEntity<String> storeUserOrder(@RequestBody Orders order){
		orderService.storeUserOrder(order);
		return ResponseEntity.ok("Order Details stored successfully !!");
		
	}

}
