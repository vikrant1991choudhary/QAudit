package com.sb.xlsql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sb.xlsql.model.CashBook;
import com.sb.xlsql.model.Files;
import com.sb.xlsql.model.SupportingDocs;

@Repository
public interface SupportingDocsRepository extends CrudRepository<SupportingDocs, Long>{
	
	
	  @Query(value = "select * from supporting_docs where voucher_no = ?", nativeQuery=true) public
	  List<SupportingDocs> findAllSupportingFilesData(String vchno);
	 

}
