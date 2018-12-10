<!DOCTYPE html>
<html>
<head>
<#assign basePath = request.contextPath/>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>眼科筛查管理平台 | 登录</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="${basePath}/res/bower_components/bootstrap/dist/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="${basePath}/res/bower_components/font-awesome/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="${basePath}/res/bower_components/Ionicons/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="${basePath}/res/dist/css/AdminLTE.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="${basePath}/res/plugins/iCheck/square/blue.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    <b>眼科筛查</b>管理平台
  </div>
  <div class="login-box-body">
    <p class="login-box-msg">请登录</p>
    <form  role='form' id="loginForm" action="${basePath}/sys/main/login" onSubmit="return commitForm()" method="post">
      <div class="form-group has-feedback">
        <input type="text" id="loginName" name="loginName" class="form-control" placeholder="用户名">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" id="loginPassword" name="loginPassword" class="form-control" placeholder="密码">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-12">
          <button type="submit"  onclick="commitForm();" class="btn btn-primary btn-block btn-flat">登录</button>
        </div>
      </div>
    </form>
</div>


</body>
<!-- jQuery 3 -->
<script src="${basePath}/res/bower_components/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="${basePath}/res/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="${basePath}/res/plugins/iCheck/icheck.min.js"></script>
<!-- Layer -->
<script type="text/javascript"  src="${basePath}/res/bootstrap/js/layer/layer.js"></script>
<script>
  $(function () {
  	var warnMessagekey = "${warnMessagekey!""}";
  	if(warnMessagekey){
  		layer.alert(warnMessagekey,{icon:2});
  	}
  
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
  });
    document.onkeydown=function(event){
      e = event ? event : (window.event ? window.event : null);
      if(e.keyCode==13){
          commitForm(); 
      }
    }
   function commitForm(){
      var loginForm=document.getElementById('loginForm');
      if($.trim($("#loginName").val()) == "" || $.trim($("#loginName").val()) == null){
           $("#loginName").focus();
           return false;
      }
      if($.trim($("#loginPassword").val())==null || $.trim($("#loginPassword").val())==""){
           $("#loginPassword").focus();
           return false;
    }
       loginForm.submit();
       return true;
   }
</script>
</html>
