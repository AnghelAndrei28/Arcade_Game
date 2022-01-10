import java.util.List;
import java.util.TreeSet;

public class Account {

    Information information;
    List<Character> characters;
    long playedGames;

    public Account(Information information, List<Character> characters, long playedGames) {
        this.information = information;
        this.characters = characters;
        this.playedGames = playedGames;
    }

    static class Information {
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

        static class InformationBuilder {
            private final Credentials credentials;
            private final TreeSet<String> favGames = new TreeSet<String>();
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
                if(validateInformation(information)) {
//                    return information;
                }
                return information;
            }

            private boolean validateInformation(Information information) {
                return true;
            }
        }

        @Override
        public String toString() {
            return "Information{" +
                    "credentials=" + credentials +
                    ", favGames=" + favGames +
                    ", name='" + name + '\'' +
                    ", country='" + country + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "information=" + information +
                ", characters=" + characters +
                ", playedGames=" + playedGames +
                '}';
    }
}
