/**
 * EasyUI DataGrid 平台自定义插件
 * @author tpeng
 */
(function ($) {

    $.fn.MyDataGrid = function (opts) {
        opts.dg_type = 'datagrid';
        if (opts.custom == undefined || ToolUtil.isArray(opts.custom)) {
            BindDataGrid(this, opts);
        } else {
            AsynBindGrid(this, opts);
        }
    };

    $.fn.MyTreeGrid = function (opts) {
        opts.dg_type = 'treegrid';
        if (opts.custom == undefined || ToolUtil.isArray(opts.custom)) {
            BindTreeGrid(this, opts);
        } else {
            AsynBindGrid(this, opts);
        }
    };

    function AsynBindGrid(thiz, opts) {
        var url = Constant.API_V1_PLUGINS + ICFuncCode.IC_DG_COLS;
        var data = {dgCode: (opts.custom.dgCode || opts.custom.code)};
        opts.ignoreLoginCheck = opts.ignoreLoginCheck == undefined ? false : true;
        AjaxUtil.postSync({
            url: url,
            data: data, ignoreLoginCheck: opts.ignoreLoginCheck,
            success: function (result) {
                if (result.code != ResultCode.SUCCESS_CODE) {
                    $.messager.alert($.tl('page:PAGE.COMM.ERROR_PROMPT', '错误提示'), result.msg, 'error');
                    return;
                }
                var alter = opts.custom.alter || {};
                var _columns = result.data[ICFuncCode.IC_DG_COLS];
                var _fcolumns = [[]];
                $.each(_columns, function (k, list) {
                    for (var i = 0; i < list.length; i++) {
                        var e = list[i];
                        var x = {};
                        try {
                            e.extraItem ? eval('x =' + e.extraItem) : {};
                        } catch (ex) {
                        }
                        e = $.extend(e, {halign: 'center'}, {extraItem: ''}, x);
                        // e.title = $.tls('grid:GRID.' + 'O' + e.operId + '.' + data.dgCode + '.' + e.field, e.title);
                        if (e.field && alter[e.field]) {
                            e = $.extend(e, alter[e.field]);
                        }
                        if (e.frozen && e.frozen == 'Y') {
                            list.splice(i--, 1);
                            _fcolumns[0].push($.extend({}, e));
                        }
                    }
                });
                opts.columns = _columns || [[]];
                opts.frozenColumns = _fcolumns;
                if ('datagrid' == opts.dg_type) {
                    BindDataGrid(thiz, opts);
                } else if ('treegrid' == opts.dg_type) {
                    BindTreeGrid(thiz, opts);
                }
            }
        });
    }

    function BindDataGrid(thiz, opts) {
        // opts.identity
        var ls = opts.onLoadSuccess;
        var bsc = opts.onBeforeSortColumn;
        opts.firstLoad = opts.firstLoad === undefined ? false : opts.firstLoad;
        opts = $.extend({
            url: '',
            striped: true,
            pagination: true,
            loadMsg: $.tl('page:PAGE.COMM.LOADING', '加载中...'),
            rownumbers: true,
            onRowContextMenu: function (e, rowIndex, rowData) {
                rowContextMenu(opts, thiz, e, rowIndex, rowData);
            },
            loader: function (params, success, error) {
                if (!opts.firstLoad) {
                    opts.firstLoad = true;
                    return false;
                }
                loader(opts, params, success, error);
            },
            loadFilter: function (result) {
                if(ToolUtil.isArray(result)){
                    return {total: result.length, rows: result};
                }
                var data = result.data[result.reqCode];
                if(ToolUtil.isArray(data)){
                    return {total: data.length, rows: data};
                }
                return {total: data.total, rows: data.rows};
            },
            singleSelect: true,
            checkOnSelect: false,
            selectOnCheck: false
        }, opts, {
            onLoadSuccess: function () {
                //重新加载后清除编辑记录
                GridEdit.clearDGEdit($(thiz));
                ls && ls();
                try {
                    $(thiz).datagrid('doCellTip', {'max-width': '280px'});
                    //grid 附件提示
                    var isfRender = $(thiz).data("RenderGridAttach");
                    if(isfRender) {
                        _BindGridAttach();
                    }
                    $(thiz).datagrid('fixRowHeight');
                } catch (e) {
                    console && console.error(e);
                }
            },
            onBeforeLoad: function (param) {
                var __flagQ = $(thiz).data('__flagQ');
                if(__flagQ == 'Y' && param.sort && param.order){
                    var flagQ = {
                        qs : [{s: param.sort, o: param.order }]
                    };
                    var options = $(thiz).datagrid('options');
                    var queryParams = $.extend(options.queryParams||{}, { __flagQ: 'Y', flagQ: FuncUtil.encodeCustom(JSON.stringify(flagQ)) });
                    $(thiz).removeData('__flagQ');
                    $(thiz).datagrid('load', queryParams);
                    return false;
                }
            },
            onBeforeSortColumn: function (sort, order) {
                var b = true;
                if(bsc){
                    b = bsc(sort, order)
                }
                if(b == true || b == undefined){
                    $(thiz).data('__flagQ', 'Y');
                }
            }
        });
        if (opts.toolbar && opts.toolbar.length > 0) {
            opts.toolbar = validToolbar(opts.toolbar);
        }
        opts.queryParams_ = opts.queryParams || {};
        var $layout = $(thiz).parent("div.mlayout");
        var $elayout = $layout.parents("div.easyui-layout");
        if ($elayout.length > 0) {
            opts.height = $elayout.height(); //以便计算行数
            if ($elayout.data("_calc") != 'Y') { //标记，避免重复计算
                //执行下面行后 chorme(window.height)会莫名减少10px 这里-10做补偿（其他浏览器还未测试）
                //+ 4 调整footer与边框距离 美观因素
                $elayout.layout({fit: false, height: $elayout.height() - 10 + 4 - (opts.offset || 0)});
                $elayout.data("_calc", "Y");
            }
            opts.fit = true;
        }
        var $elayoutWd = $elayout.length > 0 ? $elayout.width() : 0;
        var $elayoutHt = $elayout.length > 0 ? $elayout.height() : 0;

        var _h = calcBodyHeight(opts);
        opts = $.extend(opts, {pageSize: parseInt(_h / 30)});
        opts = $.extend(opts, {pageList: [opts.pageSize]});

        $(thiz).datagrid(opts);
        var options = $(thiz).datagrid('options');
        if(ENABLE_BILINGUAL == BILINGUAL.DIRECT){
            var $pele = $(thiz).parent();
            $pele.addClass(BILINGUAL_CLASS);
            $pele.siblings(".datagrid-toolbar").addClass(BILINGUAL_CLASS);
            $pele.find('.datagrid-header').css({height: '40px' });
        }
        bindDGPageBtn(thiz, opts, options);
        resetPageList(thiz, opts, options);
        _bindDGLineEdit(thiz, opts);

        var owd = options.width;
        var oht = options.height;
        var winHt = $(window).height();
        var winWd = $(window).width();

        $(window).resize(function () {
            var _nwinHt = $(window).height();
            var _nwinWd = $(window).width();
            if ($elayout.length > 0) {
                $elayout.layout('resize', {
                    width: $elayoutWd + (_nwinWd - winWd),
                    height: $elayoutHt + (_nwinHt - winHt)
                });
            } else {
                if(owd > 0) {
                    $(thiz).datagrid('resize', {
                        width: owd + (_nwinWd - winWd),
                        height: oht + (_nwinHt - winHt)
                    });
                }
            }
        });
    }

    function BindTreeGrid(thiz, opts) {
        // opts.identity
        var ls = opts.onLoadSuccess;
        var lf = opts.loadFilter;
        opts.firstLoad = opts.firstLoad === undefined ? false : opts.firstLoad;
        opts = $.extend({
            url: '',
            striped: true,
            pagination: true,
            loadMsg: $.tl('page:PAGE.COMM.LOADING', '加载中...'),
            rownumbers: true,
            onContextMenu: function (e, rowData) {
                rowContextMenu(opts, thiz, e, -1, rowData);
            },
            loader: function (params, success, error) {
                if (!opts.firstLoad) {
                    opts.firstLoad = true;
                    return false;
                }
                loader(opts, params, success, error);
            },
            singleSelect: true,
            checkOnSelect: false,
            selectOnCheck: false
        }, opts, {
            onLoadSuccess: function () {
                //重新加载后清除编辑记录
                GridEdit.clearDGEdit($(thiz));
                //treegrid 和datagrid不同， 每次load会重绘pagination区域
                var options = $(thiz).treegrid('options');
                resetPageList(thiz, opts, options);
                ls && ls();
                try {
                    $(thiz).treegrid('doCellTip', {'max-width': '280px'});
                    var isfRender = $(thiz).data("RenderGridAttach");
                    if(isfRender) {
                        _BindGridAttach();
                    }
                    $(thiz).treegrid('fixRowHeight');
                } catch (e) {
                }
            },
            loadFilter: function (result) {
                //treegrid编辑行 append会走loadFilter //这里处理一下
                if (result && result.edit === true) {
                    return {total: 1, rows: [result.data]};
                }
                return lf ? lf(result) : result;
            }
        });
        if (opts.toolbar && opts.toolbar.length > 0) {
            opts.toolbar = validToolbar(opts.toolbar);
        }
        var $layout = $(thiz).parent("div.mlayout");
        var $elayout = $layout.parents("div.easyui-layout");
        if ($elayout.length > 0) {
            opts.height = $elayout.height(); //以便计算行数
            if ($elayout.data("_calc") != 'Y') {//标记，避免重复计算
                //执行下面行后 chorme(window.height)会莫名减少10px 这里-10做补偿（其他浏览器还未测试）
                //+ 4 调整footer与边框距离 美观因素
                $elayout.layout({fit: false, height: $elayout.height() - 10 + 4 - (opts.offset || 0)});
                $elayout.data("_calc", "Y");
            }
            opts.fit = true;
        }
        var $elayoutWd = $elayout.length > 0 ? $elayout.width() : 0;
        var $elayoutHt = $elayout.length > 0 ? $elayout.height() : 0;

        var _h = calcBodyHeight(opts);
        opts = $.extend(opts, {pageSize: parseInt(_h / 36)});
        opts = $.extend(opts, {pageList: [opts.pageSize]});

        $(thiz).treegrid(opts);

        _bindTGLineEdit(thiz, opts);

        var options = $(thiz).treegrid('options');
        if(ENABLE_BILINGUAL == BILINGUAL.DIRECT){
            var $pele = $(thiz).parent();
            $pele.addClass(BILINGUAL_CLASS);
            $pele.siblings(".datagrid-toolbar").addClass(BILINGUAL_CLASS);
            $pele.find('.datagrid-header').css({height: '40px' });
        }
        var owd = options.width;
        var oht = options.height;
        var winHt = $(window).height();
        var winWd = $(window).width();

        $(window).resize(function () {
            var _nwinHt = $(window).height();
            var _nwinWd = $(window).width();
            if ($elayout.length > 0) {
                $elayout.layout('resize', {
                    width: $elayoutWd + (_nwinWd - winWd),
                    height: $elayoutHt + (_nwinHt - winHt)
                });
            } else {
                $(thiz).treegrid('resize', {
                    width: owd + (_nwinWd - winWd),
                    height: oht + (_nwinHt - winHt)
                });
            }
        });
    }

    function _bindTGLineEdit(thiz, opts) {
        if (opts.enableLineEdit) {
            var $dg = $(thiz);
            var $view1 = $dg.parent().find(".datagrid-view1");
            //$tds.off('click','*[datagrid-row-index]');
            var $header = $view1.find(".datagrid-htable");
            $header.find(".datagrid-header-rownumber").html('<img src="/css/icons/page_edit.png" />');
            $view1.on("click", 'tr[node-id]', function (event) {
                var nodeId = $(this).attr('node-id');
                var bool = true;
                if (typeof(opts.enableLineEdit) == 'function') {
                    var row = $dg.treegrid('find', nodeId);
                    bool = opts.enableLineEdit($dg, nodeId, row);
                }
                if (!bool) {
                    return;
                }
                if (GridEdit.existDGEditIndex($dg, nodeId)) {
                    $.messager.confirm('','你确定要放弃当前行的操作吗?',function(r) {
                        if (r) {
                            GridEdit.TreeGrid.cancelDGEdit($dg, nodeId);
                        }
                    });
                    return;
                }
                GridEdit.TreeGrid.beginDGEditRow($dg, nodeId, opts);
            });
        }
    }

    function _bindDGLineEdit(thiz, opts) {
        if (opts.enableLineEdit) {
            var $dg = $(thiz);
            var $view1 = $dg.parent().find(".datagrid-view1");
            //$tds.off('click','*[datagrid-row-index]');
            var $header = $view1.find(".datagrid-htable");
            $header.find(".datagrid-header-rownumber").html('<img src="/css/icons/page_edit.png" />');
            $view1.on("click", '*.datagrid-td-rownumber', function (event) {
                var options = $(thiz).datagrid('options');
                if(!options.enableLineEdit){
                    return;
                }
                var idx = parseInt($(this).parents("tr[datagrid-row-index]").attr("datagrid-row-index"));
                var bool = true;
                if (typeof(opts.enableLineEdit) == 'function') {
                    var row = $dg.datagrid('getRows')[idx];
                    bool = opts.enableLineEdit($dg, idx, row);
                }
                if (!bool) {
                    return;
                }
                if (GridEdit.existDGEditIndex($dg, idx)) {
                    $.messager.confirm('','你确定要放弃当前行的操作吗?',function(r) {
                        if (r) {
                            GridEdit.DataGrid.cancelDGEdit($dg, idx);
                        }
                    });
                    return;
                }
                GridEdit.DataGrid.beginDGEditRow($dg, idx, opts);
                if (typeof(opts.beginDGEditRow) == 'function') {
                    var row = $dg.datagrid('getRows')[idx];
                    opts.beginDGEditRow($dg, row, idx);
                }
            });
        }
    }

    /**
     * 计算datagrid-body的高度，用于初始化PageSize的计算
     * @param opts
     */
    function calcBodyHeight(opts) {
        var $h = opts.height;
        if (opts.toolbar && opts.toolbar.length > 0) {
            $h = $h - 41;
        }
        if (opts.pagination) {
            $h = $h - 35;
        }
        $h = $h - 35; //减去header 行
        return $h;
    }

    /**
     * 重置datagrid下拉选pageSize的方式，更改为输入框
     * @param thiz
     * @param opts
     */
    function resetPageList(thiz, opts, _ac) {
        if (!_ac) return;
        var $select = $(thiz).closest('div.datagrid-wrap').find('select.pagination-page-list');
        if ($select.attr("dg__hidden") == 'Y') {
            return;
        }
        var ps = $("<input type='text' class='pagination-num' value='' style='width:30px;'/>");
        ps.val(_ac.pageSize);
        ps.bind('keypress', function (event) {
            if (event.keyCode == "13") {
                var _pageSize = $(this).val();
                if (_pageSize != "" && /^\d+$/.test(_pageSize)) {
                    if ($select.find("option[text='" + _pageSize + "']").length == 0) {
                        $("<option></option>").text(_pageSize).appendTo($select);
                    }
                    $select.val(_pageSize);
                    $select.trigger('change');
                } else {
                    ps.val(_ac.pageSize);
                }
            }
        });
        $("<td>" + $.tl('page:PAGE.COMM.DG_PER_PAGE', '每页') + "</td>").append(ps).append($.tl('page:PAGE.COMM.DG_ITEM', '条')).insertBefore($select);
        $select.attr('dg__hidden', 'Y').hide();
    }

    /**
     * bind datagrid分页区域的按钮
     * @param thiz
     * @param opts
     */
    function bindDGPageBtn(thiz, opts, _ac) {
        if (opts.pagination && opts.pageButtons) {
            $(thiz).datagrid('getPager').pagination({
                buttons: opts.pageButtons
            });
        }
    }

    /**
     * 重载DataGrid的loader
     * @param opts
     * @param params
     * @param success
     * @param error
     */
    function loader(opts, params, success, error) {
        if(opts.data){
            success(opts.data);
            return;
        }
        var funcCode = (opts.custom || {}).code || '';
        var url = opts.url || Constant.API_V1_PLUGINS + funcCode;
        var data = $.extend({}, params);
        AjaxUtil.postReq({
            url: url, mask: false,
            data: data, ignoreLoginCheck: opts.ignoreLoginCheck,
            success: function (result) {
                if (result.code != ResultCode.SUCCESS_CODE) {
                    $.messager.alert($.tl('page:PAGE.COMM.ERROR_PROMPT', '错误提示'), result.msg, 'error');
                    return;
                }
                success(result);
            },
            error: function (xhr, errMsg) {
                $.messager.alert($.tl('page:PAGE.COMM.ERROR_PROMPT', '错误提示'), errMsg, 'error');
            }
        })
    }

    /**
     * toolbar 权限检查
     * @param toolbar
     * @returns {Array}
     * @private
     */
    function validToolbar(toolbar) {
        var toolbar_ = toolbar.concat();
        $.each(toolbar_, function (k, item) {
            if (typeof(item) == 'object') {
                var result = true;
                if (item['auth']) {
                    result = __VALID_AUTH(item['auth']);
                }
                if (!result) {
                    toolbar[k] = "REMOVE";
                    if (typeof(toolbar_[k + 1]) == 'string' && toolbar_[k + 1] == '-')
                        toolbar[k + 1] = "REMOVE";
                }
            }
        });
        var tbar = [];
        $.each(toolbar, function (k, d) {
            if (toolbar[k] != 'REMOVE') {
                tbar.push(d);
            }
        });
        return tbar;
    }

})(jQuery);

