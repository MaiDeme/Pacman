# Rapport pour le Projet PacMan

## Membres du groupe

Camille De Amorim  
Anne Beigeaud  
Antoine Loth  
Maïwen Demeulle

## Introduction

*Décrivez les objectifs du projet*

L'objectif du projet était d'implémenter un jeu qui ressemble le plus à l'original:

Pacman qui se dirige bien dans le labyrinthe  
Fantomes qui changent d'etats, qui sortent en entrent de leur maison  
Elements de jeu respectés : dots, energizer, bonus, niveaux,...  
Interface graphique logique (on peut avoir un menu pause, un menu de game over, ...) et dans l'esprit de Pacman
Ajouter le son
## Réalisation

### Avancement du projet

*Décrivez ce que vous avez implanté : quelles étapes, qu'est-ce qui fonctionne / ne fonctionne pas, etc*

Nous avons implémenté toutes les étapes, jusqu'à step 2 inclus. Les comportements de fantomes ne fonctionnent pas parfaitement dans certaines situation (cf test).

Concernant step 3, les dot counteurs pour rentrer et sortir de la maison des fantomes ne sont pas implementé.
Les vitesses rapides sont implementés mais ne fonctionnent pas parfaitement bien (un test de pacman ne passe pas).

Le mode Elroy de Blinky est fonctionels et les test passent.

Les bonus, sons et vie supplémentaire sont fonctionnels.

### Structure et hierarchie de classe

*Décrivez les différentes classes implantées, qui implémente quoi, qui hérite de qui, etc*

*Si vous avez modifié la structure proposée (les classes / interfaces), expliquez le et justifiez vos choix*

- Audio
  - SoundManager.java  : Cette classe nous sert de gestionnaire pour les effets sonores. Elle stocke et contrôle la lecture des sons. Elle permet de charger et de jouer le sons.
  - SoundName.java : Cette classe est un reférentiel des effets sonores.

- View
  - ActorPanel : Herite de Jpanel, affiche les acteurs du jeu (pacman, fantomes, bonus, dots, energizers).
  - BoardView : Hérite de Jpanel, implemente PacmanView, affiche le plateau de jeu.
  - ButtonListener : Implemente ActionListener, permet de gérer les actions des boutons.
  - DrawPanel : Herite de Jpanel, permet de dessiner les éléments du jeu.
  - GameOver : Herite de Jpanel, affiche l'écran de fin de jeu.
  - KeyLevel, KeyMove, KeyGameOver, KeyStart : Héritent de KeyListener et permettent de gérer les touches du clavier pour les différentes fonctionnalités selon les états du jeu.
  - PacmanLayout: enum pour les interfaces.
  - PacmanView : Interface pour la vue du jeu.
  - SpriteLoader : Permet de charger les images pour les sprites des différents éléments du jeu.
  - TitleScreen : Herite de Jpanel, affiche l'écran de titre du jeu.
  
- Model
  - Actors
    - AbstractActor : Classe abstraite qui définit les acteurs du jeu.
    - AbstractGhost : Classe abstraite qui définit les fantômes.
    - Actor : Interface pour les acteurs du jeu.
    - ActorType : Enum pour les types d'acteurs.
    - Blinky : Hérite de AbstractGhost, définit le fantôme Blinky.
    - Bonus : Interface, hérite de Actor.
    - BonusImpl : Implémente Bonus, définit les bonus du jeu.
    - BonusType : Enum pour les types de bonus.
    - Clyde : Hérite de AbstractGhost, définit le fantôme Clyde.
    - Ghost : Interface pour les fantômes, hérite de Actor.
    - GhostPenState : Enum pour les états de la maison des fantômes.
    - GhostState : Enum pour les états des fantômes.
    - GhosType : Enum pour les types de fantômes.
    - Inky : Hérite de AbstractGhost, définit le fantôme Inky.
    - Pacman : Hérite de AbstractActor, définit Pacman.
    - Pinky : Hérite de AbstractGhost, définit le fantôme Pinky.
  - Board
    - AbstractBoard : Classe abstraite qui définit le plateau de jeu.
    - Board : Interface pour le plateau de jeu.
    - BoardEvent : Classe pour les événements du plateau de jeu.
    - BoardEventType : Enum pour les types d'événements du plateau de jeu.
    - BoardInitialisationException : Exception pour l'initialisation du plateau de jeu.
    - BoardState : Enum pour les états du plateau de jeu.
    - ClassicBoard : Hérite de AbstractBoard, définit le plateau de jeu classique.
    - Counter : Classe pour les compteurs du plateau de jeu.
    - TestBoard : Hérite de AbstractBoard, définit le plateau de jeu de test.
  - Maze
    - Grid : Classe pour la grille du labyrinthe.
    - Maze : Interface pour le labyrinthe.
    - Tile : Classe pour les tuiles du labyrinthe.
    - TilePosition : Classe pour les positions des tuiles du labyrinthe.
  - Direction : Enum pour les directions.
- Control
  - Controller : Interface pour le contrôleur.
  - ForbiddeMoveException : Exception pour les actions interdits.
  - GameAction : Enum pour les actions du jeu.
  - InterfaceMode : Enum pour les modes d'interface.
  - SimpleController : Implémente Controller, définit le contrôleur simple (sans la view).
  - VisualController : Implémente Controller, définit le contrôleur visuel.
- GameType : Enum pour les types de jeu.
- PacmanGame : Classe principale du jeu, permet de lancer le jeu.
- PacManException : Exception pour le jeu Pacman.
### Tests

*Quels tests passent ? Ne passent pas ?*

