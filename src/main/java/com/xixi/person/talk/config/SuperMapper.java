package com.xixi.person.talk.Config;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
/**
 * @ Description :  基础mapper类
 * @ Author :  shengchengchao
 * @ Date :  2020/4/30
 */
public interface SuperMapper<T> extends BaseMapper<T> {
    // 这里可以写自己的公共方法、注意不要让 mp 扫描到误以为是实体 Base 的操作
}
