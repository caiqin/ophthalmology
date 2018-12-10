/** *患者管理** */
$('#btn_query').on("click",function(){
		var query ={}
		$('#tb_patients').bootstrapTable('refresh',{query:query});
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
$("#addModal").find("#hospitalName").on("click",function(){
	$("#showHospitalModal").modal("show");
});
$("#updateModal").find("#hospitalName").on("click",function(){
	$("#showHospitalModal").modal("show");
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
	$("#addPatient").data('bootstrapValidator').resetForm(true);
}); 

$("#btn_show_qr").on("click",function(){
	
	var rows = $('#tb_patients').bootstrapTable("getSelections");
	if(rows.length==0){
		layer.alert("请选择要修改的数据",{icon:2});
	}else{
		var row = rows[0];
		$("#img_qr").attr("src",imgRes+row.codeUrl);
		$("#showModal").modal("show");
	}
});
$(function(){
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
				   if(($("#addModal").data('bs.modal') || {}).isShown){
					   $("#addModal").find("#hospitalName").val(data.text);
					   $("#addModal").find("#hospitalId").val(data.hospitalId);
				   }
				   if(($("#updateModal").data('bs.modal') || {}).isShown){
					   $("#updateModal").find("#hospitalName").val(data.text);
					   $("#updateModal").find("#hospitalId").val(data.hospitalId);
				   }
				    
				}
			});
		}
	});
	
	
	$('#addPatient').bootstrapValidator({
		message: '输入的值格式不对',
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			name: {
				message: '输入的值格式不对',
				validators: {
					notEmpty: {
						message: '患者姓名不能为空'
					},
					stringLength: {
						min: 1,
						max: 20,
						message: '患者姓名字符过长，最多20个字符'
					}
				}
			},
			gender: {
				message: '输入的值格式不对',
				validators: {
					notEmpty: {
						message: '请选择性别'
					}
				}
			},
			age: {
				message: '输入的值格式不对',
				validators: {
					notEmpty: {
						message: '请输入年龄'
					}
				}
			},
			cardNo: {
				message: '输入的值格式不对',
				validators: {
					stringLength: {
						min: 0,
						max: 18,
						message: '身份证格式不对'
					}
				}
			}
		}
	})
	.on('success.form.bv', function(e) {
		e.preventDefault();
		var $form = $(e.target);
		var bv = $form.data('bootstrapValidator');
		var data = $('#addPatient').serialize();
		$.ajax({
			url: basePath+'/patient/add',
			data: data,
			type: "POST",
			beforeSend: function()
			{  
				$("#addPatient").find('#btn_submit').addClass("disabled");
				$("#addPatient").find('#btn_submit').prop('disabled', true);
			},
			success: function(data)
			{
				$('#addModal').modal('hide');
				$('#addPatient')[0].reset();
				$("#addPatient").find('#btn_submit').removeClass("disabled");
				$("#addPatient").find('#btn_submit').prop('disabled', false);
				$('#tb_patients').bootstrapTable('refresh');
				layer.alert("新增成功",{icon:1});
			}
		});
	});
	$('#updatePatient').bootstrapValidator({
		message: '输入的值格式不对',
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			name: {
                message: '输入的值格式不对',
                validators: {
                    notEmpty: {
                        message: '患者姓名不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 20,
                        message: '患者姓名字符过长，最多20个字符'
                    }
                }
            },
	        gender: {
	        	message: '输入的值格式不对',
	        	validators: {
	        		notEmpty: {
	        			message: '请选择性别'
	        		}
	        	}
	        },
	        age: {
	        	message: '输入的值格式不对',
	        	validators: {
	        		notEmpty: {
	        			message: '请输入年龄'
	        		}
	        	}
	        },
	        mobileNo: {
	        	message: '输入的值格式不对',
	        	validators: {
	        		notEmpty: {
	        			message: '手机号不能为空'
	        		},
	        		regexp: {
                        regexp: /^1\d{10}$/,
                        message: '手机号输入格式不正确'
                    }
	        	}
	        },
	        cardNo: {
				message: '输入的值格式不对',
				validators: {
					stringLength: {
						min: 0,
						max: 18,
						message: '身份证格式不对'
					}
				}
			}
		}
	})
	.on('success.form.bv', function(e) {
		e.preventDefault();
		var $form = $(e.target);
		var bv = $form.data('bootstrapValidator');
		var data = $('#updatePatient').serialize();
		$.ajax({
			url: basePath+'/patient/update',
			data: data,
			type: "POST",
			beforeSend: function()
			{  
				$('#updatePatient').find('#btn_submit').addClass("disabled");
				$('#updatePatient').find('#btn_submit').prop('disabled', true);
			},
			success: function(data)
			{
				$('#updateModal').modal('hide');
				$('#updatePatient')[0].reset();
				$('#updatePatient').find('#btn_submit').removeClass("disabled");
				$('#updatePatient').find('#btn_submit').prop('disabled', false);
				$('#tb_patients').bootstrapTable('refresh');
				layer.alert("修改成功",{icon:1});
			}
		});
	});
	$('#btn_submit_update').click(function () {
      	$('#updatePatient').submit();
    });

	/** ******详情按钮点击事件******* */
	$('#add').click(function(){
		$('#addModal').modal('show');
	});
	/** ******详情按钮点击事件******* */
	$('#detail').click(function(){
		var rows = $('#tb_patients').bootstrapTable("getSelections");
		if(rows.length==0){
			layer.alert("请选择数据",{icon:2});
		}else{
			var row = rows[0];
			$('#detailModal').modal('show');
			$('#detailPatient').find('#name').val(row.name);		
			$('#detailPatient').find("input[name='gender'][value="+row.gender+"]").attr("checked",true);
			$('#detailPatient').find('#age').val(row.age);		
			$('#detailPatient').find('#mobileNo').val(row.mobileNo);		
			$('#detailPatient').find('#cardNo').val(row.cardNo);		
			$('#detailPatient').find('#hospitalName').val(row.hospitalName);	
			$('#detailPatient').find('#cardAddress').val(row.cardAddress);		
			$('#detailPatient').find('#realAddress').val(row.realAddress);		
		}
	});
	/** ******修改按钮点击事件******* */
	$('#update').click(function(){
		var rows = $('#tb_patients').bootstrapTable("getSelections");
		if(rows.length==0){
			layer.alert("请选择要修改的数据",{icon:2});
		}else{
			var row = rows[0];
			$('#updateModal').modal('show');
			$('#updatePatient').find("#id").val(row.id);
			$('#updatePatient').find('#name').val(row.name);		
			$('#updatePatient').find("input[name='gender'][value="+row.gender+"]").attr("checked",true);
			$('#updatePatient').find('#age').val(row.age);		
			$('#updatePatient').find('#mobileNo').val(row.mobileNo);		
			$('#updatePatient').find('#cardNo').val(row.cardNo);		
			$('#updatePatient').find('#cardAddress').val(row.cardAddress);		
			$('#updatePatient').find('#hospitalName').val(row.hospitalName);		
			$('#updatePatient').find('#realAddress').val(row.realAddress);		
		}
	});

	
	
	// 1.初始化Table
    var doctorTable = new doctorTableInit();
    doctorTable.Init();
});

