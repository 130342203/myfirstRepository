(function($) {

    /**
     * @param options
     * @constructor
     */
    $.fn.MyComboGrid = function(options) {
        var params = options.params || {};
        var opts = $.extend({}, {
            panelWidth: options.panelWidth||380,
            width: 180, height: 26,
            mode:'remote',
            queryParams : params,
            fitColumns:true,
            pagination:true,
            idField:'PKID',
            textField:'TEXT',
            url : Constant.API_V1_PLUGINS + params.code
        }, options);
        $(this).combogrid(opts);
    };

})(jQuery);

/**
 * 扩展datagrid 两个tip方法
 */
$.extend($.fn.datagrid.methods, {
    /**
     * 开打提示功能
     * @param {} jq
     * @param {} params 提示消息框的样式
     * @return {}
     */
    doCellTip: function(jq, params){
        function showTip(data, td, e){
            if ($(td).text() == "")
                return;
            data.tooltip.text($(td).text()).css({
                top: (e.pageY + 10) + 'px',
                left: (e.pageX + 20) + 'px',
                'z-index': $.fn.window.defaults.zIndex,
                display: 'block'
            });
        };
        return jq.each(function(){
            var grid = $(this);
            var options = $(this).data('datagrid');
            if (!options.tooltip) {
                var panel = grid.datagrid('getPanel').panel('panel');
                var defaultCls = {
                    'border': '1px solid #333',
                    'padding': '2px',
                    'color': '#333',
                    'background': '#f7f5d1',
                    'position': 'absolute',
                    'word-wrap': 'break-word',
                    'max-width': '200px',
                    'border-radius' : '4px',
                    '-moz-border-radius' : '4px',
                    '-webkit-border-radius' : '4px',
                    'display': 'none'
                }
                var tooltip = $("<div id='celltip'></div>").appendTo('body');
                tooltip.css($.extend({}, defaultCls, params.cls));
                options.tooltip = tooltip;
                panel.find('.datagrid-header').each(function(){
                    var delegateEle = $(this).find('> div.datagrid-header-inner').length ? $(this).find('> div.datagrid-header-inner')[0] : this;
                    $(delegateEle).undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove').delegate('td', {
                        'mouseover': function(e){
                            var that = this;
                            if($(that).find("*[_notip]").length != 0){
                                return;
                            }
                            if (params.delay) {
                                if (options.tipDelayTime)
                                    clearTimeout(options.tipDelayTime);
                                options.tipDelayTime = setTimeout(function(){
                                    showTip(options, that, e);
                                }, params.delay);
                            }
                            else {
                                showTip(options, this, e);
                            }

                        },
                        'mouseout': function(e){
                            var that = this;
                            if($(that).find("*[_notip]").length != 0){
                                return;
                            }
                            if (options.tipDelayTime)
                                clearTimeout(options.tipDelayTime);
                            options.tooltip.css({
                                'display': 'none'
                            });
                        },
                        'mousemove': function(e){
                            var that = this;
                            if($(that).find("*[_notip]").length != 0){
                                return;
                            }
                            if (options.tipDelayTime)
                                clearTimeout(options.tipDelayTime);
                            //showTip(options, this, e);
                            options.tipDelayTime = setTimeout(function(){
                                showTip(options, that, e);
                            }, params.delay);
                        }
                    });
                });
                var bodyObj = panel.find('.datagrid-body');
                $(bodyObj).on('mouseover', 'td', function(e){
                    if($(this).find('table.datagrid-btable').length > 0) return;
                    if($(this).find('a._attach').length > 0) return;
                    if (params.delay) {
                        if (options.tipDelayTime)
                            clearTimeout(options.tipDelayTime);
                        var that = this;
                        options.tipDelayTime = setTimeout(function(){
                            showTip(options, that, e);
                        }, params.delay);
                    }
                    else {
                        showTip(options, this, e);
                    }

                }).on('mouseout','td', function(e){
                    if($(this).find('table.datagrid-btable').length > 0) return;
                    if($(this).find('a._attach').length > 0) return;
                    if (options.tipDelayTime)
                        clearTimeout(options.tipDelayTime);
                    options.tooltip.css({
                        'display': 'none'
                    });
                }).on('mousemove','td', function(e){
                    if($(this).find('table.datagrid-btable').length > 0) return;
                    if($(this).find('a._attach').length > 0) return;
                    var that = this;
                    if (options.tipDelayTime)
                        clearTimeout(options.tipDelayTime);
                    //showTip(options, this, e);
                    options.tipDelayTime = setTimeout(function(){
                        showTip(options, that, e);
                    }, params.delay);
                })
            }

        });
    },

    /**
     * 关闭消息提示功能
     *
     * @param {}
     *            jq
     * @return {}
     */
    cancelCellTip: function(jq){
        return jq.each(function(){
            var data = $(this).data('datagrid');
            if (data.tooltip) {
                data.tooltip.remove();
                data.tooltip = null;
                var panel = $(this).datagrid('getPanel').panel('panel');
                panel.find('.datagrid-body').undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove')
            }
            if (data.tipDelayTime) {
                clearTimeout(data.tipDelayTime);
                data.tipDelayTime = null;
            }
        });
    }
});

