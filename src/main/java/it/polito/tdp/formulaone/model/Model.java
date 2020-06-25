package it.polito.tdp.formulaone.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	FormulaOneDAO dao;
	List<Season> stagioni;
	List<Driver> driversForYear;
	Map<Integer, Driver> driverIdMap;
	List<Adiacenza> adiacenze;
	Graph<Driver,DefaultWeightedEdge> grafo;

	public Model() {
		dao = new FormulaOneDAO();
		stagioni = dao.getAllSeasons();


	}
	public List<Season> getSeason() {
		return dao.getAllSeasons();
	}

	public void creaGrafo(Season stagione) {
		grafo = new SimpleDirectedWeightedGraph<Driver, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		driverIdMap = new HashMap<Integer, Driver>();
		//Aggiungi i vertici
		driversForYear = dao.getAllDrivers(stagione, driverIdMap);

		//		for(Driver d : driversForYear) {
		//			driverIdMap.put(d.getDriverId(),d);
		//		}
		Graphs.addAllVertices(grafo, driversForYear);

		//Aggiungi gli archi

		adiacenze = dao.getAdiacenza(driverIdMap);
		for (Adiacenza a : adiacenze) {
			if(!a.getD1().equals(a.getD2())) {
				if(a.getPeso() > 0)
					Graphs.addEdge(grafo, a.getD1(), a.getD2(), a.getPeso());
			}
		}

		System.out.println("Grafo creato!\n#Vertici: "+grafo.vertexSet().size()+"\n#Archi: "+grafo.edgeSet().size());
	}
	public TopPlayer topPlayer() {
		TopPlayer topPlayer = null;
		int best = Integer.MIN_VALUE; //non metto = 0 perchè ci sono anche valori negativi

		for (Driver d : grafo.vertexSet()) {
			int sum = 0;

			for(DefaultWeightedEdge e : grafo.outgoingEdgesOf(d))
				sum += (int) grafo.getEdgeWeight(e);

			for(DefaultWeightedEdge e : grafo.incomingEdgesOf(d))
				sum -= (int) ( grafo.getEdgeWeight(e));

			if(sum > best) {
				Driver topDriver = d;
				best = sum;
				topPlayer = new TopPlayer(topDriver, best);
			}
		}
		return topPlayer;

	}

}
