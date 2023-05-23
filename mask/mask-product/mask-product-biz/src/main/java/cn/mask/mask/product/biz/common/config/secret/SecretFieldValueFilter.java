package cn.mask.mask.product.biz.common.config.secret;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.serializer.ValueFilter;

import java.lang.reflect.Field;

/**
 * @author phz
 * @date 2021/5/14 13:45
 * @since V1.0
 */
public class SecretFieldValueFilter implements ValueFilter {

	private static final char CHAR_STAR = '*';
	private static final int MOBILE_LENGTH = 11;
	private static final int ID_CARD_LENGTH = 18;

	@Override
	public Object process(Object object, String name, Object value) {
		if (value == null) {
			return null;
		}
		Field field = ReflectUtil.getField(object.getClass(), name);
		if (field == null) {
			return value;
		}
		SecretField secretField = field.getAnnotation(SecretField.class);
		if (secretField == null) {
			return value;
		}
		String convert;
		if (value instanceof StringBuilder) {
			convert = ((StringBuilder) value).toString();
		} else if (value instanceof StringBuffer) {
			convert = ((StringBuffer) value).toString();
		} else if (value instanceof String) {
			convert = ((String) value);
		} else {
			return value;
		}
		if (StrUtil.isBlank(convert)) {
			return convert;
		}
		if (SecretFieldType.MOBILE_PHONE.equals(secretField.secretFieldType())) {
			if (convert.length() != MOBILE_LENGTH) {
				return convert;
			}
			return convert.substring(0, 3) + StrUtil.repeat(CHAR_STAR, 4) + convert.substring(7);
		} else if (SecretFieldType.ID_CARD.equals(secretField.secretFieldType())) {
			if (convert.length() != ID_CARD_LENGTH) {
				return convert;
			}
			return convert.substring(0, 2) + StrUtil.repeat(CHAR_STAR, 13) + convert.substring(15);
		} else if (SecretFieldType.NAME.equals(secretField.secretFieldType())) {
			if (convert.length() == 1) {
				convert = String.valueOf(CHAR_STAR);
			} else if (convert.length() == 2) {
				convert = convert.substring(0, 1) + CHAR_STAR;
			} else {
				convert = convert.substring(0, 1) + StrUtil.repeat(CHAR_STAR, convert.length() - 2) + convert.substring(convert.length() - 1);
			}
		} else {
			convert = StrUtil.repeat(CHAR_STAR, convert.length());
		}
		return convert;
	}
}
