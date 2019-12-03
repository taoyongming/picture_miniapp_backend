package web.util;

public  final class Constant {

	private  Constant() {
	}

	/**
	 * 请求返回码
	 */
	public static String ERROR_CODE_succ="0"; //成功
	public static String ERROR_CODE_nodata="1"; //没有数据
	public static String ERROR_CODE_error="2"; //系统异常
	public static String ERROR_CODE_repeat="3"; //数据重复


	/**
	 * 是否可用
	 */
	public static String STATUS_YES="1";
	public static String STATUS_NO="0";
	
	/**
	 * 关于登录
	 */
	public static String ERROR_CODE_loginSucc="0"; //成功
	public static String ERROR_CODE_loginError="1"; //登录失败
	public static String ERROR_CODE_passwordOrUsernameError="2"; //账户或者密码不正确
	public static String ERROR_CODE_locking="3"; //账户或者密码不正确
	
	/**
	 * 角色id
	 */
	public static int ROLE_ID_1 = 1;
	public static int ROLE_ID_2 = 2;
	public static int ROLE_ID_3 = 3;


	/**
	 * 数据源配置
	 */
	public static  String DB_FENGYU = "db_fengyu";
	public static  String DB_FENGYU_GETWAY = "db_fengyu_getway";

	/**
	 * 风险趋势类型
	 */
	public static int TRENDTYPE1 = 1;
	public static int TRENDTYPE2 = 2;
	public static int TRENDTYPE3 = 3;
	public static int TRENDTYPE4 = 4;


	/**
	 * 告警类型
	 */
	public static int WARNINGTYPE_RED = 1;
	public static int WARNINGTYPE_YELLOW = 2;


	/**
	 * 发送状态 0：未发送 1：已发送
	 */
	public static final int SEND_FLAG_0 = 0; //未发送
	public static final int SEND_FLAG_1 = 1; //已发送





}
