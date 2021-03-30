package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;

import static javafx.scene.paint.Color.*;

public class Controller {
    @FXML
    private Canvas mainCanvas;
    @FXML
    public GraphicsContext gc;

    float num = 1.0f;

    @FXML
    public void initialize() {
        gc = mainCanvas.getGraphicsContext2D();
        List<Float> closingAmount = downloadStockPrices("GOOG");
        List<Float> closingAmount1 = downloadStockPrices("AAPL");
        drawLinePlot(closingAmount, closingAmount1);
    }

    public List<Float> downloadStockPrices(String stockTicker) {
        String url = "https://query1.finance.yahoo.com/v7/finance/download/" +
                stockTicker + "?period1=1262322000&period2=1451538000&interval=1mo&events=history&includeAdjustedClose=true";
        int count = 0;
        List<Float> closingAmount = new ArrayList<>();

        //Date,Open,High,Low,Close,Adj Close,Volume
        try {
            InputStream input = new URL(url).openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            String line;

            while((line = reader.readLine()) != null) {
                count++;
                //System.out.println(line);
                String columns[] = line.split(",");
                if (count != 1) {
                    //System.out.println(columns[4]);
                    closingAmount.add(Float.parseFloat(columns[4]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println("Printing List: " + closingAmount);
        //System.out.println(count);

        return closingAmount;
    }

    private float summingArrays(List<Float> floaty) {
        float sum = 0.0f;

        for (int i = 0; i < floaty.size(); i++) {
            sum += floaty.get(i);
        }

        return sum;
    }

    public void plotLine(List<Float> listy, Color color) {
        gc = mainCanvas.getGraphicsContext2D();
        gc.setStroke(color);
        //System.out.println(listy.get(0));


        float maxVal = listy.get(0);
        float minVal = listy.get(0);
        //finds the max and min values in the hosing data used for scaling
        //changed to not use pos and neg infinity
        for (int i = 0; i < listy.size(); i++) {
            float val = listy.get(i);
            //System.out.println(val);
            if (val > maxVal)
                maxVal = val;
            if (val < minVal)
                minVal = val;
        }

        //System.out.println(maxVal);
        //System.out.println(minVal);

        //width of canvas / number of bars. so the bar graph fits
        float barWidth = 200f / (listy.size());

        //starting position, 0 to x. left to right
        float x = 50f;
        float yTemp = listy.get(0);

        //iterating through data set
        for (int k = 1; k < listy.size(); k++){
            //scaling the bar's height
            //double barHeight = ((val - minVal) / (maxVal - minVal)) * h;
            float val = listy.get(k);
            float barHeight = ((val / maxVal) * 175) / num;
            float barHeightTemp = ((yTemp / maxVal) * 175) / num;
            //x position, height of canvas - barheight, bar width, barheight
            //y value is like that since top left is 0,0.
            gc.strokeLine(x, (225 - barHeightTemp), x+barWidth, (225-barHeight));
            //keep moving to the right
            x += barWidth;
            yTemp = val;
        }
    }


    public void drawLinePlot(List<Float> closingPrices1, List<Float> closingPrices2) {
        gc = mainCanvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.strokeLine(50,225,250,225);
        gc.strokeLine(50,225,50,50);

        plotLine(closingPrices1, Color.RED);
        num = (summingArrays(closingPrices1)/summingArrays(closingPrices2));
        plotLine(closingPrices2, Color.BLUE);
    }
}