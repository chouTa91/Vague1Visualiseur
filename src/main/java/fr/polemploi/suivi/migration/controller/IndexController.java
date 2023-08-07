package fr.polemploi.suivi.migration.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.polemploi.suivi.migration.config.AppContext;
import fr.polemploi.suivi.migration.service.DataCompiler;

@Controller
public class IndexController {

	private static final String PAGE_INDEX = "/pages/index";

	@Autowired
	private AppContext appContext;

	@Autowired
	private DataCompiler dataCompiler;

	/**
	 * Controller de l'index par défaut.
	 *
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/")
	public ModelAndView index() throws IOException {

		ModelAndView mv = new ModelAndView(IndexController.PAGE_INDEX);

		mv.addObject("cible", this.appContext.getCible());
		mv.addObject("versionOutil", this.appContext.getVersionOutil());
		mv.addObject("tirSynthesis", this.dataCompiler.gatherTirSynthesis());

		return mv;

	}

}
