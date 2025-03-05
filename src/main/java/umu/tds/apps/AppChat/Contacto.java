package umu.tds.apps.AppChat;

public class Contacto {

	protected String nombre;
	
	public Contacto(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre(String nombre) {
		return this.nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
