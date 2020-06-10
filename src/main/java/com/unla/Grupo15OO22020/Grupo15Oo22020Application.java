package com.unla.Grupo15OO22020;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Grupo15Oo22020Application {

	public static void main(String[] args) {
		SpringApplication.run(Grupo15Oo22020Application.class, args);
		BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
		System.out.println(pe.encode("empleado"));
	}

}
