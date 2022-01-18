package com.tyzz.blog.entity.convert;

import com.tyzz.blog.entity.pojo.Administrator;
import com.tyzz.blog.entity.vo.AdministratorVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-01-18 15:36
 */
@Mapper
public interface AdministratorConverter {
    AdministratorConverter INSTANCE = Mappers.getMapper(AdministratorConverter.class);

    AdministratorVO admin2AdminVO(Administrator admin);
}
