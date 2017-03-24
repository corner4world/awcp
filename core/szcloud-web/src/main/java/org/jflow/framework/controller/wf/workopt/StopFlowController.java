package org.jflow.framework.controller.wf.workopt;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/WF/WorkOpt")
public class StopFlowController
{
	private HttpServletRequest _request = null;
	private HttpServletResponse _response = null;
	
	public final String getFK_Flow()
	{
		return _request.getParameter("FK_Flow");
	}
	public final String getStopReason()
	{
		return _request.getParameter("TextBox1");
	}
	public final long getWorkID()
	{
		return Long.parseLong(_request.getParameter("WorkID"));
	}
	public final long getFID()
	{
		return Long.parseLong(_request.getParameter("FID"));
	}
	public final int getFK_Node()
	{
		return Integer.parseInt(_request.getParameter("FK_Node"));
	}

	@RequestMapping(value = "/StopFlow", method = RequestMethod.POST)
	public final ModelAndView execute(HttpServletRequest request,
			HttpServletResponse response)
	{
		_request = request;
		_response = response;
		String infoEnd = BP.WF.Dev2Interface.Flow_DoFlowOverByCoercion(this.getFK_Flow(), this.getFK_Node(), this.getWorkID(), this.getFID(), this.getStopReason());
		//Map result = new HashMap();
		//result.put("info", "结束流程提示:" + infoEnd);
		ModelAndView model = new ModelAndView();
		model.addObject("showMessage","结束流程提示:" + infoEnd);
		model.setViewName("/WF/ShowMessage");
		return model;
	}

}
