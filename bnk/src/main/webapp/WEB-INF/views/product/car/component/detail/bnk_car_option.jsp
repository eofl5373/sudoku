<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="innerLayout">
    <div class="dataSet optionView">
        <h2>옵션정보</h2>
        <div class="dataItem">
            <ul>
            	<c:forEach var="code" items="${ct:getAllValues(ct:getConstDef('SYS_CODE_CAR_OPTION_BASIC'))}">
                    <li class="${ct:contains(car.optionCdArr, code[0]) ? '' : 'off'}">${code[2]}</li>
            	</c:forEach>
            </ul>
            <div class="btn-accordion-wrapper" data-toggle-on="true" data-multiple-on="true">
                <div class="btn-accordion-switch accordionSet">
                    <div class="accordionData optionViewMore">
                    	<c:forEach var="code" items="${ct:getAllValues(ct:getConstDef('SYS_CODE_CAR_OPTION_TYPE'))}">
                        <dl>
                            <dt>[${code[2]}]</dt>
                            <dd>
                                <ol>
                                	<c:forEach var="subCode" items="${ct:getAllValues(code[1])}">
                                        <li class="${ct:contains(car.optionCdArr, subCode[0]) ? '' : 'off'}">${subCode[2]}</li>
                                	</c:forEach>
                                </ol>
                            </dd>
                        </dl>
                    	</c:forEach>
                    </div>
                    <div class="btn-accordion-switch-item">
                        <div class="btnSet" onclick="optionView(this)">
                            <span><a class="gray viewCase"><strong>상세옵션</strong> <em class="off">보기 +</em></a></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>