/**
 * 扩展treegrid 两个tip方法
 */
$.extend($.fn.treegrid.methods, {
    /**
     * 开打提示功能
     * @param {} jq
     * @param {} params 提示消息框的样式
     * @return {}
     */
    doCellTip: function(jq, params){
        function showTip(data, td, e){
            if ($(td).text() == "")
                return;
            data.tooltip.text($(td).text()).css({
                top: (e.pageY + 10) + 'px',
                left: (e.pageX + 20) + 'px',
                'z-index': $.fn.window.defaults.zIndex,
                display: 'block'
            });
        };
        return jq.each(function(){
            var grid = $(this);
            var options = $(this).data('treegrid');
            if (!options.tooltip) {
                var panel = grid.treegrid('getPanel').panel('panel');
                var defaultCls = {
                    'border': '1px solid #333',
                    'padding': '2px',
                    'color': '#333',
                    'background': '#f7f5d1',
                    'position': 'absolute',
                    'word-wrap': 'break-word',
                    'max-width': '200px',
                    'border-radius' : '4px',
                    '-moz-border-radius' : '4px',
                    '-webkit-border-radius' : '4px',
                    'display': 'none'
                }
                var tooltip = $("<div id='celltip'></div>").appendTo('body');
                tooltip.css($.extend({}, defaultCls, params.cls));
                options.tooltip = tooltip;
                panel.find('.datagrid-header').each(function(){
                    var delegateEle = $(this).find('> div.datagrid-header-inner').length ? $(this).find('> div.datagrid-header-inner')[0] : this;
                    $(delegateEle).undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove').delegate('td', {
                        'mouseover': function(e){
                            if (params.delay) {
                                if (options.tipDelayTime)
                                    clearTimeout(options.tipDelayTime);
                                var that = this;
                                options.tipDelayTime = setTimeout(function(){
                                    showTip(options, that, e);
                                }, params.delay);
                            }
                            else {
                                showTip(options, this, e);
                            }

                        },
                        'mouseout': function(e){
                            if (options.tipDelayTime)
                                clearTimeout(options.tipDelayTime);
                            options.tooltip.css({
                                'display': 'none'
                            });
                        },
                        'mousemove': function(e){
                            var that = this;
                            if (options.tipDelayTime)
                                clearTimeout(options.tipDelayTime);
                            //showTip(options, this, e);
                            options.tipDelayTime = setTimeout(function(){
                                showTip(options, that, e);
                            }, params.delay);
                        }
                    });
                });
                var bodyObj = panel.find('.datagrid-body');
                $(bodyObj).on('mouseover', 'td', function(e){
                    if($(this).find('table.datagrid-btable').length > 0) return;
                    if (params.delay) {
                        if (options.tipDelayTime)
                            clearTimeout(options.tipDelayTime);
                        var that = this;
                        options.tipDelayTime = setTimeout(function(){
                            showTip(options, that, e);
                        }, params.delay);
                    }
                    else {
                        showTip(options, this, e);
                    }

                }).on('mouseout','td', function(e){
                    if($(this).find('table.datagrid-btable').length > 0) return;
                    if (options.tipDelayTime)
                        clearTimeout(options.tipDelayTime);
                    options.tooltip.css({
                        'display': 'none'
                    });
                }).on('mousemove','td', function(e){
                    if($(this).find('table.datagrid-btable').length > 0) return;
                    var that = this;
                    if (options.tipDelayTime)
                        clearTimeout(options.tipDelayTime);
                    //showTip(options, this, e);
                    options.tipDelayTime = setTimeout(function(){
                        showTip(options, that, e);
                    }, params.delay);
                })
            }

        });
    },

    /**
     * 关闭消息提示功能
     *
     * @param {}
     *            jq
     * @return {}
     */
    cancelCellTip: function(jq){
        return jq.each(function(){
            var data = $(this).data('datagrid');
            if (data.tooltip) {
                data.tooltip.remove();
                data.tooltip = null;
                var panel = $(this).datagrid('getPanel').panel('panel');
                panel.find('.datagrid-body').undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove')
            }
            if (data.tipDelayTime) {
                clearTimeout(data.tipDelayTime);
                data.tipDelayTime = null;
            }
        });
    }
});

