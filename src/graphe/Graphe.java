package graphe;

import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;


public class Graphe {

	private Map<Point, Sommet> graphe;
	private int tailleX;
	private int tailleY;

	public Graphe(int x, int y) {

		graphe = new HashMap<Point, Sommet>();
		tailleX = x;
		tailleY = y;
		construireSommets();
		ajouterVoisins();
	}

	public void construireSommets() {

		for (int i = 0; i < tailleY; i++) {
			for (int j = 0; j < tailleX; j++) {
				Point point = new Point(j, i);
				graphe.put(point, new Sommet(point));
			}
		}
	}

	public void ajouterVoisins() {

		for (int i = 0; i < graphe.size(); i++) {
			int posX = i % tailleX;
			int posY = i / tailleX;

			Sommet curSommet = graphe.get(new Point(posX, posY));

			for (int j = posY - 1; j < posY + 2; j++) {
				for (int k = posX - 1; k < posX + 2; k++) {
					if (j != posY || k != posX) {
						Sommet voisin = graphe.get(new Point(k, j));
						if (voisin != null) {
							curSommet.getVoisins().add(voisin);
						}
					}

				}
			}
		}
	}

	public Map<Point, Sommet> getGraphe() {
		return graphe;
	}
	
	public void affichage(Graphics g){
		for (Sommet s : graphe.values()){
			s.affichage(g);
		}
	}

}
