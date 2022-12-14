package it.prova.gestioneordiniarticolicategorie.dao.ordine;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public class OrdineDAOImpl implements OrdineDAO{
	
	private EntityManager entityManager;

	@Override
	public List<Ordine> list() throws Exception {
		return entityManager.createQuery("from Ordine", Ordine.class).getResultList();
	}

	@Override
	public Ordine get(Long id) throws Exception {
		return entityManager.find(Ordine.class, id);
	}

	@Override
	public void update(Ordine input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		input = entityManager.merge(input);
		
	}

	@Override
	public void insert(Ordine input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);		
	}

	@Override
	public void delete(Ordine input) throws Exception {
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
	public Ordine findByFetchingArticolo(Long idLong) throws Exception {
		TypedQuery<Ordine> query = entityManager
				.createQuery("select o FROM Ordine o left join fetch o.articoli a where o.id = :idOrdine", Ordine.class);
		query.setParameter("idOrdine", idLong);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<Ordine> allOrderWithACategory(Long id) throws Exception {
		TypedQuery<Ordine> query = entityManager
				.createQuery("select o FROM Ordine o left join fetch o.articoli a join a.categorie c where c.id = :idCategoria", Ordine.class);
		query.setParameter("idCategoria", id);
		return query.getResultList();
	}

	@Override
	public Ordine mostRecentOrderFromACategory(Categoria categoria) throws Exception {
		TypedQuery<Ordine> query = entityManager
				.createQuery("FROM Ordine o inner join fetch o.articoli a inner join fetch a.categorie c where o.dataSpedizione in(select max(o.dataSpedizione) FROM Ordine o) and c.id = :idCategoria ", Ordine.class);
		query.setParameter("idCategoria", categoria.getId());
		return query.getSingleResult();
	}

	@Override
	public List<String> allAddressesWithASerialNumber(String numeroSeriale) throws Exception {
		TypedQuery<String> query = entityManager
				.createQuery("select DISTINCT(o.indirizzoSpedizione) FROM Ordine o left join o.articoli a where a.numeroSeriale = :numeroSeriale", String.class);
		query.setParameter("numeroSeriale", numeroSeriale);
		return query.getResultList();
	}
}
