package com.jialong.repository.mybatis;


import com.jialong.repository.ReadRepository;
import com.jialong.repository.WriteRepository;
import com.jialong.repository.domain.paging.PagedList;
import com.jialong.repository.domain.paging.PagedListImpl;
import com.jialong.repository.domain.queryFilters.*;
import com.jialong.repository.mybatis.entitytypeconfigurations.ColumnType;
import com.jialong.repository.mybatis.entitytypeconfigurations.EntityPropertyConfiguration;
import com.jialong.repository.mybatis.entitytypeconfigurations.EntityTypeConfiguration;
import com.jialong.repository.mybatis.entitytypeconfigurations.EntityTypeConfigurationContext;
import com.jialong.repository.mybatis.reflection.factory.ObjectWrapper;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 陈家龙 on 2017/4/10.
 */
public abstract class MybatisRepositoryImpl<Entity> implements WriteRepository<Entity>, ReadRepository<Entity> {
    protected SqlSessionTemplate sqlSessionTemplate;
    protected Class<Entity> clazz;
    protected EntityTypeConfigurationContext entityTypeConfigurationContext;
    /**
     * 查询记录总数的Key的后缀
     */
    protected String SelectCountSuffix = "_count";

    /**
     * 获取xml节点的完整Id路径
     *
     * @param xmlId xml节点的Id
     * @return xml节点的完整Id路径，包括namaspace
     */
    protected String getSM(String xmlId) {
        return this.clazz.getTypeName() + "." + xmlId;
    }

    /**
     * 获取xml节点的完整Id路径
     *
     * @param filter
     * @param <ReturnType>
     * @return
     */
    protected <ReturnType> String getSM(QueryFilter<ReturnType> filter) {
        return this.getSM(filter.getFilterKey());
    }

    public MybatisRepositoryImpl(SqlSessionTemplate sqlSessionTemplate, Class<Entity> clazz) {
        this.sqlSessionTemplate = sqlSessionTemplate;
        this.clazz = clazz;
        this.entityTypeConfigurationContext = EntityTypeConfigurationContext.INSTANCE;
        this.entityTypeConfigurationCreating();
    }

    //region IWriteRepository

    @Override
    public int update(Entity t) {
        Map<String, Object> changedFields = ObjectWrapper.determineChangedFields(t);
        if (changedFields.isEmpty())
            return 0;
        EntityTypeConfiguration<Entity> entityTypeConfiguration = this.entityTypeConfigurationContext.entity(this.clazz);
        EntityPropertyConfiguration property = entityTypeConfiguration.getProperty(ColumnType.Key);
        if (property == null) {
            return 0;
        }
        Object id = property.getValue(t);
        if (id == null)
            return 0;
        Map<String, Object> sqlParams = new HashMap<>(changedFields);
        sqlParams.put(property.getName(), id);
        int result = this.sqlSessionTemplate.update(this.getSM("update"), sqlParams);
        ObjectWrapper.resetChangedFields(t);
        return result;
    }

    @Override
    public Object insert(Entity t) {
        return this.sqlSessionTemplate.insert(this.getSM("insert"), t);
    }

    @Override
    public int delete(Entity t) {
        return this.sqlSessionTemplate.delete(this.getSM("delete"), t);
    }

    protected void entityTypeConfigurationCreating() {
        EntityTypeConfiguration entityTypeConfiguration = this.entityTypeConfigurationContext.entity(this.clazz);
        this.onEntityTypeConfigurationCreating(entityTypeConfiguration);
    }

    @Override
    public abstract void onEntityTypeConfigurationCreating(EntityTypeConfiguration<Entity> context);

    //endregion

    //region IReadRepository

    @Override
    public <IdType> Entity get(IdType id) {
        GetQueryFilterImpl<IdType, Entity> filter = new GetQueryFilterImpl(id);
        return this.get(filter);
    }

    /**
     * 获取单个数据
     */
    @Override
    public <ReturnType> ReturnType get(GetQueryFilter<ReturnType> filter) {
        return this.sqlSessionTemplate.selectOne(this.getSM(filter), filter);
    }

    @Override
    public <ReturnType> List<ReturnType> find(ListQueryFilter<ReturnType> filter) {
        return this.sqlSessionTemplate.selectList(this.getSM(filter), filter);
    }

    @Override
    public <ReturnType> PagedList<ReturnType> find(PagedQueryFilter<ReturnType> filter) {
        return this.findCore(filter);
    }

    protected <ReturnType> PagedList<ReturnType> findCore(PagedQueryFilter<ReturnType> filter) {
        List<ReturnType> data = this.sqlSessionTemplate.selectList(this.getSM(filter), filter);
        int totalCount = data.size();
        if (filter.getReturnRowCount()) {
            if (totalCount < filter.getPageSize()) {
                //如果当前页的数据行数不足一页，总记录数直接计算出来，不去DB查询了
                totalCount += filter.getPageIndex() * filter.getPageSize();
            } else {
                Number number = this.sqlSessionTemplate.selectOne(this.getSM(filter.getFilterKey() + this.SelectCountSuffix), filter);
                if (number == null) {
                    totalCount = 0;
                } else {
                    totalCount = number.intValue();
                }
            }
        }
        return new PagedListImpl<ReturnType>(data, filter.getPageIndex(), filter.getPageSize(), totalCount);
    }

    //endregion
}