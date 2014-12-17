<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jqcloud2.0.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/icheck/square/blue.css" />
	<jsp:include page="head.jsp" flush="true"/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/arrow.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/icheck.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jqcloud-1.0.4.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/noumenon.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/content.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			var oriData = "{\"schoolProvince\": \"\", \"schoolCity\": \"\", \"schoolName\": \"\", \"gender\": \"\", \"date_start\": \"\", \"date_end\": \"\"}";
			var offy = $("#tab_div").offset().top;
			//var oriWidth = $("#tab_div").width();
			$(document).scroll(function(){
				var stickyBar = $("#tab_div");
				var scrollTop = $("body").scrollTop();
				if (scrollTop >= offy)
				{
					stickyBar.addClass("sticky");
					stickyBar.css("left", ($(document).width()-stickyBar.width())/2);
					//stickyBar.css("width", oriWidth);
				}
				else if (scrollTop < offy)
				{
					stickyBar.removeClass("sticky");
				}
			});
			//var tabID=['BasicInfo','PresetFocus','ExtractFocus','Topics'];


			mytab();
			dataReady(oriData);
			
			$("#tab li a").click(function(){
				var id = $(this).attr("id").substr(4);
				$(".tabpage>div[class!='hiddentab']").toggleClass("hiddentab");
				$("#"+id).toggleClass("hiddentab");
				$("#tab li a.current").toggleClass("current");
				$(this).toggleClass("current");
				mytab(id);
				return false;
			});
									
			$("#page_ul li a").click(function(){
				var obj = $("#focusPageList").is(":visible")? "Focus":"Topic";
				var idx = $(this).parent("li").index();
				if (idx == 0) {
					ShowList(obj, "pri");
				}
				else if (idx == 16) {
					ShowList(obj, "next");
				}
				else {
					ShowList(obj, idx);
				}
				return false;
			});
			
			$("#focusTrendGenerateBtn").click(function(){
				SelectedFocusTrend_Submit(oriData);
			});
			
			$("#topicTrendGenerateBtn").click(function(){
				SelectedTopicTrend_Submit(oriData);
			});
			$(".cloudLabelUl li a").click(function(e){
				clickCloudLabel($(this), oriData);
				e.preventDefault();
			});
			
			function mytab(tabID){
				switch(tabID)
					{
					case 'BasicInfo':
						BasicInfo(oriData);
					case 'PresetFocus':
						showPreset(oriData);
						break;
					case 'ExtractFocus':
						showFocus(oriData);
						break;
					case 'Topics':
						showTopic(oriData);
						break;
					default:
						BasicInfo(oriData);
						break;
					}
			}

		});	
		



/**
 * 替换字符串中所有
 * @param obj	原字符串
 * @param str1	替换规则
 * @param str2	替换成什么
 * @return	替换后的字符串
 *//*
function replaceAll(obj,str1,str2){       
	  var result  = obj.replace(eval("/"+str1+"/gi"),str2);      
	  return result;
}*/
	</script>
	<style></style>
	<title>新浪微博-大学生生活报告</title>
</head>

