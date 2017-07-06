/**
 *
 */

app.controller('drillDownProjectionController',drillDownProjectionController);


function drillDownProjectionController($rootScope,$scope,$interval,projections,globalVars,bus){

  this.chart = null;
  this.data = null;
  this.frequencyGroupValue = null;
  this.groupBy = null;
  this.bindTo = null;



  this.init = function(){
    var chartConfig = $().c3ChartDefaults();
    this.groupBy = $scope.groupBy;

    console.log("bind to" + $scope.bindTo);
    console.log("groupBy" + $scope.groupBy);

		chartConfig.bindto = $scope.bindTo;


		chartConfig.data = {
				type: 'donut',
				onclick: function (d, i) {
          console.log("onclick", d, i);
        },
        columns: []
		};
    chartConfig.donut = {
      title: this.groupBy
    };
		this.chart = c3.generate(chartConfig);

    $scope.$on('frequencyGroupValue', function() {
      console.log('drillDownProjectionController received ' + bus.message);
      this.frequencyGroupValue = bus.message.x ;
      this.update();
    }.bind(this));

  };

  this.update = function(){
    console.log("drilling down with frequencyGroupValue = " + this.frequencyGroupValue);
    projections.get(
      globalVars.businessObject,
      this.frequencyGroupValue,
      globalVars.selectCountColumn,
      this.groupBy,
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
    // this.chart.unload();

    this.chart.load({
      unload: true,
      columns: this.data
    });

  }
  this.init();
};
