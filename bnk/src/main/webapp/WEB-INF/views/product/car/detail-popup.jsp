<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:include page="popup/evaluate_regist_pop.jsp" flush="false" />		<!-- 딜러 평가 등록 팝업 -->
<jsp:include page="popup/car_refund_pop.jsp" flush="false" />			<!-- 환불보장 팝업 -->
<jsp:include page="popup/car_sickness_pop.jsp" flush="false" />			<!-- 헛걸음 보상 팝업 -->
<jsp:include page="popup/car_year_pop.jsp" flush="false" />				<!-- 연장 팝업 -->
<jsp:include page="popup/car_declare_pop.jsp" flush="false"><jsp:param value="${car}" name="car"/></jsp:include>	<!-- 허위매물신고 팝업 -->
<jsp:include page="popup/car_reserve_pop.jsp" flush="false"><jsp:param value="${car}" name="car"/></jsp:include>	<!-- 방문시승탁송 팝업 -->
<jsp:include page="popup/dealer_sale_pop.jsp"></jsp:include>
<jsp:include page="popup/car_reauction_pop.jsp" flush="false"><jsp:param value="${reverse}" name="reverse"/></jsp:include>	<!-- 내게맞는매물 팝업 -->
<jsp:include page="popup/address_pop.jsp" flush="false"><jsp:param value="${reverse}" name="reverse"/></jsp:include>	<!-- 주소 팝업 -->
<jsp:include page="popup/sago_pop.jsp" flush="false" />				<!-- 사고이력정보 팝업 -->