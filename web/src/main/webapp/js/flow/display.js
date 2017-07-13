/**
 * Created by lb on 2015/8/13.
 */

var HOUR_OF_DAY=24;
function ajaxGetdataxx(url,urlPara,successFunction,errorFunction,JsonCallBack) {
    $.ajax({
        type: "GET",
        async: true,
        //url: baseUrl+"/getdata",
        url: url,
        //url: "http://10.111.121.12:8080/flowDisplay/test",
        //dataType: "json", //返回数据形式为json
        dataType: 'jsonp',
        //data:{displayType:type,poName:po,protocal:protocal},
        data: urlPara,
        jsonp: "callback",
        jsonpCallback: JsonCallBack,
        success: successFunction,
        error: errorFunction
    })
}