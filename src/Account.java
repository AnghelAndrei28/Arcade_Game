import java.util.List;
import java.util.TreeSet;

public class Account {

    Information information;
    List<Character> characters;
    int playedGames;

    class Information {
        private final Credentials credentials;
        private final TreeSet<String> favGames;
        private final String name;
        private final String country;

        private Information(InformationBuilder builder) {
            this.credentials = builder.credentials;
            this.favGames = builder.favGames;
            this.name = builder.name;
            this.country = builder.country;
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public String getCountry() {
            return country;
        }

        public String getName() {
            return name;
        }

        public TreeSet<String> getFavGames() {
            return favGames;
        }

        class InformationBuilder {
            private final Credentials credentials;
            private TreeSet<String> favGames = new TreeSet<String>(new MyComparator());
            private final String name;
            private String country;

            InformationBuilder(Credentials credentials, String name) {
                this.credentials = credentials;
                this.name = name;
            }

            InformationBuilder country(String country) {
                this.country = country;
                return this;
            }

            InformationBuilder games(TreeSet<String> favGames) {
                this.favGames.addAll(favGames);
                return this;
            }

            Information build() {
                Information information = new Information(this);
                validateInformation(information);
                return information;
            }

            private void validateInformation(Information information) {
                System.out.println("Verificare");
            }
        }
    }
}
