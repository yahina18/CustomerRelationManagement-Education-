package in.crm.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import in.crm.main.entities.Employee;
import in.crm.main.entities.FollowUps;
import in.crm.main.entities.Inquiry;
import in.crm.main.service.FollowUpsService;
import in.crm.main.service.InquiryService;


@Controller
@SessionAttributes("sessionEmp")
public class InquiryController {
	
	
	@Autowired
	private InquiryService inquiryService;
	
	@Autowired
	private FollowUpsService followUpsService;
	
	@GetMapping("/newInquiry")
	public String openNewInquiryPage(Model model)
	{
		model.addAttribute("inquiry", new Inquiry());
		return "new-inquiry";
	}
	
	@PostMapping("/submitInquiryForm")
	public String submitInquiryForm(@ModelAttribute("inquiry") Inquiry inquiry,
			@ModelAttribute("sessionEmp") Employee sessionEmp, Model model,
			@RequestParam(name="followUpDate", required=false) String followUpDate)
	{
		inquiry.setEmpEmail(sessionEmp.getEmail());
		
		try
		{
			inquiryService.addNewInquiry(inquiry);
			
			String status = inquiry.getStatus();
			if(status.equals("Interested - (follow up)") && followUpDate != null)
			{
				FollowUps followUps = new FollowUps();
				
				followUps.setPhoneno(inquiry.getPhoneno());
				followUps.setFollowUpDate(followUpDate);
				followUps.setEmpEmail(sessionEmp.getEmail());
				
				followUpsService.addFollowUps(followUps);
			}
			
			model.addAttribute("successMsg", "New inquiry added successsfully");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
			model.addAttribute("errorMsg", "Inquiry not added due to some error");
		}
		return "inquiry-management";
	}

}
