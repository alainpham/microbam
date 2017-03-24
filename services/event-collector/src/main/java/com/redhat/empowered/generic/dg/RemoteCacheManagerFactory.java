package com.redhat.empowered.generic.dg;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;

public class RemoteCacheManagerFactory {
	ConfigurationBuilder clientBuilder;

	public RemoteCacheManagerFactory(String hostname, int port) {
		clientBuilder = new ConfigurationBuilder();
		clientBuilder.addServer()
		.host(hostname).port(port);
	}

	public RemoteCacheManager newRemoteCacheManager() {
		return new RemoteCacheManager(clientBuilder.build());
	}
}
