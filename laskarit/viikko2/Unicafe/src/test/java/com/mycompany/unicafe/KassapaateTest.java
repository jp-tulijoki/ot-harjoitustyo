package com.mycompany.unicafe;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tulijoki
 */
public class KassapaateTest {
    
    Kassapaate paate;
    
    @Before
    public void setUp() {
        paate = new Kassapaate();
    }
    
    @Test
    public void konstruktoriAsettaaMuuttujatOikein() {
        assertEquals(100000, paate.kassassaRahaa());
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullisenKateisostoToimii() {
        assertEquals(0, paate.syoEdullisesti(240));
        assertEquals(100240, paate.kassassaRahaa());
        assertEquals(1, paate.edullisiaLounaitaMyyty());
        assertEquals(200, paate.syoEdullisesti(200));
        assertEquals(100240, paate.kassassaRahaa());
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukkaanKateisostoToimii() {
        assertEquals(0, paate.syoMaukkaasti(400));
        assertEquals(100400, paate.kassassaRahaa());
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
        assertEquals(200, paate.syoMaukkaasti(200));
        assertEquals(100400, paate.kassassaRahaa());
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullisenKorttiostoToimii() {
        assertEquals(true, paate.syoEdullisesti(new Maksukortti(1000)));
        assertEquals(1, paate.edullisiaLounaitaMyyty());
        assertEquals(false, paate.syoEdullisesti(new Maksukortti(200)));
        assertEquals(1, paate.edullisiaLounaitaMyyty());
        assertEquals(100000, paate.kassassaRahaa());
    }
    @Test
    public void maukkaanKorttiostoToimii() {
        assertEquals(true, paate.syoMaukkaasti(new Maksukortti(1000)));
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
        assertEquals(false, paate.syoMaukkaasti(new Maksukortti(200)));
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void rahanLataaminenToimii() {
        Maksukortti kortti = new Maksukortti(1000);
        paate.lataaRahaaKortille(kortti, 100);
        assertEquals(1100, kortti.saldo());
        assertEquals(100100, paate.kassassaRahaa());
        paate.lataaRahaaKortille(kortti, -500);
        assertEquals(1100, kortti.saldo());
        assertEquals(100100, paate.kassassaRahaa());
    }
   
    
}
