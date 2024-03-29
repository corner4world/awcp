package cn.org.awcp.formdesigner.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;

import cn.org.awcp.base.BaseController;
import cn.org.awcp.core.common.exception.MRTException;
import cn.org.awcp.core.utils.SessionUtils;
import cn.org.awcp.core.utils.constants.SessionContants;
import cn.org.awcp.formdesigner.application.service.FormdesignerService;
import cn.org.awcp.formdesigner.application.vo.DynamicPageVO;
import cn.org.awcp.formdesigner.application.vo.WorkFlowTemplateVO;
import cn.org.awcp.formdesigner.application.vo.WorkflowNodeVO;
import cn.org.awcp.formdesigner.application.vo.WorkflowVO;
import cn.org.awcp.formdesigner.application.vo.WorkflowVariableVO;
import cn.org.awcp.unit.vo.PunSystemVO;

@Controller
@RequestMapping("/fd/workflow")
public class PfmWorkflowController extends BaseController {

	@Autowired
	@Qualifier("formdesignerServiceImpl")
	private FormdesignerService dynamicPageService;// 动态表单Service

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Qualifier(value = "jdbcTemplate")
	@Resource
	private JdbcTemplate sqlServerJdbcTemplate;

	/**
	 * 获取所有绑定页面的节点
	 * 
	 * @param pageId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBindNodeList")
	public List<WorkflowNodeVO> getBindNodeList(String pageId) {
		List<WorkflowNodeVO> list = new ArrayList<WorkflowNodeVO>();
		DynamicPageVO dVo = dynamicPageService.findById(pageId);
		if (null == dVo) {
			throw new MRTException();
		}
		// 流程信息
		String jsonStr = dVo.getWorkflowNodeInfo();
		JSONObject o = StringUtils.isNotEmpty(jsonStr) ? JSON.parseObject(jsonStr) : new JSONObject();

		for (Object obj : o.values()) {
			logger.debug(obj.toString());
			HashMap<String, String> map = (HashMap<String, String>) StringToMap(obj.toString());
			WorkflowNodeVO vo = new WorkflowNodeVO();
			vo.setId(map.get("id"));
			vo.setName(map.get("name"));
			vo.setPriority(Integer.parseInt(map.get("priority")));
			vo.setWorkflowId(map.get("workflowId"));
			vo.setWorkflowName(map.get("workflowName"));
			vo.setCyclostyleID(map.get("cyclostyleID"));
			list.add(vo);
		}
		return list;
	}

	public static Map<String, String> StringToMap(String mapText) {
		String mapString1 = mapText.replaceAll("\"", "");
		String mapString2 = mapString1.replace("{", "");
		String mapString3 = mapString2.replace("}", "");
		String[] items = mapString3.split(",");
		Map<String, String> map = new HashMap<String, String>();
		for (String item : items) {
			String[] str = item.split(":");
			map.put(str[0], str[1]);
		}
		return map;
	}

	/**
	 * 查询已关联的流程结点
	 * 
	 * @param pageId
	 *            动态表单ID
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(String pageId) {
		ModelAndView mv = new ModelAndView();
		try {
			DynamicPageVO dVo = dynamicPageService.findById(pageId);
			if (null == dVo) {
				throw new MRTException();
			}
			// 流程信息
			String jsonStr = dVo.getWorkflowNodeInfo();
			JSONObject o = StringUtils.isNotEmpty(jsonStr) ? JSON.parseObject(jsonStr) : new JSONObject();
			mv.addObject("nVos", o.values());
		} catch (Exception e) {
			logger.info("ERROR", e);
			mv.addObject("result", "系统异常");
		}
		mv.addObject("pageId", pageId);
		mv.setViewName("formdesigner/page/tabs/workflow");
		return mv;
	}

	/**
	 * 绑定节点页面 上面是选择流程，下面是节点列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/notBindNodeList")
	public ModelAndView notBindNodeList(String pageId, String workflowId, String workflowName) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("formdesigner/page/workflow/bind-workflow-node");
		mv.addObject("workflowId", workflowId);
		mv.addObject("workflowName", workflowName);
		mv.addObject("pageId", pageId);
		if (StringUtils.isNotBlank(workflowId)) {
			Object obj = SessionUtils.getObjectFromSession(SessionContants.TARGET_SYSTEM);
			if (obj instanceof PunSystemVO) {
				// PunSystemVO system = (PunSystemVO)obj;
				// 根据流程id查询节点信息
				try {
					String sql = "SELECT NAME,NODEID FROM WF_NODE WHERE FK_FLOW='" + workflowId + "'";
					List<Map<String, Object>> ls = jdbcTemplate.queryForList(sql);

					mv.addObject("vos", ls);
				} catch (Exception e) {
					logger.info("ERROR", e);
				}
			}
		}
		return mv;
	}

	/**
	 * 流程列表
	 * 
	 * 通过流程名称和流程类型模糊查询流程列表，分页显示
	 * 
	 * 需要把已经绑定的过滤掉吗？
	 * 
	 * @param workflowName
	 *            流程名称
	 * @param categoryId
	 *            流程类型
	 * @param currentPage
	 *            当前第几页
	 * @param pageSize
	 *            每页显示记录数
	 * @return
	 */
	@RequestMapping(value = "/queryWorkflowList")
	public ModelAndView queryWorkflowList(String workflowName,
			@RequestParam(required = false, defaultValue = "0") String categoryId,
			@RequestParam(required = false, defaultValue = "1") int currentPage,
			@RequestParam(required = false, defaultValue = "10") int pageSize) {
		ModelAndView mv = new ModelAndView();
		if (currentPage < 1) {
			currentPage = 1;
		}
		Object obj = SessionUtils.getObjectFromSession(SessionContants.TARGET_SYSTEM);
		if (obj instanceof PunSystemVO) {
			// TODO 接口sysId可改为string
			// List<BpmCategoryVO> categories =
			// workflowService.findProcessCategoryList(1, Integer.MAX_VALUE,
			// Integer.parseInt(system.getSysId().toString()));
			// mv.addObject("categories", categories);
			mv.addObject("categories", null);
			mv.setViewName("formdesigner/page/workflow/workflow-list-pop");
			// 查询
			// TODO 接口sysId可改为string
			// PageList<ProcessInfoVO> list =
			// workflowService.findProcessByCatalogId(currentPage, pageSize,
			// workflowName, Integer.parseInt(system.getSysId().toString()),
			// Long.parseLong(categoryId));
			//
			// mv.addObject("vos", list);
			mv.addObject("vos", null);

			mv.addObject("currentPage", currentPage);
			mv.addObject("pageSize", pageSize);
			mv.addObject("workflowName", workflowName);
			mv.addObject("categoryName", categoryId);

		}
		return mv;
	}

