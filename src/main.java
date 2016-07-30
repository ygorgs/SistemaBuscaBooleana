import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
		//TODO deixar assim por enquanto
//		estrutura.gravarArquivo();
	}
	
	/**
	 * Método responsável por realizar a busca, o usuário deve digitar o termo que deseja buscar
	 * e selecionar qual algoritmo a aplicação deverá usar.
	 */
	private static void realizarBusca() {
		String termo;
		
		System.out.print("\nDigite a Busca: ");
		termo = input.nextLine();
		String[] listDeTermos = getListaDosTermos(termo);
		
		//resultado da busca dos termos
		HashMap<Integer, Double> resultado = buscar(listDeTermos);
		
		// ordena a lista de documentos obitida
		List<Integer> listaDeDocumentos = ordernar(resultado);
		
		// Imprime o resultado obtido
		imprimir(listaDeDocumentos);
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
	 * @return 
	 */
	private static HashMap<Integer, Double> buscar(String[] listaDeTermos) {
		return estrutura.buscar(listaDeTermos);
	};	
	
	private static List<Integer> ordernar(HashMap<Integer, Double> resultado) {
		List<Integer> lista = new ArrayList<Integer>();
		List<Double> funcoes = new ArrayList<Double>();
		funcoes.addAll(resultado.values());
		Collections.sort(funcoes);
		for (int i = funcoes.size() - 1; i > funcoes.size() - 6; i--) {
			for (Integer doc : resultado.keySet()) {
				if(resultado.get(doc).equals(funcoes.get(i)) && !lista.contains(doc)){
					lista.add(doc);
					break;
				}
			}
		}
		return lista;
	}
	
	private static void imprimir(List<Integer> listaDeDocumentos) {
		if(listaDeDocumentos.size() == 0){
			System.out.println("Nenhum Documento Encontrado");
		} else {
			System.out.println("Ranking dos Documentos: ");
			for (int i = 0; i < listaDeDocumentos.size(); i++) {
				System.out.println(i+1  + "º - " + listaDeDocumentos.get(i));
			}
		}
	}
}
