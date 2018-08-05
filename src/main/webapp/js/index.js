var buttoncount=1;
var questiontag = new Map();
var questionintag = new Map();
var newquestionmap=new Map();
var newparameterid=0;
var newquestionid=0;
var newparameterlist=[];
var solutionid=0;
var nowquestionid=0;
var nownewparameter='';
var thisnowquestion='';
function SelectText(){
	try{
		var selecter=window.getSelection().toString();
		if(selecter!=null&&selecter.trim()!=""){
			$("#tbe").append("<tr id='"+buttoncount+"'><td><div class='btn-toolbar'><div class='btn-group'><button  class='btn btn-danger' onclick='deletefuc(event);'>删除</button></div><div class='btn-group'><button name='new' class='btn btn-default' onclick='shownewquestion(event);' contenteditable='true'>"+selecter+"</button></div></div></td></tr>");
			buttoncount=buttoncount+1;
		}
	}catch(err){
		var s=selecter.text;
		if(s!=null&&s.trim()!=""){
			alert(s)
		}
	}
}

function deletefuc(event){
	var p=event.srcElement.parentNode.parentNode.parentNode.parentNode.id;
	$("#"+p).remove();
}


$("#tijiao").on("click",function(){
	var text=$("#textn").val();
	$.ajax({
		type : "POST",
		url : "/JerseyTest/webapi/myresource/getparametes",
		scriptCharset: "utf-8",
		data:{description:text},
		success : function (data) {
			var obj = eval(data);
			for(j = 0; j < obj.length; j++){
				item=obj[j];
				$("#tbe").append("<tr id='"+buttoncount+"'><td><div class='btn-toolbar'><div class='btn-group'><button  class='btn btn-danger' onclick='deletefuc(event);'>删除</button></div><div class='btn-group'><button name='inDB' id='"+item.id+"' questionid='"+item.questionid+"' class='btn btn-default' onclick='showquestion(event);'>"+item.parameter+"</button></div></div></td></tr>");
				buttoncount=buttoncount+1;
			}
		}});
});



function showquestion(event){
	tagid=event.srcElement.parentNode.parentNode.parentNode.parentNode.id;
	questionid=event.currentTarget.attributes.questionid.nodeValue;
	if(questionid!=null){
		if(questionid==0){
			alert("没有对应的question!")
		}else{
			if(questiontag.size>0){
				if(questiontag[questionid]){
					$("#"+questionid).remove();
					questiontag[questionid]=false;		
				}
			}else{
				if(questiontag[questionid]){
					$("#"+questionid).remove();
					questiontag[questionid]=false;		
				}else{
					questiontag[questionid]=true;
					$.ajax({
						type : "POST",
						url : "/JerseyTest/webapi/myresource/question",
						data:{quesid:questionid},
						success : function (data) {
							$("#"+tagid).after("<li id="+questionid+" onclick='questionparameterfuc(event);'><button>"+data+"</button></li>")
						}
					});					
				}
			}
		}
	}
}


function questionparameterfuc(event){
	questid=event.currentTarget.attributes.id.nodeValue;
	if(questionintag.size>0){
		if(questionintag[questid]){
			$("#tb"+questid).remove();
			questionintag[questid]=false;		
		}
	}else{
		if(questionintag[questid]){
			$("#tb"+questid).remove();
			questionintag[questid]=false;		
		}else{
			if(questid!=null){				
				$.ajax({
					type : "POST",
					url : "/JerseyTest/webapi/myresource/getparamaterbyquestionid",
					data:{quesid:questid},
					success : function (data) {
						$("#"+questid).after('<table id="tb'+questid+'" class="table"><thead><tr><th scope="col">参数编号</th><th scope="col">问题内参数</th><th scope="col">平行参数</th></tr></thead><tbody id="tbl'+questid+'"></tbody></table>');
						var obj = eval(data);
						for(j = 0; j < obj.length; j++){
							$("#tbl"+questid).append('<tr><td>'+obj[j].id+'</td><td contentEditable="true">'+obj[j].parameterinquestion+'</td><td contentEditable="true">'+obj[j].parameter+'</td></tr>');
						}
						$("#tbl"+questid).append("<button onclick='updateallfuc(event);'>更新</button>");
					}
				});
				questionintag[questid]=true;
			}
		}
	}
}

function updateallfuc(event){
	currentid=event.currentTarget.parentNode.id;
	thistable = new Map();
	var trList = $("#"+currentid).children("tr");
	for (var i=0;i<trList.length;i++) {
		thistable = new Map();
		var tdArr = trList.eq(i).find("td");
		thistable["id"] = tdArr[0].innerText; 
		thistable["parameterinquestion"]= tdArr[1].innerText; 
		thistable["parameter"] = tdArr[2].innerText;
		parameterlist.push(thistable);
	}
	if(parameterlist.length==trList.length){
		var map2json=JSON.stringify(parameterlist);
		$.ajax({
			type : "POST",
			url : "/JerseyTest/webapi/myresource/updateparamaters",
			data:{parametersJSON:map2json},
			success : function (data) {
				if(data){
					alert("更新成功！");
				}		
			}
		});
	}else{
		alert("获取目标列表有误！");
	}
}

