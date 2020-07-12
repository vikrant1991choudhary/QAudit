package com.sb.xlsql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sb.xlsql.model.DebtorsSupportingDocs;

@Repository
public interface DebtorsSupportingDocsRepository extends CrudRepository<DebtorsSupportingDocs, Long>{

	 @Query(value = "select * from debtors_supporting_docs where particulars = ?", nativeQuery=true) public
	  List<DebtorsSupportingDocs> findAllSupportingDebtorFileDetails(String particulars);
}