/**
 * DataGrid 行右键处理
 * @param opts
 * @param thiz
 * @param e
 * @param rowIndex
 * @param rowData
 */
function rowContextMenu(opts, thiz, e, rowIndex, rowData) {
    var _key = GridEdit._getDGEkey($(thiz));
    var storage = GridEdit.GEdit[_key] || [];
    if(storage.length > 0){
        return;
    }
    if (!opts.contextMenus) {
        return;
    }
    if ($(e.target).hasClass('datagrid-body')) {
        return;
    }
    e.preventDefault();
    if (opts.dg_type == 'treegrid') {
        $(thiz).treegrid('select', rowData[opts.idField]);
    } else if (opts.dg_type == 'datagrid') {
        $(thiz).datagrid('selectRow', rowIndex);
    } else if (opts.dg_type == 'tree') {
        $(thiz).tree('select', rowData.target);
    }
    $("#menu_" + opts.identity).menu("destroy");
    var $menu = $("<div id='menu_" + opts.identity + "'/>").menu();
    var validStock = {};
    $.each(opts.contextMenus, function (k, e) {
        validStock[e.text] = {display: true, enable: true};
    });
    if (opts.validAuthContextMenu && typeof(opts.validAuthContextMenu) == 'function') {
        var rowData = opts.dg_type == 'treegrid' ? $(thiz).treegrid('getSelected') : opts.dg_type == 'datagrid' ? $(thiz).datagrid('getSelected') : rowData;
        opts.validAuthContextMenu(rowData, validStock, rowIndex);
    }
    var hasItem = false;
    $.each(opts.contextMenus, function (k, e) {
        var vflag = true;
        if (e.auth && e.auth != "") {
            vflag = __VALID_AUTH(e.auth);
        }
        var display = (validStock[e.text] || {display: true}).display;
        var enable = (validStock[e.text] || {enable: true}).enable;
        if (vflag && display) {
            $menu.menu('appendItem', {
                id: e.id,
                text: e.text,
                iconCls: e.iconCls || '',
                onclick: e.onclick,
                disabled: !enable
            });
            hasItem = true;
        }
    });
    if (hasItem) {
        $menu.menu('show', {
            left: e.pageX,
            top: e.pageY
        });
    }
}

