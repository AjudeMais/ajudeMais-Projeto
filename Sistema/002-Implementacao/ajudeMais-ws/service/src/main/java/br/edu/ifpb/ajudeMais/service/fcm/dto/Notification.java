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

	private String icon = "ic_notification";

	private String color = "#12a7b5";

	private String title;

	private String body;

	/**
	 * 
	 */
	public Notification() {
	}

	/**
	 * 
	 * @param sound
	 * @param title
	 * @param body
	 */
	public Notification(String sound, String title, String body) {
		this.sound = sound;
		this.title = title;
		this.body = body;
	}

	/**
	 * @return o atributo sound
	 */
	public String getSound() {
		return sound;
	}

	/**
	 * @param o
	 *            parametro sound é setado em sound
	 */
	public void setSound(String sound) {
		this.sound = sound;
	}

	/**
	 * @return o atributo icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param o
	 *            parametro icon é setado em icon
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return o atributo title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param o
	 *            parametro title é setado em title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return o atributo body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param o
	 *            parametro body é setado em body
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return o atributo color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param o
	 *            parametro color é setado em color
	 */
	public void setColor(String color) {
		this.color = color;
	}

}
