
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

	$("#formSearch").find("#hospitalName").on("click",function(){
		$("#showHospitalModal").modal("show");
	});
/** *医生管理** */
$('#btn_query').on("click",function(){
		var query ={}
		$('#tb_testrecords').bootstrapTable('refresh',{query:query});
	});
$('#btn_export').on("click",function(){
	window.location.href=basePath+'/testrecordreport/export?'+$("#formSearch").serialize();
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
$("#formSearch").find("#btn_reset").on("click",function(){
	$("#formSearch").find("#aiResult").val('').trigger('change');
	$("#formSearch").find('#diagnoseResult').val('').trigger('change');
	$("#formSearch")[0].reset();
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
	$.ajax({
		url: basePath+'/hospital/getTree',
		type: "POST",
		success: function(data)
		{
			var hospitalList = JSON.parse(data);
			$('#tree').treeview({
				data: hospitalList,
				showBorder:false,
				color:'#3194d0',
				onNodeSelected: function(event, data) {
				    var level = data.level;
				   $("#formSearch").find("#hospitalName").val(data.text);
				   $("#formSearch").find("#hospitalId").val(data.hospitalId);
				}
			});
		}
	});
	// 1.初始化Table
    var deviceTable = new deviceTableInit();
    deviceTable.Init();
});



var deviceTableInit = function () {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.Init = function () {
        $('#tb_testrecords').bootstrapTable({
            url: basePath+'/testrecordreport/getDiagnoseData',         // 请求后台的URL（*）
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
            },{
                field: 'patientName',
            	align:'center',
            	width:70,
                title: '患者姓名'
            },{
            	field:'hospitalName',
            	width:200,
            	align:'center',
            	title: '医院名称'
            },{
	        	field:'doctorName',
	        	width:70,
	        	align:'center',
	        	title: '医生'
	        },{
	        	field:'diagnoseTime',
	        	width:120,
	        	align:'center',
	        	title: '诊断时间'
	        },{
	        	field:'aiResult',
	        	width:200,
	        	align:'center',
	        	title: 'AI诊断结果',
	        	formatter:formatterAi
	        },{
	        	field:'diseaseRight',
	        	width:200,
	        	align:'center',
	        	title: '专家检查结果',
	        	formatter:formatterDisease
	        }]
        });
    };

    // 得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            rows: params.limit,   // 页面大小
            page: params.offset/params.limit,  // 页码
            doctorName:$("#doctorName").val(),
            hospitalName:$("#hospitalName").val(),
            hospitalId:$("#hospitalId").val(),
            aiResult:$("#aiResult").val(),
            diagnoseResult:$("#diagnoseResult").val(),
            startTime:$("#startTime").val(),
            endTime:$("#endTime").val()
        };
        return temp;
    };
    return oTableInit;
};

function formatterDisease  (val, row){
	var diseaseLeft = row.diseaseLeft;
	var diseaseRight = row.diseaseRight;
	if(!diseaseLeft&&!diseaseRight){
		return "未诊断";
	}else{
		var l='左眼:';
		var r='右眼:';
		if(!diseaseLeft){
			r += '';
		}else
		if(diseaseLeft.endsWith("#0")){
			l += '无明显糖网病变';
		}else if(diseaseLeft.endsWith("#1")){
			l += '轻度非增殖性糖网病变';
		}else if(diseaseLeft.endsWith("#2")){
			l += '中度非增殖性糖网病变';
		}else if(diseaseLeft.endsWith("#3")){
			l += '重度非增殖性糖网病变';
		}else if(diseaseLeft.endsWith("#4")){
			l += '增殖性糖网病变';
		}
		if(!diseaseRight){
			r += '';
		}else
		if(diseaseRight.endsWith("#0")){
			r += '无明显糖网病变';
		}else if(diseaseRight.endsWith("#1")){
			r += '轻度非增殖性糖网病变';
		}else if(diseaseRight.endsWith("#2")){
			r += '中度非增殖性糖网病变';
		}else if(diseaseRight.endsWith("#3")){
			r += '重度非增殖性糖网病变';
		}else if(diseaseRight.endsWith("#4")){
			r += '增殖性糖网病变';
		}
		return r+","+l
		
	}
}
function formatterAi(val){
	var aiResult = val;
	if(!val){
		return "未诊断";
	}
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
