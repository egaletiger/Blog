package com.sjzd.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sjzd.pojo.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  标签服务类
 * </p>
 *
 * @author 
 * @since 2019-12-08
 */
public interface ITagService extends IService<Tag> {
    Integer saveTag(Tag tag);

    Integer deleteTag(Integer id);

    Integer updateTag(Tag newTag);

    IPage<Tag> listTag(Long currentPage, Long size);

    List<Tag> ListTagByTagIds(String tagIds);

    Tag findTagById(Integer id);

    Tag findTagByName(String name);

    List<Tag> listTop(Long size);
}
