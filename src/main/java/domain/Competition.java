package domain;

public class Competition {

    public int id;
    public Area area;
    public String name;
    Match match;


    public class Area{
        public int id;
        public String name;
    }
    public static class Match{
        public Match(int id, String utcDate, String status, Score score, Team homeTeam, Team awayTeam) {
            this.id = id;
            this.utcDate = utcDate;
            this.status = status;
            this.score = score;
            this.homeTeam = homeTeam;
            this.awayTeam = awayTeam;
        }

        public int id;
        public Season season;
        public String utcDate;
        public String status;
        public Score score;
        public Team homeTeam;
        public Team awayTeam;


    }

    public class Season{

        public int id;
        public String startDate;
        public String endDate;
        public int curentMatchday;
    }

    public static class Score{
        public Score(String winner) {
            this.winner = winner;
        }

        public String winner;
        public String duration;
        public Object fullTime;
        public Object halfTime;
        public Object extraTime;
        public Object penalties;
    }

    public static class Team{
        public Team(String name) {
            this.name = name;
        }

        public int id;
        public String name;
    }
}
