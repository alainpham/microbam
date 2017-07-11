package com.redhat.empowered.specific.model.trading;

import java.util.Date;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;

import com.redhat.empowered.generic.model.IndicatorRecord;

@Indexed
public class TradeProcessingDuration extends IndicatorRecord {

	private static final long serialVersionUID = 1L;
	
	//dimensions of the indicator
	@Field(analyze=Analyze.NO)
	private String tradeID;
	
	@Field(store=Store.YES,analyze=Analyze.NO)
	private String stock;
	
	@Field(store=Store.YES,analyze=Analyze.NO)
	private String stockExchange;
	
	@Field(store=Store.YES,analyze=Analyze.NO)
	private String broker;
	
	@Field(store=Store.YES,analyze=Analyze.NO)
	private String type;
	
	@Field(store=Store.YES,analyze=Analyze.NO)
	private String customer;
	
	@Field(analyze=Analyze.NO)
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
	public Integer getFrequencyGroupValue() {
		return new Long(Math.round(this.getIndicatorValue())).intValue();
	}
	
	@Override
	public String getIndicatorClass() {
		return TradeProcessingDuration.class.getSimpleName();
	}
	
	@Override
	public Date getTimestmp() {
		return timestmp;
	}
	
	
}
