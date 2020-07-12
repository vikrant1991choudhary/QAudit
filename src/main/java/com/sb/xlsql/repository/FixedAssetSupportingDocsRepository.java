package com.sb.xlsql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sb.xlsql.model.FixedAssetSupportingDocs;


@Repository
public interface FixedAssetSupportingDocsRepository extends CrudRepository<FixedAssetSupportingDocs, Long>{
	
	 @Query(value = "select * from fixed_asset_supporting_docs where particulars = ?", nativeQuery=true) public
	  List<FixedAssetSupportingDocs> findAllSupportingFixedDetailsFileDetails(String particulars);

}
