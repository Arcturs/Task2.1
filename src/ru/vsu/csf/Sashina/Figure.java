package ru.vsu.csf.Sashina;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Figure {

    private List<Point> coordinates;

    public Figure (List<Point> coordinates) {
        this.coordinates = coordinates;
    }

    public List<Point> getCoordinates () {
        return coordinates;
    }

    public void addPoint (Point a) {
        coordinates.add(a);
    }

    public void setCoordinates (List<Point> list) {
        coordinates = list;
    }

    public double square () {
        switch (coordinates.size()) {
            case 0: return -1;
            case 1: return 0;
            case 2:  return 0;
            default: double square = 0;
                ListIterator<Point> lt = coordinates.listIterator();
                Point a = lt.next(), b;
                Point first = a;
                while (lt.hasNext()) {
                    b = lt.next();
                    square += a.getX()*b.getY() - a.getY()*b.getX();
                    a = b;
                }
                square += a.getX()*first.getY() - a.getY()*first.getX();
                return Math.abs(square) * 0.5;
        }
    }

    public double perimeter () {
        switch (coordinates.size()) {
            case 0: return -1;
            case 1: return 0;
            case 2: return 0;
            default: double p = 0;
                ListIterator<Point> lt = coordinates.listIterator();
                Point a = lt.next(), b;
                Point first = a;
                while (lt.hasNext()) {
                    b = lt.next();
                    p += Math.sqrt(Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2));
                    a = b;
                }
                p += Math.sqrt(Math.pow(first.getX() - a.getX(), 2) + Math.pow(first.getY() - a.getY(), 2));
                return p;
        }
    }

    public Figure moveFigure (double dx, double dy) {
        if (coordinates.size() == 0) {
            return null;
        } else {
            List<Point> newList = new ArrayList<>();
            for (Point value : coordinates) {
                value.setX((int) (dx + value.getX()));
                value.setY((int) (dy + value.getY()));
                newList.add(value);
            }
            return new Figure(newList);
        }
    }

    public Figure circumscribedRectangle () {
        if (coordinates.size() == 0) {
            return null;
        } else {
            ListIterator<Point> lt = coordinates.listIterator();
            Point a = lt.next();
            int maxX = a.getX(), maxY = a.getY(), minX = a.getX(), minY = a.getY();
            while (lt.hasNext()) {
                a = lt.next();
                if (a.getX() > maxX) {
                    maxX = a.getX();
                } else if (a.getX() < minX) {
                    minX = a.getX();
                }
                if (a.getY() > maxY) {
                    maxY = a.getY();
                } else if (a.getY() < minY) {
                    minY = a.getY();
                }
            }
            List<Point> newList = new ArrayList<>();
            if ((maxX == minX) || (maxY == minY)) {
                return null;
            } else {
                newList.add(new Point(minX, maxY));
                newList.add(new Point(maxX, maxY));
                newList.add(new Point(maxX, minY));
                newList.add(new Point(minX, minY));
                return new Figure(newList);
            }
        }
    }

    public Figure scaleUpX (double scale) {
        if (coordinates.size() == 0 || coordinates.size() == 1) {
            return null;
        } else {
            List<Point> newList = new ArrayList<>();
            Point a = new Point(-300, -300);
            Point b = new Point(-301, -301);
            for (Point value : coordinates) {
                if (value.getY() == a.getY()) {
                    b = new Point(value.getX(), value.getY());
                } else if (value.getY() > a.getY()) {
                    a = new Point(value.getX(), value.getY());
                    b = new Point(-301, -301);
                }
            }
            for (Point value: coordinates) {
                if ((value.getX() == a.getX() && value.getY() == a.getY()) ||
                        (value.getX() == b.getX() && value.getY() == b.getY())) {
                    newList.add(new Point((int) (value.getX()*scale), value.getY()));
                } else {
                    newList.add(value);
                }
            }
            return new Figure(newList);
        }
    }

    public Figure scaleUpY (double scale) {
        if (coordinates.size() == 0 || coordinates.size() == 1) {
            return null;
        } else {
            List<Point> newList = new ArrayList<>();
            Point a = new Point(-300, -300);
            Point b = new Point(-301, -301);
            for (Point value : coordinates) {
                if (value.getY() == a.getY()) {
                    b = new Point(value.getX(), value.getY());
                } else if (value.getY() > a.getY()) {
                    a = new Point(value.getX(), value.getY());
                    b = new Point(-301, -301);
                }
            }
            for (Point value: coordinates) {
                if ((value.getX() == a.getX() && value.getY() == a.getY()) ||
                        (value.getX() == b.getX() && value.getY() == b.getY())) {
                    newList.add(new Point(value.getX(), (int) (value.getY()*scale)));
                } else {
                    newList.add(value);
                }
            }
            return new Figure(newList);
        }
    }

    public Figure scaleBottomX (double scale) {
        if (coordinates.size() == 0 || coordinates.size() == 1) {
            return null;
        } else {
            List<Point> newList = new ArrayList<>();
            Point a = new Point(300, 300);
            Point b = new Point(301, 301);
            for (Point value : coordinates) {
                if (value.getY() == a.getY()) {
                    b = new Point(value.getX(), value.getY());
                } else if (value.getY() < a.getY()) {
                    a = new Point(value.getX(), value.getY());
                    b = new Point(301, 301);
                }
            }
            for (Point value: coordinates) {
                if ((value.getX() == a.getX() && value.getY() == a.getY()) ||
                        (value.getX() == b.getX() && value.getY() == b.getY())) {
                    newList.add(new Point((int) (value.getX()*scale), value.getY()));
                } else {
                    newList.add(value);
                }
            }
            return new Figure(newList);
        }
    }

    public Figure scaleBottomY (double scale) {
        if (coordinates.size() == 0 || coordinates.size() == 1) {
            return null;
        } else {
            List<Point> newList = new ArrayList<>();
            Point a = new Point(300, 300);
            Point b = new Point(301, 301);
            for (Point value : coordinates) {
                if (value.getY() == a.getY()) {
                    b = new Point(value.getX(), value.getY());
                } else if (value.getY() < a.getY()) {
                    a = new Point(value.getX(), value.getY());
                    b = new Point(301, 301);
                }
            }
            for (Point value: coordinates) {
                if ((value.getX() == a.getX() && value.getY() == a.getY()) ||
                        (value.getX() == b.getX() && value.getY() == b.getY())) {
                    newList.add(new Point(value.getX(), (int) (value.getY()*scale)));
                } else {
                    newList.add(value);
                }
            }
            return new Figure(newList);
        }
    }

    public Figure scaleLeftX (double scale) {
        if (coordinates.size() == 0 || coordinates.size() == 1) {
            return null;
        } else {
            List<Point> newList = new ArrayList<>();
            Point a = new Point(300, 300);
            Point b = new Point(301, 301);
            for (Point value : coordinates) {
                if (value.getX() == a.getX()) {
                    b = new Point(value.getX(), value.getY());
                } else if (value.getX() < a.getX()) {
                    a = new Point(value.getX(), value.getY());
                    b = new Point(301, 301);
                }
            }
            for (Point value: coordinates) {
                if ((value.getX() == a.getX() && value.getY() == a.getY()) ||
                        (value.getX() == b.getX() && value.getY() == b.getY())) {
                    newList.add(new Point((int) (value.getX()*scale), value.getY()));
                } else {
                    newList.add(value);
                }
            }
            return new Figure(newList);
        }
    }

    public Figure scaleRightX (double scale) {
        if (coordinates.size() == 0 || coordinates.size() == 1) {
            return null;
        } else {
            List<Point> newList = new ArrayList<>();
            Point a = new Point(-300, -300);
            Point b = new Point(-301, -301);
            for (Point value : coordinates) {
                if (value.getX() == a.getX()) {
                    b = new Point(value.getX(), value.getY());
                } else if (value.getX() > a.getX()) {
                    a = new Point(value.getX(), value.getY());
                    b = new Point(-301, -301);
                }
            }
            for (Point value: coordinates) {
                if ((value.getX() == a.getX() && value.getY() == a.getY()) ||
                        (value.getX() == b.getX() && value.getY() == b.getY())) {
                    newList.add(value);
                } else {
                    newList.add(new Point((int) (value.getX()*scale), value.getY()));
                }
            }
            return new Figure(newList);
        }
    }

    public Figure scaleLeftY (double scale) {
        if (coordinates.size() == 0 || coordinates.size() == 1) {
            return null;
        } else {
            List<Point> newList = new ArrayList<>();
            Point a = new Point(300, 300);
            Point b = new Point(301, 301);
            for (Point value : coordinates) {
                if (value.getX() == a.getX()) {
                    b = new Point(value.getX(), value.getY());
                } else if (value.getX() < a.getX()) {
                    a = new Point(value.getX(), value.getY());
                    b = new Point(301, 301);
                }
            }
            for (Point value: coordinates) {
                if ((value.getX() == a.getX() && value.getY() == a.getY()) ||
                        (value.getX() == b.getX() && value.getY() == b.getY())) {
                    newList.add(value);
                } else {
                    newList.add(new Point(value.getX(), (int) (value.getY()*scale)));
                }
            }
            return new Figure(newList);
        }
    }

    public Figure scaleRightY (double scale) {
        if (coordinates.size() == 0 || coordinates.size() == 1) {
            return null;
        } else {
            List<Point> newList = new ArrayList<>();
            Point a = new Point(-300, -300);
            Point b = new Point(-301, -301);
            for (Point value : coordinates) {
                if (value.getX() == a.getX()) {
                    b = new Point(value.getX(), value.getY());
                } else if (value.getX() > a.getX()) {
                    a = new Point(value.getX(), value.getY());
                    b = new Point(-301, -301);
                }
            }
            for (Point value: coordinates) {
                if ((value.getX() == a.getX() && value.getY() == a.getY()) ||
                        (value.getX() == b.getX() && value.getY() == b.getY())) {
                    newList.add(value);
                } else {
                    newList.add(new Point(value.getX(), (int) (value.getY()*scale)));
                }
            }
            return new Figure(newList);
        }
    }

    public Figure scaleCenterX (double scale) {
        if (coordinates.size() == 0) {
            return null;
        } else {
            List<Point> newList = new ArrayList<>();
            for (Point value : coordinates) {
                newList.add(new Point((int) (value.getX()*scale), value.getY()));
            }
            return new Figure(newList);
        }
    }

    public Figure scaleCenterY (double scale) {
        if (coordinates.size() == 0) {
            return null;
        } else {
            List<Point> newList = new ArrayList<>();
            for (Point value : coordinates) {
                newList.add(new Point(value.getX(), (int) (value.getY()*scale)));
            }
            return new Figure(newList);
        }
    }
}
