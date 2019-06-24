package com.zjp.cat.service;

import java.util.Map;

/**
 * CatAnnotation监控测试
 *
 * @Author zhujunpeng
 * @create 2019-06-24
 */
public interface CatTestService {

    Map<String,String> testMethodAop() throws InterruptedException;
}
