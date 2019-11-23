package com.ljj.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @Description: TODO
 * @Author LeeJack
 * @Date 2019/11/13
 * @Version V1.0
 **/
@RestController
public class TestEndpoints {

    @GetMapping("/order")
    @ApiOperation(value = "受保护的接口")
    public String getOrder(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "order id : " + id;
    }
    @GetMapping("/product")
    @ApiOperation(value = "没有保护的接口", hidden = true)
    public String getProduct(@PathVariable String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "product id : " + id;
    }
}