/**
 * 拓展datagrid methods 动态增加/移除 Editor
 */
$.extend($.fn.datagrid.methods, {
    addEditor: function (jq, param) {
        if (param instanceof Array) {
            $.each(param, function (index, item) {
                var e = $(jq).datagrid('getColumnOption', item.field);
                e.editor = item.editor;
            });
        } else {
            var e = $(jq).datagrid('getColumnOption', param.field);
            e.editor = param.editor;
        }
    },
    removeEditor: function (jq, param) {
        if (param instanceof Array) {
            $.each(param, function (index, item) {
                var e = $(jq).datagrid('getColumnOption', item);
                e.editor = {};
            });
        } else {
            var e = $(jq).datagrid('getColumnOption', param);
            e.editor = {};
        }
    }
});

$.extend($.fn.datagrid.defaults.editors, {
    textboxSelect: {
        init: function (container, options) {
            var input = $('<input type="text">').appendTo(container);
            $('<input type="hidden" _textboxSelect="1" _textField="' + options.textField + '" _valueField="' + options.valueField + '">').appendTo(container);
            return EasyUI.initTextboxSelect(input, options);
        },
        destroy: function (target) {
            $(target).textbox('destroy');
        },
        getValue: function (target) {
            return GridEdit.getTextboxSelectValue(target, 'getValue');
            // return $(target).textbox('getValue');
        },
        setValue: function (target, value, row) {
            var $v = target.siblings(":hidden[_textboxSelect]");
            var _textField = $v.attr('_textField');
            var _valueField = $v.attr('_valueField');
            GridEdit.setTextboxSelectValue(target, row[_valueField], row[_textField])
        },
        resize: function (target, width) {
            $(target).textbox('resize', width);
        }
    },
    dateboxSelect: {
        init: function (container, options) {
            var input = $('<input type="text" class="mdate input_mdate">').appendTo(container);
            input.unbind('click').bind('click', function () {
                WdatePicker($.extend({dateFmt:'yyyy-MM-dd'}, options || {}));
            });
            return input;
        },
        destroy: function (target) {

        },
        getValue: function (target) {
            return $(target).val();
        },
        setValue: function (target, value, row) {
            $(target).val(value);
        },
        resize: function (target, width) {
            $(target).css({ width: width });
        }
    },
});

