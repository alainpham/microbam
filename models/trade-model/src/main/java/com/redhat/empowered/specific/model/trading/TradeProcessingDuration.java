package com.redhat.empowered.specific.model.trading;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import com.redhat.empowered.generic.model.IndicatorRecord;

public class TradeProcessingDuration extends IndicatorRecord {

	private static final long serialVersionUID = 1L;
	
	//dimensions of the indicator
	private String tradeID;
	private String stock;
	private String stockExchange;
	private String broker;
	private String type;
	private String customer;
	private Date tradeDate;
	
	public String getTradeID() {
		return tradeID;
	}
	public void setTradeID(String tradeID) {
		this.tradeID = tradeID;
	}
	
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getStockExchange() {
		return stockExchange;
	}
	public void setStockExchange(String stockExchange) {
		this.stockExchange = stockExchange;
	}
	public String getBroker() {
		return broker;
	}
	public void setBroker(String broker) {
		this.broker = broker;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}
	
	//ovride to define gouping granularity
	@Override
	public BigDecimal getFrequencyGroupValue() {
		return this.indicatorValue.setScale(0, RoundingMode.HALF_UP);
	}
	
	@Override
	public String getIndicatorClass() {
		return TradeProcessingDuration.class.getSimpleName();
	}
	
}
