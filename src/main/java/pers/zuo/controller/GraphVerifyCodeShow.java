package pers.zuo.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pers.zuo.util.ReqUtils;
import pers.zuo.util.VerifyCodeUtil.GraphVerifyCode;

public class GraphVerifyCodeShow extends HttpServlet {

	private static final long serialVersionUID = -4278246246576857649L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int width = ReqUtils.getParamInt(req, "width");
		int height = ReqUtils.getParamInt(req, "height");
		int nCode = ReqUtils.getParamInt(req, "nCode");

		// 告知浏览当作图片处理
		resp.setContentType("application/x-png");

		// 告诉浏览器不缓存
		resp.setHeader("pragma", "no-cache");
		resp.setHeader("cache-control", "no-cache");
		resp.setHeader("expires", "0");

		char[] randomCN = nCode > 0 ? GraphVerifyCode.randomCN(nCode) : GraphVerifyCode.randomCN();
		HttpSession session = req.getSession();
		String valcode = String.valueOf(randomCN);
		session.setAttribute("valcode", valcode);
		System.out.println(valcode);
		System.out.println(req.getSession().getAttribute("valcode"));
		BufferedImage generatorGraph = GraphVerifyCode.generatorGraph(width, height, randomCN);

		ImageIO.write(generatorGraph, "png", resp.getOutputStream());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
