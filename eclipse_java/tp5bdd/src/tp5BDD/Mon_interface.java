package tp5BDD;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;

public class Mon_interface extends JFrame implements ActionListener {

private TextField mStat, m1, m2, m3, m4,id,mdp,nom_parcours,idcours,nom_cours,an_etude;
    
    TextArea mRes;
    private Button b1, b2, b3, b4,b5,insert_livre,insert_parcours,insert_cours, cours_parcours,livre_cours;    
	private static final long serialVersionUID = 1L; 
	private boolean a_verification=false;
	static Connection connexion;
	static Statement stmt ;

	// TODO Auto-generated constructor stub
	private JTabbedPane tp;
	private JPanel pou,pod,pot,pn,ps,pc;
	private JScrollPane sp;
	public Mon_interface() {
		setTitle("Fenetre avec onglet");
		setSize(650, 750);
		setResizable(false);
		setLocationRelativeTo(this);

		tp=new JTabbedPane();
		//sp =new JScrollPane(tp,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(tp);



		//onglet222222222222222222222222222222222222222222222222222
		pod=new JPanel();


		tp.add(pod,"insertion");
		// insertion des cours
        idcours=new TextField(30);
        idcours.setText("identifiant du cours");
        nom_cours=new TextField(45);
        nom_cours.setText("nom du cours");
        an_etude=new TextField("année d'étude ");
        insert_cours=new Button("insert cours");
        insert_cours.addActionListener(this);
		//onglet333333333333333333333333333333333333333333333333333333333333333333333333333333
		pot=new JPanel();
		tp.add(pot,"recherche");
		pot.setBackground(Color.LIGHT_GRAY);

		// mon code
		mStat = new TextField(80);
		mStat.setEditable(false);


		m1 = new TextField(80);
		m2 = new TextField(80);
		m3 = new TextField(80);
		m4 = new TextField(80);
		id = new TextField(20);
		mdp= new TextField(30);
		mRes = new TextArea(10,80);

		nom_parcours=new TextField(80);
		mRes.setEditable(false);



		/**
		 * First we define the buttons, then we add to the Applet, finally add and ActionListener 
		 * (with a self-reference) to capture the user actions.  
		 */
		b1 = new Button("CONNECT");
		b2 = new Button("DISCONNECT");
		b3 = new Button("QUERY");
		b5= new Button("s'authentifier");
		insert_cours= new Button("inserer cours");
		insert_parcours= new Button("inserer parcours");
		insert_livre= new Button("inserer livre");
		cours_parcours= new Button("associer cours et parcours");
		livre_cours= new Button("associer livre et cours");
		pod.add(id);
		pod.add(mdp);
		pod.add(b5);
		pod.add(b1) ;
		pod.add(b2) ;
		pod.add(b3) ;

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b5.addActionListener(this);
		insert_cours.addActionListener(this);
		insert_livre.addActionListener(this);
		insert_parcours.addActionListener(this);
		cours_parcours.addActionListener(this);
		livre_cours.addActionListener(this);
		pod.add(mStat);
		pod.add(new Label("                                                Input cours                                                       "));
        pod.add(idcours);
        pod.add(nom_cours);
        pod.add(an_etude);
        pod.add(insert_cours);
		pod.add(new Label("                                                         Input livre:                                                                        ", Label.CENTER));
		pod.add(m1);
		pod.add(m2);
		pod.add(insert_livre);
		pod.add(new Label("	                                                               Input parcours                                                                                ", Label.CENTER));
		pod.add(m3);
		pod.add(nom_parcours);
		pod.add(insert_parcours);
		pod.add(new Label("                                                         associer un livre à un cours:                                     ", Label.CENTER));

		pod.add(livre_cours);
		pod.add(new Label("associer un cours à un à un parcours: ", Label.CENTER));
		pod.add(cours_parcours);
		// la reQUETTE
		pod.add(m4);
		m1.setText("reference du livre");  //According to the database schema
		m2.setText("Auteur du livre"); //According to the database schema
		m3.setText(" identifiant du parcours");  //According to the database schema
		nom_parcours.setText("le nom du parcours");
		m4.setText("Insert Query here");
		id.setText("identifiant");
		mdp.setText("mot de passe");
		pod.add(new Label("Query results: ", Label.CENTER));
		pod.add(mRes);
		mRes.setText("Query results");


	}
	public void actionPerformed(ActionEvent event)
	{

		// Extract the relevant information from the action (i.e. which button is pressed?)
		Object cause = event.getSource();

		// Act depending on the user action
		// Button CONNECT
		if (cause == b1)
		{
			connectToDatabase();
		}

		// Button DISCONNECT
		if (cause == b2)
		{
			disconnectFromDatabase();
		}

		//Button QUERY
		if (cause == b3)
		{
			queryDatabase();
		}

		if (cause==b5) {
			authentification();
		}
		if (cause == insert_livre) {
			insertLivre();
		}
		if(cause==insert_parcours) {
			insertParcours();
		}
		if(cause==cours_parcours) {
			insertLienCP();
		}
		if(cause==livre_cours) {
			insertLienLC();
		}
	}
	

	//authentification dans la base
	private void authentification() {
		if (verifAuth()) {
			setStatus("authentification réussi");
		} else {
			setStatus("identifiant ou mot de passe érroné");
		}

	}


