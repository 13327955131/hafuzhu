package com.hoostec.hfz.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传
 *
 * @Date: 20180619
 * @author: loo
 * @version: 1.00
 */
@RestController
@RequestMapping("common")
public class FileUploadUtils {
	@Value("${files.path}")
	private String filesPath;

	@RequestMapping("fileUploads")
	public ResultDataUtil upload(MultipartFile file) {
		String fileOrigName = file.getOriginalFilename();
		if (!fileOrigName.contains(".")) {
			throw new IllegalArgumentException("缺少后缀名");
		}
		String title = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
		fileOrigName = fileOrigName.substring(fileOrigName.lastIndexOf("."));
		String pathname = new Date().getTime()+DataUtils.getRandom(4) + fileOrigName;
		String returnPath = "/" + LocalDate.now().toString().replace("-", "/") + "/" + pathname;
		String fullPath = filesPath + returnPath;
		Map<String, String> result = new HashMap<String, String>();
		String path = FileUtil.saveFile(file, fullPath);
		if (path != null) {
			result.put("src", "/statics" + returnPath);
			result.put("title", title);
		} else {
			// TODO Exception
		}
		return ResultDataUtil.isOk(result);
	}

}
