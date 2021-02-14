package com.shu.onlineEducation.controller;

import com.shu.onlineEducation.dao.LiveAddressJpaRepository;
import com.shu.onlineEducation.entity.LiveAddress;
import com.shu.onlineEducation.utils.ExceptionUtil.ExistedException;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.Result.Result;
import com.shu.onlineEducation.utils.Result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Live_address")
@Api(tags = "直播地址模块")
public class LiveAddressController {
    @Autowired
    LiveAddressJpaRepository liveAddressJpaRepository;

    @GetMapping("/")
    @ApiOperation(value = "获取所有直播线路地址")
    @ResponseBody
    public Result getAllLiveAddress() {
        return Result.success(liveAddressJpaRepository.findAll());
    }

    @PostMapping("/addLiveAddress")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "添加直播线路地址")
    @ResponseBody
    public Result addLiveAddress(String liveAddress) throws ExistedException {
        if (liveAddressJpaRepository.existsByLiveAddress(liveAddress)) {
            throw new ExistedException(ResultCode.LIVE_ADDRESS_HAS_EXISTED);
        } else {
            LiveAddress tmpLiveAddress = new LiveAddress();
            tmpLiveAddress.setLiveAddress(liveAddress);
            liveAddressJpaRepository.saveAndFlush(tmpLiveAddress);
            return Result.success();
        }
    }

    @PostMapping("/deleteLiveAddress")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "删除直播线路地址")
    @ResponseBody
    public Result deleteLiveAddress(Integer liveAddressId) {
        liveAddressJpaRepository.deleteById(liveAddressId);
        return Result.success();
    }

    @PostMapping("/modifyLiveAddress")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "修改直播线路地址")
    @ResponseBody
    public Result modifyLiveAddress(@RequestBody LiveAddress liveAddress) throws NotFoundException {
        if (liveAddressJpaRepository.existsById(liveAddress.getLiveAddressId())) {
            liveAddressJpaRepository.saveAndFlush(liveAddress);
            return Result.success();
        } else {
            throw new NotFoundException(ResultCode.LIVE_ADDRESS_NOT_FOUND);
        }
    }
}
