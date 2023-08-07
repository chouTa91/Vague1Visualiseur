package fr.polemploi.suivi.migration.controller.db2;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.polemploi.suivi.migration.service.DataCompiler;

@Controller
@RequestMapping("db2")
public class DB2Controller {

	@Autowired
	private DataCompiler dataCompiler;

	/**
	 * Controller de l'index par défaut.
	 *
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/controle")
	public ModelAndView controleFichier() {

		ModelAndView mv = new ModelAndView("/fragments/DB2/fragment-db2-controlefichier");

		mv.addObject("db2ControleFiles", this.dataCompiler.getDb2ControleFiles());

		return mv;

	}

}
