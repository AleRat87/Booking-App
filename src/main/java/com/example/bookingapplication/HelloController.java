package com.example.bookingapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;
import javafx.scene.control.ChoiceBox;


public class HelloController {

    @FXML
    public TextField usernameTextField;
    @FXML
    public TextField passwordTextField;
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField addressTextField;
    @FXML
    public TextField phoneNumberTextField;
    @FXML
    public ChoiceBox<String> roleChoiceBox = new ChoiceBox<>();
    @FXML
    public TextField errorField;
    @FXML
    private PasswordField hiddenPasswordTextField;
    @FXML
    private CheckBox showPassword;

    File file = new File("data.csv");

    //Map containing <Username, Password>
    HashMap<String, String> loginInfo = new HashMap<>();

    Encrypt encryptor = new Encrypt();

    @FXML
    void changeVisibility(ActionEvent event) {
        if (showPassword.isSelected()) {
            passwordTextField.setText(hiddenPasswordTextField.getText());
            passwordTextField.setVisible(true);
            hiddenPasswordTextField.setVisible(false);
            return;
        }
        hiddenPasswordTextField.setText(passwordTextField.getText());
        hiddenPasswordTextField.setVisible(true);
        passwordTextField.setVisible(false);
    }

    @FXML
    void loginHandler(ActionEvent event) throws IOException, NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        String username = usernameTextField.getText();
        String password = getPassword();
        //updateLoginUsernamesAndPasswords();
        if(checkingCredentialsLogin(username, password))
        {
            roleChoiceBox.getItems().addAll("Host", "Costumer");
            roleChoiceBox.getSelectionModel().select(0);
            if (roleChoiceBox.getValue().equals("Host"))
            {
                HostPage(event);
            }
            else if(roleChoiceBox.getValue().equals("Customer"))
            {
                CustomerPage(event);
            }
        }
        else errorField.setVisible(true);

    }

    private String getPassword(){
        if(passwordTextField.isVisible()){
            return passwordTextField.getText();
        } else {
            return hiddenPasswordTextField.getText();
        }
    }
    public boolean checkingCredentialsLogin(String username, String password)throws IOException, NoSuchAlgorithmException
    {
        Scanner scanner = new Scanner(file);
        if(username == null || username.length() == 0)
            return false;
        if(password == null || password.length() == 0)
            return false;
        while (scanner.hasNext()){
            String[] usernameAndPassword = scanner.nextLine().split(",");
            loginInfo.put(usernameAndPassword[0],usernameAndPassword[1]);
            if(usernameAndPassword[0].equals(username))
            {
                if(encryptor.encryptString(password).equals(usernameAndPassword[1]))
                    return true;
                else return false;
            }
        }
        return false;
    }

    public boolean checkingCredentialsRegister(String username, String password, String name, String address, String phone_nr)throws IOException, NoSuchAlgorithmException
    {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()){
            String[] usernameAndPassword = scanner.nextLine().split(",");
            loginInfo.put(usernameAndPassword[0],usernameAndPassword[1]);
            if(usernameAndPassword[0].equals(username))
                return false;
            if(username == null || username.length() == 0)
                return false;
            if(password == null || password.length() == 0)
                return false;
            if(name == null || name.length() < 3)
                return false;
            if(address == null || address.length() < 3)
                return false;
            if(phone_nr == null || phone_nr.length() != 10)
                return false;
        }
        return true;
    }
    @FXML
    void createAccount(ActionEvent event) throws  IOException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        String username = usernameTextField.getText();
        String password = getPassword();
        String name = nameTextField.getText();
        String address = addressTextField.getText();
        String phone_nr = phoneNumberTextField.getText();

        if(checkingCredentialsRegister(username, password, name, address, phone_nr)) {
            writeToFile(username, password);
            LoginPage(event);
        }
        else {
            RegisterPage(event);
        }

    }


    private void updateLoginUsernamesAndPasswords() throws IOException {
        Scanner scanner = new Scanner(file);
        loginInfo.clear();
        loginInfo = new HashMap<>();
        while (scanner.hasNext()){
            String[] usernameAndPassword = scanner.nextLine().split(",");
            loginInfo.put(usernameAndPassword[0],usernameAndPassword[1]);
        }
    }


    private void writeToFile(String username, String password) throws IOException, NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(username + "," + encryptor.encryptString(password) + "\n");
            writer.close();
    }
    @FXML
    public void RegisterPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterPage.fxml"));
        Parent registerPage = loader.load();
        Scene scene = new Scene(registerPage);

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void LoginPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        Parent loginPage = loader.load();
        Scene scene = new Scene(loginPage);

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void HostPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HostPage.fxml"));
        Parent hostPage = loader.load();
        Scene scene = new Scene(hostPage);

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void CustomerPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerPage.fxml"));
        Parent customerPage = loader.load();
        Scene scene = new Scene(customerPage);

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
