/**
 * 持久化包.项目使用mybatis作为持久化插件，所以该包提供mybatis使用的所有接口以及xml配置文件。<br>
 * 需要遵守的规范：<br>
 * <ul>
 * <li>所有接口以及xml文件的命名以Mapper结尾。</li>
 * <li>所有接口中的方法的参数都需要使用“@Param”注解转义，转义参数的命名规则以数据库命名规范为准。</li>
 * <li>所有的mybatis配置文件中，尽量保证重用。</li>
 * <li>所有的字符串在xml中验证为空字符串时使用如下方式<code>'menuId = ""'</code></li>
 * <li>所有的sql代码都需要能说明含义的大量注释</li>
 * <li>所有的新增方法均需要向传入对象中存入insert后的列主键</li>
 * </ul>
 * 
 * @author 周峰
 * @since v3.0.0
 */
package com.jcci.repository;
