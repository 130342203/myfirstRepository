/**
 * Created by tpeng on 2018-09-18.
 * 查询区域 收缩/展示 插件
 * dependences : {}
 */
(function($) {

    $.fn.MySearchArea = function(method, options) {
        if(typeof(method) == 'string'){
            if('show' == method){
                show($.fn.MySearchArea.storage.fieldset, $(this), $.fn.MySearchArea.storage.div_);
            }else if('hide' == method){
                hide($.fn.MySearchArea.storage.fieldset, $(this), $.fn.MySearchArea.storage.div_);
            }else if('searchHide' == method) {
                searchHide($.fn.MySearchArea.storage.fieldset, $(this), $.fn.MySearchArea.storage.div_);
            }else if('clear' == method){
                clear($.fn.MySearchArea.storage.fieldset, $(this), $.fn.MySearchArea.storage.div_);
            }
        }else{
            options = method;
            var opts = $.extend({}, $.fn.MySearchArea.defaults, options);
            return this.each(function(){
                initSearchArea(opts, $(this));
            });
        }
    };

    $.fn.MySearchArea.defaults = {
    };

    $.fn.MySearchArea.storage = {
        div_ : null,
        fieldset: null
    };

    function initSearchArea(opts, $searchRect){
        $.fn.MySearchArea.storage.fieldset
            = $fieldset = $searchRect.parents("fieldset");
        $searchRect.hide();
        $.fn.MySearchArea.storage.div_ = $div_
            = $("<div style=\"text-align: center; cursor: pointer;\"><div qarea style='text-align: left;height: 20px;'>【查询条件】 无</div>" +
            "<span _tp='angle' class=\"fa fa-angle-double-down\"></span><span _tp='angle' class=\"fa fa-search\"></span></div>");
        $searchRect.after($div_);

        $div_.bind('click', function () {
            if($searchRect.is(":hidden")){
                show($fieldset, $searchRect, $div_);
            }else{
                hide($fieldset, $searchRect, $div_);
            }
        });
        $('body').bind('click', function (e) {
            var o = e.target;
            if($(o).closest($fieldset).length==0)//不是特定区域
                hide($.fn.MySearchArea.storage.fieldset, $searchRect, $.fn.MySearchArea.storage.div_);
        });
    }

    function show($fieldset, $searchRect, $div_){
        var width = $fieldset.width();
        var height = $fieldset.height();
        var $tmpDiv = $('<div _tmpdiv />').css({ width: width, height: height + 10, backgroundColor: '#fff' });
        $fieldset.after($tmpDiv);
        $fieldset.children().css({
            backgroundColor: '#fff'
        });
        $fieldset.css({
            position: 'absolute',
            width: width,
            zIndex: 8900
        });
        $searchRect.show();
        $div_.find('span.fa-angle-double-down').removeClass('fa-angle-double-down').addClass('fa-angle-double-up');
        $div_.find("div[qarea]").hide();
    }

    function hide($fieldset, $searchRect, $div_){
        $fieldset.css({
            position: 'relative',
            zIndex: 0
        });
        $fieldset.siblings("div[_tmpdiv]").remove();
        $searchRect.hide();
        $div_.find("div[qarea]").show();
        $div_.find("div[qarea]").html(renderSearchText($searchRect)).show();
        $div_.find('span.fa-angle-double-up').removeClass('fa-angle-double-up').addClass('fa-angle-double-down');
    }

    function searchHide($fieldset, $searchRect, $div_){
        $fieldset.css({
            position: 'relative',
            zIndex: 0
        });
        $fieldset.siblings("div[_tmpdiv]").remove();
        $searchRect.hide();
        $div_.find("div[qarea]").html(renderSearchText($searchRect)).show();
        $div_.find('span.fa-angle-double-up').removeClass('fa-angle-double-up').addClass('fa-angle-double-down');
    }

    function clear($fieldset, $searchRect, $div_){
        $searchRect.each(function () {
            this.reset();
        });
        $div_.find("div[qarea]").html(renderSearchText($searchRect));
        hide($fieldset, $searchRect, $div_);
    }

    function renderSearchText($searchRect) {
        var data = $searchRect.serializeObject();
        var searArry = [];
        $.each(data, function (name, value) {
            if(!value){
                return true;
            }
            var $b = $searchRect.find("*[textboxname='"+name+"']");
            if($b.length == 1){
                var mtext = $b.closest('td').prev().text();
                if($b.attr('comboname') == name){
                    searArry.push({name: mtext, text: $b.combobox('getText'), value: value });
                }else{
                    searArry.push({name: mtext, text: $b.val(), value: value });
                }

            }else{
                $b = $searchRect.find("*[name='"+name+"']");
                if($b.length == 1){
                    var mtext = $b.closest('td').prev().text();
                    searArry.push({name: mtext, text: $b.val(), value: value });
                }
            }
        });
        var searHtml = [];
        $.each(searArry, function (k, item) {
            searHtml.push('<span class="searLbl">' + item.name + " : " + item.text + '</span>');
        });
        if(searHtml.length == 0){
            return '【查询条件】 无';
        }
        return '【查询条件】 ' + searHtml.join(" ");
    }

})(jQuery);


