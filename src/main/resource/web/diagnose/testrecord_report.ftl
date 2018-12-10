<#include "../common/public_macro.ftl">  
  
<#assign fileTitle="检查报表"/>  
  
<@header title=fileTitle>  
</@header>
<style>
  </style>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
	<@navigation title=fileTitle>  
	</@navigation>

        <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        ${fileTitle}
      </h1>
      <ol class="breadcrumb">
        <@index_page></@index_page>
        <li><a href="#">${fileTitle}</a></li>
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
			                        <label class="control-label col-sm-2" for="startTime">开始时间</label>
			                        <div class="col-sm-3">
			                            <input type="text" class="form-control" id="startTime" placeholder="开始时间">
			                        </div>
			                        <label class="control-label col-sm-2" for="endTime">结束时间</label>
			                        <div class="col-sm-3">
			                            <input type="text" class="form-control" id="endTime" placeholder="结束时间">
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-top:15px">
			                        <label class="control-label col-sm-2" for="patientName">患者姓名</label>
			                        <div class="col-sm-3">
			                            <input type="text" class="form-control" id="patientName" placeholder="患者姓名">
			                        </div>
			                        <label class="control-label col-sm-2" for="hospitalName">医院名称</label>
			                        <div class="col-sm-3">
			                            <input type="text" class="form-control" id="hospitalName" placeholder="医院名称">
			                        </div>
			                        <div class="col-sm-2" style="text-align:left;">
			                            <button type="button" style="margin-left:50px" id="btn_query" class="btn btn-primary">
			                            	<i class="glyphicon glyphicon-search"></i> 查询
			                            </button>
			                        </div>
			                    </div>
			            	</form>
			            	<div id="toolbar">
						    </div>
	                        <table id="tb_testrecords" >
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

<!-- Modal -->
    <div class="modal fade" id="imageModal" tabindex="-1" backdrop='static' keyboard='false' role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
      <div class="modal-dialog" style="width:65%" role="document">
        <div class="modal-content">
          <div class="modal-header">
             <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
             <h4 class="modal-title" id="myModalLabel">图像详情</h4>
          </div>
          <div class="modal-body"  id="galley">
              <ul class="pictures" style="margin: 0; padding: 0;list-style: none;min-height:190px;height:auto;">
              </ul>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
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
<!-- Select2 -->
  <link rel="stylesheet" href="${BasePath}/res/bower_components/select2/dist/css/select2.min.css">
  <!-- Select2 -->
<script src="${BasePath}/res/bower_components/select2/dist/js/select2.full.min.js"></script>
<script src="${BasePath}/res/bower_components/select2/dist/js/i18n/zh-CN.js"></script>
<!-- Latest compiled and minified Locales -->
<script src="${BasePath}/res/bootstrap/js/table/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${BasePath}/res/bootstrap/js/validator/js/bootstrapValidator.js"></script>
<script type="text/javascript" src="${BasePath}/res/bootstrap/js/viewer/viewer.min.js"></script>
<link rel="stylesheet" href="${BasePath}/res/bootstrap/js/viewer/viewer.min.css"/>
<script src="${BasePath}/res/bizmgt/js/diagnose/testrecord_report.js"></script>

<script>
var basePath = "${BasePath!''}";


</script>
</html>
