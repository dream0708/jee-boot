package com.jee.boot.datasource.first.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jee.boot.datasource.first.entity.UmsIntegrationChangeHistory;
import com.jee.boot.datasource.first.dao.UmsIntegrationChangeHistoryMapper;
import com.jee.boot.datasource.first.service.UmsIntegrationChangeHistoryService;

/**
   @author yaomengke
   @create 2020- 08 - 18 - 17:03 
    
 */
@Service
public class UmsIntegrationChangeHistoryServiceImpl extends ServiceImpl<UmsIntegrationChangeHistoryMapper, UmsIntegrationChangeHistory> implements UmsIntegrationChangeHistoryService{

}
