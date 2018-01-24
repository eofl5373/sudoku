<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript">
$(function(){

	evaluate_list(1);

	$(".appraisePop input:checkbox").on('click', function() {
		// in the handler, 'this' refers to the box clicked on
		var $box = $(this);
		if ($box.is(":checked")) {
		  // the name of the box is retrieved using the .attr() method
		  // as it is assumed and expected to be immutable
		  var group = "input:checkbox[name='" + $box.attr("name") + "']";
		  // the checked state of the group/box on the other hand will change
		  // and the current value is retrieved using .prop() method
		  $(group).prop("checked", false);
		  $box.prop("checked", true);
		} else {
		  $box.prop("checked", false);
		}
	});
	$(".declarPop input:checkbox").on('click', function() {
		// in the handler, 'this' refers to the box clicked on
		var $box = $(this);
		if ($box.is(":checked")) {
		  // the name of the box is retrieved using the .attr() method
		  // as it is assumed and expected to be immutable
		  var group = "input:checkbox[name='" + $box.attr("name") + "']";
		  // the checked state of the group/box on the other hand will change
		  // and the current value is retrieved using .prop() method
		  $(group).prop("checked", false);
		  $box.prop("checked", true);
		} else {
		  $box.prop("checked", false);
		}
	});
	$(".appraisePop textarea").on('keyup', function() {
		var txt = $(this).val();
		$(this).prev().find('i').text('('+txt.length+'/100)');
	});



	$("select.reserve_date").on('change', function() {
		var value = this.value;

		switch(this.name){
			case 'reserve_year':
				var $year = $(this);
				var $month = $year.next('select[name=reserve_month]').empty();
				var $day = $month.next('select[name=reserve_day]').empty();
				var $time = $day.next('select[name=reserve_time]').empty();

				var now = new Date();
				var year = value;
				var month = 1;
				var day = 1;

				// 선택한 값이 현재 연도와 같을 경우
				if(value == now.getFullYear()){
					month = now.getMonth() + 1;
				}
				// 선택한 값이 현재 월과 같을 경우
				if(month == now.getMonth() + 1){
					day = now.getDate();
				}

				var years = util.range(year+1, year);
				var months = util.range(12, month);
				var days = util.range(new Date( year , month, 0).getDate(), day);
				var times = ['AM','PM'];

				if(day == now.getDate()){
					if(now.getHours() > 11){
						times = ['PM'];
					}else{
						times = ['AM','PM'];
					}
				}

				render_date($month, months, '월');
				render_date($day, days, '일');
				render_date($time, times, '오전/오후', true);
				break;
			case 'reserve_month':
				var $year = $(this).siblings('select[name=reserve_year]');
				var $month = $year.next('select[name=reserve_month]');
				var $day = $month.next('select[name=reserve_day]').empty();
				var $time = $day.next('select[name=reserve_time]').empty();

				var now = new Date();
				var year = $year[0].value;
				var month = value;
				var day = 1;

				// 선택한 값이 현재 연도, 월과 같을 경우
				if(value == now.getMonth() + 1 && year == now.getFullYear()){
					day = now.getDate();
				}

				var days = util.range(new Date( year , month, 0).getDate(), day);
				var times = ['AM','PM'];

				if(day == now.getDate()){
					if(now.getHours() > 11){
						times = ['PM'];
					}else{
						times = ['AM','PM'];
					}
				}

				render_date($day, days, '일');
				render_date($time, times, '오전/오후', true);
				break;
			case 'reserve_day':
				var $year = $(this).siblings('select[name=reserve_year]');
				var $month = $year.next('select[name=reserve_month]');
				var $day = $month.next('select[name=reserve_day]');
				var $time = $day.next('select[name=reserve_time]').empty();

				var now = new Date();
				var year = $year[0].value;
				var month = $month[0].value;
				var day = value;
				var times = ['AM','PM'];

				if(year==now.getFullYear() && month==(now.getMonth()+1) && value==now.getDate())
				{
					if(now.getHours() > 11){
						times = ['PM'];
					}else{
						times= ['AM','PM'];
					}
				}else{
					times = ['AM','PM'];
				}

				render_date($time, times, '오전/오후', true);
				break;
		}
	});

	/* 주소 변경 */
	$("#btnSearchAddr").click(function() {
		$("#keyword").val($("#searchAddrName").val());
		$.ajax({
			 url  : "http://www.juso.go.kr/addrlink/addrLinkApiJsonp.do"  //인터넷망
			,type : "post"
			,data : $("#addrFrm").serialize()
			,dataType : "jsonp"
			,crossDomain : true
			,success : function(data){
				$("#list").html("");
				var errCode = data.results.common.errorCode;
				var errDesc = data.results.common.errorMessage;
				if(errCode != "0"){
					alertify.alert(errDesc);
				}else{
					if(data != null){
						addAddrListData(data);
					}
				}
			}
		    ,error: function(xhr,status, error){
		    }
		});
	});

    $("#dataList").on("click", "li", function() {
    	$("#dataList").find("li").removeClass();
    	$(this).prop("class","selected");
    	$("#selectAddrCode").val($(this).find("span").text());
    	$("#selectAddr").val($(this).find("strong").text());
    });

    $("#btnAddrSelect").click(function() {
    	$("#zipCode").val($("#selectAddrCode").val());
    	$("#addr1").val($("#selectAddr").val());
    	$("#addr2").val("");
    	$("#btnAddrCancel").trigger("click");
    });

    $("[name=reserve_type]").on('click', function(){
    	$(this).siblings("input").prop('checked', false);
    });


	render_date_init();
	
	// jj-choi 2017-09-15 추가
	$(".btnaccNonPop").click(function(){
		alertify.alert('지원되지 않는 차량입니다');
	});
});