function shownewquestion(event){
	trid=event.srcElement.parentNode.parentNode.parentNode.parentNode.id;
	nowquestionid=trid;
	nownewparameter=event.srcElement.innerText;
	if(newquestionmap[trid]){
		newquestionmap[trid]=false;
		$("#newquestion"+trid).remove();
	}else{
		$("#"+trid).after('<div id=newquestion'+trid+'>'
				+'<div class="form-group">'
				+'<label for="question">问题:</label>'
				+'<textarea id="question'+trid+'" rows="3" cols="35"></textarea> >'
				+'</div>'
				+'<button class="btn btn-default" onclick="newques(event)">确定</button>'
				+'</div>');
		newquestionmap[trid]=true;
	}
}

function dealfuc(event){
	eventtag=event;
}

function getnewparemeterid(parameter){
	$.ajax({
		type : "POST",
		url : "/JerseyTest/webapi/myresource/insertnewparameter",
		scriptCharset: "utf-8",
		data:{parame:parameter},
		success : function (data) {
			parameterlist.push(data);
		}
	});
}


$("#tianjia").on("click",function(){
	solutiontext=$("#solution").val();
	var parameterStr="";
	tb = document.getElementById('tbe');
	var rows = tb.rows;
	for (var i=0;i<rows.length;i++) {
		tdArrid = rows[i].childNodes[0].childNodes[0].childNodes[1].childNodes[0].id;
		butname=rows[i].childNodes[0].childNodes[0].childNodes[1].childNodes[0].name;
		if(butname=="inDB"){
			newparameterlist.push(tdArrid);
		}
	}
	$.ajax({
		type : "POST",
		url : "/JerseyTest/webapi/myresource/putInparametersolution",
		scriptCharset: "utf-8",
		data:{parameters:JSON.stringify(newparameterlist),
			solutionid:solutiontext
		},
		success : function (data) {
			newparameterlist=[];
			if(data=='0'){
				alert("添加到数据库出错！");
			}else{
				alert("添加成功！");
				location.reload();
				};
		}
	});
});


function newques(event){
	trid=nowquestionid;
	thisnowquestion=$("#question"+trid).val();
	nownewparameter;
	$.ajax({
		type : "POST",
		url : "/JerseyTest/webapi/myresource/putNewparameter",
		scriptCharset: "utf-8",
		data:{newparameter:nownewparameter,
			question:thisnowquestion
		},
		success : function (data) {
			$("#question"+trid).remove();
			if(data=="0"){
				alert("添加失败！");
			}else{
				$("#newquestion"+trid).remove();
				var returndata=eval(data);
				destinationitem=returndata["parameterIN"];
				parameter=returndata["parameterlist"];
				newquestionid=returndata["questionid"];
				parameterlist=eval(parameter); 
				parerIn=destinationitem;
				targetparamearr=destinationitem.split(';');
				divstr= '<div id="selectoption">'
					+'<h2>'+thisnowquestion+'</h2>'
					+'<hr />'
					+'<select id="source" data-search="Search for options">'
					for(var theitem in parameterlist){
						parameterid=parameterlist[theitem]["parameterid"];
						strparam=parameterlist[theitem]["parameter"];
						divstr=divstr+'<option value="'+parameterid+'">'+strparam+'</option>';
					}
				destinationstr='</select>'
					+'<select id="destination"  data-search="Search for options">'
					parameterid=targetparamearr[0];
				newparameterlist.push(parameterid);
				strparam=targetparamearr[1];
				destinationstr=destinationstr+'<option value="'+parameterid+'">'+strparam+'</option>';
				divendstr='</select>'
					+'<button class="btn btn-default" onclick="newquestionparameter(event)">确定</button>'
					//+'<button class="btn btn-default" onclick="newquestionparameter(event)">确定</button>'
					+'</div>';
				$("#"+trid).after(divstr+destinationstr+divendstr);
				$('#source, #destination').listswap({
					truncate:true,
					height:162,
				});
			}
		}
	});
}


function newquestionparameter(event){	
	var optionlist=[];
	optionrows=document.getElementById('destination').children;
	for(row=0;row<optionrows.length;row++){
		thisrow=optionrows[row].value;
		optionlist.push(thisrow);
	}
	$.ajax({
		type : "POST",
		url : "/JerseyTest/webapi/myresource/bundnewparameter",
		scriptCharset: "utf-8",
		data:{parameter:JSON.stringify(optionlist),
			questionid:newquestionid
		},
		success : function (data) {
			if(data=="1"){
				alert("绑定成功！");
				$("#selectoption").remove();
			}
		}
	});
}


