package com.jee.boot.datasource.second.service;

import com.alicp.jetcache.anno.Cached;
import com.jee.boot.datasource.second.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
   @author yaomengke
   @create 2020- 08 - 19 - 15:53 
    
 */
public interface DictService extends IService<Dict>{



    public Dict getDictDetails(String type , String value) ;




}
