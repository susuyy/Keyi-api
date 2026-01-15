package com.ht.note.exception;

/**
 * 定义业务响应状态码和信息描述
 *
 * @author suyangyu
 * @since 2020-06-09
 */
public enum StatusCode {

    SUCCESS_TRUE(1200, "成功"),

    SUCCESS(20000, "操作成功"),

    ERROR(20001, "操作失败"),

    CODE_ERROR(20002, "验证码错误"),

    CARD_BIND(20003, "卡已存在绑定信息"),

    SEND_CODE_ERROR(20004, "短信发送失败"),

    OPENID_ERROR(20005, "openid错误"),

    PHONE_NULL(20006, "手机号为空"),

    USER_NULL(20007, "用户不存在"),

    USER_EXIST(20008, "用户已存在"),

    TOKEN_ERROR(40001, "token失效"),

    USER_FIND_NULL(10108,"用户不存在"),

    USER_PHONE_NOT_NULL(10109,"该手机已绑定其他微信"),

    USER_MONEY_NOT_ENOUGH(11120,"用户余额不足消费"),

    USER_CARD_NOT(11121,"用户未绑定卡"),

    CARD_PAY_ERROR(11122,"交易失败"),

    CARD_ACCOUNT_MONEY_NOT(11123,"该实体卡未查询到可用金额"),

    USER_ACCOUNT_ADD_ERROR(11124,"实体卡金额转移至余额异常"),

    ACTUAL_CARD_MONEY_DEDUCT_ERROR(11125,"实体卡金额处理异常,请再次绑定或是联系客服"),

    OPEN_CARD_ERROR(11126,"开通虚拟开失败,无法初始化用户余额数据"),

    MER_ORDER_ID_NULL(11127,"系统中查无此流水订单"),

    CARD_PAYMENT_ID_ERROR(11128,"该类型的卡支付活动错误"),
    REFUND_ERROR(11129,"退款失败"),
    REFUND_MONEY_ERROR(11130,"退款金额大于可退余额"),

    CERT_EXPIRED(12100,"证书已过期,请联系开发方申请续期"),

    CARD_POOL_ERROR(13100,"卡池中卡数量不足"),

    DATA_SIZE_ERROR(14100,"待绑定实体卡数量超出电子卡数量"),

    CARD_SELL(14101,"线上售卖卡卡不允许私发,请选择发放体验卡"),

    CARD_NULL(14102,"系统中不存在卡号"),

    AUDIT_NOT(14103,"审核为通过,无法导出数据"),

    DELETE_MAKE_ORDER_NOT(14104,"已入库的订购单不允许删除"),

    ACTIVE_ERROR(14105,"激活失败,存在已出库激活的卡片,勿重复出库激活"),

    OBJMERCHANT_NULL(14106,"查询主体不能为空"),

    BIND_PASSWORD_ERROR(14107,"绑卡密码错误"),

    CARD_TYPE_NOT_BIND(14108,"该电子卡不允许自行绑定,请联系管理员"),

    ORI_PASSWORD_ERROR(14109,"原密码错误,请联系管理员"),

    PASSWORD_ERROR(14110,"密码错误"),

    PHY_CARD_STATE_ERROR(14111,"只可冻结已激活实体卡"),

    MISS_PARAMS(15001,"缺少请求参数"),

    SOURCE_NUM_ERROR(16001,"上架数量超出卡源剩余数量,可上架数量为:"),

    CROSS_MERCHANT(17001,"无法跨商户核销"),

    STATE_ERROR(18001,"无法重复核销,此二维码已核销"),

    IMG_ERROR(19001,"该类型配置参数已存在,禁止重复增加");



    private int code;
    private String desc;

    StatusCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}

