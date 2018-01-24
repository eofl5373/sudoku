<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript" src="<c:url value="/front/js/jquery-2.2.0.min.js?latest=${jsVersion}"/>"></script>
<script type="text/javascript" src="<c:url value="/front/js/jquery.form.min.js?latest=${jsVersion}"/>"></script>

<form id="frmBnkLoan" method="post" action="${forward}" style="display: none;">
<input name="HNO" value="${car.carSeq}" type="hidden" />매물번호
<input name="MACD" value="${car.label.makerName}" type="hidden" />제조사
<input name="CNNM" value="${car.label.modelName}" type="hidden" />차명
<input name="CNCD" value="${car.carFullCode}" type="hidden" />차량코드
<input name="CARNO" value="${car.carPlateNum}" type="hidden" />차량번호
<input name="CBNO" value="${car.carFrameNum}" type="hidden" />차대번호
<input name="REGYM" value="${car.carRegDay}" type="hidden" />등록년월
<input name="MYEAR" value="${car.carRegYear}" type="hidden" />차량년식
<input name="CKM" value="${car.useKm}" type="hidden" />주행거리
<input name="GRCD" value="${car.label.carMission}" type="hidden" />변속기
<input name="FUCD" value="${car.label.carFuel}" type="hidden" />연료
<input name="SCCT" value="${car.user.dealerShopName}" type="hidden" />상사명
<input name="SCNM" value="${car.user.userName}" type="hidden" />딜러명
<input name="SCTEL" value="${car.user.dealerProfileTel}" type="hidden" />딜러연락처
<input name="SCADDR" value="${car.user.dealerShopAddr}" type="hidden" />딜러주소
<input name="SCPC" id="SCPC" value="${car.saleAmt}" type="hidden" />가격
<input name="CAR_INFO" value="${car.label.makerName} ${car.label.modelName} ${car.label.modelDtlName} ${car.label.gradeName}" type="hidden" />모델명
<input name="CAR_IMAGE_URL" value="${car.itemImgInfo.mThumbPathFullUrl}" type="hidden" />이미지
<input name="SRC_URI" value="bnkautomoa" type="hidden" />
</form>

<script>
$(document).ready(function(){
	var money = $("#SCPC").val();
	var sumMoney = Number(money) * 10000;
	
	$("#SCPC").val(sumMoney);
	$('#frmBnkLoan').submit();
});
</script>
