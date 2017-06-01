package com.redhat.empowered.generic.helpers;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.Map;

import org.apache.camel.component.infinispan.InfinispanQueryBuilder;
import org.infinispan.query.dsl.FilterConditionContext;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryBuilder;
import org.infinispan.query.dsl.QueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericQuery implements InfinispanQueryBuilder {
	
	final static Logger logger=LoggerFactory.getLogger(GenericQuery.class);
	
	private Map<String,Object> params;
	private BeanInfo info;
	private Class type;
	
	public GenericQuery(String typeName, Map<String, Object> params) throws ClassNotFoundException, IntrospectionException {
		super();
		
		//inspect the searched class in order to get the fields that can be queried
		type  =  Class.forName(typeName);
		
		info = Introspector.getBeanInfo(type,Object.class);
		logger.info("Searching type " + type.getName());
		this.params = params;
		
	}

	@Override
	public Query build(QueryFactory queryFactory) {
		

		QueryBuilder qb = queryFactory.from(type);

		FilterConditionContext ctx=null;

		// for each property of the class we look if a parameter has been set		
		for ( PropertyDescriptor pd : info.getPropertyDescriptors() ){

			Object searchValue = this.params.get(pd.getName());
			logger.info("Trying with : " + pd.getName() );
			//only search the fields that are actually indexed by checking the presence of Field annotation

			//only add search criteria when the parameter has been set in the header and when the property is indexed			
			if (searchValue!=null){
				logger.info("Searchin with " + pd.getName() + "=" + searchValue);
				//if field is a date convert the type explicitly
				if (pd.getPropertyType().equals(Date.class)){
					searchValue = new Date(Long.parseLong((String)searchValue));
				}

				if (ctx==null){ 	//first condition
					ctx = qb.having(pd.getName()).eq(searchValue);
				}else{ 				//additional conditions with and operator
					ctx.and().having(pd.getName()).eq(searchValue);
				}
			}
		}

		return qb.build();
	}


}
