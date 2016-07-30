package pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe responsável por armazenar a lista de documentos junto com o indice de frequencia
 * e calcular o IDF
 * 
 * @author Ygor Santos
 *
 */
public class ListaDocumentos {
	
	/**
	 * Relação documento:TF
	 */
	private HashMap<Integer, Integer> documentos;
	
	/**
	 * inverse document frequency
	 */
	private Double IDF;
	
	/**
	 * Número de documentos da coleção
	 */
	public static final Integer M = 1000;
	
	/**
	 * Construtor
	 */
	public ListaDocumentos(){
		documentos = new HashMap<Integer, Integer>();
	}
	
	/**
	 * Recupera o indice de frêquencia do termo em determinado documento
	 * 
	 * @param documento
	 * 			documento que se deseja obter o TF
	 * @return
	 * 			TF do termo no documento
	 */
	public Integer getTF(Integer documento){
		return documentos.get(documento);
	}

	/**
	 * Retorna a lista de documentos onde o termo pode ser encontrado
	 * 
	 * @return
	 * 		Lista de Documentos
	 */
	public List<Integer> getDocumentos() {
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(documentos.keySet());
		return list;
	}
	
	/**
	 * Adiciona um documento a lista de documentos do termo
	 * 
	 * @param docId
	 * 			Indice do documento
	 */
	public void addDocumento(Integer docId){
		if(!documentos.containsKey(docId)){
			documentos.put(docId, 1);
		} else {
			Integer TF = documentos.get(docId);
			TF++;
			documentos.put(docId, TF);
		}
	}
	
	/**
	 * Verifica se um dado documento esta presente na listagem
	 * 
	 * @param docId
	 * 			indice do documento a ser verificado
	 * @return
	 * 			Se o documento está presente ou não
	 */
	public boolean contains(Integer docId){
		return documentos.containsKey(docId);
	}
	
	/**
	 * Calcula o IDF do termo
	 */
	public void calcularIDF() {
		//Decidi definir o K como o número de documentos da coleção onde o termo aparece
		Integer K = documentos.keySet().size();
		
		//formula do IDF: Log((M+1)/K)
		IDF = Math.log((M+1)/K);
	}

	/**
	 * Retorna o IDF do termo
	 * 
	 * @return
	 * 		Valor do IDF
	 */
	public Double getIDF() {
		return IDF;
	}

	@Override
	public String toString() {
		return IDF +" "+ documentos.toString();
	}
}
