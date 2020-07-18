
var BILINGUAL = {
    DIRECT : "direct", TIP: 'tip'
};
var BILINGUAL_CLASS = "bilingual";

var ENABLE_BILINGUAL = ''; //BILINGUAL.TIP; //$.cookie('enableBilingual'); //直接展示=direct 悬浮提示tip //开启双语 不开启为''

var BILINGUAL_LANG = ""; //双语语言
var defaultNS = "errmsg";

var __TOPESA_ISF_ENABLE = 'N';

$.extend({
    tl: function (k, dk) {
        var v = $.i18n.t(k);
        return v == k ? (dk ? dk : '') : v;
    },
    tls: function (k, dk, spec) {
        if(!ENABLE_BILINGUAL){
            return $.tl(k, dk);
        }
        var v = $.i18n.t(k);
        v = v == k ? (dk ? dk : '') : v;
        var v2 = '', k2 = '';
        if(k.indexOf(":") > -1){
            var s = k.split(":");
            k2 = 'sec-' + s[0] + ":" + s[1];
        }else{
            k2 = 'sec-' + defaultNS + ":" + k;
        }
        if(ENABLE_BILINGUAL == BILINGUAL.DIRECT){
            v2 = $.i18n.t(k2);
            v2 = v2 == k2 ? '' : v2;
            v2 = v2 == v ? '' : v2;
            return v2 ? v + (spec ? spec : "<br/>") + v2 : v;
        }else if(ENABLE_BILINGUAL == BILINGUAL.TIP){
            return v + '<span _data-i18n="'+k2+'" style="display: none;"></span>';
        }
        return $.tl(k, dk);
    }
});

var Constant = {
    API_V1 : "/api/v1/",
    API_V1_PLUGINS : "/api/v1/plugins/",
    API_DATA_DICT_BY_CODES : "/api/v1/plugins/" + "IC_DATA_DICT_BY_CODES",
    IC_ATTACH_DOWN_CODES : "/api/v1/plugins/" + "IC_ATTACH_DOWN?attachId=",

    VIEWS : "/views",
    VIEWS_IC : "/views/ic",
    VIEWS_MODULE_FS : "/module/fs",
    VIEWS_MODULE_ACT : "/module/act",
    VIEWS_PDF_VIEW : "/js/plugins/pdfjs-2.0.943-dist/web/viewer-el.html?file=",

    VIEWS_ADMIN : "/views/administrator",
    VIEWS_ADMIN_BS : "/views/administrator/bs",
    VIEWS_CUSTOM_BS : "/views/customer/bs",
    VIEWS_ADMIN_TS : "/views/administrator/ts",
    VIEWS_COM : "/views/com",
    VIEWS_EXAM_OUTLINE : "/views/customer/examOutline",
    VIEWS_EX_TRAIN_RECORD : "/views/ex/trainRecord",
};

var ICViews = {
    IC_SELECT_LIST: Constant.VIEWS_IC + "/comm/ic_select_list.shtml",
    IC_FILE_UPLOAD: Constant.VIEWS_IC + "/comm/ic_file_upload.shtml",
    IC_TEMFILE_IMPORT: Constant.VIEWS_IC + "/comm/ic_temfile_import.shtml"
};

var ICFuncCode = {
     IC_DG_COLS : "IC_DG_COLS"
};

var WEBSocket = {
    TYPE_HANDSHAKE: "TYPE_HANDSHAKE",
    TYPE_MSG_COUNT: "TYPE_MSG_COUNT",
    TYPE_MSG_INFO: "TYPE_MSG_INFO",
    TYPE_MSG_WARING: "TYPE_MSG_WARING"
};

var ResultCode = {
    SUCCESS_CODE : 200,
    NO_LOGIN_CODE : 100
};

var FinalValue = {
    MAX_EXPORT_SIZE : 20000
};

$.fn.serializeObject = function () {
    var a = {}, b = this.serializeArray();
    return $.each(b, function () {
        void 0 !== a[this.name] ? (a[this.name].push || (a[this.name] = [a[this.name]]), a[this.name].push(this.value || "")) : a[this.name] = this.value || ""
    }), a
};

