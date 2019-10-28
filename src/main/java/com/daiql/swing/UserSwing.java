package com.daiql.swing;

/**
 * @author daiql
 * @date 2019/10/28 9:16
 * @description 用户界面
 */

import com.daiql.xjar.XjarTest;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserSwing {

    public static void main(String[] args) {
        // 创建 JFrame 实例
        JFrame frame = new JFrame("Jar加密，防止反编译");
        // Setting the width and height of frame
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new JPanel();
        // 添加面板
        frame.add(panel);
        /*
         * 调用用户定义的方法并添加组件到面板
         */
        placeComponents(panel);

        // 设置界面可见
        frame.setVisible(true);
    }

    private static void placeComponents(final JPanel panel) {

        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);

        // 创建 需要加密的jar JLabel
        JLabel fromLabel = new JLabel("选择需要加密的jar:");
        fromLabel.setBounds(10,20,120,25);
        panel.add(fromLabel);

        /*
         * 用于记录未加密jar的文本域
         */
        final JTextField fromText = new JTextField(20);
        fromText.setBounds(140,20,210,25);
        panel.add(fromText);

        // 创建选择按钮
        JButton fromButton = new JButton("选择...");
        fromButton.setBounds(360, 20, 80, 25);
        panel.add(fromButton);


        // 加密后jar要保存的位置
        JLabel toLabel = new JLabel("选择需要保存的位置");
        toLabel.setBounds(10,50,120,25);
        panel.add(toLabel);

        /*
         *文本域用于记录保存路径
         */
        final JTextField toText = new JTextField(20);
        toText.setBounds(140,50,210,25);
        panel.add(toText);

        // 创建选择按钮
        JButton toButton = new JButton("选择...");
        toButton.setBounds(360, 50, 80, 25);
        panel.add(toButton);


        // 输入密码的文本域
        JLabel passwordLabel = new JLabel("加密密码:");
        passwordLabel.setBounds(10,80,80,25);
        panel.add(passwordLabel);

        /*
         * 这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */
        final JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100,80,165,25);
        panel.add(passwordText);

        // 创建登录按钮
        JButton startButton = new JButton("开始");
        startButton.setBounds(10, 110, 80, 25);
        panel.add(startButton);

        //日志显示框
        final JTextArea textArea = new JTextArea("");
        textArea.setBounds(10,150,300,150);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        panel.add(textArea);

        /*
         * 选择jar按钮监听事件
         */
        fromButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {  //按钮点击事件
                JFileChooser chooser = new JFileChooser();             //设置选择器
                chooser.setMultiSelectionEnabled(false);             //设为单选
                int returnVal = chooser.showOpenDialog(null);        //是否打开文件选择框
                if (returnVal == JFileChooser.APPROVE_OPTION) {          //如果符合文件类型
                    String filepath = chooser.getSelectedFile().getAbsolutePath();      //获取绝对路径
                    if (!".jar".equals(filepath.substring(filepath.length()-4))) {
                        JOptionPane.showMessageDialog(null, "文件格式不正确，请选择jar文件！", "文件格式错误", JOptionPane.ERROR_MESSAGE);
                    } else {
                        fromText.setText(filepath);
                    }
                    System.out.println(filepath);
                }
            }
        });

        /*
         * 选择加密后jar保存路径按钮监听事件
         */
        toButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {  //按钮点击事件
                JFileChooser chooser = new JFileChooser();             //设置选择器
                chooser.setMultiSelectionEnabled(false);             //设为单选
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //设置只选目录
                chooser.setDialogTitle("选择加密后jar保存位置");
                int returnVal = chooser.showOpenDialog(null);        //是否打开文件选择框
                if (returnVal == JFileChooser.APPROVE_OPTION) {          //如果符合文件类型
                    String filepath = chooser.getSelectedFile().getAbsolutePath();      //获取绝对路径
                    toText.setText(filepath);
                    System.out.println(filepath);
                }
            }
        });

        /*
         * 选择开始径按钮的监听事件
         */
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fromJarPath = fromText.getText();
                String toJarPath = toText.getText() + "\\encrypt" + getNowDateTime() + ".jar";
                String password = new String(passwordText.getPassword());
                System.out.println("fromJarPath=" + fromJarPath);
                System.out.println("toJarPath=" + toJarPath);
                System.out.println("password=" + password);
                if (fromJarPath != null && !"".equals(fromJarPath)) {
                    if (toJarPath != null && !"\\encrypt".equals(toJarPath.substring(0,8))) {
                        if (password != null && !"".equals(password)) {
                            //打印输入日志
                            StringBuilder builder = new StringBuilder();
                            builder.append("fromJarPath=" + fromJarPath + "\n")
                                    .append("toJarPath=" + toJarPath + "\n") ;
                            textArea.setText(builder.toString());
                            //开始加密文件
                            XjarTest.encryptJarDangerMode(fromJarPath,toJarPath,password);
                            textArea.append("jar加密成功！\n请测试接口是否正常（注意：Swagger不可用）");
                        } else {
                            JOptionPane.showMessageDialog(null, "请输入加密的密码！", "密码不能为空！", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "保存路径不能为空!", "保存路径不能为空！", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "jar文件不能为空！", "jar文件不能为空！", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

    /**
     * 获取当前格式化时间的方法
     * @return
     */
    private static String getNowDateTime() {
        String dateNow = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        dateNow = format.format(date);
        return dateNow;
    }
}
