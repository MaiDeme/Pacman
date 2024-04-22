# Rapport pour le Projet PacMan

## Membres du groupe

Camille De Amorim  
Anne Beigeaud  
Antoine Loth
Maïwen Demeulle

## Introduction

*Décrivez les objetifs du projet*

L'objectif du projet est d'implémenter un jeu qui ressemble le plus a l'original:

Pacman qui se dirige bien dans le labyrinthe
Fantomes qui changent d'etats, qui sortent en entrent de leur maison
Elements de jeu respectés : dots, energizer, bonus, niveaux,...
Interface graphique logique (on peut avoir un menu pause, un menu de game over, ...) et dans l'esprit de Pacman

## Réalisation

### Avancement du projet

*Décrivez ce que vous avez implanté : quelles étapes, qu'est-ce qui fonctionne / ne fonctionne pas, etc*

### Structure et hierarchie de classe

*Décrivez les différentes classes implantées, qui implémente quoi, qui hérite de qui, etc*

*Si vous avez modifié la structure proposée (les classes / interfaces), expliquez le et justifiez vos choix*

### Tests

*Quels tests passent ? Ne passent pas ?*


*Avez-vous modifié ou rajouté des tests ?*

Différents tests ont été modifiés : 

Pacman : modification des tests pour prendre en compte le temps d'aret quand il mange des points
         modification des tests pour prendre en compte que startActors reinitialise sa direction a gauche 
                Cette fonction est utilisee pour reinitialiser la possition et la direction des acteurs

Board : modification des tests car StartActors ne sert qu'a initialiser la position des acteurs, board.start passe le jeu en état STARTED
        modification des tests sur le nombre de vie car on commence avec 3 vies
        modification des tests sur l'etat effrayé des fantomes : au niveau 6 il dure 300 frames et pas 360
        

## Organisation du travail

*Comment vous êtes-vous réparti le travail au sein du groupe ?*

Le travail a majoritairement était réparti suivant ce qu'il restait à faire et ce qui nous plaisait de faire.

Antoine : 

Maïwen : Première implémentation de l'interface et des controlleurs. Les différents Panel, l'affichage du maze, des fantomes, de pacman, des vies et l'écran de titre. Tentative de faire apparaitre l'animation quand Pacman meurt. 

Camille : 

Anne : 
-Classe Maze : tout
-Pacman : Next Move et SetIntention
-Ghost : Changement des etats des fantomes 
-Board : Vie suplémentaire de Pacman, score, Gestion des changments d'état du plateau après chaque NextFrame
-Interface : Amélioration de l'aspect graphique et KEyListener des Panels de GameOver et de NextLevel
_Controller : ReceiveAction
-Tests : Vérifications et modification des fonctions et/ou des tests si besoin

*Comment avez-vous organisé le travail commun ?*

Nous avons suivi point par point les étapes du fichier read.me. Pour la création du maze et de Pacman nous avons travaillé en groupe.
Ensuite nous avons pu nous répartir le travail.
Quand nous travaillions sur une grosse partie qui impacte le lancement de l'application nous avons travaillé sur des 
branches à part (Création de l'interface par Maïwen, Sons de Antoine, Etats de fantomes de Anne, ...).
Quand des fonctions et ou des tests ne passaient pas, nous travallions à plusieurs pour corriger le code. Par exemple, nous
avons tous travaillé sur la fonction NextMove.


## Prise de recul

### Quels progrès ?

*Décrivez là où vous pensez avoir progressé pendant ce projet*

Antoine

Maïwen : 

Camille : 

Anne : De façon générale je suis plus efficace en javascript et quand il faut rajouter une fonctionnalite j'ai dejà une
idée de comment le faire.J'ai progressé dans l'organisation des classes et leur logique (pourquoi il faut une interface, poourquoi il y a 
des héritages, ...). Je comprends aussi mieux l'interet des fonctions setValue et getValue. La ou j'ai le plus progresse
c'est dans les interfaces graphiques et les controllers car j'ai beaucoup modifié les focntions de Maïwen donc j'ai du
comprendre ce qu'elle avait fait afin de l'adapter à de nouvelles fonctionnalités.

### Quelles difficultés ?

*Quelles difficultés principales avez-vous rencontrées ? Comment les avez-vous dépassées ou contournées ?*

La difficulté principale a été de faire correspondre notre logique pour implémenter le jeu à celle des tests. Par exemple
pour la fonction Next Move de Pacman nous avons mis du temps à comprendre qu'il fallait faire des tests sur la position
après avoir bougé et non avant.

Une autre difficulté a été celle des conflits de versions quand il fallait merge nos branches de travail. 

## Conclusion

*Etes vous satisfaits ? Que feriez vous si vous deviez continuer ? Qu'est-ce que le projet vous a apporté ?*

Le jeu est très joli et fonctionne bien. Nous somes satisfaits. Si nous avions eu plus de temps nous aurions implémenté 
d'autres modes de jeu, (controles inverses, mode facile, mode difficile,... ).