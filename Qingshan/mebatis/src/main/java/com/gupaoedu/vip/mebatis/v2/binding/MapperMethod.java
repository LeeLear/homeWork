package com.gupaoedu.vip.mebatis.v2.binding;

import com.gupaoedu.vip.mebatis.v2.annotation.Flush;
import com.gupaoedu.vip.mebatis.v2.annotation.MapKey;
import com.gupaoedu.vip.mebatis.v2.mapping.MappedStatement;
import com.gupaoedu.vip.mebatis.v2.mapping.SqlCommandType;
import com.gupaoedu.vip.mebatis.v2.reflection.TypeParameterResolver;
import com.gupaoedu.vip.mebatis.v2.session.Configuration;
import com.gupaoedu.vip.mebatis.v2.session.SqlSession;
import sun.plugin2.main.server.ResultHandler;

import java.lang.reflect.*;
import java.util.*;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/7 11:18
 * @description：
 * @modified By：
 * @version: $
 */
public class MapperMethod {
    private final SqlCommand command;
    private final MethodSignature method;

    public MapperMethod(Class<?> mapperInterface, Method method, Configuration config) {
        this.command = new SqlCommand(config, mapperInterface, method);
        this.method = new MethodSignature(config, mapperInterface, method);
    }

    public Object execute(SqlSession sqlSession, Object[] args) {

            Object result = null;
            switch (command.getType()) {
                case INSERT: {
                    Object param = convertArgsToSqlCommandParam(args);
                    result = rowCountResult(sqlSession.insert(command.getName(), param));
                    break;
                }
                case UPDATE: {
                    Object param = convertArgsToSqlCommandParam(args);
                    result = rowCountResult(sqlSession.update(command.getName(), param));
                    break;
                }
                case DELETE: {
                    //Object param = method.convertArgsToSqlCommandParam(args);
                    //result = rowCountResult(sqlSession.delete(command.getName(), param));
                    break;
                }
                case SELECT:
                    if (method.returnsVoid() ) {
                       // executeWithResultHandler(sqlSession, args);
                        result = null;
                    } else if (method.returnsMany()) {
                        result = executeForMany(sqlSession, args);
//                    } else if (method.returnsMap()) {
//                        result = executeForMap(sqlSession, args);
//                    } else if (method.returnsCursor()) {
//                        result = executeForCursor(sqlSession, args);
                    } else {
                        Object param = convertArgsToSqlCommandParam(args);
                        result = sqlSession.selectOne(command.getName(), param);
//                        if (method.returnsOptional()
//                                && (result == null || !method.getReturnType().equals(result.getClass()))) {
//                            result = Optional.ofNullable(result);
//                        }
                    }
                    break;
                case FLUSH:
                    //result = sqlSession.flushStatements();
                    break;
                default:
                    throw new RuntimeException("Unknown execution method for: " + command.getName());
            }
//            if (result == null && method.getReturnType().isPrimitive() && !method.returnsVoid()) {
////                throw new RuntimeException("Mapper method '" + command.getName()
////                        + " attempted to return null from a method with a primitive return type (" + method.getReturnType() + ").");
////            }
            return result;

    }

    private <E> Object executeForMany(SqlSession sqlSession, Object[] args) {
        List<E> result;
        Object param = convertArgsToSqlCommandParam(args);

        result = sqlSession.selectList(command.getName(), param);

        // issue #510 Collections & arrays support
        if (!method.getReturnType().isAssignableFrom(result.getClass())) {
            if (method.getReturnType().isArray()) {
                return convertToArray(result);
            } else {
                return convertToDeclaredCollection(sqlSession.getConfiguration(), result);
            }
        }
        return result;
    }

    private <E> Object convertToArray(List<E> list) {
        Class<?> arrayComponentType = method.getReturnType().getComponentType();
        Object array = Array.newInstance(arrayComponentType, list.size());
        if (arrayComponentType.isPrimitive()) {
            for (int i = 0; i < list.size(); i++) {
                Array.set(array, i, list.get(i));
            }
            return array;
        } else {
            return list.toArray((E[])array);
        }
    }

