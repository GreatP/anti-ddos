/**
 * Created by lb on 2016/5/9.
 */

function mapChart() {
    var ret = {
        version: 1,
        title : {
            text: '标题',
            x:'center'
        },
        tooltip : {
            trigger: 'item'
        },
        /*legend: {
         orient: 'vertical',
         x:'left',
         data:['iphone3','iphone4','iphone5']
         },*/
        dataRange: {
            min: 0,
            max: 50,
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
                    /*{name: '北京',value: Math.round(Math.random()*1000)},
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
                     {name: '西藏',value: Math.round(Math.random()*1000)},*/
                    {name: '四川',value: 1000},
                    {name: '成都',value: 500},
                    {name: '自贡',value: 300},
                    {name: '宁夏',value: Math.round(Math.random()*1000)},
                    {name: '海南',value: Math.round(Math.random()*1000)},
                    {name: '台湾',value: Math.round(Math.random()*1000)},
                    {name: '香港',value: Math.round(Math.random()*1000)},
                    {name: '澳门',value: Math.round(Math.random()*1000)}
                ]
            }
        ],
        onRegisterApi: function (chartApi) {
        }
    }
    return ret;
}
function mapDynamicChart() {
    ret = {
        version: 1,
        backgroundColor: '#1b1b1b',
        color: ['gold','aqua','lime'],
        title : {
            text: '模拟迁徙',
            subtext:'数据纯属虚构',
            x:'center',
            textStyle : {
                color: '#fff'
            }
        },
        tooltip : {
            trigger: 'item',
            formatter: '{b}'
        },
        legend: {
            orient: 'vertical',
            x:'left',
            data:['北京 Top10'],
            selectedMode: 'single',
            textStyle : {
                color: '#fff'
            }
        },
        toolbox: {
            show : true,
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
        dataRange: {
            min : 0,
            max : 100,
            calculable : true,
            color: ['#ff3333', 'orange', 'yellow','lime','aqua'],
            textStyle:{
                color:'#fff'
            }
        },
        series : [
            {
                name: '全国',
                type: 'map',
                roam: true,
                hoverable: false,
                mapType: 'china',
                itemStyle:{
                    normal:{
                        borderColor:'rgba(100,149,237,1)',
                        borderWidth:0.5,
                        areaStyle:{
                            color: '#1b1b1b'
                        }
                    }
                },
                data:[],
                markLine : {
                    smooth:true,
                    symbol: ['none', 'circle'],
                    symbolSize : 1,
                    itemStyle : {
                        normal: {
                            color:'#fff',
                            borderWidth:1,
                            borderColor:'rgba(30,144,255,0.5)'
                        }
                    },
                    data : [
                        [{name:'北京'},{name:'包头'}],
                        [{name:'北京'},{name:'北海'}],
                        [{name:'北京'},{name:'广州'}],
                        [{name:'北京'},{name:'郑州'}],
                        [{name:'北京'},{name:'长春'}],
                        [{name:'北京'},{name:'长治'}],
                        [{name:'北京'},{name:'重庆'}],
                        [{name:'北京'},{name:'长沙'}],
                        [{name:'北京'},{name:'成都'}],
                        [{name:'北京'},{name:'常州'}],
                        [{name:'北京'},{name:'丹东'}],
                        [{name:'北京'},{name:'大连'}],
                        [{name:'北京'},{name:'东营'}],
                        [{name:'北京'},{name:'延安'}],
                        [{name:'北京'},{name:'福州'}],
                        [{name:'北京'},{name:'海口'}],
                        [{name:'北京'},{name:'呼和浩特'}],
                        [{name:'北京'},{name:'合肥'}],
                        [{name:'北京'},{name:'杭州'}],
                        [{name:'北京'},{name:'哈尔滨'}],
                        [{name:'北京'},{name:'舟山'}],
                        [{name:'北京'},{name:'银川'}],
                        [{name:'北京'},{name:'衢州'}],
                        [{name:'北京'},{name:'南昌'}],
                        [{name:'北京'},{name:'昆明'}],
                        [{name:'北京'},{name:'贵阳'}],
                        [{name:'北京'},{name:'兰州'}],
                        [{name:'北京'},{name:'拉萨'}],
                        [{name:'北京'},{name:'连云港'}],
                        [{name:'北京'},{name:'临沂'}],
                        [{name:'北京'},{name:'柳州'}],
                        [{name:'北京'},{name:'宁波'}],
                        [{name:'北京'},{name:'南京'}],
                        [{name:'北京'},{name:'南宁'}],
                        [{name:'北京'},{name:'南通'}],
                        [{name:'北京'},{name:'上海'}],
                        [{name:'北京'},{name:'沈阳'}],
                        [{name:'北京'},{name:'西安'}],
                        [{name:'北京'},{name:'汕头'}],
                        [{name:'北京'},{name:'深圳'}],
                        [{name:'北京'},{name:'青岛'}],
                        [{name:'北京'},{name:'济南'}],
                        [{name:'北京'},{name:'太原'}],
                        [{name:'北京'},{name:'乌鲁木齐'}],
                        [{name:'北京'},{name:'潍坊'}],
                        [{name:'北京'},{name:'威海'}],
                        [{name:'北京'},{name:'温州'}],
                        [{name:'北京'},{name:'武汉'}],
                        [{name:'北京'},{name:'无锡'}],
                        [{name:'北京'},{name:'厦门'}],
                        [{name:'北京'},{name:'西宁'}],
                        [{name:'北京'},{name:'徐州'}],
                        [{name:'北京'},{name:'烟台'}],
                        [{name:'北京'},{name:'盐城'}],
                        [{name:'北京'},{name:'珠海'}],
                        [{name:'广州'},{name:'盐城'}]
                    ]
                },
                geoCoord: {
                    '上海': [121.4648,31.2891],
                    '东莞': [113.8953,22.901],
                    '东营': [118.7073,37.5513],
                    '中山': [113.4229,22.478],
                    '临汾': [111.4783,36.1615],
                    '临沂': [118.3118,35.2936],
                    '丹东': [124.541,40.4242],
                    '丽水': [119.5642,28.1854],
                    '乌鲁木齐': [87.9236,43.5883],
                    '佛山': [112.8955,23.1097],
                    '保定': [115.0488,39.0948],
                    '兰州': [103.5901,36.3043],
                    '包头': [110.3467,41.4899],
                    '北京': [116.4551,40.2539],
                    '北海': [109.314,21.6211],
                    '南京': [118.8062,31.9208],
                    '南宁': [108.479,23.1152],
                    '南昌': [116.0046,28.6633],
                    '南通': [121.1023,32.1625],
                    '厦门': [118.1689,24.6478],
                    '台州': [121.1353,28.6688],
                    '合肥': [117.29,32.0581],
                    '呼和浩特': [111.4124,40.4901],
                    '咸阳': [108.4131,34.8706],
                    '哈尔滨': [127.9688,45.368],
                    '唐山': [118.4766,39.6826],
                    '嘉兴': [120.9155,30.6354],
                    '大同': [113.7854,39.8035],
                    '大连': [122.2229,39.4409],
                    '天津': [117.4219,39.4189],
                    '太原': [112.3352,37.9413],
                    '威海': [121.9482,37.1393],
                    '宁波': [121.5967,29.6466],
                    '宝鸡': [107.1826,34.3433],
                    '宿迁': [118.5535,33.7775],
                    '常州': [119.4543,31.5582],
                    '广州': [113.5107,23.2196],
                    '廊坊': [116.521,39.0509],
                    '延安': [109.1052,36.4252],
                    '张家口': [115.1477,40.8527],
                    '徐州': [117.5208,34.3268],
                    '德州': [116.6858,37.2107],
                    '惠州': [114.6204,23.1647],
                    '成都': [103.9526,30.7617],
                    '扬州': [119.4653,32.8162],
                    '承德': [117.5757,41.4075],
                    '拉萨': [91.1865,30.1465],
                    '无锡': [120.3442,31.5527],
                    '日照': [119.2786,35.5023],
                    '昆明': [102.9199,25.4663],
                    '杭州': [119.5313,29.8773],
                    '枣庄': [117.323,34.8926],
                    '柳州': [109.3799,24.9774],
                    '株洲': [113.5327,27.0319],
                    '武汉': [114.3896,30.6628],
                    '汕头': [117.1692,23.3405],
                    '江门': [112.6318,22.1484],
                    '沈阳': [123.1238,42.1216],
                    '沧州': [116.8286,38.2104],
                    '河源': [114.917,23.9722],
                    '泉州': [118.3228,25.1147],
                    '泰安': [117.0264,36.0516],
                    '泰州': [120.0586,32.5525],
                    '济南': [117.1582,36.8701],
                    '济宁': [116.8286,35.3375],
                    '海口': [110.3893,19.8516],
                    '淄博': [118.0371,36.6064],
                    '淮安': [118.927,33.4039],
                    '深圳': [114.5435,22.5439],
                    '清远': [112.9175,24.3292],
                    '温州': [120.498,27.8119],
                    '渭南': [109.7864,35.0299],
                    '湖州': [119.8608,30.7782],
                    '湘潭': [112.5439,27.7075],
                    '滨州': [117.8174,37.4963],
                    '潍坊': [119.0918,36.524],
                    '烟台': [120.7397,37.5128],
                    '玉溪': [101.9312,23.8898],
                    '珠海': [113.7305,22.1155],
                    '盐城': [120.2234,33.5577],
                    '盘锦': [121.9482,41.0449],
                    '石家庄': [114.4995,38.1006],
                    '福州': [119.4543,25.9222],
                    '秦皇岛': [119.2126,40.0232],
                    '绍兴': [120.564,29.7565],
                    '聊城': [115.9167,36.4032],
                    '肇庆': [112.1265,23.5822],
                    '舟山': [122.2559,30.2234],
                    '苏州': [120.6519,31.3989],
                    '莱芜': [117.6526,36.2714],
                    '菏泽': [115.6201,35.2057],
                    '营口': [122.4316,40.4297],
                    '葫芦岛': [120.1575,40.578],
                    '衡水': [115.8838,37.7161],
                    '衢州': [118.6853,28.8666],
                    '西宁': [101.4038,36.8207],
                    '西安': [109.1162,34.2004],
                    '贵阳': [106.6992,26.7682],
                    '连云港': [119.1248,34.552],
                    '邢台': [114.8071,37.2821],
                    '邯郸': [114.4775,36.535],
                    '郑州': [113.4668,34.6234],
                    '鄂尔多斯': [108.9734,39.2487],
                    '重庆': [107.7539,30.1904],
                    '金华': [120.0037,29.1028],
                    '铜川': [109.0393,35.1947],
                    '银川': [106.3586,38.1775],
                    '镇江': [119.4763,31.9702],
                    '长春': [125.8154,44.2584],
                    '长沙': [113.0823,28.2568],
                    '长治': [112.8625,36.4746],
                    '阳泉': [113.4778,38.0951],
                    '青岛': [120.4651,36.3373],
                    '韶关': [113.7964,24.7028]
                }
            },
            {
                name: '北京 Top10',
                type: 'map',
                mapType: 'china',
                data:[],
                markLine : {
                    smooth:true,
                    effect : {
                        show: true,
                        scaleSize: 1,
                        period: 5,
                        color: '#fff',
                        shadowBlur: 10
                    },
                    itemStyle : {
                        normal: {
                            borderWidth:1,
                            lineStyle: {
                                type: 'solid',
                                shadowBlur: 10
                            }
                        }
                    },
                    data : [
                        /*[{name:'上海',value:95}, {name:'北京'}],
                        [{name:'广州',value:90}, {name:'北京'}],
                        [{name:'大连',value:80}, {name:'北京'}],
                        [{name:'南宁',value:70}, {name:'北京'}],
                        [{name:'南昌',value:60}, {name:'北京'}],
                        [{name:'拉萨',value:50}, {name:'北京'}],
                        [{name:'长春',value:40}, {name:'北京'}],
                        [{name:'包头',value:30}, {name:'北京'}],
                        [{name:'重庆',value:20}, {name:'北京',value:800}],
                        [{name:'常州',value:10}, {name:'北京'}]*/
                        [{name:'上海'}, {name:'北京'}],
                        [{name:'广州'}, {name:'北京'}],
                        [{name:'大连'}, {name:'北京'}],
                        [{name:'南宁'}, {name:'北京'}],
                        [{name:'南昌'}, {name:'北京'}],
                        [{name:'拉萨'}, {name:'北京'}],
                        [{name:'长春'}, {name:'北京'}],
                        [{name:'包头'}, {name:'北京'}],
                        [{name:'重庆'}, {name:'北京',value:800}],
                        [{name:'常州'}, {name:'北京'}]
                    ]
                },
                markPoint : {
                    symbol:'emptyCircle',
                    symbolSize : function (v){
                        return 10 + v/10
                    },
                    effect : {
                        show: true,
                        shadowBlur : 0
                    },
                    itemStyle:{
                        normal:{
                            label:{show:false}
                        },
                        emphasis: {
                            label:{position:'top'}
                        }
                    },
                    data : [
                        {name:'上海',value:95},
                        {name:'广州',value:90},
                        {name:'大连',value:80},
                        {name:'南宁',value:70},
                        {name:'南昌',value:60},
                        {name:'拉萨',value:50},
                        {name:'长春',value:40},
                        {name:'包头',value:30},
                        {name:'重庆',value:20},
                        {name:'常州',value:10}
                    ]
                }
            }
        ]
    };
    return ret;
}
function mapMarkPointChart() {
    ret = {
        version: 1,
        title : {
            text: '攻击源分布',
            x:'center'
        },
        tooltip : {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            x:'left',
            data:[]
        },
        dataRange: {
            min : 0,
            max : 500,
            calculable : true,
            //color:['#ff3333', 'orange', 'yellow','lime','aqua']
            color: ['#FF0000','#FF7F00','#FFC125']
        },
        /*toolbox: {
            show : true,
            orient : 'vertical',
            x: 'right',
            y: 'center',
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },*/
        series : [
            {
                name: '攻击源个数',
                type: 'map',
                mapType: 'china',
                hoverable: false,
                roam:true,
               itemStyle:{
                    normal:{
                        areaStyle:{color:'#66CCFF'},
                        //areaStyle:{color:'#00C5CD'},
                        label:{show:true} //控制省份显示
                    },
                    emphasis:{label:{show:true}}
                },
                data : [],
                markPoint : {
                    symbolSize: 10,       // 标注大小，半宽（半径）参数，当图形为方向或菱形则总宽度为symbolSize * 2
                    itemStyle: {
                        normal: {
                            borderColor: '#87cefa',
                            borderWidth: 1,            // 标注边线线宽，单位px，默认为1
                            label: {
                                show: true
                            }
                        },
                        emphasis: {
                            borderColor: '#1e90ff',
                            borderWidth: 5,
                            borderWidth: 5,
                            label: {
                                show: false
                            }
                        }
                    },
                    data : [
                        {name: "海门", value: 9},
                        {name: "鄂尔多斯", value: 12},
                        {name: "招远", value: 12},
                        {name: "舟山", value: 12},
                        {name: "齐齐哈尔", value: 14},
                        {name: "盐城", value: 15},
                        {name: "赤峰", value: 16},
                        {name: "青岛", value: 18},
                        {name: "乳山", value: 18},
                        {name: "金昌", value: 19},
                        {name: "泉州", value: 21},
                        {name: "莱西", value: 21},
                        {name: "日照", value: 21},
                        {name: "胶南", value: 22},
                        {name: "南通", value: 23},
                        {name: "拉萨", value: 24},
                        {name: "云浮", value: 24},
                        {name: "梅州", value: 25},
                        {name: "文登", value: 25},
                        {name: "上海", value: 25},
                        {name: "攀枝花", value: 25},
                        {name: "威海", value: 25},
                        {name: "承德", value: 25},
                        {name: "厦门", value: 26},
                        {name: "汕尾", value: 26},
                        {name: "潮州", value: 26},
                        {name: "丹东", value: 27},
                        {name: "太仓", value: 27},
                        {name: "曲靖", value: 27},
                        {name: "烟台", value: 28},
                        {name: "福州", value: 29},
                        {name: "瓦房店", value: 30},
                        {name: "即墨", value: 30},
                        {name: "抚顺", value: 31},
                        {name: "玉溪", value: 31},
                        {name: "张家口", value: 31},
                        {name: "阳泉", value: 31},
                        {name: "莱州", value: 32},
                        {name: "湖州", value: 32},
                        {name: "汕头", value: 32},
                        {name: "昆山", value: 33},
                        {name: "宁波", value: 33},
                        {name: "湛江", value: 33},
                        {name: "揭阳", value: 34},
                        {name: "荣成", value: 34},
                        {name: "连云港", value: 35},
                        {name: "葫芦岛", value: 35},
                        {name: "常熟", value: 36},
                        {name: "东莞", value: 36},
                        {name: "河源", value: 36},
                        {name: "淮安", value: 36},
                        {name: "泰州", value: 36},
                        {name: "南宁", value: 37},
                        {name: "营口", value: 37},
                        {name: "惠州", value: 37},
                        {name: "江阴", value: 37},
                        {name: "蓬莱", value: 37},
                        {name: "韶关", value: 38},
                        {name: "嘉峪关", value: 38},
                        {name: "广州", value: 38},
                        {name: "延安", value: 38},
                        {name: "太原", value: 39},
                        {name: "清远", value: 39},
                        {name: "中山", value: 39},
                        {name: "昆明", value: 39},
                        {name: "寿光", value: 40},
                        {name: "盘锦", value: 40},
                        {name: "长治", value: 41},
                        {name: "深圳", value: 41},
                        {name: "珠海", value: 42},
                        {name: "宿迁", value: 43},
                        {name: "咸阳", value: 43},
                        {name: "铜川", value: 44},
                        {name: "平度", value: 44},
                        {name: "佛山", value: 44},
                        {name: "海口", value: 44},
                        {name: "江门", value: 45},
                        {name: "章丘", value: 45},
                        {name: "肇庆", value: 46},
                        {name: "大连", value: 47},
                        {name: "临汾", value: 47},
                        {name: "吴江", value: 47},
                        {name: "石嘴山", value: 49},
                        {name: "沈阳", value: 50},
                        {name: "苏州", value: 50},
                        {name: "茂名", value: 50},
                        {name: "嘉兴", value: 51},
                        {name: "长春", value: 51},
                        {name: "胶州", value: 52},
                        {name: "银川", value: 52},
                        {name: "张家港", value: 52},
                        {name: "三门峡", value: 53},
                        {name: "锦州", value: 54},
                        {name: "南昌", value: 54},
                        {name: "柳州", value: 54},
                        {name: "三亚", value: 54},
                        {name: "自贡", value: 56},
                        {name: "吉林", value: 56},
                        {name: "阳江", value: 57},
                        {name: "泸州", value: 57},
                        {name: "西宁", value: 57},
                        {name: "宜宾", value: 58},
                        {name: "呼和浩特", value: 58},
                        {name: "成都", value: 58},
                        {name: "大同", value: 58},
                        {name: "镇江", value: 59},
                        {name: "桂林", value: 59},
                        {name: "张家界", value: 59},
                        {name: "宜兴", value: 59},
                        {name: "北海", value: 60},
                        {name: "西安", value: 61},
                        {name: "金坛", value: 62},
                        {name: "东营", value: 62},
                        {name: "牡丹江", value: 63},
                        {name: "遵义", value: 63},
                        {name: "绍兴", value: 63},
                        {name: "扬州", value: 64},
                        {name: "常州", value: 64},
                        {name: "潍坊", value: 65},
                        {name: "重庆", value: 66},
                        {name: "台州", value: 67},
                        {name: "南京", value: 67},
                        {name: "滨州", value: 70},
                        {name: "贵阳", value: 71},
                        {name: "无锡", value: 71},
                        {name: "本溪", value: 71},
                        {name: "克拉玛依", value: 72},
                        {name: "渭南", value: 72},
                        {name: "马鞍山", value: 72},
                        {name: "宝鸡", value: 72},
                        {name: "焦作", value: 75},
                        {name: "句容", value: 75},
                        {name: "北京", value: 79},
                        {name: "徐州", value: 79},
                        {name: "衡水", value: 80},
                        {name: "包头", value: 80},
                        {name: "绵阳", value: 80},
                        {name: "乌鲁木齐", value: 84},
                        {name: "枣庄", value: 84},
                        {name: "杭州", value: 84},
                        {name: "淄博", value: 85},
                        {name: "鞍山", value: 86},
                        {name: "溧阳", value: 86},
                        {name: "库尔勒", value: 86},
                        {name: "安阳", value: 90},
                        {name: "开封", value: 90},
                        {name: "济南", value: 92},
                        {name: "德阳", value: 93},
                        {name: "温州", value: 95},
                        {name: "九江", value: 96},
                        {name: "邯郸", value: 98},
                        {name: "临安", value: 99},
                        {name: "兰州", value: 99},
                        {name: "沧州", value: 100},
                        {name: "临沂", value: 103},
                        {name: "南充", value: 104},
                        {name: "天津", value: 105},
                        {name: "富阳", value: 106},
                        {name: "泰安", value: 112},
                        {name: "诸暨", value: 112},
                        {name: "郑州", value: 113},
                        {name: "哈尔滨", value: 114},
                        {name: "聊城", value: 116},
                        {name: "芜湖", value: 117},
                        {name: "唐山", value: 119},
                        {name: "平顶山", value: 119},
                        {name: "邢台", value: 119},
                        {name: "德州", value: 120},
                        {name: "济宁", value: 120},
                        {name: "荆州", value: 127},
                        {name: "宜昌", value: 130},
                        {name: "义乌", value: 132},
                        {name: "丽水", value: 133},
                        {name: "洛阳", value: 134},
                        {name: "秦皇岛", value: 136},
                        {name: "株洲", value: 143},
                        {name: "石家庄", value: 147},
                        {name: "莱芜", value: 148},
                        {name: "常德", value: 152},
                        {name: "保定", value: 153},
                        {name: "湘潭", value: 154},
                        {name: "金华", value: 157},
                        {name: "岳阳", value: 169},
                        {name: "长沙", value: 175},
                        {name: "衢州", value: 177},
                        {name: "廊坊", value: 193},
                        {name: "菏泽", value: 194},
                        {name: "合肥", value: 229},
                        {name: "武汉", value: 273},
                        {name: "大庆", value: 279}
                    ]
                },
                geoCoord: {
                    "海门":[121.15,31.89],
                    "鄂尔多斯":[109.781327,39.608266],
                    "招远":[120.38,37.35],
                    "舟山":[122.207216,29.985295],
                    "齐齐哈尔":[123.97,47.33],
                    "盐城":[120.13,33.38],
                    "赤峰":[118.87,42.28],
                    "青岛":[120.33,36.07],
                    "乳山":[121.52,36.89],
                    "金昌":[102.188043,38.520089],
                    "泉州":[118.58,24.93],
                    "莱西":[120.53,36.86],
                    "日照":[119.46,35.42],
                    "胶南":[119.97,35.88],
                    "南通":[121.05,32.08],
                    "拉萨":[91.11,29.97],
                    "云浮":[112.02,22.93],
                    "梅州":[116.1,24.55],
                    "文登":[122.05,37.2],
                    "上海":[121.48,31.22],
                    "攀枝花":[101.718637,26.582347],
                    "威海":[122.1,37.5],
                    "承德":[117.93,40.97],
                    "厦门":[118.1,24.46],
                    "汕尾":[115.375279,22.786211],
                    "潮州":[116.63,23.68],
                    "丹东":[124.37,40.13],
                    "太仓":[121.1,31.45],
                    "曲靖":[103.79,25.51],
                    "烟台":[121.39,37.52],
                    "福州":[119.3,26.08],
                    "瓦房店":[121.979603,39.627114],
                    "即墨":[120.45,36.38],
                    "抚顺":[123.97,41.97],
                    "玉溪":[102.52,24.35],
                    "张家口":[114.87,40.82],
                    "阳泉":[113.57,37.85],
                    "莱州":[119.942327,37.177017],
                    "湖州":[120.1,30.86],
                    "汕头":[116.69,23.39],
                    "昆山":[120.95,31.39],
                    "宁波":[121.56,29.86],
                    "湛江":[110.359377,21.270708],
                    "揭阳":[116.35,23.55],
                    "荣成":[122.41,37.16],
                    "连云港":[119.16,34.59],
                    "葫芦岛":[120.836932,40.711052],
                    "常熟":[120.74,31.64],
                    "东莞":[113.75,23.04],
                    "河源":[114.68,23.73],
                    "淮安":[119.15,33.5],
                    "泰州":[119.9,32.49],
                    "南宁":[108.33,22.84],
                    "营口":[122.18,40.65],
                    "惠州":[114.4,23.09],
                    "江阴":[120.26,31.91],
                    "蓬莱":[120.75,37.8],
                    "韶关":[113.62,24.84],
                    "嘉峪关":[98.289152,39.77313],
                    "广州":[113.23,23.16],
                    "延安":[109.47,36.6],
                    "太原":[112.53,37.87],
                    "清远":[113.01,23.7],
                    "中山":[113.38,22.52],
                    "昆明":[102.73,25.04],
                    "寿光":[118.73,36.86],
                    "盘锦":[122.070714,41.119997],
                    "长治":[113.08,36.18],
                    "深圳":[114.07,22.62],
                    "珠海":[113.52,22.3],
                    "宿迁":[118.3,33.96],
                    "咸阳":[108.72,34.36],
                    "铜川":[109.11,35.09],
                    "平度":[119.97,36.77],
                    "佛山":[113.11,23.05],
                    "海口":[110.35,20.02],
                    "江门":[113.06,22.61],
                    "章丘":[117.53,36.72],
                    "肇庆":[112.44,23.05],
                    "大连":[121.62,38.92],
                    "临汾":[111.5,36.08],
                    "吴江":[120.63,31.16],
                    "石嘴山":[106.39,39.04],
                    "沈阳":[123.38,41.8],
                    "苏州":[120.62,31.32],
                    "茂名":[110.88,21.68],
                    "嘉兴":[120.76,30.77],
                    "长春":[125.35,43.88],
                    "胶州":[120.03336,36.264622],
                    "银川":[106.27,38.47],
                    "张家港":[120.555821,31.875428],
                    "三门峡":[111.19,34.76],
                    "锦州":[121.15,41.13],
                    "南昌":[115.89,28.68],
                    "柳州":[109.4,24.33],
                    "三亚":[109.511909,18.252847],
                    "自贡":[104.778442,29.33903],
                    "吉林":[126.57,43.87],
                    "阳江":[111.95,21.85],
                    "泸州":[105.39,28.91],
                    "西宁":[101.74,36.56],
                    "宜宾":[104.56,29.77],
                    "呼和浩特":[111.65,40.82],
                    "成都":[104.06,30.67],
                    "大同":[113.3,40.12],
                    "镇江":[119.44,32.2],
                    "桂林":[110.28,25.29],
                    "张家界":[110.479191,29.117096],
                    "宜兴":[119.82,31.36],
                    "北海":[109.12,21.49],
                    "西安":[108.95,34.27],
                    "金坛":[119.56,31.74],
                    "东营":[118.49,37.46],
                    "牡丹江":[129.58,44.6],
                    "遵义":[106.9,27.7],
                    "绍兴":[120.58,30.01],
                    "扬州":[119.42,32.39],
                    "常州":[119.95,31.79],
                    "潍坊":[119.1,36.62],
                    "重庆":[106.54,29.59],
                    "台州":[121.420757,28.656386],
                    "南京":[118.78,32.04],
                    "滨州":[118.03,37.36],
                    "贵阳":[106.71,26.57],
                    "无锡":[120.29,31.59],
                    "本溪":[123.73,41.3],
                    "克拉玛依":[84.77,45.59],
                    "渭南":[109.5,34.52],
                    "马鞍山":[118.48,31.56],
                    "宝鸡":[107.15,34.38],
                    "焦作":[113.21,35.24],
                    "句容":[119.16,31.95],
                    "北京":[116.46,39.92],
                    "徐州":[117.2,34.26],
                    "衡水":[115.72,37.72],
                    "包头":[110,40.58],
                    "绵阳":[104.73,31.48],
                    "乌鲁木齐":[87.68,43.77],
                    "枣庄":[117.57,34.86],
                    "杭州":[120.19,30.26],
                    "淄博":[118.05,36.78],
                    "鞍山":[122.85,41.12],
                    "溧阳":[119.48,31.43],
                    "库尔勒":[86.06,41.68],
                    "安阳":[114.35,36.1],
                    "开封":[114.35,34.79],
                    "济南":[117,36.65],
                    "德阳":[104.37,31.13],
                    "温州":[120.65,28.01],
                    "九江":[115.97,29.71],
                    "邯郸":[114.47,36.6],
                    "临安":[119.72,30.23],
                    "兰州":[103.73,36.03],
                    "沧州":[116.83,38.33],
                    "临沂":[118.35,35.05],
                    "南充":[106.110698,30.837793],
                    "天津":[117.2,39.13],
                    "富阳":[119.95,30.07],
                    "泰安":[117.13,36.18],
                    "诸暨":[120.23,29.71],
                    "郑州":[113.65,34.76],
                    "哈尔滨":[126.63,45.75],
                    "聊城":[115.97,36.45],
                    "芜湖":[118.38,31.33],
                    "唐山":[118.02,39.63],
                    "平顶山":[113.29,33.75],
                    "邢台":[114.48,37.05],
                    "德州":[116.29,37.45],
                    "济宁":[116.59,35.38],
                    "荆州":[112.239741,30.335165],
                    "宜昌":[111.3,30.7],
                    "义乌":[120.06,29.32],
                    "丽水":[119.92,28.45],
                    "洛阳":[112.44,34.7],
                    "秦皇岛":[119.57,39.95],
                    "株洲":[113.16,27.83],
                    "石家庄":[114.48,38.03],
                    "莱芜":[117.67,36.19],
                    "常德":[111.69,29.05],
                    "保定":[115.48,38.85],
                    "湘潭":[112.91,27.87],
                    "金华":[119.64,29.12],
                    "岳阳":[113.09,29.37],
                    "长沙":[113,28.21],
                    "衢州":[118.88,28.97],
                    "廊坊":[116.7,39.53],
                    "菏泽":[115.480656,35.23375],
                    "合肥":[117.27,31.86],
                    "武汉":[114.31,30.52],
                    "大庆":[125.03,46.58]
                }
            },
            {
                name: '攻击源个数Top5',
                type: 'map',
                mapType: 'china',
                data:[],
                markPoint : {
                    symbol:'emptyCircle',
                    symbolSize : function (v){
                        return 10 + v/100
                    },
                    effect : {
                        show: true,
                        shadowBlur : 0
                    },
                    itemStyle:{
                        normal:{
                            label:{show:false}
                        }
                    },
                    data : [
                        {name: "廊坊", value: 193},
                        {name: "菏泽", value: 194},
                        {name: "合肥", value: 229},
                        {name: "武汉", value: 273},
                        {name: "大庆", value: 279}
                    ]
                }
            }
        ]
    }

    return ret;
}
app.controller('attackSourceDistributionController', ['$scope', '$http','$localStorage', '$location', '$translate', '$state', '$timeout', '$animate', 'suffix', function ($scope, $http,$alerts, $location, $translate, $state, $timeout, $animate, $suffix) {
    'use strict';

    $scope.ipCityOption = mapMarkPointChart();

    $scope.getError = function(response, status) {
        console.log("getError "+response);
        console.log(status);
    }
    $scope.getCitySuccess = function(response) {
        var index;
        var cnt;
        //console.log(response);
        //console.log(response.length);

        if (response == null || response.length <= 0) {
            console.log("getCitySuccess,but response == null || response.length <= 0");
            return;
        }
        var min = 100;
        if (response[0].count <= min) {
            $scope.ipCityOption.dataRange.max = min;
        } else {
            $scope.ipCityOption.dataRange.max = (Math.round(response[0].count/100))*100;
        }
        //$scope.ipCityOption.dataRange.max = response[0].count;

        $scope.ipCityOption.series[0].markPoint.data = [];
        $scope.ipCityOption.series[1].markPoint.data = [];
        $scope.ipCityOption.series[0].geoCoord = {};
        cnt = 0;
        for (index in response) {
            if (cnt < 5) {
                $scope.ipCityOption.series[1].markPoint.data.push(
                    {name:response[index].city,value:response[index].count});
            }
            $scope.ipCityOption.series[0].markPoint.data.push({name:response[index].city,value:response[index].count});

            cnt++;
            if (response[index].lng != "" && response[index].lat != "") {
                $scope.ipCityOption.series[0].geoCoord[response[index].city] = [response[index].lng,response[index].lat];
            }

        }

        $scope.ipCityOption.version++;
    }
    $scope.getCity = function(){
        var requestUrl = getUrl($location, 'rest/ipLocation/getIpCity');
        console.log(requestUrl);
        httpGet($http, requestUrl, null, $scope.getCitySuccess, $scope.getError);

        //定时更新前端数据
        //var timer = $timeout(function(){
        //    $scope.overViewInfo();
        //},5000);
        //
        //$scope.$on(
        //    "$destroy",
        //    function(event) {
        //        $timeout.cancel(timer);
        //    }
        //);
    }

    $scope.getCity();
   // $scope.srcDistributionOption.series[1].markPoint.data = [];
}
]);






