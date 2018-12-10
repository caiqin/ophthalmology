$(function(){
	$('#formAdd').bootstrapValidator({
        message: '输入的值格式不对',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	resName: {
                message: '输入的值格式不对',
                validators: {
                    notEmpty: {
                        message: '资源名称不能为空'
                    }
                }
            },
            resUrl: {
                validators: {
                	notEmpty: {
                        message: 'url不能为空'
                    }
                }
            },
            rescCode: {
                validators: {
                	notEmpty: {
            			message: '资源编码不能为空'
            		}
                }
            },
            supperResName: {
            	validators: {
            		notEmpty: {
            			message: '请选择父资源'
            		}
            	}
            },
            seqNum: {
            	validators: {
            		notEmpty: {
                        message: '排序不能为空'
                    },
                	regexp: {
                        regexp: /^\d$/,
                        message: '排序输入格式不对，请输入数字'
                    }
            	}
            },
            level: {
                validators: {
                	notEmpty: {
                        message: '级别不能为空'
                    },
                    numeric: {
                        message: '级别输入格式不对，请输入数字'
                    }
                }
            },
            remark: {
                validators: {
                	stringLength: {
                		min: 0,
                        max: 200,
                        message: '备注长度最多200个字符'
                    }
                }
            }
        }
    })
    .on('success.form.bv', function(e) {
        e.preventDefault();
        var $form = $(e.target);
        var bv = $form.data('bootstrapValidator');
		var url=basePath+'/sys/system/resource/addResource';
		$.ajax({ 
			type: "POST",
		  	url: url,
		  	data: $("#formAdd").serialize(),
			success: function(msg){
		    	var message=eval("("+msg+")");
		    	if(message.result){
		    		layer.alert("添加资源成功!",{icon:1,title:"添加"},function(){
			    		history.back();
		    		});
		    	}else{
		    		layer.alert("添加资源失败!",{icon:2,title:"添加"});
		    	}
		    	
			}
		});
    });
	
	
});



$('#addFirstRes').click(function(){
    $('#resName').val("");
    $('#level').val("1");
    $('#supperResId').val("#");
    $('#supperResName').val("#");
});

$('#tree').treeview({
	data: resourceList,
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
