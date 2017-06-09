/**
 *
 */

app.controller('drillDownController',drillDownController);


function drillDownController($rootScope,$scope,$interval,indicators,globalVars,bus){

  this.chart = null;
  this.data = null;
  this.frequencyGroupValue = null;


  this.init = function(){
    var chartConfig = $().c3ChartDefaults();
		chartConfig.bindto = '#drillDownPieChart';
		chartConfig.data = {
				type: 'donut',
				onclick: function (d, i) {
          console.log("onclick", d, i);
        },
        columns: [
            ["setosa", 10,10],
            ["versicolor", 10],
            ["virginica", 10],
        ]
		};
    chartConfig.donut = {
        title: "Iris Petal Width"
    };
		this.chart = c3.generate(chartConfig);

    $scope.$on('frequencyGroupValue', function() {
      console.log('drillDownController received ' + bus.message);
      this.frequencyGroupValue = bus.message.x ;
      this.update();
    }.bind(this));

  };

  this.update = function(){
    console.log("drilling down with frequencyGroupValue = " + this.frequencyGroupValue);
    indicators.get(
      globalVars.businessObject,
      this.frequencyGroupValue,
      function(data){
        console.log('get data');
        console.log(data);
        if (data.data == ""){
          return;
        }
        this.data=data.data;
        this.updateChart();
      }.bind(this)
    );
  }

  this.updateChart = function(){
    console.log(this.data);
    this.chart.load({
      columns: this.data
    });
  }
  this.init();
};
