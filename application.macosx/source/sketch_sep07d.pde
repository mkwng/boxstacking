// Import everything exposed by JBox2D (except the testbed stuff)
import org.jbox2d.common.*;
import org.jbox2d.collision.*;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.joints.*;
import org.jbox2d.dynamics.contacts.*;
// BoxWrap2D
import org.jbox2d.p5.*;

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
{-24.344276, -20.947254, -0.60476685, 1.8598442, 41.504135, -25.63811, 49.892494, 0.6359024, -28.622171},
{-42.5667, 35.789314, 2.3339386, 41.330315, -14.195496, -2.7436962, -5.661728, -23.460419, -20.399113},
{-41.24933, -13.548134, -7.7232533, 30.298668, 30.21585, -4.7507687, -40.823784, -26.146608, 23.2701},
{21.345146, -30.761932, 3.357174, 7.8124046, -22.92784, 3.324356, 24.061646, 39.92576, 6.8192444},
{-5.0929604, 18.217506, 17.649765, -42.113823, 2.0999832, -3.2476444, 8.85939, 27.201683, 16.15247},
{-7.382286, -2.2173767, -16.293976, -34.6991, 9.811157, 28.03786, -24.612326, 37.910576, 0.6605644},
{-35.18751, 25.878105, 25.449818, -44.46338, -1.0795937, 11.966827, 7.5566483, 4.580147, 19.213032},
{-25.794388, 48.901817, -29.523243, -30.827105, -42.7244, -22.740509, 17.53012, -18.60202, 29.348572}
};

boolean on = true;

void setup()
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

void draw()
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


void keyPressed()
{
  on = false;
  physics.destroy();
  physics = null;
  InitScene();
  CreateObjects();
}

void InitScene()
{
  // Set up the engine with the sketch's dimensions
  physics = new Physics(this, width, height);
  m_world = physics.getWorld();
  m_world.setContactListener(new CustomListener());
  physics.setDensity(3.0);
  physics.setRestitution(0.8); // Make them super-rebounding
}

void CreateObjects()
{
  // Middle of the world
  float hw = width / 2.0;
  float hh = height / 2.0;
  
  
  
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
  void add(ContactPoint cpoint)
  {//contacts collide for the first time
  }

  void persist(ContactPoint cpoint)
  {//contacts continue to collide - i.e. resting on each other
  }

  void remove(ContactPoint cpoint)
  {//objects stop touching each other
  }

  void result(ContactResult point)
  {//coltact point is resolved into an add, persist etc
  }
}
