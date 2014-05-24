package br.ueg.unucet.plugin.statictextvisao1;

import org.zkoss.zul.Label;

import br.ueg.unucet.quid.extensao.interfaces.IComponenteInterface;

/**
 * Interface do InputText, cont�m os componentes que ser�o exibidos
 * no Descritor de Tela e como setar e obter seu valor.
 * 
 * @author Diego
 *
 */
public class StaticTextInterface implements IComponenteInterface {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 344518551310671764L;	
	
	/**
	 * Construtor DEFAULT
	 */
	public StaticTextInterface() {
	}

	/**
	 * M�todo que retorna o componente de preenchimento
	 * 
	 * @return Textbox componente
	 */
	public Label getPreenchimento(StaticText text) {
		return new Label();
	}
	
	/**
	 * M�todo que retorna o componente de visualiza��o
	 * 
	 * @return Label componente
	 */
	public Label getVisualizacao(StaticText text) {
		return new Label();
	}
	
	@Override
	public void setValor(Object valor) {
		//
	}
	
	@Override
	public Object getValor() {
		return null;
	}

	@Override
	public Object getValor(Object componente) {
		return ((Label) componente).getValue();
	}
}