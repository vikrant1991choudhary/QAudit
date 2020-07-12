package com.sb.xlsql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sb.xlsql.model.FixedAsset;

public interface FixedAssetRepository extends CrudRepository<FixedAsset, Long>  {

	@Query(value="select * from fixed_asset where id_company = ?1",nativeQuery=true)
	public List<FixedAsset> getFixedAssetAudition(String companyId);
}
