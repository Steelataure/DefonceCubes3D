

void creerBords() {
 int hauteurBord = 60;
 
 // Bord Gauche
 PShape bordG = createShape();
 bordG.beginShape(QUADS);
 bordG.textureMode(NORMAL);
 bordG.texture(textureBord);
 bordG.vertex(0, height,0,0,0);
 bordG.vertex(0, height-hauteurBord,0,1,0);
 bordG.vertex(-500, height-hauteurBord,profondeurTerrain,1,1);
 bordG.vertex(-500, height,profondeurTerrain,0,1);
 bordG.endShape();
  // Bord Haut
 PShape bordH = createShape();
 bordH.beginShape(QUADS);
 bordH.textureMode(NORMAL);
 bordH.texture(textureBord);
 bordH.vertex(-500, height,profondeurTerrain,0,0);
 bordH.vertex(-500, height-hauteurBord,profondeurTerrain,1,0);
 bordH.vertex(width+500, height-hauteurBord,profondeurTerrain,1,1);
 bordH.vertex(width+500, height,profondeurTerrain,0,1);
 bordH.endShape();
  // Bord Droit
 PShape BordD = createShape();
 BordD.beginShape(QUADS);
 BordD.textureMode(NORMAL);
 BordD.texture(textureBord);
 BordD.vertex(width, height,0,0,0);
 BordD.vertex(width, height-hauteurBord,0,1,0);
 BordD.vertex(width+500, height-hauteurBord,profondeurTerrain,1,1);
 BordD.vertex(width+500, height,profondeurTerrain,0,1);
 BordD.endShape();
 terrain.addChild(bordG);
 terrain.addChild(bordH);
 terrain.addChild(BordD);
}

void creerTerrain() {
  PShape fond = createShape();
  fond.beginShape(QUADS);
  fond.textureMode(NORMAL);
  fond.texture(textureTerrain);
  fond.vertex(0, height, 0, 0, 0);
  fond.vertex(-500, height, profondeurTerrain, 1, 0);
  fond.vertex(width+500, height, profondeurTerrain, 1, 1);
  fond.vertex(width, height, 0, 0, 1);
  fond.endShape();
  
  terrain = createShape(GROUP);
  terrain.addChild(fond);
}


 