	/**
	 * 获取公文模版列表
	 * 
	 * @return
	 */
	@RequestMapping("templateList")
	public ModelAndView getCyclostyleList(String workflowName, String type,
			@RequestParam(required = false, defaultValue = "1") int currentPage,
			@RequestParam(required = false, defaultValue = "5") int pageSize, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			if (currentPage < 1) {
				currentPage = 1;
			}

			// 获取流程列表
			String sql = "SELECT WF_FLOW.NO,WF_FLOW.NAME,WF_FLOWSORT.NAME AS FLOWSORT FROM WF_FLOW left join WF_FLOWSORT on WF_FLOW.FK_FLOWSORT=WF_FLOWSORT.NO WHERE 1=1";

			if (type != null && !"".equals(type) && Integer.parseInt(type) != 0) {
				sql += " AND WF_FLOWSORT.NO='" + type + "'";
			}

			sql += " limit " + (currentPage - 1) * pageSize + "," + pageSize;
			List<Map<String, Object>> resultSet = jdbcTemplate.queryForList(sql);
			// 获取流程列表的数量
			String countSql = "SELECT count(*) FROM WF_FLOW";
			long count = jdbcTemplate.queryForObject(countSql,Long.class);
			Paginator paginator = new Paginator(currentPage, pageSize, (int) count);
			PageList<Map<String, Object>> pageList = new PageList<Map<String, Object>>(resultSet, paginator);

			List<Map<String, Object>> templateTypes = jdbcTemplate.queryForList("SELECT NO,NAME FROM WF_FLOWSORT");

			mv.addObject("vos", pageList);
			mv.addObject("categories", templateTypes);
			mv.addObject("currentPage", currentPage);
			mv.addObject("pageSize", pageSize);
			if ("2".equals(type)) {
				mv.setViewName("formdesigner/page/workflow/workflow-list-pop_suggestion");
			} else {
				mv.setViewName("formdesigner/page/workflow/workflow-list-pop");
			}
			return mv;
		} catch (Exception e) {
			logger.info("ERROR", e);
			return null;
		}
	}

	/**
	 * 模板列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("templateList1")
	public ModelAndView templateList(String workflowName,
			@RequestParam(required = false, defaultValue = "1") int currentPage,
			@RequestParam(required = false, defaultValue = "5") int pageSize, HttpServletRequest request)
			throws Exception {

		ModelAndView mv = new ModelAndView();
		if (currentPage < 1) {
			currentPage = 1;
		}
		String templateTypeId = null;
		String templateName = null;
		if (request != null) {
			templateTypeId = request.getParameter("categoryId");
			templateName = request.getParameter("workflowName");
		}

		String countSql = "select count(*) from TemplateDefine t1, WorkType t3  WHERE t1.TypeID = t3.TypeID";
		String sql = "SELECT top " + pageSize
				+ " * from (select row_number() over(order by CreateDate DESC) as rownumber, "
				+ "t1.TemplateID, TemplateName, TemplateCode, TypeName, TemplateIsValid, IsPublicUsing, OrderID, Creator, CreateDate  "
				+ "FROM TemplateDefine t1, WorkType t3  WHERE t1.TypeID = t3.TypeID ";

		if (StringUtils.isNotEmpty(templateTypeId)) {
			sql += " and t1.TypeID = " + templateTypeId + " ";
			countSql += " and t1.TypeID = " + templateTypeId + " ";
		}
		if (StringUtils.isNotEmpty(templateName)) {
			sql += " and TemplateName like '%" + templateName + "%'";
			countSql += " and TemplateName like '%" + templateName + "%'";
		}
		sql += " )a where rownumber > " + (currentPage - 1) * pageSize;
		logger.debug(sql);

		long count = sqlServerJdbcTemplate.queryForObject(countSql,Long.class);
		Paginator paginator = new Paginator(currentPage, pageSize, (int) count);
		List models = sqlServerJdbcTemplate.queryForList(sql);
		PageList<WorkFlowTemplateVO> pageList = new PageList<WorkFlowTemplateVO>(models, paginator);
		List templateTypes = sqlServerJdbcTemplate.queryForList("select TypeId,TypeName from WorkType");
		if (StringUtils.isNotEmpty(templateTypeId)) {
			mv.addObject("categoryId", templateTypeId);
		}
		if (StringUtils.isNotEmpty(templateName)) {
			mv.addObject("workflowName", templateName);
		}
		mv.addObject("vos", pageList);
		mv.addObject("categories", templateTypes);
		mv.addObject("currentPage", currentPage);
		mv.addObject("pageSize", pageSize);
		mv.addObject("workflowName", workflowName);
		mv.setViewName("formdesigner/page/workflow/workflow-list-pop");
		return mv;
	}

	/**
	 * 关联
	 * 
	 * @param vo
	 * @param pageId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "relate")
	public String relate(WorkflowVO vo) {
		JSONObject rtn = new JSONObject();
		try {
			DynamicPageVO dVo = dynamicPageService.findById(vo.getPageId());
			if (null != dVo) {
				// 不为空更新
				String jsonStr = dVo.getWorkflowNodeInfo();
				// 如果是更新，则先转化数据库表中的值为jsonObject，然后删除之前的数据，放入新的数据
				// 如果是新增，则先转化数据库表中的值为jsonObject，然后放入新的数据
				// 放入节点id+_+流程id为key，节点信息为value的map
				JSONObject o = StringUtils.isNotEmpty(jsonStr) ? JSON.parseObject(jsonStr) : new JSONObject();
				for (WorkflowNodeVO node : vo.getNodes()) {
					o.put(node.getId() + "_" + node.getWorkflowId(), node);// 如果有，直接覆盖
				}
				dVo.setWorkflowNodeInfo(JSON.toJSONString(o));
				dynamicPageService.updateWorkflowInfo(dVo);
				rtn.put("result", "1");
				rtn.put("msg", JSON.toJSONString(o.values()));
			} else {
				rtn.put("result", "2");
				rtn.put("msg", "动态页面不存在");
			}
		} catch (Exception e) {
			rtn.put("result", "3");
			rtn.put("msg", "错误");
			logger.info("ERROR", e);
		}
		return rtn.toJSONString();
	}

	/**
	 * @param nodeIds
	 *            nodeid+_+workflowid
	 * @param pageId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delNode")
	public String delNode(String[] nodeIds, String pageId) {
		JSONObject rtn = new JSONObject();
		try {
			DynamicPageVO dVo = dynamicPageService.findById(pageId);
			if (dVo != null) {
				String jsonStr = dVo.getWorkflowNodeInfo();
				JSONObject o = StringUtils.isNotEmpty(jsonStr) ? JSON.parseObject(jsonStr) : new JSONObject();
				for (String id : nodeIds) {
					o.remove(id);
				}
				dVo.setWorkflowNodeInfo(JSON.toJSONString(o));
				dynamicPageService.updateWorkflowInfo(dVo);
				rtn.put("result", "1");
				rtn.put("msg", JSON.toJSONString(o.values()));
			} else {
				rtn.put("result", "2");
				rtn.put("msg", "动态页面不存在");
			}
		} catch (Exception e) {
			rtn.put("result", "3");
			rtn.put("msg", "错误");
			logger.info("ERROR", e);
		}
		return rtn.toJSONString();
	}

	/**
	 * @param nodeId
	 *            nodeid+_+workflowid
	 * @param pageId
	 * @return
	 */
	@RequestMapping(value = "/nodeVariableEdit")
	public ModelAndView nodeVariableEdit(String nodeId, String pageId) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageId", pageId);// 动态表单ID
		mv.addObject("nodeId", nodeId);// 节点ID

		mv.setViewName("formdesigner/page/workflow/workflow-node-vars-list-edit");
		// 查出节点对应的参数列表
		DynamicPageVO dVo = dynamicPageService.findById(pageId);
		if (dVo != null) {
			mv.addObject("dataSourceArray", dVo.getDataJson());
			String jsonStr = dVo.getWorkflowNodeInfo();
			JSONObject o = StringUtils.isNotEmpty(jsonStr) ? JSON.parseObject(jsonStr) : new JSONObject();
			String nodeString = o.getString(nodeId);
			if (StringUtils.isNotBlank(nodeString)) {
				WorkflowNodeVO nvo = JSON.parseObject(nodeString, WorkflowNodeVO.class);
				mv.addObject("wVars", nvo.getVariables());
			}
		}
		return mv;
	}

	/**
	 * 节点配置参数
	 * 
	 * @param nodeId
	 *            nodeid+_+workflowid
	 * @param pageId
	 * @return
	 */
	@RequestMapping(value = "/addNodesParams")
	public ModelAndView addNodesParams(String nodeId, String pageId, String value, String dataSource) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageId", pageId);// 动态表单ID
		mv.addObject("nodeId", nodeId);// 节点ID

		mv.setViewName("formdesigner/page/workflow/workflow-node-vars-list-edit");
		// 查出节点对应的参数列表
		DynamicPageVO dVo = dynamicPageService.findById(pageId);
		if (dVo != null) {
			mv.addObject("dataSourceArray", dVo.getDataJson());
			String jsonStr = dVo.getWorkflowNodeInfo();
			JSONObject o = StringUtils.isNotEmpty(jsonStr) ? JSON.parseObject(jsonStr) : new JSONObject();
			String nodeString = o.getString(nodeId);
			if (StringUtils.isNotBlank(nodeString)) {
				WorkflowNodeVO nvo = JSON.parseObject(nodeString, WorkflowNodeVO.class);
				List<WorkflowVariableVO> wv = new ArrayList<WorkflowVariableVO>();
				WorkflowVariableVO v1 = new WorkflowVariableVO();
				v1.setVariableName(value);
				v1.setValue(dataSource);
				wv.add(v1);
				nvo.setVariables(wv);
				String newo = (String) JSON.toJSON(nvo);
				dVo.setWorkflowNodeInfo(newo);
				dynamicPageService.saveOrUpdate(dVo);
			}
		}
		return mv;
	}

	/**
	 * @param nodeId
	 *            nodeid+_+workflowid
	 * @param pageId
	 * @return
	 */
	@RequestMapping(value = "/delNodeVariable")
	public ModelAndView delNodeVariable(HttpServletRequest request, String nodeId, String pageId) {
		String varName = "";
		try {
			request.setCharacterEncoding("UTF-8");
			varName = new String(request.getParameter("varName").getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.info("ERROR", e);
		}

		ModelAndView mv = new ModelAndView();
		mv.addObject("pageId", pageId);// 动态表单ID
		mv.addObject("nodeId", nodeId);// 节点ID

		mv.setViewName("redirect:/fd/workflow/nodeVariableEdit.do");
		// 查出节点对应的参数列表
		DynamicPageVO dVo = dynamicPageService.findById(pageId);
		if (dVo != null) {
			String jsonStr = dVo.getWorkflowNodeInfo();
			JSONObject o = StringUtils.isNotEmpty(jsonStr) ? JSON.parseObject(jsonStr) : new JSONObject();
			String nodeString = o.getString(nodeId);
			if (StringUtils.isNotBlank(nodeString)) {
				WorkflowNodeVO nvo = JSON.parseObject(nodeString, WorkflowNodeVO.class);
				List<WorkflowVariableVO> list = nvo.getVariables();
				if (list == null) {
					list = new ArrayList<WorkflowVariableVO>();
				}
				for (WorkflowVariableVO vo : list) {
					if (vo.getVariableName().equalsIgnoreCase(varName)) {
						logger.debug("=====================");
						list.remove(vo);
						break;
					}
				}
				nvo.setVariables(list);
				o.put(nodeId, nvo);
				dVo.setWorkflowNodeInfo(JSON.toJSONString(o));
				dynamicPageService.updateWorkflowInfo(dVo);
			}
		}
		return mv;
	}

	/**
	 * @param vo
	 * @param nodeId
	 *            nodeid+_+workflowid
	 * @param pageId
	 * @return
	 */
	@RequestMapping(value = "/addNodeVariable")
	public ModelAndView addNodeVariable(WorkflowVariableVO vo, String nodeId, String pageId) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageId", pageId);// 动态表单ID
		mv.addObject("nodeId", nodeId);// 节点ID

		mv.setViewName("redirect:/fd/workflow/nodeVariableEdit.do");
		// 查出节点对应的参数列表
		DynamicPageVO dVo = dynamicPageService.findById(pageId);
		if (dVo != null) {
			String jsonStr = dVo.getWorkflowNodeInfo();
			JSONObject o = StringUtils.isNotEmpty(jsonStr) ? JSON.parseObject(jsonStr) : new JSONObject();
			String nodeString = o.getString(nodeId);
			if (StringUtils.isNotBlank(nodeString)) {
				WorkflowNodeVO nvo = JSON.parseObject(nodeString, WorkflowNodeVO.class);
				List<WorkflowVariableVO> list = nvo.getVariables();
				if (list == null) {
					list = new ArrayList<WorkflowVariableVO>();
				}
				list.add(vo);
				nvo.setVariables(list);
				o.put(nodeId, nvo);
				dVo.setWorkflowNodeInfo(JSON.toJSONString(o));
				dynamicPageService.updateWorkflowInfo(dVo);
			}
		}
		return mv;
	}
}
