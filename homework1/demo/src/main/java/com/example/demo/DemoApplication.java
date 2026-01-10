package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jackson.autoconfigure.JacksonProperties.Json;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import java.sql.*;
import jakarta.persistence.*;
import java.time.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import org.springframework.transaction.annotation.Transactional;
import lombok.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

class Glo_var {
	public static String var1 = "This is a global variable";
}

class database_connection{
	static public Connection new_connection(){
		try{
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "helic", "helic"); 
			return conn;
		}catch (Exception e) {
			System.out.println("Error connecting to the database: " + e.getMessage());
			return null;
		}
	};
}

class Func{
	static public <T> List<T> select_where_equal(EntityManager em, Class<T> entityClass,String key, Object value){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(entityClass);
		Root<T> root = cq.from(entityClass);
		try{
			System.out.println("key is: "+root.get(key));
			cq.select(root).where(cb.equal(root.get(key), value));
		}
		catch(Exception e){
			cq.select(root).where(cb.equal(root.join(key).get("id"), value));
		}
		return em.createQuery(cq).getResultList();
	}

	static public void func1(){
		while(true){
			System.out.println(Glo_var.var1);
			try{
				Thread.sleep(1000);
			}
			catch(Exception e){
				System.out.println("Error in func1: " + e.getMessage());
			}
		}
		
	}
}

@Entity
@Table(name = "table_group")
class sql_table_group {
	@Id
	String group_id;
	String group_name;
	sql_table_group(String group_id, String group_name){
		this.group_id=group_id;
		this.group_name=group_name;
	}
	sql_table_group() {}
}
interface Repository_table_group extends JpaRepository<sql_table_group,String>{}

@Entity
@Table(name = "table_user")
class sql_table_user {
	@Id
	String user_id;
	String user_name;
	String user_type;
	int user_coin;
	sql_table_user(){}
	sql_table_user(String user_id, String user_name){
		this.user_id=user_id;
		this.user_name=user_name;
	}
	sql_table_user(String user_id, String user_name, String user_type, int user_coin){
		this.user_id=user_id;
		this.user_name=user_name;
		this.user_type=user_type;
		this.user_coin=user_coin;
	}
}
interface Repository_table_user extends JpaRepository<sql_table_user,String>{}

@Entity
@Table(name = "table_topic")
class sql_table_topic {
	@Id
	String topic_id;
	String topic_name;
	String topic_group_id;
	sql_table_topic(){}
	sql_table_topic(String topic_id, String topic_name, String topic_group_id){
		this.topic_id=topic_id;
		this.topic_name=topic_name;
		this.topic_group_id=topic_group_id;
	}
}
interface Repository_table_topic extends JpaRepository<sql_table_topic,String>{}

@Entity
@Table(name = "table_message")
class sql_table_message {
	@Id
	String message_id;

	@Lob
	String message_content;

	@CreationTimestamp
	LocalDateTime message_time;

	@ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "user_id")
	sql_table_user user_id;

	@ManyToOne
	@JoinColumn(name="topic_id",referencedColumnName = "topic_id")
	sql_table_topic topic_id;

	sql_table_message(){}
	sql_table_message(String message_id, String message_content,sql_table_user user_id, sql_table_topic topic_id){
		this.message_id=message_id;
		this.message_content=message_content;
		//this.message_time=message_time;
		this.user_id=user_id;
		this.topic_id=topic_id;
	}
}
interface Repository_table_message extends JpaRepository<sql_table_message,String>{}

@Entity
@Table(name = "table_id_max")
class sql_table_id_max {
	@Id
	String type;
	Long id_max;
	sql_table_id_max(String type,Long id_max){
		this.type=type;
		this.id_max=id_max;
	}
	sql_table_id_max(){}
}
interface Repository_table_id_max extends JpaRepository<sql_table_id_max,String>{}

@Entity
@Table(name = "table_product")
class sql_table_product {
	@Id
	String product_id;
	String product_name;
	int product_price;
	int product_quantity;
	int product_status=0;

	@ManyToOne
	@JoinColumn(name="product_seller_id",referencedColumnName = "user_id")
	sql_table_user product_seller_id;

	sql_table_product(){}
	sql_table_product(String product_id, String product_name, int product_price, int product_quantity, sql_table_user product_seller_id){
		this.product_id=product_id;
		this.product_name=product_name;
		this.product_price=product_price;
		this.product_quantity=product_quantity;
		this.product_seller_id=product_seller_id;
	}
}
interface Repository_table_product extends JpaRepository<sql_table_product,String>{}

