<#include "../common/public_macro.ftl">  
  
<@header title="添加用户角色">  
</@header>


<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
	<@navigation title="添加用户角色">  
	</@navigation>
    <div class="content-wrapper">
    	 <!-- Content Header (Page header) -->
	    <section class="content-header">
	      <h1>
	        添加用户角色
	      </h1>
	      <ol class="breadcrumb">
	        <@index_page></@index_page>
	        <li><a href="#">用户管理</a></li>
	        <li><a href="#">添加用户角色</a></li>
	      </ol>
	    </section>
	    <!-- Main content -->
	    <section class="content">
            <!-- /.row -->
            <div class="row" style="padding-top:10px;">
                <div class="col-lg-12">
                    <div class="box box-primary">
                        <!-- /.panel-heading -->
			            <div class="box-body">
		                    <div class="row">
		                    	<input type="hidden" value="${systemUserVo.id}" id="userId"/>
		                        <label class="control-label col-sm-2" >用户名:${systemUserVo.userName!''}</label>
		                        <label class="control-label col-sm-2"  >登录名:${systemUserVo.loginName!''}</label>
		                    </div>
		                    <div class="row">
		                        <label class="control-label col-sm-2"  for="roleName">角色名</label>
		                        <div class="col-sm-3">
		                            <input type="text" class="form-control" id="roleName">
			                        <input type="hidden" id="deleteId" value=""/>
		                        </div>
		                        <div class="col-sm-2" style="text-align:left;margin-bottom:10px;">
		                            <button type="button" style="margin-left:50px" id="btn_query" class="btn btn-primary">查询</button>
		                        </div>
		                    </div>
	                        <div class="row">
	                        	 <div class="col-lg-6">
			                        <div class="box box-success"  style="margin-left:10px;">
				                        <div class="panel-heading">
				                            已添加角色
				                        </div>
			                            <table id="" class="table table-bordered">
			                            	<thead>
			                            		<th style='text-align: center;'>角色名</th>
			                            		<th style='text-align: center;'>角色类型</th>
			                            		<th style='text-align: center;'>创建时间</th>
			                            		<th style='text-align: center;'>操作</th>
			                            	</thead>
			                            	<tbody id="already_role">
			                            		
			                            	</tbody>
			                            </table>
			                        </div>
		                       	</div>
		                       	<div class="col-lg-6">
			                        <div class="box box-danger" >
				                        <div class="panel-heading">
				                            新添加角色
				                        </div>
			                            <table id="" class="table table-bordered">
			                            	<thead>
			                            		<th  style='text-align: center;'>角色名</th>
			                            		<th style='text-align: center;'>角色类型</th>
			                            		<th style='text-align: center;'>创建时间</th>
			                            		<th style='text-align: center;'>操作</th>
			                            	</thead>
			                            	<tbody id="new_role">
			                            	</tbody>
			                            </table>
			                        </div>
		                        </div>
		                    </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                   <div class="row">
	                     <div class="col-sm-8 col-sm-offset-5" >
	                        <button type="button" style="margin-bottom:10px;" id="btn_save" class="btn btn-primary">添加</button>
	                        <button type="button" style="margin-left:20px;margin-bottom:10px;" onclick="javascript:history.back()" id="btn_query" class="btn btn-primary">返回</button>
	                    </div>
                   </div>
                </div>
                <!-- /.col-lg-12 -->
            </div>
             </section>
		</div>
		<@footer></@footer>
    </div>
	<div class="modal fade" id="addRoleModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog" role="document" style="width:800px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">添加角色</h4>
                </div>
                <div class="modal-body">
                	 <table id="tb_role" ></table>
                
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                    <button type="button" id="btn_submit" class="btn btn-primary">保存</button>
                </div>
            </div>
        </div>
    </div>
</body>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="${BasePath}/res/bootstrap/js/table/css/bootstrap-table.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="${BasePath}/res/bootstrap/js/table/bootstrap-table.min.js"></script>

<!-- Latest compiled and minified Locales -->
<script src="${BasePath}/res/bootstrap/js/table/bootstrap-table-zh-CN.min.js"></script>
<script src="${BasePath}/res/bizmgt/js/system/userrole.js"></script>

<script>
var basePath = "${BasePath!''}";
</script>
</html>
