package com.yin.service.impl;

import com.yin.mapper.OrderDao;
import com.yin.pojo.OrderDa;
import com.yin.pojo.qt.yb.Zymxb;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yin
 * @date 2024-11-12
 * @Description
 */
@Service
public class OrderService {
    @Resource
    private OrderDao orderDao;

    public synchronized void doHandle(){
        orderDao.streamDoHandler(resultContext -> {
            Zymxb resultObject = resultContext.getResultObject();
            System.out.println(resultObject);
        });
    }



}
