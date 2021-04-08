package com.xy.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

//@WebServlet("/code")
@RestController
//@RequestMapping("/code")
public class CodeController {
    @GetMapping("code")
    public void verifyCode(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        //创建空白图片
        BufferedImage image = new BufferedImage(100, 30, BufferedImage.TYPE_INT_RGB);
        //1.获取图片画笔
        Graphics g = image.getGraphics();
        Random r = new Random();//随机数

//        r.nextInt(10)，随机数在0到255之间 而且必须是整数 0-1
        //2.设置画笔颜色
//        设置背景颜色，RGB 三个参数
        g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
        //3. .绘制矩形的背景
        g.fillRect(0, 0, 100, 30);


        //4.调用自定义的方法,获取长度为3的字母数字组合的字符串
        String number = getNumber(1);
        System.out.println("Captcha:" + number);

        req.getSession().setAttribute("code", number);
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Mono", Font.BOLD, 36));
        //5.设置颜色字体后，绘制字符串
        g.drawString(number, 25, 25);
        //6.绘制10条干扰线
        for (int i = 0; i < 10; i++) {
            g.setColor(new
                    Color(r.nextInt(156), r.nextInt(166), r.nextInt(156), r.nextInt(255)));
            g.drawLine(r.nextInt(100),
                    r.nextInt(30),
                    r.nextInt(100),
                    r.nextInt(30));
            // 直线参数 （a,b,x,y),a,b代表起点，x，y代表终点
        }
        resp.setContentType("image/jpeg");
        OutputStream ops = resp.getOutputStream();
        ImageIO.write(image, "jpeg", ops);
        ops.close();
    }



    /**
     * 字符串的拼接
     *
     * @param size
     * @return
     */
    private String getNumber(int size) {
        String str = "qwertyuopasdfghjkzxcvbnm";//把L,I和O都去掉，因为和1,0不好识别
        StringBuilder number = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < size; i++) {
            number.append(str.charAt(r.nextInt(str.length())));
//            str.charAt 拿到下标为0-str长度之间的下标，下标从0开始
        }
        return number.toString();
    }
}
