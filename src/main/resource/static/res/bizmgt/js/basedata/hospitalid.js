/** *患者管理** */
$('#btn_query').on("click",function(){
		var query ={}
		$('#tb_hospitalids').bootstrapTable('refresh',{query:query});
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

$('#addModal').on('shown.bs.modal', function (e) { 
    // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零 
    $(this).css('display', 'block'); 
    var modalHeight=$(window).height() / 2 - $('#addModal .modal-dialog').height() / 2; 
    $(this).find('.modal-dialog').css({ 
      'margin-top': modalHeight 
    }); 
  }); 
$('#addModal').on('hide.bs.modal', function (e) { 
	var html='<input type="file" name="importFile" id="importFile" multiple  data-show-preview="false"  data-min-file-count="1"/>';
	$("#upload_file").html();
	$("#upload_file").html(html);
	loadFileInput();
}); 

function loadFileInput(){
	$("#importFile").fileinput({
		language : 'zh',//设置文中文
		uploadUrl : basePath+"/hospitalid/import",//图片上传的url，我这里对应的是后台struts配置好的的action方法
		showCaption : true,//显示标题
		showRemove : true, //显示移除按钮
		allowedFileExtensions: ['xls', 'xlsx'],//允许上传文件的类型
		uploadAsync : true,//默认异步上传
		showPreview : false,//是否显示预览
		textEncoding : "UTF-8",//文本编码
		autoReplaceBoolean : false,//选择图片时不清空原图片
	}).on('fileuploaded', function(event, data, previewId, index) {//异步上传成功结果处理
		if(data.response.result){
			layer.alert("导入成功",{icon:1});
			$('#btn_query').click();
			$('#addModal').modal('hide');
		}else{
			layer.alert("导入失败",{icon:2});
		}
	});
}

$(function(){
	loadFileInput();
	/** ******详情按钮点击事件******* */
	$('#import').click(function(){
		$('#addModal').modal('show');
	});
	$('#delete').click(function(){
		var rows = $('#tb_hospitalids').bootstrapTable("getSelections");
		if(rows.length==0){
			layer.alert("请选择数据",{icon:2});
		}else{
			var ids = [];
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i].id);
			}
			layer.confirm('确定删除?', {icon: 3, title:'提示'}, function(index){
				$.ajax({
					url: basePath+'/hospitalid/delete',
					data: {"ids":ids.join(',')},
					type: "POST",
					success: function(data)
					{
						data = JSON.parse(data);
						if(data.result){
							layer.alert(data.message,{icon:1});
							$('#btn_query').click();
						}
					}
				});
			});
		}
	});
	// 1.初始化Table
    var aiResultTable = new aiResultTableInit();
    aiResultTable.Init();
});

var aiResultTableInit = function () {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.Init = function () {
        $('#tb_hospitalids').bootstrapTable({
            url: basePath+'/hospitalid/getData',         // 请求后台的URL（*）
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
            singleSelect:false,
            height: 527,                        // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
            columns: [
            {
                checkbox: true
            },{
                field: 'hospitalId',
            	align:'center',
                title: '医院编号'
            },{
                field: 'startTime',
            	align:'center',
                title: '有效开始时间'
            },{
                field: 'endTime',
            	align:'center',
                title: '有效结束时间'
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
