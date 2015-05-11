package cn.itjh.luchat.usertoken.util;

/**
 * Created by Hu on 2015/5/8 0008.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Created by Hu on 2015/5/8 0008.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public  @interface Log {
    String name() default "";
}
