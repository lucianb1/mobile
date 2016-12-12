package ro.hoptrop.core.sql;

/**
 * Created by Luci on 13-Dec-16.
 */
public class SqlQueryBuilder {

    private final StringBuilder stringBuilder;

    public SqlQueryBuilder(String string) {
        stringBuilder = new StringBuilder(string);
    }

    public SqlQueryBuilder append(String text) {
        stringBuilder.append(text);
        return this;
    }

    public SqlQueryBuilder append(String prefix, String text) {
        stringBuilder.append(prefix + text);
        return this;
    }

    public SqlQueryBuilder conditionalAppend(boolean condition, String string) {
        if (condition) {
            stringBuilder.append(string);
        }
        return this;
    }

    public SqlQueryBuilder conditionalAppend(boolean condition, String prefix, String string) {
        if (condition) {
            stringBuilder.append(prefix + string);
        }
        return this;
    }


    public String toString() {
        return stringBuilder.toString();
    }

}