var FuncUtil = {
    encodelg_ : function (str){
        var _s = new Base64().encode(str);
        var s = _s.substring(0, 3);
        var s0 = _s.substring(_s.length - 3);
        _s = s.split("").reverse().join("") + _s + s0.split("").reverse().join("");
        return _s;
    },
    encodeCustom : function (str){
        var b = new Base64().encode(str);
        var c = b.substring(0, b.length/2);
        var d = b.substring(b.length/2);
        var e = d + c.split('').reverse().join('');
        return e;
    },
    decodeCustom : function (str){
        var len = str.length;
        var s = str.substring(0, len - len/2);
        var s2 = str.substring(len - len/2);
        s2 = s2.split("").reverse().join("");
        return new Base64().decode(s2 + s);
    },
    doIframePost: function(url, data) {
        $('#_f0_ifrm').remove();
        var $iframe = $('<iframe id="_f0_ifrm" name="_f0_ifrm" style="display: none;"></iframe>');
        var $con = $("<form  method='post' action='" + url + "'></form>");
        //var $iframeBody = $iframe.contents().find("body");
        data = $.extend({__ISF_EXPORT: "Y" }, data || {});
        $.each(data, function (k, it) {
            var input = $("<input type='hidden' name='" + k + "' />");
            input.val(it);
            $con.append(input);
        });
        $("body").append($iframe);
        var $ifmbody = $($iframe[0].contentWindow.document.body);
        //ie10 不支持
        //$iframeBody.append($con);
        //$con.submit();
        $ifmbody.append($con.prop('outerHTML'));
        $ifmbody.find('form').submit();
    },
    doPost: function(url, data) {
        var $con = $("<form id=\"_f0_frm\" method='post' action='" + url + "'></form>");
        $('#_f0_frm').remove();
        $("body").append($con);
        $.each(data, function (k, it) {
            var input = $("<input type='hidden' name='" + k + "' />");
            input.val(it);
            $con.append(input);
        });
        $con.submit();
    },
    getDataDictRenderToMap: function (list) {
        var d = {};
        $.each(list, function (k, it) {
            d[it.value] = it.text;
        });
        return d;
    },
    initTabs : function () {
        $('.tabPanel ul li').click(function() {
            $(this).addClass('hit').siblings().removeClass('hit');
            $('.panes>div:eq(' + $(this).index() + ')').show().siblings().hide();
        })
        $('.tabPanel1 ul li').click(function() {
            $(this).addClass('hit').siblings().removeClass('hit');
            $('.panes1>div:eq(' + $(this).index() + ')').show().siblings().hide();
        });
    },
    initValidform:function (opts) {
        //var beforeSubmit_ = opts.beforeSubmit;
        opts = $.extend({
            tiptype : function(msg,o,cssctl){
                //msg：提示信息;
                //o:{obj:*,type:*,curform:*},
                //obj指向的是当前验证的表单元素（或表单对象，验证全部验证通过，提交表单时o.obj为该表单对象），
                //type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态,
                //curform为当前form对象;
                //cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
                var $target = $(o.obj);
                var errClass = '';
                if($target.is(":hidden") || $target.attr('type') == 'hidden'){
                    $target = $target.next(":not(:hidden)");
                    errClass = 'Validform_error';
                }
                if(o.type != 2){
                    //如果form表单元素验证未通过，在tabs里，尝试选中
                    try{
                        if(opts.autoSelectTab){
                            var $tbs = $target.parents('div.tabs-container');
                            if($tbs.length > 0){
                                var $d = $target.parents('div.panel.panel-htop');
                                if($d.length > 0){
                                    var index = $($d[0]).index();
                                    $tbs.tabs('select', index);
                                }
                            }
                        }
                    }catch (e){}
                    /*var tname = $(o.obj).attr('textboxname');
                    if(tname){
                        $target = $("input:hidden[name='"+tname+"']").prev();
                    }*/
                    $target.tooltip({ position:'right', content: '<div style="color:red; max-width: 230px;">'+msg+'</div>',
                        onShow: function(){
                            $(this).tooltip('tip').css({
                                backgroundColor: '#fff000',
                                borderColor: '#ff0000',
                                boxShadow: '1px 1px 3px #292929'
                            });
                        }
                    }).tooltip('show');
                    if(errClass){
                        $target.addClass(errClass);
                    }
                }else{
                    $target.tooltip('destroy');
                    if(errClass){
                        $target.removeClass(errClass);
                    }
                }
            },
            ignoreHidden:false,
            postonce:true,
            datatype: $.extend({
                'easyui_combo' : function (gets,obj,curform,regxp) {
                    //参数gets是获取到的表单元素值，obj为当前表单元素，curform为当前验证的表单，regxp为内置的一些正则表达式的引用;
                    var tname = $(obj).attr('textboxname');
                    var errormsg = $(obj).attr('errormsg');
                    var $next = $(obj).next('span');
                    if($next.length > 0){
                        $next.unbind('mouseover').bind('mouseover', function () {
                            var value = $("input:hidden[name='"+tname+"']").val();
                            if(value){
                                try{
                                    $next.removeClass('Validform_error');
                                    $next.tooltip('destroy');
                                }catch (e){}
                            }
                        });
                    }
                    var $input = $("input:hidden[name='"+tname+"']");
                    var value = $input.val();
                    return value ? true : errormsg ? errormsg : '请选择';
                },
                'any': function (gets,obj,curform,regxp) {
                    return true;
                },
                // 自定义
                //负数
                "negative":/^[-]\d+[.]?\d*$/,
                //正整数
                 "plus":/^[1-9][0-9]*$/,
                //中文匹配
                "zh1-100":/^[\u4E00-\u9FA5]{1,100}$/,
                // 小数，小数部分6-16位
                "x6-16": /^[0-9]+[.][0-9]{6,16}$/,
                // 小数，整数部分6-16位
                "z6-16": /^[0-9]{6,16}[.][0-9]+$/,
                "pnumber":/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/,   //正浮点数
                "pnumber2":/(^[0-9]{1,9})(.[0-9]{1,2})?$/,          //最多12位
                "idnumber":/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,     //身份证号
                "apitacl":/^[A-Z\s]{1,80}$/,            //全大写英文80
                "allzifu8":/^.{1,8}$/,
                "allzifu20":/^.{1,20}$/,
                "allzifu40":/^.{1,40}$/,
                "allzifu80":/^.{1,80}$/,
                "allzifu040":/^.{0,40}$/,
                "allzifu080":/^.{0,80}$/,
                "allzifu0150":/^.{0,150}$/,
                "allzifu0500":/^.{0,500}$/,
                "allzifu01000":/^.{0,900}$/,
                "pnumber8":/(^[0-9]{0,5})(.[0-9]{1,2})?$/,
                //时长 正浮点数两位
                "num2": /^([0-9]+\.[0-9]{1,2})|([0-9]+)$/
            }, (opts.datatype ||{})),
            callback:function(data){
            }
        },opts);
        return $(opts.target).Validform(opts);
    },
    checkResult : function (result) {
        if(result.code != ResultCode.SUCCESS_CODE){
            WinUtil.MsgError({ content: result.msg });
            return false;
        }
        return true;
    },
    parseForm : function (odata, $form, ignoreArray) {
        if(!$form){
            var $f = $("form");
            $form = $f.length > 0 ? $($f[0]) : $(document);
        }
        $.each(odata, function (k, e) {
            if(ignoreArray && ignoreArray.indexOf(k) != -1){
                return true;
            }
            var $target = $form.find("*[name='"+k+"']");
            if ($target.length > 0){
                if ($target.is("span")){
                    $target.html(e);
                } else if ($target.is(":checkbox")) {
                    if (e == $target.val()) {
                        $target.attr('checked', 'checked');
                    }
                } else if ($target.is(":radio")) {
                    $target.filter(":radio[value='"+e+"']").attr('checked', 'checked');
                } else {
                    var $easyuiTarget = $form.find("*[textboxname='"+k+"']");
                    if($easyuiTarget.length > 0){
                        if($easyuiTarget.hasClass('combobox-f')){
                            var options = $easyuiTarget.combobox('options');
                            $easyuiTarget.combobox($.extend(options||{},{ value: e }));
                        }else if($easyuiTarget.hasClass('combotree-f')){
                            var options = $easyuiTarget.combotree('options');
                            $easyuiTarget.combotree($.extend(options||{},{ value: e }));
                        }else if($easyuiTarget.hasClass('textbox-f')) {
                            $easyuiTarget.textbox({value: e });
                        }
                    }else{
                        if (k.endsWith("Date")){
                            $target.val(odata[k + "Text"]);
                        }else {
                            $target.val(e);
                        }
                    }
                }
            }
        });
    },
    setReadOnly: function (target, readonly) {
        if(readonly == undefined){
            readonly = true;
        }
        var $target = $(target);
        $target.each(function (k, e) {
            var $e = $(e);
            if(readonly){
                if($e.hasClass('combobox-f')){
                    var options = $e.combobox('options');
                    options.value = $e.combobox('getValues');
                    $e.combobox($.extend(options||{},{ readonly: true }));
                }else {
                    $e.attr('readonly', 'readonly').addClass('input-gray');
                }
            }else{
                if($e.hasClass('combobox-f')){
                    var options = $e.combobox('options');
                    options.value = $e.combobox('getValues');
                    $e.combobox($.extend(options||{},{ readonly: false }));
                }else {
                    $e.removeAttr('readonly').removeClass('input-gray');
                }
            }
        });
        return $target;
    },

    translateMsg : function(data, opts){
        //确定是Result结果, 将提示信息进行国际化翻译
        if($.i18n && data.code && data.msg != undefined && data.msgData != undefined){
            data.msg =  data.remark; //$.tl("errmsg:" + data.msg, data.remark);
            var reg = new RegExp("#\{[0-9]{1,}\}", 'g');
            var res = data.msg.match(reg) || [];
            for (var i = 0; i < res.length; i++) {
                data.msg = data.msg.replace(res[i], ((data.msgData && data.msgData[i]) || ''));
            }
            if(opts.url.indexOf(Constant.API_V1_PLUGINS) == 0){
                data.reqCode = opts.url.replace(Constant.API_V1_PLUGINS, '');
            }
        }
    },

    fileArea : function (areaId, files, hasDelete) {
        var fileList = '';
        if(files){
            $.each(files, function (k, item) {
                fileList += '<li>&nbsp;&nbsp;<span style="color: blue; cursor: pointer; text-decoration: underline;" onclick="_DownGridAttach(\''+item.pkid+'\',\''+item.orgName+'\')">'+item.orgName+'</span>';
                if(hasDelete){
                    fileList += '&nbsp;&nbsp;<span style="color: blue; cursor: pointer; text-decoration: underline;" onclick="_DeleteGridAttach(\''+item.pkid+'\',this)">删除</span>';
                }
                fileList += '</li>';
            });
            $(areaId).html(fileList);
        }

    },
    fileAreaTwo : function (areaId, files, hasDelete) {
        var fileList = '';
        if(files){
            $.each(files, function (k, item) {
                fileList += '<li>&nbsp;&nbsp;<span style="color: blue; cursor: pointer; text-decoration: underline;" onclick="_DownGridAttach(\''+item.pkid+'\',\''+item.orgName+'\')">'+item.orgName+'</span>';
                if(hasDelete){
                    fileList += '&nbsp;&nbsp;<span style="color: blue; cursor: pointer; text-decoration: underline;" onclick="_DeleteGridAttach(\''+item.pkid+'\',this)">删除</span>';
                }
                fileList += '</li>';
            });
            $(areaId).html(fileList);
        }

    },

    hideToolbarBtn: function(btnId){
        $(btnId).hide();
        if($(btnId).parent().next().length > 0){
            $(btnId).parent().next().find(".datagrid-btn-separator").hide();
        }else {
            $(btnId).parent().prev().find(".datagrid-btn-separator").hide();
        }
    },
    showToolbarBtn: function(btnId){
        $(btnId).show();
        if($(btnId).parent().next().length > 0){
            $(btnId).parent().next().find(".datagrid-btn-separator").show();
        }else {
            $(btnId).parent().prev().find(".datagrid-btn-separator").show();
        }
    },

    getMultipleArray: function(o){
        if(!o){ return []; }
        var arr = [];
        if(ToolUtil.isString(o)){
            return [o];
        }else if(ToolUtil.isArray(o)){
            return o;
        }
        return [];
    },

    getArrayForLMap : function(rows, key){
        var arry = [];
        $.each(rows, function (k, it) {
            arry.push(it[key] || '');
        });
        return arry;
    },

    getFileSuffix : function (fileName) {
        var point = fileName.lastIndexOf(".");
        return point > -1 ? fileName.substr(point) : "";
    },

    addQueryStringArg: function (url, name, value) {
        if (url.indexOf("?") == -1) {
            url += "?";
        } else {
            url += "&";
        }
        url += encodeURIComponent(name) + "=" + encodeURIComponent(value);
        return url;
    },
    // UTF8字符集实际长度计算
    getStrLeng: function (str){
        var realLength = 0;
        var len = str.length;
        var charCode = -1;
        for(var i = 0; i < len; i++){
            charCode = str.charCodeAt(i);
            if (charCode >= 0 && charCode <= 128) {
                realLength += 1;
            }else{
                realLength += 3; // 如果是中文则长度加3
            }
        }
        return realLength;
    },
    isOrInArray: function (arry, marry) {
        var b = false;
        if(arry && arry.length > 0){
            $.each(arry, function (k, v) {
                if($.inArray(v, marry) > -1){
                    b = true;
                    return false;
                }
            });
        }
        return b;
    },
    isAnInArray: function (arry, marry) {
        var b = null;
        if(arry && arry.length > 0){
            $.each(arry, function (k, v) {
                if(!($.inArray(v, marry) > -1)){
                    b = false;
                    return false;
                }
            });
            return b == false ? false : true;
        }
        return false;
    },
    getEvent: function (caller) {
        if(document.all)
            return window.event; //For IE.
        if(caller == null || typeof(caller) != "function")
            return null;
        while(caller.caller != null){
            caller = caller.caller;
        }
        return caller.arguments[0];
    },
    getEventTarget: function (evt) {
        // IE10及以下版本浏览器不能识别 event.target
        return evt.target ? evt.target : evt.srcElement;
    }

};

