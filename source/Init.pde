int raqX, raqY, raqZ, raqR, profondeurTerrain;
PShape raquette, terrain;
PImage fond, textureTerrain, textureBord;

void chargeRessources() {
  // Fond
  fond = loadImage("fond2.png");
  fond.resize(width, height);
  // Terrain
  profondeurTerrain = -2500;
  textureTerrain = loadImage("texture.jpg");
  creerTerrain();
  // Bord Terrain
  textureBord = loadImage("bois.jpg");
  creerBords();
  // Raquette
  raqX = width/2;
  raqY = height-20;
  raqZ = -20;
  raquette = createShape(BOX,4,1,1);
  raquette.scale(20);
  raqR = (int) (raquette.getDepth()/2);
}
