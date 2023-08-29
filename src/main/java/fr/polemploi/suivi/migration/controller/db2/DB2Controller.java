package fr.polemploi.suivi.migration.controller.db2;

import java.io.IOException;

import fr.polemploi.suivi.migration.service.FilesRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.polemploi.suivi.migration.service.DataCompiler;

@Controller
@RequestMapping("db2")
public class DB2Controller {

	@Autowired
	private DataCompiler dataCompiler;

	@Autowired
	private FilesRetriever filesRetriever;

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

	@GetMapping("/log/conversion/{tableName}")
	public ModelAndView getConversionLogs(@PathVariable String tableName) {

		ModelAndView mv = new ModelAndView("/fragments/DB2/fragment-db2-log-modal");
		mv.addObject("logFile", this.filesRetriever.getDB2ConversionLogFile(tableName));
		return mv;
	}

	@GetMapping("/log/load/{tableName}")
	public ModelAndView getLoadLogs(@PathVariable String tableName) {

		mv.addObject("logFile", this.filesRetriever.getDB2LoadLogFile(tableName));
		ModelAndView mv = new ModelAndView("/fragments/DB2/fragment-db2-log-modal");
		return mv;
	}

}
