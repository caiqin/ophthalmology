$(function(){
	$('#btn_query').on("click",function(){
		var query ={}
		query.resName=$("#resName").val();
		$('#tb_resources').bootstrapTable('refresh',{query:query});
	});
	document.onkeydown=function(event){
	    e = event ? event : (window.event ? window.event : null);
	    if(e.keyCode==13){
	    	$('#btn_query').click();
	    }
	}
	$('#btn_add').click(function(){
	    window.location.href=basePath+"/sys/system/resource/toAddResource";
	});
	
	$('#btn_update').click(function(){
		var rows = $('#tb_resources').bootstrapTable("getSelections");
		if(rows.length==0){
			layer.alert("请选择一条数据",{icon:2});
		}else{
			window.location.href=basePath+"/sys/system/resource/toUpdateResource?resourceId="+rows[0].id;
		}
	});
	
	$('#btn_delete').click(function(){
		var rows = $('#tb_resources').bootstrapTable("getSelections");
		if(rows.length==0){
			layer.alert("请选择一条数据",{icon:2});
		}else{
			layer.confirm('确定删除该资源?', {icon: 3, title:'提示'}, function(index){
				var url = basePath+'/sys/system/resource/delResource'
				var params='id='+rows[0].id;
				$.ajax({ 
					type: "POST",
				  	url: url,
				  	data: params,
					success: function(msg){
				    	var message=eval("("+msg+")");
				    	if(message.result){
				    	    layer.alert("删除资源成功！",{icon:1});
				    	    $('#tb_resources').bootstrapTable('refresh');
				    	}else{
				    		layer.alert("删除资源失败！",{icon:2});
				    	}
					}
			  });
			});
		}
	});
	
	
	
	// 1.初始化Table
    var resourceTable = new resourceTableInit();
    resourceTable.init();
});



var resourceTableInit = function () {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.init = function () {
        $('#tb_resources').bootstrapTable({
            url: basePath+'/sys/system/resource/queryResourceList',         // 请求后台的URL（*）
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
            },{
                field: 'resName',
            	align:'center',
                title: '资源名'
            }, {
                field: 'resUrl',
            	align:'center',
                title: 'URL'
            }, {
                field: 'level',
            	align:'center',
                title: '层级'
            }, {
                field: 'resType',
            	align:'center',
                title: '资源类型'
            },{
            	field:'createTime',
            	width:200,
            	align:'center',
            	title: '创建时间'
            }]
        });
    };

    // 得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            rows: params.limit,   // 页面大小
            page: params.offset/params.limit  // 页码
        };
        return temp;
    };
    return oTableInit;
};