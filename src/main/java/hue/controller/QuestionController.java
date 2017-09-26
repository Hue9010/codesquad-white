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
	private QuestionRepository questionRepository;
	
	@PostMapping("/questions/remove/{qId}")
	public String removeQuestion(@PathVariable long qId, HttpSession session) {
		Question tempQuestion = questionRepository.findOne(qId);
		Object tempUser = session.getAttribute("loginedUser");
		if(tempUser == null) {
			return "redirect:/";
		}
		User loginUser = (User)tempUser;
		
//		if(!tempQuestion.getWriter().equals(loginUser)) {
//			return "redirect:/";
//		}
		if(!tempQuestion.isSameUser(loginUser)) {
			return "redirect:/";
		}
		questionRepository.delete(qId);
		return "redirect:/";
	}
	
	@PostMapping("/question/modify/{qId}")
	public String modifyQuestion(@PathVariable long qId, Model model, HttpSession session, String title, String contents) {
		Question tempQuestion = questionRepository.findOne(qId);
		Object tempUser = session.getAttribute("loginedUser");
		if(tempUser == null) {
			return "redirect:/";
		}
		User loginUser = (User)tempUser;
		
		if(!tempQuestion.isSameUser(loginUser)) {
			return "redirect:/";
		}
		
		tempQuestion.update(title, contents);
		questionRepository.save(tempQuestion);
		
		return "redirect:/questions/" + qId ;
	}
	
	@GetMapping("/questions/{qId}/modify")
	public String modifyForm(@PathVariable long qId, Model model, HttpSession session) {
		Question tempQuestion = questionRepository.findOne(qId);
		Object tempUser = session.getAttribute("loginedUser");
		if(tempUser == null) {
			return "redirect:/";
		}
		User loginUser = (User)tempUser;
		
		if(!tempQuestion.getWriter().equals(loginUser)) {
			return "redirect:/";
		}
		
		model.addAttribute("question", tempQuestion);
		return "qna/modify";
	}
	
	@PostMapping("/questions/add")
	public String addQuestion(String title,String contents, HttpSession session) {
		Object tempUser = session.getAttribute("loginedUser");
		if(tempUser == null) {
			return "redirect:/";
		}
		User loginUser = (User)tempUser;
		
		Question question = new Question(loginUser, title, contents); 
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
