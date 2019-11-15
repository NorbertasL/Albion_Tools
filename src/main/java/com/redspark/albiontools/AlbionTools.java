package com.redspark.albiontools;

import com.redspark.albiontools.herlpers.IdReader;
import com.redspark.albiontools.pricer.ImageScanner;
import com.redspark.albiontools.pricer.ItemPricer;
import net.sourceforge.tess4j.Tesseract;

import java.util.Scanner;

public class AlbionTools {
    private static boolean run = true;

    public static void main(String[] args) {

        System.out.println("Loading Item ID list...");
        //Pre-loading Item list
        IdReader.request();
        System.out.println("Done");


        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Albion Tools by Red_Spark");
        System.out.println("Pick a tool you would like to use:");
        System.out.println("1 : Price Checker");
        System.out.println("2 : Farmer Tool");


        String input;
        while(run){

//            //Testing
//            System.out.println("Taking pic");
//            ImageScanner imageScanner = new ImageScanner();
//            imageScanner.getTitle();
//            imageScanner.getSlot();
//            System.out.println("Done");

            input = scanner.nextLine();

            if(input.length() == 0){

                //command inputs start with '-'
            }else if(input.charAt(0)=='-'){

                switch (input.split(" ")[0]){
                    case "-e": case "-exit":
                        run = false;
                        AlbionTools.stopApp();
                        break;
                    case "-h": case "-help":
                        System.out.println("TODO");
                        break;
                    default:
                        System.out.println("Unknown command");
                        System.out.println("Use -h or -help for a list of commands");
                        System.out.println("Use -e or -exit to exit");
                        break;
                }

                //Normal input
            }else {
                switch(input){
                    case "1":
                        new ItemPricer(scanner);
                        break;

                    case "2":

                        //TODO
                        //new FarmerTool(scanner);
                        break;
                    default:
                        System.out.println("Unknown command");
                        System.out.println("Use -h or -help for a list of commands");
                        System.out.println("Use -e or -exit to exit");

                }

            }

        }
        System.out.println("Thank you for using Albion Tools");



    }
    public static void stopApp(){
        run = false;
    }

}