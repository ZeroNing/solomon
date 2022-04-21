package com.steven.solomon.base.controller;

import com.steven.solomon.base.model.BaseResponseVO;
import com.steven.solomon.json.JackJsonUtils;
import java.io.IOException;

public class BaseController {

	protected final String PAGE_INFO = "pageInfo";

	protected String responseSuccessJson() throws IOException {
		return this.responseSuccessJsonByFilter(null, null);
	}

	protected String responseSuccessJson(Object data) throws IOException {
		return this.responseSuccessJsonByFilter(data, null);
	}

	protected String responseSuccessJsonByFilter(Object data, Class<?> filter) throws IOException {
		return JackJsonUtils.formatJsonByFilter(new BaseResponseVO(data), filter);
	}
}
