package com.major.base.vo;
/**
 * 常量类
 * @author Administrator
 *
 */
public class Contains {
	/**
	 * 角色名字--工长
	 */
	public static String ROLE_NAME_GZ = "工长";
	/**
	 * 角色名称-工人
	 */
	public static final String ROLE_NAME_GR = "工人";
	/**
	 * 角色名称-技术员
	 */
	public static final String ROLE_NAME_JSY = "车间技术";
	/**
	 * 角色名称-质检员
	 */
	public static final String ROLE_NAME_ZJY = "车间质检";
	/**
	 * 角色名称-交车工长
	 */
	public static final String ROLE_NAME_JCGZ = "交车工长";
	/**
	 * 角色名称-验收员
	 */
	public static final String ROLE_NAME_YSY = "验收员";
	/**
	 * 角色名称-检修主任
	 */
	public static final String ROLE_NAME_ZR = "主任";
	/**
	 * 角色名称-段长
	 */
	public static final String ROLE_NAME_DZ= "段长";
	/**
	 * 计划状态(待休)--未完成
	 */
	public static int PLAN_STATUS_DAIXIU = 0;
	/**
	 * 分解组装记录状态--工长待签
	 */
	public static short JCPAREC_STATUS_GZDQ = 2;
	/**
	 * 秋整修次拼音缩写
	 */
	public static String PY_QZ = "QZ";
	/**
	 * 春鉴修次拼音缩写
	 */
	public static String PY_CJ = "CJ";
	/**
	 * 临修修次拼音缩写
	 */
	public static String PY_LX = "LX";
	/**
	 * 加改修次拼音缩写
	 */
	public static String PY_JG = "JG";
	/**
	 * 整治修次拼音缩写
	 */
	public static String PY_ZZ = "ZZ";
	/**
	 * 机车类型：电力SS开头
	 */
	public static String JC_TYPE_ELECTRIC_PREFIX = "SS";
	
	/**
	 * 机车类型：电力DF开头
	 */
	public static String JC_TYPE_OIL_PREFIX ="DF";
	
	/**
	 * 电力机车类型
	 */
	public static String JC_TYPE_ELECTRIC ="电力机车";
	/**
	 * 内燃机车类型
	 */
	public static String JC_TYPE_OIL ="内燃机车";
	/**
	 * 临修加改类型
	 */
	public static Short LX_JG_TYPE=(short)3;
	
	/**
	 * 工长角色标识
	 */
	public static Integer ROLE_GZ = 1;
	
	/**
	 * 技术角色标识
	 */
	public static Integer ROLE_JS = 2;
	/**
	 * 质检角色标识
	 */
	public static Integer ROLE_ZJ = 3;
	/**
	 * 交车工长角色标识
	 */
	public static Integer ROLE_JCGZ = 4;
	/**
	 * 验收角色标识
	 */
	public static Integer ROLE_YS = 5;
	
	/**
	 * 小辅修复检节点ID
	 */
	public static Integer XX_FJ_NODEID = 105;
	/**
	 * 小辅修检修节点ID
	 */
	public static Integer XX_JX_NODEID = 106;
	/**
	 * 小辅修试验节点ID
	 */
	public static Integer XX_SY_NODEID = 107;
	
	/**
	 * 中修复检节点ID
	 */
	public static Integer ZX_FJ_NODEID = 104;
	/**
	 * 中修分解节点ID
	 */
	public static Integer ZX_FG_NODEID = 100;
	/**
	 * 中修车上、组装节点ID
	 */
	public static Integer ZX_CSZZ_NODEID = 101;
	/**
	 * 中修试验节点ID
	 */
	public static Integer ZX_SY_NODEID = 102;
	/**
	 * 中修试运行节点ID
	 */
	public static Integer ZX_SYX_NODEID = 103;
	
	/**
	 * 小辅修计划标识
	 */
	public static Integer XX_PROJECT_TYPE=0;
	/**
	 * 中修计划标识
	 */
	public static Integer ZX_PROJECT_TYPE=1;
	
	public static Long PROTEAM_JCZ_ID=(long)24;
	
	/**
	 * 探伤组拼音
	 */
	public static String TSZ_PY = "TSZ2";
	
	/**
	 * 是否启用报表模板ID
	 */
	public static Integer IS_USE_REPORT_TEMPLATE = 1;
}
