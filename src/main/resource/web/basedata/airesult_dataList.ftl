<#include "../common/public_macro.ftl">  
  
<@header title="AI结果描述">  
</@header>


<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
	<@navigation title="添AI结果描述">  
	</@navigation>
	<div class="content-wrapper">
		 <section class="content-header">
	      <h1>
	        AI结果描述
	      </h1>
	      <ol class="breadcrumb">
	        <@index_page></@index_page>
	        <li><a href="#">AI结果描述</a></li>
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
			                        <div class="col-sm-10" style="text-align:right;">
			                            <button type="button" style="margin-left:50px" id="btn_query" class="btn btn-primary">
			                            	<i class="glyphicon glyphicon-search"></i> 查询
			                            </button>
			                        </div>
			            	</form>
	                        <div id="toolbar">
						        <button id="add"  class="btn btn-default">
						            <i class="glyphicon glyphicon-plus"></i> 新增
						        </button>
						        <button id="update" class="btn btn-default">
						            <i class="glyphicon glyphicon-edit"></i> 修改
						        </button>
						        <button id="delete" class="btn btn-danger">
						            <i class="glyphicon"></i> 删除
						        </button>
						    </div>
	                        <table id="tb_airesults" >
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
        <div class="modal-dialog"  role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">新增AI结果描述</h4>
                </div>
                <div class="modal-body">
					<form id="addAiResult" class="form-horizontal"  method="post">
	                    <div class="form-group">
	                        <label for="aiCode" class="col-sm-3 control-label">AI编号:</label>
	                        <div class="col-sm-9">
	                        	
	                        	<input type="text" name="aiCode" class="form-control" id="aiCode" placeholder="AI编号">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="description" class="col-sm-3 control-label">描述:</label>
	                        <div class="col-sm-9">
	                        	<input type="text" name="description" class="form-control" id="description" placeholder="描述">
	                        </div>
	                    </div>
	                </div>
	                <div class="modal-footer">
	                	<button type="submit" id="btn_submit" class="btn btn-primary">保存</button>
		                <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
		            </div>
                </form>
            </div>
        </div>
    </div>
	<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog" style="" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">修改AI结果描述</h4>
                </div>
                <div class="modal-body">
					<form id="updateAiResult" class="form-horizontal"  method="post">
	                    <div class="form-group">
	                        <label for="aiCode" class="col-sm-3 control-label">AI编号:</label>
	                        <div class="col-sm-9">
	                        <input type="hidden" name="id" class="form-control" id="id">
	                        	<input type="text" name="aiCode" class="form-control" id="aiCode" placeholder="AI编号" readonly>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="description" class="col-sm-3 control-label">描述:</label>
	                        <div class="col-sm-9">
	                        	<input type="text" name="description" class="form-control" id="description" placeholder="描述">
	                        </div>
	                    </div>
	                </div>
	                <div class="modal-footer">
	                	<button type="submit" id="btn_submit" class="btn btn-primary">保存</button>
		                <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
		            </div>
                </form>
            </div>
        </div>
    </div>
</body>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="${BasePath}/res/bootstrap/js/table/css/bootstrap-table.min.css">
<link rel="stylesheet" href="${BasePath}/res/bootstrap/js/validator/css/bootstrapValidator.css"/>

<!-- Latest compiled and minified JavaScript -->
<script src="${BasePath}/res/bootstrap/js/table/bootstrap-table.min.js"></script>
<script src="${BasePath}/res/bizmgt/js/basedata/airesult.js"></script>
<!-- Latest compiled and minified Locales -->
<script src="${BasePath}/res/bootstrap/js/table/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${BasePath}/res/bootstrap/js/validator/js/bootstrapValidator.js"></script>
<script>
var basePath = "${BasePath!''}";
var imgRes = "${imgRes!''}";
</script>
</html>
