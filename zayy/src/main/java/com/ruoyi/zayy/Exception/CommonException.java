package com.ruoyi.zayy.Exception;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;

@Component
public class CommonException {

    @ExceptionHandler(value = DataAccessException.class)
    @ResponseBody
    public void repeatException(SQLIntegrityConstraintViolationException e) {
        System.out.println("sql异常");
        System.out.println(e);
    }

}
