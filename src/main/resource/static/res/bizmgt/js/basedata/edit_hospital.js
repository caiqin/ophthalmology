$('#formUpdate').bootstrapValidator({
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
		var url=basePath+'/hospital/update';
		$.ajax({ 
			type: "POST",
		  	url: url,
		  	data: $("#formUpdate").serialize(),
			success: function(msg){
		    	var message=eval("("+msg+")");
		    	if(message.result){
		    		layer.alert("修改医院成功!",{icon:1,title:"修改"},function(){
			    		history.back();
		    		});
		    	}else{
		    		layer.alert("修改医院失败!",{icon:2,title:"修改"});
		    	}
		    	
			}
		});
    });