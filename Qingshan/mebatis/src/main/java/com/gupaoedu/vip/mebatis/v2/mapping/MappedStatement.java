package com.gupaoedu.vip.mebatis.v2.mapping;

import com.gupaoedu.vip.mebatis.v2.session.Configuration;
import com.mysql.jdbc.log.LogFactory;

import javax.crypto.KeyGenerator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/7 11:00
 * @description：
 * @modified By：
 * @version: $
 */
public final class MappedStatement {

    private String id;

    private SqlCommandType sqlCommandType;

    private String sql;

    private Class type;

    private Configuration configuration;

    private Map<Integer,String> keyProperties;

    public MappedStatement() {

    }

    public static class Builder {
        private MappedStatement mappedStatement = new MappedStatement();

        public Builder( String id, String sql, SqlCommandType sqlCommandType,Class type) {
            //mappedStatement.configuration = configuration;
            mappedStatement.id = id;
            mappedStatement.sql = getSql(sql);
//            mappedStatement.statementType = StatementType.PREPARED;
//            mappedStatement.resultSetType = ResultSetType.DEFAULT;
//            mappedStatement.parameterMap = new ParameterMap.Builder(configuration, "defaultParameterMap", null, new ArrayList<>()).build();
//            mappedStatement.resultMaps = new ArrayList<>();
            mappedStatement.sqlCommandType = sqlCommandType;
//            mappedStatement.keyGenerator = configuration.isUseGeneratedKeys() && SqlCommandType.INSERT.equals(sqlCommandType) ? Jdbc3KeyGenerator.INSTANCE : NoKeyGenerator.INSTANCE;
//            String logId = id;
//            if (configuration.getLogPrefix() != null) {
//                logId = configuration.getLogPrefix() + id;
//            }
//            mappedStatement.statementLog = LogFactory.getLog(logId);
//            mappedStatement.lang = configuration.getDefaultScriptingLanguageInstance();
            mappedStatement.type= type;

        }

        private String getSql(String sql) {
            Pattern pattern = Pattern.compile("#\\{[^\\}]+\\}",// ^ 匹配输入字符串的开始位置，除非在方括号表达式中使用，此时它表示不接受该字符集合。也就是说，在{}之间，不能存在}
                    Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(sql);
            int index =1 ;
            while (matcher.find()){
                if (mappedStatement.keyProperties==null){
                    mappedStatement.keyProperties = new HashMap<>();
                }
                String paramName = matcher.group();
                sql = sql.replace(paramName,"?");
                paramName=paramName.replaceAll("#\\{|\\}","");
//                sql = sql.replaceAll("#\\{"+paramName+"\\}","?");
                paramName=paramName.split(",")[0];
                mappedStatement.keyProperties.put(index,paramName);
                index++;
                System.out.println(paramName);
            }
            return sql;
        }

//        public Builder resource(String resource) {
//            mappedStatement.resource = resource;
//            return this;
//        }

        public String id() {
            return mappedStatement.id;
        }

//        public Builder parameterMap(ParameterMap parameterMap) {
//            mappedStatement.parameterMap = parameterMap;
//            return this;
//        }
//
//        public Builder resultMaps(List<ResultMap> resultMaps) {
//            mappedStatement.resultMaps = resultMaps;
//            for (ResultMap resultMap : resultMaps) {
//                mappedStatement.hasNestedResultMaps = mappedStatement.hasNestedResultMaps || resultMap.hasNestedResultMaps();
//            }
//            return this;
//        }
//
//        public Builder fetchSize(Integer fetchSize) {
//            mappedStatement.fetchSize = fetchSize;
//            return this;
//        }
//
//        public Builder timeout(Integer timeout) {
//            mappedStatement.timeout = timeout;
//            return this;
//        }
//
//        public Builder statementType(StatementType statementType) {
//            mappedStatement.statementType = statementType;
//            return this;
//        }
//
//        public Builder resultSetType(ResultSetType resultSetType) {
//            mappedStatement.resultSetType = resultSetType == null ? ResultSetType.DEFAULT : resultSetType;
//            return this;
//        }
//
//        public Builder cache(Cache cache) {
//            mappedStatement.cache = cache;
//            return this;
//        }
//
//        public Builder flushCacheRequired(boolean flushCacheRequired) {
//            mappedStatement.flushCacheRequired = flushCacheRequired;
//            return this;
//        }
//
//        public Builder useCache(boolean useCache) {
//            mappedStatement.useCache = useCache;
//            return this;
//        }
//
//        public Builder resultOrdered(boolean resultOrdered) {
//            mappedStatement.resultOrdered = resultOrdered;
//            return this;
//        }
//
//        public Builder keyGenerator(KeyGenerator keyGenerator) {
//            mappedStatement.keyGenerator = keyGenerator;
//            return this;
//        }
//
//        public Builder keyProperty(String keyProperty) {
//            mappedStatement.keyProperties = delimitedStringToArray(keyProperty);
//            return this;
//        }
//
//        public Builder keyColumn(String keyColumn) {
//            mappedStatement.keyColumns = delimitedStringToArray(keyColumn);
//            return this;
//        }

//        public Builder databaseId(String databaseId) {
//            mappedStatement.databaseId = databaseId;
//            return this;
//        }
//
//        public Builder lang(LanguageDriver driver) {
//            mappedStatement.lang = driver;
//            return this;
//        }
//
//        public Builder resultSets(String resultSet) {
//            mappedStatement.resultSets = delimitedStringToArray(resultSet);
//            return this;
//        }
//
//        /**
//         * @deprecated Use {@link #resultSets}
//         */
//        @Deprecated
//        public Builder resulSets(String resultSet) {
//            mappedStatement.resultSets = delimitedStringToArray(resultSet);
//            return this;
//        }

        public MappedStatement build() {
            //assert mappedStatement.configuration != null;
            assert mappedStatement.id != null;
            assert mappedStatement.sql != null;
            //assert mappedStatement.lang != null;
            //mappedStatement.resultMaps = Collections.unmodifiableList(mappedStatement.resultMaps);
            return mappedStatement;
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(SqlCommandType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public Map<Integer, String> getKeyProperties() {
        return keyProperties;
    }

    public void setKeyProperties(Map<Integer, String> keyProperties) {
        this.keyProperties = keyProperties;
    }

    @Override
    public String toString() {
        return "MappedStatement{" +
                "id='" + id + '\'' +
                ", sqlCommandType=" + sqlCommandType +
                ", sql='" + sql + '\'' +
                ", type=" + type +
                '}';
    }
}
