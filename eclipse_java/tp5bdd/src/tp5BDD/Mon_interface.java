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
/*
	 * 
	 */
	// TODO les attributs utilisés
	private boolean a_verification=false;
			private JTabbedPane tp;
			private JPanel pou,insertion,recherche,pn,ps,pc,m_connexion;
			private JScrollPane sp;
			private JCheckBox c_cours,c_parcours;
	        private TextField mStat_in, ref_livre, auteur_livre, t_id_parcours, m_query,id,mdp,nom_parcours,idcours,nom_cours,an_etude,idens,mdp_ens,nom_ens;
		    private TextField r_nom_cours,r_status;
		    private JComboBox liste_parcours,r_liste_parcours;
		
		    TextArea mRes;
		    private Button b1, b2, b3, b4,b5,insert_livre,insert_ens,insert_parcours,insert_cours, cours_parcours,livre_cours,chercher;
	        private static final long serialVersionUID = 1L;
			private static final int String = 0; 
		
		    static Connection connexion;
		    static Statement stmt ;
	public Mon_interface() {
		setTitle("bibliothque");
		setSize(720, 700);
		setResizable(false);
		setLocationRelativeTo(this);
		setBackground(Color.decode("#C298C2"));
		// le paneau de connexion
		m_connexion=new JPanel();
		add(m_connexion,BorderLayout.NORTH);
		m_connexion.setBackground(Color.decode("#55ADA9"));
		
		tp=new JTabbedPane();
		tp.setBackground(Color.decode("#55ADA9"));
		//sp =new JScrollPane(tp,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(tp);
		//onglet recheche
				recherche=new JPanel();
				tp.add(recherche,"recherche");
				recherche.setBackground(Color.LIGHT_GRAY);
				r_liste_parcours=new JComboBox<>();
				r_liste_parcours=new JComboBox<>();
				r_liste_parcours.addItem("Info");
				r_liste_parcours.addItem("Materiaux");
				r_liste_parcours.addItem("IoT");
				r_liste_parcours.addItem("télécom et réseau");
				r_liste_parcours.addItem("IS");
				chercher=new Button("chercher");
				chercher.addActionListener(this);
				r_nom_cours= new TextField(25);
				r_nom_cours.setText("nom du cours");
				c_cours=new JCheckBox();
				c_parcours=new JCheckBox();
				c_cours.addActionListener(this);
				c_parcours.addActionListener(this);
				
				
		
	
	
	//ongletd'insertion
		insertion=new JPanel();
		tp.add(insertion,"insertion");
		insertion.setLayout(new BorderLayout());
		pn =new JPanel();
		ps= new JPanel();
		insertion.add(pn,BorderLayout.NORTH);
		pc=new JPanel();
		insertion.add(pc,BorderLayout.CENTER);
		insertion.add(ps,BorderLayout.SOUTH);
		
		
		
		
	
		
		// mon code
		mStat_in = new TextField(80);
		mStat_in.setEditable(false);
		r_status=new TextField(85);
		r_status.setEditable(false);
		
		//authentification et connexion
		
	
		// insertion des cours
		idcours=new TextField(30);
		idcours.setText("identifiant du cours");
		nom_cours=new TextField(45);
		nom_cours.setText("nom du cours");
		an_etude=new TextField("année d'étude ");
		insert_cours=new Button("insert cours");
		insert_cours.addActionListener(this);
		
	
		//insertion enseignant
		idens=new TextField(30);
		idens.setText("identifiant de l'enseignant");
		mdp_ens=new TextField(45);
		mdp_ens.setText("mot de passe de l'enseignant");
		nom_ens=new TextField(45);
		nom_ens.setText("nom de l'enseignant");
		insert_ens=new Button("insert enseignat");
		insert_ens.addActionListener(this);
		
		
	// insertion des livre
		insert_livre= new Button("inserer livre");
		insert_livre.addActionListener(this);
		ref_livre = new TextField(80);
		auteur_livre = new TextField(80);
		ref_livre.setText("reference du livre");  //According to the database schema
		auteur_livre.setText("Auteur du livre"); //According to the database schema
		
		//insertion des parcours
		liste_parcours=new JComboBox<>();
		liste_parcours.addItem("Info");
		liste_parcours.addItem("Materiaux");
		liste_parcours.addItem("IoT");
		liste_parcours.addItem("télécom et réseau");
		liste_parcours.addItem("IS");
		/*t_id_parcours = new TextField(80);
		nom_parcours=new TextField(80);
		insert_parcours= new Button("inserer parcours");
		insert_parcours.addActionListener(this);
		t_id_parcours.setText(" identifiant du parcours");  //According to the database schema
		nom_parcours.setText("le nom du parcours");*/
		//authentification
		
		id = new TextField(20);
		mdp= new TextField(30);
		mRes = new TextArea(30,80);
		id.setText("identifiant");
		mdp.setText("mot de passe");
		b5= new Button("s'authentifier");
		b5.addActionListener(this);
		
		
		//query
		m_query = new TextField(80);
		mRes.setEditable(false);
		m_query.setText("Insert Query here");
		mRes.setText("Query results");
		// les association
		cours_parcours= new Button("associer cours et parcours");
		livre_cours= new Button("associer livre et cours");
		cours_parcours.addActionListener(this);
		livre_cours.addActionListener(this);

		

		/**
		 * First we define the buttons, then we add to the Applet, finally add and ActionListener 
		 * (with a self-reference) to capture the user actions.  
		 */
		b1 = new Button("CONNECT");
		b1.setBackground(Color.green);
		b2 = new Button("DISCONNECT");
		b2.setBackground(Color.red);
		b3 = new Button("QUERY");
		
		
		
		
		
		
		pn.add(id);
		pn.add(mdp);
		pn.add(b5);
		m_connexion.add(b1) ;
		m_connexion.add(b2) ;
		recherche.add(b3) ;
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		
		
		
	
		//insertion status
				pc.add(mStat_in);
		//insertion cours
		pc.add(new Label("                                               iinput cours                                                       ",Label.CENTER));
		pc.add(idcours);
		pc.add(nom_cours);
		pc.add(an_etude);
		pc.add(insert_cours);
		//insertion enseignat
		pc.add(new Label("                                              input enseignant                                                       "));
		pc.add(idens);
		pc.add(mdp_ens);
		pc.add(nom_ens);
		pc.add(insert_ens);
		
		//insertion livre
		pc.add(new Label("                                                         Input livre:                                                                        ", Label.CENTER));
		pc.add(ref_livre);
		pc.add(auteur_livre);
		pc.add(insert_livre);
		//insertion parcours
		pc.add(new Label("  Input parcours   ", Label.CENTER));
		pc.add(liste_parcours);
		//insertion.add(t_id_parcours);
		//insertion.add(nom_parcours);
		//insertion.add(insert_parcours);
		//association
		ps.add(new Label(" associations:  ", Label.CENTER));
		
		ps.add(livre_cours);
		ps.add(cours_parcours);
		// la reQUETTE
		recherche.add(m_query);
		recherche.add(r_status);
		recherche.add(c_cours);
		recherche.add(r_nom_cours);
		
		recherche.add(c_parcours);
		recherche.add(r_liste_parcours);
		
		recherche.add(chercher);
		recherche.add(mRes);
		
		

	
	}
	public void actionPerformed(ActionEvent event)
	{

		// Extract the relevant information from the action (i.e. which button is pressed?)
		Object cause = event.getSource();

		// Act depending on the user action
		// Button CONNECT#55ADA9
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
		if (cause==insert_ens) {
			insertEnseignant();
		}
		if (cause==insert_cours) {
			insertCours();
		}
		if (cause==c_cours) {
			c_parcours.setSelected(false);
		}
		if(cause==c_parcours) {
			c_cours.setSelected(false);
			
		}
		if(cause==chercher) {

			
			if(c_parcours.isSelected()) {
				findparcours();
			}
			if(c_cours.isSelected()) {
				findcours();
			}
			}
	}
	//tous les livres d'un cours
	private void findcours() {
		String query="select livre.ref,livre.auteur "
				+ "from  cours, cours_has_livre, livre  "
				+ "where cours.idcours = cours_has_livre.idcours  "
				+ "and cours_has_livre.ref=livre.ref "				
				+ "and cours.nom_cours='"+r_nom_cours.getText()+"'";
		ResultSet rs = null;
		try {
			
			stmt = connexion.createStatement();
			String auteur;
			rs = stmt.executeQuery(query);
			int ref;
			setStatus("Querying the database");
			mRes.setText("reference\t auteur \n");
			while(rs.next()){
			     ref=rs.getInt("ref");
				auteur = rs.getString("auteur");
				
				mRes.append(""+ref+"\t");
				mRes.append(auteur+"\n");
				
				
			}}catch(Exception e) {
					System.err.println(e.getMessage());
					setStatus("Query failed");
				}
	}
	// tous les livres d'un parcours
	private void findparcours() {
		String query="select livre.ref,livre.auteur "
				+ "from   cours_has_parcours, cours, cours_has_livre, livre  "
				+ "where cours.idcours = cours_has_parcours.idcours  "
				+ "and cours.idcours = cours_has_livre.idcours  "
				+ "and cours_has_livre.ref=livre.ref "
				+ "and cours_has_parcours.idparcours = "+(1+r_liste_parcours.getSelectedIndex());
				
		ResultSet rs = null;
		try {
			
			stmt = connexion.createStatement();
			String auteur;
			rs = stmt.executeQuery(query);
			int ref;
			setStatus("Querying the database");
			mRes.setText("reference\t auteur \n");
			while(rs.next()){
			     ref=rs.getInt("ref");
				auteur = rs.getString("auteur");
				
				mRes.append(""+ref+"\t");
				mRes.append(auteur+"\n");
				
				}
				
			}
		
		
		catch(Exception e) {
			System.err.println(e.getMessage());
			setStatus("Query failed");
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
		mStat_in.setText("Status: " + text);
		r_status.setText("Status: " + text);
	}
	
	/**
	 * Procedure, where the database connection should be implemented. 
	 */
	private void connectToDatabase(){
		String a_name = "user_17002447";
		String a_base = "jdbc:mysql://mysql.istic.univ-rennes1.fr/base_17002447";//17002447";
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
	/**
	 * Execute a query and display the results. Implement the database querying and the 
	 * display of the results here 
	 */
	private void queryDatabase(){

		ResultSet rs = null;
		try {
			stmt = connexion.createStatement();
			String sql;
			String res = "";
			sql = m_query.getText();
			rs = stmt.executeQuery(sql);
			setStatus("Querying the database");
			while(rs.next()){
				//String nom = rs.getString("idparcours");
				//String age= rs.getString("nom_parcours");
				int n=rs.getFetchSize();
				for (int i = 0; i < 1; i++) {
					mRes.append((String)rs.getString(i));
				}
				//String couleur = rs.getString("COULEUR");
				
				//res+= "Le nom est " + nom + ", il est age de " + age +
						//" ans et ses yeux sont de couleur ";//;couleur + "\n";
			}
			//System.out.println(res); //test de la string retournee par la boucle while
			//mRes.setText(res);
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
			setStatus("Query failed");
		}
	}

	
	private void insertLivre(){
		try{
			stmt = connexion.createStatement();
			String ref = ref_livre.getText();
			String author = auteur_livre.getText();//`base_17002447`.
			String insert = "Insert  INTO `livre` (`ref`, `auteur`) values (\'" + ref + "\', \'" + author 
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
			String identifiant = t_id_parcours.getText();
			String nom = nom_parcours.getText();
			String insert = "Insert  INTO `Parcours` values('" + identifiant + "' , '" + nom 
					+"');";
			stmt.executeUpdate(insert);
			setStatus("Inserting --( " + identifiant + ", " + nom + " )-- to the database");
		} catch(Exception e){
			System.err.println(e.getMessage());
			setStatus("Insertion failed");
		}

	}
	private void insertEnseignant(){
		try{
			stmt = connexion.createStatement();
			String identifiant = idens.getText();
			String nom = nom_ens.getText();
			String mdp=mdp_ens.getText();
			String insert = "Insert  INTO `enseignant` values('" + identifiant + "' , '" +mdp+"' , '"+ nom 
					+"');";
			stmt.executeUpdate(insert);
			setStatus("Inserting --( " + identifiant + ", " +mdp+"' , '"+ nom + " )-- to the database");
		} catch(Exception e){
			System.err.println(e.getMessage());
			setStatus("Insertion failed");
		}

	}
	private void insertCours(){
		try{
			stmt = connexion.createStatement();
			String identifiant = idcours.getText();
			String nom = nom_cours.getText();
			String annee=an_etude.getText();
			String id_ens=idens.getText();
			String insert = "Insert  INTO `cours` values('" + identifiant + "' , '" + nom +"' , '"+annee+"' , '"+id_ens
					+"');";
			stmt.executeUpdate(insert);
			setStatus("Inserting --( " + identifiant + ", " +mdp+"' , '"+ nom + " )-- to the database");
		} catch(Exception e){
			System.err.println(e.getMessage());
			setStatus("Insertion failed");
		}

	}
	
	private void insertLienCP(){
		try{
			stmt = connexion.createStatement();
			String idCours = idcours.getText();
			String idParcours = (liste_parcours.getSelectedIndex()+1)+"";
			String insert = "Insert  INTO `cours_has_parcours` values('" + idCours + "' , '" + idParcours 
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
			String idLivre = ref_livre.getText();
			String idCours = idcours.getText();
			String insert = "Insert  INTO `cours_has_livre` (`idcours`, `ref`) values('" + idCours + "' , '" + idLivre 
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
		//ref_livre = new TextField(80);
		//ref_livre.setText("What are you going to do when the light is:");
		//ref_livre.setEditable(false);
		Mon_interface ong=new Mon_interface();
		ong.setVisible(true);
	}

}
