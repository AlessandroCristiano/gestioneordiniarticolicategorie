package it.prova.gestioneordiniarticolicategorie.dao.categoria;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public class CategoriaDAOImpl implements CategoriaDAO{
	
	private EntityManager entityManager;

	@Override
	public List<Categoria> list() throws Exception {
		return entityManager.createQuery("from Categoria", Categoria.class).getResultList();
	}

	@Override
	public Categoria get(Long id) throws Exception {
		return entityManager.find(Categoria.class, id);
	}

	@Override
	public void update(Categoria input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		input = entityManager.merge(input);
		
	}

	@Override
	public void insert(Categoria input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);
		
	}

	@Override
	public void delete(Categoria input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(input));
		
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Categoria findByFetchingArticolo(Long idLong) throws Exception {
		TypedQuery<Categoria> query = entityManager
				.createQuery("select c FROM Categoria c left join fetch c.articoli a where c.id = :idCategoria", Categoria.class);
		query.setParameter("idCategoria", idLong);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public void deleteFromThirdTable(Long id) throws Exception {
		entityManager.createNativeQuery("delete from articolo_categorie where categoria_id = ?1").setParameter(1, id).executeUpdate();
	}

	@Override
	public List<Categoria> allCategoriesOfAnArticleInACertainOrder(Long id) throws Exception {
		TypedQuery<Categoria> query = entityManager
				.createQuery("select DISTINCT(c) FROM Categoria c join c.articoli a join a.ordine o where o.id = :idOrdine", Categoria.class);
		query.setParameter("idOrdine", id);
		return query.getResultList();
	}

	@Override
	public List<String> listOfCodesOfCategoriesOfOrdersOnDate(java.util.Date data) throws Exception {
		TypedQuery<String> query = entityManager.createQuery("select DISTINCT(c.codice) FROM Categoria c join c.articoli a join a.ordine o where YEAR(o.createDateTime)=YEAR(:data) and MONTH(o.createDateTime)=MONTH(:data)",String.class);
		query.setParameter("data", new java.sql.Date(data.getTime()));
		return query.getResultList();
	}	
}
