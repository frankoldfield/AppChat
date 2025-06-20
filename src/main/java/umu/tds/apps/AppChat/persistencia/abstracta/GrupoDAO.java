package umu.tds.apps.AppChat.persistencia.abstracta;

import java.util.List;

import umu.tds.apps.AppChat.dominio.Grupo;

public interface GrupoDAO {

	void create(Grupo grupo);
	void delete(Grupo grupo);
	void update(Grupo grupo);
	Grupo get(int id);
	List<Grupo> getAll();
	
}
