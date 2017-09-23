package hue.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hue.domain.User;

@Controller
public class UserController {
	
	List<User> users = new ArrayList<User>();
	
	@PostMapping("/user/create")
	public String addUser(User user){
		users.add(user);
		return "redirect:/users";
	}
	
	@GetMapping("/users")
	public String showUsers(Model model) {
		model.addAttribute("users",users);
		return "user/list";
	}
	
	@GetMapping("/users/{userId}")
	public String showProfile(Model model, @PathVariable String userId) {
		for (User user : users) {
			if(user.getUserId().equals(userId)) {
				model.addAttribute("user", user);
			}
		}
		return "user/profile";
	}
	
	@GetMapping("/users/{userId}/form")
	public String updateForm(Model model, @PathVariable String userId) {
		for (User user : users) {
			if(user.getUserId().equals(userId)) {
				model.addAttribute("user", user);
			}
		}
		return "user/updateForm";
	}
	
	@PostMapping("/users/update")
	public String updateUser(Model model, String userId, String name, String email) {
		for (User user : users) {
			if(user.getUserId().equals(userId)) {
				user.setUserId(userId);
				user.setName(name);
				user.setEmail(email);
			}
		}
		return "redirect:/users";
	}
}
