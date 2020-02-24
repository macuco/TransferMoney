package com.trasnfermoney.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.trasnfermoney.model.User;

@Repository
public class UserRepository {

	private static long MAXUSERID;
	public static Map<Long, User> USERS;
	
	static {
		MAXUSERID=1;
		USERS = new HashMap<>();
	}
	
	public User saveUser(User user) {
		if(USERS.get(user.getId())==null) {
			USERS.put(user.getId(), user);
		}
		return USERS.get(user.getId());
	}
	
	public User saveNewUser(User user) {
		user.setId(MAXUSERID++);
		USERS.put(MAXUSERID-1, user);
		return user;
	}
	
	public Optional<User> getUser(Long userId) {
		return Optional.ofNullable(USERS.get(userId));
	}
}
