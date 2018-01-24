<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript">
//검색 조건
var oDivision = '${sessUserInfo.division}';
var oSearch = {};
oSearch.words 		= [];			// 퀵검색 검색조건 담을 배열
oSearch.price 		= '';			// 선택한 최대가격
oSearch.priceMin    = '';           // 선택한 최소 가격
oSearch.priceMax    = '';           // 선택한 최대 가격
oSearch.mileage 	= '';			// 선택한 최대 주행거리
oSearch.mileageMin  = '';           // 선택한 최소 주행거리
oSearch.mileageMap  = '';           // 선택한 최대 주행거리
oSearch.country 	= '';
oSearch.countrys 	= [];
oSearch.promotions	= [];
oSearch.makers 		= [];
oSearch.models 		= [];
oSearch.modelDtls 	= [];
oSearch.modelYear 	= '';
oSearch.colors 		= [];
oSearch.areas 		= [];
oSearch.danjis 		= [];
oSearch.carNumber 	= '';
oSearch.sort		= 'T1.CREATED_DATE DESC'



var oPageInfo = {
		'data': {},
		'totListSize': 0,	// 전체 리스트 갯수
		'currentPageNo': 1,	// 현재 페이지 번호
		'bLoad': false, 	// 데이터 로딩 상태 여부
		'bHasMore': false	// 로드할 데이터 여부
	};

var seekbar = {};
seekbar.minPrice = "50";
seekbar.maxPrice = "10000";
seekbar.minMileage = "1000";
seekbar.maxMileage = "300000";
seekbar.changeMinPrice = "50";
seekbar.changeMaxPrice = "10000";
seekbar.changeMinMileage = "1000";
seekbar.changeMaxMileage = "300000";


$(function() {
	$("#modelDl").hide();
	$("#detailModelDl").hide();
	$("#danjiDl").hide();

	var searchInfo = localStorage.getObject("searchInfo");
	if(searchInfo){
		quickTextSelect(searchInfo.code, searchInfo.name);
		localStorage.setObject("searchInfo", null);
	}else{
		// 일반 매물 전체조회
		loadSearchData();
		getList();
	}


	/* $("#price").prop("min", seekbar.minPrice);
	$("#price").prop("max", seekbar.maxPrice);
	$("#price").val(seekbar.maxPrice);
	$("#mileage").prop("min", seekbar.minMileage);
	$("#mileage").prop("max", seekbar.maxMileage);
	$("#mileage").val(seekbar.maxMileage); */
	/* 퍼블리싱 ********************************************/
	console.log(seekbar);
	$( "#price" ).slider({
        range: true,
        step: 50,
        min: Number(seekbar.minPrice),
        max: Number(seekbar.maxPrice),
        values: [ Number(seekbar.changeMinPrice), Number(seekbar.changeMaxPrice) ],
        slide: function( event, ui ) {

            $("#priceTxt").html( util.addComma(ui.values[ 0 ]) + "<span>만원 ~</span>"  + util.addComma(ui.values[ 1 ]) + "<span>만원</span>") ;
        },
        change: function( event, ui ) {
        	onSeekChange('SEARCH_WORD_PRICE', ui.values[0],ui.values[1]);
            $("#priceTxt").html( util.addComma(ui.values[ 0 ]) + "<span>만원 ~</span>"  + util.addComma(ui.values[ 1 ]) + "<span>만원</span>") ;
        }
    });
    $("#priceTxt").html(util.addComma($( "#price" ).slider( "values", 0 )) + "<span>만원 ~</span>" +  util.addComma($( "#price" ).slider( "values", 1 )) + "<span>만원</span>") ;

    $( "#mileage" ).slider({
        range: true,
        step: 1000,
        min: Number(seekbar.minMileage),
        max: Number(seekbar.maxMileage),
        values: [ Number(seekbar.changeMinMileage), Number(seekbar.changeMaxMileage) ],
        slide: function( event, ui ) {
            $( "#mileageTxt" ).html( util.addComma(ui.values[ 0 ]) + "<span>km ~</span>"  + util.addComma(ui.values[ 1 ]) + "<span>km</span>");
        },
        change: function( event, ui ) {
        	onSeekChange('SEARCH_WORD_MILEAGE', ui.values[0],ui.values[1]);
            $( "#mileageTxt" ).html( util.addComma(ui.values[ 0 ]) + "<span>km ~</span>"  + util.addComma(ui.values[ 1 ]) + "<span>km</span>");
        }
    });
    $( "#mileageTxt" ).html(util.addComma($( "#mileage" ).slider( "values", 0 )) + "<span>km ~</span>" +  util.addComma($( "#mileage" ).slider( "values", 1 )) + "<span>km</span>");


	var mH = $(window).outerHeight()-738;
	$('.leftMenu .btn-accordion-switch').click(function(){
		var dotH = $(this).find('.dotList').outerHeight();
		if (dotH > 60 )
		{
			if(mH > 0){
				$(this).find('.overflowCase').css("height", mH);
			}else{
				$(this).find('.overflowCase').css("height", "100px");
			}
		}
	});


	$("document").ready(function() {
		listReactive();
	});
	$(window).resize(function(){
		listReactive();
	});
	/* end 2017-09-11 */

    /* 퍼블리싱 *******************************************
	var mH = $(window).outerHeight()-863;
		$('.overflowCase').css("height", mH);*/

	// 매물정보 입력 완성
    $("#searchName").keyup(function(){
    	if($(this).val() != "") {
    		$(".searchSelect").show();
    		getQuickSearchData($(this).val());
    	} else {
    		$(".searchSelect").hide();
    	}
    });

});

