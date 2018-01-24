package com.bnk.plus.commons;

import com.bnk.plus.config.AppConstBean;

/**
 * 시스템 공통 변수 정의 클래스
 * @author sw-yun
 */
public class CoConstDef {

	public static final String APP_NAME = AppConstBean.APP_NAME;
	/** 시스템 기본 Role - 모든 사용자가 공통으로 가지고 있는 역할 */
	public static final String SECURITY_ROLE_DEFAULT = AppConstBean.SECURITY_ROLE_DEFAULT;
	public static final String SECURITY_ROLE_DEALER = AppConstBean.SECURITY_ROLE_DEALER;

	/** 범용 flag YN */
	public static final String FLAG_YES = "Y";
	public static final String FLAG_NO = "N";

	public static final String EMPTY_STRING = "";

	/** 화면표시 날짜 형식 (yyyy-MM-dd hh:mm:ss) */
	public static final String DISP_FORMAT_DATE_ALL = "yyyy-MM-dd hh:mm:ss";

	/** 화면표시 날짜 형식 (yyyy-MM-dd) */
	public static final String DISP_FORMAT_DATE_YYYYMMDD = "yyyy-MM-dd";

	/** 모바일 화면표시 날짜 형식 (yyyy.MM.dd hh:mm:ss) */
	public static final String DISP_MOBILE_FORMAT_DATE_ALL_DOT = "yyyy.MM.dd hh:mm:ss";

	/** 모바일 화면표시 날짜 형식 (yyyy.MM.dd) */
	public static final String DISP_MOBILE_FORMAT_DATE_YYYYMMDD_DOT = "yyyy.MM.dd";

	/** 데이터 베이스 날짜 형식 (yyyy-MM-dd hh:mm:ss) */
	public static final String DATABASE_FORMAT_DATE_ALL = "yyyy-MM-dd HH:mm:ss";

	/** 데이터 베이스 날짜 형식 (yyyy-MM-dd hh:mm:ss.SSS) */
	public static final String DATABASE_FORMAT_DATE_TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS";

	/** 화면표시 날짜 형식 taglib용 full format [yyyy-MM-dd hh:mm:ss] */
	public static final String DISP_FORMAT_DATE_TAG_DEFAULT = "-- ::";

	/** 화면표시 날짜 형식 taglib용 simple format [yyyy-MM-dd] */
	public static final String DISP_FORMAT_DATE_TAG_SIMPLE = "--";
	public static final String DISP_FORMAT_DATE_TAG_SIMPLE_NUMBER = "yyyyMMdd";

	// Grid 관련
	public static final String GRID_OPERATION_ADD = "add";
	public static final String GRID_OPERATION_EDIT = "edit";
	public static final String GRID_OPERATION_DELETE = "del";
	public static final String GRID_NEWROW_DEFAULT_PREFIX = "jqg";




	public static final String BIZ_API_URL_SET_EST		= "http://car-api.kfc.center/CarEstimate/Set"; // 사용하지 말 것
	public static final String BIZ_API_URL_GET_EST		= "http://car-api.kfc.center/CarEstimate/GetAmt"; // 사용하지 말 것
	public static final String BIZ_API_URL_GET_DEALER 	= "http://car-api.kfc.center/Car/GetDealerData"; // 사용하지 말 것
	public static final String BIZ_API_URL_CAR_RESERVE	= "http://car-api.kfc.center/CarReservation/Set"; // 사용하지 말 것


	public static final String API_BIG_CAR_DATA = "api.big.car.data";

    /** The api big est req. 견적등록 */
	public static final String API_BIG_EST_REQ = "api.big.est.req";

    /** The api big est res. 견적금액확인 */
	public static final String API_BIG_EST_RES = "api.big.est.amt";
	public static final String API_BIG_RESV = "api.big.resv.req";


	/** The Constant BNK_TEL.bnk 대표전화번호 */
	public static final String BNK_TEL = "16446598";


	/** 사용자구분 : 딜러 */
	public static final String USER_DIVISION_DEALER = "D";
	/** 사용자구분 : 일반 */
	public static final String USER_DIVISION_USER = "N";

	public static final String CAR_IMAGE_DIVISION_CAR = "C";
	public static final String CAR_IMAGE_DIVISION_MYCAR = "M";
	public static final String CAR_IMAGE_DIVISION_MAKEUP = "A";

