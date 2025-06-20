package umu.tds.apps.AppChat.persistencia.abstracta;

import java.util.List;

import umu.tds.apps.AppChat.dominio.Mensaje;

public interface MensajeDAO {

	
	Mensaje create(Mensaje mensaje);
	void delete(Mensaje mensaje);
	void update(Mensaje mensaje);
	Mensaje get(int id);
	List<Mensaje> getAll();
	
}
