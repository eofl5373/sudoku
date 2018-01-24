package com.bnk.plus.web.project.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bnk.plus.commons.components.CoTopComponent;
import com.bnk.plus.entity.Board;
import com.bnk.plus.service.notice.service.NoticeService;

@Controller
@RequestMapping(value="/project/notice")

public class NoticeController extends CoTopComponent{
	
	private final String tilesPrefix = "tiles.project.notice.";
	
	@Autowired NoticeService noticeService;
	
	@RequestMapping(value="/goNotice")
	public String goNotice(Board board){
		
		List<Board> boardList = noticeService.getNoticeList(board);
		System.out.println(toJson(boardList));
		
		return tilesPrefix+"notice";
	}

}
