package umu.tds.apps.AppChat;

import java.util.ArrayList;

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
	
	public String getNombre(String nombre) {
		return this.nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
