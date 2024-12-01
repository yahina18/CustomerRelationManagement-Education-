package in.crm.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.crm.main.entities.EmployeeOrders;

public interface EmployeeOrderRepository extends JpaRepository<EmployeeOrders,Long>{

}
