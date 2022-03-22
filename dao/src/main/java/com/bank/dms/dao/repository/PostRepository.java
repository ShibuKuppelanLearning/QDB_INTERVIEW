package com.bank.dms.dao.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bank.dms.dao.model.Post;
import com.bank.dms.dao.model.User;

@Repository
public interface PostRepository extends CrudRepository<Post, Long>{

	List<Post> findByCreatedBy(User createdBy);

}
