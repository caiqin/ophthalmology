/** *医院管理** */
$('#btn_query').on("click",function(){
		var query ={}
		query.hospitalName=$("#hospitalName").val();
		$('#tb_hospitals').bootstrapTable('refresh',{query:query});
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
$("#btn_add").on("click",function(){
	location.href = basePath+"/hospital/toAdd";
});
$("#btn_show_qr").on("click",function(){
	
	var rows = $('#tb_hospitals').bootstrapTable("getSelections");
	if(rows.length==0){
		layer.alert("请选择数据",{icon:2});
	}else{
		var row = rows[0];
		$("#img_qr").attr("src",imgRes+row.url);
		$("#showModal").modal("show");
	}
});
$("#btn_update").on("click",function(){
	var rows = $('#tb_hospitals').bootstrapTable("getSelections");
	if(rows.length==0){
		layer.alert("请选择要修改的数据",{icon:2});
	}else{
		var row = rows[0];
		location.href = basePath+"/hospital/toEdit?hospitalId="+row.hospitalId;
	}
});
$(function(){
	$('#addHospital').bootstrapValidator({
        message: '输入的值格式不对',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	hospitalName: {
                message: '输入的值格式不对',
                validators: {
                    notEmpty: {
                        message: '医院名称不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 40,
                        message: '医院名称字符过长，最多20个字符'
                    }
                }
            },
            hospitalAddress: {
                validators: {
                	notEmpty: {
                        message: '医院地址不能为空'
                    },
                	stringLength: {
                		min: 1,
                        max: 60,
                        message: '医院地址不能超过60个字符'
                    }
                }
            },
            hospitalIntroduce: {
            	validators: {
            		notEmpty: {
                        message: '医院介绍不能为空'
                    },
                    stringLength: {
                		min: 6,
                        max: 128,
                        message: '医院介绍长度必须在6到128之间'
                    }
            	}
            }
        }
    })
    .on('success.form.bv', function(e) {
        e.preventDefault();
        var $form = $(e.target);
        var bv = $form.data('bootstrapValidator');
        $.ajax({
			url: basePath+'/hospital/add',
			data: $('#addHospital').serialize(),
			type: "POST",
			beforeSend: function()
			{  
				$('#addModal').find('#btn_submit').addClass("disabled");
				$('#addModal').find('#btn_submit').prop('disabled', true);
			},
			success: function(data)
			{
				var data = JSON.parse(data);
				if(data.result){
					$('#addModal').modal('hide');
					$('#addHospital')[0].reset();
					$("#addHospital").data("bootstrapValidator").resetForm();
					$('#addModal').find('#btn_submit').removeClass("disabled");
					$('#addModal').find('#btn_submit').prop('disabled', false);
					$('#tb_hospitals').bootstrapTable('refresh');
					layer.alert("添加成功",{icon:1});
				}else{
					layer.alert(data.message,{icon:2});
				}
			}
		});
    });
	
	$('#btn_create').click(function () {
		var rows = $('#tb_hospitals').bootstrapTable("getSelections");
		if(rows.length==0){
			layer.alert("请选择数据",{icon:2});
		}else{
			var row = rows[0];
			$.ajax({
				url: basePath+'/hospital/createqrcode',
				data: {"hospitalId":row.hospitalId},
				type: "POST",
				success: function(data)
				{
					var data = JSON.parse(data);
					if(data.result){
						layer.alert("生成二维码成功",{icon:1});
					}else{
						layer.alert("生成二维码失败",{icon:2});
					}
					$('#btn_query').click();
				}
			});
		}
	});
	$('#btn_enable').click(function () {
		var rows = $('#tb_hospitals').bootstrapTable("getSelections");
		if(rows.length==0){
			layer.alert("请选择数据",{icon:2});
		}else{
			var row = rows[0];
			$.ajax({
				url: basePath+'/hospital/enableQr',
				data: {"hospitalId":row.hospitalId},
				type: "POST",
				success: function(data)
				{
					var data = JSON.parse(data);
					if(data.result){
						layer.alert("操作成功",{icon:1});
					}else{
						layer.alert("操作失败",{icon:2});
					}
					$('#btn_query').click();
				}
			});
		}
	});
	
	$('#editHospital').bootstrapValidator({
        message: '输入的值格式不对',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	hospitalName: {
                message: '输入的值格式不对',
                validators: {
                    notEmpty: {
                        message: '医院名称不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 40,
                        message: '医院名称字符过长，最多20个字符'
                    }
                }
            },
            hospitalAddress: {
                validators: {
                	notEmpty: {
                        message: '医院地址不能为空'
                    },
                	stringLength: {
                		min: 1,
                        max: 60,
                        message: '医院地址不能超过60个字符'
                    }
                }
            },
            hospitalIntroduce: {
            	validators: {
            		notEmpty: {
                        message: '医院介绍不能为空'
                    },
                    stringLength: {
                		min: 6,
                        max: 128,
                        message: '医院介绍长度必须在6到128之间'
                    }
            	}
            }
        }
    })
    .on('success.form.bv', function(e) {
		e.preventDefault();
		var $form = $(e.target);
		var bv = $form.data('bootstrapValidator');
		$.ajax({
			url: basePath+'/hospital/update',
			data: $('#editHospital').serialize(),
			type: "POST",
			beforeSend: function()
			{  
				$('#btn_submit_update').addClass("disabled");
				$('#btn_submit_update').prop('disabled', true);
			},
			success: function(data)
			{
				$('#updateModal').modal('hide');
				$('#editHospital')[0].reset();
				$('#btn_submit_update').removeClass("disabled");
				$('#btn_submit_update').prop('disabled', false);
				$('#tb_hospitals').bootstrapTable('refresh');
				layer.alert("修改成功",{icon:1});
			}
		});
	});
	
	$('#btn_submit_update').click(function () {
      	$('#editHospital').submit();
    });

	

	// 1.初始化Table
    var ht = new hospitalTable();
    ht.Init();
});

var hospitalTable = function () {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.Init = function () {
        $('#tb_hospitals').bootstrapTable({
            url: basePath+'/hospital/getData',         // 请求后台的URL（*）
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
            onCheck:function(row){
            	var status = row.url;
            	if(status){
            		$('#btn_enable').show();
            	}else{
            		$('#btn_enable').hide();
            	} 
            	if(row.status=='0'){//禁用
            		$('#btn_enable').html("<i class='fa fa-lock'></i> 禁用");
            	}else{
            		$('#btn_enable').html("<i class='fa fa-unlock'></i> 启用");
            	}
            	$('#btn_create').show();
            	if(row.url){
            		$('#btn_show_qr').show();
            	}else{
            		$('#btn_show_qr').hide();
            	}
            },
            onUncheck:function(row){
            		$('#btn_create').hide();
            		$('#btn_enable').hide();
            		$('#btn_show_qr').hide();
            },
            columns: [
            {
                checkbox: true
            },{
                field: 'hospitalName',
            	align:'center',
            	width:200,
                title: '医院名称'
            }, {
            	field:'hospitalAddress',
            	width:200,
            	align:'center',
            	title: '医院地址'
            },{
	        	field:'hospitalIntroduce',
	        	width:200,
	        	align:'center',
	        	title: '医院介绍'
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