// $.extend($.fn.datagrid.defaults.editors, {
//     wdatepicker: {
//         init: function(container, options){
//             alert(container);
//             console.log(options);
//             var input = $('<input type="text">').appendTo(container);
//             return input;
//         },
//         destroy: function(target){
//             $(target).numberspinner('destroy');
//         },
//         getValue: function(target){
//             alert($(target).wdatepicker('getValue'));
//             return $(target).wdatepicker('getValue');
//         },
//         setValue: function(target, value){
//             $(target).numberspinner('setValue',value);
//         },
//         resize: function(target, width){
//             $(target).numberspinner('resize',width);
//         }
//     }
// });

/*
* 扩展easyui-tree
* */

$.extend($.fn.tree.methods, {
    /**
     * 扩展easyui tree的搜索方法
     * @param tree easyui tree的根DOM节点(UL节点)的jQuery对象
     * @param searchText 检索的文本
     * @param this-context easyui tree的tree对象
     * @param nodeId 节点pkid
     * @param nodeParentId 节点的父节点pkid
     */
    search: function (jqTree, params) {
        //easyui tree的tree对象。可以通过tree.methodName(jqTree)方式调用easyui tree的方法
        var searchText = params['searchText'];
        var nodeId =params['nodeId'];
        var nodeParentId = params['nodePId'];
        var tree = this;

        //获取所有的树节点
        var nodeList = getAllNodes(jqTree, tree);

        //如果没有搜索条件，则展示所有树节点
        searchText = $.trim(searchText);
        if (searchText == "") {
            for (var i = 0; i < nodeList.length; i++) {
                $(".tree-node-targeted", nodeList[i].target).removeClass("tree-node-targeted");
                $(nodeList[i].target).show();
            }
            //展开已选择的节点（如果之前选择了）
            var selectedNode = tree.getSelected(jqTree);
            tree.collapseAll(jqTree);
            if (selectedNode) {
                tree.expandTo(jqTree, selectedNode.target);
            }
            return;
        }

        //搜索匹配的节点并高亮显示
        var matchedNodeList = [];
        if (nodeList && nodeList.length > 0) {
            var node = null;
            for (var i = 0; i < nodeList.length; i++) {
                node = nodeList[i];
                if (matchedNodeList.indexOf(node) == -1 && isMatch(searchText,node.text)){
                    matchedNodeList.push(node);
                }
            }
        }
        //根据一匹配找到所有子节点
        var matchNodesCp = matchedNodeList;
        var matchNodes_childrens =[];
        if (nodeList && nodeList.length > 0) {
            var node = null;
            for (var i =0;i<matchNodesCp.length;i++){
                var childrens = tree.getChildren(jqTree,matchNodesCp[i].target);
                if (matchNodes_childrens.indexOf(childrens)==-1 && childrens.length != 0){
                    matchNodes_childrens.push(childrens);
                }
            }
        }

        //根据已经匹配到的节点找到其所有的父节点
        if (nodeList && nodeList.length > 0) {
            var node = null;
            for (var i =0;i<matchedNodeList.length;i++){
                node = matchedNodeList[i];
                if (node ==null){
                    continue;
                }
                // node = findFatherNode(node,nodeList,nodeId,nodeParentId);
                node = tree.getParent(jqTree,node);
                if (node !=null && matchedNodeList.indexOf(node)==-1){
                    matchedNodeList.push(node)
                }
                while (node !=null){
                    // node = findFatherNode(node,nodeList,nodeId,nodeParentId);
                    node = tree.getParent(jqTree,node);
                    if (node !=null && matchedNodeList.indexOf(node)==-1){
                        matchedNodeList.push(node)
                    }
                }
            }
        }
            //隐藏所有节点
            for (var i = 0; i < nodeList.length; i++) {
                $(".tree-node-targeted", nodeList[i].target).removeClass("tree-node-targeted");
                $(nodeList[i].target).hide();
            }
            //展示所有匹配的节点以及父节点
            for (var i = 0; i < matchedNodeList.length; i++) {
                showMatchedNode(jqTree, tree, matchedNodeList[i],searchText);
            }
            for (var i=0;i<matchNodes_childrens.length;i++){
                var childrens = matchNodes_childrens[i];
                for (var index in childrens){
                    var temp = childrens[index];
                    $(temp.target).show();
                    $(".tree-title", temp.target).addClass("tree-node-targeted");
                    // tree.expandTo(jqTree, temp.target);
                }
                if (childrens.length>0){
                    var parent_node = tree.getParent(jqTree,childrens[0].target);
                    if (childrens.length >0 && tree.getChildren(jqTree,parent_node.target).length>5){
                        //若该节点平级的超过10个，则折叠该级
                        tree.collapse(jqTree,parent_node.target);
                    }
                }

            }
    },

    /**
     * 展示节点的子节点（子节点有可能在搜索的过程中被隐藏了）
     * @param node easyui tree节点
     */
    showChildren: function (jqTree, node) {
        //easyui tree的tree对象。可以通过tree.methodName(jqTree)方式调用easyui tree的方法
        var tree = this;

        //展示子节点
        if (!tree.isLeaf(jqTree, node.target)) {
            var children = tree.getChildren(jqTree, node.target);
            if (children && children.length > 0) {
                for (var i = 0; i < children.length; i++) {
                    if ($(children[i].target).is(":hidden")) {
                        $(children[i].target).show();
                    }
                }
            }
        }
    },

    /**
     * 将滚动条滚动到指定的节点位置，使该节点可见（如果有滚动条才滚动，没有滚动条就不滚动）
     * @param param {
     *    treeContainer: easyui tree的容器（即存在滚动条的树容器）。如果为null，则取easyui tree的根UL节点的父节点。
     *    targetNode:  将要滚动到的easyui tree节点。如果targetNode为空，则默认滚动到当前已选中的节点，如果没有选中的节点，则不滚动
     * }
     */
    scrollTo: function (jqTree, param) {
        //easyui tree的tree对象。可以通过tree.methodName(jqTree)方式调用easyui tree的方法
        var tree = this;

        //如果node为空，则获取当前选中的node
        var targetNode = param && param.targetNode ? param.targetNode : tree.getSelected(jqTree);

        if (targetNode != null) {
            //判断节点是否在可视区域
            var root = tree.getRoot(jqTree);
            var $targetNode = $(targetNode.target);
            var container = param && param.treeContainer ? param.treeContainer : jqTree.parent();
            var containerH = container.height();
            var nodeOffsetHeight = $targetNode.offset().top - container.offset().top;
            if (nodeOffsetHeight > (containerH - 30)) {
                var scrollHeight = container.scrollTop() + nodeOffsetHeight - containerH + 30;
                container.scrollTop(scrollHeight);
            }
        }
    }
});

