package com.znn.shiro.shiro.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro 配置类
 *
 * 关于Configuration的讲解，可参考一下博客：https://www.cnblogs.com/WUXIAOCHANG/p/10877266.html
 */
@Configuration
public class ShiroConfig {

    /*
    * 创建ShiroFilterFactoryBean
    * */
    @Bean  //一个接口有多个实现类，@Qualifier指明@Autowired具体注入哪个实现类
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier(value = "securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //添加Shiro内置管理器
        /**
         * Shiro内置过滤器，可以实现权限相关的拦截器
         *      常用的过滤器
         *      anon：无需认证（登录）即可访问
         *      authc：必须认证才可访问
         *      user：如果使用rememberMe的功能可以直接访问
         *      perms：该资源必须得到资源权限才可访问
         *      role：该资源必须得到角色权限才可访问
         */
        Map<String,String> filterMap = new LinkedHashMap<String,String>();
        filterMap.put("/testThymeLeaf","anon");
        //放行login.html页面
        filterMap.put("/login","anon");

        //授权过滤器
        //注意：当前授权拦截后，shiro会自动跳转到未授权页面
        //perms[]中的内容是权限值
        /**
         * 权限标识符号规则：【中间用“:”（半角冒号分隔）】
         *         |---资源:操作【user:create：表示对用户资源进行create操作】【等价于：user:create:*（对所有的用户实例进行操作）】
         *         |---资源:操作:实例【user:create:01：表示对用户资源的01实例进行create操作】
         *         |
         *         |---例子：【user:*:01表示对用户资源的01实例进行所有操作】
         * ————————————————
         * 版权声明：本文为CSDN博主「老猫烧须」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
         * 原文链接：https://blog.csdn.net/nthack5730/article/details/51019451
         */
        filterMap.put("/add","perms[user:add]");
        filterMap.put("/update","perms[user:update]");
        filterMap.put("/*","authc");

        //修改调整的登录页
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        //设置未授权的提示页
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;

    }

    /**
     * 创建DefaultWebSecurityManager类
     *
     * 里面主要定义了登录、创建subject、登出等操作
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier(value = "userRealm")UserRealm userRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }

    /**
     * 创建realm
     */
    @Bean(name="userRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }

    /**
     * 配置ShiroDialect，用于Thymeleaf和shiro标签配合使用
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }




}
