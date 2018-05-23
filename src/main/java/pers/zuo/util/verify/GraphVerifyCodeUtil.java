package pers.zuo.util.verify;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * @author zuojingang 图形验证码
 */
public class GraphVerifyCodeUtil {

	private static final int DEFAULT_NUM = 5;

	private static final char[] cnArr = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
			'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'I', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'i', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z' };

	public static char[] randomCN() {
		return randomCN(DEFAULT_NUM);
	}

	public static char[] randomCN(int num) {
		char[] ret = new char[num];
		Random random = new Random();
		for (int i = 0; i < num; i++) {
			ret[i] = cnArr[random.nextInt(cnArr.length)];
		}
		return ret;
	}

	public static byte[] generatorGraphByteArray(char[] randomCN) {
		BufferedImage bufferedImage = generatorGraph(randomCN);
		ByteArrayOutputStream bOS = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "png", bOS);
		} catch (IOException e) {
			return null;
		}
		byte[] imageByteArray = bOS.toByteArray();
		return imageByteArray;
	}

	public static BufferedImage generatorGraph(char[] randomCN) {
		int width = 185;
		int height = 45;
		BufferedImage generatorGraph = generatorGraph(width, height, Color.WHITE, Color.GRAY, 4, randomCN,
				Color.DARK_GRAY, height / 5, 0.5f);
		return generatorGraph;
	}

	/**
	 * @param width>=20
	 * @param height>4
	 * @param randomCN
	 * @return
	 */
	public static BufferedImage generatorGraph(int width, int height, char[] randomCN) {
		int padding = 4;
		int interferingLineNum = height / 5;
		float noisyPointRate = 0.5f;
		return generatorGraph(width, height, Color.WHITE, Color.GRAY, padding, randomCN, Color.DARK_GRAY,
				interferingLineNum, noisyPointRate);
	}

	public static BufferedImage generatorGraph(int width, int height, Color bgColor, Color boradColor, int padding,
			char[] randomCN, Color codeColor, int interferingLineNum, float noisyPointRate) {
		int codeWidth = (width - 2 * padding) / randomCN.length;
		int codeHeight = height - padding * 2;
		BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2d = bImage.createGraphics();

		// 去锯齿
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// 设置填充色
		graphics2d.setColor(bgColor);
		// 填充区域
		graphics2d.fillRect(0, 0, width, height);

		// 绘制边框
		graphics2d.setColor(boradColor);
		graphics2d.drawRect(0, 0, width - 1, height - 1);

		int fontSize = Math.min(codeWidth, codeHeight);
		Random random = new Random();
		// 绘制验证码
		Font[] fonts = { new Font("隶书", Font.BOLD, fontSize), new Font("楷体", Font.BOLD, fontSize),
				new Font("宋体", Font.BOLD, fontSize), new Font("幼圆", Font.BOLD, fontSize) };
		for (int i = 0; i < randomCN.length; i++) {
			String cStr = String.valueOf(randomCN[i]);
			graphics2d.setColor(codeColor);
			Font font = fonts[random.nextInt(fonts.length)];
			graphics2d.setFont(font);
			int x = padding + i * codeWidth;
			// 经实测，baseLine以上大概为5/7
			int y = padding + codeHeight * 5 / 7;
			graphics2d.drawString(cStr, x, y);
		}

		// 绘制干扰线
		for (int i = 0; i < interferingLineNum; i++) {
			// 设置填充色
			graphics2d.setColor(codeColor);
			// 干扰线起始点坐标
			int xS = random.nextInt(55), yS = random.nextInt(height - 1) + 1;
			// 干扰线终止点坐标
			int xE = random.nextInt(55) + (width - 55), yE = random.nextInt(height - 1) + 1;
			graphics2d.drawLine(xS, yS, xE, yE);
		}

		// 点缀噪点
		// 最大噪点数
		int maximumNoisyPoint = (int) (width * height * noisyPointRate);
		for (int i = 0; i < maximumNoisyPoint; i++) {
			// 噪点所在X,Y坐标
			int xNP = random.nextInt(width), yNP = random.nextInt(height);
			// 噪点填充色
			Color randomColor = randomColor(random);
			// 设置噪点颜色
			bImage.setRGB(xNP, yNP, randomColor.getRGB());
		}
		// 输出图像，释放资源
		graphics2d.dispose();
		return bImage;
	}

	private static Color randomColor(Random random) {
		int colorR = random.nextInt(256);
		int colorG = random.nextInt(256);
		int colorB = random.nextInt(256);
		return new Color(colorR, colorG, colorB);
	}

}
