<#include "../common/public_macro.ftl">  
  
<@header title="添加角色资源">  
</@header>


<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
	<@navigation title="添加角色资源">  
	</@navigation>
	<div class="content-wrapper">
    	 <!-- Content Header (Page header) -->
	    <section class="content-header">
	      <h1>
	        添加角色资源
	      </h1>
	      <ol class="breadcrumb">
	        <@index_page></@index_page>
	        <li><a href="#">角色管理</a></li>
	        <li><a href="#">添加角色资源</a></li>
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
	                        <form id="formSearch" class="form-horizontal">
			                    <div class="form-group" style="margin-top:15px">
			                        <label class="control-label col-sm-2" style="width:20%;" >角色名:${systemRoleVo.roleName!''}</label>
			                        <label class="control-label col-sm-2" style="width:20%;" for="resName">资源名</label>
			                        <div class="col-sm-2">
			                            <input type="text" class="form-control" id="permiName">
			                        </div>
			                        <div class="col-sm-2" style="text-align:left;margin-bottom:10px;">
			                            <button type="button" style="margin-left:50px" id="btn_query" class="btn btn-primary">查询</button>
			                        </div>
			                    </div>
			            	</form>
	                        <input type="hidden" id="delAlreadyIds">
	                        <div class="col-lg-6">
		                        <div class="box box-success">
		                        	<div class="box-header ui-sortable-handle">
				                        <h3 class="box-title">
				                            已添加资源
				                        </h3>
			                        </div>
		                            <table id="already_resource">
		                            </table>
		                        </div>
		                    </div>
	                        <div class="col-lg-6">
		                    	<div class="box box-danger">
		                    		<div class="box-header ui-sortable-handle">
				                        <h3 class="box-title">
				                            新添加资源
				                        </h3>
				                    </div>
		                            <table id="new_resource"></table>
		                        </div>
	                        </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                    <div class="col-lg-4 col-lg-offset-5">
	                    <button type="button" style="margin-left:0px" id="btn_save" class="btn btn-primary">保存</button>
	                    <button type="button" style="margin-left:0px" onclick="javascript:history.back()" id="btn_query" class="btn btn-primary">返回</button>
                	</div>
                </div>
                <!-- /.col-lg-12 -->
            </div>
          </section>
		</div>
		<@footer></@footer>
    </div>
	<div class="modal fade" id="addPermissionModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog" role="document" style="width:800px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">添加资源</h4>
                </div>
                <div class="modal-body">
                	 <table id="tb_resource" ></table>
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
<script src="${BasePath}/res/bootstrap/js/table/bootstrap-table.min.js"></script>
<script src="${BasePath}/res/bootstrap/js/table/bootstrap-table-zh-CN.min.js"></script>
<script>
var basePath = "${BasePath!''}";
var roleId = "${systemRoleVo.id!''}";
</script>
<script src="${BasePath}/res/bizmgt/js/system/roleresource.js"></script>
</html>
