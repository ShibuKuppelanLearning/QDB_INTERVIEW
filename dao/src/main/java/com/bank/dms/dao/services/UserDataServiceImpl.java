package com.bank.dms.dao.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bank.dms.core.vo.User;
import com.bank.dms.dao.repository.UserRepository;
import com.bank.dms.util.exception.DataSaveException;

public class UserDataServiceImpl implements UserDataService {

	private static final Logger logger = LoggerFactory.getLogger(UserDataServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public Long addUser(User user) {
		Long userId = null;
		try {
			com.bank.dms.dao.model.User userModel = userRepository
					.save(new com.bank.dms.dao.model.User(user.getFirstName(), user.getLastName()));
			userId = userModel.getId();
		} catch (DataException | ConstraintViolationException | JDBCConnectionException exception) {
			logger.error("Error while saving document", exception);
			throw new DataSaveException("DATA_SAVE_ERROR", "Unable to add new User");
		} catch (Exception exception) {
			logger.error("Error while saving document", exception);
			throw new DataSaveException("UNKNOWN_SAVE_ERROR", "Unable to add new User due to unknown error");
		}
		return userId;
	}

	@Override
	public List<User> fetchAllUsers() {
		final List<User> users = new ArrayList<User>();
		try {
			Iterable<com.bank.dms.dao.model.User> userModels = userRepository.findAll();
			if (userModels != null) {
				userModels.forEach(userModel -> {
					users.add(new User(userModel.getId(), userModel.getFirstName(), userModel.getLastName()));
				});
			}
		} catch (DataException dataException) {
			logger.error("Error while fetching document", dataException);
		} catch (JDBCConnectionException jdbcConnectionException) {
			logger.error("JDBC connection exception", jdbcConnectionException);
		}
		return users;
	}

}
