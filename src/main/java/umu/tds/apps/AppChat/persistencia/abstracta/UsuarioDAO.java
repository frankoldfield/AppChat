package umu.tds.apps.AppChat.persistencia.abstracta;

import java.util.List;

import umu.tds.apps.AppChat.dominio.Usuario;

public interface UsuarioDAO {

	void create(Usuario usuario);
	void delete(Usuario usuario);
	void update(Usuario usuario);
	Usuario get(int id);
	List<Usuario> getAll();
	
}
