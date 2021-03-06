<#include "../common/public_macro.ftl">  
  
<@header title="添加医院">  
</@header>


<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
	<@navigation title="添加医院">  
	</@navigation>
	<div class="content-wrapper">
		 <section class="content-header">
	      <h1>
	        添加医院
	      </h1>
	      <ol class="breadcrumb">
	        <@index_page></@index_page>
	        <li><a href="#">医院管理</a></li>
	        <li><a href="#">添加医院</a></li>
	      </ol>
	    </section>
	    <!-- Main content -->
	    <section class="content">
            <!-- /.row -->
            <div class="row" style="padding-top:10px;">
                <div class="col-lg-6">
                    <div class="box box-primary" style="height:508px;">
                        <div class="box-header ui-sortable-handle">
                            <h4 class="box-title">已有医院</h4>
                            <button class="btn btn-default" style="float: right;margin-top: -8px;" id="addFirstHospital">添加一级医院</button>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body" style="padding: 0;">
                        	<div id="tree" style="height:460px;position: relative;  overflow-y: scroll;"></div>
                		</div>
                	</div>
                </div>
                <div class="col-lg-6">
                    <div class="box box-success">
                        <div class="box-header">
                            <h4 class="box-title">添加医院</h4>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<form id="formAdd" class="form-horizontal">
		                    	<div class="form-group" style="margin-bottom: 10px;">
			                    	<input type="hidden" name="id" id="id"/>
			                        <label for="hospitalName" class="col-sm-3 control-label">医院名称</label>
			                        <div class="col-sm-7">
			                        	<input type="text" name="hospitalName" class="form-control" id="hospitalName" placeholder="医院名称">
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <label for="seqNum" class="col-sm-3 control-label">医院地址</label>
			                        <div class="col-sm-7">
			                        	<input type="text" name="hospitalAddress" class="form-control" id="hospitalAddress" placeholder="医院地址">
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <label for="level" class="col-sm-3 control-label">层级</label>
			                        <div class="col-sm-7">
			                        	<input type="text" name="level" class="form-control" id="level" placeholder="层级" readonly>
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <label for="supperResName" class="col-sm-3 control-label">上级医院</label>
			                        <div class="col-sm-7">
			                        	<input type="text" name="parentName" class="form-control" id="parentName" placeholder="上级医院" readonly>
			                        	<input  id='parentNo' name='parentNo' type="hidden"/>
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <label for="remark" class="col-sm-3 control-label">医院介绍</label>
			                        <div class="col-sm-7">
			                        	<textarea name="hospitalIntroduce" rows="3" class="form-control" id="hospitalIntroduce" placeholder="医院介绍"></textarea>
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <div class="col-sm-2  col-md-offset-4">
				                        <button type="submit" style="margin-left:0px" id="btn_save" class="btn btn-primary">添加</button>
				                     </div>
				                     <div class="col-sm-2">
				                        <button type="button" style="margin-left:0px" onclick="javascript:history.back()" class="btn btn-primary">返回</button>
				                    </div>
			                    </div>
				            </form>
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
</body>

<!-- Latest compiled and minified JavaScript -->
<script src="${BasePath}/res/bootstrap/js/tree/bootstrap-treeview.min.css"></script>
<link rel="stylesheet" href="${BasePath}/res/bootstrap/js/validator/css/bootstrapValidator.css"/>

<!-- Latest compiled and minified Locales -->
<script src="${BasePath}/res/bootstrap/js/tree/bootstrap-treeview.min.js"></script>

<script>
	var basePath = "${BasePath!''}";
	var hospitalList = ${hospitalList!''};
	
</script>

<script type="text/javascript" src="${BasePath}/res/bootstrap/js/validator/js/bootstrapValidator.js"></script>
<script src="${BasePath}/res/bizmgt/js/basedata/add_hospital.js"></script>
</html>
