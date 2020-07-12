package com.sb.xlsql.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sb.xlsql.model.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long>  {
 
	@Query(value="SELECT * from tbluser where email = ?1 AND password=?2", nativeQuery=true)
	public User isValidUser(String loginid, String password);
}
