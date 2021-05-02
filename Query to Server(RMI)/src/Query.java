import java.util.*;

import static java.util.stream.Collectors.joining;

public class Query {
    public static class Builder {
        private String tableName;
        private QueryType type;
        private Map<String, String> whereClauses = new HashMap<>();
        private Map<String, String> updateAttributes = new HashMap<>();
        private Set<String> selectAttributes = new HashSet<>();
        private Map<String, String> insertValues = new HashMap<>();

        Builder(String tableName, QueryType type) {
            this.tableName = tableName;
            this.type = type;
        }

        public Builder addSelectAttributes(Set<String> selectAttributes) {
            if (type.equals(QueryType.SELECT))
                this.selectAttributes = selectAttributes;
            return this;
        }

        public Builder addWhereClauses(Map<String, String> whereClauses) {
            this.whereClauses = whereClauses;
            return this;
        }

        public Builder addUpdateAttributes(Map<String, String> updateAttributes) {
            if (type.equals(QueryType.UPDATE))
                this.updateAttributes = updateAttributes;
            return this;
        }

        public Builder addInsertValues(Map<String, String> insertValues) {
            if (type.equals(QueryType.INSERT))
                this.insertValues = insertValues;
            return this;
        }

        public Query build() {
            String query = "";
            switch (this.type) {
                case SELECT:
                    query = "SELECT " + getSelectAttributesasString() + " FROM " + tableName + " " + getWhereClausesasString();
                    break;
                case UPDATE:
                    if (updateAttributes.isEmpty())
                        throw new RuntimeException("Update Attributes are empty");
                    query = "UPDATE " + tableName + " SET " + getUpdateAttributesasString() + getWhereClausesasString();
                    break;
                case DELETE:
                    query = "DELETE FROM " + tableName + getWhereClausesasString();
                    break;
                case INSERT:
                    if (insertValues.isEmpty())
                        throw new RuntimeException("Insert values not found");
                    query = "INSERT INTO " + tableName + " " + getInsertValuesasString();
                    break;
                default:
                    throw new RuntimeException("Invalid query type");
            }
            System.out.println("Query : " + query);
            return new Query(query + ";");
        }

        private String getSelectAttributesasString() {
            if (selectAttributes.isEmpty()) return "*";
            return String.join(", ", selectAttributes);
        }

        private String getWhereClausesasString() {
            if (whereClauses.isEmpty()) return "";
            return " WHERE " + whereClauses
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + "=" + getValue(e.getValue()))
                    .collect(joining(" AND "));
        }

        private String getUpdateAttributesasString() {
            return updateAttributes
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + "='" + e.getValue() + "'")
                    .collect(joining(", "));
        }

        private String getInsertValuesasString() {
            List<String> columns = new ArrayList<>(insertValues.keySet());
            return "( " + String.join(", ", columns) +
                    ") VALUES ( " +
                    columns.stream().map(e -> "'" + insertValues.get(e) + "'").collect(joining(", "))
                    + ")";
        }

        private String getValue(String s) {
            return s.toLowerCase().equals("null") ? "NULL" : "'" + s + "'";
        }

    }

    private String query;

    private Query(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

}
