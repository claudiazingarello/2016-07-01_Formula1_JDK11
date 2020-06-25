package it.polito.tdp.formulaone.model;

public class TopPlayer {
	Driver d;
	int punteggio;
	
	public TopPlayer(Driver d, int punteggio) {
		super();
		this.d = d;
		this.punteggio = punteggio;
	}

	public Driver getD() {
		return d;
	}

	public void setD(Driver d) {
		this.d = d;
	}

	public int getPunteggio() {
		return punteggio;
	}

	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}

	@Override
	public String toString() {
		return d + ", punteggio= " + punteggio;
	}
	
	
}