/**
 * 展示搜索匹配的节点
 */
function showMatchedNode(jqTree, tree, node,text) {//展示父节点，隐藏子节点
    //展示所有父节点
    $(node.target).show();
    $(".tree-title", node.target).addClass("tree-node-targeted");
    var pNode = node;
    while ((pNode = tree.getParent(jqTree, pNode.target))) {
        $(pNode.target).show();
    }
    //展开到该节点
    tree.expandTo(jqTree, node.target);

   /* //如果是非叶子节点，需折叠该节点的所有子节点
    if (!tree.isLeaf(jqTree, node.target)) {
        tree.collapse(jqTree, node.target);
    }*/
}

/**
 * 判断searchText是否与targetText匹配
 * @param searchText 检索的文本
 * @param targetText 目标文本
 * @return true-检索的文本与目标文本匹配；否则为false.
 */
function isMatch(searchText, targetText) {
    return $.trim(targetText)!="" && targetText.indexOf(searchText)!=-1;
}

function isfMatch(searchText,node,nodeList,id,parentId) {//比对该节点,若符合则返回，不符合，递归寻找父节点直至符合或者直至最顶级节点都不符合，
    console.log("pkid:"+node[id]+",类别："+node.text)
    var  isf_match = isMatch(searchText,node.text)
    if (isf_match){
        return isf_match;
    } /*else {
        for (var index in nodeList){
            var node_parent = nodeList[index];
            if (node[parentId] != null && node[parentId] == node_parent[id]){
                if ( isfMatch(searchText,node_parent,nodeList,id,parentId)){
                    return true;
                }
            }
        }
    }*/
}