//최대 가격, 주행거리시
var onSeekChange = function(id, min, max) {
	var seekData = {};
	seekData.id = id
	seekData.codeMin = min;
    seekData.codeMax = max;
    seekData.code = "";

	quickIdDelete(seekData);
    delWordsNoSearch(id);

    switch(id){
    case 'SEARCH_WORD_PRICE':
    	seekData.name = util.addComma(min) + "만원 ~ " + util.addComma(max) + "만원";
    	if(min == seekbar.minPrice
   	            && max == seekbar.maxPrice ) {
   	        pageInit();
   	        getList();
   	    } else {
   	        quickTagAdd(seekData);
   	        setWords(seekData);
   	    }
        break;
    case 'SEARCH_WORD_MILEAGE':
    	seekData.name = util.addComma(min) + "km ~ " + util.addComma(max) + "km";
    	if(min == seekbar.minMileage
   	            && max == seekbar.maxMileage ) {
   	        pageInit();
   	        getList();
   	    } else {
   	        quickTagAdd(seekData);
   	        setWords(seekData);
   	    }
        break;
    }
};


// 해당 검색 체크 및 선택시
var onToggle = function(obj, data){
	switch(data.id){
	case "SEARCH_WORD_CAR":
		searchSelect(obj, data);

		/* 제조사 선택시 모델정보 요청
			- 제조사 선택 갯수가 하나
			- code 가 제조사 코드일때
			- 제조사 체크박스가 하나일때
		*/
		if( oSearch.makers.length == 1 && data.code.length == 3 &&
				$("#makeList input[id^="+data.id+"]:checked").length == 1) {
			getCarDetailList("M", oSearch.makers[0]);

		}

		/* 모델 선택시 상세 모델정보 요청
			- 모델 선택 갯수가 하나
			- code 가 모델 코드일때
			- 모델 선택 체크박스가 하나일때
		*/
		else if ( oSearch.models.length == 1 && data.code.length == 5 &&
				$("#modelList input[id^="+data.id+"]:checked").length == 1) {
			getCarDetailList("D", oSearch.models[0]);
		}

		// 모델, 제조사 display 여부
		carShowHide(data);
		break;
	case 'SEARCH_WORD_MODEL_YEAR':
		quickIdDelete(data); //
		delWordsNoSearch(data.id);

		// 연식정보가 입력 받았을경우
		if(util.nvl($(obj).val())) {
			data.code = $(obj).val();
			data.name = $(obj).val();
			quickTagAdd(data);
			setWords(data);
		}
		break;
	case 'SEARCH_WORD_AREA':
		searchSelect(obj, data);
		// 지역정보가 존재할경우
		if( oSearch.areas.length == 0) {
			$("#danjiDl").hide();
			$("#danjiList").html("");
		} else {
			$("#danjiDl").show();
			getMarketList(oSearch.areas); 	// 매매단지 리스트 조회
		}
		break;
	case 'SEARCH_WORD_CAR_NUMBER':
		quickIdDelete(data);
		delWordsNoSearch(data.id);
		// 차량번호를 입력받았을경우
		if(util.nvl($(obj).val())) {
			data.code = $(obj).val();
			data.name = $(obj).val();
			quickTagAdd(data);
			setWords(data);
		}
		break;
	default :
		searchSelect(obj, data);
		break;
	}
};

