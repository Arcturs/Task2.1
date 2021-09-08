package ru.vsu.csf.Sashina;

import javax.swing.*;

import ru.vsu.cs.util.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ListIterator;

public class FrameMain extends JFrame {

    private JPanel panelMain;
    private JTable coordinates;
    private JButton paint;
    private JLabel mistakeLabel;
    private JButton squareBut;
    private JLabel squareLabel;
    private JButton pBut;
    private JLabel pLabel;
    private JTextField xField;
    private JButton rectangleBut;
    private JTextField yField;
    private JButton moveBut;
    private JLabel rectMistakeLabel;
    private JLabel moveMistLabel;
    private JTextField scaleField;
    private JButton ScaleTopX;
    private JButton ScaleTopY;
    private JButton ScaleBotX;
    private JButton ScaleBotY;
    private JButton ScaleLeftX;
    private JButton ScaleLeftY;
    private JButton ScaleRightY;
    private JButton ScaleRightX;
    private JButton ScaleCenterX;
    private JButton ScaleCenterY;
    private JButton clearBut;
    private JLabel scaleMistLabel;
    private JLabel saveMistLabel;
    private JPanel figures;

    private int imageWidth = 600;
    private int imageHeight = 300;
    private int stepH = 150;
    private int stepW = 300;
    private int scale = 10;

    Figure fig = new Figure(new ArrayList<>());
    Figure addit = new Figure(new ArrayList<>());

    private void drawFigure (Graphics g, Color color, Figure f) {
        g.setColor(color);
        ListIterator<Point> lt = f.getCoordinates().listIterator();
        Point firstPoint = lt.next();
        Point prev = firstPoint;
        while (lt.hasNext()) {
            Point curr = lt.next();
            g.drawLine(prev.getX()*scale + stepW, prev.getY()*scale + stepH, curr.getX()*scale + stepW, curr.getY()*scale + stepH);
            prev = curr;
        }
        g.drawLine(firstPoint.getX()*scale + stepW, firstPoint.getY()*scale + stepH, prev.getX()*scale + stepW, prev.getY()*scale + stepH);
    }

