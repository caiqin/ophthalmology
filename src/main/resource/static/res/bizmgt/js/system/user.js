/***用户管理***/
$('#btn_query').on("click",function(){
		var query ={}
		query.loginName=$("#loginName").val();
		query.userName=$("#userName").val();
		$('#tb_users').bootstrapTable('refresh',{query:query});
	});
document.onkeydown=function(event){
    e = event ? event : (window.event ? window.event : null);
    if(e.keyCode==13){
    	$('#btn_query').click();
    }
}
$('#btn_query_custom').on("click",function(){
	var query ={}
	query.customFullName=$("#customFullName").val();
	$('#tb_customs').bootstrapTable('refresh',{query:query});
});
$("#formSearchCustom").on("submit",function(){
	return false;
});

$('#btn_relation').click(function () {
	var userNo = $('#tb_users').bootstrapTable("getSelections")[0].userNo;
	var rows = $('#tb_doctors').bootstrapTable("getSelections")
	if(rows.length==0){
		layer.alert("请选择要关联的数据",{icon:2});
	}else{
		var doctorId = rows[0].doctorId;
		var doctorName = rows[0].doctorName;
		
		$.ajax({ 
			type: "POST",
			url: basePath+"/sys/user/relationDoctor",
			data: {"userNo":userNo,"doctorId":doctorId,"doctorName":doctorName},
			success: function(msg){
				var message= eval('('+msg+')');
				if(message.result){
					$('#relationModal').modal("hide");
					$('#btn_query').click();
					layer.alert("关联医生成功!",{icon:1});
					$('#tb_users').bootstrapTable('refresh');
				}else{
					layer.alert("关联医生失败!",{icon:2});
				}
				
			}
		});
	}
});

$('#relationDoctor').click(function(){
	
	var rows = $('#tb_users').bootstrapTable("getSelections");
	if(rows.length==0){
		layer.alert("请选择一条数据",{icon:2});
	}else{
		$("#relationModal").modal("show");
	}
});

