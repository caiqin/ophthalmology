/** *医生管理** */
$('#btn_query').on("click",function(){
		var query ={}
		query.doctorName=$("#doctorName").val();
		$('#tb_doctors').bootstrapTable('refresh',{query:query});
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
	$('#addDoctor').bootstrapValidator({
        message: '输入的值格式不对',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	doctorName: {
                message: '输入的值格式不对',
                validators: {
                    notEmpty: {
                        message: '医生姓名不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 20,
                        message: '医生姓名字符过长，最多20个字符'
                    }
                }
            },
	        hospitalId: {
	        	message: '输入的值格式不对',
	        	validators: {
	        		notEmpty: {
	        			message: '所属医院不能为空'
	        		}
	        	}
	        },
	        docCatId: {
	        	message: '输入的值格式不对',
	        	validators: {
	        		notEmpty: {
	        			message: '医生专长不能为空'
	        		}
	        	}
	        },
	        doctorIntroduce: {
	        	message: '输入的值格式不对',
	        	validators: {
	        		notEmpty: {
	        			message: '医生介绍不能为空'
	        		},
	        		stringLength: {
	        			min: 1,
	        			max: 128,
	        			message: '医生姓名字符过长，最多128个字符'
	        		}
	        	}
	        }
        }
    })
    .on('success.form.bv', function(e) {
        e.preventDefault();
        var $form = $(e.target);
        var bv = $form.data('bootstrapValidator');
        var data = $('#addDoctor').serialize();
        var select2Obj = $('#addDoctor').find('#hospitalId').select2();
        var hospitalName = select2Obj.find("option:selected").text();
        data = data+"&hospitalName="+hospitalName;
         select2Obj = $('#addDoctor').find('#docCatId').select2();
        var catName = select2Obj.find("option:selected").text();
        data = data+"&docCatName="+catName;
        $.ajax({
			url: basePath+'/doctor/add',
			data: data,
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
					$('#addDoctor')[0].reset();
					$("#addDoctor").data("bootstrapValidator").resetForm();
					$('#addModal').find('#btn_submit').removeClass("disabled");
					$('#addModal').find('#btn_submit').prop('disabled', false);
					$(".fileinput-remove").click();
					$('#tb_doctors').bootstrapTable('refresh');
					layer.alert("添加成功",{icon:1});
				}else{
					layer.alert(data.message,{icon:2});
				}
			}
		});
    });
	$("#updateModal").on("hide.bs.modal",function(){
        $('.fileinput-remove').click();
        $("#editDoctor").data("bootstrapValidator").resetForm();
	});
	$('#editDoctor').bootstrapValidator({
		message: '输入的值格式不对',
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			doctorName: {
                message: '输入的值格式不对',
                validators: {
                    notEmpty: {
                        message: '医生姓名不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 20,
                        message: '医生姓名字符过长，最多20个字符'
                    }
                }
            },
	        hospitalId: {
	        	message: '输入的值格式不对',
	        	validators: {
	        		notEmpty: {
	        			message: '所属医院不能为空'
	        		}
	        	}
	        },
	        docCatId: {
	        	message: '输入的值格式不对',
	        	validators: {
	        		notEmpty: {
	        			message: '医生专长不能为空'
	        		}
	        	}
	        },
	        doctorIntroduce: {
	        	message: '输入的值格式不对',
	        	validators: {
	        		notEmpty: {
	        			message: '医生介绍不能为空'
	        		},
	        		stringLength: {
	        			min: 1,
	        			max: 128,
	        			message: '医生姓名字符过长，最多128个字符'
	        		}
	        	}
	        }
		}
	})
	.on('success.form.bv', function(e) {
		e.preventDefault();
		var $form = $(e.target);
		var bv = $form.data('bootstrapValidator');
		var data = $('#editDoctor').serialize();
        var select2Obj = $('#editDoctor').find('#hospitalId').select2();
        var hospitalName = select2Obj.find("option:selected").text();
        data = data+"&hospitalName="+hospitalName;
         select2Obj = $('#editDoctor').find('#docCatId').select2();
        var catName = select2Obj.find("option:selected").text();
        data = data+"&docCatName="+catName;
		$.ajax({
			url: basePath+'/doctor/update',
			data: data,
			type: "POST",
			beforeSend: function()
			{  
				$('#btn_submit_update').addClass("disabled");
				$('#btn_submit_update').prop('disabled', true);
			},
			success: function(data)
			{
				$('#updateModal').modal('hide');
				$('#editDoctor')[0].reset();
				$('#btn_submit_update').removeClass("disabled");
				$('#btn_submit_update').prop('disabled', false);
				$('#tb_doctors').bootstrapTable('refresh');
				layer.alert("修改成功",{icon:1});
			}
		});
	});
	$('#btn_submit_update').click(function () {
      	$('#editDoctor').submit();
    });
	

	/** ******修改按钮点击事件******* */
	$('#update').click(function(){
		var rows = $('#tb_doctors').bootstrapTable("getSelections");
		if(rows.length==0){
			layer.alert("请选择要修改的数据",{icon:2});
		}else{
			var html="<input type='file' name='txt_file_update' id='txt_file_update' multiple class='file-loading' />";
			$("#update_file").html("");
			$("#update_file").html(html);
			
			var row = rows[0];
			$('#updateModal').modal('show');
			$('#editDoctor').find("#id").val(row.id);
			$('#editDoctor').find('#doctorName').val(row.doctorName);		
			$('#editDoctor').find('#hospitalId').val(row.hospitalId).trigger("change");				
			$('#editDoctor').find('#docCatId').val(row.docCatId).trigger("change");		
			$('#editDoctor').find('#doctorIntroduce').val(row.doctorIntroduce);	
			var da = [];
			if(row.url){
				da.push({"id":row.id,"fileName":'',"url":row.url});
			}
			showPhotos(da);
		}
	});

	
	
	
	 $.ajax({
			url: basePath+'/hospital/getAllData',
			type: "POST",
			success: function(data)
			{
				data = JSON.parse(data);
				var html = "";
				html += "<option value=' '>请选择医院</option>"; 
				for (var i = 0; i < data.length; i++) {
					html += "<option value='"+data[i].hospitalId+"'>"+data[i].hospitalName+"</option>"; 
				}
				var obj = $("#editDoctor").find("#hospitalId");
				var obj1 = $("#addDoctor").find("#hospitalId");
				obj.empty();
				obj.html(html);
				obj1.empty();
				obj1.html(html);
			}
		});
	 $.ajax({
		 url: basePath+'/doctorCat/getAllData',
		 type: "POST",
		 success: function(data)
		 {
			 data = JSON.parse(data);
			 var html = "";
			 html += "<option value=' '>请选择医生专长</option>"; 
			 for (var i = 0; i < data.length; i++) {
				 html += "<option value='"+data[i].catId+"'>"+data[i].catName+"</option>"; 
			 }
			 var obj = $("#editDoctor").find("#docCatId");
			 var obj1 = $("#addDoctor").find("#docCatId");
			 obj.empty();
			 obj.html(html);
			 obj1.empty();
			 obj1.html(html);
		 }
	 });
	
	
	// 1.初始化Table
    var doctorTable = new doctorTableInit();
    doctorTable.Init();
});
function showPhotos(djson){
	//后台返回json字符串转换为json对象    
    var reData = eval(djson);
    // 预览图片json数据组
    var preList = new Array();
    for ( var i = 0;reData&&i < reData.length; i++) {
   		var array_element = reData[i];
		// 此处指针对.txt判断，其余自行添加
		if(array_element.url.indexOf("txt")>0){
			// 非图片类型的展示
			preList[i]= "<div class='file-preview-other-frame'><div class='file-preview-other'><span class='file-icon-4x'><i class='fa fa-file-text-o text-info'></i></span></div></div>"
		}else if(array_element.url.indexOf(".xlsx")>0||array_element.url.indexOf(".xls")>0){
			// 图片类型
			preList[i]= "<img style='width:auto;height:auto;max-width:100%;max-height:100%;' class='img-responsive' src='"+basePath+"/res/images/timg.jpg' class=\"file-preview-image\">";
		}else{
			// 图片类型
			preList[i]= "<img style='width:auto;height:auto;max-width:100%;max-height:100%;' class='img-responsive' src='"+imgRes+array_element.url+"' class=\"file-preview-image\">";
		}
	 }
    var previewJson = preList;
    // 与上面 预览图片json数据组 对应的config数据
    var preConfigList = new Array();
    for ( var i = 0; reData&&i < reData.length; i++) {
	
		var array_element = reData[i];
		  $("#editDoctor").append("<input id="+array_element.id+" class='url-1' name=url value="+array_element.url+" type=hidden />")
		var tjson = {caption: array_element.fileName, // 展示的文件名
					width: '120px', 
					url:basePath+'/doctor/deleteImg',
					key: array_element.id, // 删除是Ajax向后台传递的参数
					extra: {id: array_element.id},
					fileindex:i
					};
		preConfigList[i] = tjson;
	 }
    // 具体参数自行查询
	 $('#txt_file_update').fileinput({
		 language: 'zh', //设置语言
	        uploadUrl: basePath+'/doctor/upload', //上传的地址
	        allowedFileExtensions: ['jpg', 'gif', 'png','jpeg','bmp'],//接收的文件后缀
	        showUpload: true, //是否显示上传按钮
	        showCaption: false,//是否显示标题
	        browseClass: "btn btn-primary", //按钮样式    
	        showPreview: true, //是否显示预览  
	        maxFileCount: 1, //表示允许同时上传的最大文件个数
	        enctype: 'multipart/form-data',
	        validateInitialCount:false,
	        showRemove :true, //显示移除按钮
	        dropZoneEnabled: true,
	        initialPreviewShowDelete:true,  
	        layoutTemplates:'main1',
	        overwriteInitial: false,
	        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
	        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
	     // 这个配置就是解决办法了,初始化时限制图片大小
			initialPreview: previewJson,
			initialPreviewConfig: preConfigList
	    }).on('fileremoved', function(event, key) {  
	        $("#"+key+"").remove();
	    }).on('filedeleted', function(event, key) {  
	        $("#"+key+"").remove();
	    }).on('filesuccessremove', function (event, data, previewId, index){ 
	        $("#"+data+"").remove();
	    });
	    $(".fileinput-remove-button").on("click",function(){
	    	  $(".url-1").remove();
	    });
	    //导入文件上传完成之后的事件
	    $("#txt_file_update").on("fileuploaded", function (event, data, previewId, index) {
	       var url = data.response.images;
	        if (url.length==0) {
	            toastr.error('文件未上传成功！');
	            return;
	        }
	        //1.初始化表格
	       // var oTable = new TableInit();
	       // oTable.Init(data);
	        $("#div_startimport").show();
	      //  console.log(url)
	        $("#editDoctor").append("<input id="+previewId+" class='url-1' name=url value="+url+" type=hidden />")
	    });
}
//初始化fileinput
var FileInput = function () {
  var oFile = new Object();
  //初始化fileinput控件（第一次初始化）
  oFile.Init = function(ctrlName, uploadUrl) {
  var control = $('#' + ctrlName);

  //初始化上传控件的样式
  control.fileinput({
      language: 'zh', //设置语言
      uploadUrl: uploadUrl, //上传的地址
      allowedFileExtensions: ['jpg', 'gif', 'png','jpeg','bmp'],//接收的文件后缀
      showUpload: true, //是否显示上传按钮
      showCaption: false,//是否显示标题
      browseClass: "btn btn-primary", //按钮样式    
      showPreview: true, //是否显示预览  
      maxFileCount: 1, //表示允许同时上传的最大文件个数
      enctype: 'multipart/form-data',
      validateInitialCount:true,
      previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
      msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！"
  }).on('fileremoved', function(event, key) { 
      $("#"+key+"").remove();
  }).on('filedeleted', function(event, key) {  
      $("#"+key+"").remove();
  }).on('filedeleted', function(event, key) {  
      $("#"+key+"").remove();
  }).on('filesuccessremove', function (event, data, previewId, index){  
      $("#"+data+"").remove();
  });
  $(".fileinput-remove-button").on("click",function(){
  	  $(".url-2").remove();
  })
  //导入文件上传完成之后的事件
  $("#txt_file").on("fileuploaded", function (event, data, previewId, index) {
     var url = data.response.images;
      if (url.length==0) {
          toastr.error('文件未上传成功！');
          return;
      }
      $("#div_startimport").show();
      $("#addDoctor").append("<input id="+previewId+" class='url-2' name='url' value="+url+" type=hidden />")
  });
}
  return oFile;
};


$(function(){
	//指定上传controller访问地址
	var path = basePath+'/doctor/upload';
	//页面初始化加载initFileInput()方法传入ID名和上传地址
	var oFileInput = new FileInput();
    oFileInput.Init("txt_file", path);
});

var doctorTableInit = function () {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.Init = function () {
        $('#tb_doctors').bootstrapTable({
            url: basePath+'/doctor/getData',         // 请求后台的URL（*）
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
                field: 'doctorName',
            	align:'center',
                title: '医生姓名'
            }, {
            	field:'hospitalName',
            	width:200,
            	align:'center',
            	title: '所属医院'
            },{
            	field:'docCatName',
            	width:200,
            	align:'center',
            	title: '医生专长'
            },{
	        	field:'doctorIntroduce',
	        	width:200,
	        	align:'center',
	        	title: '医生介绍'
	        }]
        });
    };

    // 得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            rows: params.limit,   // 页面大小
            page: params.offset/params.limit  // 页码
        };
        return temp;
    };
    return oTableInit;
};
