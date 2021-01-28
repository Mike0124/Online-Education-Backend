package com.shu.onlineEducation.controller;

import com.shu.onlineEducation.dao.LiveAddressJpaRepository;
import com.shu.onlineEducation.utils.Result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Live_address")
@Api(tags = "直播地址模块")
public class LiveAddressController {
    @Autowired
    LiveAddressJpaRepository liveAddressJpaRepository;

    @GetMapping("/")
    @ApiOperation(value = "获取所有直播线路地址")
    @ResponseBody
    public Result getAllLiveAddress(){
        return Result.success(liveAddressJpaRepository.findAll());
    }
}
