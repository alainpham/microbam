package com.redhat.empowered.generic.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.math3.distribution.NormalDistribution;

public class StatisticsRecord implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String key;
	private Integer count=0;

	private Double avg;
	private Double stdev;
	private TreeMap<Integer,Integer> frequencyData = new TreeMap<Integer,Integer>();
	private Double min;
	private Double max;

	private Double expectedPercentile = new Double(95);
	private Double expectedSlaValue = new Double(30);

	private Double actualPercentile;
	private Double actualSlaValue;

	//Sums	to calculate online variance and mean
	private Double totalSum;
	private Double mn; //Mk = Mk-1+ (xk – Mk-1)/k
	private Double sn; //Sk-1 + (xk – Mk-1)*(xk – Mk) -> variance s2 = Sk/(k – 1)

	public List<Map<String,Integer>> getfrequencyDataAsList(){

		List<Map<String,Integer>> frequencyList = new ArrayList<Map<String,Integer>>();


		for (Entry<Integer, Integer> entry : frequencyData.entrySet()) {
			Map<String,Integer> m = new HashMap<String,Integer>();
			m.put("x",entry.getKey());
			m.put("y",entry.getValue());
			frequencyList.add(m);
		}

		return frequencyList;

	}

	public List<List<Serializable>> getFrequencyAsArray(){
		//function formatted for c3.js
		List<List<Serializable>> frequencyList = new ArrayList<List<Serializable>>();
		List<Serializable> xVals  = new ArrayList<Serializable>();
		List<Serializable> yVals  = new ArrayList<Serializable>();
		frequencyList.add(xVals);
		frequencyList.add(yVals);
		xVals.add("x");
		yVals.add("y");

		for (Entry<Integer, Integer> entry : frequencyData.entrySet()) {
			Map<String,BigDecimal> m = new HashMap<String,BigDecimal>();
			xVals.add(entry.getKey());
			yVals.add(new BigDecimal(entry.getValue()));
		}

		return frequencyList;

	}

	public Double getExpectedPercentile() {
		return expectedPercentile;
	}

	public void setExpectedPercentile(Double expectedPercentile) {
		this.expectedPercentile = expectedPercentile;
	}

	public Double getExpectedSlaValue() {
		return expectedSlaValue;
	}

	public void setExpectedSlaValue(Double expectedSlaValue) {
		this.expectedSlaValue = expectedSlaValue;
	}

	public Double getActualPercentile() {

		NormalDistribution normalDistribution = new NormalDistribution(this.avg,Math.sqrt(this.stdev));
		actualPercentile = normalDistribution.cumulativeProbability(expectedSlaValue);
		actualPercentile = actualPercentile * 100D;
		return actualPercentile;
	}


	public void setActualPercentile(Double actualPercentile) {

		this.actualPercentile = actualPercentile;
	}

	public Double getActualSlaValue() {
		NormalDistribution normalDistribution = new NormalDistribution(this.avg,Math.sqrt(this.stdev));
		actualSlaValue = normalDistribution.inverseCumulativeProbability(expectedPercentile * 0.01D);
		return actualSlaValue;
	}

	public void setActualSlaValue(Double actualSlaValue) {
		this.actualSlaValue = actualSlaValue;
	}

	public TreeMap<Integer, Integer> getFrequencyData() {
		return frequencyData;
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Double getMax() {
		return max;
	}
	public void setMax(Double max) {
		this.max = max;
	}

	public void setFrequencyData(TreeMap<Integer, Integer> frequencyData) {
		this.frequencyData = frequencyData;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getAvg() {
		return avg;
	}


	public void setAvg(Double avg) {
		this.avg = avg;
	}

	public Double getStdev() {
		return stdev;
	}
	public void setStdev(Double stdev) {
		this.stdev = stdev;
	}

	public void update(IndicatorRecord indicatorRecord){


		Double newAvg;
		Double newTotalSum;
		Double newmn;
		Double newsn;
		Double newStdev = 0.0;
		Integer newCount = this.count + 1;

		//count = 0, n (newCount)=1
		if (this.count == 0) {
			newmn = indicatorRecord.getIndicatorValue();
			newsn = 0D;
			newTotalSum = indicatorRecord.getIndicatorValue();
			newAvg = indicatorRecord.getIndicatorValue();
			newStdev = 0D;
			this.min = indicatorRecord.getIndicatorValue();
			this.max = indicatorRecord.getIndicatorValue();
		} else{ //count >= 1 , n (newCount)=>2

			//calculate new average
			newTotalSum = this.totalSum + indicatorRecord.getIndicatorValue();
			newAvg = (newTotalSum)/newCount.doubleValue();

			//calculate sums for standard dev
			newmn = this.mn + (indicatorRecord.getIndicatorValue()-this.mn)/newCount.doubleValue();
			newsn = this.sn + (indicatorRecord.getIndicatorValue()-this.mn)*(indicatorRecord.getIndicatorValue()-newmn);
			newStdev = Math.sqrt(newsn/(this.count));
		}


		//calculate min/max
		if (this.max < indicatorRecord.getIndicatorValue()){
			this.max = indicatorRecord.getIndicatorValue();
		}
		if (this.min > indicatorRecord.getIndicatorValue()){
			this.min = indicatorRecord.getIndicatorValue();
		}
		//updating indicators
		this.count = newCount;
		this.totalSum = newTotalSum;
		this.mn = newmn;
		this.sn = newsn;
		this.avg = newAvg;
		this.stdev = newStdev;

		//calculate percentiles and slas

		//calculate frequency chart
		Integer currentFrequency = frequencyData.get(indicatorRecord.getFrequencyGroupValue());
		if (currentFrequency!=null){
			frequencyData.put(indicatorRecord.getFrequencyGroupValue(), currentFrequency+1);
		}else
		{
			frequencyData.put(indicatorRecord.getFrequencyGroupValue(), 1);
		}
	}


}
