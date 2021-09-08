package ru.vsu.csf.Sashina;

import java.util.ArrayList;
import java.util.List;

public class Logic {

    public static Figure saveFromArrayToFigure (int [][] array) {
        if (array.length == 0) {
            return null;
        } else {
            List<Point> list = new ArrayList<>();
            for (int j = 0; j < array[0].length; j++) {
                list.add(new Point(array[0][j], array[1][j]));
            }
            Figure f = new Figure(list);
            return f;
        }
    }
}