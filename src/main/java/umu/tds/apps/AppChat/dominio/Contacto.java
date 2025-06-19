package umu.tds.apps.AppChat.dominio;

import java.util.ArrayList;
import java.util.List;

public class Contacto {

	protected String nombre;
	protected ArrayList<Mensaje> mensajes;
	
	public Contacto(String nombre) {
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
}
