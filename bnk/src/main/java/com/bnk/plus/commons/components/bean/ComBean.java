package com.bnk.plus.commons.components.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bnk.plus.commons.CoCommonFunc;
import com.bnk.plus.commons.CoConstDef;
import com.bnk.plus.commons.components.CoTopComponent;
import com.bnk.plus.commons.util.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class ComBean.
 */
/**
 * <pre>
 * ComBean.java
 * </pre>
 *
 * @author Administrator
 * @date 2016. 5. 12.
 */
/**
 * <pre>
 * ComBean.java
 * </pre>
 *
 * @author Administrator
 * @date 2016. 5. 12.
 */
public class ComBean extends CoTopComponent implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	// 로그인 User 아이디
	/**  Login User ID. */
	private String loginUserName;
	// 로그인 User 권한
	/**  Login Users Athority. */
	private String loginUserRole;
	// 현재 페이지번호
	/** The cur page. */
	private int curPage = 1;
	// 한 블록당 보여줄 글 수
	/** The page list size. */
	private int pageListSize = 10;
	// 노출할 블록 수
	/** The block size. */
	private int blockSize = 10;
	// 전체 글의 수
	/** The tot list size. */
	private int totListSize;
	// 전체 블록 수
	/** The tot block size. */
	private int totBlockSize;
	// Start Index
	/** The start index. */
	private int startIndex = 0;

	/**  전체 블록수. */
	private int totBlockPage;

	/**  현재 페이지의 몇번째 블록. */
	private int blockPage;

	/**  현재 블록의 시작 페이지. */
	private int blockStart;

	/**  현재 블록의 끝 페이지. */
	private int blockEnd;


	// 검색조건
	/** The sch condition. */
	private String schCondition;
	// 검색종류
	/** The sch type. */
	private int schType;
	// 다중 검색조건
	/** The sch conditions. */
	private String[] schConditions;
	// 검색어
	/** The sch value. */
	private String schValue;

	/* 데이터 정렬을 위한 조건 [S] */
	/** The sort field. */
	private String sortField = "";

	/** The sort order. */
	private String sortOrder = "";
	/* 데이터 정렬을 위한 조건 [E] */

	/* 필드 검색 조건 유지 [S] */
	/** The sch query string. */
	private String schQueryString;
	/* 필드 검색 조건 유지 [E] */

	// 공통 변수 영역 [S]
	/**  추천여부. (Y: 추천, N: 추천안함) */
	private String hotYn = "N";

	/**  등록자 ID. */
	private String regId;

	/**  등록일. */
	protected String regDt;

	/**  수정자 ID. */
	public String updId;

	/**  수정일시. */
	public String updDt;



	/**  삭제 여부. */
	private String delYn;
	// 공통 변수 영역 [E]

	  /**  날짜 검색 조건. */
    private String dateCondition;

    /**  시작 Date. */
    private String startDate;

    /**  끝 Date. */
    private String endDate;



	/**
	 * 접속한 유저 ID 가져오기.
	 *
	 * @return the login user name
	 */
	public String getLoginUserName() {
		return loginUserName;
	}

	/**
	 * 접속한 유저 Authority 가져오기.
	 *
	 * @return the login user role
	 */
	public String getLoginUserRole() {
		return loginUserRole;
	}

	/**
	 * 접속 유저 ID & Athority 세팅.
	 */
	public ComBean() {
		loginUserName = loginUserName();
		loginUserRole = loginUserRole();
	}
	/**
	 * Gets the cur page.
	 *
	 * @return the cur page
	 */
	public int getCurPage() {
		return curPage;
	}

	/**
	 * Sets the cur page.
	 *
	 * @param curPage the new cur page
	 */
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	/**
	 * Gets the page list size.
	 *
	 * @return the page list size
	 */
	public int getPageListSize() {
		return pageListSize;
	}

	/**
	 * Sets the page list size.
	 *
	 * @param pageListSize the new page list size
	 */
	public void setPageListSize(int pageListSize) {
		this.pageListSize = pageListSize;
	}

	/**
	 * Gets the block size.
	 *
	 * @return the block size
	 */
	public int getBlockSize() {
		return blockSize;
	}

	/**
	 * Sets the block size.
	 *
	 * @param blockSize the new block size
	 */
	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	/**
	 * Gets the tot list size.
	 *
	 * @return the tot list size
	 */
	public int getTotListSize() {
		return totListSize;
	}

	/**
	 * Total List Size 등록 (=전체 목록 갯수)
	 *
	 * 		+ Block Size와 Start Index를 갱신.
	 *
	 * @param totListSize the new tot list size
	 */
	public void setTotListSize(int totListSize) {
		this.totListSize = totListSize;
		if(pageListSize > 0){
			this.totBlockSize = totListSize/pageListSize < 1 ? 1 : totListSize%pageListSize==0?totListSize/pageListSize:(totListSize/pageListSize)+1;

			this.startIndex = (curPage-1)*pageListSize;

			int totBlockPage = (totBlockSize / blockSize);
			if(totBlockSize != blockSize) totBlockPage++;
			this.totBlockPage = totBlockPage;

			int blockPage = ((curPage-1) / blockSize) + 1;
			this.blockPage = blockPage;

			int blockStart = ((blockPage-1) * blockSize) + 1;
			int blockEnd = blockStart+blockSize-1;
			if(blockEnd > totBlockSize) blockEnd = totBlockSize;

			this.blockStart = blockStart;
			this.blockEnd = blockEnd;
		}
	}

	/**
	 * Gets the tot block size.
	 *
	 * @return the tot block size
	 */
	public int getTotBlockSize() {
		return totBlockSize;
	}

	/**
	 * Sets the tot block size.
	 *
	 * @param totBlockSize the new tot block size
	 */
	public void setTotBlockSize(int totBlockSize) {
		this.totBlockSize = totBlockSize;
	}

	/**
	 * Gets the start index.
	 *
	 * @return the start index
	 */
	public int getStartIndex() {
		return startIndex;
	}

	/**
	 * Sets the start index.
	 *
	 * @param startIndex the new start index
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	/**
	 * Gets the tot block page.
	 *
	 * @return the tot block page
	 */
	public int getTotBlockPage() {
		return totBlockPage;
	}

	/**
	 * Sets the tot block page.
	 *
	 * @param totBlockPage the new tot block page
	 */
	public void setTotBlockPage(int totBlockPage) {
		this.totBlockPage = totBlockPage;
	}

	/**
	 * Gets the block page.
	 *
	 * @return the block page
	 */
	public int getBlockPage() {
		return blockPage;
	}

	/**
	 * Sets the block page.
	 *
	 * @param blockPage the new block page
	 */
	public void setBlockPage(int blockPage) {
		this.blockPage = blockPage;
	}

	/**
	 * Gets the block start.
	 *
	 * @return the block start
	 */
	public int getBlockStart() {
		return blockStart;
	}

	/**
	 * Sets the block start.
	 *
	 * @param blockStart the new block start
	 */
	public void setBlockStart(int blockStart) {
		this.blockStart = blockStart;
	}

	/**
	 * Gets the block end.
	 *
	 * @return the block end
	 */
	public int getBlockEnd() {
		return blockEnd;
	}

	/**
	 * Sets the block end.
	 *
	 * @param blockEnd the new block end
	 */
	public void setBlockEnd(int blockEnd) {
		this.blockEnd = blockEnd;
	}
	/**
	 * Gets the sch condition.
	 *
	 * @return the sch condition
	 */
	public String getSchCondition() {
		return schCondition;
	}

	/**
	 * Sets the sch condition.
	 *
	 * @param schCondition the new sch condition
	 */
	public void setSchCondition(String schCondition) {
		this.schCondition = schCondition;
	}

	/**
	 * Sets the sch conditions.
	 *
	 * @param schConditions the new sch conditions
	 */
	public void setSchConditions(String[] schConditions) {
		this.schConditions = schConditions;
	}

	/**
	 * Gets the sch conditions.
	 *
	 * @return the sch conditions
	 */
	public String[] getSchConditions() {
		return schConditions;
	}


	/**
	 * Gets the sch value.
	 *
	 * @return the sch value
	 */
	public String getSchValue() {
		return schValue;
	}

	/**
	 * Sets the sch value.
	 *
	 * @param schValue the new sch value
	 */
	public void setSchValue(String schValue) {
		this.schValue = schValue;
	}

	/**
	 * Gets the sort field.
	 *
	 * @return the sort field
	 */
	public String getSortField() {
		return sortField;
	}

	/**
	 * Sets the sort field.
	 *
	 * @param sortField the new sort field
	 */
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	/**
	 * Gets the sort order.
	 *
	 * @return the sort order
	 */
	public String getSortOrder() {
		return sortOrder;
	}

	/**
	 * Sets the sort order.
	 *
	 * @param sortOrder the new sort order
	 */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * Gets the sch query string.
	 *
	 * @return the sch query string
	 */
	public String getSchQueryString() {
		return schQueryString;
	}

	/**
	 * Sets the sch query string.
	 *
	 * @param schQueryString the new sch query string
	 */
	public void setSchQueryString(String schQueryString) {
		this.schQueryString = schQueryString;
	}

	/**
	 * Gets the hot yn.
	 *
	 * @return the hot yn
	 */
	public String getHotYn() {
		return hotYn;
	}

	/**
	 * Sets the hot yn.
	 *
	 * @param hotYn the new hot yn
	 */
	public void setHotYn(String hotYn) {
		this.hotYn = hotYn;
	}


	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getUpdId() {
		return updId;
	}

	public void setUpdId(String updId) {
		this.updId = updId;
	}

	public String getUpdDt() {
		return updDt;
	}

	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}

	/**
	 * Gets the 등록일.
	 *
	 * @return the 등록일
	 */
	public String getCreatedDate() {
//		if(this.createdDate == null){
//			Date now = new Date();
//			SimpleDateFormat sdf = new SimpleDateFormat(CoConstDef.DATABASE_FORMAT_DATE_ALL);
//			this.createdDate = sdf.format(now);
//		}
		return this.regDt;
	}

	/**
	 * Sets the 등록일.
	 *
	 * @param createdDate the new created date
	 */
	public void setCreatedDate(String createdDate) {
		if(!StringUtil.isEmpty(createdDate)) {
			createdDate = CoCommonFunc.formatDateSimple(createdDate);
		}
		this.regDt = createdDate;
	}

	/**
	 * Sets the 등록일 as curDate.
	 */
	public void setCreatedDateCurrentTime(){
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(CoConstDef.DATABASE_FORMAT_DATE_ALL);
		this.regDt = sdf.format(now);
	}

	/**
	 * Sets the 수정일시 as curdate.
	 */
	public void setModDtCunnrentTime() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(CoConstDef.DATABASE_FORMAT_DATE_ALL);
		this.updDt = sdf.format(now);
	}



	/**
	 * Gets the del yn.
	 *
	 * @return the del yn
	 */
	public String getDelYn() {
		return delYn;
	}

	/**
	 * Sets the del yn.
	 *
	 * @param delYn the new del yn
	 */
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	/**
	 * Gets the date condition.
	 *
	 * @return the date condition
	 */
	public String getDateCondition() {
		return dateCondition;
	}

	/**
	 * Sets the date condition.
	 *
	 * @param dateCondition the new date condition
	 */
	public void setDateCondition(String dateCondition) {
		this.dateCondition = dateCondition;
	}

	/**
	 * Gets the start date.
	 *
	 * @return the start date
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date.
	 *
	 * @param startDate the new start date
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * Gets the end date.
	 *
	 * @return the end date
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * Sets the end date.
	 *
	 * @param endDate the new end date
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



	/**
	 * Gets the no.
	 *
	 * @param the sch type
	 */
	public int getSchType() {
		return schType;
	}

	/**
	 * Sets the no.
	 *
	 * @param sch type the new sch type
	 */
	public void setSchType(int schType) {
		this.schType = schType;
	}

	/**
	 * Gets the bit array sum.
	 *
	 * @param value
	 *            the value
	 * @return the bit array sum
	 */
	protected static String getBitArraySum(String value){
		int sum = 0;

		if(value != null){
			String arr[] = value.split(",");
			if(arr.length > 1){

				for (String s : arr) {
					try{
						sum += Integer.parseInt(s.trim());
					}catch(Exception e){
						return "";
					}
				}
			}else{
				sum = Integer.parseInt(value);
			}
		}
		return sum+"";
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	// 개발 완료 후 삭제할 코드
	@Override
	public String toString() {
		return "ComBean [curPage=" + curPage + ", pageListSize=" + pageListSize
				+ ", blockSize=" + blockSize + ", totListSize=" + totListSize
				+ ", totBlockSize=" + totBlockSize + ", startIndex="
				+ startIndex + ", schCondition=" + schCondition + ", schValue="
				+ schValue + ", sortField=" + sortField + ", sortOrder="
				+ sortOrder + ", schQueryString=" + schQueryString + ", hotYn="
				+ hotYn + "]";
	}

	/**
	 * Sets the login user name.<br />
	 * 특별한 경우만 사용할 것
	 *
	 * @param loginUserName the new login user name
	 */
	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}


}