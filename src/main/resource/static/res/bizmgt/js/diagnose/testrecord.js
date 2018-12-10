/** *医生管理** */
$('#btn_query').on("click",function(){
		var query ={}
		$('#tb_testrecords').bootstrapTable('refresh',{query:query});
	});
document.onkeydown=function(event){
    e = event ? event : (window.event ? window.event : null);
    if(e.keyCode==13){
    	$('#btn_query').click();
    }
}
$("#formSearch").on("submit",function(){
	return false;
});
$("#diagnoseModal").on('hide.bs.modal', function(){
	$("#diagnoseModal").find("#testRecordId").val('');
	$("#diagnoseModal").find("#resultRemark").val('');
	$("#diagnoseModal").find("#patientId").val('');
	$("#diagnoseModal").find("#patientName").val('');
	$('[id="diseaseTypeSuggest"]:checkbox').each(function(){
		this.checked=false;
	});
	$('[id="disease_right"]:checkbox').each(function(){
		this.checked=false;
	});
	$('[id="disease_left"]:checkbox').each(function(){
		this.checked=false;
	});
	$('[name="degree_right"]:radio').each(function(){
		this.checked=false;
	});
	$('[name="degree_left"]:radio').each(function(){
		this.checked=false;
	});
});
$("#btn_diagnose").on("click",function(){
	var testRecordId = $("#diagnoseModal").find("#testRecordId").val();
	var resultRemark = $("#diagnoseModal").find("#resultRemark").val();
	var patientId = $("#diagnoseModal").find("#patientId").val();
	var patientName = $("#diagnoseModal").find("#patientName").val();
	var id_array=new Array();
	$('input[name="disease_left"]:checked').each(function(){
	    if($(this).val() == '18073011504100001'){
	    	var degree = $("input[name='degree_left']:checked").val();
	    	id_array.push($(this).val()+'#'+degree);
	    }else{
	    	id_array.push($(this).val());//向数组中添加元素
	    }
	});
	var diseaseLeft = id_array.join(',');
	var id_array=new Array();
	$('input[name="disease_right"]:checked').each(function(){
		if($(this).val() == '18073011504100001'){
			var degree = $("input[name='degree_right']:checked").val();
			id_array.push($(this).val()+'#'+degree);
		}else{
			id_array.push($(this).val());//向数组中添加元素
		}
	});
	var diseaseRight = id_array.join(',');
	var suggest=new Array();
	$('input[name="diseaseTypeSuggest"]:checked').each(function(){
		suggest.push($(this).val());//向数组中添加元素
	});
	var diseaseSuggest = suggest.join(',');
	$.ajax({
		url: basePath+'/diagnoserecord/add',
		data:{"testRecordId":testRecordId,"resultRemark":resultRemark,"diseaseLeft":diseaseLeft,"diseaseRight":diseaseRight,"suggest":diseaseSuggest},
		type: "POST",
		success: function(data)
		{
			data = JSON.parse(data);
			if(data.result){
				layer.alert(data.message,{icon:1});
				$('#btn_query').click();
				$("#diagnoseModal").modal("hide");
			}else{
				layer.alert(data.message,{icon:2});
			}
		}
	});
});


