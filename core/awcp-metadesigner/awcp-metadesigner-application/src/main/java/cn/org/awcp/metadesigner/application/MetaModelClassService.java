package cn.org.awcp.metadesigner.application;

import java.util.List;

import cn.org.awcp.metadesigner.vo.MetaModelClassVO;

/**
 * 元数据模型类型
 * 
 * @author xw *
 */
public interface MetaModelClassService {

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	public List<MetaModelClassVO> findAll();

	/**
	 * 删除
	 * 
	 * @param vo
	 */
	public void remove(MetaModelClassVO vo);

	/**
	 * 修改
	 * 
	 * @param vo
	 * @param queryStr
	 */
	public void update(MetaModelClassVO vo, String queryStr);

	/**
	 * 增加
	 * 
	 * @param vo
	 */
	public void save(MetaModelClassVO vo);

	/**
	 * 查询一条数据
	 * 
	 * @param id
	 * @return
	 */
	public MetaModelClassVO get(String id);

	/**
	 * 根据项目Id查询
	 * 
	 * @param sysId
	 * @return
	 */
	public List<MetaModelClassVO> queryBySystemId(long sysId);

}
