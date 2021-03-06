# Käyttöohje

Lataa sovelluksen .jar-tiedosto [täältä](https://github.com/jp-tulijoki/ot-harjoitustyo/releases/tag/1.0). 

## Konfiguraatiot

Ohjelma ei vaadi erityisiä konfiguraatioita, mutta jos aiempien versioiden käytön
jäljiltä ohjelman kansiossa on testitietokantatiedosto, se kannattaa poistaa, ettei se häiritse, jos haluat ajaa testejä.

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla `java -jar WorkoutJournal-v1.0.jar`.

## Kirjautuminen

Sovellus avautuu kirjautumisnäkymään. Voit kirjautua syöttämällä käyttäjänimen ja salasanan sekä painamalla `Log in` nappia. 
Mikäli haluat luoda käyttäjätunnuksen, paina `Create new user` nappia.

## Käyttäjätunnuksen luominen

Voit luoda käyttäjän antamalla lomakkeella pyydetyt tiedot ja painamalla `Create new user` nappia. Maksimisykkeen voit
kirjoittaa itse, jos tiedät tai laskea laskurilla. Käyttäjätunnuksen ja salasanan tulee olla vähintään 3 merkkiä ja enintään 20 merkkiä pitkiä, ja sykkeen enemmän kuin nolla ja korkeintaan 220. Jos tunnuksen luominen onnistuu palataan kirjautumisnäkymään. Jos ei, voit kokeilla luoda tunnuksen uudella käyttäjänimellä.

## Päänäkymän toiminnallisuudet

Päänäkymän toiminnallisuudesta vaihdetaan toiseen yläreunan valikosta. Osiossa `Settings` on käyttäjään liittyvät
toiminnallisuudet ja osiossa `Exercises` on harjoituksiin liittyvät toiminnallisuudet.

### Viikkonäkymä

Kirjautumisen jälkeen näytetään viikkonäkymä, johon pääsee myös valikosta: `Exercises > Weekly Summary` Viikkonäkymässä näet
harjoittelustasi kyseisen viikon harjoitusten suorituspäivän, suoritusajan ja intensiteettitason. Viikkoa voi vaihtaa
painikkeista.

### Harjoituksen lisääminen

Harjoituksen lisäämiseen pääsee valikosta: `Exercises > Add exercise`. Harjoitus lisätään antamalla lomakkeelle harjoituksen 
tiedot. Voimaharjoituksen kilometreihin ja sykkeisiin liittyviin tietoihin ei ole väliä, mitä syötät (näitä ei käytetä tilastojen laskemisessa), mutta loogisinta on jättää ne tyhjäksi. Viikkonäkymän graafi tukee max. kolmea harjoitusta per päivä (tämä yleensä riittää kovemmillekin treenaajille). Enemmänkin voi syöttää, mutta viikkonäkymä jättää tällöin osan päivän harjoituksista pois.

### Kuukausinäkymä

Kuukausinäkymään pääsee valikosta: `Exercises > Monthly Summary` Kuukausinäkymässä näkyy harjoitusten jakautuminen harjoitustyypin ja intensiteetin mukaan, harjoitusvauhdit intensiteetin mukaan, kokonaisjuoksukilometrit sekä niiden muutos suhteessa edelliseen kuukauteen sekä yksinkertainen analyysi harjoittelun jakautumisesta. Kuukausia voi selata painikkeista.

### Harjoitusten selaaminen ja hakeminen

Näkymään pääsee valikosta: `Exercises > Previous exercises`. Oletuksena näytetään kuluvan kuukauden harjoitukset. Valitsemalla päivät kalenterinäkymiin ja painamalla `Sort exercises by date` voit hakea harjoituksia alku- ja loppupäivämäärällä. Voit myös poistaa harjoituksen painamalla harjoituksen kohdalla hiiren nappia, jolloin valittu harjoitus näkyy sinisenä ja painamalla nappia `Delete exercise`.

### Käyttäjäprofiilin muokkaaminen

Valitsemalla valikosta `Settings > Update max heart rate` voit asettaa maksimisykkeen uudelleen. Kriteerit ovat samat kuin uutta käyttäjää luotaessa.

Valitsemalla `Settings > Change password` voit vaihtaa salasanan. Kriteerit ovat samat kuin uutta käyttäjää luotaessa.

Valitsemalla `Settings > Delete user account` ja vahvistamalla poiston, voit poistaa käyttäjätunnuksesi. Huomioithan, että tätä ei voi perua. 

### Uloskirjautuminen

Ulos voi kirjautua valikosta `Settings > Log out` Uloskirjautumisen jälkeen palataan kirjautumisnäkymään.
