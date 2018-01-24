package com.bnk.plus.config.viewresolver;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.bnk.plus.commons.util.DateUtil;
import com.bnk.plus.commons.util.StringUtil;

/**
 * <pre>
 * ExcelDownloadView.java
 * 
 * 사용 예)
 * 1. "/[package...]/web/[name]/views/"에 이 클래스를 상속받는 ExcelView 클래스를 생성합니다.
 * 2. "createExcel(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response)"를 구현하여 Excel파일을 작성합니다.
 * 3. controller에서 아래와 같은 형식으로 생성한 ExcelView를 호출하고, model에는 ExcelView에서 필요한 데이터를 추가하여 사용합니다.
 *    @RequestMapping(value = "/get-excel", method = RequestMethod.GET)
 *    public ExcelDownloadView get(Model model) {
 *       model.addAttribute("fileName", "123.xls");
 *       model.addAttribute("menuList", new ArrayList<String>(){{add("가");add("나");add("다");add("라");add("마");}});
 *       return new ExcelDownloadView();
 *    }
 * 파라미터
 *   > 파일명 (선택-fileName) : 파일명 없을 경우 현재날짜 "년월일시분초"로 설정되고 파일명이 있을 경우 파일명 뒤에 마찬가지로 날짜가 포함됨 [확장자:xls, xlsx]
 *   > 기타 : 각 타입에 필요한 데이터는 model에 담아 직접 컨트롤
 * </pre>
 *
 * @author ks-choi
 * @date 2015. 8. 21.
 */
public abstract class ExcelDownloadView extends AbstractXlsView {
	
	private static final String FILE_EXTENTION_TYPE_A = ".xls";
	private static final String FILE_EXTENTION_TYPE_B = ".xlsx";
	
	@Override
	protected void buildExcelDocument(
			Map<String, Object> model,
			Workbook workbook,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String userAgent = request.getHeader("User-Agent");
		String fileName = (String)StringUtil.nvl(model.get("fileName"), "");// "test.xls";
		// 확장자 검사 및 날짜 출력
		if (fileName.indexOf(FILE_EXTENTION_TYPE_A) == -1 && fileName.indexOf(FILE_EXTENTION_TYPE_B) == -1){
			fileName += (" "+DateUtil.getCurrentDateAsString());
			fileName += FILE_EXTENTION_TYPE_A;
		} else {
			fileName = fileName.substring(0, fileName.lastIndexOf(".")) + (" "+DateUtil.getCurrentDateAsString()) + fileName.substring(fileName.lastIndexOf("."));
		}
		
		// 파일명 인코딩
		if(userAgent.indexOf("MSIE") > -1){
			fileName = URLEncoder.encode(fileName, "utf-8");
		}else{
			fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
		}
		
		// 해더 작성
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
//		// 예) buildExcelDocument() 에서 구현시 문서 생성
//		org.apache.poi.ss.usermodel.Sheet sheet = createFirstSheet(workbook);
//		createColumnLabel(sheet);
//		
//		List<String> menuList = (List<String>)model.get("menuList");
//		for(int i=0; i <= menuList.size()-1; i++){
//			createPageRow(sheet, menuList, i);
//		}
		
		createExcel(model, workbook, request, response);
	}
	
	/**
	 * <pre>
	 * 1. 설명 : 상속받아서 구현해야 할 메소드
	 * 2. 동작 : 오버라이딩된 buildExcelDocument()에서 이름을 셋팅하고 이 함수를 호출해줍니다.
	 * 3. Input : 
	 * 4. Output : 
	 * 5. 수정내역
	 * ----------------------------------------------------------------
	 * 변경일             작성자                   변경내용
	 * ----------------------------------------------------------------
	 * 2016. 4. 15.     ks-choi          최초작성
	 * ----------------------------------------------------------------
	 * </pre>
	 *
	 * @param model
	 * @param workbook
	 * @param request
	 * @param response
	 */
	public abstract void createExcel(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response);
	
	/*
	// 예) 시트를 생성합니다.
	private org.apache.poi.ss.usermodel.Sheet createFirstSheet(Workbook workbook){
		org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet();
		workbook.setSheetName(0, "테스트");
		sheet.setColumnWidth(1, 256*30);
		return sheet;
	}
	*/
	
	/*
	// 예) 컬럼 라벨을 만듭니다. (=Header)
	private void createColumnLabel(org.apache.poi.ss.usermodel.Sheet sheet){
		org.apache.poi.ss.usermodel.Row firstRow = sheet.createRow(0);
		
		org.apache.poi.ss.usermodel.Cell cell = firstRow.createCell(0);
		cell.setCellValue("순위");
		cell = firstRow.createCell(1);
		cell.setCellValue("페이지");
	}
	*/
	
	/*
	// 예) 데이터를 체웁니다. (=Row)
	private void createPageRow(org.apache.poi.ss.usermodel.Sheet sheet, List<String> menuList, int rowNum){
		org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum + 1);
		
		org.apache.poi.ss.usermodel.Cell cell = row.createCell(0);
		cell.setCellValue(rowNum + 1);
		
		cell = row.createCell(1);
		cell.setCellValue(menuList.get(rowNum)); 
	}
	*/
}