<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<c:if test="${not empty car.label.carDesc}">
<div class="innerLayout">
    <div class="dataSet">
        <h2>차량설명</h2>
        <script>
            $( document ).ready( function() {
                $('.moreToggle').bind('tap click', function(){
                    $('.moreArea').toggleClass('on');
                    $(this).toggleClass('on');
                });
            });
        </script>
            <div class="dataItem">
                <div class="moreArea">
                	<pre>${car.label.carDesc}</pre>
                </div>
                <div class="btnSet mt40">
                    <span><a class="gray viewCase moreToggle"><strong>차량설명</strong> <em class="off">더보기 +</em></a></span>
                </div>
            </div>
    </div>
</div>
</c:if>