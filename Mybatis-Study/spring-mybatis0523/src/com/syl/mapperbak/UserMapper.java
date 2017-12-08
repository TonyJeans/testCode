package com.syl.mapperbak;

import java.util.List;

import com.syl.pojobak.CustomerOrders;
import com.syl.pojobak.Orders;
import com.syl.pojobak.QueryVo;
import com.syl.pojobak.User;

public interface UserMapper {
	
	//单条查询可以写List<User> 但多条不能写对象
	User findUserById(Integer id);
	
	//动态代理形式中,返回结构集list,那么mybatis在生成实现类的时候自动调用selectLsit方法
	List<User>	findUserByUserName(String username);
	
	void insertUser(User user);
	
	List<User> findUserByVo(QueryVo vo);
	
	Integer findUserCount();
	
	List<User> findUserByUserNameAndSex(User user);
	
	List<User> findUserByIdds(QueryVo vo);
	
	List<CustomerOrders> findOrdersAndUser1();
	
	List<Orders> findOrdersAndUser2();
	
	List<User> findUserAndOrders();
}
