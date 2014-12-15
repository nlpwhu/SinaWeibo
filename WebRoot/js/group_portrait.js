function SearchGroupPortrait(keywordStr){
	if(keywordStr=='')
		keywordStr = $("#keywordStr").val();
	var data={"keywordStr":keywordStr};
	$.ajax({
		async : false,
		url : 'SearchGroupPortrait.action',
		type : 'POST',
		dataType: 'json',
		data : data,
		success : function(json, status) {
			$("#GroupPortraitTagCloud").html("");
			var keywordList = json.keywordList ;
			var word_list = [];
			for(var i=0 ; i<keywordList.length ; i++){
				if(i%3==0 && i!=0){
					var t ={text:keywordList[i].name,weight:parseInt(keywordList[i].score),html: {"class": "vertical"}};
					word_list.push(t);
				}
				else{
					var t ={text:keywordList[i].name,weight:parseInt(keywordList[i].score)};
					word_list.push(t);
				}
			}
			$("#GroupPortraitTagCloud").jQCloud(word_list);
		},
		error : function(a, b) {
			alert("可能由于网络原因，数据获取失败，请联系管理员");
		}
	});
	
/* 	(function() {
			$("#GroupPortraitTagCloud").html("");
			var keywordList = [{
				freq: 0,
				name: "一生",
				score: 230.25900268554688
				},{
				freq: 0,
				name: "下策",
				score: 313.1520080566406
				},{
				freq: 0,
				name: "不争气",
				score: 193.41700744628906}];
			var word_list = [];
			for(var i=0 ; i<keywordList.length ; i++){
				if(i%3==0 && i!=0){
					var t ={text:keywordList[i].name,weight:parseInt(keywordList[i].score),html: {"class": "vertical"}};
					word_list.push(t);
				}
				else{
					var t ={text:keywordList[i].name,weight:parseInt(keywordList[i].score)};
					word_list.push(t);
				}
			}
			$("#GroupPortraitTagCloud").jQCloud(word_list);
		})(); */
}

function FatchGroupPortraitList(){
	$.ajax({
		async : false,
		url : 'FatchGroupPortraitList.action',
		type : 'POST',
		dataType: 'json',
		success : function(json, status) {
			var gpNameList = json.gpNameList;
			$("#keywordStr").attr("placeholder", gpNameList[0]);
			SearchGroupPortrait(gpNameList[0]);
			for(var i=0 ; i<gpNameList.length;i++){
				var liName = gpNameList[i];
				var li="<li><a href=''>" + liName + "</a></li>";
				//alert(li);
				$("#GroupPortraitList").append(li);
			}
			$("#GroupPortraitList a").click(function(){
				var aname = $(this).html();
				SearchGroupPortrait(aname);
				$("#keywordStr").val(aname);
			});
		},
		error : function(a, b) {
			alert("可能由于网络原因，数据获取失败，请联系管理员");
		}
	});
/* 	(function() {
			var gpNameList = ["人生赢家","北大","华科","学渣","学霸","学霸-武汉大学","屌丝","武大","武汉大学","白富美","高富帅"];
			$("#keywordStr").attr("placeholder", gpNameList[0]);
			SearchGroupPortrait(gpNameList[0]);
			for(var i=0 ; i<gpNameList.length;i++){
				var liName = gpNameList[i];
				var li="<li><a href=''>" + liName + "</a></li>";
				//alert(li);
				$("#GroupPortraitList").append(li);
			}
			$("#GroupPortraitList a").click(function(){
				var aname = $(this).html();
				SearchGroupPortrait(aname);
				$("#keywordStr").val(aname);
			});
	})();	 */
}








/*******************************************/


$(document).ready(function(){
	FatchGroupPortraitList();
	
	$("#searchSubmit").click(function(){
		var val = $("keywordStr").val();
		SearchGroupPortrait(val);
	});
});








