package com.bnk.plus.entity;

import com.bnk.plus.commons.components.bean.ComBean;

public class Board extends ComBean{
	
	private static final long serialVersionUID = 6487483857224430941L;
	
	private String seq;
	private String title;
	private String contents;
	
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	
}
