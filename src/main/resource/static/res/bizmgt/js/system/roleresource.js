
$(function(){
	$("#btn_query").on("click",function(){
		$('#addPermissionModal').modal('show');
		// 1.初始化Table
	    var oTable = new TableInit();
	    oTable.Init();
	    var query={};
	    query.resName=$('#permiName').val();
	    $("#tb_resource").bootstrapTable('refresh',{query:query});
	});
	document.onkeydown=function(event){
	    e = event ? event : (window.event ? window.event : null);
	    if(e.keyCode==13){
	    	$('#btn_query').click();
	    }
	}
	$("#btn_save").on("click",function(){
		var perIds = $("#new_resource").bootstrapTable("getData");
		var ids = new Array();
		var delIds = $("#delAlreadyIds").val();
		for(var i=0;i<perIds.length;i++){
			ids.push(perIds[i].id);
		}
		if(ids.length>0||delIds!=''){
	      	var params='roleId='+roleId+'&perIds='+ids.join(",")+'&delIds='+delIds;
			$.ajax({ 
				type: "POST",
			  	url: basePath + "/sys/system/roleresource/addroleresource",
			  	data: params,
				success: function(msg){
			    	var message=eval("("+msg+")");
			    	if(message.result){
			    		layer.alert("分配角色资源成功！",{icon:1});
			    		history.back();
			    	}else{
			    		layer.alert("分配角色资源失败！！",{icon:2});
			    	}
			    	
				}
			});
	    }else{
	       layer.alert("请选择资源！",{icon:2});
	       return false;
	    }
	});
	$("#addPermissionModal").find("#btn_submit").click(function(){
		var rows = $('#tb_resource').bootstrapTable("getSelections");
		
		if(rows.length==0){
			layer.alert("请选择数据",{icon:2});
		}else{
			var data = $('#new_resource').bootstrapTable("getData");
			if(data.length!=0){
				for(var i=0;i<rows.length;i++){
					var flag = 0;
					for(var j=0;j<data.length;j++){
						var id = rows[i].id;
						var newId = data[j].id;
						if(id==newId){
							flag = flag+1;
							break;
						}
					}
					if(flag==0){
						$('#new_resource').bootstrapTable("append",rows[i]);
					}
				}
			}else{
				$('#new_resource').bootstrapTable("append",rows);
			}
			$('#addPermissionModal').modal('hide');
		}
	});
	var permissionTable = new alreadyPermissionTable();
	permissionTable.Init();
	var newPermTable = new newPermissionTable();
	newPermTable.Init();
});
function deleteRow(id){
	$("#new_resource").bootstrapTable("remove",{field:"id",values:[id]});
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
        $('#tb_resource').bootstrapTable({
            url: basePath+'/sys/system/resource/queryResourceList',         // 请求后台的URL（*）
            method: 'post',                      // 请求方式（*）
            contentType:"application/x-www-form-urlencoded; charset=UTF-8",
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
            //height: 450,                        // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
            columns: [{
                checkbox: true
            }, {
                field: 'resName',
            	align:'center',
                title: '资源名称'
            }, {
                field: 'resType',
            	align:'center',
                title: '资源类型',
                formatter:checkPermission
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

var alreadyPermissionTable = function () {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.Init = function () {
        $('#already_resource').bootstrapTable({
            url: basePath+'/sys/system/roleresource/roleResourceList?roleId='+roleId,         // 请求后台的URL（*）
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
                field: 'resourceName',
            	align:'center',
                title: '资源名称'
            }, {
                field: 'resType',
            	align:'center',
                title: '资源类型',
                formatter:checkPermission
            }, {
                field: 'createTime',
            	align:'center',
                title: '创建时间'
            },{
            	width:80,
            	align:'center',
            	title: '操作',
            	formatter:formateAlready
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
function deleteAlreadyRow(id,resourceId){
	$("#already_resource").bootstrapTable("remove",{field:"id",values:[id]});
	var ids = $("#delAlreadyIds").val();
	if(ids){
		$("#delAlreadyIds").val(ids+","+resourceId);
	}else{
		$("#delAlreadyIds").val(resourceId);
	}
}
function formateAlready(val, row, index){
	var id = row.id;
	var resourceId = row.resourceId;
	return "<a href='javascript:void(0)' onclick=javascript:deleteAlreadyRow('"+id+"','"+resourceId+"')>删除</a> ";
   }
var newPermissionTable = function () {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.Init = function () {
        $('#new_resource').bootstrapTable({
            //url: basePath+'/bizmgt/permissionmanager/permissionList.ihtml?userId=${systemRoleVo.id}',         // 请求后台的URL（*）
            method: 'get',                      // 请求方式（*）
            striped: true,                      // 是否显示行间隔色
            cache: false,                       // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            queryParams: oTableInit.queryParams,// 传递参数（*）
            sidePagination: "server",           // 分页方式：client客户端分页，server服务端分页（*）
            minimumCountColumns: 2,             // 最少允许的列数
            clickToSelect: true,                // 是否启用点击选中行
            height: 350,                        // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "id",                     // 每一行的唯一标识，一般为主键列
            columns: [{
                field: 'resName',
            	align:'center',
                title: '资源名称'
            }, {
                field: 'resType',
            	align:'center',
                title: '资源类型',
                formatter:checkPermission
            }, {
                field: 'createTime',
            	align:'center',
                title: '创建时间'
            },{
            	width:80,
            	align:'center',
            	title: '操作',
            	formatter:formate
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
function formate(val, row, index){
	var id = row.id;
	return "<a href='javascript:void(0)' onclick=javascript:deleteRow('"+id+"')>删除</a> ";
   }
function checkPermission(val,row){
    var type = row.resType;
    if(type==1){
       return '平台用户权限';
    }else{
       return '-';
    }
}
