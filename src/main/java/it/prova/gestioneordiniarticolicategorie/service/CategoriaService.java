package it.prova.gestioneordiniarticolicategorie.service;

import java.util.Date;
import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.categoria.CategoriaDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface CategoriaService {
	
	public List<Categoria> listAll() throws Exception;

	public Categoria caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Categoria categoriaInstance) throws Exception;

	public void inserisciNuovo(Categoria categoriaInstance) throws Exception;

	public void rimuovi(Long idCategoria) throws Exception;
	
	public void aggiungiArticolo(Categoria categoriaInstance, Articolo articoloInstance)throws Exception;
	
	public Categoria caricaSingoloElementoEager(Long idLongs)throws Exception;
	
	public void disassocia(Long id)throws Exception;
	
	public List<Categoria>tutteCategorieDiUnArticoloInUnDeterminatoOrdine(Long id)throws Exception;
	
	public List<String> listaDistintaCodiciDiCategorieDiOrdiniInData(Date data)throws Exception;
	
	
	// per injection
	public void setCategoriaDAO(CategoriaDAO categoriaDAO);

}