	/** 대출한도 조회 */
	public static final String CAR_LOAN_CHECK_URL_KEY = "api.loan.limit.mb";


	/**  Header Menu Codes (=menuId : 유저 생성시 대메뉴의 비트값을 가져오기 위한 코드). */

//	/** MAIN MENU > APP MAIN */
//	public static final String CD_DTL_MENU_MAIN_APP_MAIN = "800";
//	/** MAIN MENU > NOTICE */
//	public static final String CD_DTL_MENU_MAIN_NOTICE = "700";
//
//	/** MAIN > CODE MAIL */
//	public static final String CD_MAIL = "102";
//
//	/** DTL > MAIL */
//	public static final String CD_DTL_MAIL_REGIST = "301";


//	/** DEFAULT MAIN MENU CODE */
//	public static final String[] DEFAULT_MENUS = {
//		CD_DTL_MENU_MAIN_APP_MAIN,				// App 메인
//		CD_DTL_MENU_MAIN_NOTICE,					// 공지사항
//	};

//	/** AD login LDAP */
//	public static enum AD_LDAP_LOGIN {
//		LDAP_SERVER_URL("ldap://156.147.162.251:389"),
//		LDAP_DOMAIN("@LGE.NET"),
//		INITIAL_CONTEXT_FACTORY("com.sun.jndi.ldap.LdapCtxFactory"),
//		ERROR_49("LDAP: error code 49");
//    	private String value;
//    	private AD_LDAP_LOGIN(String value) {this.value = value;}
//    	public String getValue(){return this.value;}
//	}

//	/** 페이지 설정 코드 */
//	public static final String CD_PAGENATION = "100";
//	/** 페이징 Default 건수 */
//	public static final String DISP_PAGENATION_DEFAULT = CoCodeManager.getCodes(CD_PAGENATION).firstElement();
//	/** 페이징 선택 가능 건수 (grid에서 설정) */
//	public static final String DISP_PAGENATION_LIST_STR = CoCommonFunc.arrayToString(CoCodeManager.getCodes(CD_PAGENATION));


	/**
	 * 딜러 견적 요청, 견적요청종류 (시세문의)
	 */
	public static final String EST_TYPE_MARKET_PRICE = "2";

	/**
	 * Co Code Master - 대표 코드 [S]
	 */
	// -------------- 시스템 대표 코드
	/** 시스템 공통 상수 정의 */
	public static final String CD_SYSTEM_CONSTANT_VALUE								= "S100";
	/** 시스템 공통 상수 - 상세 코드 : 범용 1Page당 표시 레코드 수 */
	public static final String CD_SYSTEM_CONSTANT_VALUE_PAGE_SIZE					= "1";
	/** 시스템 공통 상수 - 상세코드 : 업로드 이미지 파일의 Max Width */
	public static final String CD_SYSTEM_CONSTANT_VALUE_IMAGE_W_MAX					= "2";
	/** 시스템 공통 상수 - 상세코드 : 업로드 아이콘 이미지 파일의 Max Width */
	public static final String CD_SYSTEM_CONSTANT_VALUE_ICON_IMAGE_W_MAX			= "3";
	/** 시스템 공통 상수 - 상세코드 : 게시글의 'New' 뱃지 표시일 */
	public static final String CD_SYSTEM_CONSTANT_VALUE_BOARD_BADGE_DAY_NEW			= "004";
	/** 시스템 공통 상수 - 상세코드 : 게시글의 '마감임박' 뱃지 표시일 */
	public static final String CD_SYSTEM_CONSTANT_VALUE_BOARD_BADGE_DAY_CLOSE		= "005";

	// -------------- 관리 코드
	/** 관리 코드 */
	public static final String CD_MGMT_MAIN = "130";
	/** 차량관리 코드 */
	public static final String CD_MGMT_SUB_CAR = "C100";
	/** 매매단지관리 코드 */
	public static final String CD_MGMT_SUB_MARKET = "C200";
	/** 시스템관리 코드 */
	public static final String CD_MGMT_SUB_SYSTEM = "C300";
	/** IndexedDB 버전 업데이트 */
	public static final String CD_MGMT_SUB_DB = "C400";
	// -------------- //

