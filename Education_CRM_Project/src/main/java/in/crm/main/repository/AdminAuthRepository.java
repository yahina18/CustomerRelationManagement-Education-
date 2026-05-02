package in.crm.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.crm.main.model.UserAuth;

@Repository
public interface AdminAuthRepository extends JpaRepository<UserAuth, Integer>{

     public Optional<UserAuth> findByUsername(String username);
}
