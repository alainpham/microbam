package com.redhat.empowered.tester;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.math3.random.RandomDataGenerator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.empowered.specific.model.trading.TradeProcessingDuration;

public class DataGenerator extends Thread{

	@EndpointInject(uri="activemq:queue:app.stats.trades")
	private ProducerTemplate producer;

	public ProducerTemplate getProducer() {
		return producer;
	}

	public void setProducer(ProducerTemplate producer) {
		this.producer = producer;
	}

	private RandomDataGenerator dataGenerator=  new RandomDataGenerator();


	private BigDecimal avg;
	private BigDecimal stdev;

	private volatile boolean running = true;

	private static DataGenerator generatorThread;

	public void terminate() {
		generatorThread.running = false;
		generatorThread = null;
	}



	public boolean isRunning() {
		return running;
	}



	public void setRunning(boolean running) {
		this.running = running;
	}



	public void readData(){

	}

	public BigDecimal getAvg() {
		return avg;
	}

	public void setAvg(BigDecimal avg) {
		this.avg = avg;
	}

	public BigDecimal getStdev() {
		return stdev;
	}

	public void setStdev(BigDecimal stdev) {
		this.stdev = stdev;
	}

	public void startSim(String avg, String stdev){
		if (generatorThread!=null){
			System.out.println("Simulation already Running");
			return;
		}
		generatorThread = new DataGenerator();
		generatorThread.setProducer(this.producer);
		generatorThread.running = true;
		generatorThread.avg = new BigDecimal(avg);
		generatorThread.stdev = new BigDecimal(stdev);
		generatorThread.start();
	}

	@Override
	public void run() {
		
		String stocks[] = {"Akor Corp","Air Himalaya","Aircar","Archen Michelor","AXI","BND Caribou"};
		String customers[] = {"Sanda Cude","Estefana Claborn","Sylvie Paynter","Mariko Duffey","Alta Furlong","Magdalene Holcomb","Odette Bresnahan","Rosamond Tetreault","Sandra Bowker","Luanna Capasso"};
		String brokers[]={"Franklin Bank","The Westwood Homestead Savings Bank","Culver Federal Savings and Loan Association","Columbia Savings","United Guaranty Federal Savings Bank"};
		ObjectMapper mapper = new ObjectMapper();

		while (this.running) {
			try {
				Double value = new Double(dataGenerator.nextGaussian(avg.doubleValue(), stdev.doubleValue()));
				value = Math.abs(value);
				TradeProcessingDuration tradeProcessingDuration = new TradeProcessingDuration();
				Long ts = System.currentTimeMillis();
				tradeProcessingDuration.setUid("" + System.currentTimeMillis());
				Date currDate = new Date();
				tradeProcessingDuration.setTimestmp(currDate);
				tradeProcessingDuration.setIndicatorValue(value);
				tradeProcessingDuration.setTradeDate(currDate);
				tradeProcessingDuration.setStock(stocks[dataGenerator.nextInt(0, stocks.length-1)]);
				tradeProcessingDuration.setCustomer(customers[dataGenerator.nextInt(0, customers.length-1)]);
				tradeProcessingDuration.setBroker(brokers[dataGenerator.nextInt(0, brokers.length-1)]);

				producer.sendBody(mapper.writeValueAsString(tradeProcessingDuration));
				Thread.sleep(dataGenerator.nextLong(200, 1000));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String generateOne() throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();

		Double value = 10D; 
		TradeProcessingDuration tradeProcessingDuration = new TradeProcessingDuration();
		tradeProcessingDuration.setUid("" + System.currentTimeMillis());
		tradeProcessingDuration.setTimestmp(new Date());
		tradeProcessingDuration.setIndicatorValue(value);
		return mapper.writeValueAsString(tradeProcessingDuration);
	}

}
