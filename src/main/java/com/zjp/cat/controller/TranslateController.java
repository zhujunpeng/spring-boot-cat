package com.zjp.cat.controller;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.zjp.cat.service.CatTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Cat监控控制层
 * @author zhujunpeng
 */
@RestController
@RequestMapping("/translate")
public class TranslateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranslateController.class);

    @Autowired
    private CatTestService catTestService;

    /**
     * 正常请求记录Transaction信息
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/getVoice")
    public Object getVoice(HttpServletRequest request, HttpServletResponse response) {
        Transaction t = Cat.newTransaction("URL", "translate/getVoice");
        Map<String, String> result = new HashMap<>();
        try{
            //do your business
            result.put("a", "a");
            result.put("b", "b");
            Random random = new Random();
            int time = random.nextInt(1000);
            LOGGER.info(String.format("sleep time: %s ms", time));
            // 随机休眠，用于测试接口耗时
            Thread.sleep(time);
            t.setStatus(Message.SUCCESS);
        } catch (Exception e) {
            Cat.getProducer().logError(e);
            t.setStatus(e);
        } finally {
            t.complete();
        }
        return result;
    }

    /**
     * 异常
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/testCat")
    public Object testCat(HttpServletRequest request, HttpServletResponse response) {
        Transaction t = Cat.newTransaction("TEST", "testCat");
        Map<String, String> result = new HashMap<>();
        try{
            result.put("a", "aaaaa");
            result.put("b", "bbbbbb");
            Random random = new Random();
            int time = random.nextInt(1000);
            LOGGER.info(String.format("testCat method sleep time: %s ms", time));
            Thread.sleep(time);
            int i = 1 / 0;
            // 在链路追踪里面添加信息
            t.addData("testCase测试啊","测试");
            t.setStatus(Message.SUCCESS);
        } catch (Exception e) {
            t.setStatus(e);
            LOGGER.error("接口异常",e);
            //Cat.logEvent("TEST","testCase", Event.SUCCESS, ExceptionUtils.getStackTrace(e));
            //Cat.logError(e);
        } finally {
            t.complete();
        }
        return result;
    }

    /**
     * 使用过滤器实现链路追踪
     * @param request
     * @param response
     * @return
     * @throws InterruptedException
     */
    @GetMapping(value = "/testFilter")
    public Object testFilter(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
        Map<String, String> result = new HashMap<>();
        result.put("a", "zzzz");
        result.put("b", "pppp");
        Random random = new Random();
        int time = random.nextInt(1000);
        LOGGER.info(String.format("testFilter method sleep time: %s ms", time));
        Thread.sleep(time);
        return result;
    }

    /**
     * 通过AOP的方式实现方法的监控
     * @param request
     * @param response
     * @return
     * @throws InterruptedException
     */
    @GetMapping(value = "/testMethodAop")
    public Object testMethodAop(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
        Map<String, String> result = catTestService.testMethodAop();
        return result;
    }
}
