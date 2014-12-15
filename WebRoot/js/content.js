/**
 * 这个js主要用于第一个模块(内容分析)使用，其中的类对象在包nlp.sina.model.content中
 */

/**
 * 内容分析模块的基本信息
 * 请求：BasicInfo.action
 * 返回类型：json
 * 返回数据：json.binfo，这是个类对象，参考BasicInfo
 */
function BasicInfo(oriData){
	$.ajax({
		async : false,
		url : 'BasicInfo.action',
		type : 'POST',
		dataType: 'json',
		data: oriData,
		success : function(json, status) {
			var binfo = json.binfo;
			var option = {
			  /*  title : {
			        text: '转发原创比例统计图',
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
			        data:['原创率','转发率']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            dataView : {show: true, readOnly: false},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,*/
			    series : [
			        {
			            name:'基本信息',
			            type:'pie',
			            radius : '55%',
			            center: ['50%', '50%'],
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
			                {value:binfo.repostedStatusCount, name:'原创率'},
			                {value:binfo.originalStatusCount, name:'转发率'}
			                
			            ]
			        }
			    ]
			};
			if ($('#ForwardOriginalMap div.chart').is(":visible")) {
				var chart = echarts.init($('#ForwardOriginalMap div.chart').get(0));	 
				chart.setOption(option);
			}
			option = {
/* 				title : {
			    	text: '基本数量统计图',
			        subtext: '季度数据',
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			    	data : ['数量统计']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            dataView : {show: true, readOnly: false},		   
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
 */			    xAxis : [
			        {
			            type : 'category',
						axisLabel: {
							textStyle: {
								fontSize: 18,
								fontFamily : '微软雅黑',
								fontWeight : 'bolder'
							}
						},							
			            data : ['转发总量','评论总量','表态总量','阅读总量']
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'数量统计',
			            type:'bar',
						itemStyle: {
							normal: {
							}
						},
			            data:[binfo.statusRepostCount,binfo.statusCommentCount,binfo.statusAttitudeCount,binfo.statusReadingCount]
			            
			        }
			    ]
			};                    
			
			if ($('#BasicQuantityMap div.chart').is(":visible")) {
				chart = echarts.init($('#BasicQuantityMap div.chart').get(0));	 
				chart.setOption(option);
			}
		},
		error : function(a, b) {
			alert("可能由于网络原因，获取预设话题失败，请联系管理员");
		}
	});
}

/**
 * 预设话题分析其实就是对应于内容本体的分析
 *
 */
function PresetFocusAnalysis(oriData){
	/*		获取内容本体的数据		*/
			var preFocus = FatchContentNoumenon(oriData);
			var eventList = preFocus.eventList;
			var dataReList = preFocus.dataReList;
			var optionStatistics = {
	/* 	    title : {
	        text: '预设话题基本统计图',
	        subtext: '季度数据',
	        x:'center'
	    },
		 */	    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient : 'vertical',
			        x : 'left',
			        data:[eventList[0].name,eventList[1].name,eventList[2].name,eventList[3].name,eventList[4].name]
			    },
		/* 	    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},											
			            dataView : {show: true, readOnly: false},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
		 */	    series : [
			        {
			            name:'预设话题',
			            type:'pie',
			            radius : '60%',
			            center: ['50%', '65%'],
			            data:[
			                {value:eventList[0].statusCount, name:eventList[0].name},
			                {value:eventList[1].statusCount, name:eventList[1].name},
			                {value:eventList[2].statusCount, name:eventList[2].name},
			                {value:eventList[3].statusCount, name:eventList[3].name},
			                {value:eventList[4].statusCount, name:eventList[4].name}
			            ]
			        }
			    ]
			};
				                    
			var optionTrend = {
			    title : {
			        text: '',
			        subtext: '季度数据'
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:[eventList[0].name,eventList[1].name,eventList[2].name,eventList[3].name,eventList[4].name]
			    },
			    toolbox: {
			        show : true,
			        feature : {
			        	dataZoom : {show: true},
			            dataView : {show: true, readOnly: false},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
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
			            data : preFocus.timeArray
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
			            },
			            markLine : {
			                data : [
			                    {type : 'average', name: '平均值'}
			                ]
			            }
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
			            },
			            markLine : {
			                data : [
			                    {type : 'average', name : '平均值'}
			                ]
			            }
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
			            },
			            markLine : {
			                data : [
			                    {type : 'average', name : '平均值'}
			                ]
			            }
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
			            },
			            markLine : {
			                data : [
			                    {type : 'average', name : '平均值'}
			                ]
			            }
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
			            },
			            markLine : {
			                data : [
			                    {type : 'average', name : '平均值'}
			                ]
			            }
			        }
			    ]
			};
			
			if ($('#PresetFocusAnalysis div.chart').is(":visible")) {
				var chartStatistics = echarts.init($('#PresetFocusAnalysis div.chart').get(0));	 
				chartStatistics.setOption(optionStatistics);
			}
			if ($('#PresetFocusTrend div.chart').is(":visible")) {
				var chartTrend = echarts.init($('#PresetFocusTrend div.chart').get(0));	 
				chartTrend.setOption(optionTrend);
			}
			
			GenerateEventTagCloud(eventList[0].id, $("#KeyWordsMap #EventTagCloud"), oriData);
}

