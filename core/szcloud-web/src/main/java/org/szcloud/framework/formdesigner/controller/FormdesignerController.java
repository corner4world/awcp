package org.szcloud.framework.formdesigner.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.szcloud.framework.base.BaseController;
import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.core.domain.Criteria;
import org.szcloud.framework.core.utils.SessionUtils;
import org.szcloud.framework.core.utils.Tools;
import org.szcloud.framework.core.utils.constants.SessionContants;
import org.szcloud.framework.formdesigner.application.service.FormdesignerService;
import org.szcloud.framework.formdesigner.application.service.StoreService;
import org.szcloud.framework.formdesigner.application.vo.DynamicPageVO;
import org.szcloud.framework.formdesigner.application.vo.PageActVO;
import org.szcloud.framework.formdesigner.application.vo.StoreVO;
import org.szcloud.framework.formdesigner.constrants.FormDesignGlobal;
import org.szcloud.framework.metadesigner.application.MetaModelOperateService;
import org.szcloud.framework.unit.service.PunResourceService;
import org.szcloud.framework.unit.service.PunSystemService;
import org.szcloud.framework.unit.vo.PunGroupVO;
import org.szcloud.framework.unit.vo.PunSystemVO;
import org.szcloud.framework.venson.controller.base.ControllerHelper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping("/fd")
public class FormdesignerController extends BaseController {

	@Autowired
	private FormdesignerService formdesignerServiceImpl;
	@Autowired
	@Qualifier("storeServiceImpl")
	private StoreService storeService;
	@Autowired
	@Qualifier("punResourceServiceImpl")
	private PunResourceService punResourceService;

	@Autowired
	private PunSystemService punSystemServiceImpl;
	@Resource(name = "metaModelOperateServiceImpl")
	private MetaModelOperateService meta;

