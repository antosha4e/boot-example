package com.bootexample.util;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.PostgreSQL82Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import java.util.List;

/**
 * Created: antosha4e
 * Date: 16.05.16
 */
public class BootDialect extends Dialect {
    public BootDialect() {
        super();

        registerFunction("getHash",
                new StandardSQLFunction( "regexp_like", StandardBasicTypes.STRING ) {
                    public String render(Type firstArgumentType, List arguments, SessionFactoryImplementor factory) {
                        String field = (String) arguments.get(0);
                        String value = (String) arguments.get(1);

                        System.out.println(field + " : " + value);

                        return PasswordEncryptor.encryptPassword("1");
                    }
                });
    }
}