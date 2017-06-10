package it.polito.tdp.flight.model;

public class AirportAndNum implements Comparable<AirportAndNum>{
	Airport airport;
	int numPass;
	public AirportAndNum(Airport airport, int numPass) {
		super();
		this.airport = airport;
		this.numPass = numPass;
	}
	public Airport getAirport() {
		return airport;
	}
	public void setAirport(Airport airport) {
		this.airport = airport;
	}
	public int getNumPass() {
		return numPass;
	}
	public void setNumPass(int numPass) {
		this.numPass = numPass;
	}
	@Override
	public int compareTo(AirportAndNum o) {
		
		return -(this.numPass-o.getNumPass());
	}
	
	

}
