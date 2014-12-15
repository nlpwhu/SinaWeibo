/**
 * 这个js主要用于交叉分析使用，
 */
 
var SCHOOLPAGELISTLEN = 12;
var PAGELISTLEN = 20;
var schoolPage = 1;
var schoolPageCur = 0;
var cityPage = 1;
var cityPageCur = 0;
var provinceID = 0;
var cityID = 0;

var province_val = "";
var city_val = "";
var school_val = "";
var gender_val = "";
var dateStart = "";
var dateEnd = "";
var analysisID = 1;
var sectionID = 1;
var analysis_val = "";
var section_val = "";
/**
数据格式
var data={"schoolProvince":"","schoolCity":"","schoolName":"","gender":"","date_start":"2014-12-08","date_end":"2014-12-28"}

**/

$(document).ready(function(){
	initProvince();
    //默认情况下, 给第一个省份添加choosen样式
    $('#province_sub li a').eq(0).addClass('current');
    //初始化大学列表
    initSchool();
	
	// var currentTab = false;
	$(".fir_nav").mouseenter(function(){
		var item = $(this);
		var showid = item.attr("id") + "_sub";
		var choosenItem = item.parent().find('.current');
        if(choosenItem)
            $(choosenItem).removeClass('current');
		item.children("h3").addClass("current");
		if ($("#nav_cats .current").offset().top + $("#nav_subcats").height() <= $("body").height()) {
			$("#nav_subcats").css("top", $("#nav_cats .current").position().top);
		} else {
			$("#nav_subcats").css("top", $("#nav_cats .current").position().top + $("#nav_cats .current").parent().height() - $("#nav_subcats").height());
		}
		$(".sec_nav").hide();
		$("#"+showid).show();
	});
 	$(".sec_nav").mouseleave(function(e){
		if($(e.relatedTarget).hasClass("daterangepicker"))
			return;
		var choosenItem = $(".fir_nav").parent().find('.current');
        if(choosenItem)
            $(choosenItem).removeClass('current');
		$(this).hide();
		
	});
	
	// $(".bootstrap-datetimepicker-widget").mouseenter(function(){
		
	// })
/*	$(".sec_nav").mouseenter(function(){
		currentTab = true;
	});	
	$(".sec_nav").mouseleave(function(){
		currentTab = false;
	});	
 */
 	
	$('#reservation').daterangepicker(null, function(start, end, label) {
		dateStart = start.format('YYYY-MM-DD');
		if (!dateStart) dateStart = "";
		dateEnd = end.format('YYYY-MM-DD');
		if (!dateEnd) dateEnd = "";
    });
	
	initAnalysis();
    //默认情况下, 给第一个分析添加choosen样式
    $('#analysis_sub li a').eq(0).addClass('current');
    //初始化模块列表
    initSection();
    //默认情况下, 给第一个section添加choosen样式
    $('#section_sub li a').eq(0).addClass('current');
	$('#UserGenderRate').addClass('showChart');
	updateCrumbs();
	getDataFromServer();
	// var currentTab = false;

});

function initProvince()
{
    //原先的省份列表清空
    $('#province_sub ul').html('<li><a href="">全国</a></li>');
	provinceID = 0;
    for(i=0;i<schoolList.length;i++)
    {
        $('#province_sub ul').append('<li><a class="province-item" province_id="'+schoolList[i].id+'">'+schoolList[i].name+'</a></li>');
    }
    //添加省份列表项的click事件
    $('.province-item').bind('click', function(){
            var item=$(this);
            var province = item.attr('province_id');
			provinceID = province;
			province_val = item.html();
            var choosenItem = item.parent().parent().find('.current');
            if(choosenItem)
                $(choosenItem).removeClass('current');
            item.addClass('current');
            //更新大学列表
            initCity();
			updateCrumbs();
			getDataFromServer();
        }
    );
}