var GridComSearch = {

};

var GridEdit = {

    GEdit: {},

    DataGrid: {
        beginDGEditRow: function ($dg, rowIndex, opts) {
            if (typeof(rowIndex) == 'number' && rowIndex >= 0) {
                $dg.datagrid('beginEdit', rowIndex);
                try{
                    var editors = $dg.datagrid('getEditors', rowIndex);
                    var row = $dg.datagrid('getRows')[rowIndex];
                    GridEdit._setDGValue(editors, row, opts);
                }catch (e){}
                GridEdit._setDGEIndex($dg, rowIndex, 'edit');
            }
        },

        insertDGEditRow: function ($dg, rowData) {
            var options = $dg.datagrid('options');
            if(!options.enableLineEdit){
                return;
            }
            $dg.datagrid('insertRow', {
                index: 0, row: $.extend(rowData, {__lemark: 'add'}) || {}
            });
            var rowIndex = 0;
            //$dg.datagrid('appendRow', $.extend(rowData, {__lemark: 'add'}) || {});
            //var rowIndex = $dg.datagrid('getRows').length - 1;
            $dg.datagrid('beginEdit', rowIndex);

            var _key = GridEdit._getDGEkey($dg);
            var storage = GridEdit.GEdit[_key] || [];
            for (var i = 0; i < storage.length; i++) {
                storage[i].index = storage[i].index + 1;
            }
            GridEdit.GEdit[_key] = storage;

            GridEdit._setDGEIndex($dg, rowIndex, 'add');
        },

        getEditChangeData: function ($dg) {
            var _key = GridEdit._getDGEkey($dg);
            var indexs = GridEdit.GEdit[_key] || [];
            /*for (var i = 0; i < indexs.length; i++) {
             $dg.datagrid('endEdit', indexs[i]);
             }*/
            var chg = [];
            var rows = $dg.datagrid('getRows');
            var options = $dg.datagrid('options');
            for (var i = 0; i < indexs.length; i++) {
                var index = indexs[i].index;
                if(!$dg.datagrid('validateRow', index)){
                    chg = [];
                    WinUtil.MsgError({ content: "验证失败!" });
                    break;
                }

                var row = rows.length <= indexs[i] ? {} : rows[index];
                row['__leindex'] = index;
                var editors = $dg.datagrid('getEditors', index);
                var nrow = GridEdit._getDGValue(editors, row, options);
                chg.push(nrow);
            }
            return chg;
        },

        validChangeData: function ($dg) {
            var _key = GridEdit._getDGEkey($dg);
            var indexs = GridEdit.GEdit[_key] || [];
            var bool = true;
            for (var i = 0; i < indexs.length; i++) {
                var index = indexs[i].index;
                if (!$dg.datagrid('validateRow', index)) {
                    bool = false;
                    break;
                }
            }
            return bool;
        },

        endEditAndSave: function($dg){
            var _key = GridEdit._getDGEkey($dg);
            var indexs = GridEdit.GEdit[_key] || [];
            var bool = true;
            for (var i = 0; i < indexs.length; i++) {
                var index = indexs[i].index;
                if(!$dg.datagrid('validateRow', index)){
                    WinUtil.MsgError({ content: "验证失败!" });
                    bool = false;
                    break;
                }
            }
            if(bool){
                for (var i = 0; i < indexs.length; i++) {
                    var index = indexs[i].index;
                    $dg.datagrid('endEdit', index);
                }
                GridEdit.clearDGEdit($dg);
            }
        },

        endAndClearItemEdit: function ($dg, index) {
            var _key = GridEdit._getDGEkey($dg);
            var indexs = GridEdit.GEdit[_key] || [];
            var rows = $dg.datagrid('getRows');
            for (var i = 0; i < indexs.length; i++) {
                if (index == indexs[i].index) {
                    var editors = $dg.datagrid('getEditors', index);
                    var row = rows.length <= index ? {} : rows[index];
                    var orow = $.extend({}, GridEdit._getDGValue(editors, row, options));
                    delete (orow['__lemark'])
                    $dg.datagrid('endEdit', indexs[i].index);
                    $dg.datagrid('updateRow',{ index: index, row: orow });
                    indexs.splice(i, 1);
                    break;
                }

            }
            GridEdit.GEdit[_key] = indexs;
        },


        endAndClearDGEdit: function ($dg) {
            var _key = GridEdit._getDGEkey($dg);
            var indexs = GridEdit.GEdit[_key] || [];
            var rows = $dg.datagrid('getRows');
            var options = $dg.datagrid('options');
            for (var i = 0; i < indexs.length; i++) {
                var index = indexs[i].index;
                var editors = $dg.datagrid('getEditors', index);
                var row = rows.length <= index ? {} : rows[index];
                var orow = $.extend({}, GridEdit._getDGValue(editors, row, options));
                delete (orow['__lemark'])
                $dg.datagrid('endEdit', indexs[i].index);
                $dg.datagrid('updateRow',{ index: index, row: orow });
            }
            GridEdit.clearDGEdit($dg);
        },


        cancelAndClearDGEdit: function ($dg) {
            var _key = GridEdit._getDGEkey($dg);
            var indexs = GridEdit.GEdit[_key] || [];
            var m = [];
            for (var i = 0; i < indexs.length; i++) {
                $dg.datagrid('cancelEdit', indexs[i].index);
                if (indexs[i].flag == 'add') {
                    m.push(indexs[i].index);
                }
            }
            while (m.length > 0) {
                var m0 = m[0];
                $dg.datagrid('deleteRow', m[0]);
                m.splice(0, 1);
                for (var j = 0; j < m.length; j++) {
                    if (m[j] > m0) {
                        m[j] = m[j] - 1;
                    }
                }
            }
            GridEdit.clearDGEdit($dg);
        },

        cancelDGEdit: function ($dg, index) {
            var _key = GridEdit._getDGEkey($dg);
            var indexs = GridEdit.GEdit[_key] || [];
            var m = null;
            for (var i = 0; i < indexs.length; i++) {
                if(indexs[i].index == index) {
                    $dg.datagrid('cancelEdit', indexs[i].index);
                    if (indexs[i].flag == 'add') {
                        $dg.datagrid('deleteRow', indexs[i].index);
                        m = indexs[i].index;
                    }
                    indexs.splice(i, 1);
                }
            }
            if(m != null){
                for (var i = 0; i < indexs.length; i++) {
                    if(indexs[i].index > m){
                        indexs[i].index = indexs[i].index - 1;
                    }
                }
            }
            GridEdit.GEdit[_key] = indexs;
        },

        switchDGLineEdit: function ($dg, bool) {
            var opts = $dg.datagrid('options');
            opts.enableLineEdit = bool;
        }
    },

    TreeGrid: {
        beginDGEditRow: function ($dg, rowId, opts) {
            if (rowId != undefined && rowId != null && rowId != '') {
                $dg.treegrid('beginEdit', rowId);
                try{
                    var editors = $dg.treegrid('getEditors', rowId);
                    var row = $dg.treegrid('find', nodeId);
                    GridEdit._setDGValue(editors, row, opts)
                }catch (e){}
                GridEdit._setDGEIndex($dg, rowId, 'edit');
            }
        },

        insertDGEditRow: function ($dg, rowData) {
            var options = $dg.treegrid('options');
            var vdata = {};
            vdata[options.idField] = options.idField + (new Date().getTime());
            //rowData = $.extend({[options.idField]: options.idField + (new Date().getTime())}, rowData);
            rowData = $.extend(vdata, rowData);
            $dg.treegrid('append', {
                data: {edit: true, data: $.extend(rowData, {__lemark: 'add'})}
            });
            var rowId = rowData[options.idField];
            $dg.treegrid('beginEdit', rowId);
            GridEdit._setDGEIndex($dg, rowId, 'add');
        },

        getEditChangeData: function ($dg) {
            var _key = GridEdit._getDGEkey($dg);
            var indexs = GridEdit.GEdit[_key] || [];
            var options = $dg.treegrid('options');
            /*for (var i = 0; i < indexs.length; i++) {
             $dg.treegrid('endEdit', indexs[i]);
             }*/
            var chg = [];
            for (var i = 0; i < indexs.length; i++) {
                var index = indexs[i].index;
                var row_ = $dg.treegrid('find', index);
                var row = $.extend(true, {}, row_);
                row[('_' + options.idField)] = row[options.idField];
                var editors = $dg.treegrid('getEditors', index);
                row = GridEdit._getDGValue(editors, row, options);
                chg.push(row);
            }
            return chg;
        },

        endAndClearDGEdit: function ($dg) {
            var _key = GridEdit._getDGEkey($dg);
            var indexs = GridEdit.GEdit[_key] || [];
            var options = $dg.treegrid('options');
            var changeData = GridEdit.TreeGrid.getEditChangeData($dg);
            var kvmap = {};
            if (changeData) {
                for (var i = 0; i < changeData.length; i++) {
                    var v = changeData[i][options.idField];
                    var k = changeData[i]['_' + options.idField];
                    kvmap[k] = v;
                }
            }
            for (var i = 0; i < indexs.length; i++) {
                var index = indexs[i].index;
                $dg.treegrid('endEdit', kvmap[index] ? kvmap[index] : index);
            }
            GridEdit.clearDGEdit($dg);
        },

        cancelAndClearDGEdit: function ($dg) {
            var _key = GridEdit._getDGEkey($dg);
            var indexs = GridEdit.GEdit[_key] || [];
            for (var i = 0; i < indexs.length; i++) {
                var index = indexs[i].index;
                $dg.treegrid('cancelEdit', index);
                if (indexs[i].flag == 'add') {
                    $dg.treegrid('remove', index);
                }
            }
            GridEdit.clearDGEdit($dg);
        },

        cancelDGEdit: function ($dg, index) {
            var _key = GridEdit._getDGEkey($dg);
            var indexs = GridEdit.GEdit[_key] || [];
            for (var i = 0; i < indexs.length; i++) {
                var index_ = indexs[i].index;
                if(index_ == index){
                    $dg.treegrid('cancelEdit', index);
                    if (indexs[i].flag == 'add') {
                        $dg.treegrid('remove', index);
                    }
                    indexs.splice(i, 1);
                }
            }
            GridEdit.GEdit[_key] = indexs;
        }
    },

    clearDGEdit: function ($dg) {
        var _key = GridEdit._getDGEkey($dg);
        GridEdit.GEdit[_key] = [];
    },

    existDGEditIndex: function ($dg, index) {
        var _key = GridEdit._getDGEkey($dg);
        var indexs = GridEdit.GEdit[_key] || [];
        for (var i = 0; i < indexs.length; i++) {
            var index_ = indexs[i].index;
            if (index_ == index) {
                return true;
            }
        }
        return false;
        //return $.inArray(index, storage) > -1;
    },

    _getDGEkey: function ($dg) {
        var editkey = $dg.data('editkey');
        if (!editkey) {
            editkey = "editkey_" + new Date().getTime();
            $dg.data('editkey', editkey);
        }
        return editkey;
    },

    _setDGEIndex: function ($dg, rowIndex, flag) {
        var _key = GridEdit._getDGEkey($dg);
        var storage = GridEdit.GEdit[_key] || [];
        if ($.inArray(rowIndex, storage) == -1) {
            storage.push({index: rowIndex, flag: flag});
            GridEdit.GEdit[_key] = storage;
        }
    },

    _getDGValue: function (editors, row, opts) {
        var nrow = {};
        $.extend(nrow, row);
        for (var j = 0; j < editors.length; j++) {
            var vfield = editors[j].field; var tfield = "";
            var endEditGetValue;
            var vGetValueName;
            try{
                vfield = opts.custom.alter[editors[j].field].targetField;
                endEditGetValue = opts.custom.alter[editors[j].field].endEditGetValue;
                vGetValueName = opts.custom.alter[editors[j].field].vGetValueName;
                tfield = editors[j].field;
            }catch (e){
                vfield = editors[j].field; tfield = "";
            }
            vfield = vfield ? vfield : editors[j].field;
            if (editors[j].type == 'text') {
                nrow[vfield] = $(editors[j].target).val();

            } else if (editors[j].type == 'textbox') {
                nrow[vfield] = $(editors[j].target).textbox('getValue');
            } else if (editors[j].type == 'checkbox') {
                var $checkbox = $(editors[j].target);
                if ($checkbox.is(":checked")) {
                    nrow[vfield] = $checkbox.val();
                } else {
                    nrow[vfield] = $checkbox.attr("offval") || '';
                }
            } else if (editors[j].type == 'combobox') {
                if(tfield){
                    nrow[tfield] = $(editors[j].target).combobox('getText');
                }
                nrow[vfield] = $(editors[j].target).combobox('getValue');
            } else if (editors[j].type == 'numberbox') {
                nrow[vfield] = $(editors[j].target).numberbox('getValue');
            } else if (editors[j].type == 'datebox') {
                nrow[vfield] = $(editors[j].target).datebox('getValue');
            } else if (editors[j].type == 'datetimebox') {
                nrow[vfield] = $(editors[j].target).datebox('getValue');
            } else if (editors[j].type == 'combotree') {
                if(tfield){
                    nrow[tfield] = $(editors[j].target).combotree('getText');
                }
                if(vGetValueName && $.isFunction(vGetValueName)){
                    nrow[vfield] = vGetValueName(editors[j].target, row);
                }else{
                    nrow[vfield] = $(editors[j].target).combotree('getValue');
                }
            } else if (editors[j].type == 'combogrid') {
                if(tfield){
                    nrow[tfield] = $(editors[j].target).combogrid('getText');
                }
                nrow[vfield] = $(editors[j].target).combogrid('getValue');
            } /*else if (editors[j].type == 'combotreegrid') {
                row[vfield] = $(editors[j].target).combotreegrid('getValue');
                if(tfield){
                    row[tfield] = $(editors[j].target).combotreegrid('getText');
                }
            }*/ else if (editors[j].type == 'textboxSelect') {
                nrow[vfield] = $(editors[j].target).siblings(":hidden[_textboxSelect]").val();
            } else if(editors[j].type == 'wdatepicker'){
                nrow[vfield] = $(editors[j].target).val();
            }else if (editors[j].type == 'dateboxSelect') {
                nrow[vfield] = $(editors[j].target).val();
            }else{
                alert("未知的Editor type:" + editors[j].type);
                break;
            }
            if(endEditGetValue && $.isFunction(endEditGetValue)){
                nrow[vfield] = endEditGetValue(editors[j].target, row);
            }
        }
        return nrow;
    },

    _setDGValue: function (editors, row, opts) {
        for (var j = 0; j < editors.length; j++) {
            var targetField = editors[j].field;
            var setValueFunc = null;
            try{
                targetField = opts.custom.alter[editors[j].field].targetField;
                setValueFunc = opts.custom.alter[editors[j].field].beginEditSetValue;
            }catch(e){}
            if(!targetField){
                continue;
            }
            if(setValueFunc && $.isFunction(setValueFunc)){
                setValueFunc(editors[j].target, row[targetField], row);
            }else {
                if (editors[j].type == 'text') {
                    $(editors[j].target).val(row[targetField]);
                } else if (editors[j].type == 'textbox') {
                    $(editors[j].target).textbox('setValue', row[targetField]);
                } else if (editors[j].type == 'checkbox') {
                    var $checkbox = $(editors[j].target);
                    $.each($checkbox, function () {
                        if (row[targetField] == $(this).val()) {
                            $checkbox.attr("checked", "checked");
                        }
                    });
                } else if (editors[j].type == 'combobox') {
                    $(editors[j].target).combobox('setValue', row[targetField]);
                } else if (editors[j].type == 'numberbox') {
                    $(editors[j].target).numberbox('setValue', row[targetField]);
                } else if (editors[j].type == 'datebox') {
                    $(editors[j].target).datebox('setValue', row[targetField]);
                } else if (editors[j].type == 'datetimebox') {
                    $(editors[j].target).datebox('setValue', row[targetField]);
                } else if (editors[j].type == 'combotree') {
                    $(editors[j].target).combotree('setValue', row[targetField]);
                } else if (editors[j].type == 'combogrid') {
                    $(editors[j].target).combogrid('setValue', row[targetField]);
                } /*else if (editors[j].type == 'combotreegrid') {
                row[vfield] = $(editors[j].target).combotreegrid('getValue');
                if(tfield){
                    row[tfield] = $(editors[j].target).combotreegrid('getText');
                }
            }*/ else if (editors[j].type == 'textboxSelect') {
                    $(editors[j].target).siblings(":hidden[_textboxSelect]").val(row[targetField]);
                } else if (editors[j].type == 'wdatepicker') {
                    $(editors[j].target).val(row[targetField]);
                } else if (editors[j].type == 'dateboxSelect') {
                    $(editors[j].target).val(row[targetField]);
                } else {
                    alert("未知的Editor type:" + editors[j].type);
                    break;
                }
            }
        }
    },

    /* 行编辑 options*/
    editorTextboxSelect: function (opts) {
        if (!opts.textField || !opts.valueField) {
            alert('editorTextboxSelect 必须指定textField，valueField');
            return;
        }
        return {
            type: 'textboxSelect',
            options: {
                height: 26, editable: true,
                iconWidth: 23, iconAlign: 'right',
                textField: opts.textField, valueField: opts.valueField,
                icons: [{
                    iconCls: opts.iconCls || 'icon-search',
                    handler: function (e) {
                        if (typeof(opts.handler) == 'function') {
                            opts.handler($(e.data.target));
                        }
                    }
                },
                    {
                        iconCls: 'icon-clear',
                        handler: function (e) {
                            if (typeof(opts.clearHandler) == 'function') {
                                opts.clearHandler($(e.data.target));
                            }
                        }
                    }]
            }
        };
    },

    setTextboxSelectValue: function ($target, value, text) {
        $target.textbox({value: text});
        $target.siblings(":hidden[_textboxSelect]").val(value);
    },

    getTextboxSelectValue: function ($target, get) {
        get = get || 'getText';
        if ('getText' == get) {
            return $target.textbox('getValue');
        }
        if ('getValue' == get) {
            return $target.siblings(":hidden[_textboxSelect]").val();
        }
        return '';
    },

    getTargetRowId: function ($target) {
        var rowId = $target.parents("tr[datagrid-row-index]").attr("datagrid-row-index");
        return rowId ? parseInt(rowId) : -1;
    }
};


