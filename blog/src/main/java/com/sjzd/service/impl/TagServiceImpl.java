package com.sjzd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjzd.exception.NotFoundException;
import com.sjzd.mapper.BlogTagMapper;
import com.sjzd.mapper.TagMapper;
import com.sjzd.pojo.Tag;
import com.sjzd.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  标签服务实现类
 * </p>
 *
 * @author 
 * @since 2019-12-08
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private BlogTagMapper blogTagMapper;

    @Transactional
    @Override
    public Integer saveTag(Tag tag) {
        return tagMapper.insert(tag);
    }

    @Transactional
    @Override
    public Integer deleteTag(Integer id) {
        // 检查改标签是否被使用，被引用则删除失败
        if (blogTagMapper.countBlogByTagId(id) > 0) {
            return 0;
        }
        
        // 更新blog_tag表中的信息
        if (blogTagMapper.deleteBlogTagByTagId(id) < 1) {
            return 0;
        }
        return tagMapper.deleteById(id);
    }

    @Transactional
    @Override
    public Integer updateTag(Tag newTag) {
        return tagMapper.updateById(newTag);
    }

    @Transactional
    @Override
    public IPage<Tag> listTag(Long currentPage, Long size) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        return tagMapper.selectPage(new Page<Tag>(currentPage,size),
                queryWrapper);
    }

    /**
     *  可以没有标签
     * @param tagIds
     * @return
     */
    @Override
    public List<Tag> ListTagByTagIds(String tagIds) {
        if(null == tagIds) {
            return null;
        }
        String[] split = tagIds.split(",");
        List<Integer> collect = Arrays.stream(split).map(Integer::parseInt).collect(Collectors.toList());
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", collect);
        return tagMapper.selectList(queryWrapper);
    }

    @Override
    public Tag findTagById(Integer id) {
        Tag tag = tagMapper.selectById(id);
        if (null == tag) {
            throw new NotFoundException("该资源不存在");
        }
        return tag;
    }

    @Override
    public Tag findTagByName(String name) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        Tag tag = tagMapper.selectOne(queryWrapper);
        if (null == tag) {
            throw new NotFoundException("该资源不存在");
        }
        return tag;
    }

    /**
     *  列出前4个被使用最多标签，以及使用其博客的数量
     * @return
     */
    @Override
    public List<Tag> listTop(Long size) {
        List<Tag> tags = blogTagMapper.listTop(size);
        return tags;
    }
}
