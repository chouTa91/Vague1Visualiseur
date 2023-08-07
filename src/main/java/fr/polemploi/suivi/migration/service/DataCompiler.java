package fr.polemploi.suivi.migration.service;

import java.io.IOException;

import fr.polemploi.suivi.migration.entities.db2.DB2TablesFiles;
import fr.polemploi.suivi.migration.entities.dl1.DL1DirectionRegionaleTablesFilesContainer;
import fr.polemploi.suivi.migration.entities.tir.TirDetailDB2;
import fr.polemploi.suivi.migration.entities.tir.TirDetailDL1;
import fr.polemploi.suivi.migration.entities.tir.TirSynthesis;

/**
 * Compile les informations des tirs.
 *
 * @author jbourrea
 *
 */
public interface DataCompiler {

	/**
	 * Chargement complet des informations d'un tir pour DB2.
	 *
	 * @return
	 * @throws IOException
	 */
	public TirDetailDB2 gatherTirDB2AllInfos() throws IOException;

	/**
	 * Chargement de la synthèse d'un tir.s
	 *
	 * @return
	 * @throws IOException
	 */
	public TirSynthesis gatherTirSynthesis() throws IOException;

	/**
	 * Récupération des informations de controle des fichiers de DL1.
	 *
	 * @return
	 */
	public DL1DirectionRegionaleTablesFilesContainer getDl1ControleFiles();

	/**
	 * Récupération des informations de controle des logs de DL1.
	 *
	 * @return
	 */
	public DL1DirectionRegionaleTablesFilesContainer getDl1ControleLogs();

	/**
	 * Récupération des informations de controle des fichiers de DB2.
	 *
	 * @return
	 */
	public DB2TablesFiles getDb2ControleFiles();

	/**
	 * Chargement complet des informations d'un tir pour DB2.
	 *
	 * @return
	 * @throws IOException
	 */
	public TirDetailDL1 gatherTirDL1AllInfos() throws IOException;
}
