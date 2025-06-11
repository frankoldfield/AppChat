package umu.tds.apps.AppChat.dominio;

public class ContactoIndividual extends Contacto {
	
	private String id;
	private String movil;
	
	public ContactoIndividual(String movil) {
		super("");
		this.movil = movil;
	}
	
	public ContactoIndividual(String nombre, String numero) {
		super(nombre);
		this.movil = numero;
	}
	
	public String getMovil() {
		return this.movil;
	}
	
	public void setMovil(String numero) {
		this.movil = numero;
	}

	public Usuario getUsuario() {
		// TODO Auto-generated method stub
		return null;
	}
}
