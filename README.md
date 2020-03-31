# Treenipäiväkirja
Sovelluksen avulla käyttäjä voi pitää kirjaa liikuntasuorituksistaan. Ensisijaisesti sovellus on suunnattu kestävyysharjoitteluun, jota tuetaan voimaharjoittelulla. Sovellus tarjoaa käyttäjälle myös mahdollisuuden tarkastella suorituksiaan ja saada tilastotietoa, jota voi hyödyntää harjoittelussaan.

## Sovellus
[Vaatimusmäärittely](https://github.com/jp-tulijoki/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Tuntikirjanpito](https://github.com/jp-tulijoki/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

### Komentorivitoiminnot

Projektin koodi suoritetaan komennolla:
`mvn compile exec:java -Dexec.mainClass=workoutjournal.UI.WorkoutJournalUI`

Testi suoritetaan komennolla:
`mvn test`

Testiraportti generoidaan komennolla:
`mvn test jacoco:report`
