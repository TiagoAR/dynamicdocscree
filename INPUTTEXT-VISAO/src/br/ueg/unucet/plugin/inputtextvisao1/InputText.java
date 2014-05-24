package br.ueg.unucet.plugin.inputtextvisao1;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.zkoss.zul.Label;

import br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembroVisaoZK;

/**
 * TipoMembro-Vis�o InputText
 * Classe respons�vel por representar o TipoMembro-Vis�o
 * cont�m implementa��es dos m�todos do SuperTipoMembroVisaoZK
 * 
 * @author Diego
 *
 */
public class InputText extends SuperTipoMembroVisaoZK<InputTextInterface> {
	
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 7574516772274099933L;
	
	/**
	 * Construtor DEFAULT, seta os dados que grava no banco e que s�o necess�rios
	 * para obter o componente de visualiza��o
	 */
	public InputText() {
		this.componente =  new InputTextInterface();
		setDescricao("Vis�o Padr�o");
		setVersao(1);
		setRevisao(12);
	}

	@Override
	public String getNome() {
		return "inputtextvisao";
	}

	@Override
	public Icon getImagemIlustrativa() {
		return new ImageIcon(getClass().getResource("/input.png"));
	}

	@Override
	public Object getVisaoPreenchimento() {
		return getComponente().getPreenchimento();
	}

	@Override
	public Object getVisaoVisualizacao() {
		return getComponente().getVisualizacao();
	}
	
	@Override
	public Object getVisualizacaoExemplo(Object componente, Object valorExemplo) {
		Label visualizacao = (Label) componente;
		visualizacao.setValue(valorExemplo.toString());
		return visualizacao;
	}

	@Override
	public boolean isEntradaValida(Object valor) {
		return valor instanceof String;
	}

	@Override
	public String getEventoMascara() {
		return null;
	}

	@Override
	public String getNomeTipoMembroModelo() {
		return "inputtext";
	}

	@Override
	public Object getValorVisualizacao(Object componente) {
		return this.componente.getValor(componente);
	}

}
