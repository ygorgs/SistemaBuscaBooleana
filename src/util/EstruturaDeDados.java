package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pojo.Documento;
import pojo.ListaDocumentos;

/**
 * Estrutura de Indices responsável por armazenar os termos contidos nos documentos
 * 
 * @author Ygor Santos
 */
public class EstruturaDeDados {
	
	/**
	 * HashMap contendo os termos e os documentos que eles aparecem
	 */
	HashMap<String, ListaDocumentos> map;
	
	/**
	 * Construtor
	 */
	public EstruturaDeDados(){
		map = new HashMap<String, ListaDocumentos>();
	}
	
	/**
	 * Analisa um documento a procura de termos para adicionar na HashTable
	 */
	public void add(Documento doc){
		getTermos(doc.getTitulo(), doc.getNumero());
		getTermos(doc.getTexto(), doc.getNumero());
		calcularIDFs();
	}
	
	/**
	 * Calcula os IDF's de todos os termos
	 */
	private void calcularIDFs() {
		for (ListaDocumentos list : map.values()) {
			list.calcularIDF();
		}
	}

	/**
	 * Recupera os termos de uma cadeia de string e os adiciona na HashTable
	 * @param texto
	 * @param numero
	 */
	private void getTermos(String texto, int numero) {
		String[] lista = texto.split(" ");
		ListaDocumentos documentos;
		for (String termo : lista) {
			if(!map.containsKey(termo)){
				documentos = new ListaDocumentos();
				documentos.addDocumento(numero);
				map.put(termo, documentos);
			} else {
				map.get(termo).addDocumento(numero);
			}
		}
	}
	
	/**
	 * Recupera o mapa de termos
	 * 
	 * @return HashTable contendo os temos.
	 */
	public HashMap<String, ListaDocumentos> getMap(){
		return map;
	}
	
	/**
	 * Realiza a busca de um termo na estrutura de indices e retorna os documentos que contém o termo em questão
	 * (ou uma lista vazia se o termo não existir no mapa).
	 * 
	 * @param termos
	 * 			Termo a ser buscado
	 * @return Lista contendo os documentos que o termo aparece
	 */
	public HashMap<Integer, Double> buscar(String[] termos){
		return bm25(termos);
	};
	
	private HashMap<Integer, Double> bm25(String[] termos) {
		Double funcao;
		HashMap<Integer,Double> resultado = new HashMap<Integer, Double>();
		for (String palavra : termos) {
			ListaDocumentos lista = map.get(palavra);
			for (Integer documento : lista.getDocumentos()) {
				funcao = (lista.getIDF() * lista.getTF(documento));
				if(resultado.containsKey(documento)){
					funcao += resultado.get(documento);
				}	
				resultado.put(documento, funcao);
			}
		}
		return resultado;
	}

	/**
	 * Escreve em um arquivo a lista de termos presentes no dicionario
	 */
	public void gravarArquivo(){
		String path = System.getProperty("user.dir") + "\\data\\indice.txt";
		try {
			FileWriter arq = new FileWriter(path);
			PrintWriter gravarArq = new PrintWriter(arq);
			
			gravarArq.printf("+--Resultado--+%n");
			for (String key : map.keySet()) {
				gravarArq.printf("| %s: %s |%n", key, map.get(key).toString());
			}
			gravarArq.printf("+-------------+%n");
			 
		    arq.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
