package com.aws.practice.mybatis.impl;

import com.aws.practice.mybatis.IBaseMapper;
import com.aws.practice.mybatis.IBaseService;
import com.aws.practice.mybatis.uuid.UUIDGenerator;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;


public class BaseService<T> implements IBaseService<T>
{

    private static final Logger log = LoggerFactory.getLogger(BaseService.class);

    private IBaseMapper<T> mapper;

    @Autowired
    private UUIDGenerator uuidGenerator;

    public BaseService() {
    }

    @Autowired
    protected void setMapper(IBaseMapper<T> mapper) {
        this.mapper = mapper;
    }

    private Type[] getGenericParams() {
        Type genType = this.getClass().getGenericSuperclass();
        return ((ParameterizedType)genType).getActualTypeArguments();
    }

    @Override
    public List<T> find(T t) {
        return this.mapper.select(t);
    }

    @Override
    public List<T> findByCondition(Condition condition) {
        return this.mapper.selectByCondition(condition);
    }

    @Override
    public Page<T> page(Condition t, PageInfo pageInfo) {
        Page<T> page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize()).doSelectPage(() -> {
            this.mapper.selectByCondition(t);
        });
        //page.setTotal(page.size());
        return page;
    }

    @Override
    public PageInfo<T> pageInfo(Condition t, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),pageInfo.getTotal()<=0);
        List<T> list = this.mapper.selectByCondition(t);

        return new PageInfo(list);
    }

    @Override
    public T get(String id) {
        return this.mapper.selectByPrimaryKey(id);
    }

    @Override
    public T updateSelective(T t) {
        this.mapper.updateByPrimaryKeySelective(t);
        return t;
    }

    @Override
    public T save(T t) {
        return this.save(t, (Object)null);
    }

    @Override
    public T save(T t, Object operUserId) {
        String id = null;
        boolean insertsign = false;
        Field idField = null;

        try {
            Field[] fields = t.getClass().getDeclaredFields();
            Field[] var7 = fields;
            int var8 = fields.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                Field field = var7[var9];
                if (field.getAnnotation(Id.class) != null) {
                    idField = field;
                    break;
                }
            }

            if (idField == null) {
                return null;
            }

            if (id == null) {
                id = BeanUtils.getProperty(t, idField.getName());
            }

            insertsign = id == null;
            if (!insertsign) {
                insertsign = StringUtils.isEmpty(id);
            }

            if (insertsign) {
                BeanUtils.setProperty(t, idField.getName(), this.uuidGenerator.gain());
            }
        } catch (IllegalAccessException var11) {
            log.error("保存时发生错误", var11);
            throw new RuntimeException("保存失败", var11);
        } catch (InvocationTargetException var12) {
            log.error("保存时发生错误" + var12.getMessage(), var12);
            throw new RuntimeException("保存失败", var12);
        } catch (NoSuchMethodException var13) {
            log.error("保存时发生错误" + var13.getMessage(), var13);
            throw new RuntimeException("保存失败", var13);
        }
        if (operUserId != null) {
            setOperInfo(t, operUserId, insertsign);
        }
        if (insertsign) {
            this.mapper.insert(t);
        } else {
            this.mapper.updateByPrimaryKeySelective(t);
        }
        return t;
    }

    @Override
    public List<T> batchInsert(List<T> t) {
        return batchInsert(t,null);
    }

    @Override
    public List<T> batchInsert(List<T> list, Object operUserId) {
        Field idField = null;
        for(T t:list){
            try {
                if (idField == null) {
                    Field[] fields = t.getClass().getDeclaredFields();
                    Field[] var7 = fields;
                    int var8 = fields.length;

                    for(int var9 = 0; var9 < var8; ++var9) {
                        Field field = var7[var9];
                        if (field.getAnnotation(Id.class) != null) {
                            idField = field;
                            break;
                        }
                    }
                }
                if (idField == null) {
                    return  null;
                }
                BeanUtils.setProperty(t, idField.getName(), this.uuidGenerator.gain());
            } catch (IllegalAccessException var11) {
                log.error("保存时发生错误", var11);
                throw new RuntimeException("保存失败", var11);
            } catch (InvocationTargetException var12) {
                log.error("保存时发生错误" + var12.getMessage(), var12);
                throw new RuntimeException("保存失败", var12);
            }

            if (operUserId != null) {
                setOperInfo(t, operUserId, true);
            }
        }

        this.mapper.insertList(list);
        return list;
    }

    public static void setOperInfo(Object t, Object operId, boolean buildCreateInfo) {
        try {
            if (buildCreateInfo) {
                BeanUtils.setProperty(t, "createUser", operId);
                BeanUtils.setProperty(t, "createTime", new Date());
                BeanUtils.setProperty(t, "delFlg", Integer.valueOf(0));
            }

            BeanUtils.setProperty(t, "updateUser", operId);
            BeanUtils.setProperty(t, "updateTime", new Date());
        } catch (IllegalAccessException var4) {
            log.warn("设置操作日志信息时发生错误", var4);
        } catch (InvocationTargetException var5) {
            log.warn("设置操作日志信息时发生错误", var5);
        }
    }

    @Override
    public void delete(T t) {
        try {
            BeanUtils.setProperty(t, "delFlag", Integer.valueOf(1));
        } catch (Exception var3) {
            log.error("逻辑删除失败：", var3);
        }
        this.mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public Condition newCondition() {
        Class<T> tClass = getClz();
        if(tClass == null) {
            return null;
        }
        return new Condition(tClass);
    }

    /**
     * 创建一个Class的对象来获取泛型的class
     */
    private Class<T> clz;

    @SuppressWarnings("unchecked")
    public Class<T> getClz(){
        if (clz==null) {
            clz=(Class<T>)(((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        }
        return clz;
    }
}

