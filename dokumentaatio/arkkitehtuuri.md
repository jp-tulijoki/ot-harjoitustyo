# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenne noudattaa kolmitasoista kerrosarkkitehtuuria seuraavalla pakkausrakenteella: workoutjournal.ui -> workoutjournal.domain -> workoutjournal.dao.

Pakkauksista workoutjournal.ui sisältää JavaFX:llä toteutetun graafisen käyttöliittymän, workoutjournal.domain sovelluslogiikan sekä workoutjournal.dao tietojen pysyväistalletukseen tarvittavan koodin. 

### Pakkauskaavio

![Pakkauskaavio](https://github.com/jp-tulijoki/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/package%20diagram.jpg) 

## Käyttöliittymä

Käyttöliittymä sisältää kolme Scene-olioilla toteutettua näkymää, joista yksi on aina kerrallaan kiinnitettynä sovelluksen stageen:
- kirjautuminen
- uuden käyttäjän luominen
- päänäkymä

Päänäkymä on ohjelmallisesti sama Scene-olio valitusta toiminnosta riippumatta, mutta sen ulkoasua muutetaan asetteluita vaihtamalla MenuBar-olion toimintojen mukaan.

Käyttöliittymä on ohjelmallisesti toteutettu luokassa WorkoutJournalUI. Käyttöliittymä on pyritty eristämään sovelluslogiikasta ja kutsuu sovelluslogiikan metodeja JournalTools-olion kautta. Käyttöliittymä ei ohjelman suorituksen aikana kutsu suoraan DAO:jen metodeita, vaan yhteys DAO:on tapahtuu sovelluslogiikan kautta. Käyttöliittymällä on muutamia omia metodeita, joita käytetään ainoastaan käyttöliittymäkomponenttien rakentamiseen.

Ohjelma käynnistetään erillisestä main-metodista, jonka ainoa toiminto on käyttöliittymän käynnistäminen. Käyttöliittymän init-metodissa alustetaan käytetty tietokantayhteys.

## Sovelluslogiikka

Luokat User ja Exercise muodostavat sovelluksen loogisen datamallin, ja ne kuvaavat käyttäjiä sekä käyttäjien suorittamia liikuntasuorituksia. Lisäksi workoutjournal.domain-pakkauksessa on enum-luokka IntensityLevel, jota käytetään harjoitusten kuormittavuuden määrittelyyn.

Toiminnallisuuksista vastaa luokka JournalTools, joka tarjoaa metodit käyttöliittymän toiminnoille. Näitä ovat esimerkiksi:
- int countMaxHeartRate(int age, String sex)
- String hashPassword(String password)
- boolean login(String username)
- IntensityLevel countIntensityLevel(Exercise exercise)

JournalTools saa tiedot tallennetuista käyttäjistä ja harjoituksista rajapinnat UserDAO ja ExerciseDAO toteuttavien luokkien kautta. Luokkien toteutukset annetaan riippuvuutena JournalTools-oliolle, kun sovellus käynnistetään ja JournalTools-luokan konstruktoria kutsutaan.

## Tietojen pysyväistalletus

Pakkauksen workoutjournal.dao luokat DBUserDAO ja DBExerciseDAO vastaavat tietojen tallentamisesta tietokantaan. Tietokanta on tällä hetkellä toteutettu sqliten avulla. Sovelluslogiikka käyttää luokkia rajapintojen UserDAO ja ExerciseDAO kautta, joten tietojen tallennustapaa on myös mahdollista muuttaa luomalla uudet luokat, jotka toteuttavat kyseiset rajapinnat.

Jotta tietojen eheys ei vaarantuisi, testeissä käytetään erillistä tietokantaa.

## Päätoiminnallisuudet

### Käyttäjän luominen

Sekvenssikaavio kuvaa tapauksen, jossa käyttäjä käyttää käyttäjän luontilomakkeella olevaa laskuria maksimisykkeen laskemiseen, jolloin tapahtumankäsittelijä kutsuu sovelluslogiikan metodia countMaxHeartRate antaen parametreiksi iän ja sukupuolen. Kun lomakkeelle on saatu paluuarvona oletusmaksimisyke, käyttäjä syöttää lomakkeelle käyttäjänimen, jota ei ole käytössä sekä salasanan. Kun käyttäjä on painanut käyttäjänluontinappia, kutsuu tapahtumankäsittelijä sovelluslogiikan metodia createUser parametreilla käyttäjänimi, salasana ja maksimisyke. Sovelluslogiikka selvittää UserDAOn avulla, onko kyseisellä käyttäjänimellä jo käyttäjää tietokannassa. Kun ei ole, DAO palauttaa "null", ja sovelluslogiikka kutsuu DAO:a uudelleen ja tallettaa käyttäjän tietokantaan. Käyttöliittymälle sovelluslogiikka palauttaa true, jonka jälkeen käyttöliittymä asettaa näkymäksi kirjautumisnäkymän.

![Sekvenssikaavio_käyttäjän_luominen](https://github.com/jp-tulijoki/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/sequenceDiagram_createUser.png)
