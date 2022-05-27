package Vue;

import javax.swing.border.Border;
import java.awt.*;

/**
 * Swing
 * 设置圆角边框（可以自定义边框的颜色）
 * 可以为button，文本框等人以组件添加边框
 * 使用方法：
 * JButton close = new JButton(" 关 闭 ");
 * close.setOpaque(false);// 设置原来按钮背景透明
 * close.setBorder(new RoundBorder());黑色的圆角边框
 * close.setBorder(new RoundBorder(Color.RED)); 红色的圆角边框
 *
 * @author caifl
 */
public class RoundBorder implements Border {
    private Color color;
//
    public RoundBorder(Color color) {// 有参数的构造方法
        this.color = color;
    }

    public RoundBorder() {// 无参构造方法
        this.color = Color.BLACK;
        // 如果实例化时，没有传值
        // 默认是黑色边框
    }
//
//    public Insets getBorderInsets(Component c) {
//        return new Insets(0, 0, 0, 0);
//    }
//
//    public boolean isBorderOpaque() {
//        return false;
//    }
//
//    // 实现Border（父类）方法
//    @Override
//    public void paintBorder(Component c, Graphics g, int x, int y, int width,
//                            int height) {
//        g.setColor(color);
//        g.drawRoundRect(0, 0, c.getWidth() , c.getHeight() , 25, 125);
//    }

    public Insets getBorderInsets(Component c) {
        return new Insets(0,0,0,0);
    }
    public boolean isBorderOpaque() {
        return false;
    }
    public void paintBorder(Component c, Graphics g, int x, int y,
                            int width, int height) {
        //使用黑色在组件的外边缘绘制一个圆角矩形
        g.setColor(this.color);
        g.drawRoundRect(0, 0, c.getWidth()-1, c.getHeight()-1, 45, 45);
    }

}
