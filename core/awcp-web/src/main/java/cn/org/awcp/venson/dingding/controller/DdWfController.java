package cn.org.awcp.venson.dingding.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.org.awcp.core.domain.BaseExample;
import cn.org.awcp.formdesigner.application.service.DocumentService;
import cn.org.awcp.formdesigner.application.service.FormdesignerService;
import cn.org.awcp.formdesigner.application.service.StoreService;
import cn.org.awcp.formdesigner.application.vo.DocumentVO;
import cn.org.awcp.formdesigner.application.vo.DynamicPageVO;
import cn.org.awcp.formdesigner.application.vo.StoreVO;
import cn.org.awcp.formdesigner.core.domain.design.context.act.PageAct;
import cn.org.awcp.formdesigner.utils.ScriptEngineUtils;
import cn.org.awcp.venson.controller.base.ControllerHelper;

/**
 * 流程表单页面显示
 * 
 * @author yqtao
 *
 */
@Controller
@RequestMapping(value = "/dingding")
public class DdWfController {

	/**
	 * 日志对象
	 */
	protected static final Log logger = LogFactory.getLog(DdWfController.class);

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private FormdesignerService formdesignerServiceImpl;

	@Autowired
	private StoreService storeServiceImpl;

	@Autowired
	private DocumentService documentServiceImpl;

