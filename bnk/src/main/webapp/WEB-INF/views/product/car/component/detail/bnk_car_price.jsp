<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!-- BNK 시세 -->
<div class="innerLayout bgGrayCase">
    <div class="dataSet">
        <h2>BNK 시세</h2>
        <div class="dataItem">
            <!-- pGraphSet -->
            <script>
                $(document).ready(function(){

					function start(){
						$(".pRoad").animate({width: '100%'}, 800,function(){
			                $(".pSection").show();
	           				$(".pSection span").animate({width: '${price.range_width}',marginLeft: '${price.range_pos}'},function(){
	           					$(".pSection span em").animate({width: $(".pSection span").width()-42});
	           				});
           				});
	           			setTimeout(function(){
	           				$(".pgCar").show();
	           				$(".pgPos div").animate({width: '${price.car_width}'},function(){
	           					$(".pgPos em").animate({left: '${price.car_pc_margin}'}, 1500);
	           					$(".pgPos div").animate({marginLeft: '${price.car_pos}'}, 1500,'easeInExpo',function(){ // 2017-06-07 그래프 움직임 변경
	           						$(".pgPos em strong").show();
	           					});
	           				});
	           			}, 2000);
					}
					start();
				});
            </script>
            <div class="pGraphSet">
                <c:if test="${ct:equals(price.waited, 'N')}">
                <div class="pGraphCanvas">
                    <div class="pgCar" style="display:none">
                        <div class="pgPos">
                           	<em style="left: -500px;">
                           		<strong style="display:none">
                           			<span>
                           				<fmt:formatNumber value="${price.amt}" groupingUsed="true"/>
                           			</span>만원
                           		</strong>
                           	</em>
                            <div>
                            </div>
                            <!-- div : 노란색 도로와 동일하게 자동차 위치 1차 지정 -->
                            <!-- em : 1차 지정 후 실제 빨간도로 내에서 2차 위치 지정 -->
                        </div>
                    </div>
                    <div class="pRoad">
                        <div class="pRail"><span></span></div>
						<div class="pSection" style="display:none">
							<div><span><em><!-- BNK시세영역 --></em></span></div>
                            <!-- 노란색 도로 : 시작점 - 기본 15% 이후 %로 가감하여 적용 / 가격넓이 % 가감 -->
                        </div>
                    </div>
                    <div class="pNumber">
                        <div><!-- 가격은 총 5개로 나뉘며, 각 단계별 20%  -->
                            <span><fmt:formatNumber value="${price.min}" groupingUsed="true"/></span>
                            <span><fmt:formatNumber value="${price.min}" groupingUsed="true"/></span>
                            <span><fmt:formatNumber value="${price.mid}" groupingUsed="true"/></span>
                            <span><fmt:formatNumber value="${price.max}" groupingUsed="true"/></span>
                            <span><fmt:formatNumber value="${price.max}" groupingUsed="true"/></span>
                        </div>
                    </div>
                </div>
				</c:if>
                <!---->
                <c:if test="${ct:equals(price.waited, 'Y')}">
                <div class="noGraphCanvas">
                    <strong>시세 준비중입니다</strong>
					해당 차량의 시세 정보가 없습니다
                </div>
                </c:if>
                <!---->

                <div class="pGraphInfo">
                    <ul>
                        <li>
                            <span>현재매물가</span>
                            <strong><fmt:formatNumber value="${price.amt}" groupingUsed="true"/> <i>만원</i></strong>
                        </li>
                        <li>
                            <span>BNK 소매거래시세</span>
                            <c:if test="${ct:equals(price.waited, 'N')}">
                            <strong><fmt:formatNumber value="${price.old_min}" groupingUsed="true"/> ~ <fmt:formatNumber value="${price.old_max}" groupingUsed="true"/> <i>만원</i></strong>
                            </c:if>
                            <c:if test="${ct:equals(price.waited, 'Y')}">
                            <strong>시세 준비중</strong>
                            </c:if>
                        </li>
                        <li>
                            <span>신차출고가</span>
                            <strong><fmt:formatNumber value="${price.new_min}" groupingUsed="true"/> ~ <fmt:formatNumber value="${price.new_max}" groupingUsed="true"/> <i>만원</i></strong>
                        </li>
                    </ul>
                </div>
            </div>
            <!-- //pGraphSet -->
        </div>
    </div>
</div>