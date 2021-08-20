//Alexandre Buisset 

//Gameplay DefonceCubes
//Vous devez toucher les cubes grâce à votre balle
//Pour la direction de la balle, c'est en fonction du placement
//de la souris et de la jauge de puissance
//Plus la jauge de puissance est forte, plus la balle va vers le centre
//Plus la jauge de puissance de faible, plus la balle va vers le côté


color remp = 0;
PImage rules;
PImage parametre;
PImage gameover;
PImage barrevie;
PImage victoire;

void setup() {
  size(1280,720, P3D);
  noStroke();
  //noCursor();
  rules = loadImage("info.png");
  parametre = loadImage("parametre.png");
  gameover = loadImage("gameover.jpg");
  parametre = loadImage("parametre.png");
  barrevie = loadImage("barrevie.png");
  victoire = loadImage("victory.jpg");
  
  remp = color(0,255,255);
  chargeRessources();
}

void draw() {
  background(fond);
  shape(terrain);
  lights();
  obstacle();
  puissance();
  scoreboard();
}
