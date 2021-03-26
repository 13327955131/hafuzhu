package com.hoostec.hfz.utils;

import com.hoostec.hfz.dto.ImgCodeDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

/**
 * 图片验证码
 *
 * @Date: 20180619
 * @author: loo
 * @version: 1.00
 */
@RestController
@RequestMapping("common")
public class ImgCodeUtils {
	/**
	 * 生成验证码
	 *
	 * @param request
	 * @throws IOException
	 */

	@RequestMapping(value = "captcha", method = RequestMethod.POST)
	public ResultDataUtil codeGetPwd(HttpServletRequest request) throws IOException {
		int width = 94;// 定义图片的width
		int height = 40;// 定义图片的height
		int codeCount = 4;// 定义图片上显示验证码的个数
		int xx = 16;
		int fontHeight = 30;
		int codeY = 32;
		char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q',
				'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		// 定义图像buffer
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics gd = buffImg.getGraphics();
		// 创建一个随机数生成器类
		Random random = new Random();
		// 将图像填充为白色
		gd.setColor(Color.WHITE);
		gd.fillRect(0, 0, width, height);

		// 创建字体，字体的大小应该根据图片的高度来定。
		Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
		// 设置字体。
		gd.setFont(font);

		// 画边框。
		gd.setColor(Color.WHITE);
		gd.drawRect(0, 0, width - 1, height - 1);

		// 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
		gd.setColor(new Color(0, 132, 200));
		for (int i = 0; i < 40; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			gd.drawLine(x, y, x + xl, y + yl);
		}

		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
		StringBuffer randomCode = new StringBuffer();
		int red = 0, green = 0, blue = 0;

		// 随机产生codeCount数字的验证码。
		for (int i = 0; i < codeCount; i++) {
			// 得到随机产生的验证码数字。
			String code = String.valueOf(codeSequence[random.nextInt(34)]);
			// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
			red = 0;
			green = 132;
			blue = 255;

			// 用随机产生的颜色将验证码绘制到图像中。
			gd.setColor(new Color(red, green, blue));
			gd.drawString(code, (i + 1) * xx, codeY);

			// 将产生的四个随机数组合在一起。
			randomCode.append(code);
		}
		// 将四位数字的验证码保存到Session中。
		HttpSession session = request.getSession();
		session.setAttribute("codeGetPwd", randomCode.toString().toUpperCase());
		// 将图像输出到Servlet输出流中。
		ByteArrayOutputStream sos = new ByteArrayOutputStream();
		// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(sos);
		// encoder.encode(buffImg);
		ImageIO.write(buffImg, "jpeg", sos);
		Base64.Encoder base64 = Base64.getEncoder();
		sos.close();
		ImgCodeDto imgCodeDto = new ImgCodeDto();
		// 转换图片Base64
		imgCodeDto.setImgSrc("data:images/jpeg;base64," + base64.encodeToString(sos.toByteArray()));
		imgCodeDto.setVerCode(randomCode.toString());
		return ResultDataUtil.isOk(imgCodeDto);
	}

}
