package com.hoostec.hfz.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.aliyun.oss.OSSClient;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 阿里云上传文件
 */
@RestController
@RequestMapping("common")
public class AliOSS {
	private static Logger logger = Logger.getLogger(AliOSS.class);

    // endpoint是访问OSS的域名。如果您已经在OSS的控制台上 创建了Bucket，请在控制台上查看域名。
    // 如果您还没有创建Bucket，endpoint选择请参看文档中心的“开发人员指南 > 基本概念 > 访问域名”，
    // 链接地址是：https://help.aliyun.com/document_detail/oss/user_guide/oss_concept/endpoint.html?spm=5176.docoss/user_guide/endpoint_region
    // endpoint的格式形如“http://oss-cn-hangzhou.aliyuncs.com/”，注意http://后不带bucket名称，
    // 比如“http://bucket-name.oss-cn-hangzhou.aliyuncs.com”，是错误的endpoint，请去掉其中的“bucket-name”。
    // String endpoint = "http://oss-cn-beijing.aliyuncs.com";

    private static String endpoint = "http://oss-cn-shanghai.aliyuncs.com";

    //请求访问域名
    private static final String domain = "https://hfz.oss-cn-shanghai.aliyuncs.com/";
    // accessKeyId和accessKeySecret是OSS的访问密钥，您可以在控制台上创建和查看，
    // 创建和查看访问密钥的链接地址是：https://ak-console.aliyun.com/#/。
    // 注意：accessKeyId和accessKeySecret前后都没有空格，从控制台复制时请检查并去除多余的空格。

    private static String accessKeyId = "LTAIa8fUGUHnrK7O";
    private static String accessKeySecret = "NBfFTgzbXr2E9zBhuiUEO09Ue3hYef";

    // Bucket用来管理所存储Object的存储空间，详细描述请参看“开发人员指南 > 基本概念 > OSS基本概念介绍”。
    // Bucket命名规范如下：只能包括小写字母，数字和短横线（-），必须以小写字母或者数字开头，长度必须在3-63字节之间。

    @RequestMapping("fileUpload")
    public static ResultDataUtil upload(MultipartFile file){
        String fileOrigName = file.getOriginalFilename();
        if (!fileOrigName.contains(".")) {
            throw new IllegalArgumentException("缺少后缀名");
        }
        String title = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
        //后缀
        fileOrigName = fileOrigName.substring(fileOrigName.lastIndexOf("."));
        logger.info("Started");
        Map<String, String> result = new HashMap<String, String>();
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
        	StringBuffer uploadName = new StringBuffer();
        	uploadName.append(DateUtils.getCurrentTimess()).append("/").append(UUID.randomUUID()).append(fileOrigName);

            // 上传文件流。
            ossClient.putObject("hfz", uploadName.toString(), file.getInputStream());
            result.put("title", title);
            result.put("src", domain+uploadName.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }

        return ResultDataUtil.isOk(result);
    }

}

