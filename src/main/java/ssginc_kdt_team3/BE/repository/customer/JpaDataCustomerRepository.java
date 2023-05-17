package ssginc_kdt_team3.BE.repository.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ssginc_kdt_team3.BE.DTOs.customer.CustomerListDTO;

import ssginc_kdt_team3.BE.domain.Customer;

import java.util.Optional;

public interface JpaDataCustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select new ssginc_kdt_team3.BE.DTOs.customer.CustomerListDTO(c.id, c.name, c.email, c.status, c.grade.name) from Customer c")
    Page<CustomerListDTO> findAllBy(Pageable pageable);

//    Page<Customer> findAllBy(Pageable pageable);
//    @Query("select c from Customer c inner join User u on c.id = u.id where u.email = :Email")
    Optional<Customer> findCustomerByEmail(String email);

}