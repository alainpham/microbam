package com.redhat.empowered.generic.helpers;

import java.beans.IntrospectionException;

import org.apache.camel.Exchange;
import org.apache.camel.component.infinispan.InfinispanQueryBuilder;

public class GenerateQuery {
	
	private String indicatorClass;
	
	
	
	public String getIndicatorClass() {
		return indicatorClass;
	}



	public void setIndicatorClass(String indicatorClass) {
		this.indicatorClass = indicatorClass;
	}



	public InfinispanQueryBuilder getBuilder(Exchange ex) throws ClassNotFoundException, IntrospectionException {

		InfinispanQueryBuilder qb = new GenericQuery(indicatorClass,ex.getIn().getHeaders());
		
		return qb;
	}
}