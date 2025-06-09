package umu.tds.apps.AppChat.persistencia;

import java.util.List;

import umu.tds.apps.AppChat.dominio.Usuario;

public interface UsuarioDAO {

	void create(Usuario usuario);
	boolean delete(Usuario usuario);
	void update(Usuario usuario);
	Usuario get(int id);
	List<Usuario> getAll();
	void setPremium(Usuario usuario, boolean premium);
	
}
