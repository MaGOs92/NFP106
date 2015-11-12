package a_star;

import graphe.Sommet;

public class Manhattan extends Heuristique {
	
	public Manhattan(){
		
	}
	
	public float calcHeuristique(Sommet s1, Sommet s2){
		return (int) (Math.abs(s2.getCoords().getX() - s1.getCoords().getX()) + Math.abs(s2.getCoords().getY() - s1.getCoords().getY()));
	}
}
