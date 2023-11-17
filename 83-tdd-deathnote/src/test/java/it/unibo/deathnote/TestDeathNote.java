package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.lang.Thread;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {
    private DeathNote dn;
    private final int ZERO_RULE_NUMBER = 0;
    private final String HUMAN_NAME = "Marco";
    private final String WRONG_HUMAN_NAME = "Giorgio";
    private final String SAMPLE_DEATH_CAUSE = "Karting Accident";
    private final String DEFAULT_CAUSE = "Heart attack";
    private final String NEW_DEATH_CAUSE = "Cacca addosso";
    private final String SAMPLE_DEATH_DETAILS = "Ran for too long";

    @BeforeEach
    public void setUp() {
        dn = new DeathNoteImpl();
    }

    @Test
    public void testGetRuleWithZero () {
        try {
            dn.getRule(ZERO_RULE_NUMBER);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "The rule number passed does not exist");
        }
    }

    @Test
    public void testGetRuleWithBigNumber(){
        try {
            dn.getRule(DeathNote.RULES.size() + 1);
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
    public void testWritingCauseWithoutName(){
        try{
            dn.writeDeathCause(null);
        } catch(IllegalStateException e){
            assertEquals(e.getMessage(), "Either the name passed isn't on the list or the cause is null");
        }
    }
    @Test
    public void testWritingCause(){
        dn.writeName(HUMAN_NAME);
        assertEquals(dn.getDeathCause(HUMAN_NAME), DEFAULT_CAUSE);
        dn.writeName(WRONG_HUMAN_NAME);
        assertEquals(dn.writeDeathCause(SAMPLE_DEATH_CAUSE), true);
        assertEquals(dn.getDeathCause(WRONG_HUMAN_NAME), SAMPLE_DEATH_CAUSE);
        try {
            Thread.sleep(100l);
        } catch(InterruptedException e){
            Assertions.fail("Interrupted exception has been thrown: " + e.getCause());
        }
        dn.writeDeathCause(NEW_DEATH_CAUSE);
        assertEquals(dn.getDeathCause(WRONG_HUMAN_NAME), SAMPLE_DEATH_CAUSE);
    }

    @Test
    public void testWritingDetailsWithoutName(){
        try{
            dn.writeDetails(null);
        } catch(IllegalStateException e){
            assertEquals(e.getMessage(), "Either the name passed isn't on the list or the cause is null");
        }
    }

    @Test
    public void testWritingDetails(){
        dn.writeName(HUMAN_NAME);
        assertEquals(dn.getDeathDetails(HUMAN_NAME), "");
        assertEquals(dn.writeDetails(SAMPLE_DEATH_DETAILS), true);
        assertEquals(dn.getDeathDetails(HUMAN_NAME), SAMPLE_DEATH_CAUSE);
        dn.writeName(WRONG_HUMAN_NAME);
        try {
            Thread.sleep(6100l);
        } catch(InterruptedException e){
            Assertions.fail("Interrupted exception has been thrown: " + e.getCause());
        }
        dn.writeDetails(SAMPLE_DEATH_DETAILS);
        assertEquals(dn.getDeathDetails(WRONG_HUMAN_NAME), "");
    }
}