function render_date_init(){
	var now		= new Date();
	var year	= now.getFullYear();
	var month	= 1;
	var day		= 1;

	if(year == now.getFullYear()){
		month = now.getMonth() + 1;
	}
	if(month == now.getMonth() + 1){
		day = now.getDate();
	}

	var years = util.range(year+1, year);
	var months = util.range(12, month);
	var days = util.range(new Date( year , month, 0).getDate(), day);
	var times = ['AM','PM'];

	if(day == now.getDate()){
		if(now.getHours() > 11){
			times = ['PM'];
		}else{
			times = ['AM','PM'];
		}
	}

	$('select[name=reserve_year]').each(function(){
		var $this = $(this);
		render_date($this, years, '년');
	});
	$('select[name=reserve_month]').each(function(){
		var $this = $(this);
		render_date($this, months, '월');
	});
	$('select[name=reserve_day]').each(function(){
		var $this = $(this);
		render_date($this, days, '일');
	});
	$('select[name=reserve_time]').each(function(){
		var $this = $(this);
		render_date($this, times, '오전/오후', true);
	});
}
function render_date($this, arr, label, custom){
	arr.forEach(function(obj, index){
		if(!custom){
			$this.append('<option value="'+obj+'">'+obj+label+'</option>');
		}else{
			$this.append('<option value="'+obj+'">'+(obj=='AM'?'오전':'오후')+'</option>');
		}
	});
}

