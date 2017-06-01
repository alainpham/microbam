package com.redhat.empowered.generic.processors;

import javax.transaction.TransactionManager;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.infinispan.AdvancedCache;
import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;

import com.redhat.empowered.generic.model.GenericObjectFactory;
import com.redhat.empowered.generic.model.IndicatorRecord;
import com.redhat.empowered.generic.model.StatisticsRecord;

public class TriggerMetricCalculation implements Processor {

	private CacheContainer cacheContainer ;
	private TransactionManager transactionManager;
	private GenericObjectFactory genericObjectFactory;

	private String cacheName;
	private AdvancedCache<String, StatisticsRecord> advancedCache;
	private Cache<String, StatisticsRecord> cache;

	public TriggerMetricCalculation(CacheContainer cacheContainer,String cacheName, GenericObjectFactory genericObjectFactory) {
		super();

		this.cacheContainer = cacheContainer;
		this.cacheName = cacheName;
		this.genericObjectFactory = genericObjectFactory;

		this.cache = this.cacheContainer.getCache(this.cacheName);
		this.advancedCache = cache.getAdvancedCache();
		this.transactionManager = advancedCache.getTransactionManager();
	}


	public void process(Exchange exchange) throws Exception {
		
		//expected Headers
		String key = exchange.getIn().getHeader("key",String.class);

		//expected Body
		IndicatorRecord indicatorRecord = exchange.getIn().getBody(IndicatorRecord.class);
		
		//execute update in a transaction
		transactionManager.begin();

		advancedCache.lock(key);
		StatisticsRecord statisticsRecord = advancedCache.getOrDefault(key,genericObjectFactory.createStatisticsRecord(key));
		statisticsRecord.update(indicatorRecord);
		advancedCache.put(key,statisticsRecord);
		
		transactionManager.commit();




	}



}