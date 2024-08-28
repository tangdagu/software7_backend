package com.edu.cqupt.software7.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.cqupt.software7.entity.User;
import com.edu.cqupt.software7.vo.UserPwd;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Update("UPDATE user_management SET upload_size = upload_size-#{size} WHERE uid = #{id}")
    int decUpdateUserColumnById(@Param("id") String id, @Param("size") Double size);

    @Update("UPDATE user_management SET upload_size = upload_size+#{size} WHERE uid = #{id}")
    int recoveryUpdateUserColumnById(@Param("id") String id, @Param("size") Double size);

    User queryByUername(String username);
    User selectByUid(String uid);

    Integer updateByname(String newpassword,String username);

    //yx 新增
    //用户管理新增
    void saveUser(User user);
    List<User> selectUserPage(@Param("offset") int offset,@Param("pageSize") int pageSize);
    int countUsers();
    boolean updateStatusById(@Param("uid") String uid,@Param("role") Integer role,@Param("allSize") double allSize,@Param("status") String status,@Param("uploadSize") double uploadSize);
    void removeUserById(String uid);

    void updatePwd(UserPwd user);
}
