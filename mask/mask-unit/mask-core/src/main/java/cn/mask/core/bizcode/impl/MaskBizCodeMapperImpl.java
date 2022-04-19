
package cn.mask.core.bizcode.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import cn.mask.core.bizcode.BizCodeRootVo;
import cn.mask.core.bizcode.BizCodeVo;
import cn.mask.core.bizcode.MaskBizCodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

public class MaskBizCodeMapperImpl implements MaskBizCodeMapper {
    Logger logger = LoggerFactory.getLogger(MaskBizCodeMapperImpl.class);
    private List<BizCodeVo> bizCodeVos;
    private final Map<String, String> bizCodeMap = new HashMap<>();

    public MaskBizCodeMapperImpl(String confPath) throws RuntimeException {
        if (!StringUtils.isEmpty(confPath)) {
            ClassPathResource resource = new ClassPathResource(confPath);
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
                Throwable var4 = null;
                try {
                    JAXBContext context = JAXBContext.newInstance(BizCodeRootVo.class);
                    Unmarshaller unmarshaller = context.createUnmarshaller();
                    BizCodeRootVo bizCodeTmp = (BizCodeRootVo)unmarshaller.unmarshal(br);
                    if (bizCodeTmp != null && bizCodeTmp.getCodeVos() != null) {
                        this.bizCodeVos = bizCodeTmp.getCodeVos();
                        for (BizCodeVo bizCodeVo : this.bizCodeVos) {
                            if (this.bizCodeMap.containsKey(bizCodeVo.getName()) && !this.bizCodeMap.get(bizCodeVo.getName()).equals(bizCodeVo.getValue())) {
                                throw new RuntimeException("业务监控业务映射配置文件" + confPath + "中存在多个name为" + bizCodeVo.getName() + "且value不相同的业务映射配置项");
                            }

                            this.logger.info("===加载业务编码配置信息，name=" + bizCodeVo.getName() + "; value=" + bizCodeVo.getValue());
                            this.bizCodeMap.put(bizCodeVo.getName(), bizCodeVo.getValue());
                        }
                    }
                } catch (Throwable var18) {
                    var4 = var18;
                    throw var18;
                } finally {
                    if (var4 != null) {
                        try {
                            br.close();
                        } catch (Throwable var17) {
                            var4.addSuppressed(var17);
                        }
                    } else {
                        br.close();
                    }

                }
            } catch (Exception var20) {
                this.logger.error(var20.getMessage(), var20);
                throw new RuntimeException("业务监控业务映射配置文件" + confPath + "有误,请检查配置");
            }
        }

    }

    @Override
    public String transBizCode(String url) {
        return StringUtils.isEmpty(url) ? null : this.bizCodeMap.get(url);
    }

    @Override
    public List<BizCodeVo> queryBizCode() {
        return this.bizCodeVos;
    }
}
