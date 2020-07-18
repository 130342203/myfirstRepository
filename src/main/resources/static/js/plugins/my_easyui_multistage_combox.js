/**
 * Created by tpeng on 2019-12-18.
 * 基于EasyUI 的下拉 多级联动 插件
 * dependences : {}
 */
(function($) {

    $.fn.MyEasyUIMultistageCombox = function(options) {
        var opts = $.extend({}, $.fn.MyEasyUIMultistageCombox.defaults, options);
        return initMyEasyUIMultistageCombox(opts, $(this));
    };

    $.fn.MyEasyUIMultistageCombox.defaults = {
        level: []  //__id, __def_value __width, __height
        ,textField: 'text'
        ,valueField: 'value'
    };

    var LvlMap = {};
    var defSelect = [{text: '请选择', value: ''}];

    function initMCombox(opts, lvlObj, params) {
        var _url = lvlObj.url ? lvlObj.url : opts.url;
        if(!_url){
            return;
        }
        AjaxUtil.postSync({
            url: _url,
            data: params,
            success: function (result) {
                var data = result.data[result.reqCode];
                if(data==undefined){
                    return false;
                }
                EasyUI.initCombobox("#"+lvlObj.__id, {
                    textField: opts.textField, valueField: opts.valueField,
                    width: lvlObj.__width || 120,
                    height: lvlObj.__height || 26,
                    data: defSelect.concat(data),
                    //不要用onSelect ， set默认值时不会触发 //onChange 的r只有value数值
                    onSelect: function (r) {
                        clearMcombox(opts, lvlObj);

                        var lvlIdex = LvlMap[lvlObj.__id].__level + 1;
                        if(opts.level[lvlIdex]){
                            initMCombox(opts, opts.level[lvlIdex], $.extend(opts.level[lvlIdex], r));
                        }
                    }
                });
            }
        });
    }

    function clearMcombox(opts,lvlObj) {
       var __level = LvlMap[lvlObj.__id].__level;
       $.each(LvlMap, function (__id, item) {
           if(item.__level > __level){
               $("#"+__id).combobox({ data: defSelect, value: '' });
           }
       })
    }

    function initMyEasyUIMultistageCombox(opts, $rect){
        __init_ing = true;
        LvlMap = {};
        $.each(opts.level, function (k, it) {
            LvlMap[it.__id] = $.extend(it, {__level: k });

            EasyUI.initCombobox("#"+it.__id, {
                textField: opts.textField, valueField: opts.valueField,
                width: it.__width || 120,
                height: it.__height || 26,
                data: defSelect
            });
        });

        var first = opts.level[0];
        initMCombox(opts, first, first);

        $.each(opts.level, function (k, it) {
            //若有默认值
            if(it.__def_value){
                $("#"+it.__id).combobox('select', $.trim(it.__def_value + ''));
            }
        });

        return {
            $thiz: $rect
        };
    }

})(jQuery);


