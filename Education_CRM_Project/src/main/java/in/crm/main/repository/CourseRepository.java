package in.crm.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.crm.main.entities.Courses;

@Repository
public interface CourseRepository extends JpaRepository<Courses, Long> {
	
	Courses findByName(String courseName);

}
