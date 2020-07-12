package com.sb.xlsql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sb.xlsql.model.Debtors;

public interface DebtorsRepository extends CrudRepository<Debtors, Long> {
	

}
