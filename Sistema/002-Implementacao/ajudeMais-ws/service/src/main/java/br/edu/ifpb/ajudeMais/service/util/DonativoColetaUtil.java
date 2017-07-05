
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
public class DonativoColetaUtil {


	private MensageiroAssociadoService mensageiroAssociadoService;
	
	/**
	 * 
	 */
	public DonativoColetaUtil(MensageiroAssociadoService mensageiroAssociadoService) {
		this.mensageiroAssociadoService = mensageiroAssociadoService;
	}
	
	public DonativoColetaUtil(){}
	
	/**
	 * Método auxiliar para atualizar estado de uma doação no caso de nenhum mensageiro ser encontrado.
	 * @param donativo
	 * @return
	 */
	public Donativo updateEstadoDoacao(Donativo donativo){
		EstadoDoacao estadoDoacao = new EstadoDoacao();
		estadoDoacao.setAtivo(true);
		estadoDoacao.setData(new Date());
		estadoDoacao.setEstadoDoacao(Estado.NAOACEITO);
		
		donativo.getEstadosDaDoacao().forEach(estado-> {
			if (estadoDoacao.getAtivo()) {
				estadoDoacao.setAtivo(false);
			}
		});
		
		donativo.getEstadosDaDoacao().add(estadoDoacao);
		return donativo;
	}

	/**
	 * 
	 * <p>
	 * Obtém lista de mensageiros que serão notificados.
	 * </p>
	 * 
	 * @param campanha
	 * @return
	 * @throws AjudeMaisException 
	 */
	public List<String> getNotificaveis(Donativo donativo) throws AjudeMaisException {

		List<Mensageiro> mensageiros = mensageiroAssociadoService.filterMensageirosCloser(donativo.getEndereco(),
				donativo.getCategoria().getInstituicaoCaridade().getId());

		List<String> notificaveis = new ArrayList<>();

		mensageiros.forEach(m -> {
			boolean isValid = true;
			
			for(String n : notificaveis){
				if(n.equals(m.getTokenFCM().getToken()))
					isValid = false;
			}
		
			if (isValid) {
				notificaveis.add(m.getTokenFCM().getToken());
			}
		});

		return notificaveis;
	}
}
