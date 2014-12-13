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
function FatchEventTagData(eventId, oriData){
	var keywordList;
	$.ajax({
		async : false,
		url : 'FatchEventTagData.action?eventId='+eventId,
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
function GenerateEventTagCloud(eventId, ele, oriData){
	if (!ele.is(":visible"))
	{
		return;
	}
	$(".loadingCover").show();
	ele.html("");
	
	var keywordList = FatchEventTagData(eventId, oriData) ;
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
		
$(".cloudLabelUl li a").click(function(){
	var idx = $(this).parent("li").index();
	if ($(this).parents("#PEEventTagCloud")) {
		idx += 5;
	}
	else if ($(this).parents("#PsyEventTagCloud")) {
		idx += 15;
	}

	var ele = $(this).next("#EventTagCloud");
	if (!ele) {
		ele = $(this).parent().parent().next("#EventTagCloud");
	}
	GenerateEventTagCloud(idx, ele, oriData);
	if ($(this).children("span").hasClass("labelActive"))
	{
		return false;
	}
	changeCloudLabelColor($(this));
	return false;
});
