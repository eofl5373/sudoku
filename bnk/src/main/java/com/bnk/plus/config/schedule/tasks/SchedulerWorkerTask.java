package com.bnk.plus.config.schedule.tasks;

import com.bnk.plus.commons.components.CoTopComponent;

public class SchedulerWorkerTask extends CoTopComponent {

	
	/*
	// 예제 스케쥴 입니다.
    @Scheduled(initialDelay=1000, fixedDelay=5000)
    public void tokenStoreTable() {
        log.debug("tokenstore table truncated - run" + env.getRequiredProperty("cert.mobile.email.day"));
    }
    */

	/**
	 * 30분 단위 배치
	 * 1. bnk 인증딜러 매물 업데이트
	 * 2. 성능 점검 번호 업데이트
	 */
	//@Scheduled(cron="0 10/40 * * * ?")
//	@Scheduled(initialDelay=30000, fixedDelay=1000*60*5)
	public void checkInCheckoutNo() {
		if(isLiveMode) {
			/*schLog.info("인증중고차 체크 START");
			int rtnCnt = -1;
			try {
				rtnCnt = carSchedulerService.regBnkConfCar();
			} catch (Exception e) {
				schLog.error(e.getMessage(), e);
			}
			schLog.info("인증중고차 체크 RESULT : " + rtnCnt);
			schLog.info("인증중고차 체크 END");
			
			
			schLog.info("성능점검표 체크 START");
			rtnCnt = -1;
			try {
				rtnCnt = carSchedulerService.chkCarCheckoutNo();
			} catch (Exception e) {
				schLog.error(e.getMessage(), e);
			}
			schLog.info("성능점검표 체크 RESULT : " + rtnCnt);
			schLog.info("성능점검표 체크 END");*/
		}
	}
	
//	@Scheduled(cron="0 0 * * * ?")
	public void checkInSaleCar() {
		if(isLiveMode) {
			/*schLog.info("실매물 정보 체크 START");
			int rtnCnt = -1;
			try {
				rtnCnt = carSchedulerService.chkOnSaleCar();
			} catch (Exception e) {
				schLog.error(e.getMessage(), e);
			}
			schLog.info("실매물 정보 체크 RESULT : " + rtnCnt);
			schLog.info("실매물 정보 체크 END");
			
			schLog.info("판매완료 정보 체크 START");
			rtnCnt = -1;
			try {
				rtnCnt = carSchedulerService.chkSoldCar();
			} catch (Exception e) {
				schLog.error(e.getMessage(), e);
			}
			schLog.info("판매완료 정보 체크 RESULT : " + rtnCnt);
			schLog.info("판매완료 정보 체크 END");*/
		}
	}
	
//	@Scheduled(cron="0 0 8-22 * * ?")
//	@Scheduled(fixedDelay=30000)
	public void getCarEstimateInfos() {
		if(isLiveMode) {
			/*schLog.info("딜러 견적 확인 START");
			int rtnCnt = -1;
			try {
				rtnCnt = carDealerBIGServiceImpl.getCarEstimate();
			} catch (Exception e) {
				schLog.error(e.getMessage(), e);
			}
			schLog.info("딜러 견적 확인 RESULT : " + rtnCnt);
			schLog.info("딜러 견적 확인 END");*/
		}
	}
	
	
//	@Scheduled(cron="0 0/30 8-22 * * ?")
//	@Scheduled(fixedDelay=30000)
	public void sendQuestionAnswer() {
		if(isLiveMode) {
			/*schLog.info("문의 답변 알림 START");
			try {
				carSchedulerService.sendQuestionAnswer();
			} catch (Exception e) {
				schLog.error(e.getMessage(), e);
			}
			schLog.info("문의 답변 알림 END");*/
		}
	}
	
	
	/**
	 * 내개 맞는 매물 2시간 마다 <br>
	 * app기동후 2시간 이후부터 2시간마다
	 */
//	@Scheduled(initialDelay=1000*60*60*2, fixedDelay=1000*60*60*2)
//	@Scheduled(fixedDelay=3000)
	public void chkRecommendCar() {
		if(isLiveMode) {
			/*schLog.info("매게 맞는 매물 push 발송 START");
			int rtnCnt = -1;
			try {
				rtnCnt = carSchedulerService.chkRecommendCar();
			} catch (Exception e) {
				schLog.error(e.getMessage(), e);
			}
			schLog.info("매게 맞는 매물 push 발송 RESULT : " + rtnCnt);
			schLog.info("매게 맞는 매물 push 발송 END");*/
		}
	}
	

//	@Scheduled(cron="0 0 22 * * ?")
//	@Scheduled(fixedDelay=30000)
	public void regBulkCarInfo() {
		if(isLiveMode) {
			/*schLog.info("매물정보 일괄 등록 START");
			Map<String, Integer> resultMap = null;
			try {
				resultMap = carSchedulerService.regBulkCarInfo();
			} catch (Exception e) {
				schLog.error(e.getMessage(), e);
			}
			
			if(resultMap != null) {
				schLog.info("totalCarCnt : " + resultMap.get("totalCarCnt") );
				schLog.info("totalRegCarCnt : " + resultMap.get("totalRegCarCnt"));
				schLog.info("totalDealerCnt : " + resultMap.get("totalDealerCnt"));
				schLog.info("totalRegDealerCnt : " + resultMap.get("totalRegDealerCnt"));
				schLog.info("errCnt : " + resultMap.get("errCnt"));
			}
			schLog.info("매물정보 일괄 등록 END");*/
		}
	}

	// 새벽 1시 스케줄 - 30분 지난 NVD 작업 Drop 처리
//	@Scheduled(cron="0 0 1 * * ?")


	// 0분 부터 5분 단위 스케줄 - 30분이 지난 메일은 삭제한다.
//	@Scheduled(cron="0 5,10,15,20,25,30,35,40,45,50,55 * * * *")

}