function findFatherNode(node,nodeList,id,pid) {
    for (var index in nodeList){
        if (node[pid]!=null && nodeList[index][id] && node[pid] == nodeList[index][id]){
            return nodeList[index]
        }
    }
    return null;
}

/**
 * 获取easyui tree的所有node节点
 */
function getAllNodes(jqTree, tree) {
    var allNodeList = jqTree.data("allNodeList");
    if (!allNodeList) {
        var roots = tree.getRoots(jqTree);
        allNodeList = getChildNodeList(jqTree, tree, roots);
        jqTree.data("allNodeList", allNodeList);
    }
    return allNodeList;
}

/**
 * 定义获取easyui tree的子节点的递归算法
 */
function getChildNodeList(jqTree, tree, nodes) {
    var childNodeList = [];
    if (nodes && nodes.length>0) {
        var node = null;
        for (var i=0; i<nodes.length; i++) {
            node = nodes[i];
            childNodeList.push(node);
            if (!tree.isLeaf(jqTree, node.target)) {
                var children = tree.getChildren(jqTree, node.target);
                childNodeList = childNodeList.concat(getChildNodeList(jqTree, tree, children));
            }
        }
    }
    return childNodeList;
}

var EasyUI = {
    initCombobox: function (target, opts) {
        var _hidePanel = opts.onHidePanel || function () {};
        var _onShowPanel = opts.onShowPanel || function () {};
        var _onHidePanel = function () {
            var _options = $(target).combobox("options");
            var textField = _options.textField;
            var valueField = _options.valueField;
            var multiple = _options.multiple;
            if(multiple){
                var val = $(target).combobox("getText");
                var vals = val.split(",");
                var _nvals = [];
                var allData = $(target).combobox("getData");
                for (var i = 0; i < allData.length; i++) {
                    if((','+vals+',').indexOf((','+allData[i][textField]+',')) > -1){
                        _nvals.push(allData[i][valueField]);
                    }
                }
                $(target).combobox({ value: _nvals });
            }else {
                var val = $(target).combobox("getText");
                var allData = $(target).combobox("getData");
                var result = true;
                for (var i = 0; i < allData.length; i++) {
                    if (val == allData[i][textField]) {
                        result = false;
                        break;
                    }
                }
                if (result) {
                    EasyUI.clearCombobox(target);
                }
            }
        };
        opts = $.extend({height: 26, editable: false }, {panelHeight: 26 * 5 }, opts);
        if(opts.editable){
            opts.onHidePanel = function () {
                _onHidePanel();
                _hidePanel();
            };
        }
        opts.onShowPanel = function () {
            var $panel = $(target).combobox('panel');
            var $items = $panel.find('.combobox-item');
            var maxlen = 0;
            $.each($items, function (k, it) {
                var len = FuncUtil.getStrLeng($(this).text());
                if (len > maxlen) {
                    maxlen = len;
                }
            });
            var pwd = $panel.width();
            var wd = Math.ceil(maxlen * 8.5);
            if (maxlen != 0 && (pwd < wd)) {
                $.each($items, function (k, it) {
                    $(this).css({ width: wd });
                });
            }
            _onShowPanel();
        };
        var ourl = opts.url;
        if(ourl){
            if(!opts.loadFilter){
                WinUtil.MsgError({ content: target + '必须同时配置URL,loadFilter!' });
                return;
            }
            AjaxUtil.postSyncReq({
                url: ourl,
                data: opts.params || {},
                success : function (result) {
                    opts.url = '';
                    opts.data = result;
                    $(target).combobox(opts);
                }
            });
        }else{
            $(target).combobox(opts);
        }
        return $(target);
    },

    initTree: function (target, opts) {
        opts.dg_type = "tree";
        var _onClick = opts.onClick;
        opts = $.extend({
            onContextMenu: function (e, node) {
                rowContextMenu(opts, target, e, -1, node);
            }
        }, opts, { onClick: function (node) {
            $(target).tree('toggle', node.target);
            _onClick && _onClick(node);
        } });
        var $tree = $(target).tree(opts);
        return $tree;
    },

    initCombotree: function (target, opts) {
        var _hidePanel = opts.onHidePanel || function () {};
        var _onHidePanel = function () {
            var t = $(target).combotree('tree');
            var n = t.tree('getSelected');
            if(!n){
                return;
            }
            var $txt = $(target).combotree('textbox');
            if($.trim($txt.val()) != ''
                && n.text != $txt.val()){
                $txt.val(n.text);
            }
        };
        opts = $.extend({height: 26, editable: false }, {width: 190, panelHeight: 26 * 5 }, opts);
        if(opts.editable){
            opts.onHidePanel = function () {
                _onHidePanel();
                _hidePanel();
            };
        }
        var ourl = opts.url;
        if(ourl){
            if(!opts.loadFilter){
                WinUtil.MsgError({ content: '必须同时配置URL,loadFilter!' });
                return;
            }
            AjaxUtil.postSyncReq({
                url: ourl,
                data: opts.params || {},
                success : function (result) {
                    opts.url = '';
                    opts.data = result;
                    $(target).combotree(opts);
                }
            });
        }else{
            $(target).combotree(opts);
        }
        var $txt = $(target).combotree('textbox');
        $txt.unbind('keyup').bind(
            {
                'keyup': function () {
                    if($.trim($(this).val()) == ''){
                        $(target).combotree("clear");
                    }
                }
            }
        );
        return $(target);
    },

    initTextbox: function (target, opts) {
        $(target).textbox(opts);
    },

    initTextboxSelect: function (target, opts) {
        var icons = [
            {
                iconCls: opts.iconCls || 'icon-search',
                handler: function (e) {
                    if (typeof(opts.handler) == 'function') {
                        opts.handler($(e.data.target));
                    }
                }
            }
        ];
        if(opts.hasClearBtn){
            icons.push({
                iconCls: 'icon-clear',
                handler: function (e) {
                    if (typeof(opts.clearHandler) == 'function') {
                        opts.clearHandler($(e.data.target));
                    }
                }
            });
        }
        opts = $.extend({
            height: opts.height |26, editable: false,
            iconWidth: opts.width| 22, iconAlign: 'right',
            icons: icons
        }, opts);
        return $(target).textbox(opts);
    },

    initFilebox: function (target, opts) {
        if(opts.multiple){
            $(target).attr("multiple", "multiple");
        }
        opts = $.extend({height: 26, width: 280, buttonText:'选择文件', prompt:'请选择文件...' }, opts);
        $(target).filebox(opts);
    },

    setComboboxValue : function(target, value){
        $(target).combobox({ value: value });
    },

    clearCombobox : function (target) {
        $(target).combobox('setText', '');
        $(target).combobox({ value: '' });
    },

    originalInitCombobox : function (target, opts) {
        var options = $(target).combobox('options');
        opts = $.extend(options||{}, opts);
        //$(target).combobox(opts);
        EasyUI.initCombobox(target, opts);
    },

    /**
     * 解决原生EasyUi messager.confirm 不支持获取文本表单内容的问题（原生先关闭了dialog，导致无法获取）
     * @param title
     * @param msg
     * @param fn
     */
    confirm: function (title, msg, fn) {
        var dlg = $.messager.confirm(title, msg, function (r) {
            var _r = fn(r);
            if(_r === false){
                return;
            }
            dlg.dialog("options").__canClose = '__Y';
            dlg.dialog("close");
        });
        dlg.dialog($.extend(dlg.dialog("options"), {
            closable:false,
            onBeforeClose: function () {
                var _k = dlg.dialog("options").__canClose;
                return _k == '__Y' ? true : false;
            }
        }));
    },


    /**
     * 解决原生EasyUi datagrid不支持使用行部分数据查找索引的功能
     * @param $dg
     * @param row
     * @returns {number}
     */
    getDGRowIndex : function ($dg,row){
        var opts = $dg.datagrid('options');
        var rows = $dg.datagrid('getRows');
        if (typeof row == "object") {
            for (var i = 0; i < rows.length; i++) {
                var _b = false;
                $.each(row, function (k, v) {
                    if(rows[i][k] != v){
                        _b = false;
                        return false;
                    }
                    _b = true;
                });
                if(_b){
                    return i;
                }
            }
            return -1;
        } else {
            for (var i = 0; i < rows.length; i++) {
                if (rows[i][opts.idField] == row) {
                    return i;
                }
            }
            return -1;
        }
    },

}

