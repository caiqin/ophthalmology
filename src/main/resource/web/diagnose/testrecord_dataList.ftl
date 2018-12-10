<#include "../common/public_macro.ftl">  
  
<@header title="检查记录管理">  
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
	body.modalprinter .demo-box img { display:block; position:absolute;left:50%;transform:translateX(-50%); }
	
	
	table.bottomBlank {margin-bottom:165px;}
    body.modalprinter * {
        visibility: hidden;
    }

	body.modalprinter .img-box { display:block;margin-top:100px;}
	body.modalprinter .img-box img {display:block; width:500px;height:90px; }
	
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
	<@navigation title="检查记录管理">  
	</@navigation>

        <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        检查记录管理
      </h1>
      <ol class="breadcrumb">
        <@index_page></@index_page>
        <li><a href="#">检查记录管理</a></li>
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
		                        </div>
			                    <div class="form-group" style="margin-top:15px">
			                    	<label class="control-label col-sm-2" for="diagnoseState">诊断状态</label>
			                        <div class="col-sm-3">
			                            <select class="form-control" id="diagnoseState" style="width:100%;" placeholder="诊断状态">
			                            	<option value=" ">请选择</option>
			                            	<option value="0">待诊断</option>
			                            	<option value="1">待复核</option>
			                            	<option value="2">已诊断</option>
			                            </select>
			                        </div>
			                        <div class="col-sm-3 col-sm-offset-3" style="text-align:left;">
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
						        <button id="diagnose" tag="sys_control_button" tag1="zzd" class="btn btn-default" data-toggle="modal" style="display:none;">
						            <i class="glyphicon"></i> 诊断
						        </button>
						  		<button id="btn_ai_report" class="btn btn-default">
						            <i class="glyphicon"></i> AI诊断报告
						        </button>

						    </div>
	                        <table id="tb_testrecords">
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

<div class="modal fade printable" id="AiReportModal" tabindex="-1" backdrop='static' keyboard='false' role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
      <div class="modal-dialog modal-lg" style="width:600px;" role="document">
        <div class="modal-content">
        <br/>
          <div class="modal-body"  id="galley">
             <div class="demo-box">
            	<img style="width:384px;height:64px;" src="https://jiaxunjiankang.com/about/logo.png"></img>
            </div>
         <div  style='margin-top: -20px;'>
          <!-- <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
             <h3 class="modal-title" id="myModalLabel">
             AI诊断报告</h3>
           </div>
           <br/>
             <label for="" class="col-sm-3 control-label">患者姓名:&nbsp;&nbsp;<dis id="patientName"></dis></label>
             <label for="" class="col-sm-2 control-label">性别:&nbsp;&nbsp;<dis id="gender"></dis></label>
             <label for="" class="col-sm-2 control-label">年龄:&nbsp;&nbsp;<dis id="age"></dis></label>
             <label for="" class="col-sm-5 control-label">身份证:&nbsp;&nbsp;<dis id="idCard"></dis></label>
             <label for="" class="col-sm-5 control-label">检查医院:&nbsp;&nbsp;<dis id="hospitalName"></dis></label>
             <label for="" class="col-sm-5 control-label">检查时间:&nbsp;&nbsp;<dis id="testTime"></dis></label>
             
             <table id="" class="table table-bordered bottomBlank" >
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
            		 <!--	<td style='text-align: center;' colspan=2>AI诊断结果</td>-->
	            		<td style='text-align: left;' colspan=4>
	            				<div id='right_ai'>
	            				</div>	
	            		</td>
	            		<td style='text-align: left;' colspan=6>
	            				<div id='left_ai'>
	            				</div>	
	            		</td>
            		</tr>
            	</tbody>
            </table>
            <div class="demo-box" style='position:absolute;left:10px;top:750px;'>
            	<img src="https://jiaxunjiankang.com/about/tencent2.jpg"></img>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" id="btn_print" class="btn btn-default">打印</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          </div>
        </div>
    </div>
</div>
    
