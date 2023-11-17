package it.unibo.deathnote;

import org.junit.jupiter.api.BeforeEach;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {   
    final DeathNote dn;


    @BeforeEach
    public void setUp(){
        dn = new DeathNote();
    }

}