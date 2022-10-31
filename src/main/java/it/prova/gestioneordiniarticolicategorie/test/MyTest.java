package it.prova.gestioneordiniarticolicategorie.test;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import it.prova.gestioneordiniarticolicategorie.dao.EntityManagerUtil;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;
import it.prova.gestioneordiniarticolicategorie.service.ArticoloService;
import it.prova.gestioneordiniarticolicategorie.service.CategoriaService;
import it.prova.gestioneordiniarticolicategorie.service.CategoriaServiceImpl;
import it.prova.gestioneordiniarticolicategorie.service.MyServiceFactory;
import it.prova.gestioneordiniarticolicategorie.service.OrdineService;


public class MyTest {

	public static void main(String[] args) {
		OrdineService ordineServiceInstance=MyServiceFactory.getordineServiceInstance();
		ArticoloService articoloServiceInstance= MyServiceFactory.getarticoloServiceInstance();
		CategoriaService categoriaServiceInsance= MyServiceFactory.getcategoriaServiceInstance();
		
		try {

			System.out.println("In tabella Ordine ci sono " + ordineServiceInstance.listAll().size() + " elementi.");
			System.out.println("In tabella Articolo ci sono " + articoloServiceInstance.listAll().size() + " elementi.");
			System.out.println("In tabella Categoria ci sono " + categoriaServiceInsance.listAll().size() + " elementi.");
			System.out.println(
					"**************************** inizio batteria di test ********************************************");
			testInserimentoNuovoOrdine(ordineServiceInstance);
			testAggiornamentoOrdineEsistente(ordineServiceInstance);
			testInserimentoNuovoArticolo(articoloServiceInstance,ordineServiceInstance);
			testAggiornamentoArticoloEsistente(articoloServiceInstance,ordineServiceInstance);
			testRimozioneDiArticolodaUnOrdine(articoloServiceInstance,ordineServiceInstance);
			testInserimentoNuovaCategoria(categoriaServiceInsance);
			testAggiornamentoCategoriaEsistente(categoriaServiceInsance);
			testAggiungiArticoloACategoria(ordineServiceInstance,articoloServiceInstance,categoriaServiceInsance);
			testAggiungiCategoriaAArticolo(ordineServiceInstance,articoloServiceInstance,categoriaServiceInsance);
			testRimuoviArticolo(ordineServiceInstance,articoloServiceInstance);
			testRimuoviCategoria(articoloServiceInstance,categoriaServiceInsance);
			testRimozioneOrdine(ordineServiceInstance);
			testTuttiGliOrdiniPerUnaDeterminataCategoria(articoloServiceInstance, categoriaServiceInsance, ordineServiceInstance);
			testTutteLeCategorieDegliArticoliDiUnDeterminatoOrdine(articoloServiceInstance, categoriaServiceInsance, ordineServiceInstance);
			testSommaTotaleDiPrezziDegliArticoliLegatiAdUnaCategoria(articoloServiceInstance, categoriaServiceInsance, ordineServiceInstance);
			testPiuRecentiOrdiniDataUnaCategoria(articoloServiceInstance, categoriaServiceInsance, ordineServiceInstance);
			testListaDistintaCodiciDiCategorieDiOrdiniEffettuatuiDuranteUnMese(articoloServiceInstance, categoriaServiceInsance, ordineServiceInstance);
			testSommaTotaleDeiPrezziDiTuttiGliArticoliIndirizzatiAdUnDestinatario(articoloServiceInstance, categoriaServiceInsance, ordineServiceInstance);
			testListaIndirizziConUnNumeroSeriale(articoloServiceInstance, categoriaServiceInsance, ordineServiceInstance);
			testListaArticoliConSituazioniStrane(articoloServiceInstance, categoriaServiceInsance, ordineServiceInstance);
			
			
			
			
			System.out.println(
					"****************************** fine batteria di test ********************************************");
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			// questa è necessaria per chiudere tutte le connessioni quindi rilasciare il
			// main
			EntityManagerUtil.shutdown();
		}
	}
	
	private static void testInserimentoNuovoOrdine(OrdineService ordineServiceInstance)throws Exception{
		System.out.println("..............testInserimentoNuovoOrdine..........INIZIO");
		
		Date dataSpedizione = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2022");
		Date dataScadenza = new SimpleDateFormat("dd-MM-yyyy").parse("07-10-2022");
		
		Ordine ordineInstance = new Ordine("Luigi", "Via le pastine", dataSpedizione,dataScadenza);
		
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if(ordineInstance.getId()==null)
			throw new Exception("testInserimentoNuovoOrdine Fallito");
		
		System.out.println("..............testInserimentoNuovoOrdine..........FINE");
	}
	
