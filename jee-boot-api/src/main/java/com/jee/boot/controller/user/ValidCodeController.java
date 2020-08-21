package com.jee.boot.controller.user;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jee.boot.controller.AbstractBaseController;


@Controller
public class ValidCodeController extends AbstractBaseController{
	
	
	
	    @RequestMapping(value = "/kaptcha/{uuid}" , produces = MediaType.IMAGE_PNG_VALUE)
	    public ResponseEntity<Object> kaptchaValidCode(
	            @Length(min = 20)  @NotBlank(message = "用户名不为空") @PathVariable("uuid") String uuid ,
	            HttpServletRequest httpServletRequest,
	            HttpServletResponse httpServletResponse
	    ) throws IOException {
	    	ByteArrayOutputStream stream  = new ByteArrayOutputStream() ;
	        try {
	        	 // 生产验证码字符串并保存到session中
	            String createText = defaultKaptcha.createText();
	            sessionTemplate.set("JEE:ADMIN:YZM:" + uuid ,  createText , 300);
	            // 使用生成的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
	            BufferedImage challenge = defaultKaptcha.createImage(createText);
	            ImageIO.write(challenge, "jpg", stream);
	            
	           // httpServletResponse.setHeader("huuid", uuid);
	            return new ResponseEntity<Object>(stream.toByteArray() ,
	                     HTTP_HEADERS, HttpStatus.OK);
	        } catch (IllegalArgumentException | IOException e) {
	            throw  e ;
	        }finally {
	            if(stream != null){
	                try{
	                    stream.close();
	                }catch (Exception e){

	                }
	            }
	        }

	    }

}
