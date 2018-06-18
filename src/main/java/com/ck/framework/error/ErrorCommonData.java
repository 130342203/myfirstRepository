package com.ck.framework.error;

import com.ck.framework.error.annotation.ErrorMessage;
import com.ck.framework.error.annotation.ErrorResource;

/**
 * @author ron
 *         2016/8/9.
 */
@ErrorResource
public class ErrorCommonData {

    @ErrorMessage(value = "RES.ERROR.SUSSESS",remark = "请求成功")
    public static final int SUCCESS_CODE = 200;

    @ErrorMessage(value = "RES.ERROR.FORBIDDEN_ERROR_CODE",remark = "未登陆或登陆超时，请重新登陆")
    public static final int FORBIDDEN_ERROR_CODE = 100;

    @ErrorMessage(value = "MOBILE_NOT_LOGIN",remark = "登录超时，请重新登录")
    public static final int MOBILE_NOT_LOGIN = 101;

    @ErrorMessage(value = "ERR_USER_LOGIN_FAIL",remark = "用户名或密码错误")
    public static final int ERR_USER_LOGIN_FAIL = 103;

    @ErrorMessage(value = "ERR_USER_REPEAT_CLICK",remark = "请勿多次重复点击")
    public static final int ERR_USER_REPEAT_CLICK = 104;
    @ErrorMessage(value = "ERR_EDIT_FAIL",remark = "qq来源消息无法关闭整理")
    public static  final  int ERR_EDIT_FAIL = 638;

    @ErrorMessage(value = "RES.ERROR.NOT_FOUND_ERROR",remark = "找不到指定的内容")
    public static final int NOT_FOUND_ERROR_CODE = 404;

    @ErrorMessage(value = "RES.ERROR.REQUEST_PARAM_ERROR",remark = "服务器内部错误")
    public static final int SERVER_ERROR = 500;

    @ErrorMessage(value = "RES.ERROR.REQUEST_PARAM_ERROR",remark = "请求参数有误")
    public static final int REQUEST_PARAM_ERROR = 502;

    @ErrorMessage(value = "RES.ERROR.ENUM_CONVERT_ERROR",remark = "该枚举类型不支持该转换")
    public static final int ENUM_CONVERT_ERROR = 503;

    @ErrorMessage(value = "ORIGINAL_PWD_NOT_RIGHT",remark = "您输入的原密码不正确!")
    public static final int ORIGINAL_PWD_NOT_RIGHT = 506;

    @ErrorMessage(value = "RES.ERROR.NOT_FIND_SERIAL_NUM_CODE",remark = "未找到对应的流水号编码!")
    public static final int NOT_FIND_SERIAL_NUM_CODE = 602;

    @ErrorMessage(value = "RES.ERROR.NOT_EQ_NUM",remark = "秒回响应单的供货数量，与供方仓库供件数量不相等!")
    public static final int NOT_EQ_NUM = 603;

    @ErrorMessage(value = "RES.ERROR.SAME_CITY_NO_KY",remark = "同城没有空运方式!")
    public static final int SAME_CITY_NO_KY = 604;

    @ErrorMessage(value = "RES.ERROR.EXCP_ZCJJ_COST_NOTFIND",remark = "数据异常，没有找到转场交接费用信息!")
    public static final int EXCP_ZCJJ_COST_NOTFIND = 605;

    @ErrorMessage(value = "RES.ERROR.EXCP_COST_NOTFIND",remark = "数据异常，没有找到物流配置信息!")
    public static final int EXCP_COST_NOTFIND = 606;

    @ErrorMessage(value = "RES.ERROR.EXCP_ORG_CITY_DEST_CITY",remark = "数据异常，同城不可选择空运!")
    public static final int EXCP_ORG_CITY_DEST_CITY = 607;

    @ErrorMessage(value = "RES.ERROR.EXCP_NO_HAVE_FLIGHT_DATA",remark = "供货库房 至 求援地 没有航班信息，请您换另一种物流方式")
    public static final int EXCP_NO_HAVE_FLIGHT_DATA = 608;

    @ErrorMessage(value = "RES.ERROR.EXCP_NOFIND_RECEIVE_COST",remark = "数据异常，没有对应的接运费用信息!")
    public static final int EXCP_NOFIND_RECEIVE_COST = 609;

    @ErrorMessage(value = "RES.ERROR.EXCP_NOFIND_SPECIAL_CAR_COST",remark = "数据异常，没有对应的专车费用信息!")
    public static final int EXCP_NOFIND_SPECIAL_CAR_COST = 610;

    @ErrorMessage(value = "RES.ERROR.EXCP_DEFECTIVE_STOCK_TYPE",remark = "数据异常，物流费用配置仓库类型无法覆盖!")
    public static final int EXCP_DEFECTIVE_STOCK_TYPE = 611;

