package in.crm.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.crm.main.entities.User;
import in.crm.main.repository.OrdersRepository;
import in.crm.main.service.CustomerService;


@Controller
public class CustomerController 
{
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private OrdersRepository ordersRepository;
	
	@GetMapping("/customerManagement")
	public String openCustomerManagementPage(Model model,
			@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(name="size", defaultValue = "5") int size)
	{
		Pageable pageable = PageRequest.of(page, size);
		
		Page<User> userPage = customerService.getAllUserDetailsByPagination(pageable);
		
		model.addAttribute("userPage", userPage);
		
		return "customer-management";
	}
	
	
	@GetMapping("/banUserDetails")
	public String banUserDetails(@RequestParam("userEmail") String userEmail, @RequestParam("banStatus") boolean banStatus, RedirectAttributes redirectAttributes)
	{
		try
		{
			User userObject = customerService.getCustomerDetails(userEmail);
			userObject.setBanStatus(banStatus);
			
			customerService.updateUserBanStatus(userObject);
			
			if(banStatus)
			{
				redirectAttributes.addFlashAttribute("successMsg", "User banned successfully");
			}
			else
			{
				redirectAttributes.addFlashAttribute("successMsg", "User unbanned successfully");
			}
		}
		catch(Exception e)
		{
			redirectAttributes.addFlashAttribute("errorMsg", "User not banned due to some error");
			e.printStackTrace();
		}
		return "redirect:/customerManagement";
	}
	
	@GetMapping("/userCoursesDetails")
	public String getAllCustomerCourses(@RequestParam("userEmail") String email,
			@RequestParam("userName") String custName, Model model)
	{
		List<Object[]> coursesList = ordersRepository.findCustomerCoursesByEmail(email);
		
		model.addAttribute("custCoursesList", coursesList);
		model.addAttribute("custName", custName);
		
		return "user-courses";
	}
}
