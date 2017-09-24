package hue.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hue.domain.Question;
import hue.domain.QuestionRepository;
import hue.domain.User;

@Controller
public class QuestionController {
	
	@Autowired
	QuestionRepository questionRepository;
	
	@PostMapping("/questions/remove/{qId}")
	public String removeQuestion(@PathVariable long qId, HttpSession session) {
		Question tempQuestion = questionRepository.findOne(qId);
		Object tempUser = session.getAttribute("loginedUser");
		if(tempUser == null) {
			return "redirect:/";
		}
		User loginUser = (User)tempUser;
		
		if(tempQuestion.getWriter() != loginUser.getName()) {
			return "redirect:/";
		}
		System.out.println("up");
		questionRepository.delete(qId);
		System.out.println("down");
		return "redirect:/";
	}
	
	@PostMapping("/question/modify/{qId}")
	public String modifyQuestion(@PathVariable long qId, Model model, HttpSession session, Question question) {
		Question tempQuestion = questionRepository.findOne(qId);
		Object tempUser = session.getAttribute("loginedUser");
		if(tempUser == null) {
			return "redirect:/";
		}
		User loginUser = (User)tempUser;
		
		if(tempQuestion.getWriter() != loginUser.getName()) {
			return "redirect:/";
		}
		
		tempQuestion.update(question);
		questionRepository.save(tempQuestion);
		model.addAttribute("question", question);
		
		return "redirect:/questions/" + qId ;
	}
	
	@GetMapping("/questions/{qId}/modify")
	public String modifyForm(@PathVariable long qId, Model model, HttpSession session) {
		Question question = questionRepository.findOne(qId);
		Object tempUser = session.getAttribute("loginedUser");
		if(tempUser == null) {
			return "redirect:/";
		}
		User loginUser = (User)tempUser;
		
		if(question.getWriter() != loginUser.getName()) {
			return "redirect:/";
		}
		
		model.addAttribute("question", question);
		return "qna/modify";
	}
	
	@PostMapping("/questions/add/{uid}")
	public String addQuestion(Question question, @PathVariable long uid) {
		questionRepository.save(question);
		return "redirect:/";
	}
	
	@GetMapping("/questions/form")
	public String QuestionFrom() {
		return "qna/form";
	}
	
	@GetMapping("/")
	public String showHome(Model model) {
		model.addAttribute("quesitons", questionRepository.findAll());
		return "index";
	}
	
	@GetMapping("/index")
	public String showIndex(Model model) {
		model.addAttribute("quesitons", questionRepository.findAll());
		return "index";
	}
	
	@GetMapping("/questions/{id}")
	public String showQuestion(Model model, @PathVariable long id) {
		model.addAttribute("question", questionRepository.findOne(id));
		return "qna/show";
	}
	

}
