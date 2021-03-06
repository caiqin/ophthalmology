/** *疾病种类管理** */
$('#btn_query').on("click",function(){
		$('#tb_diseaseTypes').bootstrapTable('refresh');
	});
document.onkeydown=function(event){
    e = event ? event : (window.event ? window.event : null);
    if(e.keyCode==13){
    	$('#btn_query').click();
    }
}
$("#formSearch").on("submit",function(){
	return false;
});
$(function(){
	$('#addDiseaseTypeSuggest').bootstrapValidator({
        message: '输入的值格式不对',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	diseaseTypeId: {
            	validators: {
            		notEmpty: {
                        message: '请选择疾病种类'
                    }
            	}
            },suggestContent: {
            	validators: {
            		notEmpty: {
                        message: '建议内容不能为空'
                    },
                    stringLength: {
                		min: 1,
                        max: 128,
                        message: '建议内容长度必须在1到128之间'
                    }
            	}
            }
        }
    })
    .on('success.form.bv', function(e) {
        e.preventDefault();
        var $form = $(e.target);
        var bv = $form.data('bootstrapValidator');
        $.ajax({
			url: basePath+'/diseasetypesuggest/add',
			data: $('#addDiseaseTypeSuggest').serialize()+"&diseaseTypeName="+$("#addDiseaseTypeSuggest").find("#diseaseTypeId option:selected").text(),
			type: "POST",
			beforeSend: function()
			{  
				$('#addModal').find('#btn_submit').addClass("disabled");
				$('#addModal').find('#btn_submit').prop('disabled', true);
			},
			success: function(data)
			{
				var data = JSON.parse(data);
				if(data.result){
					$('#addModal').modal('hide');
					$('#addDiseaseTypeSuggest')[0].reset();
					$("#addDiseaseTypeSuggest").data("bootstrapValidator").resetForm();
					$('#addModal').find('#btn_submit').removeClass("disabled");
					$('#addModal').find('#btn_submit').prop('disabled', false);
					$('#tb_diseaseTypeSuggests').bootstrapTable('refresh');
					layer.alert("添加成功",{icon:1});
				}else{
					layer.alert(data.message,{icon:2});
				}
			}
		});
    });
	$('#editDiseaseTypeSuggest').bootstrapValidator({
        message: '输入的值格式不对',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	suggestContent: {
            	validators: {
            		notEmpty: {
                        message: '建议内容不能为空'
                    },
                    stringLength: {
                		min: 1,
                        max: 128,
                        message: '建议内容长度必须在1到128之间'
                    }
            	}
            }
        }
    })
    .on('success.form.bv', function(e) {
		e.preventDefault();
		var $form = $(e.target);
		var bv = $form.data('bootstrapValidator');
		$.ajax({
			url: basePath+'/diseasetypesuggest/update',
			data: $('#editDiseaseTypeSuggest').serialize(),
			type: "POST",
			beforeSend: function()
			{  
				$('#btn_submit_update').addClass("disabled");
				$('#btn_submit_update').prop('disabled', true);
			},
			success: function(data)
			{
				$('#updateModal').modal('hide');
				$('#editDiseaseTypeSuggest')[0].reset();
				$('#btn_submit_update').removeClass("disabled");
				$('#btn_submit_update').prop('disabled', false);
				$('#tb_diseaseTypeSuggests').bootstrapTable('refresh');
				layer.alert("修改成功",{icon:1});
			}
		});
	});
	
	$('#btn_submit_update').click(function () {
      	$('#editDiseaseTypeSuggest').submit();
    });

	/** ******修改按钮点击事件******* */
	$('#update').click(function(){
		var rows = $('#tb_diseaseTypeSuggests').bootstrapTable("getSelections");
		if(rows.length==0){
			layer.alert("请选择要修改的数据",{icon:2});
		}else{
			var row = rows[0];
			$('#editDiseaseTypeSuggest').find("#id").val(row.id);
			$('#editDiseaseTypeSuggest').find('#diseaseTypeId').val(row.diseaseTypeId);		
			$('#editDiseaseTypeSuggest').find('#suggestContent').val(row.suggestContent);		
			$('#updateModal').modal('show');
		}
	});

	// 1.初始化Table
    var ht = new hospitalTable();
    ht.Init();
    
    $.ajax({
    	url: basePath+'/diseasetype/getAllData',
    	type: "POST",
    	success: function(data)
    	{
    		data = JSON.parse(data);
    		var html = "";
    		html += "<option value=' '>请选择疾病种类</option>"; 
    		for (var i = 0; i < data.length; i++) {
    			html += "<option value='"+data[i].diseaseTypeId+"'>"+data[i].diseaseTypeName+"</option>"; 
    		}
    		var obj = $("#addDiseaseTypeSuggest").find("#diseaseTypeId");
    		var obj1 = $("#editDiseaseTypeSuggest").find("#diseaseTypeId");
    		obj.empty();
    		obj.html(html);
    		obj1.empty();
    		obj1.html(html);
    	}
    });
});



var hospitalTable = function () {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.Init = function () {
        $('#tb_diseaseTypeSuggests').bootstrapTable({
            url: basePath+'/diseasetypesuggest/getData',         // 请求后台的URL（*）
            method: 'post',                      // 请求方式（*）
            contentType:"application/x-www-form-urlencoded; charset=UTF-8",
            toolbar: '#toolbar',                // 工具按钮用哪个容器
            striped: true,                      // 是否显示行间隔色
            cache: false,                       // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   // 是否显示分页（*）
            queryParams: oTableInit.queryParams,// 传递参数（*）
            sidePagination: "server",           // 分页方式：client客户端分页，server服务端分页（*）
            pageSize: 10,                       // 每页的记录行数（*）
            pageList: [10, 25, 50, 100],        // 可供选择的每页的行数（*）
            showColumns: true,                  // 是否显示所有的列
            showRefresh: true,                  // 是否显示刷新按钮
            minimumCountColumns: 2,             // 最少允许的列数
            clickToSelect: true,                // 是否启用点击选中行
            singleSelect:true,
            height: 527,                        // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
            columns: [
            {
                checkbox: true
            },{
                field: 'diseaseTypeName',
            	align:'center',
            	width:200,
                title: '种类名称'
            },{
	        	field:'suggestContent',
	        	width:200,
	        	align:'center',
	        	title: '建议'
	        }]
        });
    };

    // 得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            rows: params.limit,   // 页面大小
            page: params.offset/params.limit,  // 页码
            suggestContent:$("#formSearch").find("#suggestContent").val()
        };
        return temp;
    };
    return oTableInit;
};
