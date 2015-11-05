package com.affecto.stats.services;

import com.affecto.stats.repositories.InputDataSourceRepository;
import com.affecto.stats.repositories.InputQueryRepository;
import com.affecto.stats.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InputQueryRepository queryRepository;

    @Autowired
    private InputDataSourceRepository dataSourceRepository;

}
