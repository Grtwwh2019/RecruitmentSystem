package com.zzj.recruitment.Interceptor;

import com.github.pagehelper.Dialect;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Mybatis 通用分页拦截器
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-16 19:37:26
 */

/**
 * @Intercepts 和 @Signature注解用来配置拦截器要拦截的接口的方法
 * @Intercepts中的属性是一个@Signature数组，可以在同一个拦截器中同时拦截不同的接口和方法
 * @Signature包含三个属性： type：设置拦截的接口，可以是Executor、ParameterHandler、ResultSetHandler、StatementHandler
 * method：设置拦截接口中的方法名，可选值是前面4个接口对应的方法，需要和接口匹配。
 * args：设置拦截方法的参数类型数组，通过方法名和参数类型可以确定唯一一个方法。
 */
@Intercepts(
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class PageInterceptor implements Interceptor {

        private static final List<ResultMapping> EMPTY_RESULTMAPPING = new ArrayList<ResultMapping>(0);
        private Dialect dialect;
        private Field additionalParametersField;

        @Override
        public Object plugin(Object target) {
                return null;
        }

        @Override
        public void setProperties(Properties properties) {

        }

        @Override
        public Object intercept(Invocation invocation) throws Throwable {
                // 设置拦截方法的参数
                Object[] args = invocation.getArgs();
                MappedStatement ms = (MappedStatement) args[0];
                Object parameterObject = args[1];
                RowBounds rowBounds = (RowBounds) args[2];
                // 调用方法判断是否需要进行分页，如果不需要，直接返回结果


                /*等待做完整个项目再回来拆分*/


                return null;
        }
}