Lorsque le projet était à l'était step 1 et 2 tous les test respectifs passaient.

Tous les test du controller et du maze (step3 & 4) passent.

A l'état actuel du projet, certains tests (step 3) des fantomes et de board  ne passent pas notamment ceux en rapport avec la maison des fantomes (pen ghost) et les Dot Counters.

*Avez-vous modifié ou rajouté des tests ?*

Différents tests ont été modifiés :

Pacman :

- modification des tests pour prendre en compte le temps d'aret quand il mange des points
- modification des tests pour prendre en compte que startActors reinitialise sa direction a gauche  
                Cette fonction est utilisee pour reinitialiser la possition et la direction des acteurs

Board :

- modification des tests car StartActors ne sert qu'a initialiser la position des acteurs, board.start passe le jeu en état STARTED
- modification des tests sur le nombre de vie car on commence avec 3 vies
- modification des tests sur l'etat effrayé des fantomes : au niveau 6 il dure 300 frames et pas 360

Blinky : Modification de l'ordre de startActors car cette fonction réinitialise les valeurs des acteurs

## Organisation du travail

*Comment vous êtes-vous réparti le travail au sein du groupe ?*

Le travail a majoritairement était réparti suivant ce qu'il restait à faire et ce qui nous plaisait de faire.

Antoine :

- Mise en place du son (MangerDot, MangerBonus, Evènement_FRIGHTENED, Pacman_Death et autres) via la création d'une classe "SoundManager" spécifique à cela.
- Bonus : Faire apparaître bonus quand pacman mange un nombre suffisant de point, faire disparaitre le bonus lorsqu'il est mangé par PacMan et faire augmenter le score. Faire en sorte que les bonus apparaissant sont différents selon le niveaux.

Maïwen :

- Première implémentation de l'interface et des controlleurs. Les différents Panel, l'affichage du maze, des fantomes, de pacman, des vies et l'écran de titre (toute la view).
- Tentative de faire apparaitre l'animation quand Pacman meurt (visible sur une autre branche mais très peu fonctionnel).
- Debut de mise en place d'une autre hiérachie de classe pour la View avec une classe abstraite pour dessiner les actors, fantomes... (autre branche).
- High speed des actors et snapping
- Cornering pour Pacman (pas fonctionnels)

Camille : début de Pacman, début du board, fantômes, début de l'enclos des fantômes

Anne :  

- Classe Maze : tout
- Pacman : Next Move et SetIntention
- Ghost : Changement des etats des fantomes, Elroy
- Board : Vie suplémentaire de Pacman, score, Gestion des changments d'état du plateau après chaque NextFrame
- Interface : Amélioration de l'aspect graphique et KEyListener des Panels de GameOver et de NextLevel
- Controller : ReceiveAction
- Tests : Vérifications et modification des fonctions et/ou des tests si besoin

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


Antoine : En premier temps il s'agit d'une meilleur maîtrise de l'utilisation de git. Ensuite je perçois avoir nettement progressé dans ma compréhension des relations entres les différentes classes et la logique d'utilisation des méthodes. C'est à travers ce projet que j'ai réellement compris l'intérêt de maîtriser un telle langage de programmation.

Maïwen : Pour ma part, j'ai surtout progressé sur les interfaces graphiques et les controllers. J'ai pu découvrir des éléments que nous n'avions pas vu en cours comme les HashMap et l'intégration d'image, la manipulation de police de caractères et de sprites pour les animations. Globalement je comprends mieux comment utiliser les classes et leurs méthodes et dans quels circonstances faire appel à des interfaces et hiérarchies de classes.

Camille : Je connais beaucoup mieux Pacman qu'avant. Je ne pense pas avoir particulièrement progressée en Java par contre.

Anne : De façon générale je suis plus efficace en java et quand il faut rajouter une fonctionnalite j'ai dejà une
idée de comment le faire. J'ai progressé dans l'organisation des classes et leur logique (pourquoi il faut une interface, poourquoi il y a
des héritages, ...). Je comprends aussi mieux l'interet des fonctions setValue et getValue. La ou j'ai le plus progresse
c'est dans les interfaces graphiques et les controllers car j'ai beaucoup modifié les fonctions de Maïwen donc j'ai du
comprendre ce qu'elle avait fait afin de l'adapter à de nouvelles fonctionnalités.

### Quelles difficultés ?

*Quelles difficultés principales avez-vous rencontrées ? Comment les avez-vous dépassées ou contournées ?*

La difficulté principale a été de faire correspondre notre logique pour implémenter le jeu à celle des tests. Par exemple
pour la fonction Next Move de Pacman nous avons mis du temps à comprendre qu'il fallait faire des tests sur la position
après avoir bougé et non avant.

De plus, plus on avançait dans les étapes du projet plus il était possible d'implementer une fonctionnallité de multiple manières ce qui a parfois posé problème pour merge nos branches de travail car cela posait des conflits où il fallait choisir une implémentation au risque de casser le jeu.

Travailler et réutiliser le travail des autres a aussi été une difficulté, car il fallait d'abord comprendre ce que les autres avait fait alors que nous n'avions pas forcément les mêmes idées pour implémenter une fonctionnalité.

## Conclusion

*Etes vous satisfaits ? Que feriez vous si vous deviez continuer ? Qu'est-ce que le projet vous a apporté ?*

Le jeu est très joli et fonctionne bien. Nous somes satisfaits. Si nous avions eu plus de temps nous aurions implémenté d'autres modes de jeu, (controles inverses, mode facile, mode difficile,possiblité d'enregistrer des highscores,... ) et nous aurions amélioré l'interface graphique (animations, nouveau fantomes ? ...).
