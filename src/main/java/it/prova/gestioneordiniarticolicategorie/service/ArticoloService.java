package it.prova.gestioneordiniarticolicategorie.service;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.articolo.ArticoloDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface ArticoloService {
	
	public List<Articolo> listAll() throws Exception;

	public Articolo caricaSingoloElemento(Long id) throws Exception;
	
	public void aggiorna(Articolo articoloInstance) throws Exception;
	
	public void inserisciNuovo(Articolo articoloInstance) throws Exception;
	
	public void rimuovi(Long idArticolo) throws Exception;
	
	public void aggiungiCategoria(Articolo articoloInstance, Categoria categoriaInstance)throws Exception;
	
	public Articolo caricaSingoloElementoEager(Long idLong)throws Exception;
	
	public void disassocia(Long id)throws Exception;
	
	public int sommaPrezzoArticoliDiUnaCategoria(Long id)throws Exception; 
	
	public int sommaPrezziArticoliIndirizzatiAdUnDestinatario(String nome)throws Exception;
	
	public List<Articolo> listaArticoliConErrori(Ordine ordineInstance)throws Exception;
	
	// per injection
	public void setArticoloDAO(ArticoloDAO articoloDAO);

}
