package com.redhat.empowered.generic.model;


import org.springframework.beans.BeanUtils;


public class GenericObjectFactory {

	public StatisticsRecord createStatisticsRecord(String key) {
		StatisticsRecord statisticsRecord = new StatisticsRecord();
		statisticsRecord.setKey(key);
		return statisticsRecord;
	}

	public IndicatorRecord createIndicatorRecord(IndicatorRecord indicatorRecord){
		IndicatorRecord newIndicatorRecord  = new IndicatorRecord();
		BeanUtils.copyProperties(indicatorRecord, newIndicatorRecord);
		return newIndicatorRecord;		
	}
}
