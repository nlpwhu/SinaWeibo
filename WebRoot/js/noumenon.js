/*
 * 获取内容本体
 */
function FatchContentNoumenon(oriData){
	var ContentNoumenon;
	$.ajax({
		async : false,
		url : 'FatchContentNoumenon.action',
		type: 'POST',
		dataType: 'json',
		data: JSON.parse(oriData),
		success : function(json, status) {
			ContentNoumenon = json.content;
			
		},
		error : function(a, b) {
			alert("可能由于网络原因，获取预设话题失败，请联系管理员");
		}
	});
	return ContentNoumenon;
}
/*
 * 获取正能量本体
 */
function FatchPositiveEnergyNoumenon(oriData){
	var PositiveEnergyNoumenon;
	$.ajax({
		async : false,
		url : 'FatchPositiveEnergyNoumenon.action',
		type: 'POST',
		dataType: 'json',
		data: JSON.parse(oriData),
		success : function(json, status) {
			PositiveEnergyNoumenon = json.positiveEnergy;
			
		},
		error : function(a, b) {
			alert("可能由于网络原因，获取预设话题失败，请联系管理员");
		}
	});
	return PositiveEnergyNoumenon;
}
/*
 * 获取心理本体
 */
function FatchPsychologyNoumenon(oriData){
	var PsychologyNoumenon;
	$.ajax({
		async : false,
		url : 'FatchPsychologyNoumenon.action',
		type: 'POST',
		dataType: 'json',
		data: JSON.parse(oriData),
		success : function(json, status) {
			PsychologyNoumenon = json.psychology;
			
		},
		error : function(a, b) {
			alert("可能由于网络原因，获取预设话题失败，请联系管理员");
		}
	});
	return PsychologyNoumenon;
}
/**
 * 获取某个本体事件的关键词列表
 * 请求：FatchEventTagData.action
 * 参数：eventId(事件ID)
 * 返回类型：json
 * 返回数据：json.keywordList

 */
 function FatchPrefocusEventTagData(eventId, oriData){
	var keywordList;
	$.ajax({
		async : false,
		url : 'FatchPrefocusEventTagData.action?eventId='+eventId,
		type: 'POST',
		dataType: 'json',
		data: JSON.parse(oriData),
		success : function(json, status) {
			keywordList = json.keywordList ;
		},
		error : function(a, b) {
			alert("可能由于网络原因，获取预设话题失败，请联系管理员");
		}
	});
	return keywordList;
}

 function FatchPsychologyEventTagData(eventId, oriData){
	var keywordList;
	$.ajax({
		async : false,
		url : 'FatchPsychologyEventTagData.action?eventId='+eventId,
		type: 'POST',
		dataType: 'json',
		data: JSON.parse(oriData),
		success : function(json, status) {
			keywordList = json.keywordList ;
		},
		error : function(a, b) {
			alert("可能由于网络原因，获取预设话题失败，请联系管理员");
		}
	});
	return keywordList;
}

function FatchPenergyEventTagData(eventId, oriData){
	var keywordList;
	$.ajax({
		async : false,
		url : 'FatchPenergyEventTagData.action?eventId='+eventId,
		type: 'POST',
		dataType: 'json',
		data: JSON.parse(oriData),
		success : function(json, status) {
			keywordList = json.keywordList ;
		},
		error : function(a, b) {
			alert("可能由于网络原因，获取预设话题失败，请联系管理员");
		}
	});
	return keywordList;
}

/**
 * 获取某个本体事件的关键词列表，然后做关键词云图
 * 请求：FatchEventTagData.action
 * 参数：eventId(事件ID)
 */
 function GeneratePrefocusEventTagCloud(eventId, ele, oriData){
	if (!ele.is(":visible"))
	{
		return;
	}
	$(".loadingCover").show();
	ele.html("");
	
	var keywordList = FatchPrefocusEventTagData(eventId, oriData) ;
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
	ele.jQCloud(word_list, { afterCloudRender: function(){
		$(".loadingCover").hide();
	}});
}

function GeneratePsychologyEventTagCloud(eventId, ele, oriData){
	if (!ele.is(":visible"))
	{
		return;
	}
	$(".loadingCover").show();
	ele.html("");
	
	var keywordList = FatchPsychologyEventTagData(eventId, oriData) ;
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
	ele.jQCloud(word_list, { afterCloudRender: function(){
		$(".loadingCover").hide();
	}});
}

function GeneratePenergyEventTagCloud(eventId, ele, oriData){
	if (!ele.is(":visible"))
	{
		return;
	}
	$(".loadingCover").show();
	ele.html("");
	
	var keywordList = FatchPenergyEventTagData(eventId, oriData) ;
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
	ele.jQCloud(word_list, { afterCloudRender: function(){
		$(".loadingCover").hide();
	}});
}

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

	function clickCloudLabel(thisEle, oriData) {
		var idx = thisEle.parent("li").index();
		var ele = thisEle.next("#EventTagCloud");
		if (thisEle.parents("#PEEventTagCloud").length > 0) {
			GeneratePenergyEventTagCloud(idx, ele, oriData);
		}
		else if (thisEle.parents("#PsyEventTagCloud").length > 0) {
			GeneratePsychologyEventTagCloud(idx, ele, oriData);
		}
		else {
			ele = thisEle.parent().parent().next("#EventTagCloud");
			GeneratePrefocusEventTagCloud(idx, ele, oriData);
		}

		if (thisEle.children("span").hasClass("labelActive"))
		{
			return false;
		}
		changeCloudLabelColor(thisEle);
		return false;
	}
