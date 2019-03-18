package com.niutd.demo.controller;

import com.niutd.demo.entity.UserInfo;
import com.niutd.demo.service.DemoService;
import com.niutd.encrypt.RequestDecode;
import com.niutd.encrypt.ResponseEncode;
import com.niutd.utils.Result;
import com.niutd.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: niutd
 * @date: 2019/3/13 14:39
 */
@RestController /* @Controller + @ResponseBody*/
@RequestMapping("/api/user")
@Api(description = "DemoController|测试控制器")
public class DemoController {

    private final static Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private DemoService demoService;

    @GetMapping("/v1/hello")
    @ApiOperation(value = "V1 hello 接口")
    public Result helloV1() {
        return ResultUtil.success("Hello World GET v1");
    }


    @GetMapping("/v2/hello")
    @ApiOperation(value = "V2 hello 接口")
    public Result helloV2() {
        return ResultUtil.success("Hello World GET v2");
    }


    @GetMapping(value = "/")
    @ApiOperation(value = "获取用户列表")
    public Result getUserList() {
        List<UserInfo> list = demoService.getUserList();
        return ResultUtil.success(list);
    }

    @PostMapping(value = "/")
    @ApiOperation(value = "创建用户")
    @RequestDecode//请求数据解密
    public Result addUser(@Valid @RequestBody UserInfo user) {
        demoService.addUser(user);
        return ResultUtil.success(user);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据用户id获取用户信息")
    @ResponseEncode//响应数据加密
    public Result getUser(@PathVariable("id") int id) throws Exception {
        UserInfo user = demoService.getUser(id);
        if (user == null) {
            throw new Exception("用户信息为空");//异常由GlobalExceptionHandler统一处理
        }
        return ResultUtil.success(user);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "根据用户id删除用户信息")
    public Result deleteUser(@PathVariable("id") int id) {
        demoService.deleteUser(id);
        return ResultUtil.success(1);
    }

    @PatchMapping(value = "/{id}")
    @ApiOperation(value = "根据用户id修改用户信息")
    public Result updateUser(@PathVariable("id") int id, @RequestBody UserInfo user) {
        demoService.updateUser(user);
        return ResultUtil.success(1);
    }

}
