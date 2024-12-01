package in.crm.main.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import in.crm.main.entities.Feedback;
import in.crm.main.service.FeedbackService;



@Controller
@SessionAttributes("sessionUser")
public class FeedbackController 
{
	@Autowired
	private FeedbackService feedbackService;
	
	@GetMapping("/provideFeedback")
	public String openProvideFeedbackPage(Model model)
	{
		model.addAttribute("feedback", new Feedback());
		
		return "provide-feedback";
	}
	
	@PostMapping("/feedbackForm")
	public String handleFeedbackForm(@ModelAttribute("feedback") Feedback feedback, Model model)
	{
		feedback.setDateOfFeedback(LocalDate.now().toString());
	    feedback.setTimeOfFeedback(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
	    feedback.setReadStatus("unread");
	    
	    try
	    {
	        feedbackService.sendFeedback(feedback);
	        model.addAttribute("successMsg", "Feedback sent successfully, thank you..!!");
	    }
	    catch(Exception e)
	    {
	        model.addAttribute("errorMsg", "Feedback not sent due to some error, please try again later..!!");
	        e.printStackTrace();
	    }

	    return "provide-feedback";
	}
}
