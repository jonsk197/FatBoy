import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;

public class FlyingObject {

	private Image image;
	private double elasticity; //Should range between 0 and 1.
	private double fatpoints; //The amount of fatpoints rewarded for eating this.
	private int highscore; //The amouth of points rewarded for eating this.
	private double dx; //Velocity in x
	private double dy; //Velocity in y
	private double x = -0.1; //Position in x
	private double y = 0.5; //Position in y
	private double rotation;
	private double angularVelocity;
	private int timeUntilRemoved = 40;
	private Random r = new Random(); //Random generator
	private double scale = 1;
	
	public FlyingObject(Image image, double elasticity, double fatpoints, int highscore) {
		this.image = image;
		this.elasticity = elasticity;
		this.fatpoints = fatpoints;
		this.highscore = highscore;

		// Generates random numbers between -0.05 and 0.05
		dy = (0 - r.nextDouble()) * 0.03;
		// Random initial speed between 0 and 0.05
		dx = (r.nextDouble() + 0.2) * 0.02;
		angularVelocity = (0.5 -r.nextDouble()) * 0.1;
		rotation = r.nextDouble();
	}

	public double fatpoints() {
		return fatpoints;
	}
	public int highscore() {
		return highscore;
	}

	public void update() {
		dy += 0.000982;

		x += dx;
		y += dy;
		rotation += angularVelocity;
		
		//If the object hits the ground, reverse the velocity and remove
		if(y > Settings.groundLevel){
			dy = -dy * elasticity;
			y = Settings.groundLevel;
			angularVelocity += dx * 3;
			dx = dx * elasticity;
		}
		
		//If the object stops moving
		if(dx <= 0.001){
			timeUntilRemoved--;
			if(angularVelocity > 0.0001)
				angularVelocity -= 0.005;
			else
				angularVelocity = 0;
			if(timeUntilRemoved < 7)
				scale -= 0.15;
		}
		//Remove the object if it's time has come
		if (timeUntilRemoved == 0)		
			GameCanvas.removeFlyingObject(this);
	}

	public void render(Graphics2D g) {
		g.translate(Settings.width() * x, Settings.height() * y);
		g.rotate(rotation);
		g.translate(-image.getWidth(null)/2*scale, -image.getHeight(null)/2*scale);

		g.drawImage(image, 0, 0, (int)(image.getWidth(null) * scale),
				(int)(image.getHeight(null) * scale), null);
		
		g.translate(image.getWidth(null)/2*scale, image.getHeight(null)/2*scale);
		g.rotate(-rotation);
		g.translate(-Settings.width() * x, -Settings.height() * y);
	}
	/*
	 * Returns true if the given coordinates collide with the objecs hitbox.
	 */
	public boolean checkForCollision(double x, double y){
		if(x < (this.x + 0.05) && x > (this.x - 0.05))
			if(y < (this.y + 0.05) && y > (this.y -0.05))
				return true;
		
		return false;
	}
}
