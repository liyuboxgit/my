/**
 * 
 */
package liyu.test.springbootMybatis.mybatis.jdbc;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import liyu.test.springbootMybatis.mybatis.BaseEntity;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
/**
 * @author Administrator
 * Add on method with jdbcTemplate join query 
 */
public @interface Cached {
	Class<? extends BaseEntity>[] tableClasses();
}
