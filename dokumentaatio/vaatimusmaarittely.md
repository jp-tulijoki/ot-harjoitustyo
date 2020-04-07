# Vaatimusmäärittely
## Sovelluksen tarkoitus
Sovelluksen avulla käyttäjä voi pitää kirjaa liikuntasuorituksistaan. Ensisijaisesti sovellus on suunnattu kestävyysharjoitteluun, jota tuetaan voimaharjoittelulla. Sovellus tarjoaa käyttäjälle myös mahdollisuuden tarkastella suorituksiaan ja saada tilastotietoa, jota voi hyödyntää harjoittelussaan.
## Käyttäjät
Aluksi sovelluksella on ainoastaan yksi käyttäjärooli eli normaali käyttäjä. Myöhemmin sovellukseen saatetaan lisätä rooli valmentaja.
## Perusversion tarjoama toiminnallisuus
### Ennen kirjautumista
* käyttäjä voi luoda järjestelmään käyttäjätunnuksen
  * käyttäjätunnuksen tulee olla uniikki ja pituudeltaan vähintään 3 merkkiä
  * lisäksi on asetettava salasana
  * käyttäjätunnuksen luonnin yhteydessä luodaan käyttäjäprofiili ja asetetaan tarpeelliset tiedot (ainakin nimi ja maksimisyke) TEHTY
* käyttäjä voi kirjautua järjestelmään
  * kirjautuminen onnistuu syöttämällä oma käyttäjätunnus ja salasana
  * käyttäjän tietojen olemassaolo tarkistetaan ja käyttäjätiedot haetaan tietokannasta TEHTY
  * jos käyttäjää ei ole olemassa, järjestelmä ilmoittaa tästä
### Kirjautumisen jälkeen
* käyttäjä voi halutessaan muokata käyttäjäprofiiliaan
* käyttäjä voi lisätä uuden liikuntasuorituksen
* käyttäjä näkee perusnäkymässä kuluvan viikon harjoittelun
* käyttäjä voi myös tarkastella aiempia suorituksiaan
* käyttäjä voi tarkastella yhteenvetoa harjoittelustaan valitsemallaan ajanjaksolla
  * yhteenveto tarjoaa tietoa harjoittelumääristä, harjoittelun jakautumisesta eri syketasoille sekä määrien ja vauhdin kehittymisestä
* käyttäjä voi kirjautua ulos järjestelmästä TEHTY
## Jatkokehitysideoita
Ajan salliessa perusversiota voidaan täydentää esim. seuraavilla ominaisuuksilla:
* valmentajanäkymä, jossa valmentaja näkee valmennettavansa harjoittelun ja voi lisätä harjoituksia valmennettavalle
* yksityiskohtaisempi seuranta myös voimaharjoittelulle
* toiminto, jolla ohjelma ehdottaa seuraavaa harjoitusta 
