/**
 * 这个js主要用于第二个模块(行为)使用，其中的类对象在包nlp.sina.model.behaviour中
 */

/**
 * 微博发布形式分布
 * 请求：WeiboReleaseForm.action
 * 返回类型：json
 * 返回数据：json.form，这是个类对象，参考ReleaseForm类
 */
function WeiboReleaseForm(oriData){
	$.ajax({
		async : false,
		url : 'WeiboReleaseForm.action',
		type : 'POST',
		dataType: 'json',
		data: oriData,
		success : function(json, status) {
			var form = json.form;
			var total = form.piced+form.unpiced;
				var radius = [70, 100];
				var textStyle = {
								color: "#f98b18",
								fontSize: 24,
								fontFamily : '微软雅黑',
								fontWeight : 'bolder'
							};
				var itemStyle = {
					normal: {
						label: {
							color: "#f98b18",
							textStyle: textStyle
						}
					}
				};
			var labelBottom = {
				    normal : {
						color: "#ffffff",
				        label : {
				            show : true,
				            textStyle: {
							color: "#f98b18",
				                baseline : 'top'
				            }
				        },
				        labelLine : {
				            show : false
				        }
				    },
				    emphasis: {
				        color: 'rgba(0,0,0,0)',
				        label : {
				            show : true,
				            textStyle: textStyle
				        },
				        labelLine : {
				            show : false
				        }
				    }
				};
				var labelTop = {
				    normal : {
						color: "#ffffff",
				        label : {
				            show : true,
				            formatter : function (a,b,c){return parseInt(100*(total-c)/total) + '%'},
				            textStyle: {
				                baseline : 'bottom'
				            }
				        },
				        labelLine : {
				            show : false
				        }
				    },
				    emphasis: {
				        color: 'rgba(0,0,0,0)',
				        label : {
				            show : true,
				            textStyle: textStyle
				        },
				        labelLine : {
				            show : false
				        }
				    }
				};
				var noLabelItemStyle = {
					normal: {
						label: {
							show: false
						},
				        labelLine : {
				            show : false
				        }
					}
				};
				
				var placeHolderStyle = {
					normal : {
						color: 'rgba(0,0,0,0)',
						label: {show:false},
						labelLine: {show:false}
					},
					emphasis : {
						color: 'rgba(0,0,0,0)'
					}
				};

				option = {
				   /* legend: {
				        x : 'center',
				        y : 'bottom',
				        data:[
				            '图片','地理信息','表情'
				        ]
				    },
				    title : {
				        text: '微博发布形式',
				        subtext: '季度数据',
				        x: 'center'
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            dataView : {show: true, readOnly: false},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },*/
				    series : [
				        {
				            name : '图片形式',
							type : 'pie',
				            center : ['20%', '36%'],
				            radius : radius,
							clockWise:false,
							itemStyle : noLabelItemStyle,
				            data : [
				                {name:'图片形式', value:form.piced},
				                {name:'other', value:form.unpiced, itemStyle: placeHolderStyle}
				            ],
							markPoint: {
								symbol: 'arrow',
								itemStyle: itemStyle,
								data : [
									{name:'other', value:form.unpiced, itemStyle : labelTop, x:'20%', y:'60%', symbolSize:32},
									{name:'图片形式', value:'图片形式', itemStyle : labelBottom, x:'20%', y:'75%', symbolSize:32}
								]
							}
				        },
				        {
				            name : '地理信息',
							type : 'pie',
				            center : ['49%', '37%'],
				            radius : radius,
							clockWise:false,
							itemStyle : noLabelItemStyle,
				            data : [
				                {name:'地理信息', value:form.geoed},
				                {name:'other', value:form.ungeoed, itemStyle : placeHolderStyle}
				            ],
							markPoint: {
								symbol: 'arrow',
								itemStyle: itemStyle,
								data : [
									{name:'other', value:form.ungeoed, itemStyle : labelTop, x:'49%', y:'60%', symbolSize:32},
									{name : '地理信息', value : '地理信息', itemStyle : labelBottom, x:'49%', y:'75%', symbolSize:32}
								]
							}
				        },
				        {
							name: "微博表情",
				            type : 'pie',
				            center : ['78%', '37%'],
				            radius : radius,
							clockWise:false,
							itemStyle : noLabelItemStyle,
				            data : [
				                {name:'微博表情', value:form.emotioned},
				                {name:'other', value:form.unemotioned, itemStyle : placeHolderStyle}
				            ],
							markPoint: {
								symbol: 'arrow',
								itemStyle: itemStyle,
								data : [
									{name:'other', value:form.unemotioned, itemStyle : labelTop, x:'78%', y:'60%', symbolSize:32},
									{name : '微博表情', value : '微博表情', itemStyle : labelBottom, x:'78%', y:'75%', symbolSize:32}
								]
							}
				        }
				    ]
				};
				                    
			var chart = echarts.init($('#WeiboReleaseForm div.chart').get(0));	 
			chart.setOption(option);
			
		},
		error : function(a, b) {
			alert("可能由于网络原因，数据获取失败，请联系管理员");
		}
	});
}
/**
 * 发布微博的手机品牌分布
 * 请求：WeiboPhoneBrandMap.action
 * 返回类型：json
 * 返回数据：json.brandList，这是个数组，其元素参考类对象PhoneBrand
 */
