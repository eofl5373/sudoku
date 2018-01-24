package com.bnk.plus.service.notice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnk.plus.entity.Board;
import com.bnk.plus.service.notice.persistence.NoticeMapper;
import com.bnk.plus.service.notice.service.NoticeService;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService{
	
	@Autowired NoticeMapper noticeMapper;	
	
	@Override
	public List<Board> getNoticeList(Board board) {
		return noticeMapper.getNoticeList(board);
	}
}
