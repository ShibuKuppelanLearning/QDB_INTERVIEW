package com.bank.dms.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bank.dms.dao.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

}
