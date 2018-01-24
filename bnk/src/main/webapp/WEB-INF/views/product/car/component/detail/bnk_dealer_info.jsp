<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="innerLayout bgGrayCase">
    <div class="dataSet dealerInfo">
        <h2 class="dpn">판매자정보</h2>
        <div class="dataItem">
            <ul>
                <li><strong>연락처</strong><span>${car.user.dealerVirtualNum eq null ? car.user.phoneNumMask : car.user.dealerVirtualNum}</span></li>
                <li><strong>회원가입일</strong>
                <span>
                <fmt:parseDate value="${car.user.createdDate}" pattern="yyyy-MM-dd" var="createdDate"/>
                <fmt:formatDate value="${createdDate}" type="date" pattern="yyyy년 MM월 dd일" />
                </span></li>
                <li><strong>딜러매물</strong><span>판매중 <a class="btn-popup-auto" onclick="fn_saleCar();" data-pop-opts='{"target": ".sellPop"}'>${car.user.saleCarCnt}</a>대, 판매완료 ${car.user.saleCnt}대</span></li>
                <li><strong>종사자번호</strong><span>${car.user.dealerLicenseNo}</span></li>
                <li><strong>지역/상사</strong><span>${car.user.marketInfo.danjisido}&nbsp;${car.user.marketInfo.danjicity}&nbsp;${car.user.marketInfo.danjifullname}</span></li>
            </ul>
            <div id="map" style="width:100%;height:320px;"></div>
            <script>
            var map = new naver.maps.Map('map', {
                center: new naver.maps.LatLng(${car.user.marketInfo.shop.shopLocLat},${car.user.marketInfo.shop.shopLocLng}),
                zoom: 10
            });

            var marker = new naver.maps.Marker({
                position: new naver.maps.LatLng(${car.user.marketInfo.shop.shopLocLat},${car.user.marketInfo.shop.shopLocLng}),
                map: map
            });
            </script>
        </div>
    </div>
</div>