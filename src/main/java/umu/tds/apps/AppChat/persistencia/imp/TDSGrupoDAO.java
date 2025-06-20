package umu.tds.apps.AppChat.persistencia.imp;

import tds.driver.ServicioPersistencia;

public class TDSGrupoDAO {

	private static final String NOMBRE = "nombre";
	private static final String LISTA_CANCIONES = "lcanciones";

	private static ServicioPersistencia servPersistencia;
	private static TDSGrupoDAO unicaInstancia = null;

	/*
	 * Patron singleton
	 */
	public static TDSGrupoDAO getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new TDSGrupoDAO();
		return unicaInstancia;
	}
}
