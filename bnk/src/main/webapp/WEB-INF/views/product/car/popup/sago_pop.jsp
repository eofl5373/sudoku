<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
      <!-- 2017-09-14 사고이력정보 보기 -->
<div class="popupAutoWrap carAccident p-container w670">
    <!-- popup header -->
    <div class="popupHeaderAuto">
        <header><h1>중고차 사고이력정보 보고서</h1></header>
        <a class="btnClose p-close" onclick="return false;"><em class="blind">닫기</em></a>
    </div>
    <hr/>
    <!-- popup contents -->
    <div class="popupContents">
        <section>
            <div class="autoPop">
                <div class="carNum">
                	<strong>${sago.CARNUM}</strong>
                	<span>정보조회일자 : ${sago.SEARCHDATE}</span>
                </div>
                <div class="basicWrite">
                    <table summary="자동차 일반 사양정보 목록">
                        <colgroup>
                            <col width='30%'>
                            <col width='70%'>
                        </colgroup>
                        <caption>자동차 일반 사양정보 목록</caption>
                        <thead>
                        	<tr>
                        		<th colspan="2">자동차 일반 사양정보</th>
                        	</tr>
                        </thead>
                        <tbody>
                            <tr>
                                <th>자동차명</th>
                                <td>${sago.CARNAME}</td>
                            </tr>
                            <tr>
                                <th>연식</th>
                                <td>${sago.CARMODEL}</td>
                            </tr>
                            <tr>
                                <th>사용연료</th>
                                <td>${sago.USEFUEL}</td>
                            </tr>
                            <tr>
                                <th>배기량</th>
                                <td>${sago.DISPLACE}</td>
                            </tr>
                            <tr>
                                <th>차체형상</th>
                                <td>${sago.CARSHAPE}</td>
                            </tr>
                            <tr>
                                <th>용도 및 차종</th>
                                <td>${sago.CARUSED}</td>
                            </tr>
                            <tr>
                                <th>최초 보험 가입일자</th>
                                <td>${sago.JOINDATE}</td>
                            </tr>
                        </tbody>
                    </table>
                    <table summary="자동차 사고이력 정보요약 목록">
                        <colgroup>
                            <col width='30%'>
                            <col width='70%'>
                        </colgroup>
                        <caption>자동차 사고이력 정보요약</caption>
                        <thead>
                        	<tr>
                        		<th colspan="2">자동차 사고이력 정보요약</th>
                        	</tr>
                        </thead>
                        <tbody>
                            <tr>
                                <th>내차 피해</th>
                                <td>${sago.MCARDAMAGE}</td>
                            </tr>
                            <tr>
                                <th>상대 차 피해</th>
                                <td>${sago.OCARDAMAGE}</td>
                            </tr>
                            <tr>
                                <th>자동차 용도변경이력</th>
                                <td>${sago.CARDESAFE}</td>
                            </tr>
                            <tr>
                                <th>자동차 번호 변경횟수</th>
                                <td>${sago.CARNUMCHG}회</td>
                            </tr>
                            <tr>
                                <th>소유자 변경횟수</th>
                                <td>${sago.CAROWNERCHG}회</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="btnAreaType">
                    <span><button class="line p-close">닫기</button></span>
                </div>
            </div>
        </section>
    </div>
</div>
<!-- //2017-09-14 사고이력정보 보기 --> 