package it.prova.gestioneordiniarticolicategorie.dao.articolo;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface ArticoloDAO extends IBaseDAO<Articolo>{

	public Articolo findByFetchingCategorie(Long idLong)throws Exception;
	public void deleteFromThirdTable(Long id)throws Exception;
	
	public int sumPriceItemsOfACategory(Long id)throws Exception;
	
	public int sumPricesItemsAddressedToARecipient(String nome)throws Exception;
	
	public List<Articolo>listOfArticlesWithErrors(Ordine ordineInstance)throws Exception;

}
