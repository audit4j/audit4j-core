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

package org.audit4j.core.layout;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.audit4j.core.CoreConstants;
import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.exception.Audit4jRuntimeException;
import org.audit4j.core.exception.InitializationException;
import org.audit4j.core.util.EncryptionUtil;

/**
 * The Class SecureLayout.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0.0
 */
public class SecureLayout extends SimpleLayout {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5678939488854601303L;

    /** The key. */
    private String key;

    /** The salt. */
    private String salt;

    /** The util. */
    private EncryptionUtil util;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.audit4j.core.SimpleLayout#format(org.audit4j.core.dto.AuditEvent)
     */
    @Override
    public String format(AuditEvent event) {
        String formatText = super.format(event);

        try {
            return util.encrypt(formatText);
        } catch (InvalidKeyException e) {
            throw new Audit4jRuntimeException("", e);
        } catch (IllegalBlockSizeException e) {
            throw new Audit4jRuntimeException("", e);
        } catch (BadPaddingException e) {
            throw new Audit4jRuntimeException("", e);
        } catch (UnsupportedEncodingException e) {
            throw new Audit4jRuntimeException("", e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new Audit4jRuntimeException("", e);
        } catch (NoSuchAlgorithmException e) {
            throw new Audit4jRuntimeException("", e);
        } catch (NoSuchPaddingException e) {
            throw new Audit4jRuntimeException("", e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.audit4j.core.layout.SimpleLayout#init()
     */
    @Override
    public void init() throws InitializationException {
        if (key == null) {
            key = CoreConstants.DEFAULT_SECURE_KEY;
        }
        if (salt == null) {
            salt = CoreConstants.DEFAULT_SECURE_SALT;
        }
        try {
            util = EncryptionUtil.getInstance(key, salt);
        } catch (NoSuchAlgorithmException e) {
            throw new InitializationException("Could not Initialize the layout: " + SecureLayout.class.getName(), e);
        } catch (UnsupportedEncodingException e) {
            throw new InitializationException("Could not Initialize the layout: " + SecureLayout.class.getName(), e);
        } catch (InvalidKeySpecException e) {
            throw new InitializationException("Could not Initialize the layout: " + SecureLayout.class.getName(), e);
        }
        super.init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.audit4j.core.layout.SimpleLayout#stop()
     */
    @Override
    public void stop() {
        super.stop();
        util = null;
    }

    /**
     * Gets the key.
     * 
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key.
     * 
     * @param key
     *            the new key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Gets the salt.
     * 
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Sets the salt.
     * 
     * @param salt
     *            the new salt
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }
}
