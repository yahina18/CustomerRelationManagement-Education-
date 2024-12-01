package in.crm.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.crm.main.entities.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
