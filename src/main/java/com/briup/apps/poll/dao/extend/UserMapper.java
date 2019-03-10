package com.briup.apps.poll.dao.extend;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.briup.apps.poll.bean.Role;
import com.briup.apps.poll.bean.User;

import java.util.List;

/**
 * Created by sang on 2017/12/17.
 */
@Mapper
public interface UserMapper {
	// 分页查询	
	List<User> query(
			@Param("page") int page,
			@Param("pageSize") int pageSize,
			@Param("keywords") String keywords);
	long count(@Param("keywords") String keywords);

    User loadUserByUsername(@Param("username") String username);

    long reg(User user);

    int updateUserEmail(@Param("email") String email, @Param("id") Long id);

    List<User> getUserByNickname(@Param("nickname") String nickname);

    List<Role> getAllRole();

    int updateUserEnabled(@Param("enabled") Boolean enabled, @Param("uid") Long uid);

    int deleteUserById(Long uid);

    int deleteUserRolesByUid(Long id);

    int setUserRoles(@Param("rids") Long[] rids, @Param("id") Long id);

    User getUserById(@Param("id") Long id);
    
    void updateUser(@Param("user") User user);
    // id,password    
    void updateUserPassword(@Param("user") User user);
}
