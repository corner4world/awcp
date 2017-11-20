package cn.org.awcp.core.domain;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * 领域实体接口。所有实体类都要直接或间接实现这个接口。它主要起标记作用，以便于统一处理系统中的实体等。
 * 
 * @author caoyong
 * 
 */
public interface Entity<T> extends Serializable {

	T getId();

	void setId(T id);

	Type getEntityClass();

}
