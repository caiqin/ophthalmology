<#include "../common/public_macro.ftl">  
  
<@header title="AI诊断记录管理">  
</@header>

<style media="print">
    @page {
      size: auto;  /* auto is the initial value */
      margin: 0mm; /* this affects the margin in the printer settings */
    }
</style>
<style>



@media print {
    body.modalprinter * {
        visibility: hidden;
    }

    body.modalprinter .modal-dialog.focused {
        position: absolute;
        padding: 0;
        margin-left: 35px;
        left: 0;
        top: 0;
    }

    body.modalprinter .modal-dialog.focused .modal-content {
        border-width: 0;
    }

    body.modalprinter .modal-dialog.focused .modal-content .modal-header .modal-title,
    body.modalprinter .modal-dialog.focused .modal-content .modal-body,
    body.modalprinter .modal-dialog.focused .modal-content .modal-body * {
        visibility: visible;
    }

    body.modalprinter .modal-dialog.focused .modal-content .modal-header,
    body.modalprinter .modal-dialog.focused .modal-content .modal-body {
        padding: 0;
    }

    body.modalprinter  .modal-title {
        text-align:center;
    }
    body.modalprinter .modal-dialog.focused .modal-content .modal-header .modal-title {
        margin-bottom: 20px;
    }
    body.modalprinter .modal-footer{
    	display:none;
    }
    
}
</style>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
	<@navigation title="AI诊断记录管理">  
	</@navigation>

        <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        AI诊断记录管理
      </h1>
      <ol class="breadcrumb">
        <@index_page></@index_page>
        <li><a href="#">AI诊断记录管理</a></li>
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
						        <button id="btn_report" class="btn btn-default" data-toggle="modal">
						            <i class="glyphicon"></i> 报告
						        </button>
						    </div>
	                        <table id="tb_ai_diagnose" >
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
<div class="modal fade printable" id="reportModal" tabindex="-1" backdrop='static' keyboard='false' role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
      <div class="modal-dialog modal-lg" style="width:610px;" role="document">
        <div class="modal-content">
          <div class="modal-header" style="align:center;">
             <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
             <h4 class="modal-title" id="myModalLabel" style="">AI诊断报告</h4>
          </div>
          <div class="modal-body"  id="galley">
             <label for="" class="col-sm-3 control-label">患者姓名:&nbsp;&nbsp;<dis id="patientName"></dis></label>
             <label for="" class="col-sm-2 control-label">性别:&nbsp;&nbsp;<dis id="gender"></dis></label>
             <label for="" class="col-sm-2 control-label">年龄:&nbsp;&nbsp;<dis id="age"></dis></label>
             <label for="" class="col-sm-5 control-label">身份证:&nbsp;&nbsp;<dis id="idCard"></dis></label>
             <label for="" class="col-sm-5 control-label">检查医院:&nbsp;&nbsp;<dis id="hospitalName"></dis></label>
             <label for="" class="col-sm-5 control-label">检查时间:&nbsp;&nbsp;<dis id="testTime"></dis></label>
             
             <table id="" class="table table-bordered">
            	<tbody id="already_role">
            		<tr>
	            		<td style='text-align: center;width:50%;' colspan=6>右眼</td>
	            		<td style='text-align: center;width:50%;' colspan=6>左眼</td>
            		</tr>
            		<tr>
	            		<td style='text-align: center;' colspan=6>
	            			<ul class="pictures" id="pictures_right" style="margin: 0; padding: 0;list-style: none;min-height:190px;height:auto;float:left;position:relative;left:50%;"></ul>		
	            		</td>
	            		<td style='text-align: center;' colspan=6>
	            			<ul class="pictures" id="pictures_left" style="margin: 0; padding: 0;list-style: none;min-height:190px;height:auto;float:left;position:relative;left:50%;"></ul>		
	            		</td>
            		</tr>
            		<tr id="ai_tr">
            			<td colspan=1>AI诊断结果</td>
	            		<td style='text-align: center;' colspan=5>
	            				<div id='right_ai'></div>	
	            		</td>
	            		<td style='text-align: center;' colspan=6>
	            				<div id='left_ai'></div>	
	            		</td>
            		</tr>
            	</tbody>
            </table>
	        </div>
          <div class="modal-footer">
            <button type="button" id="btn_print" class="btn btn-default">打印</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          </div>
    </div>
</div>
</body>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="${BasePath}/res/bootstrap/js/table/css/bootstrap-table.min.css">
<link rel="stylesheet" href="${BasePath}/res/bootstrap/js/validator/css/bootstrapValidator.css"/>

<script src="${BasePath}/res/bootstrap/js/printThis.js"></script>
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
<script src="${BasePath}/res/bizmgt/js/diagnose/aidiagnose.js"></script>

<script>
var basePath = "${BasePath!''}";
var imgRes = "${imgRes!''}";
$(function(){
});

</script>
</html>
