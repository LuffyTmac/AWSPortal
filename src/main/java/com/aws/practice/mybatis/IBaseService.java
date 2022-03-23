package com.aws.practice.mybatis;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;


public interface IBaseService<T> {

    /**
     * 通过指定的Model查询
     * @param t 对应Model，查询的条件为全等于
     * @return 对应Model的集合
     */
    public List<T> find(T t);

    /**
     *  根据条件查询
     * @param condition
     * @return
     */
    List<T> findByCondition(Condition condition);

    /**
     * 分页查询
     * @param t
     * @param pageInfo
     * @return
     */
    Page<T> page(Condition t, PageInfo pageInfo);

    PageInfo<T> pageInfo(Condition t, PageInfo pageInfo);

    /**
     * 根据id获得一个Model
     * @param id 对应唯一编号
     * @return id所对应的Model
     */
    public T get(String id);

    /**
     * 更新对应的Model（仅仅更新不为空的部分）
     * @param t
     * @return
     */
    public T updateSelective(T t);

    /**
     * 保存（或全部更新）对应的Model
     * 如果是保存，并且为String类型的Id，会使用UUID创建一个id
     * @param t 对应的Model
     * @return 保存或更新后的Model，包含Id
     */
    public T save(T t);

    /**
     * 保存并且自动设置createUser，updateUser等信息
     * @param t
     * @param operUserId
     * @return
     */
    public T save(T t, Object operUserId);

    /**
     * 保存（或全部更新）对应的Model
     * 如果是保存，并且为String类型的Id，会使用UUID创建一个id
     * @param t 对应的Model
     * @return 保存或更新后的Model，包含Id
     */
    public List<T> batchInsert(List<T> t);

    /**
     * 保存并且自动设置createUser，updateUser等信息
     * @param t
     * @param operUserId
     * @return
     */
    public List<T> batchInsert(List<T> t, Object operUserId);

    /**
     * 删除对应编号的Model（慎用，物理删除）
     * @param t
     */
    public void delete(T t);

    /**
     * 获得<T>的Condition
     * @return
     */
    public Condition newCondition();
}
