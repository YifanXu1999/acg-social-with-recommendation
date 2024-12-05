package com.acgsocial.user.gateway.util.result;

import com.acgsocial.common.result.ResponseResult;
import com.acgsocial.utils.json.JsonUtil;

import java.util.LinkedHashMap;

public class ResponseResultUtil {

    public static  <T> T  parse(ResponseResult response, Class<T> clazz) {
        LinkedHashMap<String, String> resposnseData = (LinkedHashMap<String, String> )response.getData();
        return JsonUtil.convert(resposnseData, clazz);
    }


}
