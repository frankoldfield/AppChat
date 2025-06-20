package umu.tds.apps.AppChat.dominio;

import java.util.ArrayList;
import java.util.List;

public class Contacto {

	protected String nombre;
	protected ArrayList<Mensaje> mensajes;
	protected int id;
	public Contacto(String nombre) {
		this.id = 0;
		this.nombre = nombre;
		this.mensajes = new ArrayList<>();
	}
	
	public int addMensaje(Mensaje mensaje) {
		mensajes.add(mensaje);
		return 0;
	}
	
	public List<Mensaje> getMensajes() {
		return mensajes;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
    public String toString() {
        return nombre;
    }
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
