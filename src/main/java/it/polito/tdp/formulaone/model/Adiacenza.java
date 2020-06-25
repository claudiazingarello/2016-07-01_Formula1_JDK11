package it.polito.tdp.formulaone.model;

public class Adiacenza {
	private Driver d1;
	private Driver d2;
	private int peso;
	
	public Adiacenza(Driver d1, Driver d2, int peso) {
		super();
		this.d1 = d1;
		this.d2 = d2;
		this.peso = peso;
	}
	public Driver getD1() {
		return d1;
	}
	public void setD1(Driver d1) {
		this.d1 = d1;
	}
	public Driver getD2() {
		return d2;
	}
	public void setD2(Driver d2) {
		this.d2 = d2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	
}