@Entity
@Table(name = "table_order_user")
class sql_table_order_user{
	@Id
	String order_id;

	@CreationTimestamp
	LocalDateTime order_time;

	String order_status;

	@ManyToOne
	@JoinColumn(name="order_user_id",referencedColumnName = "user_id")
	sql_table_user order_user_id;

	sql_table_order_user(){}
	sql_table_order_user(String order_id, sql_table_user order_user_id, String order_status){
		this.order_id=order_id;
		this.order_user_id=order_user_id;
		this.order_status=order_status;
	}
}
interface Repository_table_order_user extends JpaRepository<sql_table_order_user,String>{}

@Entity
@Table(name = "table_order_product")
class sql_table_order_product{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
	@ManyToOne
	@JoinColumn(name="order_id",referencedColumnName = "order_id")
	sql_table_order_user order_id;

	String product_id;
	String product_name;
	int product_price;
	int product_quantity;
	int product_status=0;

	@ManyToOne
	@JoinColumn(name="product_seller_id",referencedColumnName = "user_id")
	sql_table_user product_seller_id;

	sql_table_order_product(){}
	sql_table_order_product(
		sql_table_order_user order_id,
		String product_id,
		String product_name,
		int product_price,
		int product_quantity,
		sql_table_user product_seller_id
	){		
		this.order_id=order_id;
		this.product_id=product_id;
		this.product_name=product_name;
		this.product_price=product_price;
		this.product_quantity=product_quantity;
		this.product_seller_id=product_seller_id;
	}
}
interface Repository_table_order_product extends JpaRepository<sql_table_order_product,Long>{}

@Component
class Common{
	@Autowired
	public JpaRepository<sql_table_id_max,String> repository_table_id_max;	
	@Autowired
	public JpaRepository<sql_table_group,String> repository_table_group; 
	@Autowired
	public JpaRepository<sql_table_user,String> repository_table_user;
	@Autowired
	public JpaRepository<sql_table_topic,String> repository_table_topic;
	@Autowired
	public JpaRepository<sql_table_message,String> repository_table_message;
	@Autowired
	public JpaRepository<sql_table_product,String> repository_table_product;
	@Autowired
	public JpaRepository<sql_table_order_user,String> repository_table_order_user;
	@Autowired
	public JpaRepository<sql_table_order_product,Long> repository_table_order_product;
	@Autowired
    public JavaMailSender mailSender;
}

@Component
class Init implements CommandLineRunner {

	@PersistenceContext
	public EntityManager entityManager;

    @Override
	@Transactional
    public void run(String... args) throws Exception {
		try{
			if(entityManager.find(sql_table_id_max.class, "user") == null)entityManager.persist(new sql_table_id_max("user", 100L));
			if(entityManager.find(sql_table_id_max.class, "topic") == null)entityManager.persist(new sql_table_id_max("topic", 100L));			
			if(entityManager.find(sql_table_id_max.class, "group") == null)entityManager.persist(new sql_table_id_max("group", 100L));
			if(entityManager.find(sql_table_id_max.class, "message") == null)entityManager.persist(new sql_table_id_max("message", 100L));
			if(entityManager.find(sql_table_id_max.class, "product") == null)entityManager.persist(new sql_table_id_max("product", 100L));
			if(entityManager.find(sql_table_id_max.class, "order") == null)entityManager.persist(new sql_table_id_max("order", 100L));

			if(entityManager.find(sql_table_group.class, "100") == null)entityManager.persist(new sql_table_group("100", "公共群"));
			entityManager.flush();
		}
		catch(Exception e){
			Glo_var.var1 = "Error in Init: " + e.getMessage();
		}
    }
}

@SpringBootApplication
public class DemoApplication {

