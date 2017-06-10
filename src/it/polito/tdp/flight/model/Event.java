package it.polito.tdp.flight.model;

public class Event implements Comparable<Event>{
	
	
	private Airport airport;
	private int time;
	/**
	 * @param airport
	 * @param passeggeri
	 * @param time
	 * @param type
	 */
	public Event(Airport airport, int time) {
		super();
		this.airport = airport;
		this.time = time;
	}
	public Airport getAirport() {
		return airport;
	}
	public void setAirport(Airport airport) {
		this.airport = airport;
	}
	
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	

	@Override
	public int compareTo(Event other) {
		return this.time-other.time;
	}
	
	
	

}
