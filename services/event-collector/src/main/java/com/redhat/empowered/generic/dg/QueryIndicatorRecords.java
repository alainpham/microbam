package com.redhat.empowered.generic.dg;

import java.util.List;


import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;
import org.infinispan.query.Search;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;

import com.redhat.empowered.generic.model.IndicatorRecord;

public class QueryIndicatorRecords {

	private Cache<String, IndicatorRecord> cache;
	
	private QueryFactory qf;
	
	public QueryIndicatorRecords(CacheContainer cacheContainer,String cacheName) {
		super();
		
		this.cache=cacheContainer.getCache(cacheName);
		this.qf = Search.getQueryFactory(this.cache);
	}
	
	public List<IndicatorRecord> listIndicatorRecords(String indicatorClass){
		
		Query q = qf.from(IndicatorRecord.class)
		.having("indicatorClass").eq(indicatorClass)
		.toBuilder().build();
		return q.list();
		
	}
	
}
