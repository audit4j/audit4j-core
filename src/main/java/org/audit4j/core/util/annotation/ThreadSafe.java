package org.audit4j.core.util.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

@InterceptorBinding
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(value = { ElementType.TYPE })
public @interface ThreadSafe {

    public String value() default "";
}
