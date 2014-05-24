package br.ueg.unucet.docscree.visao.compositor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ueg.unucet.docscree.controladores.ServicoControle;

/**
 * Compositor da tela de upload de TipoMembro
 * 
 * @author Diego
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Component
@Scope("session")
public class ServicoCompositor extends
		SuperMapeavelCompositor<ServicoControle> {
	
	public void mapearServico() {
		try {
			boolean fazerAcao = getControle().fazerAcao("mapearServico", (SuperCompositor) this);
			super.mostrarMensagem(fazerAcao);
			super.binder.loadAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}