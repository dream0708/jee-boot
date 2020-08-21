package com.jee.rest.base.util ;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class ValidateCode {

	// 图片的宽度。  
    private int width = 160;  
    // 图片的高度。  
    private int height = 40;  
    // 验证码字符个数  
    private int codeCount = 5;  
    // 验证码干扰线数  
    private int lineCount = 150;  
    // 验证码  
    private String code = null;  
    // 验证码图片Buffer  
    private BufferedImage buffImg = null;  
  
    /*private char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',  
            'K', 'L', 'M', 'N',  'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 
            'X', 'Y', 'Z',  '1', '2', '3', '4', '5', '6', '7', '8', '9' , '0' };*/  
    
    private char[] codeSequence = { '0',  '1', '2', '3', '4', '5', '6', '7', '8', '9' }; 
    
    private Random random = new Random() ;  
  
    public  ValidateCode() {  
        this.createCode();  
    }  
  
    /** 
     *  
     * @param width 图片宽 
     * @param height 图片高 
     */  
    public  ValidateCode(int width,int height) {  
        this.width=width;  
        this.height=height;  
        this.createCode();  
    }  
    /** 
     *  
     * @param width 图片宽 
     * @param height 图片高 
     * @param codeCount 字符个数 
     * @param lineCount 干扰线条数 60
     */  
    public  ValidateCode(int width,int height,int codeCount,int lineCount) {  
        this.width = width;  
        this.height = height;  
        this.codeCount = codeCount;  
        this.lineCount = lineCount;  
        this.createCode();  
    } 
    
    public Color getRandomColor(int fc , int bc){
    	  Random random = new Random();  
          if(fc>255) fc=255;  
          if(bc>255) bc=255;  
          
          int r=fc + random.nextInt(bc-fc);  
          int g=fc + random.nextInt(bc-fc);  
          int b=fc + random.nextInt(bc-fc);  
          return new Color(r,g,b);  
    }
      
    public void createCode() {  
        double codeX = width / (codeCount + 1 ) ;//每个字符的宽度  
        double codeY =  4 * height / 5 ;  
        // 图像buffer  
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
        Graphics2D  g = buffImg.createGraphics() ;  
        // 将图像填充为白色  
        g.setColor(Color.WHITE);  
        g.fillRect(0, 0, width, height);  
        // 创建字体  
        //Font font = new Font("Times New Roman",Font.PLAIN, 20); 
        Font font = new Font("微软雅黑", Font.BOLD, 24);
        g.setFont(font);
		g.setColor(Color.GRAY);
		
        //绘画干扰线
        for (int i = 0 ; i < lineCount ; i ++) {  
        	int xs = random.nextInt(width);  
            int ys = random.nextInt(height);  
            int xe = xs + random.nextInt( width /2 );  
            int ye = ys + random.nextInt( height / 2 );  
            g.drawLine(xs, ys, xe, ye);  
        }
        // randomCode记录随机产生的验证码  
        StringBuffer randomCode = new StringBuffer();  
        // 随机产生codeCount个字符的验证码。  
        double rot = 0 ;
        double oldrot = 0 ;
        for (int i = 0; i < codeCount; i ++ ) {  
            String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);  
            // 产生随机的颜色值，让输出的每个字符的颜色值都将不同。  
        	g.setColor(new Color(20 + random.nextInt(110) , 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(strRand, (int)(i * codeX + codeX / 2 ), (int) codeY);  
            rot = -0.25 + Math.abs(Math.toRadians(random.nextInt(20)));
			g.rotate(- oldrot , 0 , 20);
			g.rotate(rot, (int)(i * codeX + codeX / 2 ) , 20);
            // 将产生的四个随机数组合在一起。  
            randomCode.append(strRand);  
            oldrot = rot;
        }  
        // 将四位数字的验证码保存到Session中。  
        code = randomCode.toString(); 
        g.dispose();
    }  
      
    public void write(String path) throws IOException {  
         OutputStream sos = new FileOutputStream(path);  
         this.write(sos);  
    }  
      
    public void write(OutputStream sos) throws IOException {  
        ImageIO.write(buffImg, "jpeg", sos);  
        sos.close();  
    }  
    public BufferedImage getBuffImg() {  
        return buffImg;  
    }  
    
      
    public String getCode() {  
        return code;  
    }  
    
    
    
}
