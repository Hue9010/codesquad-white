package hue.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hue.domain.Answer;
import hue.domain.AnswerRepository;
import hue.domain.QuestionRepository;
import hue.domain.User;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@PostMapping("/add")
	public String create(HttpSession session, @PathVariable Long questionId, String contents) {
		Object tempUser = session.getAttribute("loginedUser");
		if (tempUser == null) {
			return "redirect:/";
		}
		User loginUser = (User) tempUser;
		System.out.println("save");
		answerRepository.save(new Answer(loginUser, questionRepository.findOne(questionId), contents));
		return String.format("redirect:/questions/%d", questionId);
	}
}
