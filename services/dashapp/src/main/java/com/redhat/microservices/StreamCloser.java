package com.redhat.microservices;

import java.io.InputStream;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamCloser implements Processor {

	final static Logger logger = LoggerFactory.getLogger(StreamCloser.class);
	@Override
	public void process(Exchange exchange) throws Exception {
		InputStream is = exchange.getIn().getBody(InputStream.class);
		if (is != null) {
			is.close();
		}
	}



}
