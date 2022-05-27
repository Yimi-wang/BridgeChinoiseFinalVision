package Vue;

import javax.swing.*;
import java.awt.*;

public class  RoundRectButton extends JButton
{
    public RoundRectButton(String s)
    {
        super(s);
        setMargin(new Insets(0,0,0,0));//去除文字与按钮的边沿
        setBorder(new RoundBorder());//圆角矩形边界
        setContentAreaFilled(false);//取消原先画矩形的设置
        //setBorderPainted(false);//会导致按钮没有明显边界
        setFocusPainted(false);//去除文字周围的虚线框
    }

    public RoundRectButton(ImageIcon icon)
    {
        super(icon);
        setMargin(new Insets(2,2,2,2));//去除文字与按钮的边沿
        setBorder(new RoundBorder(Color.WHITE));//圆角矩形边界
        setContentAreaFilled(false);//取消原先画矩形的设置
        //setBorderPainted(false);//会导致按钮没有明显边界
//        setFocusPainted(false);//去除文字周围的虚线框
    }
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.LIGHT_GRAY);//按下后按钮变成绿色
        } else {
            g.setColor(getBackground());
        }
        g.fillRoundRect(0, 0, getSize().width-4, getSize().height-4 ,65,95);//填充圆角矩形边界
        // 这个调用会画一个标签和焦点矩形。
        super.paintComponent(g);
    }
}