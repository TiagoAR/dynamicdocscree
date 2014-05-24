package br.ueg.unucet.quid.extensao.implementacoes;

import java.util.Collection;

import br.ueg.unucet.quid.extensao.implementacoes.SuperServico;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.extensao.interfaces.IServico;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo;
//import org.springframework.transaction.annotation.Transactional;

public abstract class ServicoPersistencia extends SuperServico {
	
	public static final String PARAMETRO_VERSAO = "VERSAO";
	public static final String PARAMETRO_REVISAO = "REVISAO";
	public static final String PARAMETRO_ID_ARTEFATO_PREENCHIDO = "ID_ARTEFATO_PREENCHIDO";
	
	//private IArtefatoPreenchidoControle<ArtefatoPreenchido, Long> artefatoPreenchidoControle;
	
	public ServicoPersistencia() {
		super();
	}

	@Override
	public ITipoMembroModelo getTipoMembroModelo() {
		return null;
	}

	@Override
	public void setTipoMembroModelo(ITipoMembroModelo tipoMembroModelo) {
	}

	@Override
	public String getNomeTipoMembroModelo() {
		return null;
	}

	@Override
	public Integer getVersaoTipoMembroModelo() {
		return null;
	}

	@Override
	public boolean isListaParametrosValidos() {
		return true;
	}

	@Override
	public boolean isListaParametrosCompativeis() {
		return true;
	}

	@Override
	public Collection<String> getNomesParametrosInvalidos() {
		return null;
	}

	@Override
	public IServico getAnterior() {
		return null;
	}

	@Override
	public IServico getProximo() {
		return null;
	}

	@Override
	public void setAnterior(IServico servico) {
	}

	@Override
	public void setProximo(IServico servico) {
	}

	@Override
	public boolean isAnteriorObrigatorio() {
		return false;
	}

	@Override
	public void setAnteriroObrigatorio(boolean obrigatorio) {
	}

	@Override
	public Integer getVersao() {
		return 0;
	}

	@Override
	public Integer getRevisao() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.servicosquid.SuperServico#efetuaAcao()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected Collection<IParametro> efetuaAcao() throws Exception {
		
		/*
		ArtefatoPreenchido artefatoPreenchido = new ArtefatoPreenchido();
		artefatoPreenchido.setCodigo(getParametroIdArtefatoPreenchido());
		//definir como se recebera o artefadoModelo
		//na verdade precisaria de um parametro de ArtefatoModelos
		//colocar um getArtefatoModelo ja proprio.
		artefatoPreenchido.setArtefato(getArtefatoModelo().getCodigo());
		artefatoPreenchido.setDescricao(getArtefatoModelo().getDescricao());
		artefatoPreenchido.setModelo(getProjetoEscolhido().getModelo().getCodigo());
		artefatoPreenchido.setNome(getArtefatoModelo().getNome());
		artefatoPreenchido.setRevisao(getParametroRevisao());
		artefatoPreenchido.setUsuario(getUsuario().getCodigo());
		artefatoPreenchido.setVersao(getParametroVersao());
		artefatoPreenchido.setValoresArtefatos(new ArrayList<ValoresArtefato>());
		if (artefatoPreenchido.getCodigo() == null || artefatoPreenchido.getCodigo().equals(Long.valueOf(0))) {
			ArtefatoPreenchido artefatoPesquisar = new ArtefatoPreenchido();
			artefatoPesquisar.setArtefato(artefatoPreenchido.getArtefato());
			artefatoPesquisar.setModelo(artefatoPreenchido.getModelo());
			Collection<ArtefatoPreenchido> respostaPesquisa = getArtefatoPreenchidoControle().pesquisarArtefatoPreenchido(artefatoPesquisar);
	 		if (!respostaPesquisa.isEmpty()) {
				Integer novaVersao = 0;
				for (ArtefatoPreenchido versaoSalva : respostaPesquisa) {
					if (versaoSalva.getVersao().compareTo(novaVersao) > 0) {
						novaVersao = versaoSalva.getVersao();
					}
				}
				artefatoPreenchido.setVersao(novaVersao + 1);
			}
			getArtefatoPreenchidoControle().inserir(artefatoPreenchido);
		} else {
			getArtefatoPreenchidoControle().alterar(artefatoPreenchido);
		}
		salvarMembros(artefatoPreenchido);
		return null;
		*/
		salvarMembros();
		return null;
	}
	
	//protected abstract boolean salvarMembros() throws QuidExcessao;
	protected abstract boolean salvarMembros();
	
	public Integer getParametroVersao() {
		Object valor = getParametroPorNome(PARAMETRO_VERSAO).getValor();
		if (valor != null) {
			return Integer.valueOf(String.valueOf(valor));
		}
		return null;
	}
	
	public Integer getParametroRevisao() {
		Object valor = getParametroPorNome(PARAMETRO_REVISAO).getValor();
		if (valor != null) {
			return Integer.valueOf(String.valueOf(valor));
		}
		return null;
	}
	
	public Long getParametroIdArtefatoPreenchido() {
		Object valor = getParametroPorNome(PARAMETRO_ID_ARTEFATO_PREENCHIDO).getValor();
		if (valor != null) {
			return Long.valueOf(String.valueOf(valor));
		}
		return null;
	}

}