	/**
	 * 根据name查询动态页面，分页显示
	 * 
	 * @param name
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(@RequestParam(required = false) String name,
			@RequestParam(required = false, defaultValue = "1") int currentPage,
			@RequestParam(required = false, defaultValue = "10") int pageSize, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		if (pageSize < 10) {
			pageSize = 10;
		}
		if (currentPage <= 0) {
			currentPage = 1;
		}
		String pageType = request.getParameter("pageType");
		String templateId = request.getParameter("templateId");
		String menuId = request.getParameter("menuId");
		Object obj = Tools.getObjectFromSession(SessionContants.TARGET_SYSTEM);
		BaseExample example = new BaseExample();
		Criteria criteria = example.createCriteria();
		if (obj instanceof PunSystemVO) {
			if (StringUtils.isNotBlank(name)) {
				if (StringUtils.isNumeric(name)) {
					criteria.andEqualTo("id", name);
				} else {
					criteria.andLike("name", "%" + name + "%");
				}

			}
			if (StringUtils.isNotBlank(pageType)) {
				criteria.andEqualTo("page_type", pageType);
			}
			if (StringUtils.isNotBlank(templateId)) {
				criteria.andEqualTo("template_id", templateId);
			}
			if (StringUtils.isNotBlank(menuId)) {
				criteria.andEqualTo("id", menuId);
			}
			PageList<DynamicPageVO> vos = formdesignerServiceImpl.selectPagedByExample(example, currentPage, pageSize,
					"ID desc");
			mv.addObject("vos", vos);
		}
		mv.addObject("name", name);
		mv.addObject("currentPage", currentPage);
		mv.addObject("pageSize", pageSize);
		mv.addObject("pageType", pageType);
		mv.addObject("templateId", templateId);
		mv.addObject("menuId", menuId);
		mv.addObject("templates", meta.search("select id ,file_name text from p_fm_template"));
		mv.addObject("menus", meta
				.search("SELECT dynamicpage_id id,menu_name text FROM p_un_menu a WHERE LENGTH(a.dynamicpage_id)>0"));
		mv.setViewName("formdesigner/page/dynamicPage-list");
		return mv;
	}

	/**
	 * 根据name查询动态页面，分页显示
	 * 
	 * @param name
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/list-bind")
	public ModelAndView bind(@RequestParam(required = false) String name,
			@RequestParam(required = false, defaultValue = "1") int currentPage,
			@RequestParam(required = false, defaultValue = "10") int pageSize, String mes) {
		ModelAndView mv = new ModelAndView();
		if (pageSize < 10) {
			pageSize = 10;
		}
		if (currentPage <= 0) {
			currentPage = 1;
		}
		Object obj = Tools.getObjectFromSession(SessionContants.TARGET_SYSTEM);
		if (obj instanceof PunSystemVO) {
			BaseExample example = new BaseExample();
			if (StringUtils.isNotBlank(name)) {
				boolean isNum = name.matches("[0-9]+");
				if (isNum) {
					example.createCriteria().andEqualTo("id", name);
				} else {
					example.createCriteria().andLike("name", "%" + name + "%");
				}

			}
			PageList<DynamicPageVO> vos = formdesignerServiceImpl.selectPagedByExample(example, currentPage, pageSize,
					"ID desc");
			mv.addObject("vos", vos);
		}

		if (mes != null && "error".equals(mes)) {
			mv.addObject("mes", "error");
		}
		mv.addObject("name", name);
		mv.addObject("currentPage", currentPage);
		mv.addObject("pageSize", pageSize);
		mv.setViewName("formdesigner/page/dynamicPage-bind");
		return mv;
	}

	/**
	 * 根据name查询动态页面，分页显示
	 * 
	 * @param name
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/listSelect")
	public ModelAndView listSelect(@RequestParam(required = false) String name,
			@RequestParam(required = false, defaultValue = "1") int currentPage,
			@RequestParam(required = false, defaultValue = "10") int pageSize) {
		ModelAndView mv = new ModelAndView();
		if (pageSize < 10) {
			pageSize = 10;
		}
		if (currentPage <= 0) {
			currentPage = 1;
		}
		Object obj = Tools.getObjectFromSession(SessionContants.TARGET_SYSTEM);
		if (obj instanceof PunSystemVO) {
			BaseExample example = new BaseExample();
			if (StringUtils.isNotBlank(name)) {
				example.createCriteria().andLike("name", "%" + name + "%");
			}
			PageList<DynamicPageVO> vos = formdesignerServiceImpl.selectPagedByExample(example, currentPage, pageSize,
					"ID desc");
			mv.addObject("vos", vos);
		}
		mv.addObject("name", name);
		mv.addObject("currentPage", currentPage);
		mv.addObject("pageSize", pageSize);
		mv.setViewName("formdesigner/page/dynamicPage-list-select");
		return mv;
	}

	/**
	 * 根据name查询动态页面，分页显示
	 * 
	 * @param name
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/listJson")
	public String listJson(@RequestParam(required = false) String name) {
		// StringBuilder sb = new StringBuilder();
		// long t = System.currentTimeMillis();
		// BaseExample example = new BaseExample();
		// if (StringUtils.isNotBlank(name)) {
		// example.createCriteria().andLike("name", "%" + name + "%");
		// }
		Long systemId = -1L;
		Object obj = Tools.getObjectFromSession(SessionContants.TARGET_SYSTEM);
		if (obj instanceof PunSystemVO) {
			PunSystemVO system = (PunSystemVO) obj;
			systemId = system.getSysId();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(name)) {
			params.put("name", "%" + name + "%");
		}
		List<DynamicPageVO> vos = formdesignerServiceImpl.listNameAndIdInSystem(systemId, params);
		// PageList<DynamicPageVO> vos =
		// formdesignerServiceImpl.selectPagedByExample(example, 1,
		// Integer.MAX_VALUE, "ID desc");
		// long e = System.currentTimeMillis();
		// sb.append("查询耗时 ： ").append((e-t)/1000.0).append(" ==== ");
		// t = System.currentTimeMillis();
		JSONArray array = new JSONArray();
		for (DynamicPageVO vo : vos) {
			JSONObject o = new JSONObject();
			o.put("id", vo.getId());
			o.put("name", vo.getName());
			array.add(o);
		}
		// e = System.currentTimeMillis();
		// sb.append("json拼装 ： ").append((e-t)/1000.0).append(" ==== ");
		// logger.debug(sb.toString());
		return array.toJSONString();
	}

	/**
	 * 查询动态页面，并跳转到编辑页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView editPage(@RequestParam(value = "errorMsg", required = false) String errorMsg,
			@RequestParam(value = "_selects", required = false) Long id,
			@RequestParam(required = false, defaultValue = "1") int currentPage) {
		// 根据id来查询，如果id为空，则为新增
		ModelAndView mv = new ModelAndView();
		mv.addObject("currentPage", currentPage);
		mv.setViewName("formdesigner/page/dynamicPage-edit");
		if (errorMsg != null && !"".equals(errorMsg)) {
			mv.addObject("tips", errorMsg);
		}
		validatorPage(mv, id);
		if (id != null) {
			DynamicPageVO vo = formdesignerServiceImpl.findById(id);
			if (vo != null) {

				BaseExample actExample = new BaseExample();
				actExample.createCriteria().andEqualTo("DYNAMICPAGE_ID", id).andLike("CODE",
						StoreService.PAGEACT_CODE + "%");
				PageList<StoreVO> actStore = storeService.selectPagedByExample(actExample, 1, Integer.MAX_VALUE,
						"BTN_GROUP ASC,T_ORDER ASC");
				List<PageActVO> acts = new ArrayList<PageActVO>();
				for (StoreVO store : actStore) {
					PageActVO act = JSON.parseObject(store.getContent(), PageActVO.class);
					acts.add(act);
				}
				actStore.clear();
				mv.addObject("acts", acts);

			}

			mv.addObject("vo", vo);
		}
		mv.addObject("_COMPOENT_TYPE_NAME", FormDesignGlobal.COMPOENT_TYPE_NAME);
		return mv;
	}

	@RequestMapping(value = "/copy")
	public ModelAndView copyDyanmiacPages(@RequestParam(value = "_selects", required = false) Long[] ids,
			@RequestParam(required = false, defaultValue = "1") int currentPage) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("currentPage", currentPage);
		for (Long id : ids) {
			formdesignerServiceImpl.copy(id);
		}
		mv.setViewName("redirect:/fd/list.do");
		return mv;
	}

	@RequestMapping(value = "/listSystem")
	public ModelAndView listSystem() {
		ModelAndView mv = new ModelAndView();
		PunSystemVO sysVO = (PunSystemVO) SessionUtils.getObjectFromSession(SessionContants.TARGET_SYSTEM);
		PunGroupVO groupVo = (PunGroupVO) SessionUtils.getObjectFromSession(SessionContants.CURRENT_USER_GROUP);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("groupId", groupVo.getGroupId());
		List<PunSystemVO> list = punSystemServiceImpl.queryResult("eqQueryList", params);
		mv.addObject("list", list);
		mv.addObject("system", sysVO);
		mv.setViewName("formdesigner/page/select-system-edit");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/copyToSystem")
	public String copyDyanmiacPagesToSystem(@RequestParam(value = "_selects", required = false) Long[] ids,
			@RequestParam(required = false, defaultValue = "1") int currentPage,
			@RequestParam(required = false) String systemId) {
		JSONObject o = new JSONObject();
		if (StringUtils.isNotBlank(systemId)) {
			for (Long id : ids) {
				formdesignerServiceImpl.copyToSystem(id, systemId);
			}
			o.put("rtn", "1");
		} else {
			o.put("rtn", "-1");
			o.put("msg", "请选择目标系统！");
		}
		return o.toJSONString();
	}

	/**
	 * 查询动态页面，并跳转到编辑页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/publish")
	public ModelAndView publishPage(@RequestParam(value = "_selects", required = false) Long[] ids,
			@RequestParam(required = false, defaultValue = "1") int currentPage) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("currentPage", currentPage);
		for (Long id : ids) {
			formdesignerServiceImpl.publish(id);
		}
		mv.setViewName("redirect:/fd/list.do");
		return mv;
	}

	/**
	 * 查询动态页面，并跳转到编辑页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/publishOnePage")
	public ModelAndView publishOnePage(Long id) {
		ModelAndView mv = new ModelAndView();
		if (id == null) {
			mv.addObject("errorMsg", "ID is null.");
		} else {
			try {
				formdesignerServiceImpl.publish(id);
			} catch (Exception e) {
				e.printStackTrace();
				mv.addObject("errorMsg", "publish error.");
			}
			mv.addObject("_selects", id);
		}
		mv.setViewName("redirect:/fd/edit.do");
		return mv;
	}

	/**
	 * 签出页面
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkOutPage")
	public String checkOutPage(Long id) {

		DynamicPageVO vo = formdesignerServiceImpl.findById(id);
		if (vo.getIsCheckOut() != null && vo.getIsCheckOut() == 1) { // 已签出状态
			if (!ControllerHelper.getUser().getName().equals(vo.getCheckOutUser())) {
				String ret = vo.getName() + "页面已被  " + vo.getCheckOutUser() + "签出！";
				return ret;
			}
		}
		formdesignerServiceImpl.checkOut(id);
		return "签出成功";
	}

	/**
	 * 签入页面
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkInPage")
	public String checkInPage(Long id) {

		DynamicPageVO vo = formdesignerServiceImpl.findById(id);
		if (vo.getIsCheckOut() != null && vo.getIsCheckOut() == 1) { // 已签出状态
			if (!ControllerHelper.getUser().getName().equals(vo.getCheckOutUser())) {
				String ret = vo.getName() + "页面已被  " + vo.getCheckOutUser() + "签出,你无法进行签入";
				return ret;
			}
		}

		formdesignerServiceImpl.checkIn(id);
		return "签入成功";

	}

	/**
	 * 保存动态页面信息 校验不通过，返回到编辑页面
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/save")
	public ModelAndView savePage(DynamicPageVO vo,
			@RequestParam(required = false, defaultValue = "1") int currentPage) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("currentPage", currentPage);
		Object o = SessionUtils.getObjectFromSession(SessionContants.TARGET_SYSTEM);
		if (o instanceof PunSystemVO) {
			PunSystemVO system = (PunSystemVO) o;
			vo.setSystemId(system.getSysId());
		}

		// 校验
		if (validatorPage(mv, vo.getId())) {
			if (vo.getId() == null) {
				vo.setCheckOutUser(ControllerHelper.getUser().getName());
				vo.setIsCheckOut(1);
			}
			formdesignerServiceImpl.saveOrUpdate(vo);
			mv.addObject("_selects", vo.getId());
			mv.setViewName("redirect:/fd/edit.do");
		} else {
			mv.addObject("_selects", vo.getId());
			mv.setViewName("redirect:/fd/edit.do");
		}
		return mv;
	}

	/**
	 * 展示当前页面的直接父级和子级页面
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/relation")
	public ModelAndView refrenceRelation(@RequestParam(value = "_select") String id) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("formdesigner/page/refRelation");
		try {
			Map<Long, DynamicPageVO> child = findChildComponentByDynamicPageId(Long.parseLong(id));
			// 找出包含该页面的包含组件（父亲）
			// 找出该包含组件所在页面
			Map<Long, DynamicPageVO> parent = new HashMap<Long, DynamicPageVO>();
			List<JSONObject> parentComponents = storeService.findParentComponentByDyanamicPageId(Long.parseLong(id));
			for (JSONObject component : parentComponents) {
				String relatePageId = component.getString("dynamicPageId");
				if (StringUtils.isNotBlank(relatePageId)) {
					DynamicPageVO vo = formdesignerServiceImpl.findById(Long.parseLong(relatePageId));
					if (vo != null) {
						parent.put(vo.getId(), vo);
					}
				}
			}
			mv.addObject("parent", parent);
			mv.addObject("child", child);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	public Map<Long, DynamicPageVO> findChildComponentByDynamicPageId(Long id) {
		List<JSONObject> components = storeService.findComponentByDyanamicPageId(id);
		// List<DynamicPageVO> child= new ArrayList<DynamicPageVO>();
		Map<Long, DynamicPageVO> child = new HashMap<Long, DynamicPageVO>();
		for (JSONObject component : components) {
			String relatePageId = component.getString("relatePageId");
			if (StringUtils.isNotBlank(relatePageId)) {
				DynamicPageVO vo = formdesignerServiceImpl.findById(Long.parseLong(relatePageId));
				if (!vo.getId().equals(id)) {
					child.putAll(findChildComponentByDynamicPageId(vo.getId()));
				}
				if (vo != null) {
					child.put(vo.getId(), vo);
				}
			}
		}
		return child;
	}

	@RequestMapping(value = "/catTemplate")
	public ModelAndView catTemplate(@RequestParam(value = "_select") String id) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("formdesigner/page/catTemplate");
		if (StringUtils.isNotBlank(id)) {
			try {
				String content = formdesignerServiceImpl.getTemplateContext(Long.valueOf(id));
				mv.addObject("content", content);
			} catch (Exception e) {
				e.printStackTrace();
				addMessage(mv, "啊哦~后台报错了，传入的id不能被转换为Long！");
			}
		} else {
			addMessage(mv, "请选择需要查看的动态页面！");
		}
		return mv;
	}

	/**
	 * 删除选择的动态页面数据
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView deletePage(@RequestParam(value = "_selects") String ids,
			@RequestParam(required = false, defaultValue = "1") int currentPage) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/fd/list.do?currentPage=" + currentPage);
		try {
			String[] array = ids.split(",");
			List<Long> temp = new ArrayList<Long>();
			for (String id : array) {
				if (!validatorPage(mv, Long.parseLong(id))) {
					return mv;
				}
				temp.add(Long.parseLong(id));
				BaseExample example = new BaseExample();
				example.createCriteria().andEqualTo("dynamicPage_id", id);
				List<StoreVO> stores = storeService.selectPagedByExample(example, 1, Integer.MAX_VALUE, null);
				for (StoreVO store : stores) {
					storeService.delete(store);
					// 删除资源表中的按钮
					if (store.getCode().contains(StoreService.PAGEACT_CODE)) {
						punResourceService.removeByRelateResoAndType(store.getId(), "3");
					}
					// TODO 删除资源表中的组件？
				}
				stores.clear();
			}
			formdesignerServiceImpl.delete(temp);
			temp.clear();
		} catch (Exception e) {
			e.printStackTrace();
			// addMessage(mv, "异常");
		}
		return mv;
	}

	/**
	 * 更动态页面新数据源
	 * 
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateData")
	public String updateDataJson(DynamicPageVO vo) {
		ModelAndView mv = new ModelAndView();
		// 校验
		if (validatorPage(mv, vo.getId())) {
			formdesignerServiceImpl.updateModelInfo(vo);
			return "1";
		} else {
			return "0";
		}
	}

	private boolean validatorPage(ModelAndView mv, Long id) {
		// 新增页面不需要判断
		if (id == null) {
			return true;
		}
		DynamicPageVO vo = formdesignerServiceImpl.findById(id);
		if (vo.getIsCheckOut() == null || vo.getIsCheckOut() == 0) {
			mv.addObject("tips", vo.getName() + "页面还未签出，编辑修改前请先签出页面");
			return false;
		} else {
			if (vo.getIsCheckOut() != null && vo.getIsCheckOut() == 1) { // 已签出状态
				if (!ControllerHelper.getUser().getName().equals(vo.getCheckOutUser())) {
					String ret = vo.getName() + "页面已被  " + vo.getCheckOutUser() + "签出！";
					mv.addObject("tips", ret);
					return false;
				}
			}
		}
		return true;
	}

	@ResponseBody
	@RequestMapping(value = "/validateCheckOut")
	public Map<String, Object> validateCheckOut(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (id == null) {
			map.put("value", 1);
			// 新增页面，无需校验是否签出；
			return map;
		}

		DynamicPageVO vo = formdesignerServiceImpl.findById(id);
		if (vo.getIsCheckOut() != null && vo.getIsCheckOut() == 1) { // 已签出状态
			if (!ControllerHelper.getUser().getName().equals(vo.getCheckOutUser())) {
				String ret = vo.getName() + "页面已被  " + vo.getCheckOutUser() + "签出！";
				map.put("value", ret);
				return map;
			}
		}
		map.put("value", 1);
		return map;
	}

}