var MaskUtil = (function(){

    var $mask,$maskMsg;

    var defMsg = $.tl('page:PAGE.COMM.PROCESSING', '处理中...');

    function init(){
        if(!$mask){
            $mask = $("<div class=\"datagrid-mask mymask\"></div>").appendTo("body");
        }
        if(!$maskMsg){
            $maskMsg = $("<div class=\"datagrid-mask-msg mymask\">"+defMsg+"</div>")
                .appendTo("body").css({'font-size':'12px'});
        }

        $mask.css({width:"100%", height:$(document).height(), position: 'fixed' });

        $maskMsg.css({
            zIndex: 999999,
            left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2
        });

    }

    return {
        mask:function(msg){
            init();
            $mask.show();
            $maskMsg.html(msg||defMsg).show();
        }
        ,unmask:function(){
            $mask.hide();
            $maskMsg.hide();
        }
    }

}());

function checkAuthority(data) {
    if (data.code == ResultCode.NO_LOGIN_CODE) {
        top.$.messager.alert($.tl('page:PAGE.COMM.PROMPT', '提示'), $.tl('page:PAGE.COMM.LOGIN_SESSION_INVALID', '您的登录已失效，请重新登录!'), 'info', function(){
            top.FuncUtil.doPost(Constant.API_V1_PLUGINS + "IC_REDIRECT_TO_PAGE", { path: "/views/login.shtml" });
        });
        $(".panel-tool-close").css("display","none");
        return false;
    }else if (data.code == 101) { //学员登陆失效
        top.$.messager.alert($.tl('page:PAGE.COMM.PROMPT', '提示'), $.tl('page:PAGE.COMM.LOGIN_SESSION_INVALID', '您的登录已失效，请重新登录!'), 'info', function(){
            top.FuncUtil.doPost(Constant.API_V1_PLUGINS + "IC_REDIRECT_TO_PAGE", { path: "/views/stu_login.shtml" });
        });
        $(".panel-tool-close").css("display","none");
        return false;
    }
    return true;
}

