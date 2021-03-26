package com.hoostec.hfz.config.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PageConfig {

	@RequestMapping("/back")
	public RedirectView backIndex() {
		return new RedirectView("/back/index.html#/");
	}
}