    private void clearFigures (Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, imageWidth*10, imageHeight*10);
        g.setColor(Color.BLACK);
        g.drawLine(imageWidth / 2, 0, imageWidth / 2, imageHeight);
        g.drawLine(0, imageHeight / 2, imageWidth, imageHeight / 2);
    }

    public FrameMain() {
        this.setTitle("Геометрия");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        JTableUtils.initJTableForArray(coordinates, 30, true, true, false, true);
        coordinates.setRowHeight(30);

        JTableUtils.writeArrayToJTable(coordinates,
                new int[][]{
                        {-4, 4, 4, -4},
                        {-4, -4, 4, 4}
                });

        this.pack();

        JPanel myPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.drawLine(imageWidth / 2, 0, imageWidth / 2, imageHeight);
                g.drawLine(0, imageHeight / 2, imageWidth, imageHeight / 2);
            }
        };
        figures.setLayout(new BorderLayout());
        myPanel.setPreferredSize(new Dimension(imageWidth,imageHeight));
        figures.add(myPanel, BorderLayout.CENTER);

        paint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int [][] arr = JTableUtils.readIntMatrixFromJTable(coordinates);
                    if (arr != null) {
                        fig = Logic.saveFromArrayToFigure(arr);
                        Graphics g = figures.getGraphics();
                        drawFigure(g, Color.ORANGE, fig);
                    } else {
                        fig = null;
                        mistakeLabel.setText("Не удалось отобразить рисунок");
                    }
                } catch (ParseException parseException) {
                    saveMistLabel.setText("Ошибка, вводите координаты в целых числах!");
                }
            }
        });

        squareBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fig == null) {
                    squareLabel.setText("Пустой лист");
                } else {
                    double res = fig.square();
                    double EPS = 1E-9;
                    if (Math.abs(res + 1) <= EPS) {
                        squareLabel.setText("Ошибка при подсчете площади");
                    } else {
                        squareLabel.setText(Double.toString(res));
                    }
                }
            }
        });

        pBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fig == null) {
                    pLabel.setText("Пустой лист");
                } else {
                    double res = fig.perimeter();
                    double EPS = 1E-9;
                    if (Math.abs(res + 1) <= EPS) {
                        pLabel.setText("Ошибка при подсчете периметра");
                    } else {
                        pLabel.setText(Double.toString(res));
                    }
                }
            }
        });

        rectangleBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (fig != null) {
                        addit = fig.circumscribedRectangle();
                        Graphics g = figures.getGraphics();
                        drawFigure(g, Color.RED, addit);
                    } else {
                        rectMistakeLabel.setText("Прямоугольник не существует");
                    }
                } catch (Exception err) {
                    rectMistakeLabel.setText("Не удалось построить прямоугольник");
                }
            }
        });

        moveBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearFigures(figures.getGraphics());
                    drawFigure(figures.getGraphics(), Color.ORANGE, fig);
                    String xs = xField.getText();
                    double x;
                    if (xs.isEmpty()) {
                        x = 0;
                    } else {
                        x = Double.parseDouble(xs);
                    }
                    String ys = yField.getText();
                    double y;
                    if (ys.isEmpty()) {
                        y = 0;
                    } else {
                        y = Double.parseDouble(ys);
                    }
                    addit = fig.moveFigure(x, y*(-1));
                    if (addit == null) {
                        moveMistLabel.setText("Нельзя переместить фигуру");
                    } else {
                        drawFigure(figures.getGraphics(), Color.BLUE, addit);
                    }
                } catch (Exception err) {
                    moveMistLabel.setText("Не удалость переместить фигуру");
                }
            }
        });

        ScaleTopX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearFigures(figures.getGraphics());
                    String scales = scaleField.getText();
                    double scale;
                    if (scales.isEmpty()) {
                        scale = 1;
                    } else {
                        scale = Double.parseDouble(scales);
                    }
                    addit = fig.scaleUpX(scale);
                    if (addit == null) {
                        scaleMistLabel.setText("Масштабировать нельзя");
                    } else {
                        drawFigure(figures.getGraphics(), Color.ORANGE, addit);
                    }
                } catch (Exception err) {
                    scaleMistLabel.setText("Не удалость масштабировать фигуру");
                }
            }
        });

        ScaleTopY.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearFigures(figures.getGraphics());
                    String scales = scaleField.getText();
                    double scale;
                    if (scales.isEmpty()) {
                        scale = 1;
                    } else {
                        scale = Double.parseDouble(scales);
                    }
                    addit = fig.scaleUpY(scale);
                    if (addit == null) {
                        scaleMistLabel.setText("Масштабировать нельзя");
                    } else {
                        drawFigure(figures.getGraphics(), Color.ORANGE, addit);
                    }
                } catch (Exception err) {
                    scaleMistLabel.setText("Не удалость масштабировать фигуру");
                }
            }
        });

        ScaleBotX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearFigures(figures.getGraphics());
                    String scales = scaleField.getText();
                    double scale;
                    if (scales.isEmpty()) {
                        scale = 1;
                    } else {
                        scale = Double.parseDouble(scales);
                    }
                    addit = fig.scaleBottomX(scale);
                    if (addit == null) {
                        scaleMistLabel.setText("Масштабировать нельзя");
                    } else {
                        drawFigure(figures.getGraphics(), Color.ORANGE, addit);
                    }
                } catch (Exception err) {
                    scaleMistLabel.setText("Не удалость масштабировать фигуру");
                }
            }
        });

        ScaleBotY.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearFigures(figures.getGraphics());
                    String scales = scaleField.getText();
                    double scale;
                    if (scales.isEmpty()) {
                        scale = 1;
                    } else {
                        scale = Double.parseDouble(scales);
                    }
                    addit = fig.scaleBottomY(scale);
                    if (addit == null) {
                        scaleMistLabel.setText("Масштабировать нельзя");
                    } else {
                        drawFigure(figures.getGraphics(), Color.ORANGE, addit);
                    }
                } catch (Exception err) {
                    scaleMistLabel.setText("Не удалость масштабировать фигуру");
                }
            }
        });

        ScaleLeftX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearFigures(figures.getGraphics());
                    String scales = scaleField.getText();
                    double scale;
                    if (scales.isEmpty()) {
                        scale = 1;
                    } else {
                        scale = Double.parseDouble(scales);
                    }
                    addit = fig.scaleLeftX(scale);
                    if (addit == null) {
                        scaleMistLabel.setText("Масштабировать нельзя");
                    } else {
                        drawFigure(figures.getGraphics(), Color.ORANGE, addit);
                    }
                } catch (Exception err) {
                    scaleMistLabel.setText("Не удалость масштабировать фигуру");
                }
            }
        });

        ScaleLeftY.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearFigures(figures.getGraphics());
                    String scales = scaleField.getText();
                    double scale;
                    if (scales.isEmpty()) {
                        scale = 1;
                    } else {
                        scale = Double.parseDouble(scales);
                    }
                    addit = fig.scaleLeftY(scale);
                    if (addit == null) {
                        scaleMistLabel.setText("Масштабировать нельзя");
                    } else {
                        drawFigure(figures.getGraphics(), Color.ORANGE, addit);
                    }
                } catch (Exception err) {
                    scaleMistLabel.setText("Не удалость масштабировать фигуру");
                }
            }
        });

        ScaleRightX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearFigures(figures.getGraphics());
                    String scales = scaleField.getText();
                    double scale;
                    if (scales.isEmpty()) {
                        scale = 1;
                    } else {
                        scale = Double.parseDouble(scales);
                    }
                    addit = fig.scaleRightX(scale);
                    if (addit == null) {
                        scaleMistLabel.setText("Масштабировать нельзя");
                    } else {
                        drawFigure(figures.getGraphics(), Color.ORANGE, addit);
                    }
                } catch (Exception err) {
                    scaleMistLabel.setText("Не удалость масштабировать фигуру");
                }
            }
        });

        ScaleRightY.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearFigures(figures.getGraphics());
                    String scales = scaleField.getText();
                    double scale;
                    if (scales.isEmpty()) {
                        scale = 1;
                    } else {
                        scale = Double.parseDouble(scales);
                    }
                    addit = fig.scaleRightY(scale);
                    if (addit == null) {
                        scaleMistLabel.setText("Масштабировать нельзя");
                    } else {
                        drawFigure(figures.getGraphics(), Color.ORANGE, addit);
                    }
                } catch (Exception err) {
                    scaleMistLabel.setText("Не удалость масштабировать фигуру");
                }
            }
        });

        ScaleCenterX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearFigures(figures.getGraphics());
                    String scales = scaleField.getText();
                    double scale;
                    if (scales.isEmpty()) {
                        scale = 1;
                    } else {
                        scale = Double.parseDouble(scales);
                    }
                    addit = fig.scaleCenterX(scale);
                    if (addit == null) {
                        scaleMistLabel.setText("Масштабировать нельзя");
                    } else {
                        drawFigure(figures.getGraphics(), Color.ORANGE, addit);
                    }
                } catch (Exception err) {
                    scaleMistLabel.setText("Не удалость масштабировать фигуру");
                }
            }
        });

        ScaleCenterY.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearFigures(figures.getGraphics());
                    String scales = scaleField.getText();
                    double scale;
                    if (scales.isEmpty()) {
                        scale = 1;
                    } else {
                        scale = Double.parseDouble(scales);
                    }
                    addit = fig.scaleCenterY(scale);
                    if (addit == null) {
                        scaleMistLabel.setText("Масштабировать нельзя");
                    } else {
                        drawFigure(figures.getGraphics(), Color.ORANGE, addit);
                    }
                } catch (Exception err) {
                    scaleMistLabel.setText("Не удалость масштабировать фигуру");
                }
            }
        });

        clearBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTableUtils.writeArrayToJTable(coordinates,
                        new int[][]{
                                {-4, 4, 4, -4},
                                {-4, -4, 4, 4}
                        });
                mistakeLabel.setText("");
                squareLabel.setText("");
                pLabel.setText("");
                xField.setText("");
                yField.setText("");
                rectMistakeLabel.setText("");
                moveMistLabel.setText("");
                scaleField.setText("");
                scaleMistLabel.setText("");
                saveMistLabel.setText("");
                clearFigures(figures.getGraphics());
                fig = new Figure(new ArrayList<>());
                addit = new Figure(new ArrayList<>());
            }
        });
    }
}
