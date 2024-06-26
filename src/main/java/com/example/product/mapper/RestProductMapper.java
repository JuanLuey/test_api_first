package com.example.product.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.example.product.entity.Products;

public class RestProductMapper implements RowMapper<Products>{
    @Override
    @Nullable

        public Products mapRow(ResultSet rs, int rowNum) throws SQLException {        
        var result = new Products();
        result.setSku(rs.getString("sku"));
        result.setName(rs.getString("name"));
        result.setStatus(rs.getString("status"));
        return result;
    }
}