	/** 차량기초정보 코드 - 101 */
	public static final String CAR_CODE_DEF_INFO = "101";
	/** 차량 제조사 코드 - 124 */
	public static final String SYS_CODE_CAR_MAKER = "124";
	/** 옵션종류 코드 - 102 */
	public static final String SYS_CODE_CAR_OPTION_TYPE = "102";
	/** 옵션종류(기본) 코드 - 106 */
	public static final String SYS_CODE_CAR_OPTION_BASIC = "106";
	/** 옵션종류(외장옵션) 코드 - 107 */
	public static final String SYS_CODE_CAR_OPTION_EXTERNAL = "107";
	/** 옵션종류(내장옵션) 코드 - 108 */
	public static final String SYS_CODE_CAR_OPTION_INTERNAL = "108";
	/** 옵션종류(안전장치) 코드 - 109 */
	public static final String SYS_CODE_CAR_OPTION_SAFETY = "109";
	/** 옵션종류(편의장치) 코드 - 110 */
	public static final String SYS_CODE_CAR_OPTION_CONVENIENCE = "110";
	/** 옵션종류(멀티미디어) 코드 - 111 */
	public static final String SYS_CODE_CAR_OPTION_MEDIA = "111";
	/** 기본옵션 코드 - 112 */
	public static final String SYS_CODE_CAR_OPTION_DEFAULT = "112";
	/** 연료종류 코드 - 103 */
	public static final String SYS_CODE_CAR_FUEL_TYPE = "103";
	/** 연료종류 코드 (미분류) */
	public static final String CD_DTL_CAR_FUEL_TYPE_ETC = "0";
	/** 차량기초정보 코드 - 104 */
	public static final String SYS_CODE_CAR_MISSION_TYPE = "104";
	/** 차량기초정보 코드(변속기 - 기타) */
	public static final String CD_DTL_CAR_MISSION_ETC = "0";
	/** 차량기초정보 코드 - 105 */
	public static final String SYS_CODE_CAR_COLOR_TYPE = "105";
	/** 차량 색상 (기타) */
	public static final String CD_DTL_CAR_COLOR_ETC = "0";
	/** 매물상태 코드 - 200 */
	public static final String SYS_CODE_CAR_STATUS = "200";
	/** 18개 지역 코드 - 206 */
	public static final String SYS_CODE_EIGHTEEN_AREA = "206";
	/** 외관상태 코드 - 202 */
	public static final String SYS_CODE_CAR_EXT_STATUS = "202";
	/** 매물사고여부 코드 - 203 */
	public static final String SYS_CODE_CAR_ACC_STATUS = "203";
	/** 렌터카사용여부 코드 - 204 */
	public static final String SYS_CODE_CAR_RENT_STATUS = "204";

	public static final String SYS_CODE_RES_STATUS = "208";
	/** 딜러평가구분 코드 - 210 */
	public static final String SYS_CODE_DEALER_EVAL_DIV = "210";
	/** 예약요청구분 코드 - 211 */
	public static final String SYS_CODE_RES_TYPE= "211";
	/**예약요청구분 : 방문 */
	public static final String SYS_CODE_RES_TYPE_VISIT = "1";
	/** 예약요청구분 : 시승 */
	public static final String SYS_CODE_RES_TYPE_TEST = "2";
	/** 예약요청구분 : 탁송 */
	public static final String SYS_CODE_RES_TYPE_CONSIGN = "3";


	/**견적요청 종류 코드 - 209 */
	public static final String SYS_CODE_EST_TYPE= "209";
	/** 메이크업 서비스 종류 코드 - 212 */
	public static final String SYS_CODE_MAKEUP_STATUS= "212";
	/** 메이크업 서비스 종류 코드 - 213 */
	public static final String SYS_CODE_MAKEUP_OPTION= "213";

	/** 허위매물 종류 코드 - 215 */
	public static final String SYS_CODE_FAKE= "215";

	/** 답변 상태 코드 - 205 */
	public static final String SYS_CODE_QNA_STATUS= "205";

	/**견적요청 상태 코드 - 216 */
	public static final String SYS_CODE_EST_STATE= "216";

	/** 이메일 도메인 종류  - 301  */
	public static final String SYS_CODE_EMAIL_DOMAIN_TYPE    = "301";