$("#btn_ai_report").on("click",function(){
	var rows = $('#tb_testrecords').bootstrapTable("getSelections");
	if(rows.length==0){
		layer.alert("请选择数据",{icon:2});
	}else{
		$.ajax({
			url: basePath+'/diagnoserecord/getDetail',
			data:{"testRecordId":rows[0].testRecordId},
			type: "POST",
			success: function(data)
			{
				data = JSON.parse(data);
				var imagesJson = JSON.parse(data.testPic);
				var html_left='';
				var html_right='';
				if(imagesJson.length>0){
					for(var i=0;i<imagesJson.length;i++){
						var title = '';
						var isOS = (imagesJson[i].type=="L");
						var isOD = (imagesJson[i].type=="R");
						if(isOS){
							title = "左眼图像";
							html_left += '<li style="background-color: #ccd; float:left;position:relative;right:50%;">'+
							'<img style="width:280px;height:210px;" title="'+title+'" data-original="'+imgRes+imagesJson[i].url+'" src="'+imgRes+imagesJson[i].url+'" class="viewer img-responsive" alt=""></li>';
						}else if(isOD){
							title = "右眼图像";
							html_right += '<li style="background-color: #ccd; float:left;position:relative;right:50%;">'+
							'<img style="width:280px;height:210px;" title="'+title+'" data-original="'+imgRes+imagesJson[i].url+'" src="'+imgRes+imagesJson[i].url+'" class="viewer img-responsive" alt=""></li>';
						}
					}
				}
				var pic_left = $("#AiReportModal").find("#pictures_left");
				pic_left.empty();
				pic_left.html(html_left);
				var pic_right = $("#AiReportModal").find("#pictures_right");
				pic_right.empty();
				pic_right.html(html_right);
				$('#reportModal').viewer({
				    url: 'data-original',
				});
				if(data.patientName){
					$("#AiReportModal").find("#patientName").html(data.patientName);
				}
				var aiResult = data.aiResult;
				if(aiResult){
					$("#ai_tr").show();
					if(aiResult.indexOf(",")!=-1){
						$.ajax({
							url: basePath+'/airesult/getAll',
							type: "POST",
							success: function(data)
							{
								data = JSON.parse(data);
								var htmlLeft ="";
								var htmlRight ="";
								for(var i=0;i<data.length;i++){
									var dd = data[i];
									htmlLeft = htmlLeft+'<div class="radio">'+
				              		'<label><input id="degree_left" name="degree_left" type="radio" value="'+dd.aiCode+'" disabled>'+dd.description+'</label>'+
					              	'</div>';
			              			htmlRight = htmlRight+'<div class="radio">'+
			              			'<label><input id="degree_right" name="degree_right" type="radio" value="'+dd.aiCode+'" disabled>'+dd.description+'</label>'+
			              			'</div>';
								}
								var right = aiResult.replace("右眼","").split(",")[0].split(":")[0];
								var left = aiResult.replace("左眼","").split(",")[1].split(":")[0]
									$("#right_ai").html(htmlRight);
									$("#left_ai").html(htmlLeft);
									$("input[name='degree_left'][value='"+left+"']").prop('checked', 'checked');
									$("input[name='degree_right'][value='"+right+"']").prop('checked', 'checked');
							}
						});
						
					}else{
						$("#right_ai").html(aiResult);
						$("#left_ai").html(aiResult);
					}
				}else{
					$("#right_ai").html("AI未诊断");
					$("#left_ai").html("AI未诊断");
				}
				if(data.cardNo){
					$("#AiReportModal").find("#idCard").html(data.cardNo.substr(0,14)+'****');
				}
				if(data.age){
					$("#AiReportModal").find("#age").html(data.age);
				}
				if(data.gender){
					$("#AiReportModal").find("#gender").html(data.gender==0?'男':'女');
				}
				if(data.hospitalName){
					$("#AiReportModal").find("#hospitalName").html(data.hospitalName);
				}
				if(data.testTime){
					$("#AiReportModal").find("#testTime").html(data.testTime);
				}
				
				$("#AiReportModal").modal("show");
			}
		});
	}
});


$("#AiReportModal").find("#btn_print").click(function(event) {
	$("#AiReportModal").find("#right_ai").find("input[name='degree_right']").removeAttr("disabled");
	$("#AiReportModal").find("#left_ai").find("input[name='degree_left']").removeAttr("disabled");
	window.print();
	$("#AiReportModal").find("#right_ai").find("input[name='degree_right']").attr("disabled","disabled");
	$("#AiReportModal").find("#left_ai").find("input[name='degree_left']").attr("disabled","disabled");
});