function increase_view_count(){
	util.incViewCnt({carSeq:'${car.carSeq}'});
}
function evaluate_regist(){

	var evaluate = {};
	evaluate.dealerId	= '${car.userId}';
	evaluate.evalDiv	= $('input[name=evalDiv]:checked')[0].value;
	evaluate.rating		= $('.appraisePop .rating').rateit('value');
	evaluate.reviews	= $('textarea[name=reviews]').val();

	if(evaluate.rating == 0){
		alertify.alert('별점은 1점 이상 입력하세요.');
		return;
	}
	if(evaluate.reviews.length === 0){
		alertify.alert('평가 내용을 입력하세요.');
		return;
	}

	alertify.confirm('작성한 평가를 등록하시겠습니까?', function(){
		$.ajax({
			method : 'POST',
			url : BNK_CTX + '/product/car/evaluateRegist/AJAX',
			contentType: 'application/json;charset=UTF-8',
			data : JSON.stringify({eval: evaluate}),
			success : function(data){
				//console.log(data);
				//초기화 추가 하기
				$('input[name=evalDiv]:checked')[0].value = "1";
				$('.appraisePop .rating').rateit('value', 0);
				$('textarea[name=reviews]').val('');
				evaluate_list(1);
				$('.p-close').trigger('click');
			},
			error : function(request,status,error){
				alertify.alert(error);
			}
		});
	});

}
function evaluate_list(curPage){
	$.ajax({
		url : BNK_CTX + '/product/car/evaluateList/AJAX',
		dataType : 'html',
		cache : false,
		data : {
			curPage : curPage
			, userId : '${car.userId}'
		},
		success : function(detailResult){
			$("#evaluateList").empty();
			$("#evaluateList").append(detailResult);
			ITCButton.setupTypeAccordion();
		},
		error : function(request,status,error){
			alertify.alert(error);
		}
	});
}
function reserve_regist(elem, code){
	var $popup = $(elem).closest('.popupAutoWrap');

	var resDate = $popup.find('.reserve_date')
	.map(function(){
		var result = '';
		switch(this.name){
			case 'reserve_year': result = this.value; break;
			case 'reserve_month':
			case 'reserve_day': result = util.lpad(this.value, 2); break;
		}
		return result;
	}).get().join('');

	var resType=code ? code : $popup.find("[name=reserve_type]:checked").val();
	var resAmpm = $popup.find('select[name=reserve_time]')[0].value;
	var resUserTel = $popup.find('.reserve_phone').map(function(){return this.value;}).get().join('');

	var resTypeLabel = resType == '1' ? '방문' : resType == '2' ? '시승' : '탁송';

	var reserve = {};
	reserve.resType			= resType;								//방문시승탁송 구분
	reserve.resDate			= resDate;								//예약날짜(년월일)
	reserve.resAmpm			= resAmpm;								//예약날짜(오전/오후)
	reserve.carSeq			= '${car.carSeq}';						//매물고유번호
	reserve.carPlateNum		= '${car.carPlateNum}';					//차량번호
	reserve.dealerLicenseNo	= '${car.user.dealerLicenseNo}';		//딜러종사자번호
	reserve.resStatus		= '1';									//예약상태
	reserve.resUserId		= '${sessUserInfo.userId}';				//예약자아이디
	reserve.resUserNm		= '${sessUserInfo.userName}';			//예약자명
	reserve.resUserTel		= util.nvl(resUserTel,'${sessUserInfo.actualPhoneMobile}');	//예약자연락처

	reserve.parkZip			= code ? $popup.find('select[name=reserve_zip]').val()   : '${sessUserInfo.zipCode}';
	reserve.parkAddr1		= code ? $popup.find('select[name=reserve_addr1]').val() : '${sessUserInfo.addr1}';
	reserve.parkAddr2		= code ? $popup.find('select[name=reserve_addr2]').val() : '${sessUserInfo.addr2}';

	//console.log(reserve);
	$.ajax({
		method:'POST'
		, url: BNK_CTX + '/product/car/reserveRegist/AJAX'
		, contentType: 'application/json;charset=UTF-8'
		, data: JSON.stringify(reserve)
		, success: function(data){
			//console.log(data);
			if(data.resCd == '00'){
				alertify.alert(resTypeLabel + '신청을 예약하였습니다.');
			}else if(data.resCd == '10'){
				alertify.alert('해당 차량은 방문/시승/탁송의 예약건수가 많아서 신청이 불가능한 상태입니다.');
				//location.href = BNK_CTX + '/front/my/reserList';
			}else if(data.resCd == '11'){
				alertify.alert('해당 차량은 예약 신청이 완료된 상태입니다.');
			}else if(data.resCd == '99'){
				alertify.alert('예약 도중 오류가 발생했습니다. \n잠시후에 다시 시도해주세요.');
			}
			$popup.find('.p-close').trigger('click');
		}
		, error: function(){
			console.log('등록 중 네트워크 오류 발생');
		}
	})

}
function declare_regist(){

	var params = {};

	params.carSeq			= '${car.carSeq}';
	params.carFullCode		= '${car.carFullCode}';
	params.carPlateNum		= '${car.carPlateNum}';
	params.falseUserId		= '${sessUserInfo.userId}';
	params.falseUserNm		= '${sessUserInfo.userName}';
	params.falseUserTel		= '${sessUserInfo.phoneMobile}';
	params.saleAmt			= '${car.saleAmt}';
	params.falseType		= $(':checkbox[name="falseType"]:checked').val();
	params.falseShopNo		= '${car.user.shopNo}';
	params.falseDealerId	= '${car.user.userId}';
	params.falseDealerNm	= '${car.user.userName}';
	params.falseDealerTel	= '${car.user.phoneMobile}';

	if(!$(':checkbox[name="falseType"]').is(':checked')){
		alertify.alert('신고내용은 필수 값입니다.');
		return;
	}

	alertify.confirm('허위매물로 신고하시겠습니까?', function(){
		$.ajax({
			method : 'POST',
			url : BNK_CTX + '/product/car/declareRegist/AJAX',
			contentType: 'application/json;charset=UTF-8',
			data: JSON.stringify(params),
			success: function(oRes){
				/* 성공적으로 결과 데이터가 넘어 왔을 때 처리 */
				if(oRes.resCd == "00"){
					alertify.alert('이미 허위매물로 등록되어있습니다.');
					return;
				}else if(oRes.resCd == "99"){
					alertify.alert("허위매물 신고 중 에러가 발생하였습니다.");
					return;
				}else if(oRes.resCd == "11"){
					alertify.alert('허위매물로 신고되었습니다.');
					$('.p-close').trigger('click');
					$(':checkbox[name="falseType"]:checked').prop('checked', false);
				}
			},
			error: function(data, status, headers, config) {
				/* 서버와의 연결이 정상적이지 않을 때 처리 */
				console.log(status);
			}
		});
	});
}
//옵션 보기
function optionView(elem){
	$em = $(elem).find('em');
	$em.toggleClass('off');
	if($em.hasClass('off')){
		//보기 +
		$em.text('보기 +');
	}else{
		//닫기 -
		$em.text('닫기 -');

	}
}