	// -------------- SNC CARMARKET 직접참조 공통코드
	/** 공통코드 이전등록지역구분 - C0004 */
	public static final String SYS_CODE_COMM_REG_AREA = "C0004";
	/** 공통코드 차량 용도구분 - C0001 */
	public static final String SYS_CODE_COMM_CAR_USE_DIV = "C0001";
	/** 공통코드 차종구분(잔존율) - C0006 */
	public static final String SYS_CODE_COMM_CAR_DIV = "C0006";
	/** 공통코드 상세차종구분 - C0003 */
	public static final String SYS_CODE_COMM_CAR_DETAIL_DIV = "C0003";
	// -------------- //

	/** URI 코드         - 401  */
	public static final String CD_URI_CODE_TYPE      = "401";
	/** 카테고리 구분 코드         - 402  */
	public static final String CD_CATEGORY_TYPE      = "402";
	public static final String CD_CAR_REGIST_NORMAL      = "1";
	public static final String CD_CAR_REGIST_DEALER      = "2";
	public static final String CD_CAR_REGIST_MAKEUP      = "3";

	/** PUSH MESSAGE TYPE */
	public static final String CD_SND_PUSH_TYPE = "410";
	/** PUSH MESSAGE TYPE - 내개맞는 매물 */
	public static final String CD_SND_PUSH_FAV = "FAV001";
	/** PUSH MESSAGE TYPE - 구매비용계산 */
	public static final String CD_SND_PUSH_CALC = "CAL001";
	/** PUSH MESSAGE TYPE - 체크리스트 */
	public static final String CD_SND_PUSH_CHECKLIST = "CHK001";
	/** PUSH MESSAGE TYPE - 명함발송 */
	public static final String CD_SND_PUSH_NAMECARD = "NAM001";




//	/** PUSH MESSAGE TYPE - 방문 예약 신청 */
//	public static final String CD_SND_PUSH_REQ_VISIT = "RES101";
//	/** PUSH MESSAGE TYPE - 시승 예약 신청 */
//	public static final String CD_SND_PUSH_REQ_TEST = "RES102";
//	/** PUSH MESSAGE TYPE - 방문 / 시승 예약 신청 */
//	public static final String CD_SND_PUSH_REQ_VISIT_TEST = "RES103";
//	/** PUSH MESSAGE TYPE - 탁송 신청 */
//	public static final String CD_SND_PUSH_REQ_CONSIGN = "RES104";
//
//	/** PUSH MESSAGE TYPE - 방문 예약 신청 승인 */
//	public static final String CD_SND_PUSH_CONF_VISIT = "RES201";
//	/** PUSH MESSAGE TYPE - 시승 예약 신청 승인 */
//	public static final String CD_SND_PUSH_CONF_TEST = "RES202";
//	/** PUSH MESSAGE TYPE - 방문 / 시승 예약 신청 승인 */
//	public static final String CD_SND_PUSH_CONF_VISIT_TEST = "RES203";
//	/** PUSH MESSAGE TYPE - 방문 / 시승 예약 신청 승인 */
//	public static final String CD_SND_PUSH_CONF_CONSIGN = "RES204";




	/** 예약신청 - 방문 - 승인대기 RES101	${CAR_NUM} 차량의 방문이 승인대기되었습니다. */
	public static final String CD_SND_PUSH_RES_VISIT_10 = "RES101";
	/** 예약신청 - 방문 - 승인요청  RES102	${CAR_NUM} 차량의 방문이 승인요청되었습니다. */
	public static final String CD_SND_PUSH_RES_VISIT_11 = "RES102";
	/** 예약신청 - 방문 - 예약완료  RES103	${CAR_NUM} 차량의 방문이 예약완료되었습니다. */
	public static final String CD_SND_PUSH_RES_VISIT_20 = "RES103";
	/** 예약신청 - 방문 - 예약취소 RES104	${CAR_NUM} 차량의 방문이 예약취소되었습니다. */
	public static final String CD_SND_PUSH_RES_VISIT_90 = "RES104";
	/** 예약신청 - 방문 - 예약거절 RES105	${CAR_NUM} 차량의 방문이 예약거절되었습니다. */
	public static final String CD_SND_PUSH_RES_VISIT_91 = "RES105";
	/** 예약신청 - 방문 - 승인거절 RES106	${CAR_NUM} 차량의 방문이 승인거절되었습니다. */
	public static final String CD_SND_PUSH_RES_VISIT_92 = "RES106";

