package com.example.mongodb.dao.impl;

import com.example.mongodb.dao.TabRepository;
import com.example.mongodb.entity.TabModel;
import org.springframework.stereotype.Repository;

/**
 * {@link TabRepositoryImpl}
 *
 * @author Liyaohui
 * @date 5/31/21
 */
@Repository
public class TabRepositoryImpl extends BaseMongoRepositoryImpl<TabModel> implements TabRepository {}
