<#--公共顶部-->  
<#assign BasePath=request.contextPath>
<#macro header title="默认文字">  
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>眼科筛查管理平台 | ${title}</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="${BasePath}/res/bower_components/bootstrap/dist/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="${BasePath}/res/bower_components/font-awesome/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="${BasePath}/res/bower_components/Ionicons/css/ionicons.min.css">
  <!-- jvectormap -->
  <link rel="stylesheet" href="${BasePath}/res/bower_components/jvectormap/jquery-jvectormap.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="${BasePath}/res/dist/css/AdminLTE.min.css">
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="${BasePath}/res/dist/css/skins/_all-skins.min.css">
<!-- jQuery 3 -->
<script src="${BasePath}/res/bower_components/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="${BasePath}/res/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- FastClick -->
<script src="${BasePath}/res/bower_components/fastclick/lib/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="${BasePath}/res/dist/js/adminlte.min.js"></script>

<link href="${BasePath}/res/bootstrap/js/bootstrap-datetimepicker.css" rel="stylesheet">

<script type="text/javascript"  src="${BasePath}/res/bootstrap/js/bootstrap-datetimepicker.js"></script>

<script type="text/javascript" src="${BasePath}/res/bootstrap/js/bootstrap-datetimepicker.fr.js" charset="UTF-8"></script>
<script type="text/javascript"  src="${BasePath}/res/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<!-- Layer -->
<script type="text/javascript"  src="${BasePath}/res/bootstrap/js/layer/layer.js"></script>

<!-- PUblic -->
<script src="${BasePath}/res/dist/js/public.js"></script>
  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

  <!-- Google Font
  <link rel="stylesheet"
        href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
        -->
<script>
	function loginOut(){
		window.location.href="${BasePath}/sys/main/loginOut";
	}					
	$.ajaxSetup({
	    type: 'POST',
	    complete: function(xhr,status) {
	        var sessionStatus = xhr.getResponseHeader('sessionstatus');
	        if(sessionStatus == 'timeout') {
	            layer.confirm('由于您长时间没有操作, session已过期, 请重新登录.', {
				  	btn: ['确定'] //按钮
				}, function(){
					window.location.href = '${BasePath}/sys/main/index';     
				}, function(){
				});
	        }
	    }
	});
	$(function(){
		$("#formSearch").append("<input type='text' style='display:none;'  class='form-control'/>")
		
			$("button[tag=sys_control_button]").hide();
			<#if rescButtonList??>
				<#list rescButtonList?keys as key>
					$("[tag1=${key}]").show();
				</#list>
			</#if>
			
		$("#btn_updatePwd").on("click",function(){
			$("#updatePwd").modal("show");
		});
		$("#updatePwd").on('hide.bs.modal', function(){
			$("#formUpdatePwd")[0].reset();
		});
		$("#btn_submit").on("click",function(){
			var oldPwd = $("#oldPwd").val();
			var newPwd = $("#newPwd").val();
			if(!oldPwd){
				layer.alert("请输入原始密码",{icon:1});
			}
			if(!newPwd){
				layer.alert("请输入新密码",{icon:1});
			}
			$.ajax({
				url: '${BasePath}/sys/main/updatePassword',
				data:{"loginName":<#if login_user??>'${login_user.loginName!''}'</#if>,"oldPassword":oldPwd,"loginPassword":newPwd},
				type: "POST",
				success: function(data1)
				{
					data1 = JSON.parse(data1);
					if(data1.result){
						$("#updatePwd").modal("hide");
						layer.alert(data1.message,{icon:1});
					}else{
						layer.alert(data1.message,{icon:2});
					}
				}
			});
		});
	});
			
		

</script>
</head>
</#macro>

<#macro navigation title="">
	  <header class="main-header">
    <!-- Logo -->
    <a href="${BasePath}/sys/main/index" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>管理</b>平台</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>眼科筛查</b>管理平台</span>
    </a>

    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>
      <div style="float:left;color:#fff;padding:15px 10px;">欢迎 <#if login_user??>${login_user.loginName!''}</#if></div>
      <!-- Navbar Right Menu -->
      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          <!-- User Account: style can be found in dropdown.less -->
          <li><a href="javascript:;" id="btn_updatePwd"><i class="fa fa-edit"></i>&nbsp;修改密码</a></li>
          <li><a href="javascript:void(0)" onclick = "loginOut()"><i class="fa fa-sign-out"></i>&nbsp;退出</a></li>
        </ul>
      </div>
    </nav>
  </header>
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul id="navigation" class="sidebar-menu" data-widget="tree">
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>
    <!-- 修改密码 -->
	<div class="modal fade" id="updatePwd" backdrop='static' keyboard='false' role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog" role="document" style="width:50%" >
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">修改密码</h4>
                </div>
                <div class="modal-body">
					<form id="formUpdatePwd" class="form-horizontal">
						<div class="form-group">
							<div class="form-group">
							   	<div class="col-sm-2 control-label">原密码</div>
							   	<div class="col-sm-8">
							      <input type="password" class="form-control" id="oldPwd" placeholder="原密码"/>
							    </div>
							</div>
							<div class="form-group">
							   	<div class="col-sm-2 control-label">新密码</div>
							   	<div class="col-sm-8">
							      <input type="text" class="form-control" id="newPwd" placeholder="新密码"/>
							    </div>
							</div>
						</div>
					</form>
				</div>
                <div class="modal-footer">
                    	<button type="button" id="btn_submit" class="btn btn-primary">修改</button>
                    	<button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                    
                </div>
            </div>
        </div>
	</div>

  
</#macro>


<#macro footer title="">
	<footer class="main-footer">
    <div class="pull-right hidden-xs">
      <b>Version</b> 0.0.1
    </div>
    <strong>Copyright &copy; 2014-2018 <a href="http://uniclans.com">Uniclans</a>.</strong> All rights
    reserved.
  </footer>
</#macro>

<#macro index_page>
	<li><a href="${BasePath}/sys/main/index"><i class="fa fa-dashboard"></i> 首页</a></li>
</#macro>