	static void init(){
		try(Connection conn = database_connection.new_connection();) {
			Statement sql_executor=conn.createStatement();
			sql_executor.execute("create database if not exists helic_mysql_shop");
		}
		catch(Exception e){
			System.out.println("Error connecting to the database: " + e.getMessage());
		}
	}
	public static void main(String[] args) {
		init();
		try(Connection conn = database_connection.new_connection();) {
			Statement sql_executor=conn.createStatement();

			System.out.println("");


			ResultSet result0=sql_executor.executeQuery("show databases");
			while (result0.next()){				
				System.out.println(result0.getString(1));				
			}

			//sql_executor.execute("ALTER DATABASE helic_mysql_test CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;");
			
			try {
				// 创建两个独立的 Statement
				Statement stmt1 = conn.createStatement();
				Statement stmt2 = conn.createStatement();
				
				// 使用第一个 Statement 获取所有表
				ResultSet tables = stmt1.executeQuery("SHOW TABLES FROM helic_mysql_shop");
				
				while (tables.next()) {
					String tableName = tables.getString(1);
					System.out.println("=== 表名: " + tableName + " ===");
					
					// 使用第二个 Statement 查询表结构
					ResultSet columns = stmt2.executeQuery("DESCRIBE helic_mysql_shop." + tableName);
					
					while (columns.next()) {
						String columnName = columns.getString("Field");
						String columnType = columns.getString("Type");
						String columnNull = columns.getString("Null");
						String columnKey = columns.getString("Key");
						String columnDefault = columns.getString("Default");
						String columnExtra = columns.getString("Extra");
						
						System.out.println("列名: " + columnName + 
										" | 类型: " + columnType + 
										" | 允许空: " + columnNull + 
										" | 键: " + columnKey + 
										" | 默认值: " + columnDefault + 
										" | 额外: " + columnExtra);
					}
					columns.close();
					System.out.println();
				}
				
				tables.close();
				stmt1.close();
				stmt2.close();
				
			} catch (SQLException e) {
				System.err.println("数据库操作错误: " + e.getMessage());
				e.printStackTrace();
			}
			
			sql_executor.execute("use helic_mysql_shop");
			/*
			System.out.println("");
			ResultSet result=sql_executor.executeQuery("select * from table_id_max");
			while (result.next()){				
				System.out.println(result.getString(1)+" "+result.getString(2));				
			}
			*/

		}catch (Exception e) {
			System.out.println("Error connecting to the database: " + e.getMessage());
		}

		//new Thread(() -> Func.func1()).start();

		SpringApplication.run(DemoApplication.class, args);

	}

}