	/** 예약신청 - 시승 - 승인대기 RES201	${CAR_NUM} 차량의 시승이 승인대기되었습니다. */
	public static final String CD_SND_PUSH_RES_TEST_10 = "RES201";
	/** 예약신청 - 시승 - 승인요청  RES202	${CAR_NUM} 차량의 시승이 승인요청되었습니다. */
	public static final String CD_SND_PUSH_RES_TEST_11 = "RES202";
	/** 예약신청 - 시승 - 예약완료  RES203	${CAR_NUM} 차량의 시승이 예약완료되었습니다. */
	public static final String CD_SND_PUSH_RES_TEST_20 = "RES203";
	/** 예약신청 - 시승 - 예약취소 RES204	${CAR_NUM} 차량의 시승이 예약취소되었습니다. */
	public static final String CD_SND_PUSH_RES_TEST_90 = "RES204";
	/** 예약신청 - 시승 - 예약거절 RES205	${CAR_NUM} 차량의 시승이 예약거절되었습니다. */
	public static final String CD_SND_PUSH_RES_TEST_91 = "RES205";
	/** 예약신청 - 시승 - 승인거절 RES206	${CAR_NUM} 차량의 시승이 승인거절되었습니다. */
	public static final String CD_SND_PUSH_RES_TEST_92 = "RES206";

	/** 예약신청 - 탹송 - 승인대기 RES301	${CAR_NUM} 차량의 탁송이 승인대기되었습니다. */
	public static final String CD_SND_PUSH_RES_CONSIGN_10 = "RES301";
	/** 예약신청 - 탹송 - 승인요청  RES302	${CAR_NUM} 차량의 탁송이 승인요청되었습니다. */
	public static final String CD_SND_PUSH_RES_CONSIGN_11 = "RES302";
	/** 예약신청 - 탹송 - 예약완료  RES303	${CAR_NUM} 차량의 탁송이 예약완료되었습니다. */
	public static final String CD_SND_PUSH_RES_CONSIGN_20 = "RES303";
	/** 예약신청 - 탹송 - 예약취소 RES304	${CAR_NUM} 차량의 탁송이 예약취소되었습니다. */
	public static final String CD_SND_PUSH_RES_CONSIGN_90 = "RES304";
	/** 예약신청 - 탹송 - 예약거절 RES305	${CAR_NUM} 차량의 탁송이 예약거절되었습니다. */
	public static final String CD_SND_PUSH_RES_CONSIGN_91 = "RES305";
	/** 예약신청 - 탹송 - 승인거절 RES306	${CAR_NUM} 차량의 탁송이 승인거절되었습니다. */
	public static final String CD_SND_PUSH_RES_CONSIGN_92 = "RES306";


	/** PUSH MESSAGE TYPE - 문의사항 */
	public static final String CD_SND_PUSH_ANS_QNA = "QNA001";
	/** PUSH MESSAGE TYPE - MAKEUP 요청 */
	public static final String CD_SND_PUSH_REQ_MAKEUP = "MAK101";
	/** PUSH MESSAGE TYPE - MAKEUP 승인 */
	public static final String CD_SND_PUSH_CONF_MAKEUP = "MAK201";
	/** PUSH MESSAGE TYPE - 딜러견적 OOOO차량의 견적결과가 도착하였습니다. */
	public static final String CD_SND_PUSH_REQ_EST_DEALER = "EST001";
	/** PUSH MESSAGE TYPE - 방문견적 */
	public static final String CD_SND_PUSH_REQ_EST_VISIT = "EST002";


	/** 최대가격 코드 - 501 */
	public static final String SYS_CODE_MAX_PRICE = "501";
	/** 주행거리 코드 - 502 */
	public static final String SYS_CODE_VEHICLE_MILE = "502";
	/** 국외산구분 코드 - 503 */
	public static final String CD_MAKER_GUBN = "503";

	/** 오토다이렉트 도메인 */
	public static final String AUTO_DIRECT_DOMAIN = "http://www.bnkcapital.co.kr/view/autodirect/pc/AutomoaDirectForwarding.jsp";
	/** 오토모바일 도메인 */
	public static final String AUTO_MOBILE_DOMAIN = "https://www.bnkcapital.co.kr/view/autodirect/AutoMobileForwarding.jsp";