	/**
	 * Set the status text. 
	 * 
	 * @param text The text to set. 
	 */
	private void setStatus(String text){
		mStat.setText("Status: " + text);
	}
	
	/**
	 * Procedure, where the database connection should be implemented. 
	 */
	private void connectToDatabase(){
		String a_name = "user_17002447";
		String a_base = "jdbc:mysql://mysql.istic.univ-rennes1.fr/base_17002447";
		String a_mdp  = "Icibic5678#main";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connexion = DriverManager.getConnection(a_base, a_name, a_mdp );
			setStatus("Connected to the database");

		} catch(Exception e){
			System.err.println(e.getMessage());
			setStatus("Connection failed");
		}
	}


	/**
	 * Procedure, where the database connection should be implemented. 
	 */
	private void disconnectFromDatabase(){
		try{
			connexion.close();
			setStatus("Disconnected from the database");
			a_verification=false;
		} catch(Exception e){
			System.err.println(e.getMessage());
			setStatus("Disconnection failed");
		}
	}

	/**
	 * Execute a query and display the results. Implement the database querying and the 
	 * display of the results here 
	 */
	private void queryDatabase(){
		try {
			Statement m_stmt = connexion.createStatement();
			String sql;
			sql = m4.getText();
			ResultSet rs = stmt.executeQuery(sql);
			setStatus("Querying the database");
			mRes.setText("The query result is presented here.\n");
			mRes.append("Joe\n");
			mRes.append("John\n");
			mRes.append("...\n");
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
			setStatus("Query failed");
		}
	}

	/**
	 * Insert tuples to the database. 
	 */
	/*
	private void insertDatabase(){
		try{
			stmt = connexion.createStatement();
			String name = m1.getText();
			String age = m2.getText();
			String color = m3.getText();
			String insert = "Insert  INTO  values('" + name + "' , '" + age 
					+"' , '" + color + "');";
			stmt.executeUpdate(insert);
			setStatus("Inserting --( " + name + ", " + age + ", " + color + " )-- to the database");
		} catch(Exception e){
			System.err.println(e.getMessage());
			setStatus("Insertion failed");
		}

	}
	*/
	
	private void insertLivre(){
		try{
			stmt = connexion.createStatement();
			String ref = m1.getText();
			String author = m2.getText();
			String insert = "Insert  INTO `base_17002447`.`livre` (`ref`, `auteur`) values (\'" + ref + "\', \'" + author 
					+"\');";
			stmt.executeUpdate(insert);
			setStatus("Inserting --( " + ref + ", " + author + " )-- to the database");
		} catch(Exception e){
			System.err.println(e.getMessage());
			setStatus("Insertion failed");
		}

	}
	
	private void insertParcours(){
		try{
			stmt = connexion.createStatement();
			String identifiant = m3.getText();
			String nom = nom_parcours.getText();
			String insert = "Insert  INTO `base_17002447`.`Parcours` values('" + identifiant + "' , '" + nom 
					+"');";
			stmt.executeUpdate(insert);
			setStatus("Inserting --( " + identifiant + ", " + nom + " )-- to the database");
		} catch(Exception e){
			System.err.println(e.getMessage());
			setStatus("Insertion failed");
		}

	}
	
	private void insertLienCP(){
		try{
			stmt = connexion.createStatement();
			String idCours = idcours.getText();
			String idParcours = m3.getText();
			String insert = "Insert  INTO `base_17002447`.`cours_has_parcours` values('" + idCours + "' , '" + idParcours 
					+"');";
			stmt.executeUpdate(insert);
			setStatus("Inserting --( " + idCours + ", " + idParcours + " )-- to the database");
		} catch(Exception e){
			System.err.println(e.getMessage());
			setStatus("Insertion failed");
		}

	}
	
	
	private void insertLienLC(){
		try{
			stmt = connexion.createStatement();
			String idLivre = m1.getText();
			String idCours = idcours.getText();
			String insert = "Insert  INTO `base_17002447`.`cours_has_livre` (`idcours`, `ref`) values('" + idCours + "' , '" + idLivre 
					+"');";
			stmt.executeUpdate(insert);
			setStatus("Inserting --( " + idCours + ", " + idLivre + " )-- to the database");
		} catch(Exception e){
			System.err.println(e.getMessage());
			setStatus("Insertion failed");
		}

	}
	
	private boolean verifAuth(){
		try {
			String identifiant = id.getText();
			String motdepasse = mdp.getText();
			
			
			Statement m_stmt = connexion.createStatement();
			String sql="SELECT idenseignant,mdp FROM `base_17002447`.`enseignant` where idenseignant=" + identifiant;
			ResultSet rs = stmt.executeQuery(sql);
			setStatus("Querying the database");
			if(rs.getString("mdp")==motdepasse) {
				a_verification=true;
			}
			mRes.setText("The query result is presented here.\n");
			mRes.append("Joe\n");
			mRes.append("John\n");
			mRes.append("...\n");
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
			setStatus("Query failed");
		}
		
		
		return true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**
		 * Definition of text fields
		 */
		//m1 = new TextField(80);
		//m1.setText("What are you going to do when the light is:");
		//m1.setEditable(false);
		Mon_interface ong=new Mon_interface();
		ong.setVisible(true);
	}

}
