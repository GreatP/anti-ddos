package com.cetc.security.ddos.persistence.service;

import com.cetc.security.ddos.persistence.CleanDevEntity;
//import com.cetc.security.ddos.persistence.UserCleanDevEntity;
import com.cetc.security.ddos.persistence.UserEntity;
//import com.cetc.security.ddos.persistence.dao.UserCleanDevDao;
import com.cetc.security.ddos.persistence.dao.UserDao;

import org.apache.log4j.Logger;
import com.cetc.security.ddos.common.utils.AntiLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Concrete implementation for user management service.
 * @author yaohong
 *
 */
@Service("userService")
public class UserService implements UserDetailsService {
    private static Logger logger = AntiLogger.getLogger(UserService.class);

	@Autowired
	private UserDao userDao;
    //@Autowired
   // UserCleanDevDao userCleanDevDao;


	public UserEntity getUser(String userName) {

		UserEntity user = userDao.findUser(userName);
        if (user == null) {
            logger.warn("Fail to find the username: " + userName);
        } else {
            logger.debug("Success to find the username: " + userName);
        }

		return user;
	}

	public UserEntity getUser(int id) {

		UserEntity user = userDao.findById(id);
		return user;
	}

	public void insertUser(UserEntity userEntity) {

		if (userEntity != null) {
			userEntity.setCreateTime(new Date());
		}
		userDao.insertUser(userEntity);
		logger.info("Success to save the user: " + userEntity);
	}

	public List<UserEntity> listUsers() {

		return userDao.listUsers();
	}

    public List<UserEntity> listUsersByRole(Role role) {

        return userDao.findUserByRole(role);
    }


	public List<UserEntity> listUsers(int start, int limit) {

		return userDao.listUsers(start, limit);
	}

	public long countAllUsers(){
		return userDao.countAll();
	}

	public int updateUser(String userName, String nickName, String password, Role role) {
		logger.debug("Start to update the user: " + userName + ", with nick name: " + nickName + ", password: " + password);
		
		return userDao.updateUser(userName, nickName, password, role);
	}

	public void deleteUser(String userName) {

		userDao.deleteUser(userName);
	}

	public void deleteUser(int id) {
		userDao.deleteUser(id);
	}

	public void deleteUser(List<Integer> ids) {
		userDao.deleteUser(ids);
	}

	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		
		UserEntity user = userDao.findUser(userName);
		if (null == user) {
            logger.error("The user with name " + userName + " was not found");
			throw new UsernameNotFoundException("The user with name " + userName + " was not found");
		}

		UserDetails secUser = new SecuredUser(user); 
		return secUser;
	}

    /*
	public static void main(String[] args) {
		String username = "admin";
		UserServiceImpl test = new UserServiceImpl();
		UserDetails user = test.getUser(username);
		System.out.println(user);
	}
	*/
/*
    @Transactional
    public void insertUser(UserEntity userEntity,  int cleanDevId, String inPort, String outPort) {
        insertUser(userEntity);

        CleanDevEntity cleanDevEntity = new CleanDevEntity();
        cleanDevEntity.setId(cleanDevId);
        UserCleanDevEntity userCleanDevEntity = new UserCleanDevEntity();
        userCleanDevEntity.setUserEntity(userEntity);
        userCleanDevEntity.setCleanDevEntity(cleanDevEntity);
        userCleanDevEntity.setCleanInport(inPort);
        userCleanDevEntity.setCleanOutport(outPort);
        userCleanDevDao.insertNoTrans(userCleanDevEntity);
    }

    public UserCleanDevEntity getUserCleanDevById(int id) {
        return userCleanDevDao.findByUserId(id);
    }
*/
}
