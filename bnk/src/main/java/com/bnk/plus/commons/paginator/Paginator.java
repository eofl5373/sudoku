package com.bnk.plus.commons.paginator;

import java.io.Writer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;


/**
 * @Class Name : PaginationRenderer.java
 * @Description : PaginationRenderer.java class
 *
 * @Modification Information
 * @
 * @ Modify Date    Modified by        Description
 * @ -------------    ------------    ---------------------------
 * @ 2017. 07. 27.    이훈경
 *
 *  @author 이훈경
 *  @since 2017. 07. 27.
 *  @version 1.0
 *
 */
public class Paginator extends SimpleTagSupport {

	// ******************************************************************
	// *                                           페이지 네비게이터 템플릿 (S)                                     *
	// ******************************************************************

	private String MainSB 		= 	"<div class='#$class'>";
    private String FistB 		= 		"<a data-page='#$page' class='#$class'>#$title</a>";
    private String PrevB 		= 		"<a data-page='#$page' class='#$class'>#$title</a>";
    private String CurB 		= 		"<strong class='#$class'>#$title</strong>";
    private String OthB 		= 		"<a data-page='#$page' class='#$class'>#$title</a>";
    private String NextB 		= 		"<a data-page='#$page' class='#$class'>#$title</a>";
    private String LastB 		= 		"<a data-page='#$page' class='#$class'>#$title</a>";
    private String MainEB 		= 	"</div>";
    // ******************************************************************
    // *                                           페이지 네비게이터 템플릿 (E)                                     *
    // ******************************************************************

	private String uri;							// 이동 URL [*페이지 번호는 ##을 Replace]		(설정은 JSTL 속성으로 입력)
    private int curPage;						// 현재 페이지											(설정은 JSTL 속성으로 입력)
    private int totPages;						// 전체 페이지											(설정은 JSTL 속성으로 입력)
    private int blockSize = 10;				// 블록 크기												(설정은 JSTL 속성으로 입력)
    private int skipSize = 10;					// 이전/다음 선택시 이동할 페이지 갯수				(설정은 JSTL 속성으로 입력)

    // Options
    private boolean goFirstUse = true;							// 처음 페이지 사용 여부		(설정은 JSTL 속성으로 입력)
    private boolean goLastUse = true;							// 마지막 페이지 사용 여부		(설정은 JSTL 속성으로 입력)

    // CSS Class
    private String paginatorBlockClass = "paginate";			// paginator 전체 메인 블록	(설정은 JSTL 속성으로 입력)
    private String goFirstClass = "first";					// 처음 페이지					(설정은 JSTL 속성으로 입력)
    private String prevPageClass = "prev";					// 이전 페이지					(설정은 JSTL 속성으로 입력)
    private String curPageClass = "";					// 현재 페이지					(설정은 JSTL 속성으로 입력)
    private String defaultPageClass = "";				// 다른 페이지					(설정은 JSTL 속성으로 입력)
    private String nextPageClass = "next";					// 다음 페이지 					(설정은 JSTL 속성으로 입력)
    private String goLastClass = "last";					// 마지막 페이지					(설정은 JSTL 속성으로 입력)

    // Label
    private String goFirstLabel = "처음";			// 처음 페이지 타이틀				(설정은 JSTL 속성으로 입력)
    private String prevPageLabel = "이전";			// 이전 페이지 타이틀				(설정은 JSTL 속성으로 입력)
    private String nextPageLabel = "다음";			// 다음 페이지 타이틀				(설정은 JSTL 속성으로 입력)
    private String goLastLabel = "마지막";			// 마지막 페이지 타이틀			(설정은 JSTL 속성으로 입력)

    private Writer getWriter() {
        JspWriter out = getJspContext().getOut();
        return out;
    }

    public Paginator(){}
    public Paginator(String uri, int curPage, int totPages){
    	this.uri		=	uri;
    	this.curPage	=	curPage;
    	this.totPages	=	totPages;
    }

    @Override
    public void doTag() throws JspException {
        Writer out = getWriter();

        boolean lastPage = curPage == totPages;
        int pgStart = Math.max(curPage - blockSize / 2, 1);
        int pgEnd = pgStart + blockSize;
        if (pgEnd > totPages + 1) {
            int diff = pgEnd - totPages;
            pgStart -= diff - 1;
            if (pgStart < 1)
                pgStart = 1;
            pgEnd = totPages + 1;
        }

        try {
            out.write(MainSB.replace("#$class", paginatorBlockClass));

            // 처음
            if (goFirstUse) out.write(FistB.replace("#$class", goFirstClass).replace("#$uri", uri.replace("##", "1")).replace("#$title", goFirstLabel));

            // 이전
            if (curPage > 1){
            	int moveSize = curPage-skipSize;
            	if (moveSize < 1) moveSize = 1;
            	out.write(PrevB.replace("#$class", prevPageClass).replace("#$uri", uri.replace("##", moveSize+"")).replace("#$title", prevPageLabel));
            }

            out.write("<span>");

            for (int i = pgStart; i < pgEnd; i++) {
            	// 현재 페이지
                if (i == curPage) out.write(CurB.replace("#$class", curPageClass).replace("#$title", curPage+""));
                // 기타 페이지
                else out.write(OthB.replace("#$class", defaultPageClass).replace("#$uri", uri.replace("##", i+"")).replace("#$title", i+""));
            }

            out.write("</span>");

            // 다음
            if (!lastPage){
            	int moveSize = curPage + skipSize;
            	if (moveSize > totPages) moveSize = totPages;
            	out.write(NextB.replace("#$class", nextPageClass).replace("#$uri", uri.replace("##", moveSize+"")).replace("#$title", nextPageLabel));
            }

            // 마지막
            if (goLastUse) out.write(LastB.replace("#$class", goLastClass).replace("#$uri", uri.replace("##", totPages+"")).replace("#$title", goLastLabel));

        	out.write(MainEB);

        } catch (java.io.IOException ex) {
            throw new JspException("Error in Paginator tag", ex);
        }
    }


