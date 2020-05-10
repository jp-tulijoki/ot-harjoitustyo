# Testausdokumentti

Ohjelman yksikkö- ja integraatiotestit on suoritettu JUnitilla ja järjestelmätason testit manuaalisesti.

## Yksikkö- ja integraatiotestit

### Sovelluslogiikka

JournalToolsTest sisältää kaikki sovelluksen automatisoidut testit ja se testaa nimensä mukaisesti sovelluslogiikan kannalta
keskeisen luokan JournalTools toiminnallisuuksia. Lähtökohtana on ollut, että jokaiselle keskeiselle metodille kirjoitetaan
testi. User ja Exercise -luokkia ja enumeita IntensityLevel ja Type ei ole testattu erikseen, mutta suurin osa näiden luokkien
sisällöistä tulee käytyä läpi testien yhteydessä.

Testit on pyritty rakentamaan realistisilla skenaarioilla: Esimerkiksi, jos metodi hakee laskentaa varten DAO:sta tietoja,
tiedot tallennetaan DAO:oon ja haetaan sieltä. Suoraviivaista syötteen käsittelyä, esimerkiksi sykkeen laskeminen tai salasanan
muunto ei-selkokieliseksi on testattu suoralla syötteellä ilman tietokantayhteyttä.

### DAO-luokat

DAO-luokkia ei ole testattu erikseen, koska kaikki yhteydet DAO:on tapahtuvat sovelluslogiikan kautta. Käytännössä DAO-metodien
toimivuus on ollut edellytyksenä monen sovelluslogiikan metodin toimivuudelle, joten nämä ovat tulleet testatuksi sovellus-
logiikan testien yhteydessä.

Testeissä on ollut käytössä erillinen testitietokanta. Testien kannalta kriittiset tiedot on poistettu aina testin 
jälkeen tietokannasta, jotta testien lopputulos ei riipu siitä, mitä tietokannassa on valmiiksi.

### Testien kattavuus

Käyttöliittymää ei ole testattu automaattisesti. Sovelluslogiikan ja DAO:n testien rivikattavuus on 98 % ja haaraumakattavuus
87 %. Muutama getteri jää testien ulkopuolelle ja yhden JournalToolsin metodin kaikkia rivejä ja haaraumia ei käydä läpi.

## Järjestelmätason testit

Järjestelmätason testit on suoritettu manuaalisesti.

### Asennus

Sovellus on ladattu [käyttöohjeen](https://github.com/jp-tulijoki/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md) mukaisesti ja sovellusta on testattu ladatusta -jar.tiedostosta.

### Toiminnallisuudet

[Vaatimusmäärittelyyn](https://github.com/jp-tulijoki/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md) sisältyvät toiminnallisuudet on käyty läpi. Testauksessa on pyritty löytämään järjestelmän haavoittuvuuksia antamalla virheellisiä syötteitä kaikkiin mahdollisiin kenttiin. Lisäksi on pyritty tekemään laskelmia, että sovelluksen piirtämät graafit ovat syötteiden mukaisia.

Ilmi tulleita bugeja on pyritty korjaamaan saman tien. Viimeisestä versiosta ei löytynyt mitään, mitä ei voitu korjata. 
