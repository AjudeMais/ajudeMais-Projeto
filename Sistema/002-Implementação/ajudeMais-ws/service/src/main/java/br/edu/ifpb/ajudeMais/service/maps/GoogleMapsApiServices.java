
package br.edu.ifpb.ajudeMais.service.maps;

import java.util.ArrayList;
import java.util.List;

import com.google.maps.model.DistanceMatrix;

import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;

/**
 * 
 * <p>
 * <b> GoogleMapsApiServices.java </b>
 * </p>
 *
 * <p>
 * Entidade ...
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class GoogleMapsApiServices {

	private List<Mensageiro> mensageiros;
	private List<Mensageiro> mensageirosSelecionados = new ArrayList<>();

	// @Autowired
	private GoogleMapsResponse googleMapsResponse = new GoogleMapsResponse();

	public GoogleMapsApiServices() {
		mensageiros = new ArrayList<>();
	}

	/**
	 * Filtra mensageiros que possuem endereços no mesmo bairro ou cidade do
	 * endereço passado.
	 * 
	 * @param enderecoDoador
	 * @return
	 * @throws NaoExisteMensageiro
	 */
	public List<Mensageiro> filtraMensageiroPorArea(Endereco enderecoDoador) throws AjudeMaisException {

		for (Mensageiro mensageiro : mensageiros) {

			for (Endereco endereco : mensageiro.getEnderecos()) {

				if ((endereco.getBairro().equals(enderecoDoador.getBairro())
						&& endereco.getLocalidade().equals(enderecoDoador.getLocalidade()))
						&& endereco.getUf().equals(enderecoDoador.getUf())) {

					mensageirosSelecionados.add(mensageiro);
					break;
				}
			}

		}

		if (mensageirosSelecionados.isEmpty()) {
			for (Mensageiro mensageiro : mensageiros) {
				for (Endereco endereco : mensageiro.getEnderecos()) {

					if ((endereco.getLocalidade().equals(enderecoDoador.getLocalidade()))
							&& endereco.getUf().equals(enderecoDoador.getUf())) {

						mensageirosSelecionados.add(mensageiro);
						break;
					}
				}

			}
		
		} else {
			return mensageirosSelecionados;
		}

		throw new AjudeMaisException("Infelizmente não há mensageiros em sua localidade.");

	}

//	/**
//	 * Verificar qual o mensageiro cadastrado mais próximo do endereço passado.
//	 * 
//	 * @param endereco
//	 * @return
//	 * @throws Exception
//	 */
//	public Mensageiro verificaMensageiroMaisProximo(Endereco endereco) throws Exception {
//		List<Mensageiro> mensageirosSelecionados = filtraMensageiroPorArea(endereco);
//
//		Mensageiro mensageiroMaisProximo = null;
//		
//		long menorDistacia = 0;
//
//		if (mensageirosSelecionados != null) {
//			
//			String[] mensageiro = new String[mensageirosSelecionados.size()];
//
//			for (int i = 0; i < mensageirosSelecionados.size(); i++) {
//				mensageiro[i] = mensageirosSelecionados.get(i).getEndereco().getRua() + ","
//						+ mensageirosSelecionados.get(i).getEndereco().getBairro() + ","
//						+ mensageirosSelecionados.get(i).getEndereco().getCidade() + ","
//						+ mensageirosSelecionados.get(i).getEndereco().getEstado();
//			}
//
//			DistanceMatrix matrix = googleMapsResponse.consultaDistancia(endereco.getRua() + ","
//					+ endereco.getBairro() + "," + endereco.getCidade() + "," + endereco.getEstado(), mensageiro);
//
//			for (int i = 0; i <= matrix.rows.length; i++) {
//				System.out.println("Rua:" + matrix.rows[0].elements[i] + ", Distancia: "
//						+ matrix.rows[0].elements[i].distance.inMeters);
//
//				if (menorDistacia > matrix.rows[0].elements[i].distance.inMeters || menorDistacia == 0) {
//					menorDistacia = matrix.rows[0].elements[i].distance.inMeters;
//					mensageiroMaisProximo = mensageirosSelecionados.get(i);
//
//				}
//			}
//
//		}
//
//		return mensageiroMaisProximo;
//	}

//	/**
//	 * Método provisório que add alguns mensageiros para realização das
//	 * operações
//	 */
//	private void addMensageiros() {
//		Endereco end1 = new Endereco("Leopoldino José Da Silva", "Centro", "Monteiro", "Paraíba");
//		mensageiros.add(new Mensageiro(end1));
//
//		Endereco end2 = new Endereco("Ac Rodovia PB 264", "Serrote", "Monteiro", "Paraíba");
//		mensageiros.add(new Mensageiro(end2));
//
//		Endereco end3 = new Endereco("R. Abelardo Pereira dos Santos", "Centro", "Monteiro", "Paraíba");
//		mensageiros.add(new Mensageiro(end3));
//
//	}
//
//	/**
//	 * Método main para testar operações.
//	 * 
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		DistanciaService distanciaService = new DistanciaService();
//		try {
//			Mensageiro msg = distanciaService
//					.verificaMensageiroMaisProximo(new Endereco("Praça João Pessoa", "Centro", "Monteiro", "Paraíba"));
//
//			System.out.println(msg.getEndereco().getRua());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