var EasyUITool = {

    WarpTreeData: function(treeData, fields, datacallback, ltop) {
        var _InitTreeData = function (arry, pid) {
            var treeData_ = {};
            $.each(arry || [], function (k, it) {
                var m = treeData_[it[pid]] || [];
                m.push(it);
                treeData_[it[pid]] = m;
            });
            return treeData_;
        };

        /**
         * 根据key 获取Tree数据 {object}
         * @param treeData
         * @param key
         * @param pkey
         * @returns {*}
         * @private
         */
        var _FindItemTreeData = function(treeData, key, pkey, fields) {
            var r_ = null;
            var pdata_ = treeData[pkey];
            $.each((pdata_ ? pdata_ : []), function (k, v) {
                if (v && typeof(v) == 'object' && (v[fields[0]] + '') == key) {
                    r_ = v;
                    return false;
                }
            });
            return r_;
        };

        /**
         * 组装 easyui-tree 的数据格式
         * @param treeData
         * @param key
         * @param pkey
         * @returns {{id: *, text: *, state: string}}
         * @private
         */
        var _WarpTreeMenu = function(treeData, key, pkey, fields, datacallback, level) {
            var tdata_ = _FindItemTreeData(treeData, key, pkey, fields);
            if (!tdata_) {
                return null;
            }
            tdata_.__level = level + 1;
            var data_ = datacallback(tdata_);
            if(!data_){return null; }
            var children_ = [];
            var pdata_ = treeData[tdata_[fields[0]] + ''];
            $.each((pdata_ ? pdata_ : []), function (k, v) {
                var r_ = _WarpTreeMenu(treeData, (v[fields[0]] + ''), (v[fields[1]] + ''), fields, datacallback, tdata_.__level);
                if (r_)
                    children_.push(r_);
            });
            if (children_.length != 0) {
                //data_.state = 'closed';
                data_.checked = false;
                data_.children = children_;
            }else{
                data_.state = 'open'; //叶子节点 设置为open
            }
            return data_;
        };

        treeData = _InitTreeData(treeData, fields[1]);
        var tdata_ = [];
        var iltop = typeof(ltop) == 'string' && ltop != "";
        var ids = {}, arrys = [];
        if(!iltop){
            $.each(treeData, function (k, v) {
                $.each(v, function (m,n) {
                    ids[(n[fields[0]] + '')] = '-';
                })
            });
            $.each(treeData, function (k, v) {
                if(!ids[k] ){
                    arrys.push(v);
                }
            });
        }else{
            var _ltop = ltop.split(',');
            $.each(_ltop, function (i, e) {
                if(treeData[e]) {
                    arrys.push(treeData[e]);
                }
            })
        }

        $.each(arrys, function (i, item) {
            $.each(item, function (k1, v1) {
                var r_ = _WarpTreeMenu(treeData, (v1[fields[0]] + ''), (v1[fields[1]] + ''), fields, datacallback, 0);
                if (r_)
                    tdata_.push(r_);
            });
        });

        return tdata_;
    },

    TreeTip : function (target, opts) {
        $(target).on('mouseenter', 'span.tree-title', function () {
            var curZindex = $(this).parents('.window').css('z-index') + 100;
            var itemTxt = $(this).text();
            var itemWidth = $(this).width() + 4; // 4像素是padding的左右值各2
            var itemLeft = $(this).position().left; // node.text文本相对于父元素偏移多少
            var cTreeWidth = $(target).width();

            if ((itemWidth + itemLeft) > cTreeWidth) {
                if ($('body').find('.tree_title_tip').length != 0) {
                    $('body').find('.tree_title_tip').remove();
                } else {
                    var html = '<div class="tree_title_tip">' + itemTxt + '</div>';
                    $('body').append(html);
                    $('.tree_title_tip').css({
                        'position': 'absolute',
                        'top': $(this).offset().top + 20,
                        'left': $(this).offset().left,
                        'z-index': curZindex
                    });
                }
            }
        });

        $(target).on('mouseleave', 'span.tree-title', function () {
            if($(event.target).closest('.tree_title_tip').length != 0){
                return;
            }
            if ($('body').find('.tree_title_tip').length != 0) {
                $('body').find('.tree_title_tip').remove();
            }
        });
    },


    TreeI18nTip : function (target, opts) {
        $(target).on('mouseenter', 'span.tree-title', function () {
            if(_getBilingualTipSwitch() != 'true'){
                return;
            }
            var $item = $(this).find('span[_data-i18n]');
            if($item.length == 0){
                return;
            }
            var key = $item.attr('_data-i18n');
            if($.trim(key) == ''){return;}
            var curZindex = $(this).parents('.window').css('z-index') + 100;
            if ($('body').find('.i18n_tip').length != 0) {
                $('body').find('.i18n_tip').remove();
            } else {
                var v2 = $.t(key);
                var msg = v2 == key ? '未翻译' : v2;

                var html = '<div class="i18n_tip">' + msg + '</div>';
                $('body').append(html);
                $('.i18n_tip').css({
                    'position': 'absolute',
                    'top': $(this).offset().top + $(this).height(),
                    'left': $(this).offset().left + $(this).width() + 8,
                    'z-index': curZindex
                });
            }
        });

        $(target).on('mouseleave', 'span.tree-title', function () {
            if ($('body').find('.i18n_tip').length != 0) {
                $('body').find('.i18n_tip').remove();
            }
        });
    },

    TreeDisableCheckbox_ : function (target, tdata_, condition) {
        var s = [];
        $.each(tdata_, function (k, item) {
            if(condition && $.isFunction(condition)){
                var node = $(target).tree('find', item.id);
                if(condition(node)==true){
                    s.push(item.id);
                    $(node.target).find(".tree-checkbox").removeClass('tree-checkbox').attr("_rmv", '1');
                }else if(condition(node)==false){
                    var $m = $(node.target).find("span[_rmv]");
                    if($m.length == 1){
                        $m.addClass('tree-checkbox').removeAttr("_rmv");
                    }
                }else{
                    //ldf 什么都不做
                }
            }
            if(item.children){
                var s1 = EasyUITool.TreeDisableCheckbox_(target, item.children, condition);
                $.each(s1 || [], function (k, it) {
                    s.push(it);
                });
            }
        });
        return s;
    },

    TreeDisableCheckbox : function (target, tdata_, condition) {
        var s = EasyUITool.TreeDisableCheckbox_(target, tdata_, condition);
        $(target).data('disableCheckbox', JSON.stringify(s));
    },

    TreeDisableCheckboxgetChecked: function (target, arry) {
        var ds = $(target).data('disableCheckbox');
        var dt = $(target).tree('getChecked', arry ? arry : ['checked']);
        if(ds) {
            var ndt = [];
            var ds_ = eval(ds);
            $.each(dt, function (k, item) {
                if(!($.inArray(item.id, ds_) > -1)){
                    ndt.push(item);
                }
            });
            return ndt;
        }
        return dt;
    }
}