$(function(){
	
	$.ajax({
		url: basePath+'/diseasetype/getAllData',
		type: "POST",
		success: function(data)
		{
			data = JSON.parse(data);
			var diseaseLeftHtml = "";
			var diseaseRightHtml = "";
			if(data){
				for(var i=0;i<data.length;i++){
					var row = data[i];
					diseaseLeftHtml += '<label class="checkbox-inline"><input id="disease_left" checked name="disease_left" type="checkbox" value="'+row.diseaseTypeId+'"> '+row.diseaseTypeName+'</label>';
					diseaseRightHtml += '<label class="checkbox-inline"><input id="disease_right" checked name="disease_right" type="checkbox" value="'+row.diseaseTypeId+'"> '+row.diseaseTypeName+'</label>';
				}
				$("#disease_left").html(diseaseLeftHtml);
				$("#disease_right").html(diseaseRightHtml);
			}
		}
	});
	
	$.ajax({
		url: basePath+'/diseasetypesuggest/getAllData',
		type: "POST",
		data:{"diseaseTypeId":"18073011504100001"},
		success: function(data)
		{
			data = JSON.parse(data);
			var diseaseHtml = "";
			if(data){
				for(var i=0;i<data.length;i++){
					var row = data[i];
					diseaseHtml += '<label class="checkbox" style="padding-bottom:5px;"><input  id="diseaseTypeSuggest" name="diseaseTypeSuggest" type="checkbox" value="'+row.suggestId+'" text="'+row.suggestContent+'"> '+row.suggestContent+'</label>';
				}
				$("#diseaseTypeSuggest").html(diseaseHtml);
				
			}
		}
	});
	// 1.初始化Table
    var deviceTable = new deviceTableInit();
    deviceTable.Init();
});
//$("#diseaseTypeSuggest").on('change', function(){
//	var id_array=new Array();
//	$('input[name="diseaseTypeSuggest"]:checked').each(function(){
//		id_array.push($(this).attr('text'));//向数组中添加元素
//	});
//	var suggest = id_array.join(',');
//	$("#resultRemark").val(suggest);
//	
//});
$("#diagnose").on('click', function(){
	var rows = $('#tb_testrecords').bootstrapTable("getSelections");
	if(rows.length==0){
		layer.alert("请选择数据",{icon:2});
	}else{
		var row = rows[0];
		var status = row.diagnoseState;
    	
		if(status==2){
    		layer.alert("该检查记录已诊断",{icon:2});
    		return false;
    	}else{
    		$.ajax({
    			url: basePath+'/diagnoserecord/getDetail',
    			type: "POST",
    			data:{"testRecordId":row.testRecordId},
    			success: function(data)
    			{
    				data = JSON.parse(data);
    				
    				if(data.patientName){
    					$("#diagnoseModal").find("#patientName").val(data.patientName);
    					$("#diagnoseModal").find("#patientName_show").html(data.patientName);
    				}
    				if(data.cardNo){
    					$("#diagnoseModal").find("#idCard").html(data.cardNo.substr(0,14)+'****');
    				}
    				if(data.age){
    					$("#diagnoseModal").find("#age").html(data.age);
    				}
    				if(data.gender){
    					$("#diagnoseModal").find("#gender").html(data.gender==0?'男':'女');
    				}
    				if(data.hospitalName){
    					$("#diagnoseModal").find("#hospitalName").html(data.hospitalName);
    				}
    				if(data.testTime){
    					$("#diagnoseModal").find("#testTime").html(data.testTime);
    				}
    				var suggest = data.suggest;
    				if(suggest){
    					var d = suggest.split(",");
    					for (var i = 0; i < d.length; i++) {
    						$("input[name='diseaseTypeSuggest'][value='"+d[i]+"']").prop('checked', 'checked');
    					}
    				}
    				var aiResult = data.aiResult;
    				if(aiResult){
    					$("#ai_tr").show();
    					if(aiResult.indexOf(",")!=-1){
    						$.ajax({
    							url: basePath+'/airesult/getAll',
    							type: "POST",
    							success: function(data)
    							{
    								data = JSON.parse(data);
    								for(var i=0;i<data.length;i++){
    									var dd = data[i];
    									var right = aiResult.replace("右眼","").split(",")[0].split(":")[0];
    									var left = aiResult.replace("左眼","").split(",")[1].split(":")[0]
    									if(dd.aiCode==right){
    										$("#diagnoseModal").find("#right_ai").html("AI诊断结果："+dd.description);
    									}
    									if(dd.aiCode==left){
    										$("#diagnoseModal").find("#left_ai").html("AI诊断结果："+dd.description);
    									}
    								}
    							}
    						});
    						
    					}else{
    						$("#diagnoseModal").find("#right_ai").html(aiResult);
    						$("#diagnoseModal").find("#left_ai").html(aiResult);
    					}
    				}else{
    					$("#diagnoseModal").find("#ai_tr").hide();
    				}
    				var diseaseIdLeft = data.diseaseIdLeft;
    				var diseaseIdRight = data.diseaseIdRight;
    				if(diseaseIdRight){
    					var d = diseaseIdRight.split(",");
    					for (var i = 0; i < d.length; i++) {
    						if(d[i].indexOf("#")!=-1){
    							$("#diagnoseModal").find("input[name='degree_right'][value='"+d[i].split("#")[1]+"']").prop('checked', 'checked');
    							$("#diagnoseModal").find("input[name='disease_right'][value='"+d[i].split("#")[0]+"']").prop('checked', 'checked').trigger("change");
    						}else{
    							$("#diagnoseModal").find("input[name='disease_right'][value='"+d[i]+"']").prop('checked', 'checked').trigger("change");
    						}
						}
    				}
    				if(diseaseIdLeft){
    					var d = diseaseIdLeft.split(",");
    					for (var i = 0; i < d.length; i++) {
    						if(d[i].indexOf("#")!=-1){
    							$("#diagnoseModal").find("input[name='degree_left'][value='"+d[i].split("#")[1]+"']").prop('checked', 'checked');
    							$("#diagnoseModal").find("input[name='disease_left'][value='"+d[i].split("#")[0]+"']").prop('checked', 'checked').trigger("change");
    						}else{
    							$("#diagnoseModal").find("input[name='disease_left'][value='"+d[i]+"']").prop('checked', 'checked').trigger("change");
    						}
    					}
    				}
    	    		$("#diagnoseModal").find("#resultRemark").val(data.resultRemark);
    			}
    		});
    	}
		
		if(row.testPic){
			showDiagnoseImages(escape(row.testPic));
		}
		$("#diagnoseModal").find("#patientId").val(row.patientId);
		$("#diagnoseModal").find("#patientName").val(row.patientName);
		$("#diagnoseModal").find("#testRecordId").val(row.testRecordId);
		
//		$("#diagnoseModal").modal("show");
	}
});
$("#btn_report").on("click",function(){
	var rows = $('#tb_testrecords').bootstrapTable("getSelections");
	if(rows.length==0){
		layer.alert("请选择数据",{icon:2});
	}else{
		$.ajax({
			url: basePath+'/diagnoserecord/getDetail',
			data:{"testRecordId":rows[0].testRecordId},
			type: "POST",
			success: function(data)
			{
				data = JSON.parse(data);
				var status = rows[0].diagnoseState;
				if(status=='0'){
					$("#suggest_info").hide();
					$("#disease_info").hide();
					$("#doctor_info").hide();
					$('#reportModal').find("#diagnose_state").show();
				}else{
					$('#reportModal').find("#diagnose_state").hide();
					$("#suggest_info").show();
					$("#disease_info").show();
					$("#doctor_info").show();
				}
				var imagesJson = JSON.parse(data.testPic);
				var html_left='';
				var html_right='';
				if(imagesJson.length>0){
					for(var i=0;i<imagesJson.length;i++){
						var title = '';
						var isOS = (imagesJson[i].type=="L");
						var isOD = (imagesJson[i].type=="R");
						if(isOS){
							title = "左眼图像";
							html_left += '<li style="background-color: #ccd; float:left;position:relative;right:50%;">'+
							'<img style="width:280px;height:210px;" title="'+title+'" data-original="'+imgRes+imagesJson[i].url+'" src="'+imgRes+imagesJson[i].url+'" class="viewer img-responsive" alt=""></li>';
						}else if(isOD){
							title = "右眼图像";
							html_right += '<li style="background-color: #ccd; float:left;position:relative;right:50%;">'+
							'<img style="width:280px;height:210px;" title="'+title+'" data-original="'+imgRes+imagesJson[i].url+'" src="'+imgRes+imagesJson[i].url+'" class="viewer img-responsive" alt=""></li>';
						}
					}
				}
				var pic_left = $("#reportModal").find("#pictures_left");
				pic_left.empty();
				pic_left.html(html_left);
				var pic_right = $("#reportModal").find("#pictures_right");
				pic_right.empty();
				pic_right.html(html_right);
				$('#reportModal').viewer({
				    url: 'data-original',
				});
				if(data.patientName){
					$("#reportModal").find("#patientName").html(data.patientName);
				}
				var aiResult = data.aiResult;
				if(aiResult){
					$("#ai_tr").show();
					if(aiResult.indexOf(",")!=-1){
						$.ajax({
							url: basePath+'/airesult/getAll',
							type: "POST",
							success: function(data)
							{
								data = JSON.parse(data);
								for(var i=0;i<data.length;i++){
									var dd = data[i];
									var right = aiResult.replace("右眼","").split(",")[0].split(":")[0];
									var left = aiResult.replace("左眼","").split(",")[1].split(":")[0]
									if(dd.aiCode==right){
										$("#right_ai").html("AI诊断结果："+dd.description);
									}
									if(dd.aiCode==left){
										$("#left_ai").html("AI诊断结果："+dd.description);
									}
								}
							}
						});
						
					}else{
						$("#right_ai").html(aiResult);
						$("#left_ai").html(aiResult);
					}
				}else{
					$("#ai_tr").hide();
				}
				if(data.cardNo){
					$("#reportModal").find("#idCard").html(data.cardNo.substr(0,14)+'****');
				}
				if(data.age){
					$("#reportModal").find("#age").html(data.age);
				}
				if(data.gender){
					$("#reportModal").find("#gender").html(data.gender==0?'男':'女');
				}
				if(data.hospitalName){
					$("#reportModal").find("#hospitalName").html(data.hospitalName);
				}
				if(data.testTime){
					$("#reportModal").find("#testTime").html(data.testTime);
				}
				if(data.doctorName){
					$("#reportModal").find("#doctorName").html(data.doctorName);
				}
				if(data.disease){
					$("#reportModal").find("#disease").html(data.disease);
				}
				if(data.resultRemark){
					$("#reportModal").find("#resultRemark").html(data.resultRemark);
				}
				if(data.doctorName){
					$("#reportModal").find("#doctorName").html(data.doctorName);
				}
				if(data.url){
					$("#reportModal").find("#sign").attr("src",imgRes+data.url);
				}
				
				var diseaseIdLeft = data.diseaseIdLeft;
				var diseaseIdRight = data.diseaseIdRight;
				var suggest = data.suggest;
				$("#suggestId").val(suggest);
				if(diseaseIdLeft){
					var d = diseaseIdLeft.split(",");
					for (var i = 0; i < d.length; i++) {
						if(d[i].indexOf("#")!=-1){
							$("input[name='disease'][value='"+d[i].split("#")[0]+"']").prop('checked', 'checked').trigger("change");
//							$("input[name='degree_right'][value='"+d[i].split("#")[1]+"']").prop('checked', 'checked');
							$("input[name='degree_left'][value='"+d[i].split("#")[1]+"']").prop('checked', 'checked');
						}
					}
				}
				if(diseaseIdRight){
					var d = diseaseIdRight.split(",");
					for (var i = 0; i < d.length; i++) {
						if(d[i].indexOf("#")!=-1){
							$("input[name='disease'][value='"+d[i].split("#")[0]+"']").prop('checked', 'checked').trigger("change");
//							$("input[name='degree_right'][value='"+d[i].split("#")[1]+"']").prop('checked', 'checked');
							$("input[name='degree_right'][value='"+d[i].split("#")[1]+"']").prop('checked', 'checked');
						}
					}
				}
				
				if(suggest){
					var d = suggest.split(",");
					for (var i = 0; i < d.length; i++) {
						$("input[name='diseaseTypeSuggest'][value='"+d[i]+"']").prop('checked', 'checked');
					}
				}
				$("#reportModal").modal("show");
			}
		});
	}
});
$().ready(function () {
    $('.modal.printable').on('shown.bs.modal', function () {
        $('.modal-dialog', this).addClass('focused');
        $('body').addClass('modalprinter');

        if ($(this).hasClass('autoprint')) {
            window.print();
        }
    }).on('hidden.bs.modal', function () {
        $('.modal-dialog', this).removeClass('focused');
        $('body').removeClass('modalprinter');
    });
});
$("#btn_print").click(function(event) {
	$("#reportModal").find("#degree_right").removeAttr("disabled");
	$("#reportModal").find("#degree_left").removeAttr("disabled");
	$("#reportModal").find("#diseaseTypeSuggest").removeAttr("disabled");
	  window.print();
	  $("#reportModal").find("#degree_right").attr("disabled","disabled");
	  $("#reportModal").find("#degree_left").attr("disabled","disabled");
	  $("#reportModal").find("#diseaseTypeSuggest").attr("disabled","disabled");
});

