package br.ueg.unucet.plugin.imagemvisao1;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.zkoss.zul.Image;

import sun.awt.image.ToolkitImage;
import br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembroVisaoZK;

/**
 * TipoMembro-Vis�o ComoboBox
 * Classe respons�vel por representar o TipoMembro-Vis�o
 * cont�m implementa��es dos m�todos do SuperTipoMembroVisaoZK
 * 
 * @author Diego
 *
 */
public class Imagem extends SuperTipoMembroVisaoZK<ImagemInterface> {
	
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 7574516772274099933L;
	
	public static final String IMAGEM_LARGURA = "IMAGEM_LARGURA";
	public static final String IMAGEM_ALTURA  = "IMAGEM_ALTURA";
	/**
	 * Construtor DEFAULT, seta os dados que grava no banco e que s�o necess�rios
	 * para obter o componente de visualiza��o
	 */
	public Imagem() {
		this.componente =  new ImagemInterface();
		setDescricao("Imagem componente");
		setVersao(1);
		setRevisao(13);
	}

	@Override
	public String getNome() {
		return "imagemvisao";
	}

	@Override
	public Icon getImagemIlustrativa() {
		return new ImageIcon(getClass().getResource("/imagem.png"));
	}

	@Override
	public Object getVisaoPreenchimento() {
		return getComponente().getPreenchimento(this);
	}

	@Override
	public Object getVisaoVisualizacao() {
		return getComponente().getVisualizacao(this);
	}
	
	@Override
	public Object getVisualizacaoExemplo(Object componente, Object valorExemplo) {
		Image visualizacao = (Image) componente;
		visualizacao.setContent(((ToolkitImage) new ImageIcon(getClass().getResource("/no_image.png")).getImage()).getBufferedImage());
		return visualizacao;
	}

	@Override
	public boolean isEntradaValida(Object valor) {
		//TODO ver como verificar o tipo da imagem.
		return true;
	}

	@Override
	public String getEventoMascara() {
		return null;
	}

	@Override
	public String getNomeTipoMembroModelo() {
		return "imagem";
	}

	@Override
	public Object getValorVisualizacao(Object componente) {
		return this.componente.getValor(componente);
	}

}
