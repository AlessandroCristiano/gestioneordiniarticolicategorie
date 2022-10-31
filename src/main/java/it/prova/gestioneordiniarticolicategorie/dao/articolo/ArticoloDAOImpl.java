package it.prova.gestioneordiniarticolicategorie.dao.articolo;

import java.util.List;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public class ArticoloDAOImpl implements ArticoloDAO {

	private EntityManager entityManager;

	@Override
	public List<Articolo> list() throws Exception {
		return entityManager.createQuery("from Articolo", Articolo.class).getResultList();
	}

	@Override
	public Articolo get(Long id) throws Exception {
		return entityManager.find(Articolo.class, id);
	}

	@Override
	public void update(Articolo input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		input = entityManager.merge(input);

	}

	@Override
	public void insert(Articolo input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);

	}

	@Override
	public void delete(Articolo input) throws Exception {
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
	public Articolo findByFetchingCategorie(Long idLong) throws Exception {
		TypedQuery<Articolo> query = entityManager
				.createQuery("select a FROM Articolo a left join fetch a.categorie c where a.id = :idArticolo", Articolo.class);
		query.setParameter("idArticolo", idLong);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public void deleteFromThirdTable(Long id) throws Exception {
		entityManager.createNativeQuery("delete from articolo_categorie where articolo_id = ?1").setParameter(1, id).executeUpdate();	
	}

	@Override
	public int sumPriceItemsOfACategory(Long id) throws Exception {
		TypedQuery<Long> query = entityManager
				.createQuery("select SUM(a.prezzoSingolo) FROM Articolo a join a.categorie c where c.id = :idCategoria", Long.class);
		query.setParameter("idCategoria", id);
		return query.getSingleResult().intValue();
	}

	@Override
	public int sumPricesItemsAddressedToARecipient(String nome) throws Exception {
		TypedQuery<Long> query = entityManager
				.createQuery("select SUM(a.prezzoSingolo) FROM Articolo a join a.ordine o where o.nomeDestinatario = :nome", Long.class);
		query.setParameter("nome", nome);
		return query.getSingleResult().intValue();
	}

	@Override
	public List<Articolo> listOfArticlesWithErrors(Ordine ordineInstance) throws Exception {
		TypedQuery<Articolo> query = entityManager
				.createQuery("select a FROM Articolo a left join fetch a.ordine o where o.id =:idOrdine and o.dataSpedizione>o.dataScadenza", Articolo.class);
		query.setParameter("idOrdine", ordineInstance.getId());
		return query.getResultList();
	}
	
	
}