    @Override
    public String toString() {
    	StringBuilder result = new StringBuilder();

    	 boolean lastPage = curPage == totPages;
         int pgStart = Math.max(curPage - blockSize / 2, 1);
         int pgEnd = pgStart + blockSize;
         if (pgEnd > totPages + 1) {
             int diff = pgEnd - totPages;
             pgStart -= diff - 1;
             if (pgStart < 1)
                 pgStart = 1;
             pgEnd = totPages + 1;
         }

         try {
        	 result.append(MainSB.replace("#$class", paginatorBlockClass));

             // 처음
             if (goFirstUse && curPage > pgStart) result.append(FistB.replace("#$class", goFirstClass).replace("#$uri", uri.replace("##", "1")).replace("#$title", goFirstLabel).replace("#$page", "1"));

             // 이전
             if (curPage > 1){
             	int moveSize = curPage-skipSize;
             	if (moveSize < 1) moveSize = 1;
             	result.append(PrevB.replace("#$class", prevPageClass).replace("#$uri", uri.replace("##", moveSize+"")).replace("#$title", prevPageLabel).replace("#$page", moveSize+""));
             }

             result.append("<span>");

             for (int i = pgStart; i < pgEnd; i++) {
             	// 현재 페이지
                 if (i == curPage) result.append(CurB.replace("#$class", curPageClass).replace("#$title", curPage+""));
                 // 기타 페이지
                 else result.append(OthB.replace("#$class", defaultPageClass).replace("#$uri", uri.replace("##", i+"")).replace("#$title", i+"").replace("#$page", i+""));
             }

             result.append("</span>");

             // 다음
             if (!lastPage){
             	int moveSize = curPage + skipSize;
             	if (moveSize > totPages) moveSize = totPages;
             	result.append(NextB.replace("#$class", nextPageClass).replace("#$uri", uri.replace("##", moveSize+"")).replace("#$title", nextPageLabel).replace("#$page", moveSize+""));
             }

             // 마지막
             if (goLastUse && curPage < pgEnd-1) result.append(LastB.replace("#$class", goLastClass).replace("#$uri", uri.replace("##", totPages+"")).replace("#$title", goLastLabel).replace("#$page", totPages+""));

         	result.append(MainEB);

         }catch(Exception e){
        	 e.printStackTrace();
         }

    	return new String(result);
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public void setCurPage(int currPage) {
        this.curPage = currPage;
    }

    public void setTotPages(int totalPages) {
        this.totPages = totalPages;
    }

    public void setSkipSize(int skipSize){
    	this.skipSize = skipSize;
    }

    public void setPaginatorBlockClass(String paginatorBlockClass) {
        this.paginatorBlockClass = paginatorBlockClass;
    }

    public void setPrevPageClass(String prevPageClass) {
        this.prevPageClass = prevPageClass;
    }
    public void setPrevPageLabel(String prevPageLabel) {
        this.prevPageLabel = prevPageLabel;
    }

    public void setNextPageClass(String nextPageClass) {
        this.nextPageClass = nextPageClass;
    }
    public void setNextPageLabel(String nextPageLabel) {
        this.nextPageLabel = nextPageLabel;
    }

    public void setCurPageClass(String curPageClass) {
        this.curPageClass = curPageClass;
    }

    public void setDefaultPageClass(String defaultPageClass) {
        this.defaultPageClass = defaultPageClass;
    }

    public void setGoFirstUse(boolean goFirstUse) {
        this.goFirstUse = goFirstUse;
    }
    public void setGoFirstClass(String goFirstClass) {
        this.goFirstClass = goFirstClass;
    }
    public void setGoFirstLabel(String goFirstLabel) {
        this.goFirstLabel = goFirstLabel;
    }

    public void setGoLastUse(boolean goLastUse) {
        this.goLastUse = goLastUse;
    }
    public void setGoLastClass(String goLastClass) {
        this.goLastClass = goLastClass;
    }
    public void setGoLastLabel(String goLastLabel) {
        this.goLastLabel = goLastLabel;
    }
}
