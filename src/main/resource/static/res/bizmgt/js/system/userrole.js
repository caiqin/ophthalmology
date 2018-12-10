$(function(){
	$("#btn_query").on("click",function(){
		$('#addRoleModal').modal('show');
		// 1.初始化Table
	    var oTable = new TableInit();
	    oTable.Init();
	});
	document.onkeydown=function(event){
	    e = event ? event : (window.event ? window.event : null);
	    if(e.keyCode==13){
	    	$('#btn_query').click();
	    }
	}
	$("#btn_save").on("click",function(){
		var userId = $("#userId").val();
		var roleids = $("#new_role").find("[type='hidden']");
		var ids = new Array();
		for(var i=0;i<roleids.length;i++){
			ids.push(roleids[i].value);
		}
		var deleteId = $.trim($("#deleteId").val());
		if(ids.length>0){
	      	var params='userId='+userId+'&roleIds='+ids.join(",")+"&deleteIds="+deleteId;
			$.ajax({ 
				type: "POST",
			  	url: basePath + "/sys/system/roleuser/addrolesinuser",
			  	data: params,
				success: function(msg){
			    	var message=eval("("+msg+")");
			    	if(message.result){
			    		layer.alert("添加用户角色成功！",{icon:1});
			    		history.back();
			    	}else{
			    		layer.alert("添加用户角色失败！！",{icon:2});
			    	}
			    	
				}
			});
	    }else{
	       layer.alert("请选择角色！",{icon:2});
	       return false;
	    }
	});
	$("#addRoleModal").find("#btn_submit").click(function(){
		var rows = $('#tb_role').bootstrapTable("getSelections");
		if(rows.length==0){
			layer.alert("请选择数据",{icon:2});
		}else{
			var html = "";
			for(var i=0;i<rows.length;i++){
				var row = rows[i];
				html = html +"<tr id='tr_new_role_"+i+"'><td>"+row.roleName+"</td>"+
				"<input type='hidden' id='roleid' value='"+row.id+"'></input>"+
	    		"<td style='text-align: center;'>"+row.roleType+"</td>"+
	    		"<td style='text-align: center;'>"+(row.createTime==undefined?"-":row.createTime)+"</td>"+
	    		"<td style='text-align: center;'><a href='javascript:void(0)' onclick='deleteRow("+i+")'>删除</a></td></tr>";
			}
			$('#new_role').html(html);
			$('#addRoleModal').modal('hide');
		}
	});
	$.ajax({ 
		type: "POST",
		url: basePath+"/sys/system/roleuser/userRoleList?userId="+$("#userId").val(),
		data: {page:0,rows:10},
		success: function(msg){
			var result=JSON.parse(msg);
			if(result.total>0){
				var rows = result.rows;
				var html = "";
				for(var i=0;i<rows.length;i++){
					var row = rows[i];
					html = html +"<tr id='tr_role_"+i+"'><input id='roleId"+i+"' type='hidden' value='"+row.id+"'><td style='text-align: center;'>"+row.roleName+"</td>"+
		    		"<td style='text-align: center;'>"+row.roleType+"</td>"+
		    		"<td style='text-align: center;'>"+(row.createTime==undefined?"-":row.createTime)+"</td>"+
		    		"<td style='text-align: center;'><a href='javascript:void(0)' onclick='deleteRowAlready("+i+")'>删除</a></td></tr>";
		    		"</tr>";
				}
				$('#already_role').html(html);		
			}
		}
	});
});

function deleteRow1(i){
	$("#tr_role_"+i).remove();
}
function deleteRow(i){
	$("#tr_new_role_"+i).remove();
}
function deleteRowAlready(i){
	var deleteId = $("#deleteId").val();
	var roleId = $("#tr_role_"+i).find("#roleId"+i).val();
	if($.trim(deleteId)==''){
		$("#deleteId").val(roleId);
	}else{
		$("#deleteId").val(deleteId+','+roleId);
	}
	$("#tr_role_"+i).remove();
}
function getIdSelections() {
    return $.map($table.bootstrapTable('getSelections'), function (row) {
        return row.id
    });
}

var TableInit = function () {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.Init = function () {
        $('#tb_role').bootstrapTable({
            url: basePath+'/sys/roles/querySystemRoles',         // 请求后台的URL（*）
            method: 'get',                      // 请求方式（*）
            striped: true,                      // 是否显示行间隔色
            cache: false,                       // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   // 是否显示分页（*）
            queryParams: oTableInit.queryParams,// 传递参数（*）
            sidePagination: "server",           // 分页方式：client客户端分页，server服务端分页（*）
            pageSize: 10,                       // 每页的记录行数（*）
            pageList: [10, 25, 50, 100],        // 可供选择的每页的行数（*）
            //showColumns: true,                  // 是否显示所有的列
            //showRefresh: true,                  // 是否显示刷新按钮
            minimumCountColumns: 2,             // 最少允许的列数
            clickToSelect: true,                // 是否启用点击选中行
            height: 350,                        // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
            columns: [{
                checkbox: true
            }, {
                field: 'roleName',
            	align:'center',
                title: '角色名'
            }, {
                field: 'resType',
            	align:'center',
                title: '角色类型'
            }, {
                field: 'createTime',
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

function formatDate(val, row){
		return '<a href="javascript:void(0)">删除</a>';
}