package pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	
	public ListaDocumentos(){
		documentos = new HashMap<Integer, Integer>();
	}
	
	public Integer getTF(Integer documento){
		return documentos.get(documento);
	}

	public List<Integer> getDocumentos() {
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(documentos.keySet());
		return list;
	}
	
	public void addDocumento(Integer docId){
		if(!documentos.containsKey(docId)){
			documentos.put(docId, 1);
		} else {
			Integer TF = documentos.get(docId);
			TF++;
			documentos.put(docId, TF);
		}
	}
	
	public boolean contains(Integer docId){
		return documentos.containsKey(docId);
	}
	
	public void calcularIDF() {
		Integer K = documentos.keySet().size();
		IDF = Math.log((M+1)/K);
	}

	public Double getIDF() {
		return IDF;
	}

	@Override
	public String toString() {
		return IDF +" "+ documentos.toString();
	}
}
