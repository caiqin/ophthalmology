$(function(){
	$('#formAdd').bootstrapValidator({
        message: '输入的值格式不对',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	hospitalName: {
                message: '输入的值格式不对',
                validators: {
                    notEmpty: {
                        message: '医院名称不能为空'
                    }
                }
            },
            parentName: {
            	validators: {
            		notEmpty: {
            			message: '请选择上级医院'
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
            hospitalIntroduce: {
                validators: {
                	stringLength: {
                		min: 0,
                        max: 200,
                        message: '医院介绍长度最多200个字符'
                    }
                }
            }
        }
    })
    .on('success.form.bv', function(e) {
        e.preventDefault();
        var $form = $(e.target);
        var bv = $form.data('bootstrapValidator');
		var url=basePath+'/hospital/add';
		$.ajax({ 
			type: "POST",
		  	url: url,
		  	data: $("#formAdd").serialize(),
			success: function(msg){
		    	var message=eval("("+msg+")");
		    	if(message.result){
		    		layer.alert("添加医院成功!",{icon:1,title:"添加"},function(){
			    		history.back();
		    		});
		    	}else{
		    		layer.alert("添加医院失败!",{icon:2,title:"添加"});
		    	}
		    	
			}
		});
    });
	
	
});


$('#addFirstHospital').click(function(){
    $('#hospitalName').val("");
    $('#level').val("1");
    $('#parentNo').val("#");
    $('#parentName').val("#");
});

$('#tree').treeview({
	data: hospitalList,
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
