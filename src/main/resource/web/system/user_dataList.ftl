<#include "../common/public_macro.ftl">  
  
<@header title="用户管理">  
</@header>


<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
	<@navigation title="用户管理">  
	</@navigation>

        <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        用户管理
      </h1>
      <ol class="breadcrumb">
        <@index_page></@index_page>
        <li><a href="#">用户管理</a></li>
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
			                        <label class="control-label col-sm-2" for="txt_search_statu">登录名</label>
			                        <div class="col-sm-3">
			                            <input type="text" class="form-control" id="loginName" placeholder="登录名">
			                        </div>
			                        <label class="control-label col-sm-2" style="width:10%;" for="txt_search_statu">真实姓名</label>
			                        <div class="col-sm-3">
			                            <input type="text" class="form-control" id="userName" placeholder="真实姓名">
			                        </div>
			                        <div class="col-sm-2" style="text-align:left;">
			                            <button type="button" style="margin-left:50px" id="btn_query" class="btn btn-primary">
			                            	<i class="glyphicon glyphicon-search"></i> 查询
			                            </button>
			                        </div>
			                    </div>
			            	</form>
	                        <div id="toolbar">
						        <button id="add" class="btn btn-default" data-toggle="modal"  data-target="#addModal">
						            <i class="glyphicon glyphicon-plus"></i> 新增
						        </button>
						        <button id="update" class="btn btn-default">
						            <i class="glyphicon glyphicon-edit"></i> 修改
						        </button>
						        <button id="addRole" class="btn btn-default">
						            <i class="glyphicon glyphicon-plus"></i> 添加角色
						        </button>
						        <button id="updatePassword" class="btn btn-default">
						            <i class="glyphicon glyphicon-edit"></i> 修改密码
						        </button>
						        <button id="lock" class="btn btn-danger">
						            <i class="fa fa-lock"></i> 锁定
						        </button>
								<button id="relationDoctor" class="btn btn-default">
						            <i class="glyphicon glyphicon-link"></i> 关联医生
						        </button>
						    </div>
	                        <table id="tb_users" >
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
	<div class="modal fade" id="addModal" tabindex="-1" backdrop='static' keyboard='false' role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">添加用户</h4>
                </div>
                <div class="modal-body">
					<form id="addUser" class="form-horizontal"  method="post">
	                    <div class="form-group">
	                        <label for="userName" class="col-sm-2 control-label">真实姓名</label>
	                        <div class="col-sm-4">
	                        	<input type="text" name="userName" class="form-control" id="userName" placeholder="真实姓名">
	                        </div>
	                        <label for="sex" class="col-sm-2 control-label">性别</label>
	                        <div class="col-sm-4" style="padding-top: 7px;">
	                        	<input type="radio" name="sex" id="sex" value="1" checked>男
	                        	<input type="radio" name="sex" style="margin-left: 10px;" id="sex" value="0" >女
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="loginName" class="col-sm-2 control-label">登录名</label>
	                        <div class="col-sm-4">
	                        	<input type="text" name="loginName" class="form-control" id="loginName" placeholder="登录名">
	                        </div>
	                        <label for="loginPassword" class="col-sm-2 control-label">密码</label>
	                        <div class="col-sm-4">
	                        	<input type="password" name="loginPassword" class="form-control" id="loginPassword" placeholder="密码">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="email" class="col-sm-2 control-label">Email</label>
	                        <div class="col-sm-4">
	                        	<input type="text" name="email" class="form-control" id="email" placeholder="Email">
	                        </div>
	                        <label for="telPhone" class="col-sm-2 control-label">电话</label>
	                        <div class="col-sm-4">
	                        	<input type="text" name="telPhone" class="form-control" id="telPhone" placeholder="电话">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="mobilePhone" class="col-sm-2 control-label">手机</label>
	                        <div class="col-sm-4">
	                        	<input type="text" name="mobilePhone" class="form-control" id="mobilePhone" placeholder="手机">
	                        </div>
	                        <label for="QQNum" class="col-sm-2 control-label">QQ</label>
	                        <div class="col-sm-4">
	                        	<input type="text" name="QQNum" class="form-control" id="QQNum" placeholder="QQ">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                    	<div class="col-lg-8 col-lg-offset-4">
		                    	<button type="submit" id="btn_submit" class="btn btn-primary">保存</button>
		                    	<button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
		                    </div>
		                </div>
	                </form>
                </div>
            </div>
        </div>
    </div>
	<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">修改用户</h4>
                </div>
					<form id="editUser" class="form-horizontal"  method="post">
                <div class="modal-body">
	                    <div class="form-group">
	                    	<input type="hidden" name="id" id="id"/>
	                        <label for="userName" class="col-sm-2 control-label">真实姓名</label>
	                        <div class="col-sm-4">
	                        	<input type="text" name="userName" class="form-control" id="userName" placeholder="真实姓名">
	                        </div>
	                        <label for="sex" class="col-sm-2 control-label">性别</label>
	                        <div class="col-sm-4" style="padding-top: 7px;">
	                        	<input type="radio" name="sex" id="sex" value="1" checked>男
	                        	<input type="radio" name="sex" style="margin-left: 10px;" id="sex" value="0" >女
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="loginName" class="col-sm-2 control-label">登录名</label>
	                        <div class="col-sm-4">
	                        	<input type="text" name="loginName" class="form-control" id="loginName" placeholder="登录名" readonly>
	                        </div>
	                        <label for="txt_statu" class="col-sm-2 control-label">Email</label>
	                        <div class="col-sm-4">
	                        	<input type="text" name="email" class="form-control" id="email" placeholder="Email">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="telPhone" class="col-sm-2 control-label">电话</label>
	                        <div class="col-sm-4">
	                        	<input type="text" name="telPhone" class="form-control" id="telPhone" placeholder="电话">
	                        </div>
	                        <label for="mobilePhone" class="col-sm-2 control-label">手机</label>
	                        <div class="col-sm-4">
	                        	<input type="text" name="mobilePhone" class="form-control" id="mobilePhone" placeholder="手机">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="QQNum" class="col-sm-2 control-label">QQ</label>
	                        <div class="col-sm-4">
	                        	<input type="text" name="QQNum" class="form-control" id="QQNum" placeholder="QQ">
	                        </div>
	                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                    <button type="submit" id="btn_submit_update" class="btn btn-primary">保存</button>
                </div>
	                </form>
            </div>
        </div>
    </div>
    <div class="modal fade" id="updatePasswordModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">修改密码</h4>
                </div>
                <div class="modal-body">
					<form id="editUserPassword" class="form-horizontal"  method="post">
	                    <div class="form-group">
	                    	<input type="hidden" id="id">
	                        <label for="loginPassword" class="col-sm-2 control-label">新密码</label>
	                        <div class="col-sm-4">
	                        	<input type="password" name="loginPassword" class="form-control" id="loginPassword" placeholder="新密码">
	                        </div>
	                        <label for="rePassword" class="col-sm-2 control-label">确认密码</label>
	                        <div class="col-sm-4">
	                        	<input type="password" name="rePassword" class="form-control" id="rePassword" placeholder="确认密码">
	                        </div>
	                    </div>
	                </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                    <button type="button" id="btn_update_password" class="btn btn-primary">保存</button>
                </div>
            </div>
        </div>
    </div>
