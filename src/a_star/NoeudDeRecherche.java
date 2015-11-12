package a_star;

import graphe.Sommet;

public class NoeudDeRecherche {

	protected NoeudDeRecherche noeudPere;
	protected int cout;
	protected Sommet sommet;
	
	public NoeudDeRecherche getNoeudPere() {
		return noeudPere;
	}
	public void setNoeudPere(NoeudDeRecherche noeudPere) {
		this.noeudPere = noeudPere;
	}
	public int getCout() {
		return cout;
	}
	public void setCout(int cout) {
		this.cout = cout;
	}
	public Sommet getSommet() {
		return sommet;
	}
	public void setSommet(Sommet sommet) {
		this.sommet = sommet;
	}
	
	public NoeudDeRecherche(NoeudDeRecherche noeudPere, Sommet sommet){
		this.noeudPere = noeudPere;
		this.cout = noeudPere.getCout() + 1;
		this.sommet = sommet;
	}
	
	public NoeudDeRecherche(Sommet sommet){
		this.sommet = sommet;
		this.cout = 0;
	}
}
