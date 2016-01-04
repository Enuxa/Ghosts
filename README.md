# Ghosts

Projet de POO

## A faire
Supprimer `Interface` et ses sous-classes pour créer des sous-classes de `Game` pour l'interface textuelle, et une autre pour l'interface graphique.
Déplacer (et adapter) les méthodes `turn`, `initialize` et `create` de `Player` dans la nouvelle classe de `Game`.

**_Attention :_** Penser à générer la doc avec javadoc -d Documentation **-charset utf8** *.java

## Répartition

### Clara

#### Classes concrètes

1. `Game`
2. `Ghost`
3. Règle d'initialisation de base
4. Règle de capture

#### Classes abstraites

1. `Board`
2. `Square`

### Pierre

#### Classes abstraites

1. `Rule` et sous-classes
2. `Interface`

#### Classes concrètes

1. `Player`
2. `RuleBook`
3. Règle de mouvement
4. Règle de fin de jeu

### Ensemble

1. Interfaces
2. Extensions
