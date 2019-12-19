package pers.zuo.entity.dto;

/**
 * @author zuojingang
 * @Title: ExcelBase
 * @Description: Todo
 * @date 2019-12-18 23:44
 */
public class ExcelBaseDto {

    private Integer lineNumber;

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public interface Validator{

        boolean validate(Integer lineNumber, Integer columnNo, String fieldVal);
    }
}
