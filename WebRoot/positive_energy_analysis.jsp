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
	<jsp:include page="head.jsp" flush="true"/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/arrow.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jqcloud-1.0.4.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/noumenon.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/pe.js"></script>
	<script type="text/javascript">
$(document).ready(function(){
	var oriData = "{\"schoolProvince\": \"\", \"schoolCity\": \"\", \"schoolName\": \"\", \"gender\": \"\", \"date_start\": \"\", \"date_end\": \"\"}";
	initPositiveEnergy(oriData);
	
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
});
</script>
<title>新浪微博-大学生生活报告</title>
<style></style>
</head>


<body>
	<div id="wrap" class="container">
		<header>
			<nav class="main">
				<ul>
					<li><a id="home" class="blocklink" href="index.jsp">基本信息</a></li>
					<li><a id="behaviour" class="blocklink" href="behaviour_analysis.jsp">行为分析</a></li>
					<li><a id="content" class="blocklink" href="content_analysis.jsp">内容分析</a></li>
					<li><a id="psychology" class="blocklink" href="psychology_analysis.jsp">心理分析</a></li>
					<li class="current"><a id="positive_energy" class="blocklink" href="positive_energy_analysis.jsp">正能量分析</a></li>
					<li><a id="ranking_list" class="blocklink" href="ranking_list_analysis.jsp">排行榜</a></li>
					<li><a id="group_portrait" class="blocklink" href="group_portrait.jsp">群体画像</a></li>
				</ul>
			</nav>
		</header>
		<div id="tab_div">
			<ul id="_tab">
				<li><a id="PEEventS" class="blocklink" href='#PEEventStatistics' >正能量基本统计图</a></li>
				<li><a id="PEEventT" class="blocklink" href='#PEEventTren' >正能量基本走势图</a></li>
				<li><a id="EventTagC" class="blocklink" href='#EventTagCloud' >正能量统计云图</a></li>
			</ul>
		</div>
		<div id="page_content">
			<div id="PEEventStatistics" class="rowline oddrows">
				<h2><span>心理基本统计图</span></h2>
				<div class="chart"></div>
				<p></p>
			</div>
			<div id="PEEventTren" class="rowline ">
				<h2><span>心理基本走势图</span></h2>
				<div class="chart"></div>
			</div>
			<div id="PEEventTagCloud" class="rowline oddrows relativeEle">
				<h2><span>关键词云图</span></h2>
				<h3><span>关键词云图</span></h3>
				<div class="loadingCover">
					<h4>加载中。。。</h4>
				</div>
				<div class="cloudLabelRow" >
					<span class="pre"></span>
					<span class="next"></span>
					<div>
						<ul class="cloudLabelUl rowul">
							<li>
								<a href=""><span class="label labelExtra label2">成功励志</span></a>
							</li>
							<li>
								<a href=""><span class="label labelExtra label5">关爱弱势群体</span></a>
							</li>
							<li>
								<a href=""><span class="label labelExtra label4">环境保护</span></a>
							</li>
							<li>
								<a href=""><span class="label labelExtra label1">灾难救助</span></a>
							</li>
							<li>
								<a href=""><span class="label labelExtra label2">见义勇为</span></a>
							</li>
							<li>
								<a href=""><span class="label labelExtra label3">爱岗敬业</span></a>
							</li>
							<li>
								<a href=""><span class="label labelExtra label4">反腐倡廉</span></a>
							</li>
							<li>
								<a href=""><span class="label labelExtra label5">爱国主义</span></a>
							</li>
							<li>
								<a href=""><span class="label labelExtra label1">尊老爱幼</span></a>
							</li>
							<li>
								<a href=""><span class="label labelExtra label2">助人为乐</span></a>
							</li>
						</ul>
					</div>
				</div>
				<div id="EventTagCloud" class="cloud jqcloud topArrowFrom"></div>
			</div>
			
			</div>
		<footer>
		</footer>
	</div>
</body>
</html>


	
	