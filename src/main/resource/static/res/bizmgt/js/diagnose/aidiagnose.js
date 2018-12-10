/** ai诊断管理** */
$('#btn_query').on("click",function(){
		var query ={}
		$('#tb_ai_diagnose').bootstrapTable('refresh',{query:query});
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
var aiResultData = [];
$(function(){
	$.ajax({
		url: basePath+'/airesult/getAll',
		type: "POST",
		success: function(data)
		{
			aiResultData = JSON.parse(data);
		}
	});
	
	
	
	// 1.初始化Table
    var deviceTable = new deviceTableInit();
    deviceTable.Init();
});

$("#btn_report").on("click",function(){
	var rows = $('#tb_ai_diagnose').bootstrapTable("getSelections");
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
										$("#right_ai").html(dd.description);
									}
									if(dd.aiCode==left){
										$("#left_ai").html(dd.description);
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
	  window.print();
});
var deviceTableInit = function () {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.Init = function () {
        $('#tb_ai_diagnose').bootstrapTable({
            url: basePath+'/aidiagnose/getData',         // 请求后台的URL（*）
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
            toolbar: '#toolbar',                // 工具按钮用哪个容器
            columns: [
            {
                checkbox: true
            }, 
            {
                field: 'patientName',
            	align:'center',
            	width:80,
                title: '患者姓名'
            },{
            	field:'hospitalName',
            	width:200,
            	align:'center',
            	title: '医院名称'
            },{
	        	field:'testTime',
	        	width:100,
	        	align:'center',
	        	title: '检查时间'
	        }/**,{
	        	field:'aiResult',
	        	width:250,
	        	align:'center',
	        	title: 'AI检查结果',
	        	formatter:formatterAi
	        }**/]
        });
    };

    // 得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            rows: params.limit,   // 页面大小
            page: params.offset/params.limit,  // 页码
            patientName:$("#patientName").val(),
            hospitalName:$("#hospitalName").val()
        };
        return temp;
    };
    return oTableInit;
};

function formatterAi(aiResult){
	if(aiResult.indexOf(",")!=-1){
		var resultL = '';
		var resultR = '';
		for(var i=0;i<aiResultData.length;i++){
			var dd = aiResultData[i];
			var right = aiResult.replace("右眼","").split(",")[0].split(":")[0];
			var left = aiResult.replace("左眼","").split(",")[1].split(":")[0]
			if(dd.aiCode==right){
				resultR = "右眼:"+dd.description;
			}
			if(dd.aiCode==left){
				resultL = "左眼:"+dd.description;
			}
		}
		return resultR+','+resultL;
	}else{
		return aiResult
	}
}
