function pipeChart() {
    var ret = {
        version : 0,
        legend: {
            y: 'bottom',
            data: []
        },
        tooltip : {
            trigger: 'item',
            formatter: "{b} : {c}  ({d}%)"
        },
        toolbox: {
            show : false
        },
        calculable : true,
        series : [
            {
                //name:'通话类型',
                type:'pie',
                smooth: true,
                radius:'65%',
                itemStyle:{
                    normal: {
                        label: {
                            show: false
                        },
                        labelLine: {
                            show: false
                        }
                    }
                },
                center: ['50%', '40%'],
                data:[]
            }
        ]
    }

    return ret;
}