var doctorTableInit = function () {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.Init = function () {
        $('#tb_patients').bootstrapTable({
            url: basePath+'/patient/getData',         // 请求后台的URL（*）
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
                field: 'name',
            	align:'center',
                title: '姓名'
            }, {
            	field:'gender',
            	width:50,
            	align:'center',
            	title: '性别',
            	formatter:function (val, row){
            		if(val=="0"){
            			return "男";
            		}else if(val=="1"){
            			return '女';
            		}
            	}
            },{
	        	field:'age',
	        	width:80,
	        	align:'center',
	        	title: '年龄'
	        },{
            	field:'mobileNo',
            	width:80,
            	align:'center',
            	title: '手机'
            },{
            	field:'cardNo',
            	width:160,
            	align:'center',
            	title: '身份证'
            },{
            	field:'hospitalName',
            	width:200,
            	align:'center',
            	title: '所属医院'
            },{
            	field:'createTime',
            	width:190,
            	align:'center',
            	title: '注册时间'
            }]
        });
    };

    // 得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            rows: params.limit,   // 页面大小
            page: params.offset/params.limit,  // 页码
            name: $('#formSearch').find('#patientName').val(),
            mobileNo:$('#formSearch').find('#mobileNo').val()
        };
        return temp;
    };
    return oTableInit;
};
