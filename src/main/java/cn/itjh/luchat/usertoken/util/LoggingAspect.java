package cn.itjh.luchat.usertoken.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Hu on 2015/5/11 0011.
 */

//把这个类声明为一个切面：需要把该类放入到IOC容器中。再声明为一个切面.
@Order(1)
@Aspect
@Component
public class LoggingAspect {

    /**
     * 声明切入点表达式，一般在该方法中不再添加其他代码。
     * 使用@Pointcut来声明切入点表达式。
     * 后面的通知直接使用方法名来引用当前的切入点表达式。
     */
    @Pointcut("execution(public String cn.itjh.luchat.usertoken.server.UserTokenServer.*(..) && args(request))")
    public void declareJoinPointExpression() {
    }

    /**
     * 前置通知，在目标方法开始之前执行。
     *
     * @param joinPoint
     * @Before("execution(public int com.spring.aop.impl.ArithmeticCalculator.add(int, int))")这样写可以指定特定的方法。
     */
    @Before("execution(public String cn.itjh.luchat.usertoken.server.UserTokenServer.*(..))")
    //这里使用切入点表达式即可。后面的可以都改成切入点表达式。如果这个切入点表达式在别的包中，在前面加上包名和类名即可
    public void beforeMethod(JoinPoint joinPoint) {
        Object retVal = null;
//        String methodName = joinPoint.getSignature().getName();
//        List<Object> args = Arrays.asList(joinPoint.getArgs());
//        System.out.println("前置通知：The method "+ methodName +" begins with " + args);
        HttpServletRequest request = null;
        //logDetail(jointPoint,"：startTime：");


        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof HttpServletRequest) {
                request = (HttpServletRequest) args[i];
            }

        }
        System.out.println("request.getLocalAddr111() -->"+request);
    }

        /**
         *后置通知，在目标方法执行之后开始执行，无论目标方法是否抛出异常。
         *在后置通知中不能访问目标方法执行的结果。
         * @param joinpoint
         */
        @After("execution(public String cn.itjh.luchat.usertoken.server.UserTokenServer.*(..))")
        public void afterMethod (JoinPoint joinpoint){
            String methodName = joinpoint.getSignature().getName();
            //List<Object>args = Arrays.asList(joinpoint.getArgs());  后置通知方法中可以获取到参数
            System.out.println("后置通知：The method " + methodName + " ends ");
        }

        /**
         *返回通知，在方法正常结束之后执行。
         *可以访问到方法的返回值。
         * @param joinpoint
         * @param result 目标方法的返回值
         */
        @AfterReturning(value = "execution(public String cn.itjh.luchat.usertoken.server.UserTokenServer.*(..))", returning = "result")
        public void afterReturnning (JoinPoint joinpoint, Object result){
            String methodName = joinpoint.getSignature().getName();
            System.out.println("返回通知：The method " + methodName + " ends with " + result);
        }

        /**
         *异常通知。目标方法出现异常的时候执行，可以访问到异常对象，可以指定在出现特定异常时才执行。
         *假如把参数写成NullPointerException则只在出现空指针异常的时候执行。
         * @param joinpoint
         * @param e
         */
        @AfterThrowing(value = "execution(public String cn.itjh.luchat.usertoken.server.UserTokenServer.*(..))", throwing = "e")
        public void afterThrowing (JoinPoint joinpoint, Exception e){
            String methodName = joinpoint.getSignature().getName();
            System.out.println("宜昌通知：The method " + methodName + " occurs exception " + e);
        }

        /**
         * 环绕通知类似于动态代理的全过程，ProceedingJoinPoint类型的参数可以决定是否执行目标方法。
         * @param point 环绕通知需要携带ProceedingJoinPoint类型的参数。
         * @return 目标方法的返回值。必须有返回值。
         */
        @Around("execution(public String cn.itjh.luchat.usertoken.server.UserTokenServer.*(..))")
        public Object aroundMethod (ProceedingJoinPoint point){
            Object result = null;
            String methodName = point.getSignature().getName();
            try {
                //前置通知
                System.out.println("The method " + methodName + " begins with " + Arrays.asList(point.getArgs()));
                //执行目标方法
                result = point.proceed();
                //翻译通知
                System.out.println("The method " + methodName + " ends with " + result);
            } catch (Throwable e) {
                //异常通知
                System.out.println("The method " + methodName + " occurs exception " + e);
                throw new RuntimeException(e);
            }
            //后置通知
            System.out.println("The method " + methodName + " ends");
            return result;
        }


    }