class Json_user {
	public String user_id;
	public String user_name;
	public String user_id_verify;
	Json_user(String user_id, String user_name, String user_id_verify) {
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_id_verify = user_id_verify;
	}
}
class Json_get_group {
	public String user_id_verify;
	public String sort_type;
	public String sort_order;
}
class Json_group_item {
	public String group_id;
	public String group_name;
	Json_group_item(String group_id, String group_name) {
		this.group_id = group_id;
		this.group_name = group_name;
	}
}
class Json_get_group_result {
	public String success_status="success";
	public List<Json_group_item> group_list;
	Json_get_group_result(String success_status) {
		this.success_status=success_status;
	}
	Json_get_group_result() {
		this.success_status="success";
	}
}
class Json_get_topic {
	public String user_id_verify;
	public String group_id;
	public String sort_type;
	public String sort_order;
}
class Json_topic_item {
	public String topic_id;
	public String topic_name;
	public String topic_group_id;
	Json_topic_item(String topic_id, String topic_name, String topic_group_id) {
		this.topic_id = topic_id;
		this.topic_name = topic_name;
		this.topic_group_id = topic_group_id;
	}
}
class Json_get_topic_result {
	public String success_status="success";
	public List<Json_topic_item> topic_list;
	Json_get_topic_result(String success_status) {
		this.success_status=success_status;
	}
	Json_get_topic_result() {
		this.success_status="success";
	}
}
class Json_create_topic {
	public String user_id_verify;
	public String group_id;
}
class Json_create_topic_result {
	public String success_status="success";
	public String topic_id;
	Json_create_topic_result(String success_status, String topic_id) {
		this.success_status=success_status;
		this.topic_id=topic_id;
	}
	Json_create_topic_result(String topic_id) {
		this.success_status="success";
		this.topic_id=topic_id;
	}
	Json_create_topic_result(int topic_id) {
		this.success_status="success";
		this.topic_id=Integer.toString(topic_id);
	}
}
class Json_get_message {
	public String user_id_verify;
	public String topic_id;
}
class Json_message_item {
	public String message_id;
	public String message_content;
	public String message_time;
	public String user_id;
	public String topic_id;
	Json_message_item(String message_id, String message_content, String message_time, String user_id, String topic_id) {
		this.message_id = message_id;
		this.message_content = message_content;
		this.message_time = message_time;
		this.user_id = user_id;
		this.topic_id = topic_id;
	}
}
class Json_get_message_result {
	public String success_status="success";
	public List<Json_message_item> message_list;
	Json_get_message_result(String success_status) {
		this.success_status=success_status;
	}
	Json_get_message_result() {
		this.success_status="success";
	}
}
class Json_create_message {
	public String user_id_verify;
	public String message_content;
	public String topic_id;	
}
class Json_success_status {
	public String success_status="success";
	Json_success_status(String success_status) {
		this.success_status=success_status;
	}
	Json_success_status() {
		this.success_status="success";
	}	
}
class Json_sign_in {
	public String user_id;
	public String user_type;
}
class Json_sign_up {
	public String sign_up;
	public String user_type;
}
class Json_create_product {
	public String product_seller_id;
	public String product_name;
	public int product_price;
	public int product_quantity;
}
class Json_get_seller_product {
	public String user_id_verify;
	Json_get_seller_product(String user_id_verify) {
		this.user_id_verify=user_id_verify;
	}
}
class Json_product_item {
	public String product_id;
	public String product_name;
	public int product_price;
	public int product_quantity;
	public String product_seller_id;
	public String product_seller_name;
	public Long product_order_id; 
	Json_product_item(String product_id, String product_name, int product_price, int product_quantity,String product_seller_id, String product_seller_name) {
		this.product_id = product_id;
		this.product_name = product_name;
		this.product_price = product_price;
		this.product_quantity = product_quantity;
		this.product_seller_id = product_seller_id;
		this.product_seller_name = product_seller_name;
	}
	Json_product_item(String product_id, String product_name, int product_price, int product_quantity) {
		this.product_id = product_id;
		this.product_name = product_name;
		this.product_price = product_price;
		this.product_quantity = product_quantity;
	}
	Json_product_item(String product_id, String product_name, int product_price, int product_quantity, String product_seller_id, String product_seller_name, Long product_order_id) {
		this(product_id, product_name, product_price, product_quantity, product_seller_id, product_seller_name);
		this.product_order_id = product_order_id;
	}
}
class Json_get_seller_product_result {
	public String success_status="success";
	public List<Json_product_item> product_list;
	Json_get_seller_product_result(String success_status) {
		this.success_status=success_status;
	}
	Json_get_seller_product_result() {
		this.success_status="success";
	}	
}
class Json_edit_product {
	public String product_id;
	public String product_seller_id;
	public String product_name;
	public int product_price;
	public int product_quantity;
}
class Json_delete_product {
	public String product_id;
	public String product_seller_id;
}
class Json_blank {
}
class Json_user_id_verify {
	public String user_id_verify;
	Json_user_id_verify() {}
	Json_user_id_verify(String user_id_verify) {
		this.user_id_verify=user_id_verify;
	}
}
class Json_get_customer_value_result {
	public int user_coin;
	Json_get_customer_value_result(int user_coin) {
		this.user_coin=user_coin;
	}
	Json_get_customer_value_result() {}
}
class Json_create_order_product {
	public String user_id;
	public String product_id;
	public int order_quantity;
}
class Json_submit_order {
	public String user_id_verify;
	public String email;
}
class Json_delete_order_product {
	public String user_id;
	public String product_order_id;
}

@RestController
class Class_sign_in{
	@Autowired
	public Common common;

	@PersistenceContext
	public EntityManager entityManager;

	@PostMapping("/sign_in")
	@Transactional
	public Json_user func_sign_in(@RequestBody Json_sign_in json_sign_in) {
		String user_id=json_sign_in.user_id;
		sql_table_user result0=entityManager.find(sql_table_user.class, user_id);
		if (result0!=null&&result0.user_type.equals(json_sign_in.user_type)){
			System.out.println("\nsucceed!"+user_id+"\n");
			return new Json_user(user_id,user_id,user_id);
		}
		else{
			System.out.println("\nfail!"+"\n");
			return new Json_user("","","");
		}
		
	}

