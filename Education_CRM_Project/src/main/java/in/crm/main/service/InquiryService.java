package in.crm.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.crm.main.entities.Inquiry;
import in.crm.main.repository.InquiryRepository;



@Service
public class InquiryService {
	
	@Autowired
	private InquiryRepository inquiryRepository;
	
	public void addNewInquiry(Inquiry inquiry)
	{
		inquiryRepository.save(inquiry);
	}

	public List<Inquiry> findInquiriesUsingPhno(String phoneNumber) {
		// TODO Auto-generated method stub
		return inquiryRepository.findByPhoneno(phoneNumber);
	}

}
