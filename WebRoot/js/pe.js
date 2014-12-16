
/*
 * 正能量分析
 */
function PositiveEnergyAnalysis(oriData){
	/*		获取正能量本体的数据		*/
	var pe = FatchPositiveEnergyNoumenon(oriData);
	
	var eventList = pe.eventList;
	var dataReList = pe.dataReList;
	var optionStatistics = {
	    /*title : {
	        text: '正能量基本统计图',
	        x:'center'
	    },*/
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        orient : 'vertical',
	        x : 'left',
	        data:[eventList[0].name,eventList[1].name,eventList[2].name,eventList[3].name,eventList[4].name,eventList[5].name,eventList[6].name,eventList[7].name,eventList[8].name,eventList[9].name]
		    
	    },
	    /*toolbox: {
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
	        	name:'正能量本体',
	            type:'pie',
	            radius : [30, 100],
	            center : ['50%', 150],
	            roseType : 'area',
	            
	            //radius : '55%',
	            //center: ['50%', '60%'],
	            data:[
	                {value:eventList[0].statusCount, name:eventList[0].name},
	                {value:eventList[1].statusCount, name:eventList[1].name},
	                {value:eventList[2].statusCount, name:eventList[2].name},
	                {value:eventList[3].statusCount, name:eventList[3].name},
	                {value:eventList[4].statusCount, name:eventList[4].name},
	                {value:eventList[5].statusCount, name:eventList[5].name},
	                {value:eventList[6].statusCount, name:eventList[6].name},
	                {value:eventList[7].statusCount, name:eventList[7].name},
	                {value:eventList[8].statusCount, name:eventList[8].name},
	                {value:eventList[9].statusCount, name:eventList[9].name}
	            ]
	        }
	    ]
	};
		                    
	var optionTrend = {
	    /*title : {
	        text: '',
	        subtext: '',
 	        x: 'left',
	    },*/
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	    	x : 'right',
	        data:[eventList[0].name,eventList[1].name,eventList[2].name,eventList[3].name,eventList[4].name,eventList[5].name,eventList[6].name,eventList[7].name,eventList[8].name,eventList[9].name]
	    },
	    /*toolbox: {
	        show : true,
	        orient : 'vertical',
	        x: 'right',
	        y: 'center',
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
	            data : pe.timeArray
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
	        },
	        {
	            name:eventList[3].name,
	            type:'line',
	            data:dataReList[3],
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
	            name:eventList[4].name,
	            type:'line',
	            data:dataReList[4],
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
	            name:eventList[5].name,
	            type:'line',
	            data:dataReList[5],
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
	            name:eventList[6].name,
	            type:'line',
	            data:dataReList[6],
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
	            name:eventList[7].name,
	            type:'line',
	            data:dataReList[7],
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
	            name:eventList[8].name,
	            type:'line',
	            data:dataReList[8],
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
	            name:eventList[9].name,
	            type:'line',
	            data:dataReList[9],
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
	
	if ($('#PEEventStatistics div.chart').is(":visible")) {
		var chartStatistics = echarts.init($('#PEEventStatistics div.chart').get(0));	 
		chartStatistics.setOption(optionStatistics);
	}
	if ($('#PEEventTren div.chart').is(":visible")) {
		var chartTrend = echarts.init($('#PEEventTren div.chart').get(0));	 
		chartTrend.setOption(optionTrend);
	}
	
	GeneratePenergyEventTagCloud(eventList[0].id, $("#PEEventTagCloud #EventTagCloud"), oriData);
		
}

function initPositiveEnergy(oriData)
{
	PositiveEnergyAnalysis(oriData);
	changeCloudLabelColor($(".cloudLabelUl li a").eq(0));
}

	
$(".cloudLabelRow>span").click(function(){
	var idx = $(this).hasClass("pre")? -1 : 1;
	var obj = $(".cloudLabelUl li");
	var objLeft = Number(obj.parent().css("margin-left").slice(0, -2));
	var maxLeft = 0;
	var maxDistance = obj.parents("div").eq(0).width() - obj.eq(-1).position().left - obj.eq(-1).width();
	var minDistance = obj.eq(0).position().left;
	if ((idx == 1 && maxDistance >= -20 && maxDistance < 20) ||
		(idx == -1 && minDistance >= -20 && minDistance < 20))
	{
		return;
	}
	var show = 0, hide = 0, leftMove = 0, rightMove = 0;
	for (var i = 0; i < obj.length - 1; i++) {
		var left = Math.floor(obj.eq(i).position().left);
		if (left > -10 && left < 10)
		{
			show = i;
			leftMove = Math.floor(obj.eq(i+1).position().left);
			rightMove = Math.floor(obj.eq(i-1).position().left);
			break;
		}
	}
	var objFinalLeft = objLeft;
	show += idx;
	hide = show - 1;
	if (show > -1 && show < obj.length) {
		if (idx == 1)
		{
			objFinalLeft -= leftMove;
		}
		else if (idx == -1)
		{
			objFinalLeft -= rightMove;
		}
		if (objFinalLeft <= maxLeft)
		{
			obj.parent().css("margin-left", objFinalLeft);
			if (obj.eq(hide).children("a").children("span").hasClass("labelActive")) {
				obj.eq(show).children("a").trigger("click");
			}
			changeCloudLabelColor(obj.find("span.labelActive").parent().eq(0));
		}
	}
});
