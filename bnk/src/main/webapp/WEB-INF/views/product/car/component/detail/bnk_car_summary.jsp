<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript" src="<c:url value="/product/js/common/basic.js?latest=${jsVersion}"/>"></script>
<div class="carSummary">
    <h2 class="dn">매물정보</h2>
    <div class="priceLarge">
        <div class="inner">
            <p class="cartit"><i>${car.label.makerName}</i> ${car.label.modelDtlName}</p>
            <span class="carbtn">
                <strong><fmt:formatNumber value="${car.saleAmt}" groupingUsed="true"/> <em>만원</em></strong>
				<!-- 2017-10-17 -->
				<script>
					$(document).ready(function(){
						$('.insOpen').click(function () {
							$('.insArea div').slideDown();
						});
						$('.insClose').click(function () {
							$('.insArea div').slideUp();
						});
					});
				</script>
				<div class="insArea">
					<button class="insOpen">보험료계산</button>
					<div style="display:none">
						<a href="http://direct.kbinsure.co.kr/websquare/promotion.jsp?pid=1090049&code=0197&page=step1" target="_blank"><img src="<c:url value="/product/images/sub/img_ins1.png"/>" alt="KB손해보험"/></a>
						<a href="http://adcnc.co.kr/net/link_all/lottehowmuch/hub/front_tabogo.asp?a_id=tabogo1" target="_blank"><img src="<c:url value="/product/images/sub/img_ins2.png"/>" alt="롯데손해보험"/></a>
						<a href="https://www.hanwhadirect.com/landing.do?cmpgId=1000000338" target="_blank"><img src="<c:url value="/product/images/sub/img_ins3.png"/>" alt="한화손해보험"/></a>
						<a href="https://www.meritzdirect.com/DAP1707CA000013/southeaster.do" target="_blank"><img src="<c:url value="/product/images/sub/img_ins4.png"/>" alt="메리츠화재"/></a>
						<button class="insClose">닫기</button>
					</div>
				</div>
				<!-- 2017-10-17 -->
               <!--  <a href="http://adcnc.co.kr/net/link_all/lottehowmuch/hub/front_tabogo.asp?a_id=tabogo1" target="_blank">보험료 계산</a><!-- 롯데손해보험 -->
                <a href="<c:url value="/product/car/bnkcapital/direct?carSeq=${car.carSeq}"/>" target="_blank">대출한도조회</a>
            </span>
        </div>
    </div>
    <div class="thumLargeBack">
	    <div class="tLBLayout"> <!-- 2017-08-31 by mj-cho -->
	        <c:if test="${car.carExistYn=='Y'}">
	    		<span class="tagOk">실매물확인완료</span> <!-- 2017-08-19 -->
	        </c:if>
	        <c:if test="${car.resCnt > 0}">
				<div class="bcBack"><span class="bookingCount"><strong>${car.resCnt}</strong>건</span></div> <!-- 2017-08-19 -->
	        </c:if>
			<div class="markSetPos"> <!-- 2017-09-12 by mj-cho -->
		        <c:if test="${car.user.bnkConfYn=='Y'}">
		           <span class="markSet lC mark1">인증</span>
		        </c:if>
		        <c:if test="${car.carGuarRefundYn=='Y'}">
		           <span class="markSet lC mark2 btn-popup-auto" data-pop-opts='{"target": ".popWrapRefund"}'>환불</span>
		        </c:if>
		        <c:if test="${car.carGuarFruitlessYn=='Y'}">
		           <span class="markSet lC mark3 btn-popup-auto" data-pop-opts='{"target": ".popWrapSickness"}'>헛걸음</span>
		        </c:if>
		        <c:if test="${car.carGuarTermYn=='Y'}">
		           <span class="markSet lC mark4 btn-popup-auto" data-pop-opts='{"target": ".popWrapYear"}'>연장</span>
		        </c:if>
	        </div>
	        <div class="swiperTypeNumber" >
	            <ul class="swiper-wrapper">
	            	<c:if test="${empty car.imgInfoList}">
	                   <li class="swiper-slide">
	                   	<div><span><img src="<c:url value="/product/images/thumbnail/shift_car_001.jpg"/>"/></span></div><!-- 2017-08-31 by mj-cho -->
	                   </li>
	            	</c:if>
	            	<c:forEach var="img" items="${car.imgInfoList}">
	                   <li class="swiper-slide">
	                   	<div><span><img src="<c:url value="${img.pImgPath}"/>" onerror='this.src="<c:url value="/product/images/thumbnail/shift_car_001.jpg"/>"'/></span></div><!-- 2017-08-31 by mj-cho -->
	                   </li>
	            	</c:forEach>
	            </ul>
	            <!-- 이전 다음 버튼  -->
	            <a href="#" class="swiper-btn swiper-button-prev"></a>
	            <a href="#" class="swiper-btn swiper-button-next"></a>
	            <div class="swiper-pagination"></div>
	        </div>
	    </div>

         <!-- 딜러정보 -->
         <div class="dealerArea">
             <div class="dataItem">
                 <span class="heartSet" data-htype="inst" data-dealer="${car.userId}"><input type="checkbox" id="summary_2" ${car.user.dealerInterestYn=='Y' ? 'checked' : '' } onclick="return false;"/><label for="summary_2"><!--찜하기--></label></span>
                 <div class="dealerProfile">
			<div class="profileImage">
				<img src="<c:url value="/product/images/thumbnail/profileCorver3.png"/>" alt="" class="corver">
				<div class="imgBack"><span><img src="${car.user.dealerProfileImgPath}" onerror="this.src='<c:url value="/product/images/thumbnail/profile1.png"/>'" alt="" /></span></div>
			</div>
                     <div class="dealName"><i class="levelBadge level${not empty car.user.gradeDealer ? car.user.gradeDealer : '1' }"></i><strong>${car.user.userName}</strong> 딜러</div>
                     <p class="dealInfo">
                         <span>${car.user.dealerDanjiName}</span>
                         <span><em>판매중 <strong class="btn-popup-auto" onclick="fn_saleCar();" data-pop-opts='{"target": ".sellPop"}'>${car.user.saleCarCnt}</strong>대</em><em>판매완료${car.user.saleCnt} 대</em></span>
                         <span class="dealPhone">${car.user.dealerVirtualNum eq null ? car.user.phoneNumMask : car.user.dealerVirtualNum}</span>
                     </p>
                 </div>
                 <div class="btnSet each">
					 <span class="left"><a class="redLine btn-popup-auto" onclick="javascript:util.loginCheck('POPUP', 'pop_reserve_visit')"><em>방문시승예약</em></a></span>
					 <span class="right"><a class="redLine btn-popup-auto" onclick="javascript:util.loginCheck('POPUP', 'pop_reserve_consign')"><em>탁송신청</em></a></span>
                     <span class="mb10">
                     	<a class="blackLine" onclick="javascript:util.loginCheck('POPUP', 'pop_declare_pop')"><em>허위매물신고</em></a>
                     	<span id="pop_declare_pop" class="btn-popup-auto" data-pop-opts='{"target": ".declarAlert"}'></span>
                     </span>
                     <span>
                     	<a class="redLine" onclick="clickTrigger(event)">
                     		<em>
                     			<i class="iconzzim">
                     				<input type="checkbox" id="summary_21" ${car.dibsYn=='Y' ? 'checked' : ''} onclick="return false;"/>
                     				<label for="summary_21"><!--찜하기--></label>
                     			</i>관심차량등록
                     		</em>
                     	</a>
                 		<input type="hidden" id="hHeart" onclick="javascript:util.loginCheck('DIBS_ON', ${car.carSeq}, '#hHeart')"/>
                     </span>
                 </div>
             </div>
         </div>
         <!-- //딜러정보 -->
    </div>
</div>
<script>
function clickTrigger(event){
	$("#hHeart").trigger('click');
}
</script>