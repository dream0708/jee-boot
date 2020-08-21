package com.jee.boot.datasource.first.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jee.boot.datasource.first.dao.UmsMemberTagMapper;
import com.jee.boot.datasource.first.entity.UmsMemberTag;
import com.jee.boot.datasource.first.service.UmsMemberTagService;

/**
   @author yaomengke
   @create 2020- 08 - 18 - 17:03 
    
 */
@Service
public class UmsMemberTagServiceImpl extends ServiceImpl<UmsMemberTagMapper, UmsMemberTag> implements UmsMemberTagService{

}