	@PostMapping("/sign_up")
	@Transactional
	public String func_sign_up(@RequestBody Json_sign_up json_sign_up) {
		Long user_id_max = -1L;
		sql_table_id_max result0=entityManager.find(sql_table_id_max.class, "user",LockModeType.PESSIMISTIC_WRITE);
		if (result0!=null){
			user_id_max=result0.id_max;
			user_id_max++;
			System.out.println("\nuser_id_max: "+user_id_max+"\n");
			entityManager.persist(new sql_table_user(user_id_max+"", user_id_max+"",json_sign_up.user_type,1000));
			result0.id_max=user_id_max;
			entityManager.flush();
		}
		return Long.toString(user_id_max);
	}
	

	@PostMapping("/get_group")
	@Transactional
	public Json_get_group_result postMethodName(@RequestBody Json_get_group json_get_group) {
		Json_get_group_result json_get_group_result = new Json_get_group_result();
		json_get_group_result.group_list=new ArrayList<>();
		List<sql_table_group> group_list=common.repository_table_group.findAll();
		for (sql_table_group group:group_list){
			json_get_group_result.group_list.add(new Json_group_item(group.group_id,group.group_name));
		}
		return json_get_group_result;
	}	

	@PostMapping("/get_topic")
	@Transactional
	public Json_get_topic_result postMethodName(@RequestBody Json_get_topic json_get_topic) {
		Json_get_topic_result json_get_topic_result = new Json_get_topic_result();
		json_get_topic_result.topic_list=new ArrayList<>();
		List<sql_table_topic> topic_list=Func.select_where_equal(entityManager, sql_table_topic.class, "topic_group_id", json_get_topic.group_id);
		for (sql_table_topic topic:topic_list){
			json_get_topic_result.topic_list.add(new Json_topic_item(topic.topic_id,topic.topic_name,topic.topic_group_id));
		}
		return json_get_topic_result;
	}

	@PostMapping("/create_topic")
	@Transactional
	public Json_create_topic_result postMethodName(@RequestBody Json_create_topic json_create_topic) {
		System.out.println("\njson_create_topic.user_id_verify: "+json_create_topic.user_id_verify+"\n");
		Long topic_id_max = -1L;		 
		sql_table_id_max result0=entityManager.find(sql_table_id_max.class, "topic",LockModeType.PESSIMISTIC_WRITE);
		try{
			if (result0!=null){
				topic_id_max=result0.id_max;
				topic_id_max++;
				System.out.println("\ntopic_id_max: "+topic_id_max+"\n");/**/
				entityManager.persist(new sql_table_topic(topic_id_max+"", topic_id_max+"", json_create_topic.group_id));
				System.out.println("\njson_create_topic.group_id: "+json_create_topic.group_id+"\n");
				result0.id_max=topic_id_max;
				entityManager.flush();
			}
			return new Json_create_topic_result(topic_id_max+"");
		}
		catch (Exception e) {
			System.out.println("Error updating topic_id_max: " + e.getMessage());
			return new Json_create_topic_result("failure", "");
		}
	}

	@PostMapping("/get_message")
	@Transactional
	public Json_get_message_result postMethodName(@RequestBody Json_get_message json_get_message) {
		try{
			System.out.println("\njson_get_message.user_id_verify: "+json_get_message.user_id_verify+"\n");
			Json_get_message_result json_get_message_result = new Json_get_message_result();
			json_get_message_result.message_list=new ArrayList<>();
			//List<sql_table_message> message_list=Func.select_where_equal(entityManager, sql_table_message.class, "topic_id", json_get_message.topic_id);
			
			JPAQueryFactory query = new JPAQueryFactory(entityManager);
        	Qsql_table_message qsql = Qsql_table_message.sql_table_message;
			List<sql_table_message> message_list=query.selectFrom(qsql).
			where(qsql.topic_id.topic_id.eq(json_get_message.topic_id)).
			orderBy(qsql.message_id.asc()).fetch();
			
			for (sql_table_message message:message_list){
				json_get_message_result.message_list.add(new Json_message_item(message.message_id,message.message_content,message.message_time.toString(),message.user_id.user_id,message.topic_id.topic_id));
			}
			return json_get_message_result;			
		}
		catch (Exception e) {
			System.out.println("Error in get_message: " + e.getMessage());
			return new Json_get_message_result("failure");
		}
	}
	
