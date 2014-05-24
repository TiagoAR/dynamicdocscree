package br.ueg.unucet.docscree.controladores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.ueg.unucet.docscree.interfaces.ICRUDControle;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.extensao.dominios.Persistivel;

/**
 * Controle Genérico, extende SuperControle e contém operações básicas de todos os controladores persistíveis
 * 
 * @author Diego
 *
 * @param <E> entidade persistivel
 */
@SuppressWarnings({"unchecked"})
public abstract class GenericoControle<E extends Persistivel> extends SuperControle implements ICRUDControle {
	
	/**
	 * Contém a lista de entidade, setado quando chamado a ação listar.
	 */
	protected List<?> lista;
	
	/**
	 * Método que retorna a entidade do mapeador de atributos.
	 * 
	 * @return E entidade
	 */
	protected E getEntidade() {
		return (E) super.getMapaAtributos().get("entidade");
	}

	/**
	 * Método que adiciona as mensagens de erro através do retorno da ação
	 * 
	 * @param retorno Retorno da ação
	 */
	protected void montarMensagemErro(Retorno<String, Collection<String>> retorno) {
		String mensagemErro = retorno.getMensagem();
		Collection<String> colecao = retorno.getParametros().get(
				Retorno.PARAMETRO_NAO_INFORMADO_INVALIDO);
		if (colecao != null && !colecao.isEmpty()) {
			Iterator<String> iterador = colecao.iterator();
			if (iterador.hasNext()) {
				mensagemErro += ": " + iterador.next();
			}
			while (iterador.hasNext()) {
				String campoNaoInformado = (String) iterador.next();
				mensagemErro += ", " + campoNaoInformado;
			}
		}
		super.mensagens.getListaMensagens().add(mensagemErro);
	}

	/**
	 * @return o(a) lista
	 */
	public List<?> getLista() {
		return lista;
	}

	/**
	 * @param lista o(a) lista a ser setado(a)
	 */
	public void setLista(List<?> lista) {
		this.lista = lista;
	}
	
	/**
	 * @see ICRUDControle#acaoListar()
	 */
	@Override
	public boolean acaoListar() {
		Retorno<String, Collection<E>> retorno = executarListagem();
		if (retorno.isSucesso()) {
			Collection<E> listaEntidades = retorno.getParametros().get(
					Retorno.PARAMERTO_LISTA);
			this.setLista(new ArrayList<E>(listaEntidades));
			return true;
		} else {
			super.mensagens.getListaMensagens().add(retorno.getMensagem());
			return false;
		}
	}
	
	/**
	 * Método que executa a listagem específica para cada caso de uso
	 * 
	 * @return Retorno retorno do framework
	 */
	protected abstract Retorno<String, Collection<E>> executarListagem();
	
	/**
	 * Método que monta mensagem de erro de permissão de acesso a ação
	 * 
	 * @param tipoUsuario o perfil de usuário que está executando a ação
	 */
	protected void montarMensagemErroPermissao(String tipoUsuario) {
		super.mensagens.getListaMensagens().add("O tipo de usuário "+tipoUsuario+" não tem permissão para executar a ação!");
	}
	
	/**
	 * Método que monta o retorno e a mensagem de erro caso ocorra um
	 * 
	 * @param retorno 
	 * @return boolean se ação foi executada com sucesso
	 */
	protected boolean montarRetorno(Retorno<String, Collection<String>> retorno) {
		if (retorno.isSucesso()) {
			return true;
		} else {
			montarMensagemErro(retorno);
			return false;
		}
	}
}
