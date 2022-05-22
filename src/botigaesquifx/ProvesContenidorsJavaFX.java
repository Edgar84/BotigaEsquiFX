package botigaesquifx;

import javafx.scene.paint.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.CallableStatement;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


//

public class ProvesContenidorsJavaFX extends Application {

    private static ConnexioBD conn = new ConnexioBD();

    static Connection connexioBD;

    static ArrayList<Client> clients = new ArrayList<>();
    static ArrayList<CursIndividual> ci = new ArrayList<>();
    static ArrayList<CursColectiu> cc = new ArrayList<>();
    static ArrayList<CursCompeticio> ccom = new ArrayList<>();
    Button btnReservar;
    Label lblNom;
    Label lblCognom;
    Label lblDni;
    Label lblNumFamilia;
    Label lblNivell;
    Label lblDataFederat;
    Label lblDataFamiliar;

    TextField txtNom;
    TextField txtCognom;
    static TextField txtDni;
    TextField txtNumFamilia;
    TextField txtNivell;
    TextField txtDataFederat;
    TextField txtDataFamiliar;

    Label lblNomCurs;
    Label lblIdCurs;
    Label lblPreuCurs;
    Label lblPreuFinalCurs;

    TextField txtNomCurs;
    TextField txtIdCurs;
    static TextField txtPreuCurs;
    TextField txtPreuFinalCurs;

    Tab tab1;
    Tab tab2;
    Tab tab3;

    public static void main(String[] args) throws SQLException {

        conn.connexio();

        consultarCursosCompeticio();
        consultarCursosIndividual();
        consultarCursosColectiu();
        consultarClient();
        launch();

    }

    @Override
    public void start(Stage escenari) throws Exception {
        // Fem servir un contenidor BorderPane per la nostra aplicació
        // el contingut de cada regió el dibuixem en els diferents mètodes

        BorderPane contenidor = new BorderPane();

        contenidor.setTop(partSuperior());
        contenidor.setBottom(partInferior());
        contenidor.setLeft(lateralEsquerre());
        contenidor.setCenter(formulariCentral());
        contenidor.setRight(lateralDret());

        // VBox contenidor = new VBox(); //organitza els nodes en vertical
        // HBox contenidor = new HBox(); //organitza els nodes en horitzontal
        // Pane contenidor = new Pane(); //coloca els nodes al cantó superior esquerra
        // StackPane contenidor = new StackPane(); //apila els nodes un al damunt de
        // l'altre
        // FlowPane contenidor = new FlowPane();

        // contenidor.getChildren().addAll(btn1,btn2,btn3);
        // contenidor.setAlignment(Pos.CENTER);
        // contenidor.setSpacing(20);

        Scene escena = new Scene(contenidor);

        escenari.setScene(escena);
        escenari.setMinHeight(300);
        escenari.setMinWidth(500);

        escenari.show();
    }

    private Pane partSuperior() {
        HBox hb = new HBox();
        hb.setPadding(new Insets(15, 0, 15, 0));
        hb.setAlignment(Pos.CENTER);
        Label titol = new Label("EXTREME SNOW");
        titol.setMinHeight(50);
        titol.setStyle("-fx-font-weight: bold;-fx-font-size:20;");
        titol.setPadding(new Insets(5, 60, 5, 60));
        titol.setTextFill(Color.web("#822f8e"));
        hb.getChildren().addAll(titol);
        
        return hb;
    }

