<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- 추천차량 -->
<c:if test="${not empty recommend.list}">

        <h2>추천 차량 <em>비슷한 차종 차량</em></h2>
        <div class="dataItem">
            <ul class="listType1 suggestion">
            	<c:forEach var="rec" items="${recommend.list}" varStatus="status">
                <li>
                    <div class="thumBack">
                        <a href="<c:url value="/product/car/detail/${rec.carSeq}"/>">
                        	<c:if test="${rec.bnkConfYn=='Y'}"> <span class="markSet mark1">인증</span></c:if>
                        	<!-- 2017-08-21 -->
                        	<span class="saleCar">
                        		<span class="imgBack"><strong><img src="<c:url value="${rec.itemImgInfo.pThumbPath}"/>" onerror='this.src="<c:url value="/product/images/thumbnail/sample1.png"/>"'/></strong></span>
								<img src='<c:url value="/product/images/thumbnail/listCarCorver.png"/>' alt="" class="corver" />
<%--                             <img src="<c:url value="${rec.itemImgInfo.pThumbPath}"/>" onerror='this.src="<c:url value="/product/images/thumbnail/sample1.png"/>"'/> --%>
                        	</span>
                        	<!-- //2017-08-21 -->
                        </a>
                    </div>
                    <div class="prBack">
                        <a href="<c:url value="/product/car/detail/${rec.carSeq}"/>">
                            <span class="productInfo">
                                <span class="tit"><strong>${rec.label.makerName}</strong><span>${rec.label.modelDtlName}</span></span>
                                <span class="option"><em>${rec.carRegYear}</em><em>${rec.carArea}</em><em><fmt:formatNumber value="${rec.useKm}" groupingUsed="true"/> km</em></span>
                            </span>
                            <span class="markGroup">
                            	<c:if test="${rec.carGuarRefundYn=='Y'}"> <span class="markSet mark2">환불</span> </c:if>
                            	<c:if test="${rec.carGuarFruitlessYn=='Y'}"> <span class="markSet mark3">헛걸음</span> </c:if>
                            	<c:if test="${rec.carGuarTermYn=='Y'}"> <span class="markSet mark4">연장</span> </c:if>
                                <strong class="goodsPrice"><fmt:formatNumber value="${rec.saleAmt}" groupingUsed="true"/><i>만원</i></strong>
                            </span>
                        </a>
                    </div>
                    <span class="heartSet" data-htype="dibs" data-seq="${rec.carSeq}"><input type="checkbox" id="recommend_${status.index}" ${rec.dibsYn=='Y' ? 'checked' : ''} onclick="return false;"/><label for="recommend_${status.index}"><!--찜하기--></label></span>
                </li>
            	</c:forEach>
            </ul>
        </div>
</c:if>