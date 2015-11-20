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

/**
 * The DeIdentify annotation.
 * 
 * This can be used to deidentify certain fields. This applies a mask for
 * certain characters with '*' character.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.PARAMETER, ElementType.FIELD })
public @interface DeIdentify {

    /**
     * Left.
     * 
     * @return the int
     */
    public int left() default 0;

    /**
     * Right.
     * 
     * @return the int
     */
    public int right() default 0;

    /**
     * From left.
     * 
     * @return the int
     */
    public int fromLeft() default 0;

    /**
     * From right.
     * 
     * @return the int
     */
    public int fromRight() default 0;

}