	/** KMC 본인인증 URL */
	public static final String KMC_URL_CD_MEMBER_AGREE = "001001";			// 회원가입 - 일반
	public static final String KMC_URL_CD_MEMBER_AGREE_DEALER = "001003";	// 회원가입 - 딜러
	public static final String KMC_URL_CD_PASSWORD_RESET_BEFORE = "001004";	// ID/PW찾기 - 이전
	public static final String KMC_URL_CD_MEMBER_MODIFY = "001007";			// ID/PW찾기 수정 - 일반
	public static final String KMC_URL_CD_MEMBER_MODIFY_DEALER = "001006";	// ID/PW찾기 수정 - 딜러
	public static final String KMC_URL_RESULT_PAGE = "/front/common/kmcResult";	// KMC 결과페이지

	/** KMC 본인인증 URL PC */
	public static final String PC_KMC_URL_CD_MEMBER_AGREE = "001008";			// 회원가입 - 일반
	public static final String PC_KMC_URL_CD_MEMBER_AGREE_DEALER = "001009";	// 회원가입 - 딜러
	public static final String PC_KMC_URL_CD_PASSWORD_RESET_BEFORE = "001011";	// ID/PW찾기 - 이전
	public static final String PC_KMC_URL_CD_MEMBER_MODIFY = "001012";			// ID/PW찾기 수정 - 일반
	public static final String PC_KMC_URL_CD_MEMBER_MODIFY_DEALER = "001010";	// ID/PW찾기 수정 - 딜러
	public static final String PC_KMC_URL_RESULT_PAGE = "https://bnkautomoa.co.kr/product/co/memberJoinPopup";	// KMC 결과페이지
	//public static final String PC_KMC_URL_RESULT_PAGE = "http:/bnkautomoa.co.kr/product/co/memberJoinPopup";	// KMC 결과페이지

	/** APP DEVICE 정보 */
	public static final String CD_DEVICE_VER_ANDROID = "600";
	public static final String CD_DEVICE_VER_IOS = "601";


	/** 액션 코드 */
	public static final String	ACTION_CODE_INSERT = "INSERT";
	public static final String	ACTION_CODE_UPDATE = "UPDATE";
	public static final String	ACTION_CODE_DELETE = "DELETE";

	/** 시스템 공통 상수 정의 Enum */
	public enum CD_SYSTEM_CONSTANT_VALUE {
    	/** 상세코드 : 범용 1Page당 표시 레코드 수 */
    	PAGE_SIZE(CD_SYSTEM_CONSTANT_VALUE_PAGE_SIZE),
    	/** 상세코드 : 업로드 이미지 파일의 Max Width */
    	IMAGE_W_MAX(CD_SYSTEM_CONSTANT_VALUE_IMAGE_W_MAX),
    	/** 상세코드 : 업로드 아이콘 이미지 파일의 Max Width */
    	ICON_IMAGE_W_MAX(CD_SYSTEM_CONSTANT_VALUE_ICON_IMAGE_W_MAX),
    	/** 상세코드 : 게시글의 'New' 뱃지 표시일 */
    	BOARD_BADGE_DAY_NEW(CD_SYSTEM_CONSTANT_VALUE_BOARD_BADGE_DAY_NEW),
    	/** 상세코드 : 게시글의 '마감임박' 뱃지 표시일 */
    	BOARD_BADGE_DAY_CLOSE(CD_SYSTEM_CONSTANT_VALUE_BOARD_BADGE_DAY_CLOSE);
    	/** */
    	private String value;
    	private CD_SYSTEM_CONSTANT_VALUE(String value) {this.value = value;}
    	public String getValue(){return this.value;}
    }



	// -------------- 서브메뉴 대표 코드
	/** 대메뉴 */
	public static final String CD_MENU_MAIN 									= "M001";

	/** 서브메뉴 - Main */
	public static final String CD_MENU_SUB_MAIN									= "M100";

	/** 서브메뉴 - 게시판 관리 */
	public static final String CD_NEWS_SUB_BOARD		 						= "M600";

	/** 캠페인 참여 경로 코드 **/
	public enum CAMPAIGN_ROUTE {
		RECOMMEND	("01"),		// 기업 및 지인 추천
		SNS			("02"),		// SNS 채널
		NOTICE		("03"),		// 각종 홈페이지 공지사항
		BROCHURE	("04"),		// 브로셔 홍보
		ETC			("99");		// 기타

		private String value;
		private CAMPAIGN_ROUTE(String value) {this.value = value;};
		public String getValue() {return this.value;};
	}
}
