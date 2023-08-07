package fr.polemploi.suivi.migration.controller.dl1;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.polemploi.suivi.migration.service.DataCompiler;
import fr.polemploi.suivi.migration.service.FilesRetriever;

@Controller
@RequestMapping("dl1")
public class DL1Controller {

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
	@GetMapping("/controlefichiers")
	public ModelAndView controleFichier() {

		ModelAndView mv = new ModelAndView("/fragments/DL1/fragment-dl1-controlefichier");

		mv.addObject("dl1ControleFilesContainer", this.dataCompiler.getDl1ControleFiles());

		return mv;

	}

	/**
	 * Controller de l'index par défaut.
	 *
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/controlelogs")
	public ModelAndView controleLogs() {

		ModelAndView mv = new ModelAndView("/fragments/DL1/fragment-dl1-controlelogs");

		mv.addObject("dl1ControleLogsContainer", this.dataCompiler.getDl1ControleLogs());

		return mv;

	}

	@GetMapping("/volumetrie/logs/{tableName}")
	public ModelAndView getLogs(@PathVariable String tableName) {

		ModelAndView mv = new ModelAndView("/fragments/DL1/fragment-dl1-volumetrie-logs");

		mv.addObject("tableName", tableName);
		mv.addObject("logs", this.filesRetriever.getDL1RawLogsFiles(tableName));

		return mv;
	}

	@GetMapping("/volumetrie/oracle/logs/{tableName}")
	public ModelAndView getOracleLogs(@PathVariable String tableName) throws IOException {

		ModelAndView mv = new ModelAndView("/fragments/DL1/fragment-dl1-volumetrie-logs");

		mv.addObject("tableName", tableName);
		mv.addObject("logs", this.filesRetriever.getDL1RawLogsChargeFiles(tableName));

		return mv;

	}

	@GetMapping("/volumetrie/logstable/{tableName}")
	public ModelAndView getOracleLogsTable(@PathVariable String tableName) throws IOException {

		ModelAndView mv = new ModelAndView("/fragments/DL1/fragment-dl1-volumetrie-logs");

		mv.addObject("tableName", tableName);
		mv.addObject("logs", this.filesRetriever.getDL1RawLogsFilesTable(tableName));

		return mv;

	}

}
