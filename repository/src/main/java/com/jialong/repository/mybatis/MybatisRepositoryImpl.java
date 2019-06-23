package com.jialong.repository.mybatis;


import com.jialong.repository.ReadRepository;
import com.jialong.repository.WriteRepository;
import com.jialong.repository.domain.paging.PagedList;
import com.jialong.repository.domain.paging.PagedListImpl;
import com.jialong.repository.domain.queryFilters.GetQueryFilter;
import com.jialong.repository.domain.queryFilters.ListQueryFilter;
import com.jialong.repository.domain.queryFilters.PagedQueryFilter;
import com.jialong.repository.domain.queryFilters.QueryFilter;
import com.jialong.repository.mybatis.entitytypeconfigurations.ColumnType;
import com.jialong.repository.mybatis.entitytypeconfigurations.EntityPropertyConfiguration;
import com.jialong.repository.mybatis.entitytypeconfigurations.EntityTypeConfiguration;
import com.jialong.repository.mybatis.entitytypeconfigurations.EntityTypeConfigurationContext;
import com.jialong.repository.mybatis.reflection.factory.ObjectWrapper;
import org.mybatis.spring.SqlSessionTemplate;

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
    protected String SelectCountSuffix = "_Count";

    /**
     * 获取xml节点的完整Id路径
     *
     * @param xmlId xml节点的Id
     * @return xml节点的完整Id路径，包括namaspace
     */
    protected String getSM(String xmlId) {
        return this.clazz.getTypeName() + "." + xmlId;
    }

    protected <ReturnType> String getSM(QueryFilter<ReturnType> filter) {
        return this.getSM(filter.getFilterKey());
    }

    public MybatisRepositoryImpl(SqlSessionTemplate sqlSessionTemplate, Class<Entity> clazz) {
        this.sqlSessionTemplate = sqlSessionTemplate;
        this.clazz = clazz;
        this.entityTypeConfigurationContext = EntityTypeConfigurationContext.INSTANCE;
        this.onEntityTypeConfigurationCreating(this.entityTypeConfigurationContext);
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
        changedFields.put(property.getName(), id);
        return this.sqlSessionTemplate.update(this.getSM("Update"), changedFields);
    }

    @Override
    public Object insert(Entity t) {
        return this.sqlSessionTemplate.insert(this.getSM("Insert"), t);
    }

    @Override
    public int delete(Entity t) {
        return this.sqlSessionTemplate.delete(this.getSM("Delete"), t);
    }

    @Override
    public abstract void onEntityTypeConfigurationCreating(EntityTypeConfigurationContext<Entity> context);

    //endregion

    //region IReadRepository

    @Override
    public <E> Entity get(E id) {
        return this.sqlSessionTemplate.selectOne(this.getSM("Get"), id);
    }

    /**
     * 获取单个数据
     */
    @Override
    public <ReturnType> ReturnType get(GetQueryFilter<ReturnType> filter) {
        return this.sqlSessionTemplate.selectOne(this.getSM(this.getSM(filter) + clazz.getSimpleName()), filter);
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