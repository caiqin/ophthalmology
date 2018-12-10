
$('#addFirstRes').click(function(){
    $('#resName').val("");
    $('#level').val("1");
    $('#supperResId').val("#");
    $('#supperResName').val("#");
});

$("#btn_save").on("click",function(){
	var supperResId = $('#supperResId').val();
    if(supperResId==""){
        layer.alert("请选择父节点",{icon:2,title:"修改"});
        return false;
    }
   	var resCode=$("#resCode").val();
    if(!resCode){
        layer.alert("请输入资源编码",{icon:2,title:"修改"});
        return false;
    }
	var url=basePath+"/sys/system/resource/updateResource";
	$.ajax({ 
		type: "POST",
	  	url: url,
	  	data: $("#formUpdate").serialize(),
		success: function(msg){
		
	    	var message=eval("("+msg+")");
	    	
	    	if(message.result){
	    		layer.alert("修改资源成功!",{icon:1,title:"修改"},function(){
		    		history.back();
	    		});
	    	}else{
	    		layer.alert("修改资源失败!",{icon:2,title:"修改"});
	    	}
	    	
		}
	});
});
