package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.ClassicalRungeKuttaIntegrator;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    double a;
    double b;
    double c;
    double d;

    @FXML
    private LineChart<Number, Number> chartV;

    @FXML
    private LineChart<Number, Number> chartU;

    @FXML
    private LineChart<Number, Number> chartI;

    @FXML
    private Text txtChoosen;

    @FXML
    private TextField ftxtI;

    @FXML
    private Text txtA;

    @FXML
    private Text txtB;

    @FXML
    private Text txtC;

    @FXML
    private Text txtD;

    @FXML
    private Button btnStart;

    @FXML
    private RadioButton radBtnRS;

    @FXML
    private ToggleGroup models;

    @FXML
    private RadioButton radBtnRZ;

    @FXML
    private RadioButton radBtnIB;

    @FXML
    private RadioButton radBtnCH;

    @FXML
    private RadioButton radBtnFS;

    @FXML
    private RadioButton radBtnLTS;

    @FXML
    private RadioButton radBtnTC;

    @FXML
    private RadioButton radBtnOWN;

    @FXML
    private Button btnChoiceOK;

    @FXML
    private TextField ftxtA;

    @FXML
    private TextField ftxtB;

    @FXML
    private TextField ftxtC;

    @FXML
    private TextField ftxtD;

    @FXML
    void choiceOK(ActionEvent event) {


        try {
            a = Double.parseDouble(ftxtA.getText());
            b = Double.parseDouble(ftxtB.getText());
            c = Double.parseDouble(ftxtC.getText());
            d = Double.parseDouble(ftxtD.getText());

            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, "Conajmniej jedna podana wartość nie jest liczbą.").showAndWait();

            return;
        }


    }

    @FXML
    void startBtnPressed(ActionEvent event) {

        RadioButton choosen = (RadioButton) models.getSelectedToggle();
        txtChoosen.setText(choosen.getText());

        switch (choosen.getId()) {

            case "radBtnOWN": {
                new Alert(Alert.AlertType.CONFIRMATION, "Set your own properties?", new ButtonType("OK", ButtonBar.ButtonData.YES), new ButtonType("CANCEL", ButtonBar.ButtonData.CANCEL_CLOSE)).showAndWait().ifPresent(response -> {
                    System.out.println("switch:");

                    switch (response.getButtonData()) {
                        case CANCEL_CLOSE: {
                            txtChoosen.setText("none");
                            break;
                        }

                        case YES: {
                            System.out.println("OK");
                            newScene("properties.fxml", 250, 200);
                            break;
                        }
                        default: {
                            System.out.println("no choice");
                        }
                    }
                });
                break;
            }
            case "radBtnTC": {

                a = 0.02;
                b = 0.25;
                c = -65;
                d = 0.05;
                break;
            }
            case "radBtnLTS": {

                a = 0.02;
                b = 0.25;
                c = -65;
                d = 2;
                break;
            }
            case "radBtnFS": {

                a = 0.1;
                b = 0.2;
                c = -65;
                d = 2;
                break;
            }
            case "radBtnCH": {

                a = 0.02;
                b = 0.2;
                c = -50;
                d = 2;
                break;
            }

            case "radBtnIB": {

                a = 0.02;
                b = 0.2;
                c = -55;
                d = 4;
                break;
            }

            case "radBtnRZ": {

                a = 0.1;
                b = 0.25;
                c = -65;
                d = 2;
                break;
            }
            case "radBtnRS": {

                a = 0.02;
                b = 0.2;
                c = -65;
                d = 8;
                break;
            }

        } // end of switch

        txtA.setText("a:\t" + a);
        txtB.setText("b:\t" + b);
        txtC.setText("c:\t" + c);
        txtD.setText("d:\t" + d);

        chartV.getData().clear();
        chartU.getData().clear();
        chartI.getData().clear();

        FirstOrderDifferentialEquations calculate = new Calculate(a, b, Double.parseDouble(ftxtI.getText()));

        FirstOrderIntegrator integrator = new ClassicalRungeKuttaIntegrator(0.01);
        Trajectory trajectory = new Trajectory();
        integrator.addStepHandler(trajectory);

        TurningPoint turningPoint = new TurningPoint(c, d);
        integrator.addEventHandler(turningPoint, 0.01, 0.001, 2000);
        double[] vu = {c, b * c};
        integrator.integrate(calculate, 0,vu,3,vu);

    }

    private void newScene(String fxmlName, int width, int height) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(fxmlName));

            Scene scene = new Scene(fxmlLoader.load(), width, height);
            Stage stage = new Stage();

            stage.setTitle("");
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setScene(scene);

            stage.show();
            //  ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

}