	@RequestMapping(value = "/wf/openTask")
	public ModelAndView openTask(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("dingding/wfDetail");
		String dynamicPageId = request.getParameter("dynamicPageId");
		String entryId = request.getParameter("FK_Node");
		String flowTempleteId = request.getParameter("FK_Flow");
		String fid = request.getParameter("FID");
		fid = StringUtils.isNumeric(fid) ? fid : "0";
		String workItemId = request.getParameter("WorkID");
		DocumentVO docVo = null;
		DynamicPageVO pageVO = null;
		Map<String, String> map = new HashMap<String, String>();
		ScriptEngine engine = null;
		try {
			docVo = documentServiceImpl.findDocByWorkItemId(flowTempleteId, workItemId);
			docVo.setDynamicPageId(dynamicPageId);
			docVo.setFlowTempleteId(flowTempleteId);
			docVo.setWorkItemId(workItemId);
			docVo.setEntryId(entryId);
			docVo.setWorkflowId(flowTempleteId);
			docVo.setFid(fid);
			if (docVo.getId() != null) {
				docVo.setUpdate(true);
				pageVO = formdesignerServiceImpl.findById(docVo.getDynamicPageId());
				docVo.setDynamicPageId(pageVO.getId().toString());
				docVo.setDynamicPageName(pageVO.getName());
			} else {
				pageVO = formdesignerServiceImpl.findById(dynamicPageId);
				docVo.setUpdate(false);
				docVo.setDynamicPageId(pageVO.getId().toString());
				docVo.setDynamicPageName(pageVO.getName());
			}

			// 页面传过来的参数
			Map<String, String[]> parameterMap = request.getParameterMap();
			for (String key : parameterMap.keySet()) {
				map.put(key, StringUtils.join(parameterMap.get(key), ";"));
			}
			docVo.setRequestParams(map);

			// 脚本执行引擎
			engine = ScriptEngineUtils.getScriptEngine(docVo, pageVO);
			engine.put("request", request);
			engine.put("session", request.getSession());
			Map<String, List<Map<String, String>>> dataMap = documentServiceImpl.initDocumentDataFlow(1, 1, docVo,
					engine, pageVO);
			BaseExample baseExample = new BaseExample();
			baseExample.createCriteria().andEqualTo("dynamicPage_id", pageVO.getId()).andLike("code",
					StoreService.COMPONENT_CODE + "%");
			List<StoreVO> stores = storeServiceImpl.selectPagedByExample(baseExample, 1, Integer.MAX_VALUE, "T_ORDER");
			String dateSource = JSONObject.parseObject(stores.get(0).getContent()).getString("dataItemCode")
					.split("\\.")[0];
			Map<String, String> temp = dataMap.get(dateSource + "_list").get(0); // 表单数据

			// 审批发起者信息
			Map<String, Object> user = getUserInfo(workItemId);
			if (fid != null && !fid.equals("0")) {
				workItemId = fid;
			}
			String sql = "SELECT GROUP_CONCAT(DISTINCT FK_EmpText) FROM wf_generworkerlist WHERE IsPass=0 AND (workid=? or FID=?) ";
			String nextStepPerson = jdbcTemplate.queryForObject(sql, String.class, workItemId, workItemId);
			if (StringUtils.isNotBlank(nextStepPerson)) {
				user.put("approved", 0);
				user.put("nextStepPerson", nextStepPerson);
			} else {
				user.put("approved", jdbcTemplate.queryForObject("select WFState from wf_generworkflow where workid=?",
						Integer.class, workItemId));
			}
			mav.addObject("user", user);

			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			boolean isCH = ControllerHelper.getLang() == Locale.SIMPLIFIED_CHINESE;
			for (StoreVO store : stores) {
				JSONObject obj = JSONObject.parseObject(store.getContent());
				Map<String, Object> m = new HashMap<String, Object>();
				String dataItemCode = obj.getString("dataItemCode");
				String value = temp.get(dataItemCode.split("\\.")[1]);
				String componentType = obj.getString("componentType");
				if (StringUtils.isNotBlank(value)) {
					if (isCH) {
						m.put("title", obj.getString("title"));
					} else {
						m.put("title", obj.getString("enTitle"));
					}
					switch (componentType) {
					case "1001":
						m.put("value", value);
						m.put("type", "textfield");
						break;
					case "1002":
						m.put("value", value);
						m.put("type", "datefield");
						break;
					case "1005":
						m.put("value", value);
						m.put("type", "textarea");
						break;
					case "1006":
						String optionScript = obj.getString("optionScript");
						m.put("value", value);
						if (StringUtils.isNotBlank(optionScript)) {
							String ret = null;
							try {
								ret = (String) engine.eval(optionScript);
							} catch (ScriptException e) {
								e.printStackTrace();
							}
							if (StringUtils.isNotBlank(ret)) {
								m.put("value", DdUtil.getVal(value, ret));
							} else {
								m.put("value", value);
							}
						}
						m.put("type", "selectfield");
						break;
					case "1011":
						m.put("value", getFile(value));
						m.put("type", "file");
						break;
					case "1016":
						m.put("value", new ArrayList<String>(Arrays.asList(value.split(","))));
						m.put("type", "img");
						break;
					case "1101":
						String funType = obj.getString("funType");
						if ("0".equals(funType)) {
							m.put("type", "textarea");
							m.put("value", value);
						} else {
							continue;
						}
					}
					data.add(m);
				}
			}
			mav.addObject("data", data);

			List<Map<String, Object>> acts = new ArrayList<Map<String, Object>>();
			BaseExample actExample = new BaseExample();
			actExample.createCriteria().andEqualTo("dynamicPage_id", pageVO.getId()).andLike("code",
					StoreService.PAGEACT_CODE + "%");
			stores = storeServiceImpl.selectPagedByExample(actExample, 1, Integer.MAX_VALUE, "T_ORDER");
			if (stores != null && stores.size() > 0) {
				for (StoreVO store : stores) {
					PageAct act = JSON.parseObject(store.getContent(), PageAct.class);
					String hiddenScript = StringEscapeUtils.unescapeHtml4(act.getHiddenScript());
					Boolean hiddenStatus = (Boolean) engine.eval(hiddenScript); // 执行隐藏脚本
					if (hiddenStatus == null) {
						hiddenStatus = false;
					}
					if (!hiddenStatus) {
						Map<String, Object> m = new HashMap<String, Object>();
						if (isCH) {
							m.put("name", act.getName());
						} else {
							m.put("name", act.getEnName());
						}
						m.put("id", store.getId());
						m.put("clientScript", StringEscapeUtils.unescapeHtml4(act.getClientScript()));
						acts.add(m);
					}
				}
			}
			mav.addObject("acts", acts);
			mav.addObject("vo", docVo);
			mav.addObject("workLogs", getWorkLog(workItemId, nextStepPerson));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	// 用户信息
	private Map<String, Object> getUserInfo(String workId) {
		String sql = "select starter creator,name,USER_HEAD_IMG as img,GROUP_CONCAT(t3.GROUP_CH_NAME) as groups from p_un_user_base_info t1 "
				+ " left join p_un_user_group t2 on t1.USER_ID=t2.USER_ID left join p_un_group t3 on t2.GROUP_ID=t3.GROUP_ID left join wf_generworkflow d on t1.USER_ID_CARD_NUMBER=d.starter"
				+ " where workId=?";
		Map<String, Object> ret = jdbcTemplate.queryForMap(sql, workId);
		String img = (String) ret.get("img");
		String name = (String) ret.get("name");
		DdUtil.dealHeadImg(ret, name, img);
		return ret;
	}

	// 处理文件
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map> getFile(String value) {
		List<Map> list = JSONObject.parseArray(value, Map.class);
		for (Map file : list) {
			int fileSize = (int) file.get("fileSize");
			file.put("fileSize", String.format("%.2f", fileSize / 1024.0));
			String fileType = (String) file.get("fileType");
			file.put("css", DdUtil.getCss(fileType));
			file.put("json", JSONObject.toJSONString(file));
		}
		return list;
	}

	// 流程日志
	private List<Map<String, Object>> getWorkLog(String workItemId, String nextStepPerson) {
		String sql = "SELECT b.name,a.creator,a.state,b.user_head_img,a.content,date_format(a.send_time,'%Y-%m-%d %H:%i') as date,a.current_node "
				+ "FROM p_fm_work_logs a LEFT JOIN p_un_user_base_info b ON a.creator=b.user_id_card_number "
				+ "WHERE work_id=? ORDER BY CURRENT_NODE,IFNULL(send_time,NOW())";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, workItemId);
		String currentUserId = ControllerHelper.getUser().getUserIdCardNumber();
		boolean isCH = ControllerHelper.getLang() == Locale.SIMPLIFIED_CHINESE;
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			String creator = (String) map.get("creator");
			String name = (String) map.get("name");
			String img = (String) map.get("user_head_img");
			// 处理用户头像
			DdUtil.dealHeadImg(map, name, img);
			// 判断当前审批人
			if (StringUtils.isNotBlank(nextStepPerson) && nextStepPerson.contains(name)) {
				map.put("state", 2);
			}
			// 判断是否是当前处理人
			if (currentUserId.equals(creator)) {
				if (isCH) {
					map.put("name", "我");
				} else {
					map.put("name", "me");
				}
			}
			// 设置样式
			if (i == 0) {
				map.put("state", -1);
				map.put("current_node", -1);
				map.put("content", "");
				map.put("css", "submit");
			} else if (i == list.size() - 1) {
				map.put("css", map.get("css") + " last");
			} else {
				map.put("css", "next");
			}
		}
		return list;
	}
}
