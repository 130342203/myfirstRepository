//制造附件
function produceAttach(attach) {
    if (attach==undefined || attach==""){
        return  "";
    }
    var pkid = attach[0].pkid;
    var name = attach[0].orgName;
    var html = "<span style='color: blue; cursor: pointer; text-decoration: underline;'" +
        " onclick="+"_DownGridAttach(\'"+pkid+"\',\'"+name+"\')>"+name+"</span>";
    return html;
}
//获取回复信息
function getApplyIdeas() {
    AjaxUtil.postSyncReq({
        url: Constant.API_V1_PLUGINS+"VIEW_BS_COMMUNICATION",
        data: {id:PAGE_PARAM.row.ID},
        success: function (result) {
            if (!FuncUtil.checkResult(result)) {
                return;
            }
            var rdata = result.data.VIEW_BS_COMMUNICATION;
            laytpl($("#communication_apply").html()).render(rdata, function (html) {
                $('#dg').html(html);
            });
        }
    });
}

