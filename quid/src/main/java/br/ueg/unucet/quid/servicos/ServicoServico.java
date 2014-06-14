package br.ueg.unucet.quid.servicos;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.dominios.Servico;
import br.ueg.unucet.quid.enums.TipoErroEnum;
import br.ueg.unucet.quid.excessoes.ServicoExcessao;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.extensao.interfaces.IServico;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo;
import br.ueg.unucet.quid.gerenciadorservico.ioc.ContextoServicos;
import br.ueg.unucet.quid.interfaces.IServicoControle;
import br.ueg.unucet.quid.interfaces.IServicoServico;

@Service("ServicoServico")
public class ServicoServico extends GenericoServico<Servico> implements IServicoServico<Servico>{

	@Autowired
	private IServicoControle<Servico, Long> servicoControle;
	
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IServicoServico#mapearServicos(java.io.File[])
	 */
	@Override
	public Retorno<File, String> mapearServicos(File[] arquivos) {
		Retorno<File, String> retorno = new Retorno<File, String>();
		try {
			Map<File, String> execucao = servicoControle.mapearServicos(arquivos);
			retorno.setSucesso(true);
			retorno.setParametros(execucao);
		} catch (ServicoExcessao e) {
			retorno.setErro(e);
			retorno.setMensagem(e.getMessage());
			retorno.setSucesso(false);
			retorno.setTipoErro(TipoErroEnum.ERRO_FATAL);
		}
		return retorno;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IServicoServico#removerServico(br.ueg.unucet.quid.extensao.interfaces.IServico)
	 */
	@Override
	public Retorno<Object, Object> removerServico(IServico iServico) {
		Retorno<Object, Object> retorno = new Retorno<Object, Object>();
		try {
			servicoControle.remover(iServico);
			
			retorno.setSucesso(true);
		} catch (ServicoExcessao e) {
			retorno.setErro(e);
			retorno.setMensagem(e.getMessage());
			retorno.setSucesso(false);
			retorno.setTipoErro(TipoErroEnum.ERRO_SIMPLES);
		}
		return retorno;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IServicoServico#listaServicos()
	 */
	@Override
	public Retorno<String, Collection<IServico>> listaServicos() {
		Collection<IServico> listaServico = servicoControle.listaServicosCadastrados();
		Retorno<String, Collection<IServico>> retorno = new Retorno<String, Collection<IServico>>();
		retorno.adicionarParametro(Retorno.PARAMERTO_LISTA, listaServico);
		if(listaServico.isEmpty()){
			retorno.setSucesso(false);
			retorno.setTipoErro(TipoErroEnum.INFORMATIVO);
			retorno.setMensagem(propertiesMensagensUtil.getValor("lista_vazia"));
		}else{
			retorno.setSucesso(true);
		}
		return retorno;
	}

	public IServicoControle<Servico, Long> getServicoControle() {
		return servicoControle;
	}

	public void setServicoControle(IServicoControle<Servico, Long> servicoControle) {
		this.servicoControle = servicoControle;
	}
	
	///TODO Metodo nao util
	public void carregaServico(String nomeServico, Integer versao,
			Integer revisao, ContextoServicos contextoServicos) throws ServicoExcessao, MalformedURLException {
		getServicoControle().carregaServico(nomeServico, versao, revisao, contextoServicos);
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IServicoServico#listaParametrosServico(br.ueg.unucet.quid.extensao.interfaces.IServico)
	 */
	@Override
	public Retorno<String, Collection<IParametro<?>>> listaParametrosServico(IServico servico) {
		Retorno<String, Collection<IParametro<?>>> retorno = new Retorno<String, Collection<IParametro<?>>>();
		retorno.setSucesso(true);
		Collection<IParametro<?>> parametros =  new ArrayList<IParametro<?>>();
		if(servico.getListaParametros() != null){
			parametros.addAll(servico.getListaParametros());
		}
		if(servico.getTipoMembroModelo() != null &&  servico.getTipoMembroModelo().getListaParametros() != null){
			parametros.addAll(servico.getTipoMembroModelo().getListaParametros());
		}
		retorno.adicionarParametro(Retorno.PARAMERTO_LISTA, parametros);
		return retorno;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IServicoServico#verificarParametrosServico(br.ueg.unucet.quid.extensao.interfaces.IServico)
	 */
	@Override
	public Retorno<String, Collection<String>> verificarParametrosServico(IServico servico) {
		Retorno<String, Collection<String>> retorno = new Retorno<String, Collection<String>>();
		if(!servico.isListaParametrosValidos()){
			retorno.setSucesso(false);
			retorno.adicionarParametro(Retorno.PARAMETRO_LISTA_PARAMETRO_SERVICO, servico.getNomesParametrosInvalidos());
		}
		if(servico.getTipoMembroModelo() != null){
			if(!servico.getTipoMembroModelo().isParametrosValidos()){
				retorno.setSucesso(false);
				retorno.adicionarParametro(Retorno.PARAMETRO_LISTA_PARAMETROS_TIPOMEMBRO, servico.getTipoMembroModelo().getNomesParametrosInvalidos());
			}
		}
		if(!retorno.isSucesso()){
			retorno.setMensagem(propertiesMensagensUtil.getValor("parametros_valor_invalidos"));
		}
		return retorno;
	}

	@Override
	public Retorno<String, Object> executaServico(String nomeServico,
			Integer versao, Integer revisao,
			Collection<IParametro<?>> parametros,
			ContextoServicos contextoServicos) throws ServicoExcessao {
		return getServicoControle().executaServico(nomeServico,versao, revisao,parametros,	contextoServicos);
	}
	
	/*
	//TODO JAVADOC
	//TODO VALIDAcaO SE O SERVIO NaO FOI ENCONTRADO COM MENSAGEM
	//TODO INSTANCIA ORIGINAL OU MAIS DE UMA INSTANCIA?
	public Retorno<String, IServico> getInstanciaServico(String nome, Integer versao) {
		Retorno<String, IServico> retorno = new Retorno<String, IServico>();
		IServico servico = this.servicoControle.getInstanciaServico(nome,versao);
		if (servico != null)
		{
			retorno.adicionarParametro(Retorno.PARAMETRO_NOVA_INSTANCIA, servico);
			retorno.setSucesso(true);
		}
		else
		{
			retorno.setSucesso(false);
			retorno.setMensagem("SERVIçO NãO ENCONTRADO");
		}
		return retorno;
	}
	*/
}