/* 2017-09-11 */
var listReactive = function(){
	$('#wrap').addClass("carListCase");
	var widths = $("#wrap.carListCase .listType1").width();
	var num = parseInt(widths/5);
	$("#wrap.carListCase .listType1 li").css('width',num+"px");

	var sWidth = $(".saleCar").width();
	var sHeight = $(".saleCar").height();
	var lHeight = $(".listSet").height();
	$(".imgBack").css({
		'width':(num)+'px',
		'height':(sHeight)+'px'
	});
	$(".listType1 .heartSet").css({
		'bottom':(lHeight-sHeight-38)+'px'
	});
};



//매물 리스트 조회
var getList = function(type){
	var oParams = {};										// 상세검색 조건
	oParams.curPage = oPageInfo.currentPageNo;		// 페이지 번호
	oParams.oConditions = oSearch.words;		// 퀵검색 조건
	oParams.sortField = oSearch.sort;

	localStorage.setObject("searchData", oSearch); //검색 정보 저장

	$.ajax({
		url : BNK_CTX + "/api/car/list/default"
		, data : JSON.stringify(oParams)
		, dataType : 'json'
		, type: "post"
		, contentType: "application/json; charset=UTF-8"
		, success : function(result){
			if(result.code == "00"){
				console.log(result);
				$("#defultCount").text(util.addComma(result.data.totListSize));
				oPageInfo.bHasMore = result.data.hasMoreList;
				// 페이징일경우 해당 list에 추가, 아닐경우 새로 그린다
				$("<div>").load("/product/car/getComponent/AJAX #component_list", {json:JSON.stringify(result.data)}, function() {
					var tag = $(this).find("#component_list").html();
					if(type == "PAGE") {
						console.log(result.data.hasMoreList);
						if(result.data.hasMoreList){
							$("#defaultList").append(tag);
						}
					} else {
						$("#defaultList").html(tag);
					}

					$("#defaultList li").hover(
					  function() {
						  $(this).addClass('on');
					  }, function() {
						  $(this).removeClass('on');
					  }
					);

					listReactive();
				});
			}
		},
		error: function(error){
			alertify.alert("데이터 통신상태가 원활하지 않습니다.");
        }
	});
};

//검색단어 추가
function appendSearchWord(data){
	switch(data.id){
	// 검색단어 - 가격
	case 'SEARCH_WORD_PRICE':
		oSearch.price = "";
		oSearch.priceMin = data.codeMin;
		oSearch.priceMax = data.codeMax;
		break;
	// 검색단어 - 주행거리
	case 'SEARCH_WORD_MILEAGE':
		oSearch.mileage = "";
		oSearch.mileageMin = data.codeMin;
		oSearch.mileageMax = data.codeMax;
		break;
	// 검색단어 - 국가
	case 'SEARCH_WORD_COUNTRY':
//		oSearch.country = data.code;
		if(oSearch.countrys.indexOf(data.code) == -1)
			oSearch.countrys.push(data.code);
		break;
	// 검색단어 - 프로모션
	case 'SEARCH_WORD_PROMOTION':
		if(oSearch.promotions.indexOf(data.code) == -1)
			oSearch.promotions.push(data.code);
		break;
	// 검색단어 - 차량
	case 'SEARCH_WORD_CAR':
		// 제조사 추가
		if(data.code.length >= 3 && oSearch.makers.indexOf(data.code.substring(0,3)) == -1)
			oSearch.makers.push(data.code.substring(0,3));
		// 모델 추가
		if(data.code.length >= 5 && oSearch.models.indexOf(data.code.substring(0,5)) == -1)
			oSearch.models.push(data.code.substring(0,5));
		// 세부모델 추가
		if(data.code.length >= 6 && oSearch.modelDtls.indexOf(data.code.substring(0,6)) == -1)
			oSearch.modelDtls.push(data.code.substring(0,6));
		break;
	// 검색단어 - 색상
	case 'SEARCH_WORD_MODEL_YEAR':
		oSearch.modelYear = data.code;
		break;
	// 검색단어 - 색상
	case 'SEARCH_WORD_COLOR':
		if(oSearch.colors.indexOf(data.code) == -1)
			oSearch.colors.push(data.code);
		break;
	// 검색단어 - 지역
	case 'SEARCH_WORD_AREA':
		// 지역 추가
		if(oSearch.areas.indexOf(data.name) == -1)
			oSearch.areas.push(data.name);
		break;
	// 검색단어 - 매매단지
	case 'SEARCH_WORD_DANJI':
		// 매매단지 추가
		if(oSearch.danjis.indexOf(data.code) == -1)
			oSearch.danjis.push(data.code);
		break;
	// 검색단어 - 차량번호
	case 'SEARCH_WORD_CAR_NUMBER':
		// 매매단지 추가
		oSearch.carNumber = data.code;
		break;
	}
}

