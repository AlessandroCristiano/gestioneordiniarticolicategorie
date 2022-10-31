package it.prova.gestioneordiniarticolicategorie.dao.ordine;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface OrdineDAO extends IBaseDAO<Ordine>{
	
	
	public Ordine findByFetchingArticolo(Long idLong)throws Exception;
	
	public List<Ordine> allOrderWithACategory(Long id)throws Exception;
	
	public Ordine mostRecentOrderFromACategory(Categoria categoria)throws Exception;
	public List<String> allAddressesWithASerialNumber(String numeroSeriale)throws Exception;

}
