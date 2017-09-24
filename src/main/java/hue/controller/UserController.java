package hue.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hue.domain.User;
import hue.domain.UserRepository;

@Controller
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/user/create")
	public String addUser(User user) {
		userRepository.save(user);
		return "redirect:/users";
	}

	@GetMapping("/users")
	public String showUsers(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "user/list";
	}

	@GetMapping("/users/{id}")
	public String showProfile(Model model, @PathVariable long id) {
		model.addAttribute("user", userRepository.findOne(id));
		return "user/profile";
	}

	@GetMapping("/users/form")
	public String addForm(Model model) {
		return "user/form";
	}

	@GetMapping("/users/{id}/form")
	public String updateForm(Model model, @PathVariable long id, HttpSession session) {
		Object tempUser = session.getAttribute("loginedUser");
		if (tempUser == null) {
			return "redirect:/";
		}
		
		User loginedUser = (User) tempUser;
		if(!loginedUser.matchId(id)) {
			return "redirect:/";
		}
		
		model.addAttribute("user", userRepository.findOne(id));
		return "user/updateForm";
	}

	@PostMapping("/users/{id}/update")
	public String updateUser(@PathVariable long id, User user, HttpSession session) {
		Object tempUser = session.getAttribute("loginedUser");
		if (tempUser == null) {
			return "redirect:/";
		}
		
		User loginedUser = (User) tempUser;
		if(!loginedUser.matchId(id)) {
			return "redirect:/";
		}
		
		User dbUser = userRepository.findOne(id);
		if (dbUser.update(user)) {
			userRepository.save(dbUser);
			session.setAttribute("loginedUser", dbUser);
		}
		return "redirect:/users";
	}

	@GetMapping("/users/login")
	public String loginForm() {
		return "user/login";
	}

	@PostMapping("/users/login")
	public String userLogin(HttpSession session, String userId, String password) {
		User dbUser = userRepository.findByUserId(userId);

		if (dbUser == null) {
			return "user/login_failed";
		}
		if (!dbUser.matchPassword(password)) {
			return "user/login_failed";
		}

		session.setAttribute("loginedUser", dbUser);
		return "redirect:/";
	}

	@GetMapping("users/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("loginedUser");
		return "redirect:/";
	}

}
