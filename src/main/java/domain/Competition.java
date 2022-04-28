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
    public class Match{
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

    public class Score{
        public String duration;
        public Object fullTime;
        public Object halfTime;
        public Object extraTime;
        public Object penalties;
    }

    public class Team{
        public int id;
        public String name;
    }
}
