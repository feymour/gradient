package gradient;

import java.util.Random;

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
	
	public int addColour() {
		return ++colours;
	}
	
	public int removeColour() {
		if (colours > 0) {
			return --colours;
		}
		return 0;
	}
	
	public int[][] draw() {
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
