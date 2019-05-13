package database.GameDbSchema;

public class GameDbSchema {

    public static final class LeaderboardTable {
        public static final String NAME = "leaderboard";                                // Refer as LeaderboardTable.NAME

        public static final class Columns {
            public static final String ID = "id";                                       // Refer as LeaderboardTable.Columns.ID
            public static final String MODE = "mode";
            public static final String SCORE = "score";
            public static final String DATE = "date";
            public static final String TOTALCORRECT = "totalcorrect";
            public static final String TOTALQUESTIONS = "totalquestions";
        }

    }


    /*

    public static final class QuestionTable {
        public static final String NAME = "question";                       // Refer as QuestionTable.NAME

        public static final class Columns {
            public static final String ID = "id";                   // Refer as QuestionTable.Columns.ID
            public static final String QUESTION = "question";
            public static final String ANSWERA = "answera";
            public static final String ANSWERB = "answerb";
            public static final String ANSWERC = "answerc";
            public static final String ANSWERD = "answerd";
            public static final String CORRECTANSWER = "correctanswer";
            public static final String ISCORRECT = "iscorrect";  // TDL: Delete this when you find out where else in code
            public static final String CORRECTONCE = "correctonce";
            public static final String CORRECTTWICE = "correcttwice";
        }
    }

     */





}
