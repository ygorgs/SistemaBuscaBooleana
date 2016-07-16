package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pojo.Documento;

/**
 * Estrutura de Indices responsável por armazenar os termos contidos nos documentos
 * 
 * @author Ygor Santos
 */
public class EstruturaDeDados {
	
	/**
	 * HashMap contendo os termos e os documentos que eles aparecem
	 */
	HashMap<String, List<Integer>> map;
	
	/**
	 * Construtor
	 */
	public EstruturaDeDados(){
		map = new HashMap<String, List<Integer>>();
	}
	
	/**
	 * Analisa um documento a procura de termos para adicionar na HashTable
	 */
	public void add(Documento doc){
		getTermos(doc.getTitulo(), doc.getNumero());
		getTermos(doc.getTexto(), doc.getNumero());
	}
	
	/**
	 * Recupera os termos de uma cadeia de string e os adiciona na HashTable
	 * @param texto
	 * @param numero
	 */
	private void getTermos(String texto, int numero) {
		String[] lista = texto.split(" ");
		List<Integer> documentos;
		for (String termo : lista) {
			if(!map.containsKey(termo)){
				documentos = new ArrayList<Integer>();
				documentos.add(numero);
				map.put(termo, documentos);
			} else {
				if(!map.get(termo).contains(numero)){
					map.get(termo).add(numero);
				}
			}
		}
	}
	
	/**
	 * Recupera o mapa de termos
	 * 
	 * @return HashTable contendo os temos.
	 */
	public HashMap<String, List<Integer>> getMap(){
		return map;
	}
	
	/**
	 * Realiza a busca de um termo na estrutura de indices e retorna os documentos que contém o termo em questão
	 * (ou uma lista vazia se o termo não existir no mapa).
	 * 
	 * @param termo
	 * 			Termo a ser buscado
	 * @return Lista contendo os documentos que o termo aparece
	 */
	public List<Integer> buscar(String termo){
		if(map.containsKey(termo)){
			return map.get(termo);
		}
		return new ArrayList<Integer>();
	}
	
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
