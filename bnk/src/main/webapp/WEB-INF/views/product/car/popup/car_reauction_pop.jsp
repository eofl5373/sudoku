<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script>
$(function(){
	//제조사리스트 초기화
	if('${sessUserInfo.userId}' != ""){
		$.ajax({
			url : BNK_CTX + "/product/co/makerCombo"
			, dataType : 'json'
			, type: "get"
			, success : function(data){
				var $select = $('select[name=makerCd]');
				render_combo($select, data, '${reverse.makerCd}');
			},
			error: function(error){
				alertify.alert("데이터 통신상태가 원활하지 않습니다.");
				console.log(status);
			}
		});
	}
	
	//내게맞는매물 수정 or 등록인지 확인
	if(util.isEmpty($("#_makerCd").val())){
		$('#modifybtn').hide();
	}else{
		$('#modifybtn').show();
		$('#regBtn').hide();
	}
	
	//연식
	if(!util.isEmpty($('#_makerCd').val())){
		$('#_carRegYear').val('${reverse.carRegYear}');
	}
});
//콤보박스 그리기
function render_combo($select, data, value){
	var label = '';
	$select.empty();
	switch($select.attr('name')){
	case 'makerCd':
		label = '제조사를 선택해주세요';
		break;
	case 'modelCd':
		label = '모델을 선택해주세요';
		break;
	case 'detailModelCd':
		label = '세부모델을 선택해주세요';
		break;
	}
	$select.append($('<option>').val('').text(label));
	for(var key in data){
		var $option = $('<option>');
		$option.val(key);
		$option.text(data[key]);
		$select.append($option);
	}

	$select.val(value);
	$select.trigger('change');
}
//차량모델리스트 가져오기
function model_list(makerCd, name){
	$.ajax({
		url : BNK_CTX + "/product/co/modelCombo"
		, data : {makerCd : makerCd}
		, dataType : 'json'
		, type: "get"
		, contentType: "application/json; charset=UTF-8"
		, success : function(data){
			var $select = $('select[name='+name+']');
			var value = '';
			switch(name){
				case 'modelCd':
					value='${reverse.modelCd}';
					break;
				case 'detailModelCd':
					value='${reverse.detailModelCd}';
					break;
			}
			render_combo($select, data, value);
		},
		error: function(error){
			alertify.alert("데이터 통신상태가 원활하지 않습니다.");
			console.log(status);
		}
	});
}

</script>
<div class="popupAutoWrap reverseAdd p-container w980">
    <!-- popup header -->
	<div class="popupHeaderAuto">
		<header><h1>내게맞는매물</h1></header>
		<a class="btnClose p-close" onclick="return false;"><em class="blind">닫기</em></a>
	</div>
	<hr>
    <!-- popup contents -->
    <div class="popupContents">
    	<form id="reverseAuctionForm">
        <section>
            <div class="autoPop">
            	<div class="popTable mt20">
					<div>
						<strong>제조사</strong>
						<span>
							<select name="makerCd" id="makerId" class="w50p" onchange="model_list(this.value, 'modelCd')">
                            </select>
                            <input type="hidden" value="${reverse.makerCd}" id="_makerCd">
						</span>
					</div>
					<div>
						<strong>모델</strong>
						<span>
							<select name="modelCd" class="w50p" onchange="model_list(this.value, 'detailModelCd')">
							</select>
						</span>
					</div>
					<div>
						<strong>세부모델</strong>
						<span>
							<select name="detailModelCd">
                            </select>
						</span>
					</div>
					<div>
						<strong>주행거리</strong>
						<span><input name="useKm" id="useKm" type="text" placeholder="주행거리를 입력하세요." value="${reverse.useKm}"></span>
					</div>
					<div>
						<strong>연식</strong>
						<span>
							<select name="carRegYear" id="_carRegYear">
								<c:forEach var="i" begin="0" end="${2017-1990}">
									<c:set var="yearOption" value="${2017-i}" />
									<option value="${yearOption}">${yearOption}</option>
								</c:forEach>
<%-- 								<jsp:useBean id="now" class="java.util.Date"/> --%>
<%-- 								<fmt:formatDate var="year" value="${now}" pattern="yyyy"/> --%>
<%-- 								<c:forEach var="i" begin="1990" end="${year+1}" varStatus="status"> --%>
<%-- 									<option value="${year+1991-i}">${year+1991-i} 년</option> --%>
<%-- 								</c:forEach> --%>
							</select>
						</span>
					</div>
					<div>
						<strong>색상</strong>
						<span>
							<select name="carColor">
								${bnk:genOptionTag('SYS_CODE_CAR_COLOR_TYPE',reverse.carColor)}
							</select>
						</span>
					</div>
				</div>
				<div class="btnAreaType mt40">
                    <span><button class="line">취소</button></span>
                    <span><button type="button" onclick="fn_insertReserve()" id="regBtn">신청</button></span>
                    <span><button type="button" onclick="fn_modifyReserve()" id="modifybtn" style="display:none">수정</button></span>
                </div>
            </div>
        </section>
        </form>
    </div>
</div>