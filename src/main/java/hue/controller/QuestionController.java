package hue.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hue.domain.Question;

@Controller
public class QuestionController {
	List<Question> questions = new ArrayList<Question>();
	
	@PostMapping("/question")
	public String addQuestion(Question question, Model model) {
		questions.add(question);
		return "redirect:/";
	}
	
	@GetMapping("/")
	public String showHome(Model model) {
		model.addAttribute("questions", questions);
		return "index";
	}
	
	@GetMapping("/index")
	public String showIndex(Model model) {
		model.addAttribute("questions", questions);
		return "index";
	}
	
	@GetMapping("/questions/{index}")
	public String showQuestion(Model model, @PathVariable int index) {
		model.addAttribute("question", questions.get(index));
		return "qna/show";
	}
	

}
