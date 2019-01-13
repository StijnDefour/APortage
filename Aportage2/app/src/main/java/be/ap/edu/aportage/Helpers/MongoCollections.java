package be.ap.edu.aportage.helpers;

public enum MongoCollections {

        CAMPUSSEN("campussen"),
        VERDIEPEN("verdiepen"),
        LOKALEN("lokalen"),
        MELDINGEN("meldingen"),
        GEBRUIKERS("gebruikers");

        private final String text;

        /**
         * @param text
         */
        MongoCollections(final String text) {
                this.text = text;
        }

        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
                return text;
        }

}
