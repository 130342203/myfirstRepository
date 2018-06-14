package com.ck.framework.param;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tpeng on 2016/4/1.
 */
public class BaseParam {

    // Grid 参数
    private Integer page = 1;

    private Integer rows = 10;

    public static final String STRING_SPLIT = "¤";

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

//    private static Pattern emoji = Pattern. compile(
//            "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
//            Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE) ;

    /**
     * 将request请求参数统一接收Map格式，并重新request.setAttribute 返回到页面
     * @author tpeng
     */
    public static Map<String,String> initParamMap(HttpServletRequest request){
        Map<String,String> rmap = new HashMap<>();
        Map paramMap = request.getParameterMap();
        for (Object obj : paramMap.entrySet()) {
            Map.Entry entry = (Map.Entry)obj;
            String name = (String) entry.getKey();
            String value = "";
            Object valObj = entry.getValue();
            if(valObj != null){
                if(valObj instanceof String[]){
                    String[] values = (String[])valObj;
                    values = toEmojiFilter(values);
                    value =  StringUtils.join(values, STRING_SPLIT);
                }else{
                    value = toEmojiFilter(valObj.toString());
                }
                rmap.put(name, value);
            }
        }
        return rmap;
    }

    public static String[] toEmojiFilter(String[] values) {
        String[] arry = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            arry[i] = toEmojiFilter(values[i]);
        }
        return arry;
    }

    public static String toEmojiFilter(String value){
        return value.replaceAll("[^\\u0000-\\uFFFF]", "");
//        Matcher emojiMatcher = emoji.matcher(value);
//        while (emojiMatcher.find()) {
//            value = emojiMatcher.replaceAll("");
//        }
//        return value;
    }

}
