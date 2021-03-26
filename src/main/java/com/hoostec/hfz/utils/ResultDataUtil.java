package com.hoostec.hfz.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResultDataUtil {
	private int code;
	private String msg = "SUCCESS!";
	private Long count;
	private Object data;

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getCount() {
		return count;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public static ResultDataUtil isOk(Object data) {
		ResultDataUtil dataUtil = new ResultDataUtil();
		dataUtil.setCode(200);
		dataUtil.setMsg("SUCCESS！");
		dataUtil.setData(data);
		return dataUtil;
	}

	public static void isOkResponse(Object data, HttpServletResponse response) {
		ResultDataUtil dataUtil = new ResultDataUtil();
		dataUtil.setCode(200);
		dataUtil.setMsg("SUCCESS！");
		dataUtil.setData(data);
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().print(new Gson().toJson(dataUtil));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void isErrorResponse(int code, String msg, HttpServletResponse response) {
		ResultDataUtil dataUtil = new ResultDataUtil();
		dataUtil.setCode(code);
		dataUtil.setMsg(msg);
		dataUtil.setData(null);
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().print(new Gson().toJson(dataUtil));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static ResultDataUtil isError(int code, String msg) {
		ResultDataUtil dataUtil = new ResultDataUtil();
		dataUtil.setCode(code);
		dataUtil.setMsg(msg);
		dataUtil.setData(null);
		return dataUtil;
	}

	public static ResultDataUtil isOkJsonList(Long count, Object data) {
		ResultDataUtil dataUtil = new ResultDataUtil();
		dataUtil.setCode(200);
		dataUtil.setMsg("SUCCESS！");
		dataUtil.setCount(count);
		dataUtil.setData(data);
		return dataUtil;
	}

}