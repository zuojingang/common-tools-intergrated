package pers.zuo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestController extends HttpServlet {

	private static final long serialVersionUID = -2368608779373396892L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		File file = new File("/Users/zuojingang/Downloads/show3");
		StringBuffer sBuffer = new StringBuffer();
		FileInputStream fileInputStream = new FileInputStream(file);
		byte[] bs = new byte[10000];
		fileInputStream.read(bs);
	}
}
