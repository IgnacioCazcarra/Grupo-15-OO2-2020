package com.unla.Grupo15OO22020.controllers;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.unla.Grupo15OO22020.entities.User;
import com.unla.Grupo15OO22020.helpers.ViewRouteHelpers;
import com.unla.Grupo15OO22020.implementation.UserRoleService;
import com.unla.Grupo15OO22020.implementation.UserService;
import com.unla.Grupo15OO22020.repositories.IUserRepository;
import com.unla.Grupo15OO22020.repositories.IUserRoleRepository;

@Controller	
public class UserController {	

	@Autowired
	@Qualifier("userRepository")
	private IUserRepository userRepository;
	@Autowired
	@Qualifier("userRoleRepository")
	private IUserRoleRepository userRoleRepository;
	@Autowired
	@Qualifier("userRoleService")
	private UserRoleService userRoleService;

	
	@GetMapping("/login")	
	public String login(Model model,	
						@RequestParam(name="error",required=false) String error,	
						@RequestParam(name="logout", required=false) String logout) {	

		if(error!=null){
			model.addAttribute("error", error);
		}
			
		model.addAttribute("logout", logout);
		
		String username = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		}
		System.out.println(username);
		if(!username.isEmpty()) {
			System.out.println("Ya esta logueado. Primero deberia desloguearse");
			return "redirect:/";
		}
		else {
			return ViewRouteHelpers.USER_LOGIN;
		}
	}	

	@GetMapping("/logout")	
	public String logout(Model model) {	
		String username = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		}
		System.out.println(username);
		if(username.isEmpty()) {
			System.out.println("no hay ninguna cuenta logueada");
			return "redirect:/";	
		}
		else return ViewRouteHelpers.USER_LOGOUT;	
	}	

	@GetMapping("/loginsuccess")	
	public String loginCheck() {	
		return "redirect:/";	
	}	
	

	
}