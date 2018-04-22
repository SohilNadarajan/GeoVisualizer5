
import javax.swing.JFrame;

//import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

public class Main
{    
    public static void main(String args[])
    {
    		MyManSurface drawing = new MyManSurface();
        PApplet.runSketch(new String[]{ "" }, drawing);
        
//        PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
//        PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
//        JFrame window = (JFrame) canvas.getFrame();
//        
//        window.setSize(1200, 800);
//        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        window.setResizable(true);
//        window.setVisible(true);
    }

}