	private static void testAggiornamentoOrdineEsistente(OrdineService ordineServiceInstance)throws Exception{
		System.out.println("..............testAggiornamentoOrdineEsistente..........INIZIO");
		
		Date dataSpedizione = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2022");
		Date dataScadenza = new SimpleDateFormat("dd-MM-yyyy").parse("07-10-2022");
		Ordine ordineInstance = new Ordine("Luigi", "Via le pastine", dataSpedizione,dataScadenza);
		
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if(ordineInstance.getId()==null)
			throw new Exception("testInserimentoNuovoOrdine Fallito");
		
		String nomePrecedente = ordineInstance.getNomeDestinatario();
		String nomeNuovo= "Mauro";
		ordineInstance.setNomeDestinatario(nomeNuovo);
		ordineServiceInstance.aggiorna(ordineInstance);
		if(ordineInstance.getNomeDestinatario().equals(nomePrecedente))
			throw new Exception("testAggiornamentoOrdineEsistente FALLITO");
			
		System.out.println("..............testAggiornamentoOrdineEsistente..........FINE");
	}
	
	public static void testInserimentoNuovoArticolo(ArticoloService articoloServiceInstance, OrdineService ordineServiceInstance)throws Exception {
		System.out.println("..............testInserimentoNuovoArticolo..........INIZIO");
		
		Date dataSpedizione = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2022");
		Date dataScadenza = new SimpleDateFormat("dd-MM-yyyy").parse("07-10-2022");
		Ordine ordineInstance = new Ordine("Luigi", "Via le pastine", dataSpedizione,dataScadenza);
		
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if(ordineInstance.getId()==null)
			throw new Exception("testInserimentoNuovoOrdine Fallito");
		
		Articolo articoloInstance= new Articolo("Peluche", "HDOHA2167", 20, new Date());
		articoloInstance.setOrdine(ordineInstance);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if(articoloInstance.getId()==null)
			throw new Exception("testInserimentoNuovoArticolo Fallito");			
			
		System.out.println("..............testInserimentoNuovoArticolo..........FINE");
	}
	
	public static void testAggiornamentoArticoloEsistente(ArticoloService articoloServiceInstance, OrdineService ordineServiceInstance)throws Exception{
		System.out.println("..............testAggiornamentoArticoloEsistente..........INIZIO");
		
		Date dataSpedizione = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2022");
		Date dataScadenza = new SimpleDateFormat("dd-MM-yyyy").parse("07-10-2022");
		Ordine ordineInstance = new Ordine("Luigi", "Via le pastine", dataSpedizione,dataScadenza);
		
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if(ordineInstance.getId()==null)
			throw new Exception("testInserimentoNuovoOrdine Fallito");
		
		Articolo articoloInstance= new Articolo("Peluche", "HDOHA2167", 20, new Date());
		articoloInstance.setOrdine(ordineInstance);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if(articoloInstance.getId()==null)
			throw new Exception("testInserimentoNuovoArticolo Fallito");
		
		String descrizionePrecedene=articoloInstance.getDescrizione();
		System.out.println(descrizionePrecedene);
		
		articoloInstance.setDescrizione("Caramelle");
		articoloServiceInstance.aggiorna(articoloInstance);
		if(articoloInstance.getDescrizione().equals(descrizionePrecedene))
			throw new Exception("testAggiornamentoArticolo FAllito");
		System.out.println("..............testAggiornamentoArticoloEsistente..........FINE");
	}
	
	public static void testRimozioneDiArticolodaUnOrdine(ArticoloService articoloServiceInstance, OrdineService ordineServiceInstance)throws Exception{
		System.out.println("..............testRimozioneDiArticolodaUnOrdine..........INIZIO");
		
		Date dataSpedizione = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2022");
		Date dataScadenza = new SimpleDateFormat("dd-MM-yyyy").parse("07-10-2022");
		Ordine ordineInstance = new Ordine("marco", "Via le polline", dataSpedizione,dataScadenza);
		
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if(ordineInstance.getId()==null)
			throw new Exception("testInserimentoNuovoOrdine Fallito");
		
		Articolo articoloInstance= new Articolo("fanta", "HDOHA2167", 20, new Date());
		articoloInstance.setOrdine(ordineInstance);
		
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if(articoloInstance.getId()==null)
			throw new Exception("testInserimentoNuovoArticolo Fallito");
		
		articoloServiceInstance.rimuovi(articoloInstance.getId());
		
		if(articoloServiceInstance.caricaSingoloElemento(articoloInstance.getId()) != null)
			throw new Exception("Rimozione di articolo da ordine fallito");
		
		System.out.println("..............testRimozioneDiArticolodaUnOrdine..........FINE");
	}
	