var deviceTableInit = function () {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.Init = function () {
        $('#tb_testrecords').bootstrapTable({
            url: basePath+'/testrecord/getData',         // 请求后台的URL（*）
            method: 'post',                      // 请求方式（*）
            contentType:"application/x-www-form-urlencoded; charset=UTF-8",
//            toolbar: '#toolbar',                // 工具按钮用哪个容器
            striped: true,                      // 是否显示行间隔色
            cache: false,                       // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   // 是否显示分页（*）
            queryParams: oTableInit.queryParams,// 传递参数（*）
            sidePagination: "server",           // 分页方式：client客户端分页，server服务端分页（*）
            pageSize: 10,                       // 每页的记录行数（*）
            pageList: [10, 25, 50, 100],        // 可供选择的每页的行数（*）
            showColumns: true,                  // 是否显示所有的列
            showRefresh: true,                  // 是否显示刷新按钮
            minimumCountColumns: 2,             // 最少允许的列数
            clickToSelect: true,                // 是否启用点击选中行
            singleSelect:true,
            height: 527,                        // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
            toolbar: '#toolbar',                // 工具按钮用哪个容器
            columns: [
            {
                checkbox: true
            },/** {
            	field:'testRecordId',
            	width:200,
            	align:'center',
            	title: '检查编号'
            },**/{
                field: 'patientName',
            	align:'center',
            	width:200,
                title: '患者姓名'
            },{
            	field:'hospitalName',
            	width:200,
            	align:'center',
            	title: '医院名称'
            }/**,{
	        	field:'deviceId',
	        	width:200,
	        	align:'center',
	        	title: '设备Id'
	        }**/,{
	        	field:'testTime',
	        	width:200,
	        	align:'center',
	        	title: '检查时间'
	        }/**,{
	        	field:'aiResult',
	        	width:200,
	        	align:'center',
	        	title: 'AI检查结果'
	        }**/,{
	        	field:'diagnoseState',
	        	width:200,
	        	align:'center',
	        	title: '专家诊断状态',
	        	formatter:function(val,row){
	        		if(val=='0'){
	        			return "待诊断";
	        		}else if(val=='1'){
	        			return "待复核";
	        		}else if(val=='2'){
	        			return "已诊断";
	        		}else{
	        			return '-';
	        		}
	        	}
	        }/**,{
	        	field:'testPic',
	        	width:100,
	        	align:'center',
	        	title: '图像',
	        	formatter:function(val,row){
	        		return '<button id="check" class="btn btn-block btn-default btn-sm" onclick="showImages(\''+escape(val)+'\')">'+
		            	'<i class="glyphicon"></i> 查看图像'+
			        	'</button>';
	        	}
	        }**/]
        });
    };

    // 得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            rows: params.limit,   // 页面大小
            page: params.offset/params.limit,  // 页码
            patientName:$.trim($("#patientName").val()),
            hospitalName:$.trim($("#hospitalName").val()),
            diagnoseState:$.trim($("#diagnoseState").val())
        };
        return temp;
    };
    return oTableInit;
};
function showImages(images){
	var str = unescape(images);
	var imagesJson = JSON.parse(str);
	var html='';
	if(imagesJson.length>0){
		for(var i=0;i<imagesJson.length;i++){
			var title = '';
			var isOS = (imagesJson[i].url.indexOf('OS')!=-1);
			var isOD = (imagesJson[i].url.indexOf('OD')!=-1);
			if(isOS){
				title = "左眼图像";
			}else if(isOD){
				title = "右眼图像";
			}
			html += '<li style="float: left;background-color: #ccd; margin: 0px 20px 10px 0px;">'+
				'<img style="width:200px;height:180px;" title="'+title+'" data-original="'+imgRes+imagesJson[i].url+'" src="'+imgRes+imagesJson[i].url+'" class="viewer img-responsive" alt=""></li>';
		}
	}
	var pic = $(".pictures");
	pic.empty();
	pic.html(html);
	$('#imageModal').viewer({
	    url: 'data-original',
	});
	$("#imageModal").modal("show");
}
function showDiagnoseImages(images){
	var str = unescape(images);
	var imagesJson = JSON.parse(str);
	var html_left='';
	var html_right='';
	if(imagesJson.length>0){
		for(var i=0;i<imagesJson.length;i++){
			var title = '';
			var isOS = (imagesJson[i].type=="L");
			var isOD = (imagesJson[i].type=="R");
			if(isOS){
				title = "左眼图像";
				html_left += '<li style="background-color: #ccd; float:left;position:relative;right:50%;">'+
				'<img style="width:280px;height:210px;" title="'+title+'" data-original="'+imgRes+imagesJson[i].url+'" src="'+imgRes+imagesJson[i].url+'" class="viewer img-responsive" alt=""></li>';
			}else if(isOD){
				title = "右眼图像";
				html_right += '<li style="background-color: #ccd; float:left;position:relative;right:50%;">'+
				'<img style="width:280px;height:210px;" title="'+title+'" data-original="'+imgRes+imagesJson[i].url+'" src="'+imgRes+imagesJson[i].url+'" class="viewer img-responsive" alt=""></li>';
			}
		}
	}
	var pic_left = $("#diagnoseModal").find("#pictures_left");
	pic_left.empty();
	pic_left.html(html_left);
	var pic_right = $("#diagnoseModal").find("#pictures_right");
	pic_right.empty();
	pic_right.html(html_right);
	$('#diagnoseModal').viewer({
	    url: 'data-original',
	});
	$("#diagnoseModal").modal("show");
}