    private <E> Object convertToDeclaredCollection(Configuration config, List<E> list) {
        Class classToCreate = resolveInterface(method.getReturnType());
        Collection collection = null;
        try {
            collection = (Collection) classToCreate.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        collection.addAll(list);
        return collection;
    }

    protected Class<?> resolveInterface(Class<?> type) {
        Class<?> classToCreate;
        if (type == List.class || type == Collection.class || type == Iterable.class) {
            classToCreate = ArrayList.class;
        } else if (type == Map.class) {
            classToCreate = HashMap.class;
        } else if (type == SortedSet.class) { // issue #510 Collections Support
            classToCreate = TreeSet.class;
        } else if (type == Set.class) {
            classToCreate = HashSet.class;
        } else {
            classToCreate = type;
        }
        return classToCreate;
    }

    private Object convertArgsToSqlCommandParam(Object[] args) {
        List array = new ArrayList();
        if(args!=null&&args.length!=0){
            for (Object arg : args) {
                array.add(arg);
            }
        }
        return  array;
    }

    private Object rowCountResult(int rowCount) {
        final Object result;
        if (method.returnsVoid()) {
            result = null;
        } else if (Integer.class.equals(method.getReturnType()) || Integer.TYPE.equals(method.getReturnType())) {
            result = rowCount;
        } else if (Long.class.equals(method.getReturnType()) || Long.TYPE.equals(method.getReturnType())) {
            result = (long)rowCount;
        } else if (Boolean.class.equals(method.getReturnType()) || Boolean.TYPE.equals(method.getReturnType())) {
            result = rowCount > 0;
        } else {
            throw new RuntimeException("Mapper method '" + command.getName() + "' has an unsupported return type: " + method.getReturnType());
        }
        return result;
    }

    public static class SqlCommand {

        private final String name;
        private final SqlCommandType type;

        public SqlCommand(Configuration configuration, Class<?> mapperInterface, Method method) {
            final String methodName = method.getName();
            final Class<?> declaringClass = method.getDeclaringClass();
            MappedStatement ms = resolveMappedStatement(mapperInterface, methodName, declaringClass,
                    configuration);
            if (ms == null) {
                if (method.getAnnotation(Flush.class) != null) {
                    name = null;
                    type = SqlCommandType.FLUSH;
                } else {
                    throw new RuntimeException("Invalid bound statement (not found): "
                            + mapperInterface.getName() + "." + methodName);
                }
            } else {
                name = ms.getId();
                type = ms.getSqlCommandType();
                if (type == SqlCommandType.UNKNOWN) {
                    throw new RuntimeException("Unknown execution method for: " + name);
                }
            }
        }

        public String getName() {
            return name;
        }

        public SqlCommandType getType() {
            return type;
        }

        private MappedStatement resolveMappedStatement(Class<?> mapperInterface, String methodName,
                                                       Class<?> declaringClass, Configuration configuration) {
            String statementId = mapperInterface.getName() + "." + methodName;
            if (configuration.hasStatement(statementId)) {
                return configuration.getMappedStatement(statementId);
            } else if (mapperInterface.equals(declaringClass)) {
                return null;
            }
            for (Class<?> superInterface : mapperInterface.getInterfaces()) {
                if (declaringClass.isAssignableFrom(superInterface)) {
                    MappedStatement ms = resolveMappedStatement(superInterface, methodName,
                            declaringClass, configuration);
                    if (ms != null) {
                        return ms;
                    }
                }
            }
            return null;
        }
    }


    public static class MethodSignature {

        private final boolean returnsMany;
        private final boolean returnsMap;
        private final boolean returnsVoid;
        private final Class<?> returnType;
        private final String mapKey;



        public MethodSignature(Configuration configuration, Class<?> mapperInterface, Method method) {
            Type resolvedReturnType = TypeParameterResolver.resolveReturnType(method, mapperInterface);
            if (resolvedReturnType instanceof Class<?>) {
                this.returnType = (Class<?>) resolvedReturnType;
            } else if (resolvedReturnType instanceof ParameterizedType) {
                this.returnType = (Class<?>) ((ParameterizedType) resolvedReturnType).getRawType();
            } else {
                this.returnType = method.getReturnType();
            }
            this.returnsVoid = void.class.equals(this.returnType);
            this.returnsMany = Collection.class.isAssignableFrom(this.returnType) || this.returnType.isArray();

            this.mapKey = getMapKey(method);
            this.returnsMap = this.mapKey != null;

        }

        private String getMapKey(Method method) {
            String mapKey = null;
            if (Map.class.isAssignableFrom(method.getReturnType())) {
                final MapKey mapKeyAnnotation = method.getAnnotation(MapKey.class);
                if (mapKeyAnnotation != null) {
                    mapKey = mapKeyAnnotation.value();
                }
            }
            return mapKey;
        }

        public boolean returnsVoid() {
            return returnsVoid;
        }

        public boolean returnsMany() {
            return returnsMany;
        }

        public Class<?> getReturnType() {
            return returnType;
        }
    }
}
