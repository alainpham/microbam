package com.redhat.empowered.tester;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.hibernate.search.annotations.Field;
import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;
import org.infinispan.query.Search;
import org.infinispan.query.dsl.FilterConditionContext;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryBuilder;
import org.infinispan.query.dsl.QueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.empowered.specific.model.trading.TradeProcessingDuration;


public class QueryProcessor implements Processor {
	
	final static Logger logger = LoggerFactory.getLogger(QueryProcessor.class);
	
	//cache container to be injected with another spring bean	
	private CacheContainer cacheContainer;


	public CacheContainer getCacheContainer() {
		return cacheContainer;
	}


	public void setCacheContainer(CacheContainer cacheContainer) {
		this.cacheContainer = cacheContainer;
	}

	
	@Override
	public void process(Exchange ex) throws Exception {
		
		//Get the targeted cachename from the exchange header
		Cache<String, Object> cache = cacheContainer.getCache("tradeProcessingDuration");
		
		//Verify if the requested type exists using java reflection
		Class c  = TradeProcessingDuration.class;

		QueryFactory queryFactory = Search.getQueryFactory(cache);
		QueryBuilder qb = queryFactory.from(c);
		qb = qb.having("frequencyGroupValue").eq(2);
		qb = ((FilterConditionContext) qb).or().having("frequencyGroupValue").eq(8);
		Query q = qb.build();


		ex.getIn().setBody(q.list());

	}

}
