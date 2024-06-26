package com.example.product.repository;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import com.example.product.entity.Products;
import com.example.product.mapper.RestProductMapper;

@Repository
public class RestProductRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Products> getSkus() {

        List<Products> result = null;

        String query = """
                SELECT P.PRD_LVL_NUMBER AS SKU,
                P.PRD_FULL_NAME NAME,
                P.DES_EST STATUS
                FROM EDSR.IFHPRDMST P
                WHERE ROWNUM <=10
                ORDER BY DBMS_RANDOM.VALUE
                    """;
        result = jdbcTemplate.query(query,
                new Object[] {},
                new int[] {},
                new RestProductMapper());

        return result;
    }

    public List<Products> findBySKU(String sku) {
        List<Products> result = null;

        String query = """
                SELECT P.PRD_LVL_NUMBER AS SKU,
                P.PRD_FULL_NAME NAME,
                P.DES_EST STATUS
                FROM EDSR.IFHPRDMST P
                WHERE RTRIM(P.PRD_LVL_NUMBER)=?
                """;

        result = jdbcTemplate.query(query,
                new Object[] { sku },
                new int[] { Types.CHAR },
                new RestProductMapper());

        return result;
    }

}