    @ErrorMessage(value = "RES.ERROR.EXCP_ORDER_ALREADY_FH",remark = "数据异常，处理的订单信息中有已安排发货的订单!")
    public static final int EXCP_ORDER_ALREADY_FH = 612;

    @ErrorMessage(value = "RES.ERROR.EXCP_LOGSORDER_JY",remark = "数据异常，此订单不能创建运单!")
    public static final int EXCP_LOGSORDER_JY = 613;

    @ErrorMessage(value = "RES.ERROR.EXCP_LOGSORDER_ERROR",remark = "物流未完成，您不能确认收货")
    public static final int EXCP_LOGSORDER_ERROR = 614;

    @ErrorMessage(value = "RES.ERROR.EXCP_LOGSORDER_UNFIXUP",remark = "此订单还未安排物流")
    public static final int EXCP_LOGSORDER_UNFIXUP = 615;

    @ErrorMessage(value = "RES.ERROR.EXCP_LOGSORDER_UNFIND",remark = "数据异常,未找到相应的物流单")
    public static final int EXCP_LOGSORDER_UNFIND = 616;

    @ErrorMessage(value = "RES.ERROR.EXCP_LOGSORDER_UNOFU",remark = "数据异常,该物流单貌似并不属于您~")
    public static final int EXCP_LOGSORDER_UNOFU = 617;

    @ErrorMessage(value = "RES.ERROR.EXCP_LOGSORDER_ATUNOK",remark = "您的材料还未审核通过~")
    public static final int EXCP_LOGSORDER_ATUNOK = 618;

    @ErrorMessage(value = "RES.ERROR.EXCP_LOGSORDER_UNTY",remark = "提运物流单还未完成配送，请您稍候~")
    public static final int EXCP_LOGSORDER_UNTY = 619;

    @ErrorMessage(value = "RES.ERROR.EXCP_ASKORDER_NOFIND",remark = "数据异常，未找到相应的求援单~")
    public static final int EXCP_ASKORDER_NOFIND = 620;

    @ErrorMessage(value = "RES.ERROR.EXCP_ASKORDER_CLOSE",remark = "求援单已关闭~")
    public static final int EXCP_ASKORDER_CLOSE = 621;

    @ErrorMessage(value = "RES.ERROR.EXCP_ORDERIMG_NOUPLOAD",remark = "请先上传证件~")
    public static final int EXCP_ORDERIMG_NOUPLOAD = 622;

    @ErrorMessage(value = "RES.ERROR.EXCP_DORDER_UNOFU",remark = "数据异常,该订单貌似并不属于您~")
    public static final int EXCP_DORDER_UNOFU = 623;

    @ErrorMessage(value = "RES.ERROR.EXCP_SERIALNO_NOFIND",remark = "数据异常，序号未上传~~")
    public static final int EXCP_DORDER_SERIALNO_NOFIND = 624;

    @ErrorMessage(value = "RES.ERROR.EXCP_DORDER_SENDLOGISTICS_NOARRIVE",remark = "数据异常，物流还未完成~")
    public static final int EXCP_DORDER_SENDLOGISTICS_NOARRIVE = 625;

    @ErrorMessage(value = "RES.ERROR.EXCP_DAYRATE_CHONGFU",remark = "数据重复~")
    public static final int EXCP_DAYRATE_CHONGFU = 626;

    @ErrorMessage(value = "RES.ERROR.EXCP_COMPANYRATE_NOTFIND",remark = "该公司未设置汇率转换方式~")
    public static final int EXCP_COMPANYRATE_NOTFIND = 627;

    @ErrorMessage(value = "RES.ERROR.EXCP_DORDERIMG_NOTFIND",remark = "未找到订单租期凭证~")
    public static final int EXCP_DORDERIMG_NOTFIND = 628;

    @ErrorMessage(value = "RES.ERROR.EXCP_MONTHRATE_NOTFIND",remark = "利顿未设置租期月份汇率~")
    public static final int EXCP_MONTHRATE_NOTFIND = 629;

    @ErrorMessage(value = "RES.ERROR.COMPANYDAYRATE_NOTFIND",remark = "利顿未给供方或需方航司设置利润比~")
    public static final int EXCP_COMPANYDAYRATE_NOTFIND = 630;

    @ErrorMessage(value = "RES.ERROR.DORDERBASIS_NOTFIND",remark = "未找到订单的报价依据~")
    public static final int EXCP_DORDERBASIS_NOTFIND = 631;

    @ErrorMessage(value = "RES.ERROR.EXCP_NOT_FIND_USABLE_LOGISTICS",remark = "未找到可用的物流商信息")
    public static final int EXCP_NOT_FIND_USABLE_LOGISTICS = 632;

