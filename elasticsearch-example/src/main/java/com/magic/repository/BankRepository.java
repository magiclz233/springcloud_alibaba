package com.magic.repository;

import com.magic.entity.Bank;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author magic_lz
 * @version 1.0
 * @classname BankRepository
 * @date 2021/2/8 : 9:24
 */
public interface BankRepository extends ElasticsearchRepository<Bank, String> {

}
