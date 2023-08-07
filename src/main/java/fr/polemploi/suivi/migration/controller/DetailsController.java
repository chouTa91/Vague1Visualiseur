package fr.polemploi.suivi.migration.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.polemploi.suivi.migration.config.AppContext;
import fr.polemploi.suivi.migration.entities.tir.TirAggregate;
import fr.polemploi.suivi.migration.service.DataCompiler;

@Controller
public class DetailsController {

	@Autowired
	private AppContext appContext;

	private static final String PAGE_DETAIL = "/pages/detail";
	private static final String PAGE_DETAIL_DL1 = "/pages/detailDL1";
	private static final String PAGE_DETAIL_DB2 = "/pages/detailDB2";
	private static final String PAGE_DETAIL_FICHIER_PERMANENT = "/pages/detailFichierPermanent";
	private static final String PAGE_DETAIL_FICHIER_TECHNIQUE = "/pages/detailFichierTechnique";
	private static final String PAGE_DETAIL_PACTABLE = "/pages/detailPactable";

	@Autowired
	private DataCompiler dataCompiler;

	/**
	 * Controller de la page des détails.
	 *
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/detail")
	public ModelAndView index() throws IOException {

		ModelAndView mv = new ModelAndView(DetailsController.PAGE_DETAIL);

		TirAggregate tirList = new TirAggregate();

		tirList.addTir(this.dataCompiler.gatherTirDB2AllInfos());

		mv.addObject("cible", this.appContext.getCible());
		mv.addObject("versionOutil", this.appContext.getVersionOutil());
		mv.addObject("tirList", tirList);

		return mv;

	}

	/**
	 * Controller de la page des détails.
	 *
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/detailDL1")
	public ModelAndView detailDL1() throws IOException {

		ModelAndView mv = new ModelAndView(DetailsController.PAGE_DETAIL_DL1);

		TirAggregate tirList = new TirAggregate();

		tirList.addTir(this.dataCompiler.gatherTirDL1AllInfos());

		mv.addObject("cible", this.appContext.getCible());
		mv.addObject("versionOutil", this.appContext.getVersionOutil());
		mv.addObject("tirList", tirList);

		return mv;

	}

	/**
	 * Controller de la page des détails.
	 *
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/detailDB2")
	public ModelAndView detailDB2() throws IOException {

		ModelAndView mv = new ModelAndView(DetailsController.PAGE_DETAIL_DB2);

		TirAggregate tirList = new TirAggregate();

		tirList.addTir(this.dataCompiler.gatherTirDB2AllInfos());

		mv.addObject("cible", this.appContext.getCible());
		mv.addObject("versionOutil", this.appContext.getVersionOutil());
		mv.addObject("tirList", tirList);

		return mv;

	}

	/**
	 * Controller de la page des détails.
	 *
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/detailFichierPermanent")
	public ModelAndView detailFichierPermanent() throws IOException {

		ModelAndView mv = new ModelAndView(DetailsController.PAGE_DETAIL_FICHIER_PERMANENT);

		return mv;

	}

	/**
	 * Controller de la page des détails.
	 *
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/detailFichierTechnique")
	public ModelAndView detailFichierTechnique() throws IOException {

		ModelAndView mv = new ModelAndView(DetailsController.PAGE_DETAIL_FICHIER_TECHNIQUE);

		return mv;

	}

	/**
	 * Controller de la page des détails.
	 *
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/detailPactable")
	public ModelAndView detailPactable() throws IOException {

		ModelAndView mv = new ModelAndView(DetailsController.PAGE_DETAIL_PACTABLE);

		return mv;

	}

}
