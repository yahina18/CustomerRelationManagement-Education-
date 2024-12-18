package in.crm.main.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.crm.main.entities.Courses;
import in.crm.main.repository.CourseRepository;



@Service
public class CourseService {
	
	
	private String UPLOAD_DIR = "src/main/resources/static/upload/";
	private String IMAGE_URL = "http://localhost:8080/upload/";

	@Autowired
	CourseRepository repository;
	
	public List<Courses> getAllCourses()
	{
		 return  repository.findAll();
	}
	
	public List<String> getAllCoursesName()
	{
		List<Courses> courseList = repository.findAll();
		List<String> courseName = new ArrayList<>();
		for(Courses course : courseList)
		{
			courseName.add(course.getName());
		}
		
		return courseName;
	}
	public void addCourse(Courses course, MultipartFile courseImg) throws IOException
	{
		String imgName = courseImg.getOriginalFilename();
		Path imgPath = Paths.get(UPLOAD_DIR+imgName);
		Files.write(imgPath, courseImg.getBytes());
		
		String imgUrl = IMAGE_URL+imgName;
		course.setImageUrl(imgUrl);
		
		repository.save(course);
	}
	
	public Courses getCourseDetails(String courseName)
	{
		return repository.findByName(courseName);
	}
	
	public void updateCourseDetails(Courses course)
	{
		repository.save(course);
	}
	
	public void deleteCourseDetails(String courseName)
	{
		Courses course = repository.findByName(courseName);
		if(course != null)
		{
			repository.delete(course);
		}
		else
		{
			throw new RuntimeException("Course not found with name : "+courseName);
		}
	}
	
}
