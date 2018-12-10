<#include "../common/public_macro.ftl">  
  
<@header title="修改资源">  
</@header>


<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
	<@navigation title="修改资源">  
	</@navigation>
	<div class="content-wrapper">
		 <section class="content-header">
	      <h1>
	        修改资源
	      </h1>
	      <ol class="breadcrumb">
	        <@index_page></@index_page>
	        <li><a href="#">资源管理</a></li>
	        <li><a href="#">修改资源</a></li>
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
	                            修改资源
	                        </h3>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="box-body">
                        	 <form id="formUpdate" class="form-horizontal">
		                    	<div class="form-group" style="margin-bottom: 10px;">
			                        <label for="resName" class="col-sm-3 control-label">名称</label>
			                        <div class="col-sm-6">
			                        	<input type="text" value="${resourceVo.resName?default("")}"  name="resName" class="form-control" id="resName" placeholder="名称">
			                        	<input  id='id' name='id' type="hidden" value="${resourceVo.id?default("")}"/> 
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <label for="resUrl" class="col-sm-3 control-label">url</label>
			                        <div class="col-sm-6">
			                        	<input type="text" name="resUrl" class="form-control" value="${resourceVo.resUrl?default("")}" id="resUrl" placeholder="url">
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <label for="resType" class="col-sm-3 control-label">资源类型</label>
			                        <div class="col-sm-6">
			                        	<select class="form-control"  id="resType" name="resType">
										  	<option value="1" <#if (resourceVo.resType!'')=='1'>selected='selected'</#if>>菜单</option>
                              				<option value="2" <#if (resourceVo.resType!'')=='2'>selected='selected'</#if>>按钮<option>
										</select>
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <label for="resCode" class="col-sm-3 control-label">资源编码</label>
			                        <div class="col-sm-6">
			                        	<input type="text" name="resCode" class="form-control" value="${resourceVo.resCode!''}" id="resCode" placeholder="资源编码">
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <label for="seqNum" class="col-sm-3 control-label">排序</label>
			                        <div class="col-sm-6">
			                        	<input type="text" name="seqNum" class="form-control" value="${resourceVo.seqNum?default("")}" id="seqNum" placeholder="排序">
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <label for="level" class="col-sm-3 control-label">菜单层级</label>
			                        <div class="col-sm-6">
			                        	<input type="text" name="level" class="form-control" readonly value="${resourceVo.level?default("")}" id="level" placeholder="菜单层级">
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <label for="supperResName" class="col-sm-3 control-label">父资源</label>
			                        <div class="col-sm-6">
			                        	<input type="text" name="supperResName" class="form-control" readonly value="${resourceVo.supperResName?default("")}" id="supperResName" placeholder="父资源">
			                        	<input  id='supperResId' name='supperResId' type="hidden" value="${resourceVo.supperResId?default("")}"/>
			                        </div>
			                    </div>
			                    <div class="form-group" style="margin-bottom: 10px;">
			                        <label for="remark" class="col-sm-3 control-label">备注</label>
			                        <div class="col-sm-6">
			                        	<input type="text" name="remark" class="form-control" value="${resourceVo.remark?default("")}" id="remark" placeholder="备注">
			                        </div>
			                    </div>
			                    <div class="form-group" style="">
			                        <label for="isShow" class="col-sm-3 control-label">是否显示</label>
			                        <div class="col-sm-4" style="margin-top: 7px;">
			                        	<input type="radio" <#if resourceVo.isShow == 1 > checked </#if> name="isShow" id="isShow" value="1">是
	                        			<input type="radio" <#if resourceVo.isShow == 0 > checked </#if> name="isShow" style="" id="isShow" value="0" >否
			                        </div>
			                    </div>
			                    <input type="hidden" id="resType"  value="${resourceVo.resType?default("")}">
				                   <#if resourceVo.createTime??>
				                   	<input type="hidden" id="createTime" name="" value="${resourceVo.createTime?datetime}" >
				                   </#if>
				              </form>
                        <!-- /.panel-body -->
                    	</div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row" style="padding-top:10px;">
            	 <table style="width:100%;margin-top:122px;">
                	<tr>
	                	 <td>
		                     <div class="col-sm-2" style="float:right;">
		                        <button type="button" style="margin-left:0px" id="btn_save" class="btn btn-primary">修改</button>
		                     </div>
	                     </td>
	                     <td>
		                     <div class="col-sm-2" style="float:left;">
		                        <button type="button" style="margin-left:0px" onclick="javascript:history.back()" class="btn btn-primary">返回</button>
		                    </div>
		                </td>
                    </tr>
                </table>
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
<script src="${BasePath}/res/bizmgt/js/system/edit_system_resource.js"></script>

<script>
var basePath = "${BasePath!''}";
$('#tree').treeview({
	data: ${resourceList!''},
	showBorder:false,
	color:'#3194d0',
	onNodeSelected: function(event, data) {
	    var level = data.level;
	    if(level!=3){
	         var abs = 1;
			 var las_number = Number(level) + Number(abs);
			 $('#level').val(las_number);
			 $('#supperResId').val(data.id);
			 $('#supperResName').val(data.text);
			 $('#isShow').attr("checked","checked");
		}else{
			$('#level').val("");
			$('#supperResId').val("");
			$('#supperResName').val("");
			layer.alert("当前节点不允许添加子节点",{icon:2});
	   }
	}
});

</script>
</html>
