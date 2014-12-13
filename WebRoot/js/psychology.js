
/*
 * 心理分析
 */
function PsychologyAnalysis(){
	/*		获取心理本体的数据		*/
	var psychology = FatchPsychologyNoumenon();
	
	var eventList = psychology.eventList;
	var dataReList = psychology.dataReList;
	var optionStatistics = {
	    /*title : {
	        text: '心理基本统计图',
	        subtext: '季度数据',
	        x:'center'
	    },*/
		tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    /*legend: {
	        orient : 'vertical',
	        x : 'left',
	        data:[eventList[0].name,eventList[1].name,eventList[2].name]
	    	
		    
	    },
	    toolbox: {
	        show : true,
	        feature : {
	            mark : {show: true},
	            dataView : {show: true, readOnly: false},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : true,*/
	    series : [
	        {
	            name:'心理本体',
	            type:'pie',
	            radius : '55%',
	            center: ['50%', '60%'],
	            itemStyle: {
					normal: {
						label: {
							textStyle: {
								fontSize: 24,
								fontFamily : '微软雅黑',
								fontWeight : 'bolder'
							}
						}
					}
				},
				data:[
	                {value:eventList[0].statusCount, name:eventList[0].name},
	                {	
						value:eventList[1].statusCount, 
						name:eventList[1].name
						/* itemStyle: {
							normal: {
								color: '#000000',
								label: {
									textStyle: {
										color: '#000000'
									}
								},
								labelLine: {
									lineStyle: {
										color: '#000000'
									}
								}
							}
						} */
					},
	                {	
						value:eventList[2].statusCount, 
						name:eventList[2].name,
						itemStyle: {
							normal: {
								color: '#34495e',
								label: {
									textStyle: {
										color: '#34495e'
									}
								},
								labelLine: {
									lineStyle: {
										color: '#34495e'
									}
								}
							}
						}
					}
	            ]
	        }
	    ]
	};
		                    
	var optionTrend = {
	    /*title : {
	        text: '心理本体走势图',
	        subtext: '季度数据'
	    },*/
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        data:[eventList[0].name,eventList[1].name,eventList[2].name]
	    },
	    /*toolbox: {
	        show : true,
	        feature : {
	        	dataZoom : {show: true},
	            dataView : {show: true, readOnly: false},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },*/
	    dataZoom : {
	        show : true,
	        realtime: true,
	        start : 0,
	        end : 100
	    },
	    calculable : true,
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : false,
	            data : psychology.timeArray
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            axisLabel : {
	                formatter: '{value}'
	            }
	        }
	    ],
	    series : [
	        {
	            name:eventList[0].name,
	            type:'line',
	            data:dataReList[0],
	            markPoint : {
	                data : [
	                    {type : 'max', name: '最大值'},
	                    {type : 'min', name: '最小值'}
	                ]
	            }/*,
	            markLine : {
	                data : [
	                    {type : 'average', name: '平均值'}
	                ]
	            }*/
	        },
	        {
	            name:eventList[1].name,
	            type:'line',
	            data:dataReList[1],
	            markPoint : {
	                data : [
	                    {type : 'max', name: '最大值'},
	                    {type : 'min', name: '最小值'}
	                ]
	            }/*,
	            markLine : {
	                data : [
	                    {type : 'average', name : '平均值'}
	                ]
	            }*/
	        },
	        {
	            name:eventList[2].name,
	            type:'line',
	            data:dataReList[2],
	            markPoint : {
	                data : [
	                    {type : 'max', name: '最大值'},
	                    {type : 'min', name: '最小值'}
	                ]
	            }/*,
	            markLine : {
	                data : [
	                    {type : 'average', name : '平均值'}
	                ]
	            }*/
	        }
	    ]
	};
	
	var chartStatistics = echarts.init($('#PsychologyEventStatistics div.chart').get(0));	 
	chartStatistics.setOption(optionStatistics);
	var chartTrend = echarts.init($('#PsychologyEventTrend div.chart').get(0));	 
	chartTrend.setOption(optionTrend);
	
	GeneratePsychologyEventTagCloud(eventList[0].id);
		
}