	@PostMapping("/create_message")
	@Transactional
	public Json_success_status postMethodName(@RequestBody Json_create_message json_create_message) {

		System.out.println("\njson_create_message.user_id_verify: "+json_create_message.user_id_verify+"\n");
		Long message_id_max = -1L;		 
		sql_table_id_max result0=entityManager.find(sql_table_id_max.class, "message",LockModeType.PESSIMISTIC_WRITE);
		try{
			if (result0!=null){
				message_id_max=result0.id_max;
				message_id_max++;
				System.out.println("\nmessage_id_max: "+message_id_max+"\n");/**/

				entityManager.persist(new sql_table_message(
					message_id_max+"",
					json_create_message.message_content,
					entityManager.getReference(sql_table_user.class, json_create_message.user_id_verify),
					entityManager.getReference(sql_table_topic.class, json_create_message.topic_id)));
				
				result0.id_max=message_id_max;
				entityManager.flush();
			}
			return new Json_success_status();
		}
		catch (Exception e) {
			System.out.println("Error updating message_id_max: " + e.getMessage());
			return new Json_success_status("failure");
		}
	}

	@Transactional
	public Json_get_seller_product_result func_get_seller_product(Json_get_seller_product json_get_seller_product) {
		Json_get_seller_product_result json_get_seller_product_result = new Json_get_seller_product_result();
		json_get_seller_product_result.product_list=new ArrayList<>();
		List<sql_table_product> product_list=entityManager.createQuery(
			"SELECT product FROM sql_table_product product WHERE product.product_seller_id.user_id=:user_id and product.product_status>=0", sql_table_product.class)
			.setParameter("user_id", json_get_seller_product.user_id_verify)
			.getResultList();
		//List<sql_table_product> product_list=Func.select_where_equal(entityManager, sql_table_product.class, "product_seller_id", json_get_seller_product.user_id_verify);
		for (sql_table_product product:product_list){	
			json_get_seller_product_result.product_list.add(new Json_product_item(
				product.product_id,
				product.product_name,
				product.product_price,
				product.product_quantity,
				product.product_seller_id.user_id,
				product.product_seller_id.user_name
			));
			System.out.println("\nproduct.product_id: "+product.product_id);

		}
		return json_get_seller_product_result;
	}

	@PostMapping("/get_seller_product")
	@Transactional
	public Json_get_seller_product_result get_seller_product(@RequestBody Json_get_seller_product json_get_seller_product) {
		return func_get_seller_product(json_get_seller_product);
	}

	@PostMapping("/create_product")
	@Transactional
	public Json_get_seller_product_result postMethodName(@RequestBody Json_create_product json_create_product) {
		System.out.println("\njson_create_product.product_seller_id: "+json_create_product.product_seller_id+"\n");
		Long product_id_max = -1L;
		sql_table_id_max result0=entityManager.find(sql_table_id_max.class, "product",LockModeType.PESSIMISTIC_WRITE);
		try{
			if (result0!=null){
				product_id_max=result0.id_max;
				product_id_max++;
				System.out.println("\nproduct_id_max: "+product_id_max+"\n");
				entityManager.persist(new sql_table_product(
					product_id_max+"",
					json_create_product.product_name,
					json_create_product.product_price,
					json_create_product.product_quantity,
					entityManager.getReference(sql_table_user.class, json_create_product.product_seller_id)));
				result0.id_max=product_id_max;
				entityManager.flush();
			}
		}
		catch (Exception e) {
			System.out.println("Error updating product_id_max: " + e.getMessage());
			return new Json_get_seller_product_result("failure");
		}
		return func_get_seller_product(new Json_get_seller_product(json_create_product.product_seller_id));
	} 

	@PostMapping("/edit_product")
	@Transactional
	public Json_get_seller_product_result postMethodName(@RequestBody Json_edit_product json_edit_product) {
		try{
			sql_table_product product=entityManager.find(sql_table_product.class, json_edit_product.product_id);
			if (product!=null){
				product.product_name=json_edit_product.product_name;
				product.product_price=json_edit_product.product_price;
				product.product_quantity=json_edit_product.product_quantity;
				entityManager.flush();
			}
		}
		catch (Exception e) {
			System.out.println("Error updating product: " + e.getMessage());
			return new Json_get_seller_product_result("failure");
		}
		return func_get_seller_product(new Json_get_seller_product(json_edit_product.product_seller_id));
	}

	@PostMapping("/delete_product")
	@Transactional
	public Json_get_seller_product_result postMethodName(@RequestBody Json_delete_product json_delete_product) {
		try{
			sql_table_product product=entityManager.find(sql_table_product.class, json_delete_product.product_id);
			if (product!=null){
				product.product_status=-1;
				entityManager.flush();
			}
		}
		catch (Exception e) {
			System.out.println("Error deleting product: " + e.getMessage());
			return new Json_get_seller_product_result("failure");
		}
		return func_get_seller_product(new Json_get_seller_product(json_delete_product.product_seller_id));
	}

