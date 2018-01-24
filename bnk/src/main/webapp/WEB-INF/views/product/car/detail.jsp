<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
    <!-- contents -->
    <div class="contents">
        <section>
            <div class="carDetail">
                <!-- 매물정보 -->
                <jsp:include page="component/detail/bnk_car_summary.jsp">
                	<jsp:param value="${car}" name="car"/>
                </jsp:include>

                <!-- 차량정보 -->
				<jsp:include page="component/detail/bnk_car_info.jsp">
					<jsp:param value="${car}" name="car"/>
				</jsp:include>

				<!-- BNK 시세 -->
				<jsp:include page="component/detail/bnk_car_price.jsp">
					<jsp:param value="${price}" name="price"/>
				</jsp:include>

				<!-- 옵션정보 -->
				<jsp:include page="component/detail/bnk_car_option.jsp">
					<jsp:param value="${car}" name="car"/>
				</jsp:include>


				<!-- 성능점검사항 -->
				<div class="innerLayout bgGrayCase">
                    <div class="dataSet carInfo">
                        <h2>성능점검사항</h2>
                        <div class="btnSet mt40">
                            <span><a href="http://www.autocafe.co.kr/asso/carcheck_kfc.asp?carcheckno=${car.carCheckNo}" class="redLine viewCase" target="_blank"><em><strong>성능점검표</strong> 보기</em></a></span>
                        </div>
                    </div>
                </div>

				<!-- 사고이력정보 -->
				<div class="innerLayout">
                    <div class="dataSet carInfo accident">
                        <h2>사고이력정보 </h2>
                        <c:if test="${!empty sago}">
						<div class="dataItem">
                            <dl>
                                <dt class="first">차량번호</dt>
                                <dd class="first">${sago.CARNUM}</dd>
                                <dt>보험사고 (내차피해)</dt>
                                <dd>${sago.MCARDAMAGE}</dd>
                                <dt class="last">보험사고 (타차피해)</dt>
                                <dd>${sago.OCARDAMAGE}</dd>
                            </dl>
                            <dl>
                                <dt class="first">소유자 변경 횟수</dt>
                                <dd class="first">${sago.CAROWNERCHG}회</dd>
                                <dt>자동차 번호 변경 횟수</dt>
                                <dd>${sago.CARNUMCHG}회</dd>
                                <dt class="last">자동차 보험 특수사고</dt>
                                <dd class="last">
                                	<c:if test="${sago.LOSS eq '0'}">없음</c:if>
                                	<c:if test="${sago.LOSS ne '0'}">${sago.LOSS}</c:if>,
                                	<c:if test="${sago.FLOOD eq '0'}">없음</c:if>
                                	<c:if test="${sago.FLOOD ne '0'}">${sago.FLOOD}</c:if>, 
                                	<c:if test="${sago.ROBBED eq '0'}">없음</c:if>
                                	<c:if test="${sago.ROBBED ne '0'}">${sago.ROBBED}</c:if>
                                </dd>
                            </dl>
                        </div>
                        </c:if>                        
                        <div class="btnSet mt40">
                            	<!-- >a href="http://www.carhistory.or.kr/main.car?realm=?" class="redLine viewCase" target="_blank" -->
					       	<c:choose>
					       		<c:when test="${empty sago}">
								<span class="btnaccNonPop btn-popup-auto"><a class="redLine viewCase"><em><strong>사고이력정보</strong> 보기</em></a></span>					       	
					       		</c:when>
					       		<c:otherwise>
					       		<span class="btnaccPop btn-popup-auto" data-pop-opts='{"target": ".carAccident"}'><a class="redLine viewCase"><em><strong>사고이력정보</strong> 보기</em></a></span>
					       		</c:otherwise>
					       	</c:choose>                     	
                        </div>
                    </div>
                </div>

				<!-- 동영상보기 -->
				<jsp:include page="component/detail/bnk_car_video.jsp">
					<jsp:param value="${car}" name="car"/>
				</jsp:include>

				<!-- 차량설명 -->
				<jsp:include page="component/detail/bnk_car_desc.jsp">
					<jsp:param value="${car}" name="car"/>
				</jsp:include>

				<!-- 판매자정보 -->
				<jsp:include page="component/detail/bnk_dealer_info.jsp">
					<jsp:param value="${car}" name="car"/>
				</jsp:include>

				<!-- 판매자거래평가 -->
				<div id="evaluateList" class="innerLayout"></div>
				
				<div class="innerLayout bgGrayCase">
					<div class="dataSet">
						<!-- 추천차량리스트 -->
						<jsp:include page="component/detail/bnk_recommend_list.jsp">
							<jsp:param value="${recommend}" name="recommend"/>
						</jsp:include>
		
						<!-- 내게맞는매물등록 -->
						<jsp:include page="component/detail/bnk_car_reauction.jsp">
							<jsp:param value="${reverse}" name="reverse"/>
						</jsp:include>
					</div>
				</div>
            </div>
        </section>
    </div>
    <!-- //contents -->