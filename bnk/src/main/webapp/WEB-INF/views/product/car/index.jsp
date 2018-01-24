<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
	<!-- leftMenu -->
	<aside class="leftMenu">
		<div class="writeFullType">
			<dl>
				<dt class="title">최대 가격<i>PRICE</i></dt>
				<dd class="data">
					<div class="sliderRange">
                        <div id="priceTxt" class="valueTxt"></div>
                        <div id="price" onchange="onSeekChange(this, {id:'SEARCH_WORD_PRICE'});"></div>
						<!-- <output id="priceVal"></output><i>만원</i>
						<input type="range" id="price" min="" max="" value="" onchange="onSeekChange(this, {id:'SEARCH_WORD_PRICE'});" data-rangeslider> -->
					</div>
				</dd>
			</dl>
			<dl>
				<dt class="title">최대 주행거리<i>MILEAGE</i></dt>
				<dd class="data">
					<div class="sliderRange">
                        <div id="mileageTxt" class="valueTxt"></div>
                        <div id="mileage" onchange="onSeekChange(this, {id:'SEARCH_WORD_MILEAGE'});"></div>
						<!-- <output id="mileageVal"></output><i>km</i>
						<input type="range" id="mileage" min="" max="" value="" onchange="onSeekChange(this, {id:'SEARCH_WORD_MILEAGE'});" data-rangeslider> -->
					</div>
				</dd>
			</dl>
			<!-- dl>
				<dd class="data">
					<label class="mr15"><input type="checkbox" id="SEARCH_WORD_COUNTRY_A" onclick="onToggle(this, {id:'SEARCH_WORD_COUNTRY',code:'A', name:'국산차'})" /> 국산차</label>
					<label><input type="checkbox" id="SEARCH_WORD_COUNTRY_B" onclick="onToggle(this, {id:'SEARCH_WORD_COUNTRY',code:'B', name:'외제차'})"/> 외제차</label>
				</dd>
			</dl -->
		</div>
		<div class="btn-accordion-wrapper writeType" data-toggle-on="true">
			<!---->
			<dl class="btn-accordion-switch accordionSet">
				<dt class="accordionTitle btn-accordion-switch-item">
					<strong>매물등급구분</strong>
				</dt>
				<dd class="accordionData">
					<ul class="dotList">
						<li><input type="checkbox" id="SEARCH_WORD_PROMOTION_A" onclick="onToggle(this, {id:'SEARCH_WORD_PROMOTION',code:'A',name:'프리미엄'})" for="r1"/> <label for='r1'>프리미엄</label></li>
						<li><input type="checkbox" id="SEARCH_WORD_PROMOTION_B" onclick="onToggle(this, {id:'SEARCH_WORD_PROMOTION',code:'B',name:'인증중고차'})" for="r2"/> <label for='r2'>인증중고차</label></li>
					</ul>
				</dd>
			</dl>
			<!---->
			<dl class="btn-accordion-switch accordionSet">
				<dt class="accordionTitle btn-accordion-switch-item">
					<strong>제조사<i>MAKE</i></strong>
				</dt>
				<dd class="accordionData">
					<ul id="makeList" class="dotList overflowCase">
						<c:forEach var="makerInfo" items="${makerList}" varStatus="status">
							<li><input type="checkbox" id="SEARCH_WORD_CAR_${makerInfo.key}" onclick="onToggle(this, {id:'SEARCH_WORD_CAR',code:'${makerInfo.key}',name:'${makerInfo.value}'})" /> <label>${makerInfo.value}</label></li>
						</c:forEach>
					</ul>
				</dd>
			</dl>
			<!---->
			<dl id="modelDl" class="btn-accordion-switch accordionSet">
				<dt class="accordionTitle btn-accordion-switch-item">
					<strong>모델<i>MODEL</i></strong>
				</dt>
				<dd class="accordionData">
					<ul id="modelList" class="dotList overflowCase">
					</ul>
				</dd>
			</dl>
			<!---->
			<dl id="detailModelDl" class="btn-accordion-switch accordionSet">
				<dt class="accordionTitle btn-accordion-switch-item">
					<strong>상세모델<i class="fs10">DETAIL MODEL</i></strong>
				</dt>
				<dd class="accordionData">
					<ul id="detailModelList" class="dotList overflowCase">
					</ul>
				</dd>
			</dl>
			<!---->
			<dl class="btn-accordion-switch accordionSet">
				<dt class="accordionTitle btn-accordion-switch-item">
					<strong>연식 <i>YEAR</i></strong>
				</dt>
				<dd class="accordionData">
					<div class="p10"><!-- 2017-08-22 -->
						<input type="text" id="SEARCH_WORD_MODEL_YEAR" maxlength="4" onblur="onToggle(this, {id:'SEARCH_WORD_MODEL_YEAR'})"/>
					</div>
				</dd>
			</dl>
			<!---->
			<dl class="btn-accordion-switch accordionSet">
				<dt class="accordionTitle btn-accordion-switch-item">
					<strong>색상<i>COLOR</i></strong>
				</dt>
				<dd class="accordionData colorck">
					<c:forEach var="colorInfo" items="${colorList}" varStatus="status">
						<span class="${colorInfo.cdSubNo}"><label for="SEARCH_WORD_COLOR_${colorInfo.cdDtlNo}"><input type="checkbox" id="SEARCH_WORD_COLOR_${colorInfo.cdDtlNo}" onclick="onToggle(this, {id:'SEARCH_WORD_COLOR',code:'${colorInfo.cdDtlNo}',name:'${colorInfo.cdDtlNm}'})" /> ${colorInfo.cdDtlNm}</label></span>
					</c:forEach>
				</dd>
			</dl>
			<!---->
			<dl class="btn-accordion-switch accordionSet">

				<dt class="accordionTitle btn-accordion-switch-item">
					<strong>지역<i>AREA</i></strong>
				</dt>
				<dd class="accordionData">
					<ul class="dotList overflowCase" id="areaList" >
						<c:forEach var="areaInfo" items="${areaList}" varStatus="status">
							<li data-value="${areaInfo.cdDtlNm}"><input type="checkbox" id="SEARCH_WORD_AREA_${status.count}" onclick="onToggle(this, {id:'SEARCH_WORD_AREA',code:'${status.count}',name:'${areaInfo.cdDtlNm}'})" /> <label for="SEARCH_WORD_AREA_${status.count}">${areaInfo.cdDtlNm}</label></li>
						</c:forEach>
					</ul>
				</dd>
			</dl>
			<!---->
			<dl id="danjiDl" class="btn-accordion-switch accordionSet">
				<dt class="accordionTitle btn-accordion-switch-item">
					<strong>매매단지<i>DETAIL AREA</i></strong>
				</dt>
				<dd class="accordionData">
					<ul class="dotList" id="danjiList">
					</ul>
				</dd>
			</dl>
			<!---->
			<dl class="btn-accordion-switch accordionSet">
				<dt class="accordionTitle btn-accordion-switch-item">
					<strong>차량번호</strong>
				</dt>
				<dd class="accordionData">
					<div class="p10"><!-- 2017-08-22 -->
						<input type="text" id="SEARCH_WORD_CAR_NUMBER" onblur="onToggle(this, {id:'SEARCH_WORD_CAR_NUMBER'});" maxlength="10"/>
					</div>
				</dd>
			</dl>
		</div>
	</aside>
	<!-- //leftMenu -->

	<!-- contents -->
	<div class="contents">
	<section>
		<div class="topSearch">
			<div id="searchText" class="searchText"><!-- 검색단어 노출시 class="searchWord" 삽입 -->
				<div class="back">
					<div class="inputArea">
						<input type="text" id="searchName" class="wFull readonly" placeholder="원하는 매물 정보를 입력하세요">
					</div>
					<input type="button" onclick="reload();" value="초기화" class="tsSeset" />
				</div>
				<div class="searchSelect" style="display:none;">
					<ul id="dropdown" >
					</ul>
				</div>
			</div>
		</div>
		<!-- -->
		<div class="titBar">
			<h2><em>매물수 <i id="defultCount">0</i>대</em></h2><!-- 일반매물 글자삭제 -->
			<div id="sortDiv" class="selectArea "><!-- class="active" 추가 시 하단 셀렉메뉴 펼쳐짐 -->
				<span id="sortSpan" onclick="sortListView()">최근등록순</span>
				<ul>
					<li onclick="sortChange(this, '01')">최근등록순</li>
					<li onclick="sortChange(this, '02')">인기순</li>
					<li onclick="sortChange(this, '03')">상품명순</li>
					<li onclick="sortChange(this, '04')">낮은 가격순</li>
					<li onclick="sortChange(this, '05')">높은 가격순</li>
				</ul>
			</div>
		</div>
		<ul id="defaultList" class="listType1">
			<li style="display:none;">
				<div class="listSet">
					<div class="thumBack">
						<a>
							<span class="saleCar">
								<span class="imgBack">
									<strong><img src="/product/images/thumbnail/sample1.png"></strong>
								</span>
								<img src="/product/images/thumbnail/listCarCorver.png" alt="" class="corver">
							</span>
						</a>
					</div>
					<div class="prBack">
						<a>
							<span class="productInfo">
								<span class="tit"><strong>기아</strong><span>더뉴K5</span><span>2.0 럭셔리</span></span>
								<span class="option"><em>2015년식</em><em>부산</em><em>34,905 km</em></span>
							</span>
							<span class="markGroup">
								<span class="markSet mark4">연장</span>
								<strong class="goodsPrice"> 1,850<i>만원</i></strong>
							</span>
						</a>
					</div>
					<span class="heartSet"><input type="checkbox" id="component_113090" onclick="javascript:util.loginCheck('DIBS_ON', 113090, this)">
					<label for="component_113090"><!--찜하기--></label></span>
				</div>
				<img src="/product/images/thumbnail/listCorver.png" alt="" class="listCorver">
			</li>
		</ul>
	</section>
	</div>
	<!-- //contents -->