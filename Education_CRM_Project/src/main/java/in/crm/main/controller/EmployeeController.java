package in.crm.main.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.crm.main.entities.Courses;
import in.crm.main.entities.Employee;
import in.crm.main.entities.EmployeeOrders;
import in.crm.main.entities.Orders;
import in.crm.main.repository.EmployeeOrderRepository;
import in.crm.main.repository.EmployeeRepository;
import in.crm.main.service.CourseService;
import in.crm.main.service.EmployeeService;
import in.crm.main.service.OrderService;

@Controller
@SessionAttributes("sessionEmp")
public class EmployeeController {
	
	@Autowired
	private EmployeeService empService;
	
	@Autowired
	private EmployeeRepository empRepository;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private EmployeeOrderRepository employeeOrderRepository;
	
    
	
	@GetMapping("/employeeLogin")
	public String openEmployeeLogin()
	{
		return "employee-Login";
	}
	@GetMapping("/employeeManagement")
	public String openEmployeeManagementPage(Model model)
	{
		List<Employee> empList = empRepository.findAll();
		model.addAttribute("empList",empList);
		return "employee-management";
	 
	}
	
	@PostMapping("/addEmployeeForm")
	public String addEmployee(@ModelAttribute("employee") Employee emp, RedirectAttributes redirectAttribute)
	{
		 try{
			 empService.addEmployee(emp);
             redirectAttribute.addFlashAttribute("successMsg","Employee Added SuccessFully");			 
		 }catch (Exception e) {
		    
			 redirectAttribute.addFlashAttribute("errorMsg","Please try again !!");
			 e.printStackTrace();
		}
	      	return "redirect:/employeeManagement";
	}
	
	@GetMapping("/editEmployee")
	public String editEmployee(@RequestParam("employeeEmail") String empEmail , Model model)
	{
		
	     Employee emp =	empRepository.findByEmail(empEmail);
	     model.addAttribute("employee",emp);
	     
	     return "edit-employee";
	     
	     
	}
	
	@PostMapping("/updateEmployeeDetailsForm")
	public String updateEmployeeDetailsForm(@ModelAttribute("newEmployeeObj") Employee newEmployeeObj, RedirectAttributes redirectAttributes)
	{
		try
		{
			Employee oldEmployeeObj = empService.getEmployeeDetails(newEmployeeObj.getEmail());
			newEmployeeObj.setId(oldEmployeeObj.getId());
			
			empService.updateEmployeeDetails(newEmployeeObj);
			
			redirectAttributes.addFlashAttribute("successMsg", "Employee details updated successfully");
		}
		catch(Exception e)
		{
			redirectAttributes.addFlashAttribute("errorMsg", "Employee details not updated due to some error");
			e.printStackTrace();
		}
		
		return "redirect:/employeeManagement";
	}
	
	@GetMapping("/deleteEmpDetails")
	public String deleteEmployeeByEmail(@RequestParam("empEmail") String email, RedirectAttributes redirectAttributes)
	{
		try {
			empService.deleteEmployeeDetails(email);
			redirectAttributes.addFlashAttribute("successMsg","Employee Deleted Successfully");
		}catch(Exception e)
		{
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMsg","Please try Again");
		}
		
		return "redirect:/employeeManagement";
		
	}
	@PostMapping("/empLoginForm")
	public String handleEmployeeLogin(@RequestParam("email") String email, @RequestParam("password") String password, Model model)
	{
	      	boolean isAuthenticate = empService.loginEmp(email, password);
	      	if(isAuthenticate) {
	      		Employee empDetails = empService.getEmployeeDetails(email);
	      		model.addAttribute("sessionEmp",empDetails);
	      		return "employee-profile";
	      		
	      	}
	      	
	      	else {
	      		model.addAttribute("errorMsg", "Incorrect Email or Paossword");
	      		return "employee-Login";
	      	}
	}
	
	@GetMapping("/employeeProfile")
	public String openEmployeeProfile()
	{
		return "employee-profile";
	}
	
	@GetMapping("/sellCourse")
	public String openSellCourse(Model model)
	{
		
		List<String> courseList =  courseService.getAllCoursesName();
		System.out.println("CourseList : "+courseList);
		model.addAttribute("courseList", courseList);
		
		String uuidOrderId =   UUID.randomUUID().toString();
		model.addAttribute("uuidOrderId",uuidOrderId);
		
		model.addAttribute("Orders", new Orders());
		
		return "sell-course";
	}
	
	
	@PostMapping("/sellCourseForm")
	public String sellCourseForm(@ModelAttribute("orders") Orders orders, @ModelAttribute("sessionEmp") Employee sessionEmp, RedirectAttributes redirectAttributes)
	{
		LocalDate ld = LocalDate.now();
		String pdate = ld.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		LocalTime lt = LocalTime.now();
		String ptime = lt.format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
		
		String purchased_date_time = pdate+", "+ptime;
		
		orders.setDateOfPurchase(purchased_date_time);
		
		try
		{
			orderService.storeUserOrder(orders);
			EmployeeOrders employeeOrder = new EmployeeOrders();
			employeeOrder.setEmployeeEmail(sessionEmp.getEmail());
			employeeOrder.setOrderId(orders.getOrderId());
			
			employeeOrderRepository.save(employeeOrder);
			redirectAttributes.addFlashAttribute("successMsg", "Course provided successfully");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMsg", "Course not provided due to some error");
		}
		return "redirect:/sellCourse";
	}
	
	
	@GetMapping("/inquiryManagement")
	public String openIquiryManagementPage(SessionStatus sessionStatus)
	{
		return "inquiry-management";
	}
	
	//-------------employee logout------------------------
	@GetMapping("/employeeLogout")
	public String employeeLogout(SessionStatus sessionStatus)
	{
		sessionStatus.setComplete();
		return "employee-login";
	}
	
}
