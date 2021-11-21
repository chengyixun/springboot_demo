package com.example.jpamultitenancy.tenant.repository;

import com.example.jpamultitenancy.dto.UserRecordDTO;
import com.example.jpamultitenancy.tenant.entity.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName: UserRecordRepository @Author: amy @Description: UserRecordRepository @Date:
 * 2021/5/25 @Version: 1.0
 */
public interface UserRecordRepository extends JpaRepository<UserRecord, Long> {

  // SELECT r.username,SUM(r.offline_num) ,SUM(r.online_num) FROM biz_user_record
  // r GROUP BY r.username;
  @Query(
      value =
          "SELECT new com.example.jpamultitenancy.dto.UserRecordDTO(r.username,SUM(r.offlineNum) ,SUM(r.onlineNum)) FROM UserRecord r GROUP BY r.username")
  List<UserRecordDTO> aggUserRecords();
}
