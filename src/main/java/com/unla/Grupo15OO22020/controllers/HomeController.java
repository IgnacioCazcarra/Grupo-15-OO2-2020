package com.unla.Grupo15OO22020.controllers;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unla.Grupo15OO22020.helpers.ViewRouteHelpers;


@Controller
@RequestMapping("/")

public class HomeController {
	
	
	@GetMapping("")
	public String index() {
		return ViewRouteHelpers.INDEX;
	}

}











