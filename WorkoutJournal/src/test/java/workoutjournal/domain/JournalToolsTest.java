package workoutjournal.domain;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import workoutjournal.DAO.UserDAO;

/**
 *
 * @author tulijoki
 */
public class JournalToolsTest {
    
    JournalTools tools;
    
    @Before
    public void setUp() {
        this.tools = new JournalTools(new UserDAO());
    }
    
    @Test
    public void countMaxHeartRateWorksProperly() {
        assertEquals(190, tools.countMaxHeartRate(30, Sex.male));
        assertEquals(174, tools.countMaxHeartRate(40, Sex.female));
    }
    
    
}
