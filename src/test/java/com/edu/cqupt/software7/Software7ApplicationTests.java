package com.edu.cqupt.software7;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.cqupt.software7.common.R;
import com.edu.cqupt.software7.entity.FilterDataInfo;
import com.edu.cqupt.software7.entity.User;
import com.edu.cqupt.software7.mapper.FilterDataInfoMapper;
import com.edu.cqupt.software7.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@SpringBootTest
class Software7ApplicationTests {
//	@Autowired
//	UserMapper userMapper;
//	@Test
//	void contextLoads() {
//	}
//
////	@Test
////	@DisplayName("插入数据")
////	public void testInsert(){
////		User user=new User(1,"test1","test","t123","男","test1@qq.com","满都镇");
////		Integer id=userMapper.insert(user);
////		System.out.printf(id.toString());
////	}
//
//	@Test
//	@DisplayName("根据id查找")
//	public void testSelectById(){
//		User user=userMapper.selectById(1);
//		System.out.println(user.toString());
//	}
//
//	@Test
//	@DisplayName("查找所有")
//	public void testSelectAll(){
//		List userList=userMapper.selectObjs(null);
//		System.out.println(userList.size());
//	}
//
//	@Test
//	@DisplayName("更新")
//	public void testUpdate(){
//		User user=new User();
//		user.setId(1);
//		user.setAddress("金葫芦镇");
//		Integer id=userMapper.updateById(user);
//		System.out.println(id);
//	}
//
//	@Test
//	@DisplayName("删除")
//	public void testDelete(){
//		userMapper.deleteById(1);
//	}
//
//	@Test
//	public void selectPage(){
//		Page<User> page=new Page<>(1,5);
//		System.out.println(userMapper.selectUserPage(page,"sdf"));
//	}


    @GetMapping("/tables/tableName")
    public R getColumnByTableName(@PathVariable String tableName){

        return new R();
    }

    @Autowired
    FilterDataInfoMapper filterDataInfoMapper;

    @Test
    public void testFilterDataInfoMapper(){
        FilterDataInfo filterDataInfo = new FilterDataInfo();
        filterDataInfo.setUid("123");

        filterDataInfoMapper.insert(filterDataInfo);

        System.out.println("回显的id" + filterDataInfo.getId());

    }

}
