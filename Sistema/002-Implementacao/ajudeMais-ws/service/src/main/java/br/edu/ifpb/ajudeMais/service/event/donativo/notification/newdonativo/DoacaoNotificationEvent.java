/**
 * <p>
 * Ajude Mais - Módulo Web Service
 * </p>
 * 
 * <p>
 * Sistema para potencializar o processo de doação.
 * </p>
 * 
 * <a href="https://github.com/AjudeMais/AjudeMais">Ajude Mais</a>
 * <a href="https://franckaj.github.io">Franck Aragão"></a>
 * 
 * AJUDE MAIS - 2017®
 * 
 */
package br.edu.ifpb.ajudeMais.service.event.donativo.notification.newdonativo;

import java.util.List;

import br.edu.ifpb.ajudeMais.domain.entity.Donativo;

public class DoacaoNotificationEvent {

	private List<String> notificaveis;

	private Donativo donativo;

	private String descricao;

	public DoacaoNotificationEvent(List<String> notificaveis, Donativo donativo, String descricao) {
		this.notificaveis = notificaveis;
		this.donativo = donativo;
		this.descricao = descricao;
	}

	/**
	 * @return o atributo descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param o
	 *            parametro descricao é setado em descricao
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return o atributo notificaveis
	 */
	public List<String> getNotificaveis() {
		return notificaveis;
	}

	/**
	 * @param o
	 *            parametro notificaveis é setado em notificaveis
	 */
	public void setNotificaveis(List<String> notificaveis) {
		this.notificaveis = notificaveis;
	}

	/**
	 * @return o atributo donativo
	 */
	public Donativo getDonativo() {
		return donativo;
	}

	/**
	 * @param o
	 * parametro donativo é setado em donativo
	 */
	public void setDonativo(Donativo donativo) {
		this.donativo = donativo;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isNotificavel() {
		return !notificaveis.isEmpty();
	}

}
