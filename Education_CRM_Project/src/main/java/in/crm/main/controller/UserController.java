package in.crm.main.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import in.crm.main.dto.PurchasedCourses;
import in.crm.main.entities.Courses;
import in.crm.main.entities.User;
import in.crm.main.repository.OrdersRepository;
import in.crm.main.repository.UserRepository;
import in.crm.main.service.CourseService;
import in.crm.main.service.UserService;
import jakarta.validation.Valid;

@Controller
@SessionAttributes("sessionUser")
public class UserController {

	@Autowired
	UserService service;
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	UserRepository userRepository;

	@GetMapping({"/","/index"})
	public String openIndexPage(Model model) {
		
		List<Courses> course = courseService.getAllCourses();
		model.addAttribute("courseList", course);
		return "index";
	}

	@GetMapping("/login")
	public String openLoginPage(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@PostMapping("/loginForm")
	public String handleLoginForm(@ModelAttribute("user") User user, Model model) {
		boolean isAuthenticate = service.LoginUser(user.getEmail(), user.getPassword());
		if (isAuthenticate) {
			
			User authenticateUser = userRepository.findByEmail(user.getEmail());
			model.addAttribute("sessionUser",authenticateUser);
			return "user-profile";
			
		}
			
		else {
			model.addAttribute("errorMsg", "Incorret Email or Password !!");
			return "login";

		}

	}

	/* register-start */

	@GetMapping("/register")
	public String openRegisterPage(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/regForm")
	public String handleRegForm(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "register";
		} else {

			try {

				service.registerUser(user);
				model.addAttribute("successMsg", "Registered Successfully");
				return "register";

			} catch (Exception e) {
				model.addAttribute("errorMsg", "Registration Failed !!");
				return "error";
			}

		}

	}
	/* register-end */
   
	@GetMapping("/logout")
	public String logout(SessionStatus status)
	{
		status.setComplete();
		return "login";
	}
	
	
	@Autowired
    private	OrdersRepository orderRepository;
	
	@GetMapping("/myCourses")
	public String myCourse(@ModelAttribute("sessionUser") User sessionUser, Model model)
	{
	     List<Object[]> pcDbList =	orderRepository.findPurchasedCoursesByEmail(sessionUser.getEmail());
		
	     List<PurchasedCourses> purchasedList =  new ArrayList<>();
	     for(Object[] courses : pcDbList)
	     {
	    	 PurchasedCourses purchaseCourse = new PurchasedCourses();
	    	 purchaseCourse.setPurchasedOn((String)courses[0]);
	    	 purchaseCourse.setDescription((String)courses[1]);
	    	 purchaseCourse.setImageUrl((String)courses[2]);
	    	 purchaseCourse.setCourseName((String)courses[3]);
	    	 purchaseCourse.setUpdatedOn((String)courses[4]);
	    	 
	    	 purchasedList.add(purchaseCourse);
	    	 
	     }
	     model.addAttribute("purchasedCoursesList", purchasedList);
	     
		return "mycourse";
	}
	
	@GetMapping("/userProfile")
	public String userProfile()
	{
		return "user-profile";
	}
	
}