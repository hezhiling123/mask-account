//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.mask.core.bizcode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(
    name = "property"
)
public class BizCodeVo {
    @XmlAttribute(
        name = "name"
    )
    private String name;
    @XmlAttribute(
        name = "value"
    )
    private String value;

    public BizCodeVo() {
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof BizCodeVo)) {
            return false;
        } else {
            return this.name != null && this.name.equals(((BizCodeVo)obj).getName()) && this.value != null && this.value.equals(((BizCodeVo)obj).getValue());
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