	public static void testInserimentoNuovaCategoria(CategoriaService categoriaServiceInsance)throws Exception{
		System.out.println("..............testInserimentoNuovaCategoria..........INIZIO");
		
		Categoria categoriaInstance= new Categoria("Libri", "JAISM10");
		categoriaServiceInsance.inserisciNuovo(categoriaInstance);
		if(categoriaInstance.getId()==null)
			throw new Exception("Inserimento categoria fallito");
		
		System.out.println("..............testInserimentoNuovaCategoria..........FINE");
	}
	
	public static void testAggiornamentoCategoriaEsistente(CategoriaService categoriaServiceInsance)throws Exception{
		System.out.println("..............testAggiornamentoCategoriaEsistente..........INIZIO");
		
		Categoria categoriaInstance= new Categoria("Libri", "JAISM10");
		categoriaServiceInsance.inserisciNuovo(categoriaInstance);
		if(categoriaInstance.getId()==null)
			throw new Exception("Inserimento categoria fallito");
		String descrizioneVecchia=categoriaInstance.getDescrizione();
		categoriaInstance.setDescrizione("Informatica");
		categoriaServiceInsance.aggiorna(categoriaInstance);
		if(categoriaInstance.getDescrizione().equals(descrizioneVecchia))
			throw new Exception("Aggiornamento categoria fallito");
		System.out.println("..............testAggiornamentoCategoriaEsistente..........FINE");
	}
	
	public static void testAggiungiArticoloACategoria(OrdineService ordineServiceInstance,ArticoloService articoloServiceInstance,CategoriaService categoriaServiceInsance)throws Exception{
		Date dataSpedizione = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2022");
		Date dataScadenza = new SimpleDateFormat("dd-MM-yyyy").parse("07-10-2022");
		Ordine ordineInstance = new Ordine("marco", "Via le polline", dataSpedizione,dataScadenza);
		
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if(ordineInstance.getId()==null)
			throw new Exception("testInserimentoNuovoOrdine Fallito");
		
		Articolo articoloInstance= new Articolo("fanta", "HDOHA2167", 20, new Date());
		articoloInstance.setOrdine(ordineInstance);
		
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if(articoloInstance.getId()==null)
			throw new Exception("testInserimentoNuovoArticolo Fallito");
		
		Categoria categoriaInstance= new Categoria("Libri", "JAISM10");
		categoriaServiceInsance.inserisciNuovo(categoriaInstance);
		if(categoriaInstance.getId()==null)
			throw new Exception("Inserimento categoria fallito");
		
		categoriaServiceInsance.aggiungiArticolo(categoriaInstance, articoloInstance);
		
		Categoria categoriareloaded=categoriaServiceInsance.caricaSingoloElementoEager(categoriaInstance.getId());
		if(categoriareloaded.getArticoli().isEmpty()) {
			throw new Exception("test testAggiungiArticoloACategoria Fallito articolo non presente");
		}
	}
	
	public static void testAggiungiCategoriaAArticolo(OrdineService ordineServiceInstance,ArticoloService articoloServiceInstance,CategoriaService categoriaServiceInsance)throws Exception{
		
		Date dataSpedizione = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2022");
		Date dataScadenza = new SimpleDateFormat("dd-MM-yyyy").parse("07-10-2022");
		Ordine ordineInstance = new Ordine("Giuseppe", "Via ", dataSpedizione,dataScadenza);
		
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if(ordineInstance.getId()==null)
			throw new Exception("testInserimentoNuovoOrdine Fallito");
		
		Articolo articoloInstance= new Articolo("Mouse", "HDOHA2167", 20, new Date());
		
		articoloInstance.setOrdine(ordineInstance);
		
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if(articoloInstance.getId()==null)
			throw new Exception("testInserimentoNuovoArticolo Fallito");
		
		Categoria categoriaInstance= new Categoria("Elettronica", "JAISM10");
		categoriaServiceInsance.inserisciNuovo(categoriaInstance);
		if(categoriaInstance.getId()==null)
			throw new Exception("Inserimento categoria fallito");
		
		articoloServiceInstance.aggiungiCategoria(articoloInstance, categoriaInstance);
		
		Articolo articoloReloaded = articoloServiceInstance.caricaSingoloElementoEager(articoloInstance.getId());
		if(articoloReloaded.getCategorie().isEmpty())
			throw new RuntimeException("testAggiungiCategoriaAArticolo: FALLITO collegamento non avvenuto");
	}
	