    @ErrorMessage(value = "RES.ERROR.EXCP_LOGSORDER_NO_CXYFY",remark = "此订单还有未确认的超协议费用，不能确认收货")
    public static final int EXCP_LOGSORDER_NO_CXYFY = 633;

    @ErrorMessage(value = "RES.ERROR.EXCP_LOGSORDER_NO_CXYFY_JY",remark = "此订单还未安排接运任务，不能确认收货，请联系利顿")
    public static final int EXCP_LOGSORDER_ERROR_JY = 634;
    @ErrorMessage(value = "RES.ERROR.EXCP_LOGSORDER_NO_UPLOADNOFIN",remark = "您还有航材，材料需要上传~")
    public static final int EXCP_LOGSORDER_ERROR_UPLOADNOFIN = 635;

    @ErrorMessage(value = "RES.ERROR.EXCP_LOGSORDER_NO_NOTEQ",remark = "填写的序号与发起归还订单的序号不符~")
    public static final int EXCP_LOGSORDER_ERROR_NOTEQ = 636;

    @ErrorMessage(value = "RES.ERROR.EXCP_LOGSORDER_UNFY",remark = "发运物流单还未完成配送，请您稍候~")
    public static final int EXCP_LOGSORDER_UNFY = 637;

    @ErrorMessage(value = "RES.ERROR.EXCP_NOT_FIND_CITY",remark = "未找到对应件号的机型,请手动选择机型")
    public static  final  int EXCP_NOT_FIND_CITY = 638;

    @ErrorMessage(value = "EXCP_NOT_FIND_AOGCOMPANYNAME",remark = "列表中存在求援航司不确定的记录")
    public static final  int EXCP_NOT_FIND_AOGCOMPANYNAME = 639;

    @ErrorMessage(value = "EXCP_NOT_FIND_ISUSEDTEMPLATE",remark = "系统中暂无该类型启用模板")
    public static final  int EXCP_NOT_FIND_ISUSEDTEMPLATE = 640;

    @ErrorMessage(value = "EXCP_NOT_FIND_ISUSEINGTENPLATE",remark = "系统中正在启用该模板,不可删除！")
    public static final  int EXCP_NOT_FIND_ISUSEINGTENPLATE = 641;

    @ErrorMessage(value = "EXCP_ISUSEING_IP_WHITELIST",remark = "系统中正在启用该白名单,不可删除！")
    public static final  int EXCP_ISUSEING_IP_WHITELIST= 642;

    @ErrorMessage(value = "RES.ERROR.EXCP_LOGSORDER_NO_CXYFY_TO_BE_CONFIRMED",remark = "此订单还有未确认的超协议费用，不能确认报价")
    public static final int EXCP_LOGSORDER_NO_CXYFY_TO_BE_CONFIRMED = 643;

    @ErrorMessage(value = "RES.ERROR.EXCP_CANSUPPLY_GF",remark = "航司供件能力错误，请联系利顿!")
    public static final int EXCP_CANSUPPLY_GF = 510;

    @ErrorMessage(value = "RES.ERROR.EXCP_LEASE_START",remark = "租期开始时间依据已上传，请不要重复上传!")
    public static final int EXCP_LEASE_START = 511;

    @ErrorMessage(value = "RES.ERROR.EXCP_LEASE_END",remark = "租期结束时间依据已上传，请不要重复上传!")
    public static final int EXCP_LEASE_END = 512;

    @ErrorMessage(value = "RES.ERROR.EXCP_LEASE_BETWEEN",remark = "租期结束日期小于租期开始时间!")
    public static final int EXCP_LEASE_BETWEEN = 513;

    @ErrorMessage(value = "RES.ERROR.EXCP_LEASE_UNSTART",remark = "请先上传租期开始时间!")
    public static final int EXCP_LEASE_UNSTART = 514;

    @ErrorMessage(value = "RES.ERROR.EXCP_DORDER_UNRETURN",remark = "订单未完成归还，不允许上传租期~")
    public static final int EXCP_DORDER_UNRETURN = 515;

    @ErrorMessage(value = "RES.ERROR.EXCP_DORDER_UNUPLOADORDERIMG",remark = "订单未上传求援单~")
    public static final int EXCP_DORDER_UNUPLOADORDERIMG = 516;

    @ErrorMessage(value = "RES.ERROR.EXCP_DORDER_CARDUNCONFIRM",remark = "证件未确认~")
    public static final int EXCP_DORDER_CARDUNCONFIRM = 517;

    @ErrorMessage(value = "RES.ERROR.EXCP_DORDER_LEASEUNCONFIRM",remark = "租期未确认~")
    public static final int EXCP_DORDER_LEASEUNCONFIRM = 518;

