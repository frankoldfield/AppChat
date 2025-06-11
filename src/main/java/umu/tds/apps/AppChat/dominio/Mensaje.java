package umu.tds.apps.AppChat.dominio;

import java.time.LocalDateTime;

public class Mensaje {
	
	private String id;
	private String texto;
	private LocalDateTime hora;
	private int emoji;
	private TipoMensaje tipo;
	private ContactoIndividual contacto_emisor;
	private Contacto contacto_receptor;
	
	public Mensaje(String texto, LocalDateTime hora, int emoji, TipoMensaje tipo, ContactoIndividual contacto_emisor, Contacto contacto_receptor) {
		this.texto = texto;
		this.hora = hora;
		this.emoji = emoji;
		this.tipo = tipo;
		this.contacto_emisor = contacto_emisor;
		this.contacto_receptor = contacto_receptor;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getHora() {
		return hora;
	}

	public void setHora(LocalDateTime hora) {
		this.hora = hora;
	}

	public int getEmoji() {
		return emoji;
	}

	public void setEmoji(int emoji) {
		this.emoji = emoji;
	}

	public TipoMensaje getTipo() {
		return tipo;
	}

	public void setTipo(TipoMensaje tipo) {
		this.tipo = tipo;
	}

	public ContactoIndividual getContacto_emisor() {
		return contacto_emisor;
	}

	public void setContacto_emisor(ContactoIndividual contacto_emisor) {
		this.contacto_emisor = contacto_emisor;
	}

	public Contacto getContacto_receptor() {
		return contacto_receptor;
	}

	public void setContacto_receptor(ContactoIndividual contacto_receptor) {
		this.contacto_receptor = contacto_receptor;
	}

}
