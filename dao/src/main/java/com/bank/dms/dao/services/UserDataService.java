package com.bank.dms.dao.services;

import java.util.List;

import com.bank.dms.core.vo.User;

public interface UserDataService {

	Long addUser(User user);
	
	List<User> fetchAllUsers();
}