function WeiboPhoneBrandMap(oriData){
	$.ajax({
		async : false,
		url : 'WeiboPhoneBrandMap.action',
		type : 'POST',
		dataType: 'json',
		data: oriData,
		success : function(json, status) {
			var brandList = json.brandList;
			var nameArray=[];
			var dataList=[];
			for(var i=0 ; i<brandList.length; i++){
				nameArray.push(brandList[i].brand);
				var data={name:brandList[i].brand,value:brandList[i].number};
				dataList.push(data);
			}
			option = {
				    /*title : {
				        text: '微博手机硬件分布',

				    },*/
				    tooltip : {
				        trigger: 'axis'
				    },
				    /*legend: {
				        data:['使用情况']
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            mark : {show: true},
				            dataView : {show: true, readOnly: false},
				            magicType: {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,*/
				    xAxis : [
				        {
				            type : 'value',
				            boundaryGap : [0, 0.01]
				        }
				    ],
				    yAxis : [
				        {
				            type : 'category',
				            data : nameArray
				        }
				    ],
				    series : [
				        {
				            name:'使用情况',
				            type:'bar',
				            data:dataList
				        }
				    ]
				};

			var chart = echarts.init($('#WeiboPhoneBrandMap div.chart').get(0));	 
			chart.setOption(option);
		},
		error : function(a, b) {
			alert("可能由于网络原因，数据获取失败，请联系管理员");
		}
	});
}

/**
 * 发布微博的软件来源分布
 * 请求：WeiboSourceNameMap.action
 * 返回类型：json
 * 返回数据：json.sourceList，这是个数组，其元素参考类对象SourceName
 */
function WeiboSourceNameMap(oriData){
	$.ajax({
		async : false,
		url : 'WeiboSourceNameMap.action',
		type : 'POST',
		dataType: 'json',
		data: oriData,
		success : function(json, status) {
			var sourceList = json.sourceList;
			var nameArray=[];
			var dataList=[];
			for(var i=0 ; i<sourceList.length; i++){
				nameArray.push(sourceList[i].sourceName);
				var data={name:sourceList[i].sourceName,value:sourceList[i].number};
				dataList.push(data);
			}
			option = {
				    /*title : {
				        text: '微博手机硬件分布',

				    },*/
				    tooltip : {
				        trigger: 'axis'
				    },
				    /*legend: {
				        data:['使用情况']
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            mark : {show: true},
				            dataView : {show: true, readOnly: false},
				            magicType: {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,*/
				    xAxis : [
				        {
				            type : 'value',
				            boundaryGap : [0, 0.01]
				        }
				    ],
				    yAxis : [
				        {
				            type : 'category',
				            data : nameArray
				        }
				    ],
				    series : [
				        {
				            name:'使用情况',
				            type:'bar',
				            data:dataList
				        }
				    ]
				};
			var chart = echarts.init($('#WeiboSourceNameMap div.chart').get(0));	 
			chart.setOption(option);	                    
		},
		error : function(a, b) {
			alert("可能由于网络原因，数据获取失败，请联系管理员");
		}
	});
}
/**
 * 发布微博的时间段数量分布
 * 请求：WeiboTimePeriodMap.action
 * 返回类型：json
 * 返回数据：json.timeList，这是数组，其元素参考类对象TimePeriod
 */
function WeiboTimePeriodMap(oriData){
	$.ajax({
		async : false,
		url : 'WeiboTimePeriodMap.action',
		type : 'POST',
		dataType: 'json',
		data: oriData,
		success : function(json, status) {
			var timeList = json.timeList;
			var name=[];
			var dataList=[];
			for(var i=0 ; i<timeList.length ; i++){
				name.push(timeList[i].period);
				dataList.push(timeList[i].number);
			}
			var option = {
			   /* title : {
			        text: '微博时间段分布图',
			        subtext: '季度数据'
			    },*/
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['小时']
			    },/*
			    toolbox: {
			        show : true,
			        feature : {
			            //mark : {show: true},
			            //dataView : {show: true, readOnly: false},
			            magicType : {show: true, type: ['line', 'bar']},
			            //restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,*/
			    xAxis : [
			        {
			            type : 'category',
			            boundaryGap : false,
			            data : name
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
			            name:'小时',
			            type:'line',
			            data:dataList,
			            markPoint : {
			                data : [
			                    {type : 'max', name: '最大值'},
			                    {type : 'min', name: '最小值'}
			                ]
			            },
			            markLine : {
			                data : [
			                    {type : 'average', name: '平均值'}
			                ]
			            }
			        }
			    ]
			};
				                    
			var chart = echarts.init($('#WeiboTimePeriodMap div.chart').get(0));	 
			chart.setOption(option);	  
		},
		error : function(a, b) {
			alert("可能由于网络原因，数据获取失败，请联系管理员");
		}
	});
}
/**
 * 用户关系图
 */
function UserRelationshipMap(){
	
}

