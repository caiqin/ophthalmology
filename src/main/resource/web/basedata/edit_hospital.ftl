<#include "../common/public_macro.ftl">  
  
<@header title="修改医院">  
</@header>


<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
	<@navigation title="修改医院">  
	</@navigation>
	<div class="content-wrapper">
		 <section class="content-header">
	      <h1>
	        修改医院
	      </h1>
	      <ol class="breadcrumb">
	        <@index_page></@index_page>
	        <li><a href="#">医院管理</a></li>
	        <li><a href="#">修改医院</a></li>
	      </ol>
	    </section>
	    <!-- Main content -->
	    <section class="content">
            <!-- /.row -->
            <div class="row" style="padding-top:10px;">
                <div class="col-lg-6">
                    <div class="box box-primary" style="height:455px;">
                        <!-- /.panel-heading -->
                        <div class="box-body" style="padding: 0;">
                        	<div id="tree" style="height:418px;position: relative;  overflow-y: scroll;"></div>
                		</div>
                	</div>
                </div>
                <div class="col-lg-6">
                    <div class="box box-success">
                    	<div class="box-header ui-sortable-handle">
	                        <h3 class="box-title">
	                            修改医院
	                        </h3>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="box-body">
                        	 <form id="formUpdate" class="form-horizontal">
		                    	<div class="form-group" style="margin-bottom: 10px;">
			                    	<input type="hidden" name="hospitalId" value = "${hospitalVo.hospitalId!''}"/>
			                        <label for="hospitalName" class="col-sm-3 control-label">医院名称</label>
			                        <div class="col-sm-7">
			                        	<input type="text" name="hospitalName" value="${hospitalVo.hospitalName!''}" class="form-control" id="hospitalName" placeholder="医院名称">
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <label for="seqNum" class="col-sm-3 control-label">医院地址</label>
			                        <div class="col-sm-7">
			                        	<input type="text" name="hospitalAddress" value = "${hospitalVo.hospitalAddress!''}" class="form-control" id="hospitalAddress" placeholder="医院地址">
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <label for="level" class="col-sm-3 control-label">层级</label>
			                        <div class="col-sm-7">
			                        	<input type="text" name="level" value="${hospitalVo.level!''}" class="form-control" id="level" placeholder="层级" readonly>
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <label for="supperResName" class="col-sm-3 control-label">上级医院</label>
			                        <div class="col-sm-7">
			                        	<input type="text" name="parentName" class="form-control" value="${hospitalVo.parentName!''}" id="parentName" placeholder="上级医院" readonly>
			                        	<input  id='parentNo' name='parentNo' value="${hospitalVo.parentNo!''}" type="hidden"/>
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <label for="remark" class="col-sm-3 control-label">医院介绍</label>
			                        <div class="col-sm-7">
			                        	<textarea name="hospitalIntroduce" rows="3" class="form-control" id="hospitalIntroduce" placeholder="医院介绍">${hospitalVo.hospitalIntroduce!''}</textarea>
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <div class="col-sm-2  col-md-offset-4">
				                        <button type="submit" style="margin-left:0px" id="btn_save" class="btn btn-primary">修改</button>
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

<!-- Latest compiled and minified Locales -->
<script src="${BasePath}/res/bootstrap/js/tree/bootstrap-treeview.min.js"></script>

<link rel="stylesheet" href="${BasePath}/res/bootstrap/js/validator/css/bootstrapValidator.css"/>

<script type="text/javascript" src="${BasePath}/res/bootstrap/js/validator/js/bootstrapValidator.js"></script>
<script src="${BasePath}/res/bizmgt/js/basedata/edit_hospital.js"></script>
<script>
var basePath = "${BasePath!''}";
$('#tree').treeview({
	data: ${hospitalList!''},
	showBorder:false,
	color:'#3194d0',
	onNodeSelected: function(event, data) {
	    var level = data.level;
	    if(level!=3){
	         var abs = 1;
			 var las_number = Number(level) + Number(abs);
			 $('#level').val(las_number);
			 $('#parentNo').val(data.hospitalId);
			 $('#parentName').val(data.text);
		}else{
			$('#level').val("");
			$('#parentNo').val("");
			$('#parentName').val("");
			layer.alert("当前节点不允许添加子节点",{icon:2});
	   }
	}
});

</script>
</html>
