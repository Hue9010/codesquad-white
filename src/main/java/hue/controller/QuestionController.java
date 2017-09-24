package hue.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hue.domain.Question;
import hue.domain.QuestionRepository;

@Controller
public class QuestionController {
	
	@Autowired
	QuestionRepository questionRepository;
	
	@PostMapping("/question/add")
	public String addQuestion(Question question) {
		questionRepository.save(question);
//		questions.add(question);
		System.out.println("here");
		return "redirect:/";
	}
	
	@GetMapping("/")
	public String showHome(Model model) {
		model.addAttribute("quesitons", questionRepository.findAll());
//		model.addAttribute("questions", questions);
		return "index";
	}
	
	@GetMapping("/index")
	public String showIndex(Model model) {
		model.addAttribute("quesitons", questionRepository.findAll());
//		model.addAttribute("questions", questions);
		return "index";
	}
	
	@GetMapping("/questions/{id}")
	public String showQuestion(Model model, @PathVariable long id) {
//		model.addAttribute("question", questions.get(index));
		model.addAttribute("question", questionRepository.findOne(id));
		return "qna/show";
	}
	

}