var GridUtil = {

    dgExportExcel: function ($dg, fileName, ifPage, resultCode, paramsExtend) {
        var options = $dg.datagrid('options');
        // 判断是否超过导出数量
        var size = 0;
        // 获取导出数据量
        if (options.pagination){
            var pagination = $dg.datagrid("getPager").data("pagination").options;
            size = pagination.total;
        } else {
            size = $dg.datagrid("getRows").length;
        }
        // 判断是否超过允许导出量
        if (FinalValue.MAX_EXPORT_SIZE < size || size == 0){
            WinUtil.MsgError({content: $.tl('page:PAGE.COMM.ERROR_EXCEL_EXPORT_OVER', '导出Excel无条目或数据量超出限制'+FinalValue.MAX_EXPORT_SIZE+'，请增加筛选条件！')});
            return;
        }
        var columnList = [];
        $.each(options.columns[options.columns.length - 1], function (k, v) {
            if(!v.ignoreExport){
                var _title = $('<span/>').html(v.title).text();
                columnList.push({field: v.field, title: _title});
            }
        });
        var params = options.queryParams;
        var dataUrl = "";
        if (options.custom == undefined || ToolUtil.isArray(options.custom)) {
            if (!options.url) {
                return;
            }
            dataUrl = options.url;
        } else {
            if (!options.custom.code) {
                return;
            }
            dataUrl = Constant.API_V1_PLUGINS + options.custom.code;
        }
        FuncUtil.doIframePost(Constant.API_V1_PLUGINS + 'EXCEL_EXPORT', $.extend({
            url: dataUrl,
            fileName: fileName,
            resultCode: resultCode ? resultCode : '',
            page: ifPage ? (options.pageNumber ? options.pageNumber : 1) : 1, //options.pageNumber,
            rows: ifPage ? (options.pageSize ? options.pageSize : 2147483647) : 2147483647, //options.pageSize,
            columnList: FuncUtil.encodelg_(JSON.stringify(columnList)),
            pagination: options.pagination
        }, params, paramsExtend ? paramsExtend : {}));
    },
    dgExportExcel_two: function ($dg, fileName,data, ifPage, resultCode) {
        var options = $dg.datagrid('options');
        // 判断是否超过导出数量
        var size = 0;
        // 获取导出数据量
        if (options.pagination){
            var pagination = $dg.datagrid("getPager").data("pagination").options;
            size = pagination.total;
        } else {
            size = $dg.datagrid("getRows").length;
        }
        // 判断是否超过允许导出量
        if (FinalValue.MAX_EXPORT_SIZE < size){
            WinUtil.MsgError({content: $.tl('page:PAGE.COMM.ERROR_EXCEL_EXPORT_OVER', '导出Excel数据量超出限制，请增加筛选条件！')});
            return;
        }
        var columnList = [];
        $.each(options.columns[options.columns.length - 1], function (k, v) {
            if(!v.ignoreExport){
                var _title = $('<span/>').html(v.title).text();
                columnList.push({field: v.field, title: _title});
            }
        });
        var params = options.queryParams;
        var dataUrl = "";
        if (options.custom == undefined || ToolUtil.isArray(options.custom)) {
            if (!options.url) {
                return;
            }
            dataUrl = options.url;
        } else {
            if (!options.custom.code) {
                return;
            }
            dataUrl = Constant.API_V1_PLUGINS + options.custom.code;
        }
        FuncUtil.doIframePost(Constant.API_V1_PLUGINS + 'EXCEL_EXPORT_TWO', $.extend(data.serializeObject(),{
            url: dataUrl,
            fileName: fileName,
            resultCode: resultCode ? resultCode : '',
            page: ifPage ? (options.pageNumber ? options.pageNumber : 1) : 1, //options.pageNumber,
            rows: ifPage ? (options.pageSize ? options.pageSize : 2147483647) : 2147483647, //options.pageSize,
            columnList: FuncUtil.encodelg_(JSON.stringify(columnList)),
            pagination: options.pagination,
        }, params));
    },
    dgExportExcelByFuncode: function (funcode, params, fileName, pagination, showNo, continuousNo, resultCode, ifPage, pageNumber, pageSize) {
        // funcode为空，则直接返回
        if (!funcode) {
            return;
        }
        // 根据funcode获取列表表头
        AjaxUtil.postReq({
            url: Constant.API_V1_PLUGINS + ICFuncCode.IC_DG_COLS,
            data: {dgCode: funcode},
            success: function (result) {
                if (!FuncUtil.checkResult(result)) {
                    return;
                }
                // 获取列表
                var _columns = result.data[ICFuncCode.IC_DG_COLS];
                $.each(_columns, function (k, list) {
                    $.each(list, function (i, e) {
                        var x = {};
                        try {
                            e.extraItem ? eval('x =' + e.extraItem) : {};
                        } catch (ex) {
                        }
                        e = $.extend(e, {halign: 'center' }, {extraItem: ''}, x);
                        e.title = $.tls('grid:GRID.' + 'O' + e.operId + '.' + funcode + '.' + e.field, e.title);
                    });
                });
                var columns = _columns || [[]];
                // 拼接导出Excel表头
                var columnList = [];
                $.each(columns[columns.length - 1], function (k, v) {
                    if(!v.checkbox){
                        var _title = $('<span/>').html(v.title).text();
                        columnList.push({field: v.field, title: _title});
                    }
                });
                // 导出Excel
                FuncUtil.doIframePost(Constant.API_V1_PLUGINS + 'EXCEL_EXPORT', $.extend({
                    url: Constant.API_V1_PLUGINS + funcode,
                    fileName: fileName,
                    resultCode: resultCode ? resultCode : '',
                    page: ifPage && pageNumber ? pageNumber : 1, //options.pageNumber,
                    rows: ifPage && pageSize ? pageSize : 2147483647, //options.pageSize,
                    columnList: FuncUtil.encodelg_(JSON.stringify(columnList)),
                    pagination: pagination ? true : false,
                    showNo: showNo ? true : false,
                    continuousNo: continuousNo ? true : false
                }, params ? params : {}));
            }
        });
    },

    onSearch: function (opts) {
        var evt = FuncUtil.getEvent(GridUtil.onSearch);
        var $form = (opts.formTarget ? $(opts.formTarget) : $(FuncUtil.getEventTarget(evt)).parents('form'));
        var data = $form.serializeObject();
        var b = true;
        if(opts.beforeSearch){
            b = opts.beforeSearch(data);
            b = b == false ? false : true;
        }
        if(!b)return;
        var options = $(opts.target).datagrid('options');
        if (options.dg_type == 'treegrid') {
            var options = $(opts.target).treegrid('options');
            $(opts.target).treegrid('load', $.extend(options.queryParams, data));
            return;
        }
        $(opts.target).datagrid('load', $.extend(options.queryParams, data));
    },

    onReset: function (opts) {
        var evt = FuncUtil.getEvent(GridUtil.onReset);
        var $form = (opts.formTarget ? $(opts.formTarget) : $(FuncUtil.getEventTarget(evt)).parents('form'));
        if($form.length == 0){
            return;
        }
        $form.get(0).reset();
        $form.find('input:hidden[resetValue]').each(function () {
            var rv = $(this).attr('resetValue');
            $(this).val(rv);
        });
        $('*[textboxname]').each(function () {
            // var attr = $(this).attr('textboxname');
            if($(this).hasClass('combotree-f')){
                $(this).combotree('clear');
            }else if($(this).hasClass('combobox-f')){
                $(this).combobox('clear');
            }else if($(this).hasClass('combogrid-f')){
                $(this).combogrid('clear');
            }else if($(this).hasClass('datebox-f')){
                $(this).datebox('clear');
            } else if($(this).hasClass('easyui-textbox')){
                $(this).textbox('setValue','');
            }
        });
        opts = $.extend({reload: true}, opts);
        if(opts.reload){
            var options = $(opts.target).datagrid('options');
            var data = $form.serializeObject();
            //序列化无法获取单选“input:radio”的值，增加获取radio的值
            try {
                var radios = $('input:radio');
                if (radios!=undefined && radios!=null && radios.length > 0){
                    for (var index =0;index < radios.length;index ++){
                        var item = $(radios[index]).attr('name');
                        data[item] = "";
                    }
                }
            }catch (e) {
                console.log(e.toString())
            }
            if (options.dg_type == 'treegrid') {
                var options = $(opts.target).treegrid('options');
                $(opts.target).treegrid('load', $.extend(options.queryParams, data));
                return;
            }
            $(opts.target).datagrid('load', $.extend(options.queryParams, data));
        }
    },

    dgComSearch: function ($dg, optcols) {
        var options = $dg.datagrid('options');
        if (options.dg_type == 'treegrid') {
            options = $dg.treegrid('options');
        }else{
            options = $dg.datagrid('options');
        }
        var columnList = [];
        $.each(options.columns[options.columns.length - 1], function (k, v) {
            var optcol = {field: v.field, title: v.title};
            if(optcols && optcols[v.field]){
                optcol = $.extend(optcols[v.field], {field: v.field, title: v.title});
            }
            optcol.title = $('<span/>').html(optcol.title).text();
            columnList.push(optcol);
        });
        WinUtil.showDialog({
            title : '通用查询',
            width: 900, height : 420,
            param: {columnList: columnList, lastFlagQData: $dg.data('LAST_FLAGQ_DATA') || '',  callback: function (qy, qs, orgData) {
                var flagQ = {qy: qy, qs: qs};
                $dg.data('LAST_FLAGQ_DATA', orgData);
                var queryParams = $.extend(options.queryParams_||{}, { __flagQ: 'Y', flagQ: FuncUtil.encodeCustom(JSON.stringify(flagQ)) });
                if (options.dg_type == 'treegrid') {
                    $dg.treegrid('load', queryParams);
                }else{
                    $dg.datagrid('load', queryParams);
                }
            } },
            url: Constant.VIEWS_IC + "/comm/ic_com_search.shtml"
        });
    }
};

