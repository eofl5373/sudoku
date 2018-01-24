package com.bnk.plus.web.product;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bnk.plus.commons.components.CoTopComponent;

/**
 * Controller
 * 컨트롤러 템플릿
 *
 * @author hk-lee
 */
@Controller
@RequestMapping(value = {"/product/test"})
public class TemplateController extends CoTopComponent{
	private final String tilesPrefix = "tiles.product.test.";

	//페이지 이동
	@RequestMapping(value={"/index"}, method = RequestMethod.GET, produces = "text/html; charset=utf-8")
	public String pageMove(HttpServletRequest req, HttpServletResponse res, Model model){
		mlog_usual.debug(" :: Load Page.");
		return tilesPrefix+"index";
	}

	//페이지 이동 - PathVariable
	@RequestMapping(value={"/detail/{detailNo}"}, method = RequestMethod.GET, produces = "text/html; charset=utf-8")
	public String pageMoveByKey(@PathVariable String detailNo, HttpServletRequest req, HttpServletResponse res, Model model){
		mlog_usual.debug(" :: Load Page.");
		return tilesPrefix+"detail";
	}

	//AJAX GET
	@RequestMapping(value={"/testGET/ajax"}, method = RequestMethod.GET, produces = "text/html; charset=utf-8")
	public @ResponseBody ResponseEntity<Object> templateAjaxGet(@RequestBody Map<String, Object> params, HttpServletRequest req, HttpServletResponse res, Model model){
		mlog_usual.debug(" :: Load AJAX GET.");
		Map<String, Object> resMap = new HashMap<>();
		String resCd = "00";
		try{
			//TODO:
		}catch (Exception e) {
			resCd = "99";
		}
		resMap.put("resCd", resCd);
		return makeJsonResponseHeader(resMap);
	}

	//AJAX POST
	@RequestMapping(value={"/testPOST/ajax"}, method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	public @ResponseBody ResponseEntity<Object> templateAjaxPost(@RequestBody Map<String, Object> params, HttpServletRequest req, HttpServletResponse res, Model model){
		mlog_usual.debug(" :: Load AJAX POST.");
		Map<String, Object> resMap = new HashMap<>();
		String resCd = "00";
		try{
			//TODO:
		}catch (Exception e) {
			resCd = "99";
		}

		resMap.put("resCd", resCd);
		return makeJsonResponseHeader(resMap);
	}

	//POPUP TEMPLATE AJAX GET
	@RequestMapping(value={"/testPOPUP/ajax"}, method = RequestMethod.GET, produces = "text/html; charset=utf-8")
	public String popupTemplateAjaxGet(@RequestBody Map<String, Object> params, HttpServletRequest req, HttpServletResponse res, Model model){
		mlog_usual.debug(" :: Load Popup Template AJAX GET.");
		return "tiles.popup.template.test.bnk_template";
	}

}