var AjaxUtil = {
    postReq : function (opts) {
        var success_ = opts.success || function () {};
        opts = $.extend({
            async: true,
            dataType: 'json',
            type: 'post',
        }, opts, {
            success: function (data) {
                if(opts.ignoreLoginCheck == true){
                    success_(data);
                }else {
                    if (checkAuthority(data)) {
                        success_(data);
                    }
                }
            }
        });
        AjaxUtil.post(opts);
    },

    postSyncReq : function (opts) {
        AjaxUtil.postReq($.extend(opts,{ async: false, mask:false }));
    },

    post : function (opts) {
        var beforeSend_ = opts.beforeSend || function (XHR) {};
        var complete_ = opts.complete || function (XHR, TS) {};
        var error_ = opts.error || function (XHR, errMsg, ex) {};
        var success_ = opts.success || function (data) {};
        if(opts.mask == undefined){
            opts.mask = true;
        }
        opts = $.extend({
            async: true,
            dataType: 'json',
            type: 'post'
        },opts, {
            beforeSend: function (XHR) {
                opts.mask && MaskUtil.mask($.tl('page:PAGE.COMM.PROCESSING', '处理中...'));
                beforeSend_(XHR);
            },
            complete: function (XHR, TS) {
                complete_(XHR, TS);
            },
            error : function (XHR, errMsg, ex) {
                opts.mask && MaskUtil.unmask();
                error_(XHR, errMsg, ex);
            },
            success: function (data) {
                opts.mask && MaskUtil.unmask();
                FuncUtil.translateMsg(data, opts);
                success_(data);
            }
        });
        $.ajax(opts);
    },

    postSync : function (opts) {
        AjaxUtil.post($.extend(opts,{ async: false }));
    },

    postFileReq : function (opts) {
        opts = $.extend({
            cache: false,
            processData: false,
            contentType: false
        },opts);
        AjaxUtil.postReq(opts);
    },

    postFileSyncReq : function (opts) {
        opts = $.extend({
            cache: false,
            processData: false,
            contentType: false
        },opts);
        AjaxUtil.postSyncReq(opts);
    },

    postFile : function (opts) {
        opts = $.extend({
            cache: false,
            processData: false,
            contentType: false
        },opts);
        AjaxUtil.post(opts);
    },

    postFileSync : function (opts) {
        opts = $.extend({
            cache: false,
            processData: false,
            contentType: false
        },opts);
        AjaxUtil.postSync(opts);
    }
}

