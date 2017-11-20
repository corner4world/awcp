package cn.org.awcp.venson.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.org.awcp.venson.controller.base.ControllerHelper;
import cn.org.awcp.venson.controller.base.ReturnResult;
import cn.org.awcp.venson.controller.base.StatusCode;
import cn.org.awcp.venson.exception.PlatformException;

/**
 * spring mvc 异常处理器
 * 
 * @author Venson
 *
 */
@Component
public class ExceptionHandler implements HandlerExceptionResolver {
	/**
	 * 日志对象
	 */
	private static Log logger = LogFactory.getLog(ExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object obj,
			Exception ex) {
		ReturnResult result = ReturnResult.get();
		handler(ex, result);
		try {
			ControllerHelper.renderJSON(null, result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ModelAndView();
	}

	public static void handler(Exception ex, ReturnResult result) {
		if (ex instanceof MaxUploadSizeExceededException) {
			result.setStatus(StatusCode.FAIL.setMessage("文件过大，请重新上传"));

		} else if (ex instanceof UncategorizedSQLException) {
			result.setStatus(StatusCode.NO_ACCESS.setMessage("检测到非法字符，禁止访问"));
		} else if (ex instanceof PlatformException) {
			result.setStatus(StatusCode.FAIL.setMessage(ex.getMessage()));
		} else {
			logger.debug("ERROR", ex);
			result.setStatus(StatusCode.FAIL.setMessage("服务器出错啦")).setData(ex.toString());
		}
	}
}