//검색단어 제외
function popSearchWord(data){
	// 배열안에 코드가 포함되어있는지 확인
	function isCodeInArr(arr, code){
		return arr.findIndex(function(d){return d.indexOf(code) == 0}) > -1;
	}
	// 배열안에 코드를 제외한다.
	function popCodeInArr(arr, code){
		var idx = arr.findIndex(function(d){return d == code;});
		if(idx > -1)arr.splice(idx, 1);
	}

	if(data == undefined) {
		return false;
	}
	switch(data.id){
	// 검색단어 - 가격
	case 'SEARCH_WORD_PRICE':
		oSearch.price = seekbar.maxPrice;
		break;
	// 검색단어 - 주행거리
	case 'SEARCH_WORD_MILEAGE':
		oSearch.mileage = seekbar.maxMileage;
		break;
	// 검색단어 - 국가
	case 'SEARCH_WORD_COUNTRY':
//		oSearch.country = '';
		popCodeInArr(oSearch.countrys, data.code);
		break;
	// 검색단어 - 프로모션
	case 'SEARCH_WORD_PROMOTION':
		popCodeInArr(oSearch.promotions, data.code);
		break;
	// 검색단어 - 차량
	case 'SEARCH_WORD_CAR':
		// 제조사 제외
		if(data.code.length == 3){
			// 1. 모델들, 세부모델들에 제조사차량이 있는지 확인한다.
			if(!isCodeInArr(oSearch.models, data.code) && !isCodeInArr(oSearch.modelDtls, data.code)){
				// 1.1. 제조사차량이 없다면 제조사들에서 제외한다.
				popCodeInArr(oSearch.makers, data.code);
			}
		}
		// 모델 제외
		else if(data.code.length == 5){
			// 1. 세부모델들에 모델이 있는지 확인한다.
			if(!isCodeInArr(oSearch.modelDtls, data.code)){
				// 1.1. 모델이 없다면 모델들에서 제외한다.
				popCodeInArr(oSearch.models, data.code);
			}
			// 2. 모델들, 검색어들에 제조사가 있는지 확인한다.
			if(!isCodeInArr(oSearch.models, data.code.substring(0,3)) && !isCodeInWords(data.id, data.code.substring(0,3))){
				// 2.1. 제조사가 없다면 제조사들에서 제외한다.
				popCodeInArr(oSearch.makers, data.code.substring(0,3));
			}
		}
		// 세부모델 제외
		else if(data.code.length == 6){
			// 1. 세부모델들에서 제외한다.
			popCodeInArr(oSearch.modelDtls, data.code);
			// 2. 세부모델들, 검색어들에서 모델이 있는지 확인한다.
			if(!isCodeInArr(oSearch.modelDtls, data.code.substring(0,5)) && !isCodeInWords(data.id, data.code.substring(0,5))){
				// 2.1. 모델이 없다면 모델들에서 제외한다.
				popCodeInArr(oSearch.models, data.code.substring(0,5));
			}
			// 3. 모델들, 검색어들에서 제조사가 있는지 확인한다.
			if(!isCodeInArr(oSearch.models, data.code.substring(0,3)) && !isCodeInWords(data.id, data.code.substring(0,3))){
				// 3.1. 제조사가 없다면 제조사들에서 제외한다.
				popCodeInArr(oSearch.makers, data.code.substring(0,3));
			}
		}
		break;
	// 검색단어 - 연식
	case 'SEARCH_WORD_MODEL_YEAR':
		oSearch.modelYear = '';
		break;
	// 검색단어 - 색상
	case 'SEARCH_WORD_COLOR':
		popCodeInArr(oSearch.colors, data.code);
		break;
	// 검색단어 - 지역
	case 'SEARCH_WORD_AREA':
		// 지역 추가
		popCodeInArr(oSearch.areas, data.name);
		break;
	// 검색단어 - 매매단지
	case 'SEARCH_WORD_DANJI':
		// 매매단지 추가
		popCodeInArr(oSearch.danjis, data.code);
		break;
	// 검색단어 - 차량번호
	case 'SEARCH_WORD_CAR_NUMBER':
		// 매매단지 추가
		oSearch.carNumber = '';
		break;
	}
}

