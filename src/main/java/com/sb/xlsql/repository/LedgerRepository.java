package com.sb.xlsql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sb.xlsql.model.CashBook;
import com.sb.xlsql.model.Ledger;

@Repository
public interface LedgerRepository extends CrudRepository<Ledger, Long>{
	
	 @Query(value = "SELECT * FROM ledger WHERE particulars LIKE '%building %' OR particulars LIKE '%car %'", nativeQuery=true)
	 public List<Ledger>  findDoucmenttobeChecked();
	 
	 

}
