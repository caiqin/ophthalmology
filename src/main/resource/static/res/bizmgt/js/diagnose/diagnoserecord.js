/** *医生管理** */
$('#btn_query').on("click",function(){
		var query ={}
		$('#tb_diagnoserecords').bootstrapTable('refresh',{query:query});
	});
document.onkeydown=function(event){
    e = event ? event : (window.event ? window.event : null);
    if(e.keyCode==13){
    	$('#btn_query').click();
    }
}
$('#btn_export').on("click",function(){
	window.location.href=basePath+'/diagnoserecord/export?'+$("#formSearch").serialize();
});
$('#startTime').datetimepicker({
    language:  'zh-CN',
    format: 'yyyy-mm-dd hh:ii:ss',
    weekStart: 1,
    //container:'.modal-dialog',
    todayBtn:  1,
	autoclose: 1,
	minuteStep:30,
	todayHighlight: 1,
	startView: 2,
	minView: 0,
	forceParse: 0
}).on("click",function(ev){
}).on("hide",function(){
	var s = $('#startTime').val();
	if(s){
		$('#startTime').val(s.substr(0,16)+":00");
	}
	return false;
});
$('#endTime').datetimepicker({
	language:  'zh-CN',
	format: 'yyyy-mm-dd hh:ii:ss',
	weekStart: 1,
	//container:'.modal-dialog',
	todayBtn:  1,
	autoclose: 1,
	minuteStep:30,
	todayHighlight: 1,
	startView: 2,
	minView: 0,
	forceParse: 0
}).on("click",function(ev){
}).on("hide",function(){
	var s = $('#endTime').val();
	if(s){
		$('#endTime').val(s.substr(0,16)+":00");
	}
	return false;
});

$("#formSearch").on("submit",function(){
	return false;
});
$("#btn_ai_report").on("click",function(){
	var rows = $('#tb_diagnoserecords').bootstrapTable("getSelections");
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
$("#btn_report").on("click",function(){
	var rows = $('#tb_diagnoserecords').bootstrapTable("getSelections");
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
				var pic_left = $("#pictures_left");
				pic_left.empty();
				pic_left.html(html_left);
				var pic_right = $("#pictures_right");
				pic_right.empty();
				pic_right.html(html_right);
				$('#detailModal').viewer({
				    url: 'data-original',
				});
				if(data.patientName){
					$("#reportModal").find("#patientName").html(data.patientName);
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
//							$("input[name='disease'][value='"+d[i].split("#")[0]+"']").prop('checked', 'checked').trigger("change");
//							$("input[name='degree_right'][value='"+d[i].split("#")[1]+"']").prop('checked', 'checked');
							$("input[name='degree_left'][value='"+d[i].split("#")[1]+"']").prop('checked', 'checked');
						}
					}
				}
				if(diseaseIdRight){
					var d = diseaseIdRight.split(",");
					for (var i = 0; i < d.length; i++) {
						if(d[i].indexOf("#")!=-1){
//							$("input[name='disease'][value='"+d[i].split("#")[0]+"']").prop('checked', 'checked').trigger("change");
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
$("#reportModal").find("#btn_print").click(function(event) {
	$("#reportModal").find("input[type='radio']").removeAttr("disabled");
	$("#reportModal").find("input[type='checkbox']").removeAttr("disabled");
	window.print();
	$("#reportModal").find("input[type='radio']").attr("disabled","disabled");
	$("#reportModal").find("input[type='checkbox']").attr("disabled","disabled");
});
$("#AiReportModal").find("#btn_print").click(function(event) {
	$("#right_ai").find("#degree_right").removeAttr("disabled");
	$("#left_ai").find("#degree_left").removeAttr("disabled");
	window.print();
	$("#right_ai").find("#degree_right").attr("disabled","disabled");
	$("#left_ai").find("#degree_left").attr("disabled","disabled");
});
$(function(){
	// 1.初始化Table
    var deviceTable = new deviceTableInit();
    deviceTable.Init();
});



var deviceTableInit = function () {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.Init = function () {
        $('#tb_diagnoserecords').bootstrapTable({
            url: basePath+'/diagnoserecord/getData',         // 请求后台的URL（*）
            method: 'post',                      // 请求方式（*）
            contentType:"application/x-www-form-urlencoded; charset=UTF-8",
            toolbar: '#toolbar',                // 工具按钮用哪个容器
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
            columns: [
            {
                checkbox: true
            }, {
                field: 'patientName',
            	align:'center',
            	width:200,
                title: '患者姓名'
            },{
            	field:'doctorName',
            	width:200,
            	align:'center',
            	title: '诊断医生姓名'
            }/**,{
	        	field:'disease',
	        	width:200,
	        	align:'center',
	        	title: '诊断结果'
	        },{
	        	field:'resultRemark',
	        	width:200,
	        	align:'center',
	        	title: '医生建议'
	        }**/,{
	        	field:'diagnoseTime',
	        	width:200,
	        	align:'center',
	        	title: '诊断时间'
	        }]
        });
    };

    // 得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            rows: params.limit,   // 页面大小
            page: params.offset/params.limit,  // 页码
            patientName:$("#patientName").val(),
            doctorName:$("#doctorName").val(),
            startTime:$("#startTime").val(),
            endTime:$("#endTime").val()
        };
        return temp;
    };
    return oTableInit;
};
