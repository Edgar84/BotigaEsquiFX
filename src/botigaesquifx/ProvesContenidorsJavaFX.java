package botigaesquifx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//

public class ProvesContenidorsJavaFX extends Application {

    private static ConnexioBD conn = new ConnexioBD();

    static Connection connexioBD;

    static ArrayList<Client> Clients = new ArrayList<>();
    static ArrayList<CursIndividual> ci = new ArrayList<>();
    static ArrayList<CursColectiu> cc = new ArrayList<>();
    static ArrayList<CursCompeticio> Ccompeticio = new ArrayList<>();

    Label lblNom;
    Label lblCognom;
    Label lblDni;

    TextField txtNom;
    TextField txtCognom;
    TextField txtDni;

    Label lblNomCurs;
    Label lblIdCurs;

    TextField txtNomCurs;
    TextField txtIdCurs;

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
        escenari.setMinWidth(300);

        escenari.show();
    }

    private Pane partSuperior() {
        HBox hb = new HBox();
        hb.getChildren().addAll(new Label("Titol"));
        hb.setAlignment(Pos.CENTER);
        return hb;
    }

    private Pane lateralEsquerre() throws SQLException {
        VBox vlateral = new VBox();

        vlateral.setAlignment(Pos.CENTER);

        TableView<Client> tblClients = new TableView<>();
        TableColumn<Client, String> colDni = new TableColumn<>("DNI");
        TableColumn<Client, String> colNom = new TableColumn<>("Nom");
        TableColumn<Client, String> colCognom = new TableColumn<>("Cognom");
        // TableColumn<Client, String> colEmail = new TableColumn<>("Email");
        // TableColumn<Client, int> coltelefon = new TableColumn<>("Telefon");

        tblClients.getColumns().addAll(colDni, colNom, colCognom);
        vlateral.getChildren().add(tblClients);

        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colCognom.setCellValueFactory(new PropertyValueFactory<>("cognom"));

        for (Client cl : Clients) {

            tblClients.getItems().add(cl);
        }

        vlateral.getChildren().addAll(new Label("CLIENTS"));

        tblClients.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Client client = (Client) newValue;

                if (client != null) {
                    txtDni.setText(client.getDni());
                    txtNom.setText(client.getNom());
                    txtCognom.setText(client.getCognom());
                }
            }
        });

        return vlateral;

    }

    private VBox formulariCentral() {
        VBox vlateral = new VBox();
        vlateral.setAlignment(Pos.CENTER);
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);

        lblNom = new Label("Nom");
        lblCognom = new Label("Cognom");
        lblDni = new Label("Dni");
        txtNom = new TextField();
        txtCognom = new TextField();
        txtDni = new TextField();

        gp.add(lblNom, 0, 0);
        gp.add(txtNom, 1, 0);
        gp.add(lblCognom, 0, 1);
        gp.add(txtCognom, 1, 1);
        gp.add(lblDni, 0, 2);
        gp.add(txtDni, 1, 2);

        lblNomCurs = new Label("Nom");
        lblIdCurs = new Label("ID");

        txtNomCurs = new TextField();
        txtIdCurs = new TextField();

        gp.add(lblNomCurs, 0, 8);
        gp.add(txtNomCurs, 1, 8);
        gp.add(lblIdCurs, 0, 9);
        gp.add(txtIdCurs, 1, 9);

        // return gp;

        vlateral.getChildren().add(gp);
        return vlateral;

    }

    private GridPane formulariCentral2() {
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);

        lblNom = new Label("Nom");
        lblCognom = new Label("Cognom");
        lblDni = new Label("Dni");
        txtNom = new TextField();
        txtCognom = new TextField();
        txtDni = new TextField();

        gp.add(lblNom, 0, 0);
        gp.add(txtNom, 1, 0);
        gp.add(lblCognom, 0, 1);
        gp.add(txtCognom, 1, 1);
        gp.add(lblDni, 0, 2);
        gp.add(txtDni, 1, 2);
        return gp;
    }

    private Pane partInferior() {
        Button btnNeteijar = new Button("Neteijar");

        btnNeteijar.setOnAction(e -> neteijarFormulari());

        Button btn2 = new Button("Boto 2");
        Button btn3 = new Button("Boto 3");
        HBox hinferior = new HBox();
        hinferior.getChildren().addAll(btnNeteijar, btn2, btn3, new Label("Barra informació interior"));

        hinferior.setAlignment(Pos.CENTER);
        return hinferior;
    }

    // private Pane lateralDret() {
    // VBox vb = new VBox();
    // Label lblDret = new Label("Lateral dret");
    // vb.setAlignment(Pos.CENTER);
    // vb.getChildren().addAll(lblDret);
    // return vb;
    // }

    private Object neteijarFormulari() {
        txtCognom.setText("");

        txtNom.setText("");
        txtCognom.setText("");
        txtDni.setText("");
        System.out.println(txtNomCurs.getText());
        txtNomCurs.setText("");
        txtIdCurs.setText("");
        return lblCognom;
    }

    private Pane lateralDret() {

        VBox vb = new VBox();
        Label lblDret = new Label("lateral Dret");

        vb.setPadding(new Insets(10.0));
        vb.setSpacing(10.0);
        vb.setAlignment(Pos.CENTER);

        TabPane tp = new TabPane();

        Tab tab1 = new Tab("Colectius", cursosColectius());
        Tab tab2 = new Tab("Competicio", cursosCometicio());
        Tab tab3 = new Tab("Individual", cursosIndividuals());

        tp.getTabs().add(tab1);
        tp.getTabs().add(tab2);
        tp.getTabs().add(tab3);

        vb.getChildren().addAll(lblDret, tp);
        return vb;
    }

    private Pane cursosColectius() {

        VBox vlateral = new VBox();
        vlateral.getChildren().addAll(new Label("CURSOS"));
        vlateral.setAlignment(Pos.CENTER);

        TableView<CursColectiu> tblClients = new TableView<>();

        TableColumn<CursColectiu, String> colNom = new TableColumn<>("NOM");
        TableColumn<CursColectiu, String> colDia = new TableColumn<>("DIA");
        TableColumn<CursColectiu, String> colPreuFinal = new TableColumn<>("Preu Final");

        tblClients.getColumns().addAll(colNom, colDia, colPreuFinal);
        vlateral.getChildren().add(tblClients);

        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colDia.setCellValueFactory(new PropertyValueFactory<>("idDia"));
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

        for (CursCompeticio CC : Ccompeticio) {

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
        // TableColumn<CursCompeticio, String> colId = new TableColumn<>("ID");
        TableColumn<CursIndividual, String> colNom = new TableColumn<>("NOM");
        TableColumn<CursIndividual, String> colPreuHora = new TableColumn<>("Preu Hora");

        // TableColumn<Client, String> colEmail = new TableColumn<>("Email");
        // TableColumn<Client, int> coltelefon = new TableColumn<>("Telefon");

        tblClients.getColumns().addAll(colNom, colPreuHora);
        vlateral.getChildren().add(tblClients);

        // colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
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
            Ccompeticio.add(new CursCompeticio(rs.getInt("id"), rs.getString("nom"), rs.getString("dni_monitor"),
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

    private static void consultarCursosColectiu() throws SQLException {

        connexioBD = conn.getConnexioBD();

        String SQL = "select * from curs,curs_colectiu where curs.id = curs_colectiu.id;";

        PreparedStatement ps = connexioBD.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            cc.add(new CursColectiu(rs.getInt("id"), rs.getString("nom"), rs.getString("dni_monitor"),
                    rs.getInt("id_dia"), rs.getInt("preu_final")));
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

            Clients.add(new Client(rs.getString("dni"), rs.getString("nom"), rs.getString("cognom"),
                    rs.getInt("telefon"), rs.getString("email"), rs.getString("num_fam"), rs.getString("num_federacio"),
                    rs.getInt("nivell"), getLocalDate(rs, "DATAFED"), getLocalDate(rs, "DATAFAM")));
        }
    }

    static public LocalDate getLocalDate(ResultSet rs, String columnName) throws SQLException {
        Date sqlDate = rs.getDate(columnName);
        return sqlDate == null ? null : sqlDate.toLocalDate();
    }

}