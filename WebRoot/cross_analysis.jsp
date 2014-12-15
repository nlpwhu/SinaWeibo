<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jqcloud2.0.css" />
	<jsp:include page="head.jsp" flush="true"/>
	<link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath()%>/css/daterangepicker-bs3.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/arrow.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jqcloud2.0.css" />
	<!--<link rel="stylesheet" href="<%=request.getContextPath()%>/css/icheck/square/blue.css" />-->
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/icheck.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/behaviour.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jqcloud-1.0.4.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/moment.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/daterangepicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/schoolsCity.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/analyse.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/icheck.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/noumenon.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/basicinfo.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/psychology.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/pe.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/content.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		$("#page_ul li a").click(function(){
			var obj = $("#focusPageList").is(":visible")? "Focus":"Topic";
			var idx = $(this).parent("li").index();
			dateStart = $("#datetimepickerStart input").val();
			dateEnd = $("#datetimepickerEnd input").val();
			var oriData = "{\"schoolProvince\": "+province_val+", \"schoolCity\": "+city_val+", \"schoolName\": "+school_val+", \"gender\": "+gender_val+", \"date_start\": "+dateStart+", \"date_end\": "+dateEnd+"}";;
			if (idx == 0) {
				ShowList(obj, "pri", oriData);
			}
			else if (idx == 16) {
				ShowList(obj, "next", oriData);
			}
			else {
				ShowList(obj, idx, oriData);
			}
			return false;
		});
		
		$("#focusTrendGenerateBtn").click(function(){
			SelectedFocusTrend_Submit(oriData);
		});
		
		$("#topicTrendGenerateBtn").click(function(){
			SelectedTopicTrend_Submit(oriData);
		});
		
	});
	</script>
	<style></style>
