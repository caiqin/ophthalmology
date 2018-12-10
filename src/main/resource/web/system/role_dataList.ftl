<#include "../common/public_macro.ftl">  
  
<@header title="角色管理">  
</@header>


<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
	<@navigation title="角色管理">  
	</@navigation>
	<div class="content-wrapper">
	    <!-- Content Header (Page header) -->
	    <section class="content-header">
	      <h1>
	        角色管理
	      </h1>
	      <ol class="breadcrumb">
	        <@index_page></@index_page>
	        <li><a href="#">角色管理</a></li>
	      </ol>
	    </section>
	    <!-- Main content -->
	    <section class="content">
	        <!-- /.row -->
	        <div class="row" style="padding-top:10px;">
	            <div class="col-lg-12">
	                <div class="box box-primary">
			            <div class="box-body">
			                <form id="formSearch" class="form-horizontal">
			                    <div class="form-group" style="margin-top:15px">
			                        <label class="control-label col-sm-2" for="roleName">角色名</label>
			                        <div class="col-sm-3">
			                            <input type="text" class="form-control" id="roleName" placeholder="角色名">
			                        </div>
			                        <div class="col-sm-2" style="text-align:left;">
			                            <button type="button" id="btn_query" class="btn btn-primary">
			                            	<i class="glyphicon glyphicon-search"></i> 查询
			                            </button>
			                        </div>
			                    </div>
			            	</form>
	                        <div id="toolbar">
						        <button id="btn_add" class="btn btn-default" data-toggle="modal"  data-target="#addModal">
						            <i class="glyphicon glyphicon-plus"></i> 新增
						        </button>
						        <button id="btn_update" class="btn btn-default">
						            <i class="glyphicon glyphicon-edit"></i> 修改
						        </button>
						        <button id="add_permission" class="btn btn-default">
						            <i class="glyphicon glyphicon-cog"></i> 分配权限
						        </button>
						        <button id="btn_stop" class="btn btn-default">
						             停用
						        </button>
						        <button id="btn_delete" class="btn btn-danger">
						            <i class="glyphicon glyphicon-remove"></i> 删除
						        </button>
						    </div>
	                        <table id="tb_roles" >
	                        </table>
		                </div>
		                <!-- /.panel-body -->
		            </div>
		            <!-- /.panel -->
		        </div>
		        <!-- /.col-lg-12 -->
		    </div>
	   </section>
	</div>
	<@footer></@footer>
</div>
    
    
	<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">添加角色</h4>
                </div>
                <div class="modal-body">
					<form id="addRole" class="form-horizontal" method="post">
	                    <div class="form-group">
	                        <label for="roleName" class="col-sm-2 control-label">角色名</label>
	                        <div class="col-sm-4">
	                        	<input type="text" name="roleName" class="form-control" id="roleName" placeholder="角色名">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="remark" class="col-sm-2 control-label">备注</label>
	                        <div class="col-sm-5" style="padding-top: 7px;">
	                        	<textarea name="remark" class="form-control" rows=4 id="remark" placeholder="备注"></textarea>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="isValid" class="col-sm-2 control-label">是否启用</label>
	                        <div class="col-sm-5">
	                        	<select id="isValid" class="form-control" name="isValid" > 
                                    <option value="1" selected>是</option> 
                                    <option value="0">否</option>      
                               </select>
	                        </div>
	                    </div>
	                </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                    <button type="button" id="btn_submit" class="btn btn-primary">保存</button>
                </div>
            </div>
        </div>
    </div>
	<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">修改角色</h4>
                </div>
                <div class="modal-body">
					<form id="updateRole" class="form-horizontal" method="post">
						<input type="hidden" id="id" name="id">
	                    <div class="form-group">
	                        <label for="roleName" class="col-sm-2 control-label">角色名</label>
	                        <div class="col-sm-4">
	                        	<input type="text" name="roleName" class="form-control" id="roleName" placeholder="角色名">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="remark" class="col-sm-2 control-label">备注</label>
	                        <div class="col-sm-5" style="padding-top: 7px;">
	                        	<textarea name="remark" class="form-control" rows=4 id="remark" placeholder="备注"></textarea>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="isValid" class="col-sm-2 control-label">是否启用</label>
	                        <div class="col-sm-5">
	                        	<select id="isValid" class="form-control" name="isValid" > 
                                    <option value="1" selected>是</option> 
                                    <option value="0">否</option>      
                               </select>
	                        </div>
	                    </div>
	                </form>
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
<script src="${BasePath}/res/bizmgt/js/system/role.js"></script>

<!-- Latest compiled and minified Locales -->
<script src="${BasePath}/res/bootstrap/js/table/bootstrap-table-zh-CN.min.js"></script>
<script>
var basePath = "${BasePath!''}";
</script>
</html>
