#Radix Converter 1.0
--------------------

Il sâ€™agit dâ€™une Calculatrice pour lâ€™informaticien : cette calculatrice permettra au moins de calculer en base 2, 10 et 16, de rentrer et visualiser dans ces bases les valeurs entiÃ¨res signÃ©es codÃ©es en complÃ©ment Ã  deux et les nombres flottants codÃ©s avec la norme IEEE-754, de mettre en Ã©vidence la mantisse et l'exposant, d'effectuer des conversions.

## Vocabulaire

Radix 
:   la base - http://en.wikipedia.org/wiki/Radix

Decimal
:   base 10

Hexadecimal
:   base 16

Binary
:   base 2

La norme IEEE-757
:   prÃ©sentation standardisÃ©e de nombres rÃ©els en binaire dans lâ€™ordinateur ([Wikipedia](http://en.wikipedia.org/wiki/IEEE_754-1985))

ComplÃ©ment Ã  deux
:   reprÃ©sentation binaire des entiers relatifs ([Wikipedia](http://fr.wikipedia.org/wiki/Compl%C3%A9ment_%C3%A0_deux))

## Fonctionnalites et exigences

### Fonctionnalites de base: conversion

 * Application client-lourd: Java Swing 
 * Capable de traiter les nombre rÃ©els en 64 bits et les nombre entiers signÃ©s 32 bits
 * Utilisateurs peut rentrer facilement les nombres en Binaire / Hexa decimal / decimal
 * La conversion se fait en temps rÃ©el (qui se rÃ©alise une fois la valeur dâ€™utilisateur est rentrÃ©)
 * Montrer les valeurs en toutes bases (2, 10, 16) en mÃªme temps sur lâ€™interface
 * Capable dâ€™enregistrer temporairement un rÃ©sultat de conversion afin de comparer les diffÃ©rences conversion.

### Fonctionnalites optionnels: calculatrice

 * Evaluer lâ€™expression mathÃ©matiques `+`, `-`, `*`, `/` en rentrant les valeurs en Base 2, 10, 16
 * Afficher les rÃ©sultats en base 2, 10, 16 en mÃªme temps
 * Enregistrer temporairement un rÃ©sultat dâ€™Ã©valuation

### License

Lâ€™application sera Open Sources sous [GNU General Public License](http://www.gnu.org/licenses/gpl.html)

## Compilation and Execution

There are 3 ways to compile and run the project
 1. Command Line.
 2. Maven (recommended): http://maven.apache.org
 3. Import as eclipse project

### Use Maven (recommended)

If you are using Maven, the folder /lib is not necessary, because Maven will download and manage all dependencies in your local repository.

Unzip the package, and Go inside the folder

    cd C:\some\path\Calculator

Compile the project: generate *.class and put them to the /target/classes folder

    mvn compile

Run Unit Test:

    mvn test

Build jar file:

    mvn jar:jar

Launch program:

    mvn exec:java -Dexec.mainClass="com.bloan.calculator.MainApp"

### Use Command line

Unzip the package, and Go inside the folder

    cd C:\some\path\Calculator

Compile the project: generate `*.class` to the `/target` folder

    dir /s /B *.java sources.txt
    javac @sources.txt -d target -classpath ./lib/*
    copy .\src\main\resources\com\bloan\calculator\* .\target\com\bloan\calculator

Launch program:

    java -cp ./target;./lib/* com.bloan.calculator.MainApp


### Dependencies 

This application use some open sources library in the `/lib` library:

 - [JUnit](http://junit.org/)
 - [Design Grid Layout](https://designgridlayout.java.net/)
 - [Guava](https://code.google.com/p/guava-libraries/)

If you are using Maven, you can throw away the folder `/lib` and let maven download dependencies library for you