package com.sjzd.mapper;

import com.sjzd.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.lang.Nullable;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-12-08
 */
public interface UserMapper extends BaseMapper<User> {
}
