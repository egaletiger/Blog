package com.sjzd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjzd.exception.NotFoundException;
import com.sjzd.mapper.TypeMapper;
import com.sjzd.pojo.Type;
import com.sjzd.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  分类服务实现类
 * </p>
 *
 * @author 
 * @since 2019-12-08
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements ITypeService {

    @Autowired
    private TypeMapper typeMapper;


    @Override
    @Transactional
    public Integer saveType(Type type) {
        return typeMapper.insert(type);
    }

    @Override
    public IPage<Type> listType(Long currentPage, Long size) {
        Page<Type> page = new Page<>(currentPage, size);
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        return typeMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Type findTypeById(Integer id) {
        Type type = typeMapper.selectById(id);
        if (null == type) {
            throw new NotFoundException("该资源不存在");
        }
        return type;
    }

    @Override
    public Type findTypeByName(String name) {
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        Type type = typeMapper.selectOne(queryWrapper);
        if (null == type) {
            throw new NotFoundException("该资源不存在");
        }
        return type;
    }

    @Override
    @Transactional
    public Integer updateType(Type newType) {
        return typeMapper.updateById(newType);
    }

    @Override
    @Transactional
    public Integer deleteType(Integer id) {
        return typeMapper.deleteById(id);
    }

    /**
     *  列出前四个使用最多的分类
     * @return
     */
    @Override
    public List<Type> listTop(Long size) {
        return typeMapper.listTop(size);
    }


}