$(function(){
	$('#addUser').bootstrapValidator({
        message: '输入的值格式不对',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	userName: {
                message: '输入的值格式不对',
                validators: {
                    notEmpty: {
                        message: '真实姓名不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 20,
                        message: '真实姓名字符过长，最多20个字符'
                    }
                }
            },
            loginName: {
                validators: {
                	notEmpty: {
                        message: '登录名不能为空'
                    },
                	stringLength: {
                		min: 1,
                        max: 20,
                        message: '登录名不能超过20个字符'
                    }
                }
            },
            email: {
                validators: {
                	notEmpty: {
                        message: 'email不能为空'
                    },
                	emailAddress: {
                        message: 'email格式不对'
                    }
                }
            },
            loginPassword: {
            	validators: {
            		notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {
                		min: 6,
                        max: 16,
                        message: '密码长度必须在6到16之间'
                    }
            	}
            },
            telPhone: {
                validators: {regexp: {
                        regexp: /^0\d{2,3}-?\d{7,8}$/,
                        message: '电话输入格式不正确，请使用格式：020-88888888'
                    }
                }
            },
            mobilePhone: {
                validators: {
                	notEmpty: {
                        message: '手机号不能为空'
                    },regexp: {
                        regexp: /^1\d{10}$/,
                        message: '手机号输入格式不正确'
                    }
                }
            },
            QQNum: {
                validators: {
                	stringLength: {
                		min: 4,
                        max: 12,
                        message: 'qq不能超过12个字符'
                    },regexp: {
                        regexp: /^[0-9]+$/,
                        message: 'qq只能包含数字'
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
			url: basePath+'/sys/user/createuser',
			data: $('#addUser').serialize(),
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
					$('#addUser')[0].reset();
					$("#addUser").data("bootstrapValidator").resetForm();
					$('#addModal').find('#btn_submit').removeClass("disabled");
					$('#addModal').find('#btn_submit').prop('disabled', false);
					$('#tb_users').bootstrapTable('refresh');
					layer.alert("添加成功",{icon:1});
				}else{
					layer.alert(data.message,{icon:2});
				}
			}
		});
    });
	$('#editUser').bootstrapValidator({
		message: '输入的值格式不对',
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			userName: {
				message: '输入的值格式不对',
				validators: {
					notEmpty: {
						message: '真实姓名不能为空'
					},
					stringLength: {
						min: 1,
						max: 20,
						message: '真实姓名字符过长，最多20个字符'
					}
				}
			},
			loginName: {
				validators: {
					notEmpty: {
						message: '登录名不能为空'
					},
					stringLength: {
						min: 1,
						max: 20,
						message: '登录名不能超过20个字符'
					}
				}
			},
			email: {
				validators: {
					notEmpty: {
						message: 'email不能为空'
					},
					emailAddress: {
						message: 'email格式不对'
					}
				}
			},
			loginPassword: {
				validators: {
					notEmpty: {
						message: '密码不能为空'
					},
					stringLength: {
						min: 6,
						max: 16,
						message: '密码长度必须在6到16之间'
					}
				}
			},
			telPhone: {
				validators: {regexp: {
					regexp: /^0\d{2,3}-?\d{7,8}$/,
					message: '电话输入格式不正确，请使用格式：020-88888888'
				}
				}
			},
			mobilePhone: {
				validators: {
					notEmpty: {
						message: '手机号不能为空'
					},regexp: {
						regexp: /^1\d{10}$/,
						message: '手机号输入格式不正确'
					}
				}
			},
			QQNum: {
				validators: {
					stringLength: {
						min: 4,
						max: 12,
						message: 'qq不能超过12个字符'
					},regexp: {
						regexp: /^[0-9]+$/,
						message: 'qq只能包含数字'
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
			url: basePath+'/sys/user/updateuser',
			data: $('#editUser').serialize(),
			type: "POST",
			beforeSend: function()
			{  
				$('#btn_submit_update').addClass("disabled");
				$('#btn_submit_update').prop('disabled', true);
			},
			success: function(data)
			{
				$('#updateModal').modal('hide');
				$('#editUser')[0].reset();
				$('#btn_submit_update').removeClass("disabled");
				$('#btn_submit_update').prop('disabled', false);
				$('#tb_users').bootstrapTable('refresh');
				layer.alert("修改成功",{icon:1});
			}
		});
	});
	$('#btn_submit_update').click(function () {
      	$('#editUser').submit();
    });
	$('#btn_update_password').click(function () {
      	var loginPassword=$('#editUserPassword').find('#loginPassword').val();
      	var rePassword=$('#editUserPassword').find('#rePassword').val();
		if(loginPassword==''){
		    layer.alert("请输入密码!",{icon:2});
		    return false;
		}
		if(loginPassword!=rePassword){
			layer.alert("两次输入的密码不一致!",{icon:2});
			return false;
		}
		var url=basePath+'/sys/user/remotePassword';
		var userid= $('#editUserPassword').find('#id').val();
		var params='loginPassword='+loginPassword+'&id='+userid;
		$.ajax({ 
			type: "POST",
		  	url: url,
		  	data: params,
			success: function(msg){
		    	var message= eval('('+msg+')');
		    	if(message.result){
		    		$('#editUserPassword')[0].reset();
		    		$('#updatePasswordModal').modal("hide");
		    		layer.alert("修改密码成功!",{icon:1});
		    	}else{
		    		layer.alert("修改密码失败!",{icon:2});
		    	}
		    	
			}
		});
    });

	/********修改按钮点击事件********/
	$('#update').click(function(){
		var rows = $('#tb_users').bootstrapTable("getSelections");
		if(rows.length==0){
			layer.alert("请选择要修改的数据",{icon:2});
		}else{
			var row = rows[0];
			$('#updateModal').modal('show');
			$('#editUser').find("#id").val(row.id);
			$('#editUser').find('#userName').val(row.userName);		
			$('#editUser').find('#loginName').val(row.loginName);		
			$('#editUser').find('#telPhone').val(row.telPhone);		
			$('#editUser').find('#mobilePhone').val(row.mobilePhone);		
			$('#editUser').find('#QQNum').val(row.QQNum);		
			$('#editUser').find('#email').val(row.email);		
			$('#editUser').find("input[id='sex'][value='"+row.sex+"']").prop("checked","checked");
		}
	});
	/********添加角色按钮点击事件********/
	$('#addRole').click(function(){
		var rows = $('#tb_users').bootstrapTable("getSelections");
		if(rows.length==0){
			layer.alert("请选择一条数据",{icon:2});
		}else{
			
			var addrole = basePath+'/sys/user/toUserRole?systemUserId='+rows[0].id;
	        window.location.href =addrole;
		}
	});
	var $modal = $('#updatePasswordModal');
	// 测试 bootstrap 居中
	$modal.on('show.bs.modal', function(){
	  var $this = $(this);
	  var $modal_dialog = $this.find('.modal-dialog');
	  // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
	  $this.css('display', 'block');
	  $modal_dialog.css({'margin-top': Math.max(0, ($(window).height() - $modal_dialog.height()) / 2) });
	});
	$("#relationModal").on('hide.bs.modal', function(){
		$("#doctorName").val("");
	});
	/********修改密码按钮点击事件********/
	$('#updatePassword').click(function(){
		var rows = $('#tb_users').bootstrapTable("getSelections");
		if(rows.length==0){
			layer.alert("请选择一条数据",{icon:2});
		}else{
			$('#editUserPassword').find('#id').val(rows[0].id);
			$("#updatePasswordModal").modal("show");
		}
	});
	/********锁定按钮点击事件********/
	$('#lock').click(function(){
		var rows = $('#tb_users').bootstrapTable("getSelections");
		if(rows.length==0){
			layer.alert("请选择一条数据",{icon:2});
		}else{
		  	var status = rows[0].userState;
		  	var id = rows[0].id;
		  	if(status=='0'){
				layer.confirm('确定锁定该用户?', {icon: 3, title:'提示'}, function(index){
			  		var  lockUrl = basePath+'/sys/user/lockuser';
			  		$.ajax({ 
						type: "POST",
					  	url: lockUrl,
					  	data: {userId:id},
						success: function(msg){
					    	var message= eval('('+msg+')');
					    	if(message.result){
					    		layer.alert("锁定用户成功!",{icon:1});
					    		$('#btn_query').click();
					    	}else{
					    		layer.alert("锁定用户失败!",{icon:2});
					    	}
					    	
						}
					});
				});
			}else{
				layer.confirm('确定解锁该用户?', {icon: 3, title:'提示'}, function(index){
			  		var  unlockUrl = basePath+'/sys/user/unlockuser';
			  		$.ajax({ 
						type: "POST",
					  	url: unlockUrl,
					  	data: {userId:id},
						success: function(msg){
					    	var message= eval('('+msg+')');
					    	if(message.result){
					    		layer.alert("解锁用户成功!",{icon:1});
					    		$('#btn_query').click();
					    	}else{
					    		layer.alert("解锁用户失败!",{icon:2});
					    	}
						}
					});
				});	
			}
		}
	});

	// 1.初始化Table
    var userTable = new userTableInit();
    userTable.Init();
    
    var doctorTable = new doctorTableInit();
    doctorTable.Init();
});

