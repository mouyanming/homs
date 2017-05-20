package jp.co.hyron.ope.controller;

import java.sql.Date;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.hyron.ope.entity.Bbs;
import jp.co.hyron.ope.repository.BbsRepository;

@Controller
@RequestMapping("/bbs")
public class BbsController {
	
	@Autowired
	private BbsRepository bbsRepository;
	
	@RequestMapping(value = {"/index"})
	public ModelAndView index(final Model model){
		Calendar now = Calendar.getInstance();
		
		Bbs b = new Bbs();
		b.setTitle("first");
		b.setUpdDt(new Date(now.getTimeInMillis()));
		
		bbsRepository.saveAndFlush(b);
		
		Bbs c = bbsRepository.findTitleByTitle("first");
		bbsRepository.delete(c);
		model.addAttribute("result", c);
		
		
		return new ModelAndView("bbs/index");
	}

}
