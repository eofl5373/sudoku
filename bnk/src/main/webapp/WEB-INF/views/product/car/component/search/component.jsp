<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="component_list">
<c:if test="${empty list || fn:length(list) == 0 }">
    <!-- css 맞쳐 추후 수정 -->
    <li class="noneLine">검색결과 등록된 매물이 없습니다.</li><!-- 2017-09-01 검색결과 없을 때 -->
</c:if>
<c:forEach var="car" items="${list}" varStatus="status">
<li>
	<div class="listSet">
		<div class="thumBack">
			<a href="<c:url value="/product/car/detail/${car.carSeq}"/>" >
				<c:if test="${ct:equals(car.bnkConfYn, 'Y')}">
					<span class="markSet mark1">인증</span>
				</c:if>
				<c:if test="${car.resCnt > 0}">
					<span class="bookingCount">예약 <strong>${car.resCnt}</strong>건</span> <!-- 2017-08-19 if문추가필요-->
				</c:if>
				<span class="saleCar">
					<span class="imgBack">
						<strong><img src="<c:url value="${car.itemImgInfo.pImgPath}"/>" onerror='this.src="<c:url value="/product/images/thumbnail/sample1.png"/>"'/></strong>
					</span>
					<img src="<c:url value="/product/images/thumbnail/listCarCorver.png"/>" alt="" class="corver" />
				</span>
			</a>
		</div>
		<div class="prBack">
			<a href="<c:url value="/product/car/detail/${car.carSeq}"/>" >
				<span class="productInfo">
					<span class="tit"><strong>${car.label.makerName}</strong><span>${car.label.modelDtlName}</span><span>${car.label.gradeName}</span></span>
					<span class="option"><em>${car.label.carRegYear}</em><em>${car.carArea}</em><em><fmt:formatNumber value="${car.label.useKm}" groupingUsed="true"/> km</em></span>
				</span>
				<span class="markGroup">
					<c:if test="${ct:equals(car.carGuarRefundYn, 'Y')}">
						<span class="markSet mark2" >환불</span>
					</c:if>
					<c:if test="${ct:equals(car.carGuarFruitlessYn, 'Y')}">
						<span class="markSet mark3" >헛걸음</span>
					</c:if>
					<c:if test="${ct:equals(car.carGuarTermYn, 'Y')}">
						<span class="markSet mark4" >연장</span>
					</c:if>
					<strong class="goodsPrice"> <fmt:formatNumber value="${car.saleAmt}" groupingUsed="true"/> <i>만원</i></strong>
				</span>
			</a>
		</div>
		<span class="heartSet"><input type="checkbox" id="component_${car.carSeq}" ${car.dibsYn=='Y' ? 'checked' : ''} onclick="javascript:util.loginCheck('DIBS_ON', ${car.carSeq}, this)"/>
		<label for="component_${car.carSeq}"><!--찜하기--></label></span>
	</div>
	<img src="<c:url value="/product/images/thumbnail/listCorver.png"/>" alt="" class="listCorver" />
</li>
</c:forEach>
</div>
