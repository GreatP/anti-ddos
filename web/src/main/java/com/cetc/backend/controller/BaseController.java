package com.cetc.backend.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import com.cetc.backend.service.UserContext;
import com.cetc.security.ddos.persistence.UserEntity;
import com.cetc.security.ddos.persistence.service.SecuredUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

import com.cetc.backend.common.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.log4j.Logger;
import com.cetc.security.ddos.common.utils.AntiLogger;


public class BaseController implements Constants {

	public static final String ERROR_PAGE = "errors/error";

	protected static final int DEFAULT_PAGE_LIMIT = 10;

	protected static final int DEFAULT_PAGE_SIZE = 10;
/*
    protected static final short ADD_PAGE = 0;

    protected static final short EDIT_PAGE = 1;
*/
    protected static final short JSON_RETURN_EXISTED = 0x00;
    protected static final short JSON_RETURN_NOT_EXIST = 0x01;

	private static String successJson;

	private static String errorJson;

	private static Gson gson = new Gson();

    private static Logger logger = AntiLogger.getLogger(BaseController.class);
	@Autowired
	protected UserContext userContext;

	@PostConstruct
	void initJSON() {
		JsonObject rtnJson = new JsonObject();
		rtnJson.addProperty(JSON_SUCCESS, true);
		successJson = rtnJson.toString();
		rtnJson.addProperty(JSON_SUCCESS, false);
		errorJson = rtnJson.toString();
	}

    /**
     * Get current user.
     *
     * @return current user
     */
    public UserEntity getCurrentUser() {
        return userContext.getCurrentUser();
    }

    public SecuredUser getCurrentSecuredUser() {
        return userContext.getCurrentSecuredUser();
    }


	/**
	 * Return success json message.
	 * 
	 * @param message
	 *            message
	 * @return json message
	 */
	public String returnSuccess(String message) {
		JsonObject rtnJson = new JsonObject();
		rtnJson.addProperty(JSON_SUCCESS, true);
		rtnJson.addProperty(JSON_MESSAGE, message);
		return rtnJson.toString();
	}

	/**
	 * Return error json message.
	 * 
	 * @param message
	 *            message
	 * @return json message
	 */
	public String returnError(String message) {
		JsonObject rtnJson = new JsonObject();
		rtnJson.addProperty(JSON_SUCCESS, false);
		rtnJson.addProperty(JSON_MESSAGE, message);
		return rtnJson.toString();
	}
	public Object returnErrorObject(String message) {
		JsonObject rtnJson = new JsonObject();
		rtnJson.addProperty(JSON_SUCCESS, false);
		rtnJson.addProperty(JSON_MESSAGE, message);
		return rtnJson;
	}

	public String returnError(Throwable th) {
		logger.error("error occur,e:", th);
		return returnError(getMessage(th));
	}

    public String returnHttp500Error(HttpServletResponse response, String message) {
        response.setStatus(500);
        return returnError(message);
    }

	/**
	 * Return raw success json message.
	 * 
	 * @return json message
	 */
	public String returnSuccess() {
		return successJson;
	}

	/**
	 * Return raw error json message.
	 * 
	 * @return json message
	 */
	public String returnError() {
		return errorJson;
	}

	/**
	 * Convert the given list into json message.
	 * 
	 * @param list
	 *            list
	 * @return json message
	 */
	public String toJson(List<?> list) {
		return gson.toJson(list);
	}

	/**
	 * Convert the given object into json message.
	 * 
	 * @param obj
	 *            object
	 * @return json message
	 */
	public String toJson(Object obj) {
		return gson.toJson(obj);
	}

	/**
	 * Convert the given object into json message.
	 * 
	 * @param <T>
	 *            content type
	 * @param content
	 *            content
	 * @param header
	 *            header value map
	 * @return json message
	 */
	public <T> HttpEntity<T> toHttpEntity(T content,
			MultiValueMap<String, String> header) {
		return new HttpEntity<T>(content, header);
	}

	/**
	 * Convert the given object into json message.
	 * 
	 * @param content
	 *            content
	 * @return json message
	 */
	public HttpEntity<String> toJsonHttpEntity(Object content) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("content-type", "application/json; charset=UTF-8");
		responseHeaders.setPragma("no-cache");
		return toHttpEntity(toJson(content), responseHeaders);
	}

	/**
	 * Convert the given map into json message.
	 * 
	 * @param map
	 *            map
	 * @return json message
	 */
	public String toJson(Map<Object, Object> map) {
		return gson.toJson(map);
	}

	public String getMessage(Throwable th) {
		if (th == null) {
			return null;
		}
		String msg = th.getMessage();
		if ((msg == null || msg.isEmpty()) && th.getCause() != null) {
			msg = th.getCause().getMessage();
		}
		return msg;
	}

    protected int getPageStart(String page) {
        int start = 0;
        if (page != null) {
            int pageNum = Integer.valueOf(page);
            if (pageNum <= 1) {
                start = 0;
            } else {
                start = (pageNum - 1) * DEFAULT_PAGE_LIMIT;
            }
        }

        return start;
    }
}
