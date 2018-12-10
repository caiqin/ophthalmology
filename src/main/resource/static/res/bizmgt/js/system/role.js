$(function(){
	$('#btn_query').on("click",function(){
		var query ={}
		query.roleName=$("#roleName").val();
		$('#tb_roles').bootstrapTable('refresh',{query:query});
	});
	document.onkeydown=function(event){
	    e = event ? event : (window.event ? window.event : null);
	    if(e.keyCode==13){
	    	$('#btn_query').click();
	    }
	}
	$('#btn_update').click(function(){
		var rows = $('#tb_roles').bootstrapTable("getSelections");
		if(rows.length==0){
			layer.alert("请选择一条数据",{icon:2});
		}else{
			var row = rows[0];
			$('#updateRole').find('#id').val(row.id);
			$('#updateRole').find('#roleName').val(row.roleName);
			$('#updateRole').find('#remark').val(row.remark);
			$('#updateRole').find('#isValid').val(row.isValid);
			$('#updateModal').modal('show');
		}
	});
	$('#add_permission').click(function(){
		var rows = $('#tb_roles').bootstrapTable("getSelections");
		if(rows.length==0){
			layer.alert("请选择一条数据",{icon:2});
		}else{
			window.location.href=basePath+"/sys/roles/toroleResouce.ihtml?systemRoleId="+rows[0].id;
		}
	});
	$('#updateModal').find('#btn_submit').click(function(){
		$.ajax({
			type: "POST",
		  	url: basePath+'/sys/roles/updateSystemRole',
		  	data: $('#updateRole').serialize(),
		  	beforeSend: function()
			{ 
				$('#updateModal').find('#btn_submit').addClass('disabled');
				$('#updateModal').find('#btn_submit').prop('disabled', true);
			},
			success: function(msg){
		    	var message=eval("("+msg+")");
		    	if(message.result){
		    		layer.alert("修改角色成功！",{icon:1});
		    		$('#tb_roles').bootstrapTable('refresh');
		    		$('#updateRole')[0].reset();
					$('#updateModal').find('#btn_submit').removeClass("disabled");
					$('#updateModal').find('#btn_submit').prop('disabled', false);
		    		$('#updateModal').modal('hide');
		    	}else{
		    	    if(message.msg=="isExist"){
		    	    	layer.alert("修改角色失败,该角色已存在!",{icon:2});
		    	    } else{
		    	    	layer.alert("修改角色失败!",{icon:2});
		    	    }
		    	}	
			}
		});
	});
	$('#addModal').find('#btn_submit').click(function(){
		$.ajax({
			type: "POST",
		  	url: basePath+'/sys/roles/addSystemRole',
		  	data: $('#addRole').serialize(),
		  	beforeSend: function()
			{  
				$('#addModal').find('#btn_submit').addClass('disabled');
				$('#addModal').find('#btn_submit').prop('disabled', true);
			},
			success: function(msg){
		    	var message=eval("("+msg+")");
		    	if(message.result){
		    		layer.alert("添加角色成功！",{icon:1});
		    		$('#tb_roles').bootstrapTable('refresh');
		    		$('#addRole')[0].reset();
					$('#addModal').find('#btn_submit').removeClass("disabled");
					$('#addModal').find('#btn_submit').prop('disabled', false);
		    		$('#addModal').modal('hide');
		    	}else{
		    		if(message.msg=="isExist"){
		    	    	layer.alert("添加角色失败,该角色已存在!",{icon:2});
		    	    } else{
		    	    	layer.alert("添加角色失败!",{icon:2});
		    	    }
		    	}	
			}
		});
	});
	$('#btn_stop').click(function(){
		var rows = $('#tb_roles').bootstrapTable("getSelections");
		if(rows.length==0){
			layer.alert("请选择一条数据",{icon:2});
		}else{
			var url = basePath+'/sys/roles/validSystemRole';
			var params='id='+rows[0].id;
        	var valid = rows[0].isValid;
            $.ajax({ 
				type: "POST",
				url: url,
				data: params,
				success: function(msg){
					var message=eval("("+msg+")");
					if(message.result){
						layer.alert("操作成功！",{icon:1});
						if(valid=="1"){
							$('#btn_stop').html("启用");
        				}else{
        					$('#btn_stop').html("停用");
        				}
						$('#tb_roles').bootstrapTable('refresh');
					}else{
						layer.alert("操作失败",{icon:2});	    		
					}
				}
			});
		}
	});
	$('#btn_delete').click(function(){
		var rows = $('#tb_roles').bootstrapTable("getSelections");
		if(rows.length==0){
			layer.alert("请选择一条数据",{icon:2});
		}else{
			layer.confirm('确定删除?', {icon: 3, title:'提示'}, function(index){
            	var url = basePath+'/sys/roles/deleterole'
            	var params='id='+rows[0].id;
            	$.ajax({ 
            		type: "POST",
            		url: url,
            		data: params,
            		success: function(msg){
            			var message=eval("("+msg+")");
            			if(message.result){
            				layer.alert("删除角色成功！",{icon:1});
            				$('#tb_roles').bootstrapTable('refresh');
            			}else{
            				var errormsg = message.msg;
            				if(errormsg=="isExit"){
            					layer.alert("删除角色失败,该角色已关联用户、权限信息",{icon:2});
            				}else{
            					layer.alert("删除角色失败",{icon:2});
            				}
            			}
            		}
            	});
			});
		}
	});
	var roleTable = new roleTableInit();
	roleTable.Init();
});

var roleTableInit = function () {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.Init = function () {
        $('#tb_roles').bootstrapTable({
            url: basePath+'/sys/roles/querySystemRoles',         // 请求后台的URL（*）
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
            	var valid = row.isValid;
            	if(valid=="0"){
					$('#btn_stop').html("启用");
				}else{
					$('#btn_stop').html("停用");
				}
            },
            height: 527,                        // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
            columns: [
            {
                checkbox: true
            },{
                field: 'roleName',
            	align:'center',
                title: '角色名'
            }, {
                field: 'roleType',
            	align:'center',
                title: '角色类型',
                formatter:checkType
            }, {
                field: 'createTime',
            	align:'center',
                title: '创建时间'
            },{
            	field:'isValid',
            	width:200,
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
}
function checkType(val,row){
    var type = row.roleType;
    if(type==1){
       return '平台用户角色';
    }else{
       return '供应商用户角色';
    }
}
function formateStatus(val,row){
    if(val==1){
       return "已启用";
    }else{
       return "已停用";
    }
}