package gradient;

public class GradientImage {

	
	
	public static void main(String[] args) {
		ColourGrid cg = new ColourGrid(10,237,2);
		System.out.println(cg.toString());
		cg.saveImage();
	}

}
