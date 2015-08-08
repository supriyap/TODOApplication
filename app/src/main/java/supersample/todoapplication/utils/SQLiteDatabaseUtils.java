package supersample.todoapplication.utils;

public class SQLiteDatabaseUtils {

    public static final String SPACE = " ";
    //Table name and columns
    public static final String TABLE_TODO = "todo";
    public static final String COL_TODO_ID = "_id";
    public static final String COL_TODO_ITEM = "item";


    public static String createToDoTableQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE");
        sb.append(SPACE);
        sb.append(TABLE_TODO);
        sb.append("(");
        sb.append(COL_TODO_ID);
        sb.append(SPACE);
        sb.append("INTEGER PRIMARY KEY,");
        sb.append(COL_TODO_ITEM);
        sb.append(SPACE);
        sb.append("TEXT");
        sb.append(")");
        return sb.toString();
    }

    public static String getInsertToDoItemQuery() {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO");
        builder.append(SPACE);
        builder.append(TABLE_TODO);
        builder.append("(");
        builder.append(COL_TODO_ITEM);
        builder.append(SPACE);
        builder.append(")");
        builder.append("VALUES");
        builder.append(SPACE);
        builder.append("(?)");

        return builder.toString();
    }

}
