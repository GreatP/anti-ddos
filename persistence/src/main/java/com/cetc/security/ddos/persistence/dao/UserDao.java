package com.cetc.security.ddos.persistence.dao;

import com.cetc.security.ddos.persistence.UserEntity;
import com.cetc.security.ddos.persistence.service.Role;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User management dao implementation
 * @author yaohong
 *
 */
@Repository("userDao")
public class UserDao extends AbstractBaseDao<UserEntity> {

	public UserEntity findUser(String userName) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", userName);
		List<UserEntity> result = super.findByProperties(params);
		if (result == null || result.size() == 0) {
			return null;
		} else {
			return result.get(0);
		}
	}

    public List<UserEntity> findUserByRole(Role role) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("role", role);
        List<UserEntity> result = super.findByProperties(params);
        if (result == null || result.size() == 0) {
            return null;
        } else {
            return result;
        }
    }


	public UserEntity findById(int id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<UserEntity> result = super.findByProperties(params);
		if (result == null || result.size() == 0) {
			return null;
		} else {
			return result.get(0);
		}
	}

	@Transactional
	public int updateUser(String userName, String nickName, String password, Role role) {
		Map<String, Object> where = new HashMap<String, Object>();
		where.put("username", userName);
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(nickName)) {
			params.put("nickName", nickName);
		}
		if (StringUtils.isNotEmpty(password)) {
			params.put("password", password);
		}

		if (role != null) {
			params.put("role", role);
		}
		return super.updateByProperties(params, where);
	}

	@Transactional
	public void deleteUser(String userName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", userName);
		super.deleteByProperties(params);
	}

	@Transactional
	public void deleteUser(int id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		super.deleteByProperties(params);
	}

	@Transactional
	public void deleteUser(List<Integer> ids) {
		super.delete(ids);
	}

	@Transactional
	public void insertUser(UserEntity userEntity) {
		super.insert(userEntity);
	}

	public List<UserEntity> listUsers() {
		return super.findAll();
	}

	public List<UserEntity> listUsers(int start, int limit) {
		return find(start, limit);
	}
	
	@Override
	public Long countAll() {
		return super.countAll();
	}

}
