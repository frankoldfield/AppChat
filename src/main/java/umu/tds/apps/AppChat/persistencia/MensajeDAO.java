package umu.tds.apps.AppChat.persistencia;

import java.util.List;

import umu.tds.apps.AppChat.dominio.Mensaje;

public interface MensajeDAO {

	
	void create(Mensaje mensaje);
	boolean delete(Mensaje mensaje);
	void update(Mensaje mensaje);
	Mensaje get(int id);
	List<Mensaje> getAll();
	
}
