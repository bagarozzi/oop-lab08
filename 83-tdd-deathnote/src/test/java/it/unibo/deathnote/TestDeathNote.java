package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {
    private DeathNote dn;
    private final int ZERO_RULE_NUMBER = 0;
    private final String HUMAN_NAME = "Marco";
    private final String WRONG_HUMAN_NAME = "Giorgio";

    @BeforeEach
    public void setUp() {
        dn = new DeathNoteImpl();
    }

    @Test
    public void testGetRuleWithZero () {
        try {
            dn.getRule(ZERO_RULE_NUMBER);
            Assertions.fail("Expected exception but none was thrown");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "The rule number passed does not exist");
        }
    }

    @Test
    public void testGetRuleWithBigNumber(){
        try {
            dn.getRule(DeathNote.RULES.size() + 1);
            Assertions.fail("Expected exception but none was thrown");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "The rule number passed does not exist");
        }
    }

    @Test
    public void checkNullRules(){
        for(String rule : DeathNote.RULES){
            assertNotEquals("", rule);
            assertNotEquals(null, rule);
        }
    }

    @Test
    public void testWritingNullName(){
        try{
            dn.writeName(null);
        } catch(NullPointerException e){
            assertEquals(e.getMessage(), "The name given is null");
            assertEquals(dn.isNameWritten(null), false);
            assertEquals(dn.isNameWritten(""), false);
        }
    }

    @Test
    public void testWritingName(){
        assertEquals(dn.isNameWritten(HUMAN_NAME), false);
        dn.writeName(HUMAN_NAME);
        assertEquals(dn.isNameWritten(WRONG_HUMAN_NAME), false);
        assertEquals(dn.isNameWritten(""), false);
    }

    @Test
    public testWritingCause(){

    }
}