package cn.mask.core.framework.web.exception;

import java.net.InetAddress;
import java.util.Random;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExceptionSeq {
    private final Logger log = LoggerFactory.getLogger(ExceptionSeq.class);
    private String hostSign = "";
    private int seqNo = 0;

    public ExceptionSeq() {
    }

    public String getExceptionSeq() {
        return this.hostSign + this.getNextSeq();
    }

    @PostConstruct
    private void init() {
        this.log.info("[mask]======ExceptionSeq.init======");
        this.hostSign = String.valueOf(this.getAID());
        this.log.info("hostSign=" + this.hostSign);
    }

    private int getAID() {
        boolean var1 = false;

        int aid;
        try {
            InetAddress inet = InetAddress.getLocalHost();
            byte[] bytes = inet.getAddress();
            this.log.info("[mask]当前主机地址：" + inet.getHostAddress());
            aid = this.getInt(bytes);
        } catch (Exception var4) {
            Random ra = new Random();
            aid = ra.nextInt(100000);
        }

        return aid;
    }

    private int getInt(byte[] bytes) {
        int size = Math.min(bytes.length, 6);
        int result = 0;

        for(int i = size - 1; i >= 0; --i) {
            if (i == size - 1) {
                result += bytes[i];
            } else {
                result += bytes[i] << 4 * (size - 1 - i);
            }
        }

        return result > 0 ? result : -result;
    }

    private synchronized int getNextSeq() {
        int sn = this.seqNo++;
        if (sn > 100000) {
            this.seqNo = 0;
            sn = 0;
        }

        return sn;
    }
}
