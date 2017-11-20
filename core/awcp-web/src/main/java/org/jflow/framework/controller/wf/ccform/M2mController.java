package org.jflow.framework.controller.wf.ccform;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jflow.framework.controller.wf.workopt.BaseController;
import org.jflow.framework.system.ui.core.BaseWebControl;
import org.jflow.framework.system.ui.core.CheckBox;
import org.jflow.framework.system.ui.core.HtmlUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import BP.DA.DataRow;
import BP.DA.DataTable;
import BP.Sys.Frm.M2M;
import BP.Sys.Frm.MapM2M;
import BP.Tools.StringHelper;
import TL.ContextHolderUtils;

/**
 * 多对多处理事件
 * 
 * @author xiaozhoupeng 20150116
 */
@Controller
@RequestMapping("/WF/CCForm")
@Scope("request")
public class M2mController extends BaseController {
	/**
	 * 日志对象
	 */
	protected final Log logger = LogFactory.getLog(getClass());

	public final String getNoOfObj() {
		if (ContextHolderUtils.getRequest().getParameter("NoOfObj") == null)
			return "";
		return ContextHolderUtils.getRequest().getParameter("NoOfObj");
	}

	public final String getFK_MapData() {
		String fk_mapdata = ContextHolderUtils.getRequest().getParameter("FK_MapData");
		if (StringHelper.isNullOrEmpty(fk_mapdata)) {
			fk_mapdata = "ND" + getFK_Node();
		}
		return fk_mapdata;
	}

	@RequestMapping(value = "/M2MSave", method = RequestMethod.POST)
	private void execute(HttpServletRequest request, HttpServletResponse response) {
		long OID = Long.parseLong(request.getParameter("OID"));

		if (OID == 0) {
			return;
		}

		MapM2M mapM2M = new MapM2M(this.getFK_MapData(), this.getNoOfObj());

		M2M m2m = new M2M();
		m2m.setFK_MapData(this.getFK_MapData());
		m2m.setEnOID(OID);
		m2m.setM2MNo(this.getNoOfObj());

		DataTable dtObj = BP.DA.DBAccess.RunSQLReturnTable(mapM2M.getDBOfObjsRun());
		String str = ",";
		String strT = "";
		int numOfselected = 0;
		String body = "";
		try {
			body = HtmlUtils.getBodyString(request);
		} catch (IOException e) {
			logger.info("ERROR", e);
		}
		HashMap<String, BaseWebControl> ctrlMap = HtmlUtils.httpParser(body, true);
		for (DataRow dr : dtObj.Rows) {
			String id = dr.get(0).toString();
			CheckBox cb = (CheckBox) ctrlMap.get("CB_" + id);
			if (cb == null) {
				continue;
			}

			if (cb.getChecked() == false) {
				continue;
			}

			str += id + ",";
			strT += "@" + id + "," + cb.getText();
			numOfselected++;
		}
		m2m.setVals(str);
		m2m.setValsName(strT);
		m2m.InitMyPK();
		m2m.setNumSelected(numOfselected);
		m2m.Save();
	}
}
