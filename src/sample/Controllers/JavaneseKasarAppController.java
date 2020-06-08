package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Helpers.DataBahasa;
import sample.Helpers.DbConnect;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author Farkhan Hamzah Firdaus
 * @project ProjectPBOKamus
 * @created 15/05/2020 - 4:36 AM
 */
public class JavaneseKasarAppController implements Initializable {

    @FXML
    private TableView<DataBahasa> table;

    @FXML
    private TableColumn<DataBahasa, String> col_jawa;

    @FXML
    private TableColumn<DataBahasa, String> col_bahasa;

    @FXML
    private TextField filter;

    @FXML
    void Beranda(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Views/Beranda.fxml"));
        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    void Halus(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Views/JavaneseHalusApp.fxml"));
        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    void Jawa(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Views/BahasaHalusApp.fxml"));
        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    void TentangKami(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Views/TentangKami.fxml"));
        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    double x = 0, y = 0;

    @FXML
    void dragged(MouseEvent event) {
        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setX(event.getScreenX()-x);
        stage.setY(event.getScreenY()-y);
    }

    @FXML
    void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }


    ObservableList<DataBahasa> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try{
            Connection connection = DbConnect.getConnection();

            ResultSet resultSet = connection.createStatement().executeQuery("select * from bahasakasar");

            while (resultSet.next()){
                observableList.addAll(new DataBahasa(resultSet.getString("Jawa"),resultSet.getString("Bahasa")));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        col_jawa.setCellValueFactory(new PropertyValueFactory<>("jawa"));
        col_bahasa.setCellValueFactory(new PropertyValueFactory<>("bahasa"));

        FilteredList<DataBahasa> filteredList = new FilteredList<>(observableList, dataBahasa -> true);

        filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(dataBahasa -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (dataBahasa.getJawa().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<DataBahasa> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedList);
    }
}
