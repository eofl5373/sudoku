package com.bnk.plus.commons;

import java.io.IOException;
import java.io.Writer;

import javax.annotation.Resource;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.bnk.plus.commons.components.CoTopComponent;
import com.bnk.plus.commons.util.StringUtil;
import com.bnk.plus.config.AppConstBean;

@Component
@PropertySources(value = {@PropertySource(value=AppConstBean.APP_CONFIG_PROPERTIES_PATH)})
public class BnkTagLibraries extends CoTopComponent {

	/** The env. */
	protected static Environment appEnv;

	/**
	 * Sets the environment.
	 *
	 * @param env the new environment
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Resource
	@SuppressWarnings("static-access")
	public void setEnvironment(Environment env) throws IOException {
		this.appEnv = env;
	}

	/**
	 * 태그생성
	 * @author hk-lee
	 */
	public static class TagGenerator extends SimpleTagSupport {
		private String dataSeq;			//데이터 고유번호
		private String registTxt;		//input태그가 등록용일 경우 value 값(데이터 고유번호 미존재)
		private String modifyTxt;		//input태그가 수정용일 경우 value 값(데이터 고유번호 존재)

		private boolean hasBody=false;	//태그 innerHTML 사용 여부 - 기본값 false
		private String tagType;			//태그 타입 (a, input, span, div 등)
		private String attrId;			//태그 ID Attribute
		private String attrClass;		//태그 Class Attribute
		private String attrType;		//태그 Type Attribute
		private String attrName;		//태그 Name Attribute
		private String attrHref;		//태그 Href Attribute
		private String innerTxt;		//태그 innerHTML
		private String onClick;			//Click 바인딩 메서드
		private String onChange;		//Change 바인딩 메서드
		private String onFocus;			//Focus 바인딩 메서드
		private String onBlur;			//Blur 바인딩 메서드

		private Writer getWriter() {
			JspWriter out = getJspContext().getOut();
			return out;
		}

		@Override
		public void doTag() throws JspException, IOException {
			Writer out = getWriter();
			StringBuffer sb = new StringBuffer();

			sb.append("<")
			.append(tagType)
			.append(StringUtil.isNotEmpty(attrId)	?" id=\""		+	attrId		+"\""	:	"")
			.append(StringUtil.isNotEmpty(attrClass)?" class=\""	+	attrClass	+"\""	:	"")
			.append(StringUtil.isNotEmpty(attrType)	?" type=\""		+	attrType	+"\""	:	"")
			.append(StringUtil.isNotEmpty(attrName)	?" name=\""		+	attrName	+"\""	:	"")
			.append(StringUtil.isNotEmpty(attrHref)	?" href=\""		+	attrHref	+"\""	:	"")
			.append(StringUtil.isNotEmpty(onClick)	?" onclick=\""	+	onClick		+"\""	:	"")
			.append(StringUtil.isNotEmpty(onChange)	?" onchange=\""	+	onChange	+"\""	:	"")
			.append(StringUtil.isNotEmpty(onFocus)	?" onfocus=\""	+	onFocus		+"\""	:	"")
			.append(StringUtil.isNotEmpty(onBlur)	?" onblur=\""	+	onBlur		+"\""	:	"");

			if("input".equals(tagType)){
				if(StringUtil.isEmpty(dataSeq)){
					sb.append(" value="+registTxt);
				}else{
					sb.append(" value=\""+modifyTxt+"\"");
				}
			}else{
				if(StringUtil.isEmpty(dataSeq) && StringUtil.isEmpty(innerTxt)){
					innerTxt = registTxt;
				}else{
					innerTxt = modifyTxt;
				}
			}

			if(hasBody){
				sb.append(">").append(innerTxt).append("</").append(tagType).append(">");
			}else{
				sb.append(" />");
			}

			out.write(new String(sb));
		}

		public String getDataSeq() {
			return dataSeq;
		}

		public void setDataSeq(String dataSeq) {
			this.dataSeq = dataSeq;
		}

		public String getRegistTxt() {
			return registTxt;
		}

		public void setRegistTxt(String registTxt) {
			this.registTxt = registTxt;
		}

		public String getModifyTxt() {
			return modifyTxt;
		}

		public void setModifyTxt(String modifyTxt) {
			this.modifyTxt = modifyTxt;
		}

		public boolean isHasBody() {
			return hasBody;
		}

		public void setHasBody(boolean hasBody) {
			this.hasBody = hasBody;
		}

		public String getTagType() {
			return tagType;
		}

		public void setTagType(String tagType) {
			this.tagType = tagType;
		}

		public String getAttrId() {
			return attrId;
		}

		public void setAttrId(String attrId) {
			this.attrId = attrId;
		}

		public String getAttrClass() {
			return attrClass;
		}

		public void setAttrClass(String attrClass) {
			this.attrClass = attrClass;
		}

		public String getAttrType() {
			return attrType;
		}

		public void setAttrType(String attrType) {
			this.attrType = attrType;
		}

		public String getAttrName() {
			return attrName;
		}

		public void setAttrName(String attrName) {
			this.attrName = attrName;
		}

		public String getAttrHref() {
			return attrHref;
		}

		public void setAttrHref(String attrHref) {
			this.attrHref = attrHref;
		}

		public String getInnerTxt() {
			return innerTxt;
		}

		public void setInnerTxt(String innerTxt) {
			this.innerTxt = innerTxt;
		}

		public String getOnClick() {
			return onClick;
		}

		public void setOnClick(String onClick) {
			this.onClick = onClick;
		}

		public String getOnChange() {
			return onChange;
		}

		public void setOnChange(String onChange) {
			this.onChange = onChange;
		}

		public String getOnFocus() {
			return onFocus;
		}

		public void setOnFocus(String onFocus) {
			this.onFocus = onFocus;
		}

		public String getOnBlur() {
			return onBlur;
		}

		public void setOnBlur(String onBlur) {
			this.onBlur = onBlur;
		}
	}


}
