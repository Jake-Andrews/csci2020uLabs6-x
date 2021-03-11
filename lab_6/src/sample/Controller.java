package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class Controller {
    @FXML
    private Canvas mainCanvas;
    @FXML
    public GraphicsContext gc;

    private static double[] avgHousingPricesByYear = {
            247381.0,264171.4,287715.3,294736.1, 308431.4,322635.9,340253.0,363153.7
    };

    private static double[] avgCommercialPricesByYear = {
            1121585.3,1219479.5,1246354.2,1295364.8, 1335932.6,1472362.0,1583521.9,1613246.3
    };

    private static String[] ageGroups = {
            "18-25", "26-35", "36-45", "46-55", "56-65", "65+"
    };

    private static int[] purchasesByAgeGroup = {
            648, 1021, 2453, 3173, 1868, 2247
    };

    private static Color[] pieColours = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE, Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
    };

    private double summingArrays(double [] ran) {
        double sum = 0.0;

        for (double val: ran) {
            sum += val;
        }

        return sum;
    }

    private int summingArrays(int [] ran) {
        int sum = 0;

        for (int val: ran) {
            sum += val;
        }

        return sum;
    }

    @FXML
    public void initialize() {
        gc = mainCanvas.getGraphicsContext2D();
        drawRectangles(150, 275, avgHousingPricesByYear, Color.RED, avgCommercialPricesByYear, Color.BLUE);
        drawPieChart(300, 275, pieColours, purchasesByAgeGroup);
    }

    public void drawPieChart(int w, int h, Color[] pieColours, int [] purchasesByAgeGroup) {
        gc = mainCanvas.getGraphicsContext2D();
        int sum = summingArrays(purchasesByAgeGroup);
        double previousAngle = 0;

        for (int i = 0; i < purchasesByAgeGroup.length; i++) {
            gc.setFill(pieColours[i]);
            //number in list / sum of list * 360 for degrees in circle
            double percentageOfPie = ((double)purchasesByAgeGroup[i]/sum)*360;

            gc.fillArc(w/2, h/3, 150, 150, previousAngle, percentageOfPie, ArcType.ROUND);
            //So the next part of the pie graph starts where the last one left off.
            previousAngle += percentageOfPie;
        }
    }

    public void drawRectangles(int w, int h, double[] housingData, Color red, double[] commercialData, Color blue) {
        //finding the scaling value for the second bar graph
        double scalingValue = 0.0;
        scalingValue = summingArrays(commercialData) / summingArrays(housingData);
        //seting colour
        gc.setFill(blue);

        double maxVal = commercialData[0];
        double minVal = commercialData[0];
        //finds the max and min values in the hosing data used for scaling
        //changed to not use pos and neg infinity
        for (double val : commercialData) {
            if (val > maxVal)
                maxVal = val;
            if (val < minVal)
                minVal = val;
        }

        //starting position, 0 to x. left to right
        double x = 0;
        //width of canvas / number of bars. so the bar graph fits
        double barWidth = w / commercialData.length;

        //iterating through data set
        for (double val : commercialData) {
            //scaling the bar's height
            //double barHeight = ((val - minVal) / (maxVal - minVal)) * h;
            double barHeight = (val/maxVal) * h;
            //x position, height of canvas - barheight, bar width, barheight
            //y value is like that since top left is 0,0.
            //barWidth / 2.5 for spacing
            gc.fillRect(x, (h-barHeight), barWidth/2.5, barHeight);

            //keep moving to the right
            x += barWidth;
        }

        gc.setFill(red);

        //used later on for scaling
        maxVal = housingData[0];
        minVal = housingData[0];
        //finds the max and min values in the hosing data
        //changed to not use pos and neg infinity
        for (double val : housingData) {
            if (val > maxVal)
                maxVal = val;
            if (val < minVal)
                minVal = val;
        }

        //width of canvas / number of bars. so the bar graph fits
        barWidth = w / housingData.length;

        x = -barWidth/2;
        for (double val : housingData) {
            //scaling value is, sum of commerical data / sum of housing data
            //the proportion that commericaldata is larger than housing data
            double barHeight = ((val/maxVal) * h) / scalingValue;
            gc.fillRect(x, (h-barHeight), barWidth/2.5, barHeight);
            x += barWidth;
        }
    }
}