<title>新浪微博-大学生生活报告</title>
</head>
<body>
	<div id="wrap" class="container">
		<header>
		</header>
		<div id="_content">
			<div id="nav_cats_wrap">
				<ul id="nav_cats">
					<li id="province" class="fir_nav">
						<h3>省份</h3>
					</li>
					<li id="city" class="fir_nav">
						<h3>城市</h3>
					</li>
					<li id="school" class="fir_nav">
						<h3>大学</h3>
					</li>
					<li id="gender" class="fir_nav">
						<h3>性别</h3>
					</li>
					<li id="period" class="fir_nav">
						<h3>日期范围</h3>
					</li>
					<li id="analysis" class="fir_nav">
						<h3>分析</h3>
					</li>
					<li id="section" class="fir_nav">
						<h3>模块</h3>
					</li>
				</ul>
				<div id="nav_subcats">
					<div id="province_sub" class="sec_nav">
						<ul class="">
							<li><a href="">全国</a></li>
							<li><a href="beijing">北京</a></li>
							<li><a href="shanghai">上海</a></li>
							<li><a href="guangdong">广东</a></li>
							<li class="current"><a href="hubei">湖北</a></li>
							<li><a href="tianjing">天津</a></li>
							<li><a href="helongjiang">黑龙江</a></li>
							<li><a href="anhui">安徽</a></li>
							<li><a href="jiangsu">江苏</a></li>
							<li><a href="hunan">湖南</a></li>
							<li><a href="shanxi">陕西</a></li>
							<li><a href="sichuan">四川</a></li>
							<li><a href="zhejiang">浙江</a></li>
							<li><a href="jilin">吉林</a></li>
							<li><a href="liaoning">辽宁</a></li>
							<li><a href="henan">河南</a></li>
							<li><a href="chongqing">重庆</a></li>
				     		<li><a href="yunnan">云南</a></li>
							<li><a href="hebei">河北</a></li>
							<li><a href="jiangxi">江西</a></li>
							<li><a href="_shanxi">山西</a></li>
							<li><a href="guizhou">贵州</a></li>
							<li><a href="guangxi">广西</a></li>
							<li><a href="fujian">福建</a></li>
							<li><a href="shandong">山东</a></li>
							<li><a href="gansu">甘肃</a></li>
							<li><a href="neimenggu">内蒙古</a></li>
							<li><a href="ningxia">宁夏</a></li>
							<li><a href="qinghai">青海</a></li>
							<li><a href="xinjiang">新疆</a></li>
							<li><a href="hainan">海南</a></li>
							<li><a href="xizang">西藏</a></li>
							<li><a href="aomen">澳门</a></li>
							<li><a href="xianggang">香港</a></li>
							<li><a href="taiwan">台湾</a></li>
						</ul>
					</div>
					<div id="city_sub" class="sec_nav">
						<h4></h4>
						<ul class="sec_nav_ul">
							<li>请先选择省份</li>	
						</ul>
					</div>
					<div id="school_sub" class="sec_nav">
						<h4></h4>
						<ul class="sec_nav_ul">
							<li>请先选择城市</li>	
						</ul>
					</div>
					<div id="gender_sub" class="sec_nav">
						<ul class="sec_nav_ul">
							<li><a class="gender-item" gender_id="m">男</a></li>
							<li><a class="gender-item" gender_id="f">女</a></li>
						</ul>
					</div>
					<div id="period_sub" class="sec_nav">
						<form class="form-horizontal">
						 <fieldset>
						  <div class="control-group">
							<div class="controls">
							 <div class="input-prepend input-group">
							   <span class="add-on input-group-addon"><i class="glyphicon glyphicon-calendar fa fa-calendar"></i></span><input type="text" style="width: 200px" name="reservation" id="reservation" class="form-control" value="03/18/2013 - 03/23/2013" /> 
							 </div>
							</div>
						  </div>
						 </fieldset>
					   </form>
					</div>
					<div id="analysis_sub" class="sec_nav">
						<ul class="sec_nav_ul">
							<li><a class="current" href="">基本信息</a></li>
							<li><a href="">行为分析</a></li>
							<li><a href="">内容分析</a></li>
							<li><a href="">心理分析</a></li>
							<li><a href="">正能量分析</a></li>
							<li><a href="">排行榜</a></li>
							<li><a href="">群体画像</a></li>	
						</ul>
					</div>
					<div id="section_sub" class="sec_nav">
						<h4>基本信息</h4>
						<ul class="sec_nav_ul">
							<li><a href=""></a></li>
						</ul>
					</div>
				</div>
			</div>
			<div id="_main">
				<ul id="crumbs">
					<!--<li><a id="c_province">湖北</a></li>
					<li><a id="c_city">武汉</a></li>
					<li><a id="c_school">武汉大学</a></li>
					<li><a id="c_gender">男</a></li>
					<li><a id="c_date">2014-12-12到2014-12-29</a></li>
					<li><a id="c_analysis">心理</a></li>
					<li><a id="c_section">心理基本统计图</a></li>-->
				</ul>
				<div id="result">
					<div id="UserGenderRate" class="rowline hiddenChart">
						<div class="chart"></div>
						<p></p>
					</div>
					<div id="UserWebAgeMap" class="rowline hiddenChart">
						<div class="chart"></div>
					</div>
					<div id="UserVerifyTypeMap" class="rowline hiddenChart">
						<div class="chart"></div>
					</div>
					<div id="UserRegionMap" class="rowline hiddenChart">
						<div id="UserProvinceMap" class="rowline">
							<div class="chart"></div>
						</div>
						<div id="UserCountryMap" class="rowline">
							<div class="chart"></div>
						</div>
					</div>
					

					<div id="WeiboReleaseForm" class="rowline hiddenChart">
						<div class="chart"></div>
						<p></p>
					</div>
					<div id="WeiboSourceMap" class="rowline hiddenChart">
						<div id="WeiboPhoneBrandMap">
						<!--<h4><span>手机品牌</span></h4>-->
							<div class="chart"></div>
						</div>
						<div id="WeiboSourceNameMap">
							<div class="chart"></div>
						</div>
					</div>

		  			<div id="WeiboTimePeriodMap" class="rowline hiddenChart">
						<div class="chart"></div>
					</div>
					<div id="UserRelationshipMap" class="rowline hiddenChart">
						<div class="chart"></div>
					</div>
					


					<div id="BasicInfo" class="">
						<div id="ForwardOriginalMap" class="rowline hiddenChart">
							<!--原BasicInfo_RO_Rate-->
							<div class="chart"></div>
						</div>
						<div id="BasicQuantityMap" class="rowline hiddenChart">
							<!--原BasicInfo_Statistics-->
							<div class="chart"></div>
							<img src="<%=request.getContextPath()%>/img/contentBasicIcon.png" />
						</div>
					</div>
					<div id="PresetFocus" class="">
						<div id="PresetFocusAnalysis" class="rowline hiddenChart">
							<!--原PresetFocusStatistics-->
							<div class="chart"></div>
							<img src="<%=request.getContextPath()%>/img/contentPreset.png" />
						</div>
						<div id="PresetFocusTrend" class="rowline hiddenChart">
							<div class="chart"></div>
						</div>
						<div id="KeyWordsMap" class="rowline relativeEle hiddenChart">
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
					<div id="ExtractFocus" class="">
						<div id="TagCloud_All" class="rowline hiddenChart">
							<div id="FocusTagCloud_All" class="cloud jqcloud"></div>
						</div>				
						<div id="ExtractFocusTrend" class="rowline hiddenChart">
							<div class="chart"></div>
						</div>
						<div>
						</div>
						<div id="focusPage" class="rowline hiddenChart">
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
						<div id="selectedCloud" class="rowline relativeEle hiddenChart">
							<div class="loadingCover">
								<h4>加载中。。。</h4>
							</div>					
							<h3 id="Span_FocusTagCloud" class=""></h3>
							<div id="FocusTagCloud" class="cloud jqcloud topArrowFrom greenStyle arrowBGWhite"></div>
						</div>
					</div>
					<div id="Topics" class="">
						<div id="ExtractTopicTrend" class="rowline hiddenChart">
							<div class="chart"></div>
						</div>
							<button id="topicTrendGenerateBtn" type="button" class="btn generateTrendBtn hiddenChart">生成微话题走势图</button>
						<div id="topicPage" class="rowline hiddenChart">
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


					<div id="PsychologyEventStatistics" class="rowline hiddenChart">
						<div class="chart"></div>
						<p></p>
					</div>
					<div id="PsychologyEventTrend" class="rowline hiddenChart">
						<div class="chart"></div>
					</div>
					<div id="PsyEventTagCloud" class="rowline relativeEle hiddenChart">
						<h3><span>关键词云图</span></h3>
						<div class="loadingCover">
							<h4>加载中。。。</h4>
						</div>				
						<ul class="cloudLabelUl rowul">
							<li>
								<a href=""><span class="label labelExtra label2">积极</span></a>
							</li>
							<li>
								<a href=""><span class="label labelExtra label4">中性</span></a>
							</li>
							<li>
								<a href=""><span class="label labelExtra label5">消极</span></a>
							</li>
						</ul>
						<div id="EventTagCloud" class="cloud jqcloud topArrowFrom"></div>
					</div>



					<div id="PEEventStatistics" class="rowline hiddenChart">
						<div class="chart"></div>
						<p></p>
					</div>
					<div id="PEEventTren" class="rowline hiddenChart">
						<div class="chart"></div>
					</div>
					<div id="PEEventTagCloud" class="rowline relativeEle hiddenChart">
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


					<!--<div id="Search" class="rowline">
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
						<div class="rowline">
							<ul id="GroupPortraitList">
							</ul>
						</div>
						<div id="GroupPortraitTagCloud" class="cloud jqcloud topArrowFrom green2Style arrowBGWhite">
						</div>
					</div>-->















				</div>	
			</div>
		</div>
	</div>

</body>
</html>