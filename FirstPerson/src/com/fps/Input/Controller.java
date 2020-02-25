package com.fps.Input;

public class Controller {
	public double x,z,y,rotationa,xa,za,rotation;
	public static boolean turnLeft;
	public static boolean turnRight;
	public static boolean run = false;
	public static boolean walk = false;
	public static boolean runBob = false;
	public void tick(boolean forward, boolean backward, boolean left, boolean right,boolean jump, boolean crouch,boolean sprint) {
		
		double rotationSpeed = 1;
		double walkSpeed = 0.75;
		double xMovement = 0;
		float jumpHeight = 0.5f;
		float crouchHeight = 0.3f;
		double zMove = 0;
		
		if(forward) {
			zMove++;
			walk = true;
		}
		if(backward) {
			zMove--;
			walk = true;
		}
		if(left) {
			xMovement--;
			walk = true;
		}
		if(right) {
			xMovement++;
			walk = true;
		}
		if(turnLeft) {
			rotationa -=rotationSpeed*0.4;
		}
		if(turnRight) {
			rotationa +=rotationSpeed*0.4;
		}
		if(jump) {
			y+=jumpHeight;
		}
		if(crouch) {
			y-=crouchHeight;
			walkSpeed*=0.5;
			walk = false;
			sprint = false;
		}
		if(sprint && (forward || backward || left || right)) {
			runBob = true;
			walkSpeed*=1.5;
		}
		if(sprint && !(forward || backward || left || right)) runBob = false;
		xa+=(xMovement*Math.cos(rotation) + zMove*Math.sin(rotation))*walkSpeed;
		za+=(zMove*Math.cos(rotation) - xMovement*Math.sin(rotation))*walkSpeed;
		if(!forward && !backward && !left && !left && !right)
			walk = false;
		if(!sprint)
			runBob = false;
		x+=xa;
		y *=0.9;
		z+=za;
		xa*=0.1;
		za*=0.1;
		rotation+=rotationa;
		rotation*=0.05; 
	}
}
