# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenne noudattaa kolmitasoista kerrosarkkitehtuuria seuraavalla pakkausrakenteella: workoutjournal.ui -> workoutjournal.domain -> workoutjournal.dao.

Pakkauksista workoutjournal.ui sisältää JavaFX:llä toteutetun graafisen käyttöliittymän, workoutjournal.domain sovelluslogiikan sekä workoutjournal.dao tietojen pysyväistalletukseen tarvittavan koodin. 

### Pakkauskaavio

![Pakkauskaavio](https://github.com/jp-tulijoki/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/packageDiagram.jpg) 

## Käyttöliittymä

Käyttöliittymä sisältää kolme Scene-olioilla toteutettua näkymää, joista yksi on aina kerrallaan kiinnitettynä sovelluksen stageen:
- kirjautuminen
- uuden käyttäjän luominen
- päänäkymä

Päänäkymä on ohjelmallisesti sama Scene-olio valitusta toiminnosta riippumatta, mutta sen ulkoasua muutetaan asetteluita vaihtamalla MenuBar-olion toimintojen mukaan.

Käyttöliittymän pääasiallinen rakenne on toteutettu luokassa WorkoutJournalUI. Käyttöliittymän aputoiminnoista vastaa luokka UITools, jonne on eriytetty pääasiassa käyttöliittymän näkyminen rakentamiseen liittyviä metodeita, joiden pitäminen WorkoutJournalUI:n koodissa tekisi siitä raskaan ja toisteisen. Perusperiaate on, että WorkoutJournalUI kutsuu UIToolsin metodeita ja UITools palauttaa halutun näkymän.

Käyttöliittymä on pyritty eristämään sovelluslogiikasta ja kutsuu sovelluslogiikan metodeja JournalTools-olion kautta. Molemmat luokat WorkoutJournalUI ja UITools kutsuvat tarvittavia sovelluslogiikan metodeita. Käyttöliittymä ei ohjelman suorituksen aikana kutsu suoraan DAO:jen metodeita, vaan yhteys DAO:on tapahtuu sovelluslogiikan kautta. 

Ohjelma käynnistetään erillisestä main-metodista, jonka ainoa toiminto on käyttöliittymän käynnistäminen. Käyttöliittymän init-metodissa alustetaan käytetty tietokantayhteys.

## Sovelluslogiikka

Luokat User ja Exercise muodostavat sovelluksen loogisen datamallin, ja ne kuvaavat käyttäjiä sekä käyttäjien suorittamia liikuntasuorituksia. Lisäksi workoutjournal.domain-pakkauksessa on enum-luokka IntensityLevel, jota käytetään harjoitusten kuormittavuuden määrittelyyn sekä enum-luokka Type, jota käytetään harjoituksen tyypin määrittämiseen.

Toiminnallisuuksista vastaa luokka JournalTools, joka tarjoaa metodit käyttöliittymän toiminnoille. Näitä ovat esimerkiksi:
- int countMaxHeartRate(int age, String sex)
- String hashPassword(String password)
- boolean login(String username)
- IntensityLevel countIntensityLevel(Exercise exercise)
- double[][] countMonthlyStats(LocalDate date)

JournalTools saa tiedot tallennetuista käyttäjistä ja harjoituksista rajapinnat UserDAO ja ExerciseDAO toteuttavien luokkien kautta. Luokkien toteutukset annetaan riippuvuutena JournalTools-oliolle, kun sovellus käynnistetään ja JournalTools-luokan konstruktoria kutsutaan.

## Tietojen pysyväistalletus

Pakkauksen workoutjournal.dao luokat DBUserDAO ja DBExerciseDAO vastaavat tietojen tallentamisesta tietokantaan. Tietokanta on tällä hetkellä toteutettu sqliten avulla. Sovelluslogiikka käyttää luokkia rajapintojen UserDAO ja ExerciseDAO kautta, joten tietojen tallennustapaa on myös mahdollista muuttaa luomalla uudet luokat, jotka toteuttavat kyseiset rajapinnat.

Jotta tietojen eheys ei vaarantuisi, testeissä käytetään erillistä tietokantaa.

## Päätoiminnallisuudet

### Käyttäjän luominen

Sekvenssikaavio kuvaa tapauksen, jossa käyttäjä käyttää käyttäjän luontilomakkeella olevaa laskuria maksimisykkeen laskemiseen, jolloin tapahtumankäsittelijä kutsuu sovelluslogiikan metodia countMaxHeartRate antaen parametreiksi iän ja sukupuolen. Kun lomakkeelle on saatu paluuarvona oletusmaksimisyke, käyttäjä syöttää lomakkeelle käyttäjänimen, jota ei ole käytössä sekä salasanan. Kun käyttäjä on painanut käyttäjänluontinappia, kutsuu tapahtumankäsittelijä sovelluslogiikan metodia createUser parametreilla käyttäjänimi, salasana ja maksimisyke. Sovelluslogiikka selvittää UserDAOn avulla, onko kyseisellä käyttäjänimellä jo käyttäjää tietokannassa. Kun ei ole, DAO palauttaa "null". Tämän jälkeen kutsuu omaa metodiaan hashPassword, joka muuttaa salasanan ei-selkokieliseksi. Sittensovelluslogiikka kutsuu DAO:a uudelleen ja tallettaa käyttäjän tietokantaan. Käyttöliittymälle sovelluslogiikka palauttaa true, jonka jälkeen käyttöliittymä asettaa näkymäksi kirjautumisnäkymän.

![Sekvenssikaavio_käyttäjän_luominen](https://github.com/jp-tulijoki/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/createUser.png)

### Käyttäjän kirjautuminen

Käyttäjän kirjautuminen noudattaa samantyyppistä logiikkaa kuin käyttäjän luominenkin. Käyttäjän annettua käyttäjänimen ja salasana, sovelluslogiikka selvittää löytyykö kyseisesllä käyttäjänimellä käyttäjää tietokannasta. Jos löytyy, tarkistetaan vielä salasanan oikeellisuus. Jos kaikki on kunnossa, palauttaa sovelluslogiikka käyttöliittymälle true, ja käyttöliittymä asettaa näkymäksi päänäkymän.

### Harjoituksen lisääminen

Sekvenssikaavio lähtee tilanteesta, jossa käyttäjä on syöttänyt tiedot lomakkeelle, ja painaa harjoituksen lisäysnappia. Tällöin tapahtumankäsittelijä kutsuu sovelluslogiikan metodia addExercise parametreina harjoituksen tiedot. (Periaatteessa on mahdollista tallentaa välimatkoja myös voimaharjoittelulle, mutta näitä tietoja ei käytetä mihinkään.) Sovelluslogiikka kutsuu edelleen DAO:n vastaavaa metodia addExercise sillä erolla, että harjoitustyypin parametrina on enum-arvon sijaan sitä vastaava kokonaisluku. Olettaen, että mitään poikkeavaa ei tapahdu, sovelluslogiikka palauttaa käyttöliittymälle true, ja käyttäjä saa vahvistusviestin onnistuneesta tallennuksesta.

![Sekvenssikaavio_harjoituksen_lisääminen](https://github.com/jp-tulijoki/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/addExercise.png)

### Tilastonäkymät

Tilastonäkymien kutsumisessa peruslogiikka on näkymästä riippumatta sama. WorkoutJournalUI:n tapahtumankäsittelijä kutsuu UIToolsin metodeita. Parametreina käytetään LocalDate-tyyppisiä muuttujia, joilla kohdennetaan tilastojen ajoittaminen oikeaan ajankohtaan, ja joiden arvoa muokataan käyttäjän toiminnan (viikkojen ja kuukausien selaamisen tai päivämäärähaun) seurauksena.

UITools kutsuu sovelluslogiikan metodeita, jotka puolestaan hakevat DAO:n metodeita kutsuen oikeat harjoitukset tietokannasta, ja tarjoavat oikeat tilastotiedot UIToolsille. Sovelluslogiikalta saamien tietojen avulla UITools rakentaa näkymän ja palauttaa sen WorkoutJournalUI:lle, joka asettaa näkymän käyttäjälle näytettävään näkymään.

## Tämänhetkisen version heikkoudet

### Käyttöliittymä

Vaikka raskaimmat näkymien rakentamiseen liittyvät metodit on eriytetty UITools-luokalle, sisältää WorkoutJournal jonkin verran toisteista koodia. Lisäksi poikkeuksista johtuvien virheilmoitusten syy on lähes poikkeuksetta tietokantayhteyden häviäminen (jota onneksi ei tapahdu kovin usein) tai "jokin odottamaton tapahtuma", mikä ei anna käyttäjälle kovin tarkkaa tietoa. (Käyttäjän toiminnasta johtuvat virheilmoitukset on pyritty määrittämään tarkemmin.)

Viikkonäkymässä näytetään vain kolme harjoitusta per päivä, mikä kaytännössä lienee riittävää. Ohjelman toiminnan kannalta tämä tarkoittaa sitä, että jos päivässä on useampia harjoituksia, osaa ei näytetä.

### Sovelluslogiikka

Sovelluslogiikan tilastotyökalut ovat yksinkertaisia, mutta sinänsä riittäviä. Sen sijaan analytiikkaosuutta voisi kehittää.

### DAO

ExerciseDAO:oon ei ole toteutettu harjoituksen muokkausominaisuutta. (Toki harjoituksen voi poistaa, ja lisätä uudelleen, mikä ei aiheuta suurta lisävaivaa.)
