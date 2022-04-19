package cn.mask.core.bizcode;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(
    name = "bizcodemapper"
)
public class BizCodeRootVo {
    @XmlElement(
        name = "property"
    )
    List<BizCodeVo> codeVos;

    public BizCodeRootVo() {
    }

    public List<BizCodeVo> getCodeVos() {
        return this.codeVos;
    }

    public void setCodeVos(List<BizCodeVo> codeVos) {
        this.codeVos = codeVos;
    }
}
