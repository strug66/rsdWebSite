package com.rsd.servlet;

import com.rsd.util.UploadUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

@MultipartConfig
@WebServlet("/servlet/upload")
public class uploadServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        Part headFile = request.getPart("headFile");

//        System.out.println("-----" + headFile);//为空 org.apache.catalina.core.ApplicationPart@6372c867
//        System.out.println("+++++" + headFile.getSubmittedFileName()); //为空 ""
//        System.out.println("00000" + headFile.getSize());  //为空 0

        String fileName = headFile.getSubmittedFileName();

        if (!fileName.equals("")) {
            InputStream in = headFile.getInputStream();
            String newFileName = UploadUtil.getNewFileName(fileName);
            String uploadPath = this.getServletContext().getRealPath("/") + UploadUtil.baseDir + newFileName;
            boolean b = UploadUtil.upload(in, uploadPath);
            response.getWriter().print(b ? "文件上传成功！" : "文件上传失败！");
        } else {
            response.sendRedirect("/tool/upload.jsp");
        }


    }
}
