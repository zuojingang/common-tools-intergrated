//package pers.zuo.util;
//
//import com.sun.javafx.scene.transform.TransformUtils;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.collections.MapUtils;
//import org.apache.commons.lang3.StringUtils;
//import tk.mybatis.mapper.entity.Example;
//
//import javax.validation.constraints.NotNull;
//import java.lang.reflect.ParameterizedType;
//import java.util.*;
//import java.util.concurrent.atomic.AtomicReference;
//
///**
// * @author zuojingang
// * @Title: DaoUtil
// * @Description: Todo
// * @date 2019-09-18 11:34
// */
//public class DaoUtil {
//
//    public static <T, D extends BaseDao<T>> T one(@NotNull D dao) {
//        return oneByEqual(dao, null);
//    }
//
//    public static <T, D extends BaseDao<T>> T oneCustomer(@NotNull D dao, CustomerCondition customerCondition) {
//        return oneByEqualCustomer(dao, null, customerCondition);
//    }
//
//    public static <T, P, D extends BaseDao<T>> T oneByEqual(@NotNull D dao, P paramBean) {
//        List<T> listByExample = listByEqual(dao, paramBean);
//        return ListUtils.first(listByExample);
//    }
//
//    public static <T, P, D extends BaseDao<T>> T oneByEqualCustomer(@NotNull D dao, P paramBean, CustomerCondition customerCondition) {
//        List<T> listByExample = listByEqualCustomer(dao, paramBean, customerCondition);
//        return ListUtils.first(listByExample);
//    }
//
//    public static <T, D extends BaseDao<T>, DTO> List<DTO> dtoList(@NotNull D dao, Class<DTO> dtoClass) {
//        return dtoListByEqual(dao, null, dtoClass);
//    }
//
//    public static <T, D extends BaseDao<T>, DTO> List<DTO> dtoListCustomer(@NotNull D dao, Class<DTO> dtoClass, CustomerCondition customerCondition) {
//        return dtoListByEqualCustomer(dao, null, dtoClass, customerCondition);
//    }
//
//    public static <T, P, D extends BaseDao<T>, DTO> List<DTO> dtoListByEqual(@NotNull D dao, P paramBean, Class<DTO> dtoClass) {
//        List<T> listByExample = listByEqual(dao, paramBean);
//        return TransformUtils.listCopy2(listByExample, dtoClass);
//    }
//
//    public static <T, P, D extends BaseDao<T>, DTO> List<DTO> dtoListByEqualCustomer(@NotNull D dao, P paramBean, Class<DTO> dtoClass, CustomerCondition customerCondition) {
//        List<T> listByExample = listByEqualCustomer(dao, paramBean, customerCondition);
//        return TransformUtils.listCopy2(listByExample, dtoClass);
//    }
//
//    public static <T, P, D extends BaseDao<T>, DTO> List<DTO> dtoListByInAndEqual(@NotNull D dao, String inField, List inList, P paramBean, Class<DTO> dtoClass){
//        List<T> listByInAndEqualOrderBy = listByInAndEqualOrderBy(dao, inField, inList, paramBean, null);
//        return TransformUtils.listCopy2(listByInAndEqualOrderBy, dtoClass);
//    }
//
//    public static <T, D extends BaseDao<T>> List<T> list(@NotNull D dao) {
//        return listByInAndEqualOrderBy(dao, null, null, null, null);
//    }
//
//    public static <T, D extends BaseDao<T>> List<T> listCustomer(@NotNull D dao, CustomerCondition customerCondition) {
//        return listByInAndEqualOrderByCustomer(dao, null, null, null, null, customerCondition);
//    }
//
//    public static <T, D extends BaseDao<T>> PageInfo<T> page(@NotNull D dao, @NotNull PageCondition pageCondition) {
//        return pageByInAndEqualOrderBy(dao, null, null, null, null, pageCondition);
//    }
//
//    public static <T, D extends BaseDao<T>> List<T> listOrderBy(@NotNull D dao, Map<List<String>, String> orderBy){
//        return listByInAndEqualOrderBy(dao, null, null, null, orderBy);
//    }
//
//    public static <T, D extends BaseDao<T>> List<T> listOrderByCustomer(@NotNull D dao, Map<List<String>, String> orderBy, CustomerCondition customerCondition){
//        return listByInAndEqualOrderByCustomer(dao, null, null, null, orderBy, customerCondition);
//    }
//
//    public static <T, D extends BaseDao<T>> PageInfo<T> pageOrderBy(@NotNull D dao, Map<List<String>, String> orderBy, @NotNull PageCondition pageCondition){
//        return pageByInAndEqualOrderBy(dao, null, null, null, orderBy, pageCondition);
//    }
//
//    public static <T, P, D extends BaseDao<T>> List<T> listByEqual(@NotNull D dao, P paramBean) {
//        return listByInAndEqualOrderBy(dao, null, null, paramBean, null);
//    }
//
//    public static <T, P, D extends BaseDao<T>> List<T> listByEqualCustomer(@NotNull D dao, P paramBean, CustomerCondition customerCondition) {
//        return listByInAndEqualOrderByCustomer(dao, null, null, paramBean, null, customerCondition);
//    }
//
//    public static <T, P, D extends BaseDao<T>> PageInfo<T> pageByEqual(@NotNull D dao, P paramBean, @NotNull PageCondition pageConditionn) {
//        return pageByInAndEqualOrderBy(dao, null, null, paramBean, null, pageConditionn);
//    }
//
//    public static <T, P, D extends BaseDao<T>> List<T> listByEqualOrderBy(@NotNull D dao, P paramBean, Map<List<String>, String> orderBy){
//        return listByInAndEqualOrderBy(dao, null, null, paramBean, orderBy);
//    }
//
//    public static <T, P, D extends BaseDao<T>> List<T> listByEqualOrderByCustomer(@NotNull D dao, P paramBean, Map<List<String>, String> orderBy, CustomerCondition customerCondition){
//        return listByInAndEqualOrderByCustomer(dao, null, null, paramBean, orderBy, customerCondition);
//    }
//
//    public static <T, P, D extends BaseDao<T>> PageInfo<T> pageByEqualOrderBy(@NotNull D dao, P paramBean, Map<List<String>, String> orderBy, @NotNull PageCondition pageCondition){
//        return pageByInAndEqualOrderBy(dao, null, null, paramBean, orderBy, pageCondition);
//    }
//
//    public static <T, P, D extends BaseDao<T>> List<T> listByIn(@NotNull D dao, String inField, List inList){
//        return listByInAndEqualOrderBy(dao, inField, inList, null, null);
//    }
//
//    public static <T, P, D extends BaseDao<T>> List<T> listByInCustomer(@NotNull D dao, String inField, List inList, CustomerCondition customerCondition){
//        return listByInAndEqualOrderByCustomer(dao, inField, inList, null, null, customerCondition);
//    }
//
//    public static <T, P, D extends BaseDao<T>> PageInfo<T> pageByIn(@NotNull D dao, String inField, List inList,@NotNull PageCondition pageCondition){
//        return pageByInAndEqualOrderBy(dao, inField, inList, null, null, pageCondition);
//    }
//
//    public static <T, P, D extends BaseDao<T>> List<T> listByInOrderBy(@NotNull D dao, String inField, List inList, Map<List<String>, String> orderBy){
//        return listByInAndEqualOrderBy(dao, inField, inList, null, orderBy);
//    }
//
//    public static <T, P, D extends BaseDao<T>> List<T> listByInOrderByCustomer(@NotNull D dao, String inField, List inList, Map<List<String>, String> orderBy, CustomerCondition customerCondition){
//        return listByInAndEqualOrderByCustomer(dao, inField, inList, null, orderBy, customerCondition);
//    }
//
//    public static <T, P, D extends BaseDao<T>> PageInfo<T> pageByInOrderBy(@NotNull D dao, String inField, List inList, Map<List<String>, String> orderBy,@NotNull PageCondition pageCondition){
//        return pageByInAndEqualOrderBy(dao, inField, inList, null, orderBy, pageCondition);
//    }
//
//    public static <T, P, D extends BaseDao<T>> List<T> listByInAndEqual(@NotNull D dao, String inField, List inList, P paramBean){
//        return listByInAndEqualOrderBy(dao, inField, inList, paramBean, null);
//    }
//
//    public static <T, P, D extends BaseDao<T>> List<T> listByInAndEqualCustomer(@NotNull D dao, String inField, List inList, P paramBean, CustomerCondition customerCondition){
//        return listByInAndEqualOrderByCustomer(dao, inField, inList, paramBean, null, customerCondition);
//    }
//
//    public static <T, P, D extends BaseDao<T>> PageInfo<T> pageByInAndEqual(@NotNull D dao, String inField, List inList, P paramBean,@NotNull PageCondition pageCondition){
//        return pageByInAndEqualOrderBy(dao, inField, inList, paramBean, null, pageCondition);
//    }
//
//    public static <T, P, D extends BaseDao<T>> List<T> listByInAndEqualOrderBy(@NotNull D dao, String inField, List inList, P paramBean, Map<List<String>, String> orderBy){
//        return listByInAndEqualOrderByCustomer(dao, inField, inList, paramBean, orderBy, null);
//    }
//
//    public static <T, P, D extends BaseDao<T>> List<T> listByInAndEqualOrderByCustomer(@NotNull D dao, String inField, List inList, P paramBean, Map<List<String>, String> orderBy, CustomerCondition customerCondition) {
//        Example example = composeExample(dao, inField, inList, paramBean, orderBy, customerCondition);
//        return dao.selectByExample(example);
//    }
//
//    public static <T, P, D extends BaseDao<T>> PageInfo<T> pageByInAndEqualOrderBy(@NotNull D dao, String inField, List inList, P paramBean, Map<List<String>, String> orderBy, @NotNull PageCondition pageCondition){
//        Example example = composeExample(dao, inField, inList, paramBean, orderBy, pageCondition);
//        return pageCondition.queryPage(dao, example);
//    }
//
//    /**
//     *
//     * @param <T> DAO对应的实体类
//     * @param <P> 进行条件查询是，相等条件的包装类
//     * @param <D> 实际操作的DAO类
//     * @param dao 要操作的DAO
//     * @param inField in条件的属性
//     * @param inList in条件的值列表
//     * @param paramBean 相等条件实体类
//     * @param orderBy 排序Map，key为字段列表，value为排序方式（升/降）
//     * @param customerCondition 自定义查询条件
//     * @return
//     */
//
//    private static <T, P, D extends BaseDao<T>> Example composeExample(@NotNull D dao, String inField, List inList, P paramBean, Map<List<String>, String> orderBy, CustomerCondition customerCondition) {
//        Class<T> poClazz = Class.class.cast(ParameterizedType.class.cast(Class.class.cast(dao.getClass().getGenericInterfaces()[0]).getGenericInterfaces()[0]).getActualTypeArguments()[0]);
//        Example example = new Example(poClazz);
//        Example.Criteria criteria = example.createCriteria();
//        if (StringUtils.isNotBlank(inField) && org.apache.commons.collections.CollectionUtils.isNotEmpty(inList)){
//            criteria.andIn(inField, inList);
//        }
//        if (Objects.nonNull(paramBean)){
//            Map<String, Object> paramMap = beanNonNullField2Map(paramBean);
//            if (org.apache.commons.collections.MapUtils.isNotEmpty(paramMap)){
//                criteria.andEqualTo(paramMap);
//            }
//        }
//        criteria.andEqualTo("active", ApplicationConstants.ACTIVE);
//
//        if (MapUtils.isNotEmpty(orderBy)){
//            Iterator<Map.Entry<List<String>, String>> iterator = orderBy.entrySet().iterator();
//            while (iterator.hasNext()){
//                Map.Entry<List<String>, String> next = iterator.next();
//                List<String> fieldList = next.getKey();
//                if (CollectionUtils.isEmpty(fieldList)){
//                    continue;
//                }
//                final AtomicReference<Example.OrderBy> orderReference = new AtomicReference<>(null);
//                fieldList.forEach(field-> orderReference.set(example.orderBy(field)));
//                Example.OrderBy order = orderReference.get();
//                if (Objects.isNull(order)){
//                    continue;
//                }
//                if ("desc".equals(next.getValue())){
//                    order.desc();
//                    continue;
//                }
//                order.asc();
//            }
//        }
//        if (Objects.nonNull(customerCondition)) {
//            customerCondition.definition(example, criteria);
//        }
//        return example;
//    }
//
//    /**
//     * provides for users custom query criteria
//     */
//    public interface CustomerCondition {
//
//        void definition(Example example, Example.Criteria criteria);
//    }
//
//    public static class PageCondition implements CustomerCondition{
//
//        public final int page;
//        public final int limit;
//
//        public static PageCondition of(int page, int limit) {
//            return new PageCondition(page, limit);
//        }
//
//        public PageCondition(int page, int limit) {
//            this.page = page;
//            this.limit = limit;
//        }
//
//        public <T> PageInfo<T> queryPage(BaseDao<T> dao, Example example) {
//            PageHelper.startPage(page, limit);
//            List<T> list = dao.selectByExample(example);
//            return PageInfo.of(list);
//        }
//
//        @Override
//        public void definition(Example example, Example.Criteria criteria) {
//
//        }
//    }
//
//    /**
//     * define simple common sort criteria
//     */
//    public static class Order {
//
//        public static final Map<List<String>, String> ASC_CREATE_TIME = asc(FieldName.CREATE_TIME);
//        public static final Map<List<String>, String> DESC_CREATE_TIME = desc(FieldName.CREATE_TIME);
//        public static final Map<List<String>, String> ASC_MODIFY_TIME = asc(FieldName.MODIFY_TIME);
//        public static final Map<List<String>, String> DESC_MODIFY_TIME = desc(FieldName.MODIFY_TIME);
//
//        public static Map<List<String>, String> asc(String... fields) {
//            if (0 == fields.length){
//                return null;
//            }
//            return ImmutableMap.of(Arrays.asList(fields), "asc");
//        }
//
//        public static Map<List<String>, String> desc(String... fields) {
//            if (0 == fields.length){
//                return null;
//            }
//            return ImmutableMap.of(Arrays.asList(fields), "desc");
//        }
//    }
//
//    public static class LikeWrapper {
//
//        public static String bothEndsLike(String val) {
//            return "%" + val + "%";
//        }
//
//        public static String fixedStartLike(String val) {
//            return val + "%";
//        }
//
//        public static String fixedEndLike(String val) {
//            return "%" + val;
//        }
//    }
//
//}
