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
88 %. Muutama getteri jää testien ulkopuolelle ja yhden JournalToolsin metodin kaikkia haaraumia ei käydä läpi.

## Järjestelmätason testit