function initCity()
{
	$("#city_sub h4").html(province_val);
    //原先的城市列表清空
    $('#city_sub ul').html('');
	var next = $('#city_sub ul').next();
	if (next)
	{
		next.remove();
	}
	cityID = 0;
	cityPage = 1;
	cityPageCur = 0;
	if (provinceID == 0)
	{	
		$('#city_sub ul').append("<li>请先选择省份</li>");
		return;
	}
	var cityData = schoolList[provinceID-1].city;
	cityPage = Math.ceil(cityData.length / PAGELISTLEN);
	if (cityPage > 1)
	{
		$('#city_sub ul').after("<p><a class=\"pre\">上一页</a><a class=\"next\">下一页</a></p>");
		$('#city_sub .pre').bind('click', function(){
			cityPageCur = updatePage(cityPageCur, cityPage, cityData, "city", -1);
		});
		$('#city_sub .next').bind('click', function(){
			cityPageCur = updatePage(cityPageCur, cityPage, cityData, "city", 1);
		});
	}
	var len = Math.min((cityPageCur + 1) * PAGELISTLEN, cityData.length);
	var i = cityPageCur * PAGELISTLEN;
    for(;i<len;i++)
    {
        $('#city_sub ul').append('<li><a class="city-item" city_id="'+cityData[i].id+'">'+cityData[i].name+'</a></li>');
    }
    //添加省份列表项的click事件
    $('.city-item').bind('click', function(){
            var item=$(this);
            var city = item.attr('city_id');
			cityID = Number(city.slice(-2));
			city_val = item.html();
            var choosenItem = item.parent().parent().find('.current');
            if(choosenItem)
                $(choosenItem).removeClass('current');
            item.addClass('current');
            //更新大学列表
			initSchool();
			//更新面包屑
			updateCrumbs();
			
			getDataFromServer();
        }
    );	
}

function initSchool()
{
	$("#school_sub h4").html(city_val);
    //原先的学校列表清空
    $('#school_sub ul').html('');
	var next = $('#school_sub ul').next();
	if (next)
	{
		next.remove();
	}
	schoolPage = 1;
	schoolPageCur = 0;
	if (provinceID == 0)
	{	
		$('#school_sub ul').append("<li>请先选择省份</li>");
		return;
	}
	if (cityID == 0)
	{	
		$('#school_sub ul').append("<li>请先选择城市</li>");
		return;
	}
	var schools = schoolList[provinceID-1].city[cityID-1].school;
	schoolPage = Math.ceil(schools.length / PAGELISTLEN);
	if (schoolPage > 1)
	{
		$('#school_sub ul').after("<p><a class=\"pre\">上一页</a><a class=\"next\">下一页</a></p>");
		$('#school_sub .pre').bind('click', function(){
			schoolPageCur = updatePage(schoolPageCur, schoolPage, schools, "school", -1);
		});
		$('#school_sub .next').bind('click', function(){
			schoolPageCur = updatePage(schoolPageCur, schoolPage, schools, "school", 1);
		});
	}
	var len = Math.min((schoolPageCur + 1) * PAGELISTLEN, schools.length);
	var i = schoolPageCur * PAGELISTLEN;
    for(;i<len;i++)
    {
        $('#school_sub ul').append('<li><a class="school-item" school_id="'+schools[i].id+'">'+schools[i].name+'</a></li>');
    }
    //添加大学列表项的click事件
    $('.school-item').bind('click', function(){
            var item=$(this);
            var school = item.attr('school_id');
			school_val = item.html();
            var choosenItem = item.parent().parent().find('.current');
            if(choosenItem)
                $(choosenItem).removeClass('current');
            item.addClass('current');
			updateCrumbs();
			getDataFromServer();
        }
    );
}

/***该方法已过期****/
function updateSchool(dir)
{
	schoolPageCur += dir;
	if (schoolPageCur < 0)
		schoolPageCur = 0;
	else if (schoolPageCur >= schoolPage)
		schoolPageCur = schoolPage - 1;
	$('#school_sub ul').html('');
	var schools = schoolList[provinceID-1].school;
	var len = Math.min((schoolPageCur + 1) * PAGELISTLEN, schools.length);
	var i = schoolPageCur * PAGELISTLEN;
    for(;i<len;i++)
    {
        $('#school_sub ul').append('<li><a class="school-item" school_id="'+schools[i].id+'">'+schools[i].name+'</a></li>');
    }
	
}

function updatePage(cur, maxpage, data, ele, dir)
{
	cur += dir;
	if (cur < 0)
		cur = 0;
	else if (cur >= maxpage)
		cur = maxpage - 1;
	$('#'+ele+'_sub ul').html('');
	//var schools = schoolList[provinceID-1].school;
	var pageListLen = PAGELISTLEN;
	if (ele == "school") {
		pageListLen = SCHOOLPAGELISTLEN;
	}
	var len = Math.min((cur + 1) * pageListLen, data.length);
	var i = cur * pageListLen;
    for(;i<len;i++)
    {
        $('#'+ele+'_sub ul').append('<li><a class="'+ele+'-item" '+ele+'_id="'+data[i].id+'">'+data[i].name+'</a></li>');
    }
	return cur;
}

