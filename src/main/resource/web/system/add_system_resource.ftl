<#include "../common/public_macro.ftl">  
  
<@header title="添加资源">  
</@header>


<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
	<@navigation title="添加资源">  
	</@navigation>
	<div class="content-wrapper">
		 <section class="content-header">
	      <h1>
	        添加资源
	      </h1>
	      <ol class="breadcrumb">
	        <@index_page></@index_page>
	        <li><a href="#">资源管理</a></li>
	        <li><a href="#">添加资源</a></li>
	      </ol>
	    </section>
	    <!-- Main content -->
	    <section class="content">
            <!-- /.row -->
            <div class="row" style="padding-top:10px;">
                <div class="col-lg-6">
                    <div class="box box-primary" style="height:508px;">
                        <div class="box-header ui-sortable-handle">
                            <h4 class="box-title">已有资源</h4>
                            <button class="btn btn-default" style="float: right;margin-top: -8px;" id="addFirstRes">添加一级菜单</button>
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
                            <h4 class="box-title">添加资源</h4>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<form id="formAdd" class="form-horizontal">
		                    	<div class="form-group" style="margin-bottom: 10px;">
			                    	<input type="hidden" name="id" id="id"/>
			                        <label for="resName" class="col-sm-3 control-label">名称</label>
			                        <div class="col-sm-7">
			                        	<input type="text" name="resName" class="form-control" id="resName" placeholder="名称">
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <label for="resUrl" class="col-sm-3 control-label">url</label>
			                        <div class="col-sm-7">
			                        	<input type="text" name="resUrl" class="form-control" id="resUrl" placeholder="url">
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <label for="resType" class="col-sm-3 control-label">资源类型</label>
			                        <div class="col-sm-7">
			                        	<select class="form-control"  id="resType" name="resType">
										  <option value="1">菜单</option>
										  <option value="2">按钮</option>
										</select>
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <label for="resCode" class="col-sm-3 control-label">资源编码</label>
			                        <div class="col-sm-7">
			                        	<input type="text" name="resCode" class="form-control" id="resCode" placeholder="资源编码">
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <label for="seqNum" class="col-sm-3 control-label">排序</label>
			                        <div class="col-sm-7">
			                        	<input type="text" name="seqNum" class="form-control" id="seqNum" placeholder="排序">
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <label for="level" class="col-sm-3 control-label">菜单层级</label>
			                        <div class="col-sm-7">
			                        	<input type="text" name="level" class="form-control" id="level" placeholder="菜单层级" readonly>
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <label for="supperResName" class="col-sm-3 control-label">父资源</label>
			                        <div class="col-sm-7">
			                        	<input type="text" name="supperResName" class="form-control" id="supperResName" placeholder="父资源" readonly>
			                        	<input  id='supperResId' name='supperResId' type="hidden"/>
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <label for="remark" class="col-sm-3 control-label">备注</label>
			                        <div class="col-sm-7">
			                        	<input type="text" name="remark" class="form-control" id="remark" placeholder="备注">
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <label for="isShow" class="col-sm-3 control-label">是否显示</label>
			                        <div class="col-sm-3" style="margin-top: 7px;">
			                        	<input type="radio" name="isShow" id="isShow" value="1" checked>是
	                        			<input type="radio" name="isShow" style="margin-left: 10px;" id="isShow" value="0" >否
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
	var resourceList = ${resourceList!''};
</script>

<script type="text/javascript" src="${BasePath}/res/bootstrap/js/validator/js/bootstrapValidator.js"></script>
<script src="${BasePath}/res/bizmgt/js/system/add_system_resource.js"></script>
</html>
