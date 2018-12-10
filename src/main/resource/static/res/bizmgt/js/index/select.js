$(function(){
	var url=basePath+"/production_line/selects.ihtml";
	$.ajax({ 
		type: "POST",
	  	url: url,
		success: function(msg){
			var data=msg;
			var options="<option value=>暂无生产线</option>";
	    	if(data){
	    		options="";
	    		for(var i=0;i<data.length;i++){
	    			options+="<option value="+data[i].productionLineNo+">"+data[i].productionLineName+"</option>";
	    		}
	    	}
			$("#productionLineNo").append(options);
			refreshOptions();
		}
	});
	$("#productionLineNo").change(function(){
		refreshOptions();
	})
	
	//var refreshOptions=function(){}
	

});
var intervalTime;
function refreshOptions(){

	var productionLineNo=$("#productionLineNo").val();
	var url1=basePath+"/production_line/select_by_no.ihtml?productionLineNo="+productionLineNo;
	$.ajax({ 
		type: "POST",
	  	url: url1,
		success: function(msg){
			var data=msg;
	    	if(data){
	    		if(intervalTime){
	    			window.clearInterval(intervalTime);
	    		}
	    		if(data.interval&&parseInt(data.interval)>0){
	    			//console.log(data.interval);
		    		var second=parseInt(data.interval)*1000;
		    		//console.log(second);
		    		intervalTime=setInterval("refreshOptions()",second);
	    		}
	    		if(data.status){
	    			if(data.status==1){
	    				$("#status").html("停机");
	    			}else if(data.status==2){
	    				$("#status").html("启动");
	    			}else if(data.status==3){
	    				$("#status").html("预警");
	    			}else if(data.status==4){
	    				$("#status").html("警告");
	    			}
	    		}else{
	    			$("#status").html("暂无");
	    		}
	    		var orders="";
	    		var orderVos=data.orderVos;
	    		if(orderVos&&orderVos.length>0){
		    		for(var i=0;i<orderVos.length;i++){
		    			if(orderVos.length==1||orderVos.length-1==i){
		    				orders+=orderVos[i].orderNo+"("+orderVos[i].customName+")";
		    			}else{
		    				orders+=orderVos[i].orderNo+"("+orderVos[i].customName+") || ";
		    			}
		    			
		    		}
		    		$("#orderNos").html(orders);
		    	}else{
		    		$("#orderNos").html("暂无");
		    	}
	    		var barData=new Array();
	    		if(data.productionTaskVo){
	    			$("#orderProductNo").html(data.productionTaskVo.productNo);
	    			$("#materialNo").html(data.productionTaskVo.materialNo);
	    			$("#batchNo").html(data.productionTaskVo.productBatchNo);
	    		}else{
	    			$("#orderProductNo").html("暂无");
	    			$("#materialNo").html("暂无");
	    			$("#batchNo").html("暂无");
	    		}
	    		if(data.status==1){
	    			$("#orderProductNo").html("暂无");
	    			$("#materialNo").html("暂无");
	    			$("#batchNo").html("暂无");
	    			$("#orderNos").html("暂无");
    			}
	    		//if(data.c5Status){
	    			//console.log(data.c5Status);
	    			if(data.c5Status==2){
	    				$("#c5_status").html("采集中");
	    			}else{
	    				$("#c5_status").html("停止");
	    			}
	    		//}
	    		$("#Hoff1_Speed").html(0);
	    		$("#Ext1_ScrewSpeed").html(0);
	    		$("#Ext1_DosingStation").html(0);
	    		$("#Ext1_BarrelZone01").html(0);
	    		var deviceParamVos=data.deviceParamVos;
	    		if(deviceParamVos&&deviceParamVos.length>0){
	    			for(var i=deviceParamVos.length-1;i>=0;i--){
		    			var paramNo=deviceParamVos[i].paramNo;
		    			var liveValue=deviceParamVos[i].liveValue;
		    			if(!liveValue){
		    				liveValue=0;
		    			}
		    			var bar1=new Object();
			    		bar1.value=45;
			    		
		    			if(paramNo=="Us1_SectorThickness.val1"){
		    				bar1.name="0度："+liveValue;
		    				barData.push(bar1);
		    			}else if(paramNo=="Us1_SectorThickness.val2"){
		    				bar1.name="45度："+liveValue;
		    				barData.push(bar1);
		    			}else if(paramNo=="Us1_SectorThickness.val3"){
		    				bar1.name="90度："+liveValue;
		    				barData.push(bar1);
		    			}else if(paramNo=="Us1_SectorThickness.val4"){
		    				bar1.name="135度："+liveValue;
		    				barData.push(bar1);
		    			}else if(paramNo=="Us1_SectorThickness.val5"){
		    				bar1.name="180度："+liveValue;
		    				barData.push(bar1);
		    			}else if(paramNo=="Us1_SectorThickness.val6"){
		    				bar1.name="225度："+liveValue;
		    				barData.push(bar1);
		    			}else if(paramNo=="Us1_SectorThickness.val7"){
		    				bar1.name="270度："+liveValue;
		    				barData.push(bar1);
		    			}else if(paramNo=="Us1_SectorThickness.val8"){
		    				bar1.name="315度："+liveValue;
		    				barData.push(bar1);
		    			}else if(paramNo=="Hoff1_Speed.Act"){
		    				if(liveValue<=0){
		    					$("#Hoff1_Speed").html(0);
		    				}else{
		    					$("#Hoff1_Speed").html(liveValue);
		    				}
		    			}else if(paramNo=="Ext1_ScrewSpeed.Act"){
		    				$("#Ext1_ScrewSpeed").html(liveValue);
		    			}else if(paramNo=="Ext1_DosingStation.ActThroughput"){
		    				$("#Ext1_DosingStation").html(liveValue);
		    			}else if(paramNo=="Ext1_BarrelZone01.Act"){
		    				$("#Ext1_BarrelZone01").html(liveValue);
		    			}
		    		}
	    			//console.log(barData);
	    		}
	    		
	    		
	    		
	    		var myChart = echarts.init(document.getElementById('bar'));
		    	option = {
		    			title : {
		    		        text: '超声波壁厚',
		    		        x:'left'
		    		    },
		    	    tooltip: {
		    	        trigger: 'item',
		    	        formatter: "{a} <br/>{b}"
		    	    },
		    	    series: [
		    	        {
		    	            name:'壁厚',
		    	            type:'pie',
		    	            selectedMode: 'single',
		    	            radius: [0, '10%'],

		    	            label: {
		    	                normal: {
		    	                    position: 'inner'
		    	                }
		    	            },
		    	            labelLine: {
		    	                normal: {
		    	                    show: false
		    	                }
		    	            }
		    	        },
		    	        {
		    	            name:'超声波壁厚值1',
		    	            type:'pie',
		    	            radius: ['45%', '55%'],
		    	            label: {
		    	                normal: {
		    	                    formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}}  ',
		    	                    backgroundColor: '#eee',
		    	                    borderColor: '#aaa',
		    	                    borderWidth: 1,
		    	                    borderRadius: 4,
		    	                    // shadowBlur:3,
		    	                    // shadowOffsetX: 2,
		    	                    // shadowOffsetY: 2,
		    	                    // shadowColor: '#999',
		    	                    // padding: [0, 7],
		    	                    rich: {
		    	                        a: {
		    	                            color: '#999',
		    	                            lineHeight: 12,
		    	                            align: 'center'
		    	                        },
		    	                        // abg: {
		    	                        //     backgroundColor: '#333',
		    	                        //     width: '100%',
		    	                        //     align: 'right',
		    	                        //     height: 22,
		    	                        //     borderRadius: [4, 4, 0, 0]
		    	                        // },
		    	                        hr: {
		    	                            borderColor: '#aaa',
		    	                            width: '100%',
		    	                            borderWidth: 0.5,
		    	                            height: 0
		    	                        },
		    	                        b: {
		    	                            fontSize: 12,
		    	                            lineHeight: 33
		    	                        },
		    	                        per: {
		    	                            color: '#eee',
		    	                            backgroundColor: '#334455',
		    	                            padding: [2, 4],
		    	                            borderRadius: 2
		    	                        }
		    	                    }
		    	                }
		    	            },
		    	            data:barData
		    	        }
		    	    ]
		    	};
		    	// 使用刚指定的配置项和数据显示图表。
			    myChart.setOption(option);
	    	}
	    	
			
		}
	});
	// 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('curve'));
    var url2=basePath+"/param_record/select_page.ihtml";
	var data=new Object();
	data.productionLineNo=productionLineNo;
	data.paramNo="Ext1_ScrewSpeed.Act";
	data.rows=100;
	//data.orderBy="asc";
	$.ajax({ 
		type: "POST",
	  	url: url2,
	  	data:data,
		success: function(msg){
			var data=msg;
	    	if(data){
	    		var rows=data.rows;
	    		var recordTimes=new Array();
	    		var paramValueActs=new Array();
	    		var j=0;
	    		for(var i=rows.length-1;i>=0;i--){
	    			var recordTime=rows[i].recordTimeString;
	    			var paramValue=rows[i].paramValue;
	    			if(rows[i].paramValue=="?"){
	    				paramValue=0;
	    			}
	    			recordTimes[j]=recordTime;
	    			paramValueActs[j]=paramValue;
	    			j++;
	    		} 
	    	}
	    	data=new Object();
			data.productionLineNo=productionLineNo;
			data.paramNo="Ext1_ScrewSpeed.Set";
			data.rows=100;
			//data.orderBy="asc";
			$.ajax({ 
				type: "POST",
			  	url: url2,
			  	data:data,
				success: function(msg){
					var data=msg;
			    	if(data){
			    		var rows=data.rows;
			    		var recordTimes=new Array();
			    		var paramValueSets=new Array();
			    		var j=0;
			    		for(var i=rows.length-1;i>=0;i--){
			    			var recordTime=rows[i].recordTimeString;
			    			var paramValue=rows[i].paramValue;
			    			if(rows[i].paramValue=="?"){
			    				paramValue=0;
			    			}
			    			recordTimes[j]=recordTime;
			    			paramValueSets[j]=paramValue;
			    			j++;
			    		} 
			    		// 指定图表的配置项和数据
					    var option = {
					    	    title: {
					    	        text: '主机速度值'
					    	    },
					    	    tooltip: {
					    	        trigger: 'axis'
					    	    },
					    	    legend: {
					    	        data:['主机速度实际值','主机速度设定值']
					    	    },
					    	    grid: {
					    	        left: '3%',
					    	        right: '4%',
					    	        bottom: '3%',
					    	        containLabel: true
					    	    },
					    	    toolbox: {
					    	        feature: {
					    	            saveAsImage: {}
					    	        }
					    	    },
					    	    xAxis: {
					    	        type: 'category',
					    	        boundaryGap: false,
					    	        data:recordTimes
					    	    },
					    	    yAxis: {
					    	        type: 'value'
					    	    },
					    	    series: [
					    	        {
					    	            name:'主机速度实际值',
					    	            type:'line',
					    	            data:paramValueActs
					    	        },{
					    	            name:'主机速度设定值',
					    	            type:'line',
					    	            data:paramValueSets
					    	        }
					    	    ]
					    	};


					    // 使用刚指定的配置项和数据显示图表。
					    myChart.setOption(option);
			    	}
			    	
				}
			});
	    	
		}
	});
	
	// 基于准备好的dom，初始化echarts实例
    var myChart1 = echarts.init(document.getElementById('curve1'));
    var url2=basePath+"/param_record/select_page.ihtml";
	var data=new Object();
	data.productionLineNo=productionLineNo;
	data.paramNo="Ext1_DosingStation.ActThroughput";
	data.rows=100;
	//data.orderBy="asc";
	$.ajax({ 
		type: "POST",
	  	url: url2,
	  	data:data,
		success: function(msg){
			var data=msg;
	    	if(data){
	    		var rows=data.rows;
	    		var recordTimes=new Array();
	    		var paramValueActs=new Array();
	    		var j=0;
	    		for(var i=rows.length-1;i>=0;i--){
	    			var recordTime=rows[i].recordTimeString;
	    			var paramValue=rows[i].paramValue;
	    			if(rows[i].paramValue=="?"){
	    				paramValue=0;
	    			}
	    			recordTimes[j]=recordTime;
	    			paramValueActs[j]=paramValue;
	    			j++;
	    		} 
	    	}
	    	data=new Object();
			data.productionLineNo=productionLineNo;
			data.paramNo="Ext1_DosingStation.SetThroughput";
			data.rows=100;
			//data.orderBy="asc";
			$.ajax({ 
				type: "POST",
			  	url: url2,
			  	data:data,
				success: function(msg){
					var data=msg;
			    	if(data){
			    		var rows=data.rows;
			    		var recordTimes=new Array();
			    		var paramValueSets=new Array();
			    		var j=0;
			    		for(var i=rows.length-1;i>=0;i--){
			    			var recordTime=rows[i].recordTimeString;
			    			var paramValue=rows[i].paramValue;
			    			if(rows[i].paramValue=="?"){
			    				paramValue=0;
			    			}
			    			recordTimes[j]=recordTime;
			    			paramValueSets[j]=paramValue;
			    			j++;
			    		} 
			    		// 指定图表的配置项和数据
					    var option = {
					    	    title: {
					    	        text: '挤出产量'
					    	    },
					    	    tooltip: {
					    	        trigger: 'axis'
					    	    },
					    	    legend: {
					    	        data:['挤出产量实际值','挤出产量设定值']
					    	    },
					    	    grid: {
					    	        left: '3%',
					    	        right: '4%',
					    	        bottom: '3%',
					    	        containLabel: true
					    	    },
					    	    toolbox: {
					    	        feature: {
					    	            saveAsImage: {}
					    	        }
					    	    },
					    	    xAxis: {
					    	        type: 'category',
					    	        boundaryGap: false,
					    	        data:recordTimes
					    	    },
					    	    yAxis: {
					    	        type: 'value'
					    	    },
					    	    series: [
					    	        {
					    	            name:'挤出产量实际值',
					    	            type:'line',
					    	            data:paramValueActs
					    	        },{
					    	            name:'挤出产量设定值',
					    	            type:'line',
					    	            data:paramValueSets
					    	        }
					    	    ]
					    	};


					    // 使用刚指定的配置项和数据显示图表。
					    myChart1.setOption(option);
			    	}
			    	
				}
			});
	    	
		}
	});
	
	
	
	// 基于准备好的dom，初始化echarts实例
    var myChart3 = echarts.init(document.getElementById('curve2'));
    var url3=basePath+"/param_record/select_page.ihtml";
	var data3=new Object();
	data3.productionLineNo=productionLineNo;
	data3.paramNo="Ext1_BarrelZone01.Act";
	data3.rows=100;
	//data.orderBy="asc";
	$.ajax({ 
		type: "POST",
	  	url: url3,
	  	data:data3,
		success: function(msg){
			var data=msg;
	    	if(data){
	    		var rows=data.rows;
	    		var recordTimes=new Array();
	    		var paramValueActs=new Array();
	    		var j=0;
	    		for(var i=rows.length-1;i>=0;i--){
	    			var recordTime=rows[i].recordTimeString;
	    			var paramValue=rows[i].paramValue;
	    			if(rows[i].paramValue=="?"){
	    				paramValue=0;
	    			}
	    			recordTimes[j]=recordTime;
	    			paramValueActs[j]=paramValue;
	    			j++;
	    		} 
	    	}
	    	data=new Object();
			data.productionLineNo=productionLineNo;
			data.paramNo="Ext1_BarrelZone01.Set";
			data.rows=100;
			//data.orderBy="asc";
			$.ajax({ 
				type: "POST",
			  	url: url3,
			  	data:data,
				success: function(msg){
					var data=msg;
			    	if(data){
			    		var rows=data.rows;
			    		var recordTimes=new Array();
			    		var paramValueSets=new Array();
			    		var j=0;
			    		for(var i=rows.length-1;i>=0;i--){
			    			var recordTime=rows[i].recordTimeString;
			    			var paramValue=rows[i].paramValue;
			    			if(rows[i].paramValue=="?"){
			    				paramValue=0;
			    			}
			    			recordTimes[j]=recordTime;
			    			paramValueSets[j]=paramValue;
			    			j++;
			    		} 
			    		// 指定图表的配置项和数据
					    var option = {
					    	    title: {
					    	        text: '机筒一区温度'
					    	    },
					    	    tooltip: {
					    	        trigger: 'axis'
					    	    },
					    	    legend: {
					    	        data:['机筒一区温度实际值','机筒一区温度设定值']
					    	    },
					    	    grid: {
					    	        left: '3%',
					    	        right: '4%',
					    	        bottom: '3%',
					    	        containLabel: true
					    	    },
					    	    toolbox: {
					    	        feature: {
					    	            saveAsImage: {}
					    	        }
					    	    },
					    	    xAxis: {
					    	        type: 'category',
					    	        boundaryGap: false,
					    	        data:recordTimes
					    	    },
					    	    yAxis: {
					    	        type: 'value'
					    	    },
					    	    series: [
					    	        {
					    	            name:'机筒一区温度实际值',
					    	            type:'line',
					    	            data:paramValueActs
					    	        },{
					    	            name:'机筒一区温度设定值',
					    	            type:'line',
					    	            data:paramValueSets
					    	        }
					    	    ]
					    	};


					    // 使用刚指定的配置项和数据显示图表。
					    myChart3.setOption(option);
			    	}
			    	
				}
			});
	    	
		}
	});

}