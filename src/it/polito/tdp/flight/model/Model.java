package it.polito.tdp.flight.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flight.db.FlightDAO;

public class Model {
	
	private List<Airport> airport;
	private List<Airport> esclusi;
	private Map<Integer,Airport> airportMap;
	private List<Route> routes;
	private FlightDAO dao;
	private SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge> graph;
	private Simulator sim;
	
	public Model() {
		super();
		dao=new FlightDAO();
	}
	
	
	public List<Airport> getAllAirports(){
		if(this.airport==null){
			airport=dao.getAllAirports();
			
			this.airportMap=new HashMap<>();
			for(Airport atemp:airport){
			this.airportMap.put(atemp.getAirportId(), atemp);
			}
			
			esclusi=dao.getAirportNoRoute(airportMap);
		}
		
		return airport;
	}
	
	public List<Route> getAllRoutes(){
		if(this.routes==null)
			routes=dao.getAllRoutes();
		
		return routes;
	}
	
	public boolean creaGrafo(int maxDistance){
		graph= new SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		
		
		Graphs.addAllVertices(graph, this.getAllAirports());
		
	    graph.removeAllVertices(esclusi);
		
		
		for(Route rtemp: this.getAllRoutes()){
			Airport suorce=this.airportMap.get(rtemp.getSourceAirportId());
			Airport destination=this.airportMap.get(rtemp.getDestinationAirportId());
			
			if(suorce!=null && destination !=null && !suorce.equals(destination)){
			double distance=LatLngTool.distance(new LatLng(suorce.getLatitude(),suorce.getLongitude()),new LatLng(destination.getLatitude(),destination.getLongitude()),LengthUnit.KILOMETER);
			
			if(distance<=maxDistance){
				DefaultWeightedEdge e=graph.addEdge(suorce, destination);
				if(e!=null)
					graph.setEdgeWeight(e, distance/800);
				
			}
			}
			
			
		
			}
		ConnectivityInspector<Airport,DefaultWeightedEdge> ci=new ConnectivityInspector<>(graph);
			return ci.isGraphConnected();
			
		}
		
	
	 public Airport getPiùLontano(int fiumicinoId){
		 //aereoporto più vicino tra quelli raggiungibili anche facendo scali
		 Airport fiumicino= this.airportMap.get(fiumicinoId);
		 Airport lontano=fiumicino;
		 Double distance=0.0;
		ConnectivityInspector<Airport,DefaultWeightedEdge> ci=new ConnectivityInspector<>(graph);
		 for(Airport atemp :ci.connectedSetOf(fiumicino)){
			 DijkstraShortestPath<Airport,DefaultWeightedEdge> djk =new DijkstraShortestPath<>(graph,fiumicino,atemp);
			 if(djk.getPathLength()>distance){
				 distance=djk.getPathLength();
				 lontano=atemp;
			 }
			 
		 }
		 return lontano;
		 
	 }
	 
	 public Airport getPiùLontanoByVoloDiretto(int fiumicinoId){
		 //aereoporto più vicino tra quelli raggiungibili con voli diretti
		 Airport fiumicino= this.airportMap.get(fiumicinoId);
		 Airport lontano=fiumicino;
		 Double distance=0.0;
		 for(DefaultWeightedEdge e: graph.outgoingEdgesOf(fiumicino)){
			 if(graph.getEdgeWeight(e)>distance){
				 distance=graph.getEdgeWeight(e);
				 lontano=graph.getEdgeTarget(e);
			 }
			 
		 }
		 return lontano;
		 
	 }
	
	 public List<AirportAndNum> simula(int k){
		 sim=new Simulator(k,graph);
		return  sim.getPasseggeri();
	 }

}
	
	
	
	
	
	