	public static void testRimuoviArticolo(OrdineService ordineServiceInstance, ArticoloService articoloServiceInstance)throws Exception{
		if(articoloServiceInstance.listAll().size()<1)
			throw new Exception("Non ci sono articoli nel db");
		
		Articolo articolo=articoloServiceInstance.listAll().get(0);
		
		articoloServiceInstance.disassocia(articolo.getId());
		articoloServiceInstance.rimuovi(articolo.getId());
		if(articolo.getId()==null)
			throw new Exception("rimozione fallita");
	}
	
	public static void testRimuoviCategoria( ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInsance)throws Exception{
		if(categoriaServiceInsance.listAll().size()<1)
			throw new Exception("Non ci sono categorie nel db");
		
		Categoria categoria=categoriaServiceInsance.listAll().get(0);
		
		categoriaServiceInsance.disassocia(categoria.getId());
		categoriaServiceInsance.rimuovi(categoria.getId());
		if(categoria.getId()==null)
			throw new Exception("rimozione fallita");
	}
	
	public static void testRimozioneOrdine(OrdineService ordineServiceInstance)throws Exception{
		if(ordineServiceInstance.listAll().size()<1)
			throw new Exception("Non ci sono ordini nel db");
		
		Ordine ordine=ordineServiceInstance.listAll().get(0);
		
		Ordine ordineRealoaded = ordineServiceInstance.caricaSingoloElementoEagerArticoli(ordine.getId());
		
		if(!ordineRealoaded.getArticoli().isEmpty())
			throw new Exception("quest'ordine è collegato a degli articoli");
	
		ordineServiceInstance.rimuovi(ordine.getId());
		if(ordine.getId()==null)
			throw new Exception("rimozione fallita");
	}
	
	public static void testTuttiGliOrdiniPerUnaDeterminataCategoria(ArticoloService articoloServiceInstance,CategoriaService categoriaServiceInsance, OrdineService ordineServiceInstance)throws Exception{
		Date dataSpedizione = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2022");
		Date dataScadenza = new SimpleDateFormat("dd-MM-yyyy").parse("07-10-2022");
		Ordine ordineInstance = new Ordine("marco", "Via le polline", dataSpedizione,dataScadenza);
		
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if(ordineInstance.getId()==null)
			throw new Exception("testInserimentoNuovoOrdine Fallito");
		
		Articolo articoloInstance= new Articolo("fanta", "HDOHA2167", 20, new Date());
		articoloInstance.setOrdine(ordineInstance);
		
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if(articoloInstance.getId()==null)
			throw new Exception("testInserimentoNuovoArticolo Fallito");
		
		Categoria categoriaInstance= new Categoria("Libri", "JAISM10");
		categoriaServiceInsance.inserisciNuovo(categoriaInstance);
		if(categoriaInstance.getId()==null)
			throw new Exception("Inserimento categoria fallito");
		
		categoriaServiceInsance.aggiungiArticolo(categoriaInstance, articoloInstance);
		
		Categoria categoriareloaded=categoriaServiceInsance.caricaSingoloElementoEager(categoriaInstance.getId());
		if(categoriareloaded.getArticoli().isEmpty()) 
			throw new Exception("test testAggiungiArticoloACategoria Fallito articolo non presente");
		
		List<Ordine>listaElementi=ordineServiceInstance.tuttiGliOrdiniPerUnaDeterminataCategoria(categoriaInstance.getId());
		if(listaElementi.size()!=1)
			throw new RuntimeException("testTuttiGliOrdiniPerUnaDeterminataCategoria fallito ");
	}
	