<body>
	<div id="wrap" class="container">
		<header>
			<nav class="main">
				<ul>
					<li><a id="home" class="blocklink" href="index.jsp">基本信息</a></li>
					<li><a id="behaviour" class="blocklink" href="behaviour_analysis.jsp">行为分析</a></li>
					<li class="current"><a id="content" class="blocklink" href="content_analysis.jsp">内容分析</a></li>
					<li><a id="psychology" class="blocklink" href="psychology_analysis.jsp">心理分析</a></li>
					<li><a id="positive_energy" class="blocklink" href="positive_energy_analysis.jsp">正能量分析</a></li>
					<li><a id="ranking_list" class="blocklink" href="ranking_list_analysis.jsp">排行榜</a></li>
					<li><a id="group_portrait" class="blocklink" href="group_portrait.jsp">群体画像</a></li>
				</ul>
			</nav>
		</header>
				
		<div id="tab_div">
			<ul id="tab">
				<li><a id="tab_BasicInfo" class="blocklink current" href="">基本信息</a></li>
				<li><a id="tab_PresetFocus" class="blocklink " href="" >预设话题</a></li>
				<li><a id="tab_ExtractFocus" class="blocklink" href="" >抽取话题</a></li>
				<li><a id="tab_Topics" class="blocklink" href="" >微话题</a></li>
			</ul>
		</div>
		<div id="page_content" class="tabpage">
			<div id="BasicInfo" class="">
				<div id="ForwardOriginalMap" class="rowline oddrows">
					<!--原BasicInfo_RO_Rate-->
					<h2><span>转发原创统计图</span></h2>
					<div class="chart"></div>
				</div>
				<div id="BasicQuantityMap" class="rowline">
					<!--原BasicInfo_Statistics-->
					<h2><span>基本数量统计图</span></h2>
					<div class="chart"></div>
					<img src="<%=request.getContextPath()%>/img/contentBasicIcon.png" />
				</div>
			</div>
			<div id="PresetFocus" class="hiddentab">
				<div id="PresetFocusAnalysis" class="rowline oddrows">
					<!--原PresetFocusStatistics-->
					<h2><span>预设话题基本统计图</span></h2>
					<div class="chart"></div>
					<img src="<%=request.getContextPath()%>/img/contentPreset.png" />
				</div>
				<div id="PresetFocusTrend" class="rowline">
					<h2><span>预设话题走势图</span></h2>
					<div class="chart"></div>
				</div>
				<div id="KeyWordsMap" class="rowline oddrows relativeEle">
					<h2><span>关键词云图</span></h2>
					<h3><span>关键词云图</span></h3>
					<div class="loadingCover">
						<h4>加载中。。。</h4>
					</div>					
					<ul class="cloudLabelUl rowul">
						<li>
							<a href=""><span class="label labelExtra label1">学业</span></a>
						</li>
						<li>
							<a href=""><span class="label labelExtra label2">文体娱</span></a>
						</li>
						<li>
							<a href=""><span class="label labelExtra label3">社交</span></a>
						</li>
						<li>
							<a href=""><span class="label labelExtra label4">情感</span></a>
						</li>
						<li>
							<a href=""><span class="label labelExtra label5">就业</span></a>
						</li>
					</ul>
					<div id="EventTagCloud" class="cloud jqcloud topArrowFrom"></div>
				</div>
			</div>
			<div id="ExtractFocus" class="hiddentab">
				<div id="TagCloud_All" class="rowline oddrows">
					<h2><span>所有话题关键词云图</span></h2>
					<div id="FocusTagCloud_All" class="cloud jqcloud"></div>
				</div>				
				<div id="ExtractFocusTrend" class="rowline">
					<h2><span>热门话题走势图</span></h2>
					<div class="chart"></div>
				</div>
				<div>
				</div>
				<div id="focusPage" class="rowline oddrows">
					<button id="focusTrendGenerateBtn" type="button" class="btn generateTrendBtn">生成热门话题走势图</button>
					<table id="focusPageList" class="topicList">
			        
   					</table>
					<ul id="page_ul" class="pagination">
						<li><a class="pre" href=""></a></li>
						<li><a href="">1</a></li>
						<li><a href="">2</a></li>
						<li><a href="">3</a></li>
						<li><a href="">4</a></li>
						<li><a href="">5</a></li>
						<li><a href="">6</a></li>
						<li><a href="">7</a></li>
						<li><a href="">8</a></li>
						<li><a href="">9</a></li>
						<li><a href="">10</a></li>
						<li><a href="">11</a></li>
						<li><a href="">12</a></li>
						<li><a href="">13</a></li>
						<li><a href="">14</a></li>
						<li><a href="">15</a></li>
						<li><a class="next" href=""></a></li>
					</ul>
				</div>
				<div id="selectedCloud" class="rowline relativeEle">
					<div class="loadingCover">
						<h4>加载中。。。</h4>
					</div>					
					<h3 id="Span_FocusTagCloud" class=""></h3>
					<div id="FocusTagCloud" class="cloud jqcloud topArrowFrom greenStyle arrowBGWhite"></div>
				</div>
			</div>
			<div id="Topics" class="hiddentab">
				<div id="ExtractTopicTrend" class="rowline oddrows">
					<h2><span>热门微话题走势图</span></h2>
					<div class="chart"></div>
				</div>
					<button id="topicTrendGenerateBtn" type="button" class="btn generateTrendBtn">生成微话题走势图</button>
				<div id="topicPage" class="rowline">
					<table id="topicPageList" class="topicList">
			        
   					</table>
					<ul id="page_ul" class="pagination">
						<li><a class="pre" href=""></a></li>
						<li><a href="">1</a></li>
						<li><a href="">2</a></li>
						<li><a href="">3</a></li>
						<li><a href="">4</a></li>
						<li><a href="">5</a></li>
						<li><a href="">6</a></li>
						<li><a href="">7</a></li>
						<li><a href="">8</a></li>
						<li><a href="">9</a></li>
						<li><a href="">10</a></li>
						<li><a href="">11</a></li>
						<li><a href="">12</a></li>
						<li><a href="">13</a></li>
						<li><a href="">14</a></li>
						<li><a href="">15</a></li>
						<li><a class="next" href=""></a></li>
					</ul>
				</div>
			</div>
		</div>
		<footer>
		</footer>
	</div>
</body>
</html>
	