DROP TABLE ActionTir;
DROP TABLE ActionDeplacement;
DROP TABLE ActionPlacement;
DROP TABLE AGagne;
DROP TABLE Etat;
DROP TABLE Action;
DROP TABLE Bateau;
DROP TABLE Tour;
DROP TABLE Participe;
DROP TABLE Parties;
DROP TABLE Joueur ;
DROP SEQUENCE sequence_parties;
DROP SEQUENCE sequence_bateau;
DROP SEQUENCE sequence_action;
DROP SEQUENCE sequence_etat;

CREATE SEQUENCE sequence_parties;
CREATE SEQUENCE sequence_bateau;
CREATE SEQUENCE sequence_action;
CREATE SEQUENCE sequence_etat;



CREATE TABLE Joueur
(
    pseudo VARCHAR(20) PRIMARY KEY,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    email VARCHAR(255),
    dateNaissance DATE,
    numRue INT,
    ville VARCHAR(255),
    check (NumRue >= 0)
);

CREATE TABLE Parties
(
		idPartie INT PRIMARY KEY,
		dateDebut DATE
);

CREATE TABLE Participe
(
		pseudo VARCHAR(20),
		idPartie INT references Parties (idPartie),
		PRIMARY KEY(pseudo, idPartie)
);

CREATE TABLE Tour
(
	numTour INT,
	idPartie INT references Parties(idPartie),
	pseudo VARCHAR(20) references Joueur(pseudo),
	PRIMARY KEY(NumTour,idPartie)
);


CREATE TABLE Bateau
(
	idBateau INT,
	type VARCHAR(20),
	-- ('destroyer', 'escorteur'),
	pseudo VARCHAR(20),
	FOREIGN key(pseudo) references Joueur(pseudo),
	CONSTRAINT type_check CHECK(type IN ('destroyer','escorteur')),
	PRIMARY KEY (idBateau)
);

CREATE TABLE Action
(
	idAction INT,
	numTour INT ,
	numAction INT,
	idPartie INT ,
	idBateau INT references Bateau(idBateau),
	PRIMARY KEY(idAction),
	FOREIGN KEY (idPartie,numTour) references Tour(idPartie,numTour)
);

CREATE TABLE Etat (
	idEtat INT,
	X INT,
	Y INT,
	direction VARCHAR(20),
	vie INT,
	idAction INT ,
	numAction INT ,
	idBateau INT references Bateau(idBateau),
	taille INT,
	PRIMARY KEY(idEtat),
	FOREIGN KEY (idAction) references Action(idAction),
	CHECK (taille BETWEEN 2 and 3),
	CHECK (vie BETWEEN 0 and 3),
	CONSTRAINT check_pivot CHECK (X BETWEEN 1 and 10 and Y BETWEEN 1 and 10),
	CONSTRAINT direction_check CHECK(direction IN ('NORD','SUD','EST','OUEST')),
	check ((direction = 'NORD' and Y - taille >= 0) or
	  		(direction = 'SUD' and Y + taille <= 11) or 
	 		(direction = 'OUEST' and X - taille >= 0)  or 
	 		(direction = 'EST' and X + taille <= 11))
);

CREATE TABLE AGagne(
	pseudo VARCHAR(20) references Joueur(pseudo),
	idPartie INT references Parties(idPartie),
	PRIMARY KEY(pseudo,idPartie)
);


CREATE TABLE ActionDeplacement(
	idAction INT references Action(idAction),
	sens VARCHAR(7),
	-- ('gauche','droite'),
	CONSTRAINT sens_check CHECK(sens IN ('GAUCHE','DROITE','AVANT','ARRIERE')),
	PRIMARY KEY (idAction)
);

CREATE TABLE ActionPlacement(
	idAction INT references Action(idAction),
	--sens VARCHAR(7),
	-- ('gauche','droite'),
	CONSTRAINT sens_check_placement CHECK(sens IN ('GAUCHE','DROITE','AVANT','ARRIERE')),
	PRIMARY KEY (idAction)
);



CREATE TABLE ActionTir(
	idAction INT references Action(idAction),
	X INT,
	Y INT,
	PRIMARY KEY (idAction),
	CHECK (X BETWEEN 1 and 10 and Y BETWEEN 1 and 10 )
);
