# Vaatimusmäärittely
## Sovelluksen tarkoitus
Sovelluksen avulla käyttäjä voi pitää kirjaa liikuntasuorituksistaan. Ensisijaisesti sovellus on suunnattu kestävyysharjoitteluun, jota tuetaan voimaharjoittelulla. Sovellus tarjoaa käyttäjälle myös mahdollisuuden tarkastella suorituksiaan ja saada tilastotietoa, jota voi hyödyntää harjoittelussaan.
## Käyttäjät
Aluksi sovelluksella on ainoastaan yksi käyttäjärooli eli normaali käyttäjä. Myöhemmin sovellukseen saatetaan lisätä rooli valmentaja.
## Perusversion tarjoama toiminnallisuus
### Ennen kirjautumista
* käyttäjä voi luoda järjestelmään käyttäjätunnuksen
  * käyttäjätunnuksen tulee olla uniikki ja pituudeltaan vähintään 3 merkkiä TEHTY OSITTAIN
  * lisäksi on asetettava salasana TEHTY OSITTAIN (Ei toistaiseksi kryptausta.)
  * käyttäjätunnuksen luonnin yhteydessä asetetaan maksimisyke, jonka voi asettaa suoraan tai laskea laskurilla TEHTY
* käyttäjä voi kirjautua järjestelmään
  * kirjautuminen onnistuu syöttämällä oma käyttäjätunnus ja salasana TEHTY OSITTAIN (Ei salasanan tarkistusta.)
  * käyttäjän tietojen olemassaolo tarkistetaan ja käyttäjätiedot haetaan tietokannasta TEHTY
  * jos kirjautumistiedot ovat puutteelliset, järjestelmä ilmoittaa tästä TEHTY
### Kirjautumisen jälkeen
* käyttäjä voi halutessaan muokata käyttäjäprofiiliaan
* käyttäjä voi lisätä uuden liikuntasuorituksen TEHTY
* käyttäjä näkee perusnäkymässä kuluvan viikon harjoittelun TEHTY
* käyttäjä voi myös tarkastella aiempia suorituksiaan
* käyttäjä voi tarkastella yhteenvetoa harjoittelustaan valitsemallaan ajanjaksolla
  * yhteenveto tarjoaa tietoa harjoittelumääristä, harjoittelun jakautumisesta eri syketasoille sekä määrien ja vauhdin kehittymisestä
* käyttäjä voi kirjautua ulos järjestelmästä TEHTY
## Jatkokehitysideoita
Ajan salliessa perusversiota voidaan täydentää esim. seuraavilla ominaisuuksilla:
* valmentajanäkymä, jossa valmentaja näkee valmennettavansa harjoittelun ja voi lisätä harjoituksia valmennettavalle
* yksityiskohtaisempi seuranta myös voimaharjoittelulle
* toiminto, jolla ohjelma ehdottaa seuraavaa harjoitusta 
