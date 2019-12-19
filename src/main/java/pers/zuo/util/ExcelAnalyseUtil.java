package pers.zuo.util;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pers.zuo.entity.dto.BusinessException;
import pers.zuo.entity.dto.ExcelBaseDto.Validator;
import pers.zuo.util.TupleUtil.Tuple3;

import java.util.*;

/**
 * @author zuojingang
 * @Title: ExcelAnalyseUtil
 * @Description: Excel工具类，检查并返回数据
 * @date 2019-12-16 19:13
 */
public class ExcelAnalyseUtil {

    public static Map<Integer, Map<Integer, String>> analyse(MultipartFile file) {
        Map<Integer, Map<Integer, String>> excelData = new HashMap<>();
        if (excelData.size() > 1001){
            throw new BusinessException("每次导入数据, 不能超过1000条!");
        }
        if (MapUtils.isEmpty(excelData)) {
            throw new BusinessException("文件无数据!");
        }
        return excelData;
    }

    /**
     * Header Demo:
     * {
     *     {
     *         "headerName": "CF EventCode"
     *     }:{
     *         "fieldName": "eventCode",
     *         "required": true,
     *         "validate": ()-> {}
     *     },
     *      …
     * }
     * @return
     */
    public static <T> List<T> analyse(MultipartFile file, Class<T> targetClass, Map<String, Tuple3<String, Boolean, Validator>> header){
        Map<Integer, Map<Integer, String>> excelData = analyse(file);
        List<T> list = null;
        try {
            list = excelData2List(excelData, targetClass, header);
        } catch (Exception e) {
            throw e instanceof BusinessException? (BusinessException)e: new BusinessException(e.getMessage(), e.getCause());
        }
        return list;
    }

    /**
     *
     * @param excelData
     * @param targetClass
     * @param header key is the display of header, value is bean fieldName
     * @param <T>
     * @return
     */
    public static <T> List<T> excelData2List(Map<Integer, Map<Integer, String>> excelData, Class<T> targetClass, Map<String, Tuple3<String, Boolean, Validator>> header){
        List<T> beanList = new ArrayList<>();
        if (MapUtils.isEmpty(excelData)){
            return beanList;
        }
        //Because of analysis excel always start from 1, so index 0 of list be free
        List<Tuple3<String, Boolean, Validator>> beanFields = ListUtil.nullList(header.size()+1);
        for (Map.Entry<Integer, Map<Integer, String>> entry: excelData.entrySet()){

            Integer lineNumber = entry.getKey();
            Map<Integer, String> rowData = entry.getValue();
            // The first line is header
            if (1 == lineNumber){

                for (Map.Entry<Integer, String> headerEntry : rowData.entrySet()) {

                    Integer columnNo = headerEntry.getKey();
                    String cellValue = Optional.ofNullable(headerEntry.getValue()).orElse("").trim();
                    Tuple3<String, Boolean, Validator> fieldNameTuple = header.get(cellValue);
                    if (Objects.isNull(fieldNameTuple)){
                        throw new RuntimeException(String.format("Excel 解析异常，非法表头：%s！", cellValue));
                    }
                    beanFields.set(columnNo, fieldNameTuple);
                }
                continue;
            }
            // Process the body rows
            T bean = ReflectUtils.getInstance(targetClass);
            Map<Tuple3<String, Boolean, Validator>, String> fieldTupleValMap = new HashMap<>();
            for (Map.Entry<Integer, String> rowEntry : rowData.entrySet()) {

                Integer columnNo = rowEntry.getKey();
                String cellValue = Optional.ofNullable(rowEntry.getValue()).orElse("").trim();
                Tuple3<String, Boolean, Validator> fieldNameTuple = beanFields.get(columnNo);
                if (Objects.isNull(fieldNameTuple)){
                    continue;
                }
                fieldTupleValMap.put(fieldNameTuple, cellValue);
                String fieldName = fieldNameTuple.first;
                ReflectUtils.setFieldVal(bean, fieldName, cellValue);
            }
            // ignore empty bean(row)
            if (ReflectUtils.isEmpty(bean)){
                continue;
            }
            // validate
            for(Map.Entry<Tuple3<String, Boolean, Validator>, String> fieldTupleValEntry: fieldTupleValMap.entrySet()){
                Tuple3<String, Boolean, Validator> fieldNameTuple = fieldTupleValEntry.getKey();
                String cellValue = fieldTupleValEntry.getValue();
                Boolean required = fieldNameTuple.second;
                Validator validator = fieldNameTuple.third;
                int columnNo = beanFields.indexOf(fieldNameTuple);
                if (required && StringUtils.isBlank(cellValue)){
                    throw new RuntimeException(String.format("第 %s 行，第 %s 列 不能为空！", lineNumber, columnNo));
                }
                if (Objects.nonNull(validator)){
                    validator.validate(lineNumber, columnNo, cellValue);
                }

            }
            ReflectUtils.setFieldVal(bean, "lineNumber", lineNumber);
            beanList.add(bean);
        }
        return beanList;
    }

}
