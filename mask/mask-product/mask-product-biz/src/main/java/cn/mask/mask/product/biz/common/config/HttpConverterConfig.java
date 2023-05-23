package cn.mask.mask.product.biz.common.config;


import cn.mask.mask.product.biz.common.config.secret.SecretFieldValueFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * json序列化框架采用FastJson实现
 * @author hezhiling
 */
@Configuration
public class HttpConverterConfig {

	@Bean
	public FastJsonConfig fastJsonConfig() {
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		SerializerFeature[] serializerFeatures = new SerializerFeature[]{
				SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullListAsEmpty,
				SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteNullBooleanAsFalse,
				SerializerFeature.WriteDateUseDateFormat,
				SerializerFeature.DisableCircularReferenceDetect};
		fastJsonConfig.setSerializerFeatures(serializerFeatures);
		fastJsonConfig.setSerializeFilters(new SecretFieldValueFilter());
		fastJsonConfig.setCharset(StandardCharsets.UTF_8);
		return fastJsonConfig;
	}

	@Bean
	@DependsOn("fastJsonConfig")
	public HttpMessageConverters fastJsonHttpMessageConverters(FastJsonConfig fastJsonConfig) {
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverterExtension();
		fastConverter.setFastJsonConfig(fastJsonConfig);
		return new HttpMessageConverters(fastConverter);
	}

	private static class FastJsonHttpMessageConverterExtension extends FastJsonHttpMessageConverter {
		@Override
		protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
			if (object instanceof String) {
				Charset charset = this.getContentTypeCharset(outputMessage.getHeaders().getContentType());
				StreamUtils.copy((String) object, charset, outputMessage.getBody());
			} else {
				super.writeInternal(object, outputMessage);
			}
		}

		private Charset getContentTypeCharset(MediaType contentType) {
			return contentType != null && contentType.getCharset() != null ? contentType.getCharset() : Charset.defaultCharset();
		}
	}
}