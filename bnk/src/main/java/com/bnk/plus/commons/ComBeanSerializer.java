package com.bnk.plus.commons;

import java.lang.reflect.Type;

import com.bnk.plus.commons.components.bean.ComBean;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ComBeanSerializer implements JsonSerializer<ComBean> {

	@Override
	public JsonElement serialize(ComBean vo, Type typeOfVo, JsonSerializationContext context) {
		return context.serialize((Object) vo);
	}

}