function initGender()
{
    //添加性别列表项的click事件
    $('.gender-item').bind('click', function(){
            var item=$(this);
            var gender = item.attr('gender_id');
			genderID = gender;
			gender_val = item.html();
            var choosenItem = item.parent().parent().find('.current');
            if(choosenItem)
                $(choosenItem).removeClass('current');
            item.addClass('current');
			updateCrumbs();
			getDataFromServer();
        }
    );
}

var analysisList = [{
		id: 1,
		name: "基本信息",
		list: [{
			id: 1,
			name: "男女比例统计图",
			div: ["UserGenderRate"],
			method: ["UserGenderRate"]
		},{
			id: 2,
			name: "网龄分布统计图",
			div: ["UserWebAgeMap"],
			method: ["UserWebAgeMap"]
		},{
			id: 3,
			name: "用户等级统计图",
			div: ["UserVerifyTypeMap"],
			method: ["UserVerifyTypeMap"]
		},{
			id: 4,
			name: "用户地域分布",
			div: ["UserRegionMap"],
			method: ["UserProvinceMap", "UserCountryMap"]
		}]
	},{
		id: 2,
		name: "行为分析",
		list: [{
			id: 1,
			name: "微博发布形式",
			div: ["WeiboReleaseForm"],
			method: ["WeiboReleaseForm"]
		},{
			id: 2,
			name: "微博来源分布",
			div: ["WeiboSourceMap"],
			method: ["WeiboPhoneBrandMap", "WeiboSourceNameMap"]
		},{
			id: 3,
			name: "时间段分布",
			div: ["WeiboTimePeriodMap"],
			method: ["WeiboTimePeriodMap"]
		},{
			id: 4,
			name: "用户关系图",
			div: ["UserRelationshipMap"],
			method: ["UserRelationshipMap"]
		}]
	},{
		id: 3,
		name: "内容分析",
		list: [{
			id: 1,
			name: "转发原创统计图",
			div: ["ForwardOriginalMap"],
			method: ["BasicInfo"]
		},{
			id: 2,
			name: "基本数量统计图",
			div: ["BasicQuantityMap"],
			method: ["BasicInfo"]
		},{
			id: 3,
			name: "预设话题基本统计图",
			div: ["PresetFocusAnalysis"],
			method: ["showPreset"]
		},{
			id: 4,
			name: "预设话题走势图",
			div: ["PresetFocusTrend"],
			method: ["showPreset"]
		},{
			id: 5,
			name: "关键词云图",
			div: ["KeyWordsMap"],
			method: ["showPreset"]
		},{
			id: 6,
			name: "所有话题关键词云图",
			div: ["TagCloud_All"],
			method: ["GenerateFocusTagCloud_All"]
		},{
			id: 7,
			name: "热门话题走势图",
			div: ["ExtractFocusTrend", "focusPage", "selectedCloud"],
			method: ["dataReady", "showFocus"]
		},{
			id: 8,
			name: "热门微话题走势图",
			div: ["ExtractTopicTrend", "topicTrendGenerateBtn", "topicPage"],
			method: ["dataReady", "showTopic"]
		}]
	},{
		id: 4,
		name: "心理分析",
		list: [{
			id: 1,
			name: "心理基本统计图",
			div: ["PsychologyEventStatistics"],
			method: ["initPsychology"]
		},{
			id: 2,
			name: "心理基本走势图",
			div: ["PsychologyEventTrend"],
			method: ["initPsychology"]
		},{
			id: 3,
			name: "心理统计云图",
			div: ["PsyEventTagCloud"],
			method: ["initPsychology"]
		}]
	},{
		id: 5,
		name: "正能量分析",
		list: [{
			id: 1,
			name: "正能量基本统计图",
			div: ["PEEventStatistics"],
			method: ["initPositiveEnergy"]
		},{
			id: 2,
			name: "正能量基本走势图",
			div: ["PEEventTren"],
			method: ["initPositiveEnergy"]
		},{
			id: 3,
			name: "正能量分析云图",
			div: ["PEEventTagCloud"],
			method: ["initPositiveEnergy"]
		}]
	}
];

