package com.sb.xlsql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sb.xlsql.model.CashBook;
import com.sb.xlsql.model.Files;

public interface FileRepository extends CrudRepository<Files, Long>{
	
	@Query(value = "select * from tbl_files where id_client=?", nativeQuery=true)
	 public List<Files> findAllFilesData(Long idCompany);
	

}
