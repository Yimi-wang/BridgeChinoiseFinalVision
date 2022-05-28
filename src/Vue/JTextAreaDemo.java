package Vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
//测试用
public class JTextAreaDemo
{
    public static void main(String[] agrs)
    {
        JFrame frame=new JFrame("Java文本域组件示例");    //创建Frame窗口
        JPanel jp=new JPanel();    //创建一个JPanel对象
        JTextArea jta=new JTextArea("ABCDEFG",7,30);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(null);
        frame.setResizable(false);
        jta.setFont(new Font("楷体",Font.BOLD,16));    //修改字体样式
        jta.setBounds(10,10,120,40);
        jp.add(jta);    //将JScrollPane添加到JPanel容器中
        jp.setBounds(10, 10, 120, 40);
        frame.add(jp);    //将JPanel容器添加到JFrame容器中
        frame.setBackground(Color.LIGHT_GRAY);
        frame.setSize(400,200);    //设置JFrame容器的大小

        frame.setVisible(true);
    }
}