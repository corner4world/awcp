package cn.org.awcp.metadesigner.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.org.awcp.core.domain.QueryChannelService;
import cn.org.awcp.core.utils.BeanUtils;
import cn.org.awcp.metadesigner.application.MetaModelClassService;
import cn.org.awcp.metadesigner.core.domain.MetaModelClass;
import cn.org.awcp.metadesigner.vo.MetaModelClassVO;

@Service(value = "metaModelClassServiceImpl")
public class MetaModelClassServiceImpl implements MetaModelClassService {

	@Resource(name = "queryChannel")
	private QueryChannelService queryChannel;

	/**
	 * 查询所有
	 */
	public List<MetaModelClassVO> findAll() {
		List<MetaModelClass> list = MetaModelClass.findAll();
		List<MetaModelClassVO> ls = new ArrayList<MetaModelClassVO>();
		for (MetaModelClass mm : list) {
			ls.add(BeanUtils.getNewInstance(mm, MetaModelClassVO.class));
		}
		return ls;
	}

	public void remove(MetaModelClassVO vo) {
		try {
			MetaModelClass mm = BeanUtils.getNewInstance(vo, MetaModelClass.class);
			MetaModelClass.getRepository().remove(mm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(MetaModelClassVO vo, String queryStr) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", vo.getId());
			map.put("name", vo.getName());
			map.put("classCode", vo.getClassCode());
			MetaModelClass.getRepository().executeUpdate(queryStr, map, MetaModelClass.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save(MetaModelClassVO vo) {
		try {
			MetaModelClass mmc = BeanUtils.getNewInstance(vo, MetaModelClass.class);
			mmc.save();
			vo.setId(mmc.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MetaModelClassVO get(String id) {
		try {
			MetaModelClass mmc = MetaModelClass.get(MetaModelClass.class, id);
			return BeanUtils.getNewInstance(mmc, MetaModelClassVO.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<MetaModelClassVO> queryBySystemId(long sysId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sysId", sysId);
		List<MetaModelClass> ls = queryChannel.queryResult(MetaModelClass.class, "queryByProjectId", map);
		List<MetaModelClassVO> list = new ArrayList<MetaModelClassVO>();
		for (MetaModelClass m : ls) {
			list.add(BeanUtils.getNewInstance(m, MetaModelClassVO.class));
		}
		return list;
	}

}
