package br.edu.ifpb.ajudeMais.api.dto;

/**
 * 
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public class MessageErrorDTO {

	private String msg;

	public MessageErrorDTO() {
	}

	/**
	 * 
	 * @param msg
	 */
	public MessageErrorDTO(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
