package it.prova.gestioneordiniarticolicategorie.dao.categoria;

import java.util.Date;
import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface CategoriaDAO extends IBaseDAO<Categoria>{

	public Categoria findByFetchingArticolo(Long idLong)throws Exception;
	public void deleteFromThirdTable(Long id)throws Exception;
	
	public List<Categoria>allCategoriesOfAnArticleInACertainOrder(Long id)throws Exception;
	
	public List<String> listOfCodesOfCategoriesOfOrdersOnDate(Date data)throws Exception;

}