	@PostMapping("/get_customer_product")
	@Transactional
	public Json_get_seller_product_result get_customer_product(@RequestBody Json_user_id_verify json_get_customer_product) {
		Json_get_seller_product_result json_get_customer_product_result = new Json_get_seller_product_result();
		json_get_customer_product_result.product_list=new ArrayList<>();
		List<sql_table_product> product_list=entityManager.createQuery(
			"SELECT product FROM sql_table_product product WHERE product.product_status>=0", sql_table_product.class)
			.getResultList();
		for (sql_table_product product:product_list){
			json_get_customer_product_result.product_list.add(new Json_product_item(
				product.product_id,
				product.product_name,
				product.product_price,
				product.product_quantity,
				product.product_seller_id.user_id,
				product.product_seller_id.user_name
			));
		}
		return json_get_customer_product_result;
	}

	@PostMapping("/get_customer_value")
	@Transactional
	public Json_get_customer_value_result get_customer_value(@RequestBody Json_user_id_verify json_get_customer_value) {
		Json_get_customer_value_result json_get_customer_value_result = new Json_get_customer_value_result();
		sql_table_user user=entityManager.find(sql_table_user.class, json_get_customer_value.user_id_verify);
		if (user!=null){
			json_get_customer_value_result.user_coin=user.user_coin;
		}
		return json_get_customer_value_result;
	}

	@PostMapping("/create_order_product")
	@Transactional
	public Json_success_status func_create_order_product(@RequestBody Json_create_order_product json_create_order_product) {
		sql_table_product product=entityManager.find(sql_table_product.class, json_create_order_product.product_id,LockModeType.PESSIMISTIC_WRITE);
		if (product!=null){
			if (product.product_quantity>=json_create_order_product.order_quantity){
				System.out.println("\nproduct.product_quantity: "+product.product_quantity+"\n");
				product.product_quantity-=json_create_order_product.order_quantity;
				
				List<sql_table_order_user> order_user_list=entityManager.createQuery(
					"""
					SELECT order_user FROM sql_table_order_user order_user 
					WHERE order_user.order_user_id.user_id=:user_id and order_user.order_status='current'
					"""	, sql_table_order_user.class)
					.setParameter("user_id", json_create_order_product.user_id)
					.getResultList();
				if(order_user_list.size()==0){
					Long order_id_max = -1L;
					sql_table_id_max result0=entityManager.find(sql_table_id_max.class, "order",LockModeType.PESSIMISTIC_WRITE);
					if (result0!=null){
						order_id_max=result0.id_max;
						order_id_max++;
						System.out.println("\norder_id_max: "+order_id_max+"\n");
						entityManager.persist(new sql_table_order_user(
							order_id_max+"",
							entityManager.getReference(sql_table_user.class, json_create_order_product.user_id),
							"current"));
						result0.id_max=order_id_max;
						entityManager.flush();
					}
					order_user_list=entityManager.createQuery(
						"""
						SELECT order_user FROM sql_table_order_user order_user 
						WHERE order_user.order_user_id.user_id=:user_id and order_user.order_status='current'
						"""	, sql_table_order_user.class)
						.setParameter("user_id", json_create_order_product.user_id)
						.getResultList();
				}
				else if(order_user_list.size()>1){
					return new Json_success_status("failure");
				}
				else{
					System.out.println("\norder_user_list.size(): "+order_user_list.size()+"\n");
				}

				entityManager.persist(new sql_table_order_product(
					order_user_list.get(0),
					product.product_id,
					product.product_name,
					product.product_price,
					json_create_order_product.order_quantity,
					product.product_seller_id
				));
				entityManager.flush();
				return new Json_success_status();
			}
			else{
				System.out.println("\nNot enough product quantity: "+product.product_quantity+"\n");
				return new Json_success_status("failure");
			}
		}
		else{
			return new Json_success_status("failure");
		}	
	}

