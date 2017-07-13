
app.controller('componentsChartController', ['$scope', '$http', '$location', '$timeout', 'suffix',function($scope, $http, $location, $timeout,  $suffix) {

    $scope.lineOption = {
        version: 1,
        title: {
            text: "折线图"
        },
        tooltip: {
            trigger: 'axis'
        },
        /*legend: {
         data: ['cetc']
         },*/
        xAxis: [
            {
                type: 'category',
                name : '时间',
                boundaryGap:false,
                axisLabel:{//刻度间隔
                    //interval:5,
                    textStyle:{//刻度显示控制
                        color:"black", //刻度颜色
                        fontSize:12  //刻度大小
                    }
                },
                data: [0,1,2,3,4,5,6,7,8,9]
            }
        ],
        //grid:[10,10,20,20],
        yAxis: [
            {
                type: 'value',
                name : '速率(Bps)'
            }
        ],
        series: [
            {
                "name":"速率(Bps)",
                //"type":"bar",
                type:'line',
                smooth:true,
                itemStyle: {
                    normal: {
                        color: '#da70d6'
                    }
                },
                data:[0,2,4,6,8,10,12,14,16,18]
            }
        ],
        onRegisterApi: function (chartApi) {
        }
    };
    
    
  //通话柱状图
    $scope.barOption = {
        version: 1,
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['柱状图'],
            y: 'bottom'
        },
        toolbox: {
            show: false
        },
        padding: 0,
        calculable: true,
        xAxis: [
            {
                axisLabel: {
                    interval: 0
                },
                type: 'category',
                data: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
            }
        ],
        yAxis: [
            {
                type: 'value',
                splitArea: {show: true}
            }
        ],
        series: [
            {
                name: '通话时段分布',
                type: 'bar',
                itemStyle: {                // 系列级个性化
                    normal: {
                        color: 'blue'
                    }

                },
                data: [2,3,4,5,6,7,8,9,10,12]
            }
        ],
        onRegisterApi: function (chartApi) {
        }
    };
    
    //pie
    $scope.pieOption = {
    	    tooltip : {
    	        show: true,
    	        formatter: "{a} <br/>{b} : {c} ({d}%)"
    	    },
    	    legend: {
    	        orient : 'vertical',
    	        x : 'left',
    	        data:['直达','营销广告','搜索引擎']
    	    },
    	    toolbox: {
    	        show : true,
    	        feature : {
    	            mark : {show: true},
    	            dataView : {show: true, readOnly: false},
    	            restore : {show: true},
    	            saveAsImage : {show: true}
    	        }
    	    },
    	    calculable : true,
    	    series : [
    	        {
    	            name:'访问来源',
    	            type:'pie',
    	            center : ['35%', 200],
    	            radius : 150,
    	            itemStyle : {
    	                normal : {
    	                    label : {
    	                        position : 'inner'
    	                    },
    	                    labelLine : {
    	                        show : false
    	                    }
    	                },
    	                emphasis : {
    	                    label : {
    	                        show : true,
    	                        formatter : "{b}\n{d}%"
    	                    }
    	                }
    	                
    	            },
    	            data:[
    	                {value:335, name:'直达'},
    	                {value:679, name:'营销广告'},
    	                {value:1548, name:'搜索引擎'}
    	            ]
    	        }
    	    ],
            onRegisterApi: function (chartApi) {
            }
    	};
    
    //map 
    $scope.mapOption = {
    	    title : {
    	        text: 'iphone销量',
    	        subtext: '纯属虚构',
    	        x:'center'
    	    },
    	    tooltip : {
    	        trigger: 'item'
    	    },
    	    legend: {
    	        orient: 'vertical',
    	        x:'left',
    	        data:['iphone3','iphone4','iphone5']
    	    },
    	    dataRange: {
    	        min: 0,
    	        max: 2500,
    	        x: 'left',
    	        y: 'bottom',
    	        text:['高','低'],           // 文本，默认为数值文本
    	        calculable : true
    	    },
    	    toolbox: {
    	        show: true,
    	        orient : 'vertical',
    	        x: 'right',
    	        y: 'center',
    	        feature : {
    	            mark : {show: true},
    	            dataView : {show: true, readOnly: false},
    	            restore : {show: true},
    	            saveAsImage : {show: true}
    	        }
    	    },
    	    roamController: {
    	        show: true,
    	        x: 'right',
    	        mapTypeControl: {
    	            'china': true
    	        }
    	    },
    	    series : [
    	        {
    	            name: 'iphone3',
    	            type: 'map',
    	            mapType: 'china',
    	            roam: false,
    	            itemStyle:{
    	                normal:{label:{show:true}},
    	                emphasis:{label:{show:true}}
    	            },
    	            data:[
    	                {name: '北京',value: Math.round(Math.random()*1000)},
    	                {name: '天津',value: Math.round(Math.random()*1000)},
    	                {name: '上海',value: Math.round(Math.random()*1000)},
    	                {name: '重庆',value: Math.round(Math.random()*1000)},
    	                {name: '河北',value: Math.round(Math.random()*1000)},
    	                {name: '河南',value: Math.round(Math.random()*1000)},
    	                {name: '云南',value: Math.round(Math.random()*1000)},
    	                {name: '辽宁',value: Math.round(Math.random()*1000)},
    	                {name: '黑龙江',value: Math.round(Math.random()*1000)},
    	                {name: '湖南',value: Math.round(Math.random()*1000)},
    	                {name: '安徽',value: Math.round(Math.random()*1000)},
    	                {name: '山东',value: Math.round(Math.random()*1000)},
    	                {name: '新疆',value: Math.round(Math.random()*1000)},
    	                {name: '江苏',value: Math.round(Math.random()*1000)},
    	                {name: '浙江',value: Math.round(Math.random()*1000)},
    	                {name: '江西',value: Math.round(Math.random()*1000)},
    	                {name: '湖北',value: Math.round(Math.random()*1000)},
    	                {name: '广西',value: Math.round(Math.random()*1000)},
    	                {name: '甘肃',value: Math.round(Math.random()*1000)},
    	                {name: '山西',value: Math.round(Math.random()*1000)},
    	                {name: '内蒙古',value: Math.round(Math.random()*1000)},
    	                {name: '陕西',value: Math.round(Math.random()*1000)},
    	                {name: '吉林',value: Math.round(Math.random()*1000)},
    	                {name: '福建',value: Math.round(Math.random()*1000)},
    	                {name: '贵州',value: Math.round(Math.random()*1000)},
    	                {name: '广东',value: Math.round(Math.random()*1000)},
    	                {name: '青海',value: Math.round(Math.random()*1000)},
    	                {name: '西藏',value: Math.round(Math.random()*1000)},
    	                {name: '四川',value: Math.round(Math.random()*1000)},
    	                {name: '宁夏',value: Math.round(Math.random()*1000)},
    	                {name: '海南',value: Math.round(Math.random()*1000)},
    	                {name: '台湾',value: Math.round(Math.random()*1000)},
    	                {name: '香港',value: Math.round(Math.random()*1000)},
    	                {name: '澳门',value: Math.round(Math.random()*1000)}
    	            ]
    	        },
    	        {
    	            name: 'iphone4',
    	            type: 'map',
    	            mapType: 'china',
    	            itemStyle:{
    	                normal:{label:{show:true}},
    	                emphasis:{label:{show:true}}
    	            },
    	            data:[
    	                {name: '北京',value: Math.round(Math.random()*1000)},
    	                {name: '天津',value: Math.round(Math.random()*1000)},
    	                {name: '上海',value: Math.round(Math.random()*1000)},
    	                {name: '重庆',value: Math.round(Math.random()*1000)},
    	                {name: '河北',value: Math.round(Math.random()*1000)},
    	                {name: '安徽',value: Math.round(Math.random()*1000)},
    	                {name: '新疆',value: Math.round(Math.random()*1000)},
    	                {name: '浙江',value: Math.round(Math.random()*1000)},
    	                {name: '江西',value: Math.round(Math.random()*1000)},
    	                {name: '山西',value: Math.round(Math.random()*1000)},
    	                {name: '内蒙古',value: Math.round(Math.random()*1000)},
    	                {name: '吉林',value: Math.round(Math.random()*1000)},
    	                {name: '福建',value: Math.round(Math.random()*1000)},
    	                {name: '广东',value: Math.round(Math.random()*1000)},
    	                {name: '西藏',value: Math.round(Math.random()*1000)},
    	                {name: '四川',value: Math.round(Math.random()*1000)},
    	                {name: '宁夏',value: Math.round(Math.random()*1000)},
    	                {name: '香港',value: Math.round(Math.random()*1000)},
    	                {name: '澳门',value: Math.round(Math.random()*1000)}
    	            ]
    	        },
    	        {
    	            name: 'iphone5',
    	            type: 'map',
    	            mapType: 'china',
    	            itemStyle:{
    	                normal:{label:{show:true}},
    	                emphasis:{label:{show:true}}
    	            },
    	            data:[
    	                {name: '北京',value: Math.round(Math.random()*1000)},
    	                {name: '天津',value: Math.round(Math.random()*1000)},
    	                {name: '上海',value: Math.round(Math.random()*1000)},
    	                {name: '广东',value: Math.round(Math.random()*1000)},
    	                {name: '台湾',value: Math.round(Math.random()*1000)},
    	                {name: '香港',value: Math.round(Math.random()*1000)},
    	                {name: '澳门',value: Math.round(Math.random()*1000)}
    	            ]
    	        }
    	    ]
    	};
    
    

}]);