// 매매단지정보
var getMarketList = function(areaNm){
	if(areaNm == "") {
		return false;
	}
	var params={danjisido    : areaNm.join(",")};
	$.ajax({
		url : BNK_CTX + "/api/market/region/list"
		, data : params
		, dataType : 'json'
		, type: "get"
		, contentType: "application/json; charset=UTF-8"
		, success : function(data){
			var tag = "";
			var list = data.data.list;
			$.each(list,function(index, info){
				var tagData 	= {};
				tagData.id 		=  "SEARCH_WORD_DANJI";
				tagData.code 	=  info.danjino;
				tagData.name 	=  info.danjifullname;
				tag += resultTagAdd(tagData);
			});
			$("#danjiList").html(tag);
			$.each(oSearch.danjis, function(index, code){
				$("#SEARCH_WORD_DANJI_"+code).prop("checked", "checked");
				$("#SEARCH_WORD_DANJI_"+code).parents("li").addClass("on");
			});
		},
		error: function(error){
			alertify.alert("데이터 통신상태가 원활하지 않습니다.");
			console.log(status);
        }
	});
};

// 차량 매물정보
var getCarDetailList = function(type, makerCd){
	if(makerCd == "") {
		return false;
	}
	$.ajax({
		url : BNK_CTX + "/product/co/modelCombo"
		, data : {makerCd : makerCd}
		, dataType : 'json'
		, type: "get"
		, contentType: "application/json; charset=UTF-8"
		, success : function(data){
			var tag = "";
			$.each(data, function(key, value){
				var tagData 	= {};
				tagData.id 		= "SEARCH_WORD_CAR";
				tagData.code 	= key;
				tagData.name 	= value;
				tag += resultTagAdd(tagData);
			});
			// 모델정보를 가져올경우
			if(type == "M") {
				$("#modelList").html(tag);
				$("#modelDl").show();
				// 기존 모델정보가 있을경우 해당 checkbox를 선택해준다
				$.each(oSearch.models, function(index, id){
					if($("#Q_SEARCH_WORD_CAR_"+id).val() != undefined) {
						$("#SEARCH_WORD_CAR_"+id).prop("checked", "checked");
						$("#SEARCH_WORD_CAR_"+id).parents("li").addClass("on");
					}
				});
			} else { // 상세 모델정보를 가져올경우
				$("#detailModelList").html(tag);
				$("#detailModelDl").show();
				// 기존 상세 모델 정보가 있을경우 해당 checkbox를 선택해준다
				$.each(oSearch.modelDtls, function(index, id){
					$("#SEARCH_WORD_CAR_"+id).prop("checked", "checked");
					$("#SEARCH_WORD_CAR_"+id).parents("li").addClass("on");
				});
			}
		},
		error: function(error){
			alertify.alert("데이터 통신상태가 원활하지 않습니다.");
			console.log(status);
        }
	});
};

// 퀵검색
var getQuickSearchData = function(name){
	$("#dropdown").html("");
	$.ajax({
		url : BNK_CTX + "/product/car/quick/search"
		, data : {searchName : name}
		, dataType : 'json'
		, type: "get"
		, contentType: "application/json; charset=UTF-8"
		, success : function(data){
			var tag = "";
			if(data.length == 0) {
				$("#dropdown").append($('<li/>', {
                    text: "매물 정보를 찾을 수 없습니다"
                }));
			} else {
				$.each(data, function(index, info){
	                $("#dropdown").append($('<li/>', {
	                    onclick: "quickTextSelect('"+info.code+"','"+info.name+"')"
	                    , value: info.code
	                    , text: info.name
	                }));
	            });
			}

		},
		error: function(error){
			alertify.alert("데이터 통신상태가 원활하지 않습니다.");
			console.log(status);
        }
	});
};


/** SUB FUNCTION **/
// 검색배열안에 코드가 포함되어있는지 확인
function isCodeInWords(id, code){
	return oSearch.words.findIndex(function(d){return d.id == id && d.code == code}) > -1;
}
// 검색배열안에서 코드 순번을 구한다.
function getCodeIdxInWords(id, code){
	if(code){
		return oSearch.words.findIndex(function(d){return d.id == id && d.code == code});
	}else{
		return oSearch.words.findIndex(function(d){return d.id == id});
	}
}
// 검색배열안에서 그룹아이디 코드들을 구한다.
function getGroupIdCodesInWords(id){
	return oSearch.words.filter(function(d){return d.id == id});
}

