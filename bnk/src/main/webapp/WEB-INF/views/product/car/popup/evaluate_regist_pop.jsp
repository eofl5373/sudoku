<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- 평가등록 -->
<div class="popupAutoWrap appraisePop p-container w670">
    <!-- popup header -->
    <div class="popupHeaderAuto">
        <header><h1>평가등록</h1></header>
        <a class="btnClose p-close" onclick="return false;"><em class="blind">닫기</em></a>
    </div>
    <hr/>
    <!-- popup contents -->
    <div class="popupContents">
        <section>
            <div class="autoPop">
                <div class="ratingArea">
                    <span>
                        <label for="ch1"><input type="checkbox" name="evalDiv" id="ch1" value="1" checked /> 상담</label>
                        <label for="ch2"><input type="checkbox" name="evalDiv" id="ch2" value="2"/> 거래</label>
                    </span>
                    <span>
                        <strong>별점</strong>
                        <div class="rating"  data-rateit-step="1"></div>
                        <i>(0/100)</i>
                    </span>
                </div>
                <textarea name="reviews" cols="10" rows="8" placeholder="평가 내용을 입력하세요." maxlength="100"></textarea>
                <div class="btnAreaType mt40">
                    <span><button type="button"  class="line p-close">취소</button></span>
                    <span><button type="button" onclick="javascript:evaluate_regist()">등록</button></span>
                </div>
            </div>
        </section>
    </div>
</div>
<!-- //평가등록 -->