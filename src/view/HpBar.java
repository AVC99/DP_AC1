package view;


import javax.swing.*;
import java.awt.*;

public class HpBar extends JProgressBar {

  public HpBar(){
      setPreferredSize(new Dimension(200,50));
      setMaximum(10);
      setMinimum(0);
      setValue(10);
      setBackground(Color.red);
      setForeground(Color.green);
      setBorder(BorderFactory.createLineBorder(Color.BLACK,2));

  }

}
