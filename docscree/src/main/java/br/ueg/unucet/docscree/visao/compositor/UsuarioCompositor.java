package br.ueg.unucet.docscree.visao.compositor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.databind.BindingListModelListModel;
import org.zkoss.zul.Label;
import org.zkoss.zul.SimpleListModel;

import br.ueg.unucet.docscree.anotacao.AtributoVisao;
import br.ueg.unucet.docscree.controladores.UsuarioControle;
import br.ueg.unucet.docscree.interfaces.ILogar;
import br.ueg.unucet.quid.dominios.Usuario;
import br.ueg.unucet.quid.enums.PerfilAcessoEnum;
import br.ueg.unucet.quid.extensao.dominios.Persistivel;
import br.ueg.unucet.quid.extensao.enums.StatusEnum;

/**
 * Classe da visão que representa o caso de uso Manter usuário; Composer do
 * Usuário no ZK.
 * 
 * @author Diego
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
@Scope("session")
public class UsuarioCompositor extends GenericoCompositor<UsuarioControle>
		implements ILogar {

	/**
	 * Default Serial
	 */
	private static final long serialVersionUID = -8506722128070841379L;

	/* Atributos para mapeamento/Inicio dos atributos da entidade */
	/**
	 * Campo nome
	 */
	@AtributoVisao(isCampoEntidade = true, nome = "nome")
	private String fldNome;
	/**
	 * Campo senha
	 */
	@AtributoVisao(isCampoEntidade = true, nome = "senha")
	private String fldSenha = "admin";
	/**
	 * Campo confirmar senha
	 */
	@AtributoVisao(isCampoEntidade = false, nome = "confirmarSenha")
	private String fldConfirmarSenha;
	/**
	 * Campo E-mail
	 */
	@AtributoVisao(isCampoEntidade = true, nome = "email")
	private String fldEmail = "admin";
	/* Fim dos atributos da entidade */
	/**
	 * Campo Status
	 */
	@AtributoVisao(isCampoEntidade = false, nome = "status")
	private Boolean fldStatus = new Boolean(true);
	/**
	 * Campo Perfil de Acesso
	 */
	@AtributoVisao(isCampoEntidade = false, nome = "perfilAcesso")
	private String fldPerfilAcesso;
	
	private Boolean isProprioUsuario = Boolean.FALSE;
	
	private String nomeSenha = "Senha:";
	private String nomeConfSenha = "Confirmar Senha:";

	/* Fim atributos */

	/**
	 * Filtro do usuário para código
	 */
	private String filtroCodigo = "";
	/**
	 * Filtro do usuário para nome
	 */
	private String filtroNome = "";
	/**
	 * Filtro do usuário para e-mail
	 */
	private String filtroEmail = "";
	/**
	 * Filtro do usuário para perfil
	 */
	private String filtroPerfil = "";

	/**
	 * @see GenericoCompositor#getTipoEntidade()
	 */
	@Override
	public Class getTipoEntidade() {
		return Usuario.class;
	}

	/**
	 * @see GenericoCompositor#limparCampos()
	 */
	@Override
	protected void limparCampos() {
		setCodigo(null);
		setFldConfirmarSenha("");
		setFldEmail("");
		setFldNome("");
		setFldPerfilAcesso("");
		setFldSenha("");
		setFldStatus(Boolean.TRUE);
		
		setNomeConfSenha("Confirmar Senha:");
		setNomeSenha("Senha:");
		setIsProprioUsuario(Boolean.FALSE);
		super.binder.loadAll();
	}

	/**
	 * @see GenericoCompositor#limparFiltros()
	 */
	@Override
	protected void limparFiltros() {
		setFiltroCodigo("");
		setFiltroEmail("");
		setFiltroNome("");
		setFiltroPerfil("");

		super.binder.loadAll();
	}

	/**
	 * Atualiza lista de entidade mudando o atributo status da entidade para
	 * inativo
	 * 
	 * @param index
	 *            indíce da entidade
	 */
	@Override
	protected void atualizarEntidadeExcluida(int index) {
		((Usuario) this.getListaEntidade().get(index))
				.setStatus(StatusEnum.INATIVO);
		acaoFiltrar();
		super.binder.loadAll();
	}

	/** 
	 * @see
	 * br.ueg.unucet.docscree.visao.compositor.GenericoCompositor#acaoSalvar()
	 */
	@Override
	public void acaoSalvar() {
		if (getIsProprioUsuario()) {
			try {
				boolean resultado = super.getControle().fazerAcao("editarProprioUsuario",
						(SuperCompositor) this);
				if (resultado) {
				} else {
					super.mostrarMensagem(resultado);
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.acaoSalvar();
		super.getControle().acaoAtualizarUsuarioLogado();
		this.salvarSessaoUsuario(super.getControle().getUsuarioLogado());
		super.binder.loadAll();
		this.forcarAtualizacaoCampos();
	}

	/** 
	 * @see br.ueg.unucet.docscree.visao.compositor.SuperCompositor#forcarAtualizacaoCampos()
	 */
	@Override
	protected void forcarAtualizacaoCampos() {
		((Label) super.getComponent().getFellow("aNomeUsuario"))
				.setValue(getNomeUsuarioLogado());
	}

	/**
	 * @see br.ueg.unucet.docscree.visao.compositor.GenericoCompositor#acaoFiltrar()
	 */
	@Override
	public void acaoFiltrar() {
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		super.binder.saveAll();
		for (Object objeto : super.getListaEntidade()) {
			Usuario usuario = (Usuario) objeto;
			if (usuario.getCodigo().toString().trim().toLowerCase()
					.contains(getFiltroCodigo().trim().toLowerCase())
					&& usuario.getNome().trim().toLowerCase()
							.contains(getFiltroNome().trim().toLowerCase())
					&& usuario.getEmail().trim().toLowerCase()
							.contains(getFiltroEmail().trim().toLowerCase())
					&& usuario.getPerfilAcesso().toString().trim()
							.toLowerCase()
							.contains(getFiltroPerfil().trim().toLowerCase())) {
				if (getExibirInativos()
						|| usuario.getStatus().toString().toLowerCase()
								.equals("ativo"))
					listaUsuarios.add(usuario);
			}
		}
		super.setListaEntidadeModelo(new BindingListModelListModel<Usuario>(
				new SimpleListModel<Usuario>(listaUsuarios)));
		super.binder.loadAll();
	}

	/**
	 * @see br.ueg.unucet.docscree.interfaces.ILogar#acaoLogar()
	 */
	@Override
	public void acaoLogar() {
		setFldConfirmarSenha(null);
		setFldNome(null);
		setFldPerfilAcesso(null);
		setFldStatus(Boolean.TRUE);
		try {
			boolean resultado = super.getControle().fazerAcao("logar",
					(SuperCompositor) this);
			if (resultado) {
				setFldEmail(null);
				setFldSenha(null);
				this.salvarSessaoUsuario(super.getControle().getUsuarioLogado());
				this.redirecionar();
			} else {
				super.mostrarMensagem(resultado);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see ILogar#acaoDeslogar()
	 */
	@Override
	public void acaoDeslogar() {
		Executions.getCurrent().getSession().invalidate();
		Executions.sendRedirect("/login.zul");
	}

	/**
	 * Método que faz a edição do próprio usuário, pega informações através da sessão
	 */
	public void acaoEditarProprioUsuario() {

		if (!Executions.getCurrent().getDesktop().getRequestPath()
				.contains("usuario.zul")) {
			redirecionar();
		}
		super.setEntidade((Persistivel) super.getUsuarioSessao());
		super.getControle().setarEntidadeVisao(this);
		setFldSenha("");
		setFldConfirmarSenha("");
		setNomeConfSenha("Nova Senha:");
		setNomeSenha("Senha Anterior:");
		setIsProprioUsuario(Boolean.TRUE);
		super.binder.loadAll();
	}

	/**
	 * @see br.ueg.unucet.docscree.interfaces.ILogar#salvarSessaoUsuario(Usuario
	 *      usuario)
	 */
	@Override
	public void salvarSessaoUsuario(Usuario usuario) {
		Executions.getCurrent().getSession().setAttribute("usuario", usuario);
	}

	/**
	 * Método que redireciona para a página de login
	 */
	public void redirecionar() {
		Executions.sendRedirect("/pages/usuario.zul");
	}

	/**
	 * Método que retorna os tipos de perfis de acesso existentes no enumerador.
	 * 
	 * @return listaPerfil
	 */
	public List<String> getListaPerfil() {
		ArrayList<String> listaPerfil = new ArrayList<>();
		for (PerfilAcessoEnum perfil : PerfilAcessoEnum.values()) {
			listaPerfil.add(perfil.toString());
		}
		return listaPerfil;
	}

	/**
	 * Método que retorna o nome do usuário logado
	 * 
	 * @return String nome usuário logado
	 */
	public String getNomeUsuarioLogado() {
		try {
			return ((Usuario) Executions.getCurrent().getSession()
					.getAttribute("usuario")).getNome();
		} catch (Exception e) {
			return "Visitante";
		}
	}

	/* GETTERS AND SETTERS */

	/**
	 * @return the fldNome
	 */
	public String getFldNome() {
		return fldNome;
	}

	/**
	 * @param fldNome
	 *            the fldNome to set
	 */
	public void setFldNome(String fldNome) {
		this.fldNome = fldNome;
	}

	/**
	 * @return the fldSenha
	 */
	public String getFldSenha() {
		return fldSenha;
	}

	/**
	 * @param fldSenha
	 *            the fldSenha to set
	 */
	public void setFldSenha(String fldSenha) {
		this.fldSenha = fldSenha;
	}

	/**
	 * @return the fldConfirmarSenha
	 */
	public String getFldConfirmarSenha() {
		return fldConfirmarSenha;
	}

	/**
	 * @param fldConfirmarSenha
	 *            the fldConfirmarSenha to set
	 */
	public void setFldConfirmarSenha(String fldConfirmarSenha) {
		this.fldConfirmarSenha = fldConfirmarSenha;
	}

	/**
	 * @return the fldEmail
	 */
	public String getFldEmail() {
		return fldEmail;
	}

	/**
	 * @param fldEmail
	 *            the fldEmail to set
	 */
	public void setFldEmail(String fldEmail) {
		this.fldEmail = fldEmail;
	}

	/**
	 * @return o(a) fldStatus
	 */
	public Boolean getFldStatus() {
		return fldStatus;
	}

	/**
	 * @param fldStatus
	 *            o(a) fldStatus a ser setado(a)
	 */
	public void setFldStatus(Boolean fldStatus) {
		this.fldStatus = fldStatus;
	}

	/**
	 * @return o(a) fldPerfilAcesso
	 */
	public String getFldPerfilAcesso() {
		return fldPerfilAcesso;
	}

	/**
	 * @param fldPerfilAcesso
	 *            o(a) flsPerfilAcesso a ser setado(a)
	 */
	public void setFldPerfilAcesso(String fldPerfilAcesso) {
		this.fldPerfilAcesso = fldPerfilAcesso;
	}

	/**
	 * @return o(a) filtroCodigo
	 */
	public String getFiltroCodigo() {
		return filtroCodigo;
	}

	/**
	 * @param filtroCodigo
	 *            o(a) filtroCodigo a ser setado(a)
	 */
	public void setFiltroCodigo(String filtroCodigo) {
		this.filtroCodigo = filtroCodigo;
	}

	/**
	 * @return o(a) filtroNome
	 */
	public String getFiltroNome() {
		return filtroNome;
	}

	/**
	 * @param filtroNome
	 *            o(a) filtroNome a ser setado(a)
	 */
	public void setFiltroNome(String filtroNome) {
		this.filtroNome = filtroNome;
	}

	/**
	 * @return o(a) filtroEmail
	 */
	public String getFiltroEmail() {
		return filtroEmail;
	}

	/**
	 * @param filtroEmail
	 *            o(a) filtroEmail a ser setado(a)
	 */
	public void setFiltroEmail(String filtroEmail) {
		this.filtroEmail = filtroEmail;
	}

	/**
	 * @return o(a) filtroPerfil
	 */
	public String getFiltroPerfil() {
		return filtroPerfil;
	}

	/**
	 * @param filtroPerfil
	 *            o(a) filtroPerfil a ser setado(a)
	 */
	public void setFiltroPerfil(String filtroPerfil) {
		this.filtroPerfil = filtroPerfil;
	}

	/**
	 * @return Boolean o(a) isProprioUsuario
	 */
	public Boolean getIsProprioUsuario() {
		return isProprioUsuario;
	}

	/**
	 * @param isProprioUsuario o(a) isProprioUsuario a ser setado(a)
	 */
	public void setIsProprioUsuario(Boolean isProprioUsuario) {
		this.isProprioUsuario = isProprioUsuario;
	}

	/**
	 * @return String o(a) nomeSenha
	 */
	public String getNomeSenha() {
		return nomeSenha;
	}

	/**
	 * @param nomeSenha o(a) nomeSenha a ser setado(a)
	 */
	public void setNomeSenha(String nomeSenha) {
		this.nomeSenha = nomeSenha;
	}

	/**
	 * @return String o(a) nomeConfSenha
	 */
	public String getNomeConfSenha() {
		return nomeConfSenha;
	}

	/**
	 * @param nomeConfSenha o(a) nomeConfSenha a ser setado(a)
	 */
	public void setNomeConfSenha(String nomeConfSenha) {
		this.nomeConfSenha = nomeConfSenha;
	}
}
