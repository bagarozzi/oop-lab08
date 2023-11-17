package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {
    private DeathNote dn;
    private final int ZERO_RULE_NUMBER = 0;
    private final String humanName = "Marco";

    @BeforeEach
    public void setUp() {
        dn = new DeathNoteImpl();
    }

    public void testGetRuleWithZero () {
        try {
            dn.getRule(ZERO_RULE_NUMBER);
            Assertion.fail("Expected exception but none was thrown");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "The rule number passed does not exist");
        }
    }

    public void testGetRuleWithBigNumber(){
        try {
            dn.getRule(DeathNote.RULES.size() + 1);
            Assertion.fail("Expected exception but none was thrown");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "The rule number passed does not exist");
        }
    }

    public void checkNullRules(){
        for(String rule : DeathNote.RULES){
            assertNotEquals("", rule);
            assertNotEquals(null, rule);
        }
    }

    public void testWriteName(){
        assertEquals(dn.isNameWritten(humanName), false);
        try{
            dn.writeName(null);
        } catch(NullPointerException e){

        }
    }

}