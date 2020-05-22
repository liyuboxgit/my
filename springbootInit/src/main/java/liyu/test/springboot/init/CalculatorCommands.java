package liyu.test.springboot.init;

import liyu.test.springboot.init.service.ShellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CalculatorCommands {
    @Autowired
    private ShellService shellService;
    @ShellMethod("计算两个整数的加法")
    public int add(int a, int b){
        return shellService.add(a,b);
    }

    @ShellMethod("计算两个整数的减法")
    public int minus(int a, int b) {
        return a - b;
    }

    @ShellMethod("计算两个整数的乘法")
    public int multiply(int a, int b) {
        return a * b;
    }

    @ShellMethod("计算两个整数的除法")
    public int div(int a, int b) {
        return a/b;
    }
}
