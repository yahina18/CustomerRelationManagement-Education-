package in.crm.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.crm.main.entities.FollowUps;
import in.crm.main.repository.FollowUpsRepository;
;

@Service
public class FollowUpsService {
	
	@Autowired
	private FollowUpsRepository followUpsRepository;
	
	public void addFollowUps(FollowUps followUps)
	{
		Optional<FollowUps> optional = followUpsRepository.findByPhoneno(followUps.getPhoneno());
		if(optional.isPresent())
		{
			FollowUps oldFollowUps = optional.get();
			oldFollowUps.setFollowUpDate(followUps.getFollowUpDate());
			followUpsRepository.save(oldFollowUps);
		}
		else
		{
			followUpsRepository.save(followUps);
		}
	}

	public List<FollowUps> getMyFollowUps(String empEmail, String followUpDate) {
		
		return followUpsRepository.findByEmpEmailAndFollowUpDate(empEmail, followUpDate);
		
	}

}
