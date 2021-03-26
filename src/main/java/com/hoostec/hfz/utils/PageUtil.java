package com.hoostec.hfz.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class PageUtil {
	/**
	 * 当前页
	 */
	@JsonIgnore
	private int currentPage = 1;
	/**
	 * 每页数量
	 */
	@JsonIgnore
	private int pageSize = 15;

}
