
	$('#startTime').datetimepicker({
        language:  'zh-CN',
        format: 'yyyy-mm-dd hh:ii:ss',
        weekStart: 1,
        //container:'.modal-dialog',
        todayBtn:  1,
		autoclose: 1,
		minuteStep:5,
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
		minuteStep:5,
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
$(function(){
	
	// 1.初始化Table
    var deviceTable = new deviceTableInit();
    deviceTable.Init();
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
            }, {
            	field:'testRecordId',
            	width:200,
            	align:'center',
            	title: '检查编号'
            },{
                field: 'patientName',
            	align:'center',
            	width:200,
                title: '患者姓名'
            },{
            	field:'hospitalName',
            	width:200,
            	align:'center',
            	title: '医院名称'
            },{
	        	field:'testTime',
	        	width:200,
	        	align:'center',
	        	title: '检查时间'
	        },{
	        	field:'aiResult',
	        	width:200,
	        	align:'center',
	        	title: 'AI检查结果'
	        },{
	        	field:'diagnoseState',
	        	width:200,
	        	align:'center',
	        	title: '诊断状态',
	        	formatter:function(val,row){
	        		if(val=='0'){
	        			return "待诊断";
	        		}else if(val=='1'){
	        			return "已诊断";
	        		}else{
	        			return "-";
	        		}
	        	}
	        }]
        });
    };

    // 得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            rows: params.limit,   // 页面大小
            page: params.offset/params.limit,  // 页码
            patientName:$("#patientName").val(),
            hospitalName:$("#hospitalName").val(),
            startTime:$("#startTime").val(),
            endTime:$("#endTime").val()
        };
        return temp;
    };
    return oTableInit;
};
