package ca.mcgill.ecse321.tutoringsystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.tutoringsystem.model.Bill;

public interface BillRepository extends CrudRepository<Bill, String> {
	Bill findBillByBillId(int billId);
}
