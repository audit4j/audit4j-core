/*
 * Copyright (c) 2014-2015 Janith Bandara, This source is a part of
 * Audit4j - An open source auditing framework.
 * http://audit4j.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.audit4j.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

/**
 * The Audit annotation.
 * 
 * This annotation can be used either in class level or method level. When
 * applying this annotation, the class or method is marked as auditable.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0.0
 */
@InterceptorBinding
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE, ElementType.METHOD })
public @interface Audit {

    /**
     * Action defines either method or operation.
     * 
     * @return the string
     */
    @Nonbinding
    public String action() default "action";

    /**
     * Selection.
     *
     * @return the string
     * @deprecated : this attribute is no longer using. 
     */
    @Deprecated
    @Nonbinding
    public SelectionType selection() default SelectionType.ALL;

    /**
     * Tag.
     *
     * @return the string
     * @deprecated see repository for more information. Repository.
     */
    @Deprecated
    @Nonbinding
    public String tag() default "default";

    /**
     * Repository. This attribute using to define the repository where audit log
     * located.
     *
     * @return the string
     */
    @Nonbinding
    public String repository() default "default";
}