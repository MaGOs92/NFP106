package a_star;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import graphe.*;

@SuppressWarnings("serial")
public class AStar extends JFrame implements MouseListener {
	
	// Constantes
	private final static int CASE_PX=50;
	private final static int TAILLE_GRAPHE=10;
	private enum State {SELECT_START, SELECT_END, SELECT_WALLS, ASTAR};
	private State state;
	
	private Point start;
	private Point end;
	private List<Point> walls;
	private Point selectedPoint=null;
	private boolean searchFinished=false;
	private boolean initFinished=false;
	private Graphe graphe;
	private List<NoeudDeRecherche> arbre;
	private Heuristique heuristique;
	
	public AStar() {
        super("A *");
        System.out.println("Création d'un nouveau graphe de taille " + TAILLE_GRAPHE);
        graphe = new Graphe(TAILLE_GRAPHE,TAILLE_GRAPHE);
        walls = new ArrayList<Point>();
        state = State.SELECT_START;
        
        final JPanel content = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                  super.paintComponent(g);
                  graphe.affichage(g);
            }
        };
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        content.setPreferredSize(new Dimension(TAILLE_GRAPHE * CASE_PX, TAILLE_GRAPHE * CASE_PX + CASE_PX));
        setContentPane(content);
        addMouseListener(this);
    	JLabel label = new JLabel();
    	label.setFont(new Font("Verdana",1,15));
    	content.add(label, BorderLayout.SOUTH);
       
        this.heuristique = selectHeuristique();
        
        Thread aStarThread = new Thread(new Runnable() {
            @Override
            public void run() {
                  while (true) {
                	  if (state == State.SELECT_START){
                		  initPoints();
                    	  label.setText("Sélectionnez un point de départ.");
                    	  content.repaint();
                          try {
                              Thread.sleep(10);
                          } 
                          catch (InterruptedException e) {
                        	  
                          }
                	  }
                	  else if (state == State.SELECT_END){
                		  initPoints();
                    	  label.setText("Sélectionnez un point d'arrivé.");
                    	  content.repaint();
                          try {
                              Thread.sleep(10);
                          } 
                          catch (InterruptedException e) {
                        	  
                          }
                	  }
                	  else if (state == State.SELECT_WALLS){
                		  initPoints();
                    	  content.repaint();
                    	  label.setText("Sélectionnez quelques obstacles. Cliquer ici pour terminer.");
                          try {
                              Thread.sleep(10);
                          } 
                          catch (InterruptedException e) {
                        	  
                          }
                	  }
                	  else {
                    	  if (!searchFinished){
                    		  aStar();
                        	  label.setText("Recherche du plus court chemin....");                  		  
                    	  }
                    	  else {
                    		  if (arbre.size() == 0){
                    			  label.setText("Pas de solution");                  		  	  
                    		  }
                    		  else {
                    			  label.setText("Chemin trouvé!");
                    		  }
                    	  }
                    	  content.repaint();
                          try {
                              Thread.sleep(500);
                          } 
                          catch (InterruptedException e) {
                        	  
                          }  
                	  }
                  }
            }
      });
        aStarThread.start();
  }
  
  public static void main(String[] args) {
	  	AStar aStar = new AStar();
	  	aStar.pack();
	  	aStar.setLocationRelativeTo(null);
	  	aStar.setVisible(true);
  }
  
  public void aStar(){
	  searchFinished=true;
	  NoeudDeRecherche selectedNode = null;
	  float meilleurCout = 0;
	  int i = 0;
	  for (NoeudDeRecherche n : arbre){
		  if(!n.getSommet().isCurrentNode() && !n.getSommet().isWall() && !n.getSommet().isStart() && !n.getSommet().isEnd()){
			  searchFinished=false;
		  }
		  if (n.getSommet().isCurrentNode() || n.getSommet().isWall()){
			  continue;
		  }
		  int g = n.getCout();
		  float h = heuristique.calcHeuristique(n.getSommet(), graphe.getGraphe().get(end));
		  float f = g+h;
		  if (i==0){
			  selectedNode = n;
			  meilleurCout = f;
		  }
		  else {
			  if (f<meilleurCout){
				  selectedNode = n;
				  meilleurCout = f;				  
			  }
		  }
		  i++;
	  }  
	  selectedNode.getSommet().setCurrentNode(true);
	  if (selectedNode.getSommet() == graphe.getGraphe().get(end)){
		  ArrayList<Sommet> solution = new ArrayList<Sommet>();
		  getSolution(selectedNode, solution);
		  for (Sommet s : solution){
			  s.setSolution(true);
		  }
		  searchFinished = true;
	  }
	  else {
		  for (Sommet s : selectedNode.getSommet().getVoisins()){
			  if (s.isWall() || s.isStart()){
				  continue;
			  }
	      		s.setBorderNode(true);
	      		arbre.add(new NoeudDeRecherche(selectedNode, s));
		  }  
	  }
  }
  
  public void getSolution(NoeudDeRecherche n, ArrayList<Sommet> solution){
	  if (n.getSommet() != graphe.getGraphe().get(start)){
		  solution.add(n.getSommet());
		  getSolution(n.getNoeudPere(), solution);
	  }
  }
  
  public Heuristique selectHeuristique(){
	  Heuristique heuristique=null;
	  boolean hasChosen = false;
	  Scanner scan = new Scanner(System.in);
	  do {
		  System.out.println("Choisir l'heuristique : ");
		  System.out.println("1 : Manhattan");
		  System.out.println("2 : Euclide");

		  int choice = scan.nextInt();
		  switch(choice){
		  case 1:
			  heuristique = new Manhattan();
			  hasChosen = true;
			  break;
		  case 2:
			  heuristique = new Euclide();
			  hasChosen = true;
			  break;
		  default:
			  System.out.println("Choix incorrect...");
			  hasChosen = false;
		  }
	  }
	  while(!hasChosen);
	  scan.close();
	  return heuristique;
  }
  
  public void initPoints(){
	  if (initFinished){
		  initAStar();
		  state = State.ASTAR;
	  }
	  else if (state == State.SELECT_START && selectedPoint != null && selectedPoint.getY()<10){
		  start = selectedPoint;
		  graphe.getGraphe().get(start).setStart(true);
		  selectedPoint = null;
		  state = State.SELECT_END;
	  }
	  else if (state == State.SELECT_END && selectedPoint != null && selectedPoint.getY()<10){
		  end = selectedPoint;
		  graphe.getGraphe().get(end).setEnd(true);
		  selectedPoint = null;
		  state = State.SELECT_WALLS;
	  }
	  else if (state == State.SELECT_WALLS && selectedPoint != null){
		  if (selectedPoint.getY() >= 10){
			  initFinished = true;
		  }
		  else {
			  Point wall = selectedPoint;
			  if (wall != start && wall != end){
				  graphe.getGraphe().get(wall).setWall(true);
				  walls.add(wall);
				  
			  }
			  selectedPoint = null;
		  }
	  }
  }
  
  public void initAStar(){
      this.arbre = new ArrayList<NoeudDeRecherche>();
      NoeudDeRecherche base = new NoeudDeRecherche(graphe.getGraphe().get(start));
      arbre.add(base);
      for (Sommet s : graphe.getGraphe().get(start).getVoisins()){
      	s.setBorderNode(true);
      	arbre.add(new NoeudDeRecherche(base, s));
      }
      heuristique.calcHeuristique(graphe.getGraphe().get(start), graphe.getGraphe().get(end));
  }

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX()/CASE_PX;
		int y = (e.getY()-25)/CASE_PX;
		this.selectedPoint = new Point(x,y);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
