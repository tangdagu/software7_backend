package com.edu.cqupt.software7.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.edu.cqupt.software7.entity.Notification;
import com.edu.cqupt.software7.entity.User;
import com.edu.cqupt.software7.vo.InsertNoticeVo;
import com.edu.cqupt.software7.vo.InsertUserVo;
import com.edu.cqupt.software7.vo.UserPwd;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author hp
* @description 针对表【Notification】的数据库操作Mapper
* @createDate 2023-05-16 16:44:39
* @Entity com.cqupt.software_1.entity.User
*/


@Mapper
public interface NoticeMapper extends BaseMapper<Notification> {




    Page<Notification> selectAllNotices();

    void saveNotification(InsertNoticeVo notification);
}




