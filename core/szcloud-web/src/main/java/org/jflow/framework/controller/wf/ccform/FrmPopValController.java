package org.jflow.framework.controller.wf.ccform;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jflow.framework.common.model.TempObject;
import org.jflow.framework.controller.wf.workopt.BaseController;
import org.jflow.framework.system.ui.core.BaseWebControl;
import org.jflow.framework.system.ui.core.CheckBox;
import org.jflow.framework.system.ui.core.HtmlUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import BP.Sys.Frm.MapExt;

@Controller
@RequestMapping("/WF/CCForm")
public class FrmPopValController extends BaseController {
	/**
	 * 日志对象
	 */
	protected final Log logger = LogFactory.getLog(getClass());

	@RequestMapping(value = "/btn_Click", method = RequestMethod.POST)
	public void btn_Click(HttpServletRequest request, HttpServletResponse response, TempObject object) {
		if (object.getBtnName().equals("Cancel")) {
			try {
				this.winClose(response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.info("ERROR", e);
			}
			return;
		}

		MapExt me = new MapExt(this.getFK_MapExt());

		int popValFormat = me.getPopValFormat();
		String val = "";
		HashMap<String, BaseWebControl> controls = HtmlUtils.httpParser(object.getFormHtml(), true);
		for (Map.Entry<String, BaseWebControl> ctl : controls.entrySet()) {
			CheckBox cb = (CheckBox) ctl.getValue();
			// CheckBox cb = (CheckBox)((ctl instanceof CheckBox) ? ctl : null);
			if (cb == null) {
				continue;
			}
			if (cb.getId().contains("CBs_")) {
				continue;
			}
			if (cb.getChecked() == false) {
				continue;
			}
			String text = cb.getText().replace("<font color=green>", "");
			text = cb.getText().replace("</font>", "");
			switch (popValFormat) {
			case 0: // 仅仅编号
				val += "," + cb.getId().replace("CB_", "");
				break;
			case 1: // 仅名称
				val += "," + text;
				break;
			case 2: // 编号与名称
				val += cb.getId().replace("CB_", "") + "," + text + ";";
				break;
			default:
				break;
			}
		}
		val = val.replace("<font color=green>", "");
		val = val.replace("</font>", "");

		if (val.length() > 2 && popValFormat != 2) {
			val = val.substring(1);
		}
		try {
			this.winCloseWithMsg1(response, val);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("ERROR", e);
		}

	}

}
