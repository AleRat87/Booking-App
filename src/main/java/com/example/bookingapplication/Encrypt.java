package com.example.bookingapplication;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
public class Encrypt {

    public String encryptString(String input) throws NoSuchAlgorithmException {

        //MessageDigest works with MD2, MD5, SHA-1, SHA-224, SHA-256
        //SHA-384 and SHA-512
        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] messageDigest = md.digest(input.getBytes());

        BigInteger bigInt = new BigInteger(1,messageDigest);

        return bigInt.toString(16);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Encrypt encryptor = new Encrypt();

        //String password = "monkey123";
        String hashedPas = "674f3c2c1a8a6f90461e8a66fb5550ba5678";

        Scanner scanner = new Scanner(System.in);

        System.out.println("Hey! Plz input your Password: \n");

        String userInput = scanner.nextLine();

        if(encryptor.encryptString(userInput).equals(hashedPas)){
            System.out.println("Correct! You are in!");
        } else{
            System.out.println("Wrong!!");
        }

    }
}


