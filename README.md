# Treenipäiväkirja
Sovelluksen avulla käyttäjä voi pitää kirjaa liikuntasuorituksistaan. Ensisijaisesti sovellus on suunnattu kestävyysharjoitteluun, jota tuetaan voimaharjoittelulla. Sovellus tarjoaa käyttäjälle myös mahdollisuuden tarkastella suorituksiaan ja saada tilastotietoa, jota voi hyödyntää harjoittelussaan.

## Sovellus
[Vaatimusmäärittely](https://github.com/jp-tulijoki/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/jp-tulijoki/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Käyttöohje](https://github.com/jp-tulijoki/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Tuntikirjanpito](https://github.com/jp-tulijoki/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

## Releaset

[Release 1](https://github.com/jp-tulijoki/ot-harjoitustyo/releases/tag/v0.5)

[Release 2](https://github.com/jp-tulijoki/ot-harjoitustyo/releases/tag/v0.6)

### Komentorivitoiminnot

Komentorivitoiminnot suoritetaan kansiossa `WorkoutJournal`.

Projektin koodi suoritetaan komennolla:
`mvn compile exec:java -Dexec.mainClass=Main.Main`

Testi suoritetaan komennolla:
`mvn test`

Testiraportti generoidaan komennolla:
`mvn test jacoco:report`

Tiedostoon checkstyle.xml määritellyt tarkistukset suoritetaan komennolla:
`mvn jxr:jxr checkstyle:checkstyle`

Suoritettava .jar-tiedosto generoidaan komennolla:
`mvn package`

Javadoc generoidaan komennolla `mvn javadoc:javadoc`
