<#include "../common/public_macro.ftl">  
  
<@header title="医院编号">  
</@header>


<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
	<@navigation title="医院编号">  
	</@navigation>
	<div class="content-wrapper">
		 <section class="content-header">
	      <h1>
	        医院编号
	      </h1>
	      <ol class="breadcrumb">
	        <@index_page></@index_page>
	        <li><a href="#">医院编号</a></li>
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
			                        <div class="col-sm-10 col-sm-offset-2" style="text-align:right;">
			                            <button type="button" style="margin-left:50px" id="btn_query" class="btn btn-primary">
			                            	<i class="glyphicon glyphicon-search"></i> 查询
			                            </button>
			                        </div>
			            	</form>
	                        <div id="toolbar">
						        <button id="import"  class="btn btn-default">
						            <i class="glyphicon glyphicon-plus"></i> 导入
						        </button>
						        <button id="delete" class="btn btn-danger">
						            <i class="glyphicon"></i> 删除
						        </button>
						    </div>
	                        <table id="tb_hospitalids" >
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
                    <h4 class="modal-title" id="myModalLabel">导入医院编号</h4>
                </div>
                <div class="modal-body">
					<form id="form_import"  method="post" action="${BasePath!''}/hospitalid/import" enctype="multipart/form-data">
	                    <div class="form-group">
	                    	<a href='https://jiaxunjiankang.com/ophthalmology/hospital/医院免费码.xls'>下载导入模板</a>
						</div>					
	                    <div class="form-group">
	                        	<label for="file">请选择文件:</label>
	                        	<div id="upload_file">
	                        		<input type="file" name="importFile" id="importFile" multiple  data-show-preview="false"  data-min-file-count="1"/>
	                        	</div>
	                    </div>
	                </div>
	                <div class="modal-footer">
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
<script src="${BasePath}/res/bizmgt/js/basedata/hospitalid.js"></script>
<!-- Latest compiled and minified Locales -->
<script src="${BasePath}/res/bootstrap/js/table/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${BasePath}/res/bootstrap/js/validator/js/bootstrapValidator.js"></script>

<link rel="stylesheet" href="${BasePath}/res/bootstrap/css/fileinput.min.css"/>
<script src="${BasePath}/res/bootstrap/js/fileinput.js"></script>
<script src="${BasePath}/res/bootstrap/js/locales/zh.js"></script>

<script>
var basePath = "${BasePath!''}";
var imgRes = "${imgRes!''}";
</script>
</html>