<!-- Modal -->
	<input type="hide" id="suggestId">
    <div class="modal fade" id="diagnoseModal" tabindex="-1" backdrop='static' keyboard='false' role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
      <div class="modal-dialog" style="width:65%" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <!-- <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
             <h4 class="modal-title" id="myModalLabel">诊断</h4>
          </div>
          <div class="modal-body"  id="galley">
              <input type="hidden" id="patientId"/>
              <input type="hidden" id="testRecordId"/>
              <input id="patientName" type="hidden">
              <label for="" class="col-sm-3 control-label">患者姓名:&nbsp;&nbsp;<dis id="patientName_show"></dis></label>
             <label for="" class="col-sm-2 control-label">性别:&nbsp;&nbsp;<dis id="gender"></dis></label>
             <label for="" class="col-sm-2 control-label">年龄:&nbsp;&nbsp;<dis id="age"></dis></label>
             <label for="" class="col-sm-5 control-label">身份证:&nbsp;&nbsp;<dis id="idCard"></dis></label>
             <label for="" class="col-sm-12 control-label">检查医院:&nbsp;&nbsp;<dis id="hospitalName"></dis></label>
             <label for="" class="col-sm-12 control-label">检查时间:&nbsp;&nbsp;<dis id="testTime"></dis></label>
             
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
            		<!-- tr id="ai_tr">
	            		<td style='text-align: center;' colspan=6>
	            				<div id='right_ai'></div>	
	            		</td>
	            		<td style='text-align: center;' colspan=6>
	            				<div id='left_ai'></div>	
	            		</td>
            		</tr-->
            		<tr>
	            		<td style='text-align: center;' colspan=2>
	            			<div class="checkbox" id = "disease_right">
					  		</div>
	            		</td>
	            		<td style='text-align: left;' colspan=4>
		    				<div class="radio" id = "degree_right">
				              	<div class="radio">
				              		<label><input id="degree_right" checked name="degree_right" type="radio" value="0">无明显糖网病变</label>
				              	</div>
				              	<div class="radio">
				              		<label><input id="degree_right" name="degree_right" type="radio" value="1">轻度非增殖性糖网病变</label>
				              	</div>
				              	<div class="radio">
				              		<label><input id="degree_right" name="degree_right" type="radio" value="2">中度非增殖性糖网病变</label>
				              	</div>
				              	<div class="radio">
				              		<label><input id="degree_right" name="degree_right" type="radio" value="3">重度非增殖性糖网病变</label>
				              	</div>
				              	<div class="radio">
				              		<label><input id="degree_right" name="degree_right" type="radio" value="4">增殖性糖网病变</label>
				              	</div>
				              	<div class="radio">
				              		<label><input id="degree_right" name="degree_right" type="radio" value="5">无有效图像信息</label>
				              	</div>
							</div>
	            		</td>
		    			<td style='text-align: center;' colspan=2>
			    			<div class="checkbox" id = "disease_left">
					  		</div>
		    			</td>
		    			<td style='text-align: left;' colspan=4>
		    				<div class="radio" id = "degree_left">
				              	<div class="radio">
				              		<label><input id="degree_left" checked name="degree_left" type="radio" value="0">无明显糖网病变</label>
				              	</div>
				              	<div class="radio">
				              		<label><input id="degree_left" name="degree_left" type="radio" value="1">轻度非增殖性糖网病变</label>
				              	</div>
				              	<div class="radio">
				              		<label><input id="degree_left" name="degree_left" type="radio" value="2">中度非增殖性糖网病变</label>
				              	</div>
				              	<div class="radio">
				              		<label><input id="degree_left" name="degree_left" type="radio" value="3">重度非增殖性糖网病变</label>
				              	</div>
				              	<div class="radio">
				              		<label><input id="degree_left" name="degree_left" type="radio" value="4">增殖性糖网病变</label>
				              	</div>
				              	<div class="radio">
				              		<label><input id="degree_left" name="degree_left" type="radio" value="5">无有效图像信息</label>
				              	</div>
							</div>
	            		</td>
            		</tr>
            	</tbody>
            </table>
          <h5 class="modal-title" id="myModalLabel">建议</h5>
          <div class="checkbox" id = "diseaseTypeSuggest">
		  </div>
          <h5 class="modal-title" id="myModalLabel">医生建议</h5>
          <textarea class="form-control" rows="3" id = "resultRemark"></textarea>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" id="btn_diagnose" class="btn btn-primary">保存</button>
          </div>
        </div>
      </div>
    </div>

<div class="modal fade printable" id="reportModal" tabindex="-1" backdrop='static' keyboard='false' role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
      <div class="modal-dialog modal-lg" style="width:650px;" role="document">
        <div class="modal-content">
          <div class="modal-header">
             <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
             <h4 class="modal-title" id="myModalLabel">诊断详情</h4>
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
	            		<td style='text-align: center;' colspan=6>
	            				<div id='right_ai'></div>	
	            		</td>
	            		<td style='text-align: center;' colspan=6>
	            				<div id='left_ai'></div>	
	            		</td>
            		</tr>
            		<tr id="disease_info">
	            		<td style='text-align: center;' colspan=2>增殖性糖网病变</td>
	            		<td style='text-align: left;' colspan=4>
		    				<div class="radio" id = "degree_right">
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
				              		<label><input id="degree_right" name="degree_right" type="radio" value="5" disabled>无有效图像信息</label>
				              	</div>
							</div>
	            		</td>
		    			<td style='text-align: left;' colspan=6>
		    				<div class="radio" id = "degree_left">
				              	<div class="radio">
				              		<label><input id="degree_left" name="degree_left" type="radio" value="0" disabled>无明显糖网病变</label>
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
				              		<label><input id="degree_right" name="degree_right" type="radio" value="5" disabled>无有效图像信息</label>
				              	</div>
							</div>
	            		</td>
            		</tr>
            		<tr id="suggest_info">
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
            <div id="doctor_info">
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
            <div id="diagnose_state">
            	<div class="row">
		            <label for="" class="col-sm-12 control-label">专家诊断状态:&nbsp;&nbsp;待诊断</label>
	            </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" id="btn_print" class="btn btn-default">打印</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          </div>
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
<script src="${BasePath}/res/bizmgt/js/diagnose/testrecord.js"></script>

<script>
var basePath = "${BasePath!''}";
var imgRes = "${imgRes!''}";
$(function(){
	var explorer = window.navigator.userAgent;
	//判断是否为Firefox浏览器
	if(explorer.indexOf("Firefox") >= 0) {
	  $("#reportModal").find('.modal-dialog').attr('style', 'width:615px;');
	}
});

</script>
</html>
