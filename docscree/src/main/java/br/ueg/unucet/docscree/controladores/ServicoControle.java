package br.ueg.unucet.docscree.controladores;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import br.ueg.unucet.docscree.utilitarios.enumerador.TipoMensagem;
import br.ueg.unucet.docscree.visao.compositor.ServicoCompositor;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.enums.TipoErroEnum;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.extensao.interfaces.IServico;
import br.ueg.unucet.quid.gerenciadorservico.ioc.ContextoServicos;

/**
 * Controlador específico do Serviço, faz upload de Serviços
 * solicita a execução de um serviço para o framework
 * @author Tiago
 *
 */
///TODO Colocar no Spring
@SuppressWarnings("unchecked")
public class ServicoControle extends SuperControle {
	
	/**
	 * Método que mapeia arquivo jar do Serviço ao framework
	 * 
	 * @return boolean se Serviço foi mapeado
	 */
	public boolean acaoMapearServico() {
		Map<File, String> servicos = (Map<File, String>) getMapaAtributos().get("arquivos");
		if (!servicos.isEmpty()) {
			File[] files = new File[servicos.size()];
			int i = 0;
			for (Iterator<File> iterator = servicos.keySet().iterator(); iterator.hasNext();) {
				File file = (File) iterator.next();
				files[i] = file;
				i++;
			}
			Retorno<File, String> mapearArquivosServico = super.getFramework().mapearArquivosServicos(files);
			((ServicoCompositor)super.getVisao()).setArquivos(mapearArquivosServico.getParametros());
			return true;
		} else {
			super.getMensagens().setTipoMensagem(TipoMensagem.ERRO);
			super.getMensagens().getListaMensagens().add("É necessário selecionar Arquivos para mapeá-los");
		}
		return false;
	}
	
	///TODO JAVADOC
	@SuppressWarnings({"rawtypes" })
	//TODO colocar em outra classe ou iniciar a construção do gerenciador de serviços
	public Retorno<Object, Object> executaServico(String nomeServico, Integer versao, Integer revisao,Collection<IParametro<?>> parametros,ContextoServicos contextoServicos) {
		Retorno<Object, Object> retorno = new Retorno<Object, Object>();
		try {
//			Retorno<String,IServico> retornoServico = super.getFramework().executaServico(nomeServico,versao,revisao,parametros,contextoServicos); 
//			IServico servico = (IServico) retornoServico.getParametros().get(Retorno.PARAMETRO_NOVA_INSTANCIA); 
//			servico.setListaParametros(parametros);
//			Collection<IParametro> executaAcao = servico.executaAcao();
//			retorno.setSucesso(true);
//			retorno.adicionarParametro(Retorno.PARAMETRO_LISTA_PARAMETRO_SERVICO, executaAcao);
		} catch (Exception e) {
			retorno.setSucesso(false);
			retorno.setErro(e);
			retorno.setMensagem("Não foi possível encontrar a classe do Serviço");
			retorno.setTipoErro(TipoErroEnum.ERRO_FATAL);
		} 
		return retorno;
	}

}
