<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<link rel="stylesheet" href="<c:url value="/product/js/rating/star/rateit.css?latest=${cssVersion}"/>" media="all" type="text/css"/>
<script type="text/javascript" src="<c:url value="/product/js/rating/star/jquery.rateit.js?latest=${jsVersion}"/>"></script>

<div class="dataSet">
    <h2 class="dpn">판매자 거래평가</h2>
    <div class="dataItem">
        <div class="dealerRating">
            <span>종합 거래평가</span><div class="rating" data-rateit-readonly="true" data-rateit-value="${empty user.evalAvg ? '0' : user.evalAvg}"></div><em class="point">${empty user.evalAvg ? '0' : user.evalAvg}</em>
            <button onclick="javascript:util.loginCheck('POPUP', 'pop_evaluate_regist')">평가작성하기<i></i></button>
            <div id="pop_evaluate_regist" style="display:none;" class="btn-popup-auto" data-pop-opts='{"target": ".appraisePop"}'></div>
        </div>
        <!---->
        <div class="dealerAfter">
            <div class="btn-accordion-wrapper" data-toggle-on="true">
                <ul>
                	<c:if test="${empty user.dealerEvalList}">
                		<li><div class="afterTit">거래 평가 결과가 없습니다.</div></li>
                	</c:if>
                	<c:forEach var="ev" items="${user.dealerEvalList}">
                		<fmt:parseDate value="${ev.createdDate}" pattern="yyyy-MM-dd" var="createdDate"/>
	                    <li class="btn-accordion-switch">
	                        <div class="afterTit">
	                            <div class="btn-accordion-switch-item">
	                                <strong>${ev.evalDivLabel}</strong>
	                                <div>${ev.reviews}</div>
	                                <span><fmt:formatDate value="${createdDate}" type="date" pattern="yyyy-MM-dd" /></span>
	                            </div>
	                        </div>
	                        <div class="afterDetail">
	                            ${ev.reviews}
	                        </div>
	                    </li>
                	</c:forEach>
                </ul>
            </div>
        </div>
        <!---->
        <paginatorAjax:print fncName="evaluate_list" curPage="${curPage}" totPages="${totPages}"/>
        <!---->
        <div class="btnSet dealerAfterbtn">
            <span>
            	<a class="redLine" onclick="javascript:util.loginCheck('POPUP', 'pop_reserve_visit')"><em><strong>사전방문/시승 예약</strong></em></a>
            	<input id="pop_reserve_visit" type="hidden" class="btn-popup-auto" data-pop-opts='{"target": ".visitReser"}'/>
            </span>
            <span>
            	<a class="redLine" onclick="javascript:util.loginCheck('POPUP', 'pop_reserve_consign')"><em><strong>탁송예약</strong></em></a>
            	<input id="pop_reserve_consign" type="hidden" class="btn-popup-auto" data-pop-opts='{"target": ".consigReser"}'/>
           	</span>
        </div>
    </div>
</div>