// 체크박스 검색 클릭시 앞에 li의 색상을 변경
function searchSelect(obj, data) {
	if($(obj).is(":checked")){
		$(obj).parents("li").addClass("on");
		quickCheckTagAdd(data);
		setWords(data);
	} else {
		$(obj).parents("li").removeClass("on");
		quickDelete(obj, data);
	}
}

// 퀵선택 삭제
function quickDelete(obj, data) {
	switch(data.id){
	case 'SEARCH_WORD_CAR':
		delWords(data);
		deleteSearchValue(obj, data);
		quickTagCheckDelete(data);
		carShowHide(data)
		break;
	case 'SEARCH_WORD_PRICE':
		$( "#price" ).slider( "values", 0, seekbar.minPrice );
		$( "#price" ).slider( "values", 1, seekbar.maxPrice );

		onSeekChange('SEARCH_WORD_PRICE', seekbar.minPrice,seekbar.maxPrice);
		delWords({id:data.id});
		quickTagDelete(data);
		break;
	case 'SEARCH_WORD_MILEAGE':
		$( "#mileage" ).slider( "values", 0, seekbar.minMileage );
        $( "#mileage" ).slider( "values", 1, seekbar.maxMileage );

		delWords({id:data.id});
		quickTagDelete(data);
		break;
	case 'SEARCH_WORD_MODEL_YEAR':
		$("#"+data.id).val("");
		delWords({id:data.id});	// 전체삭제
		quickTagDelete(data);
		break;
	case 'SEARCH_WORD_CAR_NUMBER':
		$("#"+data.id).val("");
		delWords({id:data.id});	// 전체삭제
		quickTagDelete(data);
		break;
	case 'SEARCH_WORD_AREA':
		deleteSearchValue(obj, data);
		quickTagCheckDelete(data);

		data.code = data.name;
		delWords(data);
		getMarketList(oSearch.areas);

		if( oSearch.areas.length == 0) {
            $("#danjiDl").hide();
        }
		break;
	default :
		delWords(data);
		deleteSearchValue(obj, data);
		quickTagCheckDelete(data);
		break;
	}
}

//체크박스 해제
function deleteSearchValue(obj, data) {
	var obj = $("#" + data.id + "_" + data.code );
	obj.parents("li").removeClass();
	obj.prop("checked", false);
}

//통신후 tag 추가
function resultTagAdd(data){
	var tag = '<li><input type="checkbox" id="'+data.id+'_'+data.code+'" onclick="onToggle(this, {id:\''+data.id+'\',code:\''+data.code+'\',name:\''+data.name+'\'})"> <label>'+data.name+'</label></li>'
	return tag;
}

// 퀵메뉴에 추가
function quickTagAdd(data){
	var tag ='<input type="button" id="'+data.id+'" value="'+data.name+'" onclick="quickDelete(this, {id:\''+data.id+'\',code:\''+data.code+'\',name:\''+data.name+'\'})" class="sChoiced"/>';
	$("#searchName").before(tag);
	$("#searchText").addClass("searchText searchWord");
}

// 체크박스 정보 퀵메뉴에  추가
function quickCheckTagAdd(data){
	var tag ='<input type="button" id="Q_'+data.id+'_'+data.code+'" value="'+data.name+'" onclick="quickDelete(this, {id:\''+data.id+'\',code:\''+data.code+'\',name:\''+data.name+'\'})" class="sChoiced"/>';
	$("#searchName").before(tag);
	$("#searchText").addClass("searchText searchWord");
}

//퀵메뉴에 삭제
function quickTagDelete(data) {
	if(oSearch.words.length < 1) {
		$("#searchText").removeClass().addClass("searchText");
	}
	$(".inputArea").find("input[type=button]").each(function(){
		if($(this).val() == data.name) {
			$(this).remove();
		}
	});
}

//checkbox형 퀵메뉴에 삭제
function quickTagCheckDelete(data) {
	if(oSearch.words.length < 1) {
		$("#searchText").removeClass().addClass("searchText");
	}
	$("#Q_"+data.id+"_"+data.code).remove();
}

//퀵메뉴 ID로 삭제
function quickIdDelete(data) {
	$(".inputArea #"+data.id).remove();
}

