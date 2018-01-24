package com.bnk.plus.web.project.freenotice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/project/freenotice")

public class FreeNoticeController {
	
	private final String tilesPrefix = "tiles.project.freenotice.";
	
	@RequestMapping(value="/goFreeNotice")
	public String goFreeNotice(){
		return tilesPrefix+"freenotice";
	}

}
