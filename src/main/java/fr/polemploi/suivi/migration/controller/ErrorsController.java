package fr.polemploi.suivi.migration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorsController {

	// TODO JBOU Gestion des erreurs avec ControllerAdvice et tout le tintouin.
	@GetMapping("/error")
	public String error() {
		return "error";
	}
}
