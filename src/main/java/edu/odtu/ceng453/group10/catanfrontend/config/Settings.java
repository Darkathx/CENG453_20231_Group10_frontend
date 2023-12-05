package edu.odtu.ceng453.group10.catanfrontend.config;

public class Settings {
  private static double width;
  private static double height;

  static {
    width = 1920;
    height = 1080;
  }

  public static double getHeight() {
    return height;
  }

  public static void setHeight(double height) {
    Settings.height = height;
  }


  public static double getWidth() {
    return width;
  }

  public static void setWidth(double width) {
    Settings.width = width;
  }
}