function showAnalysis(id){
	$("#result div.showChart").removeClass("showChart");
	var data="{\"schoolProvince\": "+province_val+", \"schoolCity\": "+city_val+", \"schoolName\": "+school_val+", \"gender\": "+gender_val+", \"date_start\": "+dateStart+", \"date_end\": "+dateEnd+"}";
	var show = analysisList[id-1].list;
	var i = 0, j = 0;
	//for (i=0; i < show.length; i++) {
		var div = show[i].div;
		var me = show[i].method;
		for (j = 0; j < div.length; j++) {
			$("#"+div[j]).addClass("showChart");
		}
		// for (j = 0; j < me.length; j++) {
			// var method = window[me[j]];
			// if (typeof(method) === "function")
			// {
				// method.call(this, data);
			// }
		// }
	//}
    //更新模块列表
    initSection();
	getDataFromServer();
}

function showSection(id){
	$("#result div.showChart").removeClass("showChart");
	var show = analysisList[analysisID-1].list[id-1].div;
	var j = 0;
	for (j = 0; j < show.length; j++) {
		$("#"+show[j]).addClass("showChart");
	}
}

function initAnalysis()
{
    //原先的分析列表清空
    $('#analysis_sub ul').html('');
	analysisID = 1;
    for(i=0;i<analysisList.length;i++)
    {
        $('#analysis_sub ul').append('<li><a class="analysis-item" analysis_id="'+analysisList[i].id+'">'+analysisList[i].name+'</a></li>');
    }
    //添加分析列表项的click事件
    $('.analysis-item').bind('click', function(){
            var item=$(this);
			analysisID = item.attr('analysis_id');
			showAnalysis(analysisID);
			
            var choosenItem = item.parent().parent().find('.current');
            if(choosenItem)
                $(choosenItem).removeClass('current');
            item.addClass('current');
			updateCrumbs();

        }
    );
}

function initSection()
{
	$("#section_sub h4").html(analysisList[analysisID-1].name);
    //原先的模块列表清空
    $('#section_sub ul').html('');
	var next = $('#section_sub ul').next();
	if (next)
	{
		next.remove();
	}
	sectionID = 1;
	var sectionList = analysisList[analysisID-1].list;
    for(i=0;i<sectionList.length;i++)
    {
        $('#section_sub ul').append('<li><a class="section-item" section_id="'+sectionList[i].id+'">'+sectionList[i].name+'</a></li>');
    }
    //添加模块列表项的click事件
    $('.section-item').bind('click', function(){
            var item=$(this);
            sectionID = item.attr('section_id');
			showSection(sectionID);
			updateCrumbs();

            var choosenItem = item.parent().parent().find('.current');
            if(choosenItem)
                $(choosenItem).removeClass('current');
            item.addClass('current');
			getDataFromServer();
        }
    );	
}

function updateCrumbs(){
	$("#crumbs").html("");
	if (province_val != "") {
		$("#crumbs").append("<li><a>"+province_val+"</a></li>");
		if (city_val != "") {
			$("#crumbs").append("<li><a>"+city_val+"</a></li>");
			if (school_val != "") {
				$("#crumbs").append("<li><a>"+school_val+"</a></li>");
			}
		}
	}
	else {
		$("#crumbs").append("<li><a>全国</a></li>");
	}
	if (gender_val != "") {
		$("#crumbs").append("<li><a>"+gender_val+"</a></li>");
	}
	if (dateStart != "" || dateEnd != "") {
		$("#crumbs").append("<li><a>"+dateStart+"~"+dateEnd+"</a></li>");
	}
	if (analysisID != 0) {
		$("#crumbs").append("<li><a>"+analysisList[analysisID-1].name+"</a></li>");
	}
	if (sectionID != 0) {
		$("#crumbs").append("<li><a>"+analysisList[analysisID-1].list[sectionID-1].name+"</a></li>");
	}
}

function getDataFromServer(){
	dateStart = $("#datetimepickerStart input").val();
	dateEnd = $("#datetimepickerEnd input").val();
	var data="{\"schoolProvince\": \""+province_val+"\", \"schoolCity\": \""+city_val+"\", \"schoolName\": \""+school_val+"\", \"gender\": \""+gender_val+"\", \"date_start\": \""+dateStart+"\", \"date_end\": \""+dateEnd+"\"}";
	var funcList = analysisList[analysisID-1].list[sectionID-1].method;
	if (funcList.length > 0) {
		for (i = 0; i<funcList.length; i++) {
			var method = window[funcList[i]];
			if (typeof(method) === "function")
			{
				method.call(this, JSON.parse(data));
			}
		}
	}
}