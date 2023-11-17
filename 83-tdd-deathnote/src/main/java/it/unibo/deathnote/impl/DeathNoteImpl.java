package it.unibo.deathnote.impl;

import it.unibo.deathnote.api.DeathNote;
import java.util.HashMap;

public class DeathNoteImpl implements DeathNote {

    private HashMap<String, Death> dn;

    private String lastWrittenName; 
    private long timeOfLastWrittenName;

    public DeathNoteImpl(){
        dn = new HashMap<String, Death>();
        lastWrittenName = "";
    }
    /**
     * Returns the rule with the given number.
     *
     * @param ruleNumber the number of the rule to return. The first rule has number one
     * @return the rule with the given number
     * @throws IllegalArgumentException if the given rule number is smaller than 1 or larger
     * than the number of rules
     */
    public String getRule(int ruleNumber) {
        if(ruleNumber > 0 && ruleNumber <= RULES.size()){
            return RULES.get(ruleNumber);
        } else {
            throw new IllegalArgumentException("The rule number passed does not exist");
        }
    }

    /**
     * The human whose name is written in this DeathNote will die.
     * @param name the name of the human to kill
     * @throws NullPointerException if the given name is null.
     */
    public void writeName(String name){
        if(name != null && name != ""){
            Death d = new Death();
            dn.put(name, d);
            lastWrittenName = name;
            timeOfLastWrittenName = System.currentTimeMillis();
        }
        else {
            throw new NullPointerException("The name given is null");
        }
    }

    /**
     * If the cause of death is written within the next 40 milliseconds of writing the person's
     * name, it will happen.
     *
     * @param cause the cause of the human's death
     * @return true if the cause was written within 40 milliseconds, false otherwise
     * @throws IllegalStateException if there is no name written in this DeathNote,
     * or the cause is null
     */
    public boolean writeDeathCause(String cause){
        if(cause != null && cause != "" && !dn.isEmpty()){ /* check if cause is ok */
            throw new IllegalStateException();
        }
        long delta = System.currentTimeMillis() - timeOfLastWrittenName; /* compute time */
        if(delta <= 40l){
            Death oldDeath = dn.get(lastWrittenName);
            oldDeath.cause = cause;
            dn.put(lastWrittenName, oldDeath);
            return true;
        }
        return false;
    }

    /**
     * After writing the cause of death, details of the death should be written in the next
     * 6 seconds and 40 milliseconds.
     *
     * @param details the details of the human's death
     * @return true if the details were written within 6 seconds and 40 milliseconds, false otherwise
     * @throws IllegalStateException if there is no name written in this DeathNote,
     * or the details are null
     */
    public boolean writeDetails(String details){
        if(details != null && details != "" && !dn.isEmpty()){
            throw new IllegalStateException();
        }
        long delta = System.currentTimeMillis() - timeOfLastWrittenName;
        if(delta > 40 && delta <= 6000){
            Death oldDeath = dn.get(lastWrittenName);
            oldDeath.details = details;
            dn.put(lastWrittenName, oldDeath);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * @param name the name of the person whose death cause to return
     * @return the death cause of the person with the given name.
     * If the cause of death is not specified, the method will return "heart attack".
     * @throws IllegalArgumentException if the provider name is not written in this DeathNote
     */
    public String getDeathCause(String name){
        if(!dn.containsKey(name)){
            throw new IllegalArgumentException();
        }
        return (dn.get(name).cause == "" ? dn.get(name).cause : Death.DEFAULT_CAUSE);
    }

    /**
     * @param name the name of the person whose death cause to return
     * @return the death details of the person with the given name,
     * or an empty string if no details have been provided.
     * @throws IllegalArgumentException if the provider name is not written in this DeathNote.
     */
    public String getDeathDetails(String name){
        if(!dn.containsKey(name)){
            throw new IllegalArgumentException();
        }
        return dn.get(name).details;
    }

    /**
     * @param name the name of the person
     * @return true if the given name is written in this DeathNote, false otherwise
     */
    public boolean isNameWritten(String name){
        return dn.containsKey(name);
    }

    private class Death{
        static final String DEFAULT_CAUSE = "Heart attack";

        public String cause;
        public String details;

        public void Death(){
            cause = "";
            details = "";
        }
    }
}