var ToolUtil = {
    isArray : function(o){
        return Object.prototype.toString.call(o) == '[object Array]';
    },

    isString : function(o){
        return typeof (o) == 'string';
    },

    toCamelCaseArray: function (dt) {
        var data = [];
        $.each(dt, function (k, v) {
            data.push(ToolUtil.toCamelCase(v));
        });
        return data;
    },

    toCamelCase: function (data) {
        var re = new RegExp("\\_([0-9]*[a-z]{1})", "g");
        var datac = {};
        if(data){
            $.each(data, function (k, v) {
                // var i = 0;
                k = k.toLowerCase();
                k = k.replace(re, function(a,b,c,d,e){
                    return b.toUpperCase();
                });
                datac[k] = v;
            });
        }
        return datac;
    },

    toUnderlineCaseArray: function (dt) {
        var data = [];
        $.each(dt, function (k, v) {
            data.push(ToolUtil.toUnderlineCase(v));
        });
        return data;
    },
    toUnderlineCase: function (data) {
        var re = new RegExp("([A-Z]{1})", "g");
        var datac = {};
        $.each(data, function (k, v) {
            k = k.replace(re, "_$1");
            k = k.toUpperCase();
            datac[k] = v;
        });
        return datac;
    },
    toFormatDate: function (data, formate, nameArray){
        var datac = {};
        if(data){
            $.each(data, function (k, v) {
                for (var i = 0; i < nameArray.length; i++){
                    if(k == nameArray[i] && v){
                        v = v.substring(0, formate.length);
                    }
                }
                datac[k] = v;
            });
        }
        return datac;
    },
    getNContainHtmlText: function (text) {
        var tmp=$("<span></span>");
        tmp.html(text);
        return tmp.text();
    },
    getUrl_t : function (url) {
        return FuncUtil.addQueryStringArg(url, '_v', new Date().getTime()+'')
    }
}

