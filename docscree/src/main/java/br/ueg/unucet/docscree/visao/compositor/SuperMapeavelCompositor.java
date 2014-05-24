package br.ueg.unucet.docscree.visao.compositor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;

import br.ueg.unucet.docscree.anotacao.AtributoVisao;
import br.ueg.unucet.docscree.controladores.SuperControle;
import br.ueg.unucet.docscree.modelo.ArquivoCarregado;
import br.ueg.unucet.docscree.modelo.Mensagens;
import br.ueg.unucet.docscree.utilitarios.UploadArquivo;

// TODO JAVADOC
/**
 * Compositor genérico para upload de arquivos para o framework QUID
 * inicialmente feita para mapeamento de TipoMembro e Serviços
 * 
 * @author Tiago
 *
 */
public class SuperMapeavelCompositor<E extends SuperControle > extends	SuperCompositor<E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String pendente = "Pendente!";
	
	/**
	 * Arquivo selecionado para ser removido
	 */
	private ArquivoCarregado arquivoSelecionado;

	/**
	 * Mapa de arquivos a serem salvos no framework
	 */
	@AtributoVisao(isCampoEntidade= false, nome="arquivos")
	private Map<File, String> arquivos = new HashMap<File, String>();

	
	/**
	 * Efetua o upload do arquivo para o servidor
	 * @param event
	 */
	public void uploadArquivo(UploadEvent event) {
		try {
			String pasta = UploadArquivo.criarDiretorio(((ServletContext)Executions.getCurrent().getDesktop().getWebApp().getServletContext()).getRealPath("/"), "temp");
			Media[] medias = event.getMedias();
			boolean resultado = true;
			super.getControle().setMensagens(new Mensagens());
			for (Media media : medias) {
				String mediaName = media.getName();
				if (mediaName.substring(mediaName.length() - 3, mediaName.length()).equalsIgnoreCase("jar")) {
					String arquivo = UploadArquivo.adicionarBarraFinalString(pasta)
							+  media.getName();
					if (arquivo != null) {
						FileOutputStream fos = new FileOutputStream(arquivo);
						fos.write(media.getByteData());
						fos.flush();
						fos.close();
						getArquivos().put(new File(arquivo), pendente);
					}
				} else {
					resultado = false;
					super.getControle().getMensagens().getListaMensagens().add("O " + mediaName + " não é um arquivo .jar");
				}
			}
			super.mostrarMensagem(resultado);
			super.binder.loadAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Cria lista dos arquivos a serem mapeados
	 * @return arquivoCarregados lista dos ArquivosCarregados
	 */
	public List<ArquivoCarregado> getArquivosCarregados() {
		List<ArquivoCarregado> arquivoCarregados = new ArrayList<ArquivoCarregado>();
		for (File arquivo : getArquivos().keySet()) {
			ArquivoCarregado arquivoCarregado = new ArquivoCarregado();
			arquivoCarregado.setFile(arquivo);
			arquivoCarregado.setNomeArquivo(arquivo.getName());
			arquivoCarregado.setSituacao(getArquivos().get(arquivo));
			arquivoCarregados.add(arquivoCarregado);
		}
		return arquivoCarregados;
	}
	
	/**
	 * Cancela ação de mapeamento dos Arquivos
	 */
	public void acaoCancelar() {
		setArquivos(new HashMap<File, String>());
		super.binder.loadAll();
	}

	
	public Map<File, String> getArquivos() {
		return arquivos;
	}

	public void setArquivos(Map<File, String> arquivos) {
		this.arquivos = arquivos;
	}
	
	/**
	 * Remove o arquivo do Arquivo selecionado
	 */
	public void removerArquivo() {
		try {
			super.binder.saveAll();
			getArquivos().remove(getArquivoSelecionado().getFile());
			super.binder.loadAll();
		} catch (Exception e) {
		}
	}
	
	/**
	 * @return ArquivoCarregado o(a) arquivoSelecionado
	 */
	public ArquivoCarregado getArquivoSelecionado() {
		return arquivoSelecionado;
	}

	/**
	 * @param arquivoSelecionado o(a) arquivoSelecionado a ser setado(a)
	 */
	public void setArquivoSelecionado(ArquivoCarregado arquivoSelecionado) {
		this.arquivoSelecionado = arquivoSelecionado;
	}

}
