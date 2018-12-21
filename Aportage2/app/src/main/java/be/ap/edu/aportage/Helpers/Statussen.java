package be.ap.edu.aportage.helpers;

public enum Statussen {

    BEHANDELING("behandeling"),
    ONTVANGEN("ontvangen"),
    AFGEHANDELD("afgehandeld");

    private final String text;

    /**
     * @param text
     */
    Statussen(final String text) {
        this.text = text;
    }

    public static Statussen getStatus(String status){
        if(status == BEHANDELING.toString())
            return BEHANDELING;
        else if(status == ONTVANGEN.toString())
            return ONTVANGEN;
        else if(status == AFGEHANDELD.toString())
            return AFGEHANDELD;
        else
            return null;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
