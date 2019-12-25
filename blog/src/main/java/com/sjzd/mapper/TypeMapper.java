package com.sjzd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sjzd.pojo.Type;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-12-08
 */
public interface TypeMapper extends BaseMapper<Type> {

    @Select("select ty.id, ty.name, count(*) blogSize from blog b, type ty where b.type_id " +
            "= ty.id group by ty.id order by blogSize desc limit 0, ${size}")
    List<Type> listTop(long size);
}
