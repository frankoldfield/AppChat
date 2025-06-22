package umu.tds.apps.AppChat.dominio;

public abstract class Contacto {

	protected String nombre;
	protected int id;
	public Contacto(String nombre) {
		
		this.id = 0;
		this.nombre = nombre;
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