// 퀵검색 글 자동완성 선택시
function quickTextSelect(code, name){
	console.log(code, name);
	var selData = {};
	selData.id = "SEARCH_WORD_CAR";
	selData.code = code;
	selData.name = name;
	$("#dropdown").html("");
	$("#searchName").val("");
	$(".searchSelect").hide();
	if(!isCodeInWords(selData.id, selData.code)){
		var obj = $("#"+selData.id+"_"+selData.code);
		if(obj.val() != undefined) {
			$("#"+selData.id+"_"+selData.code).trigger("click");
		} else {
			quickCheckTagAdd(selData);
			setWords(selData);
		}
	}
	carShowHide(selData);
}

// 매물정보 선택시 동적 화면 정보 변경
function carShowHide(data) {
	/* 모델 DL 부분 DISPLAY 여부
		- 제조사 선택 갯수가 하나
		- 모델 정보가 존재할경우
	*/
	if( oSearch.makers.length == 1 && oSearch.models.length > 0) {
		// 제조사 코드와 모델 코드 앞부분이 동일할 경우
		if(oSearch.makers[0] == oSearch.models[0].substring(0, 3)) {
			$("#modelDl").show();
		}
	}
	/* 상세 모델 DL 부분 DISPLAY 여부
		- 모델 선택 갯수가 하나
		- 상세모델  정보가 존재할경우
	*/
	if( oSearch.models.length == 1 && oSearch.modelDtls.length > 0) {
		// 모델 코드와 상세 모델 코드 앞부분이 동일할 경우
		if(oSearch.models[0] == oSearch.modelDtls[0].substring(0, 5)) {
			$("#detailModelDl").show();
		}
	}

	// 제조사 선택갯수가 하나가 아니라면 모델, 상세모델을 가림
	if($("#makeList input[id^="+data.id+"]:checked").length != 1) {
		$("#modelDl").hide();
		$("#detailModelDl").hide();
	}

	// 제조사, 모델, 상세모델들이 선택이 안된경우 상세모델 정보를 보여주지 않는다
	if($("#modelList input[id^="+data.id+"]:checked").length != 1 ||
			oSearch.makers.length != 1 || oSearch.models.length != 1 ) {
		$("#detailModelDl").hide();
	}
	// 상세 모델정보를 가져와 제조사 정보와 비교하여 같지 않다면 모델, 상세모델 정보를 보여주지 않는다
	$.each(oSearch.modelDtls, function(index, code){
		if(code.substring(0, 3) != oSearch.makers[0]) {
			$("#modelDl").hide();
			$("#detailModelDl").hide();
		}
	});
}


//검색단어 추가
var setWords = function(data){
	// seekbar 검색단어 추가 분기처리
	if((data.id == 'SEARCH_WORD_PRICE' || data.id == 'SEARCH_WORD_MILEAGE' ) &&
			getGroupIdCodesInWords(data.id).length > 0){
		appendSearchWord(data);
	}else if((data.code.length > 0) || (data.codeMin != "undefined" && data.codeMax != "undefined")){
		if(data.id == 'SEARCH_WORD_AREA') {
			data.code = data.name;
		}
		oSearch.words.push(data);
		appendSearchWord(data);
	}
	pageInit();
	getList();
};
//검색단어 제외
var delWords = function(data){
	var flag = true;

	// 단일삭제
	if(data.code){
		var idx = getCodeIdxInWords(data.id, data.code);
		popSearchWord(oSearch.words.splice(idx,1)[0]);
	}
	// 그룹아이디로 삭제
	else{
		var i = 0;
		while(i < oSearch.words.length && getCodeIdxInWords(data.id) > -1){
			var idx = getCodeIdxInWords(data.id);
			popSearchWord(oSearch.words.splice(idx,1)[0]);
		}
	}
	pageInit();
	getList();
};

//검색단어 제외
var delWordsNoSearch = function(id){
    var i = 0;
    while(i < oSearch.words.length && getCodeIdxInWords(id) > -1){
        var idx = getCodeIdxInWords(id);
        popSearchWord(oSearch.words.splice(idx,1)[0]);
    }
};

// sort 선택
var sortListView = function() {
	if($("#sortDiv").hasClass('active')) {
		$("#sortDiv").prop("class", "selectArea");
	} else {
		$("#sortDiv").addClass("active");
	}
};

