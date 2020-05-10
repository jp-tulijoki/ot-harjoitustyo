# Vaatimusmäärittely
## Sovelluksen tarkoitus
Sovelluksen avulla käyttäjä voi pitää kirjaa liikuntasuorituksistaan. Ensisijaisesti sovellus on suunnattu kestävyysharjoitteluun, jota tuetaan voimaharjoittelulla. Sovellus tarjoaa käyttäjälle myös mahdollisuuden tarkastella suorituksiaan ja saada tilastotietoa, jota voi hyödyntää harjoittelussaan.
## Käyttäjät
Tällä hetkellä sovelluksella on ainoastaan yksi käyttäjärooli eli normaali käyttäjä. Myöhemmin sovellukseen saatetaan lisätä rooli valmentaja.
## Perusversion tarjoama toiminnallisuus
### Ennen kirjautumista
* käyttäjä voi luoda järjestelmään käyttäjätunnuksen TEHTY
  * käyttäjätunnuksen tulee olla uniikki ja pituudeltaan vähintään 3 merkkiä ja enintään 20 merkkiä TEHTY
  * lisäksi on asetettava salasana, jonka pituus on vähintään 3 merkkiä ja enintään 20 merkkiä TEHTY
  * käyttäjätunnuksen luonnin yhteydessä asetetaan maksimisyke, jonka voi asettaa suoraan tai laskea laskurilla. Sykkeen tulee olla enemmän kuin 0 ja enintään 220. TEHTY
* käyttäjä voi kirjautua järjestelmään TEHTY
  * kirjautuminen onnistuu syöttämällä oma käyttäjätunnus ja salasana TEHTY
  * käyttäjän tietojen olemassaolo tarkistetaan ja käyttäjätiedot haetaan tietokannasta TEHTY
  * jos kirjautumistiedot ovat puutteelliset, järjestelmä ilmoittaa tästä TEHTY
### Kirjautumisen jälkeen
* käyttäjä voi halutessaan muokata käyttäjäprofiiliaan TEHTY
  * käyttäjä voi päivittää maksimisykkeen TEHTY
  * käyttäjä voi vaihtaa salasanan TEHTY
* käyttäjä voi halutessaan poistaa käyttäjäprofiilinsa TEHTY
* käyttäjä voi lisätä uuden liikuntasuorituksen TEHTY
* käyttäjä voi poistaa liikuntasuorituksen TEHTY
* käyttäjä näkee perusnäkymässä kuluvan viikon harjoittelun TEHTY
* käyttäjä voi myös tarkastella aiempia suorituksiaan TEHTY
* käyttäjä voi tarkastella yhteenvetoa harjoittelustaan kuukausitasolla TEHTY
  * yhteenveto tarjoaa tietoa harjoittelumääristä, harjoittelun jakautumisesta eri syketasoille sekä määrien ja vauhdin kehittymisestä TEHTY
  * yhteenveto tarjoaa yksinkertaisen analyysin harjoittelutasojen sopivuudesta kestävyysharjoitteluun TEHTY
* käyttäjä voi kirjautua ulos järjestelmästä TEHTY
## Jatkokehitysideoita
Myöhemmin perusversiota voidaan täydentää esim. seuraavilla ominaisuuksilla:
* valmentajanäkymä, jossa valmentaja näkee valmennettavansa harjoittelun ja voi lisätä harjoituksia valmennettavalle
* yksityiskohtaisempi seuranta myös voimaharjoittelulle
* yksityiskohtaisempi analyysi
* toiminto, jolla ohjelma ehdottaa seuraavaa harjoitusta 
