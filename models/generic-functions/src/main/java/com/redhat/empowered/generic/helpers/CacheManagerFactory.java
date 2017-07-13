package com.redhat.empowered.generic.helpers;

import java.util.Properties;

import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.cache.Index;
import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.persistence.leveldb.configuration.LevelDBStoreConfigurationBuilder;
import org.infinispan.transaction.LockingMode;
import org.infinispan.transaction.TransactionMode;
import org.infinispan.transaction.lookup.GenericTransactionManagerLookup;

public class CacheManagerFactory {
	
	private ConfigurationBuilder builder;
	

	public CacheManagerFactory(String persistenceFolder, String indexBaseFolder) {
		builder = new ConfigurationBuilder();
		Properties indexProps = new Properties();
		indexProps.setProperty("default.directory_provider","filesystem");
		indexProps.setProperty("default.indexBase", indexBaseFolder);
		builder
		//transactions
		.transaction().transactionMode(TransactionMode.TRANSACTIONAL)
		.transactionManagerLookup(new GenericTransactionManagerLookup())
		.lockingMode(LockingMode.PESSIMISTIC) 
		//indexing
		.indexing().index(Index.LOCAL).withProperties(indexProps)
		//persistence
		.persistence().addStore(LevelDBStoreConfigurationBuilder.class)
        .location(persistenceFolder + "/ldb")
        .expiredLocation(persistenceFolder + "/ldb-exp").build()
        ;
		
	}
	
	public DefaultCacheManager newCacheManager(){
		
		 DefaultCacheManager cacheManager = new DefaultCacheManager(builder.build());
		 return cacheManager;
		 
	}
	
	
}
