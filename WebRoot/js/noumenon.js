/*
 * 获取内容本体
 */
function FatchContentNoumenon(){
	var data={"schoolProvince":"","schoolCity":"","schoolName": "", "gender": "","date_start":"2014-02-01","date_end":"2014-05-01"};
	var ContentNoumenon;
	$.ajax({
		async : false,
		url : 'FatchContentNoumenon.action',
		type : 'POST',
		data : data,
		dataType: 'json',
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
function FatchPositiveEnergyNoumenon(){
	var data={"schoolProvince":"","schoolCity":"","schoolName": "", "gender": "","date_start":"2014-02-01","date_end":"2014-05-01"};
	var PositiveEnergyNoumenon;
	$.ajax({
		async : false,
		url : 'FatchPositiveEnergyNoumenon.action',
		type : 'POST',
		data: data,
		dataType: 'json',
		success : function(json, status) {
			PositiveEnergyNoumenon = json.positiveEnergy;
			
		},
		error : function(a, b) {
			alert("可能由于网络原因，获取正能量话题失败，请联系管理员");
		}
	});
	return PositiveEnergyNoumenon;
}
/*
 * 获取心理本体
 */
function FatchPsychologyNoumenon(){
	var data={"schoolProvince":"","schoolCity":"","schoolName": "", "gender": "","date_start":"2014-02-01","date_end":"2014-05-01"};
	var PsychologyNoumenon;
	$.ajax({
		async : false,
		url : 'FatchPsychologyNoumenon.action',
		type : 'POST',
		data : data,
		dataType: 'json',
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
 * 获取正能量事件的关键词列表
 * 请求：FatchPenergyEventTagData.action
 * 参数：eventId(事件ID)
 * 返回类型：json
 * 返回数据：json.keywordList
 */
function FatchPenergyEventTagData(eventId){
	var keywordList;
	$.ajax({
		async : false,
		url : 'FatchPenergyEventTagData.action?eventId='+eventId,
		type : 'GET',
		dataType: 'json',
		success : function(json, status) {
			keywordList = json.keywordList ;
		},
		error : function(a, b) {
			alert("可能由于网络原因，获取预设话题失败，请联系管理员");
		}
	});
	return keywordList;
}

function FatchPsychologyEventTagData(eventId){
	var keywordList;
	$.ajax({
		async : false,
		url : 'FatchPsychologyEventTagData.action?eventId='+eventId,
		type : 'GET',
		dataType: 'json',
		success : function(json, status) {
			keywordList = json.keywordList ;
		},
		error : function(a, b) {
			alert("可能由于网络原因，获取预设话题失败，请联系管理员");
		}
	});
	return keywordList;
}

function FatchPrefocusEventTagData(eventId){
	var keywordList;
	$.ajax({
		async : false,
		url : 'FatchPrefocusEventTagData.action?eventId='+eventId,
		type : 'GET',
		dataType: 'json',
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
 * 获取正能量事件的关键词列表，然后做关键词云图
 * 请求：FatchPenergyEventTagData.action
 * 参数：eventId(事件ID)
 */
function GeneratePenergyEventTagCloud(eventId){
	$(".loadingCover").show();
	$("#EventTagCloud").html("");
	
	var keywordList = FatchPenergyEventTagData(eventId) ;
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
	$("#EventTagCloud").jQCloud(word_list, { afterCloudRender: function(){
		$(".loadingCover").hide();
	}});
}

function GeneratePrefocusEventTagCloud(eventId){
	$(".loadingCover").show();
	$("#EventTagCloud").html("");
	
	var keywordList = FatchPrefocusEventTagData(eventId);
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
	$("#EventTagCloud").jQCloud(word_list, { afterCloudRender: function(){
		$(".loadingCover").hide();
	}});
}

function GeneratePsychologyEventTagCloud(eventId){
	$(".loadingCover").show();
	$("#EventTagCloud").html("");
	
	var keywordList = FatchPsychologyEventTagData(eventId) ;
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
	$("#EventTagCloud").jQCloud(word_list, { afterCloudRender: function(){
		$(".loadingCover").hide();
	}});
}