//판매차량갯수 클릭시 상세 팝업 open
function fn_saleCar(){
	dealer_sale_list(1);
}
//딜러 판매중 차량 리스트
function dealer_sale_list(curPage){

	$.ajax({
		url : BNK_CTX + "/product/car/dealerSaleCarList/AJAX",
		dataType : 'html',
		cache : false,
		data : {
			curPage : curPage
			, userId : '${car.userId}'
		},
		success : function(detailResult){
			$("#templete").empty();
			$("#templete").append(detailResult);
			var cnt = $('.dealerSaleCnt').val();
			$('.totCnts').text(cnt);
		},
		error : function(request,status,error){
			alertify.alert(error);
		}
	});
}
//내게맞는매물 등록
function fn_insertReserve(){
	alertify.confirm('내게맞는매물을 신청 하시겠습니까?', function(){

		if(util.isEmpty($('select[name=makerCd]').val())){
			alertify.alert("제조사 선택은 필수 입니다.");
			return false;
		}
		if(util.isEmpty($('select[name=modelCd]').val())){
			alertify.alert("모델 선택은 필수 입니다.");
			return false;
		}
		if(util.isEmpty($('select[name=detailModelCd]').val())){
			alertify.alert("세부모델 선택은 필수 입니다.");
			return false;
		}

		var param = {	makerCd			: $('select[name=makerCd]').val() ,
						modelCd			: $('select[name=modelCd]').val() ,
						detailModelCd	: $('select[name=detailModelCd]').val() ,
						useKm			: $('#useKm').val() ,
						carRegYear		: $('select[name=carRegYear]').val() ,
						carColor		: $('select[name=carColor]').val() ,
					};
		$.ajax({
			url : BNK_CTX + "/product/car/carDetailInsertReserve"
			, data :  JSON.stringify(param)
			, dataType : 'json'
			, type: "POST"
			, contentType: "application/json"
			, success : function(data, textStatus, jqXHR){
				alertify.alert("등록 되었습니다.");
				$(".p-close").trigger('click');
				$('#regBtn').hide();
				$('#modifybtn').show();
			},
			error: function(error){
				alertify.alert("데이터 통신상태가 원활하지 않습니다.");
			}
		});
	});
}
//내게맞는매물 수정
function fn_modifyReserve(){
	alertify.confirm('내게맞는매물을 수정 하시겠습니까?', function(){
		if(util.isEmpty($('select[name=makerCd]').val())){
			alertify.alert("제조사 선택은 필수 입니다.");
			return false;
		}
		if(util.isEmpty($('select[name=modelCd]').val())){
			alertify.alert("모델 선택은 필수 입니다.");
			return false;
		}
		if(util.isEmpty($('select[name=detailModelCd]').val())){
			alertify.alert("세부모델 선택은 필수 입니다.");
			return false;
		}

		var param = {	makerCd			: $('select[name=makerCd]').val() ,
						modelCd			: $('select[name=modelCd]').val() ,
						detailModelCd	: $('select[name=detailModelCd]').val() ,
						useKm			: $('#useKm').val() ,
						carRegYear		: $('select[name=carRegYear]').val() ,
						carColor		: $('select[name=carColor]').val() ,
					};
		$.ajax({
			url : BNK_CTX + "/product/car/carDetailModifyReserve"
			, data :  JSON.stringify(param)
			, dataType : 'json'
			, type: "POST"
			, contentType: "application/json"
			, success : function(data, textStatus, jqXHR){
				alertify.alert("수정 되었습니다.");
				$(".p-close").trigger('click');
			},
			error: function(error){
				alertify.alert("데이터 통신상태가 원활하지 않습니다.");
			}
		});
	});
}

/* 주소검색 TAG 생성 */
function addAddrListData(data) {
	$("#dataList").html("");
	$(data.results.juso).each(function(index){
		$("#dataList").append($('<li/>', {
			id: index
		}));
		$("#"+index).append($('<strong/>', {
	        text: this.roadAddr
		}));
		$("#"+index).append($('<span/>', {
	        text: this.zipNo
		}));
	});
}

</script>