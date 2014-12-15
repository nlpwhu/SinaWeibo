<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<jsp:include page="head.jsp" flush="true"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/basicinfo.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			var oriData = {"schoolProvince": "", "schoolCity": "", "schoolName": "", "gender": "", "date_start": "", "date_end": ""};
			UserGenderRate(oriData);
			UserWebAgeMap(oriData);
			
			UserVerifyTypeMap(oriData);
			UserProvinceMap(oriData);
			UserCountryMap(oriData);
			//UserWebAgeMap_New();
			
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
</head>

<body>
	<div id="wrap" class="container">
		<header>
			<nav class="main">
				<ul>
					<li class="current"><a id="home" class="blocklink" href="index.jsp">基本信息</a></li>
					<li><a id="behaviour" class="blocklink" href="behaviour_analysis.jsp">行为分析</a></li>
					<li><a id="content" class="blocklink" href="content_analysis.jsp">内容分析</a></li>
					<li><a id="psychology" class="blocklink" href="psychology_analysis.jsp">心理分析</a></li>
					<li><a id="positive_energy" class="blocklink" href="positive_energy_analysis.jsp">正能量分析</a></li>
					<li><a id="ranking_list" class="blocklink" href="ranking_list_analysis.jsp">排行榜</a></li>
					<li><a id="group_portrait" class="blocklink" href="group_portrait.jsp">群体画像</a></li>
				</ul>
			</nav>
		</header>
		<div id="tab_div" class="">
			<ul id="tab">
				<li><a id="UserGender" class="blocklink" href="#UserGenderRate" >男女比例</a></li>
				<li><a id="UserWebAge" class="blocklink" href="#UserWebAgeMap" >网龄分布</a></li>
				<li><a id="UserVerifyType" class="blocklink" href="#UserVerifyTypeMap" >用户等级</a></li>
				<li><a id="UserRegion" class="blocklink" href="#UserRegionMap" >地域分布</a></li>
			</ul>
		</div>
		<div id="page_content">
			<div id="UserGenderRate" class="rowline oddrows">
				<h2><span>男女比例统计图</span></h2>
				<div class="chart"></div>
				<p></p>
			</div>
			<div id="UserWebAgeMap" class="rowline">
				<h2><span>网龄分布统计图</span></h2>
				<div class="chart"></div>
			</div>
			<div id="UserVerifyTypeMap" class="rowline oddrows">
				<h2><span>用户等级统计图</span></h2>
				<div class="chart"></div>
			</div>
			<div id="UserRegionMap" class="rowline">
				<h2><span>用户地域分布</span></h2>
				<div id="UserProvinceMap" class="rowline">
					<div class="chart"></div>
				</div>
				<div id="UserCountryMap" class="rowline">
					<div class="chart"></div>
				</div>
			</div>
		</div>
		<footer>
		</footer>
	</div>
</body>
</html>

	
	