
/**
 * 
 * <p>
 * <b> DonativoColetaUtil.java </b>
 * </p>
 *
 * <p>
 * Entidade ...
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
package br.edu.ifpb.ajudeMais.service.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.domain.entity.EstadoDoacao;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.domain.enumerations.Estado;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.MensageiroAssociadoService;

/**
 * 
 * <p>
 * <b> DonativoColetaUtil.java </b>
 * </p>
 *
 * <p>
 * Contém métodos utilitários para utilização
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
@Component
public class DonativoColetaUtil {

	@Autowired
	private MensageiroAssociadoService mensageiroAssociadoService;

	/**
	 * 
	 * <p>
	 * Método auxiliar para atualizar estado de uma doação.
	 * </p>
	 * 
	 * @param donativo
	 *            donativo a ser atualizado
	 * @param estado
	 *            estado a ser alterado no donativo
	 * @param notificado
	 *            true para estado notificado / false para estado não
	 *            notificado.
	 * @return
	 */
	@Transactional
	public Donativo addEstadoDoacao(Donativo donativo, Estado estado, boolean notificado) {
		donativo.getEstadosDaDoacao().forEach(e -> {
			if (e.getAtivo()) {
				e.setAtivo(false);
			}
		});
		EstadoDoacao estadoDoacao = new EstadoDoacao();
		estadoDoacao.setAtivo(true);
		estadoDoacao.setData(new Date());
		estadoDoacao.setNotificado(notificado);
		estadoDoacao.setEstadoDoacao(estado);
		donativo.getEstadosDaDoacao().add(estadoDoacao);

		return donativo;
	}

	/**
	 * 
	 * <p>
	 * Recupera estado da doação com estado ativo de um donativo.
	 * </p>
	 * 
	 * @param donativo
	 * @return
	 */
	public EstadoDoacao getEstadoDoacaoAtivo(Donativo donativo) {
		EstadoDoacao estadoDoacao = null;
		for (EstadoDoacao estado : donativo.getEstadosDaDoacao()) {
			if (estado.getAtivo()) {
				estadoDoacao = estado;
			}
		}
		return estadoDoacao;
	}

	/**
	 * 
	 * <p>
	 * Obtém lista de mensageiros por bairro que serão notificados.
	 * </p>
	 * 
	 * @param campanha
	 * @return
	 * @throws AjudeMaisException
	 */
	public List<String> getNotificaveisToBairro(Donativo donativo) throws AjudeMaisException {

		List<Mensageiro> mensageiros = mensageiroAssociadoService.filterMensageirosCloserToBairro(
				donativo.getEndereco(), donativo.getCategoria().getInstituicaoCaridade().getId());

		return getTokensNotificaveis(mensageiros);
	}

	/**
	 * 
	 * <p>
	 * Obtém lista de mensageiros por cidade que serão notificados.
	 * </p>
	 * 
	 * @param campanha
	 * @return
	 * @throws AjudeMaisException
	 */
	public List<String> getNotificaveisToCidade(Donativo donativo) throws AjudeMaisException {

		List<Mensageiro> mensageiros = mensageiroAssociadoService.filterMensageirosCloserToCidade(
				donativo.getEndereco(), donativo.getCategoria().getInstituicaoCaridade().getId());

		return getTokensNotificaveis(mensageiros);
	}

	/**
	 * 
	 * <p>
	 * Obtém lista de tokens dos mensageiros que serão notificados;
	 * </p>
	 * 
	 * @param mensageiros
	 * @return
	 */
	private List<String> getTokensNotificaveis(List<Mensageiro> mensageiros) {
		List<String> notificaveis = new ArrayList<>();

		mensageiros.forEach(m -> {
			boolean isValid = true;

			for (String n : notificaveis) {
				if (n.equals(m.getTokenFCM().getToken()))
					isValid = false;
			}

			if (isValid) {
				notificaveis.add(m.getTokenFCM().getToken());
			}
		});

		return notificaveis;
	}
}