	public static void testTutteLeCategorieDegliArticoliDiUnDeterminatoOrdine(ArticoloService articoloServiceInstance,CategoriaService categoriaServiceInsance, OrdineService ordineServiceInstance)throws Exception{
		Date dataSpedizione = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2022");
		Date dataScadenza = new SimpleDateFormat("dd-MM-yyyy").parse("07-10-2022");
		Ordine ordineInstance = new Ordine("marco", "Via le polline", dataSpedizione,dataScadenza);
		
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if(ordineInstance.getId()==null)
			throw new Exception("testInserimentoNuovoOrdine Fallito");
		
		Articolo articoloInstance= new Articolo("fanta", "HDOHA2167", 20, new Date());
		articoloInstance.setOrdine(ordineInstance);
		
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if(articoloInstance.getId()==null)
			throw new Exception("testInserimentoNuovoArticolo Fallito");
		
		Categoria categoriaInstance= new Categoria("Libri", "JAISM10");
		categoriaServiceInsance.inserisciNuovo(categoriaInstance);
		if(categoriaInstance.getId()==null)
			throw new Exception("Inserimento categoria fallito");
		
		Categoria categoriaInstanceDue= new Categoria("Carta", "JAISM10");
		categoriaServiceInsance.inserisciNuovo(categoriaInstanceDue);
		if(categoriaInstance.getId()==null)
			throw new Exception("Inserimento categoria fallito");
		
		categoriaServiceInsance.aggiungiArticolo(categoriaInstance, articoloInstance);
		categoriaServiceInsance.aggiungiArticolo(categoriaInstanceDue, articoloInstance);
		
		Categoria categoriareloaded=categoriaServiceInsance.caricaSingoloElementoEager(categoriaInstance.getId());
		if(categoriareloaded.getArticoli().isEmpty()) 
			throw new Exception("test testAggiungiArticoloACategoria Fallito articolo non presente");
		
		List<Categoria>listaCategorie=categoriaServiceInsance.tutteCategorieDiUnArticoloInUnDeterminatoOrdine(ordineInstance.getId());
		if(listaCategorie.size()!=2)
			throw new Exception("testTutteLeCategorieDegliArticoliDiUnDeterminatoOrdine FAllito");
	}
	
	public static void testSommaTotaleDiPrezziDegliArticoliLegatiAdUnaCategoria(ArticoloService articoloServiceInstance,CategoriaService categoriaServiceInsance, OrdineService ordineServiceInstance)throws Exception {
		Date dataSpedizione = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2022");
		Date dataScadenza = new SimpleDateFormat("dd-MM-yyyy").parse("07-10-2022");
		Ordine ordineInstance = new Ordine("marco", "Via le polline", dataSpedizione,dataScadenza);
		
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if(ordineInstance.getId()==null)
			throw new Exception("testInserimentoNuovoOrdine Fallito");
		
		Articolo articoloInstance= new Articolo("fanta", "HDOHA2167", 17, new Date());
		articoloInstance.setOrdine(ordineInstance);
		
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if(articoloInstance.getId()==null)
			throw new Exception("testInserimentoNuovoArticolo Fallito");
		
		Categoria categoriaInstance= new Categoria("Libri", "JAISM10");
		categoriaServiceInsance.inserisciNuovo(categoriaInstance);
		if(categoriaInstance.getId()==null)
			throw new Exception("Inserimento categoria fallito");
		
		categoriaServiceInsance.aggiungiArticolo(categoriaInstance, articoloInstance);
		
		Categoria categoriareloaded=categoriaServiceInsance.caricaSingoloElementoEager(categoriaInstance.getId());
		if(categoriareloaded.getArticoli().isEmpty()) 
			throw new Exception("test testAggiungiArticoloACategoria Fallito articolo non presente");
		
		int somma=articoloServiceInstance.sommaPrezzoArticoliDiUnaCategoria(categoriaInstance.getId());
		if(somma!=articoloInstance.getPrezzoSingolo())
			throw new Exception("testTutteLeCategorieDegliArticoliDiUnDeterminatoOrdine FAllito");
	}
	
