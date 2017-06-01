package com.redhat.empowered.generic.helpers;

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
		Cache<String, Object> cache = cacheContainer.getCache(ex.getIn().getHeader("__cacheName", String.class));
		
		//Verify if the requested type exists using java reflection
		Class c  = Class.forName(ex.getIn().getHeader("__type",String.class));

		QueryFactory queryFactory = Search.getQueryFactory(cache);

		QueryBuilder qb = queryFactory.from(c);;


		//inspect the searched class in order to get the fields that can be queried
		BeanInfo info = Introspector.getBeanInfo( c,Object.class);
		
		boolean first = true;
		// for each property of the class we look if a parameter has been set		
		for ( PropertyDescriptor pd : info.getPropertyDescriptors() ){
			
			Object searchValue = ex.getIn().getHeader(pd.getName());
			//only search the fields that are actually indexed by checking the presence of Field annotation
//			boolean propIsIndexed = c.getField(pd.getName()).getAnnotationsByType(Field.class).length > 0;
			
			//only add search criteria when the parameter has been set in the header and when the property is indexed	
			
			logger.info("pd.getName() : " + pd.getName());

			logger.info("Search value : " + searchValue);
			
			if (searchValue!=null){
				
				//if field is a date convert the type explicitly
				if (pd.getPropertyType().equals(Date.class)){
					searchValue = new Date(Long.parseLong((String)searchValue));
				}
				
				if (first){ 	//first condition
					qb = qb.having(pd.getName()).eq(searchValue);
					first=false;
					logger.info("First");
				}else{ 				//additional conditions with and operator
					qb = ((FilterConditionContext) qb).and().having(pd.getName()).eq(searchValue);
					logger.info("Not first");
				}
			}
		}

		Query q = qb.build();


		ex.getIn().setBody(q.list());

	}

}
