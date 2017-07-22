package br.edu.ifpb.ajudeMais.domain.enumerations;

public enum Estado {

	DISPONIBILIZADO, ACEITO, CANCELADO, NAO_ACEITO, RECOLHIDO, RECEBIDO, ENTREGUE, CANCELADO_POR_MENSAGEIRO;

	public static Estado getByEstado(String estado) {
		if (estado != null) {

			for (Estado e : Estado.values()) {
				if (e.name().equals(estado))
					return e;
			}
		}
		return null;
	}
}
