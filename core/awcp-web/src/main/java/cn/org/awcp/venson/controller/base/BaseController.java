package cn.org.awcp.venson.controller.base;

import cn.org.awcp.core.utils.SessionUtils;
import cn.org.awcp.core.utils.constants.SessionContants;
import cn.org.awcp.unit.vo.PunUserBaseInfoVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.Map.Entry;

public abstract class BaseController {

	protected Log logger = LogFactory.getLog(getClass());

	protected Map<String, Object> wrapMap(Map<String, String[]> map, String... filters) {
		Map<String, Object> param = new HashMap<>(map.size());
		for (Entry<String, String[]> entry : map.entrySet()) {
			String key = entry.getKey();
			List<String> filtersList = new ArrayList<String>();
			Collections.addAll(filtersList, "privilegesID");
			if (filters != null) {
				Collections.addAll(filtersList, filters);
			}
			if (!filtersList.contains(key)) {
				// 添加参数，取第一个value值
				param.put(entry.getKey(), entry.getValue()[0]);
			}
		}
		return param;
	}

	protected Map<String, String> mapObj2Str(Map<String, Object> map) {
		return (Map) map;

	}

	public static final char UNDERLINE = '_';

	// 驼峰转下划线
	public static String camelToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(Character.toLowerCase(c));
			}
		}
		return sb.toString();
	}

	public static String underlineToCamel(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	protected String getMessage(HttpServletRequest request, String code) {
		RequestContext context = new RequestContext(request);
		return context.getMessage(code);
	}

	protected PunUserBaseInfoVO getUser() {
		return (PunUserBaseInfoVO) SessionUtils.getObjectFromSession(SessionContants.CURRENT_USER);
	}

	protected Long getUserId() {
		if (getUser() == null) {
			return null;
		}
		return getUser().getUserId();
	}

}