/**
 * 对于选定的话题，做时间走势图
 * 请求：SelectedFocusTrend.action
 * 参数：idListStr(选定的话题的ID组成的数组)
 * 返回类型：json
 * 返回数据：json.sfocus，这是个类对象，参考SelectedFocus
 */
function SelectedFocusTrend(idListStr, oriData){
	var data={"idListStr":idListStr};
	$.ajax({
		async : false,
		url : 'SelectedFocusTrend.action',
		type : 'GET',
		dataType: 'json',
		data: oriData,
		success : function(json, status) {
			var sfocus = json.sfocus;
			var focusList = sfocus.focusList;
			var dataReList = sfocus.dataReList;
			var nameArray=[];
			var series=[];
			for(var i=0 ; i<focusList.length ; i++){
				nameArray.push(focusList[i].name);
				var data={
					name:focusList[i].name,
		            type:'line',
		            data:dataReList[i],
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
				};
				series.push(data);
			}
			var optionTrend = {
		    title : {
		        text: '',
		        subtext: '季度数据'
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:nameArray
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            dataView : {show: true, readOnly: false},
		            dataZoom : {show: true},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
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
		            data : sfocus.timeArray
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
		    series : series
			};
			var chart = echarts.init($('#ExtractFocusTrend div.chart').get(0));	 
			chart.setOption(optionTrend);
		},
		error : function(a, b) {
			alert("可能由于网络原因，获取预设话题失败，请联系管理员");
		}
	});
}

function focusPageLinks(oriData){
	console.log("on");
	var idx = $(this).parents("tr").find("td.rowId input").val();
	var rowName = $(this).html();
	GenerateFocusTagCloud(idx,rowName, oriData);
	return false;
};


/**
 * 对某个抽取的话题，做关键词云图
 * GenerateFocusTagCloud.action
 * 参数：focusId(话题的ID)
 * 返回类型：json
 * 返回数据：json.keywordList，关键词数组，其元素对象nlp.sina.model.KeyWord
 */
function GenerateFocusTagCloud(focusId,showName, oriData){
	$("#FocusTagCloud").prevAll(".loadingCover").show();
	$("#FocusTagCloud").html("");
	$.ajax({
		async : false,
		url : 'GenerateFocusTagCloud.action?focusId='+focusId,
		type : 'GET',
		dataType: 'json',
		data: oriData,
		success : function(json, status) {
			var keywordList = json.keywordList ;
			var word_list = [];
			for(var i=0 ; i<keywordList.length ; i++){
				if(i%3==0 && i!=0){
					var t ={text:keywordList[i].name,weight:keywordList[i].freq,html: {"class": "vertical"}};
					word_list.push(t);
				}
				else{
					var t ={text:keywordList[i].name,weight:keywordList[i].freq};
					word_list.push(t);
				}
			}
			$("#FocusTagCloud").jQCloud(word_list, { afterCloudRender: function(){
				$("#FocusTagCloud").prevAll(".loadingCover").hide();
			}});
			$("#Span_FocusTagCloud").html(showName);
			 
		},
		error : function(a, b) {
			alert("可能由于网络原因，获取预设话题失败，请联系管理员");
		}
	});
}
/**
 * 对所有话题，做关键词云图
 * 请求：GenerateFocusTagCloud_All.action
 * 返回类型：json
 * 返回数据：json.keywordList，关键词数组，其元素对象nlp.sina.model.KeyWord
 */
function GenerateFocusTagCloud_All(oriData){
	$("#FocusTagCloud_All").html("");
	$.ajax({
		async : false,
		url : 'GenerateFocusTagCloud_All.action',
		type : 'GET',
		dataType: 'json',
		data: oriData,
		success : function(json, status) {
			var keywordList = json.keywordList ;
			var word_list = [];
			for(var i=0 ; i<keywordList.length ; i++){
				if(i%3==0 && i!=0){
					var t ={text:keywordList[i].name,weight:keywordList[i].freq,html: {"class": "vertical"}};
					word_list.push(t);
				}
				else{
					var t ={text:keywordList[i].name,weight:keywordList[i].freq};
					word_list.push(t);
				}
			}
			$("#FocusTagCloud_All").jQCloud(word_list);
			 
		},
		error : function(a, b) {
			alert("可能由于网络原因，获取数据失败，请联系管理员");
		}
	});
}
/**
 * 获取抽取的话题列表
 * 请求：FatchExtractedFocusList.action
 * 返回类型：json
 * 返回数据：json.focusList，话题列表，其元素参考nlp.sina.model.Focus
 */
function FatchExtractedFocusList(oriData){
	var focusList=[];
	$.ajax({
		async : false,
		url : 'FatchExtractedFocusList.action',
		type : 'POST',
		dataType: 'json',
		data: oriData,
		success : function(json, status) {
			focusList = json.focusList;
		},
		error : function(a, b) {
			alert("可能由于网络原因，获取预设话题失败，请联系管理员");
		}
	});
	return focusList;
}
/**
 * 对于选定的某些微话题，做时间走势图
 * 请求：SelectedTopicTrend.action
 * 参数：idListStr(选定的微话题的ID组成的数组)
 * 返回类型：json
 * 返回数据：json.stopic，这是个类对象，参考SelectedTopic
 */
function SelectedTopicTrend(idListStr, oriData){
	var data={"idListStr":idListStr};
	$.ajax({
		async : false,
		url : 'SelectedTopicTrend.action',
		type : 'GET',
		data : data,
		dataType: 'json',
		data: oriData,
		success : function(json, status) {
			var stopic = json.stopic;
			var topicList = stopic.topicList;
			var dataReList = stopic.dataReList;
			var nameArray=[];
			var series=[];
			for(var i=0 ; i<topicList.length ; i++){
				nameArray.push(topicList[i].name);
				var data={
					name:topicList[i].name,
		            type:'line',
		            data:dataReList[i],
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
				};
				series.push(data);
			}
			var optionTrend = {
		    title : {
		        text: '',
		        subtext: '季度数据'
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:nameArray
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            dataView : {show: true, readOnly: false},
		            dataZoom : {show: true},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
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
		            data : stopic.timeArray
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
		    series : series
			};
			var chart = echarts.init($('#ExtractTopicTrend div.chart').get(0));	 
			chart.setOption(optionTrend);
		},
		error : function(a, b) {
			alert("可能由于网络原因，获取预设话题失败，请联系管理员");
		}
	});
}

/**
 * 获取微话题列表
 * 请求：FatchTopicList.action
 * 返回类型：json
 * 返回数据：json.topicList，微话题列表，其元素参考类对象Topic
 */
function FatchTopicList(oriData){
	var topicList=[];
	$.ajax({
		async : false,
		url : 'FatchTopicList.action',
		type : 'GET',
		dataType: 'json',
		data: oriData,
		success : function(json, status) {
			topicList = json.topicList;
		},
		error : function(a, b) {
			alert("可能由于网络原因，获取预设话题失败，请联系管理员");
		}
	});
	return topicList;
}

function SelectedTopicTrend_Submit(oriData){  
	  var idListStr ='';    
	  $('input[name="SelectedTopic"]:checked').each(function(){    
		  idListStr+=($(this).val())+',';    
	  });
	  if(idListStr=='')
		  alert('你还没有选择任何内容！');
	  else{
		  SelectedTopicTrend(idListStr, oriData);
	  }
}  

function SelectedFocusTrend_Submit(oriData){
	var idListStr ='';    
	  $('input[name="SelectedFocus"]:checked').each(function(){    
		  idListStr+=($(this).val())+',';    
	  });
	  if(idListStr=='')
		  alert('你还没有选择任何内容！');
	  else{
		  SelectedFocusTrend(idListStr, oriData);
	  }
}

	var pageSize=10;
	var pageCount=15;
	var curPage=1;      //当前页号
	var focusList=[];
	var topicList=[];
function dataReady(oriData)
{
	if (focusList.length < 1) {
		//focusList = FatchExtractedFocusList(oriData);
	}
	if (topicList.length < 1) {
		topicList = FatchTopicList(oriData);
	}
}

function showPreset(oriData)
{
	PresetFocusAnalysis(oriData);
	changeCloudLabelColor($(".label1").parents("a"));
}

function showTopic(oriData){
	curPage=1;
	$("#topicPage #page_ul li a").filter(function(){
		return $(this).hasClass("active");
	}).toggleClass("active");
	$("#topicPage #page_ul li a").eq(curPage).addClass("active");
	SelectedTopicTrend("", oriData);
	ShowList("Topic", curPage, oriData);

}

function showFocus(oriData){
	curPage=1;
	$("#focusPage #page_ul li a").filter(function(){
		return $(this).hasClass("active");
	}).toggleClass("active");
	$("#focusPage #page_ul li a").eq(curPage).addClass("active");
	SelectedFocusTrend("", oriData);
	GenerateFocusTagCloud_All(oriData);
	ShowList("Focus", curPage, oriData);
}
		
function ShowList(objStr, pageIndex, oriData){
	var obj = $("#"+objStr.toLowerCase()+"PageList");
	if(pageIndex=='pri'){
		pageIndex=curPage-1;
		if(pageIndex<=0){
			alert('已经第一页了');
			return;
		}
	}
	else if(pageIndex=='next'){
		pageIndex=curPage+1;
		if(pageIndex>pageCount){
			alert('已经最后一页了');
			return;
		}
	}
	obj.html("");
	var index=(pageIndex-1)*pageSize;
	
	var li_ColumnTitle="<thead><tr>" +
						"<th>序号</th>" +
						"<th>话题名称</th>" +
						"<th>起始时间</th>" +
						"<th>结束时间</th>" +
						"<th>微博讨论数量</th>" +
						"</tr></thead>" +
						"<tbody>";
						
	obj.append(li_ColumnTitle);
	
	var dataList = focusList;
	if (objStr == "Topic")
	{
		dataList = topicList;
	}
	for(var i=0 ; i<pageSize; i++){
		var rowNum = index + i;
		var rowId = dataList[rowNum].id;
		var rowName = dataList[rowNum].name;
		var totalFreq = dataList[rowNum].totalFreq;
		var date_start = dataList[rowNum].date_start;
		var date_end = dataList[rowNum].date_end;
		var li_content = "<tr>" +
						"<td class=\"rowId\"><input type='checkbox' name='Selected" + objStr + "' value='" + rowId + "' /><span>" + (rowNum+1) + "</span></td>" +
						"<td><a href=''>" + rowName + "</a></td>" +
						"<td>" + date_start + "</td>" +
						"<td>" + date_end + "</td>" +
						"<td>" + totalFreq + "</td>" +
						"</tr>";
		
		obj.append(li_content);
	}
	obj.append("</tbody>");

	obj.next("#page_ul").children("li").children("a").eq(curPage).toggleClass("active");
	curPage = pageIndex;
	obj.next("#page_ul").children("li").children("a").eq(curPage).toggleClass("active");
	if (objStr == "Focus")
	{
		GenerateFocusTagCloud(focusList[index].id,focusList[index].name, oriData);
		$("#focusPageList a").click(focusPageLinks);
	}
	else
	{
		$("#topicPageList a").click(function(){ return false; });
	}
	$('input').iCheck({
		checkboxClass: 'icheckbox_square-blue',
	});

}
		