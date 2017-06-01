package com.redhat.empowered.generic.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Indexed
public class IndicatorRecord implements Serializable{

	private static final long serialVersionUID = 1L;
	protected String uid;
	
	
	protected Double indicatorValue;

	@Field(analyze = Analyze.NO)
	protected Date timestmp;

	@Field(analyze = Analyze.NO)
	protected Integer frequencyGroupValue;

	@Field(analyze = Analyze.NO)
	protected String indicatorClass;

	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}

	public Double getIndicatorValue() {
		return indicatorValue;
	}
	public void setIndicatorValue(Double indicatorValue) {
		this.indicatorValue = indicatorValue;
	}

	public Date getTimestmp() {
		return timestmp;
	}

	public void setTimestmp(Date timestmp) {
		this.timestmp = timestmp;
	}

	public Integer getFrequencyGroupValue() {
		return frequencyGroupValue;
	}
	public void setFrequencyGroupValue(Integer frequencyGroupValue) {
		this.frequencyGroupValue = frequencyGroupValue;
	}

	public String getIndicatorClass() {
		return indicatorClass;
	}

	public void setIndicatorClass(String indicatorClass) {
		this.indicatorClass = indicatorClass;
	}
	
}