/* 列表附件 窗口 */
function RenderGridAttach(_attach, rowData, rowIndex, config, _dg, isDel) {
    var htm = "<a href='javascript:void(0);' class='_attach'><img src=\"/views/img/edit2.png\" border=\"0\" style=\"width:15px;height:15px;\"/>";
    var content = '<div style="display: none;"><ul>';

    var catStorage = {};
    $.each(_attach, function (k, item) {
        var dcon = catStorage[item.category];
        if(!dcon){
            dcon = catStorage[item.category] = [];
        }
        var h = '<li>&nbsp;&nbsp;<span style="color: blue; cursor: pointer; text-decoration: underline;"' +
            ' onclick="_DownGridAttach(\''+item.pkid+'\',\''+item.orgName+'\')">' + item.orgName + '</span>';
        if(isDel){
            h += '&nbsp;<span style="color: blue; cursor: pointer; text-decoration: underline;" onclick="_DeleteGridAttach(\''+item.pkid+'\',this)">删除</span>';
        }
        h += '</li>';
        dcon.push(h);
    });
    $.each(catStorage, function (key, attachs) {
        var cat = config[key] || '附件';
        content += '<li><b>'+cat+':</b></li>';
        $.each(attachs, function (k, item) {
            content += item;
        });
    });
    content += '</ul></div>';

    htm += content + "</a>";
    _dg.data("RenderGridAttach", true);
    return htm;
}

