package br.ueg.unucet.docscree.visao.compositor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ueg.unucet.docscree.controladores.TipoMembroControle;

/**
 * Compositor da tela de upload de TipoMembro
 * 
 * @author Diego
 * @author Tiago
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Component
@Scope("session")
public class TipoMembroCompositor extends
		SuperMapeavelCompositor<TipoMembroControle> {

	/**
	 * DEFAULT SERIAL ID
	 */
	private static final long serialVersionUID = 955019688639995178L;
	
	
	/**
	 * Mapea a lista de arquivos de TipoMembro para o framework
	 */
	public void mapearTipoMembro() {
		try {
			boolean fazerAcao = getControle().fazerAcao("mapearTipoMembro", (SuperCompositor) this);
			super.mostrarMensagem(fazerAcao);
			super.binder.loadAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
