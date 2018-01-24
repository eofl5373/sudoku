package com.bnk.plus.service.notice.persistence;

import java.util.List;

import com.bnk.plus.entity.Board;

public interface NoticeMapper {
	List<Board> getNoticeList(Board board);
}
