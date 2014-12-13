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

			var pageSize=10;
			var pageCount=15;
			var curPage=1;      //当前页号
			var focusList=[];
			var topicList=[];
			mytab();
			focusList = FatchExtractedFocusList();
			topicList = FatchTopicList();
			
			$("#tab li a").click(function(){
				var id = $(this).attr("id").substr(4);
				$(".tabpage>div[class!='hiddentab']").toggleClass("hiddentab");
				$("#"+id).toggleClass("hiddentab");
				$("#tab li a.current").toggleClass("current");
				$(this).toggleClass("current");
				mytab(id);
				return false;
			});
			
			$(".cloudLabelUl li a").click(function(){
				var idx = $(this).parent("li").index();
				GenerateEventTagCloud(idx);
				if ($(this).children("span").hasClass("labelActive"))
				{
					return false;
				}
				changeCloudLabelColor($(this));
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
			
			$("#focusTrendGenerateBtn").click(SelectedFocusTrend_Submit);
			
			$("#topicTrendGenerateBtn").click(SelectedTopicTrend_Submit);
			
			function changeCloudLabelColor(obj)
			{
				var changed = $("#EventTagCloud");
				var colorToDo = obj.children("span").css("border-color");
				var left = (obj.offset().left - changed.offset().left) * 100 / changed.width();
				changed.css("border-color", colorToDo);
				$('style').append(".topArrowFrom::before{  left: "+left.toFixed(1)+"%; border-bottom-color: "+colorToDo+"}.topArrowFrom::after{  left: "+(Number(left.toFixed(1))+1)+"%;}.labelActiveStyle{	color: "+colorToDo+";	border-color: "+colorToDo+"; }");
				
				obj.parents("ul").find("span").filter(function(){
					return ($(this).hasClass("labelActive") && $(this).hasClass("labelActiveStyle"));
				}).toggleClass("labelActive").toggleClass("labelActiveStyle");
				obj.children("span").toggleClass("labelActive").toggleClass("labelActiveStyle");
			}

			function mytab(tabID){
				switch(tabID)
					{
					case 'BasicInfo':
					default:
						BasicInfo();
						break;
					case 'PresetFocus':
						PresetFocusAnalysis();
						changeCloudLabelColor($(".label1").parents("a"));
						break;
					case 'ExtractFocus':
						curPage=1;
						$("#focusPage #page_ul li a").filter(function(){
							return $(this).hasClass("active");
						}).toggleClass("active");
						$("#focusPage #page_ul li a").eq(curPage).addClass("active");
						SelectedFocusTrend("");
						GenerateFocusTagCloud_All();
						ShowList("Focus", curPage);
						break;
					case 'Topics':
						curPage=1;
						$("#topicPage #page_ul li a").filter(function(){
							return $(this).hasClass("active");
						}).toggleClass("active");
						$("#topicPage #page_ul li a").eq(curPage).addClass("active");
						SelectedTopicTrend("");
						ShowList("Topic", curPage);
						break;
					}
			}


			function ShowList(objStr, pageIndex){
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
					GenerateFocusTagCloud(focusList[index].id,focusList[index].name);
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
				<li><a id="tab_PresetFocus" class="blocklink" href="" >预设话题</a></li>
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
					<img src="<%=request.getContextPath()%>/images/contentBasicIcon.png" />
				</div>
			</div>
			<div id="PresetFocus" class="hiddentab">
				<div id="PresetFocusAnalysis" class="rowline oddrows">
					<!--原PresetFocusStatistics-->
					<h2><span>预设话题基本统计图</span></h2>
					<div class="chart"></div>
					<img src="<%=request.getContextPath()%>/images/contentPreset.png" />
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
	