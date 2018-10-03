/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alvarogalia.creapruebas;

import com.alvarogalia.creapruebas.utils.BoxBlurFilter;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;

/**
 *
 * @author Alvaro
 */
public class CreaSetdePruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PrintWriter pw = null;
        PrintWriter pw2 = null;
        try {
            pw = new PrintWriter(new FileWriter("results.properties"));
            pw2 = new PrintWriter(new FileWriter("info.lst"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Image fondo = Toolkit.getDefaultToolkit().getImage("patente.png");

        int limit = 500;
        int contador = 0;
        int width = 360;
        int height = 130;

        String[] primeraLetra = {"G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "V", "W", "X", "Y", "Z"};
        String[] segundaLetra = {"B", "C", "D", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "V", "W", "X", "Y", "Z"};
        String[] terceraLetra = {"B", "C", "D", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "V", "W", "X", "Y", "Z"};
        String[] cuartaLetra = {"B", "C", "D", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "V", "W", "X", "Y", "Z"};
        String[] primerNumero = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] segundoNumero = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

        for (int ii = 1; ii < limit; ii++) {

            int l1 = (int) (Math.random() * 16);
            int l2 = (int) (Math.random() * 20);
            int l3 = (int) (Math.random() * 20);
            int l4 = (int) (Math.random() * 20);
            int l5 = (int) (Math.random() * 9);
            int l6 = (int) (Math.random() * 9);

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2d = image.createGraphics();

            int a = (int) (Math.random() * 256);
            int r = (int) (Math.random() * 256);
            int g = (int) (Math.random() * 256);
            int b = (int) (Math.random() * 256);
            r = a;
            g = a;
            b = a;

            for (int y = 0; y < height; y++) {

                for (int x = 0; x < width; x++) {

                    int p = (a << 24) | (r << 16) | (g << 8) | b;

                    image.setRGB(x, y, p);
                }
            }

            double random = Math.random();
            while (random < 0.2 || random > 0.6) {
                random = Math.random();
            }

            double plateWidth = (int) (int) (random * 600);
            double plateHeight = (int) (plateWidth / (2.769230769230769));

            random = Math.random();
            while (random < 0.1 || random > 0.9) {
                random = Math.random();
            }

            double fromX = (double) (random * (width - plateWidth));
            double fromY = (double) (random * (height - plateHeight));

            graphics2d.drawImage(fondo, (int) fromX, (int) fromY, (int) plateWidth, (int) plateHeight, null);
            graphics2d.dispose();

            Font font = new Font("FE-Font", Font.PLAIN, (int) (plateHeight / 1.8));

            graphics2d = image.createGraphics();
            graphics2d.setFont(font);
            graphics2d.setColor(Color.DARK_GRAY);

            String text = primeraLetra[l1];
            graphics2d.drawString(text, (int) (fromX + (plateWidth / 30) * 1), (int) (fromY + plateHeight / 1.95));
            text = segundaLetra[l2];
            graphics2d.drawString(text, (int) (fromX + (plateWidth / 30) * 5), (int) (fromY + plateHeight / 1.95));
            text = terceraLetra[l3];
            graphics2d.drawString(text, (int) (fromX + (plateWidth / 30) * 11), (int) (fromY + plateHeight / 1.95));
            text = cuartaLetra[l4];
            graphics2d.drawString(text, (int) (fromX + (plateWidth / 30) * 15.3), (int) (fromY + plateHeight / 1.95));
            text = primerNumero[l5];
            graphics2d.drawString(text, (int) (fromX + (plateWidth / 30) * 21.5), (int) (fromY + plateHeight / 1.95));
            text = segundoNumero[l6];
            graphics2d.drawString(text, (int) (fromX + (plateWidth / 30) * 25.5), (int) (fromY + plateHeight / 1.95));

            graphics2d.dispose();

            BoxBlurFilter filter = new BoxBlurFilter();
            filter.setHRadius(1);
            filter.setVRadius(2);
            filter.setIterations(1);
            filter.setRadius(2);
            image = filter.filter(image, image);

            try {
                File f = new File("patentes/clplate" + String.format("%03d", contador) + ".jpg");
                ImageIO.write(image, "jpg", f);
                pw.write("patentes/clplate" + String.format("%03d", contador) + ".jpg="
                        + primeraLetra[l1] + segundaLetra[l2]
                        + terceraLetra[l3] + cuartaLetra[l4]
                        + primerNumero[l5] + segundoNumero[l6] + "\n");
                pw2.write("patentes/clplate" + String.format("%03d", contador) + ".jpg 1 "
                        + (int) fromX + " " + (int) fromY + " "
                        + (int) plateWidth + " " + (int) plateHeight + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            contador++;
        }
        pw2.close();
        pw.close();
    }

}