	public static void testPiuRecentiOrdiniDataUnaCategoria(ArticoloService articoloServiceInstance,CategoriaService categoriaServiceInsance, OrdineService ordineServiceInstance)throws Exception{
		Date dataSpedizione = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2022");
		Date dataScadenza = new SimpleDateFormat("dd-MM-yyyy").parse("07-10-2022");
		Ordine ordineInstance = new Ordine("marco", "Via le polline", dataSpedizione,dataScadenza);
		
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if(ordineInstance.getId()==null)
			throw new Exception("testInserimentoNuovoOrdine Fallito");
		
		Articolo articoloInstance= new Articolo("fanta", "HDOHA2167", 17, new Date());
		articoloInstance.setOrdine(ordineInstance);
		
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if(articoloInstance.getId()==null)
			throw new Exception("testInserimentoNuovoArticolo Fallito");
		
		Categoria categoriaInstance= new Categoria("Libri", "JAISM10");
		categoriaServiceInsance.inserisciNuovo(categoriaInstance);
		if(categoriaInstance.getId()==null)
			throw new Exception("Inserimento categoria fallito");
		
		categoriaServiceInsance.aggiungiArticolo(categoriaInstance, articoloInstance);
		
		Categoria categoriareloaded=categoriaServiceInsance.caricaSingoloElementoEager(categoriaInstance.getId());
		if(categoriareloaded.getArticoli().isEmpty()) 
			throw new Exception("test testAggiungiArticoloACategoria Fallito articolo non presente");
		
		Ordine ordineRisultato=ordineServiceInstance.ordinePiuRecenteDaUnaCategoria(categoriaInstance);
		System.out.println(ordineRisultato.getDataSpedizione());
		if(ordineRisultato.equals(null))
			throw new Exception("Ordine piu recente non coincide");
	}
	
	public static void testListaDistintaCodiciDiCategorieDiOrdiniEffettuatuiDuranteUnMese(ArticoloService articoloServiceInstance,CategoriaService categoriaServiceInsance, OrdineService ordineServiceInstance)throws Exception{
		Date dataSpedizione = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2022");
		Date dataScadenza = new SimpleDateFormat("dd-MM-yyyy").parse("07-10-2022");
		Ordine ordineInstance = new Ordine("marco", "Via le polline", dataSpedizione,dataScadenza);
		
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if(ordineInstance.getId()==null)
			throw new Exception("testInserimentoNuovoOrdine Fallito");
		
		Articolo articoloInstance= new Articolo("fanta", "HDOHA2167", 17, new Date());
		articoloInstance.setOrdine(ordineInstance);
		
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if(articoloInstance.getId()==null)
			throw new Exception("testInserimentoNuovoArticolo Fallito");
		
		Categoria categoriaInstance= new Categoria("Libri", "JOAONSCSSSF");
		categoriaServiceInsance.inserisciNuovo(categoriaInstance);
		if(categoriaInstance.getId()==null)
			throw new Exception("Inserimento categoria fallito");
		
		categoriaServiceInsance.aggiungiArticolo(categoriaInstance, articoloInstance);
		
		Categoria categoriareloaded=categoriaServiceInsance.caricaSingoloElementoEager(categoriaInstance.getId());
		if(categoriareloaded.getArticoli().isEmpty()) 
			throw new Exception("test testAggiungiArticoloACategoria Fallito articolo non presente");
		
		Date dataConfronto = new SimpleDateFormat("dd-MM-yyyy").parse("29-10-2022");
		
		List<String> listaElementi = categoriaServiceInsance.listaDistintaCodiciDiCategorieDiOrdiniInData(dataConfronto);
		if(listaElementi.isEmpty())
			throw new Exception("Risultato vuoto");
	}
	
	public static void testSommaTotaleDeiPrezziDiTuttiGliArticoliIndirizzatiAdUnDestinatario(ArticoloService articoloServiceInstance,CategoriaService categoriaServiceInsance, OrdineService ordineServiceInstance)throws Exception{
		Date dataSpedizione = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2022");
		Date dataScadenza = new SimpleDateFormat("dd-MM-yyyy").parse("07-10-2022");
		Ordine ordineInstance = new Ordine("luigi", "Via le polline", dataSpedizione,dataScadenza);
		
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if(ordineInstance.getId()==null)
			throw new Exception("testInserimentoNuovoOrdine Fallito");
		
		Articolo articoloInstance= new Articolo("fanta", "HDOHA2167", 170, new Date());
		articoloInstance.setOrdine(ordineInstance);
		
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if(articoloInstance.getId()==null)
			throw new Exception("testInserimentoNuovoArticolo Fallito");
		
		Categoria categoriaInstance= new Categoria("Libri", "JOAONSCSSSF");
		categoriaServiceInsance.inserisciNuovo(categoriaInstance);
		if(categoriaInstance.getId()==null)
			throw new Exception("Inserimento categoria fallito");
		
		categoriaServiceInsance.aggiungiArticolo(categoriaInstance, articoloInstance);
		
		Categoria categoriareloaded=categoriaServiceInsance.caricaSingoloElementoEager(categoriaInstance.getId());
		if(categoriareloaded.getArticoli().isEmpty()) 
			throw new Exception("test testAggiungiArticoloACategoria Fallito articolo non presente");
		
		int risultato= articoloServiceInstance.sommaPrezziArticoliIndirizzatiAdUnDestinatario(ordineInstance.getNomeDestinatario());
		System.out.println(risultato);
		if(risultato<1)
			throw new Exception("test FALLITO");
	}
	