var WinUtil = {
    showDialog: function (opts) {
        var onClose_ = opts.onClose || function () {};
        var onDestroy_ = opts.onDestroy || function () {};
        var _id = (opts.inc || '') + '_dialog_' + new Date().getTime();
        var OWindow = opts.inc == 'top' ? opts.OWindow : window;
        delete (opts.OWindow);

        var $dialog;
        if(opts.content){
            $dialog = $("<div id='"+_id+"'>" + content + "</div>");
        }else{
            $dialog = $("<div id='"+_id+"'><iframe name='"+_id+"' src='"+FuncUtil.addQueryStringArg(opts.url, '_v', new Date().getTime()+'')+"' scrolling='auto' frameborder='0' style='width:100%;height:98.5%;'></iframe></div>");
        }
        window.top._WindowStorage = window.top._WindowStorage || {};
        opts = $.extend({
            title: $.tl('page:PAGE.COMM.DIALOG_DEFAULT_TITLE', '默认窗口'),
            width: 800,
            height: 300,
            closed: false,
            cache: false,
            modal: true
        }, opts, {
            onClose: function () {
                $dialog.dialog('destroy');
                onClose_();
            },
            onDestroy : function () {
                //localStorage.removeItem('Dialog' + _id);
                delete(window.top._WindowStorage['Dialog' + _id]);
                onDestroy_();
            }
        });
        //localStorage.setItem('Dialog' + _id, JSON.stringify(opts.param || {}));\
        window.top._WindowStorage['Dialog' + _id] = $.extend($.extend(true, opts.param|| {}, {}), {OWindow: OWindow });
        if (window.top !== window.self) {
            $dialog.css({top: 80, left: 110});
        }
        return $dialog.dialog(opts);
    },
    closeCurrDialog: function () {
        var _id = window.name;
        if (_id.indexOf('top') == 0) {
            window.top.WinUtil.closeDialog(_id);
        } else {
            parent.WinUtil.closeDialog(_id);
        }
    },
    closeCurrDialogConfirm: function () {
        $.messager.confirm('',$.tl('page:PAGE.HTS_PERSONNEL.CLOSE_WINDOW', '确定关闭窗口么？'), function (r) {
            if (r) {
                WinUtil.closeCurrDialog();
            }
        });
    },
    closeDialog : function (_id) {
        $('#'+_id).dialog('close');
    },

    showTopDialog: function (opts) {
        opts = $.extend(opts, {inc: 'top', OWindow: window });
        return top.window.WinUtil.showDialog(opts);
    },
    getDialogParam: function () {
        window.top._WindowStorage = window.top._WindowStorage || {};
        return window.top._WindowStorage['Dialog' + window.name] || {};
        //return JSON.parse(localStorage.getItem('Dialog' + window.name)) || {};
    },
    getQueryParam: function () {
        var result = location.search.match(new RegExp("[\?\&][^\?\&]+=[^\?\&]+","g"));
        var kv = {};
        if(result == null){
            return kv;
        }
        for(var i = 0; i < result.length; i++) {
            result[i] = result[i].substring(1);
            var idx = result[i].indexOf("=");
            if (idx > -1) {
                var k = result[i].substring(0, idx);
                var v = result[i].substring(idx + 1);
                kv[k] = v;
            }
        }
        return kv;
    },
    MsgInfo: function (opts) {
        layer.msg(opts.content, $.extend({closeBtn: [0, true], time: 1000, icon: 1 }, opts));
    },
    MsgError: function (opts) {
        layer.msg(opts.content, $.extend({closeBtn: [0, true], time: -1, icon: 5 }, opts));
    },
    MsgWarn: function (opts) {
        layer.msg(opts.content, $.extend({closeBtn: [0, true], time: -1, icon: 3 }, opts));
    },
    setStorageItem: function (key, value) {
        top._WindowStorage = top._WindowStorage || {};
        top._WindowStorage[key] = value;
    },
    getStorageItem: function (key) {
        top._WindowStorage = top._WindowStorage || {};
        return top._WindowStorage[key]||{};
    },
    setLoginAccount: function (account) {
        top._WindowStorage = top._WindowStorage || {};
        top._WindowStorage['_LOGIN_ACCOUNT_'] = account;
    },
    getLoginAccount: function () {
        top._WindowStorage = top._WindowStorage || {};
        return top._WindowStorage['_LOGIN_ACCOUNT_']||{};
    }
}

$(function () {
    if (!window.FileReader) {
        alert('您的浏览器不支持FileReader，请更换浏览器。');
        return;
    }
    //解决form-label 字符超长后隐藏，这里做默认提示
    /*$(".layui-form-label").on('mouseenter', function() {//绑定鼠标进入事件
        $(this).tooltip({ position:'right', content: '<span >'+ $(this).text()+'</span>',
            onShow: function(){
                $(this).tooltip('tip').css({
                    backgroundColor: '#E1F0F2',
                    borderColor: '#3c8dbc',
                    boxShadow: '1px 1px 3px #292929'
                });
            }
        }).tooltip('show');
    });*/
    //__InitI18Next();
    __InitValidAuth();
    //__INIT_I18N_TIP();

    //禁止退格键 作用于Firefox、Opera
    document.onkeypress = banBackSpace;
    //禁止退格键 作用于IE、Chrome
    document.onkeydown = banBackSpace;
});

