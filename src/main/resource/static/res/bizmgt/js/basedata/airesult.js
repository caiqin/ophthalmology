/** *患者管理** */
$('#btn_query').on("click",function(){
		var query ={}
		$('#tb_airesults').bootstrapTable('refresh',{query:query});
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
	$("#addAiResult").data('bootstrapValidator').resetForm(true);
});
$(function(){
	$('#addAiResult').bootstrapValidator({
		message: '输入的值格式不对',
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			aiCode: {
				aiCode: '输入的值格式不对',
				validators: {
					notEmpty: {
						message: 'AI编号不能为空'
					}
				}
			},
			description: {
				message: '输入的值格式不对',
				validators: {
					notEmpty: {
						message: '描述不能为空'
					}
				}
			}
		}
	})
	.on('success.form.bv', function(e) {
		e.preventDefault();
		var $form = $(e.target);
		var bv = $form.data('bootstrapValidator');
		var data = $('#addAiResult').serialize();
		$.ajax({
			url: basePath+'/airesult/add',
			data: data,
			type: "POST",
			beforeSend: function()
			{  
				$("#addAiResult").find('#btn_submit').addClass("disabled");
				$("#addAiResult").find('#btn_submit').prop('disabled', true);
			},
			success: function(data)
			{
				$('#addModal').modal('hide');
				$('#addAiResult')[0].reset();
				$("#addAiResult").find('#btn_submit').removeClass("disabled");
				$("#addAiResult").find('#btn_submit').prop('disabled', false);
				$('#tb_airesults').bootstrapTable('refresh');
				layer.alert("新增成功",{icon:1});
			}
		});
	});
	$('#updateAiResult').bootstrapValidator({
		message: '输入的值格式不对',
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			aiCode: {
				aiCode: '输入的值格式不对',
				validators: {
					notEmpty: {
						message: 'AI编号不能为空'
					}
				}
			},
			description: {
				message: '输入的值格式不对',
				validators: {
					notEmpty: {
						message: '描述不能为空'
					}
				}
			}
		}
	})
	.on('success.form.bv', function(e) {
		e.preventDefault();
		var $form = $(e.target);
		var bv = $form.data('bootstrapValidator');
		var data = $('#updateAiResult').serialize();
		$.ajax({
			url: basePath+'/airesult/update',
			data: data,
			type: "POST",
			beforeSend: function()
			{  
				$('#updateAiResult').find('#btn_submit').addClass("disabled");
				$('#updateAiResult').find('#btn_submit').prop('disabled', true);
			},
			success: function(data)
			{
				$('#updateModal').modal('hide');
				$('#updateAiResult')[0].reset();
				$('#updateAiResult').find('#btn_submit').removeClass("disabled");
				$('#updateAiResult').find('#btn_submit').prop('disabled', false);
				$('#tb_airesults').bootstrapTable('refresh');
				layer.alert("修改成功",{icon:1});
			}
		});
	});
	$('#btn_submit_update').click(function () {
      	$('#updateAiResult').submit();
    });

	/** ******详情按钮点击事件******* */
	$('#add').click(function(){
		$('#addModal').modal('show');
	});
	$('#delete').click(function(){
		var rows = $('#tb_airesults').bootstrapTable("getSelections");
		if(rows.length==0){
			layer.alert("请选择数据",{icon:2});
		}else{
			var row = rows[0];
			layer.confirm('确定删除?', {icon: 3, title:'提示'}, function(index){
				$.ajax({
					url: basePath+'/airesult/delete',
					data: {"id":row.id},
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
	/** ******修改按钮点击事件******* */
	$('#update').click(function(){
		var rows = $('#tb_airesults').bootstrapTable("getSelections");
		if(rows.length==0){
			layer.alert("请选择要修改的数据",{icon:2});
		}else{
			var row = rows[0];
			$('#updateModal').modal('show');
			$('#updateAiResult').find("#id").val(row.id);
			$('#updateAiResult').find("#aiCode").val(row.aiCode);
			$('#updateAiResult').find('#description').val(row.description);		
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
        $('#tb_airesults').bootstrapTable({
            url: basePath+'/airesult/getData',         // 请求后台的URL（*）
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
                field: 'aiCode',
            	align:'center',
                title: 'AI编号'
            },{
                field: 'description',
            	align:'center',
                title: '描述'
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
