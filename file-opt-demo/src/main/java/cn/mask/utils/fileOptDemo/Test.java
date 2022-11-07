package cn.mask.utils.fileOptDemo;

import cn.hutool.core.util.ReUtil;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-07-26 10:19:23
 */
public class Test {
    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

    @org.junit.Test
    public void test() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("upload_time", "2022-08-29");
        jsonObject.put("elec_bill_file_path", "aaa");
        jsonObject.put("elec_bill_file_format", "PDF");
//        System.out.println(jsonObject.toJSONString());
//        System.out.println("{\"upload_time\":\"2022-08-29\",\"elec_bill_file_path\":\"aaa\",\"elec_bill_file_format\":\"PDF\"}");
        System.out.println(ReUtil.replaceAll("/user/aa", PATTERN, "*"));
        ArrayList<String> list = new ArrayList<>();
        list.add("111");
        list.add("222");
        List<String> list2 = new ArrayList<>();
        list2.add("333");
        list2.add("222");
        List<String> list3 = list2.stream().filter(t-> !list.contains(t)).collect(Collectors.toList());
        System.out.println(list3.toString());
    }

    @org.junit.Test
    public void test1() {
        String a = null;
        System.out.println("a" + a);
    }
}
