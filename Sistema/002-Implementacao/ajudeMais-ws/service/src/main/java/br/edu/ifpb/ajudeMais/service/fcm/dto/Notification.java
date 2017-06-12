package br.edu.ifpb.ajudeMais.service.fcm.dto;

/**
 * 
 * <p>
 * {@link Notification}
 * </p>
 * 
 * <p>
 * Classe representa uma notificação enviada para o FCM.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public class Notification {

	private String sound;

	private String title;

	private String body;

	public Notification() {
	}

	public Notification(String sound, String title, String body) {
		this.sound = sound;
		this.title = title;
		this.body = body;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
