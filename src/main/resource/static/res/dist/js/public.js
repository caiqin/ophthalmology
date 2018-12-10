$(function(){
		$.ajax({
			url:basePath+"/sys/main/leftPage?t=t",
			data:{},
			type:"post",
			dataType:"json",
			cache:false,
			success:function(data){
				var innerHtml = "";
				if(data&&data.data.length>0){
					for(var i=0;i<data.data.length;i++){
						var row = data.data[i];
						var children = row.children;
						innerHtml = innerHtml + '<li class="treeview">';
						innerHtml = innerHtml + '<li class="treeview">';
			          	innerHtml = innerHtml + '<a href="#">';
			          	innerHtml = innerHtml + '<i class="fa fa-cog"></i> <span>'+row.text+'</span>';
			            innerHtml = innerHtml + '<span class="pull-right-container">';
			            innerHtml = innerHtml + '<i class="fa fa-angle-left pull-right"></i>';
			            innerHtml = innerHtml + '</span>';
			            innerHtml = innerHtml + '</a>';
			            innerHtml = innerHtml + '<ul class="treeview-menu">';
			            
			            if(children&&children.length>0){
                        	for(var j=0;j<children.length;j++){
					            innerHtml = innerHtml + '<li><a href="'+children[j].href+'"><i class="fa fa-circle-o"></i> '+children[j].text+'</a></li>';
                        	}
                        }
			           
			            innerHtml = innerHtml + '</ul>';
			            innerHtml = innerHtml + '</li>';
					}					
				}
				$('#navigation').append(innerHtml);
				var url = document.location.toString();
				var realPath = GetUrlRelativePath(url);
				var aEle = $("a[href='"+realPath+"']");
				if(aEle.length!=0){
					aEle.parent().addClass("active");
					aEle.parent().parent().parent().addClass("active").addClass("menu-open");
					aEle.parent().parent().show();
				}else{
					var url = document.referrer.toString();
					var realPath = GetUrlRelativePath(url);
					aEle = $("a[href='"+realPath+"']");
					aEle.parent().addClass("active");
					aEle.parent().parent().parent().addClass("active").addClass("menu-open");
					aEle.parent().parent().show();
				}
			}
		});
	});
function GetUrlRelativePath(url)
{
	var arrUrl = url.split("//");
	var start = arrUrl[1].indexOf("/");
	var relUrl = arrUrl[1].substring(start);// stop省略，截取从start开始到结尾的所有字符

	if(relUrl.indexOf("?") != -1){
		relUrl = relUrl.split("?")[0];
	}
	return relUrl;
} 

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
Date.prototype.format = function(fmt)   
{
	var o = {   
		"M+" : this.getMonth()+1,                 // 月份
		"d+" : this.getDate(),                    // 日
		"h+" : this.getHours(),                   // 小时
		"m+" : this.getMinutes(),                 // 分
		"s+" : this.getSeconds(),                 // 秒
		"q+" : Math.floor((this.getMonth()+3)/3), // 季度
		"S"  : this.getMilliseconds()             // 毫秒
	};   
	if(/(y+)/.test(fmt)){
		fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
	}   
	for(var k in o)   
	if(new RegExp("("+ k +")").test(fmt)){
		fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
	}
	return fmt;   
}  