	public static void testListaIndirizziConUnNumeroSeriale(ArticoloService articoloServiceInstance,CategoriaService categoriaServiceInsance, OrdineService ordineServiceInstance)throws Exception{
		Date dataSpedizione = new SimpleDateFormat("dd-MM-yyyy").parse("03-10-2022");
		Date dataScadenza = new SimpleDateFormat("dd-MM-yyyy").parse("07-10-2022");
		Ordine ordineInstance = new Ordine("luigi", "Via le pastine", dataSpedizione,dataScadenza);
		
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if(ordineInstance.getId()==null)
			throw new Exception("testInserimentoNuovoOrdine Fallito");
		
		Articolo articoloInstance= new Articolo("fanta", "HDOHA2167", 170, new Date());
		articoloInstance.setOrdine(ordineInstance);
		
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if(articoloInstance.getId()==null)
			throw new Exception("testInserimentoNuovoArticolo Fallito");
		
		Categoria categoriaInstance= new Categoria("Libri", "JOAONSCSSSF");
		categoriaServiceInsance.inserisciNuovo(categoriaInstance);
		if(categoriaInstance.getId()==null)
			throw new Exception("Inserimento categoria fallito");
		
		categoriaServiceInsance.aggiungiArticolo(categoriaInstance, articoloInstance);
		
		Categoria categoriareloaded=categoriaServiceInsance.caricaSingoloElementoEager(categoriaInstance.getId());
		if(categoriareloaded.getArticoli().isEmpty()) 
			throw new Exception("test testAggiungiArticoloACategoria Fallito articolo non presente");
		
		List<String>listaIndirizzi=ordineServiceInstance.tuttiGliIndirizziConUnNumeroSeriale(articoloInstance.getNumeroSeriale());
		System.out.println(listaIndirizzi);
		if(listaIndirizzi==null)
			throw new Exception("testListaIndirizziConUnNumeroSeriale FALLITO");
	}
	
	public static void testListaArticoliConSituazioniStrane(ArticoloService articoloServiceInstance,CategoriaService categoriaServiceInsance, OrdineService ordineServiceInstance)throws Exception{
		Date dataSpedizione = new SimpleDateFormat("dd-MM-yyyy").parse("12-10-2022");
		Date dataScadenza = new SimpleDateFormat("dd-MM-yyyy").parse("02-09-2022");
		Ordine ordineInstance = new Ordine("luigi", "Via le pastine", dataSpedizione,dataScadenza);
		
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if(ordineInstance.getId()==null)
			throw new Exception("testInserimentoNuovoOrdine Fallito");
		
		Articolo articoloInstance= new Articolo("fanta", "HDOHA2167", 170, new Date());
		articoloInstance.setOrdine(ordineInstance);
		
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if(articoloInstance.getId()==null)
			throw new Exception("testInserimentoNuovoArticolo Fallito");
		
		Categoria categoriaInstance= new Categoria("Libri", "JOAONSCSSSF");
		categoriaServiceInsance.inserisciNuovo(categoriaInstance);
		if(categoriaInstance.getId()==null)
			throw new Exception("Inserimento categoria fallito");
		
		categoriaServiceInsance.aggiungiArticolo(categoriaInstance, articoloInstance);
		
		Categoria categoriareloaded=categoriaServiceInsance.caricaSingoloElementoEager(categoriaInstance.getId());
		if(categoriareloaded.getArticoli().isEmpty()) 
			throw new Exception("test testAggiungiArticoloACategoria Fallito articolo non presente");
		
		List<Articolo> listaArticoli=articoloServiceInstance.listaArticoliConErrori(ordineInstance);
		if(listaArticoli.size()==0)
			throw new Exception("testListaArticoliConSituazioniStrane FALLITO");
	}
}