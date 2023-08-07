package fr.polemploi.suivi.migration.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import fr.polemploi.suivi.migration.api.path.PathDispenser;
import fr.polemploi.suivi.migration.entities.enums.common.DirectionRegionaleEnum;
import jakarta.annotation.PostConstruct;

// TODO JBOU injection des infos dans les controller via AOP ?
@Component
public class AppContext {

	private static final Logger logger = LoggerFactory.getLogger(AppContext.class);

	@Autowired
	private PathDispenser pathDispenser;

	@Value("${pe.visualiser.cadre.cible}")
	private String cible;

	private String versionOutil;

	// TODO JBOU gestion fine du chef de file.
	private DirectionRegionaleEnum chefDeFile;

	public AppContext() {
	}

	public String getCible() {
		return this.cible;
	}

	public void setCible(String cible) {
		this.cible = cible;
	}

	public String getVersionOutil() {
		return this.versionOutil;
	}

	public void setVersionOutil(String versionOutil) {
		this.versionOutil = versionOutil;
	}

	public DirectionRegionaleEnum getChefDeFile() {
		return this.chefDeFile;
	}

	@PostConstruct
	private void initContext() {

		FileSystemResource file = new FileSystemResource(this.pathDispenser.getVersionOutillage());

		if (file.getFile().exists()) {

			BufferedReader bufferedReader;
			try {
				bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));

				for (String line; (line = bufferedReader.readLine()) != null;) {
					this.versionOutil = line;
				}

				bufferedReader.close();
			} catch (IOException e) {
				AppContext.logger.error("Couldn't find tools version file : ", e);
			}

		}

		this.defineChefDeFile();
	}

	private void defineChefDeFile() {
		switch (this.cible) {
		case "CadreQ": {
			this.chefDeFile = DirectionRegionaleEnum.DR65;
			break;
		}
		case "CadreR": {
			this.chefDeFile = DirectionRegionaleEnum.DR65;
			break;
		}
		default:
			break;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.cible, this.versionOutil);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AppContext other)) {
			return false;
		}
		return Objects.equals(this.cible, other.cible) && Objects.equals(this.versionOutil, other.versionOutil);
	}

	@Override
	public String toString() {
		return "AppContext [cible=" + this.cible + ", versionOutil=" + this.versionOutil + "]";
	}

}
