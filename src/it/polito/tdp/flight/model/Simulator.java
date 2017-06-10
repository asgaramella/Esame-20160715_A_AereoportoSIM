package it.polito.tdp.flight.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;



public class Simulator {
	
	private int K;
	private int array[]=new int[18];
	private SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge> graph;
	
	private Map<Airport,Integer> presenti;
	private PriorityQueue<Event> queue;
	
	
	public Simulator(int k, SimpleDirectedWeightedGraph<Airport, DefaultWeightedEdge> graph) {
		super();
		K = k;
		this.graph = graph;
		queue=new PriorityQueue<>();
		presenti=new HashMap<Airport,Integer>();
		for(Airport atemp:graph.vertexSet()){
			presenti.put(atemp, 0);
		}
		int i=7;
		for(int pos=0;pos<18;pos++){
			array[pos]=i;
			i=i+2;
		}
	}
	
	public void run(){
		while (!queue.isEmpty()) {
			Event e = queue.poll();
			
			int time=e.getTime();
			int timePartenza=0;
			for(int i:array){
				if(i>=time){
					timePartenza=i;
					break;
				}
			}
			
			Airport arrivo=e.getAirport();
			
			int size= graph.outgoingEdgesOf(arrivo).size();
			if(size!=0){
			int destID= (int)(Math.random()*size);
			
			List<DefaultWeightedEdge> ltemp=new ArrayList<>();
			for(DefaultWeightedEdge arco:graph.outgoingEdgesOf(arrivo)){
				ltemp.add(arco);
			}
			Airport destinazione=graph.getEdgeTarget(ltemp.get(destID));
			
			
			if(timePartenza!=0){
			int tArrivo=(int)(timePartenza+graph.getEdgeWeight(ltemp.get(destID)));
		    Event eNew=new Event(destinazione,tArrivo);
			queue.add(eNew);
			presenti.put(arrivo, presenti.get(arrivo)-1);
			presenti.put(destinazione,presenti.get(destinazione)+1);
			}
			}
			
			}
			
		
	}
	
	
	public void popolaAereoporti(){
		while(K!=0){
		for(Airport atemp:graph.vertexSet()){
			if(K!=0){
			double prob=Math.random();
			if(prob>=0.5){
				presenti.put(atemp,presenti.get(atemp)+1);
				K--;
			}
		}
		}
		}
		
		for(Airport atemp:presenti.keySet()){
			int passeggeri=presenti.get(atemp);
			if(passeggeri !=0){
				for(int i=0;i<passeggeri;i++){
				Event e=new Event(atemp,6);
				queue.add(e);
				}
			}
		}
		
	}
	
	public List<AirportAndNum> getPasseggeri(){
		this.popolaAereoporti();
		this.run();
		List<AirportAndNum> ltemp=new ArrayList<>();
		for(Airport atemp:this.presenti.keySet()){
			if(presenti.get(atemp)!=0)
				ltemp.add(new AirportAndNum(atemp,presenti.get(atemp)));
		}
		
		Collections.sort(ltemp);
		return ltemp;
	}
	
	
}