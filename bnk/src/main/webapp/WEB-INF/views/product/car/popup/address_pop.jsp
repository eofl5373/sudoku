<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- 2017-08-03 우편번호 변경 -->
<div class="popupAutoWrap findAddress p-container w670">
    <!-- popup header -->
    <div class="popupHeaderAuto">
        <header><h1>주소찾기</h1></header>
        <a class="btnClose p-close" onclick="return false;"><em class="blind">닫기</em></a>
    </div>
    <hr/>
    <!-- popup contents -->
	<div class="popupContents">
		<section>
			<div class="autoPop">
				<div class="searchArea">
					<input type="hidden" id="selectAddrCode" value="" >
					<input type="hidden" id="selectAddr" value="" >
					<input type="text" id="searchAddrName" value="" placeholder="시/군/구/도로명" onkeypress="if(event.keyCode===13){searchAddr();}">
					<button type="button" id="btnSearchAddr" class="iconSearch"><span>검색</span></button>
				</div>
				<div class="popList">
					<ul id="dataList">
                     </ul>
				</div>
				<div class="btnAreaType">
					<span><button id="btnAddrCancel" class="line p-close">취소</button></span>
					<span><button id="btnAddrSelect" >확인</button></span>
				</div>
			</div>
		</section>
	</div>
</div>

<form name="addrFrm" id="addrFrm" method="post">
	<input type="hidden" name="currentPage" value="1"/>   					<!-- 요청 변수 설정 (현재 페이지. currentPage : n > 0) -->
	<input type="hidden" name="countPerPage" value="20"/> 					<!-- 요청 변수 설정 (페이지당 출력 개수. countPerPage 범위 : 0 < n <= 100) -->
	<input type="hidden" name="resultType" value="json"/>   				<!-- 요청 변수 설정 (검색결과형식 설정, json) -->
	<input type="hidden" name="confmKey" value="U01TX0FVVEgyMDE3MDcxNDIyNTEwNDIyOTIy"/>	<!-- 요청 변수 설정 (승인키) -->
	<input type="hidden" id="keyword" name="keyword" value=""/>				<!-- 요청 변수 설정 (키워드) -->
</form>