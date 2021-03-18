package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;

import java.awt.*;
import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class Controller {
    @FXML
    private Canvas mainCanvas;
    @FXML
    public GraphicsContext gc;

    //tree map for storing weather events and how many times they occur in the file
    private static TreeMap<String, Integer> tree_map = new TreeMap<String, Integer>();

    private static Color[] pieColours = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE, Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
    };

    private int summingTreeMap(TreeMap<String, Integer> map) {
        int sum = 0;

        for (Map.Entry<String, Integer> element : tree_map.entrySet())
            sum += element.getValue();

        return sum;
    }

    @FXML
    public void initialize() {
        gc = mainCanvas.getGraphicsContext2D();
        File file = new File("resources/weatherwarnings-2015.csv");
        readFile(file);
        drawPieChart(300, 275);
        drawLegend();
    }

    public void readFile(File file) {

        //file manipulation
        try{
            FileReader fileInput = new FileReader(file);
            BufferedReader input = new BufferedReader(fileInput);

            //weather events are on the 5th entry
            int columnIndex = 5;

            //put the events into a map, if event exists, increase counter
            int count = 0;
            String line;
            while((line = input.readLine()) != null){
                String[] data = line.split(",");
                String key = (data[columnIndex]);
                if (tree_map.containsKey(key)) {
                    int value = tree_map.get(key) + 1;
                    tree_map.put(key, value);
                }else {tree_map.put(key, 1);}
            }

            input.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(tree_map);
    }
    //need to have ints to draw each part of the pie.
    public void drawPieChart(int w, int h) {
        gc = mainCanvas.getGraphicsContext2D();
        int sum = summingTreeMap(tree_map);
        double previousAngle = 0;

        int counter = 0;
        for (Map.Entry<String, Integer> element : tree_map.entrySet()) {
            gc.setFill(pieColours[counter]);
            //number in list / sum of list * 360 for degrees in circle
            double percentageOfPie = ((double)element.getValue()/sum)*360;

            gc.fillArc(w/1.8, h/3, 125, 125, previousAngle, percentageOfPie, ArcType.ROUND);
            //So the next part of the pie graph starts where the last one left off.
            previousAngle += percentageOfPie;
            counter++;
        }
    }
    //to do, draw rectangles, fill rectangles with pieColours, then get key name (string) and put that beside it.
    public void drawLegend() {
        gc = mainCanvas.getGraphicsContext2D();
        int counter = 0;
        double x = 25;
        double y = 100;

        for (Map.Entry<String, Integer> element : tree_map.entrySet()) {
            gc.setFill(Color.BLACK);
            //font fize was much too large otherwise
            gc.setFont(Font.font ("TimesNewRoman", 8));
            gc.fillText(element.getKey(),x+40, y+12);
            gc.setFill(pieColours[counter]);
            gc.fillRect(x,y,30,20);
            counter++;
            y += 30;
        }
    }
}