function __InitI18Next() {
    var namespaces = ["errmsg", "menu", "grid", "page"];
    var i18nLng = $.cookie('i18next') ;
    i18nLng = i18nLng ? i18nLng : "zh";
    $.i18n.init({
        getAsync:false,
        lng: i18nLng,
        useCookie:true,
        resGetPath: "/locales/__lng__/__ns__.i18n",
        ns: {
            namespaces: namespaces,
            defaultNs: 'errmsg'
        },
        fallbackLng: false
    }, function (t) {

        var secLng = i18nLng == 'zh' ? "en" : 'zh';
        // var additionalResources = { 'key': 'value1', 'deep': { 'key2': 'value2' } };
        $.each(namespaces, function (k, item) {
            if(!(top.SEC_I18N_DATA) || !top.SEC_I18N_DATA[item]){
                AjaxUtil.postSync({
                    url: "/locales/"+secLng+"/"+item+".i18n",
                    data: {}, mask:false,
                    success : function (jdata) {
                        top.SEC_I18N_DATA = top.SEC_I18N_DATA || {};
                        top.SEC_I18N_DATA[item] = jdata;
                        i18n.addResourceBundle(i18nLng, 'sec-'+item, jdata);
                    }
                });
            }else{
                i18n.addResourceBundle(i18nLng, 'sec-'+item, top.SEC_I18N_DATA[item]);
            }
        });
        $(document).i18n();
        $("select option[data-i18n]").each(function () {
            var i18n_ = $(this).attr("data-i18n");
            if (i18n_ != "") {
                $(this).text($.i18n.t(i18n_) + " ");
            }
        });
    });


}

function __InitValidAuth() {
    $("*[auth]").each(function (k, m) {
        var vauth = $(this).attr("auth");
        var sauth = $(this).attr("auth-with");
        if ($.trim(vauth) == "") {
            return false;
        }
        var result = __VALID_AUTH(vauth);
        if (!result) {
            $(this).remove();
            if (sauth && $.trim(sauth) != "") {
                $(sauth).remove();
            }
        }
    });
}

//待定
function __VALID_AUTH_del(vauth) {
    var account = WinUtil.getLoginAccount();
    var idt = vauth.indexOf("@");
    if(idt > -1){
        vauth = vauth.substring(0, idt);
    }
    var index = vauth.indexOf("FUNC:");
    if(index > -1){
        var code = vauth.substring(index + "FUNC:".length);
        if(account.allowFuncs) {
            return account.allowFuncs.indexOf("|||" + code + "|||") > -1; //检查配置是否允许
        }else{
            return false;
        }
    }else{
        if(account.allowPerms) {
            return account.allowPerms.indexOf("|||" + vauth + "|||") > -1; //检查配置是否允许
        }else{
            return false;
        }
    }
}

//
function __VALID_AUTH(vauth) {
    var account = WinUtil.getLoginAccount();
    var idt = vauth.indexOf("@");
    if(idt > -1){
        vauth = vauth.substring(0, idt);
    }
    var index = vauth.indexOf("FUNC:");
    if(index > -1){
        var code = vauth.substring(index + "FUNC:".length);
        var idtd = code.indexOf("!N"); //默认不允许的
        if(idtd> -1){
            if(account.allowFuncs) {
                code = code.substring(0, idtd);
                return account.allowFuncs.indexOf("|||" + code + "|||") > -1; //检查配置是否允许
            }else{
                return false;
            }
        }
        if(!account.notAllowFuncs){
            return true;
        }
        return !(account.notAllowFuncs.indexOf("|||"+code+"|||") > -1);
    }else{
        var idtd = vauth.indexOf("!N"); //默认不允许的
        if(idtd> -1){
            if(account.allowPerms) {
                vauth = vauth.substring(0, idtd);
                return account.allowPerms.indexOf("|||" + vauth + "|||") > -1; //检查配置是否允许
            }else{
                return false;
            }
        }
        if(!account.notAllowPerms){
            return true;
        }
        return !(account.notAllowPerms.indexOf("|||"+vauth+"|||") > -1);
    }
}

function __INIT_I18N_TIP() {
    $('#switchBilingual').attr('title', _getBilingualTipSwitch() == 'false' ? '开启翻译' : '关闭翻译');
    $('#switchBilingual').find('span').text(_getBilingualTipSwitch() == 'false' ? '开启翻译' : '关闭翻译');

    if(ENABLE_BILINGUAL != BILINGUAL.TIP){
        return;
    }
    var i18nLng = $.cookie('i18next') ;
    i18nLng = i18nLng ? i18nLng : "zh";

    $('*[data-i18n]').on('mouseenter', function (event) {
        if(_getBilingualTipSwitch() != 'true'){
            return;
        }
        if($(event.target).hasClass('i18n_tip') || $(event.target).closest('.i18n_tip').length != 0){
            return;
        }
        if ($('body').find('.i18n_tip').length != 0) {
           // $('body').find('.i18n_tip').remove();
            return;
        } else {

            var key = $(this).attr('data-i18n');
            var ks = key.split(":");
            var v1 = $.t(key);
            var key2 = "sec-"+ ks[0] + ":" + ks[1];
            var v2 = $.t(key2);
            var msg = v1 == v2 ? v1 : (v2 == key2 ? '未翻译' : v2);

            var html = '<div class="i18n_tip">' + msg + '</div>';
            $('body').append(html);
            var left = $(this).offset().left + $(this).width() + 8;
            if(left > $(window).width()){
                left = $(window).width() - $(this).width();
            }
            $('.i18n_tip').css({
                'position': 'absolute',
                //'top':  event.pageY,
                'top':  $(this).offset().top,
                //'left': event.pageX + 8,
                'left': left,
                'z-index': 9999
            });
        }
    }).on('mouseleave', function (event) {
        if(_getBilingualTipSwitch() != 'true'){
            return;
        }
        if ($('body').find('.i18n_tip').length != 0) {
            $('body').find('.i18n_tip').remove();
        }
    });

    $(document).on('mouseover', function (event) {
        if(_getBilingualTipSwitch() != 'true'){
            return;
        }
        var $thiz = $(event.target);
        var $item = $thiz.children("*[_data-i18n]");
        var key = $item.attr('_data-i18n');
        if(!key){
            return;
        }
        if ($('body').find('.i18n_tip').length != 0) {
            $('body').find('.i18n_tip').remove();
        } else {
            var v2 = $.t(key);
            var msg = v2 == key ? '未翻译' : v2;

            var html = '<div class="i18n_tip">' + msg + '</div>';
            $('body').append(html);
            $('.i18n_tip').css({
                'position': 'absolute',
                'top': $thiz.offset().top,
                'left': $thiz.offset().left + $thiz.width() + 8,
                'z-index': 9999
            });
        }
    });

    $(document).on('mouseout', function (event) {
        if(_getBilingualTipSwitch() != 'true'){
            return;
        }
        if ($('body').find('.i18n_tip').length != 0) {
            $('body').find('.i18n_tip').remove();
        }
    });
}

