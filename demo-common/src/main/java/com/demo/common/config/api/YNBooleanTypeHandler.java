package com.demo.common.config.api;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes({boolean.class, Boolean.class})
public class YNBooleanTypeHandler implements TypeHandler<Boolean> {

    @Override
    public Boolean getResult(ResultSet rs, String labelName) throws SQLException {
        return toBoolean(rs.getString(labelName));
    }

    @Override
    public Boolean getResult(ResultSet rs, int labelName) throws SQLException {
        return toBoolean(rs.getString(labelName));
    }

    @Override
    public Boolean getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toBoolean(cs.getString(columnIndex));
    }

    @Override
    public void setParameter(PreparedStatement ps, int columnIndex, Boolean value, JdbcType jdbcType) throws SQLException {
        if(value == null) {
            ps.setString(columnIndex, null);
        } else if(value == true) {
            ps.setString(columnIndex, "Y");
        } else {
            ps.setString(columnIndex, "N");
        }
    }

    private Boolean toBoolean(String value) {
        if(value == null) {
            return null;
        }

        if(value.equals("Y") || value.equals("y") || value.equals("1")) {
            return true;
        } else {
            return false;
        }
    }
}
