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
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/group_portrait.js"></script>
	<script type="text/javascript">

$(document).ready(function(){
	FatchGroupPortraitList();
	
	$("#searchSubmit").click(function(){
		var val = $("keywordStr").val();
		SearchGroupPortrait(val);
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
					<li><a id="home" class="blocklink" href="index.jsp">基本信息</a></li>
					<li><a id="behaviour" class="blocklink" href="behaviour_analysis.jsp">行为分析</a></li>
					<li><a id="content" class="blocklink" href="content_analysis.jsp">内容分析</a></li>
					<li><a id="psychology" class="blocklink" href="psychology_analysis.jsp">心理分析</a></li>
					<li><a id="positive_energy" class="blocklink" href="positive_energy_analysis.jsp">正能量分析</a></li>
					<li><a id="ranking_list" class="blocklink" href="ranking_list_analysis.jsp">排行榜</a></li>
					<li class="current"><a id="group_portrait" class="blocklink" href="group_portrait.jsp">群体画像</a></li>
				</ul>
			</nav>
		</header>
		<div id="tab_div">
			<ul id="_tab">
			</ul>
		</div>
		<div id="page_content">
			<div id="Search" class="rowline oddrows">
				<img src="<%=request.getContextPath()%>/img/search.png" alt="画像关键词搜索"/>
				<div id="searchRow">
					<input type="text" id="keywordStr" />
					<button id="searchSubmit" type="submit">搜索</button>
				</div>
			</div>
			<div id="searchCloud">
				<div class="loadingCover">
					<h4>加载中。。。</h4>
				</div>				
				<div class="rowline oddrows">
					<ul id="GroupPortraitList">
					</ul>
				</div>
				<div id="GroupPortraitTagCloud" class="cloud jqcloud topArrowFrom green2Style arrowBGWhite">
				</div>
			</div>
		</div>
		<footer>
		</footer>
	</div>
</body>
</html>	

	
	