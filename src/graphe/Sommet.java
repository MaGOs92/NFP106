package graphe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Sommet {
	

	public boolean isSolution() {
		return isSolution;
	}

	public void setSolution(boolean isSolution) {
		this.isSolution = isSolution;
	}

	public boolean isWall() {
		return isWall;
	}

	public void setWall(boolean isWall) {
		this.isWall = isWall;
	}

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public boolean isCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(boolean currentNode) {
		this.currentNode = currentNode;
	}

	public boolean isBorderNode() {
		return isBorderNode;
	}

	public void setBorderNode(boolean isBorderNode) {
		this.isBorderNode = isBorderNode;
	}

	private final List<Sommet> voisins;
	private final Point coords;
	
	private boolean currentNode=false;
	private boolean isBorderNode=false;
	private boolean isStart=false;
	private boolean isEnd=false;
	private boolean isWall=false;
	private boolean isSolution=false;
	
	public Sommet(Point point){
		voisins = new ArrayList<Sommet>();
		coords = point;
	}

	public List<Sommet> getVoisins() {
		return voisins;
	}

	public Point getCoords() {
		return coords;
	}
	
	public void affichage(Graphics g) {
		
		if (isSolution || isStart){
			g.setColor(Color.GREEN);
		}
		else if (isEnd){
		    g.setColor(Color.RED);
		}
		else if (isWall){
			g.setColor(Color.BLACK);
		}
		else if (currentNode){
		    g.setColor(Color.BLUE);
		}
		else if (isBorderNode) {
		    g.setColor(Color.YELLOW);
		}
		else {
			g.setColor(Color.WHITE);
		}

	    g.fillRect((int)coords.getX()*50+2, (int)coords.getY()*50+2, 46, 46);
	}
}
