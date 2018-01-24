<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- 방문사전예약 -->
<div class="popupAutoWrap visitReser p-container w670">
    <!-- popup header -->
    <div class="popupHeaderAuto">
        <header><h1>사전방문/시승 예약</h1></header>
        <a class="btnClose p-close" onclick="return false;"><em class="blind">닫기</em></a>
    </div>
    <hr/>
    <!-- popup contents -->
    <div class="popupContents">
        <section>
            <div class="autoPop">
                <div class="basicWrite">
                    <table summary="정보입력 화면입니다.">
                        <colgroup>
                            <col style="width:25%;">
                            <col style="width:75%;">
                        </colgroup>
                        <caption>정보입력</caption>
                        <tbody>
                            <!-- 2017-08-19 연락처 삭제 -->
                            <!-- 2017-09-07  -->
                            <tr>
                                <th><label for="">예약 구분</label></th>
                                <td class="kindSelect">
									<input type="checkbox" name="reserve_type" id="k1" value="1" checked="checked"/><label for="k1" >방문</label>
									<input type="checkbox" name="reserve_type" id="k2" value="2" /><label for="k2" >시승</label>
                                </td>
                            </tr>
                            <!-- //2017-09-07  -->
                            <tr>
                                <th><label for="">예약 날짜</label></th>
                                <td>
                                    <select name="reserve_year" class="reserve_date w100" ></select>
                                    <select name="reserve_month" class="reserve_date w100" ></select>
                                    <select name="reserve_day" class="reserve_date w100 ml7"></select>
                                    <select name="reserve_time" class="reserve_date w100 ml7"></select>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="reserInfo">
                    <p class="mb5"><strong>${car.user.marketInfo.shop.shopfullname} | ${car.user.marketInfo.danjifullname}</strong></p>
                    <p class="mb5">${car.user.marketInfo.shop.shopaddr}</p>
                    <p class="fs18"><i>${car.user.marketInfo.shop.shopphone}</i></p>
                </div>
                <div class="btnAreaType">
                    <span><button class="line p-close">취소</button></span>
                    <span><button type="button" onclick="reserve_regist(this)">예약</button></span>
                </div>
            </div>
        </section>
    </div>
</div>
<!-- //방문사전예약 -->

<!-- 탁송예약 -->
<div class="popupAutoWrap consigReser p-container w670">
    <!-- popup header -->
    <div class="popupHeaderAuto">
        <header><h1>탁송예약</h1></header>
        <a class="btnClose p-close" onclick="return false;"><em class="blind">닫기</em></a>
    </div>
    <hr/>
    <!-- popup contents -->
    <div class="popupContents">
        <section>
            <div class="autoPop">
                <div class="basicWrite">
                    <table summary="정보입력 화면입니다.">
                        <colgroup>
                            <col style="width:25%;">
                            <col style="width:75%;">
                        </colgroup>
                        <caption>정보입력</caption>
                        <tbody>
                            <tr>
                                <th><label for="">예약 날짜</label></th>
                                <td>
                                    <select name="reserve_year" class="reserve_date w100" ></select>
                                    <select name="reserve_month" class="reserve_date w100" ></select>
                                    <select name="reserve_day" class="reserve_date w100 ml7"></select>
                                    <select name="reserve_time" class="reserve_date w100 ml7"></select>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
				<!-- 2017-08-19 -->
				<div class="myWrite">
					<div class="writeRow type1">
						<strong><label for="btnAddr">우편번호</label></strong>
						<span><input type="text" value="${sessUserInfo.zipCode}" id="zipCode" name="reserve_zip" placeholder="우편번호" readonly></span>
						<span><button class="btn-popup-auto" id="btnAddr" onclick="return false;" data-pop-opts='{"target": ".findAddress"}'>주소검색</button></span><!-- 2017-07-19 -->
					</div>
					<div class="writeRow type1">
						<strong><label for="addr1">기본주소</label></strong>
						<span><input type="text" value="${sessUserInfo.addr1}" id="addr1" name="reserve_addr1" placeholder="주소" readonly></span>
					</div>
					<div class="writeRow type1 last">
                        <strong><label for="addr2">상세주소</label></strong>
						<span><input type="text" value="${sessUserInfo.addr2}" id="addr2" name="reserve_addr2" placeholder="상세주소"></span>
                    </div>
				</div>
				<!-- //2017-08-19 -->
                <div class="btnAreaType">
                    <span><button class="line p-close">취소</button></span>
                    <span><button type="button" onclick="reserve_regist(this, '3')">예약</button></span>
                </div>
            </div>
        </section>
    </div>
</div>
<!-- //탁송예약 -->