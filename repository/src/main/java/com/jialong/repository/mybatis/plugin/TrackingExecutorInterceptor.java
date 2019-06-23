package com.jialong.repository.mybatis.plugin;

import com.jialong.repository.mybatis.reflection.factory.ObjectWrapper;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.SimpleTypeRegistry;
import org.springframework.cglib.proxy.Factory;

import java.util.*;

@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
        , @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class TrackingExecutorInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();
        if (result == null)
            return result;
        if (result instanceof Iterable) {
            Iterable iterable = (Iterable) result;
            Iterator iterator = iterable.iterator();
            while (iterator.hasNext()) {
                Object item = iterator.next();
                if (!this.startTracking(item)) {
                    break;
                }
            }
            return result;
        }
        if (result instanceof Map) {
            Map map = (Map) result;
            for (Object item : map.values()) {
                if (!this.startTracking(item)) {
                    break;
                }
            }
            return result;
        }
//        if (type == List.class || type == Collection.class || type == Iterable.class) {
//            classToCreate = ArrayList.class;
//        } else if (type == Map.class) {
//            classToCreate = HashMap.class;
//        } else if (type == SortedSet.class) { // issue #510 Collections Support
//            classToCreate = TreeSet.class;
//        } else if (type == Set.class) {
//            classToCreate = HashSet.class;
//        } else {
//            classToCreate = type;
//        }
        this.startTracking(result);
        return result;
    }

    private boolean startTracking(Object item) {
        Class type = item.getClass();
        if (SimpleTypeRegistry.isSimpleType(type)) {
            return false;
        }
        if (!(item instanceof Factory)) {
            return false;
        }
        Factory factory = (Factory) item;
        ObjectWrapper objectWrapper = (ObjectWrapper) factory.getCallback(1);
        objectWrapper.startTracking();
        return true;
    }

    @Override
    public Object plugin(Object target) {
        return (target instanceof Executor) ? Plugin.wrap(target, this) : target;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
