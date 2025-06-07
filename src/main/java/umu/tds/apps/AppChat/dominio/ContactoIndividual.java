package umu.tds.apps.AppChat.dominio;

public class ContactoIndividual extends Contacto {
	
	private String numero;
	
	public ContactoIndividual(String nombre, String numero) {
		super(nombre);
		this.numero = numero;
	}
	
	public String getNumero() {
		return this.numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Usuario getUsuario() {
		// TODO Auto-generated method stub
		return null;
	}
}
