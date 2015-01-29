package org.audit4j.core;

import org.audit4j.core.util.EncryptionUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * The Class EncryptionUtilTest.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class EncryptionUtilTest {

	/**
	 * Test.
	 */
	@Test
	public void test() {
		EncryptionUtil util =  EncryptionUtil.getInstance("1234", "abcd");
		String raw = "testRaw";
		try {
			String encrypt = util.encrypt(raw);
			Assert.assertNotNull(encrypt);
			String decrypt = util.decrypt(encrypt);
			Assert.assertNotNull(decrypt);
			Assert.assertEquals(raw, decrypt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
