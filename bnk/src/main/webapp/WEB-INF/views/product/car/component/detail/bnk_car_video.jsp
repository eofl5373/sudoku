<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<c:if test="${not empty car.label.carVideoUrl}">
<div class="innerLayout bgGrayCase">
    <div class="dataSet">
        <h2>동영상보기</h2>
        <div class="dataItem">
            <div class="embedBack"><div class="embedContents">${car.label.carVideoUrl}</div></div>
        </div>
    </div>
</div>
</c:if>