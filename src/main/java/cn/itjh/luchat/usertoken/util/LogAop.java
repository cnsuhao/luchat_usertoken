package cn.itjh.luchat.usertoken.util;

/**
 * Created by Hu on 2015/5/8 0008.
 */

import java.lang.reflect.Method;
import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LogAop {
    ThreadLocal<Long> time = new ThreadLocal<Long>();
    ThreadLocal<String> tag = new ThreadLocal<String>();

    /**
     * 在所有标注@Log的地方切入 前置通知
     *
     * @param joinPoint
     */
    @Before("@annotation(cn.itjh.luchat.usertoken.util.Log)")
    public void beforeExec(JoinPoint joinPoint) {

        time.set(System.currentTimeMillis());
        tag.set(UUID.randomUUID().toString());

        info(joinPoint);

        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();
        System.out.println(method.getAnnotation(Log.class).name() + "标记" + tag.get());
    }

    /**
     * 返回后通知
     *
     * @param joinPoint
     */
    @After("@annotation(cn.itjh.luchat.usertoken.util.Log)")
    public void afterExec(JoinPoint joinPoint) {
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();
        System.out.println("标记为" + tag.get() + "的方法" + method.getName() + "运行消耗" + (System.currentTimeMillis() - time.get()) + "ms");
    }

    /**
     * 环绕通知
     *
     * @param pjp
     * @throws Throwable
     */
    @Around("@annotation(cn.itjh.luchat.usertoken.util.Log)")
    public Object  aroundExec(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        System.out.println("我是Around，来打酱油的");
        result = pjp.proceed();
        return result;
    }

    private void info(JoinPoint joinPoint) {




        System.out.println("--------------------------------------------------");
        System.out.println("King:\t" + joinPoint.getKind());
        System.out.println("Target:\t" + joinPoint.getTarget().toString());
        Object[] os = joinPoint.getArgs();
        System.out.println("Args:");
        for (int i = 0; i < os.length; i++) {
            System.out.println("\t==>参数[" + i + "]:\t" + os[i].toString());
        }
        System.out.println("Signature:\t" + joinPoint.getSignature());
        System.out.println("SourceLocation:\t" + joinPoint.getSourceLocation());
        System.out.println("StaticPart:\t" + joinPoint.getStaticPart());
        System.out.println("--------------------------------------------------");
    }

}
