package org.jflow.framework.controller.wf.workopt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jflow.framework.common.model.ServAccepterModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import BP.WF.Glo;
import BP.WF.Template.PubLib.NodeFormType;

@Controller
@RequestMapping("/WF/WorkOpt")
public class WorkOptController {
	/**
	 * 日志对象
	 */
	protected final Log logger = LogFactory.getLog(getClass());

	@RequestMapping(value = "/ReturnWork", method = RequestMethod.POST)
	public void returnWork(HttpServletRequest request, HttpServletResponse response) {
		// model.addAttribute("result", "用户不存在");
		Map<String, String> map = new HashMap<String, String>();
		String btnId = request.getParameter("BtnID");
		int FK_Node = Integer.valueOf(request.getParameter("FK_Node"));
		long FID = Long.valueOf(request.getParameter("FID"));
		long WorkID = Long.valueOf(request.getParameter("WorkID"));
		String FK_Flow = request.getParameter("FK_Flow");
		if (btnId.equals("Btn_Cancel")) {

			BP.WF.Template.Node curNd = new BP.WF.Template.Node(FK_Node);
			if (curNd.getFormType() == NodeFormType.SheetTree) {
				try {
					response.getOutputStream().write("<script>window.close();</script>".getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.info("ERROR", e);
				}
				// return null;
			} else {
				String url = Glo.getCCFlowAppPath() + "WF/MyFlow.jsp?" + "FK_Flow=" + FK_Flow + "&WorkID=" + WorkID
						+ "&FK_Node=" + FK_Node + "&FID=" + FID;
				try {
					response.sendRedirect(url);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.info("ERROR", e);
				}
			}
		} else if (btnId.equals("Btn_OK")) {
			String returnInfo = request.getParameter("TB_Doc");
			String reNodeEmp = request.getParameter("DDL1");
			boolean IsBackTracking = false;
			String CB_IsBackTracking = request.getParameter("CB_IsBackTracking");
			if (CB_IsBackTracking != null && (CB_IsBackTracking.equals("1") || CB_IsBackTracking.equals("on")))
				IsBackTracking = true;
			String[] strs = reNodeEmp.split("@");
			// 执行退回api.
			String rInfo;
			try {
				rInfo = BP.WF.Dev2Interface.Node_ReturnWork(FK_Flow, WorkID, FID, FK_Node, Integer.valueOf(strs[0]),
						strs[1], returnInfo, IsBackTracking);
				String url = Glo.getCCFlowAppPath() + "WF/MyFlowInfo.jsp";
				request.getSession().setAttribute("info", rInfo);
				response.sendRedirect(url);
				// map.put("showMessage", rInfo);
				// ModelAndView mv = new ModelAndView("/WF/ShowMessage");
				// mv.addObject("showMessage", rInfo);
				// return mv;
			} catch (Exception e) {
				try {
					request.getSession().setAttribute("info", e.getMessage());
					response.sendRedirect(Glo.getCCFlowAppPath() + "WF/Comm/Port/ToErrorPage.jsp");
				} catch (IOException io) {

				}
			}

		}
		// return map;
		// return new ModelAndView("redirect:/admin/systemChoose.do");
		// return new ModelAndView("admin/Managelogin");
		// return null;

	}

	@RequestMapping(value = "/ServAccepterController")
	public String ProcessRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ServAccepterModel ser = new ServAccepterModel();
		ser.setFK_Node(req.getParameter("FK_Node"));
		ser.setFK_Station(req.getParameter("StationID"));
		ser.setReq(req);
		ser.setRes(res);
		ser.setWorkID(req.getParameter("WorkID"));
		ser.setFK_Dept(req.getParameter("DeptId"));
		ser.setName(req.getParameter("name"));
		ser.setPage(Integer.parseInt(req.getParameter("page")));
		ser.setRows(Integer.parseInt(req.getParameter("rows")));
		return ser.ProcessRequest(res, req.getParameter("type").toString());
	}

}