// 정렬 정보 변경시
var sortChange = function(obj, type) {
	switch(type){
	case '01':
		oSearch.sort = "T1.CREATED_DATE DESC";
		break;
	case '02':
		oSearch.sort = "T1.PV_CNT";
		break;
	case '03':
		oSearch.sort = "convert(T1.MAKER_NAME||T1.MODEL_NAME||T1.DETAIL_MODEL_NAME||T1.GRADE_NAME, 'ISO2022-KR')";
		break;
	case '04':
		oSearch.sort = "T1.SALE_AMT";
		break;
	case '05':
		oSearch.sort = "T1.SALE_AMT DESC";
		break;
	}
	$("#sortSpan").text($(obj).text());
	$("#sortDiv").prop("class", "selectArea");
	pageInit();
	getList();
};

// 찜하기
var disbClick = function(code,car){
	util.getDibsOnCar(car);
}

// 페이징
$(window).scroll(function() {
    if ($(window).scrollTop() == $(document).height() - $(window).height()) {
    	if(oPageInfo.bHasMore){
	    	oPageInfo.currentPageNo++;
	    	getList("PAGE");
    	}
    }
});

//페이지정보 초기화
var pageInit = function(){
	// 매물페이징 영역 div 사이즈 조정
	$("#defaultList").css('height', $(window).height() - 50 );
	$(window).resize(function() {
		$("#defaultList").css('height', $(window).height() - 50 );
	});

	oPageInfo.totListSize = 0;		// 전체 리스트 갯수
	oPageInfo.data = {};				// 검색조건 파라미터
	oPageInfo.currentPageNo = 1;		// 현재 페이지 번호
	oPageInfo.bLoad = false;			// 데이터 로딩 상태 여부
	bInitializing = true;			// 초기화 여부
	oPageInfo.bHasMore = false;		// 로드할 데이터 여부
};

var reload = function(){
	localStorage.setObject("searchData", null);
	location.reload();
}
var loadSearchData = function(){
	var searchData = localStorage.getObject("searchData"); //검색정보 불러오기
	if(searchData){ oSearch = searchData; }


	oSearch.words.forEach(function(data){
		switch(data.id){
		//step 1. 차량에 대한 퀵검색, 체크 처리
		case 'SEARCH_WORD_CAR':
			$('#'+data.id+'_'+data.code).prop('checked', true);
			/* 제조사 선택시 모델정보 요청
			- 제조사 선택 갯수가 하나
			- code 가 제조사 코드일때
			*/
			if( oSearch.makers.length == 1 && data.code.length == 3) {
				getCarDetailList("M", oSearch.makers[0]);
			}
			/* 모델 선택시 상세 모델정보 요청
				- 모델 선택 갯수가 하나
				- code 가 모델 코드일때
			*/
			else if ( oSearch.models.length == 1 && data.code.length == 5) {
				getCarDetailList("D", oSearch.models[0]);
			}
			quickCheckTagAdd(data);
			break;

		//step 2. 체크박스가 아닌 것들에 대한 퀵검색 처리
		case 'SEARCH_WORD_PRICE':
			quickTagAdd(data);
			seekbar.changeMinPrice = data.codeMin ? data.codeMin : seekbar.changeMinPrice;
			seekbar.changeMaxPrice = data.codeMax ? data.codeMax : seekbar.changeMaxPrice;
			break;
		case 'SEARCH_WORD_MILEAGE':
			quickTagAdd(data);
			seekbar.changeMinMileage = data.codeMin ? data.codeMin : seekbar.changeMinMileage;
			seekbar.changeMaxMileage = data.codeMax ? data.codeMax : seekbar.changeMaxMileage;
			break;
		case 'SEARCH_WORD_MODEL_YEAR':
			$('#'+data.id).val(oSearch.modelYear);
			quickTagAdd(data);
		case 'SEARCH_WORD_CAR_NUMBER':
			$('#'+data.id).val(oSearch.carNumber);
			quickTagAdd(data);
			break;
		//step 3. 나머지 체크박스인 것들에 대한 퀵검색, 체크 처리
		case 'SEARCH_WORD_AREA':
			$("#areaList li").each(function(){
				if(this.dataset.value == data.name){
					$(this).find(':checkbox').prop('checked', true);
				}
			});
			getMarketList(oSearch.areas);
			quickCheckTagAdd(data);
			$("#danjiDl").show();
			break;
		case 'SEARCH_WORD_DANJI':
			quickCheckTagAdd(data);
			break;
		default:
			$('#'+data.id+'_'+data.code).prop('checked', true);
			quickCheckTagAdd(data);
		}
	});

}
</script>