package gradient;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class ColourGrid {
	private int height, width;
	private int colours = 2;
	
	public ColourGrid(int height, int width) {
		this.height = height;
		this.width = width;
	}
	
	public ColourGrid(int height, int width, int colours) {
		this(height, width);
		if (colours > 1) {
			this.colours = colours;
		}
	}
	
	private int addColour() {
		return ++colours;
	}
	
	private int removeColour() {
		if (colours > 0) {
			return --colours;
		}
		return 0;
	}
	
	private int[][] draw() {
		Random random = new Random();
		int chunks = colours -1;
		int chunksize = height / chunks;
		int[][] picture = new int[height][width];
		for (int i = 0; i < chunks; ++i) {
			for (int h = 0; h < chunksize; ++h) {
				int border = (int) (((double)h/(double)chunksize)*Integer.MAX_VALUE);
				for (int w = 0; w < width; ++w) {
					int res = Math.abs(random.nextInt());
					picture[i*chunksize + h][w] = res >= border ? i : i+1;
				}
			}
		}
		return picture;
	}
	
	public void saveImage() {
		BufferedImage bi = new BufferedImage((width+2)*10+1, (height+2)*10+1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi.createGraphics();
		g.setBackground(Color.WHITE);
		g.fillRect(0, 0, bi.getWidth()-1, bi.getHeight()-1);
		g.setColor(Color.BLACK);
		//Draw the grid
		for (int i=0;i<bi.getWidth();i+=10) {
			g.drawLine(i, 0, i, bi.getHeight()-1);
		}
		for (int j=0;j<bi.getHeight();j+=10) {
			g.drawLine(0, j, bi.getWidth()-1, j);
		}
		int[][] image = draw();
		for (int h=0; h<image.length; ++h) {
			for (int w=0; w<image[h].length; ++w) {
				if (image[h][w] > 0) {
					//TODO: Handle more colours
					g.fillRect((w+1)*10+1, (h+1)*10+1, 10, 10);
				}
			}
		}
		
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 9));
		for (int h=0; h<image.length; ++h) {
			if ((image.length-h+1)%5==0) {
				g.drawString("" + (image.length-h+1),1,(h+1)*10-1);
				g.drawString("" + (image.length-h+1),(width+1)*10,(h+1)*10-1);
			}
		}
		for (int w=0; w<image[0].length; ++w) {
			if ((w+2)%5==0) {
				g.drawString("" + (w+2),(image[0].length-(w+1))*10+1, 11-2);
				g.drawString("" + (w+2),(image[0].length-(w+1))*10+1, (height+2)*10-1);
			}
		}
		
		try {
			ImageIO.write(bi, "PNG", new File(String.format("grid_%s_%s.png",width, height)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public String toString() {
		String res = "";
		int[][] picture = draw();
		for(int h=0; h<height; ++h) {
			for (int w=0; w<width; ++w) {
				res += picture[h][w];
			}
			res += "\n";
		}
		return res;
	}
}
