package org.szcloud.framework.core.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.szcloud.framework.core.utils.constants.SessionContants;

/**
 * session工具类
 * @author allen
 *
 */
public abstract class SessionUtils {
	
	/**
	 * 获取当前受shiro控制的Session
	 * @return
	 */
	public static Session getCurrentSession(){
		Subject subject = SecurityUtils.getSubject();
		return subject.getSession();
	}
	
	/**
	 * 从受shiro控制的session中获取对象
	 * @param key
	 * @return
	 */
	public static Object getObjectFromSession(String key){
		Session session = getCurrentSession();
		return session.getAttribute(key);
	}
	
	/**
	 *  往session中存入对象
	 * @param key 键
	 * @param value 值
	 */
	public static void addObjectToSession(Object key,Object value){
		Session session = getCurrentSession();
		session.setAttribute(key, value);
	}
	
	public static void removeAttribute(String key)
	{
		Session session = getCurrentSession();
		session.removeAttribute(key);
	}
	
	/**
	 * 清除session中的值
	 */
	public static void clearSession(){
		Session session = getCurrentSession();
		session.removeAttribute(SessionContants.CURRENT_USER);
		session.removeAttribute(SessionContants.CURRENT_RESOURCES);
		session.removeAttribute(SessionContants.CURRENT_ROLES);
		session.removeAttribute(SessionContants.CURRENT_SYSTEM);
		session.removeAttribute(SessionContants.CURRENT_USER_GROUP);
	}
}
