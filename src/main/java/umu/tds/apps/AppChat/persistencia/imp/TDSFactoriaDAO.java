package umu.tds.apps.AppChat.persistencia.imp;

public class TDSFactoriaDAO {

	public static TDSFactoriaDAO unicaInstancia = null;
	
	/*
	 * Patron singleton
	 */
	public static TDSFactoriaDAO getInstance() {
		if (unicaInstancia == null) {
			unicaInstancia = new TDSFactoriaDAO();
		}
		return unicaInstancia;
	}
	
	public TDSFactoriaDAO() {	}
	
	public TDSUsuarioDAO getUsuarioDAO() {	
		return TDSUsuarioDAO.getInstance();
	}

	public TDSContactoIndividualDAO getContactoIndividualDAO() {
		return TDSContactoIndividualDAO.getInstance();
	}

	public TDSGrupoDAO getGrupoDAO() {
		return TDSGrupoDAO.getInstance();
	}
	
	public TDSMensajeDAO getMensajeDAO() {
		return TDSMensajeDAO.getInstance();
	}
}
