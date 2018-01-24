<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="innerLayout">
    <div class="dataSet carInfo">
        <h2>차량정보</h2>
        <div class="dataItem">
            <dl>
                <dt class="first">차량번호</dt>
                <dd class="first">${car.carPlateNum}</dd>
                <dt>연식</dt>
                <dd>${car.carRegYear}</dd>
                <dt>주행거리</dt>
                <dd><fmt:formatNumber value="${car.useKm}" groupingUsed="true"/>km</dd>
                <dt class="last">변속기</dt>
                <dd class="last">${ct:getCodeString(ct:getConstDef('SYS_CODE_CAR_MISSION_TYPE'), car.carMission)}</dd>
            </dl>
            <dl>
                <dt class="first">연료</dt>
                <dd class="first">${ct:getCodeString(ct:getConstDef('SYS_CODE_CAR_FUEL_TYPE'), car.carFuel)}</dd>
                <dt>배기량</dt>
                <dd>없음</dd>
                <dt>색상</dt>
                <dd>${ct:getCodeString(ct:getConstDef('SYS_CODE_CAR_COLOR_TYPE'), car.carColor)}</dd>
                <dt class="last">제시번호</dt>
                <dd class="last">없음</dd>
            </dl>
            <dl>
                <dt class="first">압류/저당</dt>
                <dd class="first">없음/없음</dd>
                <dt class="last">세금미납</dt>
                <dd class="last">없음</dd>
            </dl>
        </div>
    </div>
</div>