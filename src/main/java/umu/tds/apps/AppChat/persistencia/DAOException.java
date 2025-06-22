package umu.tds.apps.AppChat.persistencia;

@SuppressWarnings("serial")
public class DAOException extends Exception {

	public DAOException(final String mensaje) {
		super("DAO EXCEPTION: "+mensaje);
	}
}
