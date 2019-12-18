//package pers.zuo.util;
//
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.collections4.MapUtils;
//import org.apache.commons.lang3.StringUtils;
//import tk.mybatis.mapper.entity.Example;
//
//import javax.validation.constraints.NotNull;
//import java.lang.reflect.ParameterizedType;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
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
//    public static <T, D extends BaseDao<T>> T oneByEqual(@NotNull D dao, Map paramMap) {
//        List<T> listByExample = listByEqual(dao, paramMap);
//        return ListUtil.first(listByExample);
//    }
//
//    public static <T, D extends BaseDao<T>> T oneByEqualCustomer(@NotNull D dao, Map paramMap, CustomerCondition customerCondition) {
//        List<T> listByExample = listByEqualCustomer(dao, paramMap, customerCondition);
//        return ListUtil.first(listByExample);
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
//    public static <T, D extends BaseDao<T>, DTO> List<DTO> dtoListByEqual(@NotNull D dao, Map<String, Object> paramMap, Class<DTO> dtoClass) {
//        List<T> listByExample = listByEqual(dao, paramMap);
//        return TransformUtil.listCopy2(listByExample, dtoClass);
//    }
//
//    public static <T, D extends BaseDao<T>, DTO> List<DTO> dtoListByEqualCustomer(@NotNull D dao, Map<String, Object> paramMap, Class<DTO> dtoClass, CustomerCondition customerCondition) {
//        List<T> listByExample = listByEqualCustomer(dao, paramMap, customerCondition);
//        return TransformUtil.listCopy2(listByExample, dtoClass);
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
//    public static <T, D extends BaseDao<T>> List<T> listOrderBy(@NotNull D dao, Map<String, String> orderBy){
//        return listByInAndEqualOrderBy(dao, null, null, null, orderBy);
//    }
//
//    public static <T, D extends BaseDao<T>> List<T> listOrderByCustomer(@NotNull D dao, Map<String, String> orderBy, CustomerCondition customerCondition){
//        return listByInAndEqualOrderByCustomer(dao, null, null, null, orderBy, customerCondition);
//    }
//
//    public static <T, D extends BaseDao<T>> List<T> listByEqual(@NotNull D dao, Map<String, Object> paramMap) {
//        return listByInAndEqualOrderBy(dao, null, null, paramMap, null);
//    }
//
//    public static <T, D extends BaseDao<T>> List<T> listByEqualCustomer(@NotNull D dao, Map<String, Object> paramMap, CustomerCondition customerCondition) {
//        return listByInAndEqualOrderByCustomer(dao, null, null, paramMap, null, customerCondition);
//    }
//
//    public static <T, D extends BaseDao<T>> List<T> listByEqualOrderBy(@NotNull D dao, Map<String, Object> paramMap, Map<String, String> orderBy){
//        return listByInAndEqualOrderBy(dao, null, null, paramMap, orderBy);
//    }
//
//    public static <T, D extends BaseDao<T>> List<T> listByEqualOrderByCustomer(@NotNull D dao, Map<String, Object> paramMap, Map<String, String> orderBy, CustomerCondition customerCondition){
//        return listByInAndEqualOrderByCustomer(dao, null, null, paramMap, orderBy, customerCondition);
//    }
//
//    public static <T, D extends BaseDao<T>> List<T> listByIn(@NotNull D dao, String inField, List inList){
//        return listByInAndEqualOrderBy(dao, inField, inList, null, null);
//    }
//
//    public static <T, D extends BaseDao<T>> List<T> listByInCustomer(@NotNull D dao, String inField, List inList, CustomerCondition customerCondition){
//        return listByInAndEqualOrderByCustomer(dao, inField, inList, null, null, customerCondition);
//    }
//
//    public static <T, D extends BaseDao<T>> List<T> listByInOrderBy(@NotNull D dao, String inField, List inList, Map<String, String> orderBy){
//        return listByInAndEqualOrderBy(dao, inField, inList, null, orderBy);
//    }
//
//    public static <T, D extends BaseDao<T>> List<T> listByInOrderByCustomer(@NotNull D dao, String inField, List inList, Map<String, String> orderBy, CustomerCondition customerCondition){
//        return listByInAndEqualOrderByCustomer(dao, inField, inList, null, orderBy, customerCondition);
//    }
//
//    public static <T, D extends BaseDao<T>> List<T> listByInAndEqual(@NotNull D dao, String inField, List inList, Map<String, Object> paramMap){
//        return listByInAndEqualOrderBy(dao, inField, inList, paramMap, null);
//    }
//
//    public static <T, D extends BaseDao<T>> List<T> listByInAndEqualCustomer(@NotNull D dao, String inField, List inList, Map<String, Object> paramMap, CustomerCondition customerCondition){
//        return listByInAndEqualOrderByCustomer(dao, inField, inList, paramMap, null, customerCondition);
//    }
//
//    public static <T, D extends BaseDao<T>> List<T> listByInAndEqualOrderBy(@NotNull D dao, String inField, List inList, Map<String, Object> paramMap, Map<String, String> orderBy){
//        return listByInAndEqualOrderByCustomer(dao, inField, inList, paramMap, orderBy, null);
//    }
//
//    public static <T, D extends BaseDao<T>> List<T> listByInAndEqualOrderByCustomer(@NotNull D dao, String inField, List inList, Map<String, Object> paramMap, Map<String, String> orderBy, CustomerCondition customerCondition){
//        Class<T> poClazz = Class.class.cast(ParameterizedType.class.cast(Class.class.cast(dao.getClass().getGenericInterfaces()[0]).getGenericInterfaces()[0]).getActualTypeArguments()[0]);
//        Example example = new Example(poClazz);
//        Example.Criteria criteria = example.createCriteria();
//        if (StringUtils.isNotBlank(inField) && CollectionUtils.isNotEmpty(inList)){
//            criteria.andIn(inField, inList);
//        }
//        if (MapUtils.isNotEmpty(paramMap)){
//            criteria.andEqualTo(paramMap);
//        }
//        criteria.andEqualTo("active", ApplicationConstants.ACTIVE);
//
//        if (MapUtils.isNotEmpty(orderBy)){
//            Iterator<Map.Entry<String, String>> iterator = orderBy.entrySet().iterator();
//            while (iterator.hasNext()){
//                Map.Entry<String, String> next = iterator.next();
//                Example.OrderBy order = example.orderBy(next.getKey());
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
//        return dao.selectByExample(example);
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
//}
