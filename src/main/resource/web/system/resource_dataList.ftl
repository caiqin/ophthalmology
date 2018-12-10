<#include "../common/public_macro.ftl">  
  
<@header title="资源管理">  
</@header>


<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
	<@navigation title="资源管理">  
	</@navigation>
 <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        资源管理
      </h1>
      <ol class="breadcrumb">
        <@index_page></@index_page>
        <li><a href="#">资源管理</a></li>
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
			                        <label class="control-label col-sm-2" for="resName">资源名称</label>
			                        <div class="col-sm-3">
			                            <input type="text" class="form-control" id="resName" placeholder="资源名称">
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
						            <i class="glyphicon glyphicon-plus"></i> 添加
						        </button>
						        <button id="btn_update" class="btn btn-default">
						            <i class="glyphicon glyphicon-edit"></i> 修改
						        </button>
						        <button id="btn_delete" class="btn btn-danger">
						            <i class="glyphicon glyphicon-remove"></i> 删除
						        </button>
						    </div>
	                        <table id="tb_resources" >
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
</body>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="${BasePath}/res/bootstrap/js/table/css/bootstrap-table.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="${BasePath}/res/bootstrap/js/table/bootstrap-table.min.js"></script>
<script src="${BasePath}/res/bizmgt/js/system/resource.js"></script>

<!-- Latest compiled and minified Locales -->
<script src="${BasePath}/res/bootstrap/js/table/bootstrap-table-zh-CN.min.js"></script>
<script>
var basePath = "${BasePath!''}";
</script>
</html>