	@PostMapping("/get_customer_order")
	@Transactional
	public Json_get_seller_product_result func_get_order_product(@RequestBody Json_user_id_verify json_get_order_product) {

		Json_get_seller_product_result json_get_order_product_result = new Json_get_seller_product_result();
		json_get_order_product_result.product_list=new ArrayList<>();
		List<sql_table_order_user> order_user_list=entityManager.createQuery(
			"""
			SELECT order_user FROM sql_table_order_user order_user 
			WHERE order_user.order_user_id.user_id=:user_id and order_user.order_status='current'
			"""	, sql_table_order_user.class)
			.setParameter("user_id", json_get_order_product.user_id_verify)		
			.getResultList();
		if(order_user_list.size()==0){
			return json_get_order_product_result;
		}
		else if(order_user_list.size()>1){
			return json_get_order_product_result;
		}
		else{
			System.out.println("\norder_user_list.size(): "+order_user_list.size()+"\n");
		}
		List<sql_table_order_product> order_product_list=entityManager.createQuery(
			"""
			SELECT order_product FROM sql_table_order_product order_product 
			WHERE order_product.order_id.order_id=:order_id
			"""	, sql_table_order_product.class)
			.setParameter("order_id", order_user_list.get(0).order_id)		
			.getResultList();
		for (sql_table_order_product order_product:order_product_list){
			json_get_order_product_result.product_list.add(new Json_product_item(
				order_product.product_id,
				order_product.product_name,
				order_product.product_price,
				order_product.product_quantity,
				order_product.product_seller_id.user_id,
				order_product.product_seller_id.user_name,
				order_product.id
			));
		}	
		return json_get_order_product_result;
	}

	@PostMapping("/submit_order")
	@Transactional
	public Json_success_status func_submit_order(@RequestBody Json_submit_order json_submit_order) {
		List<sql_table_order_user> order_user_list=entityManager.createQuery(
			"""
			SELECT order_user FROM sql_table_order_user order_user 
			WHERE order_user.order_user_id.user_id=:user_id and order_user.order_status='current'
			"""	, sql_table_order_user.class)
			.setParameter("user_id", json_submit_order.user_id_verify)		
			.getResultList();
		if(order_user_list.size()==0){
			return new Json_success_status("failure");
		}
		else if(order_user_list.size()>1){
			return new Json_success_status("failure");
		}
		else{
			System.out.println("\norder_user_list.size(): "+order_user_list.size()+"\n");
		}
		
		sql_table_order_user order_user=order_user_list.get(0);
		
		
		List<sql_table_order_product> order_product_list=entityManager.createQuery(
			"""
			SELECT order_product FROM sql_table_order_product order_product 
			WHERE order_product.order_id.order_id=:order_id
			"""	, sql_table_order_product.class)
			.setParameter("order_id", order_user.order_id)		
			.getResultList();
		int total_price=0;
		for (sql_table_order_product order_product:order_product_list){
			total_price+=order_product.product_price*order_product.product_quantity;
		}
		sql_table_user user=entityManager.find(sql_table_user.class, order_user.order_user_id.user_id,LockModeType.PESSIMISTIC_WRITE);
		if(user.user_coin>=total_price){
			user.user_coin-=total_price;
			System.out.println("\nuser.user_coin: "+user.user_coin+"\n");
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("");//输入发送方的邮箱
			System.out.println("email: "+json_submit_order.email+"");
			message.setTo(json_submit_order.email+"");
			message.setSubject("确认收货");
			message.setText("确认收货");
			common.mailSender.send(message);
			System.out.println("\nemail: "+json_submit_order.email+"\n");

		}
		else{
			return new Json_success_status("failure");
		}


		order_user.order_status="submitted";
		return new Json_success_status();
	}

	@PostMapping("/delete_order_product")
	@Transactional
	public Json_success_status func_delete_order_product(@RequestBody Json_delete_order_product json_delete_order_product) {
		try{
			sql_table_order_product order_product=entityManager.find(sql_table_order_product.class, Long.parseLong(json_delete_order_product.product_order_id),LockModeType.PESSIMISTIC_WRITE);
			if (order_product!=null){
				sql_table_product product=entityManager.find(sql_table_product.class, order_product.product_id,LockModeType.PESSIMISTIC_WRITE);
				if (product!=null){
					product.product_quantity+=order_product.product_quantity;
				}
				entityManager.remove(order_product);
				entityManager.flush();
			}
			return new Json_success_status();
		}
		catch (Exception e) {
			System.out.println("Error deleting order product: " + e.getMessage());
			return new Json_success_status("failure");
		}
		
		
		
	}
	

}