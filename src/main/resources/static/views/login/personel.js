function getLocalTime(nS) {
    return new Date(parseInt(nS)).Format("yyyy-MM-dd hh:mm:ss");
}
function getLocalTimeYMD(nS) {
    return new Date(parseInt(nS)).Format("yyyy-MM-dd");
}

Date.prototype.Format = function(fmt) {//author: meizz
    var o = {
        "M+" : this.getMonth() + 1, //月份
        "d+" : this.getDate(), //日
        "h+" : this.getHours(), //小时
        "m+" : this.getMinutes(), //分
        "s+" : this.getSeconds(), //秒
        "q+" : Math.floor((this.getMonth() + 3) / 3), //季度
        "S" : this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

/*
      *   功能:实现VBScript的DateAdd功能.
      *   参数:interval,字符串表达式，表示要添加的时间间隔.
      *   参数:number,数值表达式，表示要添加的时间间隔的个数.
      *   参数:date,时间对象.
      *   返回:新的时间对象.
      *   var   now   =   new   Date();
      *   var   newDate   =   DateAdd( "d ",5,now);
      *---------------   DateAdd(interval,number,date)   -----------------
      */
function  DateAdd(interval,number,dates){
    var date = new Date(dates);
    switch(interval) {
        case "y" : {
            date.setFullYear(date.getFullYear()+number);
            return date;
            break;
        } case "q" : {
            date.setMonth(date.getMonth()+number*3);
            return date;
            break;
        } case "m" : {
            date.setMonth(date.getMonth()+number);
            return date;
            break;
        } case "w" : {
            date.setDate(date.getDate()+number*7);
            return date;
            break;
        } case "d" : {
            date.setDate(date.getDate()+number);
            return date;
            break;
        } case "h" : {
            date.setHours(date.getHours()+number);
            return date;
            break;
        } case "m" : {
            date.setMinutes(date.getMinutes()+number);
            return date;
            break;
        } case "s" : {
            date.setSeconds(date.getSeconds()+number);
            return date;
            break;
        } default : {
            date.setDate(d.getDate()+number);
            return date;
            break;
        }
    }
}

function getDataFormat(data) {
    return data.Format("yyyy-MM-dd");
}


function ifIdNo() {
    var idType =  $('input[name="idType"]').val();
    var idNo =  $('input[name="idNo"]').val();
    console.log(idType)
    console.log(idNo)
    if(idType==1){
        if(idNo.length==15||idNo.length==18){
            return true;
        }
    }else {
        return true;
    }
    return false;
}