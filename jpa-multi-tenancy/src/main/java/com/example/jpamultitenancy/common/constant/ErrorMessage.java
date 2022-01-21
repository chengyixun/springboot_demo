package com.example.jpamultitenancy.common.constant;

/**
 * {@link ErrorMessage} common error message
 *
 * @author Liyaohui
 * @date 6/20/21
 */
public interface ErrorMessage {

	String PARAM_ERROR = "参数异常";

	String DATA_ERROR = "数据异常";

	String SYSTEM_ERROR = "服务器开小差";

	String SYNC_UPDATE_ERROR = "数据已被更新，请刷新后重试";

	String EXCEL_ERROR = "Excel格式不正确，请上传.xlsx文件";

	String WX_AUTH_ACCESS_TOKEN_MISSING_ERROR = "没有可用的调用令牌";
}
