package be.ap.edu.aportage.Helpers;

public enum MongoCollections {

        CAMPUSSEN("campussen"),
        VERDIEPEN("verdiepen"),
        LOKALEN("lokalen"),
        MELDINGEN("meldingen")
        ;

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
