package org.szcloud.framework.core.domain.internal;

import org.szcloud.framework.core.domain.QueryCriterion;
import org.szcloud.framework.core.domain.QueryException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class NotEqPropCriterion implements QueryCriterion{
	private String propName1;
	private String propName2;

	public NotEqPropCriterion(String propName1, String propName2) {
		if ((StringUtils.isEmpty(propName1)) || (StringUtils.isEmpty(propName2))) {
			throw new QueryException("One of property name is null!");
		}
		this.propName1 = propName1;
		this.propName2 = propName2;
	}

	public String getPropName1() {
		return this.propName1;
	}

	public String getPropName2() {
		return this.propName2;
	}

	public boolean equals(Object other){
		if (this == other)
			return true;
		if (!(other instanceof NotEqPropCriterion))
			return false;
		NotEqPropCriterion castOther = (NotEqPropCriterion)other;
		return new EqualsBuilder().append(getPropName1(), castOther.getPropName1()).append(this.propName2, castOther.propName2).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getPropName1()).append(this.propName2).toHashCode();
	}

	public String toString(){
		return getPropName1() + " != " + this.propName2;
	}
}
