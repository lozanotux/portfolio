import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.util.concurrent.TimeUnit;

public class MouseMovement {

	public static void main(String[] args) {
		
		Robot bot = null;
		
		try {
			bot = new Robot();
			
			Point puntero = MouseInfo.getPointerInfo().getLocation();
			int x = (int) puntero.getX();
			int y = (int) puntero.getY();

			while (true) {
			
				bot.mouseMove(x + 5, y);		
				TimeUnit.SECONDS.sleep(5);
				bot.mouseMove(x - 5, y);
				TimeUnit.SECONDS.sleep(5);
			
			}
			
		} catch (AWTException e) {
			System.err.println("[ROBOT EXCEPTION] > " + e.getMessage());
		} catch (InterruptedException e) {
			System.err.println("[ROBOT EXCEPTION] > " + e.getMessage());
		}

	}

}