<div class="modal fade" id="relationModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">关联医生</h4>
                </div>
                <div class="modal-body">
                	<form id="formSearchCustom" class="form-horizontal">
	                    <div class="form-group" style="margin-top:15px">
	                        <label class="control-label col-sm-3" for="doctorName">医生姓名</label>
	                        <div class="col-sm-4">
	                            <input type="text" class="form-control" id="doctorName" placeholder="医生姓名">
	                        </div>
	                        <div class="col-sm-2" style="text-align:left;">
	                            <button type="button" style="margin-left:50px" id="btn_query_doctor" class="btn btn-primary">
	                            	<i class="glyphicon glyphicon-search"></i> 查询
	                            </button>
	                        </div>
	                    </div>
	            	</form>
                	<table id="tb_doctors"></table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                    <button type="button" id="btn_relation" class="btn btn-primary">关联</button>
                </div>
            </div>
        </div>
    </div>
</body>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="${BasePath}/res/bootstrap/js/table/css/bootstrap-table.min.css">
<link rel="stylesheet" href="${BasePath}/res/bootstrap/js/validator/css/bootstrapValidator.css"/>

<!-- Latest compiled and minified JavaScript -->
<script src="${BasePath}/res/bootstrap/js/table/bootstrap-table.min.js"></script>
<script src="${BasePath}/res/bizmgt/js/system/user.js"></script>

<!-- Latest compiled and minified Locales -->
<script src="${BasePath}/res/bootstrap/js/table/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${BasePath}/res/bootstrap/js/validator/js/bootstrapValidator.js"></script>
<script>
var basePath = "${BasePath!''}";
</script>
</html>
