package com.redhat.empowered.generic.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Resolution;

@Indexed
public class IndicatorRecord implements Serializable{

	private static final long serialVersionUID = 1L;
	protected String uid;
	protected BigDecimal indicatorValue;
	
	@Field 
	@DateBridge(resolution=Resolution.MINUTE) 
	protected Date timestmp;
	
	@Field
	protected BigDecimal frequencyGroupValue;
	
	@Field(analyze=Analyze.NO)
	protected String indicatorClass;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
	public BigDecimal getIndicatorValue() {
		return indicatorValue;
	}
	public void setIndicatorValue(BigDecimal indicatorValue) {
		this.indicatorValue = indicatorValue;
	}
	public Date getTimestmp() {
		return timestmp;
	}
	
	public void setTimestmp(Date timestmp) {
		this.timestmp = timestmp;
	}
	
	public BigDecimal getFrequencyGroupValue() {
		return frequencyGroupValue;
	}
	public void setFrequencyGroupValue(BigDecimal frequencyGroupValue) {
		this.frequencyGroupValue = frequencyGroupValue;
	}
	
	public String getIndicatorClass() {
		return indicatorClass;
	}
	public void setIndicatorClass(String indicatorClass) {
		this.indicatorClass = indicatorClass;
	}
	
	
	
	
}
