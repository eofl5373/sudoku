package com.bnk.plus.service.notice.service;

import java.util.List;

import com.bnk.plus.entity.Board;

public interface NoticeService {
	List<Board> getNoticeList(Board board);			//공지사항 목록 가져오기
}
