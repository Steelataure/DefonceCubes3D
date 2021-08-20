import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class DefonceCubes_V2 extends PApplet {

//Alexandre Buisset 

//Gameplay DefonceCubes
//Vous devez toucher les cubes grâce à votre balle
//Pour la direction de la balle, c'est en fonction du placement
//de la souris et de la jauge de puissance
//Plus la jauge de puissance est forte, plus la balle va vers le centre
//Plus la jauge de puissance de faible, plus la balle va vers le côté


int remp = 0;
PImage rules;
PImage parametre;
PImage gameover;
PImage barrevie;
PImage victoire;

public void setup() {
  
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

public void draw() {
  background(fond);
  shape(terrain);
  lights();
  obstacle();
  puissance();
  scoreboard();
}


public void creerBords() {
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

public void creerTerrain() {
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


 
int raqX, raqY, raqZ, raqR, profondeurTerrain;
PShape raquette, terrain;
PImage fond, textureTerrain, textureBord;

public void chargeRessources() {
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
//Gameplay DefonceCubes
//Vous devez toucher les cubes grâce à votre balle
//Pour la direction de la balle, c'est en fonction du placement
//de la souris et de la jauge de puissance
//Plus la jauge de puissance est forte, plus la balle va vers le centre
//Plus la jauge de puissance de faible, plus la balle va vers le côté
//Pour la souris il y a la GAUCHE, le CENTRE et la DROITE et rien d'autres
//ATTENTION l'indicateur n'a pas d'importance dans le mouvement de la balle



float jauge = 0;      //ici c'est la jauge de puissance
boolean rebond = false;  //Etat si jauge max et minimum
boolean perdu = false; boolean gagner = false;
boolean regle = false; boolean option = false;
int lancer = 0; int info = 0; //Si clique ou pas encore cliquer
int outil = 0;
float power; //puissance de la balle
float barreviex = 618; float barreviey = 18;
//barre de vie
float viex = 180; float viex2 = 20; int vie = 3; int level = 1;
float victoirex = 0;
//Position initiale de la balle
float boulex = 650; float bouley = 510; float boulez = 280;
int bouton = (255); //couleur bouton regle
float obstacle1x = 500; float obstacle1y = 510; float obstacle1z = -400; //obstacle 1 coordonées
float obstacle2x = 900; float obstacle2y = 510; float obstacle2z = -500; //obstacle 2 coordonées
float obstacle3x = 300; float obstacle3y = 510 ; float obstacle3z = -600; //obstacle 3 coordonées
//Détermine dans quel side vous avez cliquer
boolean droit = false; boolean gauche = false; boolean milieu = false;
//obstacle aléatoire
float randostacle1x = random(260,900);
float randostacle2x = random(300,900);
float randostacle3x = random(400,900);




//permet la génération aléatoire des obstacles
public void aleatoire(){
  
  randostacle1x = random(200,900);
  randostacle2x = random(300,900);
  randostacle3x = random(400,900);
}

public void puissance(){

  //Si le jeu n'est pas gagné alors action de balle, jauge etc...
 if (level != 4 || option != true){
   stroke(0); strokeWeight(4);
   fill(255);    //rectangle blanc
   rect(20,20,500,30);
   noStroke();
   fill(0xffD30000);    //rectangle rouge qui augmente
   rect(20,20,jauge,30);
     
   fill(255);
   pushMatrix(); //translate nouvelle
   translate(boulex,bouley,boulez);
   sphere(15);  //la spehere est translate
   popMatrix(); //reinitialise la translate 
   
    power = (jauge)*1.2f;    //La puissance de la balle est celle de la jauge
  
    if (lancer != 1){    //si pas encore cliquer
     jauge += 6;        //jauge (rectangle rouge) augmente
     
   fill(0);
   stroke(0); strokeWeight(10);
   line(650,600,100,mouseX,650,-250); //C'est le trait de la balle pur voir la direction, IL FAUT TROUVER MIEUX
   
   if (jauge >= 500){  //si la jauge est max
     rebond = true;
   }
   if (jauge <= 10){  //si jauge minimum
     rebond = false;
   }
   if (rebond == true){
     jauge -= 12;   //vitesse de la jauge
   }  
  }
  
   if (lancer == 1){  //si clique activer
     fill(255);
  
     if (boulez <= -800 || boulex < 200 || boulex > 1100){ //si balle en z trop loin (-1200)
      boulex = 650;
      boulez = 280;              //la balle reviens point initiale
      vie -= 1;
      perdu = true;         //perdu
      
    if (perdu == true){  //si partie perdu
           boulez = boulez -(power /35);  //la vitesse de la balle est contraire pour la remettre à 0
           lancer = 0;             //on rejoue le lancer
           droit = false;
           gauche = false;
           milieu = false;
      }
     }
   //Si pas perdu et lancer en action
     if (perdu == false || lancer == 1){
         boulez = boulez -(power /35); 
         //boulez = boulez -((power) /exp(3.5)); //vitesse de la balle variante
         
         if (droit == true){             
           boulex = boulex + (200/80) ; //si la souris à droite de la boule initiale alors va a droite
         }
          if (gauche == true){             
           boulex = boulex - (200/80); //si la souris à gauche de la boule initiale alors va a gauche
         }
         if (milieu == true){             
           boulex = 650;
         }
     }
   }
 }
}

public void scoreboard(){
  
  
  //logo paramètre
   fill(200);
   image(parametre,1105,18,165,90); //675 avant
   
  //logo règle du jeu
   fill(bouton);
   image(rules,1020,20,80,80); //840
   
   //remplissage de la barre de vie sans image
   fill(255,0,0);
   noStroke();
   rect(barreviex+201,barreviey+13,viex2,39); //806 33 20 39
   rect(barreviex+36,barreviey+10,viex,45); //641 30 180 45
   fill(255);
   image(barrevie,barreviex,barreviey,227,63); //605 20 227 63
   
  if (vie == 3){
    viex2 = 20;
    viex = 180;
  }
  if (vie == 2){
    viex2 = 0;
    viex = 130;
  }
  if (vie == 1){
    viex2 = 0;
    viex = 70;
  }
  
  if (info %2 == 1){
    regle = true;       //Si impaire affiche les regles du jeu 
    bouton = (0);     //bouton regles deviens noir
  }
  if (info %2 == 0){ //Si paire 
    regle = false;
    bouton = (255);
  }
   if (regle == true){ //Affichage des règles du jeu
       fill(255); stroke(0); strokeWeight(1);
       rect(820,120,300,160);
       fill(0);          
       textSize(20);
       text("Règle du jeu",910,155);
       text("Lancez la balle sur les cubes",830,200);
       text("pour gagner \n ",830,220);
       text("Grace à la souris + puissance",830,245);
       text("A pour restart",830,270);
     }
  if (outil %2 == 0){
    option = false;
  }
  if (outil %2 == 1){
    option = true;
  }  
   if (level == 4){
      fill(200);
      image(victoire,victoirex,-100);
  }
  //Si perdu (plus de vie)
  if (vie <= 0){
    fill(255);
    info = 0;
    boulez = 5000; //pour ne plus voir la balle
    image(gameover,0,0,1280,720);    
  }
   if (option == true){
     //OPTION A METTRE (OPTIONNEL)
  } 
}


public void obstacle(){
  
 fill(255); stroke(0); strokeWeight(2.5f);
 //Niveau 1

 if (level == 1){
 pushMatrix(); //translate nouvelle
 translate(randostacle1x,obstacle1y,obstacle1z); //200 500 -600
 box(80);  //la spehere est translate
 popMatrix(); //reinitialise la translate 
 
   if (boulez > obstacle1z-10 & boulez < obstacle1z +85){
       if (boulex > randostacle1x-80 & boulex < randostacle1x +85){ //obstacle du level 1
         
         gagner = true;
         if (gagner == true){
          level += 1;
          boulex = 650; boulez = 280;
          lancer = 0;
          droit = false; gauche = false; milieu = false;
         }
       }
   }
  }
  //Niveau 2
  if (level == 2){
    strokeWeight(2);
    pushMatrix(); //translate nouvelle
    //obstacle2x ou randostacle2x pour random
    translate(randostacle2x,obstacle2y,obstacle2z); //obstacle du level 2
    box(60);  //la spehere est translate
    popMatrix(); //reinitialise la translate 
    if (boulez > obstacle2z-10 & boulez < obstacle2z +65){
       if (boulex > randostacle2x-60 & boulex < randostacle2x +65){ //obstacle du level 1
         
         gagner = true;
         if (gagner == true){
          level += 1;
          boulex = 650; boulez = 280;
          droit = false; gauche = false; milieu = false;
          lancer = 0;
         }
       }
   }
  }
  //Niveau 3
  if (level == 3){
    strokeWeight(1.5f);
    pushMatrix(); //translate nouvelle
    translate(randostacle3x,obstacle3y,obstacle3z); //obstacle du level 2
    box(40);  //la spehere est translate
    popMatrix(); //reinitialise la translate 
    if (boulez > obstacle3z-10 & boulez < obstacle3z +45){
       if (boulex > randostacle3x-40 & boulex < randostacle3x+45){ //obstacle du level 1
         
         gagner = true;
         droit = false; gauche = false; milieu = false;
         if (gagner == true){
          level += 1;
          info = 0;
          aleatoire();
          noLoop();
         }
       }
   }  
  }
}

public void mouseClicked(){
 //quand on clique avec la souris
 
 //Si tu clique sur l'icone info 
  if (level != 4 & mouseX > 1015 & mouseX < 1100 & mouseY < 120 & mouseY > 20){ 
    info += 1;
  }
  //Si tu cliques sur l'icone outil
  else if (level != 4 & mouseX > 1150 & mouseX < 1242 & mouseY < 110 & mouseY > 10){ //Si tu clique sur l'icone
  outil += 1;
}
//Sinon si level 4 et souris sur le YES de game over
  else if (vie == 0 & mouseX > 540 & mouseX < 630 & mouseY > 500 & mouseY < 550){
    loop();
    aleatoire();
    boulex = 650; bouley = 510; boulez = 280;
    vie = 3;
    level = 1;
    lancer = 0;   
    perdu = false; gagner = false;
    aleatoire();
  }
  //sinon si gagner et Restart 
  else if (level == 4 & mouseX > 470 & mouseX < 800 & mouseY > 500 & mouseY < 645){
    level = 1;
    aleatoire();
    boulex = 650; bouley = 510; boulez = 280;
    lancer = 0;   
    perdu = false; gagner = false;
    vie = 3;
    loop();
  } 
  //Si NO de gameover alors quitte le programme
  else if (vie == 0 & mouseX > 700 & mouseX < 780 & mouseY > 500 & mouseY < 550){
    exit();   
  }
  //Si lancer à droite
  else if (lancer == 0 & mouseX > 655){                 
     lancer = 1;
     droit = true;
  }
  //Si lancer à gauche
  else if (lancer == 0 & mouseX < 635){
     lancer = 1;
     gauche = true;
  }
  //Si lancer au milieu
  else if (lancer == 0 & mouseX > 655 & mouseX < 635){  //si la souris entre 645 et 645 la balle va tout droit
  lancer = 1;
  milieu = true;
  }
  else{lancer = 1;      //Etat 1 donc permet d'arrêter la jauge 
  }
}

public void keyPressed(){
  
   if (key == 'a'){ // A changer a préfèrence permet de restart la game
    boulex = 650; bouley = 510; boulez = 280;
    aleatoire();
    vie = 3;
    level = 1;
    lancer = 0;   
    perdu = false; gagner = false;
 }
}

///////////////////////////////////////////////////////////////
/*

Futur possibilités optionnelles :
  Option
  -Mode vent (vent poussant dans une direction)
  -Activer ou désactiver les obstacles aléatoires
  -Activer le mode avec du son / si mise du son
  -Difficulté (Facile/Normal/Difficile) changer la vitesse de la jauge de puissance
  -Bouton pour choisir de quitter/reprendre
  
  -Mise en place du score
  -Mise en place du temps
  -Mettre une bonne affichage du niveau actuel
  
*/
//////////////////////////////////////////////////////////////
  public void settings() {  size(1280,720, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "DefonceCubes_V2" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
