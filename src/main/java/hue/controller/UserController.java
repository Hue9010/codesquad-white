package hue.controller;

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
	public String addUser(User user){
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
		model.addAttribute("user",userRepository.findOne(id));
		return "user/profile";
	}
	
	@GetMapping("/users/{id}/form")
	public String updateForm(Model model, @PathVariable long id) {
		model.addAttribute("user",userRepository.findOne(id));
		return "user/updateForm";
	}
	
	@PostMapping("/users/{id}/update")
	public String updateUser(@PathVariable long id, User user) {
		User dbUser = userRepository.findOne(id);
		if(dbUser.update(user)) {
			userRepository.save(dbUser);
		}
		return "redirect:/users";
	}
}
