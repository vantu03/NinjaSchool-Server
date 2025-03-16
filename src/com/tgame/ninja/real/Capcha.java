package com.tgame.ninja.real;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.Random;

public class Capcha {

    public static String st = "<^>";

    public static String[] url = { "data/npcX1/", "data/npcX2/", "data/npcX3/", "data/npcX4/", "data/npcX4/", "data/npcX4/", "data/npcX4/" };

    public static String[] url1 = { "data/capcha/capchax1", "data/capcha/capchax2", "data/capcha/capchax3", "data/capcha/capchax4", "data/capchax4.png", "data/npcX4/", "data/npcX4/" };

    public static BufferedImage[][] allImgArrow = new BufferedImage[4][3];

    public byte[] byteImage;

    public String text;

    public int size;

    public int delta;

    public String typeImage;

    public int zoom;

    public static void loadImage() {
        try {
            for (int i = 0; i < 4; ++i) {
                for (int j = 0; j < 3; ++j) {
                    FileInputStream file = new FileInputStream(String.valueOf(Capcha.url1[i]) + j + ".png");
                    BufferedImage img = ImageIO.read(file);
                    Capcha.allImgArrow[i][j] = img;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Capcha(int zoom) {
        delta = 0;
        typeImage = "png";
        if (zoom < 0) {
            zoom = 0;
        }
        int[] size = { 12, 12, 18, 22, 26, 26, 26, 26, 26 };
        this.size = size[zoom];
        this.zoom = zoom;
        text = "";
        createText();
    }

    static int abs(int x) {
        return (x > 0) ? x : (-x);
    }

    private void createText() {
        Random rd = new Random();
        rd.setSeed(System.currentTimeMillis());
        for (int i = 0; i < 6; ++i) {
            int c = abs(rd.nextInt() % Capcha.st.length());
            text = String.valueOf(text) + Capcha.st.charAt(c);
        }
    }

    public BufferedImage resizeImage(BufferedImage img, int pc) {
        try {
            int w = img.getWidth();
            int h = img.getHeight();
            w -= w * pc / 100;
            h -= h * pc / 100;
            int type = (img.getTransparency() == 1) ? 1 : 2;
            BufferedImage bf = new BufferedImage(w, h, type);
            Graphics2D g = bf.createGraphics();
            g.drawImage(img, 0, 0, w, h, null);
            return bf;
        } catch (Exception ex) {
            return img;
        }
    }

    public byte[] createImage(int index, int pc) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            FileInputStream file = new FileInputStream(Capcha.url[zoom] + "219/" + index + ".png");
            BufferedImage img = ImageIO.read(file);
            Graphics g = img.getGraphics();
            g.setColor(Color.black);
            Font f = new Font("Tahoma", Font.BOLD, size);
            g.setFont(f);
            /*AffineTransform affinetransform = new AffineTransform();
            FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
            int ws = (int) f.getStringBounds(text, frc).getWidth();
            int hs = (int) f.getStringBounds(text, frc).getWidth();
            int w = img.getWidth();
            int h = img.getHeight();*/
            g.setColor(Color.YELLOW);
            int x = 0;
            for (int i = 0; i < text.length(); ++i) {
                for (int j = 0; j < Capcha.st.length(); ++j) {
                    if (text.charAt(i) == Capcha.st.charAt(j)) {
                        g.drawImage(resizeImage(Capcha.allImgArrow[zoom][j], pc), x, 15, null);
                        x += Capcha.allImgArrow[zoom][j].getWidth() + 1;
                        break;
                    }
                }
            }
            ImageIO.write(img, typeImage, os);
            return byteImage = os.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getText() {
        return text;
    }
}
