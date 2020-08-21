package com.jee.boot.datasource.second.service.impl;

import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jee.boot.datasource.second.entity.Dict;
import com.jee.boot.datasource.second.dao.DictMapper;
import com.jee.boot.datasource.second.service.DictService;
/**
   @author yaomengke
   @create 2020- 08 - 19 - 15:53 
    
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService{


    @Override
    @Cached(name = "cache:getDictDetails:" , key = "#type + ':' + #value" , localExpire = 3600)
    public Dict getDictDetails(String type , String value) {

        return this.baseMapper.selectOne(new QueryWrapper<Dict>().lambda()
                .select(
                Dict::getSid , Dict::getName , Dict::getValue , Dict::getType , Dict::getSort , Dict::getRemark)
        .eq(Dict::getType , type).eq(Dict::getValue , value)) ;
    }
}