var userTableInit = function () {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.Init = function () {
        $('#tb_users').bootstrapTable({
            url: basePath+'/sys/user/getuserList',         // 请求后台的URL（*）
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
            onCheck:function(row){
            	var status = row.userState;
            	if(status=='0'){
            		$('#lock').html("<i class='fa fa-lock'></i> 锁定");
            	}else{
            		$('#lock').html("<i class='fa fa-unlock'></i>&nbsp;解锁");
            	} 
            },
            height: 527,                        // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
            columns: [
            {
                checkbox: true
            },{
                field: 'userName',
            	align:'center',
                title: '真实姓名'
            }, {
                field: 'loginName',
            	align:'center',
                title: '登录名'
            }, {
                field: 'mobilePhone',
            	align:'center',
                title: '手机号'
            },{
            	field: 'doctorName',
            	align:'center',
                title: '医生'
            }, {
                field: 'telPhone',
            	align:'center',
                title: '电话'
            },{
            	field:'email',
            	width:200,
            	align:'center',
            	title: 'Email'
            },{
            	field:'QQNum',
            	width:200,
            	align:'center',
            	title: 'QQ'
            },{
            	field:'userState',
            	width:100,
            	align:'center',
            	title: '状态',
            	formatter:formateStatus
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
function formateStatus(val, row){
        if(val==0){
            return "正常";
        }else{
            return "锁定";
        }
   }



var doctorTableInit = function () {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.Init = function () {
        $('#tb_doctors').bootstrapTable({
            url: basePath+'/doctor/getData',         // 请求后台的URL（*）
            method: 'post',                      // 请求方式（*）
            contentType:"application/x-www-form-urlencoded; charset=UTF-8",
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
//            height: 527,                        // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
            columns: [
            {
                checkbox: true
            },{
                field: 'doctorName',
            	align:'center',
                title: '医生姓名'
            }, {
                field: 'doctorIntroduce',
            	align:'center',
                title: '医生介绍'
            }, {
                field: 'hospitalName',
            	align:'center',
                title: '所在医院'
            }, {
                field: 'docCatName',
            	align:'center',
                title: '医院专长'
            }]
        });
    };

    // 得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            rows: params.limit,   // 页面大小
            page: params.offset/params.limit,  // 页码
            doctorName:$("#doctorName").val()
        };
        return temp;
    };
    return oTableInit;
};