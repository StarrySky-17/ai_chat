package com.example.aichat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.aichat.entity.SystemLog;
import org.apache.ibatis.annotations.Param;

public interface SystemLogMapper extends BaseMapper<SystemLog> {

    IPage<SystemLog> selectByUserId(Page<SystemLog> page, @Param("userId") Long userId);

    IPage<SystemLog> selectByOperation(Page<SystemLog> page, @Param("operation") String operation);

}