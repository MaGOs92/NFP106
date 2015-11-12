package a_star;

import graphe.Sommet;

public class Euclide extends Heuristique {
	
	public Euclide(){
		
	}
	
	public float calcHeuristique(Sommet s1, Sommet s2){
		int a = (int) Math.abs(s2.getCoords().getX() - s1.getCoords().getX());
		int b = (int) Math.abs(s2.getCoords().getY() - s1.getCoords().getY());
		float c = a*a+b*b;
		return (float) Math.sqrt(c);
	}

}
