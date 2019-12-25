package com.sjzd.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sjzd.pojo.Type;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  类型服务类
 * </p>
 *
 * @author 
 * @since 2019-12-08
 */
public interface ITypeService extends IService<Type> {

    Integer saveType(Type type);

    IPage<Type> listType(Long currentPage, Long size);

    Type findTypeById(Integer id);

    Type findTypeByName(String name);

    Integer updateType(Type newType);

    Integer deleteType(Integer id);

    List<Type> listTop(Long size);
}
