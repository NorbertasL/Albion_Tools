package com.redspark.albiontools.pricer;



import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageScanner {

    @SuppressWarnings("FieldCanBeLocal")
    private final String tessarachDataLocation= "X:\\Programming\\External Lib";

    @SuppressWarnings("unused")
    private Rectangle nameArea, slotArea, qualityArea, slotArea2, itemPowerArea, weightArea;
    private Robot robot;
    private Tesseract tesseract;

    private BufferedImage capture;
    ImageScanner(){
        try {
            robot = new Robot();
        }catch (AWTException e){
            e.printStackTrace();
        }
        tesseract = new Tesseract();
        tesseract.setDatapath(tessarachDataLocation);
        nameArea = new Rectangle(845,300,330,22);
        slotArea = new Rectangle(nameArea.x, nameArea.y+53, nameArea.width/2, nameArea.height);

    }

    String getTitle(){
        capture = robot.createScreenCapture(nameArea);
        writeImage("Name");
        String name = getText(capture);
        name = name == null || name.isEmpty() ? "None" : trimString(name);
        System.out.println("Item Name:"+name);
        return name;
    }
    @SuppressWarnings("unused")
    public String getSlot(){
        capture = robot.createScreenCapture(slotArea);
        String slot = getText(capture);

        //TODO scan second location
//        if(slot.isEmpty()){
//            capture = robot.createScreenCapture(slotArea2);
//        }
        writeImage("Slot");
        slot = slot == null
                || slot.isEmpty()
                || slot.split(":").length < 2 ? "None" : trimString(slot.split(":")[1]);
        System.out.println("Item Slot:"+slot);
        return slot;
    }
    private String getText(BufferedImage img){
        try {
            return tesseract.doOCR(img);
        }catch (TesseractException e){
            e.printStackTrace();
        }
        return null;
    }
    private void writeImage(String fileName){
        try {
            ImageIO.write(capture, "bmp", new File(fileName+".bmp"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private String trimString(String s){
        return s.replaceAll("[\r\n]+","").trim();
    }
}
