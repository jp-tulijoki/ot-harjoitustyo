# Käyttöohje

Lataa uusimman releasen .jar-tiedosto. 

## Konfiguraatiot

Ohjelma ei vaadi erityisiä konfiguraatioita, mutta jos aiempien versioiden käytön
jäljiltä ohjelman kansiossa on testitietokantatiedosto, se kannattaa poistaa, ettei se häiritse, jos haluat ajaa testejä.

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla `java -jar {.jar-tiedoston nimi}`.

## Kirjautuminen

Sovellus avautuu kirjautumisnäkymään. Voit kirjautua syöttämällä käyttäjänimen ja salasanan sekä painamalla `Log in` nappia. 
Mikäli haluat luoda käyttäjätunnuksen, paina `Create new user` nappia.

## Käyttäjätunnuksen luominen

Voit luoda käyttäjän antamalla lomakkeella pyydetyt tiedot ja painamalla `Create new user` nappia. Maksimisykkeen voit
kirjoittaa itse, jos tiedät tai laskea laskurilla. Jos tunnuksen luominen onnistuu palataan kirjautumisnäkymään. Jos ei, voit
kokeilla luoda tunnuksen uudella käyttäjänimellä.

## Päänäkymän toiminnallisuudet

Päänäkymän toiminnallisuudesta vaihdetaan toiseen yläreunan valikosta. Osiossa `Settings` on käyttäjään liittyvät
toiminnallisuudet ja osiossa `Exercises` on harjoituksiin liittyvät toiminnallisuudet.

### Viikkonäkymä

Kirjautumisen jälkeen näytetään viikkonäkymä, johon pääsee myös valikosta: `Exercises > Weekly Summary` Viikkonäkymässä näet
harjoittelustasi kyseisen viikon harjoitusten suorituspäivän, suoritusajan ja intensiteettitason. Viikkoa voi vaihtaa
painikkeista.

### Harjoituksen lisääminen

Harjoituksen lisäämiseen pääsee valikosta: `Exercises > Add exercise`. Harjoitus lisätään antamalla lomakkeelle harjoituksen 
tiedot. Voimaharjoituksen kilometreihin ja sykkeisiin liittyviin tietoihin ei ole väliä, mitä syötät, mutta loogisinta on jättää ne tyhjäksi. Viikkonäkymän graafi tukee max. kolmea harjoitusta per päivä (tämä yleensä riittää kovemmillekin treenaajille).

### Kuukausinäkymä

Kuukausinäkymään pääsee valikosta: `Exercises > Monthly Summary` Kuukausinäkymässä näkyy harjoitusmäärät ja niiden kasvu
kuukausitasolla (ja pian muutakin).

### Harjoitusten selaaminen ja hakeminen

Näkymään pääsee valikosta: `Exercises > Previous exercises`. Oletuksena näytetään kuluvan kuukauden harjoitukset. Valitsemalla päivät kalenterinäkymiin ja painamalla `Sort exercises by date` voit hakea harjoituksia alku- ja loppupäivämäärällä. Voit myös poistaa harjoituksen painamalla harjoituksen kohdalla hiiren nappia, jolloin valittu harjoitus näkyy sinisenä ja painamalla nappia `Delete exercise`.

### Käyttäjäprofiilin muokkaaminen

Valitsemalla valikosta `Settings > Update max heart rate` voit asettaa maksimisykkeen uudelleen.

Valitsemalla `Settings > Change password` voit vaihtaa salasanan.

Valitsemalla `Settings > Delete user account` ja vahvistamalla poiston, voit poistaa käyttäjätunnuksesi. Huomioithan, että tätä ei voi perua. 

### Uloskirjautuminen

Ulos voi kirjautua valikosta `Settings > Log out` Uloskirjautumisen jälkeen palataan kirjautumisnäkymään.
