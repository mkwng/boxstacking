import processing.core.*; 
import processing.xml.*; 

import org.jbox2d.common.*; 
import org.jbox2d.collision.*; 
import org.jbox2d.dynamics.*; 
import org.jbox2d.dynamics.joints.*; 
import org.jbox2d.dynamics.contacts.*; 
import org.jbox2d.p5.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class sketch_sep07d extends PApplet {

// Import everything exposed by JBox2D (except the testbed stuff)





// BoxWrap2D


// A reference to the physics engine
Physics physics;
World m_world;
Body rect1;
Body rect2;
Body rect3;
int i;
int m;
int z = 0;
int var;
float[] randNum = new float[9];



float[][] numberArray = {
{-24.344276f, -20.947254f, -0.60476685f, 1.8598442f, 41.504135f, -25.63811f, 49.892494f, 0.6359024f, -28.622171f},
{-42.5667f, 35.789314f, 2.3339386f, 41.330315f, -14.195496f, -2.7436962f, -5.661728f, -23.460419f, -20.399113f},
{-41.24933f, -13.548134f, -7.7232533f, 30.298668f, 30.21585f, -4.7507687f, -40.823784f, -26.146608f, 23.2701f},
{21.345146f, -30.761932f, 3.357174f, 7.8124046f, -22.92784f, 3.324356f, 24.061646f, 39.92576f, 6.8192444f},
{-5.0929604f, 18.217506f, 17.649765f, -42.113823f, 2.0999832f, -3.2476444f, 8.85939f, 27.201683f, 16.15247f},
{-7.382286f, -2.2173767f, -16.293976f, -34.6991f, 9.811157f, 28.03786f, -24.612326f, 37.910576f, 0.6605644f},
{-35.18751f, 25.878105f, 25.449818f, -44.46338f, -1.0795937f, 11.966827f, 7.5566483f, 4.580147f, 19.213032f},
{-25.794388f, 48.901817f, -29.523243f, -30.827105f, -42.7244f, -22.740509f, 17.53012f, -18.60202f, 29.348572f}
};

boolean on = true;

public void setup()
{
  // Medium sized scene
  size(640, 480);
  // Physics is computed 60 times per second, so let's draw at same rate
  frameRate(200);
  // Nicer graphisc
  //smooth();

  // Set up everything physics
  InitScene();
  // And add object to the scene
  CreateObjects();
}

public void draw()
{
  // Not much to do here, most drawing is handled by BoxWrap2D
  background(255);
  
  if (rect1.isSleeping() && rect2.isSleeping() && rect3.isSleeping())
  {
    Vec2 posW1 = rect1.getPosition();
    Vec2 posS1 = physics.worldToScreen(posW1);
    
    Vec2 posW2 = rect2.getPosition();
    Vec2 posS2 = physics.worldToScreen(posW2);
    
    Vec2 posW3 = rect3.getPosition();
    Vec2 posS3 = physics.worldToScreen(posW3);
    
    if (min(posS1.y, posS2.y, posS3.y) < 281)
    {
    }
    
    if (on == true)
    {
      i++;
      physics.destroy();
      physics = null;
      InitScene();
      CreateObjects();
    }
    
  }
}


public void keyPressed()
{
  on = false;
  physics.destroy();
  physics = null;
  InitScene();
  CreateObjects();
}

public void InitScene()
{
  // Set up the engine with the sketch's dimensions
  physics = new Physics(this, width, height);
  m_world = physics.getWorld();
  m_world.setContactListener(new CustomListener());
  physics.setDensity(3.0f);
  physics.setRestitution(0.8f); // Make them super-rebounding
}

public void CreateObjects()
{
  // Middle of the world
  float hw = width / 2.0f;
  float hh = height / 2.0f;
  
  
  
if (on == true) {
  randNum[0] = random(-50,50);
  randNum[1] = random(-50,50);
  randNum[2] = random(-50,50);
  randNum[3] = random(-30,30);
  randNum[4] = random(-50,50);
  randNum[5] = random(-50,50);
  randNum[6] = random(-30,30);
  randNum[7] = random(-50,50);
  randNum[8] = random(-50,50);
}
if (on == false)
{
  print("entering loop\n");
  randNum[0] = numberArray[z][0];
  randNum[1] = numberArray[z][1];
  randNum[2] = numberArray[z][2];
  randNum[3] = numberArray[z][3];
  randNum[4] = numberArray[z][4];
  randNum[5] = numberArray[z][5];
  randNum[6] = numberArray[z][6];
  randNum[7] = numberArray[z][7];
  randNum[8] = numberArray[z][8];
    z++;
    if (z >= 8) {z=0;}

}

// And two rectangles not far (coordinates of top-left, and bottom-right corners)
  rect1 = physics.createRect(
	hw - 150, hh - 40,
	hw - 50, hh + 40
  );
  // Small vector: slow
  Vec2 v1 = new Vec2(randNum[0], randNum[1]); // To the left
  rect1.setLinearVelocity(v1);
  rect1.setAngularVelocity(randNum[2]);

  rect2 = physics.createRect(
	hw + 75, hh - 40,
	hw + 175, hh + 40
  );
  // Bigger: fast
  Vec2 v2 = new Vec2(randNum[3], randNum[4]); // Toward top-right
  rect2.setLinearVelocity(v2);
  rect2.setAngularVelocity(randNum[5]);


  rect3 = physics.createRect(
	hw + 50, hh - 40,
	hw - 50, hh + 40
  );
  // Bigger: fast
  Vec2 v3 = new Vec2(randNum[6], randNum[7]); // Toward top-right
  rect3.setLinearVelocity(v3);
  rect3.setAngularVelocity(randNum[8]);

} 

public class CustomListener implements ContactListener {
  CustomListener(){
  };
  public void add(ContactPoint cpoint)
  {//contacts collide for the first time
  }

  public void persist(ContactPoint cpoint)
  {//contacts continue to collide - i.e. resting on each other
  }

  public void remove(ContactPoint cpoint)
  {//objects stop touching each other
  }

  public void result(ContactResult point)
  {//coltact point is resolved into an add, persist etc
  }
}
  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#FFFFFF", "sketch_sep07d" });
  }
}
