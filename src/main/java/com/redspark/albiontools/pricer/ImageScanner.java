package com.redspark.albiontools.pricer;



import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageScanner {

    private Rectangle nameArea, slotArea, qualityArea, slotArea2, itemPowerArea, weightArea;
    private Robot robot;
    private Tesseract tesseract;

    private BufferedImage capture;
    public ImageScanner(){
        try {
            robot = new Robot();
        }catch (AWTException e){
            System.out.println(e);
        }
        tesseract = new Tesseract();
        tesseract.setDatapath("X:\\Programming\\External Lib");
        nameArea = new Rectangle(845,300,330,22);
        slotArea = new Rectangle(nameArea.x, nameArea.y+53, nameArea.width/2, nameArea.height);



    }

    public static void Capture() throws AWTException, IOException, TesseractException {
        Rectangle screenRect = new Rectangle(845,300,330,30);
        BufferedImage capture = new Robot().createScreenCapture(screenRect);
        ImageIO.write(capture, "bmp", new File("X:\\Programming\\External Lib\\Test.bmp"));
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("X:\\Programming\\External Lib");
        System.out.println(tesseract.doOCR(capture));

    }
    public String getTitle(){
        capture = robot.createScreenCapture(nameArea);
        writeImage("Name");
        String name = getText(capture);
        name = name.isEmpty() ? "None" : trimString(name);
        System.out.println("Item Name:"+name);
        return name;
    }
    public String getSlot(){
        capture = robot.createScreenCapture(slotArea);
        String slot = getText(capture);
//        if(slot.isEmpty()){
//            capture = robot.createScreenCapture(slotArea2);
//        }
        writeImage("Slot");
        slot = slot.isEmpty() || slot.split(":").length < 2 ? "None" : trimString(slot.split(":")[1]);
        System.out.println("Item Slot:"+slot);
        return slot;
    }
    private String getText(BufferedImage img){
        try {
            return tesseract.doOCR(img);
        }catch (TesseractException e){
            System.out.println(e);
        }
        return null;
    }
    private void writeImage(String fileName){
        try {
            ImageIO.write(capture, "bmp", new File("X:\\Programming\\Albion Tools\\DEBUG STUFF\\"+fileName+".bmp"));
        }catch (Exception e){
            System.out.println(e);
        }
    }
    private String trimString(String s){
        return s.replaceAll("[\\\r\\\n]+","").trim();
    }
}
