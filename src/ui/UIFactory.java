package ui;

public class UIFactory {
  private UIFactory() {}
  static private UI _UI = new PopupUI();
  static public UI ui () {
    return _UI;
  }
}
