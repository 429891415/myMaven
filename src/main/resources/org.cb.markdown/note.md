# 第一个MyBatis程序：  
+ mybatis.jar  
+ jdbc.jar  
+ conf.xml (数据库配置信息、映射文件)  
+ 表-类：映射文件  mapper.xml  
+ 测试  

## 一、基础方式的增删改查CRUD:
+ mybatis约定：输入参数parameterType 和 输出参数resultType ，在形式上都只能有一个

+ 如果输入参数 ：是简单类型（8个基本类型+String） 是可以使用任何占位符,`#{xxxx}`  
	       如果是对象类型，则必须是对象的属性 #{属性名}

+ 输出参数：  如果返回值类型是一个 对象（如Student），则无论返回一个、还是多个，
		再resultType都写成`org.lanqiao.entity.Student`  
		即 `resultType="org.lanqiao.entity.Student"`

### 注意事项：  
+ 如果使用的 事务方式为 jdbc,则需要 手工commit提交，即session.commit();

+ 所有的标签 <select> <update>等 ，都必须有sql语句，但是sql参数值可选  
`select* from student  where stuno = #{xx}`  
+ sql有参数：`session.insert(statement, 参数值 );`

+ sql没参数：`session.insert(statement);`

## 二、mapper动态代理方式的crud （MyBatis接口开发）:  
原则：约定优于配置 

+ 硬编码方式
	abc.java  
	
		Configuration conf = new Configuration();  
		con.setName("myProject") ;

+ 配置方式：
	abc.xml    
		`<name>myProject</name>`

+ 约定：默认值就是myProject


+ 具体实现的步骤：  
    1.基础环境：mybatis.jar/ojdbc.jar、conf.xml、mapper.xml  
  
    2.(不同之处)  
	约定的目标： 省略掉statement,即根据约定 直接可以定位出SQL语句

  a.接口，接口中的方法必须遵循以下约定：  
		 *1.方法名和mapper.xml文件中标签的id值相同  
		 *2.方法的 输入参数 和mapper.xml文件中标签的 parameterType类型一致 (如果mapper.xml的标签中没有   
parameterType，则说明方法没有输入参数)  
		 * 3.方法的返回值  和mapper.xml文件中标签的 resultType类型一致 （无论查询结果是一个 还是多个  
（student、List<Student>），在mapper.xml标签中的resultType中只写 一个（Student）；如果没有resultType，则说明方法
的返回值为void）    

  除了以上约定，要实现 接口中的方法  和  Mapper.xml中SQL标签一一对应，还需要以下1点：
	namespace的值 ，就是  接口的全类名（ 接口 - mapper.xml 一一对应）

	
#### 匹配的过程：（约定的过程）  

+ 根据 接口名 找到 mapper.xml文件（根据的是namespace=接口全类名）  

+ 根据 接口的方法名 找到 mapper.xml文件中的SQL标签 （方法名=SQL标签Id值）  

以上2点可以保证： 当我们调用接口中的方法时，
程序能自动定位到 某一个Mapper.xml文件中的sqL标签


习惯：SQL映射文件（mapper.xml） 和 接口放在同一个包中 （注意修改conf.xml中加载mapper.xml文件的路径）

以上，可以通过接口的方法->SQL语句

执行：    

	StudentMapper studentMapper = session.getMapper(StudentMapper.class) ;
	studentMapper.方法();

通过session对象获取接口（session.getMapper(接口.class);），再调用该接口中的方法，程序会自动执行该方法对应的SQL。



优化  
+ 可以将配置信息 单独放入 db.properties文件中，然后再动态引入
	
 db.properties：
	k=v

<configuration>

	<properties  resource="db.properties"/>

引入之后，使用${key}

+ MyBatis全局参数
在conf.xml中设置
	<!-- 
	<settings>
			<setting name="cacheEnabled" value="false"  />
			<setting name="lazyLoadingEnabled" value="false"  />
	</settings>
 	-->

+ 别名 conf.xml
a.设置单个别名



b.批量设置别名

	<typeAliases>
		<!-- 单个别名 （别名 忽略大小写） -->
		<!-- <typeAlias type="org.lanqiao.entity.Student" alias="student"/> -->
		<!--  批量定义别名  （别名 忽略大小写），以下会自动将该包中的所有类 批量定义别名： 别名就是类名（
不带包名，忽略大小写）   -->
		<package name="org.lanqiao.entity"/>
	</typeAliases>


除了自定义别名外，MyBatis还内置了一些常见类的别名。

类型处理器（类型转换器）
1.MyBatis自带一些常见的类型处理器
	int  - number

2.自定义MyBatis类型处理器

	java -数据库(jdbc类型)
示例：
实体类Student :  boolean   stuSex  	
			true:男
			false：女

表student：	number  stuSex
			1:男
			0：女
自定义类型转换器（boolean -number）步骤：
a.创建转换器：需要实现TypeHandler接口
	通过阅读源码发现，此接口有一个实现类 BaseTypeHandler ，因此 要实现转换器有2种选择：
	i.实现接口TypeHandler接口
	ii.继承BaseTypeHandler
b.配置conf.xml


需要注意的问题：  INTEGER


```
insert into student(stuno,stuname,stuage,graname,stusex) values(#{stuNo}#{stuName},#{stuAge},#{graName} ,#{stuSex ,javaType=boolean  ,jdbcType=INTEGER   } ) 
```

注意#{stuNo} 中存放的是 属性值，需要严格区分大小写。

resultMap可以实现2个功能：
1.类型转换
2.属性-字段的映射关系

    <select id="queryStudentByStuno" 	parameterType="int"  	resultMap="studentMapping" >
		select * from student where stuno = #{stuno}
	</select>
	
	<resultMap type="student" id="studentMapping">
			<!-- 分为主键id 和非主键 result-->
			<id property="id"  column="stuno"  />
			<result property="stuName"  column="stuname" />
			<result property="stuAge"  column="stuage" />
			<result property="graName"  column="graname" />
			<result property="stuSex"  column="stusex"  javaType="boolean" jdbcType="INTEGER"/>
	
	</resultMap>
	











	
	