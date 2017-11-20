package cn.org.awcp.venson.controller.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.org.awcp.formdesigner.application.vo.DocumentVO;
import cn.org.awcp.formdesigner.application.vo.DynamicPageVO;

public final class ControllerContext {

	private static final ThreadLocal<HttpServletRequest> request_threadLocal = new ThreadLocal<HttpServletRequest>();
	private static final ThreadLocal<HttpServletResponse> reponse_threadLocal = new ThreadLocal<HttpServletResponse>();
	private static final ThreadLocal<DynamicPageVO> page_threadLocal = new ThreadLocal<DynamicPageVO>();
	private static final ThreadLocal<DocumentVO> doc_threadLocal = new ThreadLocal<DocumentVO>();
	private static final ThreadLocal<ReturnResult> result_threadLocal = new ThreadLocal<ReturnResult>();

	public static void setRequest(HttpServletRequest request) {
		request_threadLocal.set(request);
	}

	public static HttpServletRequest getRequest() {
		return request_threadLocal.get();
	}

	public static void removeRequest() {
		request_threadLocal.remove();
	}

	public static HttpSession getSession() {
		return request_threadLocal.get().getSession();
	}

	public static void setResponse(HttpServletResponse response) {
		reponse_threadLocal.set(response);
	}

	public static HttpServletResponse getResponse() {
		return reponse_threadLocal.get();
	}

	public static void removeResponse() {
		reponse_threadLocal.remove();
	}

	public static void setDoc(DocumentVO docVO) {
		doc_threadLocal.set(docVO);
	}

	public static DocumentVO getDoc() {
		return doc_threadLocal.get();
	}

	public static void setPage(DynamicPageVO pageVO) {
		page_threadLocal.set(pageVO);
	}

	public static DynamicPageVO getPage() {
		return page_threadLocal.get();
	}

	public static void setResult(ReturnResult result) {
		result_threadLocal.set(result);
	}

	public static ReturnResult getResult() {
		return result_threadLocal.get();
	}

}