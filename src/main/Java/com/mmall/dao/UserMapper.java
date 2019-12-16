package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(String username);

    int checkEmail(String email);

    // @Param("username") 为 mybaits 注解，在多参时需要使用
    User selectLogin(@Param("username") String username, @Param("password") String password);

    // 忘记密码时，通过用户名来获取密码忘记时的问题
    String selectQuestionByUsername(String username);

    // 校验忘记密码的问题答案是否正确
    int checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    // 更改密码
    int updatePasswordByUsername(@Param("username") String username, @Param("passwordNew") String passwordNew);

    // 验证密码，用于登录状态中的重置密码
    int checkPassword(@Param("password") String password, @Param("userId") Integer userId);

    // 检验email是否存在
    int checkEmailByUserId(@Param(value = "email") String email, @Param(value = "userId") Integer userId);

}