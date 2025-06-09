package umu.tds.apps.AppChat.persistencia;

import java.util.List;

import umu.tds.apps.AppChat.dominio.Grupo;

public interface GrupoDAO {

	void create(Grupo grupo);
	boolean delete(Grupo grupo);
	void update(Grupo grupo);
	Grupo get(int id);
	List<Grupo> getAll();
	
}
