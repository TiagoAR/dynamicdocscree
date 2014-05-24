package br.ueg.unucet.quid.servicosquid;

import br.ueg.unucet.quid.dominios.ArtefatoPreenchido;
import br.ueg.unucet.quid.excessoes.QuidExcessao;
import br.ueg.unucet.quid.extensao.implementacoes.ServicoPersistencia;


public class ServicoPersistenciaArquivo extends ServicoPersistencia {
	
	public static final String NOME_SERVICO = "SERVICO_PERSITENCIA_ARQUIVO";
	
	public ServicoPersistenciaArquivo() {
		super();
	}

	//@Override
	protected boolean salvarMembros(ArtefatoPreenchido artefatoPreenchido) throws QuidExcessao{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getNome() {
		return NOME_SERVICO;
	}

	@Override
	public String getDescricao() {
		return "Serviço que efetua Persistência de Artefato Preenchido em Arquivo";
	}

	@Override
	protected boolean salvarMembros() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void inicializacaoConstrutor() {
		// TODO Auto-generated method stub
		
	}

}