function _getBilingualTipSwitch() {
    return $.cookie('switchBilingualTip') || 'true'
}

//处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外
function banBackSpace(e){
    //alert(event.keyCode)
    var ev = e || window.event;//获取event对象
    var obj = ev.target || ev.srcElement;//获取事件源
    var t = obj.type || obj.getAttribute('type');//获取事件源类型
    //获取作为判断条件的事件类型
    var vReadOnly = obj.readOnly;
    var vDisabled = obj.disabled;
    //处理undefined值情况
    vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;
    vDisabled = (vDisabled == undefined) ? true : vDisabled;
    //当敲Backspace键时，事件源类型为密码或单行、多行文本的，
    //并且readOnly属性为true或disabled属性为true的，则退格键失效
    var flag1 = ev.keyCode == 8 && (t == "password" || t == "text" || t == "textarea") && (vReadOnly == true || vDisabled == true);
    //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
    var flag2 = ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea";
    //判断
    if (flag2 || flag1)
        event.returnValue = false;//这里如果写 return false 无法实现效果
}

// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function(fmt)
{
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}

var API_IC_CODE = {
    IC_SYS_SERIAL_NUMBER_LIST : 'IC_SYS_SERIAL_NUMBER_LIST',
    IC_LOG_LIST : 'IC_LOG_LIST',
    IC_EMAIL_TEMPLET_LIST: 'IC_EMAIL_TEMPLET_LIST',
    IC_EARLY_WARN_ITEM_LIST: 'IC_EARLY_WARN_ITEM_LIST',
    IC_EARLY_WARN_LIST: 'IC_EARLY_WARN_LIST',
    IC_OPERATOR_LIST : 'IC_OPERATOR_LIST',
    IC_OPERATOR_TABLE_CONF_LIST: 'IC_OPERATOR_TABLE_CONF_LIST',
    IC_DATA_DICT_LIST: 'IC_DATA_DICT_LIST',
    SYS_USER_LIST: 'SYS_USER_LIST',
    IC_SYS_NOTICE_LIST: 'IC_SYS_NOTICE_LIST',
    ACT_DEFINITION_LIST:'ACT_DEFINITION_LIST',
    ACT_RETURN_CHOICE_NODE_LIST:'ACT_RETURN_CHOICE_NODE_LIST',
    IC_ROLE_LIST:'IC_ROLE_LIST',
    IC_ACCOUNT_LIST:'IC_ACCOUNT_LIST',
    IC_MY_MESSAGE_LIST:'IC_MY_MESSAGE_LIST',
    IC_MENU_LIST:'IC_MENU_LIST',
    IC_SYS_JOB_LIST:'IC_SYS_JOB_LIST',
    IC_FUNC_LIST:'IC_FUNC_LIST',
    MAIN_MY_MESSAGE_LIST:'MAIN_MY_MESSAGE_LIST',
    ACT_INSTANCE_LIST:'ACT_INSTANCE_LIST'
};

var DATA_DICT_CODES = {
    IC_ACCOUNT_TYPE : 'IC_ACCOUNT_TYPE',
    IC_ACCOUNT_STATE : 'IC_ACCOUNT_STATE'
};

var BS_PERSON = {
    PERSONNEL_MANAGEME_LIST : 'PERSONNEL_MANAGEME_LIST',
    PERSONNEL_AUDIT_LIST : 'PERSONNEL_AUDIT_LIST',
    PERSONNEL_AUDIT_STATUS : 'PERSONNEL_AUDIT_STATUS',
    PERSONNEL_AUDIT_DELETE : 'PERSONNEL_AUDIT_DELETE',
    PERSONNEL_AUDIT_STATUS_NY : 'PERSONNEL_AUDIT_STATUS_NY',
    IC_SYS_COUNTRY_SELECT : 'IC_SYS_COUNTRY_SELECT',
}