package com.cetc.backend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.cetc.security.ddos.persistence.UserCleanDevEntity;
import com.cetc.security.ddos.persistence.service.Role;

import org.apache.log4j.Logger;

import com.cetc.security.ddos.common.utils.AntiLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cetc.backend.rest.security.TokenTransfer;
import com.cetc.backend.rest.security.TokenUtils;
import com.cetc.backend.rest.security.UserTransfer;
import com.cetc.backend.view.UserForm;
import com.cetc.security.ddos.persistence.UserEntity;
import com.cetc.security.ddos.persistence.service.UserService;
import com.google.gson.Gson;

/**
 * User management REST API.
 * @author yaohong
 *
 */
@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private static Logger logger = AntiLogger.getLogger(UserController.class);
	public static final Gson gson = new Gson();

	@Autowired
	private UserService userService;

	@Autowired
	private ShaPasswordEncoder passwordEncoder;

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authManager;

	@ResponseBody
	@RequestMapping(method=RequestMethod.GET)
	public UserTransfer getUser() {
		UserDetails userDetails = (UserDetails) getCurrentSecuredUser();
		return new UserTransfer(userDetails.getUsername(), this.createRoleList(userDetails));
	}

	@ResponseBody
	@RequestMapping(value="list", method=RequestMethod.GET)
	public String getUserInfo(HttpServletRequest request) {
		Object user = userService.listUsers();
        if (user != null) {
            logger.debug("Success to find user-list info");
        } else {
            logger.debug("Fail to find user-list info");
        }
		return toJson(user);
	}

    @ResponseBody
    @RequestMapping(value="currentUser", method=RequestMethod.GET)
    public String getCurrentUserInfo(HttpServletRequest request) {
        UserEntity userEntity = this.getCurrentUser();
        UserForm userForm = new UserForm();
        userForm.setUsername(userEntity.getUsername());
        userForm.setRole(userEntity.getRole().toString());
        return toJson(userForm);
    }

    @ResponseBody
    @RequestMapping(value="getTenant", method=RequestMethod.GET)
    public String getTenantUsersInfo(HttpServletRequest request) {
        List<UserForm> userForms = new ArrayList<UserForm>();
        List<UserEntity> userEntities = userService.listUsersByRole(Role.TENANT);
        if (userEntities != null) {
            for (UserEntity userEntity: userEntities) {
                UserForm userForm = new UserForm();
                userForm.setId(userEntity.getId());
                userForm.setRole(userEntity.getRole().toString());
                userForm.setUsername(userEntity.getUsername());
                userForm.setNickName(userEntity.getNickName());
                userForms.add(userForm);
            }
        }

        return toJson(userForms);
    }


    @ResponseBody
	@RequestMapping(value="list/{id}", method=RequestMethod.GET)
	public Object getUserInfo(HttpServletRequest request, @PathVariable("id") int id) {
        //Object userInfo = null;


        //UserEntity userEntity = userService.getUser(id);
        Object user = userService.getUser(id);
        if (user != null) {
            logger.debug("Success to find user-id: " + id + "info!");
        } else {
            logger.debug("Fail to find user-id: " + id + "info!");
        }

        return user;

        /*if (userEntity.getRole().equals(Role.TENANT)) {
            UserCleanDevForm userCleanDevForm = new UserCleanDevForm();
            userCleanDevForm.setUserEntity(userEntity);
            UserCleanDevEntity userCleanDevEntity = userService.getUserCleanDevById(userEntity.getId());
            userCleanDevForm.setCleanDevId(userCleanDevEntity.getCleanDevEntity().getId());
            userCleanDevForm.setCleanOutport(userCleanDevEntity.getCleanOutport());
            userCleanDevForm.setCleanInport(userCleanDevEntity.getCleanInport());
            userInfo = userCleanDevForm;
        } else {
            userInfo = userEntity;
        }

		return userInfo;*/
	}
	
	@ResponseBody
	@RequestMapping(value="pagelist", method=RequestMethod.GET)
	public Object getPageUserInfo(@RequestParam(value = "page", required = false) String page) {
		
		int start = getPageStart(page);
		List<UserEntity> user = userService.listUsers(start, DEFAULT_PAGE_LIMIT);
		
		UserListForm userListForm = new UserListForm();
		userListForm.setUsers(user);
		userListForm.setTotal(userService.countAllUsers());

		return userListForm;
	}
	
	@ResponseBody
	@RequestMapping(value="userinfo/{username}", method=RequestMethod.GET)
	public Object getUserPageInfo(HttpServletRequest request, @PathVariable("username") String username) {
		Object user = userService.getUser(username);
		logger.debug("Success to find username: "+ username + "info");
		return user;
	}
	
	public int checkSameUserName(String username) {
		
		int flag = 0;
		List<UserEntity> user = userService.listUsers();
		for(UserEntity oneuser:user)
		{
			if (oneuser.getUsername().equals(username))
			{
				flag =1;
				break;
			}
		}
		
		return flag;	
	}
	
	@ResponseBody
	@RequestMapping(value="checkUser", method=RequestMethod.POST)
	public String checkUser(@RequestBody UserForm form) {
		
		if (checkSameUserName(form.getUsername()) == 1)
		{		
			return returnError("the username is exist, please change your username");
		}
		return returnSuccess();
	}

	@ResponseBody
	@RequestMapping(method=RequestMethod.POST)
	public String addUser(@RequestBody UserForm form) {

		UserEntity userEntity = new UserEntity();
		
		/*
		if (checkSameUserName(form.getUsername()) == 1)
		{		
			return returnError("the username is exist, please change your username");
		}*/

		userEntity.setCreateTime(form.getCreateTime());
		userEntity.setNickName(form.getNickName());
		userEntity.setUsername(form.getUsername());
		userEntity.setRole(Role.valueOf(form.getRole()));
		String password = form.getPassword();
		userEntity.setPassword(this.passwordEncoder.encodePassword(password, null));
		//userEntity.setPassword(form.getPassword());
/*
        if (form.getRole().equals("TENANT")) {
            userService.insertUser(userEntity, form.getCleanDevId(), form.getCleanInport(), form.getCleanOutport());
        } else {
            userService.insertUser(userEntity);
        }
*/
        userService.insertUser(userEntity);
        logger.info(this.getCurrentUser().getUsername() + " add user: " + form.getUsername()
                + " configuration information!");
		return returnSuccess();
	}

	@ResponseBody
	@RequestMapping(method=RequestMethod.PUT)
	public String editUser(@RequestBody UserForm userForm) {

		UserEntity userEntity = new UserEntity();

		userEntity.setNickName(userForm.getNickName());
		userEntity.setUsername(userForm.getUsername());
		userEntity.setRole(Role.valueOf(userForm.getRole()));
		
		if (userForm.isChangePasswd() == true)
		{
			String password = userForm.getNewPasswd();
			userEntity.setPassword(this.passwordEncoder.encodePassword(password, null));
		}
		else
		{
			userEntity.setPassword(userService.getUser(userForm.getUsername()).getPassword());
		}

		userService.updateUser(userEntity.getUsername(),userEntity.getNickName(),userEntity.getPassword(),userEntity.getRole());

        logger.info(this.getCurrentUser().getUsername() + " update user: " + userForm.getUsername()
                + " configuration information!");

		return returnSuccess();
	}

	@ResponseBody
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public String deleteUser(@PathVariable("id") int id) {
        String userName = userService.getUser(id).getUsername();
        logger.info(this.getCurrentUser().getUsername() + " delete user: " + userName + " configuration information!");
		userService.deleteUser(id);
		return returnSuccess();
	}
	
	@ResponseBody
    @RequestMapping(value="batchDelUser", method = RequestMethod.POST)
    public String delSelectUser(@RequestBody List<Integer> ids) {
        for (Integer id : ids) {
            logger.info(this.getCurrentUser().getUsername() + " batch delete user: "
                    + userService.getUser(id).getUsername()+ "configuration information!");
        }
		userService.deleteUser(ids);
        return returnSuccess();
    }

	@ResponseBody
	@RequestMapping(value="/authenticate", method=RequestMethod.POST)
	public TokenTransfer authenticate(@RequestParam("username") String username, @RequestParam("password") String password)
	{
        try {
            UserEntity userEntity = this.userService.getUser(username);
            if (userEntity == null) {
                logger.warn("Login username:" + username + " was not found!");
                throw new UsernameNotFoundException("The user with name " + username + " was not found");
            }
        } catch (UsernameNotFoundException ex) {
            throw ex;
        }

		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication authentication = this.authManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException ex) {
            logger.warn("Login username: "+ username + " authenticates failed!");
            throw ex;
        }

		/*
		 * Reload user as password of authentication principal will be null after authorization and
		 * password is needed for token generation
		 */
		UserDetails userDetails = this.userService.loadUserByUsername(username);

		logger.info("Success to login, user: " + username);

		return new TokenTransfer(TokenUtils.createToken(userDetails));
	}

	private List<String> createRoleList(UserDetails userDetails)
	{
		//Map<String, Boolean> roles = new HashMap<String, Boolean>();
		List<String> roles = new ArrayList<String>();
		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			//roles.put(authority.getAuthority(), Boolean.TRUE);
			roles.add(authority.getAuthority());
		}

		return roles;
	}
	
	private class UserListForm {
        private long total;
        List<UserEntity> users;

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

		public List<UserEntity> getUsers() {
			return users;
		}

		public void setUsers(List<UserEntity> users) {
			this.users = users;
		}
    }

    private class UserCleanDevForm {
        private UserEntity userEntity;
        private int cleanDevId;
        private String cleanInport;
        private String cleanOutport;

        public UserEntity getUserEntity() {
            return userEntity;
        }

        public void setUserEntity(UserEntity userEntity) {
            this.userEntity = userEntity;
        }

        public int getCleanDevId() {
            return cleanDevId;
        }

        public void setCleanDevId(int cleanDevId) {
            this.cleanDevId = cleanDevId;
        }

        public String getCleanInport() {
            return cleanInport;
        }

        public void setCleanInport(String cleanInport) {
            this.cleanInport = cleanInport;
        }

        public String getCleanOutport() {
            return cleanOutport;
        }

        public void setCleanOutport(String cleanOutport) {
            this.cleanOutport = cleanOutport;
        }
    }
	
	private class DefResponse {
		
		int status;
		String retErrInfo;
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getRetErrInfo() {
			return retErrInfo;
		}
		public void setRetErrInfo(String retErrInfo) {
			this.retErrInfo = retErrInfo;
		}
		
	}
}
