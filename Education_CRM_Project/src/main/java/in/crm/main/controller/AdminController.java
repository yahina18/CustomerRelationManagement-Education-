package in.crm.main.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.crm.main.entities.Courses;
import in.crm.main.service.CourseService;
import in.crm.main.service.FeedbackService;





@Controller
public class AdminController {
	
	
	private String UPLOAD_DIR = "src/main/resources/static/upload/";
	private String IMAGE_URL = "http://localhost:8080/upload/";
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private FeedbackService feedService;
	
	@GetMapping("/adminLogin")
	public String openAdminLoginPage()
	{
		return "admin-login";
	}
	
	@PostMapping("/adminLoginForm")
	public String adminLoginForm(@RequestParam("adminemail") String aemail, @RequestParam("adminpass") String apass, Model model)
	{
		if(aemail.equals("admin@gmail.com") && apass.equals("admin123"))
		{
			return "admin-profile";
		}
		else
		{
			model.addAttribute("errorMsg", "Invalid email id or password");
			return "admin-login";
		}
	}
	
	/*
	 * @GetMapping("/adminProfile") public String openAdminProfilePage() { return
	 * "admin-profile"; }
	 */
	
	@GetMapping("/courseManagement")
	public String openCourseManagementPage(Model model)
	{
		List<Courses> coursesList = courseService.getAllCourses();
		
		model.addAttribute("coursesList", coursesList);
		
		return "course-management";
	}
	
	@GetMapping("/addCourse")
	public String openAddCoursePage(Model model)
	{
		model.addAttribute("course", new Courses());
		return "addCourse";
	}
	
	@PostMapping("/addCourseForm")
	public String addCourseForm(@ModelAttribute("course") Courses course, @RequestParam("courseImg") MultipartFile courseImg, Model model)
	{
		try
		{
			courseService.addCourse(course, courseImg);
			model.addAttribute("successMsg", "Course added successfully");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			model.addAttribute("errorMsg", "Course not added due to some error");
		}
		return "addCourse";
	}
	
	
	@GetMapping("/editCourse")
	public String openEditCoursePage(@RequestParam("courseName") String courseName, Model model)
	{
		Courses course = courseService.getCourseDetails(courseName);
		
		model.addAttribute("course", course);
		model.addAttribute("newCourseObj", new Courses());
		
		return "edit-course";
	}
	
	@PostMapping("/updateCourseDetailsForm")
	public String updateCourseDetailsForm(@ModelAttribute("newCourseObj") Courses newCourseObj, @RequestParam("courseImg") MultipartFile courseImg, RedirectAttributes redirectAttributes) 
	{
		Courses oldCourseObj = courseService.getCourseDetails(newCourseObj.getName());
		newCourseObj.setId(oldCourseObj.getId());
		
		try {
			
			if(!courseImg.isEmpty())
			{
				String imgName = courseImg.getOriginalFilename();
				Path imgPath = Paths.get(UPLOAD_DIR+imgName);
				Files.write(imgPath, courseImg.getBytes());
				newCourseObj.setImageUrl(IMAGE_URL+imgName);
				
			}else {
				newCourseObj.setImageUrl(oldCourseObj.getImageUrl());
			}
			courseService.updateCourseDetails(newCourseObj);
			redirectAttributes.addFlashAttribute("successMsg","Course Edited Successfully !!");
			
		}catch (Exception e) {
			
			redirectAttributes.addFlashAttribute("errorMsg","Please Try Again !!");
			e.printStackTrace();
		}
		
		return "redirect:/courseManagement";
		
	}
	
	@GetMapping("/deleteCourseDetails")
	public String deleteCourse(@RequestParam("courseName") String courseName, RedirectAttributes redirectAttributes)
	{
		 
		try {
			
			courseService.deleteCourseDetails(courseName);
	        redirectAttributes.addFlashAttribute("successMsg","Course deleted successfully!! "); 		
		}
		catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMsg","Please try again !!! ");
		     e.printStackTrace();
		}
		
		return "redirect:/courseManagement";
	}
	
	//-------------feedback----------------------------
		@GetMapping("/adminFeedback")
		public String openFeedbackPage(Model model,
				@RequestParam(name="page", defaultValue = "0") int page,
				@RequestParam(name="size", defaultValue = "4") int size)
		{
			Pageable pageable = PageRequest.of(page, size);
			
			Page<in.crm.main.entities.Feedback> feedbackPage = feedService.getAllFeedbacksByPagination(pageable);
			
			model.addAttribute("feedbackPage", feedbackPage);
			
			return "view-feedbacks";
		}
		
	    @PostMapping("/updateFeedbackStatus")
	    public String updateFeedbackStatus(@RequestParam("id") Long id, @RequestParam("status") String status, RedirectAttributes redirectAttributes)
	    {
	        boolean success = feedService.updateFeedbackStatus(id, status);
	        if (success) {
	            redirectAttributes.addFlashAttribute("successMsg", "Feedback updated successfully.");
	        } else {
	            redirectAttributes.addFlashAttribute("errorMsg", "Failed to update feedback status.");
	        }
	        return "redirect:/adminFeedback"; // Redirect to the page where feedbacks are listed
	    }
	

}
