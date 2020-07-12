package com.sb.xlsql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sb.xlsql.model.BankSupportingDocs;
import com.sb.xlsql.model.FixedAssetSupportingDocs;

public interface BankSupportingDocsRepository extends CrudRepository<BankSupportingDocs, Long> {
	
	 @Query(value = "select * from bank_supporting_docs where bank_balance = ?", nativeQuery=true) public
	  List<BankSupportingDocs> findAllSupportingBankFileDetails(String bankBalance);


}
