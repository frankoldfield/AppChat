package umu.tds.apps.AppChat.persistencia.abstracta;

import umu.tds.apps.AppChat.persistencia.DAOException;

public abstract class FactoriaDAO {
	public static final String DAO_TDS = "umu.tds.apps.AppChat.TDSFactoriaDAO";

	private static FactoriaDAO INSTANCE = null;

	public static FactoriaDAO getInstancia(String tipo) throws DAOException{
		if (INSTANCE == null)
			try { 
				INSTANCE=(FactoriaDAO) Class.forName(tipo).newInstance();
			} catch (Exception e) {	
				throw new DAOException(e.getMessage());
		} 
		return INSTANCE;
	}
	

	public static FactoriaDAO getInstancia() throws DAOException{
		return getInstancia(FactoriaDAO.DAO_TDS);
	}

	protected FactoriaDAO (){}
	
	// Metodos factoria para obtener adaptadores
	public abstract UsuarioDAO getUsuarioDAO();	
	public abstract ContactoIndividualDAO getContactoIndividualDAO();
	public abstract GrupoDAO getGrupoDAO();
	public abstract MensajeDAO getMensajeDAO();
}

