<#include "../common/public_macro.ftl">  
  
<@header title="专家确诊记录管理">  
</@header>

<style media="print">
    @page {
      size: auto;  /* auto is the initial value */
      margin: 0mm; /* this affects the margin in the printer settings */
    }
</style>
<style>


.demo-box { display:none;}
@media print {
	body.modalprinter .demo-box { display:block;position:relative; width:630px;height:120px; overflow:hidden;}
	body.modalprinter .demo-box img { position:absolute;left:50%;transform:translateX(-50%); }
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
	<@navigation title="专家确诊记录管理">  
	</@navigation>

        <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        专家确诊记录管理
      </h1> 
      <ol class="breadcrumb">
        <@index_page></@index_page>
        <li><a href="#">专家确诊记录管理</a></li>
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
			                        <label class="control-label col-sm-2" for="doctorName">医生</label>
			                        <div class="col-sm-3">
			                            <input type="text" class="form-control" id="doctorName" name="doctorName" placeholder="医生">
			                        </div>
			                        <label class="control-label col-sm-2" for="patientName">患者姓名</label>
			                        <div class="col-sm-3">
			                            <input type="text" class="form-control" id="patientName" name="patientName" placeholder="患者姓名">
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-top:15px">
			                        <label class="control-label col-sm-2" for="doctorName">确诊时间</label>
			                        <div class="col-sm-3">
			                            <input type="text" class="form-control" id="startTime" name="startTime" placeholder="开始时间">
			                        </div>
			                        <div class="col-sm-3">
			                            <input type="text" class="form-control" id="endTime" name="endTime" placeholder="结束时间">
			                        </div>
			                        <div class="col-sm-4 " style="text-align:left;">
			                            <button type="button" style="margin-left:50px" id="btn_query" class="btn btn-primary">
			                            	<i class="glyphicon glyphicon-search"></i> 查询
			                            </button>
			                            <button type="reset" style="margin-left:10px" id="btn_query" class="btn btn-primary">
			                            	<i class="glyphicon glyphicon-search"></i> 重置
			                            </button>
			                        </div>
			                    </div>
			            	</form>
			            	<div id="toolbar">
						        <#-- -<button id="btn_detail" class="btn btn-default" data-toggle="modal">
						            <i class="glyphicon"></i> 详情
						        </button>
						         -->
						        <button id="btn_report" class="btn btn-default" data-toggle="modal">
						            <i class="glyphicon"></i> 报告
						        </button>
						        <button id="btn_export" class="btn btn-default">
						            <i class="glyphicon"></i> 导出
						        </button>
						    </div>
	                        <table id="tb_diagnoserecords" >
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
      <div class="modal-dialog modal-lg" style="width:650px;" role="document">
        <div class="modal-content">
          <div class="modal-header" style="align:center;">
             <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
             <h4 class="modal-title" id="myModalLabel" style="">诊断报告</h4>
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
            		<tr>
	            		<td style='text-align: center;' colspan=2>增殖性糖网病变</td>
	            		<td style='text-align: left;' colspan=4>
		    				<div class="radio" id = "diagnose_right">
				              	<div class="radio">
				              		<label><input id="degree_right" name="degree_right" type="radio" value="0" disabled>无明显糖网病变</label>
				              	</div>
				              	<div class="radio">
				              		<label><input id="degree_right" name="degree_right" type="radio" value="1" disabled>轻度非增殖性糖网病变</label>
				              	</div>
				              	<div class="radio">
				              		<label><input id="degree_right" name="degree_right" type="radio" value="2" disabled>中度非增殖性糖网病变</label>
				              	</div>
				              	<div class="radio">
				              		<label><input id="degree_right" name="degree_right" type="radio" value="3" disabled>重度非增殖性糖网病变</label>
				              	</div>
				              	<div class="radio">
				              		<label><input id="degree_right" name="degree_right" type="radio" value="4" disabled>增殖性糖网病变</label>
				              	</div>
				              	<div class="radio">
				              		<label><input id="degree_right" name="degree_right" type="radio" value="5" disabled>无有效图像</label>
				              	</div>
							</div>
	            		</td>
		    			<td style='text-align: left;' colspan=6>
		    				<div class="radio" id = "diagnose_left">
				              	<div class="radio">
				              		<label><input id="degree_left" name="degree_left" type="radio" value="0"  disabled>无明显糖网病变</label>
				              	</div>
				              	<div class="radio">
				              		<label><input id="degree_left" name="degree_left" type="radio" value="1" disabled>轻度非增殖性糖网病变</label>
				              	</div>
				              	<div class="radio">
				              		<label><input id="degree_left" name="degree_left" type="radio" value="2" disabled>中度非增殖性糖网病变</label>
				              	</div>
				              	<div class="radio">
				              		<label><input id="degree_left" name="degree_left" type="radio" value="3" disabled>重度非增殖性糖网病变</label>
				              	</div>
				              	<div class="radio">
				              		<label><input id="degree_left" name="degree_left" type="radio" value="4" disabled>增殖性糖网病变</label>
				              	</div>
				              	<div class="radio">
				              		<label><input id="degree_left" name="degree_left" type="radio" value="5" disabled>无有效图像</label>
				              	</div>
							</div>
	            		</td>
            		</tr>
            		<tr>
	            		<td style='text-align: center;' colspan=2>建议</td>
	            		<td style='text-align: left;' colspan=8>
	            			<div class="checkbox" style = "width:100%;">
		    					<label><input id="diseaseTypeSuggest" name="diseaseTypeSuggest" type="checkbox" value="18101809580300001" disabled> 您的眼部健康我们正日夜呵护</label>
		    					</br><label><input id="diseaseTypeSuggest" name="diseaseTypeSuggest" type="checkbox" value="18101809583100002" disabled> 建议注意饮食结构控制糖尿病</label>
		    					</br><label><input id="diseaseTypeSuggest" name="diseaseTypeSuggest" type="checkbox" value="18101809590400003" disabled> 建议到专业眼科定期复查眼底</label>
		    					</br><label><input id="diseaseTypeSuggest" name="diseaseTypeSuggest" type="checkbox" value="18101809593400004" disabled> 建议每三个月作眼底照相一次</label>
		    					</br><label><input id="diseaseTypeSuggest" name="diseaseTypeSuggest" type="checkbox" value="18101810000800005" disabled> 建议尽快行眼底病变干预措施</label>
		    					</br><label><input id="diseaseTypeSuggest" name="diseaseTypeSuggest" type="checkbox" value="18101810003500006" disabled> 您的眼底病变已严重尽快手术</label>
							</div>
	            		</td>
            		</tr>
            	</tbody>
            </table>
            <div class="row">
	            <label for="" class="col-sm-12 control-label">医生姓名:&nbsp;&nbsp;<dis id="doctorName"></dis></label>
            </div>
            <div class="row">
	            <label for="" class="col-sm-12 control-label">医生建议:&nbsp;&nbsp;<dis id="resultRemark"></dis></label>
            </div>
            <div class="row">
	            <label for="" class="col-sm-12 control-label">医生签名:&nbsp;&nbsp;<img src="" style="width:200px;height:50px" id="sign"></img></label>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" id="btn_print" class="btn btn-default">打印</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          </div>
        </div>
    </div>
</div>
<div class="modal fade" id="detailModal" tabindex="-1" backdrop='static' keyboard='false' role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
      <div class="modal-dialog" style="width:75%" role="document">
        <div class="modal-content">
          <div class="modal-header">
             <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
             <h4 class="modal-title" id="myModalLabel">诊断详情</h4>
          </div>
          <div class="modal-body"  id="galley">
              <div class="row">
                	<label for="" class="col-sm-10 col-sm-offset-1 control-label">
		              <ul class="pictures" style="margin: 0; padding: 0;list-style: none;min-height:190px;height:auto;">
		              </ul>			
                	</label>
              </div>
              <div class="row">
                	<label for="" class="col-sm-5 col-sm-offset-1 control-label">患者姓名:&nbsp;&nbsp;<dis id="patientName"></dis></label>
                	<label for="" class="col-sm-6 control-label">检查医院:&nbsp;&nbsp;<dis id="hospitalName"></dis></label>
	            </div>
	            <div class="row">
	                	<label for="" class="col-sm-5 col-sm-offset-1 control-label">检查时间:&nbsp;&nbsp;<dis id="testTime"></dis></label>
	                	<label for="" class="col-sm-6 control-label">AI诊断结果:&nbsp;&nbsp;<dis id="aiResult"></dis></label>
	            </div>
	            <div class="row">
	                	<label for="" class="col-sm-5 col-sm-offset-1 control-label">确诊专家:&nbsp;&nbsp;<dis id="doctorName"></dis></label>
	                	<label for="" class="col-sm-6 control-label">确诊结果:&nbsp;&nbsp;<dis id="disease"></dis></label>
	            </div>
	            <div class="row">
	                	<label for="" class="col-sm-5 col-sm-offset-1 control-label">专家确诊时间:&nbsp;&nbsp;<dis id="diagnoseTime"></dis></label>
	                	<label for="" class="col-sm-6 control-label">专家建议:&nbsp;&nbsp;<dis id="resultRemark"></dis></label>
	            </div>
	            <div class="row">
	                	<label for="" class="col-sm-10 col-sm-offset-1 control-label">建议:&nbsp;&nbsp;<dis id="suggest"></dis></label>
	            </div>
              
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
<script src="${BasePath}/res/bizmgt/js/diagnose/diagnoserecord.js"></script>
<!-- Select2 -->
  <link rel="stylesheet" href="${BasePath}/res/bower_components/select2/dist/css/select2.min.css">
  <!-- Select2 -->
<script src="${BasePath}/res/bower_components/select2/dist/js/select2.full.min.js"></script>
<script src="${BasePath}/res/bower_components/select2/dist/js/i18n/zh-CN.js"></script>
<!-- Latest compiled and minified Locales -->
<script src="${BasePath}/res/bootstrap/js/table/bootstrap-table-zh-CN.min.js"></script>
<script src="${BasePath}/res/bootstrap/js/printThis.js"></script>
<script type="text/javascript" src="${BasePath}/res/bootstrap/js/validator/js/bootstrapValidator.js"></script>
<script type="text/javascript" src="${BasePath}/res/bootstrap/js/viewer/viewer.min.js"></script>
<link rel="stylesheet" href="${BasePath}/res/bootstrap/js/viewer/viewer.min.css"/>
<script>
var basePath = "${BasePath!''}";
var imgRes = "${imgRes!''}";
$(function () {
	$('.select2').select2({
        placeholder: "请选择",
        width: "100%",
        language: "zh-CN"
    });
	var explorer = window.navigator.userAgent;
	//判断是否为Firefox浏览器
	if(explorer.indexOf("Firefox") >= 0) {
	  $("#reportModal").find('.modal-dialog').attr('style', 'width:615px;');
	}
});

</script>
</html>
