<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<ul class="selling control">
	<c:forEach var="carList" items="${saleList.data.list}" varStatus="status">
	    <li>
	        <a href="/product/car/detail/${carList.carSeq}">
	            <span class="photoImg">
<!-- 	            	<img src="../../images/thumbnail/sample2.png" alt="" > -->
	            	<img src="<c:url value="${carList.itemImgInfo.mThumbPath}"/>" style="width:100%;display:block;" onerror='this.src="<c:url value="/product/images/thumbnail/sample2.png"/>"'/>
	            </span>
	            <span class="infoTxt">
	                <span class="tit"><strong>${carList.label.makerName}</strong><span>${carList.label.makerName}</span><span>${carList.label.modelDtlName}</span></span>
	                <span class="option"><em>${carList.carRegYear}</em><em>${carList.carArea}</em><em><fmt:formatNumber value="${carList.useKm}" pattern="#,###" />km</em></span>
	            </span>
	        </a>
	        <strong class="goodsPrice"><fmt:formatNumber value="${carList.saleAmt}" pattern="#,###" /><i>만원</i></strong>
	        <span class="markGroup">
	            <c:if test="${carList.carGuarRefundYn == 'Y'}">
                    <span class="markSet mark2">환불</span>
                </c:if>
                <c:if test="${carList.carGuarFruitlessYn == 'Y'}">
                    <span class="markSet mark3">헛걸음</span>
                </c:if>
                <c:if test="${carList.carGuarTermYn == 'Y'}">
                     <span class="markSet mark4">연장</span>
                </c:if>
	        </span>
	        <span class="heartSet">
	        	<input type="checkbox" id="ha1_${status.count }" <c:if test="${carList.dibsYn eq 'Y' }">checked="checked"</c:if> onclick="javascript:util.loginCheck('DIBS_ON', ${carList.carSeq}, this);">
	        	<label for="ha1_${status.count }"><!--찜하기--></label>
	        </span>
	    </li>
	</c:forEach>
</ul>
<input type="hidden" value="${totSize}" class="dealerSaleCnt" />
<c:if test="${fn:length(saleList.data.list) != 0}">
		<div class="pagingBtn">
			<paginatorAjax:print fncName="dealer_sale_list" curPage="${curPage}" totPages="${totPages}"/>
		</div>
</c:if>
<!---->
