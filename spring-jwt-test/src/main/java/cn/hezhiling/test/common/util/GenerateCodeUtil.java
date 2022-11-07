package cn.hezhiling.test.common.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-06-25 10:00:05
 */
public class GenerateCodeUtil {

    private GenerateCodeUtil() {}

    public static String generateCode() {
        try {
            //创建一个SecureRandom的实例，该实例会生成一个随机的int值
            SecureRandom random = SecureRandom.getInstanceStrong();
            return String.valueOf(random.nextInt(9000) + 1000);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("生成认证code失败");
        }
    }
}
