<#include "../common/public_macro.ftl">  
  
<#assign fileTitle="诊断报表"/>  
  
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
			                        <label class="control-label col-sm-2" for="aiResult">AI结果</label>
			                        <div class="col-sm-3">
			                            <select class="form-control select2" id="aiResult" name="aiResult">
			                            	<option value=' '>请选择</option>
			                            	<option value='1'>未诊断</option>
			                            	<option value='2'>无效图像</option>
			                            	<option value='3'>阴性</option>
			                            	<option value='4'>阳性</option>
			                            </select>
			                        </div>
			                        <label class="control-label col-sm-2" for="diagnoseResult">专家诊断结果</label>
			                        <div class="col-sm-3">
			                            <select class="form-control select2" id="diagnoseResult" name="diagnoseResult">
			                            	<option value=' '>请选择</option>
			                            	<option value='1'>未诊断</option>
			                            	<option value='2'>无效图像</option>
			                            	<option value='3'>阴性</option>
			                            	<option value='4'>阳性</option>
			                            </select>
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-top:15px">
			                        <label class="control-label col-sm-2" for="doctorName">医生姓名</label>
			                        <div class="col-sm-3">
			                            <input type="text" class="form-control" id="doctorName" name="doctorName" placeholder="医生姓名">
			                        </div>
			                        <label class="control-label col-sm-2" for="hospitalName">医院名称</label>
			                        <div class="col-sm-3">
			                            <input type="text" class="form-control" id="hospitalName" placeholder="医院名称">
			                            <input type="hidden" class="form-control" name="hospitalId" id="hospitalId">
			                        </div>
			                    </div>
			                        
				                	<div class="form-group" style="margin-top:15px">
				                        <label class="control-label col-sm-2" for="startTime">诊断时间</label>
				                        <div class="col-sm-3">
				                            <input type="text" class="form-control" name="startTime" id="startTime" placeholder="开始时间">
				                        </div>
				                        <div class="col-sm-3">
				                            <input type="text" class="form-control" name="endTime" id="endTime" placeholder="结束时间">
				                        </div>
				                        <div class="col-sm-3 col-sm-offset-1" style="text-align:left;">
				                            <button type="button" style="" id="btn_query" class="btn btn-primary">
				                            	<i class="glyphicon glyphicon-search"></i> 查询
				                            </button>
				                            <button type="reset" style="" id="btn_reset" class="btn btn-default">
				                            	<i class="glyphicon glyphicon-reset"></i> 重置
				                            </button>
				                        </div>
					                </div>
			            	</form>
			            	<div id="toolbar">
		            			<button type="button" id="btn_export" class="btn btn-primary">
	                            	<i class="glyphicon glyphicon-export"></i> 导出
	                            </button>
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
<div class="modal fade" id="showHospitalModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">选择医院</h4>
                </div>
                <div class="modal-body">
                	<div id="tree" style="height:460px;position: relative;  overflow-y: scroll;"></div>
	            </div>
	              <div class="modal-footer">
	                <button type="button" style="margin-left:0px" id="btn_submit" class="btn btn-primary" data-dismiss="modal">确定</button>
	                <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
	            </div>
            </div>
        </div>
    </div>
</body>
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
<script src="${BasePath}/res/bizmgt/js/diagnose/diagnoserecord_report.js"></script>

<!-- Latest compiled and minified JavaScript -->
<script src="${BasePath}/res/bootstrap/js/tree/bootstrap-treeview.min.css"></script>

<!-- Latest compiled and minified Locales -->
<script src="${BasePath}/res/bootstrap/js/tree/bootstrap-treeview.min.js"></script>
  <link rel="stylesheet" href="${BasePath}/res/bower_components/select2/dist/css/select2.min.css">
  <!-- Select2 -->
<script src="${BasePath}/res/bower_components/select2/dist/js/select2.full.min.js"></script>
<script src="${BasePath}/res/bower_components/select2/dist/js/i18n/zh-CN.js"></script>
<script>
var basePath = "${BasePath!''}";
$(function () {
	$('.select2').select2({
	    placeholder: "请选择",
	    width: "100%",
	    language: "zh-CN"
	});
});
</script>
</html>
