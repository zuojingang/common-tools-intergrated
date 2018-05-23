package pers.zuo.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pers.zuo.util.verify.GraphVerifyCodeUtil;

public class GraphVerifyCodeShow3 extends HttpServlet {

	private static final long serialVersionUID = -4278246246576857649L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 告知浏览当作图片处理
		resp.setContentType("application/x-png");
		// 告诉浏览器不缓存
		resp.setHeader("pragma", "no-cache");
		resp.setHeader("cache-control", "no-cache");
		resp.setHeader("expires", "0");

		char[] randomCN = GraphVerifyCodeUtil.randomCN();
		byte[] generatorGraphByteArray = GraphVerifyCodeUtil.generatorGraphByteArray(randomCN);
		HttpSession session = req.getSession();
		String valcode = String.valueOf(randomCN);
		session.setAttribute("valcode", valcode);
		System.out.println(valcode);
		System.out.println(req.getSession().getAttribute("valcode"));
		String graphStr = new String(generatorGraphByteArray, "UTF-8");

		resp.getWriter().print(graphStr);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