    private Pane lateralEsquerre() throws SQLException {
        
        VBox vlateral = new VBox();
        Label titol = new Label("CLIENTS");
        vlateral.getChildren().addAll(titol);
        vlateral.setPadding(new Insets(10.0));
        vlateral.setSpacing(10.0);
        

        //vlateral.setAlignment(Pos.CENTER);
        vlateral.setAlignment(Pos.TOP_CENTER);
        TableView<Client> tblClients = new TableView<>();
        TableColumn<Client, String> colNom = new TableColumn<>("Nom");
        TableColumn<Client, String> colCognom = new TableColumn<>("Cognom");
        TableColumn<Client, String> colDni = new TableColumn<>("DNI");
        
        colNom.setMinWidth(120);
        colCognom.setMinWidth(120);
        colDni.setMinWidth(120);
                
        tblClients.getColumns().addAll(colNom, colCognom, colDni);
        vlateral.getChildren().add(tblClients);

        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colCognom.setCellValueFactory(new PropertyValueFactory<>("cognom"));
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));

        for (Client cl : clients) {
            tblClients.getItems().add(cl);
        }

        tblClients.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Client client = (Client) newValue;

                if (client != null) {
                    txtDni.setText(client.getDni());
                    txtNom.setText(client.getNom());
                    txtCognom.setText(client.getCognom());
                    txtNumFamilia.setText(client.getnumFamilia());
                    txtNivell.setText(String.valueOf(client.getNivell()));
                    txtDataFederat.setText(String.valueOf(client.getDataFederat()));
                    txtDataFamiliar.setText(String.valueOf(client.getDataFamiliar()));

                    if (tab1.isSelected())
                        calcularPreuFinalColectiu();
                }
            }
        });
        return vlateral;
    }
    
    private Pane lateralDret() {

        VBox vb = new VBox();
        Label titol = new Label("CURSOS");

        vb.setPadding(new Insets(10.0));
        vb.setSpacing(10.0);
        vb.setAlignment(Pos.CENTER);

        TabPane tp = new TabPane();

        tab1 = new Tab("Colectius", cursosColectius());
        tab2 = new Tab("Competicio", cursosCometicio());
        tab3 = new Tab("Individual", cursosIndividuals());

        tp.getTabs().add(tab1);
        tp.getTabs().add(tab2);
        tp.getTabs().add(tab3);

        vb.getChildren().addAll(titol, tp);
        return vb;
    }

    private VBox formulariCentral() {
        
        VBox global = new VBox();
        global.setAlignment(Pos.CENTER);
        global.setPadding(new Insets(-92, 0, 0, 0));
        
        
        VBox vlateral = new VBox();
        Label titolClients = new Label("Clients");
        titolClients.setStyle("-fx-background-color:#c4c4c4; " + "-fx-font-family: ubuntu; -fx-font-weight: bold;");
        titolClients.setPadding(new Insets(2, 90, 2, 90));
        vlateral.getChildren().addAll(titolClients);

        vlateral.setAlignment(Pos.CENTER);
        GridPane gp = new GridPane();
        gp.setPadding(new Insets(10, 0, 10, 0));
        gp.setAlignment(Pos.CENTER);

        lblNom = new Label("Nom");
        lblCognom = new Label("Cognom");
        lblDni = new Label("Dni");
        lblNumFamilia = new Label("NumFamilia");
        lblNivell = new Label("Nivell");
        lblDataFederat = new Label("DataFederat");
        lblDataFamiliar = new Label("DataFamiliar");

        txtNom = new TextField();
        txtNom.setDisable(true);
        txtCognom = new TextField();
        txtCognom.setDisable(true);
        txtDni = new TextField();
        txtDni.setDisable(true);
        txtNumFamilia = new TextField();
        txtNumFamilia.setDisable(true);
        txtNivell = new TextField();
        txtNivell.setDisable(true);
        txtDataFederat = new TextField();
        txtDataFederat.setDisable(true);
        txtDataFamiliar = new TextField();
        txtDataFamiliar.setDisable(true);

        gp.add(lblNom, 0, 0);
        gp.add(txtNom, 1, 0);
        gp.add(lblCognom, 0, 1);
        gp.add(txtCognom, 1, 1);
        gp.add(lblDni, 0, 2);
        gp.add(txtDni, 1, 2);

        gp.add(lblNumFamilia, 0, 3);
        gp.add(txtNumFamilia, 1, 3);
        gp.add(lblNivell, 0, 4);
        gp.add(txtNivell, 1, 4);
        gp.add(lblDataFederat, 0, 5);
        gp.add(txtDataFederat, 1, 5);
        gp.add(lblDataFamiliar, 0, 6);
        gp.add(txtDataFamiliar, 1, 6);

        vlateral.getChildren().add(gp);

        VBox vlateral2 = new VBox();
        Label titolCusrsos = new Label("Cursos");
        titolCusrsos.setStyle("-fx-background-color:#c4c4c4; " + "-fx-font-family: ubuntu; -fx-font-weight: bold;");
        titolCusrsos.setPadding(new Insets(2, 90, 2, 90));
        vlateral2.getChildren().addAll(titolCusrsos);

        vlateral2.setAlignment(Pos.CENTER);
        GridPane gp2 = new GridPane();
        gp2.setPadding(new Insets(10, 0, 10, 0));
        gp2.setAlignment(Pos.CENTER);

        lblNomCurs = new Label("Nom");
        lblIdCurs = new Label("ID");
        lblPreuCurs = new Label("PreuCurs");
        lblPreuFinalCurs = new Label("PreuFinalCurs");

        txtNomCurs = new TextField();
        txtNomCurs.setDisable(true);
        txtIdCurs = new TextField();
        txtIdCurs.setDisable(true);
        txtPreuCurs = new TextField();
        txtPreuCurs.setDisable(true);
        txtPreuFinalCurs = new TextField();
        txtPreuFinalCurs.setDisable(true);

        gp2.add(lblNomCurs, 0, 1);
        gp2.add(txtNomCurs, 1, 1);
        gp2.add(lblIdCurs, 0, 2);
        gp2.add(txtIdCurs, 1, 2);
        gp2.add(lblPreuCurs, 0, 3);
        gp2.add(txtPreuCurs, 1, 3);
        gp2.add(lblPreuFinalCurs, 0, 4);
        gp2.add(txtPreuFinalCurs, 1, 4);

        // return gp;

        vlateral2.getChildren().add(gp2);
        global.getChildren().add(vlateral);
        global.getChildren().add(vlateral2);
        return global;

    }

    private Pane partInferior() {
        HBox hb = new HBox();
        hb.setPadding(new Insets(15, 0, 15, 0));
        hb.setAlignment(Pos.CENTER);
        
        
        Button btnNetejar = new Button("Netejar");
        btnNetejar.setOnAction(e -> netejarFormulari());
        
        btnReservar = new Button("Reservar");
        btnReservar.setOnAction(e -> System.out.println("no tens seleccionat cap curs"));

        Button btn3 = new Button("Boto 3");
       
        
        //HBox hinferior = new HBox();
        hb.getChildren().addAll(btnNetejar, btnReservar, btn3);

        hb.setAlignment(Pos.CENTER);
        return hb;
    }

    private Object netejarFormulari() {
        
        txtCognom.setText("");
        txtNom.setText("");
        txtCognom.setText("");
        txtDni.setText("");
        txtNumFamilia.setText("");
        txtNivell.setText("");
        txtDataFederat.setText("");
        txtDataFamiliar.setText("");
        txtNomCurs.setText("");
        txtIdCurs.setText("");
        return lblCognom;
    }

    private Pane cursosColectius() {

        VBox vlateral = new VBox();
        vlateral.getChildren().addAll(new Label("CURSOS"));
        vlateral.setAlignment(Pos.CENTER);

        TableView<CursColectiu> tblClients = new TableView<>();

        TableColumn<CursColectiu, String> colNom = new TableColumn<>("NOM");
        TableColumn<CursColectiu, String> colDia = new TableColumn<>("data");
        TableColumn<CursColectiu, String> colPreuFinal = new TableColumn<>("Preu Final");

        tblClients.getColumns().addAll(colNom, colDia, colPreuFinal);
        vlateral.getChildren().add(tblClients);

        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colDia.setCellValueFactory(new PropertyValueFactory<>("data"));
        colPreuFinal.setCellValueFactory(new PropertyValueFactory<>("preuFinal"));

        for (CursColectiu CC : cc) {

            tblClients.getItems().add(CC);
        }

        tblClients.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                CursColectiu CI = (CursColectiu) newValue;

                if (CI != null) {
                    txtNomCurs.setText(String.valueOf(CI.getNom()));
                    txtIdCurs.setText(String.valueOf(CI.getId()));
                    txtPreuCurs.setText(String.valueOf(CI.getPreuFinal()));
                    calcularPreuFinalColectiu();
                }
            }
        });
        return vlateral;
    }

    private Pane cursosCometicio() {
        VBox vlateral = new VBox();
        vlateral.getChildren().addAll(new Label("CLIENTS"));
        vlateral.setAlignment(Pos.CENTER);

        TableView<CursCompeticio> tblClients = new TableView<>();
        // TableColumn<CursCompeticio, String> colId = new TableColumn<>("ID");
        TableColumn<CursCompeticio, String> colNom = new TableColumn<>("NOM");
        TableColumn<CursCompeticio, String> colNivell = new TableColumn<>("NIVELL");
        TableColumn<CursCompeticio, String> colDatainici = new TableColumn<>("Data_inici");
        TableColumn<CursCompeticio, String> colDataFi = new TableColumn<>("Data_Fi");
        TableColumn<CursCompeticio, String> colPreu = new TableColumn<>("Preu");
        // TableColumn<Client, String> colEmail = new TableColumn<>("Email");
        // TableColumn<Client, int> coltelefon = new TableColumn<>("Telefon");

        tblClients.getColumns().addAll(colNom, colNivell, colDatainici, colDataFi, colPreu);
        vlateral.getChildren().add(tblClients);

        // colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNivell.setCellValueFactory(new PropertyValueFactory<>("nivell"));
        colDatainici.setCellValueFactory(new PropertyValueFactory<>("dataInici"));
        colDataFi.setCellValueFactory(new PropertyValueFactory<>("dataFi"));
        colPreu.setCellValueFactory(new PropertyValueFactory<>("preu"));

        for (CursCompeticio CC : ccom) {
            tblClients.getItems().add(CC);
        }

        tblClients.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                CursCompeticio CI = (CursCompeticio) newValue;

                if (CI != null) {
                    txtNomCurs.setText(String.valueOf(CI.getNom()));
                    txtIdCurs.setText(String.valueOf(CI.getId()));

                }
            }
        });
        return vlateral;
    }

    private Pane cursosIndividuals() {
        VBox vlateral = new VBox();
        vlateral.getChildren().addAll(new Label("CLIENTS"));
        vlateral.setAlignment(Pos.CENTER);

        TableView<CursIndividual> tblClients = new TableView<>();
        TableColumn<CursIndividual, String> colNom = new TableColumn<>("NOM");
        TableColumn<CursIndividual, String> colPreuHora = new TableColumn<>("Preu Hora");

        // TableColumn<Client, String> colEmail = new TableColumn<>("Email");
        // TableColumn<Client, int> coltelefon = new TableColumn<>("Telefon");

        tblClients.getColumns().addAll(colNom, colPreuHora);
        vlateral.getChildren().add(tblClients);

        // colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));

        colPreuHora.setCellValueFactory(new PropertyValueFactory<>("preuHora"));

        for (CursIndividual CC : ci) {
            tblClients.getItems().add(CC);
        }

        tblClients.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                CursIndividual CI = (CursIndividual) newValue;

                if (CI != null) {
                    txtNomCurs.setText(String.valueOf(CI.getNom()));
                    txtIdCurs.setText(String.valueOf(CI.getId()));
                }
            }
        });

        return vlateral;

    }

    private static void consultarCursosCompeticio() throws SQLException {

        connexioBD = conn.getConnexioBD();

        String SQL = "select * from curs,curs_competicio where curs.id = curs_competicio.id;";

        PreparedStatement ps = connexioBD.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ccom.add(new CursCompeticio(rs.getInt("id"), rs.getString("nom"), rs.getString("dni_monitor"),
                    rs.getInt("nivell"), rs.getDate("data_inici").toLocalDate(), rs.getDate("data_fi").toLocalDate(),
                    rs.getInt("preu")));
        }

    }

    private static void consultarCursosIndividual() throws SQLException {

        connexioBD = conn.getConnexioBD();

        String SQL = "select * from curs,curs_individual where curs.id = curs_individual.id;";

        PreparedStatement ps = connexioBD.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ci.add(new CursIndividual(rs.getInt("id"), rs.getString("nom"), rs.getString("dni_monitor"),
                    rs.getInt("preu_hora")));
        }
    }

    // private static void reservarCurs(){

    // System.out.println(txtDni.getText());

    // }

    private void calcularPreuFinalColectiu() {

        int preuCursFinal = 0;

        String tipoCurs = tab1.getText();
        // passar preu final
        // passar el descompte
        // passar el dni
        // passar el id de curs

        // 1descompre
        // 2preufinal
        // 3pasarun 0
        // 4 id curs
        // 5 dni
        // 6pasar que es colectiu

        if (txtPreuCurs.getText() != "" && txtDni.getText() != "") {
            preuCursFinal = Integer.parseInt(txtPreuCurs.getText());
        }

        System.out.println(txtDataFamiliar.getText());

        System.out.println(txtDataFamiliar.getText().equals("null"));

       

        if (txtDataFamiliar.getText().equals("null") != true) {
            preuCursFinal = (int) (preuCursFinal * 0.60);
        }

        txtPreuFinalCurs.setText(Integer.toString(preuCursFinal));

        System.out.println(tab1.getText());
        System.out.println(preuCursFinal);

        // btnReservar.setOnAction(e -> reservarCurs(60, preuCursFinal, 0, tipoCurs));
       
    }

    private void reservarCurs(int descompte, int preuCursFinal, int j, String tipoCurs) {

        int idCurs = Integer.parseInt(txtIdCurs.getText());
        String dni = (txtDni.getText());

        connexioBD = conn.getConnexioBD();

        CallableStatement SQL;
        try {
            SQL = connexioBD.prepareCall("{call llogarCursos(?,?,?,?,?,?.?)}");
        
            SQL.setLong(1, descompte);
            SQL.setLong(2, preuCursFinal);
            SQL.setLong(3, j);
            SQL.setLong(4, idCurs);
            SQL.setString(5, dni);
            SQL.setString(6, tipoCurs);
            SQL.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Cabecera");
            alert.setTitle("Info");
            alert.setContentText("Correcte reserva");
            alert.showAndWait();


        } catch (SQLException e) {
           System.out.println("Error al insert");
        }

    };

    private static void consultarCursosColectiu() throws SQLException {

        connexioBD = conn.getConnexioBD();

        String SQL = "select * from curs,curs_colectiu where curs.id = curs_colectiu.id;";

        PreparedStatement ps = connexioBD.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            cc.add(new CursColectiu(rs.getInt("id"), rs.getString("nom"), rs.getString("dni_monitor"),
                     getLocalDate(rs, "data"), rs.getInt("preu")));
        }
    }

    private static void consultarClient() throws SQLException {

        connexioBD = conn.getConnexioBD();

        String SQL = "select client.*, fam_num.num_fam,federat.nivell,federat.num_federacio,fam_num.data_caducitat as DATAFAM,federat.data_caducitat as DATAFED from client left join fam_num on client.dni = fam_num.dni left join federat on client.dni = federat.dni;";

        PreparedStatement ps = connexioBD.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            // System.out.println(rs.getDate("DATAFED"));
            // Clients.add(new Client(rs.getString("dni"), rs.getString("nom"),
            // rs.getString("cognom"),
            // rs.getInt("telefon"), rs.getString("email"), rs.getString("num_fam"),
            // rs.getString("num_federacio"), rs.getInt("nivell"),
            // rs.getDate("DATAFED").toLocalDate(), rs.getDate("DATAFAM").toLocalDate()));

            System.out.println(getLocalDate(rs, "DATAFED"));

            clients.add(new Client(rs.getString("dni"), rs.getString("nom"), rs.getString("cognom"),
                    rs.getInt("telefon"), rs.getString("email"), rs.getString("num_fam"), rs.getString("num_federacio"),
                    rs.getInt("nivell"), getLocalDate(rs, "DATAFED"), getLocalDate(rs, "DATAFAM")));
        }
    }

    static public LocalDate getLocalDate(ResultSet rs, String columnName) throws SQLException {
        Date sqlDate = rs.getDate(columnName);
        return sqlDate == null ? null : sqlDate.toLocalDate();
    }

}