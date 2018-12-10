<#include "../common/public_macro.ftl">  
  
<@header title="医院管理">  
</@header>


<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
	<@navigation title="医院管理">  
	</@navigation>

        <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        医院管理
      </h1>
      <ol class="breadcrumb">
        <@index_page></@index_page>
        <li><a href="#">医院管理</a></li>
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
			                        <label class="control-label col-sm-2" for="doctorName">医院名称</label>
			                        <div class="col-sm-3">
			                            <input type="text" class="form-control" id="doctorName" placeholder="医院名称">
			                        </div>
			                        <div class="col-sm-2" style="text-align:left;">
			                            <button type="button" style="margin-left:50px" id="btn_query" class="btn btn-primary">
			                            	<i class="glyphicon glyphicon-search"></i> 查询
			                            </button>
			                        </div>
			                    </div>
			            	</form>
	                        <div id="toolbar">
						        <button id="btn_add" class="btn btn-default" data-toggle="modal">
						            <i class="glyphicon glyphicon-plus"></i> 新增
						        </button>
						        <button id="btn_update" class="btn btn-default">
						            <i class="glyphicon glyphicon-edit"></i> 修改
						        </button>
						        <button id="btn_create" class="btn btn-default" style="display:none;">
						            <i class="glyphicon glyphicon-edit"></i> 生成二维码
						        </button>
						        <button id="btn_enable" class="btn btn-default" style="display:none;">
						            <i class="glyphicon glyphicon-edit"></i> 启用
						        </button>
						        <button id="btn_show_qr" class="btn btn-default" style="display:none;">
						            <i class="glyphicon glyphicon-edit"></i> 查看二维码
						        </button>
						    </div>
	                        <table id="tb_hospitals" >
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
                    <h4 class="modal-title" id="myModalLabel">新增医院</h4>
                </div>
                <div class="modal-body">
					<form id="addHospital" class="form-horizontal"  method="post">
	                    <div class="form-group">
	                        <label for="hospitalName" class="col-sm-2 control-label">医院名称</label>
	                        <div class="col-sm-10">
	                        	<input type="text" name="hospitalName" class="form-control" id="hospitalName" placeholder="医院名称">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="hospitalAddress" class="col-sm-2 control-label">医院地址</label>
	                        <div class="col-sm-10" style="padding-top: 7px;">
	                        	<input type='text'  name="hospitalAddress" class="form-control" id="hospitalAddress" placeholder="医院地址"/>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="hospitalIntroduce" class="col-sm-2 control-label">医院介绍</label>
	                        <div class="col-sm-10">
	                        	<textarea name="hospitalIntroduce" rows="3" class="form-control" id="hospitalIntroduce" placeholder="医院介绍"></textarea>
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
                    <h4 class="modal-title" id="myModalLabel">修改医院信息</h4>
                </div>
                <div class="modal-body">
					<form id="editHospital" class="form-horizontal"  method="post">
	                   <div class="form-group">
	                        <label for="hospitalName" class="col-sm-2 control-label">医院名称</label>
	                        <div class="col-sm-10">
	                        	<input type="hidden" name="id" class="form-control" id="id" >
	                        	<input type="text" name="hospitalName" class="form-control" id="hospitalName" placeholder="医院名称">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="hospitalAddress" class="col-sm-2 control-label">医院地址</label>
	                        <div class="col-sm-10" style="padding-top: 7px;">
	                        	<input type='text'  name="hospitalAddress" class="form-control" id="hospitalAddress" placeholder="医院地址"/>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="hospitalIntroduce" class="col-sm-2 control-label">医院介绍</label>
	                        <div class="col-sm-10">
	                        	<textarea name="hospitalIntroduce" rows="3" class="form-control" id="hospitalIntroduce" placeholder="医院介绍"></textarea>
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
	<div class="modal fade" id="showModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">查看优惠二维码</h4>
                </div>
                <div class="modal-body">
					<form id="editHospital" class="form-horizontal"  method="post">
	                   <div class="form-group">
	                        <div class="col-sm-10 col-sm-offset-2">
	                        	<img id="img_qr" src="" style="width:380px;height:380px;"></img>
	                        </div>
	                    </div>
	                </form>
	            </div>
	              <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
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
<script src="${BasePath}/res/bizmgt/js/basedata/hospital.js"></script>

<!-- Latest compiled and minified Locales -->
<script src="${BasePath}/res/bootstrap/js/table/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${BasePath}/res/bootstrap/js/validator/js/bootstrapValidator.js"></script>
<script>
var basePath = "${BasePath!''}";
var imgRes = "${imgRes!''}";
</script>
</html>
