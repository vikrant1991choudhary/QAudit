package com.sb.xlsql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sb.xlsql.model.CashBook;

public interface CashBookRepository extends CrudRepository<CashBook, Long>{
	
	 @Query(value = "select * from cashbook where company_id=?", nativeQuery=true)
	 public List<CashBook> findAllCashBookData(Long idCompany);
	 
	 @Query(value = "select * from cashbook where credit>20000 and company_id=?", nativeQuery=true)
	 public List<CashBook> findAllCashBookReport(Long idCompany);
	 
		/*
		 * @Query(value =
		 * "select *,count(id) count from cashbook where credit>0 group by particulars having count(id) >1"
		 * , nativeQuery=true) public List<CashBook> getMultipleCashBookPayment();
		 */

}
