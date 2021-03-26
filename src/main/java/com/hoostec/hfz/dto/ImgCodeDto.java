package com.hoostec.hfz.dto;

import lombok.Data;

@Data
public class ImgCodeDto {
	/**
	 * 图片base64
	 */
	private String imgSrc;
	/**
	 * 验证码
	 */
	private String verCode;
}
