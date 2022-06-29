package com.ajie.servicebase.exceptionhandler;

import com.ajie.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j//将错误日志输出到指定的文件中的注解
public class GlobalExceptionHandler {

//    全局异常
    @ExceptionHandler
    @ResponseBody
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行了全局异常处理。。。。。");
    }

//    特定异常
    @ExceptionHandler
    @ResponseBody
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行力ArithmeticException异常处理。。。。");
    }

//    自定义异常
    @ExceptionHandler
    @ResponseBody
    public R error(SelfException e) {
        log.error(e.getMessage());//将异常信息输出到log_error文件中
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }

}
