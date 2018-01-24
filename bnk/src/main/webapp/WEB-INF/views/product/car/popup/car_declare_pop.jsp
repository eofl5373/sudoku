<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- 허위매물신고alert -->
<div class="popupAutoWrap declarAlert p-container">
    <!-- popup contents -->
    <div class="popupContents">
        <section>
            <div class="alertPop">
                <p class="mb20">허위매물로 <i>신고</i>하시겠습니까?</p>
                <p class="fs16 mb40">더 나은 서비스와 민원 진행을 위해<br /> BNK오토모아에서 전화를 드립니다.</p>
                <div class="btnArea">
                    <span><button class="line p-close">취소</button></span>
                    <span><button class="btn-popup-auto" data-pop-opts='{"target": ".declarPop"}'>신고</button></span>
                </div>
                <a class="btnClose p-close" onclick="return false;"><em class="blind">닫기</em></a>
            </div>
        </section>
    </div>
</div>
<!-- //허위매물신고alert -->
<!-- 허위매물신고 -->
<div class="popupAutoWrap declarPop p-container w670">
    <!-- popup header -->
    <div class="popupHeaderAuto">
        <header><h1>허위매물신고</h1></header>
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
                                <th>신고차량정보</th>
                                <td>${car.label.makerName}&nbsp;${car.label.modelDtlName}</td>
                            </tr>
                            <tr>
                                <th>신고차량번호</th>
                                <td>${car.carPlateNum}</td>
                            </tr>
                            <tr>
                                <th>판매자</th>
                                <td>${car.user.userName}</td>
                            </tr>
                            <tr>
                                <th>신고내용</th>
                                <td class="checkArea">
                                	<c:forEach var="code" items="${ct:getAllValues(ct:getConstDef('SYS_CODE_FAKE'))}" varStatus="status">
                                    <span><label for="che${status.index}"><input type="checkbox" id="che${status.index}" value="${code[0]}" name="falseType"/>${code[2]}</label></span>
                                	</c:forEach>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="btnAreaType mt40">
                    <span><button class="line p-close">취소</button></span>
                    <span><button type="button" onclick="declare_regist()">신고</button></span>
                </div>
            </div>
        </section>
    </div>
</div>
<!-- //허위매물신고 -->