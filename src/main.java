import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import pojo.Documento;
import util.EstruturaDeDados;
import util.LeitorDeArquivo;

/**
 * Classe contendo o main da aplicação
 * 
 * @author Ygor Santos
 *
 */
public class main {
	
	public static Scanner input = new Scanner(System.in);  // Reading from System.in
	
	public static EstruturaDeDados estrutura;
	public static LeitorDeArquivo leitor;
	
	/**
	 * Fluxo principal da aplicação
	 */
	public static void main(String[] args){
		//carregar dados na aplicação
		inicializar();
		
		System.out.println("\nAtividade 01 - CheckPoint 1 - Índice invertido e Busca Booleana");
		
		String opcao;
		do {
			System.out.println();
			System.out.println("1 - Realizar Busca");
			System.out.println("2 - Sair");
			System.out.print("Escolha uma opção: ");
			opcao = input.nextLine();
			
			switch (opcao) {
			case "1":
				realizarBusca();
				break;
			default:
				break;
			}
		} while (!opcao.equals("2"));
	}
	
	/**
	 * Operações necessárias para a inicialização (ler o arquivo e cria a estrutura do índice)
	 */
	private static void inicializar() {
		System.out.println("Lendo arquivo...");
		leitor = new LeitorDeArquivo();
		List<Documento> listaDocumentos = leitor.ler();
		
		System.out.println("Criando estrutura de dados...");
		estrutura = new EstruturaDeDados();
		for (Documento documento : listaDocumentos) {
			estrutura.add(documento);
		}
		estrutura.gravarArquivo();
	}
	
	/**
	 * Método responsável por realizar a busca, o usuário deve digitar o termo que deseja buscar
	 * e selecionar qual algoritmo a aplicação deverá usar.
	 */
	private static void realizarBusca() {
		String termo;
		String algoritmo;
		
		System.out.print("\nDigite a Busca: ");
		termo = input.nextLine();
		String[] listDeTermos = getListaDosTermos(termo);
		
		
		System.out.println("1 - Algoritmo AND");
		System.out.println("2 - Algoritmo OR");
		System.out.print("Escolha um Algoritmo: ");
		algoritmo = input.nextLine();
		
		switch (algoritmo) {
		case "1":
			buscarAND(listDeTermos);
			break;
		case "2":
			buscarOR(listDeTermos);
			break;
		default:
			break;
		}
	}
	
	/**
	 * deixa o(s) termo(s) no formato adequado para a aplicação fazer a busca
	 * 
	 * @param termo 
	 * 			Linha contendo os termos que o usuário digitou
	 * @return Array com os termos separados
	 */
	private static String[] getListaDosTermos(String termo) {
		termo = leitor.limpar(termo);
		String[] lista = termo.split(" ");
		return lista;
	}

	/**
	 * Realiza a busca dos termos usando o algoritmo AND
	 * 
	 * @param listaDeTermos
	 * 			Lista contendo os termos que serão buscados.
	 */
	private static void buscarAND(String[] listaDeTermos) {
		List<Integer> resultado = new ArrayList<Integer>();
		for (String termo : listaDeTermos) {
			if(!termo.equals("")){
				mergeAND(resultado, estrutura.buscar(termo));
			}
		}
		System.out.println("\nDocumentos que contém todos termos: ");
		System.out.println(resultado);
	};
	
	/**
	 * Realiza uma operação AND entre duas listas de documentos.
	 */
	private static void mergeAND(List<Integer> resultado, List<Integer> busca) {
		if(resultado.isEmpty()){
			resultado.addAll(busca);
		} else {
			List<Integer> listaAux = new ArrayList<Integer>();
			for (Integer documento : busca) {
				if(resultado.contains(documento)){
					listaAux.add(documento);
				}
			}
			resultado.clear();
			resultado.addAll(listaAux);
		}
	}

	/**
	 * Realiza a busca dos termos usando o algoritmo OR
	 * 
	 * @param listaDeTermos
	 * 			Lista contendo os termos que serão buscados.
	 */
	private static void buscarOR(String[] listaDeTermos) {
		List<Integer> resultado = new ArrayList<Integer>();
		for (String termo : listaDeTermos) {
			if(!termo.equals("")){
				mergeOr(resultado, estrutura.buscar(termo));
			}
		}
		Collections.sort(resultado);
		System.out.println("\nDocumentos que contém, ao menos, algum dos termos: ");
		System.out.println(resultado);
	}
	
	/**
	 * Realiza uma operação OR entre duas listas de documentos.
	 */
	private static void mergeOr(List<Integer> resultado, List<Integer> busca) {
		for (Integer doc : busca) {
			if(!resultado.contains(doc)){
				resultado.add(doc);
			}
		}
	}
}
