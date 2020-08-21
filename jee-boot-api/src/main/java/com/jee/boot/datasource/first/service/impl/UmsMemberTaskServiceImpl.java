package com.jee.boot.datasource.first.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jee.boot.datasource.first.entity.UmsMemberTask;
import com.jee.boot.datasource.first.dao.UmsMemberTaskMapper;
import com.jee.boot.datasource.first.service.UmsMemberTaskService;

/**
   @author yaomengke
   @create 2020- 08 - 18 - 17:03 
    
 */
@Service
public class UmsMemberTaskServiceImpl extends ServiceImpl<UmsMemberTaskMapper, UmsMemberTask> implements UmsMemberTaskService{

}
