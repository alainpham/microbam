/**
 *
 */

app.controller('metricsController',metricsController);



function metricsController($rootScope,$scope,$interval,metrics,globalVars,bus){

	//members declaration
	this.dailySummaryBarChart = null;
	this.intervalPromise = null;
	this.data = null;


	//init function
	this.init = function(){
		var dailySummaryBarChartConfig = $().c3ChartDefaults().getDefaultBarConfig();
		dailySummaryBarChartConfig.bindto = '#dailySummaryBarChart';
		dailySummaryBarChartConfig.data = {
				type: 'bar',
				onclick: function (d, i) {
					console.log("onclick", d, i);
					bus.publishFrequencyGroupValue(d);
				},
				x: 'x',
				columns: []
		};
		this.dailySummaryBarChart = c3.generate(dailySummaryBarChartConfig);

		//first update
		this.update();

		//set update interval;
		this.intervalPromise = $interval(function() {
			this.update();
		}.bind(this), globalVars.updateIntervalMs);

		//destroy interval
		$scope.$on('$destroy', function () {
			console.log('desroying');
			$interval.cancel(this.intervalPromise);
		}.bind(this)
		);
	};

	this.update = function(){
		var chart = this.dailySummaryBarChart;
		console.log("reloading..");
		//calling webservice
		metrics.get(
				globalVars.businessObject,
				'daily',
				$rootScope.currentServerTime.substring(0,8),
				function(data){

					if (data.data == ""){
						return;
					}

					this.data=data.data;
					this.updateDailySummaryBarChart();

				}.bind(this)
		);
	};


	this.updateDailySummaryBarChart = function(){

		//color regions on graph
		this.dailySummaryBarChart.regions([
		               {
		            	   axis: 'x',
		            	   end: this.data.expectedSlaValue,
		            	   class: 'sla-ok-region'
		               },
		               {
		            	   axis: 'x',
		            	   start: this.data.expectedSlaValue,
		            	   class: 'sla-ko-region'
		               }
		               ]
		);

		var limitSlaXGrid = {
				value: this.data.expectedSlaValue,
				text: 'Limit SLA',
				class : 'limit-sla-grid',
				position: 'middle'
		};



		var actualSlaXGrid = {
				value: this.data.actualSlaValue,
				class : ((this.data.actualSlaValue > this.data.expectedSlaValue) ? 'actual-ko-sla-grid' : 'actual-ok-sla-grid'),
				text: 'Actual SLA'
		};

		this.dailySummaryBarChart.xgrids([
		              limitSlaXGrid,
		              actualSlaXGrid
		              ]);

		this.dailySummaryBarChart.load({
			columns: this.data.frequencyAsArray
		});
	}


	this.init();

};
