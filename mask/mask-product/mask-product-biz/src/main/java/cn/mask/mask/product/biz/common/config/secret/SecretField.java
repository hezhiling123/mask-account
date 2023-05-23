package cn.mask.mask.product.biz.common.config.secret;

import java.lang.annotation.*;

/**
 * @author phz
 * @date 2021/5/14 12:30
 * @since V1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SecretField {
	SecretFieldType secretFieldType() default SecretFieldType.UN_DEFINED;
}
