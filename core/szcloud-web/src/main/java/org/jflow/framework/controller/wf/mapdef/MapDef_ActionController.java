package org.jflow.framework.controller.wf.mapdef;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import BP.Sys.Frm.EventDoType;
import BP.Sys.Frm.FrmEvent;
import BP.WF.XML.EventListDtl;
import BP.WF.XML.EventListDtls;
import BP.XML.XmlEn;

/**
 * 
 * @author ly
 * @data 20150430 New Add
 */
@Controller
@RequestMapping(value = "/WF/MapDef")
public class MapDef_ActionController {
	/**
	 * 日志对象
	 */
	protected final Log logger = LogFactory.getLog(getClass());
	StringBuffer pub2 = new StringBuffer();

	/**
	 * Save Event
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/ActionBtn_Click", method = RequestMethod.POST)
	public void btn_Click(HttpServletRequest request, HttpServletResponse response) {

		String fk_MapData = request.getParameter("FK_MapData");
		String event = request.getParameter("Event");

		FrmEvent fe = new FrmEvent();
		fe.setMyPK(fk_MapData + "_" + event);
		fe.RetrieveFromDBSources();

		EventListDtls xmls = new EventListDtls();
		xmls.RetrieveAll();

		for (XmlEn obj : EventListDtls.convertXmlEns(xmls)) {
			EventListDtl xml = (EventListDtl) obj;
			if (!event.equals(xml.getNo())) {
				continue;
			}

			String doc = request.getParameter("TB_Doc");
			if (doc.equals("") || doc == null) {
				if (fe.getMyPK().length() > 3) {
					fe.Delete();
				}
				continue;
			}

			fe.setMyPK(fk_MapData + "_" + xml.getNo());
			fe.setDoDoc(doc);
			fe.setFK_Event(xml.getNo());
			fe.setFK_MapData(fk_MapData);

			int eventDoType = Integer.parseInt(request.getParameter("DDL_EventDoType"));
			fe.setHisDoType(EventDoType.forValue(eventDoType));

			fe.setMsgOKString(request.getParameter("TB_MsgOK"));
			fe.setMsgErrorString(request.getParameter("TB_MsgErr"));

			fe.Save();

			try {
				response.sendRedirect(
						"Action.jsp?FK_MapData=" + fk_MapData + "&MyPK=" + fe.getMyPK() + "&Event=" + xml.getNo());
			} catch (IOException e) {
				logger.info("ERROR", e);
			}
			return;
		}

	}

}