function _BindGridAttach() {
    //$('div[id^=qtip]').remove();
    $('._attach').each(function () {
        $(this).find("img").qtip({
            show: 'click', hide: {
                event: 'click, unfocus'
            },
            content: {
                text: $(this).find("div").html(), title: '附件',
                button: true
            },
            position:{
                my: 'center left',
                at: 'center right'
            },
            style: {
                classes: 'qtip-light qtip-shadow'
            }
        });
    });
}

function _DownGridAttach(pkid, fileName) {
    FuncUtil.doIframePost(Constant.API_V1_PLUGINS + "/IC_ATTACH_DOWN", {attachId: pkid, fileName: fileName });
}
function _DownGridAttachBySource(category, sourceId) {
    FuncUtil.doIframePost(Constant.API_V1_PLUGINS + "/IC_ATTACH_DOWN_BY_SOURCE", {category: category, sourceId: sourceId });
}

function _DeleteGridAttach(pkid, thiz) {
    $.messager.confirm($.tl('page:PAGE.COMM.CONFIRM_TITLE', '确认窗口'),
        $.tl('page:PAGE.COM.CONFIRM_DEL_CONTENT_MULTI', '你确定要删除选中项吗?'), function (r) {
            if (r) {
                AjaxUtil.postReq({
                    url: Constant.API_V1_PLUGINS + "/IC_ATTACH_DEL",
                    data: {attachId: pkid},
                    success: function (result) {
                        if (!FuncUtil.checkResult(result)) {
                            return;
                        }
                        $(thiz).parent().remove();
                        WinUtil.MsgInfo({content: result.msg});
                    }
                });
            }
        }
    );
}

//top: event.clientY + "px"
//left: event.clientX + "px"