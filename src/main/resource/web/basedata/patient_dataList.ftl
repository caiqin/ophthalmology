<#include "../common/public_macro.ftl">  
  
<@header title="患者管理">  
</@header>


<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
	<@navigation title="患者管理">  
	</@navigation>

        <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        患者管理
      </h1>
      <ol class="breadcrumb">
        <@index_page></@index_page>
        <li><a href="#">患者管理</a></li>
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
			                        <label class="control-label col-sm-2" for="mobileNo">手机</label>
			                        <div class="col-sm-3">
			                            <input type="text" class="form-control" id="mobileNo" placeholder="手机">
			                        </div>
			                        <div class="col-sm-2" style="text-align:left;">
			                            <button type="button" style="margin-left:50px" id="btn_query" class="btn btn-primary">
			                            	<i class="glyphicon glyphicon-search"></i> 查询
			                            </button>
			                        </div>
			                    </div>
			            	</form>
	                        <div id="toolbar">
						        <button id="add" tag="sys_control_button" tag1="addPatient" class="btn btn-default">
						            <i class="glyphicon glyphicon-plus"></i> 新增
						        </button>
						        <button id="update" class="btn btn-default">
						            <i class="glyphicon glyphicon-edit"></i> 修改
						        </button>
						        <button id="detail" class="btn btn-default">
						            <i class="glyphicon"></i> 详情
						        </button>
						        <button id="btn_show_qr" class="btn btn-default">
						            <i class="glyphicon"></i> 查看二维码
						        </button>
						    </div>
	                        <table id="tb_patients" >
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
	<div class="modal fade" id="detailModal" tabindex="-1" backdrop='static' keyboard='false' role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog"  role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">患者信息详情</h4>
                </div>
                <div class="modal-body">
					<form id="detailPatient" class="form-horizontal"  method="post">
	                    <div class="form-group">
	                        <label for="name" class="col-sm-3 control-label">患者姓名:</label>
	                        <div class="col-sm-9">
	                        	<input type="text" name="name" class="form-control" id="name" placeholder="患者姓名">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="gender" class="col-sm-3 control-label">性别:</label>
	                        <div class="col-sm-9">          
	                        	<label class="radio-inline">
								  <input type="radio" name="gender" id="gender" value="0"> 男
								</label>
								<label class="radio-inline">
								  <input type="radio" name="gender" id="gender" value="1"> 女
								</label>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="age" class="col-sm-3 control-label">年龄:</label>
	                        <div class="col-sm-9">
	                        	<input type="text" name="age" class="form-control" id="age" placeholder="年龄">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="hospitalName" class="col-sm-3 control-label">所属医院:</label>
	                        <div class="col-sm-9">
	                        	<input type="hidden" name="hospitalId" class="form-control" id="hospitalId" placeholder="所属医院">
	                        	<input type="text" name="hospitalName" class="form-control" id="hospitalName" placeholder="所属医院">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="mobileNo" class="col-sm-3 control-label">手机:</label>
	                        <div class="col-sm-9">
	                        	<input type="text" name="mobileNo" class="form-control" id="mobileNo" placeholder="手机">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="cardNo" class="col-sm-3 control-label">身份证:</label>
	                        <div class="col-sm-9">
	                        	<input type="text" name="cardNo" class="form-control" id="cardNo" placeholder="身份证">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="cardAddress" class="col-sm-3 control-label">身份证地址:</label>
	                        <div class="col-sm-9">
	                        	<input type="text" name="cardAddress" class="form-control" id="cardAddress" placeholder="身份证地址">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="realAddress" class="col-sm-3 control-label">真实地址:</label>
	                        <div class="col-sm-9">
	                        	<input type="text" name="realAddress" class="form-control" id="realAddress" placeholder="真实地址">
	                        </div>
	                    </div>
	                </form>
                </div>
                <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
	            </div>
            </div>
        </div>
    </div>
	<div class="modal fade" id="addModal" tabindex="-1" backdrop='static' keyboard='false' role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog"  role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">新增患者信息</h4>
                </div>
                <div class="modal-body">
					<form id="addPatient" class="form-horizontal"  method="post">
	                    <div class="form-group">
	                        <label for="name" class="col-sm-3 control-label">患者姓名:</label>
	                        <div class="col-sm-9">
	                        	<input type="text" name="name" class="form-control" id="name" placeholder="患者姓名">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="gender" class="col-sm-3 control-label">性别:</label>
	                        <div class="col-sm-9">          
	                        	<label class="radio-inline">
								  <input type="radio" name="gender" id="gender" value="0" checked> 男
								</label>
								<label class="radio-inline">
								  <input type="radio" name="gender" id="gender" value="1"> 女
								</label>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="age" class="col-sm-3 control-label">年龄:</label>
	                        <div class="col-sm-9">
	                        	<input type="text" name="age" class="form-control" id="age" placeholder="年龄">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="hospitalName" class="col-sm-3 control-label">所属医院:</label>
	                        <div class="col-sm-9">
	                        	<input type="hidden" name="hospitalId" class="form-control" id="hospitalId" placeholder="所属医院">
	                        	<input type="text" name="hospitalName" class="form-control" id="hospitalName" placeholder="所属医院">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="mobileNo" class="col-sm-3 control-label">手机:</label>
	                        <div class="col-sm-9">
	                        	<input type="text" name="mobileNo" class="form-control" id="mobileNo" placeholder="手机">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="cardNo" class="col-sm-3 control-label">身份证:</label>
	                        <div class="col-sm-9">
	                        	<input type="text" name="cardNo" class="form-control" id="cardNo" placeholder="身份证">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="cardAddress" class="col-sm-3 control-label">身份证地址:</label>
	                        <div class="col-sm-9">
	                        	<input type="text" name="cardAddress" class="form-control" id="cardAddress" placeholder="身份证地址">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="realAddress" class="col-sm-3 control-label">真实地址:</label>
	                        <div class="col-sm-9">
	                        	<input type="text" name="realAddress" class="form-control" id="realAddress" placeholder="真实地址">
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
	<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog" style="" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">修改患者信息</h4>
                </div>
                <div class="modal-body">
					<form id="updatePatient" class="form-horizontal"  method="post">
	                    <div class="form-group">
	                        <label for="name" class="col-sm-3 control-label">患者姓名:</label>
	                        <div class="col-sm-9">
	                        	<input type="hidden" name="id" class="form-control" id="id" >
	                        	<input type="text" name="name" class="form-control" id="name" placeholder="患者姓名">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="gender" class="col-sm-3 control-label">性别:</label>
	                        <div class="col-sm-9">          
	                        	<label class="radio-inline">
								  <input type="radio" name="gender" id="gender" value="0"> 男
								</label>
								<label class="radio-inline">
								  <input type="radio" name="gender" id="gender" value="1"> 女
								</label>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="age" class="col-sm-3 control-label">年龄:</label>
	                        <div class="col-sm-9">
	                        	<input type="text" name="age" class="form-control" id="age" placeholder="年龄">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="hospitalName" class="col-sm-3 control-label">所属医院:</label>
	                        <div class="col-sm-9">
	                        	<input type="hidden" name="hospitalId" class="form-control" id="hospitalId" placeholder="所属医院">
	                        	<input type="text" name="hospitalName" class="form-control" id="hospitalName" placeholder="所属医院">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="mobileNo" class="col-sm-3 control-label">手机:</label>
	                        <div class="col-sm-9">
	                        	<input type="text" name="mobileNo" class="form-control" id="mobileNo" placeholder="手机">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="cardNo" class="col-sm-3 control-label">身份证:</label>
	                        <div class="col-sm-9">
	                        	<input type="text" name="cardNo" class="form-control" id="cardNo" placeholder="身份证">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="cardAddress" class="col-sm-3 control-label">身份证地址:</label>
	                        <div class="col-sm-9">
	                        	<input type="text" name="cardAddress" class="form-control" id="cardAddress" placeholder="身份证地址">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="realAddress" class="col-sm-3 control-label">真实地址:</label>
	                        <div class="col-sm-9">
	                        	<input type="text" name="realAddress" class="form-control" id="realAddress" placeholder="真实地址">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                    	<div class="col-lg-8 col-lg-offset-4">
		                    	
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
<div class="modal fade" id="showModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">查看患者二维码</h4>
                </div>
                <div class="modal-body">
					<form id="editHospital" class="form-horizontal"  method="post">
	                   <div class="form-group">
	                        <div class="col-sm-10 col-sm-offset-2">
	                        	<img id="img_qr" src="" style="width:380px;height:380px;"></img>
	                        </div>
	                    </div>
	                </form>
	            </div>
	              <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
	            </div>
            </div>
        </div>
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
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="${BasePath}/res/bootstrap/js/table/css/bootstrap-table.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="${BasePath}/res/bootstrap/js/tree/bootstrap-treeview.min.css"></script>
<link rel="stylesheet" href="${BasePath}/res/bootstrap/js/validator/css/bootstrapValidator.css"/>

<!-- Latest compiled and minified Locales -->
<script src="${BasePath}/res/bootstrap/js/tree/bootstrap-treeview.min.js"></script>

<!-- Latest compiled and minified JavaScript -->
<script src="${BasePath}/res/bootstrap/js/table/bootstrap-table.min.js"></script>
<script src="${BasePath}/res/bizmgt/js/basedata/patient.js"></script>
<!-- Select2 -->
  <link rel="stylesheet" href="${BasePath}/res/bower_components/select2/dist/css/select2.min.css">
  <!-- Select2 -->
<script src="${BasePath}/res/bower_components/select2/dist/js/select2.full.min.js"></script>
<script src="${BasePath}/res/bower_components/select2/dist/js/i18n/zh-CN.js"></script>
<!-- Latest compiled and minified Locales -->
<script src="${BasePath}/res/bootstrap/js/table/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${BasePath}/res/bootstrap/js/validator/js/bootstrapValidator.js"></script>
<script>
var basePath = "${BasePath!''}";
var imgRes = "${imgRes!''}";
$(function () {
    		$('.select2').select2({
	        placeholder: "请选择",
	        width: "100%",
	        language: "zh-CN"
	    });
	    });
</script>
</html>