    @ErrorMessage(value = "RES.ERROR.EXCP_DORDER_UNPRICECONFIRM",remark = "报价还未确认~")
    public static final int EXCP_DORDER_UNPRICECONFIRM = 519;

    @ErrorMessage(value = "RES.ERROR.EXCP_DORDER_UNFINDPRICEBASIS",remark = "报价依据未找到~")
    public static final int EXCP_DORDER_UNFINDPRICEBASIS = 520;

    @ErrorMessage(value = "RES.ERROR.EXCP_DORDER_UNRETURNO",remark = "订单还未完成归还~")
    public static final int EXCP_DORDER_UNRETURNO = 521;

    @ErrorMessage(value = "RES.ERROR.EXCP_DORDER_HAVECASE",remark = "还有未处理的异常，请先处理~")
    public static final int EXCP_DORDER_HAVECASE = 522;

    @ErrorMessage(value = "RES.ERROR.EXCP_DORDER_HAVECASE",remark = "上传文件过大，请上传小于10M的文件~")
    public static final int EXCP_UPLOADFILE_TOMAX = 523;

    @ErrorMessage(value = "RES.ERROR.EXCP_PAYMENU_ISFIXEDE",remark = "账单已生成~请误重复点击")
    public static final int EXCP_PAYMENU_ISFIXED = 525;

    @ErrorMessage(value = "RES.ERROR.EXCP_DORDER_CANNOT_RETURN",remark = "订单没有序号无法发起归还!")
    public static final int EXCP_CANNOT_RETURN = 524;

    @ErrorMessage(value = "RES.ERROR.EXCP_ORDER_ANSWERTIMEOUT",remark = "响应已过期~不能选择供方")
    public static final int EXCP_ORDER_ANSWERTIMEOUT = 526;

    @ErrorMessage(value = "RES.ERROR.EXCP_ORDER_TIMEOUT",remark = "求援已过期~不能选择供方")
    public static final int EXCP_ORDER_TIMEOUT = 527;

    @ErrorMessage(value = "RES.ERROR.EXCP_ORDER_CLOSEED",remark = "求援已关闭~不能选择供方")
    public static final int EXCP_ORDER_CLOSEED = 528;

    @ErrorMessage(value = "RES.ERROR.EXCP_ORDER_ISHAVE",remark = "订单已生成，请勿重复点击~")
    public static final int EXCP_ORDER_ISHAVE = 529;

    @ErrorMessage(value = "RES.ERROR.EXCP_ORDER_ASSIGN1",remark = "求援单已绑定到用户~")
    public static final int EXCP_ORDER_ASSIGN1 = 530;

    @ErrorMessage(value = "RES.ERROR.EXCP_ORDER_ASSIGN2",remark = "对不起，该求援单不属于贵司~")
    public static final int EXCP_ORDER_ASSIGN2 = 531;

    @ErrorMessage(value = "RES.ERROR.EXCP_PLEASE_CHOICE_ITEM",remark = "请选择可供信息")
    public static final int EXCP_PLEASE_CHOICE_ITEM = 532;

    @ErrorMessage(value = "RES.ERROR.EXCP_CRM_SUBMITED",remark = "今日已经完成提交，请明日继续操作。")
    public static final int EXCP_CRM_SUBMITED = 533;
    @ErrorMessage(value = "RES.ERROR.EXCP_ORDER_NOTFINDAID",remark = "未找到对应的响应单数据。")
    public static final int EXCP_ORDER_NOTFINDAID = 534;

    @ErrorMessage(value = "RES.ERROR.EXCP_ORDER_NOTLOGIN",remark = "您还未登录，请扫描左侧的二维码~")
    public static final int EXCP_ORDER_NOTLOGIN = 535;

    @ErrorMessage(value = "RES.ERROR.EXCP_ORDER_NOTAUTH",remark = "您没有求援权限，请联系贵司管理员~")
    public static final int EXCP_ORDER_NOTAUTH = 536;

    @ErrorMessage(value = "RES.ERROR.EXCP_FORBIDDEN_ACOUNT",remark = "该用户账号已作废，无法登录")
    public static final int EXCP_FORBIDDEN_ACOUNT = 537;

    @ErrorMessage(value = "RES.ERROR.EXCP_FORBIDDEN_IP",remark = "该IP地址已被禁止登陆")
    public static final int EXCP_FORBIDDEN_IP = 538;

    @ErrorMessage(value = "RES.ERROR.EXCP_DUPLICATED_IP",remark = "已存在正在运行中的业务类型，不能重复添加")
    public static final int EXCP_DUPLICATED_IP = 539;

}
