package br.ueg.unucet.quid.gerenciadorservico.ioc;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import br.ueg.unucet.quid.extensao.interfaces.IServico;
import br.ueg.unucet.quid.extensao.utilitarias.LeitoraJarUtil;

public class ContextoServicos extends LeitoraJarUtil {

	List<IServico> servicosExecutados;
	List<IServico> servicosCarregados;
	
	
	//TODO JAVADOC
	public ContextoServicos(URL[] urls) {
		super(urls);
		this.servicosExecutados = new ArrayList<IServico>();
		this.servicosCarregados = new ArrayList<IServico>();
	}

	public void carregarServico(IServico servico, File jar) {
		if(!this.servicosCarregados.contains(servico))
		{
			try{
				
			super.addURL(jar.toURI().toURL());
			Collection<Class<?>> classes = listarClassesJar(new JarFile(jar));
			for (Class<?> classe : classes) {
				loadClass(classe.getName(), false);
				if (classe.isInstance(servico.getClass()))
				{
				 this.servicosCarregados.add(servico);
				}
			}	
			}
			catch(ClassNotFoundException | IOException e){
				
			}
			///
			//criar metodo para pegar a classe do servico dentro do contexto e dar um run
		}
		
	}

	///TODO IR PARA O GENERICO
	private Class<?> getClassePorDiretorio(String diretorio) {
		String nomeClasse = converteDiretorioEmPacote(diretorio);
		Class<?> classe = null;
		try {
			classe = loadClass(nomeClasse, false);
		} catch (ClassNotFoundException e) {}
		return classe;
	}
	
/////COPIAS DOS METODOS DA VERIFICADORA JAR PARA PESQUISA
////TODO UMA SUPERCLASSE COMUM A VERIFICADORAJAR E O GERENCIADOR DE SERVICOS PARA O QUID-EXTENSAO

	/*
	public void addArquivo(URL arquivo) {
		if(!this.listArquivosImportados.contains(arquivo)){
			super.addURL(arquivo);
			this.listArquivosImportados.add(arquivo);
		}
	}
	
	
	public void lerDiretorio(String diretorio) throws MalformedURLException{
		File local = new File(diretorio);
		File jars[] = local.listFiles();
		for(File file :jars){
			addArquivo(file.toURI().toURL());
		}
	}
	
	public Collection<Class<?>> listarClassesDiretorio(String diretorio) throws IOException, ClassNotFoundException{
		File local = new File(diretorio);
		File jars[] = local.listFiles();
		Collection<Class<?>> classes = new ArrayList<Class<?>>();
		for(File file : jars){
			classes.addAll(listarClassesJar(file));
		}
		return classes;
	}
	
	public Collection<Class<?>> listarClassesJar(String arquivo) throws IOException, ClassNotFoundException{
		addArquivo(arquivo);
		JarFile jar = new JarFile(arquivo);	
		return listarClassesJar(jar);
		
	}
	
	public Collection<Class<?>> listarClassesJarAdicionado(JarFile jar) throws ClassNotFoundException{
		return listarClassesJar(jar);
	}
	
	public void addArquivo(String arquivo) throws MalformedURLException{
		addArquivo((new File(arquivo)).toURI().toURL());
	}
	
	
	public Class<?> lerClass(String caminhoClassJar) throws ClassNotFoundException{
		String nomeClass = converteDiretorioEmPacote(caminhoClassJar);
		return loadClass(nomeClass);
	}
	
	
	
	public Collection<Class<?>> listarClassesJar(File arquivo) throws IOException, ClassNotFoundException{
		JarFile jar = new JarFile(arquivo);
		return listarClassesJar(jar);
	}
	
	public Class<?> getClassePrincipalJar(String diretorioArquivo) throws IOException{
		JarFile jar = new JarFile(diretorioArquivo);
		String nomeClasse = getClassePrincipalManifest();
		return getClassePorDiretorio(nomeClasse);
	}


	private String getClassePrincipalManifest() {
		// TODO Auto-generated method stub
		return null;
	}

	
	private Collection<Class<?>> listarClassesJar(JarFile jar) throws ClassNotFoundException{
		Enumeration<JarEntry> arquivos = jar.entries();
		Collection<Class<?>> classes = new ArrayList<Class<?>>();
		while(arquivos.hasMoreElements()){
			JarEntry jarEntry = arquivos.nextElement();
			Class<?> classe = getClassePorDiretorio(jarEntry.getName()); 
			if(classe != null){
				classes.add(classe);
			}
		}
		return classes;
	}
	
	private Class<?> getClassePorDiretorio(String diretorio) {
		String nomeClasse = converteDiretorioEmPacote(diretorio);
		Class<?> classe = null;
		try {
			classe = loadClass(nomeClasse, false);
		} catch (ClassNotFoundException e) {}
		return classe;
	}
	
	public String converteDiretorioEmPacote(String diretorio){
		diretorio = diretorio.replaceAll("/", ".");
		diretorio = diretorio.substring(0, diretorio.length() - 6);
		return diretorio